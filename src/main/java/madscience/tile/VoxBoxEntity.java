package madscience.tile;

import java.util.ArrayList;
import java.util.List;

import madscience.MadFurnaces;
import madscience.factory.MadTileEntityFactoryProduct;
import madscience.factory.mod.MadMod;
import madscience.factory.slotcontainers.MadSlotContainerTypeEnum;
import madscience.factory.sounds.MadSound;
import madscience.factory.tileentity.prefab.MadTileEntityPrefab;
import madscience.util.MadUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class VoxBoxEntity extends MadTileEntityPrefab
{
    /** Amount of time we will keep talking once activated. */
    private int talkTime;
    private int talkTimeMaximum;
    
    /** Provides a list of sounds, lengths and the order in which they should be played for update function. */
    private List<MadSound> talkTimeline;

    /** Determines how long we have been saying a particular word. */
    private float currentTalkWordStep;
    private float currentTalkWordMaximum;
    
    /** Last known index of spoken word we have said. */
    private int lastWordIndex;
    private int lastWordMaximum;

    /** Last literal word that was said to help ensure integrity along with index. */
    private String lastWordLiteral = "RESET";

    public VoxBoxEntity()
    {
        super();
    }

    public VoxBoxEntity(MadTileEntityFactoryProduct registeredMachine)
    {
        super(registeredMachine);
    }

    public VoxBoxEntity(String machineName)
    {
        super(machineName);
    }

    public boolean canSmelt()
    {
        super.canSmelt();
        
        // If our one and only input slot is not null then we are good to go!
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1) != null)
        {
            return true;
        }

        return false;
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack items)
    {
        // Check if input slot 1 is a written book.
        ItemStack compareWrittenBook = new ItemStack(Item.writtenBook);
        if (slot == 0)
        {
            if (compareWrittenBook.isItemEqual(items))
            {
                return true;
            }

            return false;
        }

        return false;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
    }

    @Override
    public void updateAnimation()
    {
        super.updateAnimation();
        
        // Active state has many textures based on item cook progress.
        if (this.canSmelt() && this.isPowered() && this.isRedstonePowered())
        {
            if (worldObj.getWorldTime() % 5L == 0L)
            {
                // Load this texture onto the entity.
                this.setTextureRenderedOnModel("models/" + MadFurnaces.VOXBOX_INTERNALNAME + "/voxBox0.png");
            }
            else
            {
                this.setTextureRenderedOnModel("models/" + MadFurnaces.VOXBOX_INTERNALNAME + "/voxBox1.png");
            }
        }
        else
        {
            // We are not powered or working.
            this.setTextureRenderedOnModel("models/" + MadFurnaces.VOXBOX_INTERNALNAME + "/voxBox0.png");
        }
    }

    @Override
    public void updateEntity()
    {
        // Important to call the class below us!
        super.updateEntity();

        // Server side processing for furnace.
        if (!this.worldObj.isRemote)
        {
            if (talkTime > 0 && talkTime < talkTimeMaximum)
            {
                // Animation for block.
                updateAnimation();
            }
            
            // First tick for new item being cooked in furnace.
            if (this.canSmelt() && this.isPowered() && this.isRedstonePowered() && this.talkTime <= 0 && talkTimeline == null && lastWordIndex <= 0 && lastWordMaximum <= 0)
            {
                // Read the written book.
                if (this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1) != null && this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1).stackTagCompound != null)
                {
                    // Read the book in the slot and parse it.
                    //MadMod.logger.info("VoxBox: STARTING PHRASE INTERPRETOR");
                    String bookContents = MadUtils.getWrittenBookContents(this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1).stackTagCompound);
                    
                    // Check if the string is null.
                    if (bookContents == null)
                    {
                        this.resetVOX();
                        return;
                    }
                    
                    // Check if the string is empty.
                    if (bookContents.isEmpty())
                    {
                        this.resetVOX();
                        return;
                    }
                    
                    // Breakup the books contents based on word.
                    List<String> splitBookContents = MadUtils.splitStringPerWord(bookContents, 1);
                    
                    // Create a new timeline we will populate with all words found in VOX registry.
                    talkTimeline = new ArrayList();
                    
                    // Add up the total amount of time it should take to say this phrase.
                    float estimatedTotalTalkTime = 0.0F;
                    
                    // Loop through the words and match them up to VOX sound registry.
                    for (String voxSound : splitBookContents)
                    {
                        MadSound registryVOXSound = MadMod.getSoundByName(voxSound);
                        if (registryVOXSound != null)
                        {
                            if (registryVOXSound.getSoundNameWithoutExtension().equals(voxSound))
                            {
                                talkTimeline.add(registryVOXSound);
                                estimatedTotalTalkTime += registryVOXSound.getSoundLengthInSeconds();
                                //MadMod.logger.info("VoxBox: Added word '" + registryVOXSound.internalName + "' with length of " + String.valueOf(registryVOXSound.duration) + "F");
                            }
                        }
                        else
                        {
                            // If we cannot find the word the player input then use period by default since it plays static.
                            MadSound soundNotFoundFiller = MadMod.getSoundByName("_period");
                            if (soundNotFoundFiller != null)
                            {
                                talkTimeline.add(soundNotFoundFiller);
                                estimatedTotalTalkTime += soundNotFoundFiller.getSoundLengthInSeconds();                                
                            }
                        }
                    }
                    
                    // Calculate max talk time by rounding the value to nearest second.
                    talkTimeMaximum = Math.round(estimatedTotalTalkTime) * MadUtils.SECOND_IN_TICKS;
                    //MadMod.logger.info("VoxBox: Prepared talk timeline with " + talkTimeline.size() + " entries and total length of " + String.valueOf(talkTimeMaximum) + " ticks.");
                    
                    // Determine if talk time is greater than zero.
                    if (talkTimeMaximum <= 0)
                    {
                        this.resetVOX();
                        return;
                    }
                    
                    // Setup length of talk timeline in packet terms.
                    lastWordIndex = 0;
                    lastWordMaximum = talkTimeline.size();
                    
                    // Kickstart the timer that will make the voice start talking.
                    currentTalkWordStep = 0.0F;
                    currentTalkWordMaximum = 0.0F;
                    talkTime++;
                }
            }
            else if (this.canSmelt() && this.isPowered() && this.talkTime > 0 && talkTimeline != null 
                    && currentTalkWordStep <= 0.0F && currentTalkWordMaximum <= 0.0F && lastWordIndex < lastWordMaximum)
            {
                // Decrease to amount of energy while the VOX announcer is talking.
                this.consumeEnergy(this.getEnergyConsumeRate());
                
                // Start saying each line of the file based on proper time index and tick time.
                int talkTimeIndex = 0;
                if (talkTimeMaximum > 0 && lastWordMaximum > 0)
                {
                    talkTimeIndex = (talkTime * lastWordMaximum) / talkTimeMaximum;
                }
                
                // First word so special circumstances are needed.
                if (talkTimeIndex <= 0 && talkTime == 1)
                {
                    talkTime++;
                    lastWordIndex = talkTimeIndex;
                    //MadMod.LOGGER.info("VoxBox: First word so special circumstances!");
                    return;
                }
                
                // Set last known index for talk time to prevent stuttering.
                if (talkTimeIndex > lastWordIndex && talkTimeIndex > 0)
                {
                    talkTime++;
                    lastWordIndex = talkTimeIndex;
                    //MadMod.LOGGER.info("VoxBox: Skipping index of " + String.valueOf(lastWordIndex - 1) + " because already played it!");
                }
                else if (talkTimeIndex <= lastWordIndex && talkTimeIndex > 0)
                {
                    // Count up to the next one.
                    talkTime++;
                    //MadMod.LOGGER.info("VoxBox: Skipping because last talk time is same as current talk time");
                    return;
                }
                
                // Check upper bounds of time index.
                if (lastWordIndex >= lastWordMaximum)
                {
                    lastWordIndex = lastWordMaximum;
                }
                
                // Check lower bounds of time index.
                if (lastWordIndex <= 0)
                {
                    // Force ourselves to start at index one.
                    lastWordIndex = 0;
                }
                
                // Wrap any index out of bounds exception to set limit
                MadSound talkTimeStep = null;
                try
                {
                    // Grab our current word to be spoken based on scaled time index.
                    talkTimeStep = talkTimeline.get(lastWordIndex);
                }
                catch (Exception err)
                {
                    this.resetVOX();
                    return;
                }
                
                // Abort if any problems occur.
                if (talkTimeStep == null)
                {
                    //MadMod.logger.info("VoxBox: [ERROR] Unable to get VoxBox Sound Item from VOX sound registry.");
                    return;
                }
                
                // Check if we have already said this word for this time index.
                if (lastWordLiteral != null && lastWordLiteral.equals(talkTimeStep.getSoundNameWithExtension()) && lastWordIndex <= lastWordMaximum)
                {
                    talkTime++;
                    //MadMod.logger.info("VoxBox: Skipping word of " + String.valueOf(lastWordLiteral) + " because already played it!");
                    return;
                }
                
                // Load up information into variables that will use it in coming ticks to say the VOX words in sync with tick time.
                currentTalkWordMaximum = talkTimeStep.getSoundLengthInSeconds();
                currentTalkWordStep = 0.0F;
                talkTime++;
                lastWordLiteral = talkTimeStep.getSoundNameWithoutExtension();
                this.worldObj.playSoundEffect(this.xCoord + 0.5F, this.yCoord + 0.5F, this.zCoord + 0.5F, MadMod.ID + ":" + MadFurnaces.VOXBOX_INTERNALNAME + "." + talkTimeStep.getSoundNameWithoutExtension(), 1.0F, 1.0F);
                MadMod.LOGGER.info("VoxBox: Speaking the word index " + String.valueOf(lastWordIndex) + " '" + talkTimeStep.getSoundNameWithoutExtension() + "' with length of " + String.valueOf(currentTalkWordMaximum) + "F.");
            }
            else if (this.canSmelt() && this.isPowered() && this.talkTime > 0 && talkTimeline != null && lastWordMaximum > 0 && currentTalkWordMaximum > 0.0F)
            {
                // Say the word we are supposed to at this time until we are done with it.
                if (currentTalkWordStep < currentTalkWordMaximum && talkTime < talkTimeMaximum && lastWordIndex < lastWordMaximum)
                {
                    currentTalkWordStep += 0.1F;
                    talkTime++;
                    //MadMod.logger.info("VoxBox: Counting word index " + String.valueOf(currentTalkWordStep) + "/" + String.valueOf(currentTalkWordMaximum));
                }
                else if (currentTalkWordStep >= currentTalkWordMaximum && talkTime < talkTimeMaximum && lastWordIndex < lastWordMaximum)
                {
                    // It is time to move to the next word in the index.
                    currentTalkWordStep = 0.0F;
                    currentTalkWordMaximum = 0.0F;
                    talkTime++;
                    //MadMod.logger.info("VoxBox: Finished with word, asking for another...");
                }
            }
            else if (talkTimeline != null && this.canSmelt() && this.isPowered() && this.talkTime >= this.talkTimeMaximum && lastWordIndex >= lastWordMaximum)
            {
                this.resetVOX();
            }

            this.sendUpdatePacket();
        }
    }

    private void resetVOX()
    {
        // Flatten the timer out to restart the process now that we are finished.
        talkTime = 0;
        talkTimeMaximum = 0;
        currentTalkWordStep = 0;
        currentTalkWordMaximum = 0;
        lastWordIndex = 0;
        lastWordMaximum = 0;
        lastWordLiteral = "RESET";
        talkTimeline = null;
        //MadMod.logger.info("VoxBox: STOPPING VOX, PHRASE COMPLETED!");
    }

    /** Writes a tile entity to NBT. */
    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
    }
}

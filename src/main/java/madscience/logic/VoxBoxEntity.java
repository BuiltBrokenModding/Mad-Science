package madscience.logic;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import madapi.container.MadSlotContainerTypeEnum;
import madapi.mod.MadModLoader;
import madapi.product.MadTileEntityFactoryProduct;
import madapi.sound.MadSound;
import madapi.tile.MadTileEntityPrefab;
import madapi.util.MadUtils;
import madscience.MadModMetadata;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class VoxBoxEntity extends MadTileEntityPrefab
{    
    /** Provides a list of sounds, lengths and the order in which they should be played for update function. */
    private Queue<MadSound> fifoSpeech = new LinkedList<MadSound>();

    /** Determines how long we have been saying a particular word. */
    private float currentTalkWordStep;
    
    /** Reference to current sound which should be played and counted. */
    private MadSound currentWordFromPhrase;
    
    /** Determines if entity should be attempting read a book and say the phrase inside of it. */
    private boolean shouldBeSpeaking = false;

    /** Reference to total length of the phrase we are going to say, prevents short phrases from repeating. */
    private int totalPhraseTimeInTicks;
    
    /** Reference to total time we should be speaking a phrase, this prevents repeating phrases from single button push */
    private int currentPhraseTimeInTicks;

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

    @Override
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
                this.setTextureRenderedOnModel("models/" + this.getMachineInternalName() + "/voxBox0.png");
            }
            else
            {
                this.setTextureRenderedOnModel("models/" + this.getMachineInternalName() + "/voxBox1.png");
            }
        }
        else
        {
            // We are not powered or working.
            this.setTextureRenderedOnModel("models/" + this.getMachineInternalName() + "/voxBox0.png");
        }
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();

        if (!this.worldObj.isRemote)
        {
            // Check if we should be playing animation and consuming energy.
            if (!fifoSpeech.isEmpty() && shouldBeSpeaking)
            {
                this.consumeInternalEnergy(this.getEnergyConsumeRate());
            }
            
            // -----------------------
            // WAIT FOR WORD TO FINISH
            // -----------------------
            if (this.canSmelt() && this.isPowered() && shouldBeSpeaking && currentPhraseTimeInTicks < totalPhraseTimeInTicks && currentTalkWordStep > 0.0F)
            {
                // Check if we have reached the end of this word in the total phrase.
                if (currentWordFromPhrase != null && currentTalkWordStep >= currentWordFromPhrase.getSoundLengthInSeconds())
                {
                    // Reset the current word counter.
                    currentTalkWordStep = 0.0F;
                    currentWordFromPhrase = null;
                }
                else
                {
                    // Count upwards toward total time for single word in phrase.
                    currentTalkWordStep += 0.05F;
                    
                    // Count upwards toward total time for this phrase.
                    currentPhraseTimeInTicks += 1.0F;
                    
                    // Debugging!
                    MadModLoader.log().info("VoxBox: Counting word index " + String.valueOf(currentTalkWordStep));
                }
            }
            
            // -----------------
            // SPEAK PHRASE WORD
            // -----------------
            if (this.canSmelt() && this.isPowered() && shouldBeSpeaking && currentTalkWordStep <= 0.0F && currentPhraseTimeInTicks <= totalPhraseTimeInTicks)
            {
                // Grab the next sound element from the FIFO list.
                currentWordFromPhrase = fifoSpeech.poll();
                
                if (currentWordFromPhrase != null)
                {
                    // Actually say the word, the timer will keep counting until we need the next one so sounds are evenly spaced out.
                    this.worldObj.playSoundEffect(this.xCoord + 0.5F, this.yCoord + 0.5F, this.zCoord + 0.5F, MadModMetadata.ID + ":" + this.getMachineInternalName() + "." + currentWordFromPhrase.getSoundNameWithoutExtension(), 1.0F, 1.0F);
                    currentTalkWordStep += 0.1F;
                    MadModLoader.log().info("VoxBox: Speaking the word '" + currentWordFromPhrase.getSoundNameWithoutExtension() + "' with length of " + String.valueOf(currentWordFromPhrase.getSoundLengthInSeconds()) + "F.");
                }
                else if (currentPhraseTimeInTicks < totalPhraseTimeInTicks)
                {
                    // Count upwards toward total time for this phrase.
                    currentPhraseTimeInTicks += 1.0F;
                    MadModLoader.log().info("VoxBox: Counting towards total phrase time " + String.valueOf(currentPhraseTimeInTicks) + "/" + String.valueOf(totalPhraseTimeInTicks));
                }
                else if (currentPhraseTimeInTicks >= totalPhraseTimeInTicks)
                {
                    resetVOX();
                    MadModLoader.log().info("VoxBox: Resetting phrase since end of timer reached.");
                }
            }
            
            // ---------
            // READ BOOK
            // ---------
            if (this.canSmelt() && this.isPowered() && this.isRedstonePowered() && fifoSpeech.isEmpty() && !shouldBeSpeaking && currentTalkWordStep <= 0.0F && currentPhraseTimeInTicks >= totalPhraseTimeInTicks)
            {
                // Check if we are still speaking the same phrase.
                if (currentPhraseTimeInTicks < totalPhraseTimeInTicks)
                {
                    MadModLoader.log().info("VoxBox: Cannot start another phrase until this one is complete!");
                    return;
                }
                
                // Read the written book.
                if (this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1) != null && this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1).stackTagCompound != null)
                {
                    // Read the book in the slot and parse it.
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
                    fifoSpeech.clear();
                    
                    // Reset total phrase time.
                    totalPhraseTimeInTicks = 0;
                    
                    // Reset current phrase time.
                    currentPhraseTimeInTicks = 0;
                    
                    // Loop through the words and match them up to VOX sound registry.
                    boolean anyValidWords = false;
                    for (String voxSound : splitBookContents)
                    {
                        MadSound registryVOXSound = MadModLoader.getSoundByName(voxSound);
                        if (registryVOXSound != null)
                        {
                            if (registryVOXSound.getSoundNameWithoutExtension().equals(voxSound))
                            {
                                anyValidWords = true;
                                fifoSpeech.add(registryVOXSound);
                                totalPhraseTimeInTicks += registryVOXSound.getSoundLengthInSeconds() * MadUtils.SECOND_IN_TICKS;
                            }
                        }
                        else
                        {
                            // If we cannot find the word the player input then use period by default since it plays static.
                            MadSound soundNotFoundFiller = MadModLoader.getSoundByName("_period");
                            if (soundNotFoundFiller != null)
                            {
                                fifoSpeech.add(soundNotFoundFiller);
                                totalPhraseTimeInTicks += soundNotFoundFiller.getSoundLengthInSeconds() * MadUtils.SECOND_IN_TICKS;
                            }
                        }
                    }
                    
                    // Only start speaking if there are any valid words at all.
                    if (anyValidWords)
                    {
                        // Increase total phrase time if less than three seconds.
                        int threeSecondsInTicks = (3*MadUtils.SECOND_IN_TICKS);
                        if (totalPhraseTimeInTicks <= threeSecondsInTicks)
                        {
                            totalPhraseTimeInTicks += threeSecondsInTicks;
                        }
                        
                        // Set the flag that will enable entity to start speaking from the list.
                        shouldBeSpeaking = true;
                        
                        // Debugging.
                        MadModLoader.log().info("VoxBox: Playing phrase with " + fifoSpeech.size() + " words, totaling " + (totalPhraseTimeInTicks / MadUtils.SECOND_IN_TICKS) + " seconds.");
                    }
                    else
                    {
                        this.resetVOX();
                        MadModLoader.log().info("VoxBox: Aborted phrase playback because there are no valid words in written book!");
                    }
                }
            }
        }
    }

    private void resetVOX()
    {
        // Flatten the timer out to restart the process now that we are finished.
        currentTalkWordStep = 0.0F;
        
        // Flatten the timer for the entire phrase.
        totalPhraseTimeInTicks = 0;
        currentPhraseTimeInTicks = 0;
        
        // Disable flag that lets us process work in updateEntity().
        shouldBeSpeaking = false;
        
        // Clear the entire list of words, nothing to say!
        fifoSpeech.clear();
        
        // Destroy currently held phrase if there is one.
        currentWordFromPhrase = null;
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
    }

    @Override
    public void smeltItem()
    {
        super.smeltItem();
    }

    @Override
    public void updateSound()
    {
        super.updateSound();
    }

    @Override
    public void initiate()
    {
        super.initiate();
    }

    @Override
    public void onBlockRightClick(World world, int x, int y, int z, EntityPlayer par5EntityPlayer)
    {
        super.onBlockRightClick(world, x, y, z, par5EntityPlayer);
    }

    @Override
    public void onBlockLeftClick(World world, int x, int y, int z, EntityPlayer player)
    {
        super.onBlockLeftClick(world, x, y, z, player);
    }
}

package madscience.tileentities.voxbox;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import madscience.MadConfig;
import madscience.MadFurnaces;
import madscience.MadScience;
import madscience.tileentities.prefab.MadTileEntity;
import madscience.util.MadUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class VoxBoxEntity extends MadTileEntity implements ISidedInventory
{
    private static final int[] slots_bottom = new int[]
    { 2, 1 };

    private static final int[] slots_sides = new int[]
    { 1 };
    private static final int[] slots_top = new int[]
    { 0 };

    /** Random number generator used to spit out food stuffs. */
    public Random animRand = new Random();

    /** Name to display on inventory screen. */
    private String containerCustomName;
    
    /** Amount of time we will keep talking once activated. */
    private int talkTime;
    private int talkTimeMaximum;
    
    /** Provides a list of sounds, lengths and the order in which they should be played for update function. */
    private List<VoxBoxSoundItem> talkTimeline;

    /** Determines if we currently should be playing animation frames every tick or not. */
    public boolean shouldPlay;

    /** Path to texture that we would like displayed on this block. */
    public String TEXTURE = "models/" + MadFurnaces.VOXBOX_INTERNALNAME + "/voxBox0.png";

    private ItemStack[] voxboxInput = new ItemStack[1];

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
        super(MadConfig.VOXBOX_CAPACTITY, MadConfig.VOXBOX_INPUT);
    }

    /** Returns true if automation can extract the given item in the given slot from the given side. Args: Slot, item, side */
    @Override
    public boolean canExtractItem(int slot, ItemStack items, int side)
    {
        // Extract output from the bottom of the block.
        if (slot == 0 && ForgeDirection.getOrientation(side) == ForgeDirection.WEST)
        {
            // Written book.
            return true;
        }

        // Default response is no.
        return false;
    }

    /** Returns true if automation can insert the given item in the given slot from the given side. Args: Slot, item, side */
    @Override
    public boolean canInsertItem(int slot, ItemStack items, int side)
    {
        return this.isItemValidForSlot(slot, items);
    }

    /** Returns true if the furnace can smelt an item, i.e. has a source item, destination stack isn't full, etc. */
    public boolean canSmelt()
    {
        // If our one and only input slot is not null then we are good to go!
        if (voxboxInput[0] != null)
        {
            return true;
        }

        return false;
    }

    @Override
    public void closeChest()
    {
    }

    private ItemStack DecreaseInputSlot(int slot, int numItems)
    {
        // Removes items from input slot 1 or 2.
        if (this.voxboxInput[slot] != null)
        {
            ItemStack itemstack;

            if (this.voxboxInput[slot].stackSize <= numItems)
            {
                itemstack = this.voxboxInput[slot];
                this.voxboxInput[slot] = null;
                return itemstack;
            }
            itemstack = this.voxboxInput[slot].splitStack(numItems);

            if (this.voxboxInput[slot].stackSize == 0)
            {
                this.voxboxInput[slot] = null;
            }

            return itemstack;
        }
        return null;
    }

    /** Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a new stack. */
    @Override
    public ItemStack decrStackSize(int slot, int numItems)
    {
        if (slot == 0)
        {
            // Input stack 1 - Written book.
            return DecreaseInputSlot(0, numItems);
        }

        // Something bad has occurred!
        MadScience.logger.info("decrStackSize() could not return " + numItems + " stack items from slot " + slot);
        return null;
    }

    /** Returns an array containing the indices of the slots that can be accessed by automation on the given side of this block. */
    @Override
    public int[] getAccessibleSlotsFromSide(int par1)
    {
        return par1 == 0 ? slots_bottom : (par1 == 1 ? slots_top : slots_sides);
    }

    /** Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't this more of a set than a get?* */
    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    /** Returns the name of the inventory. */
    @Override
    public String getInvName()
    {
        return this.isInvNameLocalized() ? this.containerCustomName : "container.furnace";
    }

    @Override
    public int getSizeInventory()
    {
        // We make use of other methods to reference the multiple hash tables.
        return this.voxboxInput.length;
    }

    /** Returns the stack in slot i */
    @Override
    public ItemStack getStackInSlot(int slot)
    {
        if (slot == 0)
        {
            // Input slot 1 - Written book.
            return this.voxboxInput[0];
        }

        MadScience.logger.info("getStackInSlot() could not return valid stack from slot " + slot);
        return null;
    }

    /** When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem - like when you close a workbench GUI. */
    @Override
    public ItemStack getStackInSlotOnClosing(int slot)
    {
        // Input slots.
        if (slot == 0)
        {
            // Input slot 1 - Written book
            if (this.voxboxInput[0] != null)
            {
                ItemStack itemstack = this.voxboxInput[0];
                this.voxboxInput[0] = null;
                return itemstack;
            }
            return null;
        }

        return null;
    }

    /** If this returns false, the inventory name will be used as an unlocalized name, and translated into the player's language. Otherwise it will be used directly. */
    @Override
    public boolean isInvNameLocalized()
    {
        return this.containerCustomName != null && this.containerCustomName.length() > 0;
    }

    /** Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot. */
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

    /** Do not make give this method the name canInteractWith because it clashes with Container */
    @Override
    public boolean isUseableByPlayer(EntityPlayer player)
    {
        return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public void openChest()
    {
    }

    /** Reads a tile entity from NBT. */
    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);

        // Read input/output items from world save data.
        NBTTagList nbtInput = nbt.getTagList("InputItems");
        this.voxboxInput = new ItemStack[this.getSizeInventory()];

        // Input items process.
        for (int i = 0; i < nbtInput.tagCount(); ++i)
        {
            NBTTagCompound inputSaveData = (NBTTagCompound) nbtInput.tagAt(i);
            byte b0 = inputSaveData.getByte("InputSlot");

            if (b0 >= 0 && b0 < this.voxboxInput.length)
            {
                this.voxboxInput[b0] = ItemStack.loadItemStackFromNBT(inputSaveData);
            }
        }

        // Should play animation status.
        this.shouldPlay = nbt.getBoolean("ShouldPlay");

        // Path to current texture what should be loaded onto the model.
        this.TEXTURE = nbt.getString("TexturePath");

        if (nbt.hasKey("CustomName"))
        {
            this.containerCustomName = nbt.getString("CustomName");
        }
    }

    /** Sets the custom display name to use when opening a GUI linked to this tile entity. */
    public void setGuiDisplayName(String par1Str)
    {
        this.containerCustomName = par1Str;
    }

    /** Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections). */
    @Override
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
    {
        if (par1 == 0)
        {
            this.voxboxInput[0] = par2ItemStack;
        }

        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
        {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }
    }

    /** Update current frame of animation that we should be playing. */
    private void updateAnimation()
    {
        // Active state has many textures based on item cook progress.
        if (this.canSmelt() && this.isPowered() && this.isRedstonePowered())
        {
            if (worldObj.getWorldTime() % 5L == 0L)
            {
                // Load this texture onto the entity.
                TEXTURE = "models/" + MadFurnaces.VOXBOX_INTERNALNAME + "/voxBox0.png";
            }
            else
            {
                TEXTURE = "models/" + MadFurnaces.VOXBOX_INTERNALNAME + "/voxBox1.png";
            }
        }
        else
        {
            // We are not powered or working.
            TEXTURE = "models/" + MadFurnaces.VOXBOX_INTERNALNAME + "/voxBox0.png";
        }
    }

    /** Allows the entity to update its state. */
    @Override
    public void updateEntity()
    {
        // Important to call the class below us!
        super.updateEntity();

        // Update status of the machine if it has redstone power or not.
        checkRedstonePower();

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
                if (voxboxInput[0] != null && voxboxInput[0].stackTagCompound != null)
                {
                    // Read the book in the slot and parse it.
                    //MadScience.logger.info("VoxBox: STARTING PHRASE INTERPRETOR");
                    String bookContents = MadUtils.getWrittenBookContents(voxboxInput[0].stackTagCompound);
                    
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
                        VoxBoxSoundItem registryVOXSound = VoxBoxSoundRegistry.getSoundByName(voxSound);
                        if (registryVOXSound != null)
                        {
                            if (registryVOXSound.internalName.equals(voxSound))
                            {
                                talkTimeline.add(registryVOXSound);
                                estimatedTotalTalkTime += registryVOXSound.duration;
                                //MadScience.logger.info("VoxBox: Added word '" + registryVOXSound.internalName + "' with length of " + String.valueOf(registryVOXSound.duration) + "F");
                            }
                        }
                        else
                        {
                            // If we cannot find the word the player input then use period by default since it plays static.
                            //MadScience.logger.info("VoxBox: Discarded word '" + voxSound + "' because it was not found in dictionary.");
                            talkTimeline.add(new VoxBoxSoundItem(0.43F, "_period", "_period.ogg"));
                            estimatedTotalTalkTime += 0.43F;
                        }
                    }
                    
                    // Calculate max talk time by rounding the value to nearest second.
                    talkTimeMaximum = Math.round(estimatedTotalTalkTime) * MadScience.SECOND_IN_TICKS;
                    //MadScience.logger.info("VoxBox: Prepared talk timeline with " + talkTimeline.size() + " entries and total length of " + String.valueOf(talkTimeMaximum) + " ticks.");
                    
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
                this.consumeEnergy(MadConfig.VOXBOX_CONSUME);
                
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
                    //MadScience.logger.info("VoxBox: First word so special circumstances!");
                    return;
                }
                
                // Set last known index for talk time to prevent stuttering.
                if (talkTimeIndex > lastWordIndex && talkTimeIndex > 0)
                {
                    talkTime++;
                    lastWordIndex = talkTimeIndex;
                    //MadScience.logger.info("VoxBox: Skipping index of " + String.valueOf(lastWordIndex - 1) + " because already played it!");
                }
                else if (talkTimeIndex <= lastWordIndex && talkTimeIndex > 0)
                {
                    // Count up to the next one.
                    talkTime++;
                    //MadScience.logger.info("VoxBox: Skipping because last talk time is same as current talk time");
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
                VoxBoxSoundItem talkTimeStep = null;
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
                    //MadScience.logger.info("VoxBox: [ERROR] Unable to get VoxBox Sound Item from VOX sound registry.");
                    return;
                }
                
                // Check if we have already said this word for this time index.
                if (lastWordLiteral != null && lastWordLiteral.equals(talkTimeStep.internalName) && lastWordIndex <= lastWordMaximum)
                {
                    talkTime++;
                    //MadScience.logger.info("VoxBox: Skipping word of " + String.valueOf(lastWordLiteral) + " because already played it!");
                    return;
                }
                
                // Load up information into variables that will use it in coming ticks to say the VOX words in sync with tick time.
                currentTalkWordMaximum = talkTimeStep.duration;
                currentTalkWordStep = 0.0F;
                talkTime++;
                lastWordLiteral = talkTimeStep.internalName;
                this.worldObj.playSoundEffect(this.xCoord + 0.5F, this.yCoord + 0.5F, this.zCoord + 0.5F, MadScience.ID + ":" + MadFurnaces.VOXBOX_INTERNALNAME + "." + talkTimeStep.internalName, 1.0F, 1.0F);
                //MadScience.logger.info("VoxBox: Speaking the word index " + String.valueOf(lastWordIndex) + " '" + talkTimeStep.internalName + "' with length of " + String.valueOf(currentTalkWordMaximum) + "F.");
            }
            else if (this.canSmelt() && this.isPowered() && this.talkTime > 0 && talkTimeline != null && lastWordMaximum > 0 && currentTalkWordMaximum > 0.0F)
            {
                // Say the word we are supposed to at this time until we are done with it.
                if (currentTalkWordStep < currentTalkWordMaximum && talkTime < talkTimeMaximum && lastWordIndex < lastWordMaximum)
                {
                    currentTalkWordStep += 0.1F;
                    talkTime++;
                    //MadScience.logger.info("VoxBox: Counting word index " + String.valueOf(currentTalkWordStep) + "/" + String.valueOf(currentTalkWordMaximum));
                }
                else if (currentTalkWordStep >= currentTalkWordMaximum && talkTime < talkTimeMaximum && lastWordIndex < lastWordMaximum)
                {
                    // It is time to move to the next word in the index.
                    currentTalkWordStep = 0.0F;
                    currentTalkWordMaximum = 0.0F;
                    talkTime++;
                    //MadScience.logger.info("VoxBox: Finished with word, asking for another...");
                }
            }
            else if (talkTimeline != null && this.canSmelt() && this.isPowered() && this.talkTime >= this.talkTimeMaximum && lastWordIndex >= lastWordMaximum)
            {
                this.resetVOX();
            }

            PacketDispatcher.sendPacketToAllAround(this.xCoord, this.yCoord, this.zCoord,
                    MadConfig.PACKETSEND_RADIUS, worldObj.provider.dimensionId, new VoxBoxPackets(this.xCoord, this.yCoord, this.zCoord,
                            getEnergy(ForgeDirection.UNKNOWN), getEnergyCapacity(ForgeDirection.UNKNOWN),
                            this.talkTime, this.talkTimeMaximum,
                            this.currentTalkWordStep, this.currentTalkWordMaximum,
                            this.lastWordIndex, this.lastWordMaximum, this.lastWordLiteral, this.TEXTURE).makePacket());
        }
    }

    public void resetVOX()
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
        //MadScience.logger.info("VoxBox: STOPPING VOX, PHRASE COMPLETED!");
    }

    /** Writes a tile entity to NBT. */
    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);

        // Should play animation status.
        nbt.setBoolean("ShouldPlay", this.shouldPlay);

        // Path to current texture that should be loaded onto the model.
        nbt.setString("TexturePath", this.TEXTURE);

        // Two tag lists for each type of items we have in this entity.
        NBTTagList inputItems = new NBTTagList();

        // Input slots.
        for (int i = 0; i < this.voxboxInput.length; ++i)
        {
            if (this.voxboxInput[i] != null)
            {
                NBTTagCompound inputSlots = new NBTTagCompound();
                inputSlots.setByte("InputSlot", (byte) i);
                this.voxboxInput[i].writeToNBT(inputSlots);
                inputItems.appendTag(inputSlots);
            }
        }

        // Save the input and output items.
        nbt.setTag("InputItems", inputItems);

        if (this.isInvNameLocalized())
        {
            nbt.setString("CustomName", this.containerCustomName);
        }
    }

    @SideOnly(Side.CLIENT)
    public void setTalkTime(int talkTime, int talkTimeMaximum)
    {
        // Total amount of time we have been talking on the current talk timeline.
        this.talkTime = talkTime;
        this.talkTimeMaximum = talkTimeMaximum;
    }

    @SideOnly(Side.CLIENT)
    public void setWordStep(float wordStep, float wordStepMaximum)
    {
        // Total amount of time we have been talking on the current word in the total talk timeline.
        this.currentTalkWordStep = wordStep;
        this.currentTalkWordMaximum = wordStepMaximum;
    }

    @SideOnly(Side.CLIENT)
    public void setLastIndex(int lastWordIndex, int totalWordIndex)
    {
        // Last known index of word that we spoke in the talk timeline, prevents speech stuttering.
        this.lastWordIndex = lastWordIndex;
        this.lastWordMaximum = totalWordIndex;
    }

    public void setLastWord(String lastWord)
    {
        // Last literal word that was said so we can ensure integrity of words said.
        this.lastWordLiteral = lastWord;
    }
}

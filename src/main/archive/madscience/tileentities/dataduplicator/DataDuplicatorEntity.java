package madscience.tileentities.dataduplicator;

import java.util.Random;

import madscience.MadConfig;
import madscience.MadEntities;
import madscience.MadFurnaces;
import madscience.MadScience;
import madscience.items.CombinedGenomeMonsterPlacer;
import madscience.items.dna.ItemGenome;
import madscience.tileentities.prefab.MadTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class DataDuplicatorEntity extends MadTileEntity implements ISidedInventory
{
    private static final int[] slots_top = new int[]
    { 0 };

    private static final int[] slots_bottom = new int[]
    { 2, 1 };
    private static final int[] slots_sides = new int[]
    { 1 };
    /** The ItemStacks that hold the items currently being used in the furnace */
    private ItemStack[] dataduplicatorOutput = new ItemStack[1];

    private ItemStack[] dataduplicatorInput = new ItemStack[2];

    /** The number of ticks that a fresh copy of the currently-burning item would keep the furnace burning for */
    public int currentItemCookingMaximum;

    /** The number of ticks that the current item has been cooking for */
    public int currentItemCookingValue;

    /** Random number generator used to spit out food stuffs. */
    public Random animRand = new Random();

    /** Determines if we currently should be playing animation frames every tick or not. */
    public boolean shouldPlay;

    /** Current frame of animation we should use to display in world. */
    public int curFrame;

    /** Path to texture that we would like displayed on this block. */
    public String TEXTURE = "models/" + MadFurnaces.DATADUPLICATOR_INTERNALNAME + "/off.png";

    public DataDuplicatorEntity()
    {
        super(MadConfig.DATADUPLICATOR_CAPACTITY, MadConfig.DATADUPLICATOR_INPUT);
    }

    /** Returns true if automation can extract the given item in the given slot from the given side. Args: Slot, item, side */
    @Override
    public boolean canExtractItem(int slot, ItemStack items, int side)
    {
        // Extract output from the bottom of the block.
        if (slot == 3 && ForgeDirection.getOrientation(side) == ForgeDirection.WEST)
        {
            // Empty water bucket.
            return true;
        }
        else if (slot == 4 && ForgeDirection.getOrientation(side) == ForgeDirection.WEST)
        {
            // Clean needle.
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
        // Check if we have redstone power applied to us.
        if (!this.isRedstonePowered())
        {
            return false;
        }
        
        // Check if both input slots for reel to copy and empty reel to copy onto.
        if (dataduplicatorInput[0] == null || dataduplicatorInput[1] == null)
        {
            return false;
        }

        // Check if output slots are empty and ready to be filled with.
        if (this.dataduplicatorOutput[0] == null)
        {
            return true;
        }

        // Check if input slot 1 matches output slot 1.
        if (this.dataduplicatorInput[0].isItemEqual(dataduplicatorOutput[0]))
        {
            return false;
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
        if (this.dataduplicatorInput[slot] != null)
        {
            ItemStack itemstack;

            if (this.dataduplicatorInput[slot].stackSize <= numItems)
            {
                itemstack = this.dataduplicatorInput[slot];
                this.dataduplicatorInput[slot] = null;
                return itemstack;
            }
            itemstack = this.dataduplicatorInput[slot].splitStack(numItems);

            if (this.dataduplicatorInput[slot].stackSize == 0)
            {
                this.dataduplicatorInput[slot] = null;
            }

            return itemstack;
        }
        return null;
    }

    private ItemStack DecreaseOutputSlot(int slot, int numItems)
    {
        // Removes items from either output slot 1 or 2.
        if (this.dataduplicatorOutput[slot] != null)
        {
            ItemStack itemstack;

            if (this.dataduplicatorOutput[slot].stackSize <= numItems)
            {
                itemstack = this.dataduplicatorOutput[slot];
                this.dataduplicatorOutput[slot] = null;
                return itemstack;
            }
            itemstack = this.dataduplicatorOutput[slot].splitStack(numItems);

            if (this.dataduplicatorOutput[slot].stackSize == 0)
            {
                this.dataduplicatorOutput[slot] = null;
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
            // Input stack 1 - Water bucket.
            return DecreaseInputSlot(0, numItems);
        }
        else if (slot == 1)
        {
            // Input stack 2 - Dirty needle.
            return DecreaseInputSlot(1, numItems);
        }
        else if (slot == 2)
        {
            // Output stack 1 - Empty bucket.
            return DecreaseOutputSlot(0, numItems);
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
        return MadFurnaces.DATADUPLICATOR_INTERNALNAME;
    }

    @SideOnly(Side.CLIENT)
    /**
     * Returns an integer between 0 and the passed value representing how close the current item is to being completely
     * cooked
     */
    public int getItemCookTimeScaled(int prgPixels)
    {
        // Prevent divide by zero exception by setting ceiling.
        if (currentItemCookingMaximum == 0)
        {
            currentItemCookingMaximum = 200;
        }

        return (currentItemCookingValue * prgPixels) / currentItemCookingMaximum;
    }

    public int getSizeInputInventory()
    {
        return this.dataduplicatorInput.length;
    }

    @Override
    @Deprecated
    public int getSizeInventory()
    {
        // We make use of other methods to reference the multiple hash tables.
        return 0;
    }

    public int getSizeOutputInventory()
    {
        return dataduplicatorOutput.length;
    }

    /** Returns the stack in slot i */
    @Override
    public ItemStack getStackInSlot(int slot)
    {
        if (slot == 0)
        {
            // Input slot 1 - DNA Samples.
            return this.dataduplicatorInput[0];
        }
        else if (slot == 1)
        {
            // Input slot 2 - Empty of uncompleted genome data reels.
            return this.dataduplicatorInput[1];
        }
        else if (slot == 2)
        {
            // Output slot 1 - Damaged or completed genome data reels.
            return this.dataduplicatorOutput[0];
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
            // Input slot 1 - DNA Sample
            if (this.dataduplicatorInput[0] != null)
            {
                ItemStack itemstack = this.dataduplicatorInput[0];
                this.dataduplicatorInput[0] = null;
                return itemstack;
            }
            return null;
        }
        else if (slot == 1)
        {
            // Input slot 2 - Empty Genome data reel
            if (this.dataduplicatorInput[1] != null)
            {
                ItemStack itemstack = this.dataduplicatorInput[1];
                this.dataduplicatorInput[1] = null;
                return itemstack;
            }
            return null;
        }
        else if (slot == 2)
        {
            // Output slot 1 - Filled genome data reel.
            if (this.dataduplicatorOutput[0] != null)
            {
                ItemStack itemstack = this.dataduplicatorOutput[0];
                this.dataduplicatorOutput[0] = null;
                return itemstack;
            }
            return null;
        }

        return null;
    }

    @Override
    public boolean isInvNameLocalized()
    {
        return true;
    }

    /** Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot. */
    @Override
    public boolean isItemValidForSlot(int slot, ItemStack items)
    {
        // Check if input slot 1 is full data
        ItemStack compareEmptyDataReel = new ItemStack(MadEntities.DATAREEL_EMPTY);
        if (slot == 0)
        {
            if (compareEmptyDataReel.isItemEqual(items))
            {
                return true;
            }

            return false;
        }

        // Check if input slot 2 is empty data reel.
        if (slot == 1)
        {
            if (compareEmptyDataReel.isItemEqual(items))
            {
                return false;
            }

            // Check if we are a genome data reel that is unfinished (AKA damaged).
            if (items != null && items.getItem() instanceof ItemGenome && items.isItemDamaged())
            {
                return false;
            }

            // Completed genomes are allowed to be duplicated.
            if (items != null && items.getItem() instanceof ItemGenome && !items.isItemDamaged())
            {
                return true;
            }

            // Completed mutant genomes are allowed to be duplicated.
            if (items != null && items.getItem() instanceof CombinedGenomeMonsterPlacer)
            {
                return true;
            }

            // Memory reels are allowed since only way to acquire the best is by chance.
            if (items != null && items.getItem() instanceof CombinedMemoryMonsterPlacer)
            {
                return true;
            }

            return false;
        }

        return false;
    }

    /** Do not make give this method the name canInteractWith because it clashes with Container */
    @Override
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
    {
        return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D;
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
        NBTTagList nbtOutput = nbt.getTagList("OutputItems");

        // Cast the save data onto our running object.
        this.dataduplicatorInput = new ItemStack[this.getSizeInputInventory()];
        this.dataduplicatorOutput = new ItemStack[this.getSizeOutputInventory()];

        // Input items process.
        for (int i = 0; i < nbtInput.tagCount(); ++i)
        {
            NBTTagCompound inputSaveData = (NBTTagCompound) nbtInput.tagAt(i);
            byte b0 = inputSaveData.getByte("InputSlot");

            if (b0 >= 0 && b0 < this.dataduplicatorInput.length)
            {
                this.dataduplicatorInput[b0] = ItemStack.loadItemStackFromNBT(inputSaveData);
            }
        }

        // Output items process.
        for (int i = 0; i < nbtOutput.tagCount(); ++i)
        {
            NBTTagCompound outputSaveData = (NBTTagCompound) nbtOutput.tagAt(i);
            byte b0 = outputSaveData.getByte("OutputSlot");

            if (b0 >= 0 && b0 < this.dataduplicatorOutput.length)
            {
                this.dataduplicatorOutput[b0] = ItemStack.loadItemStackFromNBT(outputSaveData);
            }
        }

        this.currentItemCookingValue = nbt.getShort("CookTime");

        // Should play animation status.
        this.shouldPlay = nbt.getBoolean("ShouldPlay");

        // Current frame of animation we are displaying.
        this.curFrame = nbt.getInteger("CurrentFrame");

        // Path to current texture what should be loaded onto the model.
        this.TEXTURE = nbt.getString("TexturePath");
    }

    /** Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections). */
    @Override
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
    {
        if (par1 == 0)
        {
            this.dataduplicatorInput[0] = par2ItemStack;
        }
        else if (par1 == 1)
        {
            this.dataduplicatorInput[1] = par2ItemStack;
        }
        else if (par1 == 2)
        {
            this.dataduplicatorOutput[0] = par2ItemStack;
        }

        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
        {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }
    }

    public void smeltItem()
    {
        // Check if we should damage the genome (new), or increase health by
        // eating DNA samples.
        if (dataduplicatorInput[1] != null && dataduplicatorInput[1].isItemDamaged() && dataduplicatorOutput[0] == null)
        {
            // Check of the genome is damaged and needs more samples to
            // complete it.
            if (dataduplicatorInput[1].isItemDamaged())
            {
                int currentGenomeStatus = dataduplicatorInput[1].getItemDamage();
                dataduplicatorInput[1].setItemDamage(--currentGenomeStatus);

                // Debug message about data reel health as it is healed by
                // the server.
                //MadScience.logger.info("WORLD(" + dataduplicatorInput[1].getUnlocalizedName() + "): " + dataduplicatorInput[1].getItemDamage());
            }

            // Check if the genome was healed completely in this last pass
            // and if so complete it.
            if (this.dataduplicatorOutput[0] == null && !dataduplicatorInput[1].isItemDamaged())
            {
                this.dataduplicatorOutput[0] = dataduplicatorInput[1].copy();

                // Sound of genome reel total completion.
                this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, DataDuplicatorSounds.DATADUPLICATOR_FINISH, 1.0F, 1.0F);

                // Remove healed data reel from input stack 2.
                --this.dataduplicatorInput[1].stackSize;
                if (this.dataduplicatorInput[1].stackSize <= 0)
                {
                    this.dataduplicatorInput[1] = null;
                }
            }

            // Remove a DNA sample from input stack 1.
            --this.dataduplicatorInput[0].stackSize;
            if (this.dataduplicatorInput[0].stackSize <= 0)
            {
                this.dataduplicatorInput[0] = null;
            }

            // We leave this function since we don't want the rest to
            // execute just in case.
            return;
        }

        if (dataduplicatorInput[1] != null && dataduplicatorOutput[0] == null)
        {
            // Add encoded genome data reel to output slot 1.
            if (this.dataduplicatorOutput[0] == null)
            {
                this.dataduplicatorOutput[0] = dataduplicatorInput[0].copy();
            }
            else if (this.dataduplicatorOutput[0].isItemEqual(dataduplicatorInput[0]))
            {
                dataduplicatorOutput[0].stackSize += dataduplicatorInput[0].stackSize;
            }

            // Remove empty data reel from input stack 2.
            --this.dataduplicatorInput[1].stackSize;
            if (this.dataduplicatorInput[1].stackSize <= 0)
            {
                this.dataduplicatorInput[1] = null;
            }
        }
    }

    /** Update current frame of animation that we should be playing. */
    private void updateAnimation()
    {
        // Active state has many textures based on item cook progress.
        if (isPowered() && canSmelt())
        {
            if (curFrame <= 9 && worldObj.getWorldTime() % 5L == 0L)
            {
                // Load this texture onto the entity.
                TEXTURE = "models/" + MadFurnaces.DATADUPLICATOR_INTERNALNAME + "/work_" + curFrame + ".png";

                // Update animation frame.
                ++curFrame;
            }
            else if (curFrame >= 10)
            {
                // Check if we have exceeded the ceiling and need to reset.
                curFrame = 0;
            }
        }
        else if (isPowered() && !canSmelt())
        {
            // Idle state single texture.
            TEXTURE = "models/" + MadFurnaces.DATADUPLICATOR_INTERNALNAME + "/idle.png";
        }
        else
        {
            // We are not powered or working.
            TEXTURE = "models/" + MadFurnaces.DATADUPLICATOR_INTERNALNAME + "/off.png";
        }
    }

    /** Allows the entity to update its state. */
    @Override
    public void updateEntity()
    {
        // Important to call the class below us!
        super.updateEntity();

        boolean inventoriesChanged = false;

        if (this.isPowered() && this.canSmelt())
        {
            // Decrease to amount of energy this item has on client and server.
            this.consumeEnergy(MadConfig.DATADUPLICATOR_CONSUME);
        }

        // Update status of the machine if it has redstone power or not.
        checkRedstonePower();

        // Server side processing for furnace.
        if (!this.worldObj.isRemote)
        {
            // Animation for block.
            updateAnimation();

            // Play sound based on state.
            updateSound();

            // First tick for new item being cooked in furnace.
            if (this.currentItemCookingValue == 0 && this.canSmelt() && this.isPowered())
            {
                // New item pulled from cooking stack to be processed, check how
                // long this item will take to cook.
                currentItemCookingMaximum = 2600;

                // Sound of genome reel total completion.
                this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, DataDuplicatorSounds.DATADUPLICATOR_START, 1.0F, 1.0F);

                // Increments the timer to kickstart the cooking loop.
                this.currentItemCookingValue++;
            }
            else if (this.currentItemCookingValue > 0 && this.canSmelt() && this.isPowered())
            {
                // Increments the timer to kickstart the cooking loop.
                this.currentItemCookingValue++;

                // Check if furnace has exceeded total amount of time to cook.
                if (this.currentItemCookingValue >= currentItemCookingMaximum)
                {
                    // Convert one item into another via 'cooking' process.
                    this.currentItemCookingValue = 0;
                    this.smeltItem();
                    inventoriesChanged = true;
                }
            }
            else
            {
                // Reset loop, prepare for next item or closure.
                this.currentItemCookingValue = 0;
            }

            PacketDispatcher.sendPacketToAllAround(this.xCoord, this.yCoord, this.zCoord, MadConfig.PACKETSEND_RADIUS, worldObj.provider.dimensionId, new DataDuplicatorPackets(this.xCoord, this.yCoord, this.zCoord, currentItemCookingValue, currentItemCookingMaximum,
                    getEnergy(ForgeDirection.UNKNOWN), getEnergyCapacity(ForgeDirection.UNKNOWN), this.TEXTURE).makePacket());
        }

        if (inventoriesChanged)
        {
            this.onInventoryChanged();
        }
    }

    private void updateSound()
    {
        // Idle sound of machine if powered but not currently working.
        if (this.isRedstonePowered() && !this.canSmelt() && this.isPowered() && worldObj.getWorldTime() % (MadScience.SECOND_IN_TICKS * 1.4F) == 0L)
        {
            this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, DataDuplicatorSounds.DATADUPLICATOR_IDLE, 1.0F, 1.0F);
        }

        // Working sound of machine when powered and can smelt.
        if (this.isRedstonePowered() && this.canSmelt() && this.isPowered() && worldObj.getWorldTime() % (MadScience.SECOND_IN_TICKS * 1.8F) == 0L)
        {
            this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, DataDuplicatorSounds.DATADUPLICATOR_WORK, 1.0F, 1.0F);
        }
    }

    /** Writes a tile entity to NBT. */
    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);

        // Amount of time left to cook current item inside of the furnace (if
        // there is one).
        nbt.setShort("CookTime", (short) this.currentItemCookingValue);

        // Should play animation status.
        nbt.setBoolean("ShouldPlay", this.shouldPlay);

        // Current frame of animation we are displaying.
        nbt.setInteger("CurrentFrame", this.curFrame);

        // Path to current texture that should be loaded onto the model.
        nbt.setString("TexturePath", this.TEXTURE);

        // Two tag lists for each type of items we have in this entity.
        NBTTagList inputItems = new NBTTagList();
        NBTTagList outputItems = new NBTTagList();

        // Input slots.
        for (int i = 0; i < this.dataduplicatorInput.length; ++i)
        {
            if (this.dataduplicatorInput[i] != null)
            {
                NBTTagCompound inputSlots = new NBTTagCompound();
                inputSlots.setByte("InputSlot", (byte) i);
                this.dataduplicatorInput[i].writeToNBT(inputSlots);
                inputItems.appendTag(inputSlots);
            }
        }

        // Output slots.
        for (int i = 0; i < this.dataduplicatorOutput.length; ++i)
        {
            if (this.dataduplicatorOutput[i] != null)
            {
                NBTTagCompound outputSlots = new NBTTagCompound();
                outputSlots.setByte("OutputSlot", (byte) i);
                this.dataduplicatorOutput[i].writeToNBT(outputSlots);
                outputItems.appendTag(outputSlots);
            }
        }

        // Save the input and output items.
        nbt.setTag("InputItems", inputItems);
        nbt.setTag("OutputItems", outputItems);
    }
}

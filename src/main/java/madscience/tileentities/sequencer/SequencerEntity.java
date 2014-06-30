package madscience.tileentities.sequencer;

import java.util.Random;

import madscience.MadConfig;
import madscience.MadEntities;
import madscience.MadFurnaces;
import madscience.MadScience;
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

public class SequencerEntity extends MadTileEntity implements ISidedInventory
{
    private static final int[] slots_top = new int[]
    { 0 };

    private static final int[] slots_bottom = new int[]
    { 2, 1 };
    private static final int[] slots_sides = new int[]
    { 1 };
    /** The ItemStacks that hold the items currently being used in the furnace */
    private ItemStack[] sequencerOutput = new ItemStack[1];

    private ItemStack[] sequencerInput = new ItemStack[2];

    /** The number of ticks that a fresh copy of the currently-burning item would keep the furnace burning for */
    int currentItemCookingMaximum;

    /** The number of ticks that the current item has been cooking for */
    int currentItemCookingValue;

    /** Determines if we currently should be playing animation frames every tick or not. */
    private boolean shouldPlay;

    /** Current frame of animation we should use to display in world. */
    private int curFrame;

    /** Path to texture that we would like displayed on this block. */
    String sequencerTexture = "models/" + MadFurnaces.SEQUENCER_INTERNALNAME + "/idle.png";

    public SequencerEntity()
    {
        super(MadConfig.SEQUENCER_CAPACTITY, MadConfig.SEQUENCER_INPUT);
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
    private boolean canSmelt()
    {
        // Check if we have water bucket and dirty needles in input slots and
        // that our internal tank has fluid.
        if (sequencerInput[0] == null || sequencerInput[1] == null)
        {
            return false;
        }
        // Check if input slot 1 is a DNA sample.
        ItemStack slot1SmeltResult = SequencerRecipes.getSmeltingResult(this.sequencerInput[0]);
        if (slot1SmeltResult == null)
        {
            // Input slot 1 was not a DNA sample.
            return false;
        }

        // Check if input slot 2 is a empty genome data reel or damaged.
        if (this.sequencerInput[1].isItemDamaged())
        {
            // Check if the data reel inserted to input slot 2 has recipe.
            ItemStack slot2SmeltResult = SequencerRecipes.getSmeltingResult(this.sequencerInput[1]);
            if (slot2SmeltResult == null)
            {
                // Input slot 2 was not a damaged genome data reel.
                return false;
            }

            // Check if the DNA sample matches the genome type it is healing.
            if (slot1SmeltResult.itemID != this.sequencerInput[1].itemID)
            {
                return false;
            }
        }
        else
        {
            // Item not damaged so check if it is an empty data reel.
            ItemStack itemsInputSlot2 = new ItemStack(MadEntities.DATAREEL_EMPTY);
            if (!itemsInputSlot2.isItemEqual(this.sequencerInput[1]))
            {
                return false;
            }
        }

        // Check if output slots are empty and ready to be filled with
        // items.
        if (this.sequencerOutput[0] == null)
        {
            return true;
        }

        // Check if input slot 2 matches output slot 1.
        if (this.sequencerInput[1].isItemEqual(sequencerOutput[0]))
        {
            return false;
        }

        // Check if output slot 1 (for DNA samples) is above item stack
        // limit.
        int slot2Result = sequencerOutput[0].stackSize + slot1SmeltResult.stackSize;
        return (slot2Result <= getInventoryStackLimit() && slot2Result <= slot1SmeltResult.getMaxStackSize());
    }

    @Override
    public void closeChest()
    {
    }

    private ItemStack DecreaseInputSlot(int slot, int numItems)
    {
        // Removes items from input slot 1 or 2.
        if (this.sequencerInput[slot] != null)
        {
            ItemStack itemstack;

            if (this.sequencerInput[slot].stackSize <= numItems)
            {
                itemstack = this.sequencerInput[slot];
                this.sequencerInput[slot] = null;
                return itemstack;
            }
            itemstack = this.sequencerInput[slot].splitStack(numItems);

            if (this.sequencerInput[slot].stackSize == 0)
            {
                this.sequencerInput[slot] = null;
            }

            return itemstack;
        }
        return null;
    }

    private ItemStack DecreaseOutputSlot(int slot, int numItems)
    {
        // Removes items from either output slot 1 or 2.
        if (this.sequencerOutput[slot] != null)
        {
            ItemStack itemstack;

            if (this.sequencerOutput[slot].stackSize <= numItems)
            {
                itemstack = this.sequencerOutput[slot];
                this.sequencerOutput[slot] = null;
                return itemstack;
            }
            itemstack = this.sequencerOutput[slot].splitStack(numItems);

            if (this.sequencerOutput[slot].stackSize == 0)
            {
                this.sequencerOutput[slot] = null;
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
        return MadFurnaces.SEQUENCER_INTERNALNAME;
    }

    @SideOnly(Side.CLIENT)
    /**
     * Returns an integer between 0 and the passed value representing how close the current item is to being completely
     * cooked
     */ int getItemCookTimeScaled(int prgPixels)
    {
        // Prevent divide by zero exception by setting ceiling.
        if (currentItemCookingMaximum == 0)
        {
            // MadScience.logger.info("CLIENT: getItemCookTimeScaled() was called with currentItemCookingMaximum being zero!");
            currentItemCookingMaximum = 200;
        }

        return (currentItemCookingValue * prgPixels) / currentItemCookingMaximum;
    }

    public int getSizeInputInventory()
    {
        return this.sequencerInput.length;
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
        return sequencerOutput.length;
    }

    /** Returns the stack in slot i */
    @Override
    public ItemStack getStackInSlot(int slot)
    {
        if (slot == 0)
        {
            // Input slot 1 - DNA Samples.
            return this.sequencerInput[0];
        }
        else if (slot == 1)
        {
            // Input slot 2 - Empty of uncompleted genome data reels.
            return this.sequencerInput[1];
        }
        else if (slot == 2)
        {
            // Output slot 1 - Damaged or completed genome data reels.
            return this.sequencerOutput[0];
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
            if (this.sequencerInput[0] != null)
            {
                ItemStack itemstack = this.sequencerInput[0];
                this.sequencerInput[0] = null;
                return itemstack;
            }
            return null;
        }
        else if (slot == 1)
        {
            // Input slot 2 - Empty Genome data reel
            if (this.sequencerInput[1] != null)
            {
                ItemStack itemstack = this.sequencerInput[1];
                this.sequencerInput[1] = null;
                return itemstack;
            }
            return null;
        }
        else if (slot == 2)
        {
            // Output slot 1 - Filled genome data reel.
            if (this.sequencerOutput[0] != null)
            {
                ItemStack itemstack = this.sequencerOutput[0];
                this.sequencerOutput[0] = null;
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
        // Check if input slot 2 is a empty genome data reel.
        if (slot == 1)
        {
            // Input slot 2 - empty genome data reel.
            ItemStack compareDirtyNeedle = new ItemStack(MadEntities.DATAREEL_EMPTY);
            if (compareDirtyNeedle.isItemEqual(items))
            {
                return true;
            }
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
        this.sequencerInput = new ItemStack[this.getSizeInputInventory()];
        this.sequencerOutput = new ItemStack[this.getSizeOutputInventory()];

        // Input items process.
        for (int i = 0; i < nbtInput.tagCount(); ++i)
        {
            NBTTagCompound inputSaveData = (NBTTagCompound) nbtInput.tagAt(i);
            byte b0 = inputSaveData.getByte("InputSlot");

            if (b0 >= 0 && b0 < this.sequencerInput.length)
            {
                this.sequencerInput[b0] = ItemStack.loadItemStackFromNBT(inputSaveData);
            }
        }

        // Output items process.
        for (int i = 0; i < nbtOutput.tagCount(); ++i)
        {
            NBTTagCompound outputSaveData = (NBTTagCompound) nbtOutput.tagAt(i);
            byte b0 = outputSaveData.getByte("OutputSlot");

            if (b0 >= 0 && b0 < this.sequencerOutput.length)
            {
                this.sequencerOutput[b0] = ItemStack.loadItemStackFromNBT(outputSaveData);
            }
        }

        this.currentItemCookingValue = nbt.getShort("CookTime");

        // Should play animation status.
        this.shouldPlay = nbt.getBoolean("ShouldPlay");

        // Current frame of animation we are displaying.
        this.curFrame = nbt.getInteger("CurrentFrame");

        // Path to current texture what should be loaded onto the model.
        this.sequencerTexture = nbt.getString("TexturePath");
    }

    /** Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections). */
    @Override
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
    {
        if (par1 == 0)
        {
            this.sequencerInput[0] = par2ItemStack;
        }
        else if (par1 == 1)
        {
            this.sequencerInput[1] = par2ItemStack;
        }
        else if (par1 == 2)
        {
            this.sequencerOutput[0] = par2ItemStack;
        }

        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
        {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }
    }

    private void smeltItem()
    {
        // Output 1 - Encoded genome data reel that used to be empty.
        ItemStack craftedItem = SequencerRecipes.getSmeltingResult(this.sequencerInput[0]);

        // Check if we should damage the genome (new), or increase health by
        // eating DNA samples.
        if (craftedItem != null && sequencerInput[1] != null && sequencerInput[1].isItemDamaged() && sequencerOutput[0] == null)
        {
            // Check of the genome is damaged and needs more samples to
            // complete it.
            if (sequencerInput[1].isItemDamaged())
            {
                int currentGenomeStatus = sequencerInput[1].getItemDamage();
                sequencerInput[1].setItemDamage(--currentGenomeStatus);

                // Debug message about data reel health as it is healed by
                // the server.
                MadScience.logger.info("WORLD(" + sequencerInput[1].getUnlocalizedName() + "): " + sequencerInput[1].getItemDamage());
            }

            // Check if the genome was healed completely in this last pass
            // and if so complete it.
            if (this.sequencerOutput[0] == null && !sequencerInput[1].isItemDamaged())
            {
                this.sequencerOutput[0] = sequencerInput[1].copy();

                // Sound of genome reel total completion.
                this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, SequencerSounds.SEQUENCER_FINISH, 1.0F, 1.0F);

                // Remove healed data reel from input stack 2.
                --this.sequencerInput[1].stackSize;
                if (this.sequencerInput[1].stackSize <= 0)
                {
                    this.sequencerInput[1] = null;
                }
            }

            // Remove a DNA sample from input stack 1.
            --this.sequencerInput[0].stackSize;
            if (this.sequencerInput[0].stackSize <= 0)
            {
                this.sequencerInput[0] = null;
            }

            // We leave this function since we don't want the rest to
            // execute just in case.
            return;
        }

        if (craftedItem != null && sequencerInput[1] != null && sequencerOutput[0] == null)
        {
            // New genomes that are fresh get set to maximum damage.
            craftedItem.setItemDamage(craftedItem.getMaxDamage());

            // Sound of genome reel creation.
            this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, SequencerSounds.SEQUENCER_START, 1.0F, 1.0F);

            // Add encoded genome data reel to output slot 1.
            if (this.sequencerOutput[0] == null)
            {
                this.sequencerOutput[0] = craftedItem.copy();
            }
            else if (this.sequencerOutput[0].isItemEqual(craftedItem))
            {
                sequencerOutput[0].stackSize += craftedItem.stackSize;
            }

            // Remove empty data reel from input stack 2.
            --this.sequencerInput[1].stackSize;
            if (this.sequencerInput[1].stackSize <= 0)
            {
                this.sequencerInput[1] = null;
            }

            // Remove a DNA sample from input stack 1.
            --this.sequencerInput[0].stackSize;
            if (this.sequencerInput[0].stackSize <= 0)
            {
                this.sequencerInput[0] = null;
            }
        }
    }

    /**
     * 
     */
    private void updateAnimation()
    {
        // Active state has many textures based on item cook progress.
        if (canSmelt())
        {
            if (curFrame <= 9 && worldObj.getWorldTime() % 15L == 0L)
            {
                // Load this texture onto the entity.
                sequencerTexture = "models/" + MadFurnaces.SEQUENCER_INTERNALNAME + "/work_" + curFrame + ".png";

                // Update animation frame.
                ++curFrame;
            }
            else if (curFrame >= 10)
            {
                // Check if we have exceeded the ceiling and need to reset.
                curFrame = 0;
            }
        }
        else
        {
            // Idle state single texture.
            sequencerTexture = "models/" + MadFurnaces.SEQUENCER_INTERNALNAME + "/idle.png";
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
            this.consumeEnergy(MadConfig.SEQUENCER_CONSUME);
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
                currentItemCookingMaximum = 200;

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

            // Send update to all players around us about tile entity.
            PacketDispatcher.sendPacketToAllAround(this.xCoord, this.yCoord, this.zCoord, MadConfig.PACKETSEND_RADIUS, worldObj.provider.dimensionId, new SequencerPackets(this.xCoord, this.yCoord, this.zCoord, currentItemCookingValue, currentItemCookingMaximum,
                    getEnergy(ForgeDirection.UNKNOWN), getEnergyCapacity(ForgeDirection.UNKNOWN), this.sequencerTexture).makePacket());
        }

        if (inventoriesChanged)
        {
            this.onInventoryChanged();
        }
    }

    private void updateSound()
    {
        // Check to see if we should play idle sounds.
        if (this.canSmelt() && this.isPowered() && worldObj.getWorldTime() % (MadScience.SECOND_IN_TICKS * 3) == 0L)
        {
            this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, SequencerSounds.SEQUENCER_WORK, 0.42F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
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
        nbt.setString("TexturePath", this.sequencerTexture);

        // Two tag lists for each type of items we have in this entity.
        NBTTagList inputItems = new NBTTagList();
        NBTTagList outputItems = new NBTTagList();

        // Input slots.
        for (int i = 0; i < this.sequencerInput.length; ++i)
        {
            if (this.sequencerInput[i] != null)
            {
                NBTTagCompound inputSlots = new NBTTagCompound();
                inputSlots.setByte("InputSlot", (byte) i);
                this.sequencerInput[i].writeToNBT(inputSlots);
                inputItems.appendTag(inputSlots);
            }
        }

        // Output slots.
        for (int i = 0; i < this.sequencerOutput.length; ++i)
        {
            if (this.sequencerOutput[i] != null)
            {
                NBTTagCompound outputSlots = new NBTTagCompound();
                outputSlots.setByte("OutputSlot", (byte) i);
                this.sequencerOutput[i].writeToNBT(outputSlots);
                outputItems.appendTag(outputSlots);
            }
        }

        // Save the input and output items.
        nbt.setTag("InputItems", inputItems);
        nbt.setTag("OutputItems", outputItems);
    }
}

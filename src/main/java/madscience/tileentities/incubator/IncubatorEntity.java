package madscience.tileentities.incubator;

import java.util.Random;

import madscience.MadConfig;
import madscience.MadFurnaces;
import madscience.MadScience;
import madscience.MadSounds;
import madscience.tileentities.prefab.MadTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class IncubatorEntity extends MadTileEntity implements ISidedInventory
{
    private static final int[] slots_top = new int[]
    { 0 };

    private static final int[] slots_bottom = new int[]
    { 2, 1 };
    private static final int[] slots_sides = new int[]
    { 1 };
    /** The ItemStacks that hold the items currently being used in the furnace */
    private ItemStack[] incubatorOutput = new ItemStack[1];

    private ItemStack[] incubatorInput = new ItemStack[2];

    /** The number of ticks that a fresh copy of the currently-burning item would keep the furnace burning for */
    public int currentItemCookingMaximum;

    /** The number of ticks that the current item has been cooking for */
    public int currentItemCookingValue;

    /** Current level of heat that the machine has accumulated while powered and active. */
    public int currentHeatValue;

    /** Maximum allowed heat value and also when the machine is considered ready. */
    public int currentHeatMaximum = 1000;

    /** Random number generator used to spit out food stuffs. */
    public Random animRand = new Random();

    /** Determines if we currently should be playing animation frames every tick or not. */
    public boolean shouldPlay;

    /** Current frame of animation we should use to display in world. */
    public int curFrame;

    /** Name to display on inventory screen. */
    private String containerCustomName;

    /** Texture that should be displayed on our model. */
    public String incubatorTexture = "models/" + MadFurnaces.INCUBATOR_INTERNALNAME + "/idle.png";

    public IncubatorEntity()
    {
        super(MadConfig.INCUBATOR_CAPACTITY, MadConfig.INCUBATOR_INPUT);
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
        // Check if power levels are at proper values before cooking.
        if (!this.isPowered())
        {
            return false;
        }

        // Check if user even wants us activated right with help of redstone.
        if (!this.isRedstonePowered())
        {
            return false;
        }

        // Check if this furnace has been heated enough to be considered operational.
        if (!this.isHeatedEnough())
        {
            return false;
        }

        // Check if input slots are empty.
        if (incubatorInput[0] == null || incubatorInput[1] == null)
        {
            return false;
        }

        // Check if input slot 1 is a fresh egg.
        ItemStack itemsInputSlot1 = new ItemStack(Item.egg);
        if (!itemsInputSlot1.isItemEqual(this.incubatorInput[0]))
        {
            return false;
        }

        // Check if input slot 2 is a completed genome data reel.
        if (this.incubatorInput[1].isItemDamaged())
        {
            return false;
        }

        // Check if the data reel inserted to input slot 2 has valid conversion.
        ItemStack itemsInputSlot2 = IncubatorRecipes.getSmeltingResult(this.incubatorInput[1]);
        if (itemsInputSlot2 == null)
        {
            // Input slot 2 was not a damaged genome data reel.
            return false;
        }

        // Check if output slots are empty and ready to be filled with
        // items.
        if (this.incubatorOutput[0] == null)
        {
            return true;
        }

        // Check if genome being cooked is same as one in output slot.
        if (this.incubatorOutput[0] != null && itemsInputSlot2 != null)
        {
            // Check item difference by sub-type since item will always be equal (monster placer).
            if (this.incubatorOutput[0].isItemEqual(itemsInputSlot2) && this.incubatorOutput[0].getItemDamage() == itemsInputSlot2.getItemDamage())
            {
                // The egg we are producing matches what the genome cooking recipe says.
                return true;
            }

            // There was a problem comparing genome to egg in output slot so we halt.
            return false;
        }

        // Check if output slot 1 is above item stack limit.
        int slot2Result = incubatorOutput[0].stackSize + itemsInputSlot1.stackSize;
        return (slot2Result <= getInventoryStackLimit() && slot2Result <= itemsInputSlot1.getMaxStackSize());
    }

    @Override
    public void closeChest()
    {
        // this.worldObj.playSoundEffect((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D, MadSounds.INCUBATOR_CLOSE, 1.0F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
    }

    private ItemStack DecreaseInputSlot(int slot, int numItems)
    {
        // Removes items from input slot 1 or 2.
        if (this.incubatorInput[slot] != null)
        {
            ItemStack itemstack;

            if (this.incubatorInput[slot].stackSize <= numItems)
            {
                itemstack = this.incubatorInput[slot];
                this.incubatorInput[slot] = null;
                return itemstack;
            }
            itemstack = this.incubatorInput[slot].splitStack(numItems);

            if (this.incubatorInput[slot].stackSize == 0)
            {
                this.incubatorInput[slot] = null;
            }

            return itemstack;
        }
        return null;
    }

    private ItemStack DecreaseOutputSlot(int slot, int numItems)
    {
        // Removes items from either output slot 1 or 2.
        if (this.incubatorOutput[slot] != null)
        {
            ItemStack itemstack;

            if (this.incubatorOutput[slot].stackSize <= numItems)
            {
                itemstack = this.incubatorOutput[slot];
                this.incubatorOutput[slot] = null;
                return itemstack;
            }
            itemstack = this.incubatorOutput[slot].splitStack(numItems);

            if (this.incubatorOutput[slot].stackSize == 0)
            {
                this.incubatorOutput[slot] = null;
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

    public float getHeatAmount()
    {
        // Returns current level of heat stored inside of the machine.
        return currentHeatValue;
    }

    public int getHeatLevelTimeScaled(int pxl)
    {
        // Returns scaled percentage of heat level used in GUI to show temperature.
        return (int) (this.getHeatAmount() * (pxl / this.getMaxHeatAmount()));
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
            currentItemCookingMaximum = 2600;
        }

        return (currentItemCookingValue * prgPixels) / currentItemCookingMaximum;
    }

    public float getMaxHeatAmount()
    {
        // Returns maximum amount of heat allowed and also operating temperature.
        return currentHeatMaximum;
    }

    public int getSizeInputInventory()
    {
        return this.incubatorInput.length;
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
        return incubatorOutput.length;
    }

    /** Returns the stack in slot i */
    @Override
    public ItemStack getStackInSlot(int slot)
    {
        if (slot == 0)
        {
            // Input slot 1 - DNA Samples.
            return this.incubatorInput[0];
        }
        else if (slot == 1)
        {
            // Input slot 2 - Empty of uncompleted genome data reels.
            return this.incubatorInput[1];
        }
        else if (slot == 2)
        {
            // Output slot 1 - Damaged or completed genome data reels.
            return this.incubatorOutput[0];
        }

        return null;
    }

    /** When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem - like when you close a workbench GUI. */
    @Override
    public ItemStack getStackInSlotOnClosing(int slot)
    {
        // Input slots.
        if (slot == 0)
        {
            // Input slot 1 - Fresh Egg
            if (this.incubatorInput[0] != null)
            {
                ItemStack itemstack = this.incubatorInput[0];
                this.incubatorInput[0] = null;
                return itemstack;
            }
            return null;
        }
        else if (slot == 1)
        {
            // Input slot 2 - Completed Genome data reel
            if (this.incubatorInput[1] != null)
            {
                ItemStack itemstack = this.incubatorInput[1];
                this.incubatorInput[1] = null;
                return itemstack;
            }
            return null;
        }
        else if (slot == 2)
        {
            // Output slot 1 - Encoded mob egg.
            if (this.incubatorOutput[0] != null)
            {
                ItemStack itemstack = this.incubatorOutput[0];
                this.incubatorOutput[0] = null;
                return itemstack;
            }
            return null;
        }

        return null;
    }

    public boolean isHeated()
    {
        // Returns true if the heater has reached it's optimal temperature.
        return this.getHeatAmount() >= this.getMaxHeatAmount();
    }

    private boolean isHeatedEnough()
    {
        // Check if heat levels are at proper values for cooking.
        // Note: 780 is approximate number when heater fills line on GUI with flame.
        if (this.getHeatAmount() > 780)
        {
            return true;
        }

        // Default response is that we are not warm enough to incubate anything.
        return false;
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
        // Check if input slot 1 is a fresh egg ready to be encoded.
        if (slot == 0)
        {
            // Input slot 1 - fresh egg.
            ItemStack compareItem = new ItemStack(Item.egg);
            if (compareItem.isItemEqual(items))
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
        this.incubatorInput = new ItemStack[this.getSizeInputInventory()];
        this.incubatorOutput = new ItemStack[this.getSizeOutputInventory()];

        // Input items process.
        for (int i = 0; i < nbtInput.tagCount(); ++i)
        {
            NBTTagCompound inputSaveData = (NBTTagCompound) nbtInput.tagAt(i);
            byte b0 = inputSaveData.getByte("InputSlot");

            if (b0 >= 0 && b0 < this.incubatorInput.length)
            {
                this.incubatorInput[b0] = ItemStack.loadItemStackFromNBT(inputSaveData);
            }
        }

        // Output items process.
        for (int i = 0; i < nbtOutput.tagCount(); ++i)
        {
            NBTTagCompound outputSaveData = (NBTTagCompound) nbtOutput.tagAt(i);
            byte b0 = outputSaveData.getByte("OutputSlot");

            if (b0 >= 0 && b0 < this.incubatorOutput.length)
            {
                this.incubatorOutput[b0] = ItemStack.loadItemStackFromNBT(outputSaveData);
            }
        }

        // Amount of time the item in the furnace had left to cook.
        this.currentItemCookingValue = nbt.getShort("CookTime");

        // Should play animation status.
        this.shouldPlay = nbt.getBoolean("ShouldPlay");

        // Current frame of animation we are displaying.
        this.curFrame = nbt.getInteger("CurrentFrame");

        // Amount of heat that was stored inside of this block.
        this.currentHeatValue = nbt.getShort("HeatLevel");

        // Path to current texture what should be loaded onto the model.
        this.incubatorTexture = nbt.getString("TexturePath");

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

    private void setHeatLevel(int amount)
    {
        // Changes block internal temperature to new value.
        if (this.currentHeatValue != amount)
        {
            this.currentHeatValue = amount;
        }
    }

    /** Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections). */
    @Override
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
    {
        if (par1 == 0)
        {
            this.incubatorInput[0] = par2ItemStack;
        }
        else if (par1 == 1)
        {
            this.incubatorInput[1] = par2ItemStack;
        }
        else if (par1 == 2)
        {
            this.incubatorOutput[0] = par2ItemStack;
        }

        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
        {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }
    }

    public void smeltItem()
    {
        // Output 1 - Encoded mob egg from genome and fresh egg.
        ItemStack craftedItem = IncubatorRecipes.getSmeltingResult(this.incubatorInput[1]);

        if (craftedItem == null)
        {
            return;
        }

        // Add encoded mob egg to output slot 1.
        if (this.incubatorOutput[0] == null)
        {
            this.incubatorOutput[0] = craftedItem.copy();
        }
        else if (this.incubatorOutput[0].isItemEqual(craftedItem))
        {
            incubatorOutput[0].stackSize += craftedItem.stackSize;
        }

        // Remove a fresh egg from input stack 1.
        --this.incubatorInput[0].stackSize;
        if (this.incubatorInput[0].stackSize <= 0)
        {
            this.incubatorInput[0] = null;
        }

        this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, MadSounds.INCUBATOR_FINISH, 1.0F, 1.0F);
    }

    /**
     * 
     */
    private void updateAnimation()
    {
        // Main state is when all four requirements have been met to cook items.
        if (canSmelt() && isPowered() && isHeatedEnough() && isRedstonePowered())
        {
            if (curFrame <= 4 && worldObj.getWorldTime() % 5L == 0L)
            {
                // Load this texture onto the entity.
                incubatorTexture = "models/" + MadFurnaces.INCUBATOR_INTERNALNAME + "/work_" + curFrame + ".png";

                // Update animation frame.
                ++curFrame;
            }
            else if (curFrame >= 5)
            {
                // Check if we have exceeded the ceiling and need to reset.
                curFrame = 0;
            }
        }
        else if (!canSmelt() && isPowered() && !isHeatedEnough() && !isRedstonePowered())
        {
            // Powered up but still very cold, not ready!
            incubatorTexture = "models/" + MadFurnaces.INCUBATOR_INTERNALNAME + "/powered.png";
        }
        else if (isPowered() && isHeatedEnough() && !canSmelt() && isRedstonePowered())
        {
            // Powered up, heater on. Just nothing inside of me!
            incubatorTexture = "models/" + MadFurnaces.INCUBATOR_INTERNALNAME + "/ready.png";
        }
        else if (!isRedstonePowered())
        {
            // Turned off.
            incubatorTexture = "models/" + MadFurnaces.INCUBATOR_INTERNALNAME + "/idle.png";
        }
    }

    /** Allows the entity to update its state. */
    @Override
    public void updateEntity()
    {
        // Important to call the class below us!
        super.updateEntity();

        boolean inventoriesChanged = false;

        // Remove power from this device if we have some and also have heater enabled.
        if (this.isPowered() && this.isRedstonePowered())
        {
            this.consumeEnergy(MadConfig.INCUBATOR_CONSUME);
        }

        // Add heat to this block if it has met the right conditions.
        if (this.isPowered() && this.isRedstonePowered() && !this.isHeated())
        {
            this.setHeatLevel(++this.currentHeatValue);
        }

        // Remove heat from this object all the time if it has any.
        if (this.getHeatAmount() > 0)
        {
            // Does not remove heat constantly but instead every five ticks.
            if (worldObj.getWorldTime() % 5L == 0L)
            {
                this.setHeatLevel(--this.currentHeatValue);
            }
        }

        // Update status of the machine if it has redstone power or not.
        checkRedstonePower();

        // Server side processing for furnace.
        if (!this.worldObj.isRemote)
        {
            // Update texture based on block state.
            this.updateAnimation();

            // Update current sound that sound be played.
            this.updateSound();

            // First tick for new item being cooked in furnace.
            if (this.currentItemCookingValue == 0 && this.canSmelt() && this.isPowered())
            {
                // New item pulled from cooking stack to be processed, check how
                // long this item will take to cook.
                currentItemCookingMaximum = 2600;

                this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, MadSounds.INCUBATOR_START, 1.0F, 1.0F);

                // Increments the timer to kickstart the cooking loop.
                this.currentItemCookingValue++;
            }
            else if (this.currentItemCookingValue > 0 && this.canSmelt() && this.isPowered())
            {
                // Run on server when we have items and electrical power.
                // Note: This is the main work loop for the block!

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

            // Update status of machine to all clients around us.
            PacketDispatcher.sendPacketToAllAround(this.xCoord, this.yCoord, this.zCoord, MadConfig.PACKETSEND_RADIUS, worldObj.provider.dimensionId, new IncubatorPackets(this.xCoord, this.yCoord, this.zCoord, currentItemCookingValue, currentItemCookingMaximum,
                    getEnergy(ForgeDirection.UNKNOWN), getEnergyCapacity(ForgeDirection.UNKNOWN), this.currentHeatValue, this.currentHeatMaximum, this.incubatorTexture).makePacket());
        }

        if (inventoriesChanged)
        {
            this.onInventoryChanged();
        }
    }

    private void updateSound()
    {
        // Check to see if we should play idle sounds.
        if (this.canSmelt() && this.isPowered() && worldObj.getWorldTime() % MadScience.SECOND_IN_TICKS == 0L)
        {
            this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, MadSounds.INCUBATOR_WORK, 1.0F, 1.0F);
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

        // Amount of heat current stored within the block.
        nbt.setShort("HeatLevel", (short) this.currentHeatValue);

        // Path to current texture that should be loaded onto the model.
        nbt.setString("TexturePath", this.incubatorTexture);

        // Two tag lists for each type of items we have in this entity.
        NBTTagList inputItems = new NBTTagList();
        NBTTagList outputItems = new NBTTagList();

        // Input slots.
        for (int i = 0; i < this.incubatorInput.length; ++i)
        {
            if (this.incubatorInput[i] != null)
            {
                NBTTagCompound inputSlots = new NBTTagCompound();
                inputSlots.setByte("InputSlot", (byte) i);
                this.incubatorInput[i].writeToNBT(inputSlots);
                inputItems.appendTag(inputSlots);
            }
        }

        // Output slots.
        for (int i = 0; i < this.incubatorOutput.length; ++i)
        {
            if (this.incubatorOutput[i] != null)
            {
                NBTTagCompound outputSlots = new NBTTagCompound();
                outputSlots.setByte("OutputSlot", (byte) i);
                this.incubatorOutput[i].writeToNBT(outputSlots);
                outputItems.appendTag(outputSlots);
            }
        }

        // Save the input and output items.
        nbt.setTag("InputItems", inputItems);
        nbt.setTag("OutputItems", outputItems);

        if (this.isInvNameLocalized())
        {
            nbt.setString("CustomName", this.containerCustomName);
        }
    }
}

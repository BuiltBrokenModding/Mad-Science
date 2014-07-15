package madscience.tile.cryofreezer;

import madscience.MadConfig;
import madscience.MadFurnaces;
import madscience.MadScience;
import madscience.factory.tileentity.prefab.MadTileEntityPrefab;
import madscience.items.ItemDecayBase;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.common.network.PacketDispatcher;

public class CryofreezerEntity extends MadTileEntityPrefab implements ISidedInventory
{
    private static final int[] slots_top = new int[]
    { 0 };

    private static final int[] slots_bottom = new int[]
    { 2, 1 };
    private static final int[] slots_sides = new int[]
    { 1 };
    /** The item that is currently being used to heal the genetic material. */
    private ItemStack[] cryofreezerInput = new ItemStack[1];

    /** The items that make up that container area of the furnace. */
    private ItemStack[] cryoFreezerStorage = new ItemStack[21];

    /** The number of ticks that a fresh copy of the currently-burning item would keep the furnace burning for */
    int currentItemCookingMaximum;

    /** The number of ticks that the current item has been cooking for */
    int currentItemCookingValue;

    // Path to texture that should be displayed on our model.
    String TEXTURE = "models/" + MadFurnaces.CRYOFREEZER_INTERNALNAME + "/idle.png";

    public CryofreezerEntity()
    {
        super(MadFurnaces.CRYOFREEZER_INTERNALNAME);
    }

    /** Returns true if automation can extract the given item in the given slot from the given side. Args: Slot, item, side */
    @Override
    public boolean canExtractItem(int slot, ItemStack items, int side)
    {
        // Allow items to be sucked out of any side.
        return true;
    }

    /** Returns true if automation can insert the given item in the given slot from the given side. Args: Slot, item, side */
    @Override
    public boolean canInsertItem(int slot, ItemStack items, int side)
    {
        return this.isItemValidForSlot(slot, items);
    }

    /** Returns true if the furnace can smelt an item, i.e. has a source item, destination stack isn't full, etc. */
    @Override
    public boolean canSmelt()
    {
        // Check if we have valid fuel and items to keep cold.
        if (this.cryofreezerInput == null || this.cryoFreezerStorage == null)
        {
            return false;
        }
        // Check if we have a valid fuel block to keep freezer cold.
        if (this.cryofreezerInput[0] == null)
        {
            return false;
        }

        // Check for snowballs.
        if (this.cryofreezerInput[0].isItemEqual(new ItemStack(Item.snowball)))
        {
            currentItemCookingMaximum = 200;
            // MadScience.logger.info("canSmelt() SNOWBALL ACCEPTED");
            return true;
        }

        // Check for iceblocks.
        if (this.cryofreezerInput[0].isItemEqual(new ItemStack(Block.ice)))
        {
            currentItemCookingMaximum = 2600;
            // MadScience.logger.info("canSmelt() ICE ACCEPTED");
            return true;
        }

        // Check for snow covers.
        if (this.cryofreezerInput[0].isItemEqual(new ItemStack(Block.snow)))
        {
            currentItemCookingMaximum = 400;
            // MadScience.logger.info("canSmelt() SNOWCOVER ACCEPTED");
            return true;
        }

        // Check for snow blocks!
        if (this.cryofreezerInput[0].isItemEqual(new ItemStack(Block.blockSnow)))
        {
            currentItemCookingMaximum = 1500;
            // MadScience.logger.info("canSmelt() SNOWBLOCK ACCEPTED");
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
        if (this.cryofreezerInput[slot] != null)
        {
            ItemStack itemstack;

            if (this.cryofreezerInput[slot].stackSize <= numItems)
            {
                itemstack = this.cryofreezerInput[slot];
                this.cryofreezerInput[slot] = null;
                return itemstack;
            }
            itemstack = this.cryofreezerInput[slot].splitStack(numItems);

            if (this.cryofreezerInput[slot].stackSize == 0)
            {
                this.cryofreezerInput[slot] = null;
            }

            return itemstack;
        }
        return null;
    }

    // This decreases the stack size
    // It has 2 params
    // @param int slotIndex, this is the slot number
    // @param int amount, this is the amount you want to decreases by
    @Override
    public ItemStack decrStackSize(int slotIndex, int amount)
    {
        // This gets the stack with the slot number you want
        ItemStack stack = getStackInSlot(slotIndex);

        // Then it checks to make sure it has something in it
        if (stack != null)
        {
            // Then it checks to make sure that it has something that is equal or lesser than the amount you want to add
            if (stack.stackSize <= amount)
            {
                setInventorySlotContents(slotIndex, null);
            }
            else
            {
                stack = stack.splitStack(amount);
                if (stack.stackSize == 0)
                {
                    setInventorySlotContents(slotIndex, null);
                }
            }
        }

        // Then it returns the stack
        return stack;
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
    public String getMachineInternalName()
    {
        return MadFurnaces.CRYOFREEZER_INTERNALNAME;
    }

    public int getSizeInputInventory()
    {
        return this.cryofreezerInput.length;
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
        return cryoFreezerStorage.length;
    }

    /** Returns the stack in slot i */
    @Override
    public ItemStack getStackInSlot(int slot)
    {
        // Input slot 1 for ice and snowballs.
        if (slot == 0)
        {
            return this.cryofreezerInput[0];
        }
        // Container storage area for needles and DNA samples.
        if (slot == 1)
        {
            return this.cryoFreezerStorage[0];
        }
        else if (slot > 1)
        {
            return this.cryoFreezerStorage[slot - 1];
        }

        // Default response is to return nothing.
        MadScience.logger.info("getStackInSlot() could not return valid stack from slot " + slot);
        return null;
    }

    /** When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem - like when you close a workbench GUI. */
    @Override
    public ItemStack getStackInSlotOnClosing(int slot)
    {
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
        // Check if machine trying to insert item into given slot is allowed.
        if (slot == 0)
        {
            // Input slot 1 - Iceblock or snowball.
            ItemStack compareSnowBalls = new ItemStack(Item.snowball);
            ItemStack compareIceblock = new ItemStack(Block.ice);
            ItemStack compareSnow = new ItemStack(Block.snow);
            ItemStack compareSnowBlock = new ItemStack(Block.blockSnow);
            if (compareSnowBalls.isItemEqual(items) || compareIceblock.isItemEqual(items) || compareSnow.isItemEqual(items) || compareSnowBlock.isItemEqual(items))
            {
                // MadScience.logger.info("isItemValidForSlot() SLOT 1 ACCEPTED");
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
        this.cryofreezerInput = new ItemStack[this.getSizeInputInventory()];
        this.cryoFreezerStorage = new ItemStack[this.getSizeOutputInventory()];

        // Input items process.
        for (int i = 0; i < nbtInput.tagCount(); ++i)
        {
            NBTTagCompound inputSaveData = (NBTTagCompound) nbtInput.tagAt(i);
            byte b0 = inputSaveData.getByte("InputSlot");

            if (b0 >= 0 && b0 < this.cryofreezerInput.length)
            {
                this.cryofreezerInput[b0] = ItemStack.loadItemStackFromNBT(inputSaveData);
            }
        }

        // Output items process.
        for (int i = 0; i < nbtOutput.tagCount(); ++i)
        {
            NBTTagCompound outputSaveData = (NBTTagCompound) nbtOutput.tagAt(i);
            byte b0 = outputSaveData.getByte("OutputSlot");

            if (b0 >= 0 && b0 < this.cryoFreezerStorage.length)
            {
                this.cryoFreezerStorage[b0] = ItemStack.loadItemStackFromNBT(outputSaveData);
            }
        }

        this.currentItemCookingValue = nbt.getShort("CookTime");

        // Path to current texture what should be loaded onto the model.
        this.TEXTURE = nbt.getString("TexturePath");
    }

    // This sets the slots contents, it has 2 params
    // @param int slot, this is the slots number
    // @param ItemStack stack, this is the stack you want to add
    @Override
    public void setInventorySlotContents(int slot, ItemStack stack)
    {
        if (slot == 0)
        {
            this.cryofreezerInput[0] = stack;
        }
        else if (slot == 1)
        {
            this.cryoFreezerStorage[0] = stack;
        }
        else if (slot > 1)
        {
            this.cryoFreezerStorage[slot - 1] = stack;
        }
        // This checks to make sure the stack is not nothing, and then makes sure the stack is not going over the limit
        // Of the stack
        if (stack != null && stack.stackSize > getInventoryStackLimit())
        {
            stack.stackSize = getInventoryStackLimit();
        }
    }

    @Override
    public void smeltItem()
    {
        // Converts input item into result item along with waste items.
        if (this.canSmelt())
        {
            // Loop through all of the storage items and see if we can heal
            // them.
            for (int i = 0; i < this.cryoFreezerStorage.length; ++i)
            {
                if (this.cryoFreezerStorage[i] != null)
                {
                    // Check if we need to heal ourselves.
                    int dmg = cryoFreezerStorage[i].getItemDamage();

                    // Heal ourselves for being inside a powered and equipped
                    // freezer.
                    if (cryoFreezerStorage[i] != null && cryoFreezerStorage[i].getItem() instanceof ItemDecayBase && dmg <= cryoFreezerStorage[i].getMaxDamage())
                    {
                        cryoFreezerStorage[i].setItemDamage(dmg - 1);

                        // Debugging message.
                        MadScience.logger.info("WORLD(" + cryoFreezerStorage[i].getUnlocalizedName() + "): " + cryoFreezerStorage[i].getItemDamage());
                    }
                }
            }

            // Remove one of the cooling blocks that is healing the needles and
            // DNA samples.
            if (this.cryofreezerInput != null && this.cryofreezerInput[0] != null)
            {
                --this.cryofreezerInput[0].stackSize;
                if (this.cryofreezerInput[0].stackSize <= 0)
                {
                    this.cryofreezerInput[0] = null;
                }
            }
        }
    }

    /**
     * 
     */
    @Override
    public void updateAnimation()
    {
        // Active state has many textures based on item cook progress.
        if (this.canSmelt() && isPowered())
        {
            // Load this texture onto the entity.
            TEXTURE = "models/" + MadFurnaces.CRYOFREEZER_INTERNALNAME + "/powered.png";

            // Play a sound of the freezer working and doing cold things.
            if (worldObj.getWorldTime() % MadScience.SECOND_IN_TICKS == 0L)
            {
                this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, CryofreezerSounds.CRYOFREEZER_IDLE, 1.0F, 1.0F);
            }
        }
        else
        {
            // Idle state single texture.
            TEXTURE = "models/" + MadFurnaces.CRYOFREEZER_INTERNALNAME + "/idle.png";
        }
    }

    /** Allows the entity to update its state. */
    @Override
    public void updateEntity()
    {
        // Important to call the class below us!
        super.updateEntity();

        boolean inventoriesChanged = false;

        // Decrease to amount of energy this item has on client and server.
        if (this.isPowered() && this.canSmelt() && this.worldObj.rand.nextBoolean())
        {
            // Power consumption is not every tick but random.
            this.consumeEnergy(MadConfig.CRYOFREEZER_CONSUME);
        }

        // Update status of the machine if it has redstone power or not.
        checkRedstonePower();

        // Server side processing for furnace.
        if (!this.worldObj.isRemote)
        {
            // Change texture based on state.
            updateAnimation();

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

            PacketDispatcher.sendPacketToAllAround(this.xCoord, this.yCoord, this.zCoord, MadConfig.PACKETSEND_RADIUS, worldObj.provider.dimensionId, new CryofreezerPackets(this.xCoord, this.yCoord, this.zCoord, currentItemCookingValue, currentItemCookingMaximum,
                    getEnergy(ForgeDirection.UNKNOWN), getEnergyCapacity(ForgeDirection.UNKNOWN), this.TEXTURE).makePacket());
        }

        if (inventoriesChanged)
        {
            this.onInventoryChanged();
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

        // Path to current texture that should be loaded onto the model.
        nbt.setString("TexturePath", this.TEXTURE);

        // Two tag lists for each type of items we have in this entity.
        NBTTagList inputItems = new NBTTagList();
        NBTTagList outputItems = new NBTTagList();

        // Input slots.
        for (int i = 0; i < this.cryofreezerInput.length; ++i)
        {
            if (this.cryofreezerInput[i] != null)
            {
                NBTTagCompound inputSlots = new NBTTagCompound();
                inputSlots.setByte("InputSlot", (byte) i);
                this.cryofreezerInput[i].writeToNBT(inputSlots);
                inputItems.appendTag(inputSlots);
            }
        }

        // Output slots.
        for (int i = 0; i < this.cryoFreezerStorage.length; ++i)
        {
            if (this.cryoFreezerStorage[i] != null)
            {
                NBTTagCompound outputSlots = new NBTTagCompound();
                outputSlots.setByte("OutputSlot", (byte) i);
                this.cryoFreezerStorage[i].writeToNBT(outputSlots);
                outputItems.appendTag(outputSlots);
            }
        }

        // Save the input and output items.
        nbt.setTag("InputItems", inputItems);
        nbt.setTag("OutputItems", outputItems);
    }
}

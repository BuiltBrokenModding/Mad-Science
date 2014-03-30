package madscience.tileentities.magloader;

import madscience.MadConfig;
import madscience.MadFurnaces;
import madscience.MadScience;
import madscience.items.ItemDecay;
import madscience.tileentities.prefab.MadTileEntity;
import madscience.util.MadUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MagLoaderEntity extends MadTileEntity implements ISidedInventory
{
    private static final int[] slots_bottom = new int[]
    { 2, 1 };

    private static final int[] slots_sides = new int[]
    { 1 };

    private static final int[] slots_top = new int[]
    { 0 };

    /** The items that make up that container area of the furnace. */
    private ItemStack[] bulletStorage = new ItemStack[13];

    // Holds all of the slots for the freezer on the server.
    public MagLoaderContainer CONTAINER;

    /** Name to display on inventory screen. */
    private String containerCustomName;

    /** The number of ticks that a fresh copy of the currently-burning item would keep the furnace burning for */
    public int currentItemCookingMaximum;

    /** The number of ticks that the current item has been cooking for */
    public int currentItemCookingValue;

    /** Stack of empty of magazine at some variable health that needs to be filled. */
    private ItemStack[] magloaderInput = new ItemStack[1];

    /** Filled pulse rifle magazines that completed the cooking process. */
    private ItemStack[] magloaderOutput = new ItemStack[1];

    // Path to texture that should be displayed on our model.
    public String TEXTURE = "models/" + MadFurnaces.MAGLOADER_INTERNALNAME + "/empty.png";

    /** Total number of bullets that we have stored inside our inventory from the server. */
    public int clientBulletCount = 0;

    /** Total number of magazine in the input slot. */
    public int clientMagazineCount = 0;

    public MagLoaderEntity()
    {
        super(MadConfig.MAGLOADER_CAPACTITY, MadConfig.MAGLOADER_INPUT);
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
    public boolean canSmelt()
    {
        // Check if we have valid fuel and items to keep cold.
        if (this.magloaderInput == null || this.bulletStorage == null)
        {
            return false;
        }
        // Check if we have a valid fuel block to keep freezer cold.
        if (this.magloaderInput[0] == null)
        {
            return false;
        }

        // Check for snowballs.
        if (this.magloaderInput[0].isItemEqual(new ItemStack(Item.snowball)))
        {
            currentItemCookingMaximum = 200;
            // MadScience.logger.info("canSmelt() SNOWBALL ACCEPTED");
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
        if (this.magloaderInput[slot] != null)
        {
            ItemStack itemstack;

            if (this.magloaderInput[slot].stackSize <= numItems)
            {
                itemstack = this.magloaderInput[slot];
                this.magloaderInput[slot] = null;
                return itemstack;
            }
            itemstack = this.magloaderInput[slot].splitStack(numItems);

            if (this.magloaderInput[slot].stackSize == 0)
            {
                this.magloaderInput[slot] = null;
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
    public String getInvName()
    {
        return this.isInvNameLocalized() ? this.containerCustomName : "container.furnace";
    }

    /** Returns an integer between 0 and the passed value representing how close the current item is to being completely cooked */
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
        return this.magloaderInput.length;
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
        return magloaderOutput.length;
    }

    public int getSizeStorageInventory()
    {
        return bulletStorage.length;
    }
    
    public int getNumberOfBulletsInStorageInventory()
    {
        return 0;
    }
    
    public int getNumberOfMagazinesInInputInventory()
    {
        // Magazine loader array is empty return zero.
        if (magloaderInput == null)
        {
            return 0;
        }
        
        // Magazine loader input slot is empty return zero.
        if (magloaderInput[0] == null)
        {
            return 0;
        }

        // Magazine loader can send proper number of magazines to return method.
        return magloaderInput[0].stackSize;            
    }

    /** Returns the stack in slot i */
    @Override
    public ItemStack getStackInSlot(int slot)
    {
        // Input slot 1 for empty or partially filled magazines.
        if (slot == 0)
        {
            return this.magloaderInput[0];
        }

        // Output slot 1 for filled magazines.
        if (slot == 1)
        {
            return this.magloaderOutput[0];
        }

        // Bullet storage area to hold tons of bullets for filling magazines.
        if (slot == 2)
        {
            return this.bulletStorage[0];
        }
        else if (slot > 2)
        {
            return this.bulletStorage[slot - 1];
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
        NBTTagList nbtStorage = nbt.getTagList("StorageItems");

        // Cast the save data onto our running object.
        this.magloaderInput = new ItemStack[this.getSizeInputInventory()];
        this.bulletStorage = new ItemStack[this.getSizeStorageInventory()];

        // Input item processing.
        for (int i = 0; i < nbtInput.tagCount(); ++i)
        {
            NBTTagCompound inputSaveData = (NBTTagCompound) nbtInput.tagAt(i);
            byte b0 = inputSaveData.getByte("InputSlot");

            if (b0 >= 0 && b0 < this.magloaderInput.length)
            {
                this.magloaderInput[b0] = ItemStack.loadItemStackFromNBT(inputSaveData);
            }
        }
        
        // Output slot processing.
        for (int i = 0; i < nbtOutput.tagCount(); ++i)
        {
            NBTTagCompound outputSaveData = (NBTTagCompound) nbtOutput.tagAt(i);
            byte b0 = outputSaveData.getByte("OutputSlot");

            if (b0 >= 0 && b0 < this.magloaderOutput.length)
            {
                this.magloaderOutput[b0] = ItemStack.loadItemStackFromNBT(outputSaveData);
            }
        }

        // Storage slots processing.
        for (int i = 0; i < nbtOutput.tagCount(); ++i)
        {
            NBTTagCompound storageSaveData = (NBTTagCompound) nbtOutput.tagAt(i);
            byte b0 = storageSaveData.getByte("StorageSlot");

            if (b0 >= 0 && b0 < this.bulletStorage.length)
            {
                this.bulletStorage[b0] = ItemStack.loadItemStackFromNBT(storageSaveData);
            }
        }

        // Amount of time we have been working.
        this.currentItemCookingValue = nbt.getShort("CookTime");
        
        // Number of bullets inside of this machine right now.
        this.clientBulletCount = nbt.getInteger("clientBulletCount");
        
        // Number of magazine in the input slot stack right now.
        this.clientMagazineCount = nbt.getInteger("clientMagazineCount");

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

    // This sets the slots contents, it has 2 params
    // @param int slot, this is the slots number
    // @param ItemStack stack, this is the stack you want to add
    @Override
    public void setInventorySlotContents(int slot, ItemStack stack)
    {
        // Input slot 1 for empty or partially filled magazines.
        if (slot == 0)
        {
            this.magloaderInput[0] = stack;
        }

        // Output slot 1 for filled magazines.
        if (slot == 1)
        {
            this.magloaderOutput[0] = stack;
        }

        // Bullet storage area to hold tons of bullets for filling magazines.
        if (slot == 2)
        {
            this.bulletStorage[0] = stack;
        }
        else if (slot > 2)
        {
            this.bulletStorage[slot - 1] = stack;
        }

        // This checks to make sure the stack is not nothing, and then makes sure the stack is not going over the limit
        // Of the stack
        if (stack != null && stack.stackSize > getInventoryStackLimit())
        {
            stack.stackSize = getInventoryStackLimit();
        }
    }

    public void smeltItem()
    {
        // Converts input item into result item along with waste items.
        if (this.canSmelt())
        {
            // Loop through all of the storage items and see if we can heal
            // them.
            for (int i = 0; i < this.bulletStorage.length; ++i)
            {
                if (this.bulletStorage[i] != null)
                {
                    // Check if we need to heal ourselves.
                    int dmg = bulletStorage[i].getItemDamage();

                    // Heal ourselves for being inside a powered and equipped
                    // freezer.
                    if (bulletStorage[i] != null && bulletStorage[i].getItem() instanceof ItemDecay && dmg <= bulletStorage[i].getMaxDamage())
                    {
                        bulletStorage[i].setItemDamage(dmg - 1);

                        // Debugging message.
                        MadScience.logger.info("WORLD(" + bulletStorage[i].getUnlocalizedName() + "): " + bulletStorage[i].getItemDamage());
                    }
                }
            }

            // Remove an empty magazine from the stack since we filled one.
            if (this.magloaderInput != null && this.magloaderInput[0] != null)
            {
                --this.magloaderInput[0].stackSize;
                if (this.magloaderInput[0].stackSize <= 0)
                {
                    this.magloaderInput[0] = null;
                }
            }
        }
    }

    private void updateAnimation()
    {
        // Active state has many textures based on item cook progress.
        if (this.canSmelt() && isPowered())
        {
            // Load this texture onto the entity.
            TEXTURE = "models/" + MadFurnaces.MAGLOADER_INTERNALNAME + "/full.png";

            // Play a sound of the freezer working and doing cold things.
            if (worldObj.getWorldTime() % MadScience.SECOND_IN_TICKS == 0L)
            {
                this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, MagLoaderSounds.MAGLOADER_IDLE, 1.0F, 1.0F);
            }
        }
        else
        {
            // Idle state single texture.
            TEXTURE = "models/" + MadFurnaces.MAGLOADER_INTERNALNAME + "/empty.png";
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
            this.consumeEnergy(MadConfig.MAGLOADER_CONSUME);
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

            PacketDispatcher.sendPacketToAllAround(this.xCoord, this.yCoord, this.zCoord, MadConfig.PACKETSEND_RADIUS, worldObj.provider.dimensionId, new MagLoaderPackets(this.xCoord, this.yCoord, this.zCoord, currentItemCookingValue,
                    currentItemCookingMaximum, getEnergy(ForgeDirection.UNKNOWN), getEnergyCapacity(ForgeDirection.UNKNOWN), this.TEXTURE, this.getNumberOfBulletsInStorageInventory(), this.getNumberOfMagazinesInInputInventory()).makePacket());
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

        // Amount of time left to cook current item inside of the furnace.
        nbt.setShort("CookTime", (short) this.currentItemCookingValue);
        
        // Current bullet count in the storage slots.
        nbt.setInteger("clientBulletCount", this.clientBulletCount);
        
        // Current magazine count in input slot.
        nbt.setInteger("clientMagazineCount", this.clientMagazineCount);

        // Path to current texture that should be loaded onto the model.
        nbt.setString("TexturePath", this.TEXTURE);

        // Two tag lists for each type of items we have in this entity.
        NBTTagList inputItems = new NBTTagList();
        NBTTagList outputItems = new NBTTagList();
        NBTTagList storageItems = new NBTTagList();

        // Input slot.
        for (int i = 0; i < this.magloaderInput.length; ++i)
        {
            if (this.magloaderInput[i] != null)
            {
                NBTTagCompound inputSlots = new NBTTagCompound();
                inputSlots.setByte("InputSlot", (byte) i);
                this.magloaderInput[i].writeToNBT(inputSlots);
                inputItems.appendTag(inputSlots);
            }
        }
        
        // Output slot.
        for (int i = 0; i < this.magloaderOutput.length; ++i)
        {
            if (this.magloaderOutput[i] != null)
            {
                NBTTagCompound outputSlots = new NBTTagCompound();
                outputSlots.setByte("OutputSlot", (byte) i);
                this.magloaderOutput[i].writeToNBT(outputSlots);
                outputItems.appendTag(outputSlots);
            }
        }

        // Storage slots.
        for (int i = 0; i < this.bulletStorage.length; ++i)
        {
            if (this.bulletStorage[i] != null)
            {
                NBTTagCompound outputSlots = new NBTTagCompound();
                outputSlots.setByte("StorageSlot", (byte) i);
                this.bulletStorage[i].writeToNBT(outputSlots);
                outputItems.appendTag(outputSlots);
            }
        }

        // Save the input and output items.
        nbt.setTag("InputItems", inputItems);
        nbt.setTag("OutputItems", outputItems);
        nbt.setTag("StorageItems", storageItems);

        if (this.isInvNameLocalized())
        {
            nbt.setString("CustomName", this.containerCustomName);
        }
    }
}

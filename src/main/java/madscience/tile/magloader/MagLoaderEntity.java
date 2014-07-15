package madscience.tile.magloader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import madscience.MadConfig;
import madscience.MadFurnaces;
import madscience.MadScience;
import madscience.MadWeapons;
import madscience.factory.tileentity.MadTileEntityPrefab;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.common.network.PacketDispatcher;

public class MagLoaderEntity extends MadTileEntityPrefab implements ISidedInventory
{
    private static final int[] slots_bottom = new int[]
    { 2, 1 };

    private static final int[] slots_sides = new int[]
    { 1 };

    private static final int[] slots_top = new int[]
    { 0 };

    /** The items that make up that container area of the furnace. */
    private ItemStack[] bulletStorage = new ItemStack[13];

    /** Total number of bullets that we have stored inside our inventory from the server. */
    int clientBulletCount = 0;

    /** Total number of magazine in the input slot. */
    int clientMagazineCount = 0;

    /** The number of ticks that a fresh copy of the currently-burning item would keep the furnace burning for */
    int currentItemCookingMaximum;

    /** The number of ticks that the current item has been cooking for */
    int currentItemCookingValue;

    /** Keeps track if we have played the sound of a magazine ItemStack being inserted into input slot 1. */
    boolean hasPlayedMagazineInsertSound = false;

    /** Stack of empty of magazine at some variable health that needs to be filled. */
    private ItemStack[] magloaderInput = new ItemStack[1];

    /** Filled pulse rifle magazines that completed the cooking process. */
    private ItemStack[] magloaderOutput = new ItemStack[1];

    /** Maximum number of rounds that have been programmed by the autoloader to feed into the magazines to prevent jamming. */
    private int MAXIMUM_ROUNDS = 95;

    /** Number of magazines that are currently being stored in the output slot. */
    int clientOutputMagazineCount;

    public MagLoaderEntity()
    {
        super(MadFurnaces.MAGLOADER_INTERNALNAME);
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

    @Override
    public boolean canSmelt()
    {
        // Check if there are no bullets or magazines in the device then it cannot operate.
        if (this.magloaderInput == null || this.bulletStorage == null)
        {
            return false;
        }

        // Check if there are magazines in the input slot.
        if (this.magloaderInput[0] == null)
        {
            return false;
        }

        // Check if we have redstone power.
        if (!this.isRedstonePowered)
        {
            return false;
        }

        // Check if there are at least 95 rounds to even load into a magazine.
        if (this.getNumberOfBulletsInStorageInventory() < MAXIMUM_ROUNDS)
        {
            return false;
        }

        // Check that the input slot contained pulse rifle magazines.
        if (this.magloaderInput[0].isItemEqual(new ItemStack(MadWeapons.WEAPONITEM_MAGAZINEITEM)))
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
    public String getMachineInternalName()
    {
        return MadFurnaces.MAGLOADER_INTERNALNAME;
    }

    public int getNumberOfBulletsInStorageInventory()
    {
        // Check to make sure bullet storage array is not null.
        if (bulletStorage == null)
        {
            return 0;
        }

        // Save CPU time and just return zero if nothing is in the array.
        if (this.bulletStorage.length <= 0)
        {
            return 0;
        }

        // Loop through all of the storage array slots.
        int totalBulletCount = 0;
        ItemStack compareBulletItem = new ItemStack(MadWeapons.WEAPONITEM_BULLETITEM);
        for (int i = 0; i < this.bulletStorage.length; ++i)
        {
            if (this.bulletStorage[i] != null && compareBulletItem.isItemEqual(this.bulletStorage[i]))
            {
                // Count how many bullets are in this stack.
                totalBulletCount += bulletStorage[i].stackSize;
            }
        }

        // Returns the total amount of bullets that we located inside of the storage array slots.
        return totalBulletCount;
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
            // Input slot 1 - Pulse Rifle Magazine in input slot 1.
            ItemStack compareMagazineItem = new ItemStack(MadWeapons.WEAPONITEM_MAGAZINEITEM);
            if (compareMagazineItem.isItemEqual(items))
            {
                return true;
            }
        }

        // Output slot 1 - Filled magazine would be here.
        if (slot == 1)
        {
            // You cannot ever insert things into the output slot.
            return false;
        }

        // Storage area for bullets.
        ItemStack compareBulletItem = new ItemStack(MadWeapons.WEAPONITEM_BULLETITEM);
        if (slot >= 2)
        {
            // Check to make sure only bullets can be put in these slots.
            if (compareBulletItem.isItemEqual(items))
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
        NBTTagList nbtStorage = nbt.getTagList("StorageItems");

        // Cast the save data onto our running object.
        this.magloaderInput = new ItemStack[this.getSizeInputInventory()];
        this.magloaderOutput = new ItemStack[this.getSizeOutputInventory()];
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
        for (int i = 0; i < nbtStorage.tagCount(); ++i)
        {
            NBTTagCompound storageSaveData = (NBTTagCompound) nbtStorage.tagAt(i);
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
        
        // Number of filled magazines in the output slot.
        this.clientOutputMagazineCount = nbt.getInteger("clientOutputMagazineCount");

        // Determines if the sound of a magazine stack being inserted into the machine has been played or not.
        this.hasPlayedMagazineInsertSound = nbt.getBoolean("hasPlayedMagazineInsertSound");
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

    @Override
    public void smeltItem()
    {
        // Converts input item into result item along with waste items.
        if (this.canSmelt())
        {
            // Prepares a compare item, counter and array list for determining what bullets to eat.
            ItemStack compareBulletItem = new ItemStack(MadWeapons.WEAPONITEM_BULLETITEM);
            int preparedRounds = 0;
            List<MagLoaderComparatorItem> list = new ArrayList<MagLoaderComparatorItem>();

            // Loop through all of the potential bullets in storage area.
            for (int i = 0; i < this.bulletStorage.length; i++)
            {
                // Build up a list of all bullets.
                ItemStack bulletFromStorageItem = this.bulletStorage[i];
                if (bulletFromStorageItem != null)
                {
                    // Count up to 95 rounds or until we are out of bullets.
                    if (bulletFromStorageItem.isItemEqual(compareBulletItem) && preparedRounds < MAXIMUM_ROUNDS)
                    {
                        // Add the bullet itemstack to our list of bullets for sorting in next step.
                        list.add(new MagLoaderComparatorItem(i, bulletFromStorageItem.stackSize));

                        // Add the number of rounds from the current stack into the total amount we are looking for.
                        preparedRounds += bulletFromStorageItem.stackSize;

                        // Debuggin'
                        // MadScience.logger.info("Slot " + String.valueOf(i) + " contains bullet itemstack with " + String.valueOf(bulletFromStorageItem.stackSize) + " rounds.");
                    }
                }
            }

            // Sort our list of bullets by stack size.
            Collections.sort(list, Collections.reverseOrder(new MagLoaderComparator()));
            // Collections.sort(list, new MagLoaderComparator());

            // Now we can be assured that the highest bullet stack count will be index zero in this list.
            int loadedRounds = 0;
            if (list != null && list.size() >= 1)
            {
                for (MagLoaderComparatorItem preparedBulletItem : list)
                {
                    if (preparedBulletItem != null)
                    {
                        // Decrease the stack for the rounds if they are 64.
                        if (this.bulletStorage[preparedBulletItem.slotNumber].stackSize >= 64)
                        {
                            if (loadedRounds < MAXIMUM_ROUNDS)
                            {
                                // Increase the number of rounds we have loaded towards our total.
                                loadedRounds += this.bulletStorage[preparedBulletItem.slotNumber].stackSize;

                                // Destroy this slot since it was full of delicious ammo.
                                this.bulletStorage[preparedBulletItem.slotNumber] = null;
                            }
                        }
                        else
                        {
                            if (loadedRounds < MAXIMUM_ROUNDS)
                            {
                                // Increase the number of rounds we have loaded towards our total.
                                if (this.bulletStorage[preparedBulletItem.slotNumber].stackSize > 0)
                                {
                                    loadedRounds += this.bulletStorage[preparedBulletItem.slotNumber].stackSize;
                                }
                                else if (this.bulletStorage[preparedBulletItem.slotNumber].stackSize <= 0)
                                {
                                    // Adding one to account for zero index
                                    loadedRounds += this.bulletStorage[preparedBulletItem.slotNumber].stackSize + 1;
                                }

                                // Always destroy the item after being done with it.
                                this.bulletStorage[preparedBulletItem.slotNumber] = null;
                            }
                        }
                    }
                }

                // Subtract the difference from the loaded rounds from a full magazine to determine if we need to put anything back.
                int bulletsToReturnToStorage = loadedRounds - MAXIMUM_ROUNDS;
                if (bulletsToReturnToStorage > 0)
                {
                    // Use the slots that we emptied out from loading the magazine in the case of the machine being fully loaded.
                    for (MagLoaderComparatorItem preparedBulletItem : list)
                    {
                        ItemStack bulletsReturned = new ItemStack(MadWeapons.WEAPONITEM_BULLETITEM, bulletsToReturnToStorage);
                        this.bulletStorage[preparedBulletItem.slotNumber] = bulletsReturned.copy();
                        // MadScience.logger.info("Magazine Loader: Returned " + String.valueOf(bulletsToReturnToStorage) + " bullets to storage area.");
                        break;
                    }
                }

                // Place a filled magazine into the output slot.
                ItemStack compareFullMagazine = new ItemStack(MadWeapons.WEAPONITEM_MAGAZINEITEM, 1, Math.abs(100 - MAXIMUM_ROUNDS));
                if (this.magloaderOutput[0] == null)
                {
                    this.magloaderOutput[0] = compareFullMagazine.copy();
                    // MadScience.logger.info("Magazine Loader: Added filled magazine to output slot.");
                }
                else if (this.magloaderOutput[0].isItemEqual(compareFullMagazine))
                {
                    magloaderOutput[0].stackSize += compareFullMagazine.stackSize;
                    // MadScience.logger.info("Magazine Loader: Added filled magazine to output slot.");
                }

                // Remove an empty magazine from the stack since we filled one.
                if (this.magloaderInput != null && this.magloaderInput[0] != null)
                {
                    // MadScience.logger.info("Magazine Loader: Removing empty magazine from input stack.");
                    --this.magloaderInput[0].stackSize;
                    if (this.magloaderInput[0].stackSize <= 0)
                    {
                        this.magloaderInput[0] = null;
                    }
                }

                // Play a sound that lets the user know something happened!
                this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, MagLoaderSounds.MAGLOADER_PUSHSTOP, 1.0F, 1.0F);
            }
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
        if (this.isPowered() && this.isRedstonePowered)
        {
            // Power consumption is not every tick but random.
            this.consumeEnergy(MadConfig.MAGLOADER_CONSUME);
        }

        // Update status of the machine if it has redstone power or not.
        checkRedstonePower();

        // Server side processing for furnace.
        if (!this.worldObj.isRemote)
        {
            // Check if we should play the sound of a magazine being inserted into the input slot.
            if (!this.hasPlayedMagazineInsertSound && this.getNumberOfMagazinesInInputInventory() > 0)
            {
                // MadScience.logger.info("Magazine Loader: Playing Insert Sound!");
                this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, MagLoaderSounds.MAGLOADER_INSERTMAGAZINE, 1.0F, 1.0F);
                this.hasPlayedMagazineInsertSound = true;
            }
            else if (this.hasPlayedMagazineInsertSound && this.getNumberOfMagazinesInInputInventory() <= 0)
            {
                // Check if we need to reset the status of the magazine insert sound.
                // MadScience.logger.info("Magazine Loader: Resetting Insert Sound!");
                this.hasPlayedMagazineInsertSound = false;
            }

            // First tick for new item being cooked in furnace.
            if (this.currentItemCookingValue == 0 && this.canSmelt() && this.isPowered())
            {
                this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, MagLoaderSounds.MAGLOADER_PUSHSTART, 1.0F, 1.0F);

                // Kickstarts the cooking timer loop.
                currentItemCookingMaximum = 200;
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
                else
                {
                    // Update progress noises while we wait.
                    if (worldObj.getWorldTime() % MadScience.SECOND_IN_TICKS == 0L)
                    {
                        this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, MagLoaderSounds.MAGLOADER_LOADING, 1.0F, 1.0F);
                    }
                }
            }
            else
            {
                // Reset loop, prepare for next item or closure.
                this.currentItemCookingValue = 0;
            }

            PacketDispatcher.sendPacketToAllAround(this.xCoord, this.yCoord, this.zCoord, MadConfig.PACKETSEND_RADIUS, worldObj.provider.dimensionId, new MagLoaderPackets(this.xCoord, this.yCoord, this.zCoord, currentItemCookingValue,
                    currentItemCookingMaximum, getEnergy(ForgeDirection.UNKNOWN), getEnergyCapacity(ForgeDirection.UNKNOWN), this.getNumberOfBulletsInStorageInventory(), this.getNumberOfMagazinesInInputInventory(), this.hasPlayedMagazineInsertSound, this.getMagazineCountInOutputSlot())
                    .makePacket());
        }

        if (inventoriesChanged)
        {
            this.onInventoryChanged();
        }
    }

    private int getMagazineCountInOutputSlot()
    {
        // Returns the number of filled magazines in the output slot.
        ItemStack outputMagazines = getStackInSlot(1);
        if (outputMagazines == null)
        {
            return 0;
        }
        
        return outputMagazines.stackSize;
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
        
        // Number of filled magazines in the output slot.
        nbt.setInteger("clientOutputMagazineCount", this.clientOutputMagazineCount);

        // Determines if the sound of magazine being inserted into the machine has been played or not.
        nbt.setBoolean("hasPlayedMagazineInsertSound", this.hasPlayedMagazineInsertSound);

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
                NBTTagCompound storageSlots = new NBTTagCompound();
                storageSlots.setByte("StorageSlot", (byte) i);
                this.bulletStorage[i].writeToNBT(storageSlots);
                storageItems.appendTag(storageSlots);
            }
        }

        // Save the input and output items.
        nbt.setTag("InputItems", inputItems);
        nbt.setTag("OutputItems", outputItems);
        nbt.setTag("StorageItems", storageItems);
    }
}

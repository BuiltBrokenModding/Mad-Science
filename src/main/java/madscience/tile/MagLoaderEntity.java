package madscience.tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import madscience.MadWeapons;
import madscience.factory.slotcontainers.MadSlotContainerTypeEnum;
import madscience.factory.tileentity.MadTileEntityFactoryProduct;
import madscience.factory.tileentity.prefab.MadTileEntityPrefab;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class MagLoaderEntity extends MadTileEntityPrefab
{
    /** Keeps track if we have played the sound of a magazine ItemStack being inserted into input slot 1. */
    boolean hasPlayedMagazineInsertSound = false;

    /** Maximum number of rounds that have been programmed by the autoloader to feed into the magazines to prevent jamming. */
    private int MAXIMUM_ROUNDS = 95;

    public MagLoaderEntity()
    {
        super();
    }

    public MagLoaderEntity(MadTileEntityFactoryProduct registeredMachine)
    {
        super(registeredMachine);
    }

    public MagLoaderEntity(String machineName)
    {
        super(machineName);
    }

    @Override
    public boolean canSmelt()
    {
        super.canSmelt();
        
        // Check if there are magazines in the input slot.
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1) == null)
        {
            return false;
        }

        // Check if we have redstone power.
        if (!this.isRedstonePowered())
        {
            return false;
        }

        // Check if there are at least 95 rounds to even load into a magazine.
        if (this.getNumberOfBulletsInStorageInventory() < MAXIMUM_ROUNDS)
        {
            return false;
        }

        // Check that the input slot contained pulse rifle magazines.
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1).isItemEqual(new ItemStack(MadWeapons.WEAPONITEM_MAGAZINEITEM)))
        {
            return true;
        }

        return false;
    }

    public int getNumberOfBulletsInStorageInventory()
    {
        // Check to make sure bullet storage array is not null.
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_STORAGE) == null)
        {
            return 0;
        }

        // Loop through all of the storage array slots.
        int totalBulletCount = 0;
        ItemStack compareBulletItem = new ItemStack(MadWeapons.WEAPONITEM_BULLETITEM);
        for (int i = 2; i < this.getSlotCountByType(MadSlotContainerTypeEnum.INPUT_STORAGE); ++i)
        {
            if (this.getStackInSlot(i) != null && compareBulletItem.isItemEqual(this.getStackInSlot(i)))
            {
                // Count how many bullets are in this stack.
                totalBulletCount += this.getStackInSlot(i).stackSize;
            }
        }

        // Returns the total amount of bullets that we located inside of the storage array slots.
        return totalBulletCount;
    }

    public int getNumberOfMagazinesInInputInventory()
    {
        // Magazine loader input slot is empty return zero.
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1) == null)
        {
            return 0;
        }

        // Magazine loader can send proper number of magazines to return method.
        return this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1).stackSize;
    }

    /** Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot. */
    @Override
    public boolean isItemValidForSlot(int slot, ItemStack items)
    {
        super.isItemValidForSlot(slot, items);
        
        // Check if machine trying to insert item into given slot is allowed.
        if (slot == this.getSlotIDByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1))
        {
            // Input slot 1 - Pulse Rifle Magazine in input slot 1.
            ItemStack compareMagazineItem = new ItemStack(MadWeapons.WEAPONITEM_MAGAZINEITEM);
            if (compareMagazineItem.isItemEqual(items))
            {
                return true;
            }
        }

        // Storage area for bullets.
        ItemStack compareBulletItem = new ItemStack(MadWeapons.WEAPONITEM_BULLETITEM);
        if (slot >= this.getSlotIDByType(MadSlotContainerTypeEnum.INPUT_STORAGE))
        {
            // Check to make sure only bullets can be put in these slots.
            if (compareBulletItem.isItemEqual(items))
            {
                return true;
            }
        }

        return false;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);

        // Determines if the sound of a magazine stack being inserted into the machine has been played or not.
        this.hasPlayedMagazineInsertSound = nbt.getBoolean("hasPlayedMagazineInsertSound");
    }

    @Override
    public void smeltItem()
    {
        super.smeltItem();
        
        // Converts input item into result item along with waste items.
        if (this.canSmelt())
        {
            // Prepares a compare item, counter and array list for determining what bullets to eat.
            ItemStack compareBulletItem = new ItemStack(MadWeapons.WEAPONITEM_BULLETITEM);
            int preparedRounds = 0;
            List<MagLoaderComparatorItem> list = new ArrayList<MagLoaderComparatorItem>();

            // Loop through all of the potential bullets in storage area.
            for (int i = 2; i < this.getSlotCountByType(MadSlotContainerTypeEnum.INPUT_STORAGE); i++)
            {
                // Build up a list of all bullets.
                ItemStack bulletFromStorageItem = this.getStackInSlot(i);
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
                        // MadMod.logger.info("Slot " + String.valueOf(i) + " contains bullet itemstack with " + String.valueOf(bulletFromStorageItem.stackSize) + " rounds.");
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
                        if (this.getStackInSlot(preparedBulletItem.slotNumber).stackSize >= 64)
                        {
                            if (loadedRounds < MAXIMUM_ROUNDS)
                            {
                                // Increase the number of rounds we have loaded towards our total.
                                loadedRounds += this.getStackInSlot(preparedBulletItem.slotNumber).stackSize;

                                // Destroy this slot since it was full of delicious ammo.
                                this.setInventorySlotContents(preparedBulletItem.slotNumber, null);
                            }
                        }
                        else
                        {
                            if (loadedRounds < MAXIMUM_ROUNDS)
                            {
                                // Increase the number of rounds we have loaded towards our total.
                                if (this.getStackInSlot(preparedBulletItem.slotNumber).stackSize > 0)
                                {
                                    loadedRounds += this.getStackInSlot(preparedBulletItem.slotNumber).stackSize;
                                }
                                else if (this.getStackInSlot(preparedBulletItem.slotNumber).stackSize <= 0)
                                {
                                    // Adding one to account for zero index
                                    loadedRounds += this.getStackInSlot(preparedBulletItem.slotNumber).stackSize + 1;
                                }

                                // Always destroy the item after being done with it.
                                this.setInventorySlotContents(preparedBulletItem.slotNumber, null);
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
                        this.setInventorySlotContents(preparedBulletItem.slotNumber, bulletsReturned.copy());
                        // MadMod.logger.info("Magazine Loader: Returned " + String.valueOf(bulletsToReturnToStorage) + " bullets to storage area.");
                        break;
                    }
                }

                // Place a filled magazine into the output slot.
                ItemStack compareFullMagazine = new ItemStack(MadWeapons.WEAPONITEM_MAGAZINEITEM, 1, Math.abs(100 - MAXIMUM_ROUNDS));
                if (this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1) == null)
                {
                    this.setInventorySlotContentsByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1, compareFullMagazine.copy());
                    // MadMod.logger.info("Magazine Loader: Added filled magazine to output slot.");
                }
                else if (this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1).isItemEqual(compareFullMagazine))
                {
                    this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1).stackSize += compareFullMagazine.stackSize;
                    // MadMod.logger.info("Magazine Loader: Added filled magazine to output slot.");
                }

                // Remove an empty magazine from the stack since we filled one.
                if (this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1) != null)
                {
                    // MadMod.logger.info("Magazine Loader: Removing empty magazine from input stack.");
                    this.decrStackSize(this.getSlotIDByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1), 1);
                }
            }
        }
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();

        // Server side processing for furnace.
        if (!this.worldObj.isRemote)
        {
            // Decrease to amount of energy this item has on client and server.
            if (this.isPowered() && this.isRedstonePowered() && this.canSmelt())
            {
                // Power consumption is not every tick but random.
                this.consumeInternalEnergy(this.getEnergyConsumeRate());
            }
            
            // Check if we should play the sound of a magazine being inserted into the input slot.
            if (!this.hasPlayedMagazineInsertSound && this.getNumberOfMagazinesInInputInventory() > 0)
            {
                // MadMod.logger.info("Magazine Loader: Playing Insert Sound!");
                this.playSoundByName("InsertMagazine");
                this.hasPlayedMagazineInsertSound = true;
            }
            else if (this.hasPlayedMagazineInsertSound && this.getNumberOfMagazinesInInputInventory() <= 0)
            {
                // Check if we need to reset the status of the magazine insert sound.
                // MadMod.logger.info("Magazine Loader: Resetting Insert Sound!");
                this.hasPlayedMagazineInsertSound = false;
            }

            // First tick for new item being cooked in furnace.
            if (this.getProgressValue() == 0 && this.canSmelt() && this.isPowered())
            {
                // Kickstarts the cooking timer loop.
                this.setProgressMaximum(200);
                this.incrementProgressValue();
            }
            else if (this.getProgressValue() > 0 && this.canSmelt() && this.isPowered())
            {
                // Run on server when we have items and electrical power.
                // Note: This is the main work loop for the block!

                // Increments the timer to kickstart the cooking loop.
                this.incrementProgressValue();

                // Check if furnace has exceeded total amount of time to cook.
                if (this.getProgressValue() >= this.getProgressMaximum())
                {
                    // Convert one item into another via 'cooking' process.
                    this.setProgressValue(0);
                    this.smeltItem();
                }
            }
            else
            {
                // Reset loop, prepare for next item or closure.
                this.setProgressValue(0);
            }
        }
        
        this.updateModel();
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

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);

        // Determines if the sound of magazine being inserted into the machine has been played or not.
        nbt.setBoolean("hasPlayedMagazineInsertSound", this.hasPlayedMagazineInsertSound);
    }
    
    private void updateModel()
    {
        // Get the cooking time scaled to 100%.
        int cookingTimeScaled = this.getItemCookTimeScaled(100);
        
        // Hide all pieces by default.
        this.hideAllModelPieces();
        
        // Always show the base.
        this.setModelWorldRenderVisibilityByName("Base", true);
        
        // Determine what part of the machine should be shown and when.
        if (cookingTimeScaled <= 25 && cookingTimeScaled > 0)
        {
            // push0 and bullet0-4 visible = 25%
            this.setModelWorldRenderVisibilityByName("push0", true);
            this.setModelWorldRenderVisibilityByName("Bullet0", true);
            this.setModelWorldRenderVisibilityByName("Bullet1", true);
            this.setModelWorldRenderVisibilityByName("Bullet2", true);
            this.setModelWorldRenderVisibilityByName("Bullet3", true);
            this.setModelWorldRenderVisibilityByName("Bullet4", true);
        }
        else if (cookingTimeScaled <= 50 && cookingTimeScaled > 25)
        {
            // push1 and bullet1-4 visible = 50%
            this.setModelWorldRenderVisibilityByName("push1", true);
            this.setModelWorldRenderVisibilityByName("Bullet1", true);
            this.setModelWorldRenderVisibilityByName("Bullet2", true);
            this.setModelWorldRenderVisibilityByName("Bullet3", true);
            this.setModelWorldRenderVisibilityByName("Bullet4", true);
        }
        else if (cookingTimeScaled <= 75 && cookingTimeScaled > 50)
        {
            // push2 and bullet2-4 visible = 75%
            this.setModelWorldRenderVisibilityByName("push2", true);
            this.setModelWorldRenderVisibilityByName("Bullet2", true);
            this.setModelWorldRenderVisibilityByName("Bullet3", true);
            this.setModelWorldRenderVisibilityByName("Bullet4", true);
        }
        else if (cookingTimeScaled <= 85 && cookingTimeScaled > 75)
        {
            // push3 and bullet3-4 visible = 85%
            this.setModelWorldRenderVisibilityByName("push3", true);
            this.setModelWorldRenderVisibilityByName("Bullet3", true);
            this.setModelWorldRenderVisibilityByName("Bullet4", true);
        }
        else if (cookingTimeScaled <= 95 && cookingTimeScaled > 85)
        {
            // push4 and bullet4-4 visible = 90%
            this.setModelWorldRenderVisibilityByName("push4", true);
            this.setModelWorldRenderVisibilityByName("Bullet4", true);
        }
        else if (cookingTimeScaled >= 100 && cookingTimeScaled > 95)
        {
            // push5 visible = Done!
            this.setModelWorldRenderVisibilityByName("push5", true);
        }
        else
        {
            if (cookingTimeScaled > 0)
            {
                // Last but not least if we have no idea just render the full push-rod.
                this.setModelWorldRenderVisibilityByName("push5", true);
            }
        }

        // Visible when an empty magazine(s) is in the machine.
        if (this.getNumberOfMagazinesInInputInventory() > 0 || this.getNumberOfMagazinesInInputInventory() > 0)
        {
            // Magazine that can be inserted into the machine for loading.
            this.setModelWorldRenderVisibilityByName("Magazine", true);
            this.setModelWorldRenderVisibilityByName("MagazineBase", true);
        }
    }

    @Override
    public void updateAnimation()
    {
        super.updateAnimation();
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

class MagLoaderComparatorItem
{
    int bulletCount;
    int slotNumber;

    MagLoaderComparatorItem(int slot, int bullets)
    {
        slotNumber = slot;
        bulletCount = bullets;
    }
}

class MagLoaderComparator implements Comparator<MagLoaderComparatorItem>
{
    @Override
    public int compare(MagLoaderComparatorItem ob1, MagLoaderComparatorItem ob2)
    {
        return ob1.bulletCount - ob2.bulletCount;
    }
}

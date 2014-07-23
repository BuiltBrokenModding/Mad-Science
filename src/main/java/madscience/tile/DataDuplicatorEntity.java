package madscience.tile;

import madscience.MadEntities;
import madscience.factory.MadTileEntityFactoryProduct;
import madscience.factory.slotcontainers.MadSlotContainerTypeEnum;
import madscience.factory.tileentity.prefab.MadTileEntityPrefab;
import madscience.items.combinedgenomes.CombinedGenomeMonsterPlacer;
import madscience.items.genomes.ItemGenomeBase;
import madscience.items.memories.CombinedMemoryMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class DataDuplicatorEntity extends MadTileEntityPrefab
{
    public DataDuplicatorEntity()
    {
        super();
    }
    
    public DataDuplicatorEntity(MadTileEntityFactoryProduct registeredMachine)
    {
        super(registeredMachine);
    }

    public DataDuplicatorEntity(String machineName)
    {
        super(machineName);
    }

    @Override
    public boolean canSmelt()
    {
        super.canSmelt();
        
        // Check if we have redstone power applied to us.
        if (!this.isRedstonePowered())
        {
            return false;
        }
        
        // Check if both input slots for reel to copy and empty reel to copy onto.
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1) == null || this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT2) == null)
        {
            return false;
        }

        // Check if output slots are empty and ready to be filled with.
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1) == null)
        {
            return true;
        }

        // Check if input slot 1 matches output slot 1.
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1).isItemEqual(this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1)))
        {
            return false;
        }

        return false;
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack items)
    {
        super.isItemValidForSlot(slot, items);
        
        // Check if input slot 1 is full data
        ItemStack compareEmptyDataReel = new ItemStack(MadEntities.DATAREEL_EMPTY);
        if (slot == this.getSlotIDByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1))
        {
            if (compareEmptyDataReel.isItemEqual(items))
            {
                return true;
            }

            return false;
        }

        // Check if input slot 2 is empty data reel.
        if (slot == this.getSlotIDByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT2))
        {
            if (compareEmptyDataReel.isItemEqual(items))
            {
                return false;
            }

            // Check if we are a genome data reel that is unfinished (AKA damaged).
            if (items != null && items.getItem() instanceof ItemGenomeBase && items.isItemDamaged())
            {
                return false;
            }

            // Completed genomes are allowed to be duplicated.
            if (items != null && items.getItem() instanceof ItemGenomeBase && !items.isItemDamaged())
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

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
    }

    @Override
    public void smeltItem()
    {
        super.smeltItem();
        
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT2) != null &&
                this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT2).isItemDamaged() &&
                this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1) == null)
        {
            if (this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT2).isItemDamaged())
            {
                int currentGenomeStatus = this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT2).getItemDamage();
                this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT2).setItemDamage(--currentGenomeStatus);
            }

            // Copy B -> A
            if (this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1) == null &&
                    !this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT2).isItemDamaged())
            {
                this.setInventorySlotContentsByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1, this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT2).copy());

                --this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT2).stackSize;
                if (this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT2).stackSize <= 0)
                {
                    this.setInventorySlotContentsByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT2, null);
                }
            }

            --this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1).stackSize;
            if (this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1).stackSize <= 0)
            {
                this.setInventorySlotContentsByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, null);
            }

            return;
        }

        // Copy A -> B
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT2) != null &&
                this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1) == null)
        {
            if (this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1) == null)
            {
                this.setInventorySlotContentsByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1, this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1).copy());
            }
            else if (this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1).isItemEqual(this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1)))
            {
                this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1).stackSize += this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1).stackSize;
            }

            --this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT2).stackSize;
            if (this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT2).stackSize <= 0)
            {
                this.setInventorySlotContentsByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT2, null);
            }
        }
    }

    @Override
    public void updateAnimation()
    {
        super.updateAnimation();
        
        // Active state has many textures based on item cook progress.
        if (isPowered() && canSmelt())
        {
            if (this.getAnimationCurrentFrame() <= 9 && worldObj.getWorldTime() % 5L == 0L)
            {
                // Load this texture onto the entity.
                this.setTextureRenderedOnModel("models/" + this.getMachineInternalName() + "/work_" + this.getAnimationCurrentFrame() + ".png");

                // Update animation frame.
                this.incrementAnimationCurrentFrame();
            }
            else if (this.getAnimationCurrentFrame() >= 10)
            {
                // Check if we have exceeded the ceiling and need to reset.
                this.setAnimationCurrentFrame(0);
            }
        }
        else if (isPowered() && !canSmelt())
        {
            // Idle state single texture.
            this.setTextureRenderedOnModel("models/" + this.getMachineInternalName() + "/idle.png");
        }
        else
        {
            // We are not powered or working.
            this.setTextureRenderedOnModel("models/" + this.getMachineInternalName() + "/off.png");
        }
    }

    @Override
    public void updateEntity()
    {
        // Important to call the class below us!
        super.updateEntity();

        if (this.isPowered() && this.canSmelt())
        {
            // Decrease to amount of energy this item has on client and server.
            this.consumeInternalEnergy(this.getEnergyConsumeRate());
        }

        // Server side processing for furnace.
        if (!this.worldObj.isRemote)
        {
            // First tick for new item being cooked in furnace.
            if (this.getProgressValue() == 0 && this.canSmelt() && this.isPowered())
            {
                // New item pulled from cooking stack to be processed, check how long this item will take to cook.
                this.setProgressMaximum(2600);

                // Increments the timer to kickstart the cooking loop.
                this.incrementProgressValue();
            }
            else if (this.getProgressValue() > 0 && this.canSmelt() && this.isPowered())
            {
                // Increments the timer to kickstart the cooking loop.
                this.incrementProgressValue();

                // Check if furnace has exceeded total amount of time to cook.
                if (this.getProgressValue() >= this.getProgressMaximum())
                {
                    // Convert one item into another via 'cooking' process.
                    this.setProgressValue(0);
                    this.smeltItem();
                    this.setInventoriesChanged();
                }
            }
            else
            {
                // Reset loop, prepare for next item or closure.
                this.setProgressValue(0);
            }
        }
    }

    @Override
    public void updateSound()
    {
        super.updateSound();
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
    }

    @Override
    public void initiate()
    {
        super.initiate();
    }
}

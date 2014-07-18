package madscience.tile;

import madscience.MadConfig;
import madscience.MadFurnaces;
import madscience.MadNeedles;
import madscience.factory.MadTileEntityFactoryProduct;
import madscience.factory.mod.MadMod;
import madscience.factory.slotcontainers.MadSlotContainerTypeEnum;
import madscience.factory.tileentity.prefab.MadTileEntityPrefab;
import madscience.util.MadUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class SanitizerEntity extends MadTileEntityPrefab
{
    public SanitizerEntity()
    {
        super();
    }
    
    public SanitizerEntity(MadTileEntityFactoryProduct registeredMachine)
    {
        super(registeredMachine);
    }

    public SanitizerEntity(String machineName)
    {
        super(machineName);
    }

    /** Returns true if the furnace can smelt an item, i.e. has a source item, destination stack isn't full, etc. */
    @Override
    public boolean canSmelt()
    {
        // Check if we have water bucket and dirty needles in input slots and that our internal tank has fluid.
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1) == null)
        {
            return false;
        }
        
        // Check if input slot 2 is a dirty needle stack.
        ItemStack recipeResult = this.getSingleFurnaceResultBySlot(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, MadSlotContainerTypeEnum.OUTPUT_RESULT1);
        if (recipeResult == null)
        {
            // Input slot 2 was not a dirty needle.
            return false;
        }

        // Check if there is fluid inside our internal tank for cleaning the needles.
        if (this.getFluidAmount() <= 0)
        {
            return false;
        }

        // Check if output slots are empty and ready to be filled with items.
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1) == null ||
                this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_EMPTYBUCKET) == null)
        {
            return true;
        }

        // Check if input slot 2 matches output slot 2.
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1).isItemEqual(this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_EMPTYBUCKET)))
        {
            return false;
        }

        // Check if output slot 2 (for cleaned needles) is above item stack limit.
        int slot2Result = this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_EMPTYBUCKET).stackSize + recipeResult.stackSize;
        return (slot2Result <= getInventoryStackLimit() && slot2Result <= recipeResult.getMaxStackSize());
    }

    /** Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot. */
    @Override
    public boolean isItemValidForSlot(int slot, ItemStack items)
    {
        // Check if machine trying to insert item into given slot is allowed.
        if (slot == 0)
        {
            // Input slot 1 - water bucket.
            ItemStack compareWaterBucket = new ItemStack(Item.bucketWater);
            if (compareWaterBucket.isItemEqual(items))
            {
                return true;
            }
        }
        else if (slot == 1)
        {
            // Input slot 2 - dirty needle.
            ItemStack compareDirtyNeedle = new ItemStack(MadNeedles.NEEDLE_DIRTY);
            if (compareDirtyNeedle.isItemEqual(items))
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
    }

    @Override
    public void smeltItem()
    {
        // Converts input item into result item along with waste items.
        if (this.canSmelt())
        {
            // Output 2 - Cleaned needle that used to be dirt input slot 2.
            ItemStack recipeResult = this.getSingleFurnaceResultBySlot(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, MadSlotContainerTypeEnum.OUTPUT_RESULT1);

            // Add cleaned needle to output slot 1 GUI.
            if (this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1) == null)
            {
                this.setInventorySlotContentsByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1, recipeResult.copy());
            }
            else if (this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1).isItemEqual(recipeResult))
            {
                this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1).stackSize += recipeResult.stackSize;
            }

            // Remove a dirty needle from input stack 2.
            --this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1).stackSize;
            if (this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1).stackSize <= 0)
            {
                this.setInventorySlotContentsByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, null);
            }
        }
    }

    @Override
    public void updateAnimation()
    {
        // Active state has many textures based on item cook progress.
        if (canSmelt() && isPowered())
        {
            if (this.getAnimationCurrentFrame() <= 9 && worldObj.getWorldTime() % 15L == 0L)
            {
                // Load this texture onto the entity.
                this.setEntityTexture("models/" + this.getMachineInternalName() + "/work_" + this.getAnimationCurrentFrame() + ".png");

                // Update animation frame.
                this.incrementAnimationCurrentFrame();
                
            }
            else if (this.getAnimationCurrentFrame() >= 10)
            {
                // Check if we have exceeded the ceiling and need to reset.
                this.setAnimationCurrentFrame(0);
            }
        }
        else
        {
            // Idle state single texture.
            this.setEntityTexture("models/" + this.getMachineInternalName() + "/idle.png");
        }
    }

    @Override
    public void updateEntity()
    {
        // Important to call the class below us!
        super.updateEntity();

        boolean inventoriesChanged = false;

        if (this.isPowered() && this.canSmelt())
        {
            // Decrease to amount of energy this item has on client and server.
            this.consumeEnergy(this.getEnergyConsumeRate());

            // Decrease the amount of water in the blocks internal storage.
            this.removeFluidAmountExact(1);
        }

        // Update status of the machine if it has redstone power or not.
        checkRedstonePower();

        // Server side processing for furnace.
        if (!this.worldObj.isRemote)
        {
            // Update block animation and model.
            this.updateAnimation();

            // Play sound while we are cleaning them needles!
            this.updateSound();

            // Checks to see if we can add a bucket from input slot into internal tank.
            this.addBucketToInternalTank();

            // First tick for new item being cooked in furnace.
            if (this.getProgressValue() == 0 && this.canSmelt() && this.isPowered())
            {
                // New item pulled from cooking stack to be processed, check how long this item will take to cook.
                this.setProgressMaximum(200);

                // Increments the timer to kickstart the cooking loop.
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
                    inventoriesChanged = true;
                }
            }
            else
            {
                // Reset loop, prepare for next item or closure.
                this.setProgressValue(0);
            }
        }

        if (inventoriesChanged)
        {
            this.onInventoryChanged();
        }
    }
    
    private boolean addBucketToInternalTank()
    {
        // Check if the input slot for filled buckets is null.
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_FILLEDBUCKET) == null)
        {
            return false;
        }

        // Output 1 - Empty bucket returns from full one in input slot 1 (used to clean the needle).
        ItemStack compareEmptyBucket = new ItemStack(Item.bucketEmpty);
        ItemStack compareWaterBucket = new ItemStack(Item.bucketWater);

        // Check if input slot 1 is a water bucket.
        if (!this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_FILLEDBUCKET).isItemEqual(compareWaterBucket))
        {
            return false;
        }

        // Check if output slot 1 (for empty buckets) is above item stack limit.
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_EMPTYBUCKET) != null)
        {
            int slot1Result = this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_EMPTYBUCKET).stackSize + compareWaterBucket.stackSize;
            boolean shouldStop = (slot1Result <= getInventoryStackLimit() && slot1Result <= compareWaterBucket.getMaxStackSize());
            if (shouldStop)
                return false;
        }

        // Check if the internal water tank has reached it
        if (this.getFluidAmount() >= this.getFluidCapacity())
        {
            return false;
        }

        // Add empty water bucket to output slot 2 GUI.
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_EMPTYBUCKET) == null)
        {
            this.setInventorySlotContentsByType(MadSlotContainerTypeEnum.OUTPUT_EMPTYBUCKET, compareEmptyBucket.copy());
        }
        else if (this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_EMPTYBUCKET).isItemEqual(compareEmptyBucket))
        {
            this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_EMPTYBUCKET).stackSize += compareEmptyBucket.stackSize;
        }

        // Add a bucket's worth of water into the internal tank.
        this.addFluidAmountByBucket(1);

        // Remove a filled bucket of water from input stack 1.
        --this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_FILLEDBUCKET).stackSize;
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_FILLEDBUCKET).stackSize <= 0)
        {
            this.setInventorySlotContentsByType(MadSlotContainerTypeEnum.INPUT_FILLEDBUCKET, null);
        }

        return false;
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
}
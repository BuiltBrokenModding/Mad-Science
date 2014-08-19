package madscience.tile;

import madscience.factory.MadItemFactory;
import madscience.factory.container.MadSlotContainerTypeEnum;
import madscience.factory.product.MadTileEntityFactoryProduct;
import madscience.factory.tile.MadTileEntityPrefab;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;

public class MainframeEntity extends MadTileEntityPrefab
{
    public MainframeEntity(MadTileEntityFactoryProduct registeredMachine)
    {
        super(registeredMachine);
    }

    public MainframeEntity(String machineName)
    {
        super(machineName);
    }

    public MainframeEntity()
    {
        super();
    }

    private void addBucketToInternalTank()
    {
        // Check if the input slot for filled buckets is null.
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_FILLEDBUCKET) == null)
        {
            return;
        }

        // Output 1 - Empty bucket returns from full one in input slot 1
        ItemStack bucketEmpty = new ItemStack(Item.bucketEmpty);
        ItemStack bucketWater = new ItemStack(Item.bucketWater);

        // Check if input slot 1 is a water bucket.
        if (!this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_FILLEDBUCKET).isItemEqual(bucketWater))
        {
            return;
        }

        // Check if output slot 1 (for empty buckets) is above item stack limit.
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_EMPTYBUCKET) != null)
        {
            int slot1Result = this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_EMPTYBUCKET).stackSize + bucketEmpty.stackSize;
            boolean underStackLimit = (slot1Result <= getInventoryStackLimit() && slot1Result <= bucketEmpty.getMaxStackSize());
            if (!underStackLimit)
            {
                // If we are not under the minecraft or item stack limit for output slot 2 then stop here.
                return;
            }
        }

        // Check if the internal water tank has reached it
        if (this.getFluidAmount() >= this.getFluidCapacity())
        {
            return;
        }

        // Add empty water bucket to output slot 2.
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_EMPTYBUCKET) == null)
        {
            this.setInventorySlotContentsByType(MadSlotContainerTypeEnum.OUTPUT_EMPTYBUCKET, bucketEmpty.copy());
        }
        else if (this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_EMPTYBUCKET).isItemEqual(bucketEmpty))
        {
            this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_EMPTYBUCKET).stackSize += bucketEmpty.stackSize;
        }

        // Add a bucket's worth of water into the internal tank.
        this.addFluidAmountByBucket(1);

        // Remove a filled bucket of water from input stack 1.
        this.decrStackSize(this.getSlotIDByType(MadSlotContainerTypeEnum.INPUT_FILLEDBUCKET), 1);
    }

    /** Returns true if the furnace can smelt an item, i.e. has a source item, destination stack isn't full, etc. */
    @Override
    public boolean canSmelt()
    {
        super.canSmelt();
        
        // Check if we have all the genome slots filled and internal tank is not null.
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1) == null ||
                this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT2) == null ||
                this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_EXTRA) == null)
        {
            return false;
        }
        
        // Check to make sure we are not overheating, this stops the computer from working.
        if (this.isOverheating())
        {
            return false;
        }

        // Check for empty reel inside input slot 4.
        ItemStack emptyDataReel = MadItemFactory.instance().getItemStackByFullyQualifiedName("components", "DataReelEmpty", 1);
        if (!emptyDataReel.isItemEqual(this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_EXTRA)))
        {
            return false;
        }

        // Check if input slots 2 and 3 which should be genome data reels are compatible with each other.
        ItemStack[] currentRecipe = this.getRecipeResult(new MadSlotContainerTypeEnum[]{
                MadSlotContainerTypeEnum.INPUT_INGREDIENT1,
                MadSlotContainerTypeEnum.INPUT_INGREDIENT2,
                MadSlotContainerTypeEnum.INPUT_EXTRA,
                MadSlotContainerTypeEnum.OUTPUT_RESULT1
        });
        
        if (currentRecipe == null)
        {
            return false;
        }

        // Check if there is fluid inside our internal tank for cleaning the needles.
        if (this.isFluidTankEmpty())
        {
            return false;
        }

        // Check if output slots are empty and ready to be filled with items.
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1) == null)
        {
            return true;
        }

        // Check if output is above stack limit.
        int slot2Result = this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1).stackSize + emptyDataReel.stackSize;
        return (slot2Result <= getInventoryStackLimit() && slot2Result <= emptyDataReel.getMaxStackSize());
    }

    /** Return current heat level for this machine. */
    private void checkHeatLevels()
    {
        // Computer is switched on and has power reserves.
        if (this.isPowered() && this.isRedstonePowered())
        {
            if (this.getEnergy(ForgeDirection.UNKNOWN) <= this.getEnergyCapacity(ForgeDirection.UNKNOWN))
            {
                // Running computer consumes energy from internal reserve.
                this.consumeInternalEnergy(this.getEnergyConsumeRate());
            }

            // Running computer generates heat with excess energy it wastes.
            if (!this.isHeatAboveZero() && worldObj.getWorldTime() % 5L == 0L)
            {
                if (!this.isOverheating() && !this.isHeatedPastTriggerValue())
                {
                    // Raise heat randomly from zero to five if we are not at optimal temperature.
                    this.setHeatLevelValue(this.getHeatLevelValue() + worldObj.rand.nextInt(10));
                }
                else if (!this.isOverheating() && this.isHeatedPastTriggerValue() && this.getFluidAmount() > FluidContainerRegistry.BUCKET_VOLUME)
                {
                    // Computer has reached operating temperature and has water to keep it cool.
                    this.setHeatLevelValue(this.getHeatLevelValue() + worldObj.rand.nextInt(2));
                }
                else if (!this.isOverheating() && this.getFluidAmount() <= FluidContainerRegistry.BUCKET_VOLUME)
                {
                    // Computer is running but has no water to keep it cool.
                    this.setHeatLevelValue(this.getHeatLevelValue() + worldObj.rand.nextInt(5));
                }
            }
        }

        // Water acts as coolant to keep running computer components cooled.
        if (!this.isHeatAboveZero() && !this.isFluidTankEmpty() && this.isPowered() && this.isRedstonePowered() && worldObj.getWorldTime() % 16L == 0L)
        {
            if (this.getHeatLevelValue() <= this.getHeatLevelMaximum() && this.isHeatAboveZero())
            {
                // Some of the water evaporates in the process of cooling off the computer.
                // Note: water is drained in amount of heat computer has so hotter it gets
                // the faster it will consume water up to rate of one bucket
                // every few ticks.
                if (this.getFluidAmount() >= this.getHeatLevelValue())
                {
                    // The overall heat levels of the computer drop so long as there is water though.
                    this.removeFluidAmountExact(this.getHeatLevelValue() / 4);
                    this.decreaseHeatValue();
                }
            }
        }
        else if (this.isHeatAboveZero() && worldObj.getWorldTime() % 8L == 0L)
        {
            // Computer will slowly dissipate heat while powered off but nowhere near as fast with coolant.
            this.decreaseHeatValue();
        }
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
        
        // Converts input item into result item along with waste items.
        ItemStack[] currentRecipe = this.getRecipeResult(new MadSlotContainerTypeEnum[]{
                MadSlotContainerTypeEnum.INPUT_INGREDIENT1,
                MadSlotContainerTypeEnum.INPUT_INGREDIENT2,
                MadSlotContainerTypeEnum.INPUT_EXTRA,
                MadSlotContainerTypeEnum.OUTPUT_RESULT1
        });
        
        ItemStack itemOutputSlot2 = currentRecipe[0].copy();

        // Add merged genome to output slot 1.
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1) == null)
        {
            this.setInventorySlotContentsByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1, itemOutputSlot2.copy());
        }
        else if (this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1).isItemEqual(itemOutputSlot2))
        {
            this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1).stackSize += itemOutputSlot2.stackSize;
        }

        // Remove a empty genome data reel from bottom right slot.
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_EXTRA) != null)
        {
            this.decrStackSize(this.getSlotIDByType(MadSlotContainerTypeEnum.INPUT_EXTRA), 1);
        }
    }

    @Override
    public void updateAnimation()
    {
        super.updateAnimation();
        
        // For animation debugging.
        boolean hasChanged = false;
        String currentMainframeState = "OFFLINE";

        if (!isPowered() || !isRedstonePowered())
        {
            currentMainframeState = "OFFLINE";
            this.setTextureRenderedOnModel("models/" + this.getMachineInternalName() + "/idle.png");
        }

        // ACTIVE - Computer is powered and has redstone signal and all required regents to work.
        if (isPowered() && isRedstonePowered() && !isOverheating() && !this.isFluidTankEmpty() && canSmelt())
        {
            currentMainframeState = "ACTIVE";
            if (this.getAnimationCurrentFrame() <= 8 && worldObj.getWorldTime() % 3L == 0L)
            {
                // Load this texture onto the entity.
                this.setTextureRenderedOnModel("models/" + this.getMachineInternalName() + "/work_" + this.getAnimationCurrentFrame() + ".png");
                this.incrementAnimationCurrentFrame();
                hasChanged = true;
            }
            else if (this.getAnimationCurrentFrame() >= 9)
            {
                // Check if we have exceeded the ceiling and need to reset.
                this.setAnimationCurrentFrame(0);
                hasChanged = true;
            }
        }

        // POWERED - Computer is powered and has redstone signal but no regents to work, it still generates heat and consumes water.
        if (isPowered() && isRedstonePowered() && !isOverheating() && !this.isFluidTankEmpty() && !canSmelt())
        {
            currentMainframeState = "POWERED";
            if (worldObj.getWorldTime() % 10L == 0L)
            {
                // Load this texture onto the entity.
                this.setTextureRenderedOnModel("models/" + this.getMachineInternalName() + "/idle_0.png");
                hasChanged = true;
            }
            else
            {
                this.setTextureRenderedOnModel("models/" + this.getMachineInternalName() + "/idle_1.png");
                hasChanged = true;
            }
        }

        // OVERHEATING - Computer is powered and has redstone signal and is above 700 units of internal heat out of 1000!
        if (isPowered() && isRedstonePowered() && isOverheating())
        {
            currentMainframeState = "OVERHEATING";
            if (this.getAnimationCurrentFrame() <= 5 && worldObj.getWorldTime() % 5L == 0L)
            {
                // Load this texture onto the entity.
                this.setTextureRenderedOnModel("models/" + this.getMachineInternalName() + "/warning_" + this.getAnimationCurrentFrame() + ".png");
                this.incrementAnimationCurrentFrame();
                hasChanged = true;
            }
            else if (this.getAnimationCurrentFrame() >= 6)
            {
                // Check if we have exceeded the ceiling and need to reset.
                this.setAnimationCurrentFrame(0);
                hasChanged = true;
            }
        }

        // OUTOFWATER - Computer is powered and has redstone signal and all
        // regents to work but no internal water supply! Overheating is
        // bound to occur!
        if (isPowered() && isRedstonePowered() && !isOverheating() && this.isFluidTankEmpty())
        {
            currentMainframeState = "OUT OF WATER";
            if (this.getAnimationCurrentFrame() <= 4 && worldObj.getWorldTime() % 8L == 0L)
            {
                // Load this texture onto the entity.
                this.setTextureRenderedOnModel("models/" + this.getMachineInternalName() + "/no_water_" + this.getAnimationCurrentFrame() + ".png");
                this.incrementAnimationCurrentFrame();
                hasChanged = true;
            }
            else if (this.getAnimationCurrentFrame() >= 5)
            {
                // Check if we have exceeded the ceiling and need to reset.
                this.setAnimationCurrentFrame(0);
                hasChanged = true;
            }
        }
    }

    @Override
    public void updateEntity()
    {
        // Important to call the class below us!
        super.updateEntity();

        // Updates heat level of the block based on internal tank amount.
        checkHeatLevels();

        // Server side processing for furnace.
        if (!this.worldObj.isRemote)
        {
            // Checks to see if we can add a bucket of water to internal tank.
            this.addBucketToInternalTank();

            // First tick for new item being cooked in furnace.
            if (this.getProgressValue() == 0 && this.canSmelt() && this.isPowered() && this.isRedstonePowered())
            {
                // Calculate length of time it should take to compute these genome combinations.
                ItemStack[] currentRecipe = this.getRecipeResult(new MadSlotContainerTypeEnum[]{
                        MadSlotContainerTypeEnum.INPUT_INGREDIENT1,
                        MadSlotContainerTypeEnum.INPUT_INGREDIENT2,
                        MadSlotContainerTypeEnum.INPUT_EXTRA,
                        MadSlotContainerTypeEnum.OUTPUT_RESULT1
                });
                
                if (currentRecipe == null)
                {
                    // Default value for cooking genome if we have none.
                    this.setProgressMaximum(200);
                }
                else
                {
                    // Defined in MadEntities during mob init.
                    this.setProgressMaximum(66);
                }

                // Increments the timer to kickstart the cooking loop.
                this.incrementProgressValue();
            }
            else if (this.getProgressValue() > 0 && this.canSmelt() && this.isPowered() && this.isRedstonePowered())
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
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
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

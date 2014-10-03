package madscience.tile;

import madapi.container.MadSlotContainerTypeEnum;
import madapi.product.MadTileEntityFactoryProduct;
import madapi.tile.MadTileEntityPrefab;
import madapi.util.MadUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ThermosonicBonderEntity extends MadTileEntityPrefab
{
    public ThermosonicBonderEntity()
    {
        super();
    }
    
    public ThermosonicBonderEntity(MadTileEntityFactoryProduct registeredMachine)
    {
        super(registeredMachine);
    }

    public ThermosonicBonderEntity(String machineName)
    {
        super(machineName);
    }

    /** Returns true if the furnace can smelt an item, i.e. has a source item, destination stack isn't full, etc. */
    @Override
    public boolean canSmelt()
    {
        super.canSmelt();
        
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
        if (!this.isHeatedPastTriggerValue())
        {
            return false;
        }

        // Check if input slots are empty.
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1) == null ||
                this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT2) == null)
        {
            return false;
        }

        // Check if input slot 1 is gold nuggets.
        ItemStack goldNuggetCompareItem = new ItemStack(Item.goldNugget);
        if (!goldNuggetCompareItem.isItemEqual(this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1)))
        {
            return false;
        }

        // Check if input slot 2 is a completed genome data reel.
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT2).isItemDamaged())
        {
            return false;
        }

        // Check if the item inserted to input slot 2 has valid conversion.
        ItemStack recipeResult = this.getRecipeResult(
                MadSlotContainerTypeEnum.INPUT_INGREDIENT1,
                MadSlotContainerTypeEnum.INPUT_INGREDIENT2,
                MadSlotContainerTypeEnum.OUTPUT_RESULT1);
        if (recipeResult == null)
        {
            // Input slot 2 was not a damaged.
            return false;
        }
        
        // Check if output slot 1 is above item stack limit.
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1) != null)
        {
            int slot1Result = this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1).stackSize + recipeResult.stackSize;
            return (slot1Result <= getInventoryStackLimit() && slot1Result <= recipeResult.getMaxStackSize());
        }

        // Check if output slots are empty and ready to be filled with items.
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1) == null)
        {
            return true;
        }

        // Check if item being cooked is same as one in output slot.
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1) != null && recipeResult != null)
        {
            // Check item difference by sub-type since item will always be equal (monster placer).
            if (this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1).isItemEqual(recipeResult) &&
                    this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1).getItemDamage() == recipeResult.getItemDamage())
            {
                // The egg we are producing matches what the cooking recipe says.
                return true;
            }

            // There was a problem comparing item in output slot so we halt.
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
        
        // Output 1 - Transformed mainframe component.
        ItemStack recipeResult = this.getRecipeResult(
                MadSlotContainerTypeEnum.INPUT_INGREDIENT1,
                MadSlotContainerTypeEnum.INPUT_INGREDIENT2,
                MadSlotContainerTypeEnum.OUTPUT_RESULT1);
        
        if (recipeResult == null)
        {
            return;
        }

        // Add transformed mainframe component to output stack.
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1) == null)
        {
            this.setInventorySlotContentsByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1, recipeResult.copy());
        }
        else if (this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1).isItemEqual(recipeResult))
        {
            this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1).stackSize += recipeResult.stackSize;
        }

        // Remove a gold nugget from input slot 1.
        --this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1).stackSize;
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1).stackSize <= 0)
        {
            this.setInventorySlotContentsByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, null);
        }

        // Remove whatever was input slot 2.
        --this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT2).stackSize;
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT2).stackSize <= 0)
        {
            this.setInventorySlotContentsByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT2, null);
        }
    }

    @Override
    public void updateAnimation()
    {
        super.updateAnimation();
        
        if (!isRedstonePowered())
        {
            // Turned off.
            this.setTextureRenderedOnModel("models/" + this.getMachineInternalName() + "/idle.png");
            return;
        }

        if (canSmelt() && isPowered() && this.isHeatedPastTriggerValue() && isRedstonePowered())
        {
            // Working state.
            if (this.getAnimationCurrentFrame() <= 5 && worldObj.getWorldTime() % MadUtils.SECOND_IN_TICKS == 0L)
            {
                // Load this texture onto the entity.
                this.setTextureRenderedOnModel("models/" + this.getMachineInternalName() + "/run_" + this.getAnimationCurrentFrame() + ".png");

                // Update animation frame.
                this.incrementAnimationCurrentFrame();
            }
            else if (this.getAnimationCurrentFrame() >= 6)
            {
                // Check if we have exceeded the ceiling and need to reset.
                this.setAnimationCurrentFrame(0);
            }
            return;
        }

        if (!canSmelt() && isPowered() && !this.isHeatedPastTriggerValue() && isRedstonePowered())
        {
            // Powered up but still very cold, not ready!
            this.setTextureRenderedOnModel("models/" + this.getMachineInternalName() + "/power_" + this.getHeatLevelTimeScaled(6) + ".png");
            return;
        }

        if (isPowered() && this.isHeatedPastTriggerValue() && !canSmelt() && isRedstonePowered())
        {
            // Powered up, heater on. Just nothing inside of me!
            this.setTextureRenderedOnModel("models/" + this.getMachineInternalName() + "/laser_off.png");
            return;
        }
    }

    /** Allows the entity to update its state. */
    @Override
    public void updateEntity()
    {
        // Important to call the class below us!
        super.updateEntity();

        // Remove power from this device if we have some and also have heater enabled.
        if (this.isPowered() && this.isRedstonePowered())
        {
            this.consumeInternalEnergy(this.getEnergyConsumeRate());
        }

        // Add heat to this block if it has met the right conditions.
        if (this.isPowered() && this.isRedstonePowered() && !this.isOverheating())
        {
            this.incrementHeatValue();
        }

        // Remove heat from this object all the time if it has any.
        if (this.isHeatAboveZero() && !this.isRedstonePowered())
        {
            // Does not remove heat constantly but instead every five ticks.
            if (worldObj.getWorldTime() % 5L == 0L)
            {
                this.decreaseHeatValue();
            }
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

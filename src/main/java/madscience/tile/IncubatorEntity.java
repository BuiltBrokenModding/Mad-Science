package madscience.tile;

import madscience.factory.item.prefab.CombinedGenomeMonsterPlacer;
import madscience.factory.item.prefab.ItemGenomeBase;
import madscience.factory.slotcontainers.MadSlotContainerTypeEnum;
import madscience.factory.tileentity.MadTileEntityFactoryProduct;
import madscience.factory.tileentity.prefab.MadTileEntityPrefab;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class IncubatorEntity extends MadTileEntityPrefab implements ISidedInventory
{
    public IncubatorEntity()
    {
        super();
    }

    public IncubatorEntity(MadTileEntityFactoryProduct registeredMachine)
    {
        super(registeredMachine);
    }

    public IncubatorEntity(String machineName)
    {
        super(machineName);
    }

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
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1) == null || this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT2) == null)
        {
            return false;
        }

        // Check if input slot 1 is a fresh egg.
        ItemStack itemsInputSlot1 = new ItemStack(Item.egg);
        if (!itemsInputSlot1.isItemEqual(this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1)))
        {
            return false;
        }

        // Check if input slot 2 is a completed genome data reel.
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT2).isItemDamaged())
        {
            return false;
        }

        // Check if the data reel inserted to input slot 2 has valid conversion.
        ItemStack[] recipeResult = this.getRecipeResult(new MadSlotContainerTypeEnum[]{
                MadSlotContainerTypeEnum.INPUT_INGREDIENT1,
                MadSlotContainerTypeEnum.INPUT_INGREDIENT2,
                MadSlotContainerTypeEnum.OUTPUT_RESULT1});
        
        if (recipeResult == null)
        {
            // Input slot 2 was not a damaged genome data reel.
            return false;
        }

        // Check if output slots are empty and ready to be filled with
        // items.
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1) == null)
        {
            return true;
        }

        // Check if genome being cooked is same as one in output slot.
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1) != null && recipeResult != null)
        {
            // Check item difference by sub-type since item will always be equal (monster placer).
            if (this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1).isItemEqual(recipeResult[0]) &&
                    this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1).getItemDamage() == recipeResult[0].getItemDamage())
            {
                // The egg we are producing matches what the genome cooking recipe says.
                return true;
            }

            // There was a problem comparing genome to egg in output slot so we halt.
            return false;
        }

        // Check if output slot 1 is above item stack limit.
        int slot2Result = this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1).stackSize + itemsInputSlot1.stackSize;
        return (slot2Result <= getInventoryStackLimit() && slot2Result <= itemsInputSlot1.getMaxStackSize());
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
        
        if (slot == 1)
        {
            // Check if we are a genome data reel.
            if (items != null && items.getItem() instanceof ItemGenomeBase)
            {
                return true;
            }

            // Check if we are a combined genome (monster from mainframe).
            if (items != null && items.getItem() instanceof CombinedGenomeMonsterPlacer)
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
        super.smeltItem();
        
        // Output 1 - Encoded mob egg from genome and fresh egg.
        ItemStack[] recipeResult = this.getRecipeResult(new MadSlotContainerTypeEnum[]{
                MadSlotContainerTypeEnum.INPUT_INGREDIENT1,
                MadSlotContainerTypeEnum.INPUT_INGREDIENT2,
                MadSlotContainerTypeEnum.OUTPUT_RESULT1});

        if (recipeResult == null)
        {
            return;
        }

        // Add encoded mob egg to output slot 1.
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1) == null)
        {
            this.setInventorySlotContentsByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1, recipeResult[0].copy());
        }
        else if (this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1).isItemEqual(recipeResult[0]))
        {
            this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1).stackSize += recipeResult[0].stackSize;
        }

        // Remove a fresh egg from input stack 1.
        this.decrStackSize(this.getSlotIDByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT2), 1);
    }

    @Override
    public void updateAnimation()
    {
        super.updateAnimation();
        
        // Main state is when all four requirements have been met to cook items.
        if (canSmelt() && isPowered() && this.isHeatedPastTriggerValue() && isRedstonePowered())
        {
            if (this.getAnimationCurrentFrame() <= 4 && worldObj.getWorldTime() % 5L == 0L)
            {
                // Load this texture onto the entity.
                this.setTextureRenderedOnModel("models/" + this.getMachineInternalName() + "/work_" + this.getAnimationCurrentFrame() + ".png");

                // Update animation frame.
                this.incrementAnimationCurrentFrame();
            }
            else if (this.getAnimationCurrentFrame() >= 5)
            {
                // Check if we have exceeded the ceiling and need to reset.
                this.setAnimationCurrentFrame(0);
            }
        }
        else if (!canSmelt() && isPowered() && !this.isHeatedPastTriggerValue() && !this.isRedstonePowered())
        {
            // Powered up but still very cold, not ready!
            this.setTextureRenderedOnModel("models/" + this.getMachineInternalName() + "/powered.png");
        }
        else if (isPowered() && this.isHeatedPastTriggerValue() && !this.canSmelt() && this.isRedstonePowered())
        {
            // Powered up, heater on. Just nothing inside of me!
            this.setTextureRenderedOnModel("models/" + this.getMachineInternalName() + "/ready.png");
        }
        else if (!isRedstonePowered())
        {
            // Turned off.
            this.setTextureRenderedOnModel("models/" + this.getMachineInternalName() + "/idle.png");
        }
    }

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
        if (this.getHeatLevelValue() > 0)
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
                // New item pulled from cooking stack to be processed, check how
                // long this item will take to cook.
                //this.setProgressMaximum(2600);
                this.setProgressMaximum(42);

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

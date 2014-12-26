package madscience;

import madscience.container.SlotContainerTypeEnum;
import madscience.factory.ItemFactory;
import madscience.mod.ModLoader;
import madscience.product.TileEntityFactoryProduct;
import madscience.tile.TileEntityPrefab;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class GenomeSequencer extends TileEntityPrefab
{
    public GenomeSequencer()
    {
        super();
    }

    public GenomeSequencer(TileEntityFactoryProduct registeredMachine)
    {
        super(registeredMachine);
    }

    public GenomeSequencer(String machineName)
    {
        super(machineName);
    }

    @Override
    public boolean canSmelt()
    {
        super.canSmelt();
        
        if (this.getStackInSlotByType(SlotContainerTypeEnum.INPUT_INGREDIENT1) == null ||
                this.getStackInSlotByType(SlotContainerTypeEnum.INPUT_INGREDIENT2) == null)
        {
            return false;
        }
        
        // Check if input slot 1 is a DNA sample.
        ItemStack recipeResult = this.getRecipeResult(
                SlotContainerTypeEnum.INPUT_INGREDIENT1,
                SlotContainerTypeEnum.INPUT_INGREDIENT2,
                SlotContainerTypeEnum.OUTPUT_RESULT1);
        
        if (recipeResult == null)
        {
            // Input slot 1 was not a DNA sample.
            return false;
        }

        // Check if input slot 2 is a empty genome data reel or damaged.
        if (this.getStackInSlotByType(SlotContainerTypeEnum.INPUT_INGREDIENT2).isItemDamaged())
        {
            // Check if the data reel inserted to input slot 2 has recipe.
            ItemStack slot2SmeltResult = this.getRecipeResult(
                    SlotContainerTypeEnum.INPUT_INGREDIENT1,
                    SlotContainerTypeEnum.INPUT_INGREDIENT2,
                    SlotContainerTypeEnum.OUTPUT_RESULT1);
            if (slot2SmeltResult == null)
            {
                // Input slot 2 was not a damaged genome data reel.
                return false;
            }

            // Check if the DNA sample matches the genome type it is healing.
            if (recipeResult.itemID != this.getStackInSlotByType(SlotContainerTypeEnum.INPUT_INGREDIENT2).itemID)
            {
                return false;
            }
        }
        else
        {
            // Item not damaged so check if it is an empty data reel.
            ItemStack emptyDataReel = ItemFactory.instance().getItemStackByFullyQualifiedName("components", "DataReelEmpty", 1);
            if (!emptyDataReel.isItemEqual(this.getStackInSlotByType(SlotContainerTypeEnum.INPUT_INGREDIENT2)))
            {
                return false;
            }
        }

        // Check if output slots are empty and ready to be filled with items.
        if (this.getStackInSlotByType(SlotContainerTypeEnum.OUTPUT_RESULT1) == null)
        {
            return true;
        }

        // Check if input slot 2 matches output slot 1.
        if (this.getStackInSlotByType(SlotContainerTypeEnum.INPUT_INGREDIENT2).isItemEqual(this.getStackInSlotByType(SlotContainerTypeEnum.OUTPUT_RESULT1)))
        {
            return false;
        }

        // Check if output slot 1 (for DNA samples) is above item stack limit.
        int slot2Result = this.getStackInSlotByType(SlotContainerTypeEnum.OUTPUT_RESULT1).stackSize + recipeResult.stackSize;
        return (slot2Result <= getInventoryStackLimit() && slot2Result <= recipeResult.getMaxStackSize());
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
        
        // Output 1 - Encoded genome data reel that used to be empty.
        ItemStack craftedItem = this.getRecipeResult(
                SlotContainerTypeEnum.INPUT_INGREDIENT1,
                SlotContainerTypeEnum.INPUT_INGREDIENT2,
                SlotContainerTypeEnum.OUTPUT_RESULT1);

        // Check if we should damage the genome (new), or increase health by eating DNA samples.
        if (craftedItem != null && this.getStackInSlotByType(SlotContainerTypeEnum.INPUT_INGREDIENT2) != null &&
                this.getStackInSlotByType(SlotContainerTypeEnum.INPUT_INGREDIENT2).isItemDamaged() &&
                this.getStackInSlotByType(SlotContainerTypeEnum.OUTPUT_RESULT1) == null)
        {
            // Check of the genome is damaged and needs more samples to complete it.
            if (this.getStackInSlotByType(SlotContainerTypeEnum.INPUT_INGREDIENT2).isItemDamaged())
            {
                int currentGenomeStatus = this.getStackInSlotByType(SlotContainerTypeEnum.INPUT_INGREDIENT2).getItemDamage();
                this.getStackInSlotByType(SlotContainerTypeEnum.INPUT_INGREDIENT2).setItemDamage(--currentGenomeStatus);

                // Debug message about data reel health as it is healed by the server.
                ModLoader.log().info("WORLD(" + this.getStackInSlotByType(SlotContainerTypeEnum.INPUT_INGREDIENT2).getUnlocalizedName() + "): " + this.getStackInSlotByType(SlotContainerTypeEnum.INPUT_INGREDIENT2).getItemDamage());
            }

            // Check if the genome was healed completely in this last pass and if so complete it.
            if (this.getStackInSlotByType(SlotContainerTypeEnum.OUTPUT_RESULT1) == null && !this.getStackInSlotByType(SlotContainerTypeEnum.INPUT_INGREDIENT2).isItemDamaged())
            {
                this.setInventorySlotContentsByType(SlotContainerTypeEnum.OUTPUT_RESULT1, this.getStackInSlotByType(SlotContainerTypeEnum.INPUT_INGREDIENT2));

                // Remove healed data reel from input stack 2.
                this.decrStackSize(this.getSlotIDByType(SlotContainerTypeEnum.INPUT_INGREDIENT2), 1);
            }

            // Remove a DNA sample from input stack 1.
            this.decrStackSize(this.getSlotIDByType(SlotContainerTypeEnum.INPUT_INGREDIENT1), 1);

            // We leave this function since we don't want the rest to execute just in case.
            return;
        }

        if (craftedItem != null && this.getStackInSlotByType(SlotContainerTypeEnum.INPUT_INGREDIENT2) != null &&
                this.getStackInSlotByType(SlotContainerTypeEnum.OUTPUT_RESULT1) == null)
        {
            // New genomes that are fresh get set to maximum damage.
            craftedItem.setItemDamage(craftedItem.getMaxDamage());

            // Add encoded genome data reel to output slot 1.
            if (this.getStackInSlotByType(SlotContainerTypeEnum.OUTPUT_RESULT1) == null)
            {
                this.setInventorySlotContentsByType(SlotContainerTypeEnum.OUTPUT_RESULT1, craftedItem.copy());
            }
            else if (this.getStackInSlotByType(SlotContainerTypeEnum.OUTPUT_RESULT1).isItemEqual(craftedItem))
            {
                this.getStackInSlotByType(SlotContainerTypeEnum.OUTPUT_RESULT1).stackSize += craftedItem.stackSize;
            }
            
            // Remove empty data reel from input stack 2.
            this.decrStackSize(this.getSlotIDByType(SlotContainerTypeEnum.INPUT_INGREDIENT2), 1);

            // Remove a DNA sample from input stack 1.
            this.decrStackSize(this.getSlotIDByType(SlotContainerTypeEnum.INPUT_INGREDIENT1), 1);
        }
    }

    @Override
    public void updateAnimation()
    {
        super.updateAnimation();
        
        // Active state has many textures based on item cook progress.
        if (canSmelt())
        {
            if (this.getAnimationCurrentFrame() <= 9 && worldObj.getWorldTime() % 15L == 0L)
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
        else
        {
            // Idle state single texture.
            this.setTextureRenderedOnModel("models/" + this.getMachineInternalName() + "/idle.png");
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
                // New item pulled from cooking stack to be processed, check how
                // long this item will take to cook.
                this.setProgressMaximum(200);

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

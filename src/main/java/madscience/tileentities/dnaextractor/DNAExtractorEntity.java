package madscience.tileentities.dnaextractor;

import madscience.MadConfig;
import madscience.MadDNA;
import madscience.MadFluids;
import madscience.MadFurnaces;
import madscience.MadNeedles;
import madscience.MadScience;
import madscience.factory.MadTileEntityFactoryProduct;
import madscience.factory.slotcontainers.MadSlotContainerTypeEnum;
import madscience.factory.tileentity.MadTileEntity;
import madscience.factory.tileentity.MadTileEntityPacketTemplate;
import madscience.items.needles.ItemDecayNeedleBase;
import madscience.items.needles.NeedleMutant;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;

public class DNAExtractorEntity extends MadTileEntity
{
    public DNAExtractorEntity()
    {
        // Required for loading tile entity from NBT (saved game state).
        super();
    }

    public DNAExtractorEntity(String machineName) // NO_UCD (unused code)
    {
        super(machineName);
    }

    public DNAExtractorEntity(MadTileEntityFactoryProduct registeredMachine) // NO_UCD (unused code)
    {
        // Primary instantiation constructor for registered machines.
        super(registeredMachine);
    }

    /** Returns true if the furnace can smelt an item, i.e. has a source item, destination stack isn't full, etc. 
     * @throws Exception */
    @Override
    public boolean canSmelt()
    {
        super.canSmelt();

        // Check if there is an input item at all in the furnace.
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1) == null)
        {
            return false;
        }

        // Check if the item in the input slot will smelt into anything.
        ItemStack recipeResult = this.getRecipeResultFromFilledSlotType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1);

        if (recipeResult == null)
        {
            // Check if we are a mutant DNA needle.
            if (this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1).getItem() instanceof NeedleMutant)
            {
                // Check if there is fluid inside our internal tank.
                if (this.getFluidAmount() < this.getFluidCapacity())
                {
                    return true;
                }
            }

            return false;
        }
        else
        {
            // Check if output slot matches what is being smelted.
            if (recipeResult != null && this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_WASTE) != null)
            {
                // Check if output stack matches what is being smelted.
                if (!(this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_WASTE).itemID == recipeResult.itemID))
                {
                    return false;
                }
            }

            // Check if output slots are empty and ready to be filled with items.
            if (this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_EMPTYBUCKET) == null && this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1) == null)
            {
                return true;
            }

            // Check if input item matches one that is already be output slot 2.
            if (this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_EMPTYBUCKET) != null && recipeResult != null && !this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_EMPTYBUCKET).isItemEqual(recipeResult))
            {
                return false;
            }

            // By default we assume we are full unless proven otherwise.
            boolean outputSlotsFull = true;

            // Check if output slot 1 is above item stack limit.
            if (this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1) != null)
            {
                int slot1Result = this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1).stackSize + recipeResult.stackSize;
                outputSlotsFull = (slot1Result <= getInventoryStackLimit() && slot1Result <= recipeResult.getMaxStackSize());
            }

            // Check if output slot 2 is above item stack limit.
            if (this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_WASTE) != null)
            {
                int slot2Result = this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_WASTE).stackSize + recipeResult.stackSize;
                outputSlotsFull = (slot2Result <= getInventoryStackLimit() && slot2Result <= recipeResult.getMaxStackSize());
            }

            return outputSlotsFull;
        }
    }

    private int getItemBurnTime(ItemStack itemstack)
    {
        // Check if the current item stack null and return zero if so.
        if (itemstack == null)
        {
            MadScience.logger.info("getItemBurnTime() was called with null ItemStack!");
            return 200;
        }

        // Parse the next item in the stack to an item.
        int i = itemstack.getItem().itemID;

        // Get the current damage multiplier of the itemstack.
        int damage = scaleItemDamageToBurnTime(itemstack);

        // Using number of chromosomes as multiplier for cooking time.
        // http://en.wikipedia.org/wiki/List_of_organisms_by_chromosome_count

        // Needle of Mutant DNA.
        if (itemstack.getItem() instanceof NeedleMutant)
        {
            damage += 666;
        }

        // Needle of Chicken DNA.
        if (i == MadDNA.DNA_CHICKEN.itemID)
        {
            damage += 780;
        }

        // Needle of Cow DNA.
        if (i == MadDNA.DNA_COW.itemID)
        {
            damage += 600;
        }

        // Needle of Creeper DNA.
        if (i == MadDNA.DNA_CREEPER.itemID)
        {
            damage += 200;
        }

        // Needle of Pig DNA.
        if (i == MadDNA.DNA_PIG.itemID)
        {
            damage += 380;
        }

        // Needle of Spider DNA.
        if (i == MadDNA.DNA_SPIDER.itemID)
        {
            damage += 140;
        }

        // Needle of Villager DNA.
        if (i == MadDNA.DNA_VILLAGER.itemID)
        {
            damage += 460;
        }

        // Default response is to always return zero.
        return damage;
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack items)
    {
        // Input Slot 1 - Genetic material we can get DNA samples from.
        if (slot == this.getSlotIDByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1))
        {
            // Check if we are a mutant DNA needle.
            if (items.getItem() instanceof NeedleMutant)
            {
                return true;
            }

            // Check if we are a valid recipe for this device.
            return this.isItemUsedInInputRecipes(items);
        }

        // Input Slot 2 - Empty bucket to be filled with liquid mutant DNA.
        if (slot == this.getSlotIDByType(MadSlotContainerTypeEnum.INPUT_EMPTYBUCKET))
        {
            ItemStack compareOutputSlot = new ItemStack(Item.bucketEmpty);
            if (compareOutputSlot.isItemEqual(items))
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

    private boolean removeMutantDNAFromInternalTank()
    {
        // Check if the input slot for filled buckets is null.
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_EMPTYBUCKET) == null)
        {
            return false;
        }

        // Items we will use to compare with in our input slots.
        ItemStack emptyBucket = new ItemStack(Item.bucketEmpty);
        ItemStack liquidDNABucket = new ItemStack(MadFluids.LIQUIDDNA_MUTANT_BUCKET_ITEM);

        // Check if input slot 2 is a empty bucket.
        if (!this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_EMPTYBUCKET).isItemEqual(emptyBucket))
        {
            return false;
        }

        // Check if output slot 2 (for filled buckets) is above item stack limit.
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_EMPTYBUCKET) != null)
        {
            int slot1Result = this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_EMPTYBUCKET).stackSize + liquidDNABucket.stackSize;
            boolean underStackLimit = (slot1Result <= getInventoryStackLimit() && slot1Result <= liquidDNABucket.getMaxStackSize());
            if (!underStackLimit)
            {
                return false;
            }
        }

        // Check if we actually have some fluid to give out.
        if (this.getFluidAmount() <= 0)
        {
            return false;
        }

        // Add a filled mutant DNA bucket to output slot 3.
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_FILLEDBUCKET) == null)
        {
            this.setInventorySlotContentsByType(MadSlotContainerTypeEnum.OUTPUT_FILLEDBUCKET, liquidDNABucket.copy());
        }
        else if (this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_FILLEDBUCKET).isItemEqual(liquidDNABucket))
        {
            if (this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_FILLEDBUCKET).stackSize <= this.getInventoryStackLimit())
            {
                this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_FILLEDBUCKET).stackSize += liquidDNABucket.stackSize;
            }
        }

        // Decrease the amount of water in the blocks internal storage.
        if (this.removeFluidAmountByBucket(1))
        {
            // Remove a empty bucket of water.
            --this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_EMPTYBUCKET).stackSize;
            if (this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_EMPTYBUCKET).stackSize <= 0)
            {
                this.setInventorySlotContentsByType(MadSlotContainerTypeEnum.INPUT_EMPTYBUCKET, null);
            }

            return true;
        }

        return false;
    }

    private int scaleItemDamageToBurnTime(ItemStack cookingItems)
    {
        // Get the current amount of damage this item has.
        int currentDamage = cookingItems.getItemDamage();

        // Minecraft durability on items is reversed from what we want here,
        // zero is full health and higher values are damage.
        int multipliedDamage = 200;
        switch (currentDamage)
        {
        case 0:
            // Full health needle.
            multipliedDamage = 150;
            break;
        case 1:
            multipliedDamage = 250;
            break;
        case 2:
            multipliedDamage = 375;
            break;
        case 3:
            multipliedDamage = 420;
            break;
        case 4:
            multipliedDamage = 555;
            break;
        case 5:
            multipliedDamage = 666;
            break;
        case 6:
            multipliedDamage = 720;
            break;
        case 7:
            multipliedDamage = 800;
            break;
        case 8:
            multipliedDamage = 980;
            break;
        case 9:
            multipliedDamage = 1500;
            break;
        case 10:
            // Expired needle.
            multipliedDamage = 2600;
            break;
        }

        // Default response is to return maximum amount of time a needle can
        // cook for.
        return multipliedDamage;
    }

    @Override
    public void smeltItem()
    {
        super.smeltItem();

        // Output 1 - Dirty needle leftover from extracting DNA sample.
        ItemStack itemOutputSlot1 = new ItemStack(MadNeedles.NEEDLE_DIRTY);

        // Output 2 - Extracted DNA sample from needle.
        ItemStack extractedDNASample = this.getRecipeResultFromFilledSlotType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1);

        // Check if we are a mutant DNA needle.
        if (extractedDNASample == null && this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1).getItem() instanceof NeedleMutant)
        {
            // Add a bucket's worth of water into the internal tank.
            this.addFluidAmountByBucket(1);
        }
        else if (extractedDNASample != null)
        {
            // Add extracted DNA sample output slot 2 on GUI.
            if (this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_WASTE) == null)
            {
                this.setInventorySlotContentsByType(MadSlotContainerTypeEnum.OUTPUT_WASTE, extractedDNASample.copy());
            }
            else if (this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_WASTE).isItemEqual(extractedDNASample))
            {
                this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_WASTE).stackSize += extractedDNASample.stackSize;
            }
        }

        // Check if we are working with a filled needle or not.
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1).getItem() instanceof ItemDecayNeedleBase)
        {
            // Add dirty needle to output slot 1 on GUI.
            if (this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1) == null)
            {
                this.setInventorySlotContentsByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1, itemOutputSlot1.copy());
            }
            else if (this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1).isItemEqual(itemOutputSlot1) && this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1).stackSize <= this.getInventoryStackLimit())
            {
                this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1).stackSize += itemOutputSlot1.stackSize;
            }
        }

        // Remove one of the input items from the GUI.
        --this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1).stackSize;
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1).stackSize <= 0)
        {
            this.setInventorySlotContentsByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, null);
        }
    }

    /** Update current texture that should be displayed based on our status. */
    @Override
    public void updateAnimation()
    {
        super.updateAnimation();

        // Active state has many textures based on item cook progress.
        if (this.canSmelt() && this.isPowered())
        {
            if (this.getAnimationCurrentFrame() <= 11 && worldObj.getWorldTime() % 25L == 0L)
            {
                // Load this texture onto the entity.
                this.setEntityTexture("models/" + this.getMachineInternalName() + "/work_" + this.getAnimationCurrentFrame() + ".png");

                // Update animation frame.
                this.setAnimationCurrentFrame(this.getAnimationCurrentFrame() + 1);
            }
            else if (this.getAnimationCurrentFrame() >= 12)
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
            this.consumeEnergy(MadConfig.DNAEXTRACTOR_CONSUME);
        }

        // Server side processing for furnace.
        if (!this.worldObj.isRemote)
        {
            // Attempt to remove fluid from internal tank if we can.
            this.removeMutantDNAFromInternalTank();

            // Change texture on block based on state.
            this.updateAnimation();

            // Update sound based on state.
            this.updateSound();

            // First tick for new item being cooked in furnace.
            if (this.getProgressValue() == 0 && this.canSmelt() && this.isPowered())
            {
                // New item pulled from cooking stack to be processed, check how long this item will take to cook.
                this.setProgressMaximum(getItemBurnTime(this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1)));

                // Increments the timer to kickstart the cooking loop.
                this.setProgressValue(this.getProgressValue() + 1);
            }
            else if (this.getProgressValue() > 0 && this.canSmelt() && this.isPowered())
            {
                // Increments the timer to kickstart the cooking loop.
                this.setProgressValue(this.getProgressValue() + 1);

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

            // Send update to clients that require it.
            this.sendUpdatePacket();
        }

        if (inventoriesChanged)
        {
            this.onInventoryChanged();
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
}

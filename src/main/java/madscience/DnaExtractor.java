package madscience;

import madscience.container.SlotContainerTypeEnum;
import madscience.factory.FluidFactory;
import madscience.factory.ItemFactory;
import madscience.mod.ModLoader;
import madscience.product.FluidFactoryProduct;
import madscience.product.TileEntityFactoryProduct;
import madscience.tile.TileEntityPrefab;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class DnaExtractor extends TileEntityPrefab // NO_UCD (unused code)
{
    public DnaExtractor()
    {
        // Required for loading tile entity from NBT (saved game state).
        super();
    }

    public DnaExtractor(String machineName) // NO_UCD (unused code)
    {
        super(machineName);
    }

    public DnaExtractor(TileEntityFactoryProduct registeredMachine) // NO_UCD (unused code)
    {
        // Primary instantiation constructor for registered machines.
        super(registeredMachine);
    }

    /** Returns true if the furnace can smelt an item, i.e. has a source item, destination stack isn't full, etc.
     * 
     * @throws Exception */
    @Override
    public boolean canSmelt()
    {
        super.canSmelt();

        // Check if there is an input item at all in the furnace.
        if (this.getStackInSlotByType(SlotContainerTypeEnum.INPUT_INGREDIENT1) == null)
        {
            return false;
        }

        // Check if the item in the input slot will smelt into anything.
        ItemStack recipeResult = this.getRecipeResult(SlotContainerTypeEnum.INPUT_INGREDIENT1, SlotContainerTypeEnum.OUTPUT_RESULT1);

        if (recipeResult == null)
        {
            // Check if we are a mutant DNA needle.
            ItemStack needleMutantCompare = ItemFactory.instance().getItemStackByFullyQualifiedName("needle", "mutant", 1);
            if (this.getStackInSlotByType(SlotContainerTypeEnum.INPUT_INGREDIENT1).getItem().equals(needleMutantCompare.getItem()))
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
            if (recipeResult != null && this.getStackInSlotByType(SlotContainerTypeEnum.OUTPUT_WASTE) != null)
            {
                // Check if output stack matches what is being smelted.
                if (!(this.getStackInSlotByType(SlotContainerTypeEnum.OUTPUT_WASTE).itemID == recipeResult.itemID))
                {
                    return false;
                }
            }

            // Check if output slots are empty and ready to be filled with items.
            if (this.getStackInSlotByType(SlotContainerTypeEnum.INPUT_EMPTYBUCKET) == null && this.getStackInSlotByType(SlotContainerTypeEnum.OUTPUT_RESULT1) == null)
            {
                return true;
            }

            // Check if input item matches one that is already be output slot 2.
            if (this.getStackInSlotByType(SlotContainerTypeEnum.INPUT_EMPTYBUCKET) != null && recipeResult != null && !this.getStackInSlotByType(SlotContainerTypeEnum.INPUT_EMPTYBUCKET).isItemEqual(recipeResult))
            {
                return false;
            }

            // By default we assume we are full unless proven otherwise.
            boolean outputSlotsFull = true;

            // Check if output slot 1 is above item stack limit.
            if (this.getStackInSlotByType(SlotContainerTypeEnum.OUTPUT_RESULT1) != null)
            {
                int slot1Result = this.getStackInSlotByType(SlotContainerTypeEnum.OUTPUT_RESULT1).stackSize + recipeResult.stackSize;
                outputSlotsFull = (slot1Result <= getInventoryStackLimit() && slot1Result <= recipeResult.getMaxStackSize());
            }

            // Check if output slot 2 is above item stack limit.
            if (this.getStackInSlotByType(SlotContainerTypeEnum.OUTPUT_WASTE) != null)
            {
                int slot2Result = this.getStackInSlotByType(SlotContainerTypeEnum.OUTPUT_WASTE).stackSize + recipeResult.stackSize;
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
            ModLoader.log().info("getItemBurnTime() was called with null ItemStack!");
            return 200;
        }

        // Get the current damage multiplier of the itemstack.
        int damage = scaleItemDamageToBurnTime(itemstack);

        // Using number of chromosomes as multiplier for cooking time.
        // http://en.wikipedia.org/wiki/List_of_organisms_by_chromosome_count

        // Needle of Mutant DNA.
        ItemStack needleMutantCompare = ItemFactory.instance().getItemStackByFullyQualifiedName("needle", "mutant", 1);
        if (needleMutantCompare.getItem().equals(itemstack.getItem()))
        {
            damage += 666;
        }
        
        // Needle of Chicken DNA.
        Item chickenItem = ItemFactory.instance().getItemStackByFullyQualifiedName("needle", "chicken", 1).getItem();
        if (itemstack.getItem().equals(chickenItem))
        {
            damage += 780;
        }

        // Needle of Cow DNA.
        Item cowItem = ItemFactory.instance().getItemStackByFullyQualifiedName("needle", "cow", 1).getItem();
        if (itemstack.getItem().equals(cowItem))
        {
            damage += 600;
        }

        // Needle of Creeper DNA.
        Item creeperItem = ItemFactory.instance().getItemStackByFullyQualifiedName("needle", "creeper", 1).getItem();
        if (itemstack.getItem().equals(creeperItem))
        {
            damage += 200;
        }

        // Needle of Pig DNA.
        Item pigItem = ItemFactory.instance().getItemStackByFullyQualifiedName("needle", "pig", 1).getItem();
        if (itemstack.getItem().equals(pigItem))
        {
            damage += 380;
        }

        // Needle of Spider DNA.
        Item spiderItem = ItemFactory.instance().getItemStackByFullyQualifiedName("needle", "spider", 1).getItem();
        if (itemstack.getItem().equals(spiderItem))
        {
            damage += 140;
        }

        // Needle of Villager DNA.
        Item villagerItem = ItemFactory.instance().getItemStackByFullyQualifiedName("needle", "villager", 1).getItem();
        if (itemstack.getItem().equals(villagerItem))
        {
            damage += 460;
        }

        // Default response is to always return zero.
        return damage;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
    }

    private boolean removeMutantDNAFromInternalTank()
    {
        // Check if the input slot for filled buckets is null.
        if (this.getStackInSlotByType(SlotContainerTypeEnum.INPUT_EMPTYBUCKET) == null)
        {
            return false;
        }

        // Items we will use to compare with in our input slots.
        ItemStack emptyBucket = new ItemStack(Item.bucketEmpty);
        
        FluidFactoryProduct fluidProduct = FluidFactory.instance().getFluidInfo("maddnamutant");
        ItemStack liquidDNABucket = new ItemStack(fluidProduct.getFluidContainer());

        // Check if input slot 2 is a empty bucket.
        if (!this.getStackInSlotByType(SlotContainerTypeEnum.INPUT_EMPTYBUCKET).isItemEqual(emptyBucket))
        {
            return false;
        }

        // Check if output slot 2 (for filled buckets) is above item stack limit.
        if (this.getStackInSlotByType(SlotContainerTypeEnum.INPUT_EMPTYBUCKET) != null)
        {
            int slot1Result = this.getStackInSlotByType(SlotContainerTypeEnum.INPUT_EMPTYBUCKET).stackSize + liquidDNABucket.stackSize;
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
        if (this.getStackInSlotByType(SlotContainerTypeEnum.OUTPUT_FILLEDBUCKET) == null)
        {
            this.setInventorySlotContentsByType(SlotContainerTypeEnum.OUTPUT_FILLEDBUCKET, liquidDNABucket.copy());
        }
        else if (this.getStackInSlotByType(SlotContainerTypeEnum.OUTPUT_FILLEDBUCKET).isItemEqual(liquidDNABucket))
        {
            if (this.getStackInSlotByType(SlotContainerTypeEnum.OUTPUT_FILLEDBUCKET).stackSize <= this.getInventoryStackLimit())
            {
                this.getStackInSlotByType(SlotContainerTypeEnum.OUTPUT_FILLEDBUCKET).stackSize += liquidDNABucket.stackSize;
            }
        }

        // Decrease the amount of water in the blocks internal storage.
        if (this.removeFluidAmountByBucket(1))
        {
            // Remove a empty bucket of water.
            --this.getStackInSlotByType(SlotContainerTypeEnum.INPUT_EMPTYBUCKET).stackSize;
            if (this.getStackInSlotByType(SlotContainerTypeEnum.INPUT_EMPTYBUCKET).stackSize <= 0)
            {
                this.setInventorySlotContentsByType(SlotContainerTypeEnum.INPUT_EMPTYBUCKET, null);
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
        ItemStack itemDirtyNeedle = ItemFactory.instance().getItemStackByFullyQualifiedName("needle", "dirty", 1);

        // Output 2 - Extracted DNA sample from needle.
        ItemStack extractedDNASample = this.getRecipeResult(SlotContainerTypeEnum.INPUT_INGREDIENT1, SlotContainerTypeEnum.OUTPUT_RESULT1);

        // Check if we are a mutant DNA needle.
        ItemStack mutantNeedleCompare = ItemFactory.instance().getItemStackByFullyQualifiedName("needle", "mutant", 1);
        if (extractedDNASample == null && this.getStackInSlotByType(SlotContainerTypeEnum.INPUT_INGREDIENT1).getItem().equals(mutantNeedleCompare.getItem()))
        {
            // Add a bucket's worth of water into the internal tank.
            this.addFluidAmountByBucket(1);
        }
        else if (extractedDNASample != null)
        {
            // Add extracted DNA sample output slot 2 on GUI.
            if (this.getStackInSlotByType(SlotContainerTypeEnum.OUTPUT_WASTE) == null)
            {
                this.setInventorySlotContentsByType(SlotContainerTypeEnum.OUTPUT_WASTE, extractedDNASample.copy());
            }
            else if (this.getStackInSlotByType(SlotContainerTypeEnum.OUTPUT_WASTE).isItemEqual(extractedDNASample))
            {
                this.getStackInSlotByType(SlotContainerTypeEnum.OUTPUT_WASTE).stackSize += extractedDNASample.stackSize;
            }
        }

        // Check if we are working with a filled needle or not.
        if (ItemFactory.instance().isItemInstanceOfRegisteredBaseType(this.getStackInSlotByType(SlotContainerTypeEnum.INPUT_INGREDIENT1).getItem(), "needle"))
        {
            // Add dirty needle to output slot 1 on GUI.
            if (this.getStackInSlotByType(SlotContainerTypeEnum.OUTPUT_RESULT1) == null)
            {
                this.setInventorySlotContentsByType(SlotContainerTypeEnum.OUTPUT_RESULT1, itemDirtyNeedle.copy());
            }
            else if (this.getStackInSlotByType(SlotContainerTypeEnum.OUTPUT_RESULT1).isItemEqual(itemDirtyNeedle) && this.getStackInSlotByType(SlotContainerTypeEnum.OUTPUT_RESULT1).stackSize <= this.getInventoryStackLimit())
            {
                this.getStackInSlotByType(SlotContainerTypeEnum.OUTPUT_RESULT1).stackSize += itemDirtyNeedle.stackSize;
            }
        }

        // Remove one of the input items from the GUI.
        --this.getStackInSlotByType(SlotContainerTypeEnum.INPUT_INGREDIENT1).stackSize;
        if (this.getStackInSlotByType(SlotContainerTypeEnum.INPUT_INGREDIENT1).stackSize <= 0)
        {
            this.setInventorySlotContentsByType(SlotContainerTypeEnum.INPUT_INGREDIENT1, null);
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
                this.setTextureRenderedOnModel("models/" + this.getMachineInternalName() + "/work_" + this.getAnimationCurrentFrame() + ".png");

                // Update animation frame.
                this.incrementAnimationCurrentFrame();
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
            // Attempt to remove fluid from internal tank if we can.
            this.removeMutantDNAFromInternalTank();

            // First tick for new item being cooked in furnace.
            if (this.getProgressValue() == 0 && this.canSmelt() && this.isPowered())
            {
                // New item pulled from cooking stack to be processed, check how long this item will take to cook.
                this.setProgressMaximum(getItemBurnTime(this.getStackInSlotByType(SlotContainerTypeEnum.INPUT_INGREDIENT1)));

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

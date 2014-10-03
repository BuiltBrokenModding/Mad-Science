package madscience.logic;

import madapi.MadItemFactory;
import madapi.container.MadSlotContainerTypeEnum;
import madapi.product.MadItemFactoryProduct;
import madapi.product.MadTileEntityFactoryProduct;
import madapi.tile.MadTileEntityPrefab;
import madapi.util.MadUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class CryotubeEntity extends MadTileEntityPrefab
{
    /** Keeps track of what state we are supposed to be in. */
    private boolean subjectIsAlive = false;
    
    public CryotubeEntity()
    {
        super();
    }

    public CryotubeEntity(MadTileEntityFactoryProduct registeredMachine)
    {
        super(registeredMachine);
    }

    public CryotubeEntity(String machineName)
    {
        super(machineName);
    }

    @Override
    public boolean canSmelt()
    {
        super.canSmelt();
        
        // Check if we have water bucket and dirty needles in input slots and that our internal tank has fluid.
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1) == null || this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT2) == null)
        {
            return false;
        }

        // Check if output slots are empty.
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1) == null)
        {
            return true;
        }

        // Check if input slot 2 matches output slot 1.
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT2).isItemEqual(this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1)))
        {
            return false;
        }

        // Check if we are full of rotten flesh.
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_WASTE) != null &&
            this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_WASTE).stackSize >= this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_WASTE).getMaxStackSize())
        {
            return false;
        }

        boolean slot1Overlimit = false;
        boolean slot2Overlimit = false;
        // Check if output slot 1 is over stack limit.
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1) != null)
        {
            int slot1Result = this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1).stackSize;
            slot1Overlimit = (slot1Result <= getInventoryStackLimit() && slot1Result <= this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1).getMaxStackSize());
        }
    
        // Check if output slot 2 is over stack limit.
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_WASTE) != null)
        {
            int slot2Result = this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_WASTE).stackSize;
            slot2Overlimit = (slot2Result <= getInventoryStackLimit() && slot2Result <= this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_WASTE).getMaxStackSize());
        }

        // If either slot is over we return false.
        if (slot1Overlimit || slot2Overlimit)
        {
            return false;
        }

        return true;
    }

    private void convertEmptyReelToMemory()
    {
        // Check if input slot 2 is empty data reel.
        ItemStack emptyDataReel = MadItemFactory.instance().getItemStackByFullyQualifiedName("components", "DataReelEmpty", 1);
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT2).getItem().equals(emptyDataReel.getItem()))
        {
            // Create memory based on ceiling of neural activity.
            MadItemFactoryProduct memoryItemInfo = MadItemFactory.instance().getItemInfo("memory");
            ItemStack createdMemory = new ItemStack(memoryItemInfo.getItem(), 1, this.getHeatLevelMaximum());

            // Add encoded memory data reel to output slot 1.
            if (this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1) == null)
            {
                this.setInventorySlotContentsByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1, createdMemory.copy());
            }
            else if (this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1).isItemEqual(createdMemory))
            {
                this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1).stackSize += createdMemory.stackSize;
            }

            // Remove a empty data reel from input stack 2.
            this.decrStackSize(this.getSlotIDByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT2), 1);
        }
    }

    private ItemStack createRandomVillagerMemory()
    {
        // Randomly picks a number from zero to five to find a villager profession type.
        int someProfessionType = this.worldObj.rand.nextInt(5);

        switch (someProfessionType)
        {
        case 0:
            // Priest
            return MadItemFactory.instance().getItemStackByFullyQualifiedName("memory", "Priest", 1);
        case 1:
            // Farmer
            return MadItemFactory.instance().getItemStackByFullyQualifiedName("memory", "Farmer", 1);
        case 2:
            // Butcher
            return MadItemFactory.instance().getItemStackByFullyQualifiedName("memory", "Butcher", 1);
        case 3:
            // Blacksmith
            return MadItemFactory.instance().getItemStackByFullyQualifiedName("memory", "Blacksmith", 1);
        case 4:
            // Librarian
            return MadItemFactory.instance().getItemStackByFullyQualifiedName("memory", "Librarian", 1);
        case 5:
            // Weakest one again...
            return MadItemFactory.instance().getItemStackByFullyQualifiedName("memory", "Priest", 1);
        default:
            // Weakest one catch-all...
            return MadItemFactory.instance().getItemStackByFullyQualifiedName("memory", "Priest", 1);

        }
    }

    /** Places a rotten flesh in the output slot for that item. */
    private void createRottenFlesh(int howManyRange)
    {
        // Add some rotten flesh into the output slot for it.
        ItemStack rottenFlesh = new ItemStack(Item.rottenFlesh, this.worldObj.rand.nextInt(howManyRange));
        if (rottenFlesh != null)
        {
            // Stop if we are full of rotten flesh.
            if (this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_WASTE) != null &&
                this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_WASTE).stackSize >= this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_WASTE).getMaxStackSize())
            {
                return;
            }

            if (this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_WASTE) == null)
            {
                this.setInventorySlotContentsByType(MadSlotContainerTypeEnum.OUTPUT_WASTE, rottenFlesh.copy());
            }
            else if (this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_WASTE).isItemEqual(rottenFlesh))
            {
                this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_WASTE).stackSize += rottenFlesh.stackSize;
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);

        // Determine if we have subject already spawned.
        this.subjectIsAlive = nbt.getBoolean("SubjectAlive");
    }
    
    private void resetCryotube()
    {
        // Change out state to officially being deceased.
        this.subjectIsAlive = false;

        // Subject died, reset chamber!
        this.setProgressValue(0);
        this.setProgressMaximum(200);

        this.setHeatLevelValue(0);
        this.setHeatLevelMaximum(0);

        this.setDamageMaximum(100);
        this.setDamageValue(0);
    }
    
    @Override
    public void updateAnimation()
    {
        super.updateAnimation();
        
        if (!isRedstonePowered())
        {
            // Cryotube is disabled and offline.
            this.setTextureRenderedOnModel("models/" + this.getMachineInternalName() + "/off.png");
            return;
        }

        // Dead or full cryotube (full when rotten flesh reaches stack limit).
        if (!canSmelt() && isRedstonePowered() && this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_WASTE) != null &&
            this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_WASTE).stackSize >= this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_WASTE).getMaxStackSize())
        {
            if (this.getAnimationCurrentFrame() <= 1 && worldObj.getWorldTime() % 15L == 0L)
            {
                // Load this texture onto the entity.
                this.setTextureRenderedOnModel("models/" + this.getMachineInternalName() + "/dead_" + this.getAnimationCurrentFrame() + ".png");

                // Update animation frame.
                this.incrementAnimationCurrentFrame();
            }
            else if (this.getAnimationCurrentFrame() >= 2)
            {
                // Check if we have exceeded the ceiling and need to reset.
                this.setAnimationCurrentFrame(0);
            }
            return;
        }

        // Powered and running status.
        if (canSmelt() && isRedstonePowered())
        {
            if (this.getAnimationCurrentFrame() <= 6 && worldObj.getWorldTime() % 15L == 0L)
            {
                // Load this texture onto the entity.
                this.setTextureRenderedOnModel("models/" + this.getMachineInternalName() + "/alive_" + this.getAnimationCurrentFrame() + ".png");

                // Update animation frame.
                this.incrementAnimationCurrentFrame();
            }
            else if (this.getAnimationCurrentFrame() >= 7)
            {
                // Check if we have exceeded the ceiling and need to reset.
                this.setAnimationCurrentFrame(0);
            }
            return;
        }

        if (!canSmelt() && isRedstonePowered())
        {
            // Cryotube is powered but has no items inside of it.
            this.setTextureRenderedOnModel("models/" + this.getMachineInternalName() + "/on.png");
            return;
        }
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();

        // Attempt to let other machines draw power from our internal reserves.
        this.produce();

        // Server side processing for furnace.
        if (!this.worldObj.isRemote)
        {
            // Check if there is to much rotten flesh in the machine to operate it.
            if (this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_WASTE) != null &&
                this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_WASTE).stackSize >= this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_WASTE).getMaxStackSize())
            {
                this.resetCryotube();
                return;
            }

            // Animation for block.
            this.updateAnimation();

            // Disable the tube and kill the subject if he is inside during power outage.
            if (!isRedstonePowered() && this.subjectIsAlive || !isRedstonePowered() && this.getProgressValue() > 0)
            {
                // Cleans out the tube.
                this.resetCryotube();

                // Adds rotten flesh to output slot 2.
                this.createRottenFlesh(5);
            }

            // Giant nested if-statement determines what machine should be doing at any given time.
            if (this.getProgressValue() <= 0 && this.getProgressValue() <= this.getProgressMaximum() && this.canSmelt() && this.isRedstonePowered() && !this.subjectIsAlive)
            {
                // --------------------------
                // TUBE EMPTY, START HATCHING
                // --------------------------

                // Remove a spawn egg from input stack 1 to begin the hatching process.
                this.decrStackSize(this.getSlotIDByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1), 1);

                // Length of time it will take to hatch a spawn egg.
                //this.setProgressMaximum(2600);
                this.setProgressMaximum(600);

                // Increments the timer to kickstart the cooking loop.
                this.incrementProgressValue();
            }
            else if (this.getProgressValue() > 0 && this.getProgressValue() < this.getProgressMaximum() && this.canSmelt() && this.isRedstonePowered() && !this.subjectIsAlive)
            {
                // Increments the timer to keep hatching process going!
                this.incrementProgressValue();
            }
            else if (this.getProgressValue() >= this.getProgressMaximum() && this.canSmelt() && this.isRedstonePowered() && !this.subjectIsAlive)
            {
                // ----------------------------------------
                // HATCHING COMPLETE, DECIDE IF STILL-BIRTH
                // ----------------------------------------

                // Decide if this egg failed to grow into the tube correctly.
                if (this.worldObj.rand.nextBoolean() && this.worldObj.rand.nextInt(100) < 42)
                {
                    // Failed to hatch this egg, try again!
                    this.setProgressValue(0);
                    this.subjectIsAlive = false;

                    // We don't make as much flesh he because it was not fully grown.
                    this.createRottenFlesh(2);
                }
                else
                {
                    // Hatched egg without error!
                    this.setProgressValue(this.getProgressMaximum());

                    // We flip our state to officially having a subject alive inside of it.
                    this.subjectIsAlive = true;

                    // Grab some comparison items from item factory.
                    ItemStack emptyDataReel = MadItemFactory.instance().getItemStackByFullyQualifiedName("components", "DataReelEmpty", 1);
                    
                    
                    // Check if we have empty data reel or existing memory to work with for generating neural activity.
                    int ceilingReel = 100;                    
                    if (this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT2).getItem().equals(emptyDataReel.getItem()))
                    {
                        // Create a random memory for our villager.
                        ceilingReel = createRandomVillagerMemory().getItemDamage();
                    }
                    else if (MadItemFactory.instance().isItemInstanceOfRegisteredBaseType(this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT2).getItem() ,"memory"))
                    {
                        // Damage value of memory item is the ceiling for neural activity.
                        ceilingReel = this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT2).getItemDamage();
                    }

                    // Setup health and maximum ceiling for neural activity based on above statement.
                    this.setDamageMaximum(100);
                    this.setDamageValue(this.getDamageMaximum());

                    // Setup neural activity, making ceiling the actual one so player knows they are at sub-optimal performance.
                    this.setHeatLevelMaximum(ceilingReel);
                    this.setHeatLevelValue(ceilingReel);
                }
            }
            else if (this.getProgressValue() == this.getProgressMaximum() && this.canSmelt() && this.isRedstonePowered() && this.subjectIsAlive && this.getDamageValue() > 0)
            {
                // -------------
                // SUBJECT ALIVE
                // -------------

                // Happens with or without nether star installed into cryotube.
                if (worldObj.getWorldTime() % MadUtils.SECOND_IN_TICKS == 0L)
                {
                    this.updateNeuralActivity();
                    
                    this.playSoundByName("Work");

                    // Check if we have nether star along with all other required regents to generate power.
                    ItemStack compareNetherStar = new ItemStack(Item.netherStar);
                    if (!this.energy.isFull() && this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_EXTRA) != null && this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_EXTRA).isItemEqual(compareNetherStar))
                    {
                        // Generate electrical power.
                        Long amtRecieved = Long.valueOf(this.getHeatLevelValue()) * this.getEnergyProduceRate();
                        this.produceEnergy(amtRecieved);
                    }
                }

                // Remove health because still alive.
                if (worldObj.getWorldTime() % MadUtils.SECOND_IN_TICKS * 5 == 0L)
                {
                    this.decreaseDamageValue();
                }
            }
            else if (this.getProgressValue() == this.getProgressMaximum() && this.canSmelt() && this.isRedstonePowered() && this.subjectIsAlive && this.getDamageValue() <= 0)
            {
                // ------------
                // SUBJECT DEAD
                // ------------

                // STILL BIRTH
                this.playSoundByName("Stillbirth");

                // Takes empty data reel and creates proper variant based on neural activity ceiling.
                this.convertEmptyReelToMemory();
                this.resetCryotube();

                // Adds rotten flesh to output slot 2.
                this.createRottenFlesh(5);
            }
        }
    }

    /** Update amount of neural activity the villager inside the tube is experiencing. */
    private void updateNeuralActivity()
    {
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT2) == null)
        {
            this.setHeatLevelValue(this.worldObj.rand.nextInt(32));
            return;
        }

        if (this.worldObj == null)
        {
            this.setHeatLevelValue(this.worldObj.rand.nextInt(32));
            return;
        }

        if (this.worldObj.rand == null)
        {
            this.setHeatLevelValue(this.worldObj.rand.nextInt(32));
            return;
        }

        // Use meta-item damage value of neural activity ceiling.
        this.setHeatLevelValue(this.worldObj.rand.nextInt(this.getHeatLevelMaximum()));
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);

        // Determine if we have subject already spawned.
        nbt.setBoolean("SubjectAlive", this.subjectIsAlive);
    }

    @Override
    public void smeltItem()
    {
        super.smeltItem();
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

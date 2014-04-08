package madscience.tileentities.mainframe;

import java.util.Random;

import madscience.MadConfig;
import madscience.MadEntities;
import madscience.MadFurnaces;
import madscience.MadScience;
import madscience.tileentities.mainframe.MainframeRecipes.GenomeRecipe;
import madscience.tileentities.prefab.MadTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MainframeEntity extends MadTileEntity implements ISidedInventory, IFluidHandler
{
    private static final int[] slots_top = new int[]
    { 0 };

    private static final int[] slots_bottom = new int[]
    { 2, 1 };
    private static final int[] slots_sides = new int[]
    { 1 };
    /** The ItemStacks that hold the items currently being used in the furnace */
    private ItemStack[] mainframeOutput = new ItemStack[2];

    private ItemStack[] mainframeInput = new ItemStack[4];

    /** The number of ticks that a fresh copy of the currently-burning item would keep the furnace burning for */
    public int currentItemCookingMaximum;

    /** The number of ticks that the current item has been cooking for */
    public int currentItemCookingValue;

    /** Current level of heat that the machine has accumulated while powered and active. */
    public int currentHeatValue;

    /** Maximum allowed heat value and also when the machine is considered ready. */
    public int currentHeatMaximum = 1000;

    // ** Maximum number of buckets of water this machine can hold internally */
    public static int MAX_WATER = FluidContainerRegistry.BUCKET_VOLUME * 10;

    /** Internal reserve of water */
    protected FluidTank internalWaterTank = new FluidTank(FluidRegistry.WATER, 0, MAX_WATER);

    // Random number generator for creating heat.
    private Random randomNumberGenny = new Random();

    /** Determines if we currently should be playing animation frames every tick or not. */
    public boolean shouldPlay;

    /** Current frame of animation we should use to display in world. */
    public int animFrame;

    /** Currently assigned texture that our renderer will reference */
    public ResourceLocation mainframeTexture;

    /** Currently assigned texture path which is saved to NBT. */
    public String mainframeTexturePath = "models/" + MadFurnaces.MAINFRAME_INTERNALNAME + "/off.png";

    /** Determines if debugging information is shown for this object. */
    public boolean showDebuggingInfo = false;

    public MainframeEntity()
    {
        super(MadConfig.MAINFRAME_CAPACTITY, MadConfig.MAINFRAME_INPUT);
    }

    private void addBucketToInternalTank()
    {
        // Check if the input slot for filled buckets is null.
        if (this.getInputSlot_WaterBucket() == null)
        {
            return;
        }

        // Output 1 - Empty bucket returns from full one in input slot 1
        ItemStack bucketEmpty = new ItemStack(Item.bucketEmpty);
        ItemStack bucketWater = new ItemStack(Item.bucketWater);

        // Check if input slot 1 is a water bucket.
        if (!this.getInputSlot_WaterBucket().isItemEqual(bucketWater))
        {
            return;
        }

        // Check if output slot 1 (for empty buckets) is above item stack limit.
        if (this.getOutputSlot_EmptyBucket() != null)
        {
            int slot1Result = this.getOutputSlot_EmptyBucket().stackSize + bucketEmpty.stackSize;
            boolean underStackLimit = (slot1Result <= getInventoryStackLimit() && slot1Result <= bucketEmpty.getMaxStackSize());
            if (!underStackLimit)
            {
                // If we are not under the minecraft or item stack limit for
                // output slot 2 then stop here.
                return;
            }
        }

        // Check if the internal water tank has reached it
        if (internalWaterTank.getFluidAmount() >= internalWaterTank.getCapacity())
        {
            return;
        }

        // Add empty water bucket to output slot 2.
        if (this.getOutputSlot_EmptyBucket() == null)
        {
            this.setOutputSlot_EmptyBucket(bucketEmpty.copy());
        }
        else if (this.getOutputSlot_EmptyBucket().isItemEqual(bucketEmpty))
        {
            mainframeOutput[1].stackSize += bucketEmpty.stackSize;
        }

        // Add a bucket's worth of water into the internal tank.
        internalWaterTank.fill(new FluidStack(FluidRegistry.WATER, FluidContainerRegistry.BUCKET_VOLUME), true);
        MadScience.logger.info("internalWaterTank() " + internalWaterTank.getFluidAmount());

        // Remove a filled bucket of water from input stack 1.
        this.DecreaseInputSlot(0, 1);
        this.setInputSlot_WaterBucket(null);
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid)
    {
        // Not allowed to remove water that has been placed inside this block.
        return false;
    }

    /** Returns true if automation can extract the given item in the given slot from the given side. Args: Slot, item, side */
    @Override
    public boolean canExtractItem(int slot, ItemStack items, int side)
    {
        // Extract output from the bottom of the block.
        if (slot == 3 && ForgeDirection.getOrientation(side) == ForgeDirection.WEST)
        {
            // Empty water bucket.
            return true;
        }
        else if (slot == 4 && ForgeDirection.getOrientation(side) == ForgeDirection.WEST)
        {
            // Clean needle.
            return true;
        }

        // Default response is no.
        return false;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid)
    {
        // This block can receive water on any side to store in internal tank.
        if (FluidRegistry.WATER == fluid)
        {
            return true;
        }

        // By default we say no to any other fluids.
        return false;
    }

    /** Returns true if automation can insert the given item in the given slot from the given side. Args: Slot, item, side */
    @Override
    public boolean canInsertItem(int slot, ItemStack items, int side)
    {
        return this.isItemValidForSlot(slot, items);
    }

    /** Returns true if the furnace can smelt an item, i.e. has a source item, destination stack isn't full, etc. */
    public boolean canSmelt()
    {
        // Check if we have all the genome slots filled and internal tank is not
        // null.
        if (this.getInputSlot_GenomeReel1() == null || this.getInputSlot_GenomeReel2() == null || this.getInputSlot_GenomeReelEmpty() == null || internalWaterTank == null)
        {
            return false;
        }
        // Check to make sure we are not overheating, this stops the
        // computer from working.
        if (this.isOverheating())
        {
            return false;
        }

        // Check for empty reel inside input slot 4.
        ItemStack genomeSlotCompare = new ItemStack(MadEntities.DATAREEL_EMPTY);
        if (!genomeSlotCompare.isItemEqual(this.getInputSlot_GenomeReelEmpty()))
        {
            return false;
        }

        // Check if input slots 2 and 3 which should be genome data reels
        // are compatible with each other.
        GenomeRecipe currentRecipe = MainframeRecipes.findGenomeRecipe(this.getInputSlot_GenomeReel1(), this.getInputSlot_GenomeReel2());
        if (currentRecipe == null)
        {
            return false;
        }

        // Check if there is fluid inside our internal tank for cleaning the
        // needles.
        if (internalWaterTank.getFluidAmount() <= 0)
        {
            return false;
        }

        // Check if output slots are empty and ready to be filled with
        // items.
        if (this.getOutputSlot_GenomeMerged() == null)
        {
            return true;
        }

        // Check if output is above stack limit.
        int slot2Result = this.getOutputSlot_GenomeMerged().stackSize + genomeSlotCompare.stackSize;
        return (slot2Result <= getInventoryStackLimit() && slot2Result <= genomeSlotCompare.getMaxStackSize());
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
                this.consumeEnergy(MadConfig.MAINFRAME_CONSUME);
            }

            // Running computer generates heat with excess energy it wastes.
            if (!this.isHeated() && worldObj.getWorldTime() % 5L == 0L)
            {
                if (this.getHeatAmount() <= this.getMaxHeatAmount() && !this.isOptimalHeatAmount())
                {
                    // Raise heat randomly from zero to five if we are not at
                    // optimal temperature.
                    this.setHeatLevel(currentHeatValue += randomNumberGenny.nextInt(10));
                }
                else if (this.getHeatAmount() <= this.getMaxHeatAmount() && this.isOptimalHeatAmount() && internalWaterTank != null && internalWaterTank.getFluidAmount() > FluidContainerRegistry.BUCKET_VOLUME)
                {
                    // Computer has reached operating temperature and has water
                    // to keep it cool.
                    this.setHeatLevel(currentHeatValue += randomNumberGenny.nextInt(2));
                }
                else if (this.getHeatAmount() <= this.getMaxHeatAmount() && internalWaterTank != null && internalWaterTank.getFluidAmount() <= FluidContainerRegistry.BUCKET_VOLUME)
                {
                    // Computer is running but has no water to keep it cool.
                    this.setHeatLevel(currentHeatValue += randomNumberGenny.nextInt(5));
                }
            }
        }

        // Water acts as coolant to keep running computer components cooled.
        if (!this.isHeated() && internalWaterTank != null && internalWaterTank.getFluidAmount() > 0 && worldObj.getWorldTime() % 16L == 0L && this.isPowered() && this.isRedstonePowered())
        {
            if (this.getHeatAmount() <= this.getMaxHeatAmount() && this.getHeatAmount() > 0)
            {
                // Some of the water evaporates in the process of cooling off
                // the computer.
                // Note: water is drained in amount of heat computer has so
                // hotter it gets
                // the faster it will consume water up to rate of one bucket
                // every few ticks.
                if (internalWaterTank.getFluidAmount() >= this.getHeatAmount())
                {
                    // The overall heat levels of the computer drop so long as
                    // there is water though.
                    internalWaterTank.drain((int) (this.getHeatAmount() / 4), true);
                    this.setHeatLevel(--this.currentHeatValue);
                }
            }
        }
        else if (worldObj.getWorldTime() % 8L == 0L && this.getHeatAmount() > 0)
        {
            // Computer will slowly dissipate heat while powered off but nowhere
            // near as fast with coolant.
            this.setHeatLevel(--this.currentHeatValue);
        }
    }

    @Override
    public void closeChest()
    {
    }

    private ItemStack DecreaseInputSlot(int slot, int numItems)
    {
        // Removes items from input slots.
        if (this.mainframeInput[slot] != null)
        {
            ItemStack itemstack;

            if (this.mainframeInput[slot].stackSize <= numItems)
            {
                itemstack = this.mainframeInput[slot];
                this.mainframeInput[slot] = null;
                return itemstack;
            }
            itemstack = this.mainframeInput[slot].splitStack(numItems);

            if (this.mainframeInput[slot].stackSize == 0)
            {
                this.mainframeInput[slot] = null;
            }

            return itemstack;
        }
        return null;
    }

    private ItemStack DecreaseOutputSlot(int slot, int numItems)
    {
        // Removes items from either output slots.
        if (this.mainframeOutput[slot] != null)
        {
            ItemStack itemstack;

            if (this.mainframeOutput[slot].stackSize <= numItems)
            {
                itemstack = this.mainframeOutput[slot];
                this.mainframeOutput[slot] = null;
                return itemstack;
            }
            itemstack = this.mainframeOutput[slot].splitStack(numItems);

            if (this.mainframeOutput[slot].stackSize == 0)
            {
                this.mainframeOutput[slot] = null;
            }

            return itemstack;
        }
        return null;
    }

    /** Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a new stack. */
    @Override
    public ItemStack decrStackSize(int slot, int numItems)
    {
        if (slot == 0)
        {
            // INPUT SLOT 1 - WATER BUCKET
            return DecreaseInputSlot(0, numItems);
        }
        else if (slot == 1)
        {
            // INPUT SLOT 2 - GENOME REEL 1
            return DecreaseInputSlot(1, numItems);
        }
        else if (slot == 2)
        {
            // INPUT SLOT 3 - GENOME REEL 2
            return DecreaseInputSlot(2, numItems);
        }
        else if (slot == 3)
        {
            // INPUT SLOT 4 - GENOME REEL EMPTY
            return DecreaseInputSlot(3, numItems);
        }
        else if (slot == 4)
        {
            // OUTPUT SLOT 1 - MERGED GENOME REEL
            return DecreaseOutputSlot(0, numItems);
        }
        else if (slot == 5)
        {
            // OUTPUT SLOT 2 - EMPTY BUCKET
            return DecreaseOutputSlot(1, numItems);
        }

        // Something bad has occurred!
        MadScience.logger.info("decrStackSize() could not return " + numItems + " stack items from slot " + slot);
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain)
    {
        // Not allowed to remove water that has been placed inside this block.
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
    {
        // Not allowed to remove water that has been placed inside this block.
        return null;
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
    {
        // Check if fluid being sent is indeed water for the needle cleaner.
        if (resource != null)
        {
            return internalWaterTank.fill(resource, doFill);
        }

        // Default response is to not fill anything if it doesn't match a
        // requirement.
        return 0;
    }

    /** Returns an array containing the indices of the slots that can be accessed by automation on the given side of this block. */
    @Override
    public int[] getAccessibleSlotsFromSide(int par1)
    {
        return par1 == 0 ? slots_bottom : (par1 == 1 ? slots_top : slots_sides);
    }

    public float getHeatAmount()
    {
        // Returns current level of heat stored inside of the machine.
        return currentHeatValue;
    }

    public int getHeatLevelTimeScaled(int pxl)
    {
        // Returns scaled percentage of heat level used in GUI to show
        // temperature.
        return (int) (this.getHeatAmount() * (pxl / this.getMaxHeatAmount()));
    }

    public ItemStack getInputSlot_GenomeReel1()
    {
        if (mainframeInput != null && mainframeInput[1] != null)
        {
            return mainframeInput[1];
        }

        return null;
    }

    public ItemStack getInputSlot_GenomeReel2()
    {
        if (mainframeInput != null && mainframeInput[2] != null)
        {
            return mainframeInput[2];
        }

        return null;
    }

    public ItemStack getInputSlot_GenomeReelEmpty()
    {
        if (mainframeInput != null && mainframeInput[3] != null)
        {
            return mainframeInput[3];
        }

        return null;
    }

    public ItemStack getInputSlot_WaterBucket()
    {
        if (mainframeInput != null && mainframeInput[0] != null)
        {
            return mainframeInput[0];
        }

        return null;
    }

    /** Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't this more of a set than a get?* */
    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    /** Returns the name of the inventory. */
    @Override
    public String getInvName()
    {
        return MadFurnaces.MAINFRAME_INTERNALNAME;
    }

    @SideOnly(Side.CLIENT)
    /**
     * Returns an integer between 0 and the passed value representing how close the current item is to being completely
     * cooked
     */
    public int getItemCookTimeScaled(int prgPixels)
    {
        // Prevent divide by zero exception by setting ceiling.
        if (currentItemCookingMaximum == 0)
        {
            // MadScience.logger.info("CLIENT: getItemCookTimeScaled() was called with currentItemCookingMaximum being zero!");
            currentItemCookingMaximum = 200;
        }

        return (currentItemCookingValue * prgPixels) / currentItemCookingMaximum;
    }

    public float getMaxHeatAmount()
    {
        // Returns maximum amount of heat allowed and also operating
        // temperature.
        return currentHeatMaximum;
    }

    public ItemStack getOutputSlot_EmptyBucket()
    {
        if (mainframeOutput != null && mainframeOutput[1] != null)
        {
            return mainframeOutput[1];
        }

        return null;
    }

    public ItemStack getOutputSlot_GenomeMerged()
    {
        if (mainframeOutput != null && mainframeOutput[0] != null)
        {
            return mainframeOutput[0];
        }

        return null;
    }

    public int getSizeInputInventory()
    {
        return this.mainframeInput.length;
    }

    @Override
    @Deprecated
    public int getSizeInventory()
    {
        // We make use of other methods to reference the multiple hash tables.
        return 0;
    }

    public int getSizeOutputInventory()
    {
        return mainframeOutput.length;
    }

    /** Returns the stack in designated slot. */
    @Override
    public ItemStack getStackInSlot(int slot)
    {
        if (slot == 0)
        {
            // Input slot 1
            return this.getInputSlot_WaterBucket();
        }
        else if (slot == 1)
        {
            // Input slot 2
            return this.getInputSlot_GenomeReel1();
        }
        else if (slot == 2)
        {
            // Input slot 3
            return this.getInputSlot_GenomeReel2();
        }
        else if (slot == 3)
        {
            // Input slot 3
            return this.getInputSlot_GenomeReelEmpty();
        }
        else if (slot == 4)
        {
            // Output slot 1
            return this.getOutputSlot_GenomeMerged();
        }
        else if (slot == 5)
        {
            // Output slot 2
            return this.getOutputSlot_EmptyBucket();
        }

        MadScience.logger.info("getStackInSlot() could not return valid stack from slot " + slot);
        return null;
    }

    /** When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem - like when you close a workbench */
    @Override
    public ItemStack getStackInSlotOnClosing(int slot)
    {
        // Input slots.
        if (slot == 0)
        {
            // Input slot 1 - Water bucket.
            if (this.getInputSlot_WaterBucket() != null)
            {
                ItemStack itemstack = this.getInputSlot_WaterBucket();
                this.setInputSlot_WaterBucket(null);
                return itemstack;
            }
            return null;
        }
        else if (slot == 1)
        {
            // Input slot 2 - Genome Reel 1
            if (this.getInputSlot_GenomeReel1() != null)
            {
                ItemStack itemstack = this.getInputSlot_GenomeReel1();
                this.setInputSlot_GenomeReel1(null);
                return itemstack;
            }
            return null;
        }
        else if (slot == 2)
        {
            // Input slot 3 - Genome Reel 2
            if (this.getInputSlot_GenomeReel2() != null)
            {
                ItemStack itemstack = this.getInputSlot_GenomeReel2();
                this.setInputSlot_GenomeReel2(null);
                return itemstack;
            }
            return null;
        }
        else if (slot == 3)
        {
            // Input slot 4 - Genome Empty Reel
            if (this.getInputSlot_GenomeReelEmpty() != null)
            {
                ItemStack itemstack = this.getInputSlot_GenomeReelEmpty();
                this.setInputSlot_GenomeReelEmpty(null);
                return itemstack;
            }
            return null;
        }
        else if (slot == 4)
        {
            // Output slot 1 - Merged Genome Data Reel
            if (this.getOutputSlot_GenomeMerged() != null)
            {
                ItemStack itemstack = this.getOutputSlot_GenomeMerged();
                this.setOutputSlot_GenomeMerged(null);
                return itemstack;
            }
            return null;
        }
        else if (slot == 5)
        {
            // Output slot 2 - Empty bucket.
            if (this.getOutputSlot_EmptyBucket() != null)
            {
                ItemStack itemstack = this.getOutputSlot_EmptyBucket();
                this.setOutputSlot_EmptyBucket(null);
                return itemstack;
            }
            return null;
        }

        return null;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from)
    {
        return new FluidTankInfo[]
        { internalWaterTank.getInfo() };
    }

    @SideOnly(Side.CLIENT)
    public int getWaterRemainingScaled(int i)
    {
        return internalWaterTank.getFluid() != null ? (int) (((float) internalWaterTank.getFluid().amount / (float) (MAX_WATER)) * i) : 0;
    }

    private boolean isHeated()
    {
        // Returns true if the heater has reached it's optimal temperature.
        return this.getHeatAmount() >= this.getMaxHeatAmount();
    }

    @Override
    public boolean isInvNameLocalized()
    {
        return true;
    }

    /** Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot. */
    @Override
    public boolean isItemValidForSlot(int slot, ItemStack items)
    {
        // Check if machine trying to insert item into given slot is allowed.
        if (slot == 0)
        {
            // Input slot 1 - water bucket.
            ItemStack waterBucket = new ItemStack(Item.bucketWater);
            if (waterBucket.isItemEqual(items))
            {
                return true;
            }
        }
        else if (slot == 3)
        {
            // Input slot 4 - genome reel empty
            ItemStack genomeEmpty = new ItemStack(MadEntities.DATAREEL_EMPTY);
            if (genomeEmpty.isItemEqual(items))
            {
                return true;
            }
        }

        return false;
    }

    private boolean isOptimalHeatAmount()
    {
        // Check if heat levels are at optimal range for operations.
        if (this.getHeatAmount() >= 420)
        {
            return true;
        }

        // Default response is that we are not warm enough to incubate anything.
        return false;
    }

    public boolean isOutOfWater()
    {
        if (internalWaterTank == null)
        {
            return false;
        }

        if (this.internalWaterTank.getFluidAmount() < FluidContainerRegistry.BUCKET_VOLUME)
        {
            return true;
        }

        return false;
    }

    public boolean isOverheating()
    {
        // Check if heat levels are at proper values for cooking.
        // Note: 780 is approximate number when heater fills line on GUI with
        // flame.
        if (this.getHeatAmount() > 780)
        {
            return true;
        }

        // Default response is that we are not warm enough to incubate anything.
        return false;
    }

    /** Do not make give this method the name canInteractWith because it clashes with Container */
    @Override
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
    {
        return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public void openChest()
    {
    }

    /** Reads a tile entity from NBT. */
    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);

        // Read input/output items from world save data.
        NBTTagList nbtInput = nbt.getTagList("InputItems");
        NBTTagList nbtOutput = nbt.getTagList("OutputItems");

        // Cast the save data onto our running object.
        this.mainframeInput = new ItemStack[this.getSizeInputInventory()];
        this.mainframeOutput = new ItemStack[this.getSizeOutputInventory()];

        // Input items process.
        for (int i = 0; i < nbtInput.tagCount(); ++i)
        {
            NBTTagCompound inputSaveData = (NBTTagCompound) nbtInput.tagAt(i);
            byte b0 = inputSaveData.getByte("InputSlot");

            if (b0 >= 0 && b0 < this.mainframeInput.length)
            {
                this.mainframeInput[b0] = ItemStack.loadItemStackFromNBT(inputSaveData);
            }
        }

        // Output items process.
        for (int i = 0; i < nbtOutput.tagCount(); ++i)
        {
            NBTTagCompound outputSaveData = (NBTTagCompound) nbtOutput.tagAt(i);
            byte b0 = outputSaveData.getByte("OutputSlot");

            if (b0 >= 0 && b0 < this.mainframeOutput.length)
            {
                this.mainframeOutput[b0] = ItemStack.loadItemStackFromNBT(outputSaveData);
            }
        }

        // Current amount of time job has to completion.
        this.currentItemCookingValue = nbt.getShort("CookTime");

        // Internal water amount inside of the device.
        this.internalWaterTank.setFluid(new FluidStack(FluidRegistry.WATER, nbt.getShort("WaterAmount")));

        // Amount of heat that was stored inside of this block.
        this.currentHeatValue = nbt.getShort("HeatLevel");

        // Should play animation status.
        this.shouldPlay = nbt.getBoolean("ShouldPlay");

        // Current frame of animation we are displaying.
        this.animFrame = nbt.getInteger("CurrentFrame");

        // Path to current texture what should be loaded onto the model.
        this.mainframeTexturePath = nbt.getString("TexturePath");
    }

    private void setHeatLevel(int amount)
    {
        // Changes block internal temperature to new value.
        if (this.currentHeatValue != amount)
        {
            this.currentHeatValue = amount;
        }
    }

    private void setInputSlot_GenomeReel1(ItemStack inputItem)
    {
        if (mainframeInput != null)
        {
            mainframeInput[1] = inputItem;
        }
    }

    private void setInputSlot_GenomeReel2(ItemStack inputItem)
    {
        if (mainframeInput != null)
        {
            mainframeInput[2] = inputItem;
        }
    }

    private void setInputSlot_GenomeReelEmpty(ItemStack inputItem)
    {
        if (mainframeInput != null)
        {
            mainframeInput[3] = inputItem;
        }
    }

    private void setInputSlot_WaterBucket(ItemStack inputItem)
    {
        if (mainframeInput != null)
        {
            mainframeInput[0] = inputItem;
        }
    }

    /** Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections). */
    @Override
    public void setInventorySlotContents(int slot, ItemStack items)
    {
        if (slot == 0)
        {
            // INPUT SLOT 1 - WATER BUCKET
            this.setInputSlot_WaterBucket(items);
        }
        else if (slot == 1)
        {
            // INPUT SLOT 2 - GENOME REEL 1
            this.setInputSlot_GenomeReel1(items);
        }
        else if (slot == 2)
        {
            // INPUT SLOT 3 - GENOME REEL 2
            this.setInputSlot_GenomeReel2(items);
        }
        else if (slot == 3)
        {
            // INPUT SLOT 4 - GENOME REEL EMPTY
            this.setInputSlot_GenomeReelEmpty(items);
        }
        else if (slot == 4)
        {
            // OUTPUT SLOT 1 - COMBINED GENOME REEL
            this.setOutputSlot_GenomeMerged(items);
        }
        else if (slot == 5)
        {
            // OUTPUT SLOT 2 - EMPTY BUCKET
            this.setOutputSlot_EmptyBucket(items);
        }

        if (items != null && items.stackSize > this.getInventoryStackLimit())
        {
            items.stackSize = this.getInventoryStackLimit();
        }
    }

    private void setOutputSlot_EmptyBucket(ItemStack outputItem)
    {
        if (mainframeOutput != null)
        {
            mainframeOutput[1] = outputItem;
        }
    }

    private void setOutputSlot_GenomeMerged(ItemStack outputItem)
    {
        if (mainframeOutput != null)
        {
            mainframeOutput[0] = outputItem;
        }
    }

    public void smeltItem()
    {
        // Converts input item into result item along with waste items.
        GenomeRecipe currentRecipe = MainframeRecipes.findGenomeRecipe(this.getInputSlot_GenomeReel1(), this.getInputSlot_GenomeReel2());
        ItemStack itemOutputSlot2 = currentRecipe.result.copy();

        // Add merged genome to output slot 1.
        if (this.getOutputSlot_GenomeMerged() == null)
        {
            this.setOutputSlot_GenomeMerged(itemOutputSlot2.copy());
        }
        else if (this.getOutputSlot_GenomeMerged().isItemEqual(itemOutputSlot2))
        {
            mainframeOutput[0].stackSize += itemOutputSlot2.stackSize;
        }

        // Remove a empty genome data reel from bottom right slot.
        if (mainframeInput != null && mainframeInput[3] != null)
        {
            --this.mainframeInput[3].stackSize;
            if (this.mainframeInput[3].stackSize <= 0)
            {
                this.mainframeInput[3] = null;
            }
        }

        // Play sound of finishing genome merger.
        this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, MainframeSounds.MAINFRAME_FINISH, 1.0F, 1.0F);
    }

    /**
     * 
     */
    private void updateAnimation()
    {
        // For animation debugging.
        boolean hasChanged = false;
        String currentMainframeState = "OFFLINE";

        if (!isPowered() || !isRedstonePowered())
        {
            currentMainframeState = "OFFLINE";
            mainframeTexturePath = "models/" + MadFurnaces.MAINFRAME_INTERNALNAME + "/off.png";
        }

        // ACTIVE - Computer is powered and has redstone signal and all required
        // regents to work.
        if (isPowered() && isRedstonePowered() && !isOverheating() && !isOutOfWater() && canSmelt())
        {
            currentMainframeState = "ACTIVE";
            if (animFrame <= 8 && worldObj.getWorldTime() % 3L == 0L)
            {
                // Load this texture onto the entity.
                mainframeTexturePath = "models/" + MadFurnaces.MAINFRAME_INTERNALNAME + "/work_" + animFrame + ".png";
                mainframeTexture = new ResourceLocation(MadScience.ID, mainframeTexturePath);
                animFrame++;
                hasChanged = true;
            }
            else if (animFrame >= 9)
            {
                // Check if we have exceeded the ceiling and need to reset.
                animFrame = 0;
                hasChanged = true;
            }
        }

        // POWERED - Computer is powered and has redstone signal but no
        // regents to work, it still generates heat and consumes water.
        if (isPowered() && isRedstonePowered() && !isOverheating() && !isOutOfWater() && !canSmelt())
        {
            currentMainframeState = "POWERED";
            if (worldObj.getWorldTime() % 10L == 0L)
            {
                // Load this texture onto the entity.
                mainframeTexturePath = "models/" + MadFurnaces.MAINFRAME_INTERNALNAME + "/idle_0.png";
                mainframeTexture = new ResourceLocation(MadScience.ID, mainframeTexturePath);
                hasChanged = true;
            }
            else
            {
                mainframeTexturePath = "models/" + MadFurnaces.MAINFRAME_INTERNALNAME + "/idle_1.png";
                mainframeTexture = new ResourceLocation(MadScience.ID, mainframeTexturePath);
                hasChanged = true;
            }
        }

        // OVERHEATING - Computer is powered and has redstone signal and is
        // above 700 units of internal heat out of 1000!
        if (isPowered() && isRedstonePowered() && isOverheating())
        {
            // Sound of overheating.
            if (worldObj.getWorldTime() % MadScience.SECOND_IN_TICKS * 1.184F == 0L)
            {
                this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, MainframeSounds.MAINFRAME_OVERHEAT, 0.42F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
            }

            currentMainframeState = "OVERHEATING";
            if (animFrame <= 5 && worldObj.getWorldTime() % 5L == 0L)
            {
                // Load this texture onto the entity.
                mainframeTexturePath = "models/" + MadFurnaces.MAINFRAME_INTERNALNAME + "/warning_" + animFrame + ".png";
                mainframeTexture = new ResourceLocation(MadScience.ID, mainframeTexturePath);
                animFrame++;
                hasChanged = true;
            }
            else if (animFrame >= 6)
            {
                // Check if we have exceeded the ceiling and need to reset.
                animFrame = 0;
                hasChanged = true;
            }
        }

        // OUTOFWATER - Computer is powered and has redstone signal and all
        // regents to work but no internal water supply! Overheating is
        // bound to occur!
        if (isPowered() && isRedstonePowered() && !isOverheating() && isOutOfWater())
        {
            currentMainframeState = "OUT OF WATER";
            if (animFrame <= 4 && worldObj.getWorldTime() % 8L == 0L)
            {
                // Load this texture onto the entity.
                mainframeTexturePath = "models/" + MadFurnaces.MAINFRAME_INTERNALNAME + "/no_water_" + animFrame + ".png";
                mainframeTexture = new ResourceLocation(MadScience.ID, mainframeTexturePath);
                animFrame++;
                hasChanged = true;
            }
            else if (animFrame >= 5)
            {
                // Check if we have exceeded the ceiling and need to reset.
                animFrame = 0;
                hasChanged = true;
            }
        }

        // Debugging information about animation state.
        if (hasChanged && showDebuggingInfo)
        {
            MadScience.logger.info("----------------------------[" + String.valueOf(worldObj.getWorldTime()) + "]");
            MadScience.logger.info("TEXTURE: " + mainframeTexturePath);
            MadScience.logger.info("STATE: " + currentMainframeState);
            MadScience.logger.info("POWERED: " + this.isPowered());
            MadScience.logger.info("REDSTONE: " + this.isRedstonePowered());
            MadScience.logger.info("WORKING: " + this.canSmelt());
            MadScience.logger.info("OUTOFWATER: " + this.isOutOfWater());
            MadScience.logger.info("OVERHEATING: " + this.isOverheating());
        }
    }

    /** Allows the entity to update its state. */
    @Override
    public void updateEntity()
    {
        // Important to call the class below us!
        super.updateEntity();

        boolean inventoriesChanged = false;

        // Updates heat level of the block based on internal tank amount.
        checkHeatLevels();

        // Update status of the machine if it has redstone power or not.
        checkRedstonePower();

        // Server side processing for furnace.
        if (!this.worldObj.isRemote)
        {
            // Updates animation texture stored in NBT based on machine state.
            this.updateAnimation();

            // Play sounds based on current state of operation.
            this.updateSound();

            // Checks to see if we can add a bucket of water to internal tank.
            this.addBucketToInternalTank();

            // First tick for new item being cooked in furnace.
            if (this.currentItemCookingValue == 0 && this.canSmelt() && this.isPowered() && this.isRedstonePowered())
            {
                // Calculate length of time it should take to compute these genome combinations.
                GenomeRecipe currentRecipe = MainframeRecipes.findGenomeRecipe(this.getInputSlot_GenomeReel1(), this.getInputSlot_GenomeReel2());
                if (currentRecipe == null)
                {
                    // Default value for cooking genome if we have none.
                    currentItemCookingMaximum = 200;
                }
                else
                {
                    // Defined in MadEntities during mob init.
                    currentItemCookingMaximum = currentRecipe.delay;
                }

                // Play starting work sound.
                this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, MainframeSounds.MAINFRAME_START, 1.0F, 1.0F);

                // Increments the timer to kickstart the cooking loop.
                this.currentItemCookingValue++;
            }
            else if (this.currentItemCookingValue > 0 && this.canSmelt() && this.isPowered() && this.isRedstonePowered())
            {
                // Run on server when we have items and electrical power.
                // Note: This is the main work loop for the block!

                // Increments the timer to kickstart the cooking loop.
                this.currentItemCookingValue++;

                // Check if furnace has exceeded total amount of time to cook.
                if (this.currentItemCookingValue >= currentItemCookingMaximum)
                {
                    // Convert one item into another via 'cooking' process.
                    this.currentItemCookingValue = 0;
                    this.smeltItem();
                    inventoriesChanged = true;
                }
            }
            else
            {
                // Reset loop, prepare for next item or closure.
                this.currentItemCookingValue = 0;
            }

            // Sends relevant information from server to respective clients that require it.
            PacketDispatcher.sendPacketToAllAround(this.xCoord, this.yCoord, this.zCoord, MadConfig.PACKETSEND_RADIUS, worldObj.provider.dimensionId, new MainframePackets(this.xCoord, this.yCoord, this.zCoord, currentItemCookingValue, currentItemCookingMaximum,
                    getEnergy(ForgeDirection.UNKNOWN), getEnergyCapacity(ForgeDirection.UNKNOWN), internalWaterTank.getFluidAmount(), internalWaterTank.getCapacity(), this.currentHeatValue, this.currentHeatMaximum, this.mainframeTexturePath).makePacket());
        }

        if (inventoriesChanged)
        {
            this.onInventoryChanged();
        }
    }

    private void updateSound()
    {
        // Check if we should be playing working sounds.
        if (this.isRedstonePowered() && this.canSmelt() && this.isPowered() && !this.isOutOfWater() && worldObj.getWorldTime() % (MadScience.SECOND_IN_TICKS * 1.2F) == 0L)
        {
            this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, MainframeSounds.MAINFRAME_WORK, 1.0F, 1.0F);
        }

        // Check to see if we should play idle sounds.
        if (this.isRedstonePowered() && this.isPowered() && !this.isOutOfWater() && worldObj.getWorldTime() % (MadScience.SECOND_IN_TICKS * 8.6F) == 0L)
        {
            this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, MainframeSounds.MAINFRAME_IDLE, 0.42F, 1.0F);
        }
    }

    /** Writes a tile entity to NBT. */
    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);

        // Amount of time left to cook current item inside of the furnace.
        nbt.setShort("CookTime", (short) this.currentItemCookingValue);

        // Amount of water that is currently stored inside of the machines
        // internal reserves.
        nbt.setShort("WaterAmount", (short) this.internalWaterTank.getFluidAmount());

        // Amount of heat current stored within the block.
        nbt.setShort("HeatLevel", (short) this.currentHeatValue);

        // Should play animation status.
        nbt.setBoolean("ShouldPlay", this.shouldPlay);

        // Current frame of animation we are displaying.
        nbt.setInteger("CurrentFrame", this.animFrame);

        // Path to current texture that should be loaded onto the model.
        nbt.setString("TexturePath", this.mainframeTexturePath);

        // Two tag lists for each type of items we have in this entity.
        NBTTagList inputItems = new NBTTagList();
        NBTTagList outputItems = new NBTTagList();

        // Input slots.
        for (int i = 0; i < this.mainframeInput.length; ++i)
        {
            if (this.mainframeInput[i] != null)
            {
                NBTTagCompound inputSlots = new NBTTagCompound();
                inputSlots.setByte("InputSlot", (byte) i);
                this.mainframeInput[i].writeToNBT(inputSlots);
                inputItems.appendTag(inputSlots);
            }
        }

        // Output slots.
        for (int i = 0; i < this.mainframeOutput.length; ++i)
        {
            if (this.mainframeOutput[i] != null)
            {
                NBTTagCompound outputSlots = new NBTTagCompound();
                outputSlots.setByte("OutputSlot", (byte) i);
                this.mainframeOutput[i].writeToNBT(outputSlots);
                outputItems.appendTag(outputSlots);
            }
        }

        // Save the input and output items.
        nbt.setTag("InputItems", inputItems);
        nbt.setTag("OutputItems", outputItems);
    }
}

package madscience.tileentities.dnaextractor;

import java.util.ArrayList;
import java.util.Random;

import madscience.MadConfig;
import madscience.MadDNA;
import madscience.MadFluids;
import madscience.MadFurnaces;
import madscience.MadNeedles;
import madscience.MadScience;
import madscience.items.ItemDecayNeedle;
import madscience.items.needles.NeedleMutant;
import madscience.tileentities.prefab.MadContainerInterface;
import madscience.tileentities.prefab.MadTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class DNAExtractorEntity extends MadTileEntity implements IFluidHandler
{    
    /** The number of ticks that a fresh copy of the currently-burning item would keep the furnace burning for */
    int currentItemCookingMaximum;

    /** The number of ticks that the current item has been cooking for */
    int currentItemCookingValue;

    // ** Maximum number of buckets of water this machine can hold internally */
    private int MAX_MUTANTDNA = FluidContainerRegistry.BUCKET_VOLUME * 10;

    /** Internal reserve of water */
    protected FluidTank internalLiquidDNAMutantTank = new FluidTank(MadFluids.LIQUIDDNA_MUTANT, 1, MAX_MUTANTDNA);

    /** Determines if we currently should be playing animation frames every tick or not. */
    private boolean shouldPlay;

    /** Current frame of animation we should use to display in world. */
    private int curFrame;

    /** Path to current texture that should be displayed on our model. */
    String TEXTURE = "models/" + MadFurnaces.DNAEXTRACTOR_INTERNALNAME + "/idle.png";

    public DNAExtractorEntity()
    {
        super(MadConfig.DNAEXTRACTOR_CAPACTITY, MadConfig.DNAEXTRACTOR_INPUT);
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid)
    {
        // DNA extractor can have mutant DNA extracted from it's internal tank.
        if (MadFluids.LIQUIDDNA_MUTANT_BLOCK.blockID == fluid.getBlockID())
        {
            return true;
        }

        return false;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid)
    {
        // DNA Extractor cannot be filled by any fluids.
        return false;
    }

    /** Returns true if the furnace can smelt an item, i.e. has a source item, destination stack isn't full, etc. */
    private boolean canSmelt()
    {
        // Check for internal tank.
        if (internalLiquidDNAMutantTank == null)
        {
            return false;
        }

        // Check if there is an input item at all in the furnace.
        if (this.getStackInSlot(DNAExtractorEnumContainer.InputGeneticMatrial.getSlotNumber()) == null)
        {
            return false;
        }

        // Check if the item in the input slot will smelt into anything.
        ItemStack itemsInputSlot = DNAExtractorRecipes.getSmeltingResult(this.getStackInSlot(DNAExtractorEnumContainer.InputGeneticMatrial.getSlotNumber()));
                
        if (itemsInputSlot == null)
        {
            // Check if we are a mutant DNA needle.
            if (this.getStackInSlot(DNAExtractorEnumContainer.InputGeneticMatrial.getSlotNumber()).getItem() instanceof NeedleMutant)
            {
                // Check if there is fluid inside our internal tank.
                if (internalLiquidDNAMutantTank.getFluidAmount() < this.MAX_MUTANTDNA)
                {
                    return true;
                }
            }

            return false;
        }
        else
        {
            // Check if output slot matches what is being smelted.
            if (itemsInputSlot != null && this.getStackInSlot(DNAExtractorEnumContainer.OutputDirtyNeedle.getSlotNumber()) != null)
            {
                // Check if output stack matches what is being smelted.
                if (!(this.getStackInSlot(DNAExtractorEnumContainer.OutputDirtyNeedle.getSlotNumber()).itemID == itemsInputSlot.itemID))
                {
                    return false;
                }
            }

            // Check if output slots are empty and ready to be filled with items.
            if (this.getStackInSlot(DNAExtractorEnumContainer.InputEmptyBucket.getSlotNumber()) == null &&
                    this.getStackInSlot(DNAExtractorEnumContainer.OutputDNASample.getSlotNumber()) == null)
            {
                return true;
            }

            // Check if input item matches one that is already be output slot 2.
            if (this.getStackInSlot(DNAExtractorEnumContainer.InputEmptyBucket.getSlotNumber()) != null &&
                    itemsInputSlot != null && !this.getStackInSlot(DNAExtractorEnumContainer.InputEmptyBucket.getSlotNumber()).isItemEqual(itemsInputSlot))
            {
                return false;
            }

            // By default we assume we are full unless proven otherwise.
            boolean outputSlotsFull = true;

            // Check if output slot 1 is above item stack limit.
            if (this.getStackInSlot(DNAExtractorEnumContainer.OutputDNASample.getSlotNumber()) != null)
            {
                int slot1Result = this.getStackInSlot(DNAExtractorEnumContainer.OutputDNASample.getSlotNumber()).stackSize + itemsInputSlot.stackSize;
                outputSlotsFull = (slot1Result <= getInventoryStackLimit() && slot1Result <= itemsInputSlot.getMaxStackSize());
            }

            // Check if output slot 2 is above item stack limit.
            if (this.getStackInSlot(DNAExtractorEnumContainer.OutputDirtyNeedle.getSlotNumber()) != null)
            {
                int slot2Result = this.getStackInSlot(DNAExtractorEnumContainer.OutputDirtyNeedle.getSlotNumber()).stackSize + itemsInputSlot.stackSize;
                outputSlotsFull = (slot2Result <= getInventoryStackLimit() && slot2Result <= itemsInputSlot.getMaxStackSize());
            }

            return outputSlotsFull;
        }
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain)
    {
        if (resource == null || !resource.isFluidEqual(internalLiquidDNAMutantTank.getFluid()))
        {
            return null;
        }
        
        return drain(from, resource.amount, doDrain);
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxEmpty, boolean doDrain)
    {
        return internalLiquidDNAMutantTank.drain(maxEmpty, doDrain);
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
    {
        // DNA Extractor cannot be filled by any fluids.
        return 0;
    }

    /** Returns the name of the inventory. */
    @Override
    public String getInvName()
    {
        return MadFurnaces.DNAEXTRACTOR_INTERNALNAME;
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

    @SideOnly(Side.CLIENT)
    /**
     * Returns an integer between 0 and the passed value representing how close the current item is to being completely
     * cooked
     */ 
    int getItemCookTimeScaled(int prgPixels)
    {
        // Prevent divide by zero exception by setting ceiling.
        if (currentItemCookingMaximum == 0)
        {
            // MadScience.logger.info("CLIENT: getItemCookTimeScaled() was called with currentItemCookingMaximum being zero!");
            currentItemCookingMaximum = 200;
        }

        return (currentItemCookingValue * prgPixels) / currentItemCookingMaximum;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from)
    {
        return new FluidTankInfo[]
        { internalLiquidDNAMutantTank.getInfo() };
    }

    @SideOnly(Side.CLIENT) 
    int getWaterRemainingScaled(int i)
    {
        return internalLiquidDNAMutantTank.getFluid() != null ? (int) (((float) internalLiquidDNAMutantTank.getFluid().amount / (float) (MAX_MUTANTDNA)) * i) : 0;
    }

    /** Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot. */
    @Override
    public boolean isItemValidForSlot(int slot, ItemStack items)
    {
        // Input Slot 1 - Genetic material we can get DNA samples from.
        if (slot == DNAExtractorEnumContainer.InputGeneticMatrial.getSlotNumber())
        {
            // Check if we are a mutant DNA needle.
            if (items.getItem() instanceof NeedleMutant)
            {
                return true;
            }

            // Check if we are a valid recipe for this device.
            ItemStack result = DNAExtractorRecipes.getSmeltingResult(items);
            if (result != null)
            {
                return true;
            }
        }

        // Input Slot 2 - Empty bucket to be filled with liquid mutant DNA.
        if (slot == DNAExtractorEnumContainer.InputEmptyBucket.getSlotNumber())
        {
            ItemStack compareOutputSlot = new ItemStack(Item.bucketEmpty);
            if (compareOutputSlot.isItemEqual(items))
            {
                return true;
            }
        }

        return false;
    }

    /** Reads a tile entity from NBT. */
    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);

        // Current time left to cook item.
        this.currentItemCookingValue = nbt.getShort("CookTime");

        // Set internal tank amount based on save data.
        this.internalLiquidDNAMutantTank.setFluid(new FluidStack(MadFluids.LIQUIDDNA_MUTANT, nbt.getShort("WaterAmount")));

        // Should play animation status.
        this.shouldPlay = nbt.getBoolean("ShouldPlay");

        // Current frame of animation we are displaying.
        this.curFrame = nbt.getInteger("CurrentFrame");

        // Path to current texture what should be loaded onto the model.
        this.TEXTURE = nbt.getString("TexturePath");
    }

    private boolean removeMutantDNAFromInternalTank()
    {
        // Check if the input slot for filled buckets is null.
        if (this.getStackInSlot(DNAExtractorEnumContainer.InputEmptyBucket.getSlotNumber()) == null)
        {
            return false;
        }

        // Items we will use to compare with in our input slots.
        ItemStack emptyBucket = new ItemStack(Item.bucketEmpty);
        ItemStack liquidDNABucket = new ItemStack(MadFluids.LIQUIDDNA_MUTANT_BUCKET_ITEM);

        // Check if input slot 2 is a empty bucket.
        if (!this.getStackInSlot(DNAExtractorEnumContainer.InputEmptyBucket.getSlotNumber()).isItemEqual(emptyBucket))
        {
            return false;
        }

        // Check if output slot 2 (for filled buckets) is above item stack limit.
        if (this.getStackInSlot(DNAExtractorEnumContainer.InputEmptyBucket.getSlotNumber()) != null)
        {
            int slot1Result = this.getStackInSlot(DNAExtractorEnumContainer.InputEmptyBucket.getSlotNumber()).stackSize + liquidDNABucket.stackSize;
            boolean underStackLimit = (slot1Result <= getInventoryStackLimit() && slot1Result <= liquidDNABucket.getMaxStackSize());
            if (!underStackLimit)
            {
                return false;
            }
        }

        // Check if we actually have some fluid to give out.
        if (internalLiquidDNAMutantTank.getFluidAmount() <= 0)
        {
            return false;
        }

        // Add a filled mutant DNA bucket to output slot 3.
        if (this.getStackInSlot(DNAExtractorEnumContainer.OutputFilledMutantDNABucket.getSlotNumber()) == null)
        {
            this.setInventorySlotContents(DNAExtractorEnumContainer.OutputFilledMutantDNABucket.getSlotNumber(), liquidDNABucket.copy());
        }
        else if (this.getStackInSlot(DNAExtractorEnumContainer.OutputFilledMutantDNABucket.getSlotNumber()).isItemEqual(liquidDNABucket))
        {
            if (this.getStackInSlot(DNAExtractorEnumContainer.OutputFilledMutantDNABucket.getSlotNumber()).stackSize <= this.getInventoryStackLimit())
            {
                this.getStackInSlot(DNAExtractorEnumContainer.OutputFilledMutantDNABucket.getSlotNumber()).stackSize += liquidDNABucket.stackSize;
            }
        }

        // Decrease the amount of water in the blocks internal storage.
        FluidStack amtRemoved = internalLiquidDNAMutantTank.drain(FluidContainerRegistry.BUCKET_VOLUME, true);

        if (amtRemoved != null && amtRemoved.amount > 0)
        {
            // Debug output for internal tank amount.
            MadScience.logger.info("internalWaterTank() " + internalLiquidDNAMutantTank.getFluidAmount());

            // Remove a empty bucket of water.
            --this.getStackInSlot(DNAExtractorEnumContainer.InputEmptyBucket.getSlotNumber()).stackSize;
            if (this.getStackInSlot(DNAExtractorEnumContainer.InputEmptyBucket.getSlotNumber()).stackSize <= 0)
            {
                this.setInventorySlotContents(DNAExtractorEnumContainer.InputEmptyBucket.getSlotNumber(), null);
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

    private void smeltItem()
    {
        // Output 1 - Dirty needle leftover from extracting DNA sample.
        ItemStack itemOutputSlot1 = new ItemStack(MadNeedles.NEEDLE_DIRTY);

        // Output 2 - Extracted DNA sample from needle.
        ItemStack extractedDNASample = DNAExtractorRecipes.getSmeltingResult(this.getStackInSlot(DNAExtractorEnumContainer.InputGeneticMatrial.getSlotNumber()));

        // Check if we are a mutant DNA needle.
        if (extractedDNASample == null && this.getStackInSlot(DNAExtractorEnumContainer.InputGeneticMatrial.getSlotNumber()).getItem() instanceof NeedleMutant)
        {
            // Add a bucket's worth of water into the internal tank.
            internalLiquidDNAMutantTank.fill(new FluidStack(MadFluids.LIQUIDDNA_MUTANT, FluidContainerRegistry.BUCKET_VOLUME), true);
        }
        else if (extractedDNASample != null)
        {
            // Add extracted DNA sample output slot 2 on GUI.
            if (this.getStackInSlot(DNAExtractorEnumContainer.OutputDirtyNeedle.getSlotNumber()) == null)
            {
                this.setInventorySlotContents(DNAExtractorEnumContainer.OutputDirtyNeedle.getSlotNumber(), extractedDNASample.copy());
            }
            else if (this.getStackInSlot(DNAExtractorEnumContainer.OutputDirtyNeedle.getSlotNumber()).isItemEqual(extractedDNASample))
            {
                this.getStackInSlot(DNAExtractorEnumContainer.OutputDirtyNeedle.getSlotNumber()).stackSize += extractedDNASample.stackSize;
            }
        }

        // Check if we are working with a filled needle or not.
        if (this.getStackInSlot(DNAExtractorEnumContainer.InputGeneticMatrial.getSlotNumber()).getItem() instanceof ItemDecayNeedle)
        {
            // Add dirty needle to output slot 1 on GUI.
            if (this.getStackInSlot(DNAExtractorEnumContainer.OutputDNASample.getSlotNumber()) == null)
            {
                this.setInventorySlotContents(DNAExtractorEnumContainer.OutputDNASample.getSlotNumber(), itemOutputSlot1.copy());
            }
            else if (this.getStackInSlot(DNAExtractorEnumContainer.OutputDNASample.getSlotNumber()).isItemEqual(itemOutputSlot1) &&
                    this.getStackInSlot(DNAExtractorEnumContainer.OutputDNASample.getSlotNumber()).stackSize <= this.getInventoryStackLimit())
            {
                this.getStackInSlot(DNAExtractorEnumContainer.OutputDNASample.getSlotNumber()).stackSize += itemOutputSlot1.stackSize;
            }
        }

        // Remove one of the input items from the GUI.
        --this.getStackInSlot(DNAExtractorEnumContainer.InputGeneticMatrial.getSlotNumber()).stackSize;
        if (this.getStackInSlot(DNAExtractorEnumContainer.InputGeneticMatrial.getSlotNumber()).stackSize <= 0)
        {
            this.setInventorySlotContents(DNAExtractorEnumContainer.InputGeneticMatrial.getSlotNumber(), null);
        }

        // Play a sound of needle being extracted.
        this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, DNAExtractorSounds.DNAEXTRACTOR_FINISH, 1.0F, 1.0F);
    }

    /**
     * Update current texture that should be displayed based on our status.
     */
    private void updateAnimation()
    {
        // Active state has many textures based on item cook progress.
        if (this.canSmelt() && this.isPowered())
        {
            if (curFrame <= 11 && worldObj.getWorldTime() % 25L == 0L)
            {
                // Load this texture onto the entity.
                TEXTURE = "models/" + MadFurnaces.DNAEXTRACTOR_INTERNALNAME + "/work_" + curFrame + ".png";

                // Update animation frame.
                ++curFrame;
            }
            else if (curFrame >= 12)
            {
                // Check if we have exceeded the ceiling and need to reset.
                curFrame = 0;
            }
        }
        else
        {
            // Idle state single texture.
            TEXTURE = "models/" + MadFurnaces.DNAEXTRACTOR_INTERNALNAME + "/idle.png";
        }
    }

    /** Allows the entity to update its state. */
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
            if (this.currentItemCookingValue == 0 && this.canSmelt() && this.isPowered())
            {
                // New item pulled from cooking stack to be processed, check how
                // long this item will take to cook.
                currentItemCookingMaximum = getItemBurnTime(this.getStackInSlot(DNAExtractorEnumContainer.InputGeneticMatrial.getSlotNumber()));

                // Increments the timer to kickstart the cooking loop.
                this.currentItemCookingValue++;
            }
            else if (this.currentItemCookingValue > 0 && this.canSmelt() && this.isPowered())
            {
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

            // Send update to clients that require it.
            PacketDispatcher.sendPacketToAllAround(this.xCoord, this.yCoord, this.zCoord, MadConfig.PACKETSEND_RADIUS, worldObj.provider.dimensionId,
                    new DNAExtractorPackets(this.xCoord, this.yCoord, this.zCoord,
                    currentItemCookingValue, currentItemCookingMaximum,
                    getEnergy(ForgeDirection.UNKNOWN), getEnergyCapacity(ForgeDirection.UNKNOWN),
                    internalLiquidDNAMutantTank.getFluidAmount(), internalLiquidDNAMutantTank.getCapacity(),
                    this.TEXTURE).makePacket());
        }

        if (inventoriesChanged)
        {
            this.onInventoryChanged();
        }
    }

    private void updateSound()
    {
        // Check to see if we should play idle sounds.
        if (this.canSmelt() && this.isPowered() && worldObj.getWorldTime() % MadScience.SECOND_IN_TICKS == 0L)
        {
            this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, DNAExtractorSounds.DNAEXTRACTOR_IDLE, 1.0F, 1.0F);
        }
    }

    /** Writes a tile entity to NBT. */
    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);

        // Time remaining of this needle extraction to get DNA sample.
        nbt.setShort("CookTime", (short) this.currentItemCookingValue);

        // Amount of water that is currently stored.
        nbt.setShort("WaterAmount", (short) this.internalLiquidDNAMutantTank.getFluidAmount());

        // Should play animation status.
        nbt.setBoolean("ShouldPlay", this.shouldPlay);

        // Current frame of animation we are displaying.
        nbt.setInteger("CurrentFrame", this.curFrame);

        // Path to current texture that should be loaded onto the model.
        nbt.setString("TexturePath", this.TEXTURE);
    }
}

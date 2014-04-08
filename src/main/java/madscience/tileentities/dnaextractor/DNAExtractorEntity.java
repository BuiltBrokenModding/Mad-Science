package madscience.tileentities.dnaextractor;

import java.util.Random;

import madscience.MadConfig;
import madscience.MadDNA;
import madscience.MadFluids;
import madscience.MadFurnaces;
import madscience.MadNeedles;
import madscience.MadScience;
import madscience.items.needles.NeedleMutant;
import madscience.tileentities.prefab.MadTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
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

public class DNAExtractorEntity extends MadTileEntity implements ISidedInventory, IFluidHandler
{
    private static final int[] slots_top = new int[]
    { 0 };

    private static final int[] slots_bottom = new int[]
    { 2, 1 };
    private static final int[] slots_sides = new int[]
    { 1 };
    /** The ItemStacks that hold the items currently being used in the furnace */
    private ItemStack[] furnaceItemStacks = new ItemStack[5];

    /** The number of ticks that a fresh copy of the currently-burning item would keep the furnace burning for */
    public int currentItemCookingMaximum;

    /** The number of ticks that the current item has been cooking for */
    public int currentItemCookingValue;

    // ** Maximum number of buckets of water this machine can hold internally */
    public int MAX_MUTANTDNA = FluidContainerRegistry.BUCKET_VOLUME * 10;

    /** Internal reserve of water */
    protected FluidTank internalLiquidDNAMutantTank = new FluidTank(MadFluids.LIQUIDDNA_MUTANT, 1, MAX_MUTANTDNA);

    /** Random number generator used to spit out food stuffs. */
    public Random animRand = new Random();

    /** Determines if we currently should be playing animation frames every tick or not. */
    public boolean shouldPlay;

    /** Current frame of animation we should use to display in world. */
    public int curFrame;

    /** Path to current texture that should be displayed on our model. */
    public String dnaExtractorTexture = "models/" + MadFurnaces.DNAEXTRACTOR_INTERNALNAME + "/idle.png";

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

    /** Returns true if automation can extract the given item in the given slot from the given side. Args: Slot, item, side */
    @Override
    public boolean canExtractItem(int par1, ItemStack par2ItemStack, int par3)
    {
        // Returns a dirty needle if you try to suck the item out of the
        // furnace.
        return par3 != 0 || par1 != 1 || par2ItemStack.itemID == MadNeedles.NEEDLE_DIRTY.itemID;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid)
    {
        // DNA Extractor cannot be filled by any fluids.
        return false;
    }

    /** Returns true if automation can insert the given item in the given slot from the given side. Args: Slot, item, side */
    @Override
    public boolean canInsertItem(int par1, ItemStack par2ItemStack, int par3)
    {
        return this.isItemValidForSlot(par1, par2ItemStack);
    }

    /** Returns true if the furnace can smelt an item, i.e. has a source item, destination stack isn't full, etc. */
    public boolean canSmelt()
    {
        // Check for internal tank.
        if (internalLiquidDNAMutantTank == null)
        {
            return false;
        }

        // Check if there is an input item at all in the furnace.
        if (this.furnaceItemStacks[0] == null)
        {
            return false;
        }

        // Check if the item in the input slot will smelt into anything.
        ItemStack itemsInputSlot = DNAExtractorRecipes.getSmeltingResult(this.furnaceItemStacks[0]);
        if (itemsInputSlot == null)
        {
            // Check if we are a mutant DNA needle.
            if (this.furnaceItemStacks[0].getItem() instanceof NeedleMutant)
            {
                // Check if there is fluid inside our internal tank.
                if (internalLiquidDNAMutantTank.getFluidAmount() < this.MAX_MUTANTDNA)
                {
                    return true;
                }
            }

            return false;
        }

        // Check if output slots are empty and ready to be filled with
        // items.
        if (this.furnaceItemStacks[1] == null || this.furnaceItemStacks[2] == null)
        {
            return true;
        }

        // Check if input item matches one that is already be output slot 2.
        if (!this.furnaceItemStacks[1].isItemEqual(itemsInputSlot))
        {
            return false;
        }

        // Check if output stack matches what is being smelted.
        if (!(this.furnaceItemStacks[1].itemID == itemsInputSlot.itemID))
        {
            return false;
        }

        // By default we assume we are full unless proven otherwise.
        boolean outputSlotsFull = true;

        // Check if output slot 1 is above item stack limit.
        int slot1Result = furnaceItemStacks[2].stackSize + itemsInputSlot.stackSize;
        outputSlotsFull = (slot1Result <= getInventoryStackLimit() && slot1Result <= itemsInputSlot.getMaxStackSize());

        // Check if output slot 2 is above item stack limit.
        int slot2Result = furnaceItemStacks[1].stackSize + itemsInputSlot.stackSize;
        outputSlotsFull = (slot2Result <= getInventoryStackLimit() && slot2Result <= itemsInputSlot.getMaxStackSize());

        return outputSlotsFull;
    }

    @Override
    public void closeChest()
    {
    }

    /** Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a new stack. */
    @Override
    public ItemStack decrStackSize(int par1, int par2)
    {
        if (this.furnaceItemStacks[par1] != null)
        {
            ItemStack itemstack;

            if (this.furnaceItemStacks[par1].stackSize <= par2)
            {
                itemstack = this.furnaceItemStacks[par1];
                this.furnaceItemStacks[par1] = null;
                return itemstack;
            }
            itemstack = this.furnaceItemStacks[par1].splitStack(par2);

            if (this.furnaceItemStacks[par1].stackSize == 0)
            {
                this.furnaceItemStacks[par1] = null;
            }

            return itemstack;
        }
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain)
    {
        if (resource == null || !resource.isFluidEqual(internalLiquidDNAMutantTank.getFluid()))
            return null;
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

    /** Returns an array containing the indices of the slots that can be accessed by automation on the given side of this block. */
    @Override
    public int[] getAccessibleSlotsFromSide(int par1)
    {
        return par1 == 0 ? slots_bottom : (par1 == 1 ? slots_top : slots_sides);
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

    /** Returns the number of slots in the inventory. */
    @Override
    public int getSizeInventory()
    {
        return this.furnaceItemStacks.length;
    }

    /** Returns the stack in slot i */
    @Override
    public ItemStack getStackInSlot(int par1)
    {
        return this.furnaceItemStacks[par1];
    }

    /** When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem - like when you close a workbench GUI. */
    @Override
    public ItemStack getStackInSlotOnClosing(int par1)
    {
        if (this.furnaceItemStacks[par1] != null)
        {
            ItemStack itemstack = this.furnaceItemStacks[par1];
            this.furnaceItemStacks[par1] = null;
            return itemstack;
        }
        return null;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from)
    {
        return new FluidTankInfo[]
        { internalLiquidDNAMutantTank.getInfo() };
    }

    @SideOnly(Side.CLIENT)
    public int getWaterRemainingScaled(int i)
    {
        return internalLiquidDNAMutantTank.getFluid() != null ? (int) (((float) internalLiquidDNAMutantTank.getFluid().amount / (float) (MAX_MUTANTDNA)) * i) : 0;
    }

    /** If this returns false, the inventory name will be used as an unlocalized name, and translated into the player's language. Otherwise it will be used directly. */
    @Override
    public boolean isInvNameLocalized()
    {
        return true;
    }

    /** Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot. */
    @Override
    public boolean isItemValidForSlot(int slot, ItemStack items)
    {
        // Input Slot 1 - Filled needle.
        if (slot == 0)
        {
            // Check if we are a mutant DNA needle.
            if (items.getItem() instanceof NeedleMutant)
            {
                return true;
            }

            // Check if we are a DNA sample.
            ItemStack result = DNAExtractorRecipes.getSmeltingResult(items);
            if (result != null)
            {
                return true;
            }
        }

        // Input Slot 2 - Empty bucket.
        if (slot == 1)
        {
            ItemStack compareOutputSlot = new ItemStack(Item.bucketEmpty);
            if (compareOutputSlot.isItemEqual(items))
            {
                return true;
            }
        }

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
        // Required by class.
    }

    /** Reads a tile entity from NBT. */
    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        NBTTagList nbttaglist = nbt.getTagList("Items");
        this.furnaceItemStacks = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.tagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");

            if (b0 >= 0 && b0 < this.furnaceItemStacks.length)
            {
                this.furnaceItemStacks[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }

        // Current time left to cook item.
        this.currentItemCookingValue = nbt.getShort("CookTime");

        // Set internal tank amount based on save data.
        this.internalLiquidDNAMutantTank.setFluid(new FluidStack(MadFluids.LIQUIDDNA_MUTANT, nbt.getShort("WaterAmount")));

        // Should play animation status.
        this.shouldPlay = nbt.getBoolean("ShouldPlay");

        // Current frame of animation we are displaying.
        this.curFrame = nbt.getInteger("CurrentFrame");

        // Path to current texture what should be loaded onto the model.
        this.dnaExtractorTexture = nbt.getString("TexturePath");
    }

    private boolean removeMutantDNAFromInternalTank()
    {
        // Check if the input slot for filled buckets is null.
        if (this.furnaceItemStacks[1] == null)
        {
            return false;
        }

        // Items we will use to compare with in our input slots.
        ItemStack emptyBucket = new ItemStack(Item.bucketEmpty);
        ItemStack liquidDNABucket = new ItemStack(MadFluids.LIQUIDDNA_MUTANT_BUCKET_ITEM);

        // Check if input slot 2 is a empty bucket.
        if (!this.furnaceItemStacks[1].isItemEqual(emptyBucket))
        {
            return false;
        }

        // Check if output slot 2 (for filled buckets) is above item stack limit.
        if (this.furnaceItemStacks[1] != null)
        {
            int slot1Result = this.furnaceItemStacks[1].stackSize + liquidDNABucket.stackSize;
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
        if (this.furnaceItemStacks[4] == null)
        {
            this.furnaceItemStacks[4] = liquidDNABucket.copy();
        }
        else if (this.furnaceItemStacks[4].isItemEqual(liquidDNABucket))
        {
            furnaceItemStacks[4].stackSize += liquidDNABucket.stackSize;
        }

        // Decrease the amount of water in the blocks internal storage.
        FluidStack amtRemoved = internalLiquidDNAMutantTank.drain(FluidContainerRegistry.BUCKET_VOLUME, true);

        if (amtRemoved != null && amtRemoved.amount > 0)
        {
            // Debug output for internal tank amount.
            MadScience.logger.info("internalWaterTank() " + internalLiquidDNAMutantTank.getFluidAmount());

            // Remove a filled bucket of water from input stack 2.
            --this.furnaceItemStacks[1].stackSize;
            if (this.furnaceItemStacks[1].stackSize <= 0)
            {
                this.furnaceItemStacks[1] = null;
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

    /** Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections). */
    @Override
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
    {
        this.furnaceItemStacks[par1] = par2ItemStack;

        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
        {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }
    }

    public void smeltItem()
    {
        // Output 1 - Dirty needle leftover from extracting DNA sample.
        ItemStack itemOutputSlot1 = new ItemStack(MadNeedles.NEEDLE_DIRTY);

        // Output 2 - Extracted DNA sample from needle.
        ItemStack itemOutputSlot2 = DNAExtractorRecipes.getSmeltingResult(this.furnaceItemStacks[0]);

        // Check if we are a mutant DNA needle.
        if (itemOutputSlot2 == null && this.furnaceItemStacks[0].getItem() instanceof NeedleMutant)
        {
            // Add a bucket's worth of water into the internal tank.
            internalLiquidDNAMutantTank.fill(new FluidStack(MadFluids.LIQUIDDNA_MUTANT, FluidContainerRegistry.BUCKET_VOLUME), true);
        }
        else if (itemOutputSlot2 != null)
        {
            // Add extracted DNA sample output slot 2 on GUI.
            if (this.furnaceItemStacks[3] == null)
            {
                this.furnaceItemStacks[3] = itemOutputSlot2.copy();
            }
            else if (this.furnaceItemStacks[3].isItemEqual(itemOutputSlot2))
            {
                furnaceItemStacks[3].stackSize += itemOutputSlot2.stackSize;
            }
        }

        // Add dirty needle to output slot 1 on GUI.
        if (this.furnaceItemStacks[2] == null)
        {
            this.furnaceItemStacks[2] = itemOutputSlot1.copy();
        }
        else if (this.furnaceItemStacks[2].isItemEqual(itemOutputSlot1))
        {
            furnaceItemStacks[2].stackSize += itemOutputSlot1.stackSize;
        }

        // Remove one of the input items from the GUI.
        --this.furnaceItemStacks[0].stackSize;
        if (this.furnaceItemStacks[0].stackSize <= 0)
        {
            this.furnaceItemStacks[0] = null;
        }

        // Play a sound of needle being extracted.
        this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, DNAExtractorSounds.DNAEXTRACTOR_FINISH, 1.0F, 1.0F);
    }

    /**
     * 
     */
    private void updateAnimation()
    {
        // Active state has many textures based on item cook progress.
        if (this.canSmelt() && this.isPowered())
        {
            if (curFrame <= 11 && worldObj.getWorldTime() % 25L == 0L)
            {
                // Load this texture onto the entity.
                dnaExtractorTexture = "models/" + MadFurnaces.DNAEXTRACTOR_INTERNALNAME + "/work_" + curFrame + ".png";

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
            dnaExtractorTexture = "models/" + MadFurnaces.DNAEXTRACTOR_INTERNALNAME + "/idle.png";
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
                currentItemCookingMaximum = getItemBurnTime(furnaceItemStacks[0]);

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
            PacketDispatcher.sendPacketToAllAround(this.xCoord, this.yCoord, this.zCoord, MadConfig.PACKETSEND_RADIUS, worldObj.provider.dimensionId, new DNAExtractorPackets(this.xCoord, this.yCoord, this.zCoord, currentItemCookingValue, currentItemCookingMaximum,
                    getEnergy(ForgeDirection.UNKNOWN), getEnergyCapacity(ForgeDirection.UNKNOWN), internalLiquidDNAMutantTank.getFluidAmount(), internalLiquidDNAMutantTank.getCapacity(), this.dnaExtractorTexture).makePacket());
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
        nbt.setString("TexturePath", this.dnaExtractorTexture);

        // Items inside.
        NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < this.furnaceItemStacks.length; ++i)
        {
            if (this.furnaceItemStacks[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte) i);
                this.furnaceItemStacks[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        nbt.setTag("Items", nbttaglist);
    }
}

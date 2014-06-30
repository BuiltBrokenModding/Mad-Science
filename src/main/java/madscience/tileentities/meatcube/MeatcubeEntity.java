package madscience.tileentities.meatcube;

import java.util.Random;

import madscience.MadConfig;
import madscience.MadFluids;
import madscience.MadFurnaces;
import madscience.MadScience;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import cpw.mods.fml.common.network.PacketDispatcher;

public class MeatcubeEntity extends TileEntity implements ISidedInventory, IFluidHandler
{
    private static final int[] slots_top = new int[]
    { 0 };
    private static final int[] slots_bottom = new int[]
    { 2, 1 };
    private static final int[] slots_sides = new int[]
    { 1 };

    /** The ItemStacks that hold the items currently being used in the furnace */
    private ItemStack[] meatcubeOutput = new ItemStack[1];

    private ItemStack[] meatcubeInput = new ItemStack[1];

    /** The number of ticks that a fresh copy of the currently-burning item would keep the furnace burning for */
    int currentItemCookingMaximum;

    /** The number of ticks that the current item has been cooking for */
    int currentItemCookingValue;

    /** Stores maximum health level of this meat cube */
    int currentMaximumMeatCubeDamage = 14;

    /** Stores current health level of this meat cube so we can display levels of damage based on it */
    int currentMeatCubeDamageValue = currentMaximumMeatCubeDamage;

    // ** Maximum number of buckets of water this machine can hold internally */
    private static int MAX_MUTANTDNA = FluidContainerRegistry.BUCKET_VOLUME * 10;

    /** Internal reserve of water */
    protected FluidTank internalLiquidDNAMutantTank = new FluidTank(MadFluids.LIQUIDDNA_MUTANT, 0, MAX_MUTANTDNA);

    /** Last amount of ticks waited before playing an animation. */
    private long lastAnimTriggerTime = 42L;

    /** Last known number of frames played. */
    private int lastAnimFrameCount = 9;

    /** Random number generator used to spit out food stuffs. */
    private Random animRand = new Random();

    /** Determines if we currently should be playing animation frames every tick or not. */
    private boolean shouldPlay;

    /** Current frame of animation we should use to display in world. */
    private int curFrame;

    /** Current texture path that should be displayed on the model */
    String meatcubeTexturePath = "models/" + MadFurnaces.MEATCUBE_INTERNALNAME + "/meatcube_0.png";

    private void addBucketToInternalTank()
    {
        // Check if the input slot for filled buckets is null.
        if (this.meatcubeInput[0] == null)
        {
            return;
        }

        // Output 1 - Empty bucket returns from full one in input slot 1
        ItemStack bucketEmpty = new ItemStack(Item.bucketEmpty);
        ItemStack bucketMutantDNA = new ItemStack(MadFluids.LIQUIDDNA_MUTANT_BUCKET_ITEM);

        // Check if input slot 1 is a filled bucket.
        if (!this.meatcubeInput[0].isItemEqual(bucketMutantDNA))
        {
            // Input slot 1 was not a filled bucket.
            return;
        }

        // Check if output slot 1 (for empty buckets) is above item stack limit.
        if (this.meatcubeOutput[0] != null)
        {
            int slot1Result = this.meatcubeOutput[0].stackSize + bucketEmpty.stackSize;
            boolean underStackLimit = (slot1Result <= getInventoryStackLimit() && slot1Result <= bucketEmpty.getMaxStackSize());
            if (!underStackLimit)
            {
                // If we are not under the minecraft or item stack limit for
                // output slot 2 then stop here.
                return;
            }
        }

        // Check if the internal water tank has reached capacity
        if (internalLiquidDNAMutantTank.getFluidAmount() >= internalLiquidDNAMutantTank.getCapacity())
        {
            return;
        }

        // Add empty water bucket to output slot 1 GUI.
        if (this.meatcubeOutput[0] == null)
        {
            this.meatcubeOutput[0] = bucketEmpty.copy();
        }
        else if (this.meatcubeOutput[0].isItemEqual(bucketEmpty))
        {
            meatcubeOutput[0].stackSize += bucketEmpty.stackSize;
        }

        // Add a bucket's worth of water into the internal tank.
        internalLiquidDNAMutantTank.fill(new FluidStack(MadFluids.LIQUIDDNA_MUTANT, FluidContainerRegistry.BUCKET_VOLUME), true);

        // Play a regrowing noise when we manually add a bucket of water into the meatcube.
        this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, MeatcubeSounds.MEATCUBE_REGROW, 1.0F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
        MadScience.logger.info("internalWaterTank() " + internalLiquidDNAMutantTank.getFluidAmount());

        // Remove a filled bucket of water from input stack 1.
        --this.meatcubeInput[0].stackSize;
        if (this.meatcubeInput[0].stackSize <= 0)
        {
            this.meatcubeInput[0] = null;
        }

        return;
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
        if (slot == 0 && ForgeDirection.getOrientation(side) == ForgeDirection.WEST)
        {
            // Filled bucket.
            return true;
        }
        else if (slot == 1 && ForgeDirection.getOrientation(side) == ForgeDirection.WEST)
        {
            // Empty water bucket
            return true;
        }

        // Default response is no.
        return false;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid)
    {
        // This block can receive water on any side to store in internal tank.
        if (MadFluids.LIQUIDDNA_MUTANT_BLOCK.blockID == fluid.getBlockID())
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
    private boolean canSmelt()
    {
        // Check for internal tank.
        if (internalLiquidDNAMutantTank == null)
        {
            return false;
        }

        // Check if meat cube needs healing or not.
        if (this.currentMeatCubeDamageValue < this.currentMaximumMeatCubeDamage)
        {
            return true;
        }

        // Check if there is fluid inside our internal tank.
        if (internalLiquidDNAMutantTank.getFluidAmount() < (FluidContainerRegistry.BUCKET_VOLUME / 4))
        {
            return false;
        }
        return false;
    }

    @Override
    public void closeChest()
    {
    }

    private ItemStack DecreaseInputSlot(int slot, int numItems)
    {
        // Removes items from input slot 1 or 2.
        if (this.meatcubeInput[slot] != null)
        {
            ItemStack itemstack;

            if (this.meatcubeInput[slot].stackSize <= numItems)
            {
                itemstack = this.meatcubeInput[slot];
                this.meatcubeInput[slot] = null;
                return itemstack;
            }
            itemstack = this.meatcubeInput[slot].splitStack(numItems);

            if (this.meatcubeInput[slot].stackSize == 0)
            {
                this.meatcubeInput[slot] = null;
            }

            return itemstack;
        }
        return null;
    }

    private ItemStack DecreaseOutputSlot(int slot, int numItems)
    {
        // Removes items from either output slot 1 or 2.
        if (this.meatcubeOutput[slot] != null)
        {
            ItemStack itemstack;

            if (this.meatcubeOutput[slot].stackSize <= numItems)
            {
                itemstack = this.meatcubeOutput[slot];
                this.meatcubeOutput[slot] = null;
                return itemstack;
            }
            itemstack = this.meatcubeOutput[slot].splitStack(numItems);

            if (this.meatcubeOutput[slot].stackSize == 0)
            {
                this.meatcubeOutput[slot] = null;
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
            // Input stack 1 - Water bucket.
            return DecreaseInputSlot(0, numItems);
        }

        if (slot == 1)
        {
            // Output stack 1 - Empty bucket
            return DecreaseOutputSlot(0, numItems);
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
            return internalLiquidDNAMutantTank.fill(resource, doFill);
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
        return MadFurnaces.MEATCUBE_INTERNALNAME;
    }

    

    public int getSizeInputInventory()
    {
        return this.meatcubeInput.length;
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
        return meatcubeOutput.length;
    }

    /** Returns the stack in slot i */
    @Override
    public ItemStack getStackInSlot(int slot)
    {
        if (slot == 0)
        {
            // Input slot 1
            return this.meatcubeInput[0];
        }

        if (slot == 1)
        {
            // Output slot 2
            return this.meatcubeOutput[0];
        }

        MadScience.logger.info("getStackInSlot() could not return valid stack from slot " + slot);
        return null;
    }

    /** When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem - like when you close a workbench GUI. */
    @Override
    public ItemStack getStackInSlotOnClosing(int slot)
    {
        // Input slot 1 - Filled bucket.
        if (slot == 0)
        {
            // Input slot 1 - Water bucket.
            if (this.meatcubeInput[0] != null)
            {
                ItemStack itemstack = this.meatcubeInput[0];
                this.meatcubeInput[0] = null;
                return itemstack;
            }
            return null;
        }

        // Output slot 1 - Empty bucket.
        if (slot == 1)
        {
            if (this.meatcubeOutput[0] != null)
            {
                ItemStack itemstack = this.meatcubeOutput[0];
                this.meatcubeOutput[0] = null;
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
        { internalLiquidDNAMutantTank.getInfo() };
    }

    int getWaterRemainingScaled(int i)
    {
        return internalLiquidDNAMutantTank.getFluid() != null ? (int) (((float) internalLiquidDNAMutantTank.getFluid().amount / (float) (MAX_MUTANTDNA)) * i) : 0;
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
            // Input slot 1 - Filled bucket.
            ItemStack compareInputSlot = new ItemStack(MadFluids.LIQUIDDNA_MUTANT_BUCKET_ITEM);
            if (compareInputSlot.isItemEqual(items))
            {
                return true;
            }
        }

        // Input slot 2 - Empty bucket.
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
        this.meatcubeInput = new ItemStack[this.getSizeInputInventory()];
        this.meatcubeOutput = new ItemStack[this.getSizeOutputInventory()];

        // Input items process.
        for (int i = 0; i < nbtInput.tagCount(); ++i)
        {
            NBTTagCompound inputSaveData = (NBTTagCompound) nbtInput.tagAt(i);
            byte b0 = inputSaveData.getByte("InputSlot");

            if (b0 >= 0 && b0 < this.meatcubeInput.length)
            {
                this.meatcubeInput[b0] = ItemStack.loadItemStackFromNBT(inputSaveData);
            }
        }

        // Output items process.
        for (int i = 0; i < nbtOutput.tagCount(); ++i)
        {
            NBTTagCompound outputSaveData = (NBTTagCompound) nbtOutput.tagAt(i);
            byte b0 = outputSaveData.getByte("OutputSlot");

            if (b0 >= 0 && b0 < this.meatcubeOutput.length)
            {
                this.meatcubeOutput[b0] = ItemStack.loadItemStackFromNBT(outputSaveData);
            }
        }

        // Amount of time before meatcube will regenerate some of itself using mutagen.
        this.currentItemCookingValue = nbt.getShort("CookTime");

        // Set internal tank amount based on save data.
        this.internalLiquidDNAMutantTank.setFluid(new FluidStack(MadFluids.LIQUIDDNA_MUTANT, nbt.getShort("WaterAmount")));

        // Current health of our meatcube which will be used to indicate model level.
        this.currentMeatCubeDamageValue = nbt.getInteger("MeatLevel");

        // Should play animation status.
        this.shouldPlay = nbt.getBoolean("ShouldPlay");

        // Current frame of animation we are displaying.
        this.curFrame = nbt.getInteger("CurrentFrame");

        // Current number of ticks we should wait until playing an animation.
        // Note: We ensure this is not restored to zero by old versions.
        this.lastAnimTriggerTime = Math.max(nbt.getLong("LastAnimTriggerTime"), MadScience.SECOND_IN_TICKS);

        // Last number of frames that were played.
        this.lastAnimFrameCount = nbt.getInteger("LastAnimFrameCount");

        // Path to current texture what should be loaded onto the model.
        this.meatcubeTexturePath = nbt.getString("TexturePath");
    }

    /** Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections). */
    @Override
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
    {
        if (par1 == 0)
        {
            this.meatcubeInput[0] = par2ItemStack;
        }
        else if (par1 == 1)
        {
            this.meatcubeOutput[0] = par2ItemStack;
        }

        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
        {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }
    }

    private void smeltItem()
    {
        // Converts input item into result item along with waste items.
        if (this.canSmelt())
        {
            // Decrease the amount of water in the blocks internal storage.
            FluidStack amtRemoved = internalLiquidDNAMutantTank.drain((FluidContainerRegistry.BUCKET_VOLUME / 4), true);

            if (amtRemoved != null && amtRemoved.amount > 0)
            {
                // Re-grow some of the meat cube (heal it).
                if (this.currentMeatCubeDamageValue < this.currentMaximumMeatCubeDamage)
                {
                    this.currentMeatCubeDamageValue++;

                    // Play a disgusting sound of the meatcube regrowing.
                    this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, MeatcubeSounds.MEATCUBE_REGROW, 1.0F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
                }
            }
        }
    }

    /** Update current texture path we should display on this tile entity. */
    private void updateAnimation()
    {
        // Check if we should be iterating our frame count.
        if (shouldPlay && curFrame < lastAnimFrameCount && worldObj.getWorldTime() % 5L == 0L)
        {
            // Load this texture onto the entity.
            meatcubeTexturePath = "models/" + MadFurnaces.MEATCUBE_INTERNALNAME + "/meatcube_" + curFrame + ".png";
            curFrame++;
        }
        else if (shouldPlay && curFrame >= lastAnimFrameCount)
        {
            curFrame = 0;
            shouldPlay = false;
        }

        // Randomly pick a number of frames and time to wait to play them.
        if (!shouldPlay && worldObj.getWorldTime() % lastAnimTriggerTime == 0L)
        {
            // Seed random number generator with world time.
            animRand = new Random(worldObj.getWorldTime());

            // Get how long we should wait until next animation.
            // Note: This cannot be zero!
            lastAnimTriggerTime = Math.max(animRand.nextInt(666), MadScience.SECOND_IN_TICKS);

            // Get how many frames of animation we should play this time.
            lastAnimFrameCount = animRand.nextInt(9);
            if (lastAnimFrameCount <= 2)
            {
                // Ensure there are always a few frames of animation played.
                lastAnimFrameCount = 9;
            }

            // Play sound effect of meatcube idly existing and being generally repulsive.
            this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, MeatcubeSounds.MEATCUBE_IDLE, 1.0F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);

            // Set the flag that triggers animation to play.
            shouldPlay = true;
        }
    }

    /** Allows the entity to update its state. */
    @Override
    public void updateEntity()
    {
        // Important to call the class below us!
        super.updateEntity();

        boolean inventoriesChanged = false;

        // Server side processing for furnace.
        if (!this.worldObj.isRemote)
        {
            // Changes texture on model that should be displayed based on state.
            updateAnimation();

            // Plays sounds idly and at random times.
            updateSounds();

            // Checks to see if we can add a bucket from input slot into
            // internal tank.
            addBucketToInternalTank();

            // First tick for new item being cooked in furnace.
            if (this.currentItemCookingValue == 0 && this.canSmelt())
            {
                // New item pulled from cooking stack to be processed, check how
                // long this item will take to cook.
                currentItemCookingMaximum = 200;

                // Increments the timer to kickstart the cooking loop.
                this.currentItemCookingValue++;
            }
            else if (this.currentItemCookingValue > 0 && this.canSmelt())
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

            // Send update about tile entity status to all players around us.
            PacketDispatcher.sendPacketToAllAround(this.xCoord, this.yCoord, this.zCoord, MadConfig.PACKETSEND_RADIUS, worldObj.provider.dimensionId, new MeatcubePackets(this.xCoord, this.yCoord, this.zCoord, currentItemCookingValue, currentItemCookingMaximum,
                    this.internalLiquidDNAMutantTank.getFluidAmount(), this.internalLiquidDNAMutantTank.getCapacity(), currentMeatCubeDamageValue, currentMaximumMeatCubeDamage, this.meatcubeTexturePath).makePacket());
        }

        if (inventoriesChanged)
        {
            this.onInventoryChanged();
        }
    }

    private void updateSounds()
    {
        // Play the sound of a heartbeat every few seconds.
        if (worldObj.getWorldTime() % ((this.currentMeatCubeDamageValue * MadScience.SECOND_IN_TICKS) + MadScience.SECOND_IN_TICKS) == 0L)
        {
            this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, MeatcubeSounds.MEATCUBE_HEARTBEAT, ((1.0F / this.currentMeatCubeDamageValue) + 0.42F), this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
        }
    }

    /** Writes a tile entity to NBT. */
    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);

        // Amount of time left to cook current item inside of the furnace.
        nbt.setShort("CookTime", (short) this.currentItemCookingValue);

        // Current level of damage that meat cube has stored on it.
        nbt.setInteger("MeatLevel", this.currentMeatCubeDamageValue);

        // Amount of water that is currently stored.
        nbt.setShort("WaterAmount", (short) this.internalLiquidDNAMutantTank.getFluidAmount());

        // Should play animation status.
        nbt.setBoolean("ShouldPlay", this.shouldPlay);

        // Current frame of animation we are displaying.
        nbt.setInteger("CurrentFrame", this.curFrame);

        // Current number of ticks we should wait until playing an animation.
        nbt.setLong("LastAnimTriggerTime", this.lastAnimTriggerTime);

        // Last number of frames that were played.
        nbt.setInteger("LastAnimFrameCount", this.lastAnimFrameCount);

        // Path to current texture that should be loaded onto the model.
        nbt.setString("TexturePath", this.meatcubeTexturePath);

        // Two tag lists for each type of items we have in this entity.
        NBTTagList inputItems = new NBTTagList();
        NBTTagList outputItems = new NBTTagList();

        // Input slots.
        for (int i = 0; i < this.meatcubeInput.length; ++i)
        {
            if (this.meatcubeInput[i] != null)
            {
                NBTTagCompound inputSlots = new NBTTagCompound();
                inputSlots.setByte("InputSlot", (byte) i);
                this.meatcubeInput[i].writeToNBT(inputSlots);
                inputItems.appendTag(inputSlots);
            }
        }

        // Output slots.
        for (int i = 0; i < this.meatcubeOutput.length; ++i)
        {
            if (this.meatcubeOutput[i] != null)
            {
                NBTTagCompound outputSlots = new NBTTagCompound();
                outputSlots.setByte("OutputSlot", (byte) i);
                this.meatcubeOutput[i].writeToNBT(outputSlots);
                outputItems.appendTag(outputSlots);
            }
        }

        // Save the input and output items.
        nbt.setTag("InputItems", inputItems);
        nbt.setTag("OutputItems", outputItems);
    }
}

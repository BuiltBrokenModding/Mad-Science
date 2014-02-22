package madscience.tileentities.sanitizer;

import java.util.Random;

import madscience.MadConfig;
import madscience.MadFurnaces;
import madscience.MadNeedles;
import madscience.MadScience;
import madscience.MadSounds;
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
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SanitizerEntity extends MadTileEntity implements ISidedInventory, IFluidHandler
{
    private static final int[] slots_top = new int[]
    { 0 };

    private static final int[] slots_bottom = new int[]
    { 2, 1 };
    private static final int[] slots_sides = new int[]
    { 1 };
    /** The ItemStacks that hold the items currently being used in the furnace */
    private ItemStack[] sanitizerOutput = new ItemStack[2];

    private ItemStack[] sanitizerInput = new ItemStack[2];

    /** The number of ticks that a fresh copy of the currently-burning item would keep the furnace burning for */
    public int currentItemCookingMaximum;

    /** The number of ticks that the current item has been cooking for */
    public int currentItemCookingValue;

    // ** Maximum number of buckets of water this machine can hold internally */
    public static int MAX_WATER = FluidContainerRegistry.BUCKET_VOLUME * 10;

    /** Internal reserve of water */
    protected FluidTank internalWaterTank = new FluidTank(FluidRegistry.WATER, 0, MAX_WATER);

    /** Random number generator used to spit out food stuffs. */
    public Random animRand = new Random();

    /** Determines if we currently should be playing animation frames every tick or not. */
    public boolean shouldPlay;

    /** Current frame of animation we should use to display in world. */
    public int curFrame;

    /** Name to display on inventory screen. */
    private String containerCustomName;

    /** Path to texture that we want rendered onto our model. */
    public String sanitizerTexturePath = "models/" + MadFurnaces.SANTITIZER_INTERNALNAME + "/idle.png";

    public SanitizerEntity()
    {
        super(MadConfig.SANTITIZER_CAPACTITY, MadConfig.SANTITIZER_INPUT);
    }

    private boolean addBucketToInternalTank()
    {
        // Check if the input slot for filled buckets is null.
        if (this.sanitizerInput[0] == null)
        {
            // MadScience.logger.info("addBucketToInternalTank() aborted due to null filled water bucket slot.");
            return false;
        }

        // Output 1 - Empty bucket returns from full one in input slot 1
        // (used to clean the needle).
        ItemStack itemOutputSlot1 = new ItemStack(Item.bucketEmpty);
        ItemStack itemInputSlot1 = new ItemStack(Item.bucketWater);

        // Check if input slot 1 is a water bucket.
        if (!this.sanitizerInput[0].isItemEqual(itemInputSlot1))
        {
            // Input slot 1 was not a water bucket.
            MadScience.logger.info("addBucketToInternalTank() aborted due to item not being a filled water bucket.");
            return false;
        }

        // Check if output slot 1 (for empty buckets) is above item stack limit.
        if (this.sanitizerOutput[1] != null)
        {
            int slot1Result = sanitizerOutput[1].stackSize + itemInputSlot1.stackSize;
            boolean shouldStop = (slot1Result <= getInventoryStackLimit() && slot1Result <= itemInputSlot1.getMaxStackSize());
            if (shouldStop)
                return false;
        }

        // Check if the internal water tank has reached it
        if (internalWaterTank.getFluidAmount() >= internalWaterTank.getCapacity())
        {
            return false;
        }

        // Add empty water bucket to output slot 2 GUI.
        if (this.sanitizerOutput[1] == null)
        {
            this.sanitizerOutput[1] = itemOutputSlot1.copy();
        }
        else if (this.sanitizerOutput[1].isItemEqual(itemOutputSlot1))
        {
            sanitizerOutput[1].stackSize += itemOutputSlot1.stackSize;
        }

        // Add a bucket's worth of water into the internal tank.
        internalWaterTank.fill(new FluidStack(FluidRegistry.WATER, FluidContainerRegistry.BUCKET_VOLUME), true);
        MadScience.logger.info("internalWaterTank() " + internalWaterTank.getFluidAmount());

        // Remove a filled bucket of water from input stack 1.
        --this.sanitizerInput[0].stackSize;
        if (this.sanitizerInput[0].stackSize <= 0)
        {
            this.sanitizerInput[0] = null;
        }

        return false;
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
        // Check if we have water bucket and dirty needles in input slots and
        // that our internal tank has fluid.
        if (sanitizerInput[1] == null || internalWaterTank == null)
        {
            return false;
        }
        // Check if input slot 2 is a dirty needle stack.
        ItemStack itemsInputSlot2 = SanitizerRecipes.getSmeltingResult(this.sanitizerInput[1]);
        if (itemsInputSlot2 == null)
        {
            // Input slot 2 was not a dirty needle.
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
        if (this.sanitizerOutput[0] == null || this.sanitizerOutput[1] == null)
        {
            return true;
        }

        // Check if input slot 2 matches output slot 2.
        if (this.sanitizerInput[1].isItemEqual(sanitizerOutput[1]))
        {
            return false;
        }

        // Check if output slot 2 (for cleaned needles) is above item stack
        // limit.
        int slot2Result = sanitizerOutput[1].stackSize + itemsInputSlot2.stackSize;
        return (slot2Result <= getInventoryStackLimit() && slot2Result <= itemsInputSlot2.getMaxStackSize());
    }

    @Override
    public void closeChest()
    {
    }

    private ItemStack DecreaseInputSlot(int slot, int numItems)
    {
        // Removes items from input slot 1 or 2.
        if (this.sanitizerInput[slot] != null)
        {
            ItemStack itemstack;

            if (this.sanitizerInput[slot].stackSize <= numItems)
            {
                itemstack = this.sanitizerInput[slot];
                this.sanitizerInput[slot] = null;
                return itemstack;
            }
            itemstack = this.sanitizerInput[slot].splitStack(numItems);

            if (this.sanitizerInput[slot].stackSize == 0)
            {
                this.sanitizerInput[slot] = null;
            }

            return itemstack;
        }
        return null;
    }

    private ItemStack DecreaseOutputSlot(int slot, int numItems)
    {
        // Removes items from either output slot 1 or 2.
        if (this.sanitizerOutput[slot] != null)
        {
            ItemStack itemstack;

            if (this.sanitizerOutput[slot].stackSize <= numItems)
            {
                itemstack = this.sanitizerOutput[slot];
                this.sanitizerOutput[slot] = null;
                return itemstack;
            }
            itemstack = this.sanitizerOutput[slot].splitStack(numItems);

            if (this.sanitizerOutput[slot].stackSize == 0)
            {
                this.sanitizerOutput[slot] = null;
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
        else if (slot == 1)
        {
            // Input stack 2 - Dirty needle.
            return DecreaseInputSlot(1, numItems);
        }
        else if (slot == 2)
        {
            // Output stack 1 - Empty bucket.
            return DecreaseOutputSlot(0, numItems);
        }
        else if (slot == 3)
        {
            // Output stack 2 - Clean needle.
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
        return this.isInvNameLocalized() ? this.containerCustomName : "container.furnace";
    }

    /** Returns an integer between 0 and the passed value representing how close the current item is to being completely cooked */
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

    public int getSizeInputInventory()
    {
        return this.sanitizerInput.length;
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
        return sanitizerOutput.length;
    }

    /** Returns the stack in slot i */
    @Override
    public ItemStack getStackInSlot(int slot)
    {
        if (slot == 0)
        {
            // Input slot 1
            return this.sanitizerInput[0];
        }
        else if (slot == 1)
        {
            // Input slot 2
            return this.sanitizerInput[1];
        }
        else if (slot == 2)
        {
            // Output slot 1
            return this.sanitizerOutput[0];
        }
        else if (slot == 3)
        {
            // Output slot 2
            return this.sanitizerOutput[1];
        }

        MadScience.logger.info("getStackInSlot() could not return valid stack from slot " + slot);
        return null;
    }

    /** When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem - like when you close a workbench GUI. */
    @Override
    public ItemStack getStackInSlotOnClosing(int slot)
    {
        // Input slots.
        if (slot == 0)
        {
            // Input slot 1 - Water bucket.
            if (this.sanitizerInput[0] != null)
            {
                ItemStack itemstack = this.sanitizerInput[0];
                this.sanitizerInput[0] = null;
                return itemstack;
            }
            return null;
        }
        else if (slot == 1)
        {
            // Input slot 2 - Dirty needles.
            if (this.sanitizerInput[1] != null)
            {
                ItemStack itemstack = this.sanitizerInput[1];
                this.sanitizerInput[1] = null;
                return itemstack;
            }
            return null;
        }
        else if (slot == 2)
        {
            // Output slot 1 - Empty bucket.
            if (this.sanitizerOutput[0] != null)
            {
                ItemStack itemstack = this.sanitizerOutput[0];
                this.sanitizerOutput[0] = null;
                return itemstack;
            }
            return null;
        }
        else if (slot == 3)
        {
            // Output slot 2 - Clean needles.
            if (this.sanitizerOutput[1] != null)
            {
                ItemStack itemstack = this.sanitizerOutput[1];
                this.sanitizerOutput[1] = null;
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

    /** If this returns false, the inventory name will be used as an unlocalized name, and translated into the player's language. Otherwise it will be used directly. */
    @Override
    public boolean isInvNameLocalized()
    {
        return this.containerCustomName != null && this.containerCustomName.length() > 0;
    }

    /** Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot. */
    @Override
    public boolean isItemValidForSlot(int slot, ItemStack items)
    {
        // Check if machine trying to insert item into given slot is allowed.
        if (slot == 0)
        {
            // Input slot 1 - water bucket.
            ItemStack compareWaterBucket = new ItemStack(Item.bucketWater);
            if (compareWaterBucket.isItemEqual(items))
            {
                return true;
            }
        }
        else if (slot == 1)
        {
            // Input slot 2 - dirty needle.
            ItemStack compareDirtyNeedle = new ItemStack(MadNeedles.NEEDLE_DIRTY);
            if (compareDirtyNeedle.isItemEqual(items))
            {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean isPowered()
    {
        return this.getEnergy(ForgeDirection.UNKNOWN) > 0;
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
        this.sanitizerInput = new ItemStack[this.getSizeInputInventory()];
        this.sanitizerOutput = new ItemStack[this.getSizeOutputInventory()];

        // Input items process.
        for (int i = 0; i < nbtInput.tagCount(); ++i)
        {
            NBTTagCompound inputSaveData = (NBTTagCompound) nbtInput.tagAt(i);
            byte b0 = inputSaveData.getByte("InputSlot");

            if (b0 >= 0 && b0 < this.sanitizerInput.length)
            {
                this.sanitizerInput[b0] = ItemStack.loadItemStackFromNBT(inputSaveData);
            }
        }

        // Output items process.
        for (int i = 0; i < nbtOutput.tagCount(); ++i)
        {
            NBTTagCompound outputSaveData = (NBTTagCompound) nbtOutput.tagAt(i);
            byte b0 = outputSaveData.getByte("OutputSlot");

            if (b0 >= 0 && b0 < this.sanitizerOutput.length)
            {
                this.sanitizerOutput[b0] = ItemStack.loadItemStackFromNBT(outputSaveData);
            }
        }

        this.currentItemCookingValue = nbt.getShort("CookTime");
        // Should play animation status.
        this.shouldPlay = nbt.getBoolean("ShouldPlay");

        // Current frame of animation we are displaying.
        this.curFrame = nbt.getInteger("CurrentFrame");

        // Path to current texture what should be loaded onto the model.
        this.sanitizerTexturePath = nbt.getString("TexturePath");

        this.internalWaterTank.setFluid(new FluidStack(FluidRegistry.WATER, nbt.getShort("WaterAmount")));

        if (nbt.hasKey("CustomName"))
        {
            this.containerCustomName = nbt.getString("CustomName");
        }
    }

    /** Sets the custom display name to use when opening a GUI linked to this tile entity. */
    public void setGuiDisplayName(String par1Str)
    {
        this.containerCustomName = par1Str;
    }

    /** Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections). */
    @Override
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
    {
        if (par1 == 0)
        {
            this.sanitizerInput[0] = par2ItemStack;
        }
        else if (par1 == 1)
        {
            this.sanitizerInput[1] = par2ItemStack;
        }
        else if (par1 == 2)
        {
            this.sanitizerOutput[0] = par2ItemStack;
        }
        else if (par1 == 3)
        {
            this.sanitizerOutput[1] = par2ItemStack;
        }

        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
        {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }
    }

    public void smeltItem()
    {
        // Converts input item into result item along with waste items.
        if (this.canSmelt())
        {
            // Output 2 - Cleaned needle that used to be dirt input slot 2.
            ItemStack itemOutputSlot2 = SanitizerRecipes.getSmeltingResult(this.sanitizerInput[1]);

            // Add cleaned needle to output slot 1 GUI.
            if (this.sanitizerOutput[0] == null)
            {
                this.sanitizerOutput[0] = itemOutputSlot2.copy();
            }
            else if (this.sanitizerOutput[0].isItemEqual(itemOutputSlot2))
            {
                sanitizerOutput[0].stackSize += itemOutputSlot2.stackSize;
            }

            // Remove a dirty needle from input stack 2.
            --this.sanitizerInput[1].stackSize;
            if (this.sanitizerInput[1].stackSize <= 0)
            {
                this.sanitizerInput[1] = null;
            }
        }
    }

    /** Update current animation that we should be playing on this tile entity. */
    private void updateAnimation()
    {
        // Active state has many textures based on item cook progress.
        if (canSmelt() && isPowered())
        {
            if (curFrame <= 9 && worldObj.getWorldTime() % 15L == 0L)
            {
                // Load this texture onto the entity.
                sanitizerTexturePath = "models/" + MadFurnaces.SANTITIZER_INTERNALNAME + "/work_" + curFrame + ".png";

                // Update animation frame.
                ++curFrame;
            }
            else if (curFrame >= 10)
            {
                // Check if we have exceeded the ceiling and need to reset.
                curFrame = 0;
            }
        }
        else
        {
            // Idle state single texture.
            sanitizerTexturePath = "models/" + MadFurnaces.SANTITIZER_INTERNALNAME + "/idle.png";
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
            this.consumeEnergy(MadConfig.SANTITIZER_CONSUME);

            // Decrease the amount of water in the blocks internal storage.
            internalWaterTank.drain(1, true);
        }

        // Update status of the machine if it has redstone power or not.
        checkRedstonePower();

        // Server side processing for furnace.
        if (!this.worldObj.isRemote)
        {
            // Update block animation and model.
            this.updateAnimation();

            // Play sound while we are cleaning them needles!
            this.updateSound();

            // Checks to see if we can add a bucket from input slot into
            // internal tank.
            this.addBucketToInternalTank();

            // First tick for new item being cooked in furnace.
            if (this.currentItemCookingValue == 0 && this.canSmelt() && this.isPowered())
            {
                // New item pulled from cooking stack to be processed, check how
                // long this item will take to cook.
                currentItemCookingMaximum = 200;

                // Increments the timer to kickstart the cooking loop.
                this.currentItemCookingValue++;
            }
            else if (this.currentItemCookingValue > 0 && this.canSmelt() && this.isPowered())
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

            // Send update about tile entity to all players around us.
            PacketDispatcher.sendPacketToAllAround(this.xCoord, this.yCoord, this.zCoord, MadConfig.PACKETSEND_RADIUS, worldObj.provider.dimensionId, new SanitizerPackets(this.xCoord, this.yCoord, this.zCoord, currentItemCookingValue, currentItemCookingMaximum,
                    getEnergy(ForgeDirection.UNKNOWN), getEnergyCapacity(ForgeDirection.UNKNOWN), this.internalWaterTank.getFluidAmount(), this.internalWaterTank.getCapacity(), this.sanitizerTexturePath).makePacket());
        }

        if (inventoriesChanged)
        {
            this.onInventoryChanged();
        }
    }

    private void updateSound()
    {
        // Check if we should be playing working sounds.
        if (this.canSmelt() && this.isPowered() && worldObj.getWorldTime() % (MadScience.SECOND_IN_TICKS * 3.0F) == 0L)
        {
            this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, MadSounds.SANTITIZER_IDLE, 1.0F, 1.0F);
        }
    }

    /** Writes a tile entity to NBT. */
    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);

        // Amount of time left to cook current item inside of the furnace (if
        // there is one).
        nbt.setShort("CookTime", (short) this.currentItemCookingValue);

        // Amount of water that is currently stored inside of the machines
        // internal reserves.
        nbt.setShort("WaterAmount", (short) this.internalWaterTank.getFluidAmount());

        // Should play animation status.
        nbt.setBoolean("ShouldPlay", this.shouldPlay);

        // Current frame of animation we are displaying.
        nbt.setInteger("CurrentFrame", this.curFrame);

        // Path to current texture that should be loaded onto the model.
        nbt.setString("TexturePath", this.sanitizerTexturePath);

        // Two tag lists for each type of items we have in this entity.
        NBTTagList inputItems = new NBTTagList();
        NBTTagList outputItems = new NBTTagList();

        // Input slots.
        for (int i = 0; i < this.sanitizerInput.length; ++i)
        {
            if (this.sanitizerInput[i] != null)
            {
                NBTTagCompound inputSlots = new NBTTagCompound();
                inputSlots.setByte("InputSlot", (byte) i);
                this.sanitizerInput[i].writeToNBT(inputSlots);
                inputItems.appendTag(inputSlots);
            }
        }

        // Output slots.
        for (int i = 0; i < this.sanitizerOutput.length; ++i)
        {
            if (this.sanitizerOutput[i] != null)
            {
                NBTTagCompound outputSlots = new NBTTagCompound();
                outputSlots.setByte("OutputSlot", (byte) i);
                this.sanitizerOutput[i].writeToNBT(outputSlots);
                outputItems.appendTag(outputSlots);
            }
        }

        // Save the input and output items.
        nbt.setTag("InputItems", inputItems);
        nbt.setTag("OutputItems", outputItems);

        if (this.isInvNameLocalized())
        {
            nbt.setString("CustomName", this.containerCustomName);
        }
    }
}

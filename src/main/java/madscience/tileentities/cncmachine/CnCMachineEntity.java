package madscience.tileentities.cncmachine;

import madscience.MadConfig;
import madscience.MadFurnaces;
import madscience.MadScience;
import madscience.factory.tileentity.MadTileEntity;
import madscience.network.MadParticlePacket;
import madscience.util.MadUtils;
import net.minecraft.block.Block;
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

public class CnCMachineEntity extends MadTileEntity implements ISidedInventory, IFluidHandler
{
    // ** Maximum number of buckets of water this machine can hold internally */
    private static int MAX_WATER = FluidContainerRegistry.BUCKET_VOLUME * 10;

    private static final int[] slots_bottom = new int[]
    { 2, 1 };
    private static final int[] slots_sides = new int[]
    { 1 };

    private static final int[] slots_top = new int[]
    { 0 };

    /** If we start smelting we want client GUI's to know what part they are making for effect. */
    String BOOK_DECODED = "INVALID BOOK";

    private ItemStack[] CnCMachineInput = new ItemStack[3];

    /** The ItemStacks that hold the items currently being used in the furnace */
    private ItemStack[] CnCMachineOutput = new ItemStack[2];

    /** Current frame of animation we should use to display in world. */
    private int curFrame;

    /** The number of ticks that a fresh copy of the currently-burning item would keep the furnace burning for */
    int currentItemCookingMaximum;

    /** The number of ticks that the current item has been cooking for */
    int currentItemCookingValue;

    /** Determines if we have iron block currently placed in input slot 1. */
    boolean hasIronBlock;

    /** Texture that should be displayed on our model. */
    String TEXTURE = "models/" + MadFurnaces.SONICLOCATOR_INTERNALNAME + "/off.png";

    /** Internal reserve of water */
    protected FluidTank WATER_TANK = new FluidTank(FluidRegistry.WATER, 0, MAX_WATER);
    
    // Sound tracking variables.
    boolean clientSound_FinishedCrushing;
    boolean clientSound_InsertIronBlock;
    boolean clientSound_InvalidBook;
    boolean clientSound_PowerOn;
    boolean clientSound_PressStop;

    public CnCMachineEntity()
    {
        super(MadFurnaces.CNCMACHINE_INTERNALNAME);
    }

    private boolean addBucketToInternalTank()
    {
        // Check if the input slot for filled buckets is null.
        if (this.CnCMachineInput[0] == null)
        {
            return false;
        }

        // Output 1 - Empty bucket returns from full one in input slot 1
        ItemStack compareEmptyBucket = new ItemStack(Item.bucketEmpty);
        ItemStack compareFilledBucket = new ItemStack(Item.bucketWater);
        if (!this.CnCMachineInput[0].isItemEqual(compareFilledBucket))
        {
            MadScience.logger.info("addBucketToInternalTank() aborted due to item not being a filled water bucket.");
            return false;
        }

        // Check if output slot 1 (for empty buckets) is above item stack limit.
        if (this.CnCMachineOutput[1] != null)
        {
            int slot1Result = CnCMachineOutput[1].stackSize + compareFilledBucket.stackSize;
            boolean shouldStop = (slot1Result <= getInventoryStackLimit() && slot1Result <= compareFilledBucket.getMaxStackSize());
            if (shouldStop)
                return false;
        }

        // Check if the internal water tank has reached it
        if (WATER_TANK.getFluidAmount() >= WATER_TANK.getCapacity())
        {
            return false;
        }

        // Add empty water bucket to output slot 2 GUI.
        if (this.CnCMachineOutput[1] == null)
        {
            this.CnCMachineOutput[1] = compareEmptyBucket.copy();
        }
        else if (this.CnCMachineOutput[1].isItemEqual(compareEmptyBucket))
        {
            CnCMachineOutput[1].stackSize += compareEmptyBucket.stackSize;
        }

        // Add a bucket's worth of water into the internal tank.
        WATER_TANK.fill(new FluidStack(FluidRegistry.WATER, FluidContainerRegistry.BUCKET_VOLUME), true);
        // MadScience.logger.info("internalWaterTank() " + WATER_TANK.getFluidAmount());

        // Remove a filled bucket of water from input stack 1.
        --this.CnCMachineInput[0].stackSize;
        if (this.CnCMachineInput[0].stackSize <= 0)
        {
            this.CnCMachineInput[0] = null;
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
            // Weapon component.
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

    @Override
    public boolean canInsertItem(int slot, ItemStack items, int side)
    {
        return this.isItemValidForSlot(slot, items);
    }

    @Override
    public boolean canSmelt()
    {
        // Check if we have a block of iron in input slot 2.
        if (!this.hasIronBlock())
        {
            return false;
        }

        // Check if there is anything already in the output slot.
        if (this.CnCMachineOutput[0] != null && this.CnCMachineOutput[0].stackSize >= 1)
        {
            //MadScience.logger.info(String.valueOf(this.CnCMachineOutput[0].stackSize));
            return false;
        }

        // Check if we have redstone power.
        if (!this.isRedstonePowered())
        {
            return false;
        }

        // Check if we have a written book in input slot 3.
        if (CnCMachineInput[2] == null)
        {
            return false;
        }

        // Check if there is fluid inside our internal tank.
        if (WATER_TANK != null && WATER_TANK.getFluidAmount() <= 0)
        {
            this.BOOK_DECODED = "NEED WATER";
            return false;
        }

        return true;
    }

    @Override
    public void closeChest()
    {
    }

    private ItemStack DecreaseInputSlot(int slot, int numItems)
    {
        // Removes items from input slot 1 or 2.
        if (this.CnCMachineInput[slot] != null)
        {
            ItemStack itemstack;

            if (this.CnCMachineInput[slot].stackSize <= numItems)
            {
                itemstack = this.CnCMachineInput[slot];
                this.CnCMachineInput[slot] = null;
                return itemstack;
            }
            itemstack = this.CnCMachineInput[slot].splitStack(numItems);

            if (this.CnCMachineInput[slot].stackSize == 0)
            {
                this.CnCMachineInput[slot] = null;
            }

            return itemstack;
        }
        return null;
    }

    private ItemStack DecreaseOutputSlot(int slot, int numItems)
    {
        // Removes items from either output slot 1 or 2.
        if (this.CnCMachineOutput[slot] != null)
        {
            ItemStack itemstack;

            if (this.CnCMachineOutput[slot].stackSize <= numItems)
            {
                itemstack = this.CnCMachineOutput[slot];
                this.CnCMachineOutput[slot] = null;
                return itemstack;
            }
            itemstack = this.CnCMachineOutput[slot].splitStack(numItems);

            if (this.CnCMachineOutput[slot].stackSize == 0)
            {
                this.CnCMachineOutput[slot] = null;
            }

            return itemstack;
        }
        return null;
    }

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
            // Input stack 2 - Iron Block.
            return DecreaseInputSlot(1, numItems);
        }
        else if (slot == 2)
        {
            // Input stack 3 - Written book.
            return DecreaseInputSlot(2, numItems);
        }
        else if (slot == 3)
        {
            // Output stack 1 - Empty bucket.
            return DecreaseOutputSlot(0, numItems);
        }
        else if (slot == 4)
        {
            // Output stack 2 - Weapon component.
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

    private void drainEnergy(boolean drainWater)
    {
        if (this.isPowered() && this.canSmelt() && this.isRedstonePowered())
        {
            // Decrease to amount of energy this item has on client and server.
            this.consumeEnergy(MadConfig.CNCMACHINE_CONSUME);

            if (drainWater)
            {
                // Decrease the amount of water in the blocks internal storage.
                WATER_TANK.drain(1, true);
            }
        }
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
    {
        // Ensure that the incoming resource is not null for safety sake.
        if (resource != null)
        {
            return WATER_TANK.fill(resource, doFill);
        }

        // Default response is to not fill anything.
        return 0;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int par1)
    {
        return par1 == 0 ? slots_bottom : (par1 == 1 ? slots_top : slots_sides);
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public String getMachineInternalName()
    {
        return MadFurnaces.CNCMACHINE_INTERNALNAME;
    }

    public ItemStack getItemFromBookContents()
    {
        // Retrieve the unencoded binary string contents from the book.
        String bookContents = this.getStringFromBookContents();

        // Decode the binary string into plain text.

        ItemStack smeltingResult = CnCMachineRecipes.getSmeltingResult(bookContents);
        if (smeltingResult == null)
        {
            return null;
        }

        return smeltingResult;
    }

    public int getSizeInputInventory()
    {
        return this.CnCMachineInput.length;
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
        return CnCMachineOutput.length;
    }

    /** Returns the stack in slot i */
    @Override
    public ItemStack getStackInSlot(int slot)
    {
        if (slot == 0)
        {
            // Input slot 1 - Water Bucket.
            return this.CnCMachineInput[0];
        }
        else if (slot == 1)
        {
            // Input slot 2 - Iron Block.
            return this.CnCMachineInput[1];
        }
        else if (slot == 2)
        {
            // Input slot 3 - Written Book.
            return this.CnCMachineInput[2];
        }
        else if (slot == 3)
        {
            // Output slot 1 - Empty Bucket.
            return this.CnCMachineOutput[0];
        }
        else if (slot == 4)
        {
            // Output slot 2 - Weapon Component.
            return this.CnCMachineOutput[1];
        }

        MadScience.logger.info("getStackInSlot() could not return valid stack from slot " + slot);
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot)
    {
        // Input slots.
        if (slot == 0)
        {
            // Input slot 1 - Water bucket.
            if (this.CnCMachineInput[0] != null)
            {
                ItemStack itemstack = this.CnCMachineInput[0];
                this.CnCMachineInput[0] = null;
                return itemstack;
            }
            return null;
        }
        else if (slot == 1)
        {
            // Input slot 2 - Iron Block.
            if (this.CnCMachineInput[1] != null)
            {
                ItemStack itemstack = this.CnCMachineInput[1];
                this.CnCMachineInput[1] = null;
                return itemstack;
            }
            return null;
        }
        else if (slot == 2)
        {
            // Input slot 3 - Written Book.
            if (this.CnCMachineInput[2] != null)
            {
                ItemStack itemstack = this.CnCMachineInput[2];
                this.CnCMachineInput[2] = null;
                return itemstack;
            }
            return null;
        }
        else if (slot == 3)
        {
            // Output slot 1 - Empty bucket.
            if (this.CnCMachineOutput[0] != null)
            {
                ItemStack itemstack = this.CnCMachineOutput[0];
                this.CnCMachineOutput[0] = null;
                return itemstack;
            }
            return null;
        }
        else if (slot == 4)
        {
            // Output slot 2 - Weapon Component.
            if (this.CnCMachineOutput[1] != null)
            {
                ItemStack itemstack = this.CnCMachineOutput[1];
                this.CnCMachineOutput[1] = null;
                return itemstack;
            }
            return null;
        }

        return null;
    }

    public String getStringFromBookContents()
    {
        // Depending on client or server we will get info from another place.
        String bookContents = null;
        if (this.worldObj != null && !this.worldObj.isRemote)
        {
            // SERVER

            // Check that the written book exists for us to examine.
            if (this.CnCMachineInput == null)
            {
                return null;
            }

            if (this.CnCMachineInput[2] == null)
            {
                return null;
            }

            if (this.CnCMachineInput[2].stackTagCompound == null)
            {
                return null;
            }

            // Retrieve the unencoded binary string contents from the book.
            bookContents = MadUtils.getWrittenBookContents(this.CnCMachineInput[2].stackTagCompound);
            if (bookContents == null)
            {
                return null;
            }
        }
        else if (this.worldObj != null && this.worldObj.isRemote)
        {
            // CLIENT

            if (!this.BOOK_DECODED.isEmpty())
            {
                // User has entered a valid schematic into the device.
                bookContents = this.BOOK_DECODED;
                if (bookContents == null)
                {
                    return null;
                }
            }
            else
            {
                // User has entered a invalid schematic into the device.
                bookContents = "INVALID BOOK";
            }
        }

        return bookContents;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from)
    {
        return new FluidTankInfo[]
        { WATER_TANK.getInfo() };
    }

    @SideOnly(Side.CLIENT) 
    int getWaterRemainingScaled(int i)
    {
        return WATER_TANK.getFluid() != null ? (int) (((float) WATER_TANK.getFluid().amount / (float) (MAX_WATER)) * i) : 0;
    }

    private boolean hasIronBlock()
    {
        // Null checks.
        if (this.CnCMachineInput == null)
        {
            this.clientSound_InsertIronBlock = false;
            return false;
        }

        if (this.CnCMachineInput[1] == null)
        {
            this.clientSound_InsertIronBlock = false;
            return false;
        }

        // Compare iron block.
        ItemStack compareIronBlock = new ItemStack(Block.blockIron);
        if (this.CnCMachineInput[1].isItemEqual(compareIronBlock))
        {
            return true;
        }

        // Default response.
        this.clientSound_InsertIronBlock = false;
        return false;
    }

    @Override
    public boolean isInvNameLocalized()
    {
        return true;
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack items)
    {
        // Check if machine trying to insert item into given slot is allowed.
        if (slot == 0)
        {
            // Input slot 1 - Water Bucket.
            ItemStack compareWaterBucket = new ItemStack(Item.bucketWater);
            if (compareWaterBucket.isItemEqual(items))
            {
                return true;
            }
        }
        else if (slot == 1)
        {
            // Input slot 2 - Iron Block.
            ItemStack compareIronBlock = new ItemStack(Block.blockIron);
            if (compareIronBlock.isItemEqual(items))
            {
                return true;
            }
        }
        else if (slot == 2)
        {
            // Input slot 3 - Written Book.
            ItemStack compareWrittenBook = new ItemStack(Item.writtenBook);
            if (compareWrittenBook.isItemEqual(items))
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

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);

        // Read input/output items from world save data.
        NBTTagList nbtInput = nbt.getTagList("InputItems");
        NBTTagList nbtOutput = nbt.getTagList("OutputItems");

        // Cast the save data onto our running object.
        this.CnCMachineInput = new ItemStack[this.getSizeInputInventory()];
        this.CnCMachineOutput = new ItemStack[this.getSizeOutputInventory()];

        // Input items process.
        for (int i = 0; i < nbtInput.tagCount(); ++i)
        {
            NBTTagCompound inputSaveData = (NBTTagCompound) nbtInput.tagAt(i);
            byte b0 = inputSaveData.getByte("InputSlot");

            if (b0 >= 0 && b0 < this.CnCMachineInput.length)
            {
                this.CnCMachineInput[b0] = ItemStack.loadItemStackFromNBT(inputSaveData);
            }
        }

        // Output items process.
        for (int i = 0; i < nbtOutput.tagCount(); ++i)
        {
            NBTTagCompound outputSaveData = (NBTTagCompound) nbtOutput.tagAt(i);
            byte b0 = outputSaveData.getByte("OutputSlot");

            if (b0 >= 0 && b0 < this.CnCMachineOutput.length)
            {
                this.CnCMachineOutput[b0] = ItemStack.loadItemStackFromNBT(outputSaveData);
            }
        }

        // Amount of time we have been cutting the iron block.
        this.currentItemCookingValue = nbt.getShort("CookTime");

        // Amount of fluid that is stored inside of our tank.
        this.WATER_TANK.setFluid(new FluidStack(FluidRegistry.WATER, nbt.getShort("WaterAmount")));

        // Current frame of animation we are displaying.
        this.curFrame = nbt.getInteger("CurrentFrame");

        // Path to current texture what should be loaded onto the model.
        this.TEXTURE = nbt.getString("TexturePath");

        // Determines if we have iron block installed inside us.
        this.hasIronBlock = nbt.getBoolean("hasIronBlock");
        
        // Sound tracking.
        this.clientSound_FinishedCrushing = nbt.getBoolean("FinishCrushing");
        this.clientSound_InsertIronBlock = nbt.getBoolean("InsertIronBlock");
        this.clientSound_InvalidBook = nbt.getBoolean("InvalidBook");
        this.clientSound_PowerOn = nbt.getBoolean("PowerOn");
        this.clientSound_PressStop = nbt.getBoolean("PressStop");
    }

    @Override
    public void setInventorySlotContents(int slotNumber, ItemStack item)
    {
        if (slotNumber == 0)
        {
            // Input Slot 1 - Water Bucket.
            this.CnCMachineInput[0] = item;
        }
        else if (slotNumber == 1)
        {
            // Input Slot 2 - Iron Block.
            this.CnCMachineInput[1] = item;
        }
        else if (slotNumber == 2)
        {
            // Input Slot 3 - Written Book.
            this.CnCMachineInput[2] = item;
        }
        else if (slotNumber == 3)
        {
            // Output Slot 1 - Empty Bucket.
            this.CnCMachineOutput[0] = item;
        }
        else if (slotNumber == 4)
        {
            // Output Slot 2 - Weapon Component.
            this.CnCMachineOutput[1] = item;
        }

        // If stack size is greater than maximum amount then just set it to stack limit.
        if (item != null && item.stackSize > this.getInventoryStackLimit())
        {
            item.stackSize = this.getInventoryStackLimit();
        }
    }

    @Override
    public void smeltItem()
    {
        // Converts input item into resulting weapon component part.
        if (this.canSmelt())
        {
            // Output 2 - Weapon component that was crafted from the iron block.
            ItemStack smeltingResult = getItemFromBookContents();
            if (smeltingResult == null)
            {
                // MadScience.logger.info("CnC Machine: Could not complete smelting process, could not cast binary text to itemstack.");
                return;
            }
            
            // Play sound effect of the machine being finished and happy about it.
            this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, CnCMachineSounds.CNCMACHINE_FINISHED, 1.0F, 1.0F);

            // Adds the smelted weapon component into the output slot.
            if (this.CnCMachineOutput[0] == null)
            {
                this.CnCMachineOutput[0] = smeltingResult.copy();
            }
            else if (this.CnCMachineOutput[0].isItemEqual(smeltingResult))
            {
                CnCMachineOutput[0].stackSize += smeltingResult.stackSize;
            }

            // Removes the source iron block used in the construction of the weapon part.
            --this.CnCMachineInput[1].stackSize;
            if (this.CnCMachineInput[1].stackSize <= 0)
            {
                this.CnCMachineInput[1] = null;
            }
        }
    }

    private void updateAnimation(int cookTimeScaled)
    {
        // Active state has many textures based on item cook progress.
        if (this.canSmelt() && this.isPowered() && this.isRedstonePowered() && cookTimeScaled > 7)
        {
            // Play particle effect of water splashing down.
            PacketDispatcher.sendPacketToAllAround(this.xCoord, this.yCoord, this.zCoord, MadConfig.PACKETSEND_RADIUS, worldObj.provider.dimensionId, new MadParticlePacket("splash", 0.5D + this.xCoord, this.yCoord + 0.5D, this.zCoord + 0.5D,
                    this.worldObj.rand.nextFloat(), this.worldObj.rand.nextFloat() + 0.5F, this.worldObj.rand.nextFloat()).makePacket());

            // Plays water jet-stream animation to look like iron block is being cut into shape.
            if (curFrame <= 6 && worldObj.getWorldTime() % 15L == 0L)
            {
                // Load this texture onto the entity.
                TEXTURE = "models/" + MadFurnaces.CNCMACHINE_INTERNALNAME + "/work" + curFrame + ".png";

                // Update animation frame.
                ++curFrame;
            }
            else if (curFrame >= 7)
            {
                // Check if we have exceeded the ceiling and need to reset.
                curFrame = 0;
            }
        }
        else if (this.canSmelt() && this.isPowered() && this.isRedstonePowered() && cookTimeScaled < 7)
        {
            TEXTURE = "models/" + MadFurnaces.CNCMACHINE_INTERNALNAME + "/ready.png";
        }
        else if (!this.canSmelt() && this.isPowered() && !this.isRedstonePowered())
        {
            TEXTURE = "models/" + MadFurnaces.CNCMACHINE_INTERNALNAME + "/powered.png";
        }
        else if (!this.canSmelt() && this.isPowered() && this.isRedstonePowered())
        {
            TEXTURE = "models/" + MadFurnaces.CNCMACHINE_INTERNALNAME + "/ready.png";
        }
        else
        {
            // Idle state single texture.
            TEXTURE = "models/" + MadFurnaces.CNCMACHINE_INTERNALNAME + "/off.png";
        }
    }

    /** Allows the entity to update its state. */
    @Override
    public void updateEntity()
    {
        // Important to call the class below us!
        super.updateEntity();

        boolean inventoriesChanged = false;

        // Drain energy if we are operating but not fluid.
        this.drainEnergy(false);

        // Update status of the machine if it has redstone power or not.
        this.checkRedstonePower();

        // Server side processing for furnace.
        if (!this.worldObj.isRemote)
        {
            // Checks to see if we can add a bucket from input slot.
            this.addBucketToInternalTank();

            // Get the scaled percentage of our progress based on how many steps we have.
            int cookTimeScaled = this.getItemCookTimeScaled(17);

            // First tick for new item being cooked in furnace.
            if (this.currentItemCookingValue == 0 && this.canSmelt() && this.isPowered())
            {
                ItemStack smeltingResult = getItemFromBookContents();
                if (smeltingResult == null)
                {
                    this.BOOK_DECODED = "INVALID BOOK";
                    this.currentItemCookingValue = 0;
                    this.clientSound_FinishedCrushing = false;
                    this.clientSound_PressStop = false;
                }
                else
                {                    
                    // For displaying part name in the GUI for clients.
                    this.BOOK_DECODED = getStringFromBookContents();
                    
                    // We know the book inserted is good.
                    this.clientSound_InvalidBook = false;

                    // New item pulled from cooking stack to be processed.
                    currentItemCookingMaximum = 1000;

                    // Increments the timer to kickstart the cooking loop.
                    this.currentItemCookingValue++;

                    // Drain only power at this time.
                    this.drainEnergy(false);
                }
            }
            else if (this.currentItemCookingValue > 0 && this.canSmelt() && this.isPowered())
            {
                // Run on server when we have items and electrical power.
                // Note: This is the main work loop for the block!

                // Drain only power until the water is turned on.
                this.drainEnergy(cookTimeScaled >= 7);

                // Increments the timer to kickstart the cooking loop.
                this.currentItemCookingValue++;

                // Check if furnace has exceeded total amount of time to cook.
                if (this.currentItemCookingValue >= currentItemCookingMaximum)
                {
                    // Convert one item into another via 'cooking' process.
                    this.smeltItem();
                    this.clientSound_FinishedCrushing = false;
                    this.clientSound_PressStop = false;
                    this.currentItemCookingValue = 0;
                    inventoriesChanged = true;
                }
            }
            else
            {
                // Reset loop, prepare for next item or closure.
                this.currentItemCookingValue = 0;
                this.clientSound_PressStop = false;
                this.clientSound_FinishedCrushing = false;
            }
            
            // Change the reference to texture we should be displaying.
            this.updateAnimation(cookTimeScaled);
            
            // Play sounds on given intervals or as background noise.
            this.updateSound(cookTimeScaled);

            // Send update about tile entity to all players around us.
            PacketDispatcher.sendPacketToAllAround(this.xCoord, this.yCoord, this.zCoord, MadConfig.PACKETSEND_RADIUS, worldObj.provider.dimensionId,
                    new CnCMachinePackets(this.xCoord, this.yCoord, this.zCoord,
                            currentItemCookingValue, currentItemCookingMaximum,
                            getEnergy(ForgeDirection.UNKNOWN), getEnergyCapacity(ForgeDirection.UNKNOWN),
                            this.WATER_TANK.getFluidAmount(), this.WATER_TANK.getCapacity(),
                            this.BOOK_DECODED,
                            this.TEXTURE,
                            this.hasIronBlock(),
                            this.clientSound_FinishedCrushing,
                            this.clientSound_InsertIronBlock,
                            this.clientSound_InvalidBook,
                            this.clientSound_PowerOn,
                            this.clientSound_PressStop)
                    .makePacket());
        }

        if (inventoriesChanged)
        {
            this.onInventoryChanged();
        }
    }

    private void updateSound(int cookTimeScaled)
    {
        //MadScience.logger.info("Cook Time Scaled: " + String.valueOf(cookTimeScaled));
        
        // Play sound of a iron block being inserted into the machine.
        if (this.hasIronBlock() && !this.clientSound_InsertIronBlock)
        {
            this.clientSound_InsertIronBlock = true;
            this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, CnCMachineSounds.CNCMACHINE_INSERTIRONBLOCK, 0.5F, 1.0F);
        }
        
        // Play a sound of redstone signal activating the machine and making it active.
        if (this.isRedstonePowered() && !this.clientSound_PowerOn)
        {
            this.clientSound_PowerOn = true;
            this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, CnCMachineSounds.CNCMACHINE_POWERON, 0.5F, 1.0F);
        }
        else if (!this.isRedstonePowered() && this.clientSound_PowerOn)
        {
            // Power was set to being on while it was off. This resets it!
            this.clientSound_PowerOn = false;
            
            // Since the machine lost it's current power state we reset this alarm.
            this.clientSound_InvalidBook = false;
        }
        
        // The next batch of sounds are only played while the machine is operational.
        if (this.isPowered() && this.isRedstonePowered() && this.canSmelt())
        {
            // Play sound telling player inserted book is invalid.
            if (this.BOOK_DECODED.contains("INVALID") && !this.clientSound_InvalidBook)
            {
                this.clientSound_InvalidBook = true;
                this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, CnCMachineSounds.CNCMACHINE_INVALIDBOOK, 0.5F, 1.0F);
            }
            else if (!this.BOOK_DECODED.contains("INVALID") && this.clientSound_InvalidBook)
            {
                this.clientSound_InvalidBook = false;
            }
            
            // --------------
            // CRUSHING PHASE
            // --------------
            if (cookTimeScaled <= 6 && this.currentItemCookingValue > 0 && !this.BOOK_DECODED.contains("INVALID"))
            {
                // Background sound played while crushing iron block every 3 seconds.
                if (worldObj.getWorldTime() % MadScience.SECOND_IN_TICKS * 3L == 0L)
                {
                    this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, CnCMachineSounds.CNCMACHINE_PRESSINGWORK, 0.5F, 1.0F);
                }
                
                // Played while iron block is being pressed every 2 seconds.
                if (cookTimeScaled < 4 && worldObj.getWorldTime() % MadScience.SECOND_IN_TICKS * 2L == 0L)
                {
                    this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, CnCMachineSounds.CNCMACHINE_PRESS, 0.5F, 1.0F);
                }
                
                // Play a sound to indicate the end of the crushing phase.
                if (cookTimeScaled == 4 && !this.clientSound_PressStop)
                {
                    this.clientSound_PressStop = true;
                    this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, CnCMachineSounds.CNCMACHINE_PRESSSTOP, 1.0F, 1.0F);
                    
                    // Spawn smoke particles to simulate the block being heated from all the pressure.
                    PacketDispatcher.sendPacketToAllAround(this.xCoord, this.yCoord, this.zCoord, MadConfig.PACKETSEND_RADIUS, worldObj.provider.dimensionId, new MadParticlePacket("largesmoke", 0.5D + this.xCoord, this.yCoord + 0.666D, this.zCoord + 0.5D, worldObj.rand.nextFloat(),
                            worldObj.rand.nextFloat() + 3.0D, worldObj.rand.nextFloat()).makePacket());
                }
            }
            
            // -------------------
            // WATER CUTTING PHASE
            // -------------------
            if (cookTimeScaled >= 7 && this.currentItemCookingValue <= this.currentItemCookingMaximum)
            {
                // Play a sound to indicate the crusher is powering down and water jets are powering on.
                if (cookTimeScaled == 7 && !this.clientSound_FinishedCrushing)
                {
                    this.clientSound_FinishedCrushing  = true;
                    this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, CnCMachineSounds.CNCMACHINE_FINISHCRUSHING, 1.0F, 1.0F);
                }
                
                // Background sound played while cutting block with water every 4 seconds.
                if (cookTimeScaled <= 15 && worldObj.getWorldTime() % MadScience.SECOND_IN_TICKS * 4L == 0L)
                {
                    this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, CnCMachineSounds.CNCMACHINE_WATERWORK, 0.5F, 1.0F);
                }
                
                // Played while water is being splashed onto the iron block every 3.6 seconds.
                if (cookTimeScaled > 7 && cookTimeScaled <= 15 && worldObj.getWorldTime() % MadScience.SECOND_IN_TICKS * 3.6F == 0L)
                {
                    this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, CnCMachineSounds.CNCMACHINE_WATERFLOW, 0.5F, 1.0F);
                }
            }
        }
    }

    /** Writes a tile entity to NBT. */
    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);

        // Amount of time left to cook current item.
        nbt.setShort("CookTime", (short) this.currentItemCookingValue);

        // Amount of water that is currently stored.
        nbt.setShort("WaterAmount", (short) this.WATER_TANK.getFluidAmount());

        // Current frame of animation we are displaying.
        nbt.setInteger("CurrentFrame", this.curFrame);

        // Path to current texture that should be loaded onto the model.
        nbt.setString("TexturePath", this.TEXTURE);

        // Determines if we have iron block inside machine.
        nbt.setBoolean("hasIronBlock", this.hasIronBlock);
        
        // Sound tracking.
        nbt.setBoolean("FinishCrushing", this.clientSound_FinishedCrushing);
        nbt.setBoolean("InsertIronBlock", this.clientSound_InsertIronBlock);
        nbt.setBoolean("InvalidBook", this.clientSound_InvalidBook);
        nbt.setBoolean("PowerOn", this.clientSound_PowerOn);
        nbt.setBoolean("PressStop", this.clientSound_PressStop);

        // Two tag lists for each type of items we have in this entity.
        NBTTagList inputItems = new NBTTagList();
        NBTTagList outputItems = new NBTTagList();

        // Input slots.
        for (int i = 0; i < this.CnCMachineInput.length; ++i)
        {
            if (this.CnCMachineInput[i] != null)
            {
                NBTTagCompound inputSlots = new NBTTagCompound();
                inputSlots.setByte("InputSlot", (byte) i);
                this.CnCMachineInput[i].writeToNBT(inputSlots);
                inputItems.appendTag(inputSlots);
            }
        }

        // Output slots.
        for (int i = 0; i < this.CnCMachineOutput.length; ++i)
        {
            if (this.CnCMachineOutput[i] != null)
            {
                NBTTagCompound outputSlots = new NBTTagCompound();
                outputSlots.setByte("OutputSlot", (byte) i);
                this.CnCMachineOutput[i].writeToNBT(outputSlots);
                outputItems.appendTag(outputSlots);
            }
        }

        // Save the input and output items.
        nbt.setTag("InputItems", inputItems);
        nbt.setTag("OutputItems", outputItems);
    }
}

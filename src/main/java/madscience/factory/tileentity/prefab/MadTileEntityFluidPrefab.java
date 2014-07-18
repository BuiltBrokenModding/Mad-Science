package madscience.factory.tileentity.prefab;

import madscience.factory.MadTileEntityFactoryProduct;
import madscience.factory.fluids.MadFluid;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

abstract class MadTileEntityFluidPrefab extends MadTileEntityInventoryPrefab implements IFluidHandler
{
    /** Internal reserve of some fluid. */
    private FluidTank internalTank = null;

    /* Holds reference we get from machine factory about what type of fluid this tile entity works with. */
    private Fluid supportedFluid = null;

    public MadTileEntityFluidPrefab()
    {
        super();
    }

    MadTileEntityFluidPrefab(MadTileEntityFactoryProduct registeredMachine)
    {
        super(registeredMachine);

        MadFluid[] supportedFluids = registeredMachine.getFluidsSupported();
        
        // Only create the fluid tank or do work if we have any fluids to support.
        if (supportedFluids != null)
        {
            int i = supportedFluids.length;
            for (int j = 0; j < i; ++j)
            {
                MadFluid currentFluid = supportedFluids[j];
                if (FluidRegistry.isFluidRegistered(currentFluid.getInternalName()))
                {
                    // Grab instance of this fluid from the registry so we can fill our tank with it.
                    if (currentFluid.getInternalName().equals(FluidRegistry.WATER.getName()))
                    {
                        supportedFluid = FluidRegistry.WATER;
                    }
                    else if (currentFluid.getInternalName().equals(FluidRegistry.LAVA.getName()))
                    {
                        supportedFluid = FluidRegistry.LAVA;
                    }
                    else
                    {
                        supportedFluid = FluidRegistry.getFluid(currentFluid.getInternalName());                    
                    }
    
                    // Create the tank based on any information we have from machine factory.
                    // TODO: Only 1 internal tank is allowed to be automatically created at this time.
                    internalTank = new FluidTank(supportedFluid, currentFluid.getStartingAmount(), currentFluid.getMaximumAmount());
                }
            }
        }
    }

    public boolean addFluidAmountByBucket(int numberOfBuckets)
    {
        int total = numberOfBuckets * FluidContainerRegistry.BUCKET_VOLUME;
        int acceptedAmount = this.internalTank.fill(new FluidStack(supportedFluid, total), true);

        if (acceptedAmount > 0)
        {
            return true;
        }

        return false;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid)
    {
        return false;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid)
    {
        return false;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain)
    {
        if (resource == null || !resource.isFluidEqual(internalTank.getFluid()))
        {
            return null;
        }

        return drain(from, resource.amount, doDrain);
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxEmpty, boolean doDrain)
    {
        return internalTank.drain(maxEmpty, doDrain);
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
    {
        return 0;
    }

    public int getFluidAmount()
    {
        if (this.internalTank == null)
        {
            return 0;
        }

        return internalTank.getFluidAmount();
    }

    public int getFluidCapacity()
    {
        if (this.internalTank == null)
        {
            return 0;
        }

        return internalTank.getCapacity();
    }

    public String getFluidLocalizedName()
    {
        return this.internalTank.getFluid().getFluid().getLocalizedName();
    }

    @SideOnly(Side.CLIENT)
    public int getFluidRemainingScaled(int pixels)
    {
        return internalTank.getFluid() != null ? (int) (((float) internalTank.getFluid().amount / (float) (internalTank.getCapacity())) * pixels) : 0;
    }

    public FluidStack getFluidStack()
    {
        return this.internalTank.getFluid();
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from)
    {
        return new FluidTankInfo[]
        { internalTank.getInfo() };
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);

        // Determine if we know what type of fluid we should be using.
        if (nbt.hasKey("FluidType") && this.supportedFluid == null)
        {
            // Re-create our tank from NBT save data.
            short maxFluid = nbt.getShort("FluidTotal");
            short fluidAmt = nbt.getShort("FluidAmount");
            String storedFluidType = nbt.getString("FluidType");

            // Check if the fluid type we want even exists.
            if (FluidRegistry.isFluidRegistered(storedFluidType))
            {
                // Grab an instance of this fluid we want in our machine.
                Fluid fluidSupported = FluidRegistry.getFluid(storedFluidType);
                supportedFluid = fluidSupported;

                // Re-create the tank from save data.
                internalTank = new FluidTank(fluidSupported, fluidAmt, maxFluid);
                this.internalTank.setFluid(new FluidStack(fluidSupported, fluidAmt));
                return;
            }
        }

        // Set internal tank amount based on save data.
        if (this.supportedFluid != null)
        {
            this.internalTank.setFluid(new FluidStack(supportedFluid, nbt.getShort("FluidAmount")));
        }
    }

    public boolean removeFluidAmountByBucket(int numberOfBuckets)
    {
        if (this.internalTank != null)
        {
            int totalBuckets = numberOfBuckets * FluidContainerRegistry.BUCKET_VOLUME;
            FluidStack amountRemoved = internalTank.drain(totalBuckets, true);
            if (amountRemoved != null && amountRemoved.amount > 0)
            {
                return true;
            }
        }

        return false;
    }
    
    public boolean removeFluidAmountExact(int milliBuckets)
    {
        if (this.internalTank != null)
        {
            FluidStack amountRemoved = internalTank.drain(milliBuckets, true);
            if (amountRemoved != null && amountRemoved.amount > 0)
            {
                return true;
            }
        }

        return false;
    }

    public void setFluidAmount(int amount)
    {
        if (this.internalTank != null)
        {
            this.internalTank.setFluid(new FluidStack(supportedFluid, amount));
        }
    }

    public void setFluidCapacity(int capacity)
    {
        if (this.internalTank != null)
        {
            this.internalTank.setCapacity(capacity);
        }
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);

        if (this.internalTank != null)
        {
            // Amount of water that is currently stored.
            nbt.setShort("FluidAmount", (short) this.internalTank.getFluidAmount());
    
            // Total amount of fluid we can store total.
            nbt.setShort("FluidTotal", (short) this.internalTank.getCapacity());
        }

        if (this.supportedFluid != null)
        {
            // Type of fluid which we need to remember.
            nbt.setString("FluidType", this.supportedFluid.getName());
        }
    }

    @Override
    public void initiate()
    {
        super.initiate();
    }
}

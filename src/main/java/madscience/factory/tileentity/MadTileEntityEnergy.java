package madscience.factory.tileentity;

import java.util.EnumSet;

import madscience.factory.MadTileEntityFactoryProduct;
import madscience.factory.energy.MadEnergyInterface;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import universalelectricity.api.CompatibilityModule;
import universalelectricity.api.UniversalClass;
import universalelectricity.api.energy.EnergyStorageHandler;
import universalelectricity.api.energy.IEnergyContainer;
import universalelectricity.api.energy.IEnergyInterface;
import universalelectricity.api.vector.Vector3;

@UniversalClass
abstract class MadTileEntityEnergy extends MadTileEntityFluid implements IEnergyInterface, IEnergyContainer
{
    protected EnergyStorageHandler energy;

    public MadTileEntityEnergy()
    {
        super();
    }

    MadTileEntityEnergy(MadTileEntityFactoryProduct registeredMachine)
    {
        super(registeredMachine);

        // Grab energy information from factory product.
        MadEnergyInterface[] energySupported = registeredMachine.getEnergySupported();

        if (energySupported.length >= 1)
        {
            for (int i = 0; i < energySupported.length; i++)
            {
                MadEnergyInterface energyInterface = energySupported[i];

                // TODO: Only 1 energy storage handler is supported at this time.
                energy = new EnergyStorageHandler(energyInterface.getEnergyCapacity(), energyInterface.getEnergyMaxRecieve(), energyInterface.getEnergyMaxExtract());
            }
        }
        else
        {
            // No energy is used if we just call to the object without the other overload.
            energy = new EnergyStorageHandler(0, 0, 0);
        }
    }

    @Override
    public boolean canConnect(ForgeDirection direction, Object obj)
    {
        return true;
    }

    private void consume()
    {
        for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS)
        {
            if (this.energy.getEnergy() < this.energy.getEnergyCapacity())
            {
                TileEntity tileEntity = new Vector3(this).translate(direction).getTileEntity(this.worldObj);

                if (tileEntity != null)
                {
                    long maxRecieve = this.energy.getMaxReceive();
                    long used = CompatibilityModule.extractEnergy(tileEntity, direction.getOpposite(), this.energy.receiveEnergy(maxRecieve, false), true);
                    this.energy.receiveEnergy(used, true);
                }
            }
        }
    }

    public void consumeEnergy(long amount)
    {
        if (!this.energy.isEmpty())
        {
            this.energy.setEnergy(Math.max((this.energy.getEnergy() - amount), 0));
        }
    }

    @Override
    public long getEnergy(ForgeDirection from)
    {
        return this.energy.getEnergy();
    }

    @Override
    public long getEnergyCapacity(ForgeDirection from)
    {
        return this.energy.getEnergyCapacity();
    }

    /** The electrical input direction.
     * 
     * @return The direction that electricity is entered into the tile. Return null for no input. By default you can accept power from all sides. */
    public EnumSet<ForgeDirection> getInputDirections()
    {
        return EnumSet.allOf(ForgeDirection.class);
    }

    /** The electrical output direction.
     * 
     * @return The direction that electricity is output from the tile. Return null for no output. By default it will return an empty EnumSet. */
    public EnumSet<ForgeDirection> getOutputDirections()
    {
        return EnumSet.noneOf(ForgeDirection.class);
    }

    public int getPowerRemainingScaled(int prgPixels)
    {
        Double result = Long.valueOf(this.getEnergy(ForgeDirection.UNKNOWN)).doubleValue() * Long.valueOf(prgPixels).doubleValue() / Long.valueOf(this.getEnergyCapacity(ForgeDirection.UNKNOWN)).doubleValue();
        return result.intValue();
    }

    public boolean isPowered()
    {
        if (this.energy == null)
        {
            return false;
        }
        
        return this.energy.getEnergy() > 0;
    }

    @Override
    public long onExtractEnergy(ForgeDirection from, long extract, boolean doExtract)
    {
        return this.energy.extractEnergy(extract, doExtract);
    }

    @Override
    public void onInventoryChanged()
    {
        super.onInventoryChanged();
    }

    @Override
    public long onReceiveEnergy(ForgeDirection from, long receive, boolean doReceive)
    {
        return this.energy.receiveEnergy(receive, doReceive);
    }

    protected void produce()
    {
        for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS)
        {
            if (this.energy.getEnergy() > 0)
            {
                TileEntity tileEntity = new Vector3(this).translate(direction).getTileEntity(this.worldObj);

                if (tileEntity != null)
                {
                    long used = CompatibilityModule.receiveEnergy(tileEntity, direction.getOpposite(), this.energy.extractEnergy(this.energy.getEnergy(), false), true);
                    this.energy.extractEnergy(used, true);
                }
            }
        }
    }

    public void produceEnergy(long amount)
    {
        if (!this.energy.isFull())
        {
            this.energy.setEnergy(this.energy.getEnergy() + amount);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);

        // Load only the amount of power since everything will be established if this exists.
        if (this.energy != null)
        {
            // Current amount of energy.
            this.energy.readFromNBT(nbt);
        }
        else
        {
            // Current energy levels.
            long currentEnergy = nbt.getLong("energy");

            // Maximum amount of energy.
            long maximumEnergy = nbt.getLong("energyMax");

            // Maximum receive energy.
            long maxRecieve = nbt.getLong("energyMaxReceive");

            // Maximum extract energy.
            long maxExtract = nbt.getLong("energyMaxExtract");

            // Re-create energy state from NBT save data.
            this.energy = new EnergyStorageHandler(maximumEnergy, maxRecieve, maxExtract);
            this.energy.setEnergy(currentEnergy);
        }
    }

    @Override
    public void setEnergy(ForgeDirection from, long energy)
    {
        this.energy.setEnergy(energy);
    }

    public void setEnergyCapacity(long energy)
    {
        this.energy.setCapacity(energy);
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();

        // Accept energy if we are allowed to do so.
        if (this.energy != null && this.energy.checkReceive())
        {
            this.consume();
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);

        // Current amount of energy.
        this.energy.writeToNBT(nbt);

        // Maximum amount of energy.
        nbt.setLong("energyMax", this.energy.getEnergyCapacity());

        // Maximum receive.
        nbt.setLong("energyMaxReceive", this.energy.getMaxReceive());

        // Maximum extract.
        nbt.setLong("energyMaxExtract", this.energy.getMaxExtract());
    }

    @Override
    public void initiate()
    {
        super.initiate();
    }
}

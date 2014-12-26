package madscience.tile;


import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import madscience.energy.EnergySupported;
import madscience.product.TileEntityFactoryProduct;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;

import java.util.EnumSet;


abstract class TileEntityEnergyPrefab extends TileEntityFluidPrefab implements IEnergyHandler
{
    /**
     * Interface from UE team that handles many of the common energy requirements.
     */
    protected EnergyStorage energy;
    /**
     * Amount of energy this machine requires per tick to operate.
     */
    private int energyConsumeRate = 0;
    /**
     * Amount of energy this machine is capable of producing per tick.
     */
    private int energyProduceRate = 0;

    public TileEntityEnergyPrefab()
    {
        super();
    }

    protected TileEntityEnergyPrefab(TileEntityFactoryProduct registeredMachine)
    {
        super( registeredMachine );

        // Grab energy information from factory product.
        EnergySupported[] energySupported = registeredMachine.getEnergySupported();

        if (energySupported != null && energySupported.length >= 1)
        {
            for (int i = 0; i < energySupported.length; i++)
            {
                EnergySupported energyInterface = energySupported[i];

                // TODO: Only 1 energy storage handler is supported at this time.
                this.energy = new EnergyStorage( energyInterface.getEnergyCapacity(),
                                                 energyInterface.getEnergyMaxRecieve(),
                                                 energyInterface.getEnergyMaxExtract() );

                // Consumption and production information for generators.
                this.energyConsumeRate = energyInterface.getConsumptionRate();
                this.energyProduceRate = energyInterface.getProductionRate();
            }
        }
        else
        {
            // No energy is used if we just call to the object without the other overload.
            this.energy = new EnergyStorage( 0,
                                             0,
                                             0 );
        }
    }

    @Override
    public boolean canInterface(ForgeDirection arg0)
    {
        return true;
    }

    @Override
    public int extractEnergy(ForgeDirection arg0, int arg1, boolean arg2)
    {
        return this.energy.extractEnergy( arg1,
                                          arg2 );
    }

    @Override
    public int getEnergyStored(ForgeDirection arg0)
    {
        return this.energy.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection arg0)
    {
        return this.energy.getMaxEnergyStored();
    }

    @Override
    public int receiveEnergy(ForgeDirection arg0, int arg1, boolean arg2)
    {
        return this.energy.receiveEnergy( arg1,
                                          arg2 );
    }

    public void setEnergyCapacity(int capacity)
    {
        this.energy.setCapacity( capacity );
    }

    public int getEnergy()
    {
        return this.energy.getEnergyStored();
    }

    public void setEnergy(int energy)
    {
        this.energy.setEnergyStored( energy );
    }

    public int getMaxEnergy()
    {
        return this.energy.getMaxEnergyStored();
    }

    /**
     * Determine if the given amount of energy can be drained from machine internal energy supplies.
     */
    public void consumeInternalEnergy(int amount)
    {
        if (this.energy.getEnergyStored() > 0)
        {
            this.energy.setEnergyStored( Math.max( (this.energy.getEnergyStored() - amount),
                                                   0 ) );
        }
    }

    /**
     * The electrical input direction.
     *
     * @return The direction that electricity is entered into the tile. Return null for no input. By default you can accept power from all sides.
     */
    public EnumSet<ForgeDirection> getInputDirections()
    {
        return EnumSet.allOf( ForgeDirection.class );
    }

    /**
     * The electrical output direction.
     *
     * @return The direction that electricity is output from the tile. Return null for no output. By default it will return an empty EnumSet.
     */
    public EnumSet<ForgeDirection> getOutputDirections()
    {
        return EnumSet.noneOf( ForgeDirection.class );
    }

    public int getPowerRemainingScaled(int prgPixels)
    {
        Double result = Long.valueOf( this.getEnergyStored( ForgeDirection.UNKNOWN ) ).doubleValue() *
                        Long.valueOf( prgPixels ).doubleValue() /
                        Long.valueOf( this.getMaxEnergyStored( ForgeDirection.UNKNOWN ) ).doubleValue();
        return result.intValue();
    }

    public boolean isPowered()
    {
        if (this.energy == null)
        {
            return false;
        }

        return this.energy.getEnergyStored() > 0;
    }

    @Override
    public void onInventoryChanged()
    {
        super.onInventoryChanged();
    }

    public void produceEnergy(int amount)
    {
        if (this.energy.getEnergyStored() < this.energy.getMaxEnergyStored())
        {
            this.energy.setEnergyStored( this.energy.getEnergyStored() + amount );
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT( nbt );

        // Load only the amount of power since everything will be established if this exists.
        if (this.energy != null)
        {
            // Current amount of energy.
            this.energy.readFromNBT( nbt );
        }
        else
        {
            // Consumption amount.
            this.energyConsumeRate = nbt.getInteger( "energyConsumeRate" );

            // Production amount.
            this.energyProduceRate = nbt.getInteger( "energyProduceRate" );

            // Current energy levels.
            int currentEnergy = nbt.getInteger( "energy" );

            // Maximum amount of energy.
            int maximumEnergy = nbt.getInteger( "energyMax" );

            // Maximum receive energy.
            int maxRecieve = nbt.getInteger( "energyMaxReceive" );

            // Maximum extract energy.
            int maxExtract = nbt.getInteger( "energyMaxExtract" );

            // Re-create energy state from NBT save data.
            this.energy = new EnergyStorage( maximumEnergy,
                                             maxRecieve,
                                             maxExtract );

            this.energy.modifyEnergyStored( currentEnergy );
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
        super.writeToNBT( nbt );

        // Current amount of energy.
        this.energy.writeToNBT( nbt );

        // Maximum amount of energy.
        nbt.setInteger( "energyMax",
                        this.energy.getMaxEnergyStored() );

        // Maximum receive.
        nbt.setInteger( "energyMaxReceive",
                        this.energy.getMaxReceive() );

        // Maximum extract.
        nbt.setInteger( "energyMaxExtract",
                        this.energy.getMaxExtract() );

        // Consumption amount.
        nbt.setInteger( "energyConsumeRate",
                        this.energyConsumeRate );

        // Production amount.
        nbt.setInteger( "energyProduceRate",
                        this.energyProduceRate );
    }

    @Override
    public void initiate()
    {
        super.initiate();
    }

    @Override
    public void invalidate()
    {
        super.invalidate();
    }

    @Override
    public void validate()
    {
        super.validate();
    }

    public int getEnergyConsumeRate()
    {
        return energyConsumeRate;
    }

    public void setEnergyConsumeRate(int energyConsumeRate)
    {
        this.energyConsumeRate = energyConsumeRate;
    }

    public int getEnergyProduceRate()
    {
        return energyProduceRate;
    }

    public void setEnergyProduceRate(int energyProduceRate)
    {
        this.energyProduceRate = energyProduceRate;
    }
}

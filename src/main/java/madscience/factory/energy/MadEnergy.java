package madscience.factory.energy;

import com.google.gson.annotations.Expose;

public final class MadEnergy
{
    @Expose private long energyMaxExtract;
    @Expose private long energyMaxRecieve;
    @Expose private long energyCapacity;

    public MadEnergy(long capacity, long maxRecieve, long maxExtract) // NO_UCD (unused code)
    {
        this.energyCapacity = capacity;
        this.energyMaxRecieve = maxRecieve;
        this.energyMaxExtract = maxExtract;
    }

    public long getEnergyCapacity()
    {
        return energyCapacity;
    }

    public long getEnergyMaxExtract()
    {
        return energyMaxExtract;
    }

    public long getEnergyMaxRecieve()
    {
        return energyMaxRecieve;
    }

    public void setEnergyCapacity(long energyCapacity)
    {
        this.energyCapacity = energyCapacity;
    }

    public void setEnergyMaxExtract(long energyMaxExtract)
    {
        this.energyMaxExtract = energyMaxExtract;
    }

    public void setEnergyMaxRecieve(long energyMaxRecieve)
    {
        this.energyMaxRecieve = energyMaxRecieve;
    }
}

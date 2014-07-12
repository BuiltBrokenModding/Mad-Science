package madscience.factory.energy;

import com.google.gson.annotations.Expose;

public final class MadEnergy implements IMadEnergy
{
    @Expose private long energyMaxExtract;
    @Expose private long energyMaxRecieve;
    @Expose private long energyCapacity;

    public MadEnergy(long capacity, long maxRecieve, long maxExtract)
    {
        this.energyCapacity = capacity;
        this.energyMaxRecieve = maxRecieve;
        this.energyMaxExtract = maxExtract;
    }

    @Override
    public long getEnergyCapacity()
    {
        return energyCapacity;
    }

    @Override
    public long getEnergyMaxExtract()
    {
        return energyMaxExtract;
    }

    @Override
    public long getEnergyMaxRecieve()
    {
        return energyMaxRecieve;
    }

    @Override
    public void setEnergyCapacity(long energyCapacity)
    {
        this.energyCapacity = energyCapacity;
    }

    @Override
    public void setEnergyMaxExtract(long energyMaxExtract)
    {
        this.energyMaxExtract = energyMaxExtract;
    }

    @Override
    public void setEnergyMaxRecieve(long energyMaxRecieve)
    {
        this.energyMaxRecieve = energyMaxRecieve;
    }
}

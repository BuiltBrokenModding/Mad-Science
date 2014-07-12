package madscience.factory.energy;

import madscience.MadConfig;

public final class MadEnergy implements IMadEnergy
{
    private long energyMaxExtract;
    private long energyMaxRecieve;
    private long energyCapacity;

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

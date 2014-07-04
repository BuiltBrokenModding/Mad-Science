package madscience.factory.energy;

public interface MadEnergyInterface
{

    public abstract long getEnergyCapacity();

    public abstract long getEnergyMaxExtract();

    public abstract long getEnergyMaxRecieve();

    public abstract void setEnergyCapacity(long energyCapacity);

    public abstract void setEnergyMaxExtract(long energyMaxExtract);

    public abstract void setEnergyMaxRecieve(long energyMaxRecieve);

}
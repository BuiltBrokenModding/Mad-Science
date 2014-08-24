package madscience.factory.energy;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public final class MadEnergy
{
    @Expose 
    @SerializedName("EnergyMaxExtract")
    private long energyMaxExtract;
    
    @Expose 
    @SerializedName("EnergyMaxRecieve")
    private long energyMaxRecieve;
    
    @Expose 
    @SerializedName("EnergyCapacity")
    private long energyCapacity;
    
    @Expose 
    @SerializedName("EnergyConsumptionRate")
    private long consumptionRate;
    
    @Expose 
    @SerializedName("EnergyProductionRate")
    private long productionRate;

    @SuppressWarnings("ucd")
    public MadEnergy(long capacity,
                     long maxRecieve,
                     long maxExtract,
                     long consumeRate,
                     long produceRate) // NO_UCD (unused code)
    {
        this.energyMaxRecieve = maxRecieve;
        this.energyMaxExtract = maxExtract;
        
        this.energyCapacity = capacity;
        
        this.consumptionRate = consumeRate;
        this.productionRate = produceRate;
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

    public long getConsumptionRate()
    {
        return consumptionRate;
    }

    public void setConsumptionRate(long consumptionRate)
    {
        this.consumptionRate = consumptionRate;
    }

    public long getProductionRate()
    {
        return productionRate;
    }

    public void setProductionRate(long productionRate)
    {
        this.productionRate = productionRate;
    }
}

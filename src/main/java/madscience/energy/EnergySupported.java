package madscience.energy;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public final class EnergySupported
{
    @Expose
    @SerializedName("EnergyMaxExtract")
    private int energyMaxExtract;

    @Expose
    @SerializedName("EnergyMaxRecieve")
    private int energyMaxRecieve;

    @Expose
    @SerializedName("EnergyCapacity")
    private int energyCapacity;

    @Expose
    @SerializedName("EnergyConsumptionRate")
    private int consumptionRate;

    @Expose
    @SerializedName("EnergyProductionRate")
    private int productionRate;

    public EnergySupported(int capacity,
                           int maxRecieve,
                           int maxExtract,
                           int consumeRate,
                           int produceRate) // NO_UCD (unused code)
    {
        this.energyMaxRecieve = maxRecieve;
        this.energyMaxExtract = maxExtract;

        this.energyCapacity = capacity;

        this.consumptionRate = consumeRate;
        this.productionRate = produceRate;
    }

    public int getEnergyCapacity()
    {
        return energyCapacity;
    }

    public void setEnergyCapacity(int energyCapacity)
    {
        this.energyCapacity = energyCapacity;
    }

    public int getEnergyMaxExtract()
    {
        return energyMaxExtract;
    }

    public void setEnergyMaxExtract(int energyMaxExtract)
    {
        this.energyMaxExtract = energyMaxExtract;
    }

    public int getEnergyMaxRecieve()
    {
        return energyMaxRecieve;
    }

    public void setEnergyMaxRecieve(int energyMaxRecieve)
    {
        this.energyMaxRecieve = energyMaxRecieve;
    }

    public int getConsumptionRate()
    {
        return consumptionRate;
    }

    public void setConsumptionRate(int consumptionRate)
    {
        this.consumptionRate = consumptionRate;
    }

    public int getProductionRate()
    {
        return productionRate;
    }

    public void setProductionRate(int productionRate)
    {
        this.productionRate = productionRate;
    }
}

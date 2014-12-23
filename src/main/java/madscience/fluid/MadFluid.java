package madscience.fluid;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public final class MadFluid
{
    @Expose
    @SerializedName("InternalName")
    private final String internalName;

    @Expose
    @SerializedName("StartingAmount")
    private final int startingAmount;

    @Expose
    @SerializedName("MaximumAmount")
    private final int maximumAmount;

    public MadFluid(String fluidDictionaryName, int amount, int capacity) // NO_UCD (unused code)
    {
        this.internalName = fluidDictionaryName;
        this.startingAmount = amount;
        this.maximumAmount = capacity;
    }

    public String getInternalName()
    {
        return internalName;
    }

    public int getMaximumAmount()
    {
        return maximumAmount;
    }

    public int getStartingAmount()
    {
        return startingAmount;
    }
}

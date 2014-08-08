package madscience.factory.fluid;

import com.google.gson.annotations.Expose;

public final class MadFluid
{
    @Expose private final String internalName;
    @Expose private final int startingAmount;
    @Expose private final int maximumAmount;

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

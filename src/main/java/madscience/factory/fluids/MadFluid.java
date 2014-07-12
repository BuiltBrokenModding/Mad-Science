package madscience.factory.fluids;

import com.google.gson.annotations.Expose;

public final class MadFluid implements IMadFluid
{
    @Expose private final String internalName;
    @Expose private final int startingAmount;
    @Expose private final int maximumAmount;

    public MadFluid(String fluidDictionaryName, int amount, int capacity)
    {
        this.internalName = fluidDictionaryName;
        this.startingAmount = amount;
        this.maximumAmount = capacity;
    }

    @Override
    public String getInternalName()
    {
        return internalName;
    }

    @Override
    public int getMaximumAmount()
    {
        return maximumAmount;
    }

    @Override
    public int getStartingAmount()
    {
        return startingAmount;
    }
}

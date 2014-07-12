package madscience.factory.fluids;

import madscience.MadFluids;
import net.minecraftforge.fluids.FluidContainerRegistry;

public final class MadFluid implements IMadFluid
{
    private final String internalName;
    private final int startingAmount;
    private final int maximumAmount;

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

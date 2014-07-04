package madscience.tileentities.dnaextractor;

import madscience.MadFluids;
import madscience.factory.fluids.MadFluidInterface;
import net.minecraftforge.fluids.FluidContainerRegistry;

public enum DNAExtractorEnumFluids implements MadFluidInterface
{
    MutantDNA(MadFluids.LIQUIDDNA_MUTANT_INTERNALNAME, 0, FluidContainerRegistry.BUCKET_VOLUME * 10);

    private String internalName;
    private int startingAmount;
    private int maximumAmount;

    DNAExtractorEnumFluids(String fluidDictionaryName, int amount, int capacity)
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

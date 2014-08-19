package madscience;

import java.util.ArrayList;
import java.util.List;

import madscience.factory.data.MadFluidFactoryProductData;

public class MadManualFluids
{
    private List<MadFluidFactoryProductData> manualFluids = null;
    
    public List<MadFluidFactoryProductData> getManualFluids()
    {
        return manualFluids;
    }

    public MadManualFluids()
    {
        manualFluids = new ArrayList<MadFluidFactoryProductData>();
        
        // ----------
        // LIQUID DNA
        // ----------
        {
            MadFluidFactoryProductData DNA = new MadFluidFactoryProductData("maddna", 3, 4000, 5, "maddna_still", "maddna_flowing", "madDNABucket", true);
            manualFluids.add(DNA);
        }
        
        // ----------
        // MUTANT DNA
        // ----------
        {
            MadFluidFactoryProductData mutantDNA = new MadFluidFactoryProductData("maddnamutant", 3, 4000, 5, "maddnamutant_still", "maddnamutant_flowing", "madDNAMutantBucket", true);
            manualFluids.add(mutantDNA);
        }
    }
}

package madscience.items.dna;

import madscience.factory.mod.MadMod;

public class DNABat extends ItemDecayDNABase
{

    public DNABat(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadMod.getCreativeTab());
    }

}

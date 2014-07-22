package madscience.items.dna;

import madscience.factory.mod.MadMod;

public class DNAEnderman extends ItemDecayDNABase
{

    public DNAEnderman(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadMod.getCreativeTab());
    }

}

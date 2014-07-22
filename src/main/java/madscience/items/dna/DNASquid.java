package madscience.items.dna;

import madscience.factory.mod.MadMod;

public class DNASquid extends ItemDecayDNABase
{

    public DNASquid(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadMod.getCreativeTab());
    }

}

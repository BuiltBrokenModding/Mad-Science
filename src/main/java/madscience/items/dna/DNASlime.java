package madscience.items.dna;

import madscience.factory.mod.MadMod;

public class DNASlime extends ItemDecayDNABase
{

    public DNASlime(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadMod.getCreativeTab());
    }

}

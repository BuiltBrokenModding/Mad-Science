package madscience.items.dna;

import madscience.factory.mod.MadMod;

public class DNAGhast extends ItemDecayDNABase
{

    public DNAGhast(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadMod.getCreativeTab());
    }

}

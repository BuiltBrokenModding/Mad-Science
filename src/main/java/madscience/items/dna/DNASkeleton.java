package madscience.items.dna;

import madscience.factory.mod.MadMod;

public class DNASkeleton extends ItemDecayDNABase
{

    public DNASkeleton(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadMod.getCreativeTab());
    }

}

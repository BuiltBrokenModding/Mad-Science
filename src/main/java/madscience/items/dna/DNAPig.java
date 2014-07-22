package madscience.items.dna;

import madscience.factory.mod.MadMod;

public class DNAPig extends ItemDecayDNABase
{

    public DNAPig(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadMod.getCreativeTab());
    }

}

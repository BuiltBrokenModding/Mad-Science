package madscience.items.dna;

import madscience.factory.mod.MadMod;

public class DNACow extends ItemDecayDNABase
{

    public DNACow(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadMod.getCreativeTab());
    }

}

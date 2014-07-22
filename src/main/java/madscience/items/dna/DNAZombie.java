package madscience.items.dna;

import madscience.factory.mod.MadMod;

public class DNAZombie extends ItemDecayDNABase
{

    public DNAZombie(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadMod.getCreativeTab());
    }

}

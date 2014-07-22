package madscience.items.dna;

import madscience.factory.mod.MadMod;

public class DNAHorse extends ItemDecayDNABase
{

    public DNAHorse(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadMod.getCreativeTab());
    }

}

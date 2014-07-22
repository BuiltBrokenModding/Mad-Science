package madscience.items.dna;

import madscience.factory.mod.MadMod;

public class DNAMushroomCow extends ItemDecayDNABase
{

    public DNAMushroomCow(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadMod.getCreativeTab());
    }

}

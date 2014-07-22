package madscience.items.dna;

import madscience.factory.mod.MadMod;

public class DNAChicken extends ItemDecayDNABase
{

    public DNAChicken(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadMod.getCreativeTab());
    }

}

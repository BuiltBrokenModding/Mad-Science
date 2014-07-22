package madscience.items.dna;

import madscience.factory.mod.MadMod;

public class DNASpider extends ItemDecayDNABase
{

    public DNASpider(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadMod.getCreativeTab());
    }

}

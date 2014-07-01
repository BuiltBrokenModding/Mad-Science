package madscience.items.dna;

import madscience.MadEntities;

public class DNASpider extends ItemDecayDNABase
{

    public DNASpider(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadEntities.tabMadScience);
    }

}

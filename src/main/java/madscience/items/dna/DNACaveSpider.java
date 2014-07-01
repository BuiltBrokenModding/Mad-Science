package madscience.items.dna;

import madscience.MadEntities;

public class DNACaveSpider extends ItemDecayDNABase
{

    public DNACaveSpider(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadEntities.tabMadScience);
    }
}

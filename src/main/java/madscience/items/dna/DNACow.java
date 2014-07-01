package madscience.items.dna;

import madscience.MadEntities;

public class DNACow extends ItemDecayDNABase
{

    public DNACow(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadEntities.tabMadScience);
    }

}

package madscience.items.dna;

import madscience.MadEntities;

public class DNAPig extends ItemDecayDNABase
{

    public DNAPig(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadEntities.tabMadScience);
    }

}

package madscience.items.dna;

import madscience.MadEntities;

public class DNASkeleton extends ItemDecayDNABase
{

    public DNASkeleton(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadEntities.tabMadScience);
    }

}

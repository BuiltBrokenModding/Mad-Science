package madscience.items.dna;

import madscience.MadEntities;

public class DNASquid extends ItemDecayDNABase
{

    public DNASquid(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadEntities.tabMadScience);
    }

}

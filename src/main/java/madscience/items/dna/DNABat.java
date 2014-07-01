package madscience.items.dna;

import madscience.MadEntities;

public class DNABat extends ItemDecayDNABase
{

    public DNABat(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadEntities.tabMadScience);
    }

}

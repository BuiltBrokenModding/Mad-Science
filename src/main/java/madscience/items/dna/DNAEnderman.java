package madscience.items.dna;

import madscience.MadEntities;

public class DNAEnderman extends ItemDecayDNABase
{

    public DNAEnderman(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadEntities.tabMadScience);
    }

}

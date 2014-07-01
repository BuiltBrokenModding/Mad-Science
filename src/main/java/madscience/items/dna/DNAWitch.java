package madscience.items.dna;

import madscience.MadEntities;

public class DNAWitch extends ItemDecayDNABase
{

    public DNAWitch(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadEntities.tabMadScience);
    }

}

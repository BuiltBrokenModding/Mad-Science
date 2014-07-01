package madscience.items.dna;

import madscience.MadEntities;

public class DNACreeper extends ItemDecayDNABase
{

    public DNACreeper(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadEntities.tabMadScience);
    }

}

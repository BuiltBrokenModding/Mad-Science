package madscience.items.dna;

import madscience.MadEntities;

public class DNASlime extends ItemDecayDNABase
{

    public DNASlime(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadEntities.tabMadScience);
    }

}

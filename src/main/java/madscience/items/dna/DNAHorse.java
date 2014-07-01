package madscience.items.dna;

import madscience.MadEntities;

public class DNAHorse extends ItemDecayDNABase
{

    public DNAHorse(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadEntities.tabMadScience);
    }

}

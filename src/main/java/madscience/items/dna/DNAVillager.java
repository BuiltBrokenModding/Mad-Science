package madscience.items.dna;

import madscience.MadEntities;

public class DNAVillager extends ItemDecayDNABase
{

    public DNAVillager(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadEntities.tabMadScience);
    }

}

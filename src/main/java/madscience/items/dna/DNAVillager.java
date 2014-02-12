package madscience.items.dna;

import madscience.MadEntities;
import madscience.items.ItemDecayDNA;

public class DNAVillager extends ItemDecayDNA
{

    public DNAVillager(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadEntities.tabMadScience);
    }

}

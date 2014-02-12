package madscience.items.dna;

import madscience.MadEntities;
import madscience.items.ItemDecayDNA;

public class DNAWitch extends ItemDecayDNA
{

    public DNAWitch(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadEntities.tabMadScience);
    }

}

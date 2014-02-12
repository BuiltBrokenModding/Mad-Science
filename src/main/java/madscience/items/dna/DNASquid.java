package madscience.items.dna;

import madscience.MadEntities;
import madscience.items.ItemDecayDNA;

public class DNASquid extends ItemDecayDNA
{

    public DNASquid(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadEntities.tabMadScience);
    }

}

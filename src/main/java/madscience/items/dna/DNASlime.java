package madscience.items.dna;

import madscience.MadEntities;
import madscience.items.ItemDecayDNA;

public class DNASlime extends ItemDecayDNA
{

    public DNASlime(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadEntities.tabMadScience);
    }

}

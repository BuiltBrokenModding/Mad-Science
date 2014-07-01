package madscience.items.genomes;

import madscience.MadEntities;

public class GenomeCow extends ItemGenomeBase
{

    public GenomeCow(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadEntities.tabMadScience);
    }

}

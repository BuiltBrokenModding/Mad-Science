package madscience.items.genomes;

import madscience.MadEntities;

public class GenomeBat extends ItemGenomeBase
{
    public GenomeBat(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadEntities.tabMadScience);
    }
}

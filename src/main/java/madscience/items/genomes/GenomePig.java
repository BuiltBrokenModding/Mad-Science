package madscience.items.genomes;

import madscience.MadEntities;

public class GenomePig extends ItemGenomeBase
{

    public GenomePig(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadEntities.tabMadScience);
    }
}

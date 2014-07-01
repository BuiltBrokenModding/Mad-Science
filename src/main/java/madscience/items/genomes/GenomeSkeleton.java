package madscience.items.genomes;

import madscience.MadEntities;

public class GenomeSkeleton extends ItemGenomeBase
{

    public GenomeSkeleton(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadEntities.tabMadScience);
    }

}

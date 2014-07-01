package madscience.items.genomes;

import madscience.MadEntities;

public class GenomeGhast extends ItemGenomeBase
{

    public GenomeGhast(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadEntities.tabMadScience);
    }

}

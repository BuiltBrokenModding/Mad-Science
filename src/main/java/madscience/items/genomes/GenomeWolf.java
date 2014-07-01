package madscience.items.genomes;

import madscience.MadEntities;

public class GenomeWolf extends ItemGenomeBase
{

    public GenomeWolf(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadEntities.tabMadScience);
    }

}

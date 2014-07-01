package madscience.items.genomes;

import madscience.MadEntities;

public class GenomeZombie extends ItemGenomeBase
{

    public GenomeZombie(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadEntities.tabMadScience);
    }
}

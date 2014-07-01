package madscience.items.genomes;

import madscience.MadEntities;

public class GenomePigZombie extends ItemGenomeBase
{

    public GenomePigZombie(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadEntities.tabMadScience);
    }

}

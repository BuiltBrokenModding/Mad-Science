package madscience.items.genomes;

import madscience.MadEntities;

public class GenomeVillager extends ItemGenomeBase
{

    public GenomeVillager(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadEntities.tabMadScience);
    }
}

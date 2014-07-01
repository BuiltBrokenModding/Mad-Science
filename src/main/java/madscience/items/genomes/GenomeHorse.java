package madscience.items.genomes;

import madscience.MadEntities;

public class GenomeHorse extends ItemGenomeBase
{

    public GenomeHorse(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadEntities.tabMadScience);
    }

}

package madscience.items.genomes;

import madscience.MadEntities;

public class GenomeWitch extends ItemGenomeBase
{

    public GenomeWitch(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadEntities.tabMadScience);
    }

}

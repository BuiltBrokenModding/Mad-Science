package madscience.items.genomes;

import madscience.MadEntities;

public class GenomeOcelot extends ItemGenomeBase
{

    public GenomeOcelot(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadEntities.tabMadScience);
    }

}

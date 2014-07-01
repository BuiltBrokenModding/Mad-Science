package madscience.items.genomes;

import madscience.MadEntities;

public class GenomeEnderman extends ItemGenomeBase
{

    public GenomeEnderman(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadEntities.tabMadScience);
    }

}

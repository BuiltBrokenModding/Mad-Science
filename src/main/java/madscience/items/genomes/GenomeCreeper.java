package madscience.items.genomes;

import madscience.MadEntities;

public class GenomeCreeper extends ItemGenomeBase
{

    public GenomeCreeper(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadEntities.tabMadScience);
    }

}

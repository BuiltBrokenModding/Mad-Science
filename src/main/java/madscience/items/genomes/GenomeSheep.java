package madscience.items.genomes;

import madscience.MadEntities;

public class GenomeSheep extends ItemGenomeBase
{

    public GenomeSheep(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadEntities.tabMadScience);
    }

}

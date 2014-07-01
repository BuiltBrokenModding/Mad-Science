package madscience.items.genomes;

import madscience.MadEntities;

public class GenomeSquid extends ItemGenomeBase
{

    public GenomeSquid(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadEntities.tabMadScience);
    }

}

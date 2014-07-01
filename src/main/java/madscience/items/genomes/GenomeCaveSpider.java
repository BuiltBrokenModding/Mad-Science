package madscience.items.genomes;

import madscience.MadEntities;

public class GenomeCaveSpider extends ItemGenomeBase
{

    public GenomeCaveSpider(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadEntities.tabMadScience);
    }
}

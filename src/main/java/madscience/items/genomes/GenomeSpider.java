package madscience.items.genomes;

import madscience.MadEntities;

public class GenomeSpider extends ItemGenomeBase
{

    public GenomeSpider(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadEntities.tabMadScience);
    }
}

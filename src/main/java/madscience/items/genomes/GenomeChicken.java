package madscience.items.genomes;

import madscience.MadEntities;

public class GenomeChicken extends ItemGenomeBase
{
    public GenomeChicken(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadEntities.tabMadScience);
    }
}

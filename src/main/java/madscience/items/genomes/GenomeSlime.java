package madscience.items.genomes;

import madscience.MadEntities;

public class GenomeSlime extends ItemGenomeBase
{
    public GenomeSlime(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadEntities.tabMadScience);
    }
}

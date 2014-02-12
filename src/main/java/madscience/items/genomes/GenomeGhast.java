package madscience.items.genomes;

import madscience.MadEntities;
import madscience.items.ItemGenome;

public class GenomeGhast extends ItemGenome
{

    public GenomeGhast(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadEntities.tabMadScience);
    }

}

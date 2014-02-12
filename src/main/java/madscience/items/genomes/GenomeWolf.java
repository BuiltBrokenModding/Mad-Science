package madscience.items.genomes;

import madscience.MadEntities;
import madscience.items.ItemGenome;

public class GenomeWolf extends ItemGenome
{

    public GenomeWolf(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadEntities.tabMadScience);
    }

}

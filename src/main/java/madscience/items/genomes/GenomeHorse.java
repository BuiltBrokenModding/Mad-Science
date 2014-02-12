package madscience.items.genomes;

import madscience.MadEntities;
import madscience.items.ItemGenome;

public class GenomeHorse extends ItemGenome
{

    public GenomeHorse(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadEntities.tabMadScience);
    }

}

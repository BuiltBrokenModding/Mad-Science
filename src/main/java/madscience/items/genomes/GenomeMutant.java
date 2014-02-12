package madscience.items.genomes;

import madscience.MadEntities;
import madscience.items.ItemGenome;

public class GenomeMutant extends ItemGenome
{

    public GenomeMutant(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadEntities.tabMadScience);
    }
}

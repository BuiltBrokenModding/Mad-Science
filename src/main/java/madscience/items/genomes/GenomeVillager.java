package madscience.items.genomes;

import madscience.factory.mod.MadMod;

public class GenomeVillager extends ItemGenomeBase
{

    public GenomeVillager(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadMod.getCreativeTab());
    }
}

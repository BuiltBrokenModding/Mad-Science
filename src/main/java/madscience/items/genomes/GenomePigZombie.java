package madscience.items.genomes;

import madscience.factory.mod.MadMod;

public class GenomePigZombie extends ItemGenomeBase
{

    public GenomePigZombie(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadMod.getCreativeTab());
    }

}

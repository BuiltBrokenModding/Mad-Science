package madscience.item.genomes;

import madscience.factory.mod.MadMod;

public class GenomeZombie extends ItemGenomeBase
{

    public GenomeZombie(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadMod.getCreativeTab());
    }
}

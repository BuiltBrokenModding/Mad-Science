package madscience.item.genomes;

import madscience.factory.mod.MadMod;

public class GenomeWolf extends ItemGenomeBase
{

    public GenomeWolf(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadMod.getCreativeTab());
    }

}

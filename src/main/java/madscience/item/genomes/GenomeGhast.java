package madscience.item.genomes;

import madscience.factory.mod.MadMod;

public class GenomeGhast extends ItemGenomeBase
{

    public GenomeGhast(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadMod.getCreativeTab());
    }

}
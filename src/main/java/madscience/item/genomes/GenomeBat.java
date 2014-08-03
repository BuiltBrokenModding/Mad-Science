package madscience.item.genomes;

import madscience.factory.mod.MadMod;

public class GenomeBat extends ItemGenomeBase
{
    public GenomeBat(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadMod.getCreativeTab());
    }
}

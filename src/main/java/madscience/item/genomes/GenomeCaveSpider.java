package madscience.item.genomes;

import madscience.factory.mod.MadMod;

public class GenomeCaveSpider extends ItemGenomeBase
{

    public GenomeCaveSpider(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadMod.getCreativeTab());
    }
}

package madscience.item.genomes;

import madscience.factory.mod.MadMod;

public class GenomeSkeleton extends ItemGenomeBase
{

    public GenomeSkeleton(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadMod.getCreativeTab());
    }

}

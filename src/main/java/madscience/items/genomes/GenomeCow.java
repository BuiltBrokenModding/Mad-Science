package madscience.items.genomes;

import madscience.factory.mod.MadMod;

public class GenomeCow extends ItemGenomeBase
{

    public GenomeCow(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadMod.getCreativeTab());
    }

}

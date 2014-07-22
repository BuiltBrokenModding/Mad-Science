package madscience.items.genomes;

import madscience.factory.mod.MadMod;

public class GenomeSlime extends ItemGenomeBase
{
    public GenomeSlime(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadMod.getCreativeTab());
    }
}

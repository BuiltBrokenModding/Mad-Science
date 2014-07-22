package madscience.items.genomes;

import madscience.factory.mod.MadMod;

public class GenomeChicken extends ItemGenomeBase
{
    public GenomeChicken(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadMod.getCreativeTab());
    }
}

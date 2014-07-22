package madscience.items.genomes;

import madscience.factory.mod.MadMod;

public class GenomeEnderman extends ItemGenomeBase
{

    public GenomeEnderman(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadMod.getCreativeTab());
    }

}

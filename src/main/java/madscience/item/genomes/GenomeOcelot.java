package madscience.item.genomes;

import madscience.factory.mod.MadMod;

public class GenomeOcelot extends ItemGenomeBase
{

    public GenomeOcelot(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadMod.getCreativeTab());
    }

}

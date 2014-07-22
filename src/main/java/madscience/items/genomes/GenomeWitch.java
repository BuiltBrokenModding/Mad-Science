package madscience.items.genomes;

import madscience.factory.mod.MadMod;

public class GenomeWitch extends ItemGenomeBase
{

    public GenomeWitch(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadMod.getCreativeTab());
    }

}

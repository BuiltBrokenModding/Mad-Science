package madscience.item.genomes;

import madscience.factory.mod.MadMod;

public class GenomeSquid extends ItemGenomeBase
{

    public GenomeSquid(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadMod.getCreativeTab());
    }

}

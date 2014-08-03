package madscience.item.genomes;

import madscience.factory.mod.MadMod;

public class GenomeMushroomCow extends ItemGenomeBase
{

    public GenomeMushroomCow(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadMod.getCreativeTab());
    }

}

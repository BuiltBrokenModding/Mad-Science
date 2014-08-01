package madscience.items.genomes;

import madscience.factory.item.prefab.ItemGenomeBase;
import madscience.factory.mod.MadMod;

public class GenomeMushroomCow extends ItemGenomeBase
{

    public GenomeMushroomCow(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadMod.getCreativeTab());
    }

}

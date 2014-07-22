package madscience.items.datareel;

import madscience.factory.mod.MadMod;
import madscience.items.genomes.ItemGenomeBase;

public class ItemDataReelEmpty extends ItemGenomeBase
{
    public ItemDataReelEmpty(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadMod.getCreativeTab());
    }
}

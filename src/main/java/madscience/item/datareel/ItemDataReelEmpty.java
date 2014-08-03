package madscience.item.datareel;

import madscience.factory.mod.MadMod;

public class ItemDataReelEmpty extends ItemGenomeBase
{
    public ItemDataReelEmpty(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadMod.getCreativeTab());
    }
}

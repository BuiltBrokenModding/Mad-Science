package madscience.items;

import madscience.MadEntities;

public class ItemDataReelEmpty extends ItemGenome
{
    public ItemDataReelEmpty(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadEntities.tabMadScience);
    }
}

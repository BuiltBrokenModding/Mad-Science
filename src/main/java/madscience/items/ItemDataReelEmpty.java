package madscience.items;

import madscience.MadEntities;

public class ItemDataReelEmpty extends ItemGenome
{
    public ItemDataReelEmpty(int primaryColor, int secondaryColor)
    {
        super(primaryColor, secondaryColor);
        this.setCreativeTab(MadEntities.tabMadScience);
    }
}

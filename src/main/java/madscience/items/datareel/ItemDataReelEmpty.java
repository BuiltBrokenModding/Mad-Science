package madscience.items.datareel;

import madscience.MadEntities;
import madscience.items.genomes.ItemGenomeBase;

public class ItemDataReelEmpty extends ItemGenomeBase
{
    public ItemDataReelEmpty(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadEntities.tabMadScience);
    }
}

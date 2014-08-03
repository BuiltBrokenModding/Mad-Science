package madscience;

import java.util.ArrayList;
import java.util.List;

import madscience.factory.item.MadItemFactoryProductData;

public class MadManualItems
{
    private static final List<MadItemFactoryProductData> manualItems = new ArrayList<MadItemFactoryProductData>();

    public static List<MadItemFactoryProductData> getManualitems()
    {
        return manualItems;
    }
    
    static
    {
        // DNA Samples.
        MadItemFactoryProductData dnaSamples = new MadItemFactoryProductData(
                itemBaseName,
                noRepair,
                maxDamage,
                maxStacksize,
                damageVSEntity,
                damageVSBlocks,
                enchantability,
                canHarvestBlocks,
                logicClassFullyQualifiedName,
                subItemsArchive);
        
        
        // Add all of the created item data to list.
        manualItems.add(dnaSamples);
    }
}

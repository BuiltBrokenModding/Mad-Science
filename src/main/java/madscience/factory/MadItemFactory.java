package madscience.factory;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import madscience.factory.data.MadItemFactoryProductData;
import madscience.factory.item.MadMetaItemData;
import madscience.factory.mod.MadMod;
import madscience.factory.product.MadItemFactoryProduct;
import madscience.util.MadUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class MadItemFactory
{
    private static MadItemFactory instance;
    
    private static final Map<String, MadItemFactoryProduct> registeredItems = new LinkedHashMap<String, MadItemFactoryProduct>();
    
    public MadItemFactory()
    {
        super();
    }
    
    public static synchronized MadItemFactory instance()
    {
        if (instance == null)
        {
            instance = new MadItemFactory();
        }
        
        return instance;
    }
    
    public MadItemFactoryProduct registerItem(MadItemFactoryProductData itemData) throws IllegalArgumentException
    {
        // Pass the data object into the product to activate it, creates needed data structures inside it based on data supplied.
        MadItemFactoryProduct itemProduct = new MadItemFactoryProduct(itemData);

        // Check to make sure we have not added this item before.
        if (!isValidItemID(itemProduct.getItemBaseName()))
        {
            throw new IllegalArgumentException("Duplicate MadItemFactoryProduct '" + itemProduct.getItemBaseName() + "' was added. Execution halted!");
        }

        // Debugging!
        MadMod.log().info("[MadItemFactory]Registering item: " + itemProduct.getItemBaseName());
        
        // Actually register the item with the product listing.
        registeredItems.put(itemProduct.getItemBaseName(), itemProduct);
        
        // Register the item with Minecraft/Forge.
        GameRegistry.registerItem(itemProduct.getItem(), itemProduct.getItemBaseName());
        
        return itemProduct;
    }
    
    public MadItemFactoryProduct getItemInfo(String id)
    {
        return registeredItems.get(id);
    }
    
    public Collection<MadItemFactoryProduct> getItemInfoList()
    {
        return Collections.unmodifiableCollection(registeredItems.values());
    }
    
    public boolean isValidItemID(String id)
    {
        return !registeredItems.containsKey(id);
    }
    
    public MadItemFactoryProductData[] getItemDataList()
    {
        // Loop through every registered item in the system.
        Set<MadItemFactoryProductData> allItems = new HashSet<MadItemFactoryProductData>();
        for (Iterator iterator = getItemInfoList().iterator(); iterator.hasNext();)
        {
            MadItemFactoryProduct registeredItem = (MadItemFactoryProduct) iterator.next();
            if (registeredItem != null)
            {
                // Add the item configuration data to our list for saving.
                allItems.add(registeredItem.getData());
            }
        }

        return allItems.toArray(new MadItemFactoryProductData[]{});
    }

    /** Parses registered items base names for first parameter, if located then parses all sub-items of the given
     *  item looking for match. Once base name and sub-item name are located a copy of the associated item is returned
     *  as an ItemStack with the required amount. */
    public ItemStack getItemStackByFullyQualifiedName(String baseName, String subItemName, int returnAmount)
    {
        // Primary input cannot be null.
        if (baseName == null)
        {
            return null;
        }
        
        if (baseName.isEmpty())
        {
            return null;
        }
        
        // No upper-case allowed.
        baseName = baseName.toLowerCase();
        subItemName = subItemName.toLowerCase();
        
        // Search through master list of registered items.
        if (!MadItemFactory.registeredItems.containsKey(baseName))
        {
            return null;
        }
        
        // Grab the registered item instance.
        MadItemFactoryProduct registeredItem = MadItemFactory.registeredItems.get(baseName);
        if (registeredItem == null)
        {
            return null;
        }
        
        // Determine if we need to look inside sub-items or not.
        if (subItemName != null && !subItemName.isEmpty() && registeredItem.hasSubItems())
        {
            // Locate sub-item name inside registered item.
            for (MadMetaItemData subItem : registeredItem.getSubItems())
            {
                if (subItem.getItemName().equals(subItemName))
                {
                    // Sub-item located, we return the requested amount of them.
                    return new ItemStack(registeredItem.getItem(), returnAmount, subItem.getMetaID());
                }
            }
        }
        else if (subItemName == null || (subItemName != null && subItemName.isEmpty()))
        {
            // Just return the base item since that is all we are looking for.
            return new ItemStack(registeredItem.getItem(), returnAmount, 0);
        }
        
        // Default response is to return nothing.
        return null;
    }

    /** Returns true if the given input item is apart of the given base registered type.
     *  This is determined first by checking if the base type exists as a key and if input
     *  item equals this key. */
    public boolean isItemInstanceOfRegisteredBaseType(Item compareItem, String baseItemTypeName)
    {
        // Null checks.
        if (compareItem == null)
        {
            return false;
        }
        
        if (baseItemTypeName == null)
        {
            return false;
        }
        
        if (baseItemTypeName.isEmpty())
        {
            return false;
        }
        
        // No upper-case allowed.
        baseItemTypeName = baseItemTypeName.toLowerCase();
        
        // Check if base type exists as key in registered types.
        if (MadItemFactory.registeredItems.containsKey(baseItemTypeName))
        {
            // Check if these names match.
            if (MadUtils.cleanTag(compareItem.getUnlocalizedName()).equals(baseItemTypeName))
            {
                return true;
            }
        }
        else
        {
            return false;
        }
        
        // Default response is to always say no.
        return false;
    }

}

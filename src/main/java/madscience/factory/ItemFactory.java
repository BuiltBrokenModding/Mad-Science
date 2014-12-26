package madscience.factory;


import cpw.mods.fml.common.registry.GameRegistry;
import madscience.item.SubItemsArchive;
import madscience.item.UnregisteredItem;
import madscience.mod.ModLoader;
import madscience.product.ItemFactoryProduct;
import madscience.util.MiscUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.*;


public class ItemFactory
{
    private static final Map<String, ItemFactoryProduct> registeredItems =
            new LinkedHashMap<String, ItemFactoryProduct>();
    private static ItemFactory instance;

    public ItemFactory()
    {
        super();
    }

    public static synchronized ItemFactory instance()
    {
        if (instance == null)
        {
            instance = new ItemFactory();
        }

        return instance;
    }

    public ItemFactoryProduct registerItem(UnregisteredItem itemData) throws IllegalArgumentException
    {
        // Pass the data object into the product to activate it, creates needed data structures inside it based on data supplied.
        ItemFactoryProduct itemProduct = new ItemFactoryProduct( itemData );

        // Check to make sure we have not added this item before.
        if (! isValidItemID( itemProduct.getItemBaseName() ))
        {
            throw new IllegalArgumentException( "Duplicate ItemFactoryProduct '" +
                                                itemProduct.getItemBaseName() +
                                                "' was added. Execution halted!" );
        }

        // Debugging!
        ModLoader.log().info( "[ItemFactory]Registering item: " + itemProduct.getItemBaseName() );

        // Actually register the item with the product listing.
        registeredItems.put( itemProduct.getItemBaseName(),
                             itemProduct );

        // Register the item with Minecraft/Forge.
        GameRegistry.registerItem( itemProduct.getItem(),
                                   itemProduct.getItemBaseName() );

        return itemProduct;
    }

    public ItemFactoryProduct getItemInfo(String id)
    {
        return registeredItems.get( id );
    }

    public Collection<ItemFactoryProduct> getItemInfoList()
    {
        return Collections.unmodifiableCollection( registeredItems.values() );
    }

    public boolean isValidItemID(String id)
    {
        return ! registeredItems.containsKey( id );
    }

    public List<UnregisteredItem> getItemDataList()
    {
        // Loop through every registered item in the system.
        List<UnregisteredItem> allItems = new ArrayList<UnregisteredItem>();
        for (Iterator iterator = getItemInfoList().iterator(); iterator.hasNext(); )
        {
            ItemFactoryProduct registeredItem = (ItemFactoryProduct) iterator.next();
            if (registeredItem != null)
            {
                // Add the item configuration data to our list for saving.
                allItems.add( registeredItem.getData() );
            }
        }

        // Sort the list alphabetically.
        Collections.sort( allItems,
                          new Comparator<UnregisteredItem>()
                          {
                              @Override
                              public int compare(final UnregisteredItem object1, final UnregisteredItem object2)
                              {
                                  return object1.getItemBaseName().compareTo( object2.getItemBaseName() );
                              }
                          } );

        return allItems;
    }

    /**
     * Parses registered items base names for first parameter, if located then parses all sub-items of the given
     * item looking for match. Once base name and sub-item name are located a copy of the associated item is returned
     * as an ItemStack with the required amount.
     */
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
        if (! ItemFactory.registeredItems.containsKey( baseName ))
        {
            return null;
        }

        // Grab the registered item instance.
        ItemFactoryProduct registeredItem = ItemFactory.registeredItems.get( baseName );
        if (registeredItem == null)
        {
            return null;
        }

        // Determine if we need to look inside sub-items or not.
        if (subItemName != null && ! subItemName.isEmpty() &&
            registeredItem.hasSubItems())
        {
            // Locate sub-item name inside registered item.
            for (SubItemsArchive subItem : registeredItem.getSubItems())
            {
                if (subItem.getItemName().equals( subItemName ))
                {
                    // Sub-item located, we return the requested amount of them.
                    return new ItemStack( registeredItem.getItem(),
                                          returnAmount,
                                          subItem.getMetaID() );
                }
            }
        }
        else if (subItemName == null || (subItemName != null && subItemName.isEmpty()))
        {
            // Just return the base item since that is all we are looking for.
            return new ItemStack( registeredItem.getItem(),
                                  returnAmount,
                                  0 );
        }

        // Default response is to return nothing.
        return null;
    }

    /**
     * Returns true if the given input item is apart of the given base registered type.
     * This is determined first by checking if the base type exists as a key and if input
     * item equals this key.
     */
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
        if (ItemFactory.registeredItems.containsKey( baseItemTypeName ))
        {
            // Check if these names match.
            if (MiscUtils.cleanTag( compareItem.getUnlocalizedName() ).equals( baseItemTypeName ))
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

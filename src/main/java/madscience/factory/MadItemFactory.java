package madscience.factory;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import madscience.MadForgeMod;
import madscience.factory.item.MadItemFactoryProduct;
import madscience.factory.item.MadItemFactoryProductData;
import madscience.factory.mod.MadMod;
import net.minecraft.util.Icon;
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
        
        // Register client only information such as rendering and model information to the given item.
        MadForgeMod.proxy.registerRenderingHandler(itemProduct.getItemID());

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

    /** Associates a loaded Minecraft/Forge asset with given sub-item render pass. */
    public void loadRenderPassIcon(
            String baseItemName,
            String subItemName,
            int renderPass,
            Icon icon)
    {
        // Check if valid registered item name.
        if (this.registeredItems.containsKey(baseItemName))
        {
            // Update the current item product instance from factory.
            this.registeredItems.get(baseItemName).loadRenderPassIcon(
                    subItemName,
                    renderPass,
                    icon);
        }
    }
}

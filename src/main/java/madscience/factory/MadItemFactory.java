package madscience.factory;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import cpw.mods.fml.common.registry.GameRegistry;
import madscience.MadForgeMod;
import madscience.factory.block.MadBlockTooltip;
import madscience.factory.item.MadItemFactoryProduct;
import madscience.factory.item.MadItemFactoryProductData;
import madscience.factory.mod.MadMod;

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
        if (!isValidItemID(itemProduct.getItemName()))
        {
            throw new IllegalArgumentException("Duplicate MadItemFactoryProduct '" + itemProduct.getItemName() + "' was added. Execution halted!");
        }

        // Debugging!
        MadMod.log().info("[MadItemFactory]Registering item: " + itemProduct.getItemName());
        
        // Actually register the item with the product listing.
        registeredItems.put(itemProduct.getItemName(), itemProduct);
        
        // Register the item with Minecraft/Forge.
        GameRegistry.registerItem(itemProduct.getItemInstance(), itemProduct.getItemName());
        
        // Register client only information such as rendering and model information to the given item.
        MadForgeMod.proxy.registerRenderingHandler(itemProduct.getItemID());

        return itemProduct;
    }
    
    public MadItemFactoryProduct getItemInfo(String id)
    {
        return registeredItems.get(id);
    }
    
    public Collection<MadItemFactoryProduct> getMachineInfoList()
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
        Set<MadItemFactoryProductData> allMachines = new HashSet<MadItemFactoryProductData>();
        for (Iterator iterator = getMachineInfoList().iterator(); iterator.hasNext();)
        {
            MadItemFactoryProduct registeredMachine = (MadItemFactoryProduct) iterator.next();
            if (registeredMachine != null)
            {
                // Add the item configuration data to our list for saving.
                allMachines.add(registeredMachine.getData());
            }
        }

        return allMachines.toArray(new MadItemFactoryProductData[]{});
    }
}

package madscience.factory;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import madscience.factory.block.MadBlockFactoryProduct;
import madscience.factory.block.MadBlockFactoryProductData;
import madscience.factory.block.template.MadBlockTooltip;
import madscience.factory.mod.MadMod;
import cpw.mods.fml.common.registry.GameRegistry;

public class MadBlockFactory
{
    private static MadBlockFactory instance;
    
    private static final Map<String, MadBlockFactoryProduct> registeredBlocks = new LinkedHashMap<String, MadBlockFactoryProduct>();
    
    public MadBlockFactory()
    {
        super();
    }
    
    public static synchronized MadBlockFactory instance()
    {
        if (instance == null)
        {
            instance = new MadBlockFactory();
        }
        
        return instance;
    }
    
    public MadBlockFactoryProduct registerBlock(MadBlockFactoryProductData blockData) throws IllegalArgumentException
    {
        // Pass the data object into the product to activate it, creates needed data structures inside it based on data supplied.
        MadBlockFactoryProduct blockProduct = new MadBlockFactoryProduct(blockData);

        // Check to make sure we have not added this item before.
        if (!isValidItemID(blockProduct.getBlockBaseName()))
        {
            throw new IllegalArgumentException("Duplicate MadBlockFactoryProduct '" + blockProduct.getBlockBaseName() + "' was added. Execution halted!");
        }

        // Debugging!
        MadMod.log().info("[MadBlockFactory]Registering block: " + blockProduct.getBlockBaseName());
        
        // Actually register the block with the product listing.
        registeredBlocks.put(blockProduct.getBlockBaseName(), blockProduct);
        
        // Register the block with Minecraft/Forge.
        GameRegistry.registerBlock(blockProduct.getBlock(), MadBlockTooltip.class, blockProduct.getBlockBaseName());

        return blockProduct;
    }
    
    public boolean isValidItemID(String blockBaseName)
    {
        return this.registeredBlocks.containsKey(blockBaseName);
    }

    public MadBlockFactoryProduct getBlockInfo(String id)
    {
        return this.registeredBlocks.get(id);
    }
    
    public Collection<MadBlockFactoryProduct> getBlockInfoList()
    {
        return Collections.unmodifiableCollection(this.registeredBlocks.values());
    }
    
    public MadBlockFactoryProductData[] getBlockDataList()
    {
        // Loop through every registered block in the system.
        Set<MadBlockFactoryProductData> allBlocks = new HashSet<MadBlockFactoryProductData>();
        for (Iterator iterator = getBlockInfoList().iterator(); iterator.hasNext();)
        {
            MadBlockFactoryProduct registeredBlock = (MadBlockFactoryProduct) iterator.next();
            if (registeredBlock != null)
            {
                // Add the block configuration data to our list for saving.
                allBlocks.add(registeredBlock.getData());
            }
        }

        return allBlocks.toArray(new MadBlockFactoryProductData[]{});
    }
}

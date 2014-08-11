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
import madscience.factory.block.MadMetaBlockData;
import madscience.factory.block.template.MadBlockTooltip;
import madscience.factory.mod.MadMod;
import madscience.util.MadUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
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
    
    /** Associates a loaded Minecraft/Forge asset with given sub-block render pass. */
    public void loadRenderPassIcon(
            String baseBlockName,
            String subBlockName,
            int renderPass,
            Icon icon)
    {
        // Check if valid registered block name.
        if (MadBlockFactory.registeredBlocks.containsKey(baseBlockName))
        {
            // Update the current block product instance from factory.
            MadBlockFactory.registeredBlocks.get(baseBlockName).loadRenderPassIcon(
                    subBlockName,
                    renderPass,
                    icon);
        }
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
    
    /** Parses registered block base names for first parameter, if located then parses all sub-blocks of the given
     *  block looking for match. Once base name and sub-block name are located a copy of the associated block is returned
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
        
        // Search through master list of registered blocks.
        if (!MadBlockFactory.registeredBlocks.containsKey(baseName))
        {
            return null;
        }
        
        // Grab the registered block instance.
        MadBlockFactoryProduct registeredItem = MadBlockFactory.registeredBlocks.get(baseName);
        if (registeredItem == null)
        {
            return null;
        }
        
        // Determine if we need to look inside sub-blocks or not.
        if (subItemName != null && !subItemName.isEmpty() && registeredItem.hasSubBlocks())
        {
            // Locate sub-block name inside registered block.
            for (MadMetaBlockData subItem : registeredItem.getSubBlocks())
            {
                if (subItem.getSubBlockName().equals(subItemName))
                {
                    // Sub-block located, we return the requested amount of them.
                    return new ItemStack(registeredItem.getBlock(), returnAmount, subItem.getMetaID());
                }
            }
        }
        else if (subItemName == null || (subItemName != null && subItemName.isEmpty()))
        {
            // Just return the base block since that is all we are looking for.
            return new ItemStack(registeredItem.getBlock(), returnAmount, 0);
        }
        
        // Default response is to return nothing.
        return null;
    }

    /** Returns true if the given input item is apart of the given base registered type.
     *  This is determined first by checking if the base type exists as a key and if input
     *  block equals this key. */
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
        if (MadBlockFactory.registeredBlocks.containsKey(baseItemTypeName))
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

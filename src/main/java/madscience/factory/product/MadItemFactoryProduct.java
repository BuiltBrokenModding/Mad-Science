package madscience.factory.product;

import madscience.factory.data.MadItemFactoryProductData;
import madscience.factory.item.MadItemPrefab;
import madscience.factory.item.MadItemRenderPass;
import madscience.factory.item.MadMetaItemData;
import net.minecraft.util.Icon;

public class MadItemFactoryProduct
{
    /** Holds all serializable data related to creation of items. */
    private MadItemFactoryProductData data;
    
    /** Stores reference to item logic class itself which makes up logic or brains of this item. */
    private Class<? extends MadItemPrefab> logicClass;
    
    /** Reference to actual item used by Minecraft/Forge and what will register against it. */
    private MadItemPrefab item;
    
    public MadItemFactoryProduct(MadItemFactoryProductData itemData)
    {
        super();
        
        // Primary data source for this item, typically loaded from JSON.
        this.data = itemData;
        
        // Optional controller class that gives advanced control over item behavior and logic.
        this.logicClass = getLogicClassByNamespace(itemData.getLogicClassFullyQualifiedName());
        
        // Create the item that we will register against Minecraft/Forge.
        if (this.logicClass != null)
        {
            this.item = getNewItemLogicClassInstance();
        }
        else
        {
            this.item = new MadItemPrefab(this);
        }
    }
    
    /** Consumes namespace string for logic class and attempts to load and create a new instance of it for use in the game world. */
    public MadItemPrefab getNewItemLogicClassInstance()
    {
        // Attempt to create a new instance of the logic class that was passed to us at creation.
        try
        {
            return logicClass.getDeclaredConstructor(MadItemFactoryProduct.class).newInstance(this);
        }
        catch (Exception err)
        {
            // Something terrible has happened!
            err.printStackTrace();
        }

        // Default response is to return nothing!
        return null;
    }
    
    /** Converts fully qualified domain name for a given class into that class. It must be based on MadItemPrefab or loading will fail! */
    private Class<? extends MadItemPrefab> getLogicClassByNamespace(String fullyQualifiedName)
    {
        // Check input data for null.
        if (fullyQualifiedName == null)
        {
            return null;
        }
        
        // Check if there is any string data at all.
        if (fullyQualifiedName.isEmpty())
        {
            return null;
        }
        
        Class<? extends MadItemPrefab> tempClass = null;
        try
        {
            // Forcefully cast whatever we attempt to load as MadItemPrefab since that is the only thing we will work with.
            tempClass = (Class<? extends MadItemPrefab>) Class.forName(fullyQualifiedName);
            if (tempClass != null)
            {
                return tempClass;
            }
        }
        catch (ClassNotFoundException err)
        {
            err.printStackTrace();
        }
        
        // Default response is to return nothing!
        return null;
    }

    public MadItemFactoryProductData getData()
    {
        return data;
    }

    public Class<? extends MadItemPrefab> getLogicClass()
    {
        return logicClass;
    }

    public int getItemID()
    {
        return data.getItemID();
    }

    public String getItemBaseName()
    {
        return data.getItemBaseName();
    }

    public boolean isNoRepair()
    {
        return data.isNoRepair();
    }

    public int getMaxDamage()
    {
        return data.getMaxDamage();
    }

    public int getMaxStacksize()
    {
        return data.getMaxStacksize();
    }

    public boolean canHarvestBlocks()
    {
        return data.isCanHarvestBlocks();
    }

    public int getDamageVSEntity()
    {
        return data.getDamageVSEntity();
    }

    public int getEnchantability()
    {
        return data.getEnchantability();
    }

    public float getDamageVSBlock()
    {
        return data.getDamageVSBlocks();
    }

    public MadItemPrefab getItem()
    {
        return item;
    }

    public boolean hasSubItems()
    {
        // Every item has 1 default sub-item, if greater than that we have multiple sub-types.
        if (data.getSubItemsArchive() != null &&
            data.getSubItemsArchive().length > 1)
        {
            return true;
        }
        
        return false;
    }

    public MadMetaItemData getSubItemByDamageValue(int itemDamage)
    {
        // Loop through all sub-items looking for match.
        for (MadMetaItemData subItem : data.getSubItemsArchive())
        {
            // Check if sub-item meta ID (damage value) matches input parameter.
            if (subItem.getMetaID() == itemDamage)
            {
                return subItem;
            }
        }
        
        // Default response is null since that damage value is not mapped to any sub-item.
        return null;
    }

    public MadMetaItemData[] getSubItems()
    {
        return data.getSubItemsArchive();
    }

    /** Determine if we need more than a single render pass for this item. */
    public boolean requiresMultipleRenderPasses()
    {
        for (MadMetaItemData subItem : this.getSubItems())
        {
            // Return true on the first instance of having more than a single render pass for the entire item.
            if (subItem.getRenderPassCount() > 1)
            {
                return true;
            }
        }
        
        // Default response is to support only a single render pass.
        return false;
    }

    /** Associates a loaded Minecraft/Forge icon with given sub-item render pass. */
    public void loadRenderPassIcon(
            String subItemName,
            int renderPass,
            Icon icon)
    {
        // Loop through all sub-items looking for the one we want to update.
        for (MadMetaItemData subItem : this.getSubItems())
        {
            if (subItem.getItemName().equals(subItemName))
            {
                // Locate the matching render pass inside of this sub-item.
                for (MadItemRenderPass renderPassObject : subItem.getRenderPassArchive())
                {
                    if (renderPassObject.getRenderPass() == renderPass && !renderPassObject.isLoadedIcon())
                    {
                        // Update the icon of this matching sub-item render type.
                        renderPassObject.setIcon(icon);
                    }
                }
            }
        }
    }
}

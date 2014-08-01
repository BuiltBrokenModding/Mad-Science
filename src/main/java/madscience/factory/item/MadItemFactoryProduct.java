package madscience.factory.item;

import madscience.factory.item.prefab.MadItemPrefab;
import madscience.factory.tileentity.MadTileEntityFactoryProduct;

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
        this.item = new MadItemPrefab(this);
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

    public String getItemName()
    {
        return data.getItemBaseName();
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

    public boolean requiresMultipleRenderPasses()
    {
        return .;
    }
}

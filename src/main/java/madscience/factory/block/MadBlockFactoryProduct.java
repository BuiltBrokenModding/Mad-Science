package madscience.factory.block;

import madscience.factory.block.prefab.MadBlockPrefab;

public class MadBlockFactoryProduct
{
    /** Contains all information loaded from factory and JSON loader. */
    private MadBlockFactoryProductData data = null;
    
    /** Reference to optional class which can control and override functionality on this block. */
    private Class<? extends MadBlockPrefab> logicClass = null;
    
    /** Reference that will be populated by instance of registered block for use in game world. */
    private MadBlockPrefab block;
    
    public MadBlockFactoryProduct(MadBlockFactoryProductData blockData)
    {
        super();
        
        this.data = blockData;
        
        this.logicClass = getLogicClassByNamespace(blockData.getLogicClassFullyQualifiedName());
        
        if (this.logicClass != null)
        {
            this.block = getNewItemLogicClassInstance();
        }
        else
        {
            this.block = new MadBlockPrefab(this);
        }
    }
    
    /** Consumes namespace string for logic class and attempts to load and create a new instance of it for use in the game world. */
    public MadBlockPrefab getNewItemLogicClassInstance()
    {
        // Attempt to create a new instance of the logic class that was passed to us at creation.
        try
        {
            return logicClass.getDeclaredConstructor(MadBlockFactoryProduct.class).newInstance(this);
        }
        catch (Exception err)
        {
            // Something terrible has happened!
            err.printStackTrace();
        }

        // Default response is to return nothing!
        return null;
    }
    
    /** Converts fully qualified domain name for a given class into that class. It must be based on MadBlockPrefab or loading will fail! */
    private Class<? extends MadBlockPrefab> getLogicClassByNamespace(String fullyQualifiedName)
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
        
        Class<? extends MadBlockPrefab> tempClass = null;
        try
        {
            // Forcefully cast whatever we attempt to load as MadBlockPrefab since that is the only thing we will work with.
            tempClass = (Class<? extends MadBlockPrefab>) Class.forName(fullyQualifiedName);
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

    public int getBlockID()
    {
        return data.getBlockID();
    }

    public String getBlockBaseName()
    {
        return data.getBlockName();
    }

    public MadBlockFactoryProductData getData()
    {
        return data;
    }

    public Class<? extends MadBlockPrefab> getLogicClass()
    {
        return logicClass;
    }

    public MadBlockPrefab getBlock()
    {
        return block;
    }

    public float getLightBrightness()
    {
        return data.getLightBrightness();
    }

    public int getLightOpacity()
    {
        return data.getLightOpacity();
    }

    public float getBlockExplosionResistance()
    {
        return data.getExplosionResistance();
    }

    public float getBlockHardness()
    {
        return data.getBlockHardness();
    }

    public int getBlockFireResistance()
    {
        return data.getFireResistance();
    }
}

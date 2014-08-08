package madscience.factory.block;

import com.google.gson.annotations.Expose;

public class MadBlockFactoryProductData
{
    /** Holds the internal name of this block as used in config files and referenced in other lists. */
    @Expose
    private String blockName;
    
    /** Holds string reference to our logic class which will be populated at runtime. */
    @Expose
    private String logicClassFullyQualifiedName;
    
    /** Determines in Minecraft terms how hard it is to break this particular block with tools. */
    @Expose
    private float blockHardness;
    
    /** Determines in Minecraft terms how resistant this block will be to being destroyed by explosions (if modflag for world damage is enabled). */
    @Expose
    private float explosionResistance;
    
    /** Determines how resistant this block will be to fire in the game world. Higher values make for higher chances of catching on fire when near open flame. */
    @Expose
    private int fireResistance;
    
    /** Sets how much light is blocked going through this block. */
    @Expose
    private int lightOpacity;
    
    /** Sets the amount of light emitted by a block from 0.0f to 1.0f (converts internally to 0-15). */
    @Expose
    private float lightBrightness;
    
    /** All blocks can have meta data which treats damage values as another block. This archive defines all those sub-blocks based on damage value. */
    @Expose
    private MadMetaBlockData[] subBlocksArchive;
    
    /** Reference number used by Forge/MC to keep track of this block. */
    private int blockID;
    
    public MadBlockFactoryProductData(
            String blockName,
            String logicClassFullyQualifiedName,
            float blockHardness,
            float explosionResistance,
            int fireResistance,
            int lightOpacity,
            float lightBrightness,
            MadMetaBlockData[] subBlocksArchive)
    {
        super();
        
        this.blockName = blockName;
        this.logicClassFullyQualifiedName = logicClassFullyQualifiedName;
        this.blockHardness = blockHardness;
        this.explosionResistance = explosionResistance;
        this.fireResistance = fireResistance;
        this.lightOpacity = lightOpacity;
        this.lightBrightness = lightBrightness;
        this.subBlocksArchive = subBlocksArchive;
    }

    public String getBlockName()
    {
        return blockName;
    }

    public void setBlockName(String blockName)
    {
        this.blockName = blockName;
    }

    public String getLogicClassFullyQualifiedName()
    {
        return logicClassFullyQualifiedName;
    }

    public void setLogicClassFullyQualifiedName(String logicClassFullyQualifiedName)
    {
        this.logicClassFullyQualifiedName = logicClassFullyQualifiedName;
    }

    public float getBlockHardness()
    {
        return blockHardness;
    }

    public void setBlockHardness(float blockHardness)
    {
        this.blockHardness = blockHardness;
    }

    public float getExplosionResistance()
    {
        return explosionResistance;
    }

    public void setExplosionResistance(float explosionResistance)
    {
        this.explosionResistance = explosionResistance;
    }

    public int getFireResistance()
    {
        return fireResistance;
    }

    public void setFireResistance(int fireResistance)
    {
        this.fireResistance = fireResistance;
    }

    public int getLightOpacity()
    {
        return lightOpacity;
    }

    public void setLightOpacity(int lightOpacity)
    {
        this.lightOpacity = lightOpacity;
    }

    public float getLightBrightness()
    {
        return lightBrightness;
    }

    public void setLightBrightness(float lightBrightness)
    {
        this.lightBrightness = lightBrightness;
    }

    public MadMetaBlockData[] getSubBlocksArchive()
    {
        return subBlocksArchive;
    }

    public void setSubBlocksArchive(MadMetaBlockData[] subBlocksArchive)
    {
        this.subBlocksArchive = subBlocksArchive;
    }

    public int getBlockID()
    {
        return blockID;
    }

    public void setBlockID(int blockID)
    {
        this.blockID = blockID;
    }
}

package madscience.factory.data;

import madscience.factory.item.MadMetaItemData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MadItemFactoryProductData
{
    /** Defines name of item before any sub-items. Final form would be like itemBaseName.itemName. */
    @Expose
    @SerializedName("ItemBaseName")
    private String itemBaseName;
    
    /** Determines if this item can be repairs in an anvil or by crafting it with other items of same type. */
    @Expose
    @SerializedName("NoRepair")
    private boolean noRepair;
    
    /** Maximum amount of damage this given item can take before it is considered destroyed, this can also be metadata to determine which item is what. */
    @Expose
    @SerializedName("MaxDamage")
    private int maxDamage;

    /** Determines how many of this item can be stacked into a single inventory slot. Default is 64. */
    @Expose
    @SerializedName("MaxStackSize")
    private int maxStacksize;
    
    /** Amount of damage this item does against mobs and other players. */
    @Expose
    @SerializedName("DamageVSEntity")
    private int damageVSEntity;
    
    /** Amount of damage this item does against other blocks and tiles in the game world. */
    @Expose
    @SerializedName("DamageVSBlocks")
    private float damageVSBlocks;
    
    /** Number determines what level of enchantments Minecraft/Forge is able to apply to this item. */
    @Expose
    @SerializedName("Enchantability")
    private int enchantability;
    
    /** Determines if this item can begin executing code to damage and pickup blocks in the game world. */
    @Expose
    @SerializedName("CanHarvestBlocks")
    private boolean canHarvestBlocks;

    /** Class that will store logic specific to this entity, useful for advanced things like weapons. */
    @Expose
    @SerializedName("LogicClassFullyQualifiedName")
    private String logicClassFullyQualifiedName;
    
    /** All items can have meta data which treats damage values as another item. This archive defines all those sub items based on damage value. */
    @Expose
    @SerializedName("SubItemsArchive")
    private MadMetaItemData[] subItemsArchive;
    
    /** Not saved by JSON loaded but is configured by Minecraft/Forge configuration file class. */
    private int itemID;
    
    public MadItemFactoryProductData(
            String itemBaseName,
            boolean noRepair,
            int maxDamage,
            int maxStacksize,
            int damageVSEntity,
            float damageVSBlocks,
            int enchantability,
            boolean canHarvestBlocks,
            String logicClassFullyQualifiedName,
            MadMetaItemData[] subItemsArchive)
    {
        super();
        
        this.itemBaseName = itemBaseName.toLowerCase();
        this.noRepair = noRepair;
        this.maxDamage = maxDamage;
        this.maxStacksize = maxStacksize;
        this.damageVSEntity = damageVSEntity;
        this.damageVSBlocks = damageVSBlocks;
        this.enchantability = enchantability;
        this.canHarvestBlocks = canHarvestBlocks;
        this.logicClassFullyQualifiedName = logicClassFullyQualifiedName;
        this.subItemsArchive = subItemsArchive;
    }

    public String getItemBaseName()
    {
        return itemBaseName;
    }

    public void setItemBaseName(String itemBaseName)
    {
        this.itemBaseName = itemBaseName;
    }

    public int getItemID()
    {
        return itemID;
    }

    public void setItemID(int itemID)
    {
        this.itemID = itemID;
    }

    public boolean isNoRepair()
    {
        return noRepair;
    }

    public void setNoRepair(boolean noRepair)
    {
        this.noRepair = noRepair;
    }

    public int getMaxDamage()
    {
        return maxDamage;
    }

    public void setMaxDamage(int maxDamage)
    {
        this.maxDamage = maxDamage;
    }

    public int getMaxStacksize()
    {
        return maxStacksize;
    }

    public void setMaxStacksize(int maxStacksize)
    {
        this.maxStacksize = maxStacksize;
    }

    public int getDamageVSEntity()
    {
        return damageVSEntity;
    }

    public void setDamageVSEntity(int damageVSEntity)
    {
        this.damageVSEntity = damageVSEntity;
    }

    public float getDamageVSBlocks()
    {
        return damageVSBlocks;
    }

    public void setDamageVSBlocks(float damageVSBlocks)
    {
        this.damageVSBlocks = damageVSBlocks;
    }

    public int getEnchantability()
    {
        return enchantability;
    }

    public void setEnchantability(int enchantability)
    {
        this.enchantability = enchantability;
    }

    public boolean isCanHarvestBlocks()
    {
        return canHarvestBlocks;
    }

    public void setCanHarvestBlocks(boolean canHarvestBlocks)
    {
        this.canHarvestBlocks = canHarvestBlocks;
    }

    public String getLogicClassFullyQualifiedName()
    {
        return logicClassFullyQualifiedName;
    }

    public void setLogicClassFullyQualifiedName(String logicClassFullyQualifiedName)
    {
        this.logicClassFullyQualifiedName = logicClassFullyQualifiedName;
    }

    public MadMetaItemData[] getSubItemsArchive()
    {
        return subItemsArchive;
    }

    public void setSubItemsArchive(MadMetaItemData[] subItemsArchive)
    {
        this.subItemsArchive = subItemsArchive;
    }
}

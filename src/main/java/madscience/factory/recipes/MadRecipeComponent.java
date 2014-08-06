package madscience.factory.recipes;

import madscience.factory.container.MadSlotContainerTypeEnum;
import madscience.factory.mod.MadMod;
import net.minecraft.item.ItemStack;

import com.google.gson.annotations.Expose;

public class MadRecipeComponent
{
    /** Determines where an individual recipe item will go in slot. */
    @Expose private MadSlotContainerTypeEnum slotDestination;
    
    /** Holds reference to the item (meta or otherwise) that we expect in given slot. */
    @Expose private String slotExpectedItem;
    
    /** Reference to how many of the given item we should have for this slot. */
    @Expose private int slotExpectedAmount;
    
    /** Reference to mod ID the given item should come from. */
    @Expose private String parentModID;
    
    /** Reference to how much damage this item has or in the case of meta items determines which one it will be. */
    @Expose private String metaDamage;
    
    /** Determines if this recipe has been recognized by Minecraft/Forge GameRegistry as being valid. */
    private boolean hasLoaded = false;
    
    /** Stores reference to recipe component ItemStack (with metadata). Populated when loadRecipes() is called.*/
    private ItemStack[] associatedItemStack = null;
    
    MadRecipeComponent(MadSlotContainerTypeEnum slotDestination, String parentModID, String internalName, String metaDamage, int amount)
    {
        super();
        
        this.slotDestination = slotDestination;
        this.parentModID = parentModID;
        this.slotExpectedItem = internalName;
        this.metaDamage = metaDamage;
        this.slotExpectedAmount = amount;
    }

    public MadSlotContainerTypeEnum getSlotType()
    {
        return this.slotDestination;
    }

    public String getInternalName()
    {
        return this.slotExpectedItem;
    }

    public int getAmount()
    {
        return this.slotExpectedAmount;
    }
    
    public String getNameWithModID()
    {
        return this.parentModID + ":" + this.slotExpectedItem;
    }

    public String getMetaDamage()
    {
        return this.metaDamage;
    }

    public boolean isLoaded()
    {
        return this.hasLoaded;
    }

    public ItemStack[] getItemStackArray()
    {
        if (!this.hasLoaded)
        {
            MadMod.log().warning("[MadRecipeComponent]Cannot return associated itemstack for recipe since it was never loaded!");
            return null;
        }
        
        return this.associatedItemStack;
    }

    public void associateItemStackToRecipeComponent(ItemStack[] associatedItemStack)
    {
        // Prevent double-loading!
        if (hasLoaded)
        {
            MadMod.log().warning("[MadRecipeComponent]Already loaded and verified this recipe with GameRegistry!");
            return;
        }
        
        if (this.associatedItemStack != null)
        {
            MadMod.log().warning("[MadRecipeComponent]Associated item stack is not null! How can this be?!");
            return;
        }
        
        // Make sure this cannot happen twice.
        hasLoaded = true;
        
        // Take a copy of the inputed parameter item for future reference.
        this.associatedItemStack = associatedItemStack;
    }

    public String getModID()
    {
        return this.parentModID;
    }

    public String getExpectedItemName()
    {
        return this.slotExpectedItem;
    }
}

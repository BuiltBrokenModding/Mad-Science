package madscience.factory.furnace;

import madscience.factory.mod.MadMod;
import net.minecraft.item.ItemStack;

import com.google.gson.annotations.Expose;

public class MadFurnaceRecipeComponent
{
    @Expose private String itemName;
    
    @Expose private int itemAmount;
    
    @Expose private String parentModID;
    
    @Expose private String metaDamage;
    
    private boolean hasLoaded = false;
    
    private ItemStack[] associatedItemStack = null;
    
    MadFurnaceRecipeComponent(String parentModID, String internalName, String metaDamage, int amount)
    {
        super();
        
        this.parentModID = parentModID;
        this.itemName = internalName;
        this.metaDamage = metaDamage;
        this.itemAmount = amount;
    }

    public String getInternalName()
    {
        return this.itemName;
    }

    public int getAmount()
    {
        return this.itemAmount;
    }
    
    public String getNameWithModID()
    {
        return this.parentModID + ":" + this.itemName;
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
            MadMod.log().warning("[MadFurnaceRecipeComponent]Cannot return associated itemstack for recipe since it was never loaded!");
            return null;
        }
        
        return this.associatedItemStack;
    }

    public void associateItemStackToRecipeComponent(ItemStack[] associatedItemStack)
    {
        // Prevent double-loading!
        if (hasLoaded)
        {
            MadMod.log().warning("[MadFurnaceRecipeComponent]Already loaded and verified this recipe with GameRegistry!");
            return;
        }
        
        if (this.associatedItemStack != null)
        {
            MadMod.log().warning("[MadFurnaceRecipeComponent]Associated item stack is not null! How can this be?!");
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
}

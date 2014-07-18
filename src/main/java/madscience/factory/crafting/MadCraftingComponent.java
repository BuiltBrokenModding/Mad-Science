package madscience.factory.crafting;

import madscience.factory.mod.MadMod;
import net.minecraft.item.ItemStack;

import com.google.gson.annotations.Expose;

public class MadCraftingComponent
{
    /** Holds reference to the item (meta or otherwise) that we expect in given slot. */
    @Expose private String slotExpectedItem;
    
    /** Reference to how many of the given item we should have for this slot. */
    @Expose private int slotExpectedAmount;
    
    /** Reference to mod ID the given item should come from. */
    @Expose private String parentModID;
    
    /** Reference to how much damage this item has or in the case of meta items determines which one it will be. */
    @Expose private String metaDamage;
    
    /** Reference to position this item needs to be in the crafting grid to maintain list order. */
    @Expose private int craftingGridPosition;
    
    /** Determines if this recipe has been recognized by Minecraft/Forge GameRegistry as being valid. */
    private boolean hasLoaded = false;
    
    /** Stores reference to recipe component ItemStack (with metadata). Populated when loadRecipes() is called.*/
    private ItemStack[] associatedItemStack = null;
    
    MadCraftingComponent(int craftingGridPosition, String parentModID, String internalName, String metaDamage, int amount)
    {
        super();
        
        this.craftingGridPosition = craftingGridPosition;
        this.parentModID = parentModID;
        this.slotExpectedItem = internalName;
        this.metaDamage = metaDamage;
        this.slotExpectedAmount = amount;
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
            MadMod.LOGGER.warning("[MadRecipeComponent]Cannot return associated itemstack for recipe since it was never loaded!");
            return null;
        }
        
        return this.associatedItemStack;
    }

    public void associateItemStackToRecipeComponent(ItemStack[] associatedItemStack)
    {
        // Prevent double-loading!
        if (hasLoaded)
        {
            MadMod.LOGGER.warning("[MadRecipeComponent]Already loaded and verified this recipe with GameRegistry!");
            return;
        }
        
        if (this.associatedItemStack != null)
        {
            MadMod.LOGGER.warning("[MadRecipeComponent]Associated item stack is not null! How can this be?!");
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

    public int getCraftingGridPosition()
    {
        return this.craftingGridPosition;
    }

    public Character getCraftingGridPositionAsCharacter()
    {
        switch(this.craftingGridPosition)
        {
            case 0:
                return '0';
            case 1:
                return '1';
            case 2:
                return '2';
            case 3:
                return '3';
            case 4:
                return '4';
            case 5:
                return '5';
            case 6:
                return '6';
            case 7:
                return '7';
            case 8:
                return '8';
            default:
                return null;
        }
    }
}

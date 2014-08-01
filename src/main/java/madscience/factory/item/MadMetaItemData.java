package madscience.factory.item;

import com.google.gson.annotations.Expose;

import madscience.factory.crafting.MadCraftingRecipe;
import madscience.factory.sounds.MadSound;

public class MadMetaItemData
{
    /** Name of the item which will also be used to reference it in code and in rendering registry. */
    @Expose
    private String itemName;
    
    /** Defines what damage value on the base item will equal this sub item. */
    @Expose
    private int metaID;
    
    /** Contains all recipes that can be used to create this particular item. */
    @Expose
    private MadCraftingRecipe[] craftingRecipes;
    
    /** Contains all related sounds for this item that as associated with triggers to be played at certain times. */
    @Expose
    private MadSound[] soundArchive;

    public String getItemName()
    {
        return itemName;
    }

    public MadMetaItemData(
            int metaID,
            String itemName,
            MadCraftingRecipe[] craftingRecipes,
            MadSound[] soundArchive)
    {
        super();
        
        this.metaID = metaID;
        this.itemName = itemName;
        this.craftingRecipes = craftingRecipes;
        this.soundArchive = soundArchive;
    }

    public void setItemName(String itemName)
    {
        this.itemName = itemName;
    }

    public int getMetaID()
    {
        return metaID;
    }

    public void setMetaID(int metaID)
    {
        this.metaID = metaID;
    }

    public MadCraftingRecipe[] getCraftingRecipes()
    {
        return craftingRecipes;
    }

    public void setCraftingRecipes(MadCraftingRecipe[] craftingRecipes)
    {
        this.craftingRecipes = craftingRecipes;
    }

    public MadSound[] getSoundArchive()
    {
        return soundArchive;
    }

    public void setSoundArchive(MadSound[] soundArchive)
    {
        this.soundArchive = soundArchive;
    }
}

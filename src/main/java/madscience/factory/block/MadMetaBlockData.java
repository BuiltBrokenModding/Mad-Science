package madscience.factory.block;

import madscience.factory.crafting.MadCraftingRecipe;
import madscience.factory.furnace.MadFurnaceRecipe;
import madscience.factory.model.MadModel;
import madscience.factory.sound.MadSound;

import com.google.gson.annotations.Expose;

public class MadMetaBlockData
{
    /** Name of the item which will also be used to reference it in code and in rendering registry. */
    @Expose
    private String subBlockName;
    
    /** Defines what damage value on the base item will equal this sub item. */
    @Expose
    private int metaID;
    
    /** Contains all recipes that can be used to create this particular item. */
    @Expose
    private MadCraftingRecipe[] craftingRecipes;
    
    /** Contains all related sounds for this item that as associated with triggers to be played at certain times. */
    @Expose
    private MadSound[] soundArchive;
    
    /** Blocks cannot have models or any complex render passes we only allow path to a simple texture to be loaded. */
    @Expose
    private String texturePath;
    
    /** Contains recipes that will be loaded into Minecraft vanilla furnace for processing. Allows items to be cooked in vanilla furnace. */
    @Expose
    private MadFurnaceRecipe[] furnaceRecipes;
    
    /** Determines if sounds for this given item product have been registered on Minecraft client. */
    private boolean soundArchiveLoaded = false;
    
    public MadMetaBlockData(
            int metaID,
            String subBlockName,
            String texturePath,
            MadCraftingRecipe[] craftingRecipes,
            MadFurnaceRecipe[] furnaceRecipes,
            MadSound[] soundArchive)
    {
        super();
        
        this.metaID = metaID;
        this.subBlockName = subBlockName;
        this.texturePath = texturePath;
        this.craftingRecipes = craftingRecipes;
        this.furnaceRecipes = furnaceRecipes;
        this.soundArchive = soundArchive;
    }

    public String getSubBlockName()
    {
        return subBlockName;
    }

    public void setSubBlockName(String subBlockName)
    {
        this.subBlockName = subBlockName;
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

    public String getTexturePath()
    {
        return texturePath;
    }

    public void setTexturePath(String texturePath)
    {
        this.texturePath = texturePath;
    }

    public MadFurnaceRecipe[] getFurnaceRecipes()
    {
        return furnaceRecipes;
    }

    public void setFurnaceRecipes(MadFurnaceRecipe[] furnaceRecipes)
    {
        this.furnaceRecipes = furnaceRecipes;
    }
}

package madscience.factory.item;

import net.minecraft.util.Icon;
import madscience.factory.crafting.MadCraftingRecipe;
import madscience.factory.model.MadItemModel;
import madscience.factory.sounds.MadSound;

import com.google.gson.annotations.Expose;

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
    
    /** Contains all of the models and texture information for rendering factory and Minecraft/Forge. */
    @Expose
    private MadItemModel modelArchive;
    
    /** Reference to item icon layers and what color they should render as. Path is relative to Minecraft/Forge asset folder for items. */
    @Expose
    private MadItemRenderPass[] renderPassArchive;
    
    public MadMetaItemData(
            int metaID,
            String itemName,
            MadCraftingRecipe[] craftingRecipes,
            MadSound[] soundArchive,
            MadItemModel modelArchive,
            MadItemRenderPass[] renderPasses)
    {
        super();
        
        this.metaID = metaID;
        this.itemName = itemName;
        this.craftingRecipes = craftingRecipes;
        this.soundArchive = soundArchive;
        this.modelArchive = modelArchive;
        this.renderPassArchive = renderPasses;
    }
    
    public String getItemName()
    {
        return itemName;
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

    public MadItemModel getModelArchive()
    {
        return modelArchive;
    }

    public void setModelArchive(MadItemModel modelArchive)
    {
        this.modelArchive = modelArchive;
    }

    public MadItemRenderPass[] getRenderPassArchive()
    {
        return renderPassArchive;
    }

    public void setRenderPassArchive(MadItemRenderPass[] renderPassArchive)
    {
        this.renderPassArchive = renderPassArchive;
    }

    public int getColorForPass(int pass)
    {
        // Loop through the render passes looking for correct render pass.
        for (MadItemRenderPass renderPass : this.renderPassArchive)
        {
            if (renderPass.getRenderPass() == pass)
            {
                // Grabs the color for this given render pass.
                return renderPass.getColorRGB();
            }
        }
        
        // Default response is to return the color white.
        return 16777215;
    }

    public Icon getIconForPass(int pass)
    {
        for (MadItemRenderPass renderPass : this.renderPassArchive)
        {
            if (renderPass.getRenderPass() == pass)
            {
                // Grabs the color for this given render pass.
                return renderPass.getIcon();
            }
        }
        
        return null;
    }

    /** Return the total number of render passes required for this sub-item. */
    public int getRenderPassCount()
    {
        if (this.renderPassArchive != null)
        {
            return this.renderPassArchive.length;        
        }
        
        // Default response is to say we have a single render pass.
        return 1;
    }
}

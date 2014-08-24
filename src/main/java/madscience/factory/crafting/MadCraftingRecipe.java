package madscience.factory.crafting;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public final class MadCraftingRecipe
{
    /** Final input result of recipe. */
    @Expose
    @SerializedName("CraftingRecipeComponents")
    private final MadCraftingComponent[] craftingRecipeComponents = null;
    
    /** Holds reference to type of crafting recipe this is. Minecraft/Forge allows for Shaped and Shapeless recipes currently. */
    @Expose
    @SerializedName("CraftingRecipeType")
    private final MadCraftingRecipeTypeEnum craftingRecipeType = null;
    
    /** Determines how many of the given source block will be created when this recipe is crafted. */
    @Expose
    @SerializedName("CraftingAmount")
    private final int craftingAmount = 1;

    public MadCraftingComponent[] getCraftingRecipeComponents()
    {
        return this.craftingRecipeComponents;
    }

    public MadCraftingRecipeTypeEnum getCraftingRecipeType()
    {
        return this.craftingRecipeType;
    }

    public int getCraftingAmount()
    {
        return this.craftingAmount;
    }
}
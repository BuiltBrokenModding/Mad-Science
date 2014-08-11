package madscience.factory.block;

import java.util.ArrayList;
import java.util.List;

import madscience.factory.block.prefab.MadBlockPrefab;
import madscience.factory.crafting.MadCraftingComponent;
import madscience.factory.crafting.MadCraftingRecipe;
import madscience.factory.crafting.MadCraftingRecipeTypeEnum;
import madscience.factory.furnace.MadFurnaceRecipe;
import madscience.factory.mod.MadMod;
import madscience.factory.recipe.MadRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.Icon;
import cpw.mods.fml.common.registry.GameRegistry;

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
    
    /** Associates a loaded Minecraft/Forge icon with given sub-block render pass. */
    public void loadRenderPassIcon(
            String subBlockName,
            int renderPass,
            Icon icon)
    {
        // Loop through all sub-blocks looking for the one we want to update.
        for (MadMetaBlockData subBlock : this.data.getSubBlocks())
        {
            if (subBlock.getSubBlockName().equals(subBlockName))
            {
                // Locate the matching render pass inside of this sub-block.
                for (MadBlockRenderPass renderPassObject : subBlock.getRenderPassArchive())
                {
                    if (renderPassObject.getRenderPass() == renderPass)
                    {
                        // Update the icon of this matching sub-block render type.
                        renderPassObject.setIcon(icon);
                    }
                }
            }
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
    
    /** Parses input and output blocks and associates them with Minecraft/Forge ItemStacks.
     *  After this it will register the completed items with vanilla cobblestone Furnace. */
    public void loadVanillaFurnaceRecipes()
    {
        // Loop through all the sub items inside of this single block.
        for (MadMetaBlockData subItem : this.data.getSubBlocks())
        {
            // Loop through all the vanilla furnace recipes and associate them with Minecraft/Forge ItemStacks.
            for (MadFurnaceRecipe furnaceRecipe : subItem.getFurnaceRecipes())
            {
                // Input Component
                if (furnaceRecipe.getInputComponent() != null)
                {
                    // Query game registry and vanilla blocks and items for the incoming name in an attempt to turn it into an itemstack.
                    ItemStack[] inputItem = MadRecipe.getItemStackFromString(
                            furnaceRecipe.getInputComponent().getModID(),
                            furnaceRecipe.getInputComponent().getInternalName(),
                            furnaceRecipe.getInputComponent().getAmount(),
                            furnaceRecipe.getInputComponent().getMetaDamage());
                    
                    if (inputItem != null && !furnaceRecipe.getInputComponent().isLoaded())
                    {
                        furnaceRecipe.getInputComponent().associateItemStackToRecipeComponent(inputItem);
                    }
                }
                
                // Output Component
                if (furnaceRecipe.getOutputComponent() != null)
                {
                    ItemStack[] outputItem = MadRecipe.getItemStackFromString(
                            furnaceRecipe.getOutputComponent().getModID(),
                            furnaceRecipe.getOutputComponent().getInternalName(),
                            furnaceRecipe.getOutputComponent().getAmount(),
                            furnaceRecipe.getOutputComponent().getMetaDamage());
                    
                    if (outputItem != null && !furnaceRecipe.getOutputComponent().isLoaded())
                    {
                        furnaceRecipe.getOutputComponent().associateItemStackToRecipeComponent(outputItem);
                    }
                }
                
                // Now that input and output items are loaded lets apply this to vanilla furnace.
                if (furnaceRecipe.getInputComponent().isLoaded() && furnaceRecipe.getOutputComponent().isLoaded())
                {
                    ItemStack[] inputItems = furnaceRecipe.getInputComponent().getItemStackArray();
                    ItemStack[] outputItems = furnaceRecipe.getOutputComponent().getItemStackArray();
                    if (inputItems[0] != null && outputItems[0] != null)
                    {
                        // Note: 0.0F is for experience gained from smelting this item.
                        FurnaceRecipes.smelting().addSmelting(inputItems[0].itemID, outputItems[0], 0.0F);
                    }
                }
            }
        }
    }

    /** Loads recipes associated with the block itself so that it may be crafted in the game.
     * This should only be run once, and in postInit after all other mods have been loaded so items from them may be queried. */
    public void loadCraftingRecipes()
    {
        int totalLoadedRecipeItems = 0;
        int totalFailedRecipeItems = 0;
        
        // Loop through all of the sub blocks inside of this item product data.
        for (MadMetaBlockData subItem : this.data.getSubBlocks())
        {
            // Grab the recipe data for each individual sub-block.
            for (MadCraftingRecipe itemCraftingRecipe : subItem.getCraftingRecipes())
            {
                // Keeps track if anything bad happened during crafting recipe creation.
                boolean errorsWithAssociation = true;
                
                // Reference to recipe we want to create, Minecraft/Forge accepts them as object array.
                List<Object> craftingInputArray = new ArrayList<Object>();
                
                // Crafting strings that makeup reference to what numbers goto what slot on the grid.
                Object[] craftingGridLayout = new Object[9];
                
                // Initialize each slot with empty space since in Minecraft/Forge this means null/nothing.
                for (int i = 0; i < craftingGridLayout.length; i++)
                {
                    craftingGridLayout[i] = " ";
                }
                
                for (MadCraftingComponent recipeComponent : itemCraftingRecipe.getCraftingRecipeComponents())
                {
                    // Starting building output string now, append to it below.
                    String resultInputPrint = "[" + this.data.getBlockName() + "]Crafting Component " + recipeComponent.getModID() + ":" + recipeComponent.getInternalName();
    
                    // Query game registry and vanilla blocks and items for the incoming name in an attempt to turn it into an itemstack.
                    ItemStack[] inputItem = MadRecipe.getItemStackFromString(recipeComponent.getModID(), recipeComponent.getInternalName(), recipeComponent.getAmount(), recipeComponent.getMetaDamage());
    
                    boolean searchResult = false;
                    if (inputItem != null)
                    {
                        searchResult = true;
                        totalLoadedRecipeItems++;
                        resultInputPrint += "=SUCCESS";
                        recipeComponent.associateItemStackToRecipeComponent(inputItem);
                    }
                    else
                    {
                        searchResult = false;
                        totalFailedRecipeItems++;
                        resultInputPrint += "=FAILED";
                    }
                    
                    // Mark this entry as being valid since we made it this far.
                    errorsWithAssociation = false;
                   
                    // Only add the craft slot number if we are making a shaped recipe.
                    if (itemCraftingRecipe.getCraftingRecipeType() == MadCraftingRecipeTypeEnum.SHAPED)
                    {
                        // First, place the prepared crafting component into grid as a string.
                        craftingGridLayout[recipeComponent.getCraftingGridPosition()] = String.valueOf(recipeComponent.getCraftingGridPosition());
                        
                        // Second, add to input array the crafting grid number.
                        craftingInputArray.add(recipeComponent.getCraftingGridPositionAsCharacter());
                    }
                    
                    // Third, add to input array the crafting ingredient (we always will want this).
                    for (ItemStack craftComponent : recipeComponent.getItemStackArray())
                    {
                        craftingInputArray.add(craftComponent);
                    }
    
                    // Debugging!
                    if (!searchResult)
                    {
                        MadMod.log().info(resultInputPrint);
                    }
                }
                
                // If the above crafting component registration went well then we will add them.
                if (!errorsWithAssociation)
                {
                    // Depending on type of recipe, very different things need to happen with the data we have collected so far.
                    switch (itemCraftingRecipe.getCraftingRecipeType())
                    {
                        case SHAPED:
                        {
                            // Move the string array into proper alignment for recipe input, any slots without components become blank spaces this is intentional.
                            String[] craftingGridLayoutFinal = new String[3];
                            craftingGridLayoutFinal[0] = String.valueOf(craftingGridLayout[0]) + String.valueOf(craftingGridLayout[1]) + String.valueOf(craftingGridLayout[2]);
                            craftingGridLayoutFinal[1] = String.valueOf(craftingGridLayout[3]) + String.valueOf(craftingGridLayout[4]) + String.valueOf(craftingGridLayout[5]);
                            craftingGridLayoutFinal[2] = String.valueOf(craftingGridLayout[6]) + String.valueOf(craftingGridLayout[7]) + String.valueOf(craftingGridLayout[8]);
                            
                            // Construct the final object array recipe input.
                            List<Object> recipeFinalInput = new ArrayList<Object>();
                            recipeFinalInput.add(craftingGridLayoutFinal[0]);
                            recipeFinalInput.add(craftingGridLayoutFinal[1]);
                            recipeFinalInput.add(craftingGridLayoutFinal[2]);
                            recipeFinalInput.addAll(craftingInputArray);
                            
                            // Actually add the recipe to Minecraft/Forge.
                            try
                            {
                                GameRegistry.addShapedRecipe(new ItemStack(this.getBlock(), itemCraftingRecipe.getCraftingAmount(), subItem.getMetaID()),
                                        recipeFinalInput.toArray(new Object[]{}));
                            }
                            catch (Exception err)
                            {
                                MadMod.log().info("[" + this.data.getBlockName() + "]Unable to load shaped crafting recipe!");
                            }
                            
                            break;
                        }
                        case SHAPELESS:
                        {              
                            try
                            {
                                // Shapeless recipes are a little easier since we only need to pass in the item array.
                                GameRegistry.addShapelessRecipe(new ItemStack(this.getBlock(), itemCraftingRecipe.getCraftingAmount(), subItem.getMetaID()),
                                        craftingInputArray.toArray(new Object[]{}));
                            }
                            catch (Exception err)
                            {
                                MadMod.log().info("[" + this.data.getBlockName() + "]Unable to load shapeless crafting recipe!");
                            }
                            
                            break;
                        }
                        default:
                        {
                            // Nothing to see here.
                            break;
                        }
                    }
                }
                else
                {
                    MadMod.log().info("[" + this.data.getBlockName() + "]Bad Crafting Recipe: " + itemCraftingRecipe.getCraftingRecipeType().name());
                }
            }
        }
        
        // Information about total loaded and failed.
        MadMod.log().info("[" + this.data.getBlockName() + "]Total Loaded Crafting Recipe Items: " + totalLoadedRecipeItems);
        MadMod.log().info("[" + this.data.getBlockName() + "]Failed To Load Crafting Recipe Items: " + totalFailedRecipeItems);
    }

    public MadMetaBlockData[] getSubBlocks()
    {
        return data.getSubBlocks();
    }

    public boolean hasSubBlocks()
    {
        // Every block has 1 default sub-block, if greater than that we have multiple sub-types.
        if (data.getSubBlocks() != null &&
            data.getSubBlocks().length > 1)
        {
            return true;
        }
        
        return false;
    }
}

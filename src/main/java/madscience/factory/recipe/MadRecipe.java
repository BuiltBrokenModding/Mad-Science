package madscience.factory.recipe;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import madscience.factory.container.MadSlotContainerTypeEnum;
import madscience.factory.crafting.MadCraftingComponent;
import madscience.factory.crafting.MadCraftingRecipe;
import madscience.factory.crafting.MadCraftingRecipeTypeEnum;
import madscience.factory.furnace.MadFurnaceRecipe;
import madscience.factory.mod.MadMod;
import madscience.util.MadUtils;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import com.google.gson.annotations.Expose;

import cpw.mods.fml.common.registry.GameRegistry;

public final class MadRecipe
{
    /** Master list of recipes for this machine. */
    private final HashMap<List<MadRecipeComponent[]>, MadRecipeComponent[]> smeltingList = new HashMap<List<MadRecipeComponent[]>, MadRecipeComponent[]>();
    
    /** Final input result of recipe. */
    @Expose private final MadRecipeComponent[] inputIngredientsArray;
    
    /** Final output result of recipe. */
    @Expose private final MadRecipeComponent[] outputResultsArray;
    
    /** Amount of time in seconds it should take to create this recipe. */
    @Expose private final int creationTimeInSeconds;
    
    /** Amount of experience the player will get after crafting this recipe. */
    @Expose private final float experienceFromCreation;
    
    public MadRecipe( // NO_UCD (unused code)
            MadSlotContainerTypeEnum ingredient1Slot,
            String ingredient1Info,
            MadSlotContainerTypeEnum ingredient2Slot,
            String ingredient2Info,
            MadSlotContainerTypeEnum ingredient3Slot,
            String ingredient3Info,
            MadSlotContainerTypeEnum ingredient4Slot,
            String ingredient4Info,
            MadSlotContainerTypeEnum ingredient5Slot,
            String ingredient5Info,
            MadSlotContainerTypeEnum output1Slot,
            String output1Info,
            MadSlotContainerTypeEnum output2Slot,
            String output2Info,
            MadSlotContainerTypeEnum output3Slot,
            String output3Info,
            MadSlotContainerTypeEnum output4Slot,
            String output4Info,
            MadSlotContainerTypeEnum output5Slot,
            String output5Info,
            int creationTimeInSeconds,
            float experienceGained)
    {        
        // Array of input components that makeup this given recipe result.
        ArrayList<MadRecipeComponent> tempInputList = new ArrayList<MadRecipeComponent>();
        
        // Array of recipe results which are created when given required ingredients.
        ArrayList<MadRecipeComponent> tempOutputList = new ArrayList<MadRecipeComponent>();
        
        // Only add input ingredients that are not null.
        if (ingredient1Info != null && !ingredient1Info.isEmpty())
        {            
            MadRecipeComponent recipeComponent1 = this.parseParametersToComponents(ingredient1Slot, ingredient1Info);
            if (recipeComponent1 != null)
            {
                tempInputList.add(recipeComponent1);
            }
        }
        
        if (ingredient2Info != null && !ingredient2Info.isEmpty())
        {
            MadRecipeComponent recipeComponent2 = this.parseParametersToComponents(ingredient2Slot, ingredient2Info);
            if (recipeComponent2 != null)
            {
                tempInputList.add(recipeComponent2);
            }
        }
        
        if (ingredient3Info != null && !ingredient3Info.isEmpty())
        {
            MadRecipeComponent recipeComponent3 = this.parseParametersToComponents(ingredient3Slot, ingredient3Info);
            if (recipeComponent3 != null)
            {
                tempInputList.add(recipeComponent3);
            }
        }
        
        if (ingredient4Info != null && !ingredient4Info.isEmpty())
        {
            MadRecipeComponent recipeComponent4 = this.parseParametersToComponents(ingredient4Slot, ingredient4Info);
            if (recipeComponent4 != null)
            {
                tempInputList.add(recipeComponent4);
            }
        }
        
        if (ingredient5Info != null && !ingredient5Info.isEmpty())
        {
            MadRecipeComponent recipeComponent5 = this.parseParametersToComponents(ingredient5Slot, ingredient5Info);
            if (recipeComponent5 != null)
            {
                tempInputList.add(recipeComponent5);
            }
        }
        
        // Add output results as required.
        if (output1Info != null && !output1Info.isEmpty())
        {
            MadRecipeComponent recipeOutput1 = this.parseParametersToComponents(output1Slot, output1Info);
            if (recipeOutput1 != null)
            {
                tempOutputList.add(recipeOutput1);
            }
        }
        
        if (output2Info != null && !output2Info.isEmpty())
        {
            MadRecipeComponent recipeOutput2 = this.parseParametersToComponents(output2Slot, output2Info);
            if (recipeOutput2 != null)
            {
                tempOutputList.add(recipeOutput2);
            }
        }
        
        if (output3Info != null && !output3Info.isEmpty())
        {
            MadRecipeComponent recipeOutput3 = this.parseParametersToComponents(output3Slot, output3Info);
            if (recipeOutput3 != null)
            {
                tempOutputList.add(recipeOutput3);
            }
        }
        
        if (output4Info != null && !output4Info.isEmpty())
        {
            MadRecipeComponent recipeOutput4 = this.parseParametersToComponents(output4Slot, output4Info);
            if (recipeOutput4 != null)
            {
                tempOutputList.add(recipeOutput4);
            }
        }
        
        if (output5Info != null && !output5Info.isEmpty())
        {
            MadRecipeComponent recipeOutput5 = this.parseParametersToComponents(output5Slot, output5Info);
            if (recipeOutput5 != null)
            {
                tempOutputList.add(recipeOutput5);
            }
        }
        
        // Input array.
        inputIngredientsArray = tempInputList.toArray(new MadRecipeComponent[tempInputList.size()]);
        
        // Output array.
        outputResultsArray = tempOutputList.toArray(new MadRecipeComponent[tempOutputList.size()]);
        
        // Time to create.
        this.creationTimeInSeconds = creationTimeInSeconds;
        
        // Experience gained.
        this.experienceFromCreation = experienceGained;
    }
    
    public int getCreationTimeInSeconds()
    {
        return this.creationTimeInSeconds;
    }
    
    public int getCreationTimeInTicks()
    {
        return this.creationTimeInSeconds * MadUtils.SECOND_IN_TICKS;
    }
    
    /** Loads recipes associated with the product itself so that it may be crafted in the game.
     * This should only be run once, and in postInit after all other mods have been loaded so products from them may be queried. */
    public static void loadCraftingRecipes(MadCraftingRecipe[] recipesToLoad, String productName, ItemStack craftingResult)
    {
        int totalLoadedRecipeItems = 0;
        int totalFailedRecipeItems = 0;
        
        // Skip items that have no crafting recipes to register.
        if (recipesToLoad == null)
        {
            return;
        }
        
        // Grab the recipe data for each individual sub-item.
        for (MadCraftingRecipe itemCraftingRecipe : recipesToLoad)
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
                // Start building output string now, append to it below.
                String resultInputPrint = "[" + productName + "]Crafting Component " + recipeComponent.getModID() + ":" + recipeComponent.getInternalName();

                // Query game registry and vanilla blocks and items for the incoming name in an attempt to turn it into an itemstack.
                ItemStack[] inputItem = MadRecipe.getItemStackFromString(recipeComponent.getModID(), recipeComponent.getInternalName(), recipeComponent.getAmount(), recipeComponent.getMetaDamage());

                boolean searchResult = false;
                if (inputItem != null)
                {
                    searchResult = true;
                    errorsWithAssociation = false;
                    totalLoadedRecipeItems++;
                    resultInputPrint += "=SUCCESS";
                    recipeComponent.associateItemStackToRecipeComponent(inputItem);
                }
                else
                {
                    searchResult = false;
                    errorsWithAssociation = true;
                    totalFailedRecipeItems++;
                    resultInputPrint += "=FAILED";
                }
               
                // Only add the craft slot number if we are making a shaped recipe.
                if (itemCraftingRecipe.getCraftingRecipeType() == MadCraftingRecipeTypeEnum.SHAPED)
                {
                    // First, place the prepared crafting component into grid as a string.
                    craftingGridLayout[recipeComponent.getCraftingGridPosition()] = String.valueOf(recipeComponent.getCraftingGridPosition());
                    
                    // Second, add to input array the crafting grid number.
                    craftingInputArray.add(recipeComponent.getCraftingGridPositionAsCharacter());
                }
                
                // Third, add to input array the crafting ingredient (we always will want this).
                if (recipeComponent.isLoaded())
                {
                    for (ItemStack craftComponent : recipeComponent.getItemStackArray())
                    {
                        craftingInputArray.add(craftComponent);
                    }
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
                            GameRegistry.addShapedRecipe(new ItemStack(craftingResult.getItem(), itemCraftingRecipe.getCraftingAmount(), craftingResult.getItemDamage()),
                                    recipeFinalInput.toArray(new Object[]{}));
                        }
                        catch (Exception err)
                        {
                            MadMod.log().info("[" + productName + "]Unable to load shaped crafting recipe!");
                        }
                        
                        break;
                    }
                    case SHAPELESS:
                    {              
                        try
                        {
                            // Shapeless recipes are a little easier since we only need to pass in the item array.
                            GameRegistry.addShapelessRecipe(new ItemStack(craftingResult.getItem(), itemCraftingRecipe.getCraftingAmount(), craftingResult.getItemDamage()),
                                    craftingInputArray.toArray(new Object[]{}));
                        }
                        catch (Exception err)
                        {
                            MadMod.log().info("[" + productName + "]Unable to load shapeless crafting recipe!");
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
                MadMod.log().info("[" + productName + "]Bad Crafting Recipe: " + itemCraftingRecipe.getCraftingRecipeType().name());
            }
        }
        
        // Information about total loaded and failed.
        MadMod.log().info("[" + productName + "]Total Loaded Crafting Recipe Items: " + totalLoadedRecipeItems);
        MadMod.log().info("[" + productName + "]Failed To Load Crafting Recipe Items: " + totalFailedRecipeItems);
    }
    
    /** Parses input and output items and associates them with Minecraft/Forge ItemStacks.
     *  After this it will register the completed items with vanilla cobblestone Furnace. 
     * @param madFurnaceRecipes */
    public static void loadVanillaFurnaceRecipes(MadFurnaceRecipe[] madFurnaceRecipes)
    {
        // Skip items that have no vanilla furnace recipes.
        if (madFurnaceRecipes == null)
        {
            return;
        }
        
        // Loop through all the vanilla furnace recipes and associate them with Minecraft/Forge ItemStacks.
        for (MadFurnaceRecipe furnaceRecipe : madFurnaceRecipes)
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
                    FurnaceRecipes.smelting().addSmelting(inputItems[0].itemID, outputItems[0], 0.0F);
                }
            }
        }
    }
    
    /** Converts input parameters from recipe creation into components for machine recipe system. */
    private MadRecipeComponent parseParametersToComponents(
            MadSlotContainerTypeEnum slotType,
            String fullName)
    {
        try
        {
            // Split the input data with separator.
            String[] parts = fullName.split(":");
            
            // Convert last bit to quantity of item.
            int amount = 1;
            if (parts[3] != null && !parts[3].isEmpty())
            {
                amount = Integer.parseInt(parts[3]);
            }
            
            // Convert second to last bit to damage or metadata of this item.
            String metaDamage = "0";
            if (parts[2] != null && !parts[2].isEmpty())
            {
                metaDamage = parts[2];
            }
            
            // MODID:REGENT:DAMAGE(METADATA):AMOUNT
            // Note: '*'(STAR) represents wildcard for names and meta/damage. Ex. "madscience:genome*:*:1" would allow any genome with any damage.
            String partName = parts[1];
            MadRecipeComponent regent1 = new MadRecipeComponent(slotType, parts[0], partName, String.valueOf(metaDamage), amount);
            return regent1;
        }
        catch (Exception err)
        {
            // Something went wrong parsing the input data.
            MadMod.log().warning("Unable to parse input parameters into MadRecipeComponent for '" + fullName + "'!");
        }
        
        return null;
    }

    public MadRecipeComponent[] getInputIngredientsArray()
    {
        return inputIngredientsArray;
    }

    public MadRecipeComponent[] getOutputResultsArray()
    {
        return outputResultsArray;
    }

    public float getExperienceFromCreation()
    {
        return experienceFromCreation;
    }
    
    /** Return itemstack from GameRegistry or from vanilla Item/Block list. */
    public static ItemStack[] getItemStackFromString(String modID, String itemName, int stackSize, String metaDataText)
    {
        // Reference list we will return at the end of work.
        ArrayList<ItemStack> itemsToAssociate = new ArrayList<ItemStack>();
        Collection<String> unlocalizedNames = new TreeSet<String>(Collator.getInstance());

        // Reference to if this recipe deals with wildcard (*) values in meta/damage or name.
        boolean wildcardName = itemName.contains("*");
        boolean wildcardMeta = metaDataText.contains("*");

        // Reference to actual metadata since we have to parse it.
        int metaData = 0;
        if (!wildcardMeta)
        {
            // If not using wildcard for damage then parse it as integer.
            metaData = Integer.parseInt(metaDataText);
        }

        // Only lookup individual itemstacks if we are not hunting wildcards.
        if (!wildcardName)
        {
            // Mod items and blocks query.
            ItemStack potentialModItem = GameRegistry.findItemStack(modID, itemName, stackSize);
            if (potentialModItem != null)
            {
                if (!unlocalizedNames.contains(MadUtils.cleanTag(potentialModItem.getUnlocalizedName())))
                {
                    itemsToAssociate.add(potentialModItem);
                    unlocalizedNames.add(MadUtils.cleanTag(potentialModItem.getUnlocalizedName()));
                }
            }
        }

        // Vanilla item query.
        for (Item potentialMCItem : Item.itemsList)
        {
            if (potentialMCItem == null)
            {
                continue;
            }

            // Check if we need to accommodate metadata.
            int tmpMeta = 0;
            if (wildcardMeta)
            {
                tmpMeta = 0;
            }
            else
            {
                // Use given value if not wild.
                tmpMeta = metaData;
            }

            ItemStack vanillaItemStack = new ItemStack(potentialMCItem, stackSize, tmpMeta);

            if (vanillaItemStack != null)
            {
                try
                {
                    // Check if name contains wildcard value.
                    if (wildcardName && potentialMCItem.getUnlocalizedName().contains(itemName.replace("*", "")))
                    {
                        if (!unlocalizedNames.contains(vanillaItemStack.getUnlocalizedName()))
                        {
                            itemsToAssociate.add(vanillaItemStack);
                            unlocalizedNames.add(vanillaItemStack.getUnlocalizedName());
                        }
                    }
                    else if (!wildcardName && MadUtils.cleanTag(vanillaItemStack.getUnlocalizedName()).equals(itemName))
                    {
                        if (!unlocalizedNames.contains(MadUtils.cleanTag(vanillaItemStack.getUnlocalizedName())))
                        {
                            itemsToAssociate.add(vanillaItemStack);
                            unlocalizedNames.add(MadUtils.cleanTag(vanillaItemStack.getUnlocalizedName()));
                        }
                    }
                }
                catch (Exception err)
                {
                    continue;
                }
            }
        }

        // Vanilla block query.
        for (Block potentialMCBlock : Block.blocksList)
        {
            if (potentialMCBlock == null)
            {
                continue;
            }

            // Check if we need to accommodate metadata.
            int tmpMeta = 0;
            if (wildcardMeta)
            {
                tmpMeta = 0;
            }
            else
            {
                // Use given value if not wild.
                tmpMeta = metaData;
            }

            ItemStack vanillaItemStack = new ItemStack(potentialMCBlock, stackSize, tmpMeta);

            if (vanillaItemStack != null)
            {
                try
                {
                    // Check if name contains wildcard value.
                    if (wildcardName && vanillaItemStack.getUnlocalizedName().contains(itemName.replace("*", "")))
                    {
                        if (!unlocalizedNames.contains(vanillaItemStack.getUnlocalizedName()))
                        {
                            itemsToAssociate.add(vanillaItemStack);
                            unlocalizedNames.add(vanillaItemStack.getUnlocalizedName());
                        }
                    }
                    else if (!wildcardName && MadUtils.cleanTag(vanillaItemStack.getUnlocalizedName()).equals(itemName))
                    {
                        if (!unlocalizedNames.contains(MadUtils.cleanTag(vanillaItemStack.getUnlocalizedName())))
                        {
                            itemsToAssociate.add(vanillaItemStack);
                            unlocalizedNames.add(MadUtils.cleanTag(vanillaItemStack.getUnlocalizedName()));
                        }
                    }
                }
                catch (Exception err)
                {
                    continue;
                }
            }
        }

        // Last ditch effort to save compatibility starts here!
        if (itemName.equals("dyePowder") || itemName.equals("dye"))
        {
            // Return whatever type of dye was requested.
            itemsToAssociate.add(new ItemStack(Item.dyePowder, stackSize, metaData));
        }

        if (itemName.equals("wool") || itemName.equals("cloth"))
        {
            // Return whatever color wool was requested.
            itemsToAssociate.add(new ItemStack(Block.cloth, stackSize, metaData));
        }

        // Check if we have items to return back after all that work.
        if (itemsToAssociate.size() > 0)
        {
            return itemsToAssociate.toArray(new ItemStack[]{});
        }

        // Default response is to return nothing.
        return null;
    }
}
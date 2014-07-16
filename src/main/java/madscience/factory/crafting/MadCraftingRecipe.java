package madscience.factory.crafting;

import java.util.ArrayList;

import madscience.factory.mod.MadMod;

import com.google.gson.annotations.Expose;

public final class MadCraftingRecipe
{
    /** Final input result of recipe. */
    @Expose
    private final MadCraftingComponent[] craftingRecipeComponents;
    
    /** Holds reference to type of crafting recipe this is. Minecraft/Forge allows for Shaped and Shapeless recipes currently. */
    @Expose
    private final MadCraftingRecipeTypeEnum craftingRecipeType;
    
    /** Determines how many of the given source block will be created when this recipe is crafted. */
    @Expose
    private final int craftingAmount;

    public MadCraftingRecipe( // NO_UCD (unused code)
            MadCraftingRecipeTypeEnum recipeType,
            int craftingAmount,
            String ingredient1Info,
            String ingredient2Info,
            String ingredient3Info,
            String ingredient4Info,
            String ingredient5Info,
            String ingredient6Info,
            String ingredient7Info,
            String ingredient8Info,
            String ingredient9Info)
    {
        // Array of input components that makeup this given recipe result.
        ArrayList<MadCraftingComponent> tempInputList = new ArrayList<MadCraftingComponent>();

        // Only add input ingredients that are not null.
        if (ingredient1Info != null && !ingredient1Info.isEmpty())
        {
            MadCraftingComponent recipeComponent1 = this.parseParametersToComponents(ingredient1Info);
            if (recipeComponent1 != null)
            {
                tempInputList.add(recipeComponent1);
            }
        }

        if (ingredient2Info != null && !ingredient2Info.isEmpty())
        {
            MadCraftingComponent recipeComponent2 = this.parseParametersToComponents(ingredient2Info);
            if (recipeComponent2 != null)
            {
                tempInputList.add(recipeComponent2);
            }
        }

        if (ingredient3Info != null && !ingredient3Info.isEmpty())
        {
            MadCraftingComponent recipeComponent3 = this.parseParametersToComponents(ingredient3Info);
            if (recipeComponent3 != null)
            {
                tempInputList.add(recipeComponent3);
            }
        }

        if (ingredient4Info != null && !ingredient4Info.isEmpty())
        {
            MadCraftingComponent recipeComponent4 = this.parseParametersToComponents(ingredient4Info);
            if (recipeComponent4 != null)
            {
                tempInputList.add(recipeComponent4);
            }
        }

        if (ingredient5Info != null && !ingredient5Info.isEmpty())
        {
            MadCraftingComponent recipeComponent5 = this.parseParametersToComponents(ingredient5Info);
            if (recipeComponent5 != null)
            {
                tempInputList.add(recipeComponent5);
            }
        }

        if (ingredient6Info != null && !ingredient6Info.isEmpty())
        {
            MadCraftingComponent recipeComponent6 = this.parseParametersToComponents(ingredient6Info);
            if (recipeComponent6 != null)
            {
                tempInputList.add(recipeComponent6);
            }
        }

        if (ingredient7Info != null && !ingredient7Info.isEmpty())
        {
            MadCraftingComponent recipeComponent7 = this.parseParametersToComponents(ingredient7Info);
            if (recipeComponent7 != null)
            {
                tempInputList.add(recipeComponent7);
            }
        }

        if (ingredient8Info != null && !ingredient8Info.isEmpty())
        {
            MadCraftingComponent recipeComponent8 = this.parseParametersToComponents(ingredient8Info);
            if (recipeComponent8 != null)
            {
                tempInputList.add(recipeComponent8);
            }
        }

        if (ingredient9Info != null && !ingredient9Info.isEmpty())
        {
            MadCraftingComponent recipeComponent9 = this.parseParametersToComponents(ingredient9Info);
            if (recipeComponent9 != null)
            {
                tempInputList.add(recipeComponent9);
            }
        }

        // Input array which makes up the crafting recipe for this machine.
        this.craftingRecipeComponents = tempInputList.toArray(new MadCraftingComponent[tempInputList.size()]);
        
        // Moving over the type of crafting recipe that this is.
        this.craftingRecipeType = recipeType;
        
        // Amount of the machine you will get in return for crafting it.
        this.craftingAmount = craftingAmount;
    }

    /** Converts input parameters from recipe creation into components for machine recipe system. */
    private MadCraftingComponent parseParametersToComponents(String delimitedString)
    {
        try
        {
            // Split the input data with separator.
            String[] parts = delimitedString.split(":");

            // Convert last bit to quantity of item.
            int amount = 1;
            if (parts[4] != null && !parts[4].isEmpty())
            {
                amount = Integer.parseInt(parts[4]);
            }

            // Convert second to last bit to damage or metadata of this item.
            int metaDamage = 0;
            if (parts[3] != null && !parts[3].isEmpty())
            {
                metaDamage = Integer.parseInt(parts[3]);
            }

            // Convert the first bit into slot number.
            int slotNumber = 0;
            if (parts[0] != null && !parts[0].isEmpty())
            {
                slotNumber = Integer.parseInt(parts[0]);
            }

            // SLOT:MODID:REGENT:DAMAGE(METADATA):AMOUNT
            MadCraftingComponent regent1 = new MadCraftingComponent(slotNumber, parts[1], parts[2], metaDamage, amount);
            return regent1;
        }
        catch (Exception err)
        {
            // Something went wrong parsing the input data.
            MadMod.LOGGER.warning("Unable to parse input parameters into MadCraftingComponent for '" + delimitedString + "'!");
        }

        return null;
    }

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
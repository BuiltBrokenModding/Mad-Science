package madscience.factory.recipes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import madscience.MadScience;
import madscience.factory.slotcontainers.MadSlotContainerTypeEnum;

public final class MadRecipe implements IMadRecipe
{
    /** Master list of recipes for this machine. */
    private final HashMap<List<MadRecipeComponent[]>, MadRecipeComponent[]> smeltingList = new HashMap<List<MadRecipeComponent[]>, MadRecipeComponent[]>();
    
    /** Final input result of recipe. */
    private final MadRecipeComponent[] inputIngredientsArray;
    
    /** Final output result of recipe. */
    private final MadRecipeComponent[] outputResultsArray;
    
    /** Amount of time in seconds it should take to create this recipe. */
    private final int creationTimeInSeconds;
    
    /** Amount of experience the player will get after crafting this recipe. */
    private final float experienceFromCreation;
    
    public MadRecipe(
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
    
    @Override
    public int getCreationTimeInSeconds()
    {
        return this.creationTimeInSeconds;
    }

    @Override
    public int getCreationTimeInTicks()
    {
        return this.creationTimeInSeconds * MadScience.SECOND_IN_TICKS;
    }
    
    /** Converts input parameters from recipe creation into components for machine recipe system. */
    @Override
    public MadRecipeComponent parseParametersToComponents(
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
            int metaDamage = 0;
            if (parts[2] != null && !parts[2].isEmpty())
            {
                metaDamage = Integer.parseInt(parts[2]);
            }
            
            // MODID:REGENT:DAMAGE(METADATA):AMOUNT
            MadRecipeComponent regent1 = new MadRecipeComponent(slotType, parts[0], parts[1], metaDamage, amount);
            return regent1;
        }
        catch (Exception err)
        {
            // Something went wrong parsing the input data.
            MadScience.logger.warning("Unable to parse input parameters into MadRecipeComponent for '" + fullName + "'!");
        }
        
        return null;
    }
    
    @Override
    public IMadRecipeComponent[] getRecipeResultBySlotType(MadSlotContainerTypeEnum slotType)
    {
        // If we have no recipe outputs then stop now!
        if (this.outputResultsArray == null)
        {
            return null;
        }
        
        // Create a temporary array to hold matching result types based on slot type.
        ArrayList<MadRecipeComponent> tempInputList = new ArrayList<MadRecipeComponent>();
        
        // Loop through output recipe results.
        for (MadRecipeComponent recipeResult : this.outputResultsArray) 
        {
            // Check for matching slot types.
            if (recipeResult.getSlotType().equals(slotType))
            {
                // Add this to the list!
                tempInputList.add(recipeResult);
            }
        }
        
        // Create temporary array of all inputed ingredient components.
        MadRecipeComponent[] matchingRecipeResults = tempInputList.toArray(new MadRecipeComponent[tempInputList.size()]);
        if (matchingRecipeResults != null)
        {
            return matchingRecipeResults;
        }
        
        return null;
    }

    /** Returns array of results for the given parameters, returns empty array if nothing. */
    @Override
    public IMadRecipeComponent[] getRecipeResultByIngredients(
            MadRecipeComponent ingredient1,
            MadRecipeComponent ingredient2,
            MadRecipeComponent ingredient3,
            MadRecipeComponent ingredient4,
            MadRecipeComponent ingredient5)
    {
        // Create a temporary array of these ingredients to query the mapping with.
        ArrayList<MadRecipeComponent> tempInputList = new ArrayList<MadRecipeComponent>();
        
        // Only add input ingredients that are not null.
        if (ingredient1 != null)
        {
            tempInputList.add(ingredient1);
        }
        
        if (ingredient2 != null)
        {
            tempInputList.add(ingredient2);
        }
        
        if (ingredient3 != null)
        {
            tempInputList.add(ingredient3);
        }
        
        if (ingredient4 != null)
        {
            tempInputList.add(ingredient4);
        }
        
        if (ingredient5 != null)
        {
            tempInputList.add(ingredient5);
        }
        
        // Create temporary array of all inputed ingredient components.
        IMadRecipeComponent[] queryIngredients = tempInputList.toArray(new MadRecipeComponent[tempInputList.size()]);
        
        // Iterate over all possible recipes in smelting array.
        for(Entry<List<MadRecipeComponent[]>, MadRecipeComponent[]> entry : smeltingList.entrySet())
        {
            // Look for input that matches our own.
            if (entry.getKey().equals(tempInputList))
            {
                return entry.getValue();
            }
        }
        
        // Default response is to null if nothing is found.
        return null;
    }

    @Override
    public IMadRecipeComponent[] getInputIngredientsArray()
    {
        return inputIngredientsArray;
    }

    @Override
    public IMadRecipeComponent[] getOutputResultsArray()
    {
        return outputResultsArray;
    }

    @Override
    public float getExperienceFromCreation()
    {
        return experienceFromCreation;
    }
}
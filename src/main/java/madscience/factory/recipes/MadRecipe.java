package madscience.factory.recipes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import madscience.factory.mod.MadMod;
import madscience.factory.slotcontainers.MadSlotContainerTypeEnum;
import madscience.util.MadUtils;

import com.google.gson.annotations.Expose;

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
}
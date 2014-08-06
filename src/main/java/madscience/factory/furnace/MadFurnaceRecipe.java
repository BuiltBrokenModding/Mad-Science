package madscience.factory.furnace;

import java.util.ArrayList;

import madscience.factory.mod.MadMod;

import com.google.gson.annotations.Expose;

public class MadFurnaceRecipe
{
    @Expose private MadFurnaceRecipeComponent inputComponent;
    
    @Expose private MadFurnaceRecipeComponent outputComponent;
    
    public MadFurnaceRecipe(
            String furnaceInput,
            String furnaceOutput)
    {
        super();
        
        if (furnaceInput != null && !furnaceInput.isEmpty())
        {
            MadFurnaceRecipeComponent smeltingInput = this.parseParametersToComponents(furnaceInput);
            if (smeltingInput != null)
            {
                inputComponent = smeltingInput;
            }
        }

        if (furnaceOutput != null && !furnaceOutput.isEmpty())
        {
            MadFurnaceRecipeComponent smeltingOutput = this.parseParametersToComponents(furnaceOutput);
            if (smeltingOutput != null)
            {
                outputComponent = smeltingOutput;
            }
        }
    }
    
    private MadFurnaceRecipeComponent parseParametersToComponents(String delimitedString)
    {
        try
        {
            // Split the input data with separator.
            String[] parts = delimitedString.split(":");

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

            // SLOT:MODID:REGENT:DAMAGE(METADATA):AMOUNT
            MadFurnaceRecipeComponent regent1 = new MadFurnaceRecipeComponent(parts[0], parts[1], String.valueOf(metaDamage), amount);
            return regent1;
        }
        catch (Exception err)
        {
            // Something went wrong parsing the input data.
            MadMod.log().warning("Unable to parse input parameters into MadCraftingComponent for '" + delimitedString + "'!");
        }

        return null;
    }

    public MadFurnaceRecipeComponent getInputComponent()
    {
        return inputComponent;
    }

    public MadFurnaceRecipeComponent getOutputComponent()
    {
        return outputComponent;
    }
}

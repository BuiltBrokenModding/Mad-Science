package madscience.factory.furnace;

import com.google.gson.annotations.Expose;

public class MadFurnaceRecipe
{
    @Expose 
    private MadFurnaceRecipeComponent inputComponent = null;
    
    @Expose 
    private MadFurnaceRecipeComponent outputComponent = null;
    
    public MadFurnaceRecipeComponent getInputComponent()
    {
        return inputComponent;
    }

    public MadFurnaceRecipeComponent getOutputComponent()
    {
        return outputComponent;
    }
}

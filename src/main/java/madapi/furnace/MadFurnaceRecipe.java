package madapi.furnace;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MadFurnaceRecipe
{
    @Expose 
    @SerializedName("InputComponent")
    private MadFurnaceRecipeComponent inputComponent = null;
    
    @Expose 
    @SerializedName("OutputComponent")
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

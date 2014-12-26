package madscience.furnace;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class FurnaceRecipe
{
    @Expose
    @SerializedName("InputComponent")
    private FurnaceRecipeComponent inputComponent = null;

    @Expose
    @SerializedName("OutputComponent")
    private FurnaceRecipeComponent outputComponent = null;

    public FurnaceRecipeComponent getInputComponent()
    {
        return inputComponent;
    }

    public FurnaceRecipeComponent getOutputComponent()
    {
        return outputComponent;
    }
}

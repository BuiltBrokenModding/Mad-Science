package madscience.rendering;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import madscience.model.ModelPosition;
import madscience.model.ModelScale;


public class WorldRenderInfo
{
    @Expose
    @SerializedName("ModelWorldPosition")
    private ModelPosition modelWorldPosition;

    @Expose
    @SerializedName("ModelWorldScale")
    private ModelScale modelWorldScale;

    public WorldRenderInfo(ModelPosition modelWorldPosition, ModelScale modelWorldScale)
    {
        super();

        this.modelWorldPosition = modelWorldPosition;
        this.modelWorldScale = modelWorldScale;
    }

    public ModelPosition getModelWorldPosition()
    {
        return modelWorldPosition;
    }

    public ModelScale getModelWorldScale()
    {
        return modelWorldScale;
    }
}

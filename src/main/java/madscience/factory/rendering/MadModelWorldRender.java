package madscience.factory.rendering;

import madscience.factory.model.MadModelPosition;
import madscience.factory.model.MadModelScale;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MadModelWorldRender
{
    @Expose
    @SerializedName("ModelWorldPosition")
    private MadModelPosition modelWorldPosition;
    
    @Expose
    @SerializedName("ModelWorldScale")
    private MadModelScale modelWorldScale;
    
    public MadModelWorldRender(
            MadModelPosition modelWorldPosition,
            MadModelScale modelWorldScale)
    {
        super();
        
        this.modelWorldPosition = modelWorldPosition;
        this.modelWorldScale = modelWorldScale;
    }

    public MadModelPosition getModelWorldPosition()
    {
        return modelWorldPosition;
    }

    public MadModelScale getModelWorldScale()
    {
        return modelWorldScale;
    }
}

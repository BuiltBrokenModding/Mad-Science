package madapi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MadModelPosition
{
    @Expose
    @SerializedName("ModelTranslateX")
    private float modelTranslateX;
    
    @Expose
    @SerializedName("ModelTranslateY")
    private float modelTranslateY;
    
    @Expose
    @SerializedName("ModelTranslateZ")
    private float modelTranslateZ;
    
    public MadModelPosition(
            float modelWorldTranslateX,
            float modelWorldTranslateY,
            float modelWorldTranslateZ)
    {
        super();
        
        this.modelTranslateX = modelWorldTranslateX;
        this.modelTranslateY = modelWorldTranslateY;
        this.modelTranslateZ = modelWorldTranslateZ;
    }

    public float getModelTranslateX()
    {
        return modelTranslateX;
    }

    public float getModelTranslateY()
    {
        return modelTranslateY;
    }

    public float getModelTranslateZ()
    {
        return modelTranslateZ;
    }
}

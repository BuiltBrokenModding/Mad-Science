package madscience.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ModelScale
{
    @Expose
    @SerializedName("ModelScaleX")
    private float modelScaleX;

    @Expose
    @SerializedName("ModelScaleY")
    private float modelScaleY;

    @Expose
    @SerializedName("ModelScaleZ")
    private float modelScaleZ;

    public ModelScale(float modelWorldScaleX, float modelWorldScaleY, float modelWorldScaleZ)
    {
        super();

        this.modelScaleX = modelWorldScaleX;
        this.modelScaleY = modelWorldScaleY;
        this.modelScaleZ = modelWorldScaleZ;
    }

    public float getModelScaleX()
    {
        return modelScaleX;
    }

    public void setModelScaleX(float modelScaleX)
    {
        this.modelScaleX = modelScaleX;
    }

    public float getModelScaleY()
    {
        return modelScaleY;
    }

    public void setModelScaleY(float modelScaleY)
    {
        this.modelScaleY = modelScaleY;
    }

    public float getModelScaleZ()
    {
        return modelScaleZ;
    }

    public void setModelScaleZ(float modelScaleZ)
    {
        this.modelScaleZ = modelScaleZ;
    }

}

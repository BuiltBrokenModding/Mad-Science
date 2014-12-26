package madscience.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ModelRotation
{
    @Expose
    @SerializedName("ModelRotationX")
    private float modelRotationX;

    @Expose
    @SerializedName("ModelRotationY")
    private float modelRotationY;

    @Expose
    @SerializedName("ModelRotationZ")
    private float modelRotationZ;

    @Expose
    @SerializedName("ModelRotationAngle")
    private float modelRotationAngle;

    @SuppressWarnings("ucd")
    public ModelRotation(float modelRotationAngle, float modelRotationX, float modelRotationY, float modelRotationZ)
    {
        super();

        this.modelRotationX = modelRotationX;
        this.modelRotationY = modelRotationY;
        this.modelRotationZ = modelRotationZ;
        this.modelRotationAngle = modelRotationAngle;
    }

    public float getModelRotationX()
    {
        return modelRotationX;
    }

    public float getModelRotationY()
    {
        return modelRotationY;
    }

    public float getModelRotationZ()
    {
        return modelRotationZ;
    }

    public float getModelRotationAngle()
    {
        return modelRotationAngle;
    }
}

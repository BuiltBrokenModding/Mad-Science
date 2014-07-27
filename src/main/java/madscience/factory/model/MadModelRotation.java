package madscience.factory.model;

import com.google.gson.annotations.Expose;

public class MadModelRotation
{
    @Expose
    private float modelRotationX;
    
    @Expose
    private float modelRotationY;
    
    @Expose
    private float modelRotationZ;
    
    @Expose
    private float modelRotationAngle;
    
    public MadModelRotation(
            float modelRotationAngle,
            float modelRotationX,
            float modelRotationY,
            float modelRotationZ)
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

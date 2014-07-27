package madscience.factory.model;

import com.google.gson.annotations.Expose;

public class MadModelScale
{
    @Expose
    private float modelScaleX;
    
    @Expose
    private float modelScaleY;
    
    @Expose
    private float modelScaleZ;
    
    public MadModelScale(
            float modelWorldScaleX,
            float modelWorldScaleY,
            float modelWorldScaleZ)
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

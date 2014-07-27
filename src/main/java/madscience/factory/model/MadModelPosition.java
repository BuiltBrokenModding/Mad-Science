package madscience.factory.model;

import com.google.gson.annotations.Expose;

public class MadModelPosition
{
    @Expose
    private float modelTranslateX;
    
    @Expose
    private float modelTranslateY;
    
    @Expose
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

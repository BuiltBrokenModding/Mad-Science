package madscience.factory.model;

import com.google.gson.annotations.Expose;

public class MadModelBounds
{
    @Expose
    private MadModelPosition lowerBounds;
    
    @Expose
    private MadModelPosition upperBounds;
    
    public MadModelBounds(MadModelPosition lowerBounds, MadModelPosition upperBounds)
    {
        super();
        
        this.lowerBounds = lowerBounds;
        this.upperBounds = upperBounds;
    }

    public MadModelPosition getLowerBounds()
    {
        return lowerBounds;
    }

    public MadModelPosition getUpperBounds()
    {
        return upperBounds;
    }
}

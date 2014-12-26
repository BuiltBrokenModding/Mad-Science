package madscience.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class BoundingBox
{
    @Expose
    @SerializedName("LowerBounds")
    private ModelPosition lowerBounds;

    @Expose
    @SerializedName("UpperBounds")
    private ModelPosition upperBounds;

    public BoundingBox(ModelPosition lowerBounds, ModelPosition upperBounds)
    {
        super();

        this.lowerBounds = lowerBounds;
        this.upperBounds = upperBounds;
    }

    public ModelPosition getLowerBounds()
    {
        return lowerBounds;
    }

    public ModelPosition getUpperBounds()
    {
        return upperBounds;
    }
}

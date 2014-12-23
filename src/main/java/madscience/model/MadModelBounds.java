package madscience.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class MadModelBounds
{
    @Expose
    @SerializedName("LowerBounds")
    private MadModelPosition lowerBounds;

    @Expose
    @SerializedName("UpperBounds")
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

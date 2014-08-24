package madscience.factory.control;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public final class MadGUIControl
{
    /* Screen coordinates for where data where render. */
    @Expose
    @SerializedName("ScreenX")
    private final int screenX;

    /* Screen coordinates for where data where render. */
    @Expose 
    @SerializedName("ScreenY")
    private final int screenY;

    /* Filler coordinates where image data to render at screen coords can be found. */
    @Expose 
    @SerializedName("FillerX")
    private final int fillerX;

    /* Filler coordinates where image data to render at screen coords can be found. */
    @Expose 
    @SerializedName("FillerY")
    private final int fillerY;

    /* Total size of area that needs to be rendered. */
    @Expose 
    @SerializedName("SizeX")
    private final int sizeX;

    /* Total size of area that needs to be rendered. */
    @Expose 
    @SerializedName("SizeY")
    private final int sizeY;

    /* Type of control based on enumeration. */
    @Expose 
    @SerializedName("ControlType")
    private final MadGUIControlTypeEnum controlType;

    public MadGUIControl(MadGUIControlTypeEnum controlType, int screenX, int screenY, int fillX, int fillY, int sizeX, int sizeY) // NO_UCD (unused code)
    {
        this.controlType = controlType;

        this.screenX = screenX;
        this.screenY = screenY;

        this.fillerX = fillX;
        this.fillerY = fillY;

        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public int fillerX()
    {
        return this.fillerX;
    }

    public int fillerY()
    {
        return this.fillerY;
    }

    public MadGUIControlTypeEnum getControlType()
    {
        return this.controlType;
    }

    public int screenX()
    {
        return this.screenX;
    }

    public int screenY()
    {
        return this.screenY;
    }

    public int sizeX()
    {
        return this.sizeX;
    }

    public int sizeY()
    {
        return this.sizeY;
    }
}

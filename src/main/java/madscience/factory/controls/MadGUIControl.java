package madscience.factory.controls;

import com.google.gson.annotations.Expose;


public final class MadGUIControl
{
    /* Screen coordinates for where data where render. */
    @Expose private final int screenX;

    /* Screen coordinates for where data where render. */
    @Expose private final int screenY;

    /* Filler coordinates where image data to render at screen coords can be found. */
    @Expose private final int fillerX;

    /* Filler coordinates where image data to render at screen coords can be found. */
    @Expose private final int fillerY;

    /* Total size of area that needs to be rendered. */
    @Expose private final int sizeX;

    /* Total size of area that needs to be rendered. */
    @Expose private final int sizeY;

    /* Type of control based on enumeration. */
    @Expose private final MadGUIControlTypeEnum controlType;

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

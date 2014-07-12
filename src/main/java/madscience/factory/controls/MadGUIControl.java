package madscience.factory.controls;

import com.google.gson.annotations.Expose;


public final class MadGUIControl implements IMadGUIControl
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

    public MadGUIControl(MadGUIControlTypeEnum controlType, int screenX, int screenY, int fillX, int fillY, int sizeX, int sizeY)
    {
        this.controlType = controlType;

        this.screenX = screenX;
        this.screenY = screenY;

        this.fillerX = fillX;
        this.fillerY = fillY;

        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    @Override
    public int fillerX()
    {
        return this.fillerX;
    }

    @Override
    public int fillerY()
    {
        return this.fillerY;
    }

    @Override
    public MadGUIControlTypeEnum getControlType()
    {
        return this.controlType;
    }

    @Override
    public int screenX()
    {
        return this.screenX;
    }

    @Override
    public int screenY()
    {
        return this.screenY;
    }

    @Override
    public int sizeX()
    {
        return this.sizeX;
    }

    @Override
    public int sizeY()
    {
        return this.sizeY;
    }
}

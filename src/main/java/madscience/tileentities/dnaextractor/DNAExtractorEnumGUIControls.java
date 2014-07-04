package madscience.tileentities.dnaextractor;

import madscience.factory.controls.MadGUIControlInterface;
import madscience.factory.controls.MadGUIControlTypeEnum;

public enum DNAExtractorEnumGUIControls implements MadGUIControlInterface
{
    MutantDNATank(MadGUIControlTypeEnum.TankGauge, 131, 19, 176, 31, 16, 58), Power(MadGUIControlTypeEnum.PowerLevelX, 10, 49, 176, 0, 14, 14), Work(MadGUIControlTypeEnum.CookingProgressY, 32, 31, 176, 14, 31, 17);

    /* Screen coordinates for where data where render. */
    private final int screenX;

    /* Screen coordinates for where data where render. */
    private final int screenY;

    /* Filler coordinates where image data to render at screen coords can be found. */
    private final int fillerX;

    /* Filler coordinates where image data to render at screen coords can be found. */
    private final int fillerY;

    /* Total size of area that needs to be rendered. */
    private final int sizeX;

    /* Total size of area that needs to be rendered. */
    private final int sizeY;

    /* Type of control based on enumeration. */
    private final MadGUIControlTypeEnum controlType;

    private DNAExtractorEnumGUIControls(MadGUIControlTypeEnum controlType, int screenX, int screenY, int fillX, int fillY, int sizeX, int sizeY)
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

package madscience.factory.controls;

public interface MadGUIControlInterface
{
    /* Filler coordinates where image data to render at screen coords can be found. */
    public abstract int fillerX();

    /* Filler coordinates where image data to render at screen coords can be found. */
    public abstract int fillerY();

    /* Determines what type of control this is based on standardized types. */
    public abstract MadGUIControlTypeEnum getControlType();

    /* Screen coordinates for where data where render. */
    public abstract int screenX();

    /* Screen coordinates for where data where render. */
    public abstract int screenY();

    /* Total size of area that needs to be rendered. */
    public abstract int sizeX();

    /* Total size of area that needs to be rendered. */
    public abstract int sizeY();
}
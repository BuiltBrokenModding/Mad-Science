package madscience.factory.controls;

public interface MadGUIControlInterface
{
    /* Screen coordinates for where data where render. */
    public abstract int screenY();

    /* Screen coordinates for where data where render. */
    public abstract int screenX();

    /* Filler coordinates where image data to render at screen coords can be found. */
    public abstract int fillerY();

    /* Filler coordinates where image data to render at screen coords can be found. */
    public abstract int fillerX();

    /* Total size of area that needs to be rendered. */
    public abstract int sizeY();

    /* Total size of area that needs to be rendered. */
    public abstract int sizeX();
    
    /* Determines what type of control this is based on standardized types. */
    public abstract MadGUIControlTypeEnum getControlType();
}
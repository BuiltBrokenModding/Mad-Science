package madscience.factory.buttons;

public interface MadGUIButtonInterface
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
    
    /* Determines what type of button this is based on standardized types. */
    public abstract MadGUIButtonTypeEnum getButtonType();
    
    /* Determines action that will be taken when this button is clicked by user. */
    public abstract MadGUIButtonClickActionEnum getClickAction();
    
    /* Index that this particular button instance will reference. */
    public abstract int buttonID();
    
    /* Internal string used to search in localization files.  */
    public abstract String getTooltip();
    
    /* Custom object that lets us carry along any extra information required */
    public abstract Object getUserData();
}
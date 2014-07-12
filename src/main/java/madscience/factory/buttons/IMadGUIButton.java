package madscience.factory.buttons;

public interface IMadGUIButton
{
    /** Index that this particular button instance will reference. */
    public abstract int buttonID();

    /** Determines what type of button this is based on standardized types. */
    public abstract MadGUIButtonTypeEnum getButtonType();

    /** Determines action that will be taken when this button is clicked by user. */
    public abstract MadGUIButtonClickActionEnum getClickAction();

    /** Custom object that lets us carry along any extra information required */
    public abstract Object getUserData();

    /** Screen coordinates for where data where render. */
    public abstract int screenX();

    /** Screen coordinates for where data where render. */
    public abstract int screenY();

    /** Total size of area that needs to be rendered. */
    public abstract int sizeX();

    /** Total size of area that needs to be rendered. */
    public abstract int sizeY();

    /** Unlocalized name that will represent this button in localization file. */
    public abstract String getTooltip();
}
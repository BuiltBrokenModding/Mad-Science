package madscience.factory.buttons;

import com.google.gson.annotations.Expose;

public final class MadGUIButton implements IMadGUIButton
{
    /** Screen coordinates for where data where render. */
    @Expose private final int screenX;

    /** Screen coordinates for where data where render. */
    @Expose private final int screenY;

    /** Filler coordinates where image data to render at screen coords can be found. */
    @Expose private final int fillerX;

    /** Filler coordinates where image data to render at screen coords can be found. */
    @Expose private final int fillerY;

    /** Total size of area that needs to be rendered. */
    @Expose private final int sizeX;

    /** Total size of area that needs to be rendered. */
    @Expose private final int sizeY;

    /** String token used to locate tooltip information in localization files */
    @Expose private final String tooltipToken;

    /** Type of control based on enumeration. */
    @Expose private final MadGUIButtonTypeEnum buttonType;

    /** Determines action taken when button is clicked by player */
    @Expose private final MadGUIButtonClickActionEnum clickAction;

    /** Custom object that holds anything that we want it to. */
    @Expose private final Object userData;
    
    /** Reference to button via ID number which is unique to every button and separate from containers or other controls. */
    @Expose private final int buttonID;

    public MadGUIButton(int buttonID, MadGUIButtonTypeEnum buttonType, MadGUIButtonClickActionEnum clickAction, String unlocalizedTooltip, Object userData, int screenX, int screenY, int fillX, int fillY, int sizeX, int sizeY)
    {
        // ID the button will have in Minecraft/Forge button list.
        this.buttonID = buttonID;
        
        // Determines what type of button we will be.
        this.buttonType = buttonType;

        // Determines action taken when clicked.
        this.clickAction = clickAction;

        // Extra information that can be casted to anything based on click action.
        this.userData = userData;

        // Location of button on the screen relative to GUI.
        this.screenX = screenX;
        this.screenY = screenY;

        // Location of button filler texture (if there is any, invisible buttons need this).
        this.fillerX = fillX;
        this.fillerY = fillY;

        // Determines to the total size of this container slot.
        this.sizeX = sizeX;
        this.sizeY = sizeY;

        // Unlocalized name for button tooltip that will be looked up in localization file.
        this.tooltipToken = unlocalizedTooltip;
    }

    @Override
    public int buttonID()
    {
        return this.buttonID;
    }

    @Override
    public MadGUIButtonTypeEnum getButtonType()
    {
        return this.buttonType;
    }

    @Override
    public MadGUIButtonClickActionEnum getClickAction()
    {
        return this.clickAction;
    }

    @Override
    public Object getUserData()
    {
        return this.userData;
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

    @Override
    public String getTooltip()
    {
        return this.tooltipToken;
    }
}

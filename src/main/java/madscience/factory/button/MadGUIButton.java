package madscience.factory.button;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public final class MadGUIButton
{
    /** Screen coordinates for where data will render. */
    @Expose
    @SerializedName("ScreenX")
    private final int screenX;

    /** Screen coordinates for where data will render. */
    @Expose
    @SerializedName("ScreenY")
    private final int screenY;

    /** Filler coordinates where image data to render at screen coords can be found. */
    @Expose
    @SerializedName("FillerX")
    private final int fillerX;

    /** Filler coordinates where image data to render at screen coords can be found. */
    @Expose
    @SerializedName("FillerY")
    private final int fillerY;

    /** Total size of area that needs to be rendered. */
    @Expose
    @SerializedName("SizeX")
    private final int sizeX;

    /** Total size of area that needs to be rendered. */
    @Expose
    @SerializedName("SizeY")
    private final int sizeY;

    /** String token used to locate tooltip information in localization files */
    @Expose
    @SerializedName("TooltipToken")
    private final String tooltipToken;

    /** Type of control based on enumeration. */
    @Expose
    @SerializedName("ButtonType")
    private final MadGUIButtonTypeEnum buttonType;

    /** Determines action taken when button is clicked by player */
    @Expose
    @SerializedName("ClickAction")
    private final MadGUIButtonClickActionEnum clickAction;

    /** Custom object that holds anything that we want it to. */
    @Expose
    @SerializedName("UserData")
    private final Object userData;
    
    /** Reference to button via ID number which is unique to every button and separate from containers or other controls. */
    @Expose
    @SerializedName("ButtonID")
    private final int buttonID;

    public MadGUIButton(int buttonID, MadGUIButtonTypeEnum buttonType, MadGUIButtonClickActionEnum clickAction, String unlocalizedTooltip, Object userData, int screenX, int screenY, int fillX, int fillY, int sizeX, int sizeY) // NO_UCD (unused code)
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

    public int buttonID()
    {
        return this.buttonID;
    }

    public MadGUIButtonTypeEnum getButtonType()
    {
        return this.buttonType;
    }

    public MadGUIButtonClickActionEnum getClickAction()
    {
        return this.clickAction;
    }

    public Object getUserData()
    {
        return this.userData;
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

    public String getTooltip()
    {
        return this.tooltipToken;
    }
}

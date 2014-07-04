package madscience.tileentities.dnaextractor;

import madscience.MadConfig;
import madscience.factory.buttons.MadGUIButtonClickActionEnum;
import madscience.factory.buttons.MadGUIButtonInterface;
import madscience.factory.buttons.MadGUIButtonTypeEnum;
import net.minecraft.util.StatCollector;

public enum DNAExtractorEnumGUIButtons implements MadGUIButtonInterface
{    
    HelpLink(MadGUIButtonTypeEnum.InvisibleButton, MadGUIButtonClickActionEnum.OpenLink, MadConfig.DNAEXTRACTOR_HELP, 166, 4, 9, 32, 6, 5);

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
    
    /* String token used to locate tooltip information in localization files */
    private final String tooltipToken;
    
    /* Type of control based on enumeration. */
    private final MadGUIButtonTypeEnum buttonType;
    
    /* Determines action taken when button is clicked by player */
    private final MadGUIButtonClickActionEnum clickAction;
    
    /* Custom object that holds anything that we want it to. */
    private final Object userData;

    DNAExtractorEnumGUIButtons(MadGUIButtonTypeEnum buttonType,
            MadGUIButtonClickActionEnum clickAction,
            Object userData,
            int screenX, int screenY,
            int fillX, int fillY,
            int sizeX, int sizeY)
    {        
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
        
        // Use the ordinal position in enumeration to get name and base tooltip token from it.
        // Note: Constructs a string that looks like "DNAExtractorEnumGUIButtons.HelpLink.tooltip"
        String friendlyName = this.getClass().getSimpleName() + "." + this.name() + ".tooltip";
        
        // Grab the localized tooltip from our localization files based on the token.
        String slotTooltip = StatCollector.translateToLocal(friendlyName);
        
        // Only put the tooltip into variable if it is not empty.
        if (slotTooltip != null && !slotTooltip.isEmpty())
        {
            // This text will be displayed above the given container slot.
            this.tooltipToken = slotTooltip;
        }
        else
        {
            // Default response is to return an empty string which will render no tooltip.
            this.tooltipToken = "";
        }
    }

    @Override
    public int buttonID()
    {
        return this.ordinal();
    }

    @Override
    public String getTooltip()
    {
        return this.tooltipToken;
    }

    @Override
    public int screenY()
    {
        return this.screenY;
    }
    
    @Override
    public int screenX()
    {
        return this.screenX;
    }
    
    @Override
    public int fillerY()
    {
        return this.fillerY;
    }
    
    @Override
    public int fillerX()
    {
        return this.fillerX;
    }
    
    @Override
    public int sizeY()
    {
        return this.sizeY;
    }
    
    @Override
    public int sizeX()
    {
        return this.sizeX;
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
}

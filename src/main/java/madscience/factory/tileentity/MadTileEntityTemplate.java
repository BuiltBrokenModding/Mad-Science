package madscience.factory.tileentity;

import madscience.factory.interfaces.buttons.MadGUIButtonInterface;
import madscience.factory.interfaces.controls.MadGUIControlInterface;
import madscience.factory.interfaces.slotcontainers.MadSlotContainerInterface;

public class MadTileEntityTemplate
{    
    /* Holds the internal name of this machine as used in config files and referenced in other lists. */
    private final String machineName;
    
    /* Stores all of the slot containers where items can be inputed and extracted from. */
    private final MadSlotContainerInterface[] containerTemplate;
    
    /* Stores all of the GUI controls like tanks, progress bars, animations, etc. */
    private final MadGUIControlInterface[] guiTemplate;
    
    /* Stores all of the GUI button, includes invisible ones with custom textures also. */
    private final MadGUIButtonInterface[] buttonTemplate;

    /* Constructs a Minecraft Forge tile entity which can be interacted with by the player. */
    public MadTileEntityTemplate(String machineName,
            MadSlotContainerInterface[] containerTemplate,
            MadGUIControlInterface[] guiTemplate,
            MadGUIButtonInterface[] buttonTemplate)
    {
        this.machineName = machineName;
        this.containerTemplate = containerTemplate;
        this.guiTemplate = guiTemplate;
        this.buttonTemplate = buttonTemplate;
    }
    
    /* Returns the internal name of this machine used by code (not for players!) */
    public String getMachineName()
    {
        return this.machineName;
    }
    
    /* Returns array of slot containers used by server to keep track of items. */
    public MadSlotContainerInterface[] getSlotContainers()
    {
        return this.containerTemplate;
    }
    
    /* Returns array of GUI controls used by client to show machine progress when working. */
    public MadGUIControlInterface[] getGUIControls()
    {
        return this.guiTemplate;
    }
    
    /* Returns array of GUI buttons used by client and server to keep track of clicks and events. */
    public MadGUIButtonInterface[] getGUIButtons()
    {
        return this.buttonTemplate;
    }
}
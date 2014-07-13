package madscience.factory;

import madscience.factory.buttons.MadGUIButton;
import madscience.factory.controls.MadGUIControl;
import madscience.factory.energy.MadEnergy;
import madscience.factory.fluids.MadFluid;
import madscience.factory.recipes.MadRecipe;
import madscience.factory.slotcontainers.MadSlotContainer;
import madscience.factory.sounds.MadSound;

import com.google.gson.annotations.Expose;

public class MadTileEntityFactoryProductData
{
    /** Holds the internal name of this machine as used in config files and referenced in other lists. */
    @Expose
    private String machineName;
    
    /** Reference number used by Forge/MC to keep track of this tile entity. */
    @Expose
    private int blockID;
    
    /** Holds string reference to our logic class which will be populated at runtime. */
    @Expose
    private String logicClassFullyQualifiedName;
    
    /** Stores slot containers where items can be inputed and extracted from. */
    @Expose
    private MadSlotContainer[] containerTemplate;
    
    /** Stores GUI controls like tanks, progress bars, animations, etc. */
    @Expose
    private MadGUIControl[] guiControlsTemplate;
    
    /** Stores GUI buttons, includes invisible ones with custom textures also. */
    @Expose
    private MadGUIButton[] guiButtonTemplate;
    
    /** Stores fluids that this machine will be able to interface with it's internal tank methods. */
    @Expose
    private MadFluid[] fluidsSupported;
    
    /** Stores information about how we want to plugged into other electrical grids. */
    @Expose
    private MadEnergy[] energySupported;
    
    /** Stores collection of sounds that can be mapped to various triggers on this machine. */
    @Expose
    private MadSound[] soundArchive;
    
    /** Stores all known recipes that should be associated with this machine. */
    @Expose
    private MadRecipe[] recipeArchive;
    
    public MadTileEntityFactoryProductData( // NO_UCD (unused code)
            String machineName,
            int blockID,
            String logicClassNamespace,
            MadSlotContainer[] containerTemplate,
            MadGUIControl[] guiTemplate,
            MadGUIButton[] buttonTemplate,
            MadFluid[] fluidsTemplate,
            MadEnergy[] energyTemplate,
            MadSound[] soundArchive,
            MadRecipe[] recipeArchive)
    {
        super();
        
        // Basic machine info.
        this.machineName = machineName;
        this.blockID = blockID;
        this.logicClassFullyQualifiedName = logicClassNamespace;
        
        // Optional container info for machine functionality and GUI.
        this.containerTemplate = containerTemplate;
        this.guiControlsTemplate = guiTemplate;
        this.guiButtonTemplate = buttonTemplate;
        this.fluidsSupported = fluidsTemplate;
        this.energySupported = energyTemplate;
        this.soundArchive = soundArchive;
        this.recipeArchive = recipeArchive;
    }

    public String getMachineName()
    {
        return machineName;
    }

    public int getBlockID()
    {
        return blockID;
    }

    public String getLogicClassFullyQualifiedName()
    {
        return logicClassFullyQualifiedName;
    }

    public MadSlotContainer[] getContainerTemplate()
    {
        return containerTemplate;
    }

    public MadGUIControl[] getGuiControlsTemplate()
    {
        return guiControlsTemplate;
    }

    public MadGUIButton[] getGuiButtonTemplate()
    {
        return guiButtonTemplate;
    }

    public MadFluid[] getFluidsSupported()
    {
        return fluidsSupported;
    }

    public MadEnergy[] getEnergySupported()
    {
        return energySupported;
    }

    public MadSound[] getSoundArchive()
    {
        return soundArchive;
    }

    public MadRecipe[] getRecipeArchive()
    {
        return recipeArchive;
    }
}
package madscience.factory;

import madscience.factory.buttons.IMadGUIButton;
import madscience.factory.controls.IMadGUIControl;
import madscience.factory.energy.IMadEnergy;
import madscience.factory.fluids.IMadFluid;
import madscience.factory.recipes.IMadRecipe;
import madscience.factory.slotcontainers.IMadSlotContainer;
import madscience.factory.sounds.IMadSound;
import madscience.factory.tileentity.MadTileEntityPrefab;
import net.minecraft.block.BlockContainer;

import com.google.gson.annotations.Expose;

public class MadTileEntityFactoryProductData
{
    /** Holds the internal name of this machine as used in config files and referenced in other lists. */
    @Expose
    public String machineName;
    
    /** Reference number used by Forge/MC to keep track of this tile entity. */
    @Expose
    public int blockID;
    
    /** Holds string reference to our logic class which will be populated at runtime. */
    @Expose
    public String logicClassFullyQualifiedName;
    
    /** Stores slot containers where items can be inputed and extracted from. */
    @Expose
    public IMadSlotContainer[] containerTemplate;
    
    /** Stores GUI controls like tanks, progress bars, animations, etc. */
    @Expose
    public IMadGUIControl[] guiControlsTemplate;
    
    /** Stores GUI buttons, includes invisible ones with custom textures also. */
    @Expose
    public IMadGUIButton[] guiButtonTemplate;
    
    /** Stores fluids that this machine will be able to interface with it's internal tank methods. */
    @Expose
    public IMadFluid[] fluidsSupported;
    
    /** Stores information about how we want to plugged into other electrical grids. */
    @Expose
    public IMadEnergy[] energySupported;
    
    /** Stores collection of sounds that can be mapped to various triggers on this machine. */
    @Expose
    public IMadSound[] soundArchive;
    
    /** Stores all known recipes that should be associated with this machine. */
    @Expose
    public IMadRecipe[] recipeArchive;
    
    /** Stores if we have loaded the sounds or not, allowing us to throw an error if called again */
    public boolean soundArchiveLoaded;
    
    /** Stores block container class which provides Forge/MC with server side slot info. */
    public BlockContainer blockContainer;
    
    /** Stores reference to tile entity class itself which makes up logic or brains of this machine. */
    public Class<? extends MadTileEntityPrefab> tileEntityLogicClass;

    public MadTileEntityFactoryProductData(
            IMadSlotContainer[] containerTemplate,
            IMadGUIControl[] guiControlsTemplate,
            IMadGUIButton[] guiButtonTemplate,
            IMadFluid[] fluidsSupported,
            IMadEnergy[] energySupported,
            IMadSound[] soundArchive,
            IMadRecipe[] recipeArchive)
    {
        this.containerTemplate = containerTemplate;
        this.guiControlsTemplate = guiControlsTemplate;
        this.guiButtonTemplate = guiButtonTemplate;
        this.fluidsSupported = fluidsSupported;
        this.energySupported = energySupported;
        this.soundArchive = soundArchive;
        this.recipeArchive = recipeArchive;
        this.soundArchiveLoaded = false;
    }
}
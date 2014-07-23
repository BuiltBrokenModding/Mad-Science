package madscience.factory;

import java.util.Hashtable;

import madscience.factory.buttons.MadGUIButton;
import madscience.factory.controls.MadGUIControl;
import madscience.factory.crafting.MadCraftingRecipe;
import madscience.factory.damage.MadDamage;
import madscience.factory.energy.MadEnergy;
import madscience.factory.fluids.MadFluid;
import madscience.factory.heat.MadHeat;
import madscience.factory.mod.MadMod;
import madscience.factory.model.MadModel;
import madscience.factory.model.MadModelFile;
import madscience.factory.recipes.MadRecipe;
import madscience.factory.slotcontainers.MadSlotContainer;
import madscience.factory.sounds.MadSound;

import com.google.gson.annotations.Expose;

public class MadTileEntityFactoryProductData
{
    /** Holds the internal name of this machine as used in config files and referenced in other lists. */
    @Expose
    private String machineName;
    
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
    
    /** Stores recipes that are used to make this machine itself. */
    @Expose
    private MadCraftingRecipe[] craftingRecipes;
    
    @Expose
    private MadModel modelArchive;
    
    @Expose
    private MadHeat[] heatLevelsSupported;
    
    @Expose
    private MadDamage[] damageTrackingSupported;
    
    /** Reference number used by Forge/MC to keep track of this tile entity. */
    private int blockID;
    
    public MadTileEntityFactoryProductData( // NO_UCD (unused code)
            String machineName,
            String logicClassNamespace,
            MadSlotContainer[] containerTemplate,
            MadGUIControl[] guiTemplate,
            MadGUIButton[] buttonTemplate,
            MadFluid[] fluidsTemplate,
            MadEnergy[] energyTemplate,
            MadHeat[] heatTemplate,
            MadDamage[] damageTemplate,
            MadSound[] soundArchive,
            MadRecipe[] recipeArchive,
            MadCraftingRecipe[] craftingRecipe,
            MadModel modelArchive)
    {
        super();
        
        // Note: BlockID is set by configuration phase in MadForgeMod.java
        
        // Basic machine info.
        this.machineName = machineName;
        this.logicClassFullyQualifiedName = logicClassNamespace;
        
        // Optional container info for machine functionality and GUI.
        this.containerTemplate = containerTemplate;
        this.guiControlsTemplate = guiTemplate;
        this.guiButtonTemplate = buttonTemplate;
        
        // Fluids.
        this.fluidsSupported = fluidsTemplate;
        
        // Electricity.
        this.energySupported = energyTemplate;
        
        // Sounds.
        this.soundArchive = soundArchive;
        
        // Crafting recipes to create the machine itself and recipes that go inside it.
        this.recipeArchive = recipeArchive;
        this.craftingRecipes = craftingRecipe;
        
        // Model and texture paths.
        this.modelArchive = modelArchive;
        
        // Heat level information.
        this.heatLevelsSupported = heatTemplate;
        
        // Damage information about machines health.
        this.damageTrackingSupported = damageTemplate;
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

    public MadCraftingRecipe[] getCraftingRecipes()
    {
        return craftingRecipes;
    }

    public void setMachineName(String machineName)
    {
        this.machineName = machineName;
    }

    public void setBlockID(int blockID)
    {
        this.blockID = blockID;
    }

    public void setLogicClassFullyQualifiedName(String logicClassFullyQualifiedName)
    {
        this.logicClassFullyQualifiedName = logicClassFullyQualifiedName;
    }

    public void setContainerTemplate(MadSlotContainer[] containerTemplate)
    {
        this.containerTemplate = containerTemplate;
    }

    public void setGuiControlsTemplate(MadGUIControl[] guiControlsTemplate)
    {
        this.guiControlsTemplate = guiControlsTemplate;
    }

    public void setGuiButtonTemplate(MadGUIButton[] guiButtonTemplate)
    {
        this.guiButtonTemplate = guiButtonTemplate;
    }

    public void setFluidsSupported(MadFluid[] fluidsSupported)
    {
        this.fluidsSupported = fluidsSupported;
    }

    public void setEnergySupported(MadEnergy[] energySupported)
    {
        this.energySupported = energySupported;
    }

    public void setSoundArchive(MadSound[] soundArchive)
    {
        this.soundArchive = soundArchive;
    }

    public void setRecipeArchive(MadRecipe[] recipeArchive)
    {
        this.recipeArchive = recipeArchive;
    }

    public void setCraftingRecipes(MadCraftingRecipe[] craftingRecipe)
    {
        this.craftingRecipes = craftingRecipe;
    }

    public MadModel getModelArchive()
    {
        return modelArchive;
    }

    public void setModelArchive(MadModel modelRenderArchive)
    {
        this.modelArchive = modelRenderArchive;
    }

    public MadHeat[] getHeatLevelsSupported()
    {
        return heatLevelsSupported;
    }

    public void setHeatLevelsSupported(MadHeat[] heatLevelsSupported)
    {
        this.heatLevelsSupported = heatLevelsSupported;
    }

    public MadDamage[] getDamageTrackingSupported()
    {
        return damageTrackingSupported;
    }

    public void setDamageTrackingSupported(MadDamage[] damageTrackingSupported)
    {
        this.damageTrackingSupported = damageTrackingSupported;
    }
}
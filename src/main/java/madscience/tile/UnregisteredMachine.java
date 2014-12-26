package madscience.tile;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import madscience.block.MultiBlockConfiguration;
import madscience.button.GUIButtonTemplate;
import madscience.container.ContainerTemplate;
import madscience.control.GUIControlsTemplate;
import madscience.crafting.CraftingRecipe;
import madscience.damage.DamageTrackingSupported;
import madscience.energy.EnergySupported;
import madscience.fluid.FluidsSupported;
import madscience.heat.HeatLevelsSupported;
import madscience.model.BoundingBox;
import madscience.model.ModelArchive;
import madscience.recipe.RecipeArchive;
import madscience.sound.SoundArchive;


public class UnregisteredMachine
{
    /**
     * Holds the internal name of this machine as used in config files and referenced in other lists.
     */
    @Expose
    @SerializedName("MachineName")
    private String machineName;

    /**
     * Holds string reference to our logic class which will be populated at runtime.
     */
    @Expose
    @SerializedName("LogicClassFullyQualifiedName")
    private String logicClassFullyQualifiedName;

    /**
     * Determines in Minecraft terms how hard it is to break this particular machine with tools.
     */
    @Expose
    @SerializedName("BlockHardness")
    private float blockHardness;

    /**
     * Determines in Minecraft terms how resistant this block will be to being destroyed by explosions (if modflag for world damage is enabled).
     */
    @Expose
    @SerializedName("ExplosionResistance")
    private float explosionResistance;

    /**
     * Using a single vector we will determine if we need multi-blocks based on ID registered for tile (since it needs a block to work anyway).
     */
    @Expose
    @SerializedName("MultiBlockConfiguration")
    private MultiBlockConfiguration multiBlockConfiguration;

    /**
     * Stores upper and lower bounds stored as vectors for use in the block to determine how large black outline for bounding box should be.
     */
    @Expose
    @SerializedName("BoundingBox")
    private BoundingBox boundingBox;

    /**
     * Stores slot containers where items can be inputed and extracted from.
     */
    @Expose
    @SerializedName("ContainerTemplate")
    private ContainerTemplate[] containerTemplate;

    /**
     * Stores GUI controls like tanks, progress bars, animations, etc.
     */
    @Expose
    @SerializedName("GUIControlsTemplate")
    private GUIControlsTemplate[] guiControlsTemplate;

    /**
     * Stores GUI buttons, includes invisible ones with custom textures also.
     */
    @Expose
    @SerializedName("GUIButtonTemplate")
    private GUIButtonTemplate[] guiButtonTemplate;

    /**
     * Stores fluids that this machine will be able to interface with it's internal tank methods.
     */
    @Expose
    @SerializedName("FluidsSupported")
    private FluidsSupported[] fluidsSupported;

    /**
     * Stores information about how we want to plugged into other electrical grids.
     */
    @Expose
    @SerializedName("EnergySupported")
    private EnergySupported[] energySupported;

    /**
     * Stores collection of sounds that can be mapped to various triggers on this machine.
     */
    @Expose
    @SerializedName("SoundArchive")
    private SoundArchive[] soundArchive;

    /**
     * Stores all known recipes that should be associated with this machine.
     */
    @Expose
    @SerializedName("RecipeArchive")
    private RecipeArchive[] recipeArchive;

    /**
     * Stores recipes that are used to make this machine itself.
     */
    @Expose
    @SerializedName("CraftingRecipes")
    private CraftingRecipe[] craftingRecipes;

    @Expose
    @SerializedName("ModelArchive")
    private ModelArchive modelArchive;

    @Expose
    @SerializedName("HeatLevelsSupported")
    private HeatLevelsSupported[] heatLevelsSupported;

    @Expose
    @SerializedName("DamageTrackingSupported")
    private DamageTrackingSupported[] damageTrackingSupported;

    /**
     * Reference number used by Forge/MC to keep track of this tile entity.
     */
    private int blockID;

    public UnregisteredMachine( // NO_UCD (unused code)
                                String machineName,
                                String logicClassNamespace,
                                float blockHardness,
                                float explosionResistance,
                                BoundingBox boundingBox,
                                MultiBlockConfiguration ghostBlockVector,
                                ContainerTemplate[] containerTemplate,
                                GUIControlsTemplate[] guiTemplate,
                                GUIButtonTemplate[] buttonTemplate,
                                FluidsSupported[] fluidsTemplate,
                                EnergySupported[] energyTemplate,
                                HeatLevelsSupported[] heatTemplate,
                                DamageTrackingSupported[] damageTemplate,
                                SoundArchive[] soundArchive,
                                RecipeArchive[] recipeArchive,
                                CraftingRecipe[] craftingRecipe,
                                ModelArchive modelArchive)
    {
        super();

        // Note: BlockID is set by configuration phase in ForgeMod.java

        // Name of machine which also is how we reference it in code and as key in dictionary lists.
        this.machineName = machineName;

        // Namespace path to class that should be available at runtime which factory will attempt to load and bind to MadTileEntityPrefab.
        this.logicClassFullyQualifiedName = logicClassNamespace;

        // Determines how hard a block is to break using Minecraft/Forge floating points.
        this.blockHardness = blockHardness;

        // Determines how resistant this block will be to explosions, higher values make for more resistance (indestructable like bedrock is -1).
        this.explosionResistance = explosionResistance;

        // Bounding box for this machine which determines how
        this.boundingBox = boundingBox;

        // Determines how many ghost blocks need to be created on sides, top or bottom of machine center. 
        this.multiBlockConfiguration = ghostBlockVector;

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

    public void setMachineName(String machineName)
    {
        this.machineName = machineName;
    }

    public int getBlockID()
    {
        return blockID;
    }

    public void setBlockID(int blockID)
    {
        this.blockID = blockID;
    }

    public String getLogicClassFullyQualifiedName()
    {
        return logicClassFullyQualifiedName;
    }

    public void setLogicClassFullyQualifiedName(String logicClassFullyQualifiedName)
    {
        this.logicClassFullyQualifiedName = logicClassFullyQualifiedName;
    }

    public ContainerTemplate[] getContainerTemplate()
    {
        return containerTemplate;
    }

    public void setContainerTemplate(ContainerTemplate[] containerTemplate)
    {
        this.containerTemplate = containerTemplate;
    }

    public GUIControlsTemplate[] getGuiControlsTemplate()
    {
        return guiControlsTemplate;
    }

    public void setGuiControlsTemplate(GUIControlsTemplate[] guiControlsTemplate)
    {
        this.guiControlsTemplate = guiControlsTemplate;
    }

    public GUIButtonTemplate[] getGuiButtonTemplate()
    {
        return guiButtonTemplate;
    }

    public void setGuiButtonTemplate(GUIButtonTemplate[] guiButtonTemplate)
    {
        this.guiButtonTemplate = guiButtonTemplate;
    }

    public FluidsSupported[] getFluidsSupported()
    {
        return fluidsSupported;
    }

    public void setFluidsSupported(FluidsSupported[] fluidsSupported)
    {
        this.fluidsSupported = fluidsSupported;
    }

    public EnergySupported[] getEnergySupported()
    {
        return energySupported;
    }

    public void setEnergySupported(EnergySupported[] energySupported)
    {
        this.energySupported = energySupported;
    }

    public SoundArchive[] getSoundArchive()
    {
        return soundArchive;
    }

    public void setSoundArchive(SoundArchive[] soundArchive)
    {
        this.soundArchive = soundArchive;
    }

    public RecipeArchive[] getRecipeArchive()
    {
        return recipeArchive;
    }

    public void setRecipeArchive(RecipeArchive[] recipeArchive)
    {
        this.recipeArchive = recipeArchive;
    }

    public CraftingRecipe[] getCraftingRecipes()
    {
        return craftingRecipes;
    }

    public void setCraftingRecipes(CraftingRecipe[] craftingRecipe)
    {
        this.craftingRecipes = craftingRecipe;
    }

    public ModelArchive getModelArchive()
    {
        return modelArchive;
    }

    public void setModelArchive(ModelArchive modelRenderArchive)
    {
        this.modelArchive = modelRenderArchive;
    }

    public HeatLevelsSupported[] getHeatLevelsSupported()
    {
        return heatLevelsSupported;
    }

    public void setHeatLevelsSupported(HeatLevelsSupported[] heatLevelsSupported)
    {
        this.heatLevelsSupported = heatLevelsSupported;
    }

    public DamageTrackingSupported[] getDamageTrackingSupported()
    {
        return damageTrackingSupported;
    }

    public void setDamageTrackingSupported(DamageTrackingSupported[] damageTrackingSupported)
    {
        this.damageTrackingSupported = damageTrackingSupported;
    }

    public float getBlockHardness()
    {
        return blockHardness;
    }

    public void setBlockHardness(float blockHardness)
    {
        this.blockHardness = blockHardness;
    }

    public float getExplosionResistance()
    {
        return explosionResistance;
    }

    public void setExplosionResistance(float explosionResistance)
    {
        this.explosionResistance = explosionResistance;
    }

    public BoundingBox getBoundingBox()
    {
        return boundingBox;
    }

    public void setBoundingBox(BoundingBox boundingBox)
    {
        this.boundingBox = boundingBox;
    }

    public MultiBlockConfiguration getMultiBlockConfiguration()
    {
        return multiBlockConfiguration;
    }

    public void setMultiBlockConfiguration(MultiBlockConfiguration multiBlockConfiguration)
    {
        this.multiBlockConfiguration = multiBlockConfiguration;
    }
}
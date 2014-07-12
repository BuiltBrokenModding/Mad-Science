package madscience;

import java.util.ArrayList;
import java.util.List;

import madscience.factory.MadTileEntityFactory;
import madscience.factory.MadTileEntityFactoryProduct;
import madscience.factory.buttons.MadGUIButton;
import madscience.factory.buttons.MadGUIButtonClickActionEnum;
import madscience.factory.buttons.MadGUIButtonTypeEnum;
import madscience.factory.controls.MadGUIControl;
import madscience.factory.controls.MadGUIControlTypeEnum;
import madscience.factory.energy.MadEnergy;
import madscience.factory.fluids.MadFluid;
import madscience.factory.recipes.MadRecipe;
import madscience.factory.slotcontainers.MadSlotContainer;
import madscience.factory.slotcontainers.MadSlotContainerTypeEnum;
import madscience.factory.sounds.MadSound;
import madscience.factory.sounds.MadSoundPlaybackTypeEnum;
import madscience.factory.sounds.MadSoundTriggerEnum;
import madscience.items.ItemBlockTooltip;
import madscience.items.combinedgenomes.MadGenomeInfo;
import madscience.items.combinedgenomes.MadGenomeRegistry;
import madscience.tile.clayfurnace.ClayfurnaceBlock;
import madscience.tile.clayfurnace.ClayfurnaceEntity;
import madscience.tile.clayfurnace.ClayfurnaceRecipes;
import madscience.tile.cncmachine.CnCMachineBlock;
import madscience.tile.cncmachine.CnCMachineBlockGhost;
import madscience.tile.cncmachine.CnCMachineEntity;
import madscience.tile.cncmachine.CnCMachineRecipes;
import madscience.tile.cryofreezer.CryofreezerBlock;
import madscience.tile.cryofreezer.CryofreezerEntity;
import madscience.tile.cryotube.CryotubeBlock;
import madscience.tile.cryotube.CryotubeBlockGhost;
import madscience.tile.cryotube.CryotubeEntity;
import madscience.tile.dataduplicator.DataDuplicatorBlock;
import madscience.tile.dataduplicator.DataDuplicatorEntity;
import madscience.tile.incubator.IncubatorBlock;
import madscience.tile.incubator.IncubatorEntity;
import madscience.tile.incubator.IncubatorRecipes;
import madscience.tile.magloader.MagLoaderBlock;
import madscience.tile.magloader.MagLoaderBlockGhost;
import madscience.tile.magloader.MagLoaderEntity;
import madscience.tile.mainframe.MainframeBlock;
import madscience.tile.mainframe.MainframeEntity;
import madscience.tile.mainframe.MainframeRecipes;
import madscience.tile.meatcube.MeatcubeBlock;
import madscience.tile.meatcube.MeatcubeEntity;
import madscience.tile.sanitizer.SanitizerBlock;
import madscience.tile.sanitizer.SanitizerEntity;
import madscience.tile.sequencer.SequencerBlock;
import madscience.tile.sequencer.SequencerEntity;
import madscience.tile.soniclocator.SoniclocatorBlock;
import madscience.tile.soniclocator.SoniclocatorBlockGhost;
import madscience.tile.soniclocator.SoniclocatorEntity;
import madscience.tile.thermosonicbonder.ThermosonicBonderBlock;
import madscience.tile.thermosonicbonder.ThermosonicBonderEntity;
import madscience.tile.thermosonicbonder.ThermosonicBonderRecipes;
import madscience.tile.voxbox.VoxBoxBlock;
import madscience.tile.voxbox.VoxBoxEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;

import com.sun.media.sound.InvalidFormatException;

import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.registry.GameRegistry;

public class MadFurnaces
{
    // -------------
    // TILE ENTITIES
    // -------------

    // Needle Sanitizer
    public static BlockContainer SANTITIZER_TILEENTITY;
    public static final String SANTITIZER_INTERNALNAME = "needleSanitizer";

    // Mainframe
    public static BlockContainer MAINFRAME_TILEENTITY;
    public static final String MAINFRAME_INTERNALNAME = "computerMainframe";

    // Genome Sequencer
    public static BlockContainer SEQUENCER_TILEENTITY;
    public static final String SEQUENCER_INTERNALNAME = "genomeSequencer";

    // Cryogenic Freezer
    public static BlockContainer CRYOFREEZER_TILEENTITY;
    public static final String CRYOFREEZER_INTERNALNAME = "cryoFreezer";

    // Genome Incubator
    public static BlockContainer INCUBATOR_TILEENTITY;
    public static final String INCUBATOR_INTERNALNAME = "genomeIncubator";

    // Cryogenic Tube
    public static BlockContainer CRYOTUBE_TILEENTITY;
    public static final String CRYOTUBE_INTERNALNAME = "cryoTube";

    // Cryogenic Tube 'Ghost Block'
    public static Block CRYOTUBEGHOST;
    private static final String CRYOTUBEGHOST_INTERNALNAME = "ghostCryoTube";

    // Thermosonic Bonder
    public static BlockContainer THERMOSONIC_TILEENTITY;
    public static final String THERMOSONIC_INTERNALNAME = "thermosonicBonder";

    // Data Reel Duplicator
    public static BlockContainer DATADUPLICATOR_TILEENTITY;
    public static final String DATADUPLICATOR_INTERNALNAME = "dataDuplicator";

    // Soniclocator Device
    public static BlockContainer SONICLOCATOR_TILEENTITY;
    public static final String SONICLOCATOR_INTERNALNAME = "soniclocator";

    // Soniclocator 'Ghost Block'
    public static Block SONICLOCATORGHOST;
    private static final String SONICLOCATORGHOST_INTERNALNAME = "ghostSoniclocator";

    // Meat Cube [Slime + Cow,Pig,Chicken]
    public static BlockContainer MEATCUBE_TILEENTITY;
    public static final String MEATCUBE_INTERNALNAME = "meatCube";
    
    // Clay Furnace
    public static BlockContainer CLAYFURNACE_TILEENTITY;
    public static final String CLAYFURNACE_INTERNALNAME = "clayFurnace";
    
    // VOX Box
    public static BlockContainer VOXBOX_TILEENTITY;
    public static final String VOXBOX_INTERNALNAME = "voxBox";
    
    // Magazine Loader
    public static BlockContainer MAGLOADER_TILEENTITY;
    public static final String MAGLOADER_INTERNALNAME = "magLoader";
    
    // Magazine Loader 'Ghost Block'
    public static Block MAGLOADERGHOST;
    private static final String MAGLOADERGHOST_INTERNALNAME = "ghostMagLoader";    
    
    // CnC Machine
    public static BlockContainer CNCMACHINE_TILEENTITY;
    public static final String CNCMACHINE_INTERNALNAME = "cncMachine";
    
    // CnC Machine 'Ghost Block'
    public static Block CNCMACHINEGHOST_TILEENTITY;
    private static final String CNCMACHINEGHOST_INTERNALNAME = "ghostCnCMachine";    
    
    // -----------------------------
    // CUSTOM FURNANCES REGISTRY ADD
    // -----------------------------

    @EventHandler
    static void addDNAExtractor(int blockID) throws InvalidFormatException
    {        
        // TODO: Get machine name from loaded file.
        String machineNameInternal = "dnaExtractor";
        String logicClassNamespace = "madscience.tile.DNAExtractorEntity";
        
        // CONTAINERS
        List<MadSlotContainer> machineContainers = new ArrayList<MadSlotContainer>();
        machineContainers.add(new MadSlotContainer(0, MadSlotContainerTypeEnum.INPUT_INGREDIENT1, ForgeDirection.UNKNOWN, ForgeDirection.NORTH, false, true, 9, 32, 18, 18));
        machineContainers.add(new MadSlotContainer(1, MadSlotContainerTypeEnum.INPUT_EMPTYBUCKET, ForgeDirection.UNKNOWN, ForgeDirection.EAST, false, true, 152, 61, 18, 18));
        machineContainers.add(new MadSlotContainer(2, MadSlotContainerTypeEnum.OUTPUT_RESULT1, ForgeDirection.SOUTH, ForgeDirection.UNKNOWN, true, false, 72, 32, 18, 18));
        machineContainers.add(new MadSlotContainer(3, MadSlotContainerTypeEnum.OUTPUT_WASTE, ForgeDirection.UP, ForgeDirection.UNKNOWN, true, false, 105, 32, 18, 18));
        machineContainers.add(new MadSlotContainer(4, MadSlotContainerTypeEnum.OUTPUT_FILLEDBUCKET, ForgeDirection.WEST, ForgeDirection.UNKNOWN, true, false, 152, 36, 18, 18));
        
        // GUI CONTROLS
        List<MadGUIControl> machineGUIControls = new ArrayList<MadGUIControl>();
        machineGUIControls.add(new MadGUIControl(MadGUIControlTypeEnum.TankGauge, 131, 19, 176, 31, 16, 58));
        machineGUIControls.add(new MadGUIControl(MadGUIControlTypeEnum.PowerLevelX, 10, 49, 176, 0, 14, 14));
        machineGUIControls.add(new MadGUIControl(MadGUIControlTypeEnum.CookingProgressY, 32, 31, 176, 14, 31, 17));
        
        // GUI BUTTONS
        List<MadGUIButton> machineGUIButtons = new ArrayList<MadGUIButton>();
        machineGUIButtons.add(new MadGUIButton(0, MadGUIButtonTypeEnum.InvisibleButton, MadGUIButtonClickActionEnum.OpenLink, "button.help" , "http://madsciencemod.com/minecraft-item/dna-extractor/", 166, 4, 9, 32, 6, 5));
        
        // FLUIDS
        List<MadFluid> machineFluid = new ArrayList<MadFluid>();
        machineFluid.add(new MadFluid(MadFluids.LIQUIDDNA_MUTANT_INTERNALNAME, 0, 10000));
        
        // ENERGY
        List<MadEnergy> machineEnergy = new ArrayList<MadEnergy>();
        machineEnergy.add(new MadEnergy(100000, 200, 0));
        
        // SOUNDS
        List<MadSound> machineSound = new ArrayList<MadSound>();
        machineSound.add(new MadSound("Finish.ogg", 2, 1, MadSoundTriggerEnum.WORKEND, MadSoundPlaybackTypeEnum.PLAY));
        machineSound.add(new MadSound("Idle.ogg", 2, 1, MadSoundTriggerEnum.IDLEON, MadSoundPlaybackTypeEnum.PLAY));
        
        // RECIPES
        List<MadRecipe> machineRecipes = new ArrayList<MadRecipe>();
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:needleBat:0:1", null, "", null, "", null, "", null, "",
                MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:dnaBat:0:1", null, "", null, "", null, "", null, "",
                60, 0.50F));
                
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:needleCaveSpider:0:1", null, "", null, "", null, "", null, "",
                 MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:dnaCaveSpider:0:1", null, "", null, "", null, "", null, "",
                 60, 0.35F));
        
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "minecraft:fermentedSpiderEye:0:1", null, "", null, "", null, "", null, "",
                 MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:dnaCaveSpider:0:1", null, "", null, "", null, "", null, "",
                 60, 0.35F));
         
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:needleChicken:0:1", null, "", null, "", null, "", null, "",
                 MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:dnaChicken:0:1", null, "", null, "", null, "", null, "",
                 60, 0.35F));
        
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "minecraft:feather:0:1", null, "", null, "", null, "", null, "",
                 MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:dnaChicken:0:1", null, "", null, "", null, "", null, "",
                 60, 0.15F));
        
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "minecraft:egg:0:1", null, "", null, "", null, "", null, "",
                 MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:dnaChicken:0:1", null, "", null, "", null, "", null, "",
                 60, 0.15F));
        
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "minecraft:chickenCooked:0:1", null, "", null, "", null, "", null, "",
                 MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:dnaChicken:0:1", null, "", null, "", null, "", null, "",
                 60, 0.15F));
        
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "minecraft:chickenRaw:0:1", null, "", null, "", null, "", null, "",
                 MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:dnaChicken:0:1", null, "", null, "", null, "", null, "",
                 60, 0.15F));
         
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:needleCow:0:1", null, "", null, "", null, "", null, "",
                 MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:dnaCow:0:1", null, "", null, "", null, "", null, "",
                 60, 0.30F));
        
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "minecraft:leather:0:1", null, "", null, "", null, "", null, "",
                 MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:dnaCow:0:1", null, "", null, "", null, "", null, "",
                 60, 0.30F));
        
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "minecraft:beefCooked:0:1", null, "", null, "", null, "", null, "",
                 MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:dnaCow:0:1", null, "", null, "", null, "", null, "",
                 60, 0.30F));
        
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "minecraft:beefRaw:0:1", null, "", null, "", null, "", null, "",
                 MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:dnaCow:0:1", null, "", null, "", null, "", null, "",
                 60, 0.30F));
        
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "minecraft:bootsCloth:0:1", null, "", null, "", null, "", null, "",
                 MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:dnaCow:0:1", null, "", null, "", null, "", null, "",
                 60, 0.30F));
        
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "minecraft:helmetCloth:0:1", null, "", null, "", null, "", null, "",
                 MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:dnaCow:0:1", null, "", null, "", null, "", null, "",
                 60, 0.30F));
        
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "minecraft:leggingsCloth:0:1", null, "", null, "", null, "", null, "",
                 MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:dnaCow:0:1", null, "", null, "", null, "", null, "",
                 60, 0.30F));
        
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "minecraft:chestplateCloth:0:1", null, "", null, "", null, "", null, "",
                 MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:dnaCow:0:1", null, "", null, "", null, "", null, "",
                 60, 0.30F));
        
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "minecraft:milk:0:1", null, "", null, "", null, "", null, "",
                 MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:dnaCow:0:1", null, "", null, "", null, "", null, "",
                 60, 0.30F));   
         
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:needleCreeper:0:1", null, "", null, "", null, "", null, "",
                 MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:dnaCreeper:0:1", null, "", null, "", null, "", null, "",
                 60, 0.20F));
        
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "minecraft:sulphur:0:1", null, "", null, "", null, "", null, "",
                 MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:dnaCreeper:0:1", null, "", null, "", null, "", null, "",
                 60, 0.22F));
        
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "minecraft:skull:4:1", null, "", null, "", null, "", null, "",
                 MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:dnaCreeper:0:1", null, "", null, "", null, "", null, "",
                 60, 0.22F));
         
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:needleEnderman:0:1", null, "", null, "", null, "", null, "",
                 MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:dnaEnderman:0:1", null, "", null, "", null, "", null, "",
                 60, 0.42F));
        
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "minecraft:enderPearl:0:1", null, "", null, "", null, "", null, "",
                 MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:dnaEnderman:0:1", null, "", null, "", null, "", null, "",
                 60, 0.42F));
        
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "minecraft:eyeOfEnder:0:1", null, "", null, "", null, "", null, "",
                 MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:dnaEnderman:0:1", null, "", null, "", null, "", null, "",
                 60, 0.42F));
         
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "minecraft:ghastTear:0:1", null, "", null, "", null, "", null, "",
                 MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:dnaGhast:0:1", null, "", null, "", null, "", null, "",
                 60, 0.42F));
         
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:needleHorse:0:1", null, "", null, "", null, "", null, "",
                 MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:dnaHorse:0:1", null, "", null, "", null, "", null, "",
                 60, 0.42F));
         
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:needleMushroomCow:0:1", null, "", null, "", null, "", null, "",
                 MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:dnaMushroomCow:0:1", null, "", null, "", null, "", null, "",
                 60, 0.42F));

        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:needleOcelot:0:1", null, "", null, "", null, "", null, "",
                 MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:dnaOcelot:0:1", null, "", null, "", null, "", null, "",
                 60, 0.42F));
         
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:needlePig:0:1", null, "", null, "", null, "", null, "",
                 MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:dnaPig:0:1", null, "", null, "", null, "", null, "",
                 60, 0.35F));
        
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "minecraft:porkchopRaw:0:1", null, "", null, "", null, "", null, "",
                 MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:dnaPig:0:1", null, "", null, "", null, "", null, "",
                 60, 0.35F));
        
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "minecraft:porkchopCooked:0:1", null, "", null, "", null, "", null, "",
                 MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:dnaPig:0:1", null, "", null, "", null, "", null, "",
                 60, 0.35F));
         
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:needleSheep:0:1", null, "", null, "", null, "", null, "",
                 MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:dnaSheep:0:1", null, "", null, "", null, "", null, "",
                 60, 0.42F));
        
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "minecraft:wool:0:1", null, "", null, "", null, "", null, "",
                 MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:dnaSheep:0:1", null, "", null, "", null, "", null, "",
                 60, 0.42F));
         
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "minecraft:bone:0:1", null, "", null, "", null, "", null, "",
                 MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:dnaSkeleton:0:1", null, "", null, "", null, "", null, "",
                 60, 0.42F));
        
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "minecraft:dyePowder:15:1", null, "", null, "", null, "", null, "",
                 MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:dnaSkeleton:0:1", null, "", null, "", null, "", null, "",
                 60, 0.42F));
        
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "minecraft:skull:0:1", null, "", null, "", null, "", null, "",
                 MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:dnaSkeleton:0:1", null, "", null, "", null, "", null, "",
                 60, 0.42F));
         
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "minecraft:slimeball:0:1", null, "", null, "", null, "", null, "",
                 MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:dnaSlime:0:1", null, "", null, "", null, "", null, "",
                 60, 0.50F));
        
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "minecraft:pistonStickyBase:0:1", null, "", null, "", null, "", null, "",
                 MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:dnaSlime:0:1", null, "", null, "", null, "", null, "",
                 60, 0.50F));
         
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:needleSpider:0:1", null, "", null, "", null, "", null, "",
                 MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:dnaSpider:0:1", null, "", null, "", null, "", null, "",
                 60, 0.66F));
        
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "minecraft:spiderEye:0:1", null, "", null, "", null, "", null, "",
                 MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:dnaSpider:0:1", null, "", null, "", null, "", null, "",
                 60, 0.66F));
        
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "minecraft:string:0:1", null, "", null, "", null, "", null, "",
                 MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:dnaSpider:0:1", null, "", null, "", null, "", null, "",
                 60, 0.66F));
         
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:needleSquid:0:1", null, "", null, "", null, "", null, "",
                 MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:dnaSquid:0:1", null, "", null, "", null, "", null, "",
                 60, 0.42F));
        
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "minecraft:dyePowder:0:1", null, "", null, "", null, "", null, "",
                 MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:dnaSquid:0:1", null, "", null, "", null, "", null, "",
                 60, 0.42F));
         
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:needleVillager:0:1", null, "", null, "", null, "", null, "",
                 MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:dnaVillager:0:1", null, "", null, "", null, "", null, "",
                 60, 0.35F));
         
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:needleWitch:0:1", null, "", null, "", null, "", null, "",
                 MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:dnaWitch:0:1", null, "", null, "", null, "", null, "",
                 60, 0.66F));
         
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:needleWolf:0:1", null, "", null, "", null, "", null, "",
                 MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:dnaWolf:0:1", null, "", null, "", null, "", null, "",
                 60, 0.42F));
         
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:needleZombie:0:1", null, "", null, "", null, "", null, "",
                 MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:dnaZombie:0:1", null, "", null, "", null, "", null, "",
                 60, 0.35F));
        
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "minecraft:skull:2:1", null, "", null, "", null, "", null, "",
                 MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:dnaZombie:0:1", null, "", null, "", null, "", null, "",
                 60, 0.35F));
        
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "minecraft:rottenFlesh:0:1", null, "", null, "", null, "", null, "",
                 MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:dnaZombie:0:1", null, "", null, "", null, "", null, "",
                 60, 0.35F));
        
        // Register machine with the registry so we can generate all needed MC/Forge data.
        boolean addResult = false;
        addResult = MadTileEntityFactory.registerMachine(
                machineNameInternal,
                blockID,
                logicClassNamespace, // TODO: Get logic class from loaded file.
                machineContainers.toArray(new MadSlotContainer[]{}),
                machineGUIControls.toArray(new MadGUIControl[]{}),
                machineGUIButtons.toArray(new MadGUIButton[]{}),
                machineFluid.toArray(new MadFluid[]{}),
                machineEnergy.toArray(new MadEnergy[]{}),
                machineSound.toArray(new MadSound[]{}),
                machineRecipes.toArray(new MadRecipe[]{}));
        
        // Check the result!
        if (!addResult)
        {
            throw new InvalidFormatException("Unable to register tile entity '" + machineNameInternal + "'");
        }
        
        // Grab our factory product from the tile entity factory.
        MadTileEntityFactoryProduct registeredMachine = MadTileEntityFactory.getMachineInfo(machineNameInternal);
        
        // Check the result!
        if (registeredMachine == null)
        {
            throw new InvalidFormatException("Unable to retrieve recently registered tile entity '" + machineNameInternal + "'");
        }

        // Shaped Recipe.
        GameRegistry.addRecipe(new ItemStack(registeredMachine.getBlockContainer(), 1), new Object[]
        { "414",
          "424",
          "434",

        '1', new ItemStack(MadCircuits.CIRCUIT_ENDEREYE),
        '2', new ItemStack(MadCircuits.CIRCUIT_SPIDEREYE),
        '3', new ItemStack(MadComponents.COMPONENT_COMPUTER),
        '4', new ItemStack(MadComponents.COMPONENT_CASE), });
    }
    
    @EventHandler
    static void createCryoFreezerTileEntity(int blockID)
    {
        // Cryogenic Freezer
        MadScience.logger.info("-Cryogenic Freezer Tile Entity");
        CRYOFREEZER_TILEENTITY = (BlockContainer) new CryofreezerBlock(blockID).setUnlocalizedName(CRYOFREEZER_INTERNALNAME);

        // Register the block with the world (so we can then tie it to a tile
        // entity).
        GameRegistry.registerBlock(CRYOFREEZER_TILEENTITY, ItemBlockTooltip.class, MadScience.ID + CRYOFREEZER_INTERNALNAME);

        // Register the tile-entity with the game world.
        GameRegistry.registerTileEntity(CryofreezerEntity.class, CRYOFREEZER_INTERNALNAME);

        // Register our rendering handles on clients and ignore them on servers.
        MadScience.proxy.registerRenderingHandler(blockID);

        // Shaped Recipe
        GameRegistry.addRecipe(new ItemStack(CRYOFREEZER_TILEENTITY, 1), new Object[]
        { "131",
          "121",
          "141",

        '1', new ItemStack(MadComponents.COMPONENT_CASE),
        '2', new ItemStack(MadComponents.COMPONENT_COMPUTER),
        '3', new ItemStack(MadCircuits.CIRCUIT_COMPARATOR),
        '4', new ItemStack(MadComponents.COMPONENT_FAN), });
    }

    static void createCryotubeGhostTileEntity(int blockID)
    {
        // Acts as a collision box for upper two blocks of cryotube.
        MadScience.logger.info("-Cryogenic Tube Ghost Block");
        CRYOTUBEGHOST = new CryotubeBlockGhost(blockID).setUnlocalizedName(CRYOTUBEGHOST_INTERNALNAME);
        GameRegistry.registerBlock(CRYOTUBEGHOST, MadScience.ID + CRYOTUBEGHOST_INTERNALNAME);
    }

    static void createCryotubeTileEntity(int blockID)
    {
        // Converts both a villagers brain activity and body heat into a renewable energy source.
        MadScience.logger.info("-Cryogenic Tube Tile Entity");
        CRYOTUBE_TILEENTITY = (BlockContainer) new CryotubeBlock(blockID).setUnlocalizedName(CRYOTUBE_INTERNALNAME);
        GameRegistry.registerBlock(CRYOTUBE_TILEENTITY, ItemBlockTooltip.class, MadScience.ID + CRYOTUBE_INTERNALNAME);
        GameRegistry.registerTileEntity(CryotubeEntity.class, CRYOTUBE_INTERNALNAME);

        // Register our rendering handles on clients and ignore them on servers.
        MadScience.proxy.registerRenderingHandler(blockID);

        // Shaped Recipe
        GameRegistry.addRecipe(new ItemStack(CRYOTUBE_TILEENTITY, 1), new Object[]
        { "121",
          "131",
          "141",

        '1', Block.blockIron,
        '2', new ItemStack(MadCircuits.CIRCUIT_ENDEREYE),
        '3', new ItemStack(MadComponents.COMPONENT_COMPUTER),
        '4', new ItemStack(MadComponents.COMPONENT_POWERSUPPLY), });
    }

    static void createDataReelDuplicatorTileEntity(int blockID)
    {
        // Copies data reels for memories and genomes alike.
        MadScience.logger.info("-Data Reel Duplicator Tile Entity");
        DATADUPLICATOR_TILEENTITY = (BlockContainer) new DataDuplicatorBlock(blockID).setUnlocalizedName(DATADUPLICATOR_INTERNALNAME);
        GameRegistry.registerBlock(DATADUPLICATOR_TILEENTITY, ItemBlockTooltip.class, MadScience.ID + DATADUPLICATOR_INTERNALNAME);
        GameRegistry.registerTileEntity(DataDuplicatorEntity.class, DATADUPLICATOR_INTERNALNAME);

        // Register our rendering handles on clients and ignore them on servers.
        MadScience.proxy.registerRenderingHandler(blockID);

        // Shaped Recipe for Data Reel Duplicator.
        GameRegistry.addRecipe(new ItemStack(MadFurnaces.DATADUPLICATOR_TILEENTITY, 1), new Object[]
        { "161",
          "232",
          "454",

        '1', new ItemStack(MadEntities.DATAREEL_EMPTY, 1),
        '2', new ItemStack(MadComponents.COMPONENT_CASE),
        '3', new ItemStack(Item.redstoneRepeater, 1),
        '4', new ItemStack(MadCircuits.CIRCUIT_SPIDEREYE),
        '5', new ItemStack(MadComponents.COMPONENT_POWERSUPPLY),
        '6', new ItemStack(MadComponents.COMPONENT_FAN) });
    }

    @EventHandler
    static void createGeneIncubatorTileEntity(int blockID)
    {
        // Genome Incubator
        MadScience.logger.info("-Genome Incubator Tile Entity");
        INCUBATOR_TILEENTITY = (BlockContainer) new IncubatorBlock(blockID).setUnlocalizedName(INCUBATOR_INTERNALNAME);

        // Register the block with the world (so we can then tie it to a tile entity).
        GameRegistry.registerBlock(INCUBATOR_TILEENTITY, ItemBlockTooltip.class, MadScience.ID + INCUBATOR_INTERNALNAME);

        // Register the tile-entity with the game world.
        GameRegistry.registerTileEntity(IncubatorEntity.class, INCUBATOR_INTERNALNAME);

        // Register our rendering handles on clients and ignore them on servers.
        MadScience.proxy.registerRenderingHandler(blockID);

        // Shaped Recipe
        GameRegistry.addRecipe(new ItemStack(INCUBATOR_TILEENTITY, 1), new Object[]
        { "656",
          "142",
          "636",

        '1', new ItemStack(MadCircuits.CIRCUIT_GLOWSTONE),
        '2', new ItemStack(MadCircuits.CIRCUIT_COMPARATOR),
        '3', new ItemStack(MadComponents.COMPONENT_POWERSUPPLY), 
        '4', new ItemStack(MadComponents.COMPONENT_COMPUTER), 
        '5', new ItemStack(MadComponents.COMPONENT_FAN), 
        '6', new ItemStack(MadComponents.COMPONENT_CASE), });
    }

    @EventHandler
    static void createGeneSequencerTileEntity(int blockID)
    {
        // Genetic Sequencer
        MadScience.logger.info("-Genetic Sequencer Tile Entity");
        SEQUENCER_TILEENTITY = (BlockContainer) new SequencerBlock(blockID).setUnlocalizedName(SEQUENCER_INTERNALNAME);

        // Register the block with the world (so we can then tie it to a tile
        // entity).
        GameRegistry.registerBlock(SEQUENCER_TILEENTITY, ItemBlockTooltip.class, MadScience.ID + SEQUENCER_INTERNALNAME);

        // Register the tile-entity with the game world.
        GameRegistry.registerTileEntity(SequencerEntity.class, SEQUENCER_INTERNALNAME);

        // Register our rendering handles on clients and ignore them on servers.
        MadScience.proxy.registerRenderingHandler(blockID);

        // Shaped Recipe
        GameRegistry.addRecipe(new ItemStack(SEQUENCER_TILEENTITY, 1), new Object[]
        { "172",
          "858",
          "364",

        '1', new ItemStack(MadCircuits.CIRCUIT_EMERALD),
        '2', new ItemStack(MadCircuits.CIRCUIT_COMPARATOR),
        '3', new ItemStack(MadCircuits.CIRCUIT_DIAMOND),
        '4', new ItemStack(MadCircuits.CIRCUIT_ENDEREYE),
        '5', new ItemStack(MadComponents.COMPONENT_COMPUTER), 
        '6', new ItemStack(MadComponents.COMPONENT_POWERSUPPLY), 
        '7', new ItemStack(MadComponents.COMPONENT_FAN), 
        '8', new ItemStack(MadComponents.COMPONENT_CASE), });
    }

    @EventHandler
    static void createMainframeTileEntity(int blockID)
    {
        // Populate our static instance.
        MadScience.logger.info("-Computer Mainframe Tile Entity");
        MAINFRAME_TILEENTITY = (BlockContainer) new MainframeBlock(blockID).setUnlocalizedName(MAINFRAME_INTERNALNAME);

        // Register the block with the world (so we can then tie it to a tile
        // entity).
        GameRegistry.registerBlock(MAINFRAME_TILEENTITY, ItemBlockTooltip.class, MadScience.ID + MAINFRAME_INTERNALNAME);

        // Register the tile-entity with the game world.
        GameRegistry.registerTileEntity(MainframeEntity.class, MAINFRAME_INTERNALNAME);

        // Register our rendering handles on clients and ignore them on servers.
        MadScience.proxy.registerRenderingHandler(blockID);

        // Shaped Recipe
        GameRegistry.addRecipe(new ItemStack(MAINFRAME_TILEENTITY, 1), new Object[]
        { "111",
          "121", 
          "111",

        '1', new ItemStack(MadComponents.COMPONENT_COMPUTER),
        '2', new ItemStack(MadComponents.COMPONENT_CASE), });
    }

    @EventHandler
    static void createMeatcubeTileEntity(int blockID, int metaID, int primaryColor, int secondaryColor, int cookTime)
    {
        // Disgusting meat cube that spawns chicken, cow and pig meat when hit.
        MadScience.logger.info("-Disgusting Meat Cube Tile Entity");
        MEATCUBE_TILEENTITY = (BlockContainer) new MeatcubeBlock(blockID).setUnlocalizedName(MEATCUBE_INTERNALNAME);
        GameRegistry.registerBlock(MEATCUBE_TILEENTITY, ItemBlockTooltip.class, MadScience.ID + MEATCUBE_INTERNALNAME);
        GameRegistry.registerTileEntity(MeatcubeEntity.class, MEATCUBE_INTERNALNAME);

        // Register our rendering handles on clients and ignore them on servers.
        MadScience.proxy.registerRenderingHandler(blockID);

        // Add mob to combined genome entity list so it can be created by other
        MadGenomeRegistry.registerGenome(new MadGenomeInfo((short) metaID, MEATCUBE_INTERNALNAME, primaryColor, secondaryColor));

        // Create meatcube with slime and pig, cow or chicken genomes!
        MainframeRecipes.addRecipe(new ItemStack(MadGenomes.GENOME_SLIME), new ItemStack(MadGenomes.GENOME_COW), new ItemStack(MadEntities.COMBINEDGENOME_MONSTERPLACER, 1, metaID), cookTime);
        MainframeRecipes.addRecipe(new ItemStack(MadGenomes.GENOME_SLIME), new ItemStack(MadGenomes.GENOME_PIG), new ItemStack(MadEntities.COMBINEDGENOME_MONSTERPLACER, 1, metaID), cookTime);
        MainframeRecipes.addRecipe(new ItemStack(MadGenomes.GENOME_SLIME), new ItemStack(MadGenomes.GENOME_CHICKEN), new ItemStack(MadEntities.COMBINEDGENOME_MONSTERPLACER, 1, metaID), cookTime);

        // Now we need to bake our meatcube in the oven until golden brown.
        IncubatorRecipes.addSmelting(MadEntities.COMBINEDGENOME_MONSTERPLACER.itemID, metaID, new ItemStack(MEATCUBE_TILEENTITY, 1));
    }

    @EventHandler
    static void createSanitizerTileEntity(int blockID)
    {
        MadScience.logger.info("-Needle Sanitizer Tile Entity");
        
        // Populate our static instance.
        SANTITIZER_TILEENTITY = (BlockContainer) new SanitizerBlock(blockID).setUnlocalizedName(SANTITIZER_INTERNALNAME);

        // Register the block with the world (so we can then tie it to a tile
        // entity).
        GameRegistry.registerBlock(SANTITIZER_TILEENTITY, ItemBlockTooltip.class, MadScience.ID + SANTITIZER_INTERNALNAME);

        // Register the tile-entity with the game world.
        GameRegistry.registerTileEntity(SanitizerEntity.class, SANTITIZER_INTERNALNAME);

        // Register our rendering handles on clients and ignore them on servers.
        MadScience.proxy.registerRenderingHandler(blockID);

        // Shaped Recipe
        GameRegistry.addRecipe(new ItemStack(SANTITIZER_TILEENTITY, 1), new Object[]
        { "545", 
          "535", 
          "126",

        '1', new ItemStack(MadCircuits.CIRCUIT_GLOWSTONE),
        '2', new ItemStack(MadCircuits.CIRCUIT_REDSTONE),
        '3', new ItemStack(MadComponents.COMPONENT_POWERSUPPLY), 
        '4', new ItemStack(MadComponents.COMPONENT_FAN), 
        '5', new ItemStack(MadComponents.COMPONENT_CASE), 
        '6', new ItemStack(MadCircuits.CIRCUIT_ENDERPEARL), });
    }

    static void createSoniclocatorGhostTileEntity(int blockID)
    {
        MadScience.logger.info("-Soniclocator Ghost Block");
        
        // Acts as a collision box for upper two blocks of Soniclocator device.
        SONICLOCATORGHOST = new SoniclocatorBlockGhost(blockID).setUnlocalizedName(SONICLOCATORGHOST_INTERNALNAME);
        GameRegistry.registerBlock(SONICLOCATORGHOST, MadScience.ID + SONICLOCATORGHOST_INTERNALNAME);
    }

    static void createSoniclocatorTileEntity(int blockID)
    {
        MadScience.logger.info("-Soniclocator Tile Entity");
        
        // Transposes block types in exchange for others using sonic waves.
        SONICLOCATOR_TILEENTITY = (BlockContainer) new SoniclocatorBlock(blockID).setUnlocalizedName(SONICLOCATOR_INTERNALNAME);
        GameRegistry.registerBlock(SONICLOCATOR_TILEENTITY, ItemBlockTooltip.class, MadScience.ID + SONICLOCATOR_INTERNALNAME);
        GameRegistry.registerTileEntity(SoniclocatorEntity.class, SONICLOCATOR_INTERNALNAME);

        // Register our rendering handles on clients and ignore them on servers.
        MadScience.proxy.registerRenderingHandler(blockID);

        // Recipe for Soniclocator.
        GameRegistry.addRecipe(new ItemStack(SONICLOCATOR_TILEENTITY, 1), new Object[]
        { "111", 
          "323", 
          "545",

        '1', new ItemStack(MadComponents.COMPONENT_THUMPER),
        '2', new ItemStack(MadComponents.COMPONENT_SCREEN),
        '3', new ItemStack(MadComponents.COMPONENT_COMPUTER),
        '4', new ItemStack(MadComponents.COMPONENT_POWERSUPPLY),
        '5', new ItemStack(MadCircuits.CIRCUIT_ENDEREYE),
        });
    }

    static void createThermosonicBonderTileEntity(int blockID)
    {
        MadScience.logger.info("-Thermosonic Bonder Tile Entity");
        
        // Creates silicon wafers, transistors, CPU's, and RAM chips.
        THERMOSONIC_TILEENTITY = (BlockContainer) new ThermosonicBonderBlock(blockID).setUnlocalizedName(THERMOSONIC_INTERNALNAME);
        GameRegistry.registerBlock(THERMOSONIC_TILEENTITY, ItemBlockTooltip.class, MadScience.ID + THERMOSONIC_INTERNALNAME);
        GameRegistry.registerTileEntity(ThermosonicBonderEntity.class, THERMOSONIC_INTERNALNAME);

        // Register our rendering handles on clients and ignore them on servers.
        MadScience.proxy.registerRenderingHandler(blockID);
        
        // Grab the final sacrifice block from our configuration file.
        ItemStack finalSacrifice = null;
        try
        {
            finalSacrifice = new ItemStack(MadConfig.THERMOSONICBONDER_FINALSACRIFICE, 1, 0);
        }
        catch (Exception err)
        {
            MadScience.logger.info("Attempted to load a final sacrifice ID for a block that does not exist, learn to config file better user!");
            MadScience.logger.info("Setting Thermosonic Bonder final sacrifice item back to a beacon just to spite you!");
            finalSacrifice = new ItemStack(Block.beacon);
        }

        // Shaped Recipe for Thermosonic Bonder Tile Entity
        GameRegistry.addRecipe(new ItemStack(MadFurnaces.THERMOSONIC_TILEENTITY, 1), new Object[]
        { "343", 
          "353", 
          "121",

        '1', Block.glowStone,
        '2', finalSacrifice,
        '3', Block.blockIron,
        '4', Block.blockRedstone,
        '5', Block.blockDiamond });

        // 1x Fused Quartz = 1x Silicon Wafer.
        ThermosonicBonderRecipes.addSmelting(MadComponents.COMPONENT_FUSEDQUARTZ.itemID,
                                             new ItemStack(MadComponents.COMPONENT_SILICONWAFER));

        // 1x Silicon Wafer = 16x Transistors.
        ThermosonicBonderRecipes.addSmelting(MadComponents.COMPONENT_SILICONWAFER.itemID,
                                             new ItemStack(MadComponents.COMPONENT_TRANSISTOR, 16));

        // 1x Redstone Circuit = 1x CPU.
        ThermosonicBonderRecipes.addSmelting(MadCircuits.CIRCUIT_REDSTONE.itemID,
                                             new ItemStack(MadComponents.COMPONENT_CPU));

        // 1x Glowstone Circuit 1x RAM.
        ThermosonicBonderRecipes.addSmelting(MadCircuits.CIRCUIT_GLOWSTONE.itemID,
                                             new ItemStack(MadComponents.COMPONENT_RAM));
    }

    static void createClayFurnaceTileEntity(int blockID)
    {
        MadScience.logger.info("-Clay Furnace Tile Entity");
        
        // A early-game block that can give huge return on investment for ores for clay and fire and time.
        CLAYFURNACE_TILEENTITY = (BlockContainer) new ClayfurnaceBlock(blockID).setUnlocalizedName(CLAYFURNACE_INTERNALNAME);
        GameRegistry.registerBlock(CLAYFURNACE_TILEENTITY, ItemBlockTooltip.class, MadScience.ID + CLAYFURNACE_INTERNALNAME);
        GameRegistry.registerTileEntity(ClayfurnaceEntity.class, CLAYFURNACE_INTERNALNAME);

        // Register our rendering handles on clients and ignore them on servers.
        MadScience.proxy.registerRenderingHandler(blockID);
        
        // Wrapping hardened clay blocks around a furnace will make a clay furnace.
        GameRegistry.addRecipe(new ItemStack(CLAYFURNACE_TILEENTITY, 1), new Object[]
        { "111", 
          "121", 
          "111",

        '1', new ItemStack(Block.hardenedClay),
        '2', new ItemStack(Block.furnaceIdle)
        });
        
        // Clay Furnace will only convert gold and iron ore into full blocks.
        ClayfurnaceRecipes.addSmelting(Block.oreIron.blockID, new ItemStack(Block.blockIron), 0.15F);
        ClayfurnaceRecipes.addSmelting(Block.oreGold.blockID, new ItemStack(Block.blockGold), 0.15F);
    }

    static void createVOXBoxTileEntity(int blockID)
    {
        MadScience.logger.info("-Announcement Box Tile Entity");
        
        // Automatic Diagnostic and Announcement System
        // AKA: Black Mesa Announcement System
        VOXBOX_TILEENTITY = (BlockContainer) new VoxBoxBlock(blockID).setUnlocalizedName(VOXBOX_INTERNALNAME);
        GameRegistry.registerBlock(VOXBOX_TILEENTITY, ItemBlockTooltip.class, MadScience.ID + VOXBOX_INTERNALNAME);
        GameRegistry.registerTileEntity(VoxBoxEntity.class, VOXBOX_INTERNALNAME);

        // Register our rendering handles on clients and ignore them on servers.
        MadScience.proxy.registerRenderingHandler(blockID);
        
        // Recipe for VOX box contains a juke block.
        GameRegistry.addRecipe(new ItemStack(VOXBOX_TILEENTITY, 1), new Object[]
        { "121", 
          "465", 
          "131",

          '1', new ItemStack(MadComponents.COMPONENT_CASE, 1, 0),
          '2', new ItemStack(MadComponents.COMPONENT_COMPUTER, 1, 0),
          '3', new ItemStack(MadComponents.COMPONENT_POWERSUPPLY, 1, 0),
          '4', new ItemStack(MadCircuits.CIRCUIT_SPIDEREYE, 1, 0),
          '5', new ItemStack(MadCircuits.CIRCUIT_ENDEREYE, 1, 0),
          '6', new ItemStack(Block.jukebox, 1, 0),
        });
    }

    static void createMagLoaderTileEntity(int blockID)
    {
        MadScience.logger.info("-Magazine Loader Tile Entity");
        
        // Loads ammunition into pulse rifle magazine at in-human speeds.
        MAGLOADER_TILEENTITY = (BlockContainer) new MagLoaderBlock(blockID).setUnlocalizedName(MAGLOADER_INTERNALNAME);
        GameRegistry.registerBlock(MAGLOADER_TILEENTITY, ItemBlockTooltip.class, MadScience.ID + MAGLOADER_INTERNALNAME);
        GameRegistry.registerTileEntity(MagLoaderEntity.class, MAGLOADER_INTERNALNAME);

        // Register our rendering handles on clients and ignore them on servers.
        MadScience.proxy.registerRenderingHandler(blockID);
        
        // Recipe for Magazine Loader.
        GameRegistry.addRecipe(new ItemStack(MAGLOADER_TILEENTITY, 1), new Object[]
        { " 1 ", 
          " 2 ", 
          "435",

          '1', new ItemStack(Block.hopperBlock, 1, 0),
          '2', new ItemStack(Block.pistonBase, 1, 0),
          '3', new ItemStack(Block.dispenser, 1, 0),
          '4', new ItemStack(MadComponents.COMPONENT_POWERSUPPLY, 1, 0),
          '5', new ItemStack(MadCircuits.CIRCUIT_COMPARATOR, 1, 0)
        });
    }

    static void createMagLoaderGhostTileEntity(int blockID)
    {
        MadScience.logger.info("-Magazine Loader Ghost Block");
        
        // Acts as a collision box for upper blocks of Magazine Loader.
        MAGLOADERGHOST = new MagLoaderBlockGhost(blockID).setUnlocalizedName(MAGLOADERGHOST_INTERNALNAME);
        GameRegistry.registerBlock(MAGLOADERGHOST, MadScience.ID + MAGLOADERGHOST_INTERNALNAME);
    }

    static void createCnCMachineTileEntity(int blockID)
    {
        MadScience.logger.info("-Cnc Machine Tile Entity");
        
        // Cuts out blocks of Iron into shapes for gun parts using binary codes in written books.
        CNCMACHINE_TILEENTITY = (BlockContainer) new CnCMachineBlock(blockID).setUnlocalizedName(CNCMACHINE_INTERNALNAME);
        GameRegistry.registerBlock(CNCMACHINE_TILEENTITY, ItemBlockTooltip.class, MadScience.ID + CNCMACHINE_INTERNALNAME);
        GameRegistry.registerTileEntity(CnCMachineEntity.class, CNCMACHINE_INTERNALNAME);

        // Register our rendering handles on clients and ignore them on servers.
        MadScience.proxy.registerRenderingHandler(blockID);
        
        // Recipe for CnC machine.
        GameRegistry.addRecipe(new ItemStack(CNCMACHINE_TILEENTITY, 1), new Object[]
        { "456", 
          "212", 
          "232",

          '1', new ItemStack(Block.sand, 64, 0),
          '2', new ItemStack(Block.obsidian, 1, 0),
          '3', new ItemStack(MadComponents.COMPONENT_POWERSUPPLY, 1, 0),
          '4', new ItemStack(MadCircuits.CIRCUIT_EMERALD, 1, 0),
          '5', new ItemStack(Block.pistonBase, 1, 0),
          '6', new ItemStack(MadComponents.COMPONENT_CPU, 1, 0),
        });
        
        // Machine recipes for converting iron blocks into weapon parts from binary code in written book.
        CnCMachineRecipes.addSmeltingResult("m41a barrel", new ItemStack(MadComponents.COMPONENT_PULSERIFLEBARREL), 0.42F);
        CnCMachineRecipes.addSmeltingResult("m41a bolt", new ItemStack(MadComponents.COMPONENT_PULSERIFLEBOLT), 0.42F);
        CnCMachineRecipes.addSmeltingResult("m41a reciever", new ItemStack(MadComponents.COMPONENT_PULSERIFLERECIEVER), 0.42F);
        CnCMachineRecipes.addSmeltingResult("m41a trigger", new ItemStack(MadComponents.COMPONENT_PULSERIFLETRIGGER), 0.42F);
        CnCMachineRecipes.addSmeltingResult("m41a magazine", new ItemStack(MadWeapons.WEAPONITEM_MAGAZINEITEM, 16), 0.42F);
        CnCMachineRecipes.addSmeltingResult("m41a bullets", new ItemStack(MadComponents.COMPONENT_PULSERIFLEBULLETCASING, 64), 0.42F);
        CnCMachineRecipes.addSmeltingResult("m41a grenade", new ItemStack(MadComponents.COMPONENT_PULSERIFLEGRENADECASING, 32), 0.42F);
    }

    static void createCnCMachineGhostTileEntity(int blockID)
    {
        MadScience.logger.info("-CnC Machine Ghost Block");
        
        // Acts as a collision box for upper blocks of CnC Machine.
        CNCMACHINEGHOST_TILEENTITY = (Block) new CnCMachineBlockGhost(blockID).setUnlocalizedName(CNCMACHINEGHOST_INTERNALNAME);
        GameRegistry.registerBlock(CNCMACHINEGHOST_TILEENTITY, MadScience.ID + CNCMACHINEGHOST_INTERNALNAME);
    }
}

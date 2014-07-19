package madscience;

import java.util.ArrayList;
import java.util.List;

import madscience.factory.MadTileEntityFactoryProductData;
import madscience.factory.buttons.MadGUIButton;
import madscience.factory.buttons.MadGUIButtonClickActionEnum;
import madscience.factory.buttons.MadGUIButtonTypeEnum;
import madscience.factory.controls.MadGUIControl;
import madscience.factory.controls.MadGUIControlTypeEnum;
import madscience.factory.crafting.MadCraftingRecipe;
import madscience.factory.crafting.MadCraftingRecipeTypeEnum;
import madscience.factory.energy.MadEnergy;
import madscience.factory.fluids.MadFluid;
import madscience.factory.heat.MadHeat;
import madscience.factory.model.MadModel;
import madscience.factory.model.MadModelFile;
import madscience.factory.recipes.MadRecipe;
import madscience.factory.slotcontainers.MadSlotContainer;
import madscience.factory.slotcontainers.MadSlotContainerTypeEnum;
import madscience.factory.sounds.MadSound;
import madscience.factory.sounds.MadSoundPlaybackTypeEnum;
import madscience.factory.sounds.MadSoundTriggerEnum;
import net.minecraftforge.common.ForgeDirection;

public class MadManualMachine
{
    /** Called right before configuration with ID manager occurs, allows machines to be manually inserted via code by developer. */
    public static MadTileEntityFactoryProductData getMachine()
    {
//        // NAME
//        String machineNameInternal = "thermosonicBonder";
//
//        // LOGIC
//        String logicClassNamespace = "madscience.tile.ThermosonicBonderEntity";
//        
//        // CONTAINERS
//        List<MadSlotContainer> machineContainers = new ArrayList<MadSlotContainer>();
//        machineContainers.add(new MadSlotContainer(0, MadSlotContainerTypeEnum.INPUT_INGREDIENT1, ForgeDirection.UNKNOWN, ForgeDirection.NORTH, false, true, 37, 39, 18, 18));
//        machineContainers.add(new MadSlotContainer(1, MadSlotContainerTypeEnum.INPUT_INGREDIENT2, ForgeDirection.UNKNOWN, ForgeDirection.EAST, false, true, 69, 39, 18, 18));
//        machineContainers.add(new MadSlotContainer(2, MadSlotContainerTypeEnum.OUTPUT_RESULT1, ForgeDirection.SOUTH, ForgeDirection.UNKNOWN, true, false, 140, 39, 18, 18));
//
//        // GUI CONTROLS
//        List<MadGUIControl> machineGUIControls = new ArrayList<MadGUIControl>();
//        machineGUIControls.add(new MadGUIControl(MadGUIControlTypeEnum.HeatGauge, 13, 15, 176, 31, 18, 40));
//        machineGUIControls.add(new MadGUIControl(MadGUIControlTypeEnum.PowerLevelX, 15, 57, 176, 0, 14, 14));
//        machineGUIControls.add(new MadGUIControl(MadGUIControlTypeEnum.CookingProgressY, 93, 38, 176, 14, 36, 17));
//
//        // GUI BUTTONS
//        List<MadGUIButton> machineGUIButtons = new ArrayList<MadGUIButton>();
//        machineGUIButtons.add(new MadGUIButton(0, MadGUIButtonTypeEnum.InvisibleButton, MadGUIButtonClickActionEnum.OpenLink, "button.help" , "http://madsciencemod.com/minecraft-item/thermosonic-bonder/", 166, 4, 9, 32, 6, 5));
//
//        // FLUIDS
//        List<MadFluid> machineFluid = new ArrayList<MadFluid>();
//        //machineFluid.add(new MadFluid("water", 0, 10000));
//
//        // ENERGY
//        List<MadEnergy> machineEnergy = new ArrayList<MadEnergy>();
//        machineEnergy.add(new MadEnergy(100000, 200, 0, 1, 0));
//        
//        // HEAT
//        List<MadHeat> machineHeat = new ArrayList<MadHeat>();
//        machineHeat.add(new MadHeat(0, 780, 1000));
//
//        // SOUNDS
//        List<MadSound> machineSound = new ArrayList<MadSound>();
//        machineSound.add(new MadSound("Idle.ogg", 1, 3, MadSoundTriggerEnum.IDLEON, MadSoundPlaybackTypeEnum.LOOP));
//        machineSound.add(new MadSound("LaserStart.ogg", 2, 1, MadSoundTriggerEnum.WORKSTART, MadSoundPlaybackTypeEnum.PLAY));
//        machineSound.add(new MadSound("LaserStop.ogg", 1, 1, MadSoundTriggerEnum.WORKEND, MadSoundPlaybackTypeEnum.PLAY));
//        machineSound.add(new MadSound("LaserWorking.ogg", 1, 2, MadSoundTriggerEnum.WORKING, MadSoundPlaybackTypeEnum.LOOP));
//        machineSound.add(new MadSound("Stamp.ogg", 1, 10, MadSoundTriggerEnum.WORKING, MadSoundPlaybackTypeEnum.RANDOM));
//
//        // RECIPES
//        List<MadRecipe> machineRecipes = new ArrayList<MadRecipe>();
//        machineRecipes.add(new MadRecipe(
//                MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:componentFusedQuartz:0:1",
//                MadSlotContainerTypeEnum.INPUT_INGREDIENT2, "minecraft:goldNugget:0:1",
//                null, "",
//                null, "",
//                null, "",
//                MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:componentSiliconWafer:0:1",
//                null, "",
//                null, "",
//                null, "",
//                null, "",
//                90,
//                0.10F));
//        machineRecipes.add(new MadRecipe(
//                MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:componentSiliconWafer:0:1",
//                MadSlotContainerTypeEnum.INPUT_INGREDIENT2, "minecraft:goldNugget:0:1",
//                null, "",
//                null, "",
//                null, "",
//                MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:componentTransistor:0:16",
//                null, "",
//                null, "",
//                null, "",
//                null, "",
//                90,
//                0.10F));
//        machineRecipes.add(new MadRecipe(
//                MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:circuitRedstone:0:1",
//                MadSlotContainerTypeEnum.INPUT_INGREDIENT2, "minecraft:goldNugget:0:1",
//                null, "",
//                null, "",
//                null, "",
//                MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:componentCPU:0:1",
//                null, "",
//                null, "",
//                null, "",
//                null, "",
//                90,
//                0.10F));
//        machineRecipes.add(new MadRecipe(
//                MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:circuitGlowstone:0:1",
//                MadSlotContainerTypeEnum.INPUT_INGREDIENT2, "minecraft:goldNugget:0:1",
//                null, "",
//                null, "",
//                null, "",
//                MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:componentRAM:0:1",
//                null, "",
//                null, "",
//                null, "",
//                null, "",
//                90,
//                0.10F));
//                        
//        // CRAFTING RECIPE
//        // Note: Layout of crafting grid is as follows.
//        // 012
//        // 345
//        // 678
//        List<MadCraftingRecipe> machineCraftingRecipes = new ArrayList<MadCraftingRecipe>();
//        machineCraftingRecipes.add(new MadCraftingRecipe(
//                MadCraftingRecipeTypeEnum.SHAPED,
//                1,
//                "0:minecraft:blockIron:0:1",
//                "1:minecraft:blockRedstone:0:1",
//                "2:minecraft:blockIron:0:1",
//                "3:minecraft:blockIron:0:1",
//                "4:minecraft:blockDiamond:0:1",
//                "5:minecraft:blockIron:0:1",
//                "6:minecraft:lightgem:0:1",
//                "7:minecraft:beacon:0:1",
//                "8:minecraft:lightgem:0:1"));
//        
//        // MODELS
//        List<MadModelFile> madchineModels = new ArrayList<MadModelFile>();
//        madchineModels.add(new MadModelFile("/assets/madscience/models/thermosonicBonder/thermosonicBonder.mad"));
//        
//        // MODELS AND TEXTURE
//        MadModel machineModel = new MadModel(madchineModels.toArray(new MadModelFile[]{}), "models/thermosonicBonder/Off.png");
//        
//        // RESULT
//        MadTileEntityFactoryProductData finalMachine = new MadTileEntityFactoryProductData(
//                machineNameInternal,
//                logicClassNamespace,
//                machineContainers.toArray(new MadSlotContainer[]{}),
//                machineGUIControls.toArray(new MadGUIControl[]{}),
//                machineGUIButtons.toArray(new MadGUIButton[]{}),
//                machineFluid.toArray(new MadFluid[]{}),
//                machineEnergy.toArray(new MadEnergy[]{}),
//                machineHeat.toArray(new MadHeat[]{}),
//                machineSound.toArray(new MadSound[]{}),
//                machineRecipes.toArray(new MadRecipe[]{}),
//                machineCraftingRecipes.toArray(new MadCraftingRecipe[]{}),
//                machineModel);
//        
//        return finalMachine;
        return null;
    }
}

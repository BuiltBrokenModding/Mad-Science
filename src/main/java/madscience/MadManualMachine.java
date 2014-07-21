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
//        String machineNameInternal = "computerMainframe";
//
//        // LOGIC
//        String logicClassNamespace = "madscience.tile.MainframeEntity";
//        
//        // CONTAINERS
//        List<MadSlotContainer> machineContainers = new ArrayList<MadSlotContainer>();
//        machineContainers.add(new MadSlotContainer(0, MadSlotContainerTypeEnum.INPUT_FILLEDBUCKET, ForgeDirection.UNKNOWN, ForgeDirection.NORTH, false, true, 27, 27, 18, 18));
//        machineContainers.add(new MadSlotContainer(1, MadSlotContainerTypeEnum.INPUT_INGREDIENT1, ForgeDirection.UNKNOWN, ForgeDirection.EAST, false, true, 73, 17, 18, 18));
//        machineContainers.add(new MadSlotContainer(2, MadSlotContainerTypeEnum.INPUT_INGREDIENT2, ForgeDirection.UNKNOWN, ForgeDirection.SOUTH, false, true, 96, 17, 18, 18));
//        machineContainers.add(new MadSlotContainer(3, MadSlotContainerTypeEnum.INPUT_EXTRA, ForgeDirection.UNKNOWN, ForgeDirection.WEST, false, true, 115, 55, 18, 18));
//        machineContainers.add(new MadSlotContainer(4, MadSlotContainerTypeEnum.OUTPUT_RESULT1, ForgeDirection.NORTH, ForgeDirection.UNKNOWN, true, false, 148, 38, 18, 18));
//        machineContainers.add(new MadSlotContainer(5, MadSlotContainerTypeEnum.OUTPUT_EMPTYBUCKET, ForgeDirection.EAST, ForgeDirection.UNKNOWN, true, false, 27, 7, 18, 18));
//        
//        // GUI CONTROLS
//        List<MadGUIControl> machineGUIControls = new ArrayList<MadGUIControl>();
//        machineGUIControls.add(new MadGUIControl(MadGUIControlTypeEnum.PowerLevelY, 54, 57, 176, 0, 14, 14));
//        machineGUIControls.add(new MadGUIControl(MadGUIControlTypeEnum.CookingProgressX, 78, 34, 176, 112, 65, 20));
//        machineGUIControls.add(new MadGUIControl(MadGUIControlTypeEnum.TankGaugeY, 7, 7, 176, 14, 16, 58));
//        machineGUIControls.add(new MadGUIControl(MadGUIControlTypeEnum.HeatGaugeY, 52, 15, 176, 72, 18, 40));
//
//        // GUI BUTTONS
//        List<MadGUIButton> machineGUIButtons = new ArrayList<MadGUIButton>();
//        machineGUIButtons.add(new MadGUIButton(0, MadGUIButtonTypeEnum.InvisibleButton, MadGUIButtonClickActionEnum.OpenLink, "button.help" , "http://madsciencemod.com/minecraft-item/computer-mainframe/", 166, 4, 9, 32, 6, 5));
//
//        // FLUIDS
//        List<MadFluid> machineFluid = new ArrayList<MadFluid>();
//        machineFluid.add(new MadFluid("water", 0, 10000));
//
//        // ENERGY
//        List<MadEnergy> machineEnergy = new ArrayList<MadEnergy>();
//        machineEnergy.add(new MadEnergy(100000, 200, 0, 1, 0));
//        
//        // HEAT
//        List<MadHeat> machineHeat = new ArrayList<MadHeat>();
//        machineHeat.add(new MadHeat(0, 420, 1000));
//
//        // SOUNDS
//        List<MadSound> machineSound = new ArrayList<MadSound>();
//        machineSound.add(new MadSound("Finish.ogg", 1, 1, MadSoundTriggerEnum.WORKEND, MadSoundPlaybackTypeEnum.PLAY));
//        machineSound.add(new MadSound("Start.ogg", 1, 1, MadSoundTriggerEnum.WORKSTART, MadSoundPlaybackTypeEnum.PLAY));
//        machineSound.add(new MadSound("Work.ogg", 1, 5, MadSoundTriggerEnum.WORKING, MadSoundPlaybackTypeEnum.RANDOM));
//        machineSound.add(new MadSound("Idle.ogg", 1, 1, MadSoundTriggerEnum.IDLEON, MadSoundPlaybackTypeEnum.LOOP));
//        machineSound.add(new MadSound("Overheat.ogg", 1, 1, MadSoundTriggerEnum.OVERHEAT, MadSoundPlaybackTypeEnum.LOOP));
//
//        // RECIPES
//        List<MadRecipe> machineRecipes = new ArrayList<MadRecipe>();
//        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:genomePig:0:1", MadSlotContainerTypeEnum.INPUT_INGREDIENT2, "madscience:genomeZombie:0:1", MadSlotContainerTypeEnum.INPUT_EXTRA, "madscience:dataReelEmpty:0:1", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:genomeMonsterPlacer:0:1", null, "", null, "", null, "", null, "", 66, 0.1F));
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
//                "0:madscience:componentComputer:0:1",
//                "1:madscience:componentComputer:0:1",
//                "2:madscience:componentComputer:0:1",
//                "3:madscience:componentComputer:0:1",
//                "4:madscience:componentCase:0:1",
//                "5:madscience:componentComputer:0:1",
//                "6:madscience:componentComputer:0:1",
//                "7:madscience:componentComputer:0:1",
//                "8:madscience:componentComputer:0:1"));
//        
//        // MODELS
//        List<MadModelFile> madchineModels = new ArrayList<MadModelFile>();
//        madchineModels.add(new MadModelFile("/assets/madscience/models/computerMainframe/computerMainframe.mad"));
//        
//        // MODELS AND TEXTURE
//        MadModel machineModel = new MadModel(madchineModels.toArray(new MadModelFile[]{}), "models/computerMainframe/idle.png");
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

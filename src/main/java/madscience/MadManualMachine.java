package madscience;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.ForgeDirection;
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
import madscience.factory.model.MadModel;
import madscience.factory.model.MadModelFile;
import madscience.factory.recipes.MadRecipe;
import madscience.factory.slotcontainers.MadSlotContainer;
import madscience.factory.slotcontainers.MadSlotContainerTypeEnum;
import madscience.factory.sounds.MadSound;
import madscience.factory.sounds.MadSoundPlaybackTypeEnum;
import madscience.factory.sounds.MadSoundTriggerEnum;

public class MadManualMachine
{
    /** Called right before configuration with ID manager occurs, allows machines to be manually inserted via code by developer. */
    public static MadTileEntityFactoryProductData getMachine()
    {
//        // NAME
//        String machineNameInternal = "needleSanitizer";
//
//        // LOGIC
//        String logicClassNamespace = "madscience.tile.SanitizerEntity";
//        
//        // CONTAINERS
//        List<MadSlotContainer> machineContainers = new ArrayList<MadSlotContainer>();
//        machineContainers.add(new MadSlotContainer(0, MadSlotContainerTypeEnum.INPUT_FILLEDBUCKET, ForgeDirection.UNKNOWN, ForgeDirection.NORTH, false, true, 31, 34, 18, 18));
//        machineContainers.add(new MadSlotContainer(1, MadSlotContainerTypeEnum.INPUT_INGREDIENT1, ForgeDirection.UNKNOWN, ForgeDirection.EAST, false, true, 73, 34, 18, 18));
//        machineContainers.add(new MadSlotContainer(2, MadSlotContainerTypeEnum.OUTPUT_RESULT1, ForgeDirection.SOUTH, ForgeDirection.UNKNOWN, true, false, 134, 34, 18, 18));
//        machineContainers.add(new MadSlotContainer(3, MadSlotContainerTypeEnum.OUTPUT_EMPTYBUCKET, ForgeDirection.UP, ForgeDirection.UNKNOWN, true, false, 31, 9, 18, 18));
//
//        // GUI CONTROLS
//        List<MadGUIControl> machineGUIControls = new ArrayList<MadGUIControl>();
//        machineGUIControls.add(new MadGUIControl(MadGUIControlTypeEnum.TankGauge, 8, 9, 176, 31, 16, 58));
//        machineGUIControls.add(new MadGUIControl(MadGUIControlTypeEnum.PowerLevelX, 74, 52, 176, 0, 14, 14));
//        machineGUIControls.add(new MadGUIControl(MadGUIControlTypeEnum.CookingProgressY, 96, 34, 176, 14, 24, 17));
//
//        // GUI BUTTONS
//        List<MadGUIButton> machineGUIButtons = new ArrayList<MadGUIButton>();
//        machineGUIButtons.add(new MadGUIButton(0, MadGUIButtonTypeEnum.InvisibleButton, MadGUIButtonClickActionEnum.OpenLink, "button.help" , "http://madsciencemod.com/minecraft-item/syringe-sanitizer/", 166, 4, 9, 32, 6, 5));
//
//        // FLUIDS
//        List<MadFluid> machineFluid = new ArrayList<MadFluid>();
//        machineFluid.add(new MadFluid("water", 0, 10000));
//
//        // ENERGY
//        List<MadEnergy> machineEnergy = new ArrayList<MadEnergy>();
//        machineEnergy.add(new MadEnergy(100000, 200, 0, 1, 0));
//
//        // SOUNDS
//        List<MadSound> machineSound = new ArrayList<MadSound>();
//        machineSound.add(new MadSound("Idle.ogg", 3, 1, MadSoundTriggerEnum.WORKING, MadSoundPlaybackTypeEnum.PLAY));
//
//        // RECIPES
//        List<MadRecipe> machineRecipes = new ArrayList<MadRecipe>();
//        machineRecipes.add(new MadRecipe(
//                MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:needleDirty:0:1",
//                null, "",
//                null, "",
//                null, "",
//                null, "",
//                MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:needleEmpty:0:1",
//                null, "",
//                null, "",
//                null, "",
//                null, "",
//                60,
//                0.15F));
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
//                "0:madscience:componentCase:0:1",
//                "1:madscience:componentFan:0:1",
//                "2:madscience:componentCase:0:1",
//                "3:madscience:componentCase:0:1",
//                "4:madscience:componentPowerSupply:0:1",
//                "5:madscience:componentCase:0:1",
//                "6:madscience:circuitGlowstone:0:1",
//                "7:madscience:circuitRedstone:0:1",
//                "8:madscience:circuitEnderPearl:0:1"));
//        
//        // MODELS
//        List<MadModelFile> madchineModels = new ArrayList<MadModelFile>();
//        madchineModels.add(new MadModelFile("/assets/madscience/models/needleSanitizer/needleSanitizer.mad"));
//        
//        // MODELS AND TEXTURE
//        MadModel machineModel = new MadModel(madchineModels.toArray(new MadModelFile[]{}), "models/needleSanitizer/idle.png");
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
//                machineSound.toArray(new MadSound[]{}),
//                machineRecipes.toArray(new MadRecipe[]{}),
//                machineCraftingRecipes.toArray(new MadCraftingRecipe[]{}),
//                machineModel);
//        
//        return finalMachine;
        return null;
    }
}

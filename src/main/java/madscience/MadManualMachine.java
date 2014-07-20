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
//        String machineNameInternal = "cryoFreezer";
//
//        // LOGIC
//        String logicClassNamespace = "madscience.tile.CryofreezerEntity";
//        
//        // CONTAINERS
//        List<MadSlotContainer> machineContainers = new ArrayList<MadSlotContainer>();
//        machineContainers.add(new MadSlotContainer(0, MadSlotContainerTypeEnum.INPUT_INGREDIENT1, ForgeDirection.UNKNOWN, ForgeDirection.NORTH, false, true, 9, 35, 18, 18));
//        
//        machineContainers.add(new MadSlotContainer(1, MadSlotContainerTypeEnum.INPUT_STORAGE, ForgeDirection.SOUTH, ForgeDirection.UNKNOWN, false, true, 39, 16, 18, 18));
//        machineContainers.add(new MadSlotContainer(2, MadSlotContainerTypeEnum.INPUT_STORAGE, ForgeDirection.SOUTH, ForgeDirection.UNKNOWN, false, true, 57, 16, 18, 18));
//        machineContainers.add(new MadSlotContainer(3, MadSlotContainerTypeEnum.INPUT_STORAGE, ForgeDirection.SOUTH, ForgeDirection.UNKNOWN, false, true, 75, 16, 18, 18));
//        machineContainers.add(new MadSlotContainer(4, MadSlotContainerTypeEnum.INPUT_STORAGE, ForgeDirection.SOUTH, ForgeDirection.UNKNOWN, false, true, 93, 16, 18, 18));
//        machineContainers.add(new MadSlotContainer(5, MadSlotContainerTypeEnum.INPUT_STORAGE, ForgeDirection.SOUTH, ForgeDirection.UNKNOWN, false, true, 111, 16, 18, 18));
//        machineContainers.add(new MadSlotContainer(6, MadSlotContainerTypeEnum.INPUT_STORAGE, ForgeDirection.SOUTH, ForgeDirection.UNKNOWN, false, true, 129, 16, 18, 18));
//        machineContainers.add(new MadSlotContainer(7, MadSlotContainerTypeEnum.INPUT_STORAGE, ForgeDirection.SOUTH, ForgeDirection.UNKNOWN, false, true, 147, 16, 18, 18));
//        machineContainers.add(new MadSlotContainer(8, MadSlotContainerTypeEnum.INPUT_STORAGE, ForgeDirection.SOUTH, ForgeDirection.UNKNOWN, false, true, 39, 34, 18, 18));
//        machineContainers.add(new MadSlotContainer(9, MadSlotContainerTypeEnum.INPUT_STORAGE, ForgeDirection.SOUTH, ForgeDirection.UNKNOWN, false, true, 57, 34, 18, 18));
//        machineContainers.add(new MadSlotContainer(10, MadSlotContainerTypeEnum.INPUT_STORAGE, ForgeDirection.SOUTH, ForgeDirection.UNKNOWN, false, true, 75, 34, 18, 18));
//        machineContainers.add(new MadSlotContainer(11, MadSlotContainerTypeEnum.INPUT_STORAGE, ForgeDirection.SOUTH, ForgeDirection.UNKNOWN, false, true, 93, 34, 18, 18));
//        machineContainers.add(new MadSlotContainer(12, MadSlotContainerTypeEnum.INPUT_STORAGE, ForgeDirection.SOUTH, ForgeDirection.UNKNOWN, false, true, 111, 34, 18, 18));
//        machineContainers.add(new MadSlotContainer(13, MadSlotContainerTypeEnum.INPUT_STORAGE, ForgeDirection.SOUTH, ForgeDirection.UNKNOWN, false, true, 129, 34, 18, 18));
//        machineContainers.add(new MadSlotContainer(14, MadSlotContainerTypeEnum.INPUT_STORAGE, ForgeDirection.SOUTH, ForgeDirection.UNKNOWN, false, true, 147, 34, 18, 18));
//        machineContainers.add(new MadSlotContainer(15, MadSlotContainerTypeEnum.INPUT_STORAGE, ForgeDirection.SOUTH, ForgeDirection.UNKNOWN, false, true, 39, 52, 18, 18));
//        machineContainers.add(new MadSlotContainer(16, MadSlotContainerTypeEnum.INPUT_STORAGE, ForgeDirection.SOUTH, ForgeDirection.UNKNOWN, false, true, 57, 52, 18, 18));
//        machineContainers.add(new MadSlotContainer(17, MadSlotContainerTypeEnum.INPUT_STORAGE, ForgeDirection.SOUTH, ForgeDirection.UNKNOWN, false, true, 75, 52, 18, 18));
//        machineContainers.add(new MadSlotContainer(18, MadSlotContainerTypeEnum.INPUT_STORAGE, ForgeDirection.SOUTH, ForgeDirection.UNKNOWN, false, true, 93, 52, 18, 18));
//        machineContainers.add(new MadSlotContainer(19, MadSlotContainerTypeEnum.INPUT_STORAGE, ForgeDirection.SOUTH, ForgeDirection.UNKNOWN, false, true, 111, 52, 18, 18));
//        machineContainers.add(new MadSlotContainer(20, MadSlotContainerTypeEnum.INPUT_STORAGE, ForgeDirection.SOUTH, ForgeDirection.UNKNOWN, false, true, 129, 52, 18, 18));
//        machineContainers.add(new MadSlotContainer(21, MadSlotContainerTypeEnum.INPUT_STORAGE, ForgeDirection.SOUTH, ForgeDirection.UNKNOWN, false, true, 147, 52, 18, 18));
//
//        // GUI CONTROLS
//        List<MadGUIControl> machineGUIControls = new ArrayList<MadGUIControl>();
//        //machineGUIControls.add(new MadGUIControl(MadGUIControlTypeEnum.HeatGauge, 13, 15, 176, 31, 18, 40));
//        machineGUIControls.add(new MadGUIControl(MadGUIControlTypeEnum.PowerLevelY, 10, 56, 176, 0, 14, 14));
//        machineGUIControls.add(new MadGUIControl(MadGUIControlTypeEnum.CookingProgressY, 10, 14, 176, 14, 14, 16));
//
//        // GUI BUTTONS
//        List<MadGUIButton> machineGUIButtons = new ArrayList<MadGUIButton>();
//        machineGUIButtons.add(new MadGUIButton(0, MadGUIButtonTypeEnum.InvisibleButton, MadGUIButtonClickActionEnum.OpenLink, "button.help" , "http://madsciencemod.com/minecraft-item/cryogenic-freezer/", 166, 4, 9, 32, 6, 5));
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
//        //machineHeat.add(new MadHeat(0, 780, 1000));
//
//        // SOUNDS
//        List<MadSound> machineSound = new ArrayList<MadSound>();
//        machineSound.add(new MadSound("Idle.ogg", 1, 1, MadSoundTriggerEnum.IDLEON, MadSoundPlaybackTypeEnum.LOOP));
//
//        // RECIPES
//        List<MadRecipe> machineRecipes = new ArrayList<MadRecipe>();
//        machineRecipes.add(new MadRecipe(
//                MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:item.dna*:*:1",
//                null, "",
//                null, "",
//                null, "",
//                null, "",
//                null, "",
//                null, "",
//                null, "",
//                null, "",
//                null, "",
//                90,
//                0.1F));
//        machineRecipes.add(new MadRecipe(
//                MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:item.needle*:*:1",
//                null, "",
//                null, "",
//                null, "",
//                null, "",
//                null, "",
//                null, "",
//                null, "",
//                null, "",
//                null, "",
//                90,
//                0.1F));
//        machineRecipes.add(new MadRecipe(
//                MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "minecraft:item.snow*:*:1",
//                null, "",
//                null, "",
//                null, "",
//                null, "",
//                null, "",
//                null, "",
//                null, "",
//                null, "",
//                null, "",
//                90,
//                0.1F));
//        machineRecipes.add(new MadRecipe(
//                MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "minecraft:tile.snow*:*:1",
//                null, "",
//                null, "",
//                null, "",
//                null, "",
//                null, "",
//                null, "",
//                null, "",
//                null, "",
//                null, "",
//                90,
//                0.1F));
//        machineRecipes.add(new MadRecipe(
//                MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "minecraft:tile.ice*:*:1",
//                null, "",
//                null, "",
//                null, "",
//                null, "",
//                null, "",
//                null, "",
//                null, "",
//                null, "",
//                null, "",
//                90,
//                0.1F));
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
//                "1:madscience:circuitComparator:0:1",
//                "2:madscience:componentCase:0:1",
//                "3:madscience:componentCase:0:1",
//                "4:madscience:componentComputer:0:1",
//                "5:madscience:componentCase:0:1",
//                "6:madscience:componentCase:0:1",
//                "7:madscience:componentFan:0:1",
//                "8:madscience:componentCase:0:1"));
//        
//        // MODELS
//        List<MadModelFile> madchineModels = new ArrayList<MadModelFile>();
//        madchineModels.add(new MadModelFile("/assets/madscience/models/cryoFreezer/cryoFreezer.mad"));
//        
//        // MODELS AND TEXTURE
//        MadModel machineModel = new MadModel(madchineModels.toArray(new MadModelFile[]{}), "models/cryoFreezer/idle.png");
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

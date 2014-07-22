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
import madscience.factory.damage.MadDamage;
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
//        String machineNameInternal = "meatCube";
//
//        // LOGIC
//        String logicClassNamespace = "madscience.tile.MeatcubeEntity";
//        
//        // CONTAINERS
//        List<MadSlotContainer> machineContainers = new ArrayList<MadSlotContainer>();
//        machineContainers.add(new MadSlotContainer(0, MadSlotContainerTypeEnum.INPUT_FILLEDBUCKET, ForgeDirection.UNKNOWN, ForgeDirection.NORTH, false, true, 90, 43, 18, 18));
//        machineContainers.add(new MadSlotContainer(1, MadSlotContainerTypeEnum.OUTPUT_EMPTYBUCKET, ForgeDirection.EAST, ForgeDirection.UNKNOWN, true, false, 90, 18, 18, 18));
//        
//        // GUI CONTROLS
//        List<MadGUIControl> machineGUIControls = new ArrayList<MadGUIControl>();
//        //machineGUIControls.add(new MadGUIControl(MadGUIControlTypeEnum.PowerLevelY, 80, 43, 176, 0, 14, 14));
//        //machineGUIControls.add(new MadGUIControl(MadGUIControlTypeEnum.CookingProgressX, 78, 34, 176, 112, 65, 20));
//        machineGUIControls.add(new MadGUIControl(MadGUIControlTypeEnum.TankGaugeY, 67, 18, 176, 0, 16, 58));
//        //machineGUIControls.add(new MadGUIControl(MadGUIControlTypeEnum.HeatGaugeY, 52, 15, 176, 72, 18, 40));
//
//        // GUI BUTTONS
//        List<MadGUIButton> machineGUIButtons = new ArrayList<MadGUIButton>();
//        machineGUIButtons.add(new MadGUIButton(0, MadGUIButtonTypeEnum.InvisibleButton, MadGUIButtonClickActionEnum.OpenLink, "button.help" , "http://madsciencemod.com/minecraft-item/meat-cube/", 166, 4, 9, 32, 6, 5));
//
//        // FLUIDS
//        List<MadFluid> machineFluid = new ArrayList<MadFluid>();
//        machineFluid.add(new MadFluid("maddnamutant", 0, 10000));
//
//        // ENERGY
//        List<MadEnergy> machineEnergy = new ArrayList<MadEnergy>();
//        //machineEnergy.add(new MadEnergy(2500, 200, 0, 128, 0));
//        
//        // HEAT
//        List<MadHeat> machineHeat = new ArrayList<MadHeat>();
//        //machineHeat.add(new MadHeat(0, 420, 1000));
//        
//        // DAMAGE
//        List<MadDamage> machineDamage = new ArrayList<MadDamage>();
//        machineDamage.add(new MadDamage(0, 15, 0));
//
//        // SOUNDS
//        List<MadSound> machineSound = new ArrayList<MadSound>();
//        machineSound.add(new MadSound("Belly.ogg", 3, 6, MadSoundTriggerEnum.WORKSTART, MadSoundPlaybackTypeEnum.RANDOM));
//        machineSound.add(new MadSound("Heartbeat.ogg", 2, 4, MadSoundTriggerEnum.HIGHDAMAGE, MadSoundPlaybackTypeEnum.RANDOM));
//        machineSound.add(new MadSound("Idle.ogg", 2, 5, MadSoundTriggerEnum.IDLEOFF, MadSoundPlaybackTypeEnum.RANDOM));
//        machineSound.add(new MadSound("Meatslap.ogg", 1, 6, MadSoundTriggerEnum.LEFTCLICK, MadSoundPlaybackTypeEnum.RANDOM));
//        machineSound.add(new MadSound("Moo.ogg", 1, 5, MadSoundTriggerEnum.DAMAGED, MadSoundPlaybackTypeEnum.RANDOM));
//
//        // RECIPES
//        List<MadRecipe> machineRecipes = new ArrayList<MadRecipe>();
//        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:madDNAMutantBucket:0:1", null, "", null, "", null, "", null, "", null, "", null, "", null, "", null, "", null, "", 0, 0.0F));
//        
//        // CRAFTING RECIPE
//        // Note: Layout of crafting grid is as follows.
//        // 012
//        // 345
//        // 678
//        List<MadCraftingRecipe> machineCraftingRecipes = new ArrayList<MadCraftingRecipe>();
////        machineCraftingRecipes.add(new MadCraftingRecipe(
////                MadCraftingRecipeTypeEnum.SHAPED,
////                1,
////                "0:madscience:componentCase:0:1",
////                "1:madscience:componentComputer:0:1",
////                "2:madscience:componentCase:0:1",
////                "3:madscience:circuitSpiderEye:0:1",
////                "4:minecraft:jukebox:0:1",
////                "5:madscience:circuitEnderEye:0:1",
////                "6:madscience:componentPowerSupply:0:1",
////                "7:madscience:componentComputer:0:1",
////                "8:madscience:componentComputer:0:1"));
//        
//        // MODELS
//        List<MadModelFile> madchineModels = new ArrayList<MadModelFile>();
//        madchineModels.add(new MadModelFile("/assets/madscience/models/meatCube/meatCube.mad"));
//        madchineModels.add(new MadModelFile("/assets/madscience/models/meatCube/meatCube1.mad"));
//        madchineModels.add(new MadModelFile("/assets/madscience/models/meatCube/meatCube2.mad"));
//        madchineModels.add(new MadModelFile("/assets/madscience/models/meatCube/meatCube3.mad"));
//        madchineModels.add(new MadModelFile("/assets/madscience/models/meatCube/meatCube4.mad"));
//        madchineModels.add(new MadModelFile("/assets/madscience/models/meatCube/meatCube5.mad"));
//        madchineModels.add(new MadModelFile("/assets/madscience/models/meatCube/meatCube6.mad"));
//        madchineModels.add(new MadModelFile("/assets/madscience/models/meatCube/meatCube7.mad"));
//        madchineModels.add(new MadModelFile("/assets/madscience/models/meatCube/meatCube8.mad"));
//        madchineModels.add(new MadModelFile("/assets/madscience/models/meatCube/meatCube9.mad"));
//        madchineModels.add(new MadModelFile("/assets/madscience/models/meatCube/meatCube10.mad"));
//        madchineModels.add(new MadModelFile("/assets/madscience/models/meatCube/meatCube11.mad"));
//        madchineModels.add(new MadModelFile("/assets/madscience/models/meatCube/meatCube12.mad"));
//        madchineModels.add(new MadModelFile("/assets/madscience/models/meatCube/meatCube13.mad"));
//        madchineModels.add(new MadModelFile("/assets/madscience/models/meatCube/meatCube14.mad"));
//        
//        // MODELS AND TEXTURE
//        MadModel machineModel = new MadModel(madchineModels.toArray(new MadModelFile[]{}), "models/meatCube/idle.png");
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
//                machineDamage.toArray(new MadDamage[]{}),
//                machineSound.toArray(new MadSound[]{}),
//                machineRecipes.toArray(new MadRecipe[]{}),
//                machineCraftingRecipes.toArray(new MadCraftingRecipe[]{}),
//                machineModel);
//        
//        return finalMachine;
        return null;
    }
}

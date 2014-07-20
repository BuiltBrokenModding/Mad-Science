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
//        String machineNameInternal = "genomeSequencer";
//
//        // LOGIC
//        String logicClassNamespace = "madscience.tile.SequencerEntity";
//        
//        // CONTAINERS
//        List<MadSlotContainer> machineContainers = new ArrayList<MadSlotContainer>();
//        machineContainers.add(new MadSlotContainer(0, MadSlotContainerTypeEnum.INPUT_INGREDIENT1, ForgeDirection.UNKNOWN, ForgeDirection.NORTH, false, true, 22, 36, 18, 18));
//        machineContainers.add(new MadSlotContainer(1, MadSlotContainerTypeEnum.INPUT_INGREDIENT2, ForgeDirection.UNKNOWN, ForgeDirection.SOUTH, false, true, 54, 36, 18, 18));
//        machineContainers.add(new MadSlotContainer(2, MadSlotContainerTypeEnum.OUTPUT_RESULT1, ForgeDirection.WEST, ForgeDirection.UNKNOWN, true, false, 133, 36, 18, 18));
//        
//        // GUI CONTROLS
//        List<MadGUIControl> machineGUIControls = new ArrayList<MadGUIControl>();
//        //machineGUIControls.add(new MadGUIControl(MadGUIControlTypeEnum.HeatGauge, 13, 15, 176, 31, 18, 40));
//        machineGUIControls.add(new MadGUIControl(MadGUIControlTypeEnum.PowerLevelY, 55, 54, 176, 0, 14, 14));
//        machineGUIControls.add(new MadGUIControl(MadGUIControlTypeEnum.CookingProgressX, 78, 35, 176, 14, 43, 17));
//
//        // GUI BUTTONS
//        List<MadGUIButton> machineGUIButtons = new ArrayList<MadGUIButton>();
//        machineGUIButtons.add(new MadGUIButton(0, MadGUIButtonTypeEnum.InvisibleButton, MadGUIButtonClickActionEnum.OpenLink, "button.help" , "http://madsciencemod.com/minecraft-item/genetic-sequencer/", 166, 4, 9, 32, 6, 5));
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
//        machineSound.add(new MadSound("Finish.ogg", 1, 1, MadSoundTriggerEnum.WORKEND, MadSoundPlaybackTypeEnum.PLAY));
//        machineSound.add(new MadSound("Start.ogg", 2, 1, MadSoundTriggerEnum.WORKSTART, MadSoundPlaybackTypeEnum.PLAY));
//        machineSound.add(new MadSound("Work1.ogg", 2, 8, MadSoundTriggerEnum.WORKING, MadSoundPlaybackTypeEnum.LOOP));
//
//        // RECIPES
//        List<MadRecipe> machineRecipes = new ArrayList<MadRecipe>();
//        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:genomeBat:*:1", null, "", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:genomeBat:0:1", null, "", null, "", null, "", null, "", 90, 0.1F));
//        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:dnaBat:*:1", MadSlotContainerTypeEnum.INPUT_INGREDIENT2, "madscience:dataReelEmpty:0:1", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:genomeBat:0:1", null, "", null, "", null, "", null, "", 90, 0.5F));
//
//        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:genomeCaveSpider:*:1", null, "", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:genomeCaveSpider:0:1", null, "", null, "", null, "", null, "", 90, 0.1F));
//        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:dnaCaveSpider:*:1", MadSlotContainerTypeEnum.INPUT_INGREDIENT2, "madscience:dataReelEmpty:0:1", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:genomeCaveSpider:0:1", null, "", null, "", null, "", null, "", 90, 0.5F));
//        
//        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:genomeChicken:*:1", null, "", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:genomeChicken:0:1", null, "", null, "", null, "", null, "", 90, 0.1F));
//        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:dnaChicken:*:1", MadSlotContainerTypeEnum.INPUT_INGREDIENT2, "madscience:dataReelEmpty:0:1", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:genomeChicken:0:1", null, "", null, "", null, "", null, "", 90, 0.5F));
//        
//        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:genomeCow:*:1", null, "", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:genomeCow:0:1", null, "", null, "", null, "", null, "", 90, 0.1F));
//        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:dnaCow:*:1", MadSlotContainerTypeEnum.INPUT_INGREDIENT2, "madscience:dataReelEmpty:0:1", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:genomeCow:0:1", null, "", null, "", null, "", null, "", 90, 0.5F));
//        
//        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:genomeCreeper:*:1", null, "", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:genomeCreeper:0:1", null, "", null, "", null, "", null, "", 90, 0.1F));
//        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:dnaCreeper:*:1", MadSlotContainerTypeEnum.INPUT_INGREDIENT2, "madscience:dataReelEmpty:0:1", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:genomeCreeper:0:1", null, "", null, "", null, "", null, "", 90, 0.5F));
//        
//        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:genomeEnderman:*:1", null, "", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:genomeEnderman:0:1", null, "", null, "", null, "", null, "", 90, 0.1F));
//        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:dnaEnderman:*:1", MadSlotContainerTypeEnum.INPUT_INGREDIENT2, "madscience:dataReelEmpty:0:1", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:genomeEnderman:0:1", null, "", null, "", null, "", null, "", 90, 0.5F));
//        
//        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:genomeGhast:*:1", null, "", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:genomeGhast:0:1", null, "", null, "", null, "", null, "", 90, 0.1F));
//        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:dnaGhast:*:1", MadSlotContainerTypeEnum.INPUT_INGREDIENT2, "madscience:dataReelEmpty:0:1", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:genomeGhast:0:1", null, "", null, "", null, "", null, "", 90, 0.5F));
//        
//        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:genomeHorse:*:1", null, "", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:genomeHorse:0:1", null, "", null, "", null, "", null, "", 90, 0.1F));
//        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:dnaHorse:*:1", MadSlotContainerTypeEnum.INPUT_INGREDIENT2, "madscience:dataReelEmpty:0:1", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:genomeHorse:0:1", null, "", null, "", null, "", null, "", 90, 0.5F));
//        
//        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:genomeMushroomCow:*:1", null, "", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:genomeMushroomCow:0:1", null, "", null, "", null, "", null, "", 90, 0.1F));
//        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:dnaMushroomCow:*:1", MadSlotContainerTypeEnum.INPUT_INGREDIENT2, "madscience:dataReelEmpty:0:1", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:genomeMushroomCow:0:1", null, "", null, "", null, "", null, "", 90, 0.5F));
//        
//        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:genomeOcelot:*:1", null, "", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:genomeOcelot:0:1", null, "", null, "", null, "", null, "", 90, 0.1F));
//        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:dnaOcelot:*:1", MadSlotContainerTypeEnum.INPUT_INGREDIENT2, "madscience:dataReelEmpty:0:1", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:genomeOcelot:0:1", null, "", null, "", null, "", null, "", 90, 0.5F));
//        
//        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:genomePig:*:1", null, "", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:genomePig:0:1", null, "", null, "", null, "", null, "", 90, 0.1F));
//        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:dnaPig:*:1", MadSlotContainerTypeEnum.INPUT_INGREDIENT2, "madscience:dataReelEmpty:0:1", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:genomePig:0:1", null, "", null, "", null, "", null, "", 90, 0.5F));
//        
//        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:genomePigZombie:*:1", null, "", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:genomePigZombie:0:1", null, "", null, "", null, "", null, "", 90, 0.1F));
//        
//        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:genomeSheep:*:1", null, "", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:genomeSheep:0:1", null, "", null, "", null, "", null, "", 90, 0.1F));
//        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:dnaSheep:*:1", MadSlotContainerTypeEnum.INPUT_INGREDIENT2, "madscience:dataReelEmpty:0:1", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:genomeSheep:0:1", null, "", null, "", null, "", null, "", 90, 0.5F));
//        
//        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:genomeSkeleton:*:1", null, "", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:genomeSkeleton:0:1", null, "", null, "", null, "", null, "", 90, 0.1F));
//        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:dnaSkeleton:*:1", MadSlotContainerTypeEnum.INPUT_INGREDIENT2, "madscience:dataReelEmpty:0:1", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:genomeSkeleton:0:1", null, "", null, "", null, "", null, "", 90, 0.5F));
//        
//        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:genomeSlime:*:1", null, "", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:genomeSlime:0:1", null, "", null, "", null, "", null, "", 90, 0.1F));
//        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:dnaSlime:*:1", MadSlotContainerTypeEnum.INPUT_INGREDIENT2, "madscience:dataReelEmpty:0:1", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:genomeSlime:0:1", null, "", null, "", null, "", null, "", 90, 0.5F));
//        
//        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:genomeSpider:*:1", null, "", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:genomeSpider:0:1", null, "", null, "", null, "", null, "", 90, 0.1F));
//        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:dnaSpider:*:1", MadSlotContainerTypeEnum.INPUT_INGREDIENT2, "madscience:dataReelEmpty:0:1", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:genomeSpider:0:1", null, "", null, "", null, "", null, "", 90, 0.5F));
//        
//        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:genomeSquid:*:1", null, "", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:genomeSquid:0:1", null, "", null, "", null, "", null, "", 90, 0.1F));
//        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:dnaSquid:*:1", MadSlotContainerTypeEnum.INPUT_INGREDIENT2, "madscience:dataReelEmpty:0:1", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:genomeSquid:0:1", null, "", null, "", null, "", null, "", 90, 0.5F));
//        
//        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:genomeVillager:*:1", null, "", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:genomeVillager:0:1", null, "", null, "", null, "", null, "", 90, 0.1F));
//        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:dnaVillager:*:1", MadSlotContainerTypeEnum.INPUT_INGREDIENT2, "madscience:dataReelEmpty:0:1", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:genomeVillager:0:1", null, "", null, "", null, "", null, "", 90, 0.5F));
//        
//        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:genomeWitch:*:1", null, "", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:genomeWitch:0:1", null, "", null, "", null, "", null, "", 90, 0.1F));
//        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:dnaWitch:*:1", MadSlotContainerTypeEnum.INPUT_INGREDIENT2, "madscience:dataReelEmpty:0:1", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:genomeWitch:0:1", null, "", null, "", null, "", null, "", 90, 0.5F));
//        
//        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:genomeWolf:*:1", null, "", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:genomeWolf:0:1", null, "", null, "", null, "", null, "", 90, 0.1F));
//        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:dnaWolf:*:1", MadSlotContainerTypeEnum.INPUT_INGREDIENT2, "madscience:dataReelEmpty:0:1", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:genomeWolf:0:1", null, "", null, "", null, "", null, "", 90, 0.5F));
//        
//        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:genomeZombie:*:1", null, "", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:genomeZombie:0:1", null, "", null, "", null, "", null, "", 90, 0.1F));
//        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "madscience:dnaZombie:*:1", MadSlotContainerTypeEnum.INPUT_INGREDIENT2, "madscience:dataReelEmpty:0:1", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:genomeZombie:0:1", null, "", null, "", null, "", null, "", 90, 0.5F));
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
//                "0:madscience:circuitEmerald:0:1",
//                "1:madscience:componentFan:0:1",
//                "2:madscience:circuitComparator:0:1",
//                "3:madscience:componentCase:0:1",
//                "4:madscience:componentComputer:0:1",
//                "5:madscience:componentCase:0:1",
//                "6:madscience:circuitDiamond:0:1",
//                "7:madscience:componentPowerSupply:0:1",
//                "8:madscience:circuitEnderEye:0:1"));
//        
//        // MODELS
//        List<MadModelFile> madchineModels = new ArrayList<MadModelFile>();
//        madchineModels.add(new MadModelFile("/assets/madscience/models/genomeSequencer/genomeSequencer.mad"));
//        
//        // MODELS AND TEXTURE
//        MadModel machineModel = new MadModel(madchineModels.toArray(new MadModelFile[]{}), "models/genomeSequencer/idle.png");
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

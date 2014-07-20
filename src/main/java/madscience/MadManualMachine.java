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
        // NAME
        String machineNameInternal = "genomeIncubator";

        // LOGIC
        String logicClassNamespace = "madscience.tile.IncubatorEntity";
        
        // CONTAINERS
        List<MadSlotContainer> machineContainers = new ArrayList<MadSlotContainer>();
        machineContainers.add(new MadSlotContainer(0, MadSlotContainerTypeEnum.INPUT_INGREDIENT1, ForgeDirection.UNKNOWN, ForgeDirection.NORTH, false, true, 37, 39, 18, 18));
        machineContainers.add(new MadSlotContainer(1, MadSlotContainerTypeEnum.INPUT_INGREDIENT2, ForgeDirection.UNKNOWN, ForgeDirection.SOUTH, false, true, 69, 39, 18, 18));
        machineContainers.add(new MadSlotContainer(2, MadSlotContainerTypeEnum.OUTPUT_RESULT1, ForgeDirection.WEST, ForgeDirection.UNKNOWN, true, false, 140, 39, 18, 18));
        
        // GUI CONTROLS
        List<MadGUIControl> machineGUIControls = new ArrayList<MadGUIControl>();
        machineGUIControls.add(new MadGUIControl(MadGUIControlTypeEnum.PowerLevelY, 15, 57, 176, 0, 14, 14));
        machineGUIControls.add(new MadGUIControl(MadGUIControlTypeEnum.CookingProgressX, 93, 38, 176, 14, 36, 17));
        machineGUIControls.add(new MadGUIControl(MadGUIControlTypeEnum.HeatGaugeY, 13, 15, 176, 31, 18, 40));

        // GUI BUTTONS
        List<MadGUIButton> machineGUIButtons = new ArrayList<MadGUIButton>();
        machineGUIButtons.add(new MadGUIButton(0, MadGUIButtonTypeEnum.InvisibleButton, MadGUIButtonClickActionEnum.OpenLink, "button.help" , "http://madsciencemod.com/minecraft-item/genome-incubator/", 166, 4, 9, 32, 6, 5));

        // FLUIDS
        List<MadFluid> machineFluid = new ArrayList<MadFluid>();
        //machineFluid.add(new MadFluid("water", 0, 10000));

        // ENERGY
        List<MadEnergy> machineEnergy = new ArrayList<MadEnergy>();
        machineEnergy.add(new MadEnergy(100000, 200, 0, 1, 0));
        
        // HEAT
        List<MadHeat> machineHeat = new ArrayList<MadHeat>();
        machineHeat.add(new MadHeat(0, 780, 1000));

        // SOUNDS
        List<MadSound> machineSound = new ArrayList<MadSound>();
        machineSound.add(new MadSound("Finish.ogg", 1, 1, MadSoundTriggerEnum.WORKEND, MadSoundPlaybackTypeEnum.PLAY));
        machineSound.add(new MadSound("Start.ogg", 1, 1, MadSoundTriggerEnum.WORKSTART, MadSoundPlaybackTypeEnum.PLAY));
        machineSound.add(new MadSound("Work.ogg", 1, 6, MadSoundTriggerEnum.WORKING, MadSoundPlaybackTypeEnum.RANDOM));

        // RECIPES
        List<MadRecipe> machineRecipes = new ArrayList<MadRecipe>();
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "minecraft:egg:0:1", MadSlotContainerTypeEnum.INPUT_INGREDIENT2, "madscience:genomeMonsterPlacer:*:1", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:meatCube:0:1", null, "", null, "", null, "", null, "", 90, 0.1F));
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "minecraft:egg:0:1", MadSlotContainerTypeEnum.INPUT_INGREDIENT2, "madscience:genomeBat:0:1", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "minecraft:monsterPlacer:65:1", null, "", null, "", null, "", null, "", 90, 0.1F));
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "minecraft:egg:0:1", MadSlotContainerTypeEnum.INPUT_INGREDIENT2, "madscience:genomeCaveSpider:0:1", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "minecraft:monsterPlacer:59:1", null, "", null, "", null, "", null, "", 90, 0.1F));
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "minecraft:egg:0:1", MadSlotContainerTypeEnum.INPUT_INGREDIENT2, "madscience:genomeChicken:0:1", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "minecraft:monsterPlacer:93:1", null, "", null, "", null, "", null, "", 90, 0.1F));
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "minecraft:egg:0:1", MadSlotContainerTypeEnum.INPUT_INGREDIENT2, "madscience:genomeCow:0:1", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "minecraft:monsterPlacer:92:1", null, "", null, "", null, "", null, "", 90, 0.1F));
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "minecraft:egg:0:1", MadSlotContainerTypeEnum.INPUT_INGREDIENT2, "madscience:genomeCreeper:0:1", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "minecraft:monsterPlacer:50:1", null, "", null, "", null, "", null, "", 90, 0.1F));
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "minecraft:egg:0:1", MadSlotContainerTypeEnum.INPUT_INGREDIENT2, "madscience:genomeEnderman:0:1", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "minecraft:monsterPlacer:58:1", null, "", null, "", null, "", null, "", 90, 0.1F));
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "minecraft:egg:0:1", MadSlotContainerTypeEnum.INPUT_INGREDIENT2, "madscience:genomeGhast:0:1", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "minecraft:monsterPlacer:56:1", null, "", null, "", null, "", null, "", 90, 0.1F));
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "minecraft:egg:0:1", MadSlotContainerTypeEnum.INPUT_INGREDIENT2, "madscience:genomeHorse:0:1", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "minecraft:monsterPlacer:100:1", null, "", null, "", null, "", null, "", 90, 0.1F));
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "minecraft:egg:0:1", MadSlotContainerTypeEnum.INPUT_INGREDIENT2, "madscience:genomeMushroomCow:0:1", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "minecraft:monsterPlacer:96:1", null, "", null, "", null, "", null, "", 90, 0.1F));
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "minecraft:egg:0:1", MadSlotContainerTypeEnum.INPUT_INGREDIENT2, "madscience:genomeOcelot:0:1", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "minecraft:monsterPlacer:98:1", null, "", null, "", null, "", null, "", 90, 0.1F));
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "minecraft:egg:0:1", MadSlotContainerTypeEnum.INPUT_INGREDIENT2, "madscience:genomePig:0:1", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "minecraft:monsterPlacer:90:1", null, "", null, "", null, "", null, "", 90, 0.1F));
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "minecraft:egg:0:1", MadSlotContainerTypeEnum.INPUT_INGREDIENT2, "madscience:genomePigZombie:0:1", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "minecraft:monsterPlacer:57:1", null, "", null, "", null, "", null, "", 90, 0.1F));
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "minecraft:egg:0:1", MadSlotContainerTypeEnum.INPUT_INGREDIENT2, "madscience:genomeSheep:0:1", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "minecraft:monsterPlacer:91:1", null, "", null, "", null, "", null, "", 90, 0.1F));
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "minecraft:egg:0:1", MadSlotContainerTypeEnum.INPUT_INGREDIENT2, "madscience:genomeSkeleton:0:1", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "minecraft:monsterPlacer:51:1", null, "", null, "", null, "", null, "", 90, 0.1F));
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "minecraft:egg:0:1", MadSlotContainerTypeEnum.INPUT_INGREDIENT2, "madscience:genomeSlime:0:1", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "minecraft:monsterPlacer:55:1", null, "", null, "", null, "", null, "", 90, 0.1F));
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "minecraft:egg:0:1", MadSlotContainerTypeEnum.INPUT_INGREDIENT2, "madscience:genomeSpider:0:1", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "minecraft:monsterPlacer:52:1", null, "", null, "", null, "", null, "", 90, 0.1F));
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "minecraft:egg:0:1", MadSlotContainerTypeEnum.INPUT_INGREDIENT2, "madscience:genomeSquid:0:1", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "minecraft:monsterPlacer:94:1", null, "", null, "", null, "", null, "", 90, 0.1F));
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "minecraft:egg:0:1", MadSlotContainerTypeEnum.INPUT_INGREDIENT2, "madscience:genomeVillager:0:1", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "minecraft:monsterPlacer:120:1", null, "", null, "", null, "", null, "", 90, 0.1F));
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "minecraft:egg:0:1", MadSlotContainerTypeEnum.INPUT_INGREDIENT2, "madscience:genomeWitch:0:1", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "minecraft:monsterPlacer:66:1", null, "", null, "", null, "", null, "", 90, 0.1F));
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "minecraft:egg:0:1", MadSlotContainerTypeEnum.INPUT_INGREDIENT2, "madscience:genomeWolf:0:1", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "minecraft:monsterPlacer:95:1", null, "", null, "", null, "", null, "", 90, 0.1F));
        machineRecipes.add(new MadRecipe(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "minecraft:egg:0:1", MadSlotContainerTypeEnum.INPUT_INGREDIENT2, "madscience:genomeZombie:0:1", null, "", null, "", null, "", MadSlotContainerTypeEnum.OUTPUT_RESULT1, "minecraft:monsterPlacer:54:1", null, "", null, "", null, "", null, "", 90, 0.1F));
        
        // CRAFTING RECIPE
        // Note: Layout of crafting grid is as follows.
        // 012
        // 345
        // 678
        List<MadCraftingRecipe> machineCraftingRecipes = new ArrayList<MadCraftingRecipe>();
        machineCraftingRecipes.add(new MadCraftingRecipe(
                MadCraftingRecipeTypeEnum.SHAPED,
                1,
                "0:madscience:componentCase:0:1",
                "1:madscience:componentFan:0:1",
                "2:madscience:componentCase:0:1",
                "3:madscience:circuitGlowstone:0:1",
                "4:madscience:componentComputer:0:1",
                "5:madscience:circuitComparator:0:1",
                "6:madscience:componentCase:0:1",
                "7:madscience:componentPowerSupply:0:1",
                "8:madscience:componentCase:0:1"));
        
        // MODELS
        List<MadModelFile> madchineModels = new ArrayList<MadModelFile>();
        madchineModels.add(new MadModelFile("/assets/madscience/models/genomeIncubator/genomeIncubator.mad"));
        
        // MODELS AND TEXTURE
        MadModel machineModel = new MadModel(madchineModels.toArray(new MadModelFile[]{}), "models/genomeIncubator/idle.png");
        
        // RESULT
        MadTileEntityFactoryProductData finalMachine = new MadTileEntityFactoryProductData(
                machineNameInternal,
                logicClassNamespace,
                machineContainers.toArray(new MadSlotContainer[]{}),
                machineGUIControls.toArray(new MadGUIControl[]{}),
                machineGUIButtons.toArray(new MadGUIButton[]{}),
                machineFluid.toArray(new MadFluid[]{}),
                machineEnergy.toArray(new MadEnergy[]{}),
                machineHeat.toArray(new MadHeat[]{}),
                machineSound.toArray(new MadSound[]{}),
                machineRecipes.toArray(new MadRecipe[]{}),
                machineCraftingRecipes.toArray(new MadCraftingRecipe[]{}),
                machineModel);
        
        return finalMachine;
        //return null;
    }
}

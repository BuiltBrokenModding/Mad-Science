package madscience;

import java.util.ArrayList;
import java.util.List;

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
import madscience.factory.model.MadModelBounds;
import madscience.factory.model.MadModelFile;
import madscience.factory.model.MadModelPosition;
import madscience.factory.model.MadModelRotation;
import madscience.factory.model.MadModelScale;
import madscience.factory.recipes.MadRecipe;
import madscience.factory.rendering.MadModelItemRender;
import madscience.factory.rendering.MadModelWorldRender;
import madscience.factory.slotcontainers.MadSlotContainer;
import madscience.factory.slotcontainers.MadSlotContainerTypeEnum;
import madscience.factory.sounds.MadSound;
import madscience.factory.sounds.MadSoundPlaybackTypeEnum;
import madscience.factory.sounds.MadSoundTriggerEnum;
import madscience.factory.tileentity.MadTileEntityFactoryProductData;
import net.minecraftforge.common.ForgeDirection;

public class MadManualMachine
{
    /** Called right before configuration with ID manager occurs, allows machines to be manually inserted via code by developer. */
    public static MadTileEntityFactoryProductData getMachine()
    {
        // NAME
        String machineNameInternal = "cryoTube";

        // LOGIC
        String logicClassNamespace = "madscience.tile.CryotubeEntity";
        
        // HARDNESS
        float blockHardness = 3.5F;
        
        // EXPLOSION RESISTANCE
        float explosionResistance = 2000.0F;
        
        // CONTAINERS
        List<MadSlotContainer> machineContainers = new ArrayList<MadSlotContainer>();
        machineContainers.add(new MadSlotContainer(0, MadSlotContainerTypeEnum.INPUT_INGREDIENT1, ForgeDirection.UNKNOWN, ForgeDirection.NORTH, false, true, 11, 26, 18, 18));
        machineContainers.add(new MadSlotContainer(1, MadSlotContainerTypeEnum.INPUT_INGREDIENT2, ForgeDirection.UNKNOWN, ForgeDirection.EAST, false, true, 11, 47, 18, 18));
        machineContainers.add(new MadSlotContainer(2, MadSlotContainerTypeEnum.INPUT_EXTRA, ForgeDirection.UNKNOWN, ForgeDirection.SOUTH, false, true, 113, 52, 18, 18));
        machineContainers.add(new MadSlotContainer(3, MadSlotContainerTypeEnum.OUTPUT_RESULT1, ForgeDirection.WEST, ForgeDirection.UNKNOWN, true, false, 144, 22, 18, 18));
        machineContainers.add(new MadSlotContainer(4, MadSlotContainerTypeEnum.OUTPUT_WASTE, ForgeDirection.DOWN, ForgeDirection.UNKNOWN, true, false, 144, 56, 18, 18));
        
        // GUI CONTROLS
        List<MadGUIControl> machineGUIControls = new ArrayList<MadGUIControl>();
        machineGUIControls.add(new MadGUIControl(MadGUIControlTypeEnum.PowerLevelY, 115, 20, 179, 59, 12, 26));
        machineGUIControls.add(new MadGUIControl(MadGUIControlTypeEnum.CookingProgressX, 35, 37, 176, 0, 26, 10));
        machineGUIControls.add(new MadGUIControl(MadGUIControlTypeEnum.DamageLevelY, 68, 17, 176, 10, 11, 46));
        machineGUIControls.add(new MadGUIControl(MadGUIControlTypeEnum.HeatGaugeY, 91, 17, 187, 10, 11, 46));

        // GUI BUTTONS
        List<MadGUIButton> machineGUIButtons = new ArrayList<MadGUIButton>();
        machineGUIButtons.add(new MadGUIButton(0, MadGUIButtonTypeEnum.InvisibleButton, MadGUIButtonClickActionEnum.OpenLink, "button.help" , "http://madsciencemod.com/minecraft-item/cryogenic-tube/", 166, 4, 9, 32, 6, 5));

        // FLUIDS
        List<MadFluid> machineFluid = new ArrayList<MadFluid>();
        //machineFluid.add(new MadFluid("maddnamutant", 0, 10000));

        // ENERGY
        List<MadEnergy> machineEnergy = new ArrayList<MadEnergy>();
        machineEnergy.add(new MadEnergy(225120000000L, 0, 562800000L, 0, 1407000L));
        
        // HEAT
        List<MadHeat> machineHeat = new ArrayList<MadHeat>();
        machineHeat.add(new MadHeat(0, 32, 512));
        
        // DAMAGE
        List<MadDamage> machineDamage = new ArrayList<MadDamage>();
        machineDamage.add(new MadDamage(0, 100, 100));

        // SOUNDS
        List<MadSound> machineSound = new ArrayList<MadSound>();
        machineSound.add(new MadSound("Stillbirth.ogg", 2, 1, MadSoundTriggerEnum.CUSTOM, MadSoundPlaybackTypeEnum.PLAY));
        machineSound.add(new MadSound("Work.ogg", 1, 1, MadSoundTriggerEnum.CUSTOM, MadSoundPlaybackTypeEnum.PLAY));
        machineSound.add(new MadSound("CrackEgg.ogg", 4, 1, MadSoundTriggerEnum.WORKSTART, MadSoundPlaybackTypeEnum.PLAY));
        machineSound.add(new MadSound("Hatching.ogg", 1, 1, MadSoundTriggerEnum.WORKSTART, MadSoundPlaybackTypeEnum.PLAY));
        machineSound.add(new MadSound("Idle.ogg", 1, 1, MadSoundTriggerEnum.IDLEON, MadSoundPlaybackTypeEnum.LOOP));
        machineSound.add(new MadSound("Off.ogg", 2, 1, MadSoundTriggerEnum.IDLEOFF, MadSoundPlaybackTypeEnum.PLAY));
        machineSound.add(new MadSound("Hatch.ogg", 1, 10, MadSoundTriggerEnum.WORKING, MadSoundPlaybackTypeEnum.RANDOM));

        // RECIPES
        List<MadRecipe> machineRecipes = new ArrayList<MadRecipe>();
        machineRecipes.add(new MadRecipe(
                MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "minecraft:monsterPlacer:120:1",
                MadSlotContainerTypeEnum.INPUT_INGREDIENT2, "madscience:memoryMonsterPlacer:*:1",
                MadSlotContainerTypeEnum.INPUT_EXTRA, "minecraft:netherStar:0:1",
                null, "",
                null, "",
                MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:memoryMonsterPlacer:0:1",
                null, "",
                null, "",
                null, "",
                null, "",
                130, 0.15F));
        
        machineRecipes.add(new MadRecipe(
                MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "minecraft:monsterPlacer:120:1",
                MadSlotContainerTypeEnum.INPUT_INGREDIENT2, "madscience:dataReelEmpty:0:1",
                MadSlotContainerTypeEnum.INPUT_EXTRA, "minecraft:netherStar:0:1",
                null, "",
                null, "",
                MadSlotContainerTypeEnum.OUTPUT_RESULT1, "madscience:memoryMonsterPlacer:0:1",
                null, "",
                null, "",
                null, "",
                null, "",
                130, 0.15F));
        
        // CRAFTING RECIPE
        // Note: Layout of crafting grid is as follows.
        // 012
        // 345
        // 678
        List<MadCraftingRecipe> machineCraftingRecipes = new ArrayList<MadCraftingRecipe>();
        machineCraftingRecipes.add(new MadCraftingRecipe(
                MadCraftingRecipeTypeEnum.SHAPED,
                1,
                "0:minecraft:blockIron:0:1",
                "1:madscience:circuitEnderEye:0:1",
                "2:minecraft:blockIron:0:1",
                "3:minecraft:blockIron:0:1",
                "4:madscience:componentComputer:0:1",
                "5:minecraft:blockIron:0:1",
                "6:minecraft:blockIron:0:1",
                "7:madscience:componentPowerSupply:0:1",
                "8:minecraft:blockIron:0:1"));
        
        // MODELS
        List<MadModelFile> madchineModels = new ArrayList<MadModelFile>();
        madchineModels.add(new MadModelFile("/assets/madscience/models/cryoTube/cryoTube.mad", true));
        
        // MODEL ITEM RENDER INFO
        MadModelItemRender itemRenderer = new MadModelItemRender(
                true,
                true,
                true,
                false,
                new MadModelScale(1.4F, 1.4F, 1.4F),            // EQUIPPED
                new MadModelPosition(0.1F, 0.3F, 0.3F),
                new MadModelRotation(90.0F, 0.0F, 1.0F, 0.0F),
                new MadModelScale(1.0F, 1.0F, 1.0F),            // FIRST_PERSON           
                new MadModelPosition(0.2F, 0.9F, 0.5F),         
                new MadModelRotation(90.0F, 0.0F, 0.5F, 0.0F),  
                new MadModelScale(1.0F, 1.0F, 1.0F),         // INVENTORY         
                new MadModelPosition(0.5F, 0.42F, 0.5F),
                new MadModelRotation(270.0F, 0.0F, 0.5F, 0.0F), 
                new MadModelScale(1.0F, 1.0F, 1.0F),            // ENTITY            
                new MadModelPosition(0.5F, 0.5F, 0.5F),
                new MadModelRotation(180.0F, 0.0F, 1.0F, 0.0F));
        
        // MODEL WORLD RENDER INFO
        MadModelWorldRender worldRenderer = new MadModelWorldRender(
                new MadModelPosition(0.5F, 0.5F, 0.5F),
                new MadModelScale(1.0F, 1.0F, 1.0F));
        
        // MODELS AND TEXTURE
        MadModel machineModel = new MadModel(
                madchineModels.toArray(new MadModelFile[]{}),
                "models/cryoTube/off.png",
                itemRenderer,
                worldRenderer);
        
        // BOUNDING BOX
        MadModelBounds machineBounds = new MadModelBounds(
                new MadModelPosition(0.0F, 0.0F, 0.0F),
                new MadModelPosition(1.0F, 3.0F, 1.0F));
        
        // RESULT
        MadTileEntityFactoryProductData finalMachine = new MadTileEntityFactoryProductData(
                machineNameInternal,
                logicClassNamespace,
                blockHardness,
                explosionResistance,
                machineBounds,
                machineContainers.toArray(new MadSlotContainer[]{}),
                machineGUIControls.toArray(new MadGUIControl[]{}),
                machineGUIButtons.toArray(new MadGUIButton[]{}),
                machineFluid.toArray(new MadFluid[]{}),
                machineEnergy.toArray(new MadEnergy[]{}),
                machineHeat.toArray(new MadHeat[]{}),
                machineDamage.toArray(new MadDamage[]{}),
                machineSound.toArray(new MadSound[]{}),
                machineRecipes.toArray(new MadRecipe[]{}),
                machineCraftingRecipes.toArray(new MadCraftingRecipe[]{}),
                machineModel);
        
        return finalMachine;
//        return null;
    }
}

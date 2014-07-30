package madscience;

import java.util.ArrayList;
import java.util.List;

import madscience.factory.block.MadGhostBlockData;
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
//        // NAME
//        String machineNameInternal = "cncMachine";
//
//        // LOGIC
//        String logicClassNamespace = "madscience.tile.CnCMachineEntity";
//        
//        // HARDNESS
//        float blockHardness = 3.5F;
//        
//        // EXPLOSION RESISTANCE
//        float explosionResistance = 2000.0F;
//        
//        // CONTAINERS
//        List<MadSlotContainer> machineContainers = new ArrayList<MadSlotContainer>();
//        machineContainers.add(new MadSlotContainer(0, MadSlotContainerTypeEnum.INPUT_FILLEDBUCKET, ForgeDirection.UNKNOWN, ForgeDirection.NORTH, false, true, 31, 34, 18, 18));
//        machineContainers.add(new MadSlotContainer(1, MadSlotContainerTypeEnum.INPUT_INGREDIENT1, ForgeDirection.UNKNOWN, ForgeDirection.EAST, false, true, 67, 44, 18, 18));
//        machineContainers.add(new MadSlotContainer(2, MadSlotContainerTypeEnum.INPUT_INGREDIENT2, ForgeDirection.UNKNOWN, ForgeDirection.SOUTH, false, true, 67, 17, 18, 18));
//        machineContainers.add(new MadSlotContainer(3, MadSlotContainerTypeEnum.OUTPUT_RESULT1, ForgeDirection.UP, ForgeDirection.UNKNOWN, true, false, 131, 44, 18, 18));
//        machineContainers.add(new MadSlotContainer(4, MadSlotContainerTypeEnum.OUTPUT_EMPTYBUCKET, ForgeDirection.WEST, ForgeDirection.UNKNOWN, true, false, 31, 9, 18, 18));
//        
//        // GUI CONTROLS
//        List<MadGUIControl> machineGUIControls = new ArrayList<MadGUIControl>();
//        machineGUIControls.add(new MadGUIControl(MadGUIControlTypeEnum.PowerLevelY, 68, 62, 176, 0, 14, 14));
//        machineGUIControls.add(new MadGUIControl(MadGUIControlTypeEnum.CookingProgressX, 89, 45, 176, 14, 31, 14));
//        machineGUIControls.add(new MadGUIControl(MadGUIControlTypeEnum.TankGaugeY, 8, 9, 176, 28, 16, 58));
//        //machineGUIControls.add(new MadGUIControl(MadGUIControlTypeEnum.HeatGaugeY, 88, 18, 176, 14, 18, 40));
//        //machineGUIControls.add(new MadGUIControl(MadGUIControlTypeEnum.DamageLevelY, 68, 17, 176, 10, 11, 46));
//
//        // GUI BUTTONS
//        List<MadGUIButton> machineGUIButtons = new ArrayList<MadGUIButton>();
//        machineGUIButtons.add(new MadGUIButton(0, MadGUIButtonTypeEnum.InvisibleButton, MadGUIButtonClickActionEnum.OpenLink, "button.help" , "http://madsciencemod.com/minecraft-item/cnc-machine/", 166, 4, 9, 32, 6, 5));
//
//        // FLUIDS
//        List<MadFluid> machineFluid = new ArrayList<MadFluid>();
//        machineFluid.add(new MadFluid("water", 0, 10000));
//
//        // ENERGY
//        List<MadEnergy> machineEnergy = new ArrayList<MadEnergy>();
//        machineEnergy.add(new MadEnergy(25000L, 200L, 0L, 1L, 0L));
//        
//        // HEAT
//        List<MadHeat> machineHeat = new ArrayList<MadHeat>();
//        //machineHeat.add(new MadHeat(0, 32, 512));
//        
//        // DAMAGE
//        List<MadDamage> machineDamage = new ArrayList<MadDamage>();
//        //machineDamage.add(new MadDamage(0, 100, 100));
//
//        // SOUNDS
//        List<MadSound> machineSound = new ArrayList<MadSound>();
//        machineSound.add(new MadSound("FinishCrushing.ogg", 2, 1, MadSoundTriggerEnum.CUSTOM, MadSoundPlaybackTypeEnum.PLAY));
//        machineSound.add(new MadSound("Finished.ogg", 1, 1, MadSoundTriggerEnum.WORKEND, MadSoundPlaybackTypeEnum.PLAY));
//        machineSound.add(new MadSound("InsertIronBlock.ogg", 1, 1, MadSoundTriggerEnum.CUSTOM, MadSoundPlaybackTypeEnum.PLAY));
//        machineSound.add(new MadSound("InvalidBook.ogg", 1, 1, MadSoundTriggerEnum.CUSTOM, MadSoundPlaybackTypeEnum.PLAY));
//        machineSound.add(new MadSound("PowerOn.ogg", 1, 1, MadSoundTriggerEnum.CUSTOM, MadSoundPlaybackTypeEnum.PLAY));
//        machineSound.add(new MadSound("Press.ogg", 2, 4, MadSoundTriggerEnum.CUSTOM, MadSoundPlaybackTypeEnum.RANDOM));
//        machineSound.add(new MadSound("PressingWork.ogg", 3, 1, MadSoundTriggerEnum.CUSTOM, MadSoundPlaybackTypeEnum.PLAY));
//        machineSound.add(new MadSound("PressStop.ogg", 3, 1, MadSoundTriggerEnum.CUSTOM, MadSoundPlaybackTypeEnum.PLAY));
//        machineSound.add(new MadSound("WaterFlow.ogg", 4, 1, MadSoundTriggerEnum.CUSTOM, MadSoundPlaybackTypeEnum.PLAY));
//        machineSound.add(new MadSound("WaterWork.ogg", 4, 1, MadSoundTriggerEnum.CUSTOM, MadSoundPlaybackTypeEnum.PLAY));
//
//        // RECIPES
//        List<MadRecipe> machineRecipes = new ArrayList<MadRecipe>();
//        machineRecipes.add(new MadRecipe(
//                MadSlotContainerTypeEnum.INPUT_INGREDIENT1, "minecraft:blockIron:0:1",
//                MadSlotContainerTypeEnum.INPUT_INGREDIENT2, "minecraft:writtenBook:0:1",
//                null, "",
//                null, "",
//                null, "",
//                null, "",       // OUTPUT
//                null, "",
//                null, "",
//                null, "",
//                null, "",
//                50, 0.42F));
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
//                "1:minecraft:pistonBase:0:1",
//                "2:madscience:componentCPU:0:1",
//                "3:minecraft:obsidian:0:1",
//                "4:minecraft:sand:0:1",
//                "5:minecraft:obsidian:0:1",
//                "6:minecraft:obsidian:0:1",
//                "7:madscience:componentPowerSupply:0:1",
//                "8:minecraft:obsidian:0:1"));
//        
//        // MODELS
//        List<MadModelFile> madchineModels = new ArrayList<MadModelFile>();
//        madchineModels.add(new MadModelFile("/assets/madscience/models/cncMachine/Base.mad", true));
//        madchineModels.add(new MadModelFile("/assets/madscience/models/cncMachine/Monitor.mad", true));
//        madchineModels.add(new MadModelFile("/assets/madscience/models/cncMachine/CompressedBlock0.mad", false));
//        madchineModels.add(new MadModelFile("/assets/madscience/models/cncMachine/CompressedBlock1.mad", false));
//        madchineModels.add(new MadModelFile("/assets/madscience/models/cncMachine/CompressedBlock2.mad", false));
//        madchineModels.add(new MadModelFile("/assets/madscience/models/cncMachine/CompressedBlock3.mad", false));
//        madchineModels.add(new MadModelFile("/assets/madscience/models/cncMachine/Cutblock0.mad", false));
//        madchineModels.add(new MadModelFile("/assets/madscience/models/cncMachine/Cutblock1.mad", false));
//        madchineModels.add(new MadModelFile("/assets/madscience/models/cncMachine/Cutblock2.mad", false));
//        madchineModels.add(new MadModelFile("/assets/madscience/models/cncMachine/Cutblock3.mad", false));
//        madchineModels.add(new MadModelFile("/assets/madscience/models/cncMachine/Cutblock4.mad", false));
//        madchineModels.add(new MadModelFile("/assets/madscience/models/cncMachine/Cutblock5.mad", false));
//        madchineModels.add(new MadModelFile("/assets/madscience/models/cncMachine/Cutblock6.mad", false));
//        madchineModels.add(new MadModelFile("/assets/madscience/models/cncMachine/Cutblock7.mad", false));
//        madchineModels.add(new MadModelFile("/assets/madscience/models/cncMachine/Cutblock8.mad", false));
//        madchineModels.add(new MadModelFile("/assets/madscience/models/cncMachine/Press0.mad", true));
//        madchineModels.add(new MadModelFile("/assets/madscience/models/cncMachine/Press1.mad", false));
//        madchineModels.add(new MadModelFile("/assets/madscience/models/cncMachine/Press2.mad", false));
//        madchineModels.add(new MadModelFile("/assets/madscience/models/cncMachine/Press3.mad", false));
//        madchineModels.add(new MadModelFile("/assets/madscience/models/cncMachine/Water0.mad", false));
//        madchineModels.add(new MadModelFile("/assets/madscience/models/cncMachine/Water1.mad", false));
//        madchineModels.add(new MadModelFile("/assets/madscience/models/cncMachine/Water2.mad", false));
//        madchineModels.add(new MadModelFile("/assets/madscience/models/cncMachine/Water3.mad", false));
//        madchineModels.add(new MadModelFile("/assets/madscience/models/cncMachine/Water4.mad", false));
//        madchineModels.add(new MadModelFile("/assets/madscience/models/cncMachine/Water5.mad", false));
//        madchineModels.add(new MadModelFile("/assets/madscience/models/cncMachine/Water6.mad", false));
//        madchineModels.add(new MadModelFile("/assets/madscience/models/cncMachine/Water7.mad", false));
//        madchineModels.add(new MadModelFile("/assets/madscience/models/cncMachine/Water8.mad", false));
//        
//        // MODEL ITEM RENDER INFO
//        MadModelItemRender itemRenderer = new MadModelItemRender(
//                true,
//                true,
//                true,
//                false,
//                new MadModelScale(1.4F, 1.4F, 1.4F),            // EQUIPPED
//                new MadModelPosition(0.1F, 0.3F, 0.3F),
//                new MadModelRotation(90.0F, 0.0F, 1.0F, 0.0F),
//                new MadModelScale(1.0F, 1.0F, 1.0F),            // FIRST_PERSON           
//                new MadModelPosition(0.2F, 0.9F, 0.5F),         
//                new MadModelRotation(90.0F, 0.0F, 0.5F, 0.0F),  
//                new MadModelScale(1.0F, 1.0F, 1.0F),         // INVENTORY         
//                new MadModelPosition(0.5F, 0.42F, 0.5F),
//                new MadModelRotation(270.0F, 0.0F, 0.5F, 0.0F), 
//                new MadModelScale(1.0F, 1.0F, 1.0F),            // ENTITY            
//                new MadModelPosition(0.5F, 0.5F, 0.5F),
//                new MadModelRotation(180.0F, 0.0F, 1.0F, 0.0F));
//        
//        // MODEL WORLD RENDER INFO
//        MadModelWorldRender worldRenderer = new MadModelWorldRender(
//                new MadModelPosition(0.5F, 0.5F, 0.5F),
//                new MadModelScale(1.0F, 1.0F, 1.0F));
//        
//        // MODELS AND TEXTURE
//        MadModel machineModel = new MadModel(
//                madchineModels.toArray(new MadModelFile[]{}),
//                "models/cncMachine/off.png",
//                itemRenderer,
//                worldRenderer);
//        
//        // BOUNDING BOX
//        MadModelBounds machineBounds = new MadModelBounds(
//                new MadModelPosition(0.0F, 0.0F, 0.0F),
//                new MadModelPosition(1.0F, 2.0F, 1.0F));
//        
//        // GHOST BLOCK CONFIGURATION
//        MadGhostBlockData ghostBlockConfig = new MadGhostBlockData(0, 1, 0);
//        
//        // RESULT
//        MadTileEntityFactoryProductData finalMachine = new MadTileEntityFactoryProductData(
//                machineNameInternal,
//                logicClassNamespace,
//                blockHardness,
//                explosionResistance,
//                machineBounds,
//                ghostBlockConfig,
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

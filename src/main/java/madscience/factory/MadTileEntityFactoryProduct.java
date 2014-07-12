package madscience.factory;

import java.util.ArrayList;
import java.util.List;

import madscience.MadScience;
import madscience.factory.buttons.IMadGUIButton;
import madscience.factory.controls.IMadGUIControl;
import madscience.factory.energy.IMadEnergy;
import madscience.factory.fluids.IMadFluid;
import madscience.factory.recipes.IMadRecipeComponent;
import madscience.factory.recipes.IMadRecipe;
import madscience.factory.slotcontainers.IMadSlotContainer;
import madscience.factory.sounds.IMadSound;
import madscience.factory.sounds.MadSoundPlaybackTypeEnum;
import madscience.factory.sounds.MadSoundTriggerEnum;
import madscience.factory.tileentity.MadContainerTemplate;
import madscience.factory.tileentity.MadGUITemplate;
import madscience.factory.tileentity.MadTileEntityBlockTemplate;
import madscience.factory.tileentity.MadTileEntityPrefab;
import net.minecraft.block.BlockContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MadTileEntityFactoryProduct
{
    /** Holds the internal name of this machine as used in config files and referenced in other lists. */
    private static String machineName;

    /** Reference number used by Forge/MC to keep track of this tile entity. */
    private static int blockID;

    /** Stores block container class which provides Forge/MC with server side slot info. */
    private static BlockContainer blockContainer;

    /** Stores reference to tile entity class itself which makes up logic or brains of this machine. */
    private static Class<? extends MadTileEntityPrefab> tileEntityLogicClass;

    /** Stores slot containers where items can be inputed and extracted from. */
    private IMadSlotContainer[] containerTemplate = new IMadSlotContainer[0];

    /** Stores GUI controls like tanks, progress bars, animations, etc. */
    private IMadGUIControl[] guiControlsTemplate = new IMadGUIControl[0];

    /** Stores GUI buttons, includes invisible ones with custom textures also. */
    private IMadGUIButton[] guiButtonTemplate = new IMadGUIButton[0];

    /** Stores fluids that this machine will be able to interface with it's internal tank methods. */
    private IMadFluid[] fluidsSupported = new IMadFluid[0];

    /** Stores information about how we want to plugged into other electrical grids. */
    private IMadEnergy[] energySupported = new IMadEnergy[0];

    /** Stores collection of sounds that can be mapped to various triggers on this machine. */
    private IMadSound[] soundArchive = new IMadSound[0];

    /** Stores all known recipes that should be associated with this machine. */
    private IMadRecipe[] recipeArchive = new IMadRecipe[0];

    /** Stores if we have loaded the sounds or not, allowing us to throw an error if called again */
    private boolean soundArchiveLoaded = false;

    MadTileEntityFactoryProduct(String machineName, int blockID, Class<? extends MadTileEntityPrefab> logicClass)
    {
        // Important information that makes up the basics of any machine.
        this.machineName = machineName;
        this.blockID = blockID;
        this.tileEntityLogicClass = logicClass;

        // Setup the block which will create the tile entity once placed in the game world.
        this.blockContainer = (BlockContainer) new MadTileEntityBlockTemplate(this);
    }

    public int getBlockID()
    {
        return blockID;
    }

    /** Returns client GUI which can be pushed to renderer. */
    public MadGUITemplate getClientGUIElement(InventoryPlayer playerEntity, MadTileEntityPrefab worldEntity)
    {
        return new MadGUITemplate(playerEntity, worldEntity);
    }

    public IMadSlotContainer[] getContainerTemplate()
    {
        return containerTemplate;
    }

    public IMadEnergy[] getEnergySupported()
    {
        return energySupported;
    }

    public IMadFluid[] getFluidsSupported()
    {
        return fluidsSupported;
    }

    public IMadGUIButton[] getGuiButtonTemplate()
    {
        return guiButtonTemplate;
    }

    public IMadGUIControl[] getGuiControlsTemplate()
    {
        return guiControlsTemplate;
    }

    /** Returns machines internal name as it should be referenced by rest of code. */
    public String getMachineName()
    {
        return machineName;
    }

    /** Returns container slots for storing items in machines on server. */
    public MadContainerTemplate getServerGUIElement(InventoryPlayer playerEntity, MadTileEntityPrefab worldEntity)
    {
        return new MadContainerTemplate(playerEntity, worldEntity);
    }

    public void setContainerTemplate(IMadSlotContainer[] containerTemplate)
    {
        this.containerTemplate = containerTemplate;
    }

    public void setEnergySupported(IMadEnergy[] energySupported)
    {
        this.energySupported = energySupported;
    }

    public void setFluidsSupported(IMadFluid[] fluidSupported)
    {
        this.fluidsSupported = fluidSupported;
    }

    public void setGuiButtonTemplate(IMadGUIButton[] guiButtonTemplate)
    {
        this.guiButtonTemplate = guiButtonTemplate;
    }

    public void setGuiControlsTemplate(IMadGUIControl[] guiControlsTemplate)
    {
        this.guiControlsTemplate = guiControlsTemplate;
    }

    public BlockContainer getBlockContainer()
    {
        return blockContainer;
    }

    public MadTileEntityPrefab getNewTileEntityLogicClassInstance()
    {
        // Attempt to create a new instance of the logic class that was passed to us at creation.
        try
        {
            return tileEntityLogicClass.getDeclaredConstructor(MadTileEntityFactoryProduct.class).newInstance(this);
        }
        catch (Exception err)
        {
            // Something terrible has happened!
            err.printStackTrace();
        }

        // Default response is to return nothing!
        return null;
    }

    public Class<? extends MadTileEntityPrefab> getTileEntityLogicClass()
    {
        // Returns the reference to class for logic that makes up our tile entity.
        return tileEntityLogicClass;
    }

    public void loadRecipes()
    {
        // Complain if we are somehow already loaded!
        if (this.recipeArchive == null)
        {
            MadScience.logger.warning("[MadTileEntityFactoryProduct]Unable to load recipes for '" + this.machineName + "' because it has none!");
            return;
        }

        // Recipe archives are nested so we need to loop through them to get to first one (even if it is the only one).
        int totalLoadedRecipeItems = 0;
        int totalFailedRecipeItems = 0;
        for (IMadRecipe machineRecipe : this.recipeArchive)
        {
            // Recipe ingredients.
            for (IMadRecipeComponent inputIngredient : machineRecipe.getInputIngredientsArray())
            {
                if (inputIngredient.getSlotType().name().toLowerCase().contains("input"))
                {
                    // Starting building output string now, append to it below.
                    String resultInputPrint = "[" + this.machineName + "]Input Ingredient " + inputIngredient.getModID() + ":" + inputIngredient.getInternalName();

                    // Query game registry and vanilla blocks and items for the incoming name in an attempt to turn it into an itemstack.
                    ItemStack inputItem = MadTileEntityFactory.findItemStack(inputIngredient.getModID(), inputIngredient.getInternalName(), inputIngredient.getAmount(), inputIngredient.getMetaDamage());

                    boolean searchResult = false;
                    if (inputItem != null)
                    {
                        searchResult = true;
                        totalLoadedRecipeItems++;
                        resultInputPrint += "=SUCCESS";
                        inputIngredient.loadRecipe(inputItem.copy());
                    }
                    else
                    {
                        searchResult = false;
                        totalFailedRecipeItems++;
                        resultInputPrint += "=FAILED";
                    }

                    // Debugging!
                    if (!searchResult)
                    {
                        MadScience.logger.info(resultInputPrint);
                    }
                }
                else
                {
                    MadScience.logger.info("[" + this.machineName + "]Bad Input: " + inputIngredient.getSlotType().name());
                }
            }

            // Recipe results.
            for (IMadRecipeComponent outputResult : machineRecipe.getOutputResultsArray())
            {
                if (outputResult.getSlotType().name().toLowerCase().contains("output"))
                {
                    String resultOutputPrint = "[" + this.machineName + "]Output Result " + outputResult.getModID() + ":" + outputResult.getInternalName();
                    ItemStack outputStack = MadTileEntityFactory.findItemStack(outputResult.getModID(), outputResult.getInternalName(), outputResult.getAmount(), outputResult.getMetaDamage());

                    boolean searchResult = false;
                    if (outputStack != null)
                    {
                        searchResult = true;
                        totalLoadedRecipeItems++;
                        resultOutputPrint += "=SUCCESS";
                        outputResult.loadRecipe(outputStack.copy());
                    }
                    else
                    {
                        searchResult = false;
                        totalFailedRecipeItems++;
                        resultOutputPrint += "=FAILED";
                    }

                    if (!searchResult)
                    {
                        MadScience.logger.info(resultOutputPrint);
                    }
                }
                else
                {
                    MadScience.logger.info("[" + this.machineName + "]Bad Output: " + outputResult.getSlotType().name());
                }
            }
        }

        MadScience.logger.info("[" + this.machineName + "]Total Loaded Recipe Items: " + totalLoadedRecipeItems);
        MadScience.logger.info("[" + this.machineName + "]Failed To Load Recipe Items: " + totalFailedRecipeItems);
    }

    public void setSoundArchive(IMadSound[] soundArchive)
    {
        this.soundArchive = soundArchive;
    }

    public String[] getSoundArchiveFilenameArray()
    {
        // Throw an exception if we have already done this! Bad programmer!
        if (soundArchiveLoaded)
        {
            throw new IllegalStateException("Sound archive for '" + this.machineName + "' has already been loaded!");
        }

        // Close the door behind us.
        this.soundArchiveLoaded = true;

        // List we will be returning if everything goes well.
        List<String> soundFileList = new ArrayList<String>();

        // Begin looping through all the sounds that makeup this particular product.
        for (int i = 0; i < soundArchive.length; i++)
        {
            // Grab the interface to particular sound.
            IMadSound machineSound = soundArchive[i];
            if (machineSound == null)
            {
                continue;
            }

            // Check if this sound is random one and needs multiple files checked.
            if (machineSound.getSoundPlaybackMode().equals(MadSoundPlaybackTypeEnum.RANDOM) && machineSound.getSoundRandomVariance() > 0)
            {
                // Add all the files that makeup this array of random files.
                // Note: Minecraft will automatically play a random sound if named File1,2,3.
                for (int x = 1; x < machineSound.getSoundRandomVariance(); x = x++)
                {
                    soundFileList.add(MadScience.ID + ":" + this.machineName + "/" + machineSound.getSoundNameWithoutExtension() + x + "." + machineSound.getSoundExtension());
                }
                continue;
            }
            else
            {
                // Add just the individual sound file.
                soundFileList.add(MadScience.ID + ":" + this.machineName + "/" + machineSound.getSoundNameWithExtension());
                continue;
            }
        }

        // Convert list of strings into string array.
        String[] finalList = soundFileList.toArray(new String[soundFileList.size()]);

        // Check if the array is larger than zero and not null.
        if (finalList != null && finalList.length > 0)
        {
            return finalList;
        }

        // Default response is to return nothing.
        return null;
    }

    public void playTriggerSound(MadSoundTriggerEnum trigger, int x, int y, int z, World worldObj)
    {
        // Locate the sound to play based on trigger enumeration.
        for (int i = 0; i < soundArchive.length; i++)
        {
            IMadSound machineSound = soundArchive[i];
            if (machineSound == null)
            {
                continue;
            }

            // Check if input matches the sound type.
            // Note: Multiple triggers of same type will break on first instance.
            if (machineSound.getSoundTrigger().equals(trigger))
            {
                String soundName = MadScience.ID + ":" + this.machineName + "." + machineSound.getSoundNameWithoutExtension();
                worldObj.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, soundName, 1.0F, 1.0F);
                MadScience.logger.info("[" + this.machineName + "]Playing Sound: " + soundName);
                break;
            }
        }
    }

    public IMadRecipe[] getRecipeArchive()
    {
        return recipeArchive;
    }

    public void setRecipeArchive(IMadRecipe[] recipeArchive)
    {
        this.recipeArchive = recipeArchive;
    }
}
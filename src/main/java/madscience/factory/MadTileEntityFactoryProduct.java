package madscience.factory;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import madscience.MadScience;
import madscience.factory.buttons.IMadGUIButton;
import madscience.factory.controls.IMadGUIControl;
import madscience.factory.energy.IMadEnergy;
import madscience.factory.fluids.IMadFluid;
import madscience.factory.recipes.IMadRecipe;
import madscience.factory.recipes.IMadRecipeComponent;
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

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cpw.mods.fml.client.FMLClientHandler;

public class MadTileEntityFactoryProduct
{
    private MadTileEntityFactoryProductData data = new MadTileEntityFactoryProductData(
            new IMadSlotContainer[0],
            new IMadGUIControl[0],
            new IMadGUIButton[0],
            new IMadFluid[0],
            new IMadEnergy[0],
            new IMadSound[0],
            new IMadRecipe[0]);

    MadTileEntityFactoryProduct(String machineName, int blockID, String logicClassNamespace)
    {
        // Important information that makes up the basics of any machine.
        this.data.machineName = machineName;
        this.data.blockID = blockID;
        
        // Load the class file from the provided namespace.
        this.data.logicClassFullyQualifiedName = logicClassNamespace;
        try
        {
            // Forcefully cast whatever we attempt to load as MadTileEntity since that is the only thing we will work with.
            this.data.tileEntityLogicClass = (Class<? extends MadTileEntityPrefab>) Class.forName(logicClassNamespace);
        }
        catch (ClassNotFoundException err)
        {
            err.printStackTrace();
        }

        // Setup the block which will create the tile entity once placed in the game world.
        this.data.blockContainer = (BlockContainer) new MadTileEntityBlockTemplate(this);
    }

    public int getBlockID()
    {
        return data.blockID;
    }

    /** Returns client GUI which can be pushed to renderer. */
    public MadGUITemplate getClientGUIElement(InventoryPlayer playerEntity, MadTileEntityPrefab worldEntity)
    {
        return new MadGUITemplate(playerEntity, worldEntity);
    }

    public IMadSlotContainer[] getContainerTemplate()
    {
        return data.containerTemplate;
    }

    public IMadEnergy[] getEnergySupported()
    {
        return data.energySupported;
    }

    public IMadFluid[] getFluidsSupported()
    {
        return data.fluidsSupported;
    }

    public IMadGUIButton[] getGuiButtonTemplate()
    {
        return data.guiButtonTemplate;
    }

    public IMadGUIControl[] getGuiControlsTemplate()
    {
        return data.guiControlsTemplate;
    }

    /** Returns machines internal name as it should be referenced by rest of code. */
    public String getMachineName()
    {
        return data.machineName;
    }

    /** Returns container slots for storing items in machines on server. */
    public MadContainerTemplate getServerGUIElement(InventoryPlayer playerEntity, MadTileEntityPrefab worldEntity)
    {
        return new MadContainerTemplate(playerEntity, worldEntity);
    }

    public void setContainerTemplate(IMadSlotContainer[] containerTemplate)
    {
        this.data.containerTemplate = containerTemplate;
    }

    public void setEnergySupported(IMadEnergy[] energySupported)
    {
        this.data.energySupported = energySupported;
    }

    public void setFluidsSupported(IMadFluid[] fluidSupported)
    {
        this.data.fluidsSupported = fluidSupported;
    }

    public void setGuiButtonTemplate(IMadGUIButton[] guiButtonTemplate)
    {
        this.data.guiButtonTemplate = guiButtonTemplate;
    }

    public void setGuiControlsTemplate(IMadGUIControl[] guiControlsTemplate)
    {
        this.data.guiControlsTemplate = guiControlsTemplate;
    }

    public BlockContainer getBlockContainer()
    {
        return data.blockContainer;
    }

    public MadTileEntityPrefab getNewTileEntityLogicClassInstance()
    {
        // Attempt to create a new instance of the logic class that was passed to us at creation.
        try
        {
            return data.tileEntityLogicClass.getDeclaredConstructor(MadTileEntityFactoryProduct.class).newInstance(this);
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
        return data.tileEntityLogicClass;
    }

    public void loadRecipes()
    {
        // Complain if we are somehow already loaded!
        if (this.data.recipeArchive == null)
        {
            MadScience.logger.warning("[MadTileEntityFactoryProduct]Unable to load recipes for '" + this.data.machineName + "' because it has none!");
            return;
        }

        // Recipe archives are nested so we need to loop through them to get to first one (even if it is the only one).
        int totalLoadedRecipeItems = 0;
        int totalFailedRecipeItems = 0;
        for (IMadRecipe machineRecipe : this.data.recipeArchive)
        {
            // Recipe ingredients.
            for (IMadRecipeComponent inputIngredient : machineRecipe.getInputIngredientsArray())
            {
                if (inputIngredient.getSlotType().name().toLowerCase().contains("input"))
                {
                    // Starting building output string now, append to it below.
                    String resultInputPrint = "[" + this.data.machineName + "]Input Ingredient " + inputIngredient.getModID() + ":" + inputIngredient.getInternalName();

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
                    MadScience.logger.info("[" + this.data.machineName + "]Bad Input: " + inputIngredient.getSlotType().name());
                }
            }

            // Recipe results.
            for (IMadRecipeComponent outputResult : machineRecipe.getOutputResultsArray())
            {
                if (outputResult.getSlotType().name().toLowerCase().contains("output"))
                {
                    String resultOutputPrint = "[" + this.data.machineName + "]Output Result " + outputResult.getModID() + ":" + outputResult.getInternalName();
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
                    MadScience.logger.info("[" + this.data.machineName + "]Bad Output: " + outputResult.getSlotType().name());
                }
            }
        }

        MadScience.logger.info("[" + this.data.machineName + "]Total Loaded Recipe Items: " + totalLoadedRecipeItems);
        MadScience.logger.info("[" + this.data.machineName + "]Failed To Load Recipe Items: " + totalFailedRecipeItems);
    }

    public void setSoundArchive(IMadSound[] soundArchive)
    {
        this.data.soundArchive = soundArchive;
    }

    public String[] getSoundArchiveFilenameArray()
    {
        // Throw an exception if we have already done this! Bad programmer!
        if (data.soundArchiveLoaded)
        {
            throw new IllegalStateException("Sound archive for '" + this.data.machineName + "' has already been loaded!");
        }

        // Close the door behind us.
        this.data.soundArchiveLoaded = true;

        // List we will be returning if everything goes well.
        List<String> soundFileList = new ArrayList<String>();

        // Begin looping through all the sounds that makeup this particular product.
        for (int i = 0; i < data.soundArchive.length; i++)
        {
            // Grab the interface to particular sound.
            IMadSound machineSound = data.soundArchive[i];
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
                    soundFileList.add(MadScience.ID + ":" + this.data.machineName + "/" + machineSound.getSoundNameWithoutExtension() + x + "." + machineSound.getSoundExtension());
                }
                continue;
            }
            else
            {
                // Add just the individual sound file.
                soundFileList.add(MadScience.ID + ":" + this.data.machineName + "/" + machineSound.getSoundNameWithExtension());
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
        for (int i = 0; i < data.soundArchive.length; i++)
        {
            IMadSound machineSound = data.soundArchive[i];
            if (machineSound == null)
            {
                continue;
            }

            // Check if input matches the sound type.
            // Note: Multiple triggers of same type will break on first instance.
            if (machineSound.getSoundTrigger().equals(trigger))
            {
                String soundName = MadScience.ID + ":" + this.data.machineName + "." + machineSound.getSoundNameWithoutExtension();
                worldObj.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, soundName, 1.0F, 1.0F);
                //MadScience.logger.info("[" + this.machineName + "]Playing Sound: " + soundName);
                break;
            }
        }
    }
    
    private static String GetValidFileName(String fileName)
    {
        String cleanedFilename = null;
        try
        {
            cleanedFilename = new String(fileName.getBytes(), "UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        cleanedFilename = cleanedFilename.replaceAll("[\\?\\\\/:|<>\\*]", " "); //filter ? \ / : | < > *
        cleanedFilename = cleanedFilename.replaceAll("\\s+", "_");
        return cleanedFilename;
    }

    public IMadRecipe[] getRecipeArchive()
    {
        return data.recipeArchive;
    }

    public void setRecipeArchive(IMadRecipe[] recipeArchive)
    {
        this.data.recipeArchive = recipeArchive;
    }
    
    public void dumpJSONToDisk()
    {
        // Create a JSON builder that makes nice human-readable entries and only uses the fields we specified. 
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
        
        // Convert the data portion of our tile entity factory product to JSON string.
        String json = gson.toJson(data);
        
        // Save this information to the disk!
        File dataDir = FMLClientHandler.instance().getClient().mcDataDir;
        try
        {
            FileUtils.writeStringToFile(new File(dataDir, "json/" + GetValidFileName(data.machineName) + ".json"), json);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
        // Debugging!
        System.out.println(json);
    }

    public MadTileEntityFactoryProductData getData()
    {
        return data;
    }

    public void setData(MadTileEntityFactoryProductData data)
    {
        this.data = data;
    }
}
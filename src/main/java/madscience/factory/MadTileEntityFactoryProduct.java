package madscience.factory;

import java.util.ArrayList;
import java.util.List;

import madscience.factory.buttons.MadGUIButton;
import madscience.factory.controls.MadGUIControl;
import madscience.factory.crafting.MadCraftingComponent;
import madscience.factory.crafting.MadCraftingRecipe;
import madscience.factory.crafting.MadCraftingRecipeTypeEnum;
import madscience.factory.damage.MadDamage;
import madscience.factory.energy.MadEnergy;
import madscience.factory.fluids.MadFluid;
import madscience.factory.heat.MadHeat;
import madscience.factory.mod.MadMod;
import madscience.factory.model.MadModel;
import madscience.factory.recipes.MadRecipe;
import madscience.factory.recipes.MadRecipeComponent;
import madscience.factory.slotcontainers.MadSlotContainer;
import madscience.factory.sounds.MadSound;
import madscience.factory.sounds.MadSoundPlaybackTypeEnum;
import madscience.factory.sounds.MadSoundTriggerEnum;
import madscience.factory.tileentity.MadContainerTemplate;
import madscience.factory.tileentity.MadGUITemplate;
import madscience.factory.tileentity.MadTileEntityBlockTemplate;
import madscience.factory.tileentity.prefab.MadTileEntityPrefab;
import net.minecraft.block.BlockContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.common.registry.GameRegistry;

public class MadTileEntityFactoryProduct
{
    /** Serializable class that holds references to all needed data to create a factory machine. */
    private MadTileEntityFactoryProductData data = null;
    
    /** Stores if we have loaded the sounds or not, allowing us to throw an error if called again */
    private boolean soundArchiveLoaded;
    
    /** Stores block container class which provides Forge/MC with server side slot info. */
    private BlockContainer blockContainer;
    
    /** Stores reference to tile entity class itself which makes up logic or brains of this machine. */
    private Class<? extends MadTileEntityPrefab> tileEntityLogicClass;

    MadTileEntityFactoryProduct(MadTileEntityFactoryProductData machineData)
    {
        super();
        
        // Transfer the configuration data into the product which we will use to create everything we need.
        this.data = machineData;
        
        // Load the class file from the provided namespace.
        this.tileEntityLogicClass = getLogicClassByNamespace(machineData.getLogicClassFullyQualifiedName());

        // Setup the block which will create the tile entity once placed in the game world.
        this.blockContainer = (BlockContainer) new MadTileEntityBlockTemplate(this);
    }

    /** Converts fully qualified domain name for a given class into that class. It must be based on MineTileEntityPrefab or loading will fail! */
    private Class<? extends MadTileEntityPrefab> getLogicClassByNamespace(String fullyQualifiedName)
    {
        // Check input data for null.
        if (fullyQualifiedName == null)
        {
            return null;
        }
        
        // Check if there is any string data at all.
        if (fullyQualifiedName.isEmpty())
        {
            return null;
        }
        
        Class<? extends MadTileEntityPrefab> tempClass = null;
        try
        {
            // Forcefully cast whatever we attempt to load as MadTileEntity since that is the only thing we will work with.
            tempClass = (Class<? extends MadTileEntityPrefab>) Class.forName(fullyQualifiedName);
            if (tempClass != null)
            {
                return  tempClass;
            }
        }
        catch (ClassNotFoundException err)
        {
            err.printStackTrace();
        }
        
        // Default response is to return nothing!
        return null;
    }
    
    public void setBlockID(int newID)
    {
        data.setBlockID(newID);
    }

    public int getBlockID()
    {
        return data.getBlockID();
    }

    /** Returns client GUI which can be pushed to renderer. */
    public MadGUITemplate getClientGUIElement(InventoryPlayer playerEntity, MadTileEntityPrefab worldEntity)
    {
        return new MadGUITemplate(playerEntity, worldEntity);
    }

    public MadSlotContainer[] getContainerTemplate()
    {
        return data.getContainerTemplate();
    }

    public MadEnergy[] getEnergySupported()
    {
        return data.getEnergySupported();
    }

    public MadFluid[] getFluidsSupported()
    {
        return data.getFluidsSupported();
    }

    public MadGUIButton[] getGuiButtonTemplate()
    {
        return data.getGuiButtonTemplate();
    }

    public MadGUIControl[] getGuiControlsTemplate()
    {
        return data.getGuiControlsTemplate();
    }

    /** Returns machines internal name as it should be referenced by rest of code. */
    public String getMachineName()
    {
        return data.getMachineName();
    }

    /** Returns container slots for storing items in machines on server. */
    public MadContainerTemplate getServerGUIElement(InventoryPlayer playerEntity, MadTileEntityPrefab worldEntity)
    {
        return new MadContainerTemplate(playerEntity, worldEntity);
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
    
    /** Loads recipes associated with the machine itself so that it may be crafted in the game.
     * This should only be run once, and in postInit after all other mods have been loaded so items from them may be queried. */
    public void loadCraftingRecipes()
    {
        // Complain if we are somehow already loaded!
        if (this.data.getCraftingRecipes() == null)
        {
            MadMod.log().warning("[MadTileEntityFactoryProduct]Unable to load crafting recipes for '" + this.data.getMachineName() + "' because it has none!");
            return;
        }
        
        int totalLoadedRecipeItems = 0;
        int totalFailedRecipeItems = 0;
        for (MadCraftingRecipe machineCraftingRecipe : this.data.getCraftingRecipes())
        {
            // Keeps track if anything bad happened during crafting recipe creation.
            boolean errorsWithAssociation = true;
            
            // Reference to recipe we want to create, Minecraft/Forge accepts them as object array.
            List<Object> craftingInputArray = new ArrayList<Object>();
            
            // Crafting strings that makeup reference to what numbers goto what slot on the grid.
            Object[] craftingGridLayout = new Object[9];
            
            // Initialize each slot with empty space since in Minecraft/Forge this means null/nothing.
            for (int i = 0; i < craftingGridLayout.length; i++)
            {
                craftingGridLayout[i] = " ";
            }
            
            for (MadCraftingComponent recipeComponent : machineCraftingRecipe.getCraftingRecipeComponents())
            {
                // Starting building output string now, append to it below.
                String resultInputPrint = "[" + this.data.getMachineName() + "]Crafting Component " + recipeComponent.getModID() + ":" + recipeComponent.getInternalName();

                // Query game registry and vanilla blocks and items for the incoming name in an attempt to turn it into an itemstack.
                ItemStack[] inputItem = MadTileEntityFactory.getItemStackFromString(recipeComponent.getModID(), recipeComponent.getInternalName(), recipeComponent.getAmount(), recipeComponent.getMetaDamage());

                boolean searchResult = false;
                if (inputItem != null)
                {
                    searchResult = true;
                    totalLoadedRecipeItems++;
                    resultInputPrint += "=SUCCESS";
                    recipeComponent.associateItemStackToRecipeComponent(inputItem);
                }
                else
                {
                    searchResult = false;
                    totalFailedRecipeItems++;
                    resultInputPrint += "=FAILED";
                }
                
                // Mark this entry as being valid since we made it this far.
                errorsWithAssociation = false;
               
                // Only add the craft slot number if we are making a shaped recipe.
                if (machineCraftingRecipe.getCraftingRecipeType() == MadCraftingRecipeTypeEnum.SHAPED)
                {
                    // First, place the prepared crafting component into grid as a string.
                    craftingGridLayout[recipeComponent.getCraftingGridPosition()] = String.valueOf(recipeComponent.getCraftingGridPosition());
                    
                    // Second, add to input array the crafting grid number.
                    craftingInputArray.add(recipeComponent.getCraftingGridPositionAsCharacter());
                }
                
                // Third, add to input array the crafting ingredient (we always will want this).
                for (ItemStack craftComponent : recipeComponent.getItemStackArray())
                {
                    craftingInputArray.add(craftComponent);
                }

                // Debugging!
                if (!searchResult)
                {
                    MadMod.log().info(resultInputPrint);
                }
            }
            
            // If the above crafting component registration went well then we will add them.
            if (!errorsWithAssociation)
            {
                // Depending on type of recipe, very different things need to happen with the data we have collected so far.
                switch (machineCraftingRecipe.getCraftingRecipeType())
                {
                    case SHAPED:
                    {
                        // Move the string array into proper alignment for recipe input, any slots without components become blank spaces this is intentional.
                        String[] craftingGridLayoutFinal = new String[3];
                        craftingGridLayoutFinal[0] = String.valueOf(craftingGridLayout[0]) + String.valueOf(craftingGridLayout[1]) + String.valueOf(craftingGridLayout[2]);
                        craftingGridLayoutFinal[1] = String.valueOf(craftingGridLayout[3]) + String.valueOf(craftingGridLayout[4]) + String.valueOf(craftingGridLayout[5]);
                        craftingGridLayoutFinal[2] = String.valueOf(craftingGridLayout[6]) + String.valueOf(craftingGridLayout[7]) + String.valueOf(craftingGridLayout[8]);
                        
                        // Construct the final object array recipe input.
                        List<Object> recipeFinalInput = new ArrayList<Object>();
                        recipeFinalInput.add(craftingGridLayoutFinal[0]);
                        recipeFinalInput.add(craftingGridLayoutFinal[1]);
                        recipeFinalInput.add(craftingGridLayoutFinal[2]);
                        recipeFinalInput.addAll(craftingInputArray);
                        
                        // Actually add the recipe to Minecraft/Forge.
                        try
                        {
                            GameRegistry.addShapedRecipe(new ItemStack(this.getBlockContainer(),
                                    machineCraftingRecipe.getCraftingAmount()),
                                    recipeFinalInput.toArray(new Object[]{}));
                        }
                        catch (Exception err)
                        {
                            MadMod.log().info("[" + this.getMachineName() + "]Unable to load shaped crafting recipe!");
                        }
                        
                        break;
                    }
                    case SHAPELESS:
                    {              
                        try
                        {
                            // Shapeless recipes are a little easier since we only need to pass in the item array.
                            GameRegistry.addShapelessRecipe(new ItemStack(this.getBlockContainer(),
                                    machineCraftingRecipe.getCraftingAmount()),
                                    craftingInputArray.toArray(new Object[]{}));
                        }
                        catch (Exception err)
                        {
                            MadMod.log().info("[" + this.getMachineName() + "]Unable to load shapeless crafting recipe!");
                        }
                        
                        break;
                    }
                    default:
                    {
                        // Nothing to see here.
                        break;
                    }
                }
            }
            else
            {
                MadMod.log().info("[" + this.data.getMachineName() + "]Bad Crafting Recipe: " + machineCraftingRecipe.getCraftingRecipeType().name());
            }
        }
        
        // Information about total loaded and failed.
        MadMod.log().info("[" + this.data.getMachineName() + "]Total Loaded Crafting Recipe Items: " + totalLoadedRecipeItems);
        MadMod.log().info("[" + this.data.getMachineName() + "]Failed To Load Crafting Recipe Items: " + totalFailedRecipeItems);
    }

    /** Loads all recipes associated with slots inside of the machine allowing it to verify it's list against actual game items.
     * This should only be run once, and in postInit after all other mods have been loaded so items from them may be queried. */
    public void loadMachineInternalRecipes()
    {
        // Complain if we are somehow already loaded!
        if (this.data.getRecipeArchive() == null)
        {
            MadMod.log().warning("[MadTileEntityFactoryProduct]Unable to load internal machine recipes for '" + this.data.getMachineName() + "' because it has none!");
            return;
        }

        // Recipe archives are nested so we need to loop through them to get to first one (even if it is the only one).
        int totalLoadedRecipeItems = 0;
        int totalFailedRecipeItems = 0;
        for (MadRecipe machineRecipe : this.data.getRecipeArchive())
        {
            // INPUT: Recipe ingredients.
            for (MadRecipeComponent inputIngredient : machineRecipe.getInputIngredientsArray())
            {
                if (inputIngredient.getSlotType().name().toLowerCase().contains("input"))
                {
                    // Starting building output string now, append to it below.
                    String resultInputPrint = "[" + this.data.getMachineName() + "]Input Ingredient " + inputIngredient.getModID() + ":" + inputIngredient.getInternalName();

                    // Query game registry and vanilla blocks and items for the incoming name in an attempt to turn it into an itemstack.
                    ItemStack[] inputItem = MadTileEntityFactory.getItemStackFromString(inputIngredient.getModID(), inputIngredient.getInternalName(), inputIngredient.getAmount(), inputIngredient.getMetaDamage());

                    boolean searchResult = false;
                    if (inputItem != null)
                    {
                        searchResult = true;
                        totalLoadedRecipeItems += inputItem.length;
                        resultInputPrint += "=SUCCESS";
                        inputIngredient.associateItemStackToRecipeComponent(inputItem);
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
                        MadMod.log().info(resultInputPrint);
                    }
                }
                else
                {
                    MadMod.log().info("[" + this.data.getMachineName() + "]Bad Input: " + inputIngredient.getSlotType().name());
                }
            }

            // OUTPUT: Recipe results.
            for (MadRecipeComponent outputResult : machineRecipe.getOutputResultsArray())
            {
                if (outputResult.getSlotType().name().toLowerCase().contains("output"))
                {
                    String resultOutputPrint = "[" + this.data.getMachineName() + "]Output Result " + outputResult.getModID() + ":" + outputResult.getInternalName();
                    ItemStack[] outputStack = MadTileEntityFactory.getItemStackFromString(outputResult.getModID(), outputResult.getInternalName(), outputResult.getAmount(), outputResult.getMetaDamage());

                    boolean searchResult = false;
                    if (outputStack != null)
                    {
                        searchResult = true;
                        totalLoadedRecipeItems += outputStack.length;
                        resultOutputPrint += "=SUCCESS";
                        outputResult.associateItemStackToRecipeComponent(outputStack);
                    }
                    else
                    {
                        searchResult = false;
                        totalFailedRecipeItems++;
                        resultOutputPrint += "=FAILED";
                    }

                    if (!searchResult)
                    {
                        MadMod.log().info(resultOutputPrint);
                    }
                }
                else
                {
                    MadMod.log().info("[" + this.data.getMachineName() + "]Bad Output: " + outputResult.getSlotType().name());
                }
            }
        }

        MadMod.log().info("[" + this.data.getMachineName() + "]Total Loaded Recipe Items: " + totalLoadedRecipeItems);
        MadMod.log().info("[" + this.data.getMachineName() + "]Failed To Load Recipe Items: " + totalFailedRecipeItems);
    }

    /** Populates an internal list of sounds associated with this machine and returns them as a string array to be ready by Minecraft/Forge. */
    public String[] loadSoundArchive()
    {
        // Throw an exception if we have already done this! Bad programmer!
        if (soundArchiveLoaded)
        {
            throw new IllegalStateException("Sound archive for '" + this.data.getMachineName() + "' has already been loaded!");
        }

        // Close the door behind us.
        this.soundArchiveLoaded = true;

        // List we will be returning if everything goes well.
        List<String> soundFileList = new ArrayList<String>();

        // Begin looping through all the sounds that makeup this particular product.
        for (int i = 0; i < data.getSoundArchive().length; i++)
        {
            // Grab the interface to particular sound.
            MadSound machineSound = data.getSoundArchive()[i];
            if (machineSound == null)
            {
                continue;
            }

            // Store pathing information to where this sound lives.
            String machineSoundPath = MadMod.ID + ":" + this.data.getMachineName() + "/";
            machineSound.setResourcePath(machineSoundPath);
            
            // For future reference we have been here.
            machineSound.setLoaded();
            
            // Reference to this sound globally which anything can address.
            MadMod.addSoundToArchive(machineSound.getSoundNameWithoutExtension(), machineSound);
            
            // Check if this sound is random one and needs multiple files checked.
            if (machineSound.getSoundPlaybackMode().equals(MadSoundPlaybackTypeEnum.RANDOM) && machineSound.getSoundRandomVariance() > 0)
            {
                // Add all the files that makeup this array of random files.
                // Note: Minecraft will automatically play a random sound if named File1,2,3.
                for (int x = 1; x <= machineSound.getSoundRandomVariance(); x++)
                {
                    MadMod.log().info("[" + this.getMachineName() + "]Loading random sound " + machineSound.getSoundNameWithoutExtension() + String.valueOf(x) + " " + String.valueOf(x) + "/" + String.valueOf(machineSound.getSoundRandomVariance()));
                    String fullRandomSoundPath = machineSoundPath + machineSound.getSoundNameWithoutExtension() + x + "." + machineSound.getSoundExtension();  
                    
                    // Add to list which gets returned to Minecraft/Forge for actual loading.
                    soundFileList.add(fullRandomSoundPath);
                }
            }
            else
            {
                // Add just the individual sound file.
                MadMod.log().info("[" + this.getMachineName() + "]Loading sound " + machineSound.getSoundNameWithoutExtension());
                String fullSingleSoundPath = machineSoundPath + machineSound.getSoundNameWithExtension(); 
                soundFileList.add(fullSingleSoundPath);
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
        for (int i = 0; i < data.getSoundArchive().length; i++)
        {
            MadSound machineSound = data.getSoundArchive()[i];
            if (machineSound == null)
            {
                continue;
            }

            // Check if input matches the sound type.
            // Note: Multiple triggers of same type will break on first instance.
            if (machineSound.getSoundTrigger().equals(trigger))
            {
                String soundName = MadMod.ID + ":" + this.data.getMachineName() + "." + machineSound.getSoundNameWithoutExtension();
                worldObj.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, soundName, 1.0F, 1.0F);
                //MadScience.logger.info("[" + this.machineName + "]Playing Sound: " + soundName);
                break;
            }
        }
    }
    
    public MadCraftingRecipe[] getCraftingRecipe()
    {
        return data.getCraftingRecipes();
    }
    
    public MadRecipe[] getRecipeArchive()
    {
        return data.getRecipeArchive();
    }

    public MadTileEntityFactoryProductData getData()
    {
        return data;
    }

    public MadModel getModelArchive()
    {
        return data.getModelArchive();
    }

    public MadHeat[] getHeatLevelsSupported()
    {
        return data.getHeatLevelsSupported();
    }

    public MadDamage[] getDamageTrackingSupported()
    {
        return data.getDamageTrackingSupported();
    }
}
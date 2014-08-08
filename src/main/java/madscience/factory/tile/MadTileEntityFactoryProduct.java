package madscience.factory.tile;

import java.util.ArrayList;
import java.util.List;

import madscience.factory.block.MadGhostBlockData;
import madscience.factory.button.MadGUIButton;
import madscience.factory.container.MadSlotContainer;
import madscience.factory.control.MadGUIControl;
import madscience.factory.crafting.MadCraftingComponent;
import madscience.factory.crafting.MadCraftingRecipe;
import madscience.factory.crafting.MadCraftingRecipeTypeEnum;
import madscience.factory.damage.MadDamage;
import madscience.factory.energy.MadEnergy;
import madscience.factory.fluid.MadFluid;
import madscience.factory.heat.MadHeat;
import madscience.factory.mod.MadMod;
import madscience.factory.model.MadModel;
import madscience.factory.model.MadModelBounds;
import madscience.factory.model.MadModelPosition;
import madscience.factory.recipe.MadRecipe;
import madscience.factory.recipe.MadRecipeComponent;
import madscience.factory.sound.MadSound;
import madscience.factory.sound.MadSoundPlaybackTypeEnum;
import madscience.factory.sound.MadSoundTriggerEnum;
import madscience.factory.tile.prefab.MadTileEntityPrefab;
import madscience.factory.tile.template.MadContainerTemplate;
import madscience.factory.tile.template.MadGUITemplate;
import madscience.factory.tile.template.MadTileEntityBlockTemplate;
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
    
    public MadTileEntityFactoryProduct(MadTileEntityFactoryProductData machineData)
    {
        super();
        
        // Transfer the configuration data into the product which we will use to create everything we need.
        this.data = machineData;
        
        // Load the class file from the provided namespace.
        this.tileEntityLogicClass = getLogicClassByNamespace(machineData.getLogicClassFullyQualifiedName());

        // Setup the block which will create the tile entity once placed in the game world.
        this.blockContainer = new MadTileEntityBlockTemplate(this);
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
                return tempClass;
            }
        }
        catch (ClassNotFoundException err)
        {
            err.printStackTrace();
        }
        
        // Default response is to return nothing!
        return null;
    }
    
    /** Allows configuration manager from Minecraft/Forge to manipulate expected block ID for a given machine based on ID manager defaults
     *  or from generated configuration file that is apart of Forge and FML to make modding life easier. */
    public void setBlockID(int newID)
    {
        data.setBlockID(newID);
    }

    /** Returns registered ID for this block that was given to us by Minecraft/Forge configuration file. */
    public int getBlockID()
    {
        return data.getBlockID();
    }
    
    /** Determines how hard this block is to break with conventional Minecraft/Forge tools such as pickaxe. */
    public float getBlockHardness()
    {
        return data.getBlockHardness();
    }
    
    /** Returns how resistant this block is to explosions, higher values make for more resistance. */
    public float getBlockExplosionResistance()
    {
        return data.getExplosionResistance();
    }

    /** Returns client GUI which can be pushed to renderer. */
    public MadGUITemplate getClientGUIElement(InventoryPlayer playerEntity, MadTileEntityPrefab worldEntity)
    {
        return new MadGUITemplate(playerEntity, worldEntity);
    }

    /** Determines how many slots for holding Minecraft/Forge ItemStacks need to be created. Holds all reference data to slot type and intended purpose for each. */
    public MadSlotContainer[] getContainerTemplate()
    {
        return data.getContainerTemplate();
    }

    /** Contains all energy related information for this machine such as consumption, production rates and how much can be extracted or inserted in a given tick. */
    public MadEnergy[] getEnergySupported()
    {
        return data.getEnergySupported();
    }

    /** Contains valid reference to FluidRegistry fluids that need to be associated with this machine so it may contain milli-buckets (mB). */
    public MadFluid[] getFluidsSupported()
    {
        return data.getFluidsSupported();
    }

    /** All buttons that need to be created on this GUI with their registered callback and intended functions. */
    public MadGUIButton[] getGuiButtonTemplate()
    {
        return data.getGuiButtonTemplate();
    }

    /** All controls for GUI that are not buttons or slots (such as progress bars, animation, etc). */
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

    /** Returns object containing all information about how many input and output slots and their intended functions for this machine. */
    public BlockContainer getBlockContainer()
    {
        return blockContainer;
    }

    /** Consumes namespace string for logic class and attempts to load and create a new instance of it for use in the game world. */
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

    /** Returns already created instance of machine logic class loaded from namespace string. */
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
                ItemStack[] inputItem = MadRecipe.getItemStackFromString(recipeComponent.getModID(), recipeComponent.getInternalName(), recipeComponent.getAmount(), recipeComponent.getMetaDamage());

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
                    ItemStack[] inputItem = MadRecipe.getItemStackFromString(inputIngredient.getModID(), inputIngredient.getInternalName(), inputIngredient.getAmount(), inputIngredient.getMetaDamage());

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
                    ItemStack[] outputStack = MadRecipe.getItemStackFromString(outputResult.getModID(), outputResult.getInternalName(), outputResult.getAmount(), outputResult.getMetaDamage());

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
    
    /** Plays a sound registered to this machine by name without extension. */
    public void playSoundByName(String soundNameWithoutExtension, int x, int y, int z, World worldObj)
    {
        // Check if the sound to play actually exists or not.
        for (int i = 0; i < data.getSoundArchive().length; i++)
        {
            MadSound machineSound = data.getSoundArchive()[i];
            if (machineSound == null)
            {
                continue;
            }

            // Check if sound name matches the one from internal list.
            if (machineSound.getSoundNameWithoutExtension().equals(soundNameWithoutExtension))
            {
                String soundName = MadMod.ID + ":" + this.data.getMachineName() + "." + machineSound.getSoundNameWithoutExtension();
                worldObj.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, soundName, 1.0F, 1.0F);
                //MadMod.log().info("[" + this.getMachineName() + "]Playing Sound By Name: " + soundName);
                break;
            }
        }
    }

    /** Plays a sound registered to specific machine event such as working, idle, destroyed, etc. */
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
            // Note: Multiple sounds with same trigger will play one after the other.
            if (machineSound.getSoundTrigger().equals(trigger))
            {
                String soundName = MadMod.ID + ":" + this.data.getMachineName() + "." + machineSound.getSoundNameWithoutExtension();
                worldObj.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, soundName, 1.0F, 1.0F);
                //MadMod.log().info("[" + this.getMachineName() + "]Playing Trigger Sound: " + soundName);
            }
        }
    }
    
    /** Returns array of ingredients for Minecraft/Forge to load into default crafting grid to craft this machine in the game. */
    public MadCraftingRecipe[] getCraftingRecipe()
    {
        return data.getCraftingRecipes();
    }
    
    /** Returns every possible recipe that will be associated with the internal workings of this machine (not for crafting. */
    public MadRecipe[] getRecipeArchive()
    {
        return data.getRecipeArchive();
    }

    /** Returns all of the data associated with this given machine, this is used for serializing machine JSON to disk. */
    public MadTileEntityFactoryProductData getData()
    {
        return data;
    }

    /** Contains all rendering information for clients to render MadTechneModels and load them from AdvancedModelLoader. */
    public MadModel getModelArchive()
    {
        return data.getModelArchive();
    }

    /** Determines if heat levels inside of the machine need to be tracked for data changes. */
    public MadHeat[] getHeatLevelsSupported()
    {
        return data.getHeatLevelsSupported();
    }

    /** Determines if damage tracking is supported on this tile and if we should be tracking it for data changes. */
    public MadDamage[] getDamageTrackingSupported()
    {
        return data.getDamageTrackingSupported();
    }

    /** Returns object with two vector positions inside of it representing block bounds that will be rendered on client. */
    public MadModelBounds getBlockBounds()
    {
        return data.getBoundingBox();
    }

    /** Sets bounds that will be rendered around the block on clients. This bounds in game looks like a thin black line outlining the entire machine. */
    public void setBlockBoundsDefault()
    {
        MadModelBounds defaultBoundingBox = new MadModelBounds(
                new MadModelPosition(0.0F, 0.0F, 0.0F),
                new MadModelPosition(1.0F, 1.0F, 1.0F));
        
        this.data.setBoundingBox(defaultBoundingBox);
    }
    
    /** Sets default multi-block configuration which entails no extra blocks except for the center one that should be the machine (0x0x0). */
    public void setMultiBlockDefaults()
    {
        // Default is no ghost blocks on any sides with only the machine itself in the center.
        MadGhostBlockData machineGhostBlocks = new MadGhostBlockData(0, 0, 0);
        this.data.setMultiBlockConfiguration(machineGhostBlocks);
    }

    /** Grabs ghost/multi block configuration vector which determines how many invisible blocks need to be places on sides and top. */
    public MadGhostBlockData getMultiBlockConfiguration()
    {
        return data.getMultiBlockConfiguration();
    }
}
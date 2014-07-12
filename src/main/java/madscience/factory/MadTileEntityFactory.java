package madscience.factory;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import madscience.MadScience;
import madscience.factory.buttons.IMadGUIButton;
import madscience.factory.controls.IMadGUIControl;
import madscience.factory.energy.IMadEnergy;
import madscience.factory.fluids.IMadFluid;
import madscience.factory.recipes.IMadRecipe;
import madscience.factory.slotcontainers.IMadSlotContainer;
import madscience.factory.sounds.IMadSound;
import madscience.items.ItemBlockTooltip;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class MadTileEntityFactory
{
    /** Mapping of machine names to created products. */
    private static final Map<String, MadTileEntityFactoryProduct> registeredMachines = new LinkedHashMap<String, MadTileEntityFactoryProduct>();

    public static MadTileEntityFactoryProduct getMachineInfo(String id)
    {
        //MadScience.logger.info("[MadTileEntityFactory]Querying machine: " + id);
        return registeredMachines.get(id);
    }

    public static Collection<MadTileEntityFactoryProduct> getMachineInfoList()
    {
        return Collections.unmodifiableCollection(registeredMachines.values());
    }

    private static boolean isValidMachineID(String id)
    {
        return !registeredMachines.containsKey(id);
    }
    
    /** Removes extra tags from internal and unlocalized names in preparation for matchmaking in recipe system. */
    private static String cleanTag(String tag) 
    {
        return tag.replace("minecraft.", "").replaceFirst("^tile\\.", "").replaceFirst("^item\\.", "");
    }
    
    public static void printUnlocalizedItemNames()
    {
        for(Item potentialMCItem : Item.itemsList) 
        {
            if(potentialMCItem == null)
            {
                continue;
            }
            
            MadScience.logger.info(potentialMCItem.getUnlocalizedName());
        }
        
        for(Block potentialMCBlock : Block.blocksList) 
        {
            if(potentialMCBlock == null)
            {
                continue;
            }
            
            MadScience.logger.info(potentialMCBlock.getUnlocalizedName());
        }
    }
    
    /** Return itemstack from GameRegistry or from vanilla Item/Block list. */
    public static ItemStack findItemStack(String modID, String itemName, int stackSize, int metaData)
    {
        // Mod items and blocks query.
        ItemStack potentialModItem = GameRegistry.findItemStack(modID, itemName, stackSize);
        if (potentialModItem != null)
        {
            return potentialModItem;
        }
        
        // Only continue if modID is for minecraft vanilla items or blocks.
        if (!modID.equals("minecraft"))
        {
            return null;
        }
        
        // Vanilla item query.
        for(Item potentialMCItem : Item.itemsList) 
        {
            if(potentialMCItem == null)
            {
                continue;
            }
            
            ItemStack vanillaItemStack = new ItemStack(potentialMCItem, metaData, stackSize);
            
            if (vanillaItemStack != null)
            {
                try
                {
                    String vanillaItemUnlocalizedName = cleanTag(vanillaItemStack.getUnlocalizedName());
                    //MadScience.logger.info(vanillaItemStack.getUnlocalizedName());
                    
                    if (vanillaItemUnlocalizedName.equals(itemName))
                    {
                        return vanillaItemStack;
                    }
                }
                catch (Exception err)
                {
                    continue;
                }
            }
        }
        
        // Vanilla block query.
        for(Block potentialMCBlock : Block.blocksList) 
        {
            if(potentialMCBlock == null)
            {
                continue;
            }
            
            ItemStack vanillaItemStack = new ItemStack(potentialMCBlock, metaData, stackSize);
            
            if (vanillaItemStack != null)
            {
                try
                {
                    String vanillaBlockUnlocalizedName = cleanTag(vanillaItemStack.getUnlocalizedName());
                    //MadScience.logger.info(vanillaItemStack.getUnlocalizedName());
                    
                    if (vanillaBlockUnlocalizedName.equals(itemName))
                    {
                        return vanillaItemStack;
                    }
                }
                catch (Exception err)
                {
                    continue;
                }
            }
        }
        
        // Last ditch effort to save compatibility starts here!
        if (itemName.equals("dyePowder") || itemName.equals("dye"))
        {
            // Return whatever type of dye was requested.
            return new ItemStack(Item.dyePowder, metaData, stackSize);
        }
        
        if (itemName.equals("wool") || itemName.equals("cloth"))
        {
            // Return whatever color wool was requested.
            return new ItemStack(Block.cloth, metaData, stackSize);
        }
        
        // Default response is to return nothing.
        return null;
    }

    public static boolean registerMachine(String machineName, int blockID,
            String logicClassNamespace,
            IMadSlotContainer[] containerTemplate,
            IMadGUIControl[] guiTemplate,
            IMadGUIButton[] buttonTemplate,
            IMadFluid[] fluidsTemplate,
            IMadEnergy[] energyTemplate,
            IMadSound[] soundArchive,
            IMadRecipe[] recipeArchive) throws IllegalArgumentException
    {
        // Setup basic tile entity template object, the name and ID are unchangeable and referenced only.
        MadTileEntityFactoryProduct tileEntityProduct = new MadTileEntityFactoryProduct(
                machineName,
                blockID,
                logicClassNamespace);

        // Adds required things most tile entities need such as GUI, slots, energy and fluid support, sounds, etc.
        tileEntityProduct.setContainerTemplate(containerTemplate);
        tileEntityProduct.setGuiControlsTemplate(guiTemplate);
        tileEntityProduct.setGuiButtonTemplate(buttonTemplate);
        tileEntityProduct.setFluidsSupported(fluidsTemplate);
        tileEntityProduct.setEnergySupported(energyTemplate);
        tileEntityProduct.setSoundArchive(soundArchive);
        tileEntityProduct.setRecipeArchive(recipeArchive);
        
        // Check to make sure we have not added this machine before.
        if (!isValidMachineID(machineName))
        {
            throw new IllegalArgumentException("Duplicate MadTileEntityFactoryProduct '" + machineName + "' was added. Execution halted!");
        }

        // Debugging!
        MadScience.logger.info("[MadTileEntityFactory]Registering machine: " + machineName);
        
        // Actually register the machine with the product listing.
        registeredMachines.put(tileEntityProduct.getMachineName(), tileEntityProduct);
        
        // Register the machine with Minecraft/Forge.
        GameRegistry.registerTileEntity(tileEntityProduct.getTileEntityLogicClass(), machineName);
        GameRegistry.registerBlock(tileEntityProduct.getBlockContainer(), ItemBlockTooltip.class, MadScience.ID + machineName);
        MadScience.proxy.registerRenderingHandler(blockID);
        
        return true;
    }

    /** Serializes all registered machines to disk. Meat for developer use only. Use with caution! */
    public static void dumpAllMachineJSON()
    {
        for (Iterator iterator = getMachineInfoList().iterator(); iterator.hasNext();)
        {
            MadTileEntityFactoryProduct registeredMachine = (MadTileEntityFactoryProduct) iterator.next();
            if (registeredMachine != null)
            {
                registeredMachine.dumpJSONToDisk();
            }
        }
    }
}
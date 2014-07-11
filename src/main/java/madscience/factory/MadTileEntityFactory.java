package madscience.factory;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import madscience.MadScience;
import madscience.factory.buttons.MadGUIButtonInterface;
import madscience.factory.controls.MadGUIControlInterface;
import madscience.factory.energy.MadEnergyInterface;
import madscience.factory.fluids.MadFluidInterface;
import madscience.factory.recipes.MadRecipeInterface;
import madscience.factory.slotcontainers.MadSlotContainerInterface;
import madscience.factory.sounds.MadSoundInterface;
import madscience.factory.tileentity.MadTileEntity;

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
            
            ItemStack vanillaItemStack = new ItemStack(potentialMCItem, 0, stackSize);
            
            if (vanillaItemStack != null)
            {
                try
                {
                    String vanillaItemUnlocalizedName = cleanTag(vanillaItemStack.getUnlocalizedName());
                    
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
            
            ItemStack vanillaItemStack = new ItemStack(potentialMCBlock, 0, stackSize);
            
            if (vanillaItemStack != null)
            {
                try
                {
                    String vanillaBlockUnlocalizedName = cleanTag(vanillaItemStack.getUnlocalizedName());
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
        
        // Default response is to return nothing.
        return null;
    }

    public static boolean registerMachine(String machineName, int blockID,
            Class<? extends MadTileEntity> logicClass,
            MadSlotContainerInterface[] containerTemplate,
            MadGUIControlInterface[] guiTemplate,
            MadGUIButtonInterface[] buttonTemplate,
            MadFluidInterface[] fluidsTemplate,
            MadEnergyInterface[] energyTemplate,
            MadSoundInterface[] soundArchive,
            MadRecipeInterface[] recipeArchive) throws IllegalArgumentException
    {
        // Setup basic tile entity template object, the name and ID are unchangeable and referenced only.
        MadTileEntityFactoryProduct tileEntityProduct = new MadTileEntityFactoryProduct(
                machineName,
                blockID,
                logicClass);

        // Adds required things most tile entities need such as GUI, slots, energy and fluid support, sounds, etc.
        tileEntityProduct.setContainerTemplate(containerTemplate);
        tileEntityProduct.setGuiControlsTemplate(guiTemplate);
        tileEntityProduct.setGuiButtonTemplate(buttonTemplate);
        tileEntityProduct.setFluidsSupported(fluidsTemplate);
        tileEntityProduct.setEnergySupported(energyTemplate);
        tileEntityProduct.setSoundArchive(soundArchive);
        tileEntityProduct.setRecipeArchive(recipeArchive);
        
        // Check to make sure we have not added this machine before.
        if (!isValidMachineID(tileEntityProduct.getMachineName()))
        {
            throw new IllegalArgumentException("Duplicate MadTileEntityFactoryProduct '" + tileEntityProduct.getMachineName() + "' was added. Execution halted!");
        }

        // Debugging!
        MadScience.logger.info("[MadTileEntityFactory]Registering machine: " + tileEntityProduct.getMachineName());
        
        // Actually register the machine with the product listing.
        registeredMachines.put(tileEntityProduct.getMachineName(), tileEntityProduct);
        
        return true;
    }

}
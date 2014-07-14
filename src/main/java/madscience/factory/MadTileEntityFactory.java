package madscience.factory;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import madscience.MadScience;
import madscience.items.ItemBlockTooltip;
import madscience.util.MadUtils;
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

    /** Return itemstack from GameRegistry or from vanilla Item/Block list. */
    static ItemStack getItemStackFromString(String modID, String itemName, int stackSize, int metaData)
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
        for (Item potentialMCItem : Item.itemsList)
        {
            if (potentialMCItem == null)
            {
                continue;
            }

            ItemStack vanillaItemStack = new ItemStack(potentialMCItem, metaData, stackSize);

            if (vanillaItemStack != null)
            {
                try
                {
                    String vanillaItemUnlocalizedName = MadUtils.cleanTag(vanillaItemStack.getUnlocalizedName());
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
        for (Block potentialMCBlock : Block.blocksList)
        {
            if (potentialMCBlock == null)
            {
                continue;
            }

            ItemStack vanillaItemStack = new ItemStack(potentialMCBlock, metaData, stackSize);

            if (vanillaItemStack != null)
            {
                try
                {
                    String vanillaBlockUnlocalizedName = MadUtils.cleanTag(vanillaItemStack.getUnlocalizedName());
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

    public static MadTileEntityFactoryProduct registerMachine(MadTileEntityFactoryProductData machineData) throws IllegalArgumentException
    {
        // Pass the data object into the product to activate it, creates needed data structures inside it based on data supplied.
        MadTileEntityFactoryProduct tileEntityProduct = new MadTileEntityFactoryProduct(machineData);

        // Check to make sure we have not added this machine before.
        if (!isValidMachineID(tileEntityProduct.getMachineName()))
        {
            throw new IllegalArgumentException("Duplicate MadTileEntityFactoryProduct '" + tileEntityProduct.getMachineName() + "' was added. Execution halted!");
        }

        // Debugging!
        MadScience.logger.info("[MadTileEntityFactory]Registering machine: " + tileEntityProduct.getMachineName());

        // Actually register the machine with the product listing.
        registeredMachines.put(tileEntityProduct.getMachineName(), tileEntityProduct);

        // Register the machine with Minecraft/Forge.
        GameRegistry.registerTileEntity(tileEntityProduct.getTileEntityLogicClass(), tileEntityProduct.getMachineName());
        GameRegistry.registerBlock(tileEntityProduct.getBlockContainer(), ItemBlockTooltip.class, MadScience.ID + tileEntityProduct.getMachineName());
        MadScience.proxy.registerRenderingHandler(tileEntityProduct.getBlockID());

        return tileEntityProduct;
    }
}
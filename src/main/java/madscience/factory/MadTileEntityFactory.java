package madscience.factory;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import madscience.MadForgeMod;
import madscience.factory.mod.MadMod;
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

    public static MadTileEntityFactoryProductData[] getMachineDataList()
    {
        // Loop through every registered machine in the system.
        Set<MadTileEntityFactoryProductData> allMachines = new HashSet<MadTileEntityFactoryProductData>();
        for (Iterator iterator = MadTileEntityFactory.getMachineInfoList().iterator(); iterator.hasNext();)
        {
            MadTileEntityFactoryProduct registeredMachine = (MadTileEntityFactoryProduct) iterator.next();
            if (registeredMachine != null)
            {
                // Add the machines configuration data to our list for saving.
                allMachines.add(registeredMachine.getData());
            }
        }

        return allMachines.toArray(new MadTileEntityFactoryProductData[]{});
    }

    private static boolean isValidMachineID(String id)
    {
        return !registeredMachines.containsKey(id);
    }

    /** Return itemstack from GameRegistry or from vanilla Item/Block list. */
    static ItemStack[] getItemStackFromString(String modID, String itemName, int stackSize, String metaDataText)
    {
        // Reference list we will return at the end of work.
        ArrayList<ItemStack> itemsToAssociate = new ArrayList<ItemStack>();
        Collection<String> unlocalizedNames = new TreeSet<String>(Collator.getInstance());

        // Reference to if this recipe deals with wildcard (*) values in meta/damage or name.
        boolean wildcardName = itemName.contains("*");
        boolean wildcardMeta = metaDataText.contains("*");

        // Reference to actual metadata since we have to parse it.
        int metaData = 0;
        if (!wildcardMeta)
        {
            // If not using wildcard for damage then parse it as integer.
            metaData = Integer.parseInt(metaDataText);
        }

        // Only lookup individual itemstacks if we are not hunting wildcards.
        if (!wildcardName)
        {
            // Mod items and blocks query.
            ItemStack potentialModItem = GameRegistry.findItemStack(modID, itemName, stackSize);
            if (potentialModItem != null)
            {
                if (!unlocalizedNames.contains(MadUtils.cleanTag(potentialModItem.getUnlocalizedName())))
                {
                    itemsToAssociate.add(potentialModItem);
                    unlocalizedNames.add(MadUtils.cleanTag(potentialModItem.getUnlocalizedName()));
                }
            }
        }

        // Vanilla item query.
        for (Item potentialMCItem : Item.itemsList)
        {
            if (potentialMCItem == null)
            {
                continue;
            }

            // Check if we need to accommodate metadata.
            int tmpMeta = 0;
            if (wildcardMeta)
            {
                tmpMeta = 0;
            }
            else
            {
                // Use given value if not wild.
                tmpMeta = metaData;
            }

            ItemStack vanillaItemStack = new ItemStack(potentialMCItem, stackSize, tmpMeta);

            if (vanillaItemStack != null)
            {
                try
                {
                    // Check if name contains wildcard value.
                    if (wildcardName && potentialMCItem.getUnlocalizedName().contains(itemName.replace("*", "")))
                    {
                        if (!unlocalizedNames.contains(vanillaItemStack.getUnlocalizedName()))
                        {
                            itemsToAssociate.add(vanillaItemStack);
                            unlocalizedNames.add(vanillaItemStack.getUnlocalizedName());
                        }
                    }
                    else if (!wildcardName && MadUtils.cleanTag(vanillaItemStack.getUnlocalizedName()).equals(itemName))
                    {
                        if (!unlocalizedNames.contains(MadUtils.cleanTag(vanillaItemStack.getUnlocalizedName())))
                        {
                            itemsToAssociate.add(vanillaItemStack);
                            unlocalizedNames.add(MadUtils.cleanTag(vanillaItemStack.getUnlocalizedName()));
                        }
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

            // Check if we need to accommodate metadata.
            int tmpMeta = 0;
            if (wildcardMeta)
            {
                tmpMeta = 0;
            }
            else
            {
                // Use given value if not wild.
                tmpMeta = metaData;
            }

            ItemStack vanillaItemStack = new ItemStack(potentialMCBlock, tmpMeta, stackSize);

            if (vanillaItemStack != null)
            {
                try
                {
                    // Check if name contains wildcard value.
                    if (wildcardName && vanillaItemStack.getUnlocalizedName().contains(itemName.replace("*", "")))
                    {
                        if (!unlocalizedNames.contains(vanillaItemStack.getUnlocalizedName()))
                        {
                            itemsToAssociate.add(vanillaItemStack);
                            unlocalizedNames.add(vanillaItemStack.getUnlocalizedName());
                        }
                    }
                    else if (!wildcardName && MadUtils.cleanTag(vanillaItemStack.getUnlocalizedName()).equals(itemName))
                    {
                        if (!unlocalizedNames.contains(MadUtils.cleanTag(vanillaItemStack.getUnlocalizedName())))
                        {
                            itemsToAssociate.add(vanillaItemStack);
                            unlocalizedNames.add(MadUtils.cleanTag(vanillaItemStack.getUnlocalizedName()));
                        }
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
            itemsToAssociate.add(new ItemStack(Item.dyePowder, stackSize, metaData));
        }

        if (itemName.equals("wool") || itemName.equals("cloth"))
        {
            // Return whatever color wool was requested.
            itemsToAssociate.add(new ItemStack(Block.cloth, stackSize, metaData));
        }

        // Check if we have items to return back after all that work.
        if (itemsToAssociate.size() > 0)
        {
            return itemsToAssociate.toArray(new ItemStack[]{});
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
        MadMod.log().info("[MadTileEntityFactory]Registering machine: " + tileEntityProduct.getMachineName());

        // Actually register the machine with the product listing.
        registeredMachines.put(tileEntityProduct.getMachineName(), tileEntityProduct);

        // Register the machine with Minecraft/Forge.
        GameRegistry.registerTileEntity(tileEntityProduct.getTileEntityLogicClass(), tileEntityProduct.getMachineName());
        GameRegistry.registerBlock(tileEntityProduct.getBlockContainer(), ItemBlockTooltip.class, MadMod.ID + tileEntityProduct.getMachineName());
        MadForgeMod.proxy.registerRenderingHandler(tileEntityProduct.getBlockID());

        return tileEntityProduct;
    }
}
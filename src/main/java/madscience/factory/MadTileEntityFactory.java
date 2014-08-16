package madscience.factory;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import madscience.MadForgeMod;
import madscience.factory.block.MadGhostBlockData;
import madscience.factory.itemblock.MadItemBlockTooltip;
import madscience.factory.mod.MadMod;
import madscience.factory.model.MadModel;
import madscience.factory.tile.MadTileEntityFactoryProduct;
import madscience.factory.tile.MadTileEntityFactoryProductData;
import cpw.mods.fml.common.registry.GameRegistry;

public class MadTileEntityFactory
{
    /** Prevents multiple instances of this class from being created. */
    private static MadTileEntityFactory instance;
    
    /** Mapping of machine names to created products. */
    private static final Map<String, MadTileEntityFactoryProduct> registeredMachines = new LinkedHashMap<String, MadTileEntityFactoryProduct>();
    
    public MadTileEntityFactory()
    {
        super();
    }
    
    /** Only a single instance of the tile entity factory may ever exist. */
    public static synchronized MadTileEntityFactory instance()
    {
        if (instance == null)
        {
            instance = new MadTileEntityFactory();
        }
        
        return instance;
    }

    /** Returns information for a particular machine based on name. */
    public MadTileEntityFactoryProduct getMachineInfo(String id)
    {
        return registeredMachines.get(id);
    }

    /** Returns a unmodifiable list of all registered machine by this factory. */
    public Collection<MadTileEntityFactoryProduct> getMachineInfoList()
    {
        return Collections.unmodifiableCollection(registeredMachines.values());
    }

    /** Returns all registered machines, but as data objects which may be serialized by JSON loader to disk. */
    public MadTileEntityFactoryProductData[] getMachineDataList()
    {
        // Loop through every registered machine in the system.
        Set<MadTileEntityFactoryProductData> allMachines = new HashSet<MadTileEntityFactoryProductData>();
        for (Iterator iterator = getMachineInfoList().iterator(); iterator.hasNext();)
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

    /** Determines is this machine name has already been registered by this factory. Hopefully this never returns true. */
    private boolean isValidMachineID(String id)
    {
        return !registeredMachines.containsKey(id);
    }

    /** Registers product data that is loaded from JSON or created manually in class files. This method will register the machine with our own internal systems
     *  but also all required Minecraft/Forge systems (whatever they may be since ForgeTeam like to change it all the time anyway). */
    public MadTileEntityFactoryProduct registerMachine(MadTileEntityFactoryProductData machineData) throws IllegalArgumentException
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
        
        // Check if rendering information is null and needs to be set to defaults.
        this.checkRenderingInformation(tileEntityProduct);
        
        // Ensure that ghost block config at minimum has the single block that is the machine.
        this.checkGhostBlockConfig(tileEntityProduct);

        // Actually register the machine with the product listing.
        registeredMachines.put(tileEntityProduct.getMachineName(), tileEntityProduct);
        
        // Register the machine with Minecraft/Forge.
        GameRegistry.registerTileEntity(tileEntityProduct.getTileEntityLogicClass(), tileEntityProduct.getMachineName());
        GameRegistry.registerBlock(tileEntityProduct.getBlockContainer(), MadItemBlockTooltip.class, MadMod.ID + tileEntityProduct.getMachineName());
        
        // Register client only information such as rendering and model information to the given machine.
        MadForgeMod.proxy.registerRenderingHandler(tileEntityProduct.getBlockID());

        return tileEntityProduct;
    }

    /** Ensures that machine being registered at very minimum specifies the single block that is itself with empty int based vector. */
    private void checkGhostBlockConfig(MadTileEntityFactoryProduct tileEntityProduct)
    {
        MadGhostBlockData machineGhostBlocks = tileEntityProduct.getMultiBlockConfiguration();
        if (machineGhostBlocks == null)
        {
            // Default is no ghost blocks on any sides with only the machine itself in the center.
            tileEntityProduct.setMultiBlockDefaults();
        }
    }

    /** Ensures that a given tile entity factory product will always have proper rendering information even if none is provided (for whatever reason). */
    private void checkRenderingInformation(MadTileEntityFactoryProduct tileEntityProduct)
    {
        // Check if model rendering information exists.
        MadModel renderingInformation = tileEntityProduct.getModelArchive();
        if (renderingInformation != null)
        {
            // Rendering information for tile as it would exist as an item block in players inventory.
            if (renderingInformation.getItemRenderInfoClone() == null)
            {
                MadMod.log().info("[" + tileEntityProduct.getMachineName() + "]Creating default ITEM rendering information where there is none.");
                renderingInformation.setItemRenderInfoDefaults();
            }
            
            // Rendering information for tile as it would exist in the game world as seen by the player and other players.
            if (renderingInformation.getWorldRenderInfoClone() == null)
            {
                MadMod.log().info("[" + tileEntityProduct.getMachineName() + "]Creating default WORLD rendering information where there is none.");
                renderingInformation.setWorldRenderInfoDefaults();
            }
        }
        else
        {
            throw new IllegalArgumentException("Cannot register MadTileEntityFactoryProduct '" + tileEntityProduct.getMachineName() + "'. This tile entity contains no models!");
        }
    }
}
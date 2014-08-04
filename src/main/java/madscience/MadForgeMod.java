package madscience;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;
import java.util.logging.Level;

import madscience.factory.MadItemFactory;
import madscience.factory.MadTileEntityFactory;
import madscience.factory.item.MadItemFactoryProduct;
import madscience.factory.item.MadItemFactoryProductData;
import madscience.factory.mod.MadMod;
import madscience.factory.tile.MadTileEntityFactoryProduct;
import madscience.factory.tile.MadTileEntityFactoryProductData;
import madscience.network.MadPacketHandler;
import madscience.proxy.CommonProxy;
import net.minecraft.launchwrapper.LogWrapper;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLFingerprintViolationEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = MadMod.ID, name = MadMod.NAME, version = MadMod.VERSION_FULL, useMetadata = false, acceptedMinecraftVersions = MadMod.MINECRAFT_VERSION, dependencies = MadMod.DEPENDENCIES)
@NetworkMod(channels = { MadMod.CHANNEL_NAME }, packetHandler = MadPacketHandler.class, clientSideRequired = true, serverSideRequired = false)
public class MadForgeMod
{
    // Proxy that runs commands based on where they are from so we can separate server and client logic easily.
    @SidedProxy(clientSide = MadMod.CLIENT_PROXY, serverSide = MadMod.SERVER_PROXY)
    public static CommonProxy proxy;

    // Public instance of our mod that Forge needs to hook us, based on our internal modid.
    @Instance(value = MadMod.CHANNEL_NAME)
    public static MadForgeMod instance;

    // Public extra data about our mod that Forge uses in the mods listing page for more information.
    @Mod.Metadata(MadMod.ID)
    private static ModMetadata metadata;

    // Hooks Forge's replacement openGUI function so we can route our own ID's to proper interfaces.
    private static MadGUI guiHandler = new MadGUI();

    @EventHandler
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void postInit(FMLPostInitializationEvent event) // NO_UCD (unused code)
    {
        // ---------------
        // FACTORY RECIPES
        // ---------------
        
        // Register item factory crafting recipes.
        Iterable<MadItemFactoryProduct> registeredItems = MadItemFactory.instance().getItemInfoList();
        for (Iterator iterator = registeredItems.iterator(); iterator.hasNext();)
        {
            MadItemFactoryProduct registeredItem = (MadItemFactoryProduct) iterator.next();
            if (registeredItem != null)
            {                
                // Recipes for crafting the item (if one exists, since most are made by machines).
                registeredItem.loadCraftingRecipes();
            }
        }
        
        // Loop through all the tile entity factory objects and populate their recipe ItemStacks.
        Iterable<MadTileEntityFactoryProduct> registeredMachines = MadTileEntityFactory.instance().getMachineInfoList();
        for (Iterator iterator = registeredMachines.iterator(); iterator.hasNext();)
        {
            MadTileEntityFactoryProduct registeredMachine = (MadTileEntityFactoryProduct) iterator.next();
            if (registeredMachine != null)
            {
                // Recipes that pertain to machine itself, association slots with items they should have in them.
                registeredMachine.loadMachineInternalRecipes();
                
                // Recipes for crafting the machine itself, registered with Minecraft/Forge GameRegistry.
                registeredMachine.loadCraftingRecipes();
            }
        }
        
        // TODO: Register crafting recipes that are not tied to any particular machine or item.
        
        // ----------------
        // NOT ENOUGH ITEMS
        // ----------------
        
        // Interface with NEI and attempt to call functions from it if it exists.
        // Note: This method was given by Alex_hawks, buy him a beer if you see him!
        if (Loader.isModLoaded("NotEnoughItems"))
        {
            try
            {
                Class clazz = Class.forName("codechicken.nei.api.API");
                Method m = clazz.getMethod("hideItem", Integer.TYPE);

                // Magazine Loader Ghost Block.
                //m.invoke(null, MadFurnaces.MAGLOADERGHOST.blockID);
            }
            catch (Throwable err)
            {
                MadMod.log().log(Level.WARNING, "NEI Integration has failed...");
                MadMod.log().log(Level.WARNING, "Please email devs@madsciencemod.com the following stacktrace.");
                err.printStackTrace();
                MadMod.log().log(Level.WARNING, "Spamming console to make more obvious...");
                for (int i = 0; i < 15; i++)
                {
                    MadMod.log().log(Level.WARNING, "Something Broke. See above.");
                }
            }

        }
        
        // ---------
        // DEBUGGING
        // ---------
        
        // Prints out all internal names.
        proxy.dumpUnlocalizedNames();
        
        // Dumps all registered machines JSON to disk.
        proxy.dumpAllMachineJSON();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) // NO_UCD (unused code)
    {
        // Registers sound handler which will be called to load sounds added to event bus for sounds.
        proxy.registerSoundHandler();
        
        // Registers GUI handler which allows server and client to map ID's to GUI's.
        NetworkRegistry.instance().registerGuiHandler(MadForgeMod.instance, MadForgeMod.guiHandler);
        
        // Check Mad Science Jenkins build server for latest build numbers to compare with running one.
        MadUpdates.checkJenkinsBuildNumbers();
    }

    @EventHandler
    public void invalidFingerprint(FMLFingerprintViolationEvent event) // NO_UCD (unused code)
    {
        // Check to see if fingerprint matches what we expect.
        if (MadMod.FINGERPRINT.equals(MadMod.FINGERPRINT))
        {
            LogWrapper.warning("The copy of " + MadMod.NAME + " that you are running passesd all verification and fingerprint checks. It has not been modified from original.");
        }
        else
        {
            LogWrapper.severe("The copy of " + MadMod.NAME + " that you are running has been modified from the original, and unpredictable things may happen. Please consider re-downloading the original version of the mod.");
        }
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) // NO_UCD (unused code)
    {
        // --------------
        // PRE-INT CONFIG
        // --------------
        
        // Register instance.
        instance = this;

        // Populate generic logger with instance provided by Minecraft/Forge. 
        MadMod.setLog(event.getModLog(), FMLLog.getLogger());
        
        // Generate and read our standardized Forge configuration file.
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        
        // Load existing configuration data.
        config.load();
        
        // Loop through loaded machines and get ID's for all those blocks.
        configureTileEntities(config);
        
        // Loop through loaded items and get ID's for all those items.
        configureItems(config);
        
        // Save any changed or added values to config.
        config.save();

        // Setup Mod Metadata for players to see in mod list with other mods.
        metadata.modId = MadMod.ID;
        metadata.name = MadMod.NAME;
        metadata.description = MadMod.DESCRIPTION;
        metadata.url = MadMod.HOME_URL;
        metadata.logoFile = MadMod.LOGO_PATH;
        metadata.version = MadMod.VMAJOR + "." + MadMod.VMINOR + MadMod.VREVISION;
        metadata.authorList = Arrays.asList(MadMod.AUTHORS);
        metadata.credits = MadMod.CREDITS;
        metadata.autogenerated = false;

        // Registers all items with factory.
        loadItems();
        
        // -----
        // ARMOR
        // -----

        // Note: Armor follows an array 0,1,2,3 for helmet, body, leggings, boots.
        MadEntities.createLabCoatGoggles(MadConfig.LABCOAT_GOGGLES, 0);
        MadEntities.createLabCoatBody(MadConfig.LABCOAT_BODY, 1);
        MadEntities.createLabCoatLeggings(MadConfig.LABCOAT_LEGGINGS, 2);
        
        // ------
        // FLUIDS
        // ------
        
        MadMod.log().info("Creating Fluids");

        // Creates both flowing and still variants of new fluid.
        MadFluids.createLiquidDNA(MadConfig.LIQUIDDNA, MadConfig.LIQUIDDNA_BUCKET);

        // Liquid Mutant DNA.
        MadFluids.createLiquidDNAMutant(MadConfig.LIQUIDDNA_MUTANT, MadConfig.LIQUIDDNA_MUTANT_BUCKET);

        // ------
        // BLOCKS
        // ------
        
        MadMod.log().info("Creating Blocks");

        // Abomination Egg
        MadBlocks.createAbominationEgg(MadConfig.ABOMINATIONEGG);

        // Enderslime Block
        MadBlocks.createEnderslimeBlock(MadConfig.ENDERSLIMEBLOCK);
        
        // Registers all tile entities with factory.
        loadTileEntities();
    }

    private void loadTileEntities()
    {
        MadMod.log().info("Creating Tile Entities");
        
        // Take the machines from loaded mod instance and register them with tile entity factory.
        MadTileEntityFactoryProductData[] machineData = MadMod.getUnregisteredMachines();
        for (int i = 0; i < machineData.length; i++) 
        {
            MadTileEntityFactoryProductData unregisteredMachine = machineData[i];
            MadTileEntityFactory.instance().registerMachine(unregisteredMachine);
        }
    }

    private void loadItems()
    {
        MadMod.log().info("Creating Items");
        
        // Grab all unregistered items from mod manager and pass them through the item factory.
        MadItemFactoryProductData[] itemData = MadMod.getUnregisteredItems();
        for (int i = 0; i < itemData.length; i++) 
        {
            MadItemFactoryProductData unregisteredItem = itemData[i];
            MadItemFactory.instance().registerItem(unregisteredItem);
        }
    }

    private void configureTileEntities(Configuration config)
    {
        MadTileEntityFactoryProductData[] machineData = MadMod.getUnregisteredMachines();
        for (int x = 0; x < machineData.length; x++) 
        {
            MadTileEntityFactoryProductData unregisteredMachine = machineData[x];
            
            // Get a new block ID from our ID manager, and set the block ID for our unregistered machine to whatever ID Manager decides.
            int defaultBlockID = MadMod.getNextBlockID();
            unregisteredMachine.setBlockID(defaultBlockID);
            
            // Get configuration file information, if there is any...
            int configBlockID = config.getBlock(Configuration.CATEGORY_BLOCK, unregisteredMachine.getMachineName(), unregisteredMachine.getBlockID()).getInt();
            
            // Check if unregistered machine default ID is different from read value.
            if (unregisteredMachine.getBlockID() == configBlockID)
            {
                MadMod.log().info("[" + unregisteredMachine.getMachineName() + "]Using default block ID of " + String.valueOf(configBlockID));
            }
            else
            {
                MadMod.log().info("[" + unregisteredMachine.getMachineName() + "]Using user configured block ID of " + String.valueOf(configBlockID));
            }
        }
    }

    private void configureItems(Configuration config)
    {
        MadItemFactoryProductData[] itemData = MadMod.getUnregisteredItems();
        for (int i = 0; i < itemData.length; i++)
        {
            MadItemFactoryProductData unregisteredItem = itemData[i];
            
            // Get new item ID from ID manager, use this as default.
            int defaultItemID = MadMod.getNextItemID();
            unregisteredItem.setItemID(defaultItemID);
            
            // Grab existing item item ID configuration if it exists.
            int configItemID = config.getItem(Configuration.CATEGORY_ITEM, unregisteredItem.getItemBaseName(), unregisteredItem.getItemID()).getInt();
            
            // Check if we used the configuration value or the ID manager one.
            if (unregisteredItem.getItemID() == configItemID)
            {
                MadMod.log().info("[" + unregisteredItem.getItemBaseName() + "]Using default item ID of " + String.valueOf(configItemID));
            }
            else
            {
                MadMod.log().info("[" + unregisteredItem.getItemBaseName() + "]Using user configured item ID of " + String.valueOf(configItemID));
            }
        }
    }
}

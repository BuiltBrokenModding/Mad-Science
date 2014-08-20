package madscience.factory.mod;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import madscience.factory.MadFluidFactory;
import madscience.factory.MadItemFactory;
import madscience.factory.MadTileEntityFactory;
import madscience.factory.creativetab.MadCreativeTab;
import madscience.factory.data.MadFluidFactoryProductData;
import madscience.factory.data.MadItemFactoryProductData;
import madscience.factory.data.MadTileEntityFactoryProductData;
import madscience.factory.recipe.MadRecipe;
import madscience.factory.sound.MadSound;
import madscience.util.IDManager;
import net.minecraft.item.ItemStack;

import com.google.common.base.Throwables;
import com.google.gson.Gson;

public class MadMod
{    
    // Identification.
    public static final String ID = "madscience";
    public static final String NAME = "Mad Science";
    public static final String CHANNEL_NAME = "madscience";
    
    // Metadata.
    public static final String DESCRIPTION = "Adds machines, items and mobs to create your own laboratory! Remember kids, science has no limits.. no bounds..";
    public static final String HOME_URL = "http://madsciencemod.com/";
    
    public static final String LOGO_PATH = "assets/madscience/logo.png";
    public static final String CREDITS = "Thanks to Prowler for the awesome assets!";
    public static final String[] AUTHORS = {"Maxwolf Goodliffe", "Fox Diller"};
    public static final String FINGERPRINT = "@FINGERPRINT@";
    public static final String MINECRAFT_VERSION = "[1.6.4,)";
    public static final String DEPENDENCIES = "required-after:Forge@[9.11.1.953,);after:BuildCraft|Energy;after:factorization;after:IC2;after:Railcraft;after:ThermalExpansion";
    
    // Proxy Classes Namespace.
    public static final String CLIENT_PROXY = "madscience.proxy.ClientProxy";
    public static final String SERVER_PROXY = "madscience.proxy.CommonProxy";
    
    // Full version string for internal reference by mod.
    public static final String VMAJOR = "@MAJOR@";
    public static final String VMINOR = "@MINOR@";
    public static final String VREVISION = "@REVIS@";
    public static final String VBUILD = "@BUILD@";
    public static final String VERSION_FULL = VMAJOR + "." + VMINOR + "." + VREVISION + "." + VBUILD;

    /** Update checker. */
    public static final String UPDATE_URL = "http://madsciencemod.com:8080/job/Mad%20Science/Release%20Version/api/xml?xpath=/freeStyleBuild/number";
    
    // Directories definition for assets and localization files.
    public static final String RESOURCE_DIRECTORY = "/assets/" + MadMod.ID + "/";
    public static final String BASE_DIRECTORY_NO_SLASH = MadMod.ID + "/";
    public static final String ASSET_DIRECTORY = "/assets/" + MadMod.ID + "/";
    public static final String TEXTURE_DIRECTORY = "textures/";
    public static final String MODEL_DIRECTORY = "models/";
    
    /** Determines how far away before data packets will no longer be sent to player in question. */
    public static final int PACKET_SEND_RADIUS = 25;
    
    /** Quick links to popular places. */
    public static final String MODEL_PATH = ASSET_DIRECTORY + MODEL_DIRECTORY;
    
    /** Allow only one instance to be created. */
    private static MadMod instance;
    
    /** Loaded data from JSON loader, useful for obtaining metadata for Forge and other data. */
    private static MadModData data = null;
    
    /** Hook standardized logging class so we can report data on the console without standard out. */
    private static Logger logger = null;
    
    /** Data container for loaded tiles from JSON loader and manual machines added by code. */
    private static List<MadTileEntityFactoryProductData> unregisteredMachines;
    
    /** Data container for loaded items from JSON loader and also manual items added by code. */
    private static List<MadItemFactoryProductData> unregisteredItems;
    
    /** Data container for loaded fluids from JSON loader and manual fluids added by code. */
    private static List<MadFluidFactoryProductData> unregisteredFluids;
    
    /** Holds an internal reference to every registered sound for easy reference. */
    private static Map<String, MadSound> soundArchive = new HashMap<String, MadSound>();
    
    /** Auto-incrementing configuration IDs. Use this to make sure no config ID is the same. */
    private static IDManager idManager;
    
    /** Defines a tab that is created in the Minecraft creative menu where all this mods items and blocks will be registered. */
    private static MadCreativeTab creativeTab = new MadCreativeTab(ID);
    
    private MadMod()
    {
        super();
    }
    
    @SuppressWarnings("ucd")
    public static synchronized MadMod instance() 
    {
        if (instance == null)
        {
            instance = new MadMod();
        }
        
        return instance;
     }
    
    static
    {
        // Name of the JSON file we are looking for along the classpath.
        String expectedFilename = "mod.json";
        
        // Input we expect to be filled with JSON for every machine we want to load.
        String jsonMachineInput = null;
        
        try
        {
            // Locate all the of JSON files stored in the machines asset folder.
            InputStream machinesJSON = MadMod.class.getClassLoader().getResourceAsStream(expectedFilename);
            
            if (machinesJSON != null)
            {
                // Read the entire contents of the input stream.
                BufferedReader reader = new BufferedReader(new InputStreamReader(machinesJSON));
                StringBuilder out = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) 
                {
                    out.append(line);
                }
                
                // Copy over the data we just read from the resource stream.
                jsonMachineInput = out.toString();
                
                // Cleanup!
                reader.close();
            }
            else
            {
                logger.info("Unable to locate machine master list '" + expectedFilename + "'");
            }
        }
        catch (Exception err)
        {
            throw Throwables.propagate(err);
        }
        
        // Parse the JSON string we got from resources into an array of product data.
        Gson gson = new Gson();
        MadModData loadedModData = null;
        loadedModData = gson.fromJson(jsonMachineInput, MadModData.class);
        
        // Data from JSON loader that is not for factories.
        setData(loadedModData);
        
        // Loads information from JSON MadModData into respective unregistered products.
        loadFluidJSON(loadedModData);
        loadItemJSON(loadedModData);
        loadTileEntityJSON(loadedModData);
        
        // Initializes ID manager with respective starting ranges for block and item ID's.
        idManager = new IDManager(loadedModData.getIDManagerBlockIndex(), loadedModData.getIDManagerItemIndex());
    }

    private static void loadFluidJSON(MadModData loadedModData)
    {
        // Load fluids (and fluid containers).
        unregisteredFluids = new ArrayList<MadFluidFactoryProductData>();
        
        if (loadedModData.getUnregisteredFluids() != null)
        {
            for (MadFluidFactoryProductData jsonFluid : loadedModData.getUnregisteredFluids())
            {
                unregisteredFluids.add(jsonFluid);
            }
        }
    }

    private static void loadTileEntityJSON(MadModData loadedModData)
    {
        // Load tiles (machines).
        unregisteredMachines = new ArrayList<MadTileEntityFactoryProductData>();
        
        if (loadedModData.getUnregisteredMachines() != null)
        {
            for (MadTileEntityFactoryProductData jsonMachine : loadedModData.getUnregisteredMachines())
            {
                unregisteredMachines.add(jsonMachine);
            }
        }
    }

    private static void loadItemJSON(MadModData loadedModData)
    {
        // Load Items.
        unregisteredItems = new ArrayList<MadItemFactoryProductData>();
        
        if (loadedModData.getUnregisteredItems() != null)
        {
            for (MadItemFactoryProductData jsonItem : loadedModData.getUnregisteredItems())
            {
                unregisteredItems.add(jsonItem);
            }
        }
    }

    /** Returns the next block ID based on initial index on creation. */
    public static int getNextBlockID()
    {
        return idManager.getNextBlockID();
    }

    /** Returns next item ID based on initial index. */
    public static int getNextItemID()
    {
        return idManager.getNextItemID();
    }
    
    /** Adds a sound to static hashmap which allows for easy querying of sound objects by short name. */
    public static void addSoundToArchive(String shortName, MadSound soundObject)
    {
        soundArchive.put(shortName, soundObject);
    }
    
    /** Returns reference to sound object if one exists. Returns null if sound is not found in archive. */
    public static MadSound getSoundByName(String soundNameWithoutExtension)
    {
        return soundArchive.get(soundNameWithoutExtension);
    }
    
    /** Used to serialize mod data to JSON file on disk during data dump. */
    public static MadModData getMadModData()
    {
        return new MadModData(
                ID,
                NAME,
                CHANNEL_NAME,
                DESCRIPTION,
                HOME_URL,
                LOGO_PATH,
                CREDITS,
                AUTHORS,
                FINGERPRINT,
                MINECRAFT_VERSION,
                DEPENDENCIES,
                CLIENT_PROXY,
                SERVER_PROXY,
                VMAJOR,
                VMINOR,
                VREVISION,
                VBUILD,
                UPDATE_URL,
                data.getIdManagerBlockIndex(),
                data.getIdManagerItemIndex(),
                data.getCreativeTabIconName(),
                data.getCreativeTabIconMetadata(),
                MadTileEntityFactory.instance().getMachineDataList(),
                MadItemFactory.instance().getItemDataList(),
                MadFluidFactory.instance().getFluidDataList());
    }

    public static MadCreativeTab getCreativeTab()
    {
        return creativeTab;
    }

    public static Logger log()
    {
        return logger;
    }

    public static void setLog(Logger lOGGER, Logger fmlLogger)
    {
        logger = lOGGER;
        logger.setParent(fmlLogger);
    }

    /** Returns array of all loaded and unregistered items. */
    public static MadItemFactoryProductData[] getUnregisteredItems()
    {
        return unregisteredItems.toArray(new MadItemFactoryProductData[]{});
    }
    
    /** Returns array of all loaded and unregistered tile entities. */
    public static MadTileEntityFactoryProductData[] getUnregisteredMachines()
    {
        return unregisteredMachines.toArray(new MadTileEntityFactoryProductData[]{});
    }

    public static MadFluidFactoryProductData[] getUnregisteredFluids()
    {
        return unregisteredFluids.toArray(new MadFluidFactoryProductData[]{});
    }

    /** Returns ItemStack for creative tab which should be set by the user. */
    public static ItemStack getCreativeTabIcon()
    {
        ItemStack possibleItem = MadRecipe.getItemStackFromString(
                ID,
                data.getCreativeTabIconName(),
                1,
                data.getCreativeTabIconMetadata());
        
        if (possibleItem != null)
        {
            return possibleItem;
        }
        
        return null;
    }

    public static void setData(MadModData data)
    {
        MadMod.data = data;
    }
}

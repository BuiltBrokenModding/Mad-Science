package madscience.mod;


import com.google.common.base.Throwables;
import com.google.gson.Gson;
import madscience.ModMetadata;
import madscience.factory.FluidFactory;
import madscience.factory.ItemFactory;
import madscience.factory.TileEntityFactory;
import madscience.fluid.UnregisteredFluid;
import madscience.item.UnregisteredItem;
import madscience.recipe.RecipeArchive;
import madscience.sound.SoundArchive;
import madscience.tile.UnregisteredMachine;
import madscience.util.IDManager;
import net.minecraft.item.ItemStack;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;


public class ModLoader
{
    // Directories definition for assets and localization files.
    public static final String RESOURCE_DIRECTORY = "/assets/" +
                                                    ModMetadata.ID + "/";
    public static final String BASE_DIRECTORY_NO_SLASH = ModMetadata.ID + "/";
    public static final String ASSET_DIRECTORY = "/assets/" +
                                                 ModMetadata.ID + "/";
    public static final String TEXTURE_DIRECTORY = "textures/";
    public static final String MODEL_DIRECTORY = "models/";

    /**
     * Determines how far away before data packets will no longer be sent to player in question.
     */
    public static final int PACKET_SEND_RADIUS = 25;

    /**
     * Quick links to popular places.
     */
    public static final String MODEL_PATH = ASSET_DIRECTORY + MODEL_DIRECTORY;

    /**
     * Allow only one instance to be created.
     */
    private static ModLoader instance;

    /**
     * Loaded data from JSON loader, useful for obtaining metadata for Forge and other data.
     */
    private static ModData data = null;

    /**
     * Hook standardized logging class so we can report data on the console without standard out.
     */
    private static Logger logger = null;

    /**
     * Data container for loaded tiles from JSON loader and manual machines added by code.
     */
    private static List<UnregisteredMachine> unregisteredMachines;

    /**
     * Data container for loaded items from JSON loader and also manual items added by code.
     */
    private static List<UnregisteredItem> unregisteredItems;

    /**
     * Data container for loaded fluids from JSON loader and manual fluids added by code.
     */
    private static List<UnregisteredFluid> unregisteredFluids;

    /**
     * Holds an internal reference to every registered sound for easy reference.
     */
    private static Map<String, SoundArchive> soundArchive = new HashMap<String, SoundArchive>();

    /**
     * Auto-incrementing configuration IDs. Use this to make sure no config ID is the same.
     */
    private static IDManager idManager;

    /**
     * Defines a tab that is created in the Minecraft creative menu where all this mods items and blocks will be registered.
     */
    private static CreativeTab creativeTab = new CreativeTab( ModMetadata.ID );

    private ModLoader()
    {
        super();
    }

    public static synchronized ModLoader instance()
    {
        if (instance == null)
        {
            instance = new ModLoader();
        }

        return instance;
    }

    static
    {
        // Name of the JSON file we are looking for along the classpath.
        String expectedFilename = ModMetadata.ID + ".json";

        // Input we expect to be filled with JSON for every machine we want to load.
        String jsonMachineInput = null;

        try
        {
            // Locate all the of JSON files stored in the machines asset folder.
            InputStream machinesJSON = ModLoader.class.getClassLoader().getResourceAsStream( expectedFilename );

            if (machinesJSON != null)
            {
                // Read the entire contents of the input stream.
                BufferedReader reader = new BufferedReader( new InputStreamReader( machinesJSON ) );
                StringBuilder out = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null)
                {
                    out.append( line );
                }

                // Copy over the data we just read from the resource stream.
                jsonMachineInput = out.toString();

                // Cleanup!
                reader.close();
            }
            else
            {
                logger.info( "Unable to locate machine master list '" +
                             expectedFilename + "'" );
            }
        }
        catch (Exception err)
        {
            throw Throwables.propagate( err );
        }

        // Parse the JSON string we got from resources into an array of product data.
        Gson gson = new Gson();
        ModData loadedModData = null;
        loadedModData = gson.fromJson( jsonMachineInput,
                                       ModData.class );

        // Data from JSON loader that is not for factories.
        setData( loadedModData );

        // Loads information from JSON ModData into respective unregistered products.
        loadFluidJSON( loadedModData );
        loadItemJSON( loadedModData );
        loadTileEntityJSON( loadedModData );

        // Initializes ID manager with respective starting ranges for block and item ID's.
        idManager = new IDManager( loadedModData.getIDManagerBlockIndex(),
                                   loadedModData.getIDManagerItemIndex() );
    }

    private static void loadFluidJSON(ModData loadedModData)
    {
        // Load fluids (and fluid containers).
        unregisteredFluids = new ArrayList<UnregisteredFluid>();

        if (loadedModData.getUnregisteredFluids() != null)
        {
            for (UnregisteredFluid jsonFluid : loadedModData.getUnregisteredFluids())
            {
                unregisteredFluids.add( jsonFluid );
            }
        }
    }

    private static void loadTileEntityJSON(ModData loadedModData)
    {
        // Load tiles (machines).
        unregisteredMachines = new ArrayList<UnregisteredMachine>();

        if (loadedModData.getUnregisteredMachines() != null)
        {
            for (UnregisteredMachine jsonMachine : loadedModData.getUnregisteredMachines())
            {
                unregisteredMachines.add( jsonMachine );
            }
        }
    }

    private static void loadItemJSON(ModData loadedModData)
    {
        // Load Items.
        unregisteredItems = new ArrayList<UnregisteredItem>();

        if (loadedModData.getUnregisteredItems() != null)
        {
            for (UnregisteredItem jsonItem : loadedModData.getUnregisteredItems())
            {
                unregisteredItems.add( jsonItem );
            }
        }
    }

    /**
     * Returns the next block ID based on initial index on creation.
     */
    public static int getNextBlockID()
    {
        return idManager.getNextBlockID();
    }

    /**
     * Returns next item ID based on initial index.
     */
    public static int getNextItemID()
    {
        return idManager.getNextItemID();
    }

    /**
     * Adds a sound to static hashmap which allows for easy querying of sound objects by short name.
     */
    public static void addSoundToArchive(String shortName, SoundArchive soundObject)
    {
        soundArchive.put( shortName,
                          soundObject );
    }

    /**
     * Returns reference to sound object if one exists. Returns null if sound is not found in archive.
     */
    public static SoundArchive getSoundByName(String soundNameWithoutExtension)
    {
        return soundArchive.get( soundNameWithoutExtension );
    }

    /**
     * Used to serialize mod data to JSON file on disk during data dump.
     */
    public static ModData getMadModData()
    {
        return new ModData( ModMetadata.ID,
                            ModMetadata.NAME,
                            ModMetadata.CHANNEL_NAME,
                            ModMetadata.DESCRIPTION,
                            ModMetadata.HOME_URL,
                            ModMetadata.LOGO_PATH,
                            ModMetadata.CREDITS,
                            ModMetadata.AUTHORS,
                            ModMetadata.FINGERPRINT,
                            ModMetadata.MINECRAFT_VERSION,
                            ModMetadata.DEPENDENCIES,
                            ModMetadata.CLIENT_PROXY,
                            ModMetadata.SERVER_PROXY,
                            ModMetadata.VMAJOR,
                            ModMetadata.VMINOR,
                            ModMetadata.VREVISION,
                            ModMetadata.VBUILD,
                            ModMetadata.UPDATE_URL,
                            data.getIdManagerBlockIndex(),
                            data.getIdManagerItemIndex(),
                            data.getCreativeTabIconName(),
                            data.getCreativeTabIconMetadata(),
                            TileEntityFactory.instance().getMachineDataList(),
                            ItemFactory.instance().getItemDataList(),
                            FluidFactory.instance().getFluidDataList() );
    }

    public static CreativeTab getCreativeTab()
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
        logger.setParent( fmlLogger );
    }

    /**
     * Returns array of all loaded and unregistered items.
     */
    public static UnregisteredItem[] getUnregisteredItems()
    {
        return unregisteredItems.toArray( new UnregisteredItem[] {} );
    }

    /**
     * Returns array of all loaded and unregistered tile entities.
     */
    public static UnregisteredMachine[] getUnregisteredMachines()
    {
        return unregisteredMachines.toArray( new UnregisteredMachine[] {} );
    }

    /**
     * Returns array of all loaded and unregistered fluids.
     */
    public static UnregisteredFluid[] getUnregisteredFluids()
    {
        return unregisteredFluids.toArray( new UnregisteredFluid[] {} );
    }

    /**
     * Returns ItemStack for creative tab which should be set by the user.
     */
    public static ItemStack getCreativeTabIcon()
    {
        ItemStack possibleItem = RecipeArchive.getItemStackFromString( ModMetadata.ID,
                                                                       data.getCreativeTabIconName(),
                                                                       1,
                                                                       data.getCreativeTabIconMetadata() );

        if (possibleItem != null)
        {
            return possibleItem;
        }

        return null;
    }

    public static void setData(ModData data)
    {
        ModLoader.data = data;
    }
}

package madscience.factory.mod;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import madscience.factory.MadTileEntityFactoryProductData;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

public final class MadMod
{    
    @Expose
    public static MadTileEntityFactoryProductData[] tileEntities = null;
    
    @Expose
    public static final String ID = "madscience";
    
    @Expose
    public static final String NAME = "Example Mod";
    
    @Expose
    public static final String CHANNEL_NAME = "exampleChannel";
    
    @Expose
    public static final String DESCRIPTION = "Example Description";
    
    @Expose
    public static final String HOME_URL = "http://examplemod.com/";
    
    @Expose
    public static final String UPDATE_URL = "http://ci.examplemod.com/";
    
    @Expose
    public static final String LOGO_PATH = "assets/logo.png";
    
    @Expose
    public static final String CREDITS = "Thank you Minecraft/Forge and MCP team!";
    
    @Expose
    public static final String[] AUTHORS = {"Example Modder1", "Example Modder2"};
    
    @Expose
    public static final String FINGERPRINT = "exampleFingerprint";
    
    @Expose
    public static final String MINECRAFT_VERSION = "";
    
    @Expose
    public static final String DEPENDENCIES = "";
    
    @Expose
    public static final String VMAJOR = "1";
    
    @Expose 
    public static final String VMINOR = "0";
    
    @Expose
    public static final String VREVISION = "0";
    
    @Expose
    public static final String VBUILD = "0";
    
    @Expose 
    public static final String CLIENT_PROXY = "";
    
    @Expose
    public static final String SERVER_PROXY = "";
    
    // Instance of this mod configuration.
    public static final MadMod INSTANCE = new MadMod();
    
    // Full version string for internal reference by mod.
    public static final String VERSION_FULL = VMAJOR + "." + VMINOR + VREVISION + "." + VBUILD; // NO_UCD (use private)
    
    // Directories definition for assets and localization files.
    public static final String RESOURCE_DIRECTORY = "/assets/" + MadMod.ID + "/";
    public static final String BASE_DIRECTORY_NO_SLASH = MadMod.ID + "/";
    public static final String ASSET_DIRECTORY = "/assets/" + MadMod.ID + "/";
    public static final String TEXTURE_DIRECTORY = "textures/";
    public static final String MODEL_DIRECTORY = "models/";
    
    // Quick links to popular places.
    public static final String MODEL_PATH = ASSET_DIRECTORY + MODEL_DIRECTORY;
    
    // Hook Forge's standardized logging class so we can report data on the console without standard out.
    public static Logger LOGGER;
    
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
                LOGGER.info("Unable to locate machine master list '" + expectedFilename + "'");
            }
        }
        catch (Exception err)
        {
            err.printStackTrace();
        }
        
        // Parse the JSON string we got from resources into an array of product data.
        Gson gson = new Gson();
        MadMod loadedModData = null;
        loadedModData = gson.fromJson(jsonMachineInput, MadMod.class);
        
        if (loadedModData != null)
        {            
            // Set the tile entity data onto the main mod object.
            setTileEntities(loadedModData.getTileEntities());
        }
    }
    
    public MadMod(MadTileEntityFactoryProductData[] tileEntities)
    {
        super();
        
        this.tileEntities = tileEntities;
    }
    
    public MadMod()
    {
        super();
    }

    public static MadTileEntityFactoryProductData[] getTileEntities()
    {
        return tileEntities;
    }

    public static void setTileEntities(MadTileEntityFactoryProductData[] tiles)
    {
        tileEntities = tiles;
    }

    public static MadMod getData()
    {
        // Create a new instance of a MadMod to return for JSON serialization.
        return new MadMod();
    }
}

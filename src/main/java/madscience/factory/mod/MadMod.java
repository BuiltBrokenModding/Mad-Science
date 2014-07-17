package madscience.factory.mod;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.logging.Logger;

import madscience.factory.MadTileEntityFactory;
import madscience.factory.MadTileEntityFactoryProductData;

import com.google.common.base.Throwables;
import com.google.gson.Gson;

public class MadMod
{    
    // Identification.
    public static final String ID = "madscience";
    public static final String NAME = "Example Mod";
    public static final String CHANNEL_NAME = "exampleChannel";
    
    // Metadata.
    public static final String DESCRIPTION = "Example Description";
    public static final String HOME_URL = "http://examplemod.com/";
    
    public static final String LOGO_PATH = "assets/logo.png";
    public static final String CREDITS = "Thank you Minecraft/Forge and MCP team!";
    public static final String[] AUTHORS = {"Example Modder1", "Example Modder2"};
    public static final String FINGERPRINT = "exampleFingerprint";
    public static final String MINECRAFT_VERSION = "";
    public static final String DEPENDENCIES = "";
    
    // Proxy Classes Namespace.
    public static final String CLIENT_PROXY = "";
    public static final String SERVER_PROXY = "";
    
    // Full version string for internal reference by mod.
    public static final String VMAJOR = "1";
    public static final String VMINOR = "0";
    public static final String VREVISION = "0";
    public static final String VBUILD = "0";
    public static final String VERSION_FULL = VMAJOR + "." + VMINOR + "." + VREVISION + "." + VBUILD;

    // Update checker.
    public static final String UPDATE_URL = "http://ci.examplemod.com/";
    
    // Directories definition for assets and localization files.
    public static final String RESOURCE_DIRECTORY = "/assets/" + MadMod.ID + "/";
    public static final String BASE_DIRECTORY_NO_SLASH = MadMod.ID + "/";
    public static final String ASSET_DIRECTORY = "/assets/" + MadMod.ID + "/";
    public static final String TEXTURE_DIRECTORY = "textures/";
    public static final String MODEL_DIRECTORY = "models/";
    
    // Quick links to popular places.
    public static final String MODEL_PATH = ASSET_DIRECTORY + MODEL_DIRECTORY;
    
    // Hook standardized logging class so we can report data on the console without standard out.
    public static Logger LOGGER = null;
    
    // Data container which gets serialized with all our mod information.
    private static MadModData unregisteredMachines;
    
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
            throw Throwables.propagate(err);
        }
        
        // Parse the JSON string we got from resources into an array of product data.
        Gson gson = new Gson();
        MadModData loadedModData = null;
        loadedModData = gson.fromJson(jsonMachineInput, MadModData.class);
        
        // Populate this class with the data we just got.
        unregisteredMachines = loadedModData;
        
        // Change our base mod information via reflection.
        try
        {
            setID(unregisteredMachines.getID());
            setName(unregisteredMachines.getModName());
            setChannelName(unregisteredMachines.getChannelName());
            setDescription(unregisteredMachines.getDescription());
            setHomeURL(unregisteredMachines.getUpdateURL());
            setUpdateURL(unregisteredMachines.getUpdateURL());
            setLogoPath(unregisteredMachines.getLogoPath());
            setCredits(unregisteredMachines.getCredits());
            setAuthors(unregisteredMachines.getAuthors());
            setFingerprint(unregisteredMachines.getFingerprint());
            setVersionFull(unregisteredMachines.getVersionMajor() + "." + unregisteredMachines.getVersionMinor() + "." + unregisteredMachines.getVersionRevision() + "." + unregisteredMachines.getVersionBuild());
            setMinecraftVersion(unregisteredMachines.getMCAcceptedVersions());
            setDependencies(unregisteredMachines.getForgeDependencies());
            setClientProxy(unregisteredMachines.getProxyClientNamespace());
            setServerProxy(unregisteredMachines.getProxySeverNamespace());
        }
        catch (Exception err)
        {
            err.printStackTrace();
        }
    }
    
    public static MadTileEntityFactoryProductData[] getUnregisteredMachines()
    {
        return unregisteredMachines.getUnregisteredMachines();
    }
    
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
                MadTileEntityFactory.getMachineDataList());
    }
    
    public static void setID(String id) throws Exception
    {
        Field staticField = MadMod.class.getDeclaredField("ID");
        setValue(null, staticField, id);
    }

    public static void setName(String name) throws Exception
    {
        Field staticField = MadMod.class.getDeclaredField("NAME");
        setValue(null, staticField, name);
    }

    public static void setChannelName(String channelName) throws Exception
    {
        Field staticField = MadMod.class.getDeclaredField("CHANNEL_NAME");
        setValue(null, staticField, channelName);
    }

    public static void setDescription(String description) throws Exception
    {
        Field staticField = MadMod.class.getDeclaredField("DESCRIPTION");
        setValue(null, staticField, description);
    }

    public static void setHomeURL(String homeUrl) throws Exception
    {
        Field staticField = MadMod.class.getDeclaredField("HOME_URL");
        setValue(null, staticField, homeUrl);
    }

    public static void setLogoPath(String logoPath) throws Exception
    {
        Field staticField = MadMod.class.getDeclaredField("LOGO_PATH");
        setValue(null, staticField, logoPath);
    }

    public static void setCredits(String credits) throws Exception
    {
        Field staticField = MadMod.class.getDeclaredField("CREDITS");
        setValue(null, staticField, credits);
    }

    public static void setAuthors(String[] authors) throws Exception
    {
        Field staticField = MadMod.class.getDeclaredField("AUTHORS");
        setValue(null, staticField, authors);
    }

    public static void setFingerprint(String fingerprint) throws Exception
    {
        Field staticField = MadMod.class.getDeclaredField("FINGERPRINT");
        setValue(null, staticField, fingerprint);
    }

    public static void setMinecraftVersion(String minecraftVersion) throws Exception
    {
        Field staticField = MadMod.class.getDeclaredField("MINECRAFT_VERSION");
        setValue(null, staticField, minecraftVersion);
    }

    public static void setDependencies(String dependencies) throws Exception
    {
        Field staticField = MadMod.class.getDeclaredField("DEPENDENCIES");
        setValue(null, staticField, dependencies);
    }

    public static void setClientProxy(String clientProxy) throws Exception
    {
        Field staticField = MadMod.class.getDeclaredField("CLIENT_PROXY");
        setValue(null, staticField, clientProxy);
    }

    public static void setServerProxy(String serverProxy) throws Exception
    {
        Field staticField = MadMod.class.getDeclaredField("SERVER_PROXY");
        setValue(null, staticField, serverProxy);
    }

    public static void setVmajor(String vmajor) throws Exception
    {
        Field staticField = MadMod.class.getDeclaredField("VMAJOR");
        setValue(null, staticField, vmajor);
    }

    public static void setVminor(String vminor) throws Exception
    {
        Field staticField = MadMod.class.getDeclaredField("VMINOR");
        setValue(null, staticField, vminor);
    }

    public static void setVrevision(String vrevision) throws Exception
    {
        Field staticField = MadMod.class.getDeclaredField("VREVISION");
        setValue(null, staticField, vrevision);
    }

    public static void setVbuild(String vbuild) throws Exception
    {
        Field staticField = MadMod.class.getDeclaredField("VBUILD");
        setValue(null, staticField, vbuild);
    }

    public static void setVersionFull(String versionFull) throws Exception
    {
        Field staticField = MadMod.class.getDeclaredField("VERSION_FULL");
        setValue(null, staticField, versionFull);
    }

    public static void setUpdateURL(String updateUrl) throws Exception
    {
        Field staticField = MadMod.class.getDeclaredField("UPDATE_URL");
        setValue(null, staticField, updateUrl);
    }
    
    /**
     * 
     * Set the value of a field reflectively.
     */
    protected static void setValue(Object owner, Field field, Object value) throws Exception 
    {
      makeModifiable(field);
      field.set(owner, value);
    }
    
    /**
     * 
     * Force the field to be modifiable and accessible.
     */
    protected static void makeModifiable(Field nameField) throws Exception 
    {
      nameField.setAccessible(true);
      int modifiers = nameField.getModifiers();
      Field modifierField = nameField.getClass().getDeclaredField("modifiers");
      modifiers = modifiers & ~Modifier.FINAL;
      modifierField.setAccessible(true);
      modifierField.setInt(nameField, modifiers);
    }
}

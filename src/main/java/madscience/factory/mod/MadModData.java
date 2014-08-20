package madscience.factory.mod;

import madscience.factory.data.MadFluidFactoryProductData;
import madscience.factory.data.MadItemFactoryProductData;
import madscience.factory.data.MadTileEntityFactoryProductData;

import com.google.gson.annotations.Expose;

public class MadModData
{
    // --------------
    // IDENTIFICATION
    // --------------
    @Expose
    private String id;

    @Expose
    private String name;

    @Expose
    private String channelName;

    // --------
    // METADATA
    // --------
    @Expose
    private String description;

    @Expose
    private String homeURL;

    @Expose
    private String logoPath;

    @Expose
    private String credits;

    @Expose
    private String[] authors;

    @Expose
    private String fingerprint;

    @Expose
    private String minecraftVersion;

    @Expose
    private String dependencies;

    // ----------------
    // PROXY NAMESPACES
    // ----------------
    @Expose
    private String proxyClient;

    @Expose
    private String proxyServer;

    // -------
    // VERSION
    // -------
    @Expose
    private String versionMajor;

    @Expose
    private String versionMinor;

    @Expose
    private String versionRevision;

    @Expose
    private String versionBuild;

    // --------------
    // UPDATE CHECKER
    // --------------
    @Expose
    private String updateURL;
    
    // ----------
    // ID MANAGER
    // ----------
    @Expose
    private int idManagerBlockIndex;
    
    @Expose
    private int idManagerItemIndex;
    
    // -----------------
    // CREATIVE TAB ICON
    // -----------------
    @Expose
    private String creativeTabIconName;
    
    @Expose
    private int creativeTabIconMetadata;
    
    // -------------
    // TILE ENTITIES
    // -------------
    @Expose
    private MadTileEntityFactoryProductData[] unregisteredMachines;
    
    // -----
    // ITEMS
    // -----
    @Expose
    private MadItemFactoryProductData[] unregisteredItems;
    
    // ------
    // FLUIDS
    // ------
    @Expose
    private MadFluidFactoryProductData[] unregisteredFluids;

    @SuppressWarnings("ucd")
    public MadModData(String id,
            String name,
            String channelName,
            String description,
            String homeURL,
            String logoPath,
            String credits,
            String[] authors,
            String fingerprint,
            String mcVersionSupported,
            String forgeDependencies,
            String proxyClient,
            String proxyServer,
            String versionMajor,
            String versionMinor,
            String versionRevision,
            String versionBuild,
            String updateURL,
            int blockIDStart,
            int itemIDStart,
            String creativeTabIconName,
            int creativeTabIconMeta,
            MadTileEntityFactoryProductData[] madMachines,
            MadItemFactoryProductData[] madItems,
            MadFluidFactoryProductData[] madFluids)
    {
        super();

        this.id = id;
        this.name = name;
        this.channelName = channelName;

        this.description = description;
        this.homeURL = homeURL;
        this.logoPath = logoPath;
        this.credits = credits;
        this.authors = authors;
        this.fingerprint = fingerprint;

        this.minecraftVersion = mcVersionSupported;
        this.dependencies = forgeDependencies;

        this.proxyClient = proxyClient;
        this.proxyServer = proxyServer;

        this.versionMajor = versionMajor;
        this.versionMinor = versionMinor;
        this.versionRevision = versionRevision;
        this.versionBuild = versionBuild;

        this.updateURL = updateURL;
        
        this.idManagerBlockIndex = blockIDStart;
        this.idManagerItemIndex = itemIDStart;

        this.creativeTabIconName = creativeTabIconName;
        this.creativeTabIconMetadata = creativeTabIconMeta;
        
        this.unregisteredMachines = madMachines;
        this.unregisteredItems = madItems;
        this.unregisteredFluids = madFluids;
    }

    public String getID()
    {
        return id;
    }

    public String getModName()
    {
        return name;
    }

    public String getChannelName()
    {
        return channelName;
    }

    public String getDescription()
    {
        return description;
    }

    public String getHomeURL()
    {
        return homeURL;
    }

    public String getLogoPath()
    {
        return logoPath;
    }

    public String getCredits()
    {
        return credits;
    }

    public String[] getAuthors()
    {
        return authors;
    }

    public String getFingerprint()
    {
        return fingerprint;
    }

    public String getMCAcceptedVersions()
    {
        return minecraftVersion;
    }

    public String getForgeDependencies()
    {
        return dependencies;
    }

    public String getProxyClientNamespace()
    {
        return proxyClient;
    }

    public String getProxySeverNamespace()
    {
        return proxyServer;
    }

    public String getVersionMajor()
    {
        return versionMajor;
    }

    public String getVersionMinor()
    {
        return versionMinor;
    }

    public String getVersionRevision()
    {
        return versionRevision;
    }

    public String getVersionBuild()
    {
        return versionBuild;
    }

    public String getUpdateURL()
    {
        return updateURL;
    }

    public int getIDManagerBlockIndex()
    {
        return idManagerBlockIndex;
    }

    public int getIDManagerItemIndex()
    {
        return idManagerItemIndex;
    }
    
    public MadTileEntityFactoryProductData[] getUnregisteredMachines()
    {
        return unregisteredMachines;
    }

    public MadItemFactoryProductData[] getUnregisteredItems()
    {
        return unregisteredItems;
    }

    public MadFluidFactoryProductData[] getUnregisteredFluids()
    {
        return unregisteredFluids;
    }

    /** Name of icon that should be loaded onto the creative tab menu in post-initialization (after factories have registered everything). */
    public String getCreativeTabIconName()
    {
        if (creativeTabIconName != null && !creativeTabIconName.isEmpty())
        {
            return creativeTabIconName;
        }
        
        return "default";
    }

    /** Metadata related to icon name that will be loaded onto the creative tab for this mod ID. */
    public int getCreativeTabIconMetadata()
    {
        return creativeTabIconMetadata;
    }

    /** Defines where ID manager starts counting block ID's. */
    public int getIdManagerBlockIndex()
    {
        return idManagerBlockIndex;
    }

    /** Defines where ID manager starts counting item ID's. */
    public int getIdManagerItemIndex()
    {
        return idManagerItemIndex;
    }
}

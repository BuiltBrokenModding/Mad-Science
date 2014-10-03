package madapi.mod;

import java.util.List;

import madapi.data.MadFluidFactoryProductData;
import madapi.data.MadItemFactoryProductData;
import madapi.data.MadTileEntityFactoryProductData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MadModData
{
    // --------------
    // IDENTIFICATION
    // --------------
    @Expose
    @SerializedName("ID")
    private String id;

    @Expose
    @SerializedName("Name")
    private String name;

    @Expose
    @SerializedName("ChannelName")
    private String channelName;

    // --------
    // METADATA
    // --------
    @Expose
    @SerializedName("Description")
    private String description;

    @Expose
    @SerializedName("HomeURL")
    private String homeURL;

    @Expose
    @SerializedName("LogoPath")
    private String logoPath;

    @Expose
    @SerializedName("Credits")
    private String credits;

    @Expose
    @SerializedName("Authors")
    private String[] authors;

    @Expose
    @SerializedName("Fingerprint")
    private String fingerprint;

    @Expose
    @SerializedName("MinecraftVersion")
    private String minecraftVersion;

    @Expose
    @SerializedName("Dependencies")
    private String dependencies;

    // ----------------
    // PROXY NAMESPACES
    // ----------------
    @Expose
    @SerializedName("ProxyClient")
    private String proxyClient;

    @Expose
    @SerializedName("ProxyServer")
    private String proxyServer;

    // -------
    // VERSION
    // -------
    @Expose
    @SerializedName("VersionMajor")
    private String versionMajor;

    @Expose
    @SerializedName("VersionMinor")
    private String versionMinor;

    @Expose
    @SerializedName("VersionRevision")
    private String versionRevision;

    @Expose
    @SerializedName("VersionBuild")
    private String versionBuild;

    // --------------
    // UPDATE CHECKER
    // --------------
    @Expose
    @SerializedName("UpdateURL")
    private String updateURL;
    
    // ----------
    // ID MANAGER
    // ----------
    @Expose
    @SerializedName("IDManagerBlockIndex")
    private int idManagerBlockIndex;
    
    @Expose
    @SerializedName("IDManagerItemIndex")
    private int idManagerItemIndex;
    
    // -----------------
    // CREATIVE TAB ICON
    // -----------------
    @Expose
    @SerializedName("CreativeTabIconName")
    private String creativeTabIconName;
    
    @Expose
    @SerializedName("CreativeTabIconMetaData")
    private int creativeTabIconMetadata;
    
    // -------------
    // TILE ENTITIES
    // -------------
    @Expose
    @SerializedName("UnregisteredMachines")
    private List<MadTileEntityFactoryProductData> unregisteredMachines;
    
    // -----
    // ITEMS
    // -----
    @Expose
    @SerializedName("UnregisteredItems")
    private List<MadItemFactoryProductData> unregisteredItems;
    
    // ------
    // FLUIDS
    // ------
    @Expose
    @SerializedName("UnregisteredFluids")
    private List<MadFluidFactoryProductData> unregisteredFluids;

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
            List<MadTileEntityFactoryProductData> madMachines,
            List<MadItemFactoryProductData> madItems,
            List<MadFluidFactoryProductData> madFluids)
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
    
    public List<MadTileEntityFactoryProductData> getUnregisteredMachines()
    {
        return unregisteredMachines;
    }

    public List<MadItemFactoryProductData> getUnregisteredItems()
    {
        return unregisteredItems;
    }

    public List<MadFluidFactoryProductData> getUnregisteredFluids()
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

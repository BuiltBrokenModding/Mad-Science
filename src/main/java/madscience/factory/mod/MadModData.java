package madscience.factory.mod;

import com.google.gson.annotations.Expose;

import madscience.factory.MadTileEntityFactoryProductData;

public class MadModData
{
    // Identification.
    private String idDefault = new String("exampleMod");
    @Expose
    private String id;

    private String nameDefault = new String("Example Mod");
    @Expose
    private String name;

    private String channelNameDefault = new String("exampleChannel");
    @Expose
    private String channelName;

    // Metadata.
    private String descriptionDefault = new String("Example Description");
    @Expose
    private String description;

    private String homeURLDefault = new String("http://examplemod.com/");
    @Expose
    private String homeURL;

    private String logoPathDefault = new String("assets/logo.png");
    @Expose
    private String logoPath;

    private String creditsDefault = new String("Thank you Minecraft/Forge and MCP team!");
    @Expose
    private String credits;

    private String[] authorsDefault =
    { "Example Modder1", "Example Modder2" };
    @Expose
    private String[] authors;

    private String fingerprintDefault = new String("exampleFingerprint");
    @Expose
    private String fingerprint;

    private String minecraftVersionDefault = new String("");
    @Expose
    private String minecraftVersion;

    private String dependenciesDefault = new String("");
    @Expose
    private String dependencies;

    // Proxy Classes Namespace.
    private String proxyClientDefault = new String("");
    @Expose
    private String proxyClient;

    private String proxyServerDefault = new String("");
    @Expose
    private String proxyServer;

    // Full version string for internal reference by mod.
    private String versionMajorDefault = new String("1");
    @Expose
    private String versionMajor;

    private String versionMinorDefault = new String("0");
    @Expose
    private String versionMinor;

    private String versionRevisionDefault = new String("0");
    @Expose
    private String versionRevision;

    private String versionBuildDefault = new String("0");
    @Expose
    private String versionBuild;

    // Update checker.
    private String updateURLDefault = new String("http://ci.examplemod.com/");
    @Expose
    private String updateURL;

    // Machines
    @Expose
    private MadTileEntityFactoryProductData[] unregisteredMachines;

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
            MadTileEntityFactoryProductData[] madMachines)
    {
        super();

        this.id = id == null ? idDefault : id;
        this.name = name == null ? nameDefault : name;
        this.channelName = channelName == null ? channelNameDefault : channelName;

        this.description = description == null ? descriptionDefault : description;
        this.homeURL = homeURL == null ? homeURLDefault : homeURL;
        this.logoPath = logoPath == null ? logoPathDefault : logoPath;
        this.credits = credits == null ? creditsDefault : credits;
        this.authors = authors == null ? authorsDefault : authors;
        this.fingerprint = fingerprint == null ? fingerprintDefault : fingerprint;

        this.minecraftVersion = mcVersionSupported == null ? minecraftVersionDefault : mcVersionSupported;
        this.dependencies = forgeDependencies == null ? dependenciesDefault : forgeDependencies;

        this.proxyClient = proxyClient == null ? proxyClientDefault : proxyClient;
        this.proxyServer = proxyServer == null ? proxyServerDefault : proxyServer;

        this.versionMajor = versionMajor == null ? versionMajorDefault : versionMajor;
        this.versionMinor = versionMinor == null ? versionMinorDefault : versionMinor;
        this.versionRevision = versionRevision == null ? versionRevisionDefault : versionRevision;
        this.versionBuild = versionBuild == null ? versionBuildDefault : versionBuild;

        this.updateURL = updateURL == null ? updateURLDefault : updateURL;

        this.unregisteredMachines = madMachines;
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

    public MadTileEntityFactoryProductData[] getUnregisteredMachines()
    {
        return unregisteredMachines;
    }
}

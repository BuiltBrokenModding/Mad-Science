package madscience;

public class MadModMetadata
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
}

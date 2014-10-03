package madapi;

public class MadModMetadata
{
    // Identification.
    public static final String ID = "examplemod";
    public static final String NAME = "Example Mod";
    public static final String CHANNEL_NAME = "examplemod";
    
    // Metadata.
    public static final String DESCRIPTION = "Example description for Mad API!";
    public static final String HOME_URL = "http://mygreatmod.com/";
    
    public static final String LOGO_PATH = "assets/examplemod/logo.png";
    public static final String CREDITS = "Thanks to Forge team for being awesome!";
    public static final String[] AUTHORS = {"Example Guy"};
    public static final String FINGERPRINT = "@FINGERPRINT@";
    public static final String MINECRAFT_VERSION = "[1.6.4,)";
    public static final String DEPENDENCIES = "required-after:Forge@[9.11.1.953,);after:BuildCraft|Energy;after:factorization;after:IC2;after:Railcraft;after:ThermalExpansion";
    
    // Proxy Classes Namespace.
    public static final String CLIENT_PROXY = "madapi.proxy.ClientProxy";
    public static final String SERVER_PROXY = "madapi.proxy.CommonProxy";
    
    // Full version string for internal reference by mod.
    public static final String VMAJOR = "@MAJOR@";
    public static final String VMINOR = "@MINOR@";
    public static final String VREVISION = "@REVIS@";
    public static final String VBUILD = "@BUILD@";
    public static final String VERSION_FULL = VMAJOR + "." + VMINOR + "." + VREVISION + "." + VBUILD;

    /** Update checker. */
    public static final String UPDATE_URL = "http://mygreatmod.com/latest";
}

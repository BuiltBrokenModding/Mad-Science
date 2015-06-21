package madscience;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class MadConfig
{
    // Modified from Source
    // http://www.minecraftforge.net/wiki/Reference_Mod_File

    @Retention(RetentionPolicy.RUNTIME)
    private @interface CfgBool
    {
    }

    @Retention(RetentionPolicy.RUNTIME)
    private @interface CfgDNA
    {
    }

    @Retention(RetentionPolicy.RUNTIME)
    private @interface CfgEnergy
    {
    }

    @Retention(RetentionPolicy.RUNTIME)
    private @interface CfgGenomes
    {
    }

    @Retention(RetentionPolicy.RUNTIME)
    private @interface CfgId
    {
        boolean block() default false;
    }

    @Retention(RetentionPolicy.RUNTIME)
    private @interface CfgInt
    {
    }

    @Retention(RetentionPolicy.RUNTIME)
    private @interface CfgUpdates
    {
        boolean isBool() default false;
    }

    @Retention(RetentionPolicy.RUNTIME)
    private @interface CfgHelp
    {
    }

    @Retention(RetentionPolicy.RUNTIME)
    private @interface CfgMemories
    {
    }

    @Retention(RetentionPolicy.RUNTIME)
    private @interface CfgMobs
    {
    }

    @Retention(RetentionPolicy.RUNTIME)
    private @interface CfgNeedles
    {
    }

    @Retention(RetentionPolicy.RUNTIME)
    private @interface CfgProcessing
    {
    }

    @Retention(RetentionPolicy.RUNTIME)
    private @interface CfgCircuits
    {
    }

    @Retention(RetentionPolicy.RUNTIME)
    private @interface CfgComponents
    {
    }

    @Retention(RetentionPolicy.RUNTIME)
    private @interface CfgWeapons
    {
    }

    // Header for each configuration section.
    public static final String CATAGORY_MOBS = "mobs";
    public static final String CATAGORY_GENOMES = "genomes";
    public static final String CATAGORY_DNA = "dna";
    public static final String CATAGORY_NEEDLES = "needles";
    public static final String CATAGORY_MEMORIES = "memories";
    public static final String CATAGORY_ENERGY = "energy";
    public static final String CATAGORY_PROCESSING = "processing";
    public static final String CATAGORY_CIRCUITS = "circuits";
    public static final String CATAGORY_COMPONENTS = "components";
    public static final String CATAGORY_UPDATES = "updates";
    public static final String CATAGORY_HELP = "help";
    public static final String CATAGORY_WEAPONS = "weapons";

    // Mob ID's for genetically modified entity list.
    private static int madGMOMobIDs = 50;


    // ---------------------------------
    // GENETICALLY MODIFIED MOB META IDS
    // ---------------------------------
    // COMBINED GENOME META IDS
    // ------------------------

    // Default amount of time we will spend processing some genomes into new ones.
    public final static int COOKTIME_DEFAULT = 2600;

    // Werewolf [Wolf + Villager]
    public final static int GMO_WEREWOLF_METAID_DEFAULT = ++madGMOMobIDs;
    public static
    @CfgMobs
    int GMO_WEREWOLF_METAID = GMO_WEREWOLF_METAID_DEFAULT;
    public static
    @CfgProcessing
    int GMO_WEREWOLF_COOKTIME = COOKTIME_DEFAULT;

    // Disgusting Meatcube [Slime + Pig,Cow,Chicken]
    public final static int GMO_MEATCUBE_METAID_DEFAULT = ++madGMOMobIDs;
    public static
    @CfgMobs
    int GMO_MEATCUBE_METAID = GMO_MEATCUBE_METAID_DEFAULT;
    public static
    @CfgProcessing
    int GMO_MEATCUBE_COOKTIME = COOKTIME_DEFAULT;

    // Creeper Cow [Creeper + Cow]
    public final static int GMO_CREEPERCOW_METAID_DEFAULT = ++madGMOMobIDs;
    public static
    @CfgMobs
    int GMO_CREEPERCOW_METAID = GMO_CREEPERCOW_METAID_DEFAULT;
    public static
    @CfgProcessing
    int GMO_CREEPERCOW_COOKTIME = COOKTIME_DEFAULT;

    // Enderslime [Enderman + Slime]
    public final static int GMO_ENDERSLIME_METAID_DEFAULT = ++madGMOMobIDs;
    public static
    @CfgMobs
    int GMO_ENDERSLIME_METAID = GMO_ENDERSLIME_METAID_DEFAULT;
    public static
    @CfgProcessing
    int GMO_ENDERSLIME_COOKTIME = COOKTIME_DEFAULT;

    // --------------------------
    // Bart74(bart.74@hotmail.fr)
    // --------------------------

    // Wooly Cow [Cow + Sheep]
    public final static int GMO_WOOLYCOW_METAID_DEFAULT = ++madGMOMobIDs;
    public static
    @CfgMobs
    int GMO_WOOLYCOW_METAID = GMO_WOOLYCOW_METAID_DEFAULT;
    public static
    @CfgProcessing
    int GMO_WOOLYCOW_COOKTIME = COOKTIME_DEFAULT;

    // ----------------------------------------
    // Deuce_Loosely(captainlunautic@yahoo.com)
    // ----------------------------------------

    // Shoggoth [Slime + Squid]
    public final static int GMO_SHOGGOTH_METAID_DEFAULT = ++madGMOMobIDs;
    public static
    @CfgMobs
    int GMO_SHOGGOTH_METAID = GMO_SHOGGOTH_METAID_DEFAULT;
    public static
    @CfgProcessing
    int GMO_SHOGGOTH_COOKTIME = COOKTIME_DEFAULT;

    // ------------------------------------
    // monodemono(coolplanet3000@gmail.com)
    // ------------------------------------

    // The Abomination [Enderman + Spider]
    public final static int GMO_ABOMINATION_METAID_DEFAULT = ++madGMOMobIDs;
    public static
    @CfgMobs
    int GMO_ABOMINATION_METAID = GMO_ABOMINATION_METAID_DEFAULT;
    public static
    @CfgProcessing
    int GMO_ABOMINATION_COOKTIME = COOKTIME_DEFAULT;

    // ---------------------------------
    // Pyrobrine(haskar.spore@gmail.com)
    // ---------------------------------

    // Wither Skeleton [Enderman + Skeleton]
    public final static int GMO_WITHERSKELETON_METAID_DEFAULT = ++madGMOMobIDs;
    public static
    @CfgMobs
    int GMO_WITHERSKELETON_METAID = GMO_WITHERSKELETON_METAID_DEFAULT;
    public static
    @CfgProcessing
    int GMO_WITHERSKELETON_COOKTIME = COOKTIME_DEFAULT;

    // Villager Zombie [Villager + Zombie]
    public final static int GMO_VILLAGERZOMBIE_METAID_DEFAULT = ++madGMOMobIDs;
    public static
    @CfgMobs
    int GMO_VILLAGERZOMBIE_METAID = GMO_VILLAGERZOMBIE_METAID_DEFAULT;
    public static
    @CfgProcessing
    int GMO_VILLAGERZOMBIE_COOKTIME = COOKTIME_DEFAULT;

    // Skeleton Horse [Horse + Skeleton]
    public final static int GMO_SKELETONHORSE_METAID_DEFAULT = ++madGMOMobIDs;
    public static
    @CfgMobs
    int GMO_SKELETONHORSE_METAID = GMO_SKELETONHORSE_METAID_DEFAULT;
    public static
    @CfgProcessing
    int GMO_SKELETONHORSE_COOKTIME = COOKTIME_DEFAULT;

    // Zombie Horse [Zombie + Horse]
    public final static int GMO_ZOMBIEHORSE_METAID_DEFAULT = ++madGMOMobIDs;
    public static
    @CfgMobs
    int GMO_ZOMBIEHORSE_METAID = GMO_ZOMBIEHORSE_METAID_DEFAULT;
    public static
    @CfgProcessing
    int GMO_ZOMBIEHORSE_COOKTIME = COOKTIME_DEFAULT;

    // ---------------------------------
    // TheTechnician(tallahlf@gmail.com)
    // ---------------------------------

    // Ender Squid [Enderman + Squid]
    public final static int GMO_ENDERSQUID_METAID_DEFAULT = ++madGMOMobIDs;
    public static
    @CfgMobs
    int GMO_ENDERSQUID_METAID = GMO_ENDERSQUID_METAID_DEFAULT;
    public static
    @CfgProcessing
    int GMO_ENDERSQUID_COOKTIME = COOKTIME_DEFAULT;

    // -----------------------
    // TILE ENTITY DECLARATION
    // -----------------------

    // Defaults for power storage and input/output rates.
    public final static long MACHINE_CAPACITY_DEFAULT = 100000;
    public final static long MACHINE_TRANSFERRATE_DEFAULT = 200;
    public final static long MACHINE_CONSUMERATE_DEFAULT = 1;

    // DNA Extractor
    public static
    @CfgEnergy
    long DNAEXTRACTOR_CAPACTITY = MACHINE_CAPACITY_DEFAULT;
    public static
    @CfgEnergy
    long DNAEXTRACTOR_INPUT = MACHINE_TRANSFERRATE_DEFAULT;
    public static
    @CfgEnergy
    long DNAEXTRACTOR_CONSUME = MACHINE_CONSUMERATE_DEFAULT;
    public static
    @CfgHelp
    String DNAEXTRACTOR_HELP = "http://madsciencemod.com/minecraft-item/dna-extractor/";

    // Needle Sanitizer
    public static
    @CfgEnergy
    long SANTITIZER_CAPACTITY = MACHINE_CAPACITY_DEFAULT;
    public static
    @CfgEnergy
    long SANTITIZER_INPUT = MACHINE_TRANSFERRATE_DEFAULT;
    public static
    @CfgEnergy
    long SANTITIZER_CONSUME = MACHINE_CONSUMERATE_DEFAULT;
    public static
    @CfgHelp
    String SANTITIZER_HELP = "http://madsciencemod.com/minecraft-item/syringe-sanitizer/";

    // Computer Mainframe
    public static
    @CfgEnergy
    long MAINFRAME_CAPACTITY = MACHINE_CAPACITY_DEFAULT;
    public static
    @CfgEnergy
    long MAINFRAME_INPUT = MACHINE_TRANSFERRATE_DEFAULT;
    public static
    @CfgEnergy
    long MAINFRAME_CONSUME = MACHINE_CONSUMERATE_DEFAULT;
    public static
    @CfgHelp
    String MAINFRAME_HELP = "http://madsciencemod.com/minecraft-item/computer-mainframe/";

    // Genetic Sequencer
    public static
    @CfgEnergy
    long SEQUENCER_CAPACTITY = MACHINE_CAPACITY_DEFAULT;
    public static
    @CfgEnergy
    long SEQUENCER_INPUT = MACHINE_TRANSFERRATE_DEFAULT;
    public static
    @CfgEnergy
    long SEQUENCER_CONSUME = MACHINE_CONSUMERATE_DEFAULT;
    public static
    @CfgHelp
    String SEQUENCER_HELP = "http://madsciencemod.com/minecraft-item/genetic-sequencer/";

    // Cryogenic Freezer
    public static
    @CfgEnergy
    long CRYOFREEZER_CAPACTITY = MACHINE_CAPACITY_DEFAULT;
    public static
    @CfgEnergy
    long CRYOFREEZER_INPUT = MACHINE_TRANSFERRATE_DEFAULT;
    public static
    @CfgEnergy
    long CRYOFREEZER_CONSUME = MACHINE_CONSUMERATE_DEFAULT;
    public static
    @CfgHelp
    String CRYOFREEZER_HELP = "http://madsciencemod.com/minecraft-item/cryogenic-freezer/";

    // Genome Incubator
    public static
    @CfgEnergy
    long INCUBATOR_CAPACTITY = MACHINE_CAPACITY_DEFAULT;
    public static
    @CfgEnergy
    long INCUBATOR_INPUT = MACHINE_TRANSFERRATE_DEFAULT;
    public static
    @CfgEnergy
    long INCUBATOR_CONSUME = MACHINE_CONSUMERATE_DEFAULT;
    public static
    @CfgHelp
    String INCUBATOR_HELP = "http://madsciencemod.com/minecraft-item/genome-incubator/";

    // Meat Cube
    public static
    @CfgHelp
    String MEATCUBE_HELP = "http://madsciencemod.com/minecraft-item/meat-cube/";

    // Cryogenic Tube
    public static
    @CfgEnergy
    long CRYOTUBE_CAPACTITY = 225120000000L;
    public static
    @CfgEnergy
    long CRYOTUBE_OUTPUT = 562800000L;
    public static
    @CfgEnergy
    long CRYOTUBE_PRODUCE = 1407000L;
    public static
    @CfgHelp
    String CRYOTUBE_HELP = "http://madsciencemod.com/minecraft-item/cryogenic-tube/";

    // Thermosonic Bonder
    public static
    @CfgEnergy
    long THERMOSONIC_CAPACTITY = MACHINE_CAPACITY_DEFAULT;
    public static
    @CfgEnergy
    long THERMOSONIC_INPUT = MACHINE_TRANSFERRATE_DEFAULT;
    public static
    @CfgEnergy
    long THERMOSONIC_CONSUME = MACHINE_CONSUMERATE_DEFAULT;
    public static
    @CfgHelp
    String THERMOSONIC_HELP = "http://madsciencemod.com/minecraft-item/thermosonic-bonder/";

    // Data Reel Duplicator
    public static
    @CfgEnergy
    long DATADUPLICATOR_CAPACTITY = MACHINE_CAPACITY_DEFAULT;
    public static
    @CfgEnergy
    long DATADUPLICATOR_INPUT = MACHINE_TRANSFERRATE_DEFAULT;
    public static
    @CfgEnergy
    long DATADUPLICATOR_CONSUME = MACHINE_CONSUMERATE_DEFAULT;
    public static
    @CfgHelp
    String DATADUPLICATOR_HELP = "http://madsciencemod.com/minecraft-item/data-reel-duplicator/";

    // Soniclocator
    public static
    @CfgEnergy
    long SONICLOCATOR_CAPACTITY = MACHINE_CAPACITY_DEFAULT;
    public static
    @CfgEnergy
    long SONICLOCATOR_INPUT = MACHINE_TRANSFERRATE_DEFAULT;
    public static
    @CfgEnergy
    long SONICLOCATOR_CONSUME = MACHINE_CONSUMERATE_DEFAULT;
    public static
    @CfgHelp
    String SONICLOCATOR_HELP = "http://madsciencemod.com/minecraft-item/soniclocator/";

    // Clay Furnace
    public static
    @CfgHelp
    String CLAYFURNACE_HELP = "http://madsciencemod.com/minecraft-item/clay-furnace/";

    // VOX Box
    public static
    @CfgEnergy
    long VOXBOX_CAPACTITY = MACHINE_CAPACITY_DEFAULT / 4;
    public static
    @CfgEnergy
    long VOXBOX_INPUT = MACHINE_TRANSFERRATE_DEFAULT;
    public static
    @CfgEnergy
    long VOXBOX_CONSUME = 128;
    public static
    @CfgHelp
    String VOXBOX_HELP = "http://madsciencemod.com/minecraft-item/voxbox/";

    // Magazine Loader
    public static
    @CfgEnergy
    long MAGLOADER_CAPACTITY = MACHINE_CAPACITY_DEFAULT / 4;
    public static
    @CfgEnergy
    long MAGLOADER_INPUT = MACHINE_TRANSFERRATE_DEFAULT;
    public static
    @CfgEnergy
    long MAGLOADER_CONSUME = MACHINE_CONSUMERATE_DEFAULT;
    public static
    @CfgHelp
    String MAGLOADER_HELP = "http://madsciencemod.com/minecraft-item/magazine-loader/";

    // CnC Machine
    public static
    @CfgEnergy
    long CNCMACHINE_CAPACTITY = MACHINE_CAPACITY_DEFAULT / 4;
    public static
    @CfgEnergy
    long CNCMACHINE_INPUT = MACHINE_TRANSFERRATE_DEFAULT;
    public static
    @CfgEnergy
    long CNCMACHINE_CONSUME = MACHINE_CONSUMERATE_DEFAULT;
    public static
    @CfgHelp
    String CNCMACHINE_HELP = "http://madsciencemod.com/minecraft-item/cnc-machine/";


    // ----------------
    // FEATURE SWITCHES
    // ----------------

    // Determines if 'The Abomination' should lay eggs.
    public static
    @CfgBool
    boolean ABOMINATION_LAYSEGGS = true;

    // Determines if 'The Abomination' is allowed to teleport.
    public static
    @CfgBool
    boolean ABOMINATION_TELEPORTS = true;

    // Needles decay into dirty ones.
    public static
    @CfgBool
    boolean DECAY_BLOODWORK = true;

    // Needles decay at a rate of one health every 6000 ticks.
    public static
    @CfgInt
    int DECAY_DELAY_IN_SECONDS = 30;

    // Determines how many seconds clay furnace will cook before finishing
    public static
    @CfgInt
    int CLAYFURNACE_COOKTIME_IN_SECONDS = 420;

    // Distance which we will send packet updates about machines to players.
    public static
    @CfgInt
    int PACKETSEND_RADIUS = 25;

    // ID that will determine block to be used to 'unlock' thermosonic bonder and thus every other item in the mod.
    public static
    @CfgInt
    int THERMOSONICBONDER_FINALSACRIFICE = 138;

    // Determines if M41A Pulse Rifle bullets will damage the world at all with random chance.
    public static
    @CfgBool
    boolean PULSERIFLE_BULLETS_DAMAGEWORLD = true;

    // ----------------
    // UPDATES SWITCHES
    // ----------------

    // Determines if we should inform the user about updates and nightly builds.
    public static
    @CfgUpdates(isBool = true)
    boolean UPDATE_CHECKER = true;

    // Determines what the update URL should be for the mod.
    public static
    @CfgUpdates
    String UPDATE_URL = "http://madsciencemod.com:8080/job/Mad%20Science/Release%20Version/api/xml?xpath=/freeStyleBuild/number";


}
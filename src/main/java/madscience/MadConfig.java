package madscience;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.util.logging.Level;

import madscience.util.IDManager;
import net.minecraft.block.Block;
import net.minecraft.launchwrapper.LogWrapper;
import net.minecraftforge.common.Configuration;

public class MadConfig
{
    // Modified from Source
    // http://www.minecraftforge.net/wiki/Reference_Mod_File

    @Retention(RetentionPolicy.RUNTIME)
    private static @interface CfgBool
    {
    }

    @Retention(RetentionPolicy.RUNTIME)
    private static @interface CfgDNA
    {
    }

    @Retention(RetentionPolicy.RUNTIME)
    private static @interface CfgEnergy
    {
    }

    @Retention(RetentionPolicy.RUNTIME)
    private static @interface CfgGenomes
    {
    }

    @Retention(RetentionPolicy.RUNTIME)
    private static @interface CfgId
    {
        public boolean block() default false;
    }

    @Retention(RetentionPolicy.RUNTIME)
    private static @interface CfgInt
    {
    }
    
    @Retention(RetentionPolicy.RUNTIME)
    private static @interface CfgUpdates
    {
        public boolean isBool() default false;
    }
    
    @Retention(RetentionPolicy.RUNTIME)
    private static @interface CfgHelp
    {
    }

    @Retention(RetentionPolicy.RUNTIME)
    private static @interface CfgMemories
    {
    }

    @Retention(RetentionPolicy.RUNTIME)
    private static @interface CfgMobs
    {
    }

    @Retention(RetentionPolicy.RUNTIME)
    private static @interface CfgNeedles
    {
    }

    @Retention(RetentionPolicy.RUNTIME)
    private static @interface CfgProcessing
    {
    }
    
    @Retention(RetentionPolicy.RUNTIME)
    private static @interface CfgCircuits
    {
    }
    
    @Retention(RetentionPolicy.RUNTIME)
    private static @interface CfgComponents
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

    // Mob ID's for genetically modified entity list.
    private static int madGMOMobIDs = 50;
    
    /**
     * Auto-incrementing configuration IDs. Use this to make sure no config ID is the same.
     */
    //public static final IDManager idManager = new IDManager(3768, 13768);
    public static final IDManager idManager = new IDManager(500, 3840);

    public static int getNextBlockID()
    {
            return idManager.getNextBlockID();
    }

    public static int getNextItemID()
    {
            return idManager.getNextItemID();
    }

    // ---------------------------
    // CUSTOM MONSTER PLACER ITEMS
    // ---------------------------

    // GeneticallyModifiedMonsterPlacer
    public static @CfgMobs
    int GENETICALLYMODIFIED_MONSTERPLACER = getNextItemID();

    // CombinedGenomeMonsterPlacer
    public static @CfgGenomes
    int COMBINEDGENOME_MONSTERPLACER = getNextItemID();

    // CombinedMemoryMonsterPlacer
    public static @CfgMemories
    int COMBINEDMEMORY_MONSTERPLACER = getNextItemID();

    // ---------------------------------
    // GENETICALLY MODIFIED MOB META IDS
    // ---------------------------------
    // COMBINED GENOME META IDS
    // ------------------------

    // Default amount of time we will spend processing some genomes into new ones.
    public final static int COOKTIME_DEFAULT = 2600;

    // Werewolf [Wolf + Villager]
    public final static int GMO_WEREWOLF_METAID_DEFAULT = ++madGMOMobIDs;
    public static @CfgMobs
    int GMO_WEREWOLF_METAID = GMO_WEREWOLF_METAID_DEFAULT;
    public static @CfgProcessing
    int GMO_WEREWOLF_COOKTIME = COOKTIME_DEFAULT;

    // Disgusting Meatcube [Slime + Pig,Cow,Chicken]
    public final static int GMO_MEATCUBE_METAID_DEFAULT = ++madGMOMobIDs;
    public static @CfgMobs
    int GMO_MEATCUBE_METAID = GMO_MEATCUBE_METAID_DEFAULT;
    public static @CfgProcessing
    int GMO_MEATCUBE_COOKTIME = COOKTIME_DEFAULT;

    // Creeper Cow [Creeper + Cow]
    public final static int GMO_CREEPERCOW_METAID_DEFAULT = ++madGMOMobIDs;
    public static @CfgMobs
    int GMO_CREEPERCOW_METAID = GMO_CREEPERCOW_METAID_DEFAULT;
    public static @CfgProcessing
    int GMO_CREEPERCOW_COOKTIME = COOKTIME_DEFAULT;
    
    // Enderslime [Enderman + Slime]
    public final static int GMO_ENDERSLIME_METAID_DEFAULT = ++madGMOMobIDs;
    public static @CfgMobs
    int GMO_ENDERSLIME_METAID = GMO_ENDERSLIME_METAID_DEFAULT;
    public static @CfgProcessing
    int GMO_ENDERSLIME_COOKTIME = COOKTIME_DEFAULT;

    // --------------------------
    // Bart74(bart.74@hotmail.fr)
    // --------------------------

    // Wooly Cow [Cow + Sheep]
    public final static int GMO_WOOLYCOW_METAID_DEFAULT = ++madGMOMobIDs;
    public static @CfgMobs
    int GMO_WOOLYCOW_METAID = GMO_WOOLYCOW_METAID_DEFAULT;
    public static @CfgProcessing
    int GMO_WOOLYCOW_COOKTIME = COOKTIME_DEFAULT;

    // ----------------------------------------
    // Deuce_Loosely(captainlunautic@yahoo.com)
    // ----------------------------------------

    // Shoggoth [Slime + Squid]
    public final static int GMO_SHOGGOTH_METAID_DEFAULT = ++madGMOMobIDs;
    public static @CfgMobs
    int GMO_SHOGGOTH_METAID = GMO_SHOGGOTH_METAID_DEFAULT;
    public static @CfgProcessing
    int GMO_SHOGGOTH_COOKTIME = COOKTIME_DEFAULT;

    // ------------------------------------
    // monodemono(coolplanet3000@gmail.com)
    // ------------------------------------

    // The Abomination [Enderman + Spider]
    public final static int GMO_ABOMINATION_METAID_DEFAULT = ++madGMOMobIDs;
    public static @CfgMobs
    int GMO_ABOMINATION_METAID = GMO_ABOMINATION_METAID_DEFAULT;
    public static @CfgProcessing
    int GMO_ABOMINATION_COOKTIME = COOKTIME_DEFAULT;

    // ---------------------------------
    // Pyrobrine(haskar.spore@gmail.com)
    // ---------------------------------

    // Wither Skeleton [Enderman + Skeleton]
    public final static int GMO_WITHERSKELETON_METAID_DEFAULT = ++madGMOMobIDs;
    public static @CfgMobs
    int GMO_WITHERSKELETON_METAID = GMO_WITHERSKELETON_METAID_DEFAULT;
    public static @CfgProcessing
    int GMO_WITHERSKELETON_COOKTIME = COOKTIME_DEFAULT;

    // Villager Zombie [Villager + Zombie]
    public final static int GMO_VILLAGERZOMBIE_METAID_DEFAULT = ++madGMOMobIDs;
    public static @CfgMobs
    int GMO_VILLAGERZOMBIE_METAID = GMO_VILLAGERZOMBIE_METAID_DEFAULT;
    public static @CfgProcessing
    int GMO_VILLAGERZOMBIE_COOKTIME = COOKTIME_DEFAULT;

    // Skeleton Horse [Horse + Skeleton]
    public final static int GMO_SKELETONHORSE_METAID_DEFAULT = ++madGMOMobIDs;
    public static @CfgMobs
    int GMO_SKELETONHORSE_METAID = GMO_SKELETONHORSE_METAID_DEFAULT;
    public static @CfgProcessing
    int GMO_SKELETONHORSE_COOKTIME = COOKTIME_DEFAULT;

    // Zombie Horse [Zombie + Horse]
    public final static int GMO_ZOMBIEHORSE_METAID_DEFAULT = ++madGMOMobIDs;
    public static @CfgMobs
    int GMO_ZOMBIEHORSE_METAID = GMO_ZOMBIEHORSE_METAID_DEFAULT;
    public static @CfgProcessing
    int GMO_ZOMBIEHORSE_COOKTIME = COOKTIME_DEFAULT;

    // ---------------------------------
    // TheTechnician(tallahlf@gmail.com)
    // ---------------------------------

    // Ender Squid [Enderman + Squid]
    public final static int GMO_ENDERSQUID_METAID_DEFAULT = ++madGMOMobIDs;
    public static @CfgMobs
    int GMO_ENDERSQUID_METAID = GMO_ENDERSQUID_METAID_DEFAULT;
    public static @CfgProcessing
    int GMO_ENDERSQUID_COOKTIME = COOKTIME_DEFAULT;

    // --------
    // CIRCUITS
    // --------
    
    // Circuit Comparator
    public static @CfgCircuits
    int CIRCUIT_COMPARATOR = getNextItemID();
    
    // Circuit Diamond
    public static @CfgCircuits
    int CIRCUIT_DIAMOND = getNextItemID();
    
    // Circuit Emerald
    public static @CfgCircuits
    int CIRCUIT_EMERALD = getNextItemID();
    
    // Circuit Ender Eye
    public static @CfgCircuits
    int CIRCUIT_ENDEREYE = getNextItemID();
    
    // Circuit Ender Pearl
    public static @CfgCircuits
    int CIRCUIT_ENDERPEARL = getNextItemID();
    
    // Circuit Glowstone
    public static @CfgCircuits
    int CIRCUIT_GLOWSTONE = getNextItemID();
    
    // Circuit Redstone
    public static @CfgCircuits
    int CIRCUIT_REDSTONE = getNextItemID();
    
    // Circuit Spider Eye
    public static @CfgCircuits
    int CIRCUIT_SPIDEREYE = getNextItemID();
    
    // ----------
    // COMPONENTS
    // ----------
    
    // Component Case
    public static @CfgComponents
    int COMPONENT_CASE = getNextItemID();
    
    // Component Computer
    public static @CfgComponents
    int COMPONENT_COMPUTER = getNextItemID();

    // Component CPU
    public static @CfgComponents
    int COMPONENT_CPU = getNextItemID();
    
    // Component Fan
    public static @CfgComponents
    int COMPONENT_FAN = getNextItemID();

    // Component Fused Quartz 
    public static @CfgComponents
    int COMPONENT_FUSEDQUARTZ = getNextItemID();
    
    // Component Magnetic Tape
    public static @CfgComponents
    int COMPONENT_MAGNETICTAPE = getNextItemID();
    
    // Component Power Supply
    public static @CfgComponents
    int COMPONENT_POWERSUPPLY = getNextItemID();
    
    // Component RAM
    public static @CfgComponents
    int COMPONENT_RAM = getNextItemID();
    
    // Component Screen
    public static @CfgComponents
    int COMPONENT_SCREEN = getNextItemID();
    
    // Component Silicon Wafer
    public static @CfgComponents
    int COMPONENT_SILICONWAFER = getNextItemID();
    
    // Component Transistor
    public static @CfgComponents
    int COMPONENT_TRANSISTOR = getNextItemID();
    
    // Component Thumper
    public static @CfgComponents
    int COMPONENT_THUMPER = getNextItemID();
    
    // Component Enderslime
    public static @CfgComponents
    int COMPONENT_ENDERSLIME = getNextItemID();
    
    // -----
    // ITEMS
    // -----

    // Liquid Bucket DNA
    public static @CfgId
    int LIQUIDDNA_BUCKET = getNextItemID();

    // Bucket of Liquid Mutant DNA
    public static @CfgId
    int LIQUIDDNA_MUTANT_BUCKET = getNextItemID();

    // Empty Data Reel
    public static @CfgId
    int DATAREEL_EMPTY = getNextItemID();

    // -------
    // NEEDLES
    // -------

    // NeedleEmptyItem to take DNA from mobs.
    public static @CfgNeedles
    int NEEDLE_EMPTY = getNextItemID();

    // Cave Spider needle.
    public static @CfgNeedles
    int NEEDLE_CAVESPIDER = getNextItemID();

    // Chicken needle.
    public static @CfgNeedles
    int NEEDLE_CHICKEN = getNextItemID();

    // Cow needle.
    public static @CfgNeedles
    int NEEDLE_COW = getNextItemID();

    // Creeper needle.
    public static @CfgNeedles
    int NEEDLE_CREEPER = getNextItemID();

    // Bat needle.
    public static @CfgNeedles
    int NEEDLE_BAT = getNextItemID();

    // Enderman needle.
    public static @CfgNeedles
    int NEEDLE_ENDERMAN = getNextItemID();

    // Horse needle.
    public static @CfgNeedles
    int NEEDLE_HORSE = getNextItemID();

    // Mushroom Cow needle.
    public static @CfgNeedles
    int NEEDLE_MUSHROOMCOW = getNextItemID();

    // Ocelot needle.
    public static @CfgNeedles
    int NEEDLE_OCELOT = getNextItemID();

    // Pig needle.
    public static @CfgNeedles
    int NEEDLE_PIG = getNextItemID();

    // Sheep needle.
    public static @CfgNeedles
    int NEEDLE_SHEEP = getNextItemID();

    // Spider needle.
    public static @CfgNeedles
    int NEEDLE_SPIDER = getNextItemID();

    // Squid needle.
    public static @CfgNeedles
    int NEEDLE_SQUID = getNextItemID();

    // Villager needle.
    public static @CfgNeedles
    int NEEDLE_VILLAGER = getNextItemID();

    // Witch needle.
    public static @CfgNeedles
    int NEEDLE_WITCH = getNextItemID();

    // Wolf needle.
    public static @CfgNeedles
    int NEEDLE_WOLF = getNextItemID();

    // Zombie needle.
    public static @CfgNeedles
    int NEEDLE_ZOMBIE = getNextItemID();

    // Mutant needle.
    public static @CfgNeedles
    int NEEDLE_MUTANT = getNextItemID();

    // Dirty needle.
    public static @CfgNeedles
    int NEEDLE_DIRTY = getNextItemID();

    // -----------
    // DNA SAMPLES
    // -----------

    // Cave Spider DNA
    public static @CfgDNA
    int DNA_CAVESPIDER = getNextItemID();

    // Chicken DNA.
    public static @CfgDNA
    int DNA_CHICKEN = getNextItemID();

    // Cow DNA.
    public static @CfgDNA
    int DNA_COW = getNextItemID();

    // Creeper DNA.
    public static @CfgDNA
    int DNA_CREEPER = getNextItemID();

    // Bat DNA.
    public static @CfgDNA
    int DNA_BAT = getNextItemID();

    // Enderman DNA
    public static @CfgDNA
    int DNA_ENDERMAN = getNextItemID();

    // Ghast DNA
    public static @CfgDNA
    int DNA_GHAST = getNextItemID();

    // Horse DNA
    public static @CfgDNA
    int DNA_HORSE = getNextItemID();

    // Mushroom Cow DNA
    public static @CfgDNA
    int DNA_MUSHROOMCOW = getNextItemID();

    // Ocelot DNA
    public static @CfgDNA
    int DNA_OCELOT = getNextItemID();

    // Pig DNA.
    public static @CfgDNA
    int DNA_PIG = getNextItemID();

    // Pig Zombie DNA
    public static @CfgDNA
    int DNA_PIGZOMBIE = getNextItemID();

    // Sheep DNA
    public static @CfgDNA
    int DNA_SHEEP = getNextItemID();

    // Skeleton DNA
    public static @CfgDNA
    int DNA_SKELETON = getNextItemID();

    // Spider DNA.
    public static @CfgDNA
    int DNA_SPIDER = getNextItemID();

    // Slime DNA.
    public static @CfgDNA
    int DNA_SLIME = getNextItemID();

    // Squid DNA
    public static @CfgDNA
    int DNA_SQUID = getNextItemID();

    // Villager DNA.
    public static @CfgDNA
    int DNA_VILLAGER = getNextItemID();

    // Witch DNA
    public static @CfgDNA
    int DNA_WITCH = getNextItemID();

    // Wolf DNA
    public static @CfgDNA
    int DNA_WOLF = getNextItemID();

    // Zombie DNA.
    public static @CfgDNA
    int DNA_ZOMBIE = getNextItemID();

    // Mutant DNA (catch all for mutations and other various creatures).
    public static @CfgDNA
    int DNA_MUTANT = getNextItemID();

    // -----------------
    // GENOME DATA REELS
    // -----------------

    // Cave Spider Genome Data Reel
    public static @CfgGenomes
    int GENOME_CAVESPIDER = getNextItemID();

    // Chicken Genome Data Reel
    public static @CfgGenomes
    int GENOME_CHICKEN = getNextItemID();

    // Cow Genome Data Reel
    public static @CfgGenomes
    int GENOME_COW = getNextItemID();

    // Creeper Genome Data Reel
    public static @CfgGenomes
    int GENOME_CREEPER = getNextItemID();

    // Bat Genome Data Reel
    public static @CfgGenomes
    int GENOME_BAT = getNextItemID();

    // Enderman Genome Data Reel
    public static @CfgGenomes
    int GENOME_ENDERMAN = getNextItemID();

    // Ghast Genome Data Reel
    public static @CfgGenomes
    int GENOME_GHAST = getNextItemID();

    // Horse Genome Data Reel
    public static @CfgGenomes
    int GENOME_HORSE = getNextItemID();

    // Mushroom Cow Genome Data Reel
    public static @CfgGenomes
    int GENOME_MUSHROOMCOW = getNextItemID();

    // Ocelot Genome Data Reel
    public static @CfgGenomes
    int GENOME_OCELOT = getNextItemID();

    // Pig Genome Data Reel
    public static @CfgGenomes
    int GENOME_PIG = getNextItemID();

    // Pig Zombie Data Reel
    public static @CfgGenomes
    int GENOME_PIGZOMBIE = getNextItemID();

    // Sheep Genome Data Reel
    public static @CfgGenomes
    int GENOME_SHEEP = getNextItemID();

    // Skeleton Genome Data Reel
    public static @CfgGenomes
    int GENOME_SKELETON = getNextItemID();

    // Spider Genome Data Reel
    public static @CfgGenomes
    int GENOME_SPIDER = getNextItemID();

    // Slime Genome Data Reel
    public static @CfgGenomes
    int GENOME_SLIME = getNextItemID();

    // Squid Genome Data Reel
    public static @CfgGenomes
    int GENOME_SQUID = getNextItemID();

    // Villager Genome Data Reel
    public static @CfgGenomes
    int GENOME_VILLAGER = getNextItemID();

    // Witch Genome Data Reel
    public static @CfgGenomes
    int GENOME_WITCH = getNextItemID();

    // Wolf Genome Data Reel
    public static @CfgGenomes
    int GENOME_WOLF = getNextItemID();

    // Zombie Genome Data Reel
    public static @CfgGenomes
    int GENOME_ZOMBIE = getNextItemID();

    // -----------------------
    // TILE ENTITY DECLARATION
    // -----------------------

    // Defaults for power storage and input/output rates.
    public final static long MACHINE_CAPACITY_DEFAULT = 100000;
    public final static long MACHINE_TRANSFERRATE_DEFAULT = 200;
    public final static long MACHINE_CONSUMERATE_DEFAULT = 1;

    // DNA Extractor
    public static @CfgId(block = true) int DNA_EXTRACTOR = getNextBlockID();
    public static @CfgEnergy long DNAEXTRACTOR_CAPACTITY = MACHINE_CAPACITY_DEFAULT;
    public static @CfgEnergy long DNAEXTRACTOR_INPUT = MACHINE_TRANSFERRATE_DEFAULT;
    public static @CfgEnergy long DNAEXTRACTOR_CONSUME = MACHINE_CONSUMERATE_DEFAULT;
    public static @CfgHelp String DNAEXTRACTOR_HELP = "http://madsciencemod.com/minecraft-item/dna-extractor/";

    // Needle Sanitizer
    public static @CfgId(block = true) int SANTITIZER = getNextBlockID();
    public static @CfgEnergy long SANTITIZER_CAPACTITY = MACHINE_CAPACITY_DEFAULT;
    public static @CfgEnergy long SANTITIZER_INPUT = MACHINE_TRANSFERRATE_DEFAULT;
    public static @CfgEnergy long SANTITIZER_CONSUME = MACHINE_CONSUMERATE_DEFAULT;
    public static @CfgHelp String SANTITIZER_HELP = "http://madsciencemod.com/minecraft-item/syringe-sanitizer/";

    // Computer Mainframe
    public static @CfgId(block = true) int MAINFRAME = getNextBlockID();
    public static @CfgEnergy long MAINFRAME_CAPACTITY = MACHINE_CAPACITY_DEFAULT;
    public static @CfgEnergy long MAINFRAME_INPUT = MACHINE_TRANSFERRATE_DEFAULT;
    public static @CfgEnergy long MAINFRAME_CONSUME = MACHINE_CONSUMERATE_DEFAULT;
    public static @CfgHelp String MAINFRAME_HELP = "http://madsciencemod.com/minecraft-item/computer-mainframe/";

    // Genetic Sequencer
    public static @CfgId(block = true) int GENE_SEQUENCER = getNextBlockID();
    public static @CfgEnergy long SEQUENCER_CAPACTITY = MACHINE_CAPACITY_DEFAULT;
    public static @CfgEnergy long SEQUENCER_INPUT = MACHINE_TRANSFERRATE_DEFAULT;
    public static @CfgEnergy long SEQUENCER_CONSUME = MACHINE_CONSUMERATE_DEFAULT;
    public static @CfgHelp String SEQUENCER_HELP = "http://madsciencemod.com/minecraft-item/genetic-sequencer/";

    // Cryogenic Freezer
    public static @CfgId(block = true) int CRYOFREEZER = getNextBlockID();
    public static @CfgEnergy long CRYOFREEZER_CAPACTITY = MACHINE_CAPACITY_DEFAULT;
    public static @CfgEnergy long CRYOFREEZER_INPUT = MACHINE_TRANSFERRATE_DEFAULT;
    public static @CfgEnergy long CRYOFREEZER_CONSUME = MACHINE_CONSUMERATE_DEFAULT;
    public static @CfgHelp String CRYOFREEZER_HELP = "http://madsciencemod.com/minecraft-item/cryogenic-freezer/";

    // Genome Incubator
    public static @CfgId(block = true) int INCUBATOR = getNextBlockID();
    public static @CfgEnergy long INCUBATOR_CAPACTITY = MACHINE_CAPACITY_DEFAULT;
    public static @CfgEnergy long INCUBATOR_INPUT = MACHINE_TRANSFERRATE_DEFAULT;
    public static @CfgEnergy long INCUBATOR_CONSUME = MACHINE_CONSUMERATE_DEFAULT;
    public static @CfgHelp String INCUBATOR_HELP = "http://madsciencemod.com/minecraft-item/genome-incubator/";

    // Meat Cube
    public static @CfgId(block = true) int MEATCUBE = getNextBlockID();
    public static @CfgHelp String MEATCUBE_HELP = "http://madsciencemod.com/minecraft-item/meat-cube/";

    // Cryogenic Tube
    public static @CfgId(block = true) int CRYOTUBE = getNextBlockID();
    public static @CfgId(block = true) int CRYOTUBEGHOST = getNextBlockID();
    public static @CfgEnergy long CRYOTUBE_CAPACTITY = 225120000000L;
    public static @CfgEnergy long CRYOTUBE_OUTPUT = 562800000L;
    public static @CfgEnergy long CRYOTUBE_PRODUCE = 1407000L;
    public static @CfgHelp String CRYOTUBE_HELP = "http://madsciencemod.com/minecraft-item/cryogenic-tube/";

    // Thermosonic Bonder
    public static @CfgId(block = true) int THERMOSONIC = getNextBlockID();
    public static @CfgEnergy long THERMOSONIC_CAPACTITY = MACHINE_CAPACITY_DEFAULT;
    public static @CfgEnergy long THERMOSONIC_INPUT = MACHINE_TRANSFERRATE_DEFAULT;
    public static @CfgEnergy long THERMOSONIC_CONSUME = MACHINE_CONSUMERATE_DEFAULT;
    public static @CfgHelp String THERMOSONIC_HELP = "http://madsciencemod.com/minecraft-item/thermosonic-bonder/";

    // Data Reel Duplicator
    public static @CfgId(block = true) int DATADUPLICATOR = getNextBlockID();
    public static @CfgEnergy long DATADUPLICATOR_CAPACTITY = MACHINE_CAPACITY_DEFAULT;
    public static @CfgEnergy long DATADUPLICATOR_INPUT = MACHINE_TRANSFERRATE_DEFAULT;
    public static @CfgEnergy long DATADUPLICATOR_CONSUME = MACHINE_CONSUMERATE_DEFAULT;
    public static @CfgHelp String DATADUPLICATOR_HELP = "http://madsciencemod.com/minecraft-item/data-reel-duplicator/";

    // Soniclocator
    public static @CfgId(block = true) int SONICLOCATOR = getNextBlockID();
    public static @CfgId(block = true) int SONICLOCATOREGHOST = getNextBlockID();
    public static @CfgEnergy long SONICLOCATOR_CAPACTITY = MACHINE_CAPACITY_DEFAULT;
    public static @CfgEnergy long SONICLOCATOR_INPUT = MACHINE_TRANSFERRATE_DEFAULT;
    public static @CfgEnergy long SONICLOCATOR_CONSUME = MACHINE_CONSUMERATE_DEFAULT;
    public static @CfgHelp String SONICLOCATOR_HELP = "http://madsciencemod.com/minecraft-item/soniclocator/";
    
    // Clay Furnace
    public static @CfgId(block = true) int CLAYFURNACE = getNextBlockID();
    public static @CfgHelp String CLAYFURNACE_HELP = "http://madsciencemod.com/minecraft-item/clay-furnace/";

    // -----------------
    // FLUID DECLARATION
    // -----------------

    // Liquid DNA
    public static @CfgId(block = true) int LIQUIDDNA = getNextBlockID();

    // Liquid Mutant DNA
    public static @CfgId(block = true) int LIQUIDDNA_MUTANT = getNextBlockID();

    // -----------------
    // BLOCK DECLARATION
    // -----------------

    // Abomination Egg
    public static @CfgId(block = true) int ABOMINATIONEGG = getNextBlockID();
    
    // Enderslime Block
    public static @CfgId(block = true) int ENDERSLIMEBLOCK = getNextBlockID();

    // ----------------
    // FEATURE SWITCHES
    // ----------------

    // Determines if 'The Abomination' should lay eggs.
    public static @CfgBool boolean ABOMINATION_LAYSEGGS = true;

    // Determines if 'The Abomination' is allowed to teleport.
    public static @CfgBool boolean ABOMINATION_TELEPORTS = true;

    // Needles decay into dirty ones.
    public static @CfgBool boolean DECAY_BLOODWORK = true;

    // Needles decay at a rate of one health every 6000 ticks.
    public static @CfgInt int DECAY_DELAY_IN_SECONDS = 30;
    
    // Determines how many seconds clay furnace will cook before finishing
    public static @CfgInt int CLAYFURNACE_COOKTIME_IN_SECONDS = 420;
    
    // Distance which we will send packet updates about machines to players.
    public static @CfgInt int PACKETSEND_RADIUS = 25;
    
    // ID that will determine block to be used to 'unlock' thermosonic bonder and thus every other item in the mod.
    public static @CfgInt int THERMOSONICBONDER_FINALSACRIFICE = 138;
    
    // ----------------
    // UPDATES SWITCHES
    // ----------------
    
    // Determines if we should inform the user about updates and nightly builds.
    public static @CfgUpdates(isBool = true) boolean UPDATE_CHECKER = true;
    
    // Determines what the update URL should be for the mod.
    public static @CfgUpdates String UPDATE_URL = "http://madsciencemod.com:8080/job/Mad%20Science/Release%20Version/api/xml?xpath=/freeStyleBuild/number";

    public static void load(Configuration config)
    {
        try
        {
            config.load();
            Field[] fields = MadConfig.class.getFields();
            for (Field field : fields)
            {
                CfgId annoBlock = field.getAnnotation(CfgId.class);
                CfgBool annoBool = field.getAnnotation(CfgBool.class);
                CfgInt annoInt = field.getAnnotation(CfgInt.class);
                CfgMobs annoMobs = field.getAnnotation(CfgMobs.class);
                CfgGenomes annoGenomes = field.getAnnotation(CfgGenomes.class);
                CfgDNA annoDNA = field.getAnnotation(CfgDNA.class);
                CfgNeedles annoNeedles = field.getAnnotation(CfgNeedles.class);
                CfgEnergy annoEnergy = field.getAnnotation(CfgEnergy.class);
                CfgProcessing annoCPUTime = field.getAnnotation(CfgProcessing.class);
                CfgCircuits annoCircuits= field.getAnnotation(CfgCircuits.class);
                CfgComponents annoComponents = field.getAnnotation(CfgComponents.class);
                CfgUpdates annoUpdates = field.getAnnotation(CfgUpdates.class);
                CfgHelp annoHelp = field.getAnnotation(CfgHelp.class);

                // Config property is block or item.
                if (annoBlock != null &&
                    annoCPUTime == null &&
                    annoBool == null &&
                    annoInt == null &&
                    annoMobs == null &&
                    annoGenomes == null &&
                    annoDNA == null &&
                    annoNeedles == null &&
                    annoEnergy == null &&
                    annoCircuits == null &&
                    annoComponents == null &&
                    annoUpdates == null &&
                    annoHelp == null)
                {
                    int id = field.getInt(null);
                    if (annoBlock.block())
                    {
                        id = config.getBlock(field.getName(), id).getInt();
                    }
                    else
                    {
                        id = config.getItem(field.getName(), id).getInt();
                    }
                    field.setInt(null, id);
                }
                else if (annoHelp != null &&
                        annoCPUTime == null &&
                        annoBool == null &&
                        annoInt == null &&
                        annoMobs == null &&
                        annoGenomes == null &&
                        annoDNA == null &&
                        annoNeedles == null &&
                        annoEnergy == null &&
                        annoCircuits == null &&
                        annoComponents == null &&
                        annoBlock == null &&
                        annoUpdates == null)
                    {
                        String helpURL = (String) field.get(String.class);
                        String updateurl = config.get(MadConfig.CATAGORY_HELP, field.getName(), helpURL).getString();
                        field.set(String.class, updateurl);
                    }
                else if (annoUpdates != null &&
                        annoCPUTime == null &&
                        annoBool == null &&
                        annoInt == null &&
                        annoMobs == null &&
                        annoGenomes == null &&
                        annoDNA == null &&
                        annoNeedles == null &&
                        annoEnergy == null &&
                        annoCircuits == null &&
                        annoComponents == null &&
                        annoBlock == null &&
                        annoHelp == null)
                    {
                        if (field.isAnnotationPresent(CfgUpdates.class) && annoUpdates.isBool())
                        {
                            boolean bool = field.getBoolean(null);
                            bool = config.get(MadConfig.CATAGORY_UPDATES, field.getName(), bool).getBoolean(bool);
                            field.setBoolean(null, bool);
                        }
                        else
                        {
                            String possibleURL = (String) field.get(String.class);
                            String updateurl = config.get(MadConfig.CATAGORY_UPDATES, field.getName(), possibleURL).getString();
                            field.set(String.class, updateurl);
                        }
                    }
                else if (annoEnergy != null &&
                        annoCPUTime == null &&
                        annoBlock == null &&
                        annoBool == null &&
                        annoInt == null &&
                        annoMobs == null &&
                        annoGenomes == null &&
                        annoDNA == null &&
                        annoNeedles == null &&
                        annoCircuits == null &&
                        annoComponents == null &&
                        annoUpdates == null &&
                        annoHelp == null)
                {
                    // Config property is energy long integer.
                    if (field.isAnnotationPresent(CfgEnergy.class))
                    {
                        Double someInt = field.getDouble(null);
                        someInt = config.get(MadConfig.CATAGORY_ENERGY, field.getName(), someInt).getDouble(someInt);
                        field.setLong(null, someInt.longValue());
                    }
                }
                else if (annoBool != null && 
                        annoCPUTime == null &&
                        annoBlock == null &&
                        annoInt == null &&
                        annoMobs == null && 
                        annoGenomes == null &&
                        annoDNA == null &&
                        annoNeedles == null && 
                        annoEnergy == null &&
                        annoCircuits == null &&
                        annoComponents == null &&
                        annoUpdates == null &&
                        annoHelp == null)
                {
                    // Config property is bool.
                    if (field.isAnnotationPresent(CfgBool.class))
                    {
                        boolean bool = field.getBoolean(null);
                        bool = config.get(Configuration.CATEGORY_GENERAL, field.getName(), bool).getBoolean(bool);
                        field.setBoolean(null, bool);
                    }
                }
                else if (annoCircuits != null &&
                        annoInt == null &&
                        annoCPUTime == null &&
                        annoBlock == null &&
                        annoBool == null && 
                        annoMobs == null &&
                        annoGenomes == null &&
                        annoDNA == null && 
                        annoNeedles == null &&
                        annoEnergy == null &&
                        annoComponents == null &&
                        annoUpdates == null &&
                        annoHelp == null)
                {
                    // Config property is circuit.
                    if (field.isAnnotationPresent(CfgCircuits.class))
                    {
                        int someInt = field.getInt(null);
                        someInt = config.get(MadConfig.CATAGORY_CIRCUITS, field.getName(), someInt).getInt(someInt);
                        field.setInt(null, someInt);
                    }
                }
                else if (annoComponents != null && 
                        annoInt == null &&
                        annoCPUTime == null &&
                        annoBlock == null &&
                        annoBool == null && 
                        annoMobs == null &&
                        annoGenomes == null &&
                        annoDNA == null && 
                        annoNeedles == null &&
                        annoEnergy == null &&
                        annoCircuits == null &&
                        annoUpdates == null &&
                        annoHelp == null)
                {
                    // Config property is component.
                    if (field.isAnnotationPresent(CfgComponents.class))
                    {
                        int someInt = field.getInt(null);
                        someInt = config.get(MadConfig.CATAGORY_COMPONENTS, field.getName(), someInt).getInt(someInt);
                        field.setInt(null, someInt);
                    }
                }
                else if (annoInt != null &&
                        annoCPUTime == null &&
                        annoBlock == null &&
                        annoBool == null && 
                        annoMobs == null &&
                        annoGenomes == null &&
                        annoDNA == null && 
                        annoNeedles == null &&
                        annoEnergy == null &&
                        annoCircuits == null &&
                        annoComponents == null &&
                        annoUpdates == null &&
                        annoHelp == null)
                {
                    // Config property is int.
                    if (field.isAnnotationPresent(CfgInt.class))
                    {
                        int someInt = field.getInt(null);
                        someInt = config.get(Configuration.CATEGORY_GENERAL, field.getName(), someInt).getInt(someInt);
                        field.setInt(null, someInt);
                    }
                }
                else if (annoCPUTime != null && 
                        annoBlock == null &&
                        annoBool == null &&
                        annoInt == null &&
                        annoMobs == null && 
                        annoGenomes == null &&
                        annoDNA == null &&
                        annoNeedles == null &&
                        annoEnergy == null &&
                        annoCircuits == null &&
                        annoComponents == null &&
                        annoUpdates == null &&
                        annoHelp == null)
                {
                    // Config property is mainframe processing time.
                    if (field.isAnnotationPresent(CfgProcessing.class))
                    {
                        int someInt = field.getInt(null);
                        someInt = config.get(MadConfig.CATAGORY_PROCESSING, field.getName(), someInt).getInt(someInt);
                        field.setInt(null, someInt);
                    }
                }
                else if (annoMobs != null &&
                        annoCPUTime == null &&
                        annoBlock == null &&
                        annoBool == null &&
                        annoInt == null &&
                        annoGenomes == null &&
                        annoDNA == null &&
                        annoNeedles == null && 
                        annoEnergy == null &&
                        annoCircuits == null &&
                        annoComponents == null &&
                        annoUpdates == null &&
                        annoHelp == null)
                {
                    // Config property is mobs.
                    if (field.isAnnotationPresent(CfgMobs.class))
                    {
                        int someInt = field.getInt(null);
                        someInt = config.get(MadConfig.CATAGORY_MOBS, field.getName(), someInt).getInt(someInt);
                        field.setInt(null, someInt);
                    }
                }
                else if (annoGenomes != null &&
                        annoCPUTime == null &&
                        annoBlock == null &&
                        annoBool == null &&
                        annoInt == null &&
                        annoMobs == null &&
                        annoDNA == null && 
                        annoNeedles == null &&
                        annoEnergy == null &&
                        annoCircuits == null &&
                        annoComponents == null &&
                        annoUpdates == null &&
                        annoHelp == null)
                {
                    // Config property is genomes.
                    if (field.isAnnotationPresent(CfgGenomes.class))
                    {
                        int someInt = field.getInt(null);
                        someInt = config.get(MadConfig.CATAGORY_GENOMES, field.getName(), someInt).getInt(someInt);
                        field.setInt(null, someInt);
                    }
                }
                else if (annoDNA != null &&
                        annoCPUTime == null &&
                        annoBlock == null && 
                        annoBool == null && 
                        annoInt == null && 
                        annoMobs == null &&
                        annoGenomes == null &&
                        annoNeedles == null &&
                        annoEnergy == null &&
                        annoCircuits == null &&
                        annoComponents == null &&
                        annoUpdates == null &&
                        annoHelp == null)
                {
                    // Config property is DNA.
                    if (field.isAnnotationPresent(CfgDNA.class))
                    {
                        int someInt = field.getInt(null);
                        someInt = config.get(MadConfig.CATAGORY_DNA, field.getName(), someInt).getInt(someInt);
                        field.setInt(null, someInt);
                    }
                }
                else if (annoNeedles != null &&
                        annoCPUTime == null &&
                        annoBlock == null && 
                        annoBool == null &&
                        annoInt == null &&
                        annoMobs == null &&
                        annoGenomes == null &&
                        annoDNA == null && 
                        annoEnergy == null &&
                        annoCircuits == null &&
                        annoComponents == null &&
                        annoUpdates == null &&
                        annoHelp == null)
                {
                    // Config property is needle.
                    if (field.isAnnotationPresent(CfgNeedles.class))
                    {
                        int someInt = field.getInt(null);
                        someInt = config.get(MadConfig.CATAGORY_NEEDLES, field.getName(), someInt).getInt(someInt);
                        field.setInt(null, someInt);
                    }
                }
            }
        }
        catch (Exception e)
        {
            // failed to load config log
            LogWrapper.log(Level.WARNING, "Failed to load configuration file!");
        }
        finally
        {
            config.save();
        }
    }

}
package madscience;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.util.logging.Level;

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

    // Item ID's begin at 5000 range and count up from there.
    private static int madItemIDs = 3840;

    // Block ID's begin at 500 range and count up from there.
    private static int madBlockIDs = 500;

    // Mob ID's for genetically modified entity list.
    private static int madGMOMobIDs = 50;

    // ---------------------------
    // CUSTOM MONSTER PLACER ITEMS
    // ---------------------------

    // GeneticallyModifiedMonsterPlacer
    public final static int GENETICALLYMODIFIED_MONSTERPLACER_DEFAULT = ++madItemIDs;
    public static @CfgMobs
    int GENETICALLYMODIFIED_MONSTERPLACER = GENETICALLYMODIFIED_MONSTERPLACER_DEFAULT;

    // CombinedGenomeMonsterPlacer
    public final static int COMBINEDGENOME_MONSTERPLACER_DEFAULT = ++madItemIDs;
    public static @CfgGenomes
    int COMBINEDGENOME_MONSTERPLACER = COMBINEDGENOME_MONSTERPLACER_DEFAULT;

    // CombinedMemoryMonsterPlacer
    public final static int COMBINEDMEMORY_MONSTERPLACER_DEFAULT = ++madItemIDs;
    public static @CfgMemories
    int COMBINEDMEMORY_MONSTERPLACER = COMBINEDMEMORY_MONSTERPLACER_DEFAULT;

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
    public final static int CIRCUIT_COMPARATOR_DEFAULT = ++madItemIDs;
    public static @CfgCircuits
    int CIRCUIT_COMPARATOR = CIRCUIT_COMPARATOR_DEFAULT;
    
    // Circuit Diamond
    public final static int CIRCUIT_DIAMOND_DEFAULT = ++madItemIDs;
    public static @CfgCircuits
    int CIRCUIT_DIAMOND = CIRCUIT_DIAMOND_DEFAULT;
    
    // Circuit Emerald
    public final static int CIRCUIT_EMERALD_DEFAULT = ++madItemIDs;
    public static @CfgCircuits
    int CIRCUIT_EMERALD = CIRCUIT_EMERALD_DEFAULT;
    
    // Circuit Ender Eye
    public final static int CIRCUIT_ENDEREYE_DEFAULT = ++madItemIDs;
    public static @CfgCircuits
    int CIRCUIT_ENDEREYE = CIRCUIT_ENDEREYE_DEFAULT;
    
    // Circuit Ender Pearl
    public final static int CIRCUIT_ENDERPEARL_DEFAULT = ++madItemIDs;
    public static @CfgCircuits
    int CIRCUIT_ENDERPEARL = CIRCUIT_ENDERPEARL_DEFAULT;
    
    // Circuit Glowstone
    public final static int CIRCUIT_GLOWSTONE_DEFAULT = ++madItemIDs;
    public static @CfgCircuits
    int CIRCUIT_GLOWSTONE = CIRCUIT_GLOWSTONE_DEFAULT;
    
    // Circuit Redstone
    public final static int CIRCUIT_REDSTONE_DEFAULT = ++madItemIDs;
    public static @CfgCircuits
    int CIRCUIT_REDSTONE = CIRCUIT_REDSTONE_DEFAULT;
    
    // Circuit Spider Eye
    public final static int CIRCUIT_SPIDEREYE_DEFAULT = ++madItemIDs;
    public static @CfgCircuits
    int CIRCUIT_SPIDEREYE = CIRCUIT_SPIDEREYE_DEFAULT;
    
    // ----------
    // COMPONENTS
    // ----------
    
    // Component Case
    public final static int COMPONENT_CASE_DEFAULT = ++madItemIDs;
    public static @CfgComponents
    int COMPONENT_CASE = COMPONENT_CASE_DEFAULT;
    
    // Component Computer
    public final static int COMPONENT_COMPUTER_DEFAULT = ++madItemIDs;
    public static @CfgComponents
    int COMPONENT_COMPUTER = COMPONENT_COMPUTER_DEFAULT;

    // Component CPU
    public final static int COMPONENT_CPU_DEFAULT = ++madItemIDs;
    public static @CfgComponents
    int COMPONENT_CPU = COMPONENT_CPU_DEFAULT;
    
    // Component Fan
    public final static int COMPONENT_FAN_DEFAULT = ++madItemIDs;
    public static @CfgComponents
    int COMPONENT_FAN = COMPONENT_FAN_DEFAULT;

    // Component Fused Quartz 
    public final static int COMPONENT_FUSEDQUARTZ_DEFAULT = ++madItemIDs;
    public static @CfgComponents
    int COMPONENT_FUSEDQUARTZ = COMPONENT_FUSEDQUARTZ_DEFAULT;
    
    // Component Magnetic Tape
    public final static int COMPONENT_MAGNETICTAPE_DEFAULT = ++madItemIDs;
    public static @CfgComponents
    int COMPONENT_MAGNETICTAPE = COMPONENT_MAGNETICTAPE_DEFAULT;
    
    // Component Power Supply
    public final static int COMPONENT_POWERSUPPLY_DEFAULT = ++madItemIDs;
    public static @CfgComponents
    int COMPONENT_POWERSUPPLY = COMPONENT_POWERSUPPLY_DEFAULT;
    
    // Component RAM
    public final static int COMPONENT_RAM_DEFAULT = ++madItemIDs;
    public static @CfgComponents
    int COMPONENT_RAM = COMPONENT_RAM_DEFAULT;
    
    // Component Screen
    public final static int COMPONENT_SCREEN_DEFAULT = ++madItemIDs;
    public static @CfgComponents
    int COMPONENT_SCREEN = COMPONENT_SCREEN_DEFAULT;
    
    // Component Silicon Wafer
    public final static int COMPONENT_SILICONWAFER_DEFAULT = ++madItemIDs;
    public static @CfgComponents
    int COMPONENT_SILICONWAFER = COMPONENT_SILICONWAFER_DEFAULT;
    
    // Component Transistor
    public final static int COMPONENT_TRANSISTOR_DEFAULT = ++madItemIDs;
    public static @CfgComponents
    int COMPONENT_TRANSISTOR = COMPONENT_TRANSISTOR_DEFAULT;
    
    // Component Thumper
    public final static int COMPONENT_THUMPER_DEFAULT = ++madItemIDs;
    public static @CfgComponents
    int COMPONENT_THUMPER = COMPONENT_THUMPER_DEFAULT;
    
    // Component Enderslime
    public final static int COMPONENT_ENDERSLIME_DEFAULT = ++madItemIDs;
    public static @CfgComponents
    int COMPONENT_ENDERSLIME = COMPONENT_ENDERSLIME_DEFAULT;
    
    // -----
    // ITEMS
    // -----

    // Liquid Bucket DNA
    public final static int LIQUIDDNA_BUCKET_DEFAULT = ++madItemIDs;
    public static @CfgId
    int LIQUIDDNA_BUCKET = LIQUIDDNA_BUCKET_DEFAULT;

    // Bucket of Liquid Mutant DNA
    public final static int LIQUIDDNA_MUTANT_BUCKET_DEFAULT = ++madItemIDs;
    public static @CfgId
    int LIQUIDDNA_MUTANT_BUCKET = LIQUIDDNA_MUTANT_BUCKET_DEFAULT;

    // Empty Data Reel
    public final static int DATAREEL_EMPTY_DEFAULT = ++madItemIDs;
    public static @CfgId
    int DATAREEL_EMPTY = DATAREEL_EMPTY_DEFAULT;

    // -------
    // NEEDLES
    // -------

    // NeedleEmptyItem to take DNA from mobs.
    public final static int NEEDLE_DEFAULT = ++madItemIDs;
    public static @CfgNeedles
    int NEEDLE = NEEDLE_DEFAULT;

    // Cave Spider needle.
    public final static int NEEDLE_CAVESPIDER_DEFAULT = ++madItemIDs;
    public static @CfgNeedles
    int NEEDLE_CAVESPIDER = NEEDLE_CAVESPIDER_DEFAULT;

    // Chicken needle.
    public final static int NEEDLE_CHICKEN_DEFAULT = ++madItemIDs;
    public static @CfgNeedles
    int NEEDLE_CHICKEN = NEEDLE_CHICKEN_DEFAULT;

    // Cow needle.
    public final static int NEEDLE_COW_DEFAULT = ++madItemIDs;
    public static @CfgNeedles
    int NEEDLE_COW = NEEDLE_COW_DEFAULT;

    // Creeper needle.
    public final static int NEEDLE_CREEPER_DEFAULT = ++madItemIDs;
    public static @CfgNeedles
    int NEEDLE_CREEPER = NEEDLE_CREEPER_DEFAULT;

    // Bat needle.
    public final static int NEEDLE_BAT_DEFAULT = ++madItemIDs;
    public static @CfgNeedles
    int NEEDLE_BAT = NEEDLE_BAT_DEFAULT;

    // Enderman needle.
    public final static int NEEDLE_ENDERMAN_DEFAULT = ++madItemIDs;
    public static @CfgNeedles
    int NEEDLE_ENDERMAN = NEEDLE_ENDERMAN_DEFAULT;

    // Horse needle.
    public final static int NEEDLE_HORSE_DEFAULT = ++madItemIDs;
    public static @CfgNeedles
    int NEEDLE_HORSE = NEEDLE_HORSE_DEFAULT;

    // Mushroom Cow needle.
    public final static int NEEDLE_MUSHROOMCOW_DEFAULT = ++madItemIDs;
    public static @CfgNeedles
    int NEEDLE_MUSHROOMCOW = NEEDLE_MUSHROOMCOW_DEFAULT;

    // Ocelot needle.
    public final static int NEEDLE_OCELOT_DEFAULT = ++madItemIDs;
    public static @CfgNeedles
    int NEEDLE_OCELOT = NEEDLE_OCELOT_DEFAULT;

    // Pig needle.
    public final static int NEEDLE_PIG_DEFAULT = ++madItemIDs;
    public static @CfgNeedles
    int NEEDLE_PIG = NEEDLE_PIG_DEFAULT;

    // Sheep needle.
    public final static int NEEDLE_SHEEP_DEFAULT = ++madItemIDs;
    public static @CfgNeedles
    int NEEDLE_SHEEP = NEEDLE_SHEEP_DEFAULT;

    // Spider needle.
    public final static int NEEDLE_SPIDER_DEFAULT = ++madItemIDs;
    public static @CfgNeedles
    int NEEDLE_SPIDER = NEEDLE_SPIDER_DEFAULT;

    // Squid needle.
    public final static int NEEDLE_SQUID_DEFAULT = ++madItemIDs;
    public static @CfgNeedles
    int NEEDLE_SQUID = NEEDLE_SQUID_DEFAULT;

    // Villager needle.
    public final static int NEEDLE_VILLAGER_DEFAULT = ++madItemIDs;
    public static @CfgNeedles
    int NEEDLE_VILLAGER = NEEDLE_VILLAGER_DEFAULT;

    // Witch needle.
    public final static int NEEDLE_WITCH_DEFAULT = ++madItemIDs;
    public static @CfgNeedles
    int NEEDLE_WITCH = NEEDLE_WITCH_DEFAULT;

    // Wolf needle.
    public final static int NEEDLE_WOLF_DEFAULT = ++madItemIDs;
    public static @CfgNeedles
    int NEEDLE_WOLF = NEEDLE_WOLF_DEFAULT;

    // Zombie needle.
    public final static int NEEDLE_ZOMBIE_DEFAULT = ++madItemIDs;
    public static @CfgNeedles
    int NEEDLE_ZOMBIE = NEEDLE_ZOMBIE_DEFAULT;

    // Mutant needle.
    public final static int NEEDLE_MUTANT_DEFAULT = ++madItemIDs;
    public static @CfgNeedles
    int NEEDLE_MUTANT = NEEDLE_MUTANT_DEFAULT;

    // Dirty needle.
    public final static int NEEDLE_DIRTY_DEFAULT = ++madItemIDs;
    public static @CfgNeedles
    int NEEDLE_DIRTY = NEEDLE_DIRTY_DEFAULT;

    // -----------
    // DNA SAMPLES
    // -----------

    // Cave Spider DNA
    public final static int DNA_CAVESPIDER_DEFAULT = ++madItemIDs;
    public static @CfgDNA
    int DNA_CAVESPIDER = DNA_CAVESPIDER_DEFAULT;

    // Chicken DNA.
    public final static int DNA_CHICKEN_DEFAULT = ++madItemIDs;
    public static @CfgDNA
    int DNA_CHICKEN = DNA_CHICKEN_DEFAULT;

    // Cow DNA.
    public final static int DNA_COW_DEFAULT = ++madItemIDs;
    public static @CfgDNA
    int DNA_COW = DNA_COW_DEFAULT;

    // Creeper DNA.
    public final static int DNA_CREEPER_DEFAULT = ++madItemIDs;
    public static @CfgDNA
    int DNA_CREEPER = DNA_CREEPER_DEFAULT;

    // Bat DNA.
    public final static int DNA_BAT_DEFAULT = ++madItemIDs;
    public static @CfgDNA
    int DNA_BAT = DNA_BAT_DEFAULT;

    // Enderman DNA
    public final static int DNA_ENDERMAN_DEFAULT = ++madItemIDs;
    public static @CfgDNA
    int DNA_ENDERMAN = DNA_ENDERMAN_DEFAULT;

    // Ghast DNA
    public final static int DNA_GHAST_DEFAULT = ++madItemIDs;
    public static @CfgDNA
    int DNA_GHAST = DNA_GHAST_DEFAULT;

    // Horse DNA
    public final static int DNA_HORSE_DEFAULT = ++madItemIDs;
    public static @CfgDNA
    int DNA_HORSE = DNA_HORSE_DEFAULT;

    // Mushroom Cow DNA
    public final static int DNA_MUSHROOMCOW_DEFAULT = ++madItemIDs;
    public static @CfgDNA
    int DNA_MUSHROOMCOW = DNA_MUSHROOMCOW_DEFAULT;

    // Ocelot DNA
    public final static int DNA_OCELOT_DEFAULT = ++madItemIDs;
    public static @CfgDNA
    int DNA_OCELOT = DNA_OCELOT_DEFAULT;

    // Pig DNA.
    public final static int DNA_PIG_DEFAULT = ++madItemIDs;
    public static @CfgDNA
    int DNA_PIG = DNA_PIG_DEFAULT;

    // Pig Zombie DNA
    public final static int DNA_PIGZOMBIE_DEFAULT = ++madItemIDs;
    public static @CfgDNA
    int DNA_PIGZOMBIE = DNA_PIGZOMBIE_DEFAULT;

    // Sheep DNA
    public final static int DNA_SHEEP_DEFAULT = ++madItemIDs;
    public static @CfgDNA
    int DNA_SHEEP = DNA_SHEEP_DEFAULT;

    // Skeleton DNA
    public final static int DNA_SKELETON_DEFAULT = ++madItemIDs;
    public static @CfgDNA
    int DNA_SKELETON = DNA_SKELETON_DEFAULT;

    // Spider DNA.
    public final static int DNA_SPIDER_DEFAULT = ++madItemIDs;
    public static @CfgDNA
    int DNA_SPIDER = DNA_SPIDER_DEFAULT;

    // Slime DNA.
    public final static int DNA_SLIME_DEFAULT = ++madItemIDs;
    public static @CfgDNA
    int DNA_SLIME = DNA_SLIME_DEFAULT;

    // Squid DNA
    public final static int DNA_SQUID_DEFAULT = ++madItemIDs;
    public static @CfgDNA
    int DNA_SQUID = DNA_SQUID_DEFAULT;

    // Villager DNA.
    public final static int DNA_VILLAGER_DEFAULT = ++madItemIDs;
    public static @CfgDNA
    int DNA_VILLAGER = DNA_VILLAGER_DEFAULT;

    // Witch DNA
    public final static int DNA_WITCH_DEFAULT = ++madItemIDs;
    public static @CfgDNA
    int DNA_WITCH = DNA_WITCH_DEFAULT;

    // Wolf DNA
    public final static int DNA_WOLF_DEFAULT = ++madItemIDs;
    public static @CfgDNA
    int DNA_WOLF = DNA_WOLF_DEFAULT;

    // Zombie DNA.
    public final static int DNA_ZOMBIE_DEFAULT = ++madItemIDs;
    public static @CfgDNA
    int DNA_ZOMBIE = DNA_ZOMBIE_DEFAULT;

    // Mutant DNA (catch all for mutations and other various creatures).
    public final static int DNA_MUTANT_DEFAULT = ++madItemIDs;
    public static @CfgDNA
    int DNA_MUTANT = DNA_MUTANT_DEFAULT;

    // -----------------
    // GENOME DATA REELS
    // -----------------

    // Cave Spider Genome Data Reel
    public final static int GENOME_CAVESPIDER_DEFAULT = ++madItemIDs;
    public static @CfgGenomes
    int GENOME_CAVESPIDER = GENOME_CAVESPIDER_DEFAULT;

    // Chicken Genome Data Reel
    public final static int GENOME_CHICKEN_DEFAULT = ++madItemIDs;
    public static @CfgGenomes
    int GENOME_CHICKEN = GENOME_CHICKEN_DEFAULT;

    // Cow Genome Data Reel
    public final static int GENOME_COW_DEFAULT = ++madItemIDs;
    public static @CfgGenomes
    int GENOME_COW = GENOME_COW_DEFAULT;

    // Creeper Genome Data Reel
    public final static int GENOME_CREEPER_DEFAULT = ++madItemIDs;
    public static @CfgGenomes
    int GENOME_CREEPER = GENOME_CREEPER_DEFAULT;

    // Bat Genome Data Reel
    public final static int GENOME_BAT_DEFAULT = ++madItemIDs;
    public static @CfgGenomes
    int GENOME_BAT = GENOME_BAT_DEFAULT;

    // Enderman Genome Data Reel
    public final static int GENOME_ENDERMAN_DEFAULT = ++madItemIDs;
    public static @CfgGenomes
    int GENOME_ENDERMAN = GENOME_ENDERMAN_DEFAULT;

    // Ghast Genome Data Reel
    public final static int GENOME_GHAST_DEFAULT = ++madItemIDs;
    public static @CfgGenomes
    int GENOME_GHAST = GENOME_GHAST_DEFAULT;

    // Horse Genome Data Reel
    public final static int GENOME_HORSE_DEFAULT = ++madItemIDs;
    public static @CfgGenomes
    int GENOME_HORSE = GENOME_HORSE_DEFAULT;

    // Mushroom Cow Genome Data Reel
    public final static int GENOME_MUSHROOMCOW_DEFAULT = ++madItemIDs;
    public static @CfgGenomes
    int GENOME_MUSHROOMCOW = GENOME_MUSHROOMCOW_DEFAULT;

    // Ocelot Genome Data Reel
    public final static int GENOME_OCELOT_DEFAULT = ++madItemIDs;
    public static @CfgGenomes
    int GENOME_OCELOT = GENOME_OCELOT_DEFAULT;

    // Pig Genome Data Reel
    public final static int GENOME_PIG_DEFAULT = ++madItemIDs;
    public static @CfgGenomes
    int GENOME_PIG = GENOME_PIG_DEFAULT;

    // Pig Zombie Data Reel
    public final static int GENOME_PIGZOMBIE_DEFAULT = ++madItemIDs;
    public static @CfgGenomes
    int GENOME_PIGZOMBIE = GENOME_PIGZOMBIE_DEFAULT;

    // Sheep Genome Data Reel
    public final static int GENOME_SHEEP_DEFAULT = ++madItemIDs;
    public static @CfgGenomes
    int GENOME_SHEEP = GENOME_SHEEP_DEFAULT;

    // Skeleton Genome Data Reel
    public final static int GENOME_SKELETON_DEFAULT = ++madItemIDs;
    public static @CfgGenomes
    int GENOME_SKELETON = GENOME_SKELETON_DEFAULT;

    // Spider Genome Data Reel
    public final static int GENOME_SPIDER_DEFAULT = ++madItemIDs;
    public static @CfgGenomes
    int GENOME_SPIDER = GENOME_SPIDER_DEFAULT;

    // Slime Genome Data Reel
    public final static int GENOME_SLIME_DEFAULT = ++madItemIDs;
    public static @CfgGenomes
    int GENOME_SLIME = GENOME_SLIME_DEFAULT;

    // Squid Genome Data Reel
    public final static int GENOME_SQUID_DEFAULT = ++madItemIDs;
    public static @CfgGenomes
    int GENOME_SQUID = GENOME_SQUID_DEFAULT;

    // Villager Genome Data Reel
    public final static int GENOME_VILLAGER_DEFAULT = ++madItemIDs;
    public static @CfgGenomes
    int GENOME_VILLAGER = GENOME_VILLAGER_DEFAULT;

    // Witch Genome Data Reel
    public final static int GENOME_WITCH_DEFAULT = ++madItemIDs;
    public static @CfgGenomes
    int GENOME_WITCH = GENOME_WITCH_DEFAULT;

    // Wolf Genome Data Reel
    public final static int GENOME_WOLF_DEFAULT = ++madItemIDs;
    public static @CfgGenomes
    int GENOME_WOLF = GENOME_WOLF_DEFAULT;

    // Zombie Genome Data Reel
    public final static int GENOME_ZOMBIE_DEFAULT = ++madItemIDs;
    public static @CfgGenomes
    int GENOME_ZOMBIE = GENOME_ZOMBIE_DEFAULT;

    // -----------------------
    // TILE ENTITY DECLARATION
    // -----------------------

    // Defaults for power storage and input/output rates.
    public final static long MACHINE_CAPACITY_DEFAULT = 100000;
    public final static long MACHINE_TRANSFERRATE_DEFAULT = 200;
    public final static long MACHINE_CONSUMERATE_DEFAULT = 1;

    // DNA Extractor
    public final static int DNA_EXTRACTOR_DEFAULT = ++madBlockIDs;
    public static @CfgId(block = true)
    int DNA_EXTRACTOR = DNA_EXTRACTOR_DEFAULT;
    public static @CfgEnergy
    long DNAEXTRACTOR_CAPACTITY = MACHINE_CAPACITY_DEFAULT;
    public static @CfgEnergy
    long DNAEXTRACTOR_INPUT = MACHINE_TRANSFERRATE_DEFAULT;
    public static @CfgEnergy
    long DNAEXTRACTOR_CONSUME = MACHINE_CONSUMERATE_DEFAULT;

    // Needle Sanitizer
    public final static int SANTITIZER_DEFAULT = ++madBlockIDs;
    public static @CfgId(block = true)
    int SANTITIZER = SANTITIZER_DEFAULT;
    public static @CfgEnergy
    long SANTITIZER_CAPACTITY = MACHINE_CAPACITY_DEFAULT;
    public static @CfgEnergy
    long SANTITIZER_INPUT = MACHINE_TRANSFERRATE_DEFAULT;
    public static @CfgEnergy
    long SANTITIZER_CONSUME = MACHINE_CONSUMERATE_DEFAULT;

    // Computer Mainframe
    public final static int MAINFRAME_DEFAULT = ++madBlockIDs;
    public static @CfgId(block = true)
    int MAINFRAME = MAINFRAME_DEFAULT;
    public static @CfgEnergy
    long MAINFRAME_CAPACTITY = MACHINE_CAPACITY_DEFAULT;
    public static @CfgEnergy
    long MAINFRAME_INPUT = MACHINE_TRANSFERRATE_DEFAULT;
    public static @CfgEnergy
    long MAINFRAME_CONSUME = MACHINE_CONSUMERATE_DEFAULT;

    // Genetic Sequencer
    public final static int GENE_SEQUENCER_DEFAULT = ++madBlockIDs;
    public static @CfgId(block = true)
    int GENE_SEQUENCER = GENE_SEQUENCER_DEFAULT;
    public static @CfgEnergy
    long SEQUENCER_CAPACTITY = MACHINE_CAPACITY_DEFAULT;
    public static @CfgEnergy
    long SEQUENCER_INPUT = MACHINE_TRANSFERRATE_DEFAULT;
    public static @CfgEnergy
    long SEQUENCER_CONSUME = MACHINE_CONSUMERATE_DEFAULT;

    // Cryogenic Freezer
    public final static int CRYOFREEZER_DEFAULT = ++madBlockIDs;
    public static @CfgId(block = true)
    int CRYOFREEZER = CRYOFREEZER_DEFAULT;
    public static @CfgEnergy
    long CRYOFREEZER_CAPACTITY = MACHINE_CAPACITY_DEFAULT;
    public static @CfgEnergy
    long CRYOFREEZER_INPUT = MACHINE_TRANSFERRATE_DEFAULT;
    public static @CfgEnergy
    long CRYOFREEZER_CONSUME = MACHINE_CONSUMERATE_DEFAULT;

    // Genome Incubator
    public final static int INCUBATOR_DEFAULT = ++madBlockIDs;
    public static @CfgId(block = true)
    int INCUBATOR = INCUBATOR_DEFAULT;
    public static @CfgEnergy
    long INCUBATOR_CAPACTITY = MACHINE_CAPACITY_DEFAULT;
    public static @CfgEnergy
    long INCUBATOR_INPUT = MACHINE_TRANSFERRATE_DEFAULT;
    public static @CfgEnergy
    long INCUBATOR_CONSUME = MACHINE_CONSUMERATE_DEFAULT;

    // Meat Cube
    public final static int MEATCUBE_DEFAULT = ++madBlockIDs;
    public static @CfgId(block = true)
    int MEATCUBE = MEATCUBE_DEFAULT;

    // Cryogenic Tube
    public final static int CRYOTUBE_DEFAULT = ++madBlockIDs;
    public static @CfgId(block = true)
    int CRYOTUBE = CRYOTUBE_DEFAULT;
    public static @CfgEnergy
    long CRYOTUBE_CAPACTITY = 225120000000L;
    public static @CfgEnergy
    long CRYOTUBE_OUTPUT = 562800000L;
    public static @CfgEnergy
    long CRYOTUBE_PRODUCE = 1407000L;

    // Cryogenic Tube 'Ghost Block'
    public final static int CRYOTUBEGHOST_DEFAULT = ++madBlockIDs;
    public static @CfgId(block = true)
    int CRYOTUBEGHOST = CRYOTUBEGHOST_DEFAULT;

    // Thermosonic Bonder
    public final static int THERMOSONIC_DEFAULT = ++madBlockIDs;
    public static @CfgId(block = true)
    int THERMOSONIC = THERMOSONIC_DEFAULT;
    public static @CfgEnergy
    long THERMOSONIC_CAPACTITY = MACHINE_CAPACITY_DEFAULT;
    public static @CfgEnergy
    long THERMOSONIC_INPUT = MACHINE_TRANSFERRATE_DEFAULT;
    public static @CfgEnergy
    long THERMOSONIC_CONSUME = MACHINE_CONSUMERATE_DEFAULT;

    // Data Reel Duplicator
    public final static int DATADUPLICATOR_DEFAULT = ++madBlockIDs;
    public static @CfgId(block = true)
    int DATADUPLICATOR = DATADUPLICATOR_DEFAULT;
    public static @CfgEnergy
    long DATADUPLICATOR_CAPACTITY = MACHINE_CAPACITY_DEFAULT;
    public static @CfgEnergy
    long DATADUPLICATOR_INPUT = MACHINE_TRANSFERRATE_DEFAULT;
    public static @CfgEnergy
    long DATADUPLICATOR_CONSUME = MACHINE_CONSUMERATE_DEFAULT;

    // Soniclocator
    public final static int SONICLOCATOR_DEFAULT = ++madBlockIDs;
    public static @CfgId(block = true)
    int SONICLOCATOR = SONICLOCATOR_DEFAULT;
    public static @CfgEnergy
    long SONICLOCATOR_CAPACTITY = MACHINE_CAPACITY_DEFAULT;
    public static @CfgEnergy
    long SONICLOCATOR_INPUT = MACHINE_TRANSFERRATE_DEFAULT;
    public static @CfgEnergy
    long SONICLOCATOR_CONSUME = MACHINE_CONSUMERATE_DEFAULT;

    // Soniclocator 'Ghost Block'
    public final static int SONICLOCATORGHOST_DEFAULT = ++madBlockIDs;
    public static @CfgId(block = true)
    int SONICLOCATOREGHOST = SONICLOCATORGHOST_DEFAULT;

    // -----------------
    // FLUID DECLARATION
    // -----------------

    // Liquid DNA
    public final static int LIQUIDDNA_DEFAULT = ++madBlockIDs;
    public static @CfgId(block = true)
    int LIQUIDDNA = LIQUIDDNA_DEFAULT;

    // Liquid Mutant DNA
    public final static int LIQUIDDNA_MUTANT_DEFAULT = ++madBlockIDs;
    public static @CfgId(block = true)
    int LIQUIDDNA_MUTANT = LIQUIDDNA_MUTANT_DEFAULT;

    // -----------------
    // BLOCK DECLARATION
    // -----------------

    // Abomination Egg
    public final static int ABOMINATIONEGG_DEFAULT = ++madBlockIDs;
    public static @CfgId(block = true)
    int ABOMINATIONEGG = ABOMINATIONEGG_DEFAULT;

    // ----------------
    // FEATURE SWITCHES
    // ----------------

    // Determines if 'The Abomination' should lay eggs.
    public static @CfgBool
    boolean ABOMINATION_LAYSEGGS = true;

    // Determines if 'The Abomination' is allowed to teleport.
    public static @CfgBool
    boolean ABOMINATION_TELEPORTS = true;

    // Needles decay into dirty ones.
    public static @CfgBool
    boolean DECAY_BLOODWORK = true;

    // Needles decay at a rate of one health every 6000 ticks.
    public static @CfgInt
    int DECAY_DELAY_IN_SECONDS = 30;

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
                    annoComponents == null)
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
                        annoComponents == null)
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
                        annoComponents == null)
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
                        annoComponents == null)
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
                        annoCircuits == null)
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
                        annoComponents == null)
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
                        annoComponents == null)
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
                        annoComponents == null)
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
                        annoComponents == null)
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
                        annoComponents == null)
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
                        annoComponents == null)
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
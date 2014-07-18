package madscience;

import madscience.factory.mod.MadMod;

public class MadConfig
{
    // Mob ID's for genetically modified entity list.
    private static int madGMOMobIDs = 50;

    // ---------------------------
    // CUSTOM MONSTER PLACER ITEMS
    // ---------------------------

    // GeneticallyModifiedMonsterPlacer
    public static int GENETICALLYMODIFIED_MONSTERPLACER = MadMod.getNextItemID();

    // CombinedGenomeMonsterPlacer
    public static int COMBINEDGENOME_MONSTERPLACER = MadMod.getNextItemID();

    // CombinedMemoryMonsterPlacer
    public static int COMBINEDMEMORY_MONSTERPLACER = MadMod.getNextItemID();

    // ---------------------------------
    // GENETICALLY MODIFIED MOB META IDS
    // ---------------------------------
    // COMBINED GENOME META IDS
    // ------------------------

    // Default amount of time we will spend processing some genomes into new ones.
    private final static int COOKTIME_DEFAULT = 2600;

    // Werewolf [Wolf + Villager]
    private final static int GMO_WEREWOLF_METAID_DEFAULT = ++madGMOMobIDs;
    public static int GMO_WEREWOLF_METAID = GMO_WEREWOLF_METAID_DEFAULT;
    public static int GMO_WEREWOLF_COOKTIME = COOKTIME_DEFAULT;

    // Disgusting Meatcube [Slime + Pig,Cow,Chicken]
    private final static int GMO_MEATCUBE_METAID_DEFAULT = ++madGMOMobIDs;
    public static int GMO_MEATCUBE_METAID = GMO_MEATCUBE_METAID_DEFAULT;
    public static int GMO_MEATCUBE_COOKTIME = COOKTIME_DEFAULT;

    // Creeper Cow [Creeper + Cow]
    private final static int GMO_CREEPERCOW_METAID_DEFAULT = ++madGMOMobIDs;
    public static int GMO_CREEPERCOW_METAID = GMO_CREEPERCOW_METAID_DEFAULT;
    public static int GMO_CREEPERCOW_COOKTIME = COOKTIME_DEFAULT;

    // Enderslime [Enderman + Slime]
    private final static int GMO_ENDERSLIME_METAID_DEFAULT = ++madGMOMobIDs;
    public static int GMO_ENDERSLIME_METAID = GMO_ENDERSLIME_METAID_DEFAULT;
    public static int GMO_ENDERSLIME_COOKTIME = COOKTIME_DEFAULT;

    // --------------------------
    // Bart74(bart.74@hotmail.fr)
    // --------------------------

    // Wooly Cow [Cow + Sheep]
    private final static int GMO_WOOLYCOW_METAID_DEFAULT = ++madGMOMobIDs;
    public static int GMO_WOOLYCOW_METAID = GMO_WOOLYCOW_METAID_DEFAULT;
    public static int GMO_WOOLYCOW_COOKTIME = COOKTIME_DEFAULT;

    // ----------------------------------------
    // Deuce_Loosely(captainlunautic@yahoo.com)
    // ----------------------------------------

    // Shoggoth [Slime + Squid]
    private final static int GMO_SHOGGOTH_METAID_DEFAULT = ++madGMOMobIDs;
    public static int GMO_SHOGGOTH_METAID = GMO_SHOGGOTH_METAID_DEFAULT;
    public static int GMO_SHOGGOTH_COOKTIME = COOKTIME_DEFAULT;

    // ------------------------------------
    // monodemono(coolplanet3000@gmail.com)
    // ------------------------------------

    // The Abomination [Enderman + Spider]
    private final static int GMO_ABOMINATION_METAID_DEFAULT = ++madGMOMobIDs;
    public static int GMO_ABOMINATION_METAID = GMO_ABOMINATION_METAID_DEFAULT;
    public static int GMO_ABOMINATION_COOKTIME = COOKTIME_DEFAULT;

    // ---------------------------------
    // Pyrobrine(haskar.spore@gmail.com)
    // ---------------------------------

    // Wither Skeleton [Enderman + Skeleton]
    private final static int GMO_WITHERSKELETON_METAID_DEFAULT = ++madGMOMobIDs;
    public static int GMO_WITHERSKELETON_METAID = GMO_WITHERSKELETON_METAID_DEFAULT;
    public static int GMO_WITHERSKELETON_COOKTIME = COOKTIME_DEFAULT;

    // Villager Zombie [Villager + Zombie]
    private final static int GMO_VILLAGERZOMBIE_METAID_DEFAULT = ++madGMOMobIDs;
    public static int GMO_VILLAGERZOMBIE_METAID = GMO_VILLAGERZOMBIE_METAID_DEFAULT;
    public static int GMO_VILLAGERZOMBIE_COOKTIME = COOKTIME_DEFAULT;

    // Skeleton Horse [Horse + Skeleton]
    private final static int GMO_SKELETONHORSE_METAID_DEFAULT = ++madGMOMobIDs;
    public static int GMO_SKELETONHORSE_METAID = GMO_SKELETONHORSE_METAID_DEFAULT;
    public static int GMO_SKELETONHORSE_COOKTIME = COOKTIME_DEFAULT;

    // Zombie Horse [Zombie + Horse]
    private final static int GMO_ZOMBIEHORSE_METAID_DEFAULT = ++madGMOMobIDs;
    public static int GMO_ZOMBIEHORSE_METAID = GMO_ZOMBIEHORSE_METAID_DEFAULT;
    public static int GMO_ZOMBIEHORSE_COOKTIME = COOKTIME_DEFAULT;

    // ---------------------------------
    // TheTechnician(tallahlf@gmail.com)
    // ---------------------------------

    // Ender Squid [Enderman + Squid]
    private final static int GMO_ENDERSQUID_METAID_DEFAULT = ++madGMOMobIDs;
    public static int GMO_ENDERSQUID_METAID = GMO_ENDERSQUID_METAID_DEFAULT;
    public static int GMO_ENDERSQUID_COOKTIME = COOKTIME_DEFAULT;

    // --------
    // CIRCUITS
    // --------

    // Circuit Comparator
    public static int CIRCUIT_COMPARATOR = MadMod.getNextItemID();

    // Circuit Diamond
    public static int CIRCUIT_DIAMOND = MadMod.getNextItemID();

    // Circuit Emerald
    public static int CIRCUIT_EMERALD = MadMod.getNextItemID();

    // Circuit Ender Eye
    public static int CIRCUIT_ENDEREYE = MadMod.getNextItemID();

    // Circuit Ender Pearl
    public static int CIRCUIT_ENDERPEARL = MadMod.getNextItemID();

    // Circuit Glowstone
    public static int CIRCUIT_GLOWSTONE = MadMod.getNextItemID();

    // Circuit Redstone
    public static int CIRCUIT_REDSTONE = MadMod.getNextItemID();

    // Circuit Spider Eye
    public static int CIRCUIT_SPIDEREYE = MadMod.getNextItemID();

    // ----------
    // COMPONENTS
    // ----------

    // Component Case
    public static int COMPONENT_CASE = MadMod.getNextItemID();

    // Component Computer
    public static int COMPONENT_COMPUTER = MadMod.getNextItemID();

    // Component CPU
    public static int COMPONENT_CPU = MadMod.getNextItemID();

    // Component Fan
    public static int COMPONENT_FAN = MadMod.getNextItemID();

    // Component Fused Quartz
    public static int COMPONENT_FUSEDQUARTZ = MadMod.getNextItemID();

    // Component Magnetic Tape
    public static int COMPONENT_MAGNETICTAPE = MadMod.getNextItemID();

    // Component Power Supply
    public static int COMPONENT_POWERSUPPLY = MadMod.getNextItemID();

    // Component RAM
    public static int COMPONENT_RAM = MadMod.getNextItemID();

    // Component Screen
    public static int COMPONENT_SCREEN = MadMod.getNextItemID();

    // Component Silicon Wafer
    public static int COMPONENT_SILICONWAFER = MadMod.getNextItemID();

    // Component Transistor
    public static int COMPONENT_TRANSISTOR = MadMod.getNextItemID();

    // Component Thumper
    public static int COMPONENT_THUMPER = MadMod.getNextItemID();

    // Component Enderslime
    public static int COMPONENT_ENDERSLIME = MadMod.getNextItemID();

    // Component M41A Barrel
    public static int COMPONENT_PULSERIFLEBARREL = MadMod.getNextItemID();

    // Component M41A Bolt
    public static int COMPONENT_PULSERIFLEBOLT = MadMod.getNextItemID();

    // Component M41A Receiver
    public static int COMPONENT_PULSERIFLERECEIVER = MadMod.getNextItemID();

    // Component M41A Trigger
    public static int COMPONENT_PULSERIFLETRIGGER = MadMod.getNextItemID();

    // Component Bullet Casing
    public static int COMPONENT_PULSERIFLEBULLETCASING = MadMod.getNextItemID();

    // Component Grenade Casing
    public static int COMPONENT_PULSERIFLEGRENADECASING = MadMod.getNextItemID();

    // -----
    // ITEMS
    // -----

    // Liquid Bucket DNA
    public static int LIQUIDDNA_BUCKET = MadMod.getNextItemID();

    // Bucket of Liquid Mutant DNA
    public static int LIQUIDDNA_MUTANT_BUCKET = MadMod.getNextItemID();

    // Empty Data Reel
    public static int DATAREEL_EMPTY = MadMod.getNextItemID();

    // Lab Coat Body
    public static int LABCOAT_BODY = MadMod.getNextItemID();

    // Lab Coat Leggings
    public static int LABCOAT_LEGGINGS = MadMod.getNextItemID();

    // Lab Coat Goggles
    public static int LABCOAT_GOGGLES = MadMod.getNextItemID();

    // Warning Sign
    public static int WARNING_SIGN = MadMod.getNextItemID();

    // -------
    // NEEDLES
    // -------

    // NeedleEmptyItem to take DNA from mobs.
    public static int NEEDLE_EMPTY = MadMod.getNextItemID();

    // Cave Spider needle.
    public static int NEEDLE_CAVESPIDER = MadMod.getNextItemID();

    // Chicken needle.
    public static int NEEDLE_CHICKEN = MadMod.getNextItemID();

    // Cow needle.
    public static int NEEDLE_COW = MadMod.getNextItemID();

    // Creeper needle.
    public static int NEEDLE_CREEPER = MadMod.getNextItemID();

    // Bat needle.
    public static int NEEDLE_BAT = MadMod.getNextItemID();

    // Enderman needle.
    public static int NEEDLE_ENDERMAN = MadMod.getNextItemID();

    // Horse needle.
    public static int NEEDLE_HORSE = MadMod.getNextItemID();

    // Mushroom Cow needle.
    public static int NEEDLE_MUSHROOMCOW = MadMod.getNextItemID();

    // Ocelot needle.
    public static int NEEDLE_OCELOT = MadMod.getNextItemID();

    // Pig needle.
    public static int NEEDLE_PIG = MadMod.getNextItemID();

    // Sheep needle.
    public static int NEEDLE_SHEEP = MadMod.getNextItemID();

    // Spider needle.
    public static int NEEDLE_SPIDER = MadMod.getNextItemID();

    // Squid needle.
    public static int NEEDLE_SQUID = MadMod.getNextItemID();

    // Villager needle.
    public static int NEEDLE_VILLAGER = MadMod.getNextItemID();

    // Witch needle.
    public static int NEEDLE_WITCH = MadMod.getNextItemID();

    // Wolf needle.
    public static int NEEDLE_WOLF = MadMod.getNextItemID();

    // Zombie needle.
    public static int NEEDLE_ZOMBIE = MadMod.getNextItemID();

    // Mutant needle.
    public static int NEEDLE_MUTANT = MadMod.getNextItemID();

    // Dirty needle.
    public static int NEEDLE_DIRTY = MadMod.getNextItemID();

    // -----------
    // DNA SAMPLES
    // -----------

    // Cave Spider DNA
    public static int DNA_CAVESPIDER = MadMod.getNextItemID();

    // Chicken DNA.
    public static int DNA_CHICKEN = MadMod.getNextItemID();

    // Cow DNA.
    public static int DNA_COW = MadMod.getNextItemID();

    // Creeper DNA.
    public static int DNA_CREEPER = MadMod.getNextItemID();

    // Bat DNA.
    public static int DNA_BAT = MadMod.getNextItemID();

    // Enderman DNA
    public static int DNA_ENDERMAN = MadMod.getNextItemID();

    // Ghast DNA
    public static int DNA_GHAST = MadMod.getNextItemID();

    // Horse DNA
    public static int DNA_HORSE = MadMod.getNextItemID();

    // Mushroom Cow DNA
    public static int DNA_MUSHROOMCOW = MadMod.getNextItemID();

    // Ocelot DNA
    public static int DNA_OCELOT = MadMod.getNextItemID();

    // Pig DNA.
    public static int DNA_PIG = MadMod.getNextItemID();

    // Sheep DNA
    public static int DNA_SHEEP = MadMod.getNextItemID();

    // Skeleton DNA
    public static int DNA_SKELETON = MadMod.getNextItemID();

    // Spider DNA.
    public static int DNA_SPIDER = MadMod.getNextItemID();

    // Slime DNA.
    public static int DNA_SLIME = MadMod.getNextItemID();

    // Squid DNA
    public static int DNA_SQUID = MadMod.getNextItemID();

    // Villager DNA.
    public static int DNA_VILLAGER = MadMod.getNextItemID();

    // Witch DNA
    public static int DNA_WITCH = MadMod.getNextItemID();

    // Wolf DNA
    public static int DNA_WOLF = MadMod.getNextItemID();

    // Zombie DNA.
    public static int DNA_ZOMBIE = MadMod.getNextItemID();

    // -----------------
    // GENOME DATA REELS
    // -----------------

    // Cave Spider Genome Data Reel
    public static int GENOME_CAVESPIDER = MadMod.getNextItemID();

    // Chicken Genome Data Reel
    public static int GENOME_CHICKEN = MadMod.getNextItemID();

    // Cow Genome Data Reel
    public static int GENOME_COW = MadMod.getNextItemID();

    // Creeper Genome Data Reel
    public static int GENOME_CREEPER = MadMod.getNextItemID();

    // Bat Genome Data Reel
    public static int GENOME_BAT = MadMod.getNextItemID();

    // Enderman Genome Data Reel
    public static int GENOME_ENDERMAN = MadMod.getNextItemID();

    // Ghast Genome Data Reel
    public static int GENOME_GHAST = MadMod.getNextItemID();

    // Horse Genome Data Reel
    public static int GENOME_HORSE = MadMod.getNextItemID();

    // Mushroom Cow Genome Data Reel
    public static int GENOME_MUSHROOMCOW = MadMod.getNextItemID();

    // Ocelot Genome Data Reel
    public static int GENOME_OCELOT = MadMod.getNextItemID();

    // Pig Genome Data Reel
    public static int GENOME_PIG = MadMod.getNextItemID();

    // Pig Zombie Data Reel
    public static int GENOME_PIGZOMBIE = MadMod.getNextItemID();

    // Sheep Genome Data Reel
    public static int GENOME_SHEEP = MadMod.getNextItemID();

    // Skeleton Genome Data Reel
    public static int GENOME_SKELETON = MadMod.getNextItemID();

    // Spider Genome Data Reel
    public static int GENOME_SPIDER = MadMod.getNextItemID();

    // Slime Genome Data Reel
    public static int GENOME_SLIME = MadMod.getNextItemID();

    // Squid Genome Data Reel
    public static int GENOME_SQUID = MadMod.getNextItemID();

    // Villager Genome Data Reel
    public static int GENOME_VILLAGER = MadMod.getNextItemID();

    // Witch Genome Data Reel
    public static int GENOME_WITCH = MadMod.getNextItemID();

    // Wolf Genome Data Reel
    public static int GENOME_WOLF = MadMod.getNextItemID();

    // Zombie Genome Data Reel
    public static int GENOME_ZOMBIE = MadMod.getNextItemID();

    // -----------------------
    // TILE ENTITY DECLARATION
    // -----------------------

    // Defaults for power storage and input/output rates.
    private final static long MACHINE_CAPACITY_DEFAULT = 100000;
    private final static long MACHINE_TRANSFERRATE_DEFAULT = 200;
    private final static long MACHINE_CONSUMERATE_DEFAULT = 1;

    // Computer Mainframe
    public static int MAINFRAME = MadMod.getNextBlockID();
    public static long MAINFRAME_CAPACTITY = MACHINE_CAPACITY_DEFAULT;
    public static long MAINFRAME_INPUT = MACHINE_TRANSFERRATE_DEFAULT;
    public static long MAINFRAME_CONSUME = MACHINE_CONSUMERATE_DEFAULT;
    public static String MAINFRAME_HELP = "http://madsciencemod.com/minecraft-item/computer-mainframe/";

    // Genetic Sequencer
    public static int GENE_SEQUENCER = MadMod.getNextBlockID();
    public static long SEQUENCER_CAPACTITY = MACHINE_CAPACITY_DEFAULT;
    public static long SEQUENCER_INPUT = MACHINE_TRANSFERRATE_DEFAULT;
    public static long SEQUENCER_CONSUME = MACHINE_CONSUMERATE_DEFAULT;
    public static String SEQUENCER_HELP = "http://madsciencemod.com/minecraft-item/genetic-sequencer/";

    // Cryogenic Freezer
    public static int CRYOFREEZER = MadMod.getNextBlockID();
    public static long CRYOFREEZER_CAPACTITY = MACHINE_CAPACITY_DEFAULT;
    public static long CRYOFREEZER_INPUT = MACHINE_TRANSFERRATE_DEFAULT;
    public static long CRYOFREEZER_CONSUME = MACHINE_CONSUMERATE_DEFAULT;
    public static String CRYOFREEZER_HELP = "http://madsciencemod.com/minecraft-item/cryogenic-freezer/";

    // Genome Incubator
    public static int INCUBATOR = MadMod.getNextBlockID();
    public static long INCUBATOR_CAPACTITY = MACHINE_CAPACITY_DEFAULT;
    public static long INCUBATOR_INPUT = MACHINE_TRANSFERRATE_DEFAULT;
    public static long INCUBATOR_CONSUME = MACHINE_CONSUMERATE_DEFAULT;
    public static String INCUBATOR_HELP = "http://madsciencemod.com/minecraft-item/genome-incubator/";

    // Meat Cube
    public static int MEATCUBE = MadMod.getNextBlockID();
    public static String MEATCUBE_HELP = "http://madsciencemod.com/minecraft-item/meat-cube/";

    // Cryogenic Tube
    public static int CRYOTUBE = MadMod.getNextBlockID();
    static int CRYOTUBEGHOST = MadMod.getNextBlockID();
    public static long CRYOTUBE_CAPACTITY = 225120000000L;
    public static long CRYOTUBE_OUTPUT = 562800000L;
    public static long CRYOTUBE_PRODUCE = 1407000L;
    public static String CRYOTUBE_HELP = "http://madsciencemod.com/minecraft-item/cryogenic-tube/";

    // Thermosonic Bonder
    public static int THERMOSONIC = MadMod.getNextBlockID();
    public static long THERMOSONIC_CAPACTITY = MACHINE_CAPACITY_DEFAULT;
    public static long THERMOSONIC_INPUT = MACHINE_TRANSFERRATE_DEFAULT;
    public static long THERMOSONIC_CONSUME = MACHINE_CONSUMERATE_DEFAULT;
    public static String THERMOSONIC_HELP = "http://madsciencemod.com/minecraft-item/thermosonic-bonder/";

    // Soniclocator
    public static int SONICLOCATOR = MadMod.getNextBlockID();
    static int SONICLOCATOREGHOST = MadMod.getNextBlockID();
    public static long SONICLOCATOR_CAPACTITY = MACHINE_CAPACITY_DEFAULT;
    public static long SONICLOCATOR_INPUT = MACHINE_TRANSFERRATE_DEFAULT;
    public static long SONICLOCATOR_CONSUME = MACHINE_CONSUMERATE_DEFAULT;
    public static String SONICLOCATOR_HELP = "http://madsciencemod.com/minecraft-item/soniclocator/";

    // Clay Furnace
    public static int CLAYFURNACE = MadMod.getNextBlockID();
    public static String CLAYFURNACE_HELP = "http://madsciencemod.com/minecraft-item/clay-furnace/";

    // VOX Box
    public static int VOXBOX = MadMod.getNextBlockID();
    public static long VOXBOX_CAPACTITY = MACHINE_CAPACITY_DEFAULT / 4;
    public static long VOXBOX_INPUT = MACHINE_TRANSFERRATE_DEFAULT;
    public static long VOXBOX_CONSUME = 128;
    public static String VOXBOX_HELP = "http://madsciencemod.com/minecraft-item/voxbox/";

    // Magazine Loader
    public static int MAGLOADER = MadMod.getNextBlockID();
    static int MAGLOADERGHOST = MadMod.getNextBlockID();
    public static long MAGLOADER_CAPACTITY = MACHINE_CAPACITY_DEFAULT / 4;
    public static long MAGLOADER_INPUT = MACHINE_TRANSFERRATE_DEFAULT;
    public static long MAGLOADER_CONSUME = MACHINE_CONSUMERATE_DEFAULT;
    public static String MAGLOADER_HELP = "http://madsciencemod.com/minecraft-item/magazine-loader/";

    // CnC Machine
    public static int CNCMACHINE = MadMod.getNextBlockID();
    static int CNCMACHINEGHOST = MadMod.getNextBlockID();
    public static long CNCMACHINE_CAPACTITY = MACHINE_CAPACITY_DEFAULT / 4;
    public static long CNCMACHINE_INPUT = MACHINE_TRANSFERRATE_DEFAULT;
    public static long CNCMACHINE_CONSUME = MACHINE_CONSUMERATE_DEFAULT;
    public static String CNCMACHINE_HELP = "http://madsciencemod.com/minecraft-item/cnc-machine/";

    // -----------------
    // FLUID DECLARATION
    // -----------------

    // Liquid DNA
    public static int LIQUIDDNA = MadMod.getNextBlockID();

    // Liquid Mutant DNA
    public static int LIQUIDDNA_MUTANT = MadMod.getNextBlockID();

    // -----------------
    // BLOCK DECLARATION
    // -----------------

    // Abomination Egg
    public static int ABOMINATIONEGG = MadMod.getNextBlockID();

    // Enderslime Block
    public static int ENDERSLIMEBLOCK = MadMod.getNextBlockID();

    // -------
    // WEAPONS
    // -------

    // M41A Pulse Rifle
    public static int WEAPON_PULSERIFLE = MadMod.getNextItemID();

    // Pulse Rifle Bullet
    public static int WEAPON_PULSERIFLE_BULLETITEM = MadMod.getNextItemID();

    // Pulse Rifle Grenade
    public static int WEAPON_PULSERIFLE_GRENADEITEM = MadMod.getNextItemID();

    // Pulse Rifle Magazine
    public static int WEAPON_PULSERIFLE_MAGAZINEITEM = MadMod.getNextItemID();

    // ----------------
    // FEATURE SWITCHES
    // ----------------

    // Determines if 'The Abomination' should lay eggs.
    public static boolean ABOMINATION_LAYSEGGS = true;

    // Determines if 'The Abomination' is allowed to teleport.
    public static boolean ABOMINATION_TELEPORTS = true;

    // Needles decay into dirty ones.
    public static boolean DECAY_BLOODWORK = true;

    // Needles decay at a rate of one health every 6000 ticks.
    public static int DECAY_DELAY_IN_SECONDS = 30;

    // Determines how many seconds clay furnace will cook before finishing
    public static int CLAYFURNACE_COOKTIME_IN_SECONDS = 420;

    // Distance which we will send packet updates about machines to players.
    public static int PACKETSEND_RADIUS = 25;

    // ID that will determine block to be used to 'unlock' thermosonic bonder and thus every other item in the mod.
    public static int THERMOSONICBONDER_FINALSACRIFICE = 138;

    // Determines if M41A Pulse Rifle bullets will damage the world at all with random chance.
    public static boolean PULSERIFLE_BULLETS_DAMAGEWORLD = true;

    // ----------------
    // UPDATES SWITCHES
    // ----------------

    // Determines if we should inform the user about updates and nightly builds.
    public static boolean UPDATE_CHECKER = true;
}
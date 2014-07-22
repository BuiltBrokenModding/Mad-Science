package madscience;

import madscience.factory.mod.MadMod;
import madscience.items.dna.DNABat;
import madscience.items.dna.DNACaveSpider;
import madscience.items.dna.DNAChicken;
import madscience.items.dna.DNACow;
import madscience.items.dna.DNACreeper;
import madscience.items.dna.DNAEnderman;
import madscience.items.dna.DNAGhast;
import madscience.items.dna.DNAHorse;
import madscience.items.dna.DNAMushroomCow;
import madscience.items.dna.DNAOcelot;
import madscience.items.dna.DNAPig;
import madscience.items.dna.DNASheep;
import madscience.items.dna.DNASkeleton;
import madscience.items.dna.DNASlime;
import madscience.items.dna.DNASpider;
import madscience.items.dna.DNASquid;
import madscience.items.dna.DNAVillager;
import madscience.items.dna.DNAWitch;
import madscience.items.dna.DNAWolf;
import madscience.items.dna.DNAZombie;
import net.minecraft.item.Item;
import cpw.mods.fml.common.registry.GameRegistry;

public class MadDNA
{
    // -----------
    // DNA SAMPLES
    // -----------

    // DNA Sample of Cave Spider
    static DNACaveSpider DNA_CAVESPIDER;
    private static final String DNA_CAVESPIDER_INTERNALNAME = "dnaCaveSpider";

    // DNA Sample of Chicken.
    public static DNAChicken DNA_CHICKEN;
    private static final String DNA_CHICKEN_INTERNALNAME = "dnaChicken";

    // DNA Sample of Cow.
    public static DNACow DNA_COW;
    private static final String DNA_COW_INTERNALNAME = "dnaCow";

    // DNA Sample of Creeper.
    public static DNACreeper DNA_CREEPER;
    private static final String DNA_CREEPER_INTERNALNAME = "dnaCreeper";

    // DNA Sample of Enderman.
    static DNAEnderman DNA_ENDERMAN;
    private static final String DNA_ENDERMAN_INTERNALNAME = "dnaEnderman";

    // DNA Sample of Ghast.
    static DNAGhast DNA_GHAST;
    private static final String DNA_GHAST_INTERNALNAME = "dnaGhast";

    // DNA Sample of Horse.
    static DNAHorse DNA_HORSE;
    private static final String DNA_HORSE_INTERNALNAME = "dnaHorse";

    // DNA Sample of Mushroom Cow.
    static DNAMushroomCow DNA_MUSHROOMCOW;
    private static final String DNA_MUSHROOMCOW_INTERNALNAME = "dnaMushroomCow";

    // DNA Sample of Ocelot.
    static DNAOcelot DNA_OCELOT;
    private static final String DNA_OCELOT_INTERNALNAME = "dnaOcelot";

    // DNA Sample of Pig.
    public static DNAPig DNA_PIG;
    private static final String DNA_PIG_INTERNALNAME = "dnaPig";

    // DNA Sample of Sheep.
    static DNASheep DNA_SHEEP;
    private static final String DNA_SHEEP_INTERNALNAME = "dnaSheep";

    // DNA Sample of Skeleton.
    static DNASkeleton DNA_SKELETON;
    private static final String DNA_SKELETON_INTERNALNAME = "dnaSkeleton";

    // DNA Sample of Spider.
    public static DNASpider DNA_SPIDER;
    private static final String DNA_SPIDER_INTERNALNAME = "dnaSpider";

    // DNA Sample of Squid.
    static DNASquid DNA_SQUID;
    private static final String DNA_SQUID_INTERNALNAME = "dnaSquid";

    // DNA Sample of Villager.
    public static DNAVillager DNA_VILLAGER;
    private static final String DNA_VILLAGER_INTERNALNAME = "dnaVillager";

    // DNA Sample of Witch.
    static DNAWitch DNA_WITCH;
    private static final String DNA_WITCH_INTERNALNAME = "dnaWitch";

    // DNA Sample of Wolf.
    static DNAWolf DNA_WOLF;
    private static final String DNA_WOLF_INTERNALNAME = "dnaWolf";

    // DNA Sample of Zombie.
    static DNAZombie DNA_ZOMBIE;
    private static final String DNA_ZOMBIE_INTERNALNAME = "dnaZombie";

    // DNA Sample of Bat.
    static DNABat DNA_BAT;
    private static String DNA_BAT_INTERNALNAME = "dnaBat";

    // DNA Sample of Slime.
    static Item DNA_SLIME;
    private static String DNA_SLIME_INTERNALNAME = "dnaSlime";

    // -----------------------
    // DNA SAMPLE REGISTRY ADD
    // -----------------------

    static void createBatDNA(int itemID)
    {
        // Adds pure sample of Bat DNA.
        MadMod.log().info("-DNA Bat");
        DNA_BAT = (DNABat) new DNABat(itemID, 4996656, 986895).setUnlocalizedName(DNA_BAT_INTERNALNAME);
        GameRegistry.registerItem(DNA_BAT, DNA_BAT_INTERNALNAME);
    }

    static void createCaveSpiderDNA(int itemID)
    {
        MadMod.log().info("-DNA Cave Spider");
        DNA_CAVESPIDER = (DNACaveSpider) new DNACaveSpider(itemID, 803406, 11013646).setUnlocalizedName(DNA_CAVESPIDER_INTERNALNAME);
        GameRegistry.registerItem(DNA_CAVESPIDER, DNA_CAVESPIDER_INTERNALNAME);
    }

    static void createChickenDNA(int itemID)
    {
        // Adds pure sample of Chicken DNA.
        MadMod.log().info("-DNA Chicken");
        DNA_CHICKEN = (DNAChicken) new DNAChicken(itemID, 10592673, 16711680).setUnlocalizedName(DNA_CHICKEN_INTERNALNAME);
        GameRegistry.registerItem(DNA_CHICKEN, DNA_CHICKEN_INTERNALNAME);
    }

    static void createCowDNA(int itemID)
    {
        // Adds pure sample of Cow DNA.
        MadMod.log().info("-DNA Cow");
        DNA_COW = (DNACow) new DNACow(itemID, 4470310, 10592673).setUnlocalizedName(DNA_COW_INTERNALNAME);
        GameRegistry.registerItem(DNA_COW, DNA_COW_INTERNALNAME);
    }

    static void createCreeperDNA(int itemID)
    {
        // Adds pure sample of Creeper DNA.
        MadMod.log().info("-DNA Creeper");
        DNA_CREEPER = (DNACreeper) new DNACreeper(itemID, 894731, 0).setUnlocalizedName(DNA_CREEPER_INTERNALNAME);
        GameRegistry.registerItem(DNA_CREEPER, DNA_CREEPER_INTERNALNAME);
    }

    static void createEndermanDNA(int itemID)
    {
        MadMod.log().info("-DNA Enderman");
        DNA_ENDERMAN = (DNAEnderman) new DNAEnderman(itemID, 1447446, 0).setUnlocalizedName(DNA_ENDERMAN_INTERNALNAME);
        GameRegistry.registerItem(DNA_ENDERMAN, DNA_ENDERMAN_INTERNALNAME);
    }

    static void createGhastDNA(int itemID)
    {
        MadMod.log().info("-DNA Ghast");
        DNA_GHAST = (DNAGhast) new DNAGhast(itemID, 16382457, 12369084).setUnlocalizedName(DNA_GHAST_INTERNALNAME);
        GameRegistry.registerItem(DNA_GHAST, DNA_GHAST_INTERNALNAME);
    }

    static void createHorseDNA(int itemID)
    {
        MadMod.log().info("-DNA Horse");
        DNA_HORSE = (DNAHorse) new DNAHorse(itemID, 12623485, 15656192).setUnlocalizedName(DNA_HORSE_INTERNALNAME);
        GameRegistry.registerItem(DNA_HORSE, DNA_HORSE_INTERNALNAME);
    }

    static void createMushroomCowDNA(int itemID)
    {
        MadMod.log().info("-DNA Mushroom Cow");
        DNA_MUSHROOMCOW = (DNAMushroomCow) new DNAMushroomCow(itemID, 10489616, 12040119).setUnlocalizedName(DNA_MUSHROOMCOW_INTERNALNAME);
        GameRegistry.registerItem(DNA_MUSHROOMCOW, DNA_MUSHROOMCOW_INTERNALNAME);
    }

    static void createOcelotDNA(int itemID)
    {
        MadMod.log().info("-DNA Ocelot");
        DNA_OCELOT = (DNAOcelot) new DNAOcelot(itemID, 15720061, 5653556).setUnlocalizedName(DNA_OCELOT_INTERNALNAME);
        GameRegistry.registerItem(DNA_OCELOT, DNA_OCELOT_INTERNALNAME);
    }

    static void createPigDNA(int itemID)
    {
        // Adds pure sample of Pig DNA.
        MadMod.log().info("-DNA Pig");
        DNA_PIG = (DNAPig) new DNAPig(itemID, 15771042, 14377823).setUnlocalizedName(DNA_PIG_INTERNALNAME);
        GameRegistry.registerItem(DNA_PIG, DNA_PIG_INTERNALNAME);
    }

    static void createSheepDNA(int itemID)
    {
        MadMod.log().info("-DNA Sheep");
        DNA_SHEEP = (DNASheep) new DNASheep(itemID, 15198183, 16758197).setUnlocalizedName(DNA_SHEEP_INTERNALNAME);
        GameRegistry.registerItem(DNA_SHEEP, DNA_SHEEP_INTERNALNAME);
    }

    static void createSkeletonDNA(int itemID)
    {
        MadMod.log().info("-DNA Skeleton");
        DNA_SKELETON = (DNASkeleton) new DNASkeleton(itemID, 12698049, 4802889).setUnlocalizedName(DNA_SKELETON_INTERNALNAME);
        GameRegistry.registerItem(DNA_SKELETON, DNA_SKELETON_INTERNALNAME);
    }

    static void createSlimeDNA(int itemID)
    {
        // Adds pure sample of Slime DNA.
        MadMod.log().info("-DNA Slime");
        DNA_SLIME = new DNASlime(itemID, 5349438, 8306542).setUnlocalizedName(DNA_SLIME_INTERNALNAME);
        GameRegistry.registerItem(DNA_SLIME, DNA_SLIME_INTERNALNAME);
    }

    static void createSpiderDNA(int itemID)
    {
        // Adds pure sample of Spider DNA.
        MadMod.log().info("-DNA Spider");
        DNA_SPIDER = (DNASpider) new DNASpider(itemID, 3419431, 11013646).setUnlocalizedName(DNA_SPIDER_INTERNALNAME);
        GameRegistry.registerItem(DNA_SPIDER, DNA_SPIDER_INTERNALNAME);
    }

    static void createSquidDNA(int itemID)
    {
        MadMod.log().info("-DNA Squid");
        DNA_SQUID = (DNASquid) new DNASquid(itemID, 2243405, 7375001).setUnlocalizedName(DNA_SQUID_INTERNALNAME);
        GameRegistry.registerItem(DNA_SQUID, DNA_SQUID_INTERNALNAME);
    }

    static void createVillagerDNA(int itemID)
    {
        // Adds pure sample of Villager DNA.
        MadMod.log().info("-DNA Villager");
        DNA_VILLAGER = (DNAVillager) new DNAVillager(itemID, 5651507, 12422002).setUnlocalizedName(DNA_VILLAGER_INTERNALNAME);
        GameRegistry.registerItem(DNA_VILLAGER, DNA_VILLAGER_INTERNALNAME);
    }

    static void createWitchDNA(int itemID)
    {
        MadMod.log().info("-DNA Witch");
        DNA_WITCH = (DNAWitch) new DNAWitch(itemID, 3407872, 5349438).setUnlocalizedName(DNA_WITCH_INTERNALNAME);
        GameRegistry.registerItem(DNA_WITCH, DNA_WITCH_INTERNALNAME);
    }

    static void createWolfDNA(int itemID)
    {
        MadMod.log().info("-DNA Wolf");
        DNA_WOLF = (DNAWolf) new DNAWolf(itemID, 14144467, 13545366).setUnlocalizedName(DNA_WOLF_INTERNALNAME);
        GameRegistry.registerItem(DNA_WOLF, DNA_WOLF_INTERNALNAME);
    }

    static void createZombieDNA(int itemID)
    {
        // DNA Extractor
        // Adds pure sample of Zombie DNA.
        MadMod.log().info("-DNA Zombie");
        DNA_ZOMBIE = (DNAZombie) new DNAZombie(itemID, 44975, 7969893).setUnlocalizedName(DNA_ZOMBIE_INTERNALNAME);
        GameRegistry.registerItem(DNA_ZOMBIE, DNA_ZOMBIE_INTERNALNAME);
    }
}

package madscience;

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
import madscience.tileentities.dnaextractor.DNAExtractorRecipes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class MadDNA
{
    // -----------
    // DNA SAMPLES
    // -----------

    // DNA Sample of Cave Spider
    public static DNACaveSpider DNA_CAVESPIDER;
    public static final String DNA_CAVESPIDER_DISPLAYNAME = "Sample of Cave Spider DNA";
    public static final String DNA_CAVESPIDER_INTERNALNAME = "dnaCaveSpider";

    // DNA Sample of Chicken.
    public static DNAChicken DNA_CHICKEN;
    public static final String DNA_CHICKEN_DISPLAYNAME = "Sample of Chicken DNA";
    public static final String DNA_CHICKEN_INTERNALNAME = "dnaChicken";

    // DNA Sample of Cow.
    public static DNACow DNA_COW;
    public static final String DNA_COW_DISPLAYNAME = "Sample of Cow DNA";
    public static final String DNA_COW_INTERNALNAME = "dnaCow";

    // DNA Sample of Creeper.
    public static DNACreeper DNA_CREEPER;
    public static final String DNA_CREEPER_DISPLAYNAME = "Sample of Creeper DNA";
    public static final String DNA_CREEPER_INTERNALNAME = "dnaCreeper";

    // DNA Sample of Enderman.
    public static DNAEnderman DNA_ENDERMAN;
    public static final String DNA_ENDERMAN_DISPLAYNAME = "Sample of Enderman DNA";
    public static final String DNA_ENDERMAN_INTERNALNAME = "dnaEnderman";

    // DNA Sample of Ghast.
    public static DNAGhast DNA_GHAST;
    public static final String DNA_GHAST_DISPLAYNAME = "Sample of Ghast DNA";
    public static final String DNA_GHAST_INTERNALNAME = "dnaGhast";

    // DNA Sample of Horse.
    public static DNAHorse DNA_HORSE;
    public static final String DNA_HORSE_DISPLAYNAME = "Sample of Horse DNA";
    public static final String DNA_HORSE_INTERNALNAME = "dnaHorse";

    // DNA Sample of Mushroom Cow.
    public static DNAMushroomCow DNA_MUSHROOMCOW;
    public static final String DNA_MUSHROOMCOW_DISPLAYNAME = "Sample of Mushrom Cow DNA";
    public static final String DNA_MUSHROOMCOW_INTERNALNAME = "dnaMushroomCow";

    // DNA Sample of Ocelot.
    public static DNAOcelot DNA_OCELOT;
    public static final String DNA_OCELOT_DISPLAYNAME = "Sample of Ocelot DNA";
    public static final String DNA_OCELOT_INTERNALNAME = "dnaOcelot";

    // DNA Sample of Pig.
    public static DNAPig DNA_PIG;
    public static final String DNA_PIG_DISPLAYNAME = "Sample of Pig DNA";
    public static final String DNA_PIG_INTERNALNAME = "dnaPig";

    // DNA Sample of Sheep.
    public static DNASheep DNA_SHEEP;
    public static final String DNA_SHEEP_DISPLAYNAME = "Sample of Sheep DNA";
    public static final String DNA_SHEEP_INTERNALNAME = "dnaSheep";

    // DNA Sample of Skeleton.
    public static DNASkeleton DNA_SKELETON;
    public static final String DNA_SKELETON_DISPLAYNAME = "Sample of Skeleton DNA";
    public static final String DNA_SKELETON_INTERNALNAME = "dnaSkeleton";

    // DNA Sample of Spider.
    public static DNASpider DNA_SPIDER;
    public static final String DNA_SPIDER_DISPLAYNAME = "Sample of Spider DNA";
    public static final String DNA_SPIDER_INTERNALNAME = "dnaSpider";

    // DNA Sample of Squid.
    public static DNASquid DNA_SQUID;
    public static final String DNA_SQUID_DISPLAYNAME = "Sample of Squid DNA";
    public static final String DNA_SQUID_INTERNALNAME = "dnaSquid";

    // DNA Sample of Villager.
    public static DNAVillager DNA_VILLAGER;
    public static final String DNA_VILLAGER_DISPLAYNAME = "Sample of Villager DNA";
    public static final String DNA_VILLAGER_INTERNALNAME = "dnaVillager";

    // DNA Sample of Witch.
    public static DNAWitch DNA_WITCH;
    public static final String DNA_WITCH_DISPLAYNAME = "Sample of Witch DNA";
    public static final String DNA_WITCH_INTERNALNAME = "dnaWitch";

    // DNA Sample of Wolf.
    public static DNAWolf DNA_WOLF;
    public static final String DNA_WOLF_DISPLAYNAME = "Sample of Wolf DNA";
    public static final String DNA_WOLF_INTERNALNAME = "dnaWolf";

    // DNA Sample of Zombie.
    public static DNAZombie DNA_ZOMBIE;
    public static final String DNA_ZOMBIE_DISPLAYNAME = "Sample of Zombie DNA";
    public static final String DNA_ZOMBIE_INTERNALNAME = "dnaZombie";

    // DNA Sample of Bat.
    public static DNABat DNA_BAT;
    public static String DNA_BAT_DISPLAYNAME = "Sample of Bat DNA";
    public static String DNA_BAT_INTERNALNAME = "dnaBat";

    // DNA Sample of Slime.
    public static Item DNA_SLIME;
    public static String DNA_SLIME_DISPLAYNAME = "Sample of Slime DNA";
    public static String DNA_SLIME_INTERNALNAME = "dnaSlime";

    // -----------------------
    // DNA SAMPLE REGISTRY ADD
    // -----------------------

    public static void createBatDNA(int itemID)
    {
        // Adds pure sample of Bat DNA.
        DNA_BAT = (DNABat) new DNABat(itemID, 4996656, 986895).setUnlocalizedName(DNA_BAT_INTERNALNAME);
        GameRegistry.registerItem(DNA_BAT, DNA_BAT_INTERNALNAME);
        LanguageRegistry.addName(DNA_BAT, DNA_BAT_DISPLAYNAME);

        // DNA Extractor
        DNAExtractorRecipes.addSmelting(MadNeedles.NEEDLE_BAT.itemID, new ItemStack(DNA_BAT), 0.50F);
    }

    public static void createCaveSpiderDNA(int itemID)
    {
        DNA_CAVESPIDER = (DNACaveSpider) new DNACaveSpider(itemID, 803406, 11013646).setUnlocalizedName(DNA_CAVESPIDER_INTERNALNAME);
        GameRegistry.registerItem(DNA_CAVESPIDER, DNA_CAVESPIDER_INTERNALNAME);
        LanguageRegistry.addName(DNA_CAVESPIDER, DNA_CAVESPIDER_DISPLAYNAME);

        // DNA Extractor
        DNAExtractorRecipes.addSmelting(MadNeedles.NEEDLE_CAVESPIDER.itemID, new ItemStack(DNA_CAVESPIDER), 0.35F);
    }

    public static void createChickenDNA(int itemID)
    {
        // Adds pure sample of Chicken DNA.
        DNA_CHICKEN = (DNAChicken) new DNAChicken(itemID, 10592673, 16711680).setUnlocalizedName(DNA_CHICKEN_INTERNALNAME);
        GameRegistry.registerItem(DNA_CHICKEN, DNA_CHICKEN_INTERNALNAME);
        LanguageRegistry.addName(DNA_CHICKEN, DNA_CHICKEN_DISPLAYNAME);

        // DNA Extractor
        DNAExtractorRecipes.addSmelting(MadNeedles.NEEDLE_CHICKEN.itemID, new ItemStack(DNA_CHICKEN), 0.15F);
    }

    public static void createCowDNA(int itemID)
    {
        // Adds pure sample of Cow DNA.
        DNA_COW = (DNACow) new DNACow(itemID, 4470310, 10592673).setUnlocalizedName(DNA_COW_INTERNALNAME);
        GameRegistry.registerItem(DNA_COW, DNA_COW_INTERNALNAME);
        LanguageRegistry.addName(DNA_COW, DNA_COW_DISPLAYNAME);

        // DNA Extractor
        DNAExtractorRecipes.addSmelting(MadNeedles.NEEDLE_COW.itemID, new ItemStack(DNA_COW), 0.30F);
    }

    public static void createCreeperDNA(int itemID)
    {
        // Adds pure sample of Creeper DNA.
        DNA_CREEPER = (DNACreeper) new DNACreeper(itemID, 894731, 0).setUnlocalizedName(DNA_CREEPER_INTERNALNAME);
        GameRegistry.registerItem(DNA_CREEPER, DNA_CREEPER_INTERNALNAME);
        LanguageRegistry.addName(DNA_CREEPER, DNA_CREEPER_DISPLAYNAME);

        // DNA Extractor
        DNAExtractorRecipes.addSmelting(MadNeedles.NEEDLE_CREEPER.itemID, new ItemStack(DNA_CREEPER), 0.22F);
    }

    public static void createEndermanDNA(int itemID)
    {
        DNA_ENDERMAN = (DNAEnderman) new DNAEnderman(itemID, 1447446, 0).setUnlocalizedName(DNA_ENDERMAN_INTERNALNAME);
        GameRegistry.registerItem(DNA_ENDERMAN, DNA_ENDERMAN_INTERNALNAME);
        LanguageRegistry.addName(DNA_ENDERMAN, DNA_ENDERMAN_DISPLAYNAME);

        // DNA Extractor
        DNAExtractorRecipes.addSmelting(MadNeedles.NEEDLE_ENDERMAN.itemID, new ItemStack(DNA_ENDERMAN), 0.42F);
    }

    public static void createGhastDNA(int itemID)
    {
        DNA_GHAST = (DNAGhast) new DNAGhast(itemID, 16382457, 12369084).setUnlocalizedName(DNA_GHAST_INTERNALNAME);
        GameRegistry.registerItem(DNA_GHAST, DNA_GHAST_INTERNALNAME);
        LanguageRegistry.addName(DNA_GHAST, DNA_GHAST_DISPLAYNAME);

        // DNA Extractor (ghast tears give ghast DNA).
        DNAExtractorRecipes.addSmelting(Item.ghastTear.itemID, new ItemStack(DNA_GHAST), 0.42F);
    }

    public static void createHorseDNA(int itemID)
    {
        DNA_HORSE = (DNAHorse) new DNAHorse(itemID, 12623485, 15656192).setUnlocalizedName(DNA_HORSE_INTERNALNAME);
        GameRegistry.registerItem(DNA_HORSE, DNA_HORSE_INTERNALNAME);
        LanguageRegistry.addName(DNA_HORSE, DNA_HORSE_DISPLAYNAME);

        // DNA Extractor
        DNAExtractorRecipes.addSmelting(MadNeedles.NEEDLE_HORSE.itemID, new ItemStack(DNA_HORSE), 0.42F);
    }

    public static void createMushroomCowDNA(int itemID)
    {
        DNA_MUSHROOMCOW = (DNAMushroomCow) new DNAMushroomCow(itemID, 10489616, 12040119).setUnlocalizedName(DNA_MUSHROOMCOW_INTERNALNAME);
        GameRegistry.registerItem(DNA_MUSHROOMCOW, DNA_MUSHROOMCOW_INTERNALNAME);
        LanguageRegistry.addName(DNA_MUSHROOMCOW, DNA_MUSHROOMCOW_DISPLAYNAME);

        // DNA Extractor
        DNAExtractorRecipes.addSmelting(MadNeedles.NEEDLE_MUSHROOMCOW.itemID, new ItemStack(DNA_MUSHROOMCOW), 0.42F);
    }

    public static void createOcelotDNA(int itemID)
    {
        DNA_OCELOT = (DNAOcelot) new DNAOcelot(itemID, 15720061, 5653556).setUnlocalizedName(DNA_OCELOT_INTERNALNAME);
        GameRegistry.registerItem(DNA_OCELOT, DNA_OCELOT_INTERNALNAME);
        LanguageRegistry.addName(DNA_OCELOT, DNA_OCELOT_DISPLAYNAME);

        // DNA Extractor
        DNAExtractorRecipes.addSmelting(MadNeedles.NEEDLE_OCELOT.itemID, new ItemStack(DNA_OCELOT), 0.42F);
    }

    public static void createPigDNA(int itemID)
    {
        // Adds pure sample of Pig DNA.
        DNA_PIG = (DNAPig) new DNAPig(itemID, 15771042, 14377823).setUnlocalizedName(DNA_PIG_INTERNALNAME);
        GameRegistry.registerItem(DNA_PIG, DNA_PIG_INTERNALNAME);
        LanguageRegistry.addName(DNA_PIG, DNA_PIG_DISPLAYNAME);

        // DNA Extractor
        DNAExtractorRecipes.addSmelting(MadNeedles.NEEDLE_PIG.itemID, new ItemStack(DNA_PIG), 0.35F);
    }

    public static void createSheepDNA(int itemID)
    {
        DNA_SHEEP = (DNASheep) new DNASheep(itemID, 15198183, 16758197).setUnlocalizedName(DNA_SHEEP_INTERNALNAME);
        GameRegistry.registerItem(DNA_SHEEP, DNA_SHEEP_INTERNALNAME);
        LanguageRegistry.addName(DNA_SHEEP, DNA_SHEEP_DISPLAYNAME);

        // DNA Extractor
        DNAExtractorRecipes.addSmelting(MadNeedles.NEEDLE_SHEEP.itemID, new ItemStack(DNA_SHEEP), 0.42F);
    }

    public static void createSkeletonDNA(int itemID)
    {
        DNA_SKELETON = (DNASkeleton) new DNASkeleton(itemID, 12698049, 4802889).setUnlocalizedName(DNA_SKELETON_INTERNALNAME);
        GameRegistry.registerItem(DNA_SKELETON, DNA_SKELETON_INTERNALNAME);
        LanguageRegistry.addName(DNA_SKELETON, DNA_SKELETON_DISPLAYNAME);

        // DNA Extractor (a full bone is required to make skeleton DNA).
        DNAExtractorRecipes.addSmelting(Item.bone.itemID, new ItemStack(DNA_SKELETON), 0.42F);
    }

    public static void createSlimeDNA(int itemID)
    {
        // Adds pure sample of Slime DNA.
        DNA_SLIME = new DNASlime(itemID, 5349438, 8306542).setUnlocalizedName(DNA_SLIME_INTERNALNAME);
        GameRegistry.registerItem(DNA_SLIME, DNA_SLIME_INTERNALNAME);
        LanguageRegistry.addName(DNA_SLIME, DNA_SLIME_DISPLAYNAME);

        // DNA Extractor (slime ball turns into slime DNA)
        DNAExtractorRecipes.addSmelting(Item.slimeBall.itemID, new ItemStack(DNA_SLIME), 0.50F);
    }

    public static void createSpiderDNA(int itemID)
    {
        // Adds pure sample of Spider DNA.
        DNA_SPIDER = (DNASpider) new DNASpider(itemID, 3419431, 11013646).setUnlocalizedName(DNA_SPIDER_INTERNALNAME);
        GameRegistry.registerItem(DNA_SPIDER, DNA_SPIDER_INTERNALNAME);
        LanguageRegistry.addName(DNA_SPIDER, DNA_SPIDER_DISPLAYNAME);

        // DNA Extractor
        DNAExtractorRecipes.addSmelting(MadNeedles.NEEDLE_SPIDER.itemID, new ItemStack(DNA_SPIDER), 0.66F);
    }

    public static void createSquidDNA(int itemID)
    {
        DNA_SQUID = (DNASquid) new DNASquid(itemID, 2243405, 7375001).setUnlocalizedName(DNA_SQUID_INTERNALNAME);
        GameRegistry.registerItem(DNA_SQUID, DNA_SQUID_INTERNALNAME);
        LanguageRegistry.addName(DNA_SQUID, DNA_SQUID_DISPLAYNAME);

        // DNA Extractor
        DNAExtractorRecipes.addSmelting(MadNeedles.NEEDLE_SQUID.itemID, new ItemStack(DNA_SQUID), 0.42F);
    }

    public static void createVillagerDNA(int itemID)
    {
        // Adds pure sample of Villager DNA.
        DNA_VILLAGER = (DNAVillager) new DNAVillager(itemID, 5651507, 12422002).setUnlocalizedName(DNA_VILLAGER_INTERNALNAME);
        GameRegistry.registerItem(DNA_VILLAGER, DNA_VILLAGER_INTERNALNAME);
        LanguageRegistry.addName(DNA_VILLAGER, DNA_VILLAGER_DISPLAYNAME);

        // DNA Extractor
        DNAExtractorRecipes.addSmelting(MadNeedles.NEEDLE_VILLAGER.itemID, new ItemStack(DNA_VILLAGER), 1.0F);
    }

    public static void createWitchDNA(int itemID)
    {
        DNA_WITCH = (DNAWitch) new DNAWitch(itemID, 3407872, 5349438).setUnlocalizedName(DNA_WITCH_INTERNALNAME);
        GameRegistry.registerItem(DNA_WITCH, DNA_WITCH_INTERNALNAME);
        LanguageRegistry.addName(DNA_WITCH, DNA_WITCH_DISPLAYNAME);

        // DNA Extractor
        DNAExtractorRecipes.addSmelting(MadNeedles.NEEDLE_WITCH.itemID, new ItemStack(DNA_WITCH), 0.42F);
    }

    public static void createWolfDNA(int itemID)
    {
        DNA_WOLF = (DNAWolf) new DNAWolf(itemID, 14144467, 13545366).setUnlocalizedName(DNA_WOLF_INTERNALNAME);
        GameRegistry.registerItem(DNA_WOLF, DNA_WOLF_INTERNALNAME);
        LanguageRegistry.addName(DNA_WOLF, DNA_WOLF_DISPLAYNAME);

        // DNA Extractor
        DNAExtractorRecipes.addSmelting(MadNeedles.NEEDLE_WOLF.itemID, new ItemStack(DNA_WOLF), 0.42F);
    }

    public static void createZombieDNA(int itemID)
    {
        // Adds pure sample of Zombie DNA.
        DNA_ZOMBIE = (DNAZombie) new DNAZombie(itemID, 44975, 7969893).setUnlocalizedName(DNA_ZOMBIE_INTERNALNAME);
        GameRegistry.registerItem(DNA_ZOMBIE, DNA_ZOMBIE_INTERNALNAME);
        LanguageRegistry.addName(DNA_ZOMBIE, DNA_ZOMBIE_DISPLAYNAME);

        // DNA Extractor
        DNAExtractorRecipes.addSmelting(MadNeedles.NEEDLE_ZOMBIE.itemID, new ItemStack(DNA_ZOMBIE), 0.50F);
    }
}

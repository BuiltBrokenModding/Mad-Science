package madscience;

import madscience.items.genomes.GenomeBat;
import madscience.items.genomes.GenomeCaveSpider;
import madscience.items.genomes.GenomeChicken;
import madscience.items.genomes.GenomeCow;
import madscience.items.genomes.GenomeCreeper;
import madscience.items.genomes.GenomeEnderman;
import madscience.items.genomes.GenomeGhast;
import madscience.items.genomes.GenomeHorse;
import madscience.items.genomes.GenomeMushroomCow;
import madscience.items.genomes.GenomeOcelot;
import madscience.items.genomes.GenomePig;
import madscience.items.genomes.GenomePigZombie;
import madscience.items.genomes.GenomeSheep;
import madscience.items.genomes.GenomeSkeleton;
import madscience.items.genomes.GenomeSlime;
import madscience.items.genomes.GenomeSpider;
import madscience.items.genomes.GenomeSquid;
import madscience.items.genomes.GenomeVillager;
import madscience.items.genomes.GenomeWitch;
import madscience.items.genomes.GenomeWolf;
import madscience.items.genomes.GenomeZombie;
import madscience.tile.incubator.IncubatorRecipes;
import madscience.tile.mainframe.MainframeRecipes;
import madscience.tile.sequencer.SequencerRecipes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

class MadGenomes
{
    // -----------------
    // GENOME DATA REELS
    // -----------------

    // Cave Spider Genome Data Reel
    private static GenomeCaveSpider GENOME_CAVESPIDER;
    private static final String GENOME_CAVESPIDER_INTERNALNAME = "genomeCaveSpider";

    // Chicken Genome Data Reel
    static GenomeChicken GENOME_CHICKEN;
    private static final String GENOME_CHICKEN_INTERNALNAME = "genomeChicken";

    // Cow Genome Data Reel
    static GenomeCow GENOME_COW;
    private static final String GENOME_COW_INTERNALNAME = "genomeCow";

    // Creeper Genome Data Reel
    static GenomeCreeper GENOME_CREEPER;
    private static final String GENOME_CREEPER_INTERNALNAME = "genomeCreeper";

    // Enderman Genome Data Reel
    static GenomeEnderman GENOME_ENDERMAN;
    private static final String GENOME_ENDERMAN_INTERNALNAME = "genomeEnderman";

    // Ghast Genome Data Reel
    private static GenomeGhast GENOME_GHAST;
    private static final String GENOME_GHAST_INTERNALNAME = "genomeGhast";

    // Horse Genome Data Reel
    static GenomeHorse GENOME_HORSE;
    private static final String GENOME_HORSE_INTERNALNAME = "genomeHorse";

    // Mushroom Cow Genome Data Reel
    private static GenomeMushroomCow GENOME_MUSHROOMCOW;
    private static final String GENOME_MUSHROOMCOW_INTERNALNAME = "genomeMushroomCow";

    // Ocelot Genome Data Reel
    private static GenomeOcelot GENOME_OCELOT;
    private static final String GENOME_OCELOT_INTERNALNAME = "genomeOcelot";

    // Pig Genome Data Reel
    static GenomePig GENOME_PIG;
    private static final String GENOME_PIG_INTERNALNAME = "genomePig";

    // Pig Zombie Genome Data Reel
    private static GenomePigZombie GENOME_PIGZOMBIE;
    private static final String GENOME_PIGZOMBIE_INTERNALNAME = "genomePigZombie";

    // Sheep Genome Data Reel
    static GenomeSheep GENOME_SHEEP;
    private static final String GENOME_SHEEP_INTERNALNAME = "genomeSheep";

    // Skeleton Genome Data Reel
    static GenomeSkeleton GENOME_SKELETON;
    private static final String GENOME_SKELETON_INTERNALNAME = "genomeSkeleton";

    // Squid Genome Data Reel
    static GenomeSquid GENOME_SQUID;
    private static final String GENOME_SQUID_INTERNALNAME = "genomeSquid";

    // Spider Genome Data Reel
    static GenomeSpider GENOME_SPIDER;
    private static final String GENOME_SPIDER_INTERNALNAME = "genomeSpider";

    // Villager Genome Data Reel
    static GenomeVillager GENOME_VILLAGER;
    private static final String GENOME_VILLAGER_INTERNALNAME = "genomeVillager";

    // Witch Genome Data Reel
    private static GenomeWitch GENOME_WITCH;
    private static final String GENOME_WITCH_INTERNALNAME = "genomeWitch";

    // Wolf Genome Data Reel
    static GenomeWolf GENOME_WOLF;
    private static final String GENOME_WOLF_INTERNALNAME = "genomeWolf";

    // Zombie Genome Data Reel
    static GenomeZombie GENOME_ZOMBIE;
    private static final String GENOME_ZOMBIE_INTERNALNAME = "genomeZombie";

    // Bat Genome Data Reel
    private static GenomeBat GENOME_BAT;
    private static final String GENOME_BAT_INTERNALNAME = "genomeBat";

    // Slime Genome Data Reel
    static GenomeSlime GENOME_SLIME;
    private static final String GENOME_SLIME_INTERNALNAME = "genomeSlime";

    // -----------------
    // GENOME DATA REELS
    // -----------------

    static void createBatGenome(int itemID)
    {
        // Bat Genome Data Reel
        GENOME_BAT = (GenomeBat) new GenomeBat(itemID, 4996656, 986895).setUnlocalizedName(GENOME_BAT_INTERNALNAME);
        GameRegistry.registerItem(GENOME_BAT, GENOME_BAT_INTERNALNAME);

        // Incubator
        IncubatorRecipes.addSmelting(GENOME_BAT.itemID, new ItemStack(Item.monsterPlacer, 1, 65));

        // Sequencer
        SequencerRecipes.addSmelting(GENOME_BAT.itemID, new ItemStack(GENOME_BAT), 0.01F);
        SequencerRecipes.addSmelting(MadDNA.DNA_BAT.itemID, new ItemStack(GENOME_BAT), 0.05F);
    }

    static void createCaveSpiderGenome(int itemID)
    {
        GENOME_CAVESPIDER = (GenomeCaveSpider) new GenomeCaveSpider(itemID, 803406, 11013646).setUnlocalizedName(GENOME_CAVESPIDER_INTERNALNAME);
        GameRegistry.registerItem(GENOME_CAVESPIDER, GENOME_CAVESPIDER_INTERNALNAME);

        // Incubator
        IncubatorRecipes.addSmelting(GENOME_CAVESPIDER.itemID, new ItemStack(Item.monsterPlacer, 1, 59));

        // Sequencer
        SequencerRecipes.addSmelting(GENOME_CAVESPIDER.itemID, new ItemStack(GENOME_CAVESPIDER), 0.01F);
        SequencerRecipes.addSmelting(MadDNA.DNA_CAVESPIDER.itemID, new ItemStack(GENOME_CAVESPIDER), 0.05F);
    }

    static void createChickenGenome(int itemID)
    {
        // Chicken Genome Data Reel
        GENOME_CHICKEN = (GenomeChicken) new GenomeChicken(itemID, 10592673, 16711680).setUnlocalizedName(GENOME_CHICKEN_INTERNALNAME);
        GameRegistry.registerItem(GENOME_CHICKEN, GENOME_CHICKEN_INTERNALNAME);

        // Incubator
        IncubatorRecipes.addSmelting(GENOME_CHICKEN.itemID, new ItemStack(Item.monsterPlacer, 1, 93));

        // Sequencer
        SequencerRecipes.addSmelting(GENOME_CHICKEN.itemID, new ItemStack(GENOME_CHICKEN), 0.01F);
        SequencerRecipes.addSmelting(MadDNA.DNA_CHICKEN.itemID, new ItemStack(GENOME_CHICKEN), 0.05F);
    }

    static void createCowGenome(int itemID)
    {
        // Cow Genome Data Reel
        GENOME_COW = (GenomeCow) new GenomeCow(itemID, 4470310, 10592673).setUnlocalizedName(GENOME_COW_INTERNALNAME);
        GameRegistry.registerItem(GENOME_COW, GENOME_COW_INTERNALNAME);

        // Incubator
        IncubatorRecipes.addSmelting(GENOME_COW.itemID, new ItemStack(Item.monsterPlacer, 1, 92));

        // Sequencer
        SequencerRecipes.addSmelting(GENOME_COW.itemID, new ItemStack(GENOME_COW), 0.01F);
        SequencerRecipes.addSmelting(MadDNA.DNA_COW.itemID, new ItemStack(GENOME_COW), 0.05F);
    }

    static void createCreeperGenome(int itemID)
    {
        // Creeper Genome Data Reel
        GENOME_CREEPER = (GenomeCreeper) new GenomeCreeper(itemID, 894731, 0).setUnlocalizedName(GENOME_CREEPER_INTERNALNAME);
        GameRegistry.registerItem(GENOME_CREEPER, GENOME_CREEPER_INTERNALNAME);

        // Incubator
        IncubatorRecipes.addSmelting(GENOME_CREEPER.itemID, new ItemStack(Item.monsterPlacer, 1, 50));

        // Sequencer
        SequencerRecipes.addSmelting(GENOME_CREEPER.itemID, new ItemStack(GENOME_CREEPER), 0.01F);
        SequencerRecipes.addSmelting(MadDNA.DNA_CREEPER.itemID, new ItemStack(GENOME_CREEPER), 0.05F);
    }

    static void createEndermanGenome(int itemID)
    {
        GENOME_ENDERMAN = (GenomeEnderman) new GenomeEnderman(itemID, 1447446, 0).setUnlocalizedName(GENOME_ENDERMAN_INTERNALNAME);
        GameRegistry.registerItem(GENOME_ENDERMAN, GENOME_ENDERMAN_INTERNALNAME);

        // Incubator
        IncubatorRecipes.addSmelting(GENOME_ENDERMAN.itemID, new ItemStack(Item.monsterPlacer, 1, 58));

        // Sequencer
        SequencerRecipes.addSmelting(GENOME_ENDERMAN.itemID, new ItemStack(GENOME_ENDERMAN), 0.01F);
        SequencerRecipes.addSmelting(MadDNA.DNA_ENDERMAN.itemID, new ItemStack(GENOME_ENDERMAN), 0.05F);
    }

    static void createGhastGenome(int itemID)
    {
        GENOME_GHAST = (GenomeGhast) new GenomeGhast(itemID, 16382457, 12369084).setUnlocalizedName(GENOME_GHAST_INTERNALNAME);
        GameRegistry.registerItem(GENOME_GHAST, GENOME_GHAST_INTERNALNAME);

        // Incubator
        IncubatorRecipes.addSmelting(GENOME_GHAST.itemID, new ItemStack(Item.monsterPlacer, 1, 56));

        // Sequencer
        SequencerRecipes.addSmelting(GENOME_GHAST.itemID, new ItemStack(GENOME_GHAST), 0.01F);
        SequencerRecipes.addSmelting(MadDNA.DNA_GHAST.itemID, new ItemStack(GENOME_GHAST), 0.05F);
    }

    static void createHorseGenome(int itemID)
    {
        GENOME_HORSE = (GenomeHorse) new GenomeHorse(itemID, 12623485, 15656192).setUnlocalizedName(GENOME_HORSE_INTERNALNAME);
        GameRegistry.registerItem(GENOME_HORSE, GENOME_HORSE_INTERNALNAME);

        // Incubator
        IncubatorRecipes.addSmelting(GENOME_HORSE.itemID, new ItemStack(Item.monsterPlacer, 1, 100));

        // Sequencer
        SequencerRecipes.addSmelting(GENOME_HORSE.itemID, new ItemStack(GENOME_HORSE), 0.01F);
        SequencerRecipes.addSmelting(MadDNA.DNA_HORSE.itemID, new ItemStack(GENOME_HORSE), 0.05F);
    }

    static void createMushroomCowGenome(int itemID)
    {
        GENOME_MUSHROOMCOW = (GenomeMushroomCow) new GenomeMushroomCow(itemID, 10489616, 12040119).setUnlocalizedName(GENOME_MUSHROOMCOW_INTERNALNAME);
        GameRegistry.registerItem(GENOME_MUSHROOMCOW, GENOME_MUSHROOMCOW_INTERNALNAME);

        // Incubator
        IncubatorRecipes.addSmelting(GENOME_MUSHROOMCOW.itemID, new ItemStack(Item.monsterPlacer, 1, 96));

        // Sequencer
        SequencerRecipes.addSmelting(GENOME_MUSHROOMCOW.itemID, new ItemStack(GENOME_MUSHROOMCOW), 0.01F);
        SequencerRecipes.addSmelting(MadDNA.DNA_MUSHROOMCOW.itemID, new ItemStack(GENOME_MUSHROOMCOW), 0.05F);
    }

    static void createOcelotGenome(int itemID)
    {
        GENOME_OCELOT = (GenomeOcelot) new GenomeOcelot(itemID, 15720061, 5653556).setUnlocalizedName(GENOME_OCELOT_INTERNALNAME);
        GameRegistry.registerItem(GENOME_OCELOT, GENOME_OCELOT_INTERNALNAME);
        // LanguageRegistry.addName(GENOME_OCELOT, GENOME_OCELOT_DISPLAYNAME);

        // Incubator
        IncubatorRecipes.addSmelting(GENOME_OCELOT.itemID, new ItemStack(Item.monsterPlacer, 1, 98));

        // Sequencer
        SequencerRecipes.addSmelting(GENOME_OCELOT.itemID, new ItemStack(GENOME_OCELOT), 0.01F);
        SequencerRecipes.addSmelting(MadDNA.DNA_OCELOT.itemID, new ItemStack(GENOME_OCELOT), 0.05F);
    }

    static void createPigGenome(int itemID)
    {
        // Pig Genome Data Reel
        GENOME_PIG = (GenomePig) new GenomePig(itemID, 15771042, 14377823).setUnlocalizedName(GENOME_PIG_INTERNALNAME);
        GameRegistry.registerItem(GENOME_PIG, GENOME_PIG_INTERNALNAME);

        // Incubator
        IncubatorRecipes.addSmelting(GENOME_PIG.itemID, new ItemStack(Item.monsterPlacer, 1, 90));

        // Sequencer
        SequencerRecipes.addSmelting(GENOME_PIG.itemID, new ItemStack(GENOME_PIG), 0.01F);
        SequencerRecipes.addSmelting(MadDNA.DNA_PIG.itemID, new ItemStack(GENOME_PIG), 0.05F);
    }

    static void createPigZombieGenome(int itemID)
    {
        // PigZombie is a mutant, so he can only be created by merging pig and zombie genomes in mainframe.
        GENOME_PIGZOMBIE = (GenomePigZombie) new GenomePigZombie(itemID, 15373203, 5009705).setUnlocalizedName(GENOME_PIGZOMBIE_INTERNALNAME);
        GameRegistry.registerItem(GENOME_PIGZOMBIE, GENOME_PIGZOMBIE_INTERNALNAME);

        // Recipes for creating this vanilla mob.
        SequencerRecipes.addSmelting(GENOME_PIGZOMBIE.itemID, new ItemStack(GENOME_PIGZOMBIE), 0.01F);
        MainframeRecipes.addRecipe(new ItemStack(GENOME_ZOMBIE), new ItemStack(GENOME_PIG), new ItemStack(GENOME_PIGZOMBIE), 1337);
        IncubatorRecipes.addSmelting(GENOME_PIGZOMBIE.itemID, new ItemStack(Item.monsterPlacer, 1, 57));
    }

    static void createSheepGenome(int itemID)
    {
        GENOME_SHEEP = (GenomeSheep) new GenomeSheep(itemID, 15198183, 16758197).setUnlocalizedName(GENOME_SHEEP_INTERNALNAME);
        GameRegistry.registerItem(GENOME_SHEEP, GENOME_SHEEP_INTERNALNAME);

        // Incubator
        IncubatorRecipes.addSmelting(GENOME_SHEEP.itemID, new ItemStack(Item.monsterPlacer, 1, 91));

        // Sequencer
        SequencerRecipes.addSmelting(GENOME_SHEEP.itemID, new ItemStack(GENOME_SHEEP), 0.01F);
        SequencerRecipes.addSmelting(MadDNA.DNA_SHEEP.itemID, new ItemStack(GENOME_SHEEP), 0.05F);
    }

    static void createSkeletonGenome(int itemID)
    {
        GENOME_SKELETON = (GenomeSkeleton) new GenomeSkeleton(itemID, 12698049, 4802889).setUnlocalizedName(GENOME_SKELETON_INTERNALNAME);
        GameRegistry.registerItem(GENOME_SKELETON, GENOME_SKELETON_INTERNALNAME);

        // Incubator
        IncubatorRecipes.addSmelting(GENOME_SKELETON.itemID, new ItemStack(Item.monsterPlacer, 1, 51));

        // Sequencer
        SequencerRecipes.addSmelting(GENOME_SKELETON.itemID, new ItemStack(GENOME_SKELETON), 0.01F);
        SequencerRecipes.addSmelting(MadDNA.DNA_SKELETON.itemID, new ItemStack(GENOME_SKELETON), 0.05F);
    }

    static void createSlimeGenome(int itemID)
    {
        // Slime Genome Data Reel
        GENOME_SLIME = (GenomeSlime) new GenomeSlime(itemID, 5349438, 8306542).setUnlocalizedName(GENOME_SLIME_INTERNALNAME);
        GameRegistry.registerItem(GENOME_SLIME, GENOME_SLIME_INTERNALNAME);

        // Incubator
        IncubatorRecipes.addSmelting(GENOME_SLIME.itemID, new ItemStack(Item.monsterPlacer, 1, 55));

        // Sequencer
        SequencerRecipes.addSmelting(GENOME_SLIME.itemID, new ItemStack(GENOME_SLIME), 0.01F);
        SequencerRecipes.addSmelting(MadDNA.DNA_SLIME.itemID, new ItemStack(GENOME_SLIME), 0.05F);
    }

    static void createSpiderGenome(int itemID)
    {
        // Spider Genome Data Reel
        GENOME_SPIDER = (GenomeSpider) new GenomeSpider(itemID, 3419431, 11013646).setUnlocalizedName(GENOME_SPIDER_INTERNALNAME);
        GameRegistry.registerItem(GENOME_SPIDER, GENOME_SPIDER_INTERNALNAME);

        // Incubator
        IncubatorRecipes.addSmelting(GENOME_SPIDER.itemID, new ItemStack(Item.monsterPlacer, 1, 52));

        // Sequencer
        SequencerRecipes.addSmelting(GENOME_SPIDER.itemID, new ItemStack(GENOME_SPIDER), 0.01F);
        SequencerRecipes.addSmelting(MadDNA.DNA_SPIDER.itemID, new ItemStack(GENOME_SPIDER), 0.05F);
    }

    static void createSquidGenome(int itemID)
    {
        GENOME_SQUID = (GenomeSquid) new GenomeSquid(itemID, 2243405, 7375001).setUnlocalizedName(GENOME_SQUID_INTERNALNAME);
        GameRegistry.registerItem(GENOME_SQUID, GENOME_SQUID_INTERNALNAME);

        // Incubator
        IncubatorRecipes.addSmelting(GENOME_SQUID.itemID, new ItemStack(Item.monsterPlacer, 1, 94));

        // Sequencer
        SequencerRecipes.addSmelting(GENOME_SQUID.itemID, new ItemStack(GENOME_SQUID), 0.01F);
        SequencerRecipes.addSmelting(MadDNA.DNA_SQUID.itemID, new ItemStack(GENOME_SQUID), 0.05F);
    }

    static void createVillagerGenome(int itemID)
    {
        // Villager Genome Data Reel
        GENOME_VILLAGER = (GenomeVillager) new GenomeVillager(itemID, 5651507, 12422002).setUnlocalizedName(GENOME_VILLAGER_INTERNALNAME);
        GameRegistry.registerItem(GENOME_VILLAGER, GENOME_VILLAGER_INTERNALNAME);

        // Incubator
        IncubatorRecipes.addSmelting(GENOME_VILLAGER.itemID, new ItemStack(Item.monsterPlacer, 1, 120));

        // Sequencer
        SequencerRecipes.addSmelting(GENOME_VILLAGER.itemID, new ItemStack(GENOME_VILLAGER), 0.01F);
        SequencerRecipes.addSmelting(MadDNA.DNA_VILLAGER.itemID, new ItemStack(GENOME_VILLAGER), 0.05F);
    }

    static void createWitchGenome(int itemID)
    {
        GENOME_WITCH = (GenomeWitch) new GenomeWitch(itemID, 3407872, 5349438).setUnlocalizedName(GENOME_WITCH_INTERNALNAME);
        GameRegistry.registerItem(GENOME_WITCH, GENOME_WITCH_INTERNALNAME);

        // Incubator
        IncubatorRecipes.addSmelting(GENOME_WITCH.itemID, new ItemStack(Item.monsterPlacer, 1, 66));

        // Sequencer
        SequencerRecipes.addSmelting(GENOME_WITCH.itemID, new ItemStack(GENOME_WITCH), 0.01F);
        SequencerRecipes.addSmelting(MadDNA.DNA_WITCH.itemID, new ItemStack(GENOME_WITCH), 0.05F);
    }

    static void createWolfGenome(int itemID)
    {
        GENOME_WOLF = (GenomeWolf) new GenomeWolf(itemID, 14144467, 13545366).setUnlocalizedName(GENOME_WOLF_INTERNALNAME);
        GameRegistry.registerItem(GENOME_WOLF, GENOME_WOLF_INTERNALNAME);

        // Incubator
        IncubatorRecipes.addSmelting(GENOME_WOLF.itemID, new ItemStack(Item.monsterPlacer, 1, 95));

        // Sequencer
        SequencerRecipes.addSmelting(GENOME_WOLF.itemID, new ItemStack(GENOME_WOLF), 0.01F);
        SequencerRecipes.addSmelting(MadDNA.DNA_WOLF.itemID, new ItemStack(GENOME_WOLF), 0.05F);
    }

    static void createZombieGenome(int itemID)
    {
        // Zombie Genome Data Reel
        GENOME_ZOMBIE = (GenomeZombie) new GenomeZombie(itemID, 44975, 7969893).setUnlocalizedName(GENOME_ZOMBIE_INTERNALNAME);
        GameRegistry.registerItem(GENOME_ZOMBIE, GENOME_ZOMBIE_INTERNALNAME);

        // Incubator
        IncubatorRecipes.addSmelting(GENOME_ZOMBIE.itemID, new ItemStack(Item.monsterPlacer, 1, 54));

        // Sequencer
        SequencerRecipes.addSmelting(GENOME_ZOMBIE.itemID, new ItemStack(GENOME_ZOMBIE), 0.01F);
        SequencerRecipes.addSmelting(MadDNA.DNA_ZOMBIE.itemID, new ItemStack(GENOME_ZOMBIE), 0.05F);
    }
}

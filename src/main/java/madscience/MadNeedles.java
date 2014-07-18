package madscience;

import madscience.factory.mod.MadMod;
import madscience.items.needles.NeedleBat;
import madscience.items.needles.NeedleCaveSpider;
import madscience.items.needles.NeedleChicken;
import madscience.items.needles.NeedleCow;
import madscience.items.needles.NeedleCreeper;
import madscience.items.needles.NeedleDirtyItem;
import madscience.items.needles.NeedleEmptyItem;
import madscience.items.needles.NeedleEnderman;
import madscience.items.needles.NeedleHorse;
import madscience.items.needles.NeedleMushroomCow;
import madscience.items.needles.NeedleMutant;
import madscience.items.needles.NeedleOcelot;
import madscience.items.needles.NeedlePig;
import madscience.items.needles.NeedleSheep;
import madscience.items.needles.NeedleSpider;
import madscience.items.needles.NeedleSquid;
import madscience.items.needles.NeedleVillager;
import madscience.items.needles.NeedleWitch;
import madscience.items.needles.NeedleWolf;
import madscience.items.needles.NeedleZombie;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class MadNeedles
{
    // -------
    // NEEDLES
    // -------

    // Empty needle.
    public static NeedleEmptyItem NEEDLE_ITEM;
    public static final String NEEDLE_ITEM_INTERNALNAME = "needleEmpty";

    // Dirty needle.
    public static NeedleDirtyItem NEEDLE_DIRTY;
    private static final String NEEDLEDIRTY_ITEM_INTERNALNAME = "needleDirty";

    // Needle with cave spider DNA.
    public static NeedleCaveSpider NEEDLE_CAVESPIDER;
    private static final String NEEDLE_CAVESPIDER_INTERNALNAME = "needleCaveSpider";

    // Needle with chicken DNA.
    public static NeedleChicken NEEDLE_CHICKEN;
    private static final String NEEDLE_CHICKEN_INTERNALNAME = "needleChicken";

    // Needle with cow DNA.
    public static NeedleCow NEEDLE_COW;
    private static final String NEEDLE_COW_INTERNALNAME = "needleCow";

    // Needle with creeper DNA.
    public static NeedleCreeper NEEDLE_CREEPER;
    private static final String NEEDLE_CREEPER_INTERNALNAME = "needleCreeper";

    // Needle with enderman DNA.
    public static NeedleEnderman NEEDLE_ENDERMAN;
    private static final String NEEDLE_ENDERMAN_INTERNALNAME = "needleEnderman";

    // Needle with horse DNA.
    public static NeedleHorse NEEDLE_HORSE;
    private static final String NEEDLE_HORSE_INTERNALNAME = "needleHorse";

    // Needle with mushroom cow DNA.
    public static NeedleMushroomCow NEEDLE_MUSHROOMCOW;
    private static final String NEEDLE_MUSHROOMCOW_INTERNALNAME = "needleMushroomCow";

    // Needle with ocelot DNA.
    public static NeedleOcelot NEEDLE_OCELOT;
    private static final String NEEDLE_OCELOT_INTERNALNAME = "needleOcelot";

    // Needle with pig DNA.
    public static NeedlePig NEEDLE_PIG;
    private static final String NEEDLE_PIG_INTERNALNAME = "needlePig";

    // Needle with sheep DNA.
    public static NeedleSheep NEEDLE_SHEEP;
    private static final String NEEDLE_SHEEP_INTERNALNAME = "needleSheep";

    // Needle with spider DNA.
    public static NeedleSpider NEEDLE_SPIDER;
    private static final String NEEDLE_SPIDER_INTERNALNAME = "needleSpider";

    // Needle with squid DNA.
    public static NeedleSquid NEEDLE_SQUID;
    private static final String NEEDLE_SQUID_INTERNALNAME = "needleSquid";

    // Needle with villager DNA.
    public static NeedleVillager NEEDLE_VILLAGER;
    private static final String NEEDLE_VILLAGER_INTERNALNAME = "needleVillager";

    // Needle with witch DNA.
    public static NeedleWitch NEEDLE_WITCH;
    private static final String NEEDLE_WITCH_INTERNALNAME = "needleWitch";

    // Needle with wolf DNA.
    public static NeedleWolf NEEDLE_WOLF;
    private static final String NEEDLE_WOLF_INTERNALNAME = "needleWolf";

    // Needle with zombie DNA.
    public static NeedleZombie NEEDLE_ZOMBIE;
    private static final String NEEDLE_ZOMBIE_INTERNALNAME = "needleZombie";

    // Needle with mutant DNA.
    public static NeedleMutant NEEDLE_MUTANT;
    private static final String NEEDLE_MUTANT_INTERNALNAME = "needleMutant";

    // Needle with bat DNA.
    public static NeedleBat NEEDLE_BAT;
    private static String NEEDLE_BAT_INTERNALNAME = "needleBat";

    // -------------------
    // NEEDLE REGISTRY ADD
    // -------------------

    static void createBatNeedle(int itemID)
    {
        // Adds needle filled with Bat blood.
        MadMod.LOGGER.info("-Needle Bat Blood");
        NEEDLE_BAT = (NeedleBat) new NeedleBat(itemID, 4996656, 986895).setUnlocalizedName(NEEDLE_BAT_INTERNALNAME);
        GameRegistry.registerItem(NEEDLE_BAT, NEEDLE_BAT_INTERNALNAME);
    }

    static void createCaveSpiderNeedle(int itemID)
    {
        // Adds needle filled with Cave Spider blood.
        MadMod.LOGGER.info("-Needle Cave Spider Blood");
        NEEDLE_CAVESPIDER = (NeedleCaveSpider) new NeedleCaveSpider(itemID, 803406, 11013646).setUnlocalizedName(NEEDLE_CAVESPIDER_INTERNALNAME);
        GameRegistry.registerItem(NEEDLE_CAVESPIDER, NEEDLE_CAVESPIDER_INTERNALNAME);
    }

    static void createChickenNeedle(int itemID)
    {
        // Adds needle filled with Chicken blood.
        MadMod.LOGGER.info("-Needle Chicken Blood");
        NEEDLE_CHICKEN = (NeedleChicken) new NeedleChicken(itemID, 10592673, 16711680).setUnlocalizedName(NEEDLE_CHICKEN_INTERNALNAME);
        GameRegistry.registerItem(NEEDLE_CHICKEN, NEEDLE_CHICKEN_INTERNALNAME);
    }

    static void createCowNeedle(int itemID)
    {
        // Adds needle filled with Cow blood.
        MadMod.LOGGER.info("-Needle Cow Blood");
        NEEDLE_COW = (NeedleCow) new NeedleCow(itemID, 4470310, 10592673).setUnlocalizedName(NEEDLE_COW_INTERNALNAME);
        GameRegistry.registerItem(NEEDLE_COW, NEEDLE_COW_INTERNALNAME);
    }

    static void createCreeperNeedle(int itemID)
    {
        // Adds needle filled with Creeper blood.
        MadMod.LOGGER.info("-Needle Creeper Blood");
        NEEDLE_CREEPER = (NeedleCreeper) new NeedleCreeper(itemID, 894731, 0).setUnlocalizedName(NEEDLE_CREEPER_INTERNALNAME);
        GameRegistry.registerItem(NEEDLE_CREEPER, NEEDLE_CREEPER_INTERNALNAME);
    }

    static void createDirtyNeedle(int itemID)
    {
        // Adds dirty needle that is left over from extraction.
        MadMod.LOGGER.info("-Dirty Needle");
        NEEDLE_DIRTY = (NeedleDirtyItem) new NeedleDirtyItem(itemID).setUnlocalizedName(NEEDLEDIRTY_ITEM_INTERNALNAME);
        GameRegistry.registerItem(NEEDLE_DIRTY, NEEDLEDIRTY_ITEM_INTERNALNAME);
    }

    static void createEmptyNeedle(int itemID)
    {
        // Adds empty needle to the game used for taking DNA out of mobs.
        MadMod.LOGGER.info("-Empty Needle");
        NEEDLE_ITEM = (NeedleEmptyItem) new NeedleEmptyItem(itemID).setUnlocalizedName(NEEDLE_ITEM_INTERNALNAME);
        GameRegistry.registerItem(NEEDLE_ITEM, NEEDLE_ITEM_INTERNALNAME);

        // Shaped.
        GameRegistry.addRecipe(new ItemStack(NEEDLE_ITEM, 1), new Object[]
        {
                // Recipe.
                "GIG", "G G", " S ",

                // Parameters.
                'G', Block.glass, 'I', Item.ingotIron, 'S', Item.stick });
    }

    static void createEndermanNeedle(int itemID)
    {
        // Adds needle filled with Enderman blood.
        MadMod.LOGGER.info("-Needle Enderman Blood");
        NEEDLE_ENDERMAN = (NeedleEnderman) new NeedleEnderman(itemID, 1447446, 0).setUnlocalizedName(NEEDLE_ENDERMAN_INTERNALNAME);
        GameRegistry.registerItem(NEEDLE_ENDERMAN, NEEDLE_ENDERMAN_INTERNALNAME);
    }

    static void createHorseNeedle(int itemID)
    {
        // Adds needle filled with Horse blood.
        MadMod.LOGGER.info("-Needle Horse Blood");
        NEEDLE_HORSE = (NeedleHorse) new NeedleHorse(itemID, 12623485, 15656192).setUnlocalizedName(NEEDLE_HORSE_INTERNALNAME);
        GameRegistry.registerItem(NEEDLE_HORSE, NEEDLE_HORSE_INTERNALNAME);
    }

    static void createMushroomCowNeedle(int itemID)
    {
        // Adds needle filled with Mushroom Cow blood.
        MadMod.LOGGER.info("-Needle Mushroom Cow Blood");
        NEEDLE_MUSHROOMCOW = (NeedleMushroomCow) new NeedleMushroomCow(itemID, 10489616, 12040119).setUnlocalizedName(NEEDLE_MUSHROOMCOW_INTERNALNAME);
        GameRegistry.registerItem(NEEDLE_MUSHROOMCOW, NEEDLE_MUSHROOMCOW_INTERNALNAME);
    }

    static void createMutantNeedle(int itemID)
    {
        // Adds needle filled with Mutant blood.
        MadMod.LOGGER.info("-Needle Mutant Blood");
        NEEDLE_MUTANT = (NeedleMutant) new NeedleMutant(itemID, 5349438, 8306542).setUnlocalizedName(NEEDLE_MUTANT_INTERNALNAME);
        GameRegistry.registerItem(NEEDLE_MUTANT, NEEDLE_MUTANT_INTERNALNAME);
    }

    static void createOcelotNeedle(int itemID)
    {
        // Adds needle filled with Ocelot blood.
        MadMod.LOGGER.info("-Needle Ocelot Blood");
        NEEDLE_OCELOT = (NeedleOcelot) new NeedleOcelot(itemID, 15720061, 5653556).setUnlocalizedName(NEEDLE_OCELOT_INTERNALNAME);
        GameRegistry.registerItem(NEEDLE_OCELOT, NEEDLE_OCELOT_INTERNALNAME);
    }

    static void createPigNeedle(int itemID)
    {
        // Adds needle filled with Pig blood.
        MadMod.LOGGER.info("-Needle Pig Blood");
        NEEDLE_PIG = (NeedlePig) new NeedlePig(itemID, 15771042, 14377823).setUnlocalizedName(NEEDLE_PIG_INTERNALNAME);
        GameRegistry.registerItem(NEEDLE_PIG, NEEDLE_PIG_INTERNALNAME);
    }

    static void createSheepNeedle(int itemID)
    {
        // Adds needle filled with Sheep blood.
        MadMod.LOGGER.info("-Needle Sheep Blood");
        NEEDLE_SHEEP = (NeedleSheep) new NeedleSheep(itemID, 15771042, 14377823).setUnlocalizedName(NEEDLE_SHEEP_INTERNALNAME);
        GameRegistry.registerItem(NEEDLE_SHEEP, NEEDLE_SHEEP_INTERNALNAME);
    }

    static void createSpiderNeedle(int itemID)
    {
        // Adds needle filled with Spider blood.
        MadMod.LOGGER.info("-Needle Spider Blood");
        NEEDLE_SPIDER = (NeedleSpider) new NeedleSpider(itemID, 3419431, 11013646).setUnlocalizedName(NEEDLE_SPIDER_INTERNALNAME);
        GameRegistry.registerItem(NEEDLE_SPIDER, NEEDLE_SPIDER_INTERNALNAME);
    }

    static void createSquidNeedle(int itemID)
    {
        // Adds needle filled with Squid blood.
        MadMod.LOGGER.info("-Needle Squid Blood");
        NEEDLE_SQUID = (NeedleSquid) new NeedleSquid(itemID, 2243405, 7375001).setUnlocalizedName(NEEDLE_SQUID_INTERNALNAME);
        GameRegistry.registerItem(NEEDLE_SQUID, NEEDLE_SQUID_INTERNALNAME);
    }

    static void createVillagerNeedle(int itemID)
    {
        // Adds needle filled with Villager blood.
        MadMod.LOGGER.info("-Needle Villager Blood");
        NEEDLE_VILLAGER = (NeedleVillager) new NeedleVillager(itemID, 5651507, 12422002).setUnlocalizedName(NEEDLE_VILLAGER_INTERNALNAME);
        GameRegistry.registerItem(NEEDLE_VILLAGER, NEEDLE_VILLAGER_INTERNALNAME);
    }

    static void createWitchNeedle(int itemID)
    {
        // Adds needle filled with Witch blood.
        MadMod.LOGGER.info("-Needle Witch Blood");
        NEEDLE_WITCH = (NeedleWitch) new NeedleWitch(itemID, 3407872, 5349438).setUnlocalizedName(NEEDLE_WITCH_INTERNALNAME);
        GameRegistry.registerItem(NEEDLE_WITCH, NEEDLE_WITCH_INTERNALNAME);
    }

    static void createWolfNeedle(int itemID)
    {
        // Adds needle filled with Wolf blood.
        MadMod.LOGGER.info("-Needle Wolf Blood");
        NEEDLE_WOLF = (NeedleWolf) new NeedleWolf(itemID, 14144467, 13545366).setUnlocalizedName(NEEDLE_WOLF_INTERNALNAME);
        GameRegistry.registerItem(NEEDLE_WOLF, NEEDLE_WOLF_INTERNALNAME);
    }

    static void createZombieNeedle(int itemID)
    {
        // Adds needle filled with Zombie blood.
        MadMod.LOGGER.info("-Needle Zombie Blood");
        NEEDLE_ZOMBIE = (NeedleZombie) new NeedleZombie(itemID, 44975, 7969893).setUnlocalizedName(NEEDLE_ZOMBIE_INTERNALNAME);
        GameRegistry.registerItem(NEEDLE_ZOMBIE, NEEDLE_ZOMBIE_INTERNALNAME);
    }
}

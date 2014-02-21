package madscience;

import madscience.items.NeedleDirtyItem;
import madscience.items.NeedleEmptyItem;
import madscience.items.needles.NeedleBat;
import madscience.items.needles.NeedleCaveSpider;
import madscience.items.needles.NeedleChicken;
import madscience.items.needles.NeedleCow;
import madscience.items.needles.NeedleCreeper;
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
import madscience.tileentities.sanitizer.SanitizerRecipes;
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
    public static final String NEEDLEDIRTY_ITEM_INTERNALNAME = "needleDirty";

    // Needle with cave spider DNA.
    public static NeedleCaveSpider NEEDLE_CAVESPIDER;
    public static final String NEEDLE_CAVESPIDER_INTERNALNAME = "needleCaveSpider";

    // Needle with chicken DNA.
    public static NeedleChicken NEEDLE_CHICKEN;
    public static final String NEEDLE_CHICKEN_INTERNALNAME = "needleChicken";

    // Needle with cow DNA.
    public static NeedleCow NEEDLE_COW;
    public static final String NEEDLE_COW_INTERNALNAME = "needleCow";

    // Needle with creeper DNA.
    public static NeedleCreeper NEEDLE_CREEPER;
    public static final String NEEDLE_CREEPER_INTERNALNAME = "needleCreeper";

    // Needle with enderman DNA.
    public static NeedleEnderman NEEDLE_ENDERMAN;
    public static final String NEEDLE_ENDERMAN_INTERNALNAME = "needleEnderman";

    // Needle with horse DNA.
    public static NeedleHorse NEEDLE_HORSE;
    public static final String NEEDLE_HORSE_INTERNALNAME = "needleHorse";

    // Needle with mushroom cow DNA.
    public static NeedleMushroomCow NEEDLE_MUSHROOMCOW;
    public static final String NEEDLE_MUSHROOMCOW_INTERNALNAME = "needleMushroomCow";

    // Needle with ocelot DNA.
    public static NeedleOcelot NEEDLE_OCELOT;
    public static final String NEEDLE_OCELOT_INTERNALNAME = "needleOcelot";

    // Needle with pig DNA.
    public static NeedlePig NEEDLE_PIG;
    public static final String NEEDLE_PIG_INTERNALNAME = "needlePig";

    // Needle with sheep DNA.
    public static NeedleSheep NEEDLE_SHEEP;
    public static final String NEEDLE_SHEEP_INTERNALNAME = "needleSheep";

    // Needle with spider DNA.
    public static NeedleSpider NEEDLE_SPIDER;
    public static final String NEEDLE_SPIDER_INTERNALNAME = "needleSpider";

    // Needle with squid DNA.
    public static NeedleSquid NEEDLE_SQUID;
    public static final String NEEDLE_SQUID_INTERNALNAME = "needleSquid";

    // Needle with villager DNA.
    public static NeedleVillager NEEDLE_VILLAGER;
    public static final String NEEDLE_VILLAGER_INTERNALNAME = "needleVillager";

    // Needle with witch DNA.
    public static NeedleWitch NEEDLE_WITCH;
    public static final String NEEDLE_WITCH_INTERNALNAME = "needleWitch";

    // Needle with wolf DNA.
    public static NeedleWolf NEEDLE_WOLF;
    public static final String NEEDLE_WOLF_INTERNALNAME = "needleWolf";

    // Needle with zombie DNA.
    public static NeedleZombie NEEDLE_ZOMBIE;
    public static final String NEEDLE_ZOMBIE_INTERNALNAME = "needleZombie";

    // Needle with mutant DNA.
    public static NeedleMutant NEEDLE_MUTANT;
    public static final String NEEDLE_MUTANT_INTERNALNAME = "needleMutant";

    // Needle with bat DNA.
    public static NeedleBat NEEDLE_BAT;
    public static String NEEDLE_BAT_INTERNALNAME = "needleBat";

    // -------------------
    // NEEDLE REGISTRY ADD
    // -------------------

    public static void createBatNeedle(int itemID)
    {
        // Adds needle filled with Bat blood.
        NEEDLE_BAT = (NeedleBat) new NeedleBat(itemID, 4996656, 986895).setUnlocalizedName(NEEDLE_BAT_INTERNALNAME);
        GameRegistry.registerItem(NEEDLE_BAT, NEEDLE_BAT_INTERNALNAME);
        // LanguageRegistry.addName(NEEDLE_BAT, NEEDLE_BAT_DISPLAYNAME);
    }

    public static void createCaveSpiderNeedle(int itemID)
    {
        // Adds needle filled with Cave Spider blood.
        NEEDLE_CAVESPIDER = (NeedleCaveSpider) new NeedleCaveSpider(itemID, 803406, 11013646).setUnlocalizedName(NEEDLE_CAVESPIDER_INTERNALNAME);
        GameRegistry.registerItem(NEEDLE_CAVESPIDER, NEEDLE_CAVESPIDER_INTERNALNAME);
        // LanguageRegistry.addName(NEEDLE_CAVESPIDER, NEEDLE_CAVESPIDER_DISPLAYNAME);
    }

    public static void createChickenNeedle(int itemID)
    {
        // Adds needle filled with Chicken blood.
        NEEDLE_CHICKEN = (NeedleChicken) new NeedleChicken(itemID, 10592673, 16711680).setUnlocalizedName(NEEDLE_CHICKEN_INTERNALNAME);
        GameRegistry.registerItem(NEEDLE_CHICKEN, NEEDLE_CHICKEN_INTERNALNAME);
        // LanguageRegistry.addName(NEEDLE_CHICKEN, NEEDLE_CHICKEN_DISPLAYNAME);
    }

    public static void createCowNeedle(int itemID)
    {
        // Adds needle filled with Cow blood.
        NEEDLE_COW = (NeedleCow) new NeedleCow(itemID, 4470310, 10592673).setUnlocalizedName(NEEDLE_COW_INTERNALNAME);
        GameRegistry.registerItem(NEEDLE_COW, NEEDLE_COW_INTERNALNAME);
        // LanguageRegistry.addName(NEEDLE_COW, NEEDLE_COW_DISPLAYNAME);
    }

    public static void createCreeperNeedle(int itemID)
    {
        // Adds needle filled with Creeper blood.
        NEEDLE_CREEPER = (NeedleCreeper) new NeedleCreeper(itemID, 894731, 0).setUnlocalizedName(NEEDLE_CREEPER_INTERNALNAME);
        GameRegistry.registerItem(NEEDLE_CREEPER, NEEDLE_CREEPER_INTERNALNAME);
        // LanguageRegistry.addName(NEEDLE_CREEPER, NEEDLE_CREEPER_DISPLAYNAME);
    }

    public static void createDirtyNeedle(int itemID)
    {
        // Adds dirty needle that is left over from extraction or when
        // expiration occurs.
        NEEDLE_DIRTY = (NeedleDirtyItem) new NeedleDirtyItem(itemID).setUnlocalizedName(NEEDLEDIRTY_ITEM_INTERNALNAME);
        GameRegistry.registerItem(NEEDLE_DIRTY, NEEDLEDIRTY_ITEM_INTERNALNAME);
        // LanguageRegistry.addName(NEEDLE_DIRTY, NEEDLEDIRTY_ITEM_DISPLAYNAME);

        // Needle Sanitizer (cleans dirty needles).
        SanitizerRecipes.addSmelting(NEEDLE_DIRTY.itemID, new ItemStack(NEEDLE_ITEM), 0.15F);
    }

    public static void createEmptyNeedle(int itemID)
    {
        // Adds empty needle to the game used for taking DNA out of mobs.
        NEEDLE_ITEM = (NeedleEmptyItem) new NeedleEmptyItem(itemID).setUnlocalizedName(NEEDLE_ITEM_INTERNALNAME);
        GameRegistry.registerItem(NEEDLE_ITEM, NEEDLE_ITEM_INTERNALNAME);
        // LanguageRegistry.addName(NEEDLE_ITEM, NEEDLE_ITEM_DISPLAYNAME);

        // Shaped.
        GameRegistry.addRecipe(new ItemStack(NEEDLE_ITEM, 1), new Object[]
        {
                // Recipe.
                "GIG", "G G", " S ",

                // Parameters.
                'G', Block.glass, 'I', Item.ingotIron, 'S', Item.stick });
    }

    public static void createEndermanNeedle(int itemID)
    {
        // Adds needle filled with Enderman blood.
        NEEDLE_ENDERMAN = (NeedleEnderman) new NeedleEnderman(itemID, 1447446, 0).setUnlocalizedName(NEEDLE_ENDERMAN_INTERNALNAME);
        GameRegistry.registerItem(NEEDLE_ENDERMAN, NEEDLE_ENDERMAN_INTERNALNAME);
        // LanguageRegistry.addName(NEEDLE_ENDERMAN, NEEDLE_ENDERMAN_DISPLAYNAME);
    }

    public static void createHorseNeedle(int itemID)
    {
        // Adds needle filled with Horse blood.
        NEEDLE_HORSE = (NeedleHorse) new NeedleHorse(itemID, 12623485, 15656192).setUnlocalizedName(NEEDLE_HORSE_INTERNALNAME);
        GameRegistry.registerItem(NEEDLE_HORSE, NEEDLE_HORSE_INTERNALNAME);
        // LanguageRegistry.addName(NEEDLE_HORSE, NEEDLE_HORSE_DISPLAYNAME);
    }

    public static void createMushroomCowNeedle(int itemID)
    {
        // Adds needle filled with Mushroom Cow blood.
        NEEDLE_MUSHROOMCOW = (NeedleMushroomCow) new NeedleMushroomCow(itemID, 10489616, 12040119).setUnlocalizedName(NEEDLE_MUSHROOMCOW_INTERNALNAME);
        GameRegistry.registerItem(NEEDLE_MUSHROOMCOW, NEEDLE_MUSHROOMCOW_INTERNALNAME);
        // LanguageRegistry.addName(NEEDLE_MUSHROOMCOW, NEEDLE_MUSHROOMCOW_DISPLAYNAME);
    }

    public static void createMutantNeedle(int itemID)
    {
        // Adds needle filled with Mutant blood.
        NEEDLE_MUTANT = (NeedleMutant) new NeedleMutant(itemID, 5349438, 8306542).setUnlocalizedName(NEEDLE_MUTANT_INTERNALNAME);
        GameRegistry.registerItem(NEEDLE_MUTANT, NEEDLE_MUTANT_INTERNALNAME);
        // LanguageRegistry.addName(NEEDLE_MUTANT, NEEDLE_MUTANT_DISPLAYNAME);
    }

    public static void createOcelotNeedle(int itemID)
    {
        // Adds needle filled with Ocelot blood.
        NEEDLE_OCELOT = (NeedleOcelot) new NeedleOcelot(itemID, 15720061, 5653556).setUnlocalizedName(NEEDLE_OCELOT_INTERNALNAME);
        GameRegistry.registerItem(NEEDLE_OCELOT, NEEDLE_OCELOT_INTERNALNAME);
        // LanguageRegistry.addName(NEEDLE_OCELOT, NEEDLE_OCELOT_DISPLAYNAME);
    }

    public static void createPigNeedle(int itemID)
    {
        // Adds needle filled with Pig blood.
        NEEDLE_PIG = (NeedlePig) new NeedlePig(itemID, 15771042, 14377823).setUnlocalizedName(NEEDLE_PIG_INTERNALNAME);
        GameRegistry.registerItem(NEEDLE_PIG, NEEDLE_PIG_INTERNALNAME);
        // LanguageRegistry.addName(NEEDLE_PIG, NEEDLE_PIG_DISPLAYNAME);
    }

    public static void createSheepNeedle(int itemID)
    {
        // Adds needle filled with Sheep blood.
        NEEDLE_SHEEP = (NeedleSheep) new NeedleSheep(itemID, 15771042, 14377823).setUnlocalizedName(NEEDLE_SHEEP_INTERNALNAME);
        GameRegistry.registerItem(NEEDLE_SHEEP, NEEDLE_SHEEP_INTERNALNAME);
        // LanguageRegistry.addName(NEEDLE_SHEEP, NEEDLE_SHEEP_DISPLAYNAME);
    }

    public static void createSpiderNeedle(int itemID)
    {
        // Adds needle filled with Spider blood.
        NEEDLE_SPIDER = (NeedleSpider) new NeedleSpider(itemID, 3419431, 11013646).setUnlocalizedName(NEEDLE_SPIDER_INTERNALNAME);
        GameRegistry.registerItem(NEEDLE_SPIDER, NEEDLE_SPIDER_INTERNALNAME);
        // LanguageRegistry.addName(NEEDLE_SPIDER, NEEDLE_SPIDER_DISPLAYNAME);
    }

    public static void createSquidNeedle(int itemID)
    {
        // Adds needle filled with Squid blood.
        NEEDLE_SQUID = (NeedleSquid) new NeedleSquid(itemID, 2243405, 7375001).setUnlocalizedName(NEEDLE_SQUID_INTERNALNAME);
        GameRegistry.registerItem(NEEDLE_SQUID, NEEDLE_SQUID_INTERNALNAME);
        // LanguageRegistry.addName(NEEDLE_SQUID, NEEDLE_SQUID_DISPLAYNAME);
    }

    public static void createVillagerNeedle(int itemID)
    {
        // Adds needle filled with Villager blood.
        NEEDLE_VILLAGER = (NeedleVillager) new NeedleVillager(itemID, 5651507, 12422002).setUnlocalizedName(NEEDLE_VILLAGER_INTERNALNAME);
        GameRegistry.registerItem(NEEDLE_VILLAGER, NEEDLE_VILLAGER_INTERNALNAME);
        // LanguageRegistry.addName(NEEDLE_VILLAGER, NEEDLE_VILLAGER_DISPLAYNAME);
    }

    public static void createWitchNeedle(int itemID)
    {
        // Adds needle filled with Witch blood.
        NEEDLE_WITCH = (NeedleWitch) new NeedleWitch(itemID, 3407872, 5349438).setUnlocalizedName(NEEDLE_WITCH_INTERNALNAME);
        GameRegistry.registerItem(NEEDLE_WITCH, NEEDLE_WITCH_INTERNALNAME);
        // LanguageRegistry.addName(NEEDLE_WITCH, NEEDLE_WITCH_DISPLAYNAME);
    }

    public static void createWolfNeedle(int itemID)
    {
        // Adds needle filled with Wolf blood.
        NEEDLE_WOLF = (NeedleWolf) new NeedleWolf(itemID, 14144467, 13545366).setUnlocalizedName(NEEDLE_WOLF_INTERNALNAME);
        GameRegistry.registerItem(NEEDLE_WOLF, NEEDLE_WOLF_INTERNALNAME);
        // LanguageRegistry.addName(NEEDLE_WOLF, NEEDLE_WOLF_DISPLAYNAME);
    }

    public static void createZombieNeedle(int itemID)
    {
        // Adds needle filled with Zombie blood.
        NEEDLE_ZOMBIE = (NeedleZombie) new NeedleZombie(itemID, 44975, 7969893).setUnlocalizedName(NEEDLE_ZOMBIE_INTERNALNAME);
        GameRegistry.registerItem(NEEDLE_ZOMBIE, NEEDLE_ZOMBIE_INTERNALNAME);
        // LanguageRegistry.addName(NEEDLE_ZOMBIE, NEEDLE_ZOMBIE_DISPLAYNAME);
    }
}

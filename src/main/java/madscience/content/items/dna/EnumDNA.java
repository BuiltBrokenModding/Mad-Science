package madscience.content.items.dna;

import com.builtbroken.mc.lib.helper.ReflectionUtility;
import madscience.MadScience;
import madscience.content.abomination.EntityAbomination;
import madscience.util.MadColors;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.lang.reflect.Field;

/**
 * Created by robert on 2/6/2015.
 */
public enum EnumDNA
{
    //TODO replace class references with registry names and alternate registry names
    //TODO this way we avoid mod conflict if a mod overrides the base mobs


    BAT("Bat", EntityBat.class, 4996656, 986895),
    CAVE_SPIDER("CaveSpider", EntityCaveSpider.class, 803406, 11013646),
    CHICKEN("Chicken", EntityChicken.class, 10592673, 16711680),
    COW("Cow", EntityCow.class, 4470310, 10592673),
    CREEPER("Creeper", EntityCreeper.class, 894731, 0),
    ENDERMAN("Enderman", EntityEnderman.class, 1447446, 0),
    GHAST("Ghast", EntityGhast.class, 16382457, 12369084),
    HORSE("Horse", EntityHorse.class, 12623485, 15656192),
    DONKEY("Donkey", EntityHorse.class, 12623485, 15656192),
    MUSHROOM_COW("MushroomCow", EntityMooshroom.class, 10489616, 12040119),
    OCELOT("Ocelot", EntityOcelot.class, 15720061, 5653556),
    PIG("Pig", EntityPig.class, 15771042, 14377823),
    SHEEP("Sheep", EntitySheep.class, 15198183, 16758197),
    SKELETON("Skeleton", EntitySkeleton.class, 12698049, 4802889),
    SLIME("Slime", EntitySlime.class, 5349438, 8306542),
    SPIDER("Spider", EntitySpider.class, 3419431, 11013646),
    SQUID("Squid", EntitySquid.class, 2243405, 7375001),
    VILLAGER("Villager", EntityVillager.class, 5651507, 12422002),
    WITCH("Witch", EntityWitch.class, 3407872, 5349438),
    WOLF("Wolf", EntityWolf.class, 14144467, 13545366),
    ZOMBIE("Zombie", EntityPigZombie.class, 44975, 7969893),
    // Werewolf [Wolf + Villager]
    //TODO WEREWOLF("Werewolf", WerewolfMobEntity.class, MadColors.villagerEgg(), MadColors.wolfEgg()),
    // Creeper Cow [Cow + Creeper]
    //TODO CREEPER_COW("CreeperCow", CreeperCowMobEntity.class, MadColors.creeperEgg(), MadColors.cowEgg()),
    // Enderslime [Enderman + Slime]
    //TODO ENDERSLIME("Enderslime", EnderslimeMobEntity.class, MadColors.endermanEgg(), MadColors.slimeEgg()),
    // Wooly Cow [Cow + Sheep] Bart74(bart.74@hotmail.fr)
    //TODO WOOLY_COW("WoolyCow", WoolyCowMobEntity.class, MadColors.cowEgg(), MadColors.sheepEgg()),
    // Shoggoth [Slime + Squid] Deuce_Loosely(captainlunautic@yahoo.com)
    //TODO SHOGGOTH("Shoggoth", ShoggothMobEntity.class, MadColors.slimeEgg(), MadColors.squidEgg()),
    // The Abomination [Enderman + Spider] monodemono(coolplanet3000@gmail.com)
    ABOMINATION("Abomination", EntityAbomination.class, MadColors.endermanEgg(), MadColors.spiderEgg()),
    // Wither Skeleton [Enderman + Skeleton] Pyrobrine(haskar.spore@gmail.com)
    WITHER_SKELETON("WitherSkeleton", EntitySkeleton.class, MadColors.endermanEgg(), MadColors.skeletonEgg()),
    // Villager Zombie [Villager + Zombie] Pyrobrine(haskar.spore@gmail.com)
    VILLAGER_ZOMBIE("VillagerZombie", EntityZombie.class, MadColors.villagerEgg(), MadColors.zombieEgg()),
    // Skeleton Horse [Horse + Skeleton] Pyrobrine(haskar.spore@gmail.com)
    SKELETON_HORSE("SkeletonHorse", EntityHorse.class, MadColors.horseEgg(), MadColors.skeletonEgg()),
    // Zombie Horse [Zombie + Horse] Pyrobrine(haskar.spore@gmail.com)
    ZOMBIE_HORSE("ZombieHorse", EntityHorse.class, MadColors.horseEgg(), MadColors.zombieEgg());
    // Ender Squid [Enderman + Squid] TheTechnician(tallahlf@gmail.com)
    //TODO ENDER_SQUID("EnderSquid", EnderSquidMobEntity.class, MadColors.endermanEgg(), MadColors.squidEgg());

    private final String INTERNAL_NAME;
    private final Class<? extends Entity> ENTITY_CLASS;
    public final int primary_color;
    public final int secondary_color;

    //TO MODDERS: Feel free to change these but remember to change the recipes with it
    public ItemStack spawnItem;
    public ItemStack needleItem;
    public ItemStack dnaItem;
    public ItemStack gnomeItem;

    EnumDNA(String langName, Class<? extends Entity> entity, int p, int s)
    {
        this.INTERNAL_NAME = langName;
        this.ENTITY_CLASS = entity;
        this.primary_color = p;
        this.secondary_color = s;
    }

    public String dnaString()
    {
        return "dna" + INTERNAL_NAME;
    }

    public String genomeString()
    {
        return "genome" + INTERNAL_NAME;
    }

    public String needleString()
    {
        return "needle" + INTERNAL_NAME;
    }

    public static void registerRecipes()
    {
        for (EnumDNA dna : values())
        {
            //TODO IncubatorRecipes.addSmelting(dna.getGnomeItem(), dna.getSpawnItem());
            //TODO SequencerRecipes.addSmelting(dna.getGnomeItem(), dna.getGnomeItem(), 0.01F);
            //TODO SequencerRecipes.addSmelting(dna.getDnaItem(), dna.getGnomeItem(), 0.05F);
        }
    }

    public static ItemStack getNeedleFor(Entity entity)
    {
        if (entity instanceof EntityPlayer)
        {
            //TODO make return player DNA when we add genetics.
            return new ItemStack(MadScience.itemNeedle, 1, VILLAGER.ordinal() + 2);
        }
        else if (entity instanceof EntitySkeleton)
        {
            if (((EntitySkeleton) entity).getSkeletonType() == 1)
            {
                return new ItemStack(MadScience.itemNeedle, 1, WITHER_SKELETON.ordinal() + 2);
            }
            else
            {
                return new ItemStack(MadScience.itemNeedle, 1, SKELETON.ordinal() + 2);
            }
        }
        else if (entity instanceof EntityZombie)
        {
            if (((EntityZombie) entity).isVillager())
            {
                return new ItemStack(MadScience.itemNeedle, 1, VILLAGER_ZOMBIE.ordinal() + 2);
            }
            else
            {
                return new ItemStack(MadScience.itemNeedle, 1, ZOMBIE.ordinal() + 2);
            }
        }
        else if (entity instanceof EntityHorse)
        {
            if (((EntityHorse) entity).getHorseType() == 3)
            {
                return new ItemStack(MadScience.itemNeedle, 1, ZOMBIE_HORSE.ordinal() + 2);
            }
            else if (((EntityHorse) entity).getHorseType() == 4)
            {
                return new ItemStack(MadScience.itemNeedle, 1, SKELETON_HORSE.ordinal() + 2);
            }
            else if (((EntityHorse) entity).getHorseType() == 0)
            {
                return new ItemStack(MadScience.itemNeedle, 1, HORSE.ordinal() + 2);
            }
            else
            {
                return new ItemStack(MadScience.itemNeedle, 1, DONKEY.ordinal() + 2);
            }
        }
        for (EnumDNA dna : values())
        {
            if (entity.getClass() == dna.ENTITY_CLASS)
            {
                //Needle is offset by 2 for dirty and empty needle
                return new ItemStack(MadScience.itemNeedle, 1, dna.ordinal() + 2);
            }
        }
        return null;
    }

    public ItemStack getNeedleItem()
    {
        if (needleItem == null)
        {
            needleItem = new ItemStack(MadScience.itemNeedle, 1, ordinal() + 2);
        }
        return needleItem;
    }

    public ItemStack getDnaItem()
    {
        if (dnaItem == null)
        {
            dnaItem = new ItemStack(MadScience.itemDNA, 1, ordinal());
        }
        return dnaItem;
    }

    public ItemStack getGnomeItem()
    {
        if (gnomeItem == null)
        {
            gnomeItem = new ItemStack(MadScience.itemGnome, 1, ordinal() + 1);
        }
        return gnomeItem;
    }

    public ItemStack getSpawnItem()
    {
        if (spawnItem == null)
        {
            Field field = ReflectionUtility.getMCField(EntityList.class, "classToIDMapping", "field_75624_e");
            if (field != null)
            {
                try
                {
                    int id = field.getInt(null);
                    if (EntityList.entityEggs.containsKey(id))
                    {
                        spawnItem = new ItemStack(Items.spawn_egg, 1, ((EntityList.EntityEggInfo) EntityList.entityEggs.get(id)).spawnedID);
                    }
                } catch (IllegalAccessException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return spawnItem.copy();
    }
}

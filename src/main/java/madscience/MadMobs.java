package madscience;

import madscience.items.ItemGenome;
import madscience.items.MadGenomeInfo;
import madscience.items.MadSpawnEggInfo;
import madscience.items.genomes.GenomePigZombie;
import madscience.mobs.werewolf.WerewolfMobModel;
import madscience.mobs.werewolf.WerewolfMobRender;
import madscience.tileentities.incubator.IncubatorRecipes;
import madscience.tileentities.mainframe.MainframeRecipes;
import madscience.tileentities.meatcube.MeatcubeBlock;
import madscience.tileentities.meatcube.MeatcubeEntity;
import madscience.tileentities.meatcube.MeatcubeRender;
import madscience.tileentities.sequencer.SequencerRecipes;
import net.minecraft.block.BlockContainer;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class MadMobs
{
    // -------------------------
    // GENETICALLY MODIFIED MOBS
    // -------------------------

    // Werewolf [Wolf + Villager]
    public static final String GMO_WEREWOLF_DISPLAYNAME = "Werewolf";
    public static final String GMO_WEREWOLF_INTERNALNAME = "gmoWerewolf";
    public static final String GENOME_WEREWOLF_INTERNALNAME = "genomeWerewolf";
    
    // Creeper Cow [Cow + Creeper]
    public static final String GMO_CREEPERCOW_DISPLAYNAME = "Creeper Cow";
    public static final String GMO_CREEPERCOW_INTERNALNAME = "gmoCreeperCow";
    public static final String GENOME_CREEPERCOW_INTERNALNAME = "genomeCreeperCow";

    // --------------------------
    // Bart74(bart.74@hotmail.fr)
    // --------------------------

    // Wooly Cow [Cow + Sheep]
    public static final String GMO_WOOLYCOW_DISPLAYNAME = "Wooly Cow";
    public static final String GMO_WOOLYCOW_INTERNALNAME = "gmoWoolyCow";
    public static final String GENOME_WOOLYCOW_INTERNALNAME = "genomeWoolyCow";

    // ----------------------------------------
    // Deuce_Loosely(captainlunautic@yahoo.com)
    // ----------------------------------------

    // Shoggoth [Slime + Squid]
    public static final String GMO_SHOGGOTH_DISPLAYNAME = "Shoggoth";
    public static final String GMO_SHOGGOTH_INTERNALNAME = "gmoShoggoth";
    public static final String GENOME_SHOGGOTH_INTERNALNAME = "genomeShoggoth";

    // ------------------------------------
    // monodemono(coolplanet3000@gmail.com)
    // ------------------------------------

    // The Abomination [Enderman + Spider]
    public static final String GMO_ABOMINATION_DISPLAYNAME = "The Abomination";
    public static final String GMO_ABOMINATION_INTERNALNAME = "gmoAbomination";
    public static final String GENOME_ABOMINATION_INTERNALNAME = "genomeAbomination";

    // ---------------------------------
    // Pyrobrine(haskar.spore@gmail.com)
    // ---------------------------------

    // Wither Skeleton [Enderman + Skeleton]
    public static final String GMO_WITHERSKELETON_DISPLAYNAME = "Wither Skeleton";
    public static final String GENOME_WITHERSKELETON_INTERNALNAME = "genomeWitherSkeleton";

    // Villager Zombie [Villager + Zombie]
    public static final String GMO_VILLAGERZOMBIE_DISPLAYNAME = "Villager Zombie";
    public static final String GENOME_VILLAGERZOMBIE_INTERNALNAME = "genomeVillagerZombie";

    // Skeleton Horse [Horse + Skeleton]
    public static final String GMO_SKELETONHORSE_DISPLAYNAME = "Skeleton Horse";
    public static final String GENOME_SKELETONHORSE_INTERNALNAME = "genomeSkeletonHorse";

    // Zombie Horse [Zombie + Horse]
    public static final String GMO_ZOMBIEHORSE_DISPLAYNAME = "Zombie Horse";
    public static final String GENOME_ZOMBIEHORSE_INTERNALNAME = "genomeZombieHorse";

    // ---------------------------------
    // TheTechnician(tallahlf@gmail.com)
    // ---------------------------------

    // Ender Squid [Enderman + Squid]
    public static final String GMO_ENDERSQUID_DISPLAYNAME = "Ender Squid";
    public static final String GMO_ENDERSQUID_INTERNALNAME = "gmoEnderSquid";
    public static final String GENOME_ENDERSQUID_INTERNALNAME = "genomeEnderSquid";
    
    @EventHandler
    public static void createGMOMob(int metaID, Class mobEntity, NBTTagCompound spawnData, String eggInternalName, String genomeInternalName, String eggDisplayName, int primaryColor, int secondaryColor, ItemGenome primaryGenome, ItemGenome secondaryGenome, int mainframeComputeTime)
    {        
        // Add mob to genetically modified mob list so it can be spawned.
        GMORegistry.registerSpawnEgg(new MadSpawnEggInfo((short) metaID, eggInternalName, eggDisplayName, spawnData, primaryColor, secondaryColor));
        LanguageRegistry.instance().addStringLocalization("entity." + eggInternalName + ".name", eggDisplayName);

        // Add mob to combined genome entity list so it can be created.
        GenomeRegistry.registerGenome(new MadGenomeInfo((short) metaID, genomeInternalName, eggDisplayName, primaryColor, secondaryColor));
        LanguageRegistry.instance().addStringLocalization("entity." + genomeInternalName + ".name", eggDisplayName);

        // Get a free and unique entity ID for our mob.
        int freeMobID = EntityRegistry.findGlobalUniqueEntityId();
        EntityRegistry.registerGlobalEntityID(mobEntity, eggInternalName, freeMobID);
        EntityRegistry.registerModEntity(mobEntity, eggInternalName, freeMobID, MadScience.instance, 42, 3, true);

        // Register our rendering handles on clients and ignore them on servers.
        MadScience.proxy.registerRenderingHandler(metaID);

        // Recipes for creating this custom mob.
        MainframeRecipes.addRecipe(new ItemStack(primaryGenome), new ItemStack(secondaryGenome), new ItemStack(MadEntities.COMBINEDGENOME_MONSTERPLACER, 1, metaID), mainframeComputeTime);
        IncubatorRecipes.addSmelting(MadEntities.COMBINEDGENOME_MONSTERPLACER.itemID, metaID, new ItemStack(MadEntities.GENETICALLYMODIFIED_MONSTERPLACER, 1, metaID));
    }
    
    @EventHandler
    public static void createVanillaGMOMob(int metaID, NBTTagCompound spawnData, String eggInternalName, String genomeInternalName, String eggDisplayName, int primaryColor, int secondaryColor, ItemGenome primaryGenome, ItemGenome secondaryGenome, int mainframeComputeTime)
    {
        GMORegistry.registerSpawnEgg(new MadSpawnEggInfo((short) metaID, eggInternalName, eggDisplayName, spawnData, primaryColor, secondaryColor));
        LanguageRegistry.instance().addStringLocalization("entity." + eggInternalName + ".name", eggDisplayName);

        // Add mob to combined genome entity list so it can be created.
        GenomeRegistry.registerGenome(new MadGenomeInfo((short) metaID, genomeInternalName, eggDisplayName, primaryColor, secondaryColor));
        LanguageRegistry.instance().addStringLocalization("entity." + genomeInternalName + ".name", eggDisplayName);

        // Recipes for creating this vanilla mob.
        MainframeRecipes.addRecipe(new ItemStack(primaryGenome), new ItemStack(secondaryGenome), new ItemStack(MadEntities.COMBINEDGENOME_MONSTERPLACER, 1, metaID), mainframeComputeTime);
        IncubatorRecipes.addSmelting(MadEntities.COMBINEDGENOME_MONSTERPLACER.itemID, metaID, new ItemStack(MadEntities.GENETICALLYMODIFIED_MONSTERPLACER, 1, metaID));
    }
}

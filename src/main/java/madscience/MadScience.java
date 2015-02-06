package madscience;

import com.builtbroken.mc.lib.mod.AbstractMod;
import com.builtbroken.mc.lib.mod.AbstractProxy;
import com.builtbroken.mc.lib.mod.ModCreativeTab;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLFingerprintViolationEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import madscience.gui.MadGUI;
import madscience.items.ItemCircuits;
import madscience.items.ItemComponents;
import madscience.items.dna.ItemDNASample;
import madscience.items.dna.ItemGenome;
import madscience.items.weapons.ItemWeaponPart;
import madscience.items.dna.ItemNeedle;
import madscience.mobs.abomination.AbominationMobEntity;
import madscience.mobs.abomination.AbominationMobLivingHandler;
import madscience.mobs.creepercow.CreeperCowMobEntity;
import madscience.mobs.enderslime.EnderslimeMobEntity;
import madscience.mobs.endersquid.EnderSquidMobEntity;
import madscience.mobs.shoggoth.ShoggothMobEntity;
import madscience.mobs.werewolf.WerewolfMobEntity;
import madscience.mobs.woolycow.WoolyCowMobEntity;
import madscience.server.CommonProxy;
import madscience.util.MadColors;
import madscience.util.MadTags;
import net.minecraft.entity.EntityList;
import net.minecraft.item.Item;
import net.minecraft.launchwrapper.LogWrapper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

@Mod(modid = MadScience.ID, name = MadScience.NAME, version = MadScience.VERSION_FULL, useMetadata = false, acceptedMinecraftVersions = "[1.6.4,)", dependencies = "required-after:Forge@[9.11.1.953,);after:BuildCraft|Energy;after:factorization;after:IC2;after:Railcraft;after:ThermalExpansion")
public class MadScience extends AbstractMod
{
    // Used in Forge mod identification below.
    public static final String ID = "madscience";
    public static final String CHANNEL_NAME = ID;
    public static final String NAME = "Mad Science";

    // Version identification.
    public static final String V_MAJOR = "@MAJOR@";
    public static final String V_MINOR = "@MINOR@";
    public static final String V_REVIS = "@REVIS@";
    public static final String V_BUILD = "@BUILD@";
    public static final String VERSION_FULL = V_MAJOR + "." + V_MINOR + V_REVIS + "." + V_BUILD;

    // Directories definition for assets and localization files.
    public static final String RESOURCE_DIRECTORY = "/assets/" + ID + "/";
    public static final String LANGUAGE_DIRECTORY = RESOURCE_DIRECTORY + "languages/";
    public static final String BASE_DIRECTORY_NO_SLASH = ID + "/";
    public static final String BASE_DIRECTORY = "/" + BASE_DIRECTORY_NO_SLASH;
    public static final String ASSET_DIRECTORY = "/assets/" + ID + "/";
    public static final String TEXTURE_DIRECTORY = "textures/";
    public static final String GUI_TEXTURE_DIRECTORY = TEXTURE_DIRECTORY + "gui/";
    public static final String BLOCK_TEXTURE_DIRECTORY = TEXTURE_DIRECTORY + "blocks/";
    public static final String ITEM_TEXTURE_DIRECTORY = TEXTURE_DIRECTORY + "items/";
    public static final String MODEL_DIRECTORY = "models/";
    public static final String MODEL_PATH = ASSET_DIRECTORY + MODEL_DIRECTORY;

    // Gradle imprints MD5 of source code into this file upon compilation for integrity check.
    public static final String FINGERPRINT = "@FINGERPRINT@";

    // Excellent reference to how many ticks make up a second.
    public static final int SECOND_IN_TICKS = 20;

    // Hook Forge's standardized logging class so we can report data on the console without standard out.
    public static Logger logger;

    // Proxy that runs commands based on where they are from so we can separate server and client logic easily.
    @SidedProxy(clientSide = "madscience.client.ClientProxy", serverSide = "madscience.server.CommonProxy")
    public static CommonProxy proxy;

    // Public instance of our mod that Forge needs to hook us, based on our internal modid.
    @Instance(MadScience.ID)
    public static MadScience instance;

    // Public extra data about our mod that Forge uses in the mods listing page for more information.
    @Mod.Metadata(MadScience.ID)
    public static ModMetadata metadata;

    // Hooks Forge's replacement openGUI function so we can route our block ID's to proper interfaces.
    public static MadGUI guiHandler = new MadGUI();

    public static ModCreativeTab creativeTab;

    public static Item itemCircuits;
    public static Item itemComponents;
    public static Item itemWeaponParts;
    public static Item itemNeedle;
    public static Item itemGnome;
    public static Item itemDNA;

    public MadScience()
    {
        super(ID);
    }

    /**
     * @param event
     */
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        super.postInit(event);
        // Interface with NEI and attempt to call functions from it if it exists.
        // Note: This method was given by Alex_hawks, buy him a beer if you see him!
        if (Loader.isModLoaded("NotEnoughItems"))
        {
            try
            {
                Class clazz = Class.forName("codechicken.nei.api.API");
                Method m = clazz.getMethod("hideItem", Integer.TYPE);

                // Cryotube Ghost Block.
                m.invoke(null, MadFurnaces.CRYOTUBEGHOST);

                // Soniclocator Ghost Block.
                m.invoke(null, MadFurnaces.SONICLOCATORGHOST);

                // Magazine Loader Ghost Block.
                m.invoke(null, MadFurnaces.MAGLOADERGHOST);

                // CnC Machine Ghost Block.
                m.invoke(null, MadFurnaces.CNCMACHINEGHOST_TILEENTITY);
            } catch (Throwable e)
            {
                logger.log(Level.WARNING, "NEI Integration has failed...");
                logger.log(Level.WARNING, "Please email devs@madsciencemod.com the following stacktrace.");
                e.printStackTrace();
                logger.log(Level.WARNING, "Spamming console to make more obvious...");
                for (int i = 0; i < 15; i++)
                {
                    logger.log(Level.WARNING, "Something Broke. See above.");
                }
            }

        }
    }

    @Override
    public AbstractProxy getProxy()
    {
        return proxy;
    }

    /**
     * @param event
     */
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        super.init(event);

        // Check Mad Science Jenkins build server for latest build numbers to compare with running one.
        MadUpdates.checkJenkinsBuildNumbers();
    }



    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        super.preInit(event);
        creativeTab = new ModCreativeTab("tabMadScience");
        getManager().setTab(creativeTab);

        // Setup Mod Metadata for players to see in mod list with other mods.
        metadata.modId = MadScience.ID;
        metadata.name = MadScience.NAME;
        metadata.description = "Adds machines, items and mobs to create your own laboratory! Remember kids, science has no limits.. no bounds..";
        metadata.url = "http://madsciencemod.com/";
        metadata.logoFile = "assets/madscience/logo.png";
        metadata.version = V_MAJOR + "." + V_MINOR + V_REVIS;
        metadata.authorList = Arrays.asList(new String[] {"Maxwolf Goodliffe", "Fox Diller"});
        metadata.credits = "Thanks to Prowler for the awesome assets!";
        metadata.autogenerated = false;

        // ------
        // FLUIDS
        // ------
        logger.info("Creating Fluids");

        // Creates both flowing and still variants of new fluid.
        // Note: Still's ID must be 1 above Flowing.
        MadFluids.createLiquidDNA(MadConfig.LIQUIDDNA, MadConfig.LIQUIDDNA_BUCKET);

        // Liquid Mutant DNA.
        MadFluids.createLiquidDNAMutant(MadConfig.LIQUIDDNA_MUTANT, MadConfig.LIQUIDDNA_MUTANT_BUCKET);

        // ------
        // BLOCKS
        // ------
        logger.info("Creating Blocks");

        // Abomination Egg
        MadBlocks.createAbominationEgg(MadConfig.ABOMINATIONEGG);

        // Enderslime Block
        MadBlocks.createEnderslimeBlock(MadConfig.ENDERSLIMEBLOCK);

        itemCircuits = getManager().newItem(ItemCircuits.class);
        itemComponents = getManager().newItem(ItemComponents.class);
        itemWeaponParts = getManager().newItem(ItemWeaponPart.class);
        itemNeedle = getManager().newItem(ItemNeedle.class);
        itemGnome = getManager().newItem(ItemGenome.class);
        itemDNA = getManager().newItem(ItemDNASample.class);

        // -------
        // WEAPONS
        // -------

        MadWeapons.createPulseRifle(MadConfig.WEAPON_PULSERIFLE);
        MadWeapons.createPulseRifleBullet(MadConfig.WEAPON_PULSERIFLE_BULLETITEM);
        MadWeapons.createPulseRifleGrenade(MadConfig.WEAPON_PULSERIFLE_GRENADEITEM);
        MadWeapons.createPulseRifleMagazine(MadConfig.WEAPON_PULSERIFLE_MAGAZINEITEM);

        // -------------
        // TILE ENTITIES
        // -------------
        logger.info("Creating Tile Entities");

        MadFurnaces.createDNAExtractorTileEntity(MadConfig.DNA_EXTRACTOR);
        MadFurnaces.createSanitizerTileEntity(MadConfig.SANTITIZER);
        MadFurnaces.createMainframeTileEntity(MadConfig.MAINFRAME);
        MadFurnaces.createGeneSequencerTileEntity(MadConfig.GENE_SEQUENCER);
        MadFurnaces.createCryoFreezerTileEntity(MadConfig.CRYOFREEZER);
        MadFurnaces.createGeneIncubatorTileEntity(MadConfig.INCUBATOR);
        MadFurnaces.createCryotubeTileEntity(MadConfig.CRYOTUBE);
        MadFurnaces.createCryotubeGhostTileEntity(MadConfig.CRYOTUBEGHOST);
        MadFurnaces.createThermosonicBonderTileEntity(MadConfig.THERMOSONIC);
        MadFurnaces.createDataReelDuplicatorTileEntity(MadConfig.DATADUPLICATOR);
        MadFurnaces.createSoniclocatorTileEntity(MadConfig.SONICLOCATOR);
        MadFurnaces.createSoniclocatorGhostTileEntity(MadConfig.SONICLOCATOREGHOST);
        MadFurnaces.createClayFurnaceTileEntity(MadConfig.CLAYFURNACE);
        MadFurnaces.createVOXBoxTileEntity(MadConfig.VOXBOX);
        MadFurnaces.createMagLoaderTileEntity(MadConfig.MAGLOADER);
        MadFurnaces.createMagLoaderGhostTileEntity(MadConfig.MAGLOADERGHOST);
        MadFurnaces.createCnCMachineTileEntity(MadConfig.CNCMACHINE);
        MadFurnaces.createCnCMachineGhostTileEntity(MadConfig.CNCMACHINEGHOST);

        // -----
        // ARMOR
        // -----

        // Note: Armor follows an array 0,1,2,3 for helmet, body, leggings, boots.
        MadEntities.createLabCoatGoggles(MadConfig.LABCOAT_GOGGLES, 0);
        MadEntities.createLabCoatBody(MadConfig.LABCOAT_BODY, 1);
        MadEntities.createLabCoatLeggings(MadConfig.LABCOAT_LEGGINGS, 2);

        // -----
        // ITEMS
        // -----

        MadEntities.createWarningSign(MadConfig.WARNING_SIGN);

        // --------------------
        // MONSTER PLACER ITEMS
        // --------------------
        logger.info("Creating Monster Placer Items");

        MadEntities.createGeneticallyModifiedMonsterPlacer(MadConfig.GENETICALLYMODIFIED_MONSTERPLACER);
        MadEntities.createCombinedGenomeMonsterPlacer(MadConfig.COMBINEDGENOME_MONSTERPLACER);
        MadEntities.createCombinedMemoryMonsterPlacer(MadConfig.COMBINEDMEMORY_MONSTERPLACER);

        // -------
        // RECIPES
        // -------
        logger.info("Creating Recipes");

        MadRecipes.createCircuitRecipes();
        MadRecipes.createComponentsRecipes();
        MadRecipes.createWeaponRecipes();
        MadRecipes.createOtherRecipes();

        // -------------------------
        // GENETICALLY MODIFIED MOBS
        // -------------------------
        logger.info("Creating Genetically Modified Creatures");

        // Werewolf [Villager + Wolf]
        MadMobs.createGMOMob(MadConfig.GMO_WEREWOLF_METAID, WerewolfMobEntity.class, new NBTTagCompound(), MadMobs.GMO_WEREWOLF_INTERNALNAME, MadMobs.GENOME_WEREWOLF_INTERNALNAME, MadColors.villagerEgg(), MadColors.wolfEgg(), MadGenomes.GENOME_VILLAGER,
                MadGenomes.GENOME_WOLF, MadConfig.GMO_WEREWOLF_COOKTIME);

        // Disgusting Meatcube [Slime + Cow,Pig,Chicken]
        MadFurnaces.createMeatcubeTileEntity(MadConfig.MEATCUBE, MadConfig.GMO_MEATCUBE_METAID, MadColors.slimeEgg(), MadColors.pigEgg(), MadConfig.GMO_MEATCUBE_COOKTIME);

        // Creeper Cow [Creeper + Cow]
        MadMobs.createGMOMob(MadConfig.GMO_CREEPERCOW_METAID, CreeperCowMobEntity.class, new NBTTagCompound(), MadMobs.GMO_CREEPERCOW_INTERNALNAME, MadMobs.GENOME_CREEPERCOW_INTERNALNAME, MadColors.creeperEgg(), MadColors.cowEgg(),
                MadGenomes.GENOME_CREEPER, MadGenomes.GENOME_COW, MadConfig.GMO_CREEPERCOW_COOKTIME);

        // Enderslime [Enderman + Slime]
        MadMobs.createGMOMob(MadConfig.GMO_ENDERSLIME_METAID, EnderslimeMobEntity.class, new NBTTagCompound(), MadMobs.GMO_ENDERSLIME_INTERNALNAME, MadMobs.GENOME_ENDERSLIME_INTERNALNAME, MadColors.endermanEgg(), MadColors.slimeEgg(),
                MadGenomes.GENOME_ENDERMAN, MadGenomes.GENOME_SLIME, MadConfig.GMO_ENDERSLIME_COOKTIME);

        // --------------------------
        // Bart74(bart.74@hotmail.fr)
        // --------------------------

        // Wooly cow [Cow + Sheep]
        MadMobs.createGMOMob(MadConfig.GMO_WOOLYCOW_METAID, WoolyCowMobEntity.class, new NBTTagCompound(), MadMobs.GMO_WOOLYCOW_INTERNALNAME, MadMobs.GENOME_WOOLYCOW_INTERNALNAME, MadColors.cowEgg(), MadColors.sheepEgg(), MadGenomes.GENOME_COW,
                MadGenomes.GENOME_SHEEP, MadConfig.GMO_WOOLYCOW_COOKTIME);

        // ----------------------------------------
        // Deuce_Loosely(captainlunautic@yahoo.com)
        // ----------------------------------------

        // Shoggoth [Slime + Squid]
        MadMobs.createGMOMob(MadConfig.GMO_SHOGGOTH_METAID, ShoggothMobEntity.class, new NBTTagCompound(), MadMobs.GMO_SHOGGOTH_INTERNALNAME, MadMobs.GENOME_SHOGGOTH_INTERNALNAME, MadColors.slimeEgg(), MadColors.squidEgg(), MadGenomes.GENOME_SLIME,
                MadGenomes.GENOME_SQUID, MadConfig.GMO_SHOGGOTH_COOKTIME);

        // ------------------------------------
        // monodemono(coolplanet3000@gmail.com)
        // ------------------------------------

        // The Abomination [Enderman + Spider]
        MadMobs.createGMOMob(MadConfig.GMO_ABOMINATION_METAID, AbominationMobEntity.class, new NBTTagCompound(), MadMobs.GMO_ABOMINATION_INTERNALNAME, MadMobs.GENOME_ABOMINATION_INTERNALNAME, MadColors.endermanEgg(), MadColors.spiderEgg(),
                MadGenomes.GENOME_ENDERMAN, MadGenomes.GENOME_SPIDER, MadConfig.GMO_ABOMINATION_COOKTIME);

        // Add Forge hook for Abomination so we can know when it kills another mob so we can lay an egg.
        MinecraftForge.EVENT_BUS.register(new AbominationMobLivingHandler());

        // ---------------------------------
        // Pyrobrine(haskar.spore@gmail.com)
        // ---------------------------------

        // Wither Skeleton [Enderman + Skeleton]
        MadMobs.createVanillaGMOMob(MadConfig.GMO_WITHERSKELETON_METAID, MadTags.witherSkeleton(), EntityList.getStringFromID(51), MadMobs.GENOME_WITHERSKELETON_INTERNALNAME, MadColors.endermanEgg(), MadColors.skeletonEgg(), MadGenomes.GENOME_ENDERMAN,
                MadGenomes.GENOME_SKELETON, MadConfig.GMO_WITHERSKELETON_COOKTIME);

        // Villager Zombie [Villager + Zombie]
        MadMobs.createVanillaGMOMob(MadConfig.GMO_VILLAGERZOMBIE_METAID, MadTags.villagerZombie(), EntityList.getStringFromID(54), MadMobs.GENOME_VILLAGERZOMBIE_INTERNALNAME, MadColors.villagerEgg(), MadColors.zombieEgg(), MadGenomes.GENOME_VILLAGER,
                MadGenomes.GENOME_ZOMBIE, MadConfig.GMO_VILLAGERZOMBIE_COOKTIME);

        // Skeleton Horse [Horse + Skeleton]
        MadMobs.createVanillaGMOMob(MadConfig.GMO_SKELETONHORSE_METAID, MadTags.horseType(4), EntityList.getStringFromID(100), MadMobs.GENOME_SKELETONHORSE_INTERNALNAME, MadColors.horseEgg(), MadColors.skeletonEgg(), MadGenomes.GENOME_HORSE,
                MadGenomes.GENOME_SKELETON, MadConfig.GMO_SKELETONHORSE_COOKTIME);

        // Zombie Horse [Zombie + Horse]
        MadMobs.createVanillaGMOMob(MadConfig.GMO_ZOMBIEHORSE_METAID, MadTags.horseType(3), EntityList.getStringFromID(100), MadMobs.GENOME_ZOMBIEHORSE_INTERNALNAME, MadColors.horseEgg(), MadColors.zombieEgg(), MadGenomes.GENOME_ZOMBIE,
                MadGenomes.GENOME_HORSE, MadConfig.GMO_ZOMBIEHORSE_COOKTIME);

        // ---------------------------------
        // TheTechnician(tallahlf@gmail.com)
        // ---------------------------------

        // Ender Squid [Enderman + Squid]
        MadMobs.createGMOMob(MadConfig.GMO_ENDERSQUID_METAID, EnderSquidMobEntity.class, new NBTTagCompound(), MadMobs.GMO_ENDERSQUID_INTERNALNAME, MadMobs.GENOME_ENDERSQUID_INTERNALNAME, MadColors.endermanEgg(), MadColors.squidEgg(),
                MadGenomes.GENOME_ENDERMAN, MadGenomes.GENOME_SQUID, MadConfig.GMO_ENDERSQUID_COOKTIME);

        // ---------
        // DONE INIT
        // ---------
        logger.info("Finished loading all madness!");
    }
}

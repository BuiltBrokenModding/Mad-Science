package madscience;

import java.awt.Color;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import madscience.mobs.abomination.AbominationMobEntity;
import madscience.mobs.abomination.AbominationMobLivingHandler;
import madscience.mobs.creepercow.CreeperCowMobEntity;
import madscience.mobs.endersquid.EnderSquidMobEntity;
import madscience.mobs.shoggoth.ShoggothMobEntity;
import madscience.mobs.werewolf.WerewolfMobEntity;
import madscience.mobs.woolycow.WoolyCowMobEntity;
import madscience.server.CommonProxy;
import madscience.server.ExampleServerCommand;
import madscience.network.MadGUI;
import madscience.network.MadPacketHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityList;
import net.minecraft.item.ItemStack;
import net.minecraft.launchwrapper.LogWrapper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLLog;
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
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = MadScience.ID, name = MadScience.NAME, version = MadScience.VERSION, useMetadata = false, acceptedMinecraftVersions = "[1.6.4,)", dependencies = "required-after:Forge@[9.11.1.953,);required-after:UniversalElectricity;after:BuildCraft|Energy;after:factorization;after:IC2;after:Railcraft;after:ThermalExpansion")
@NetworkMod(channels =
{ MadScience.CHANNEL_NAME }, packetHandler = MadPacketHandler.class, clientSideRequired = true, serverSideRequired = false)
public class MadScience
{
    // Forge identification.
    public static final String ID = "madscience";
    public static final String CHANNEL_NAME = ID;
    public static final String NAME = "Mad Science";
    public static final String VERSION = "@MAJOR@.@MINOR@.@REVIS@@BUILD@";
    
    // Directories definition
	public static final String RESOURCE_DIRECTORY = "/assets/" + ID + "/";
	public static final String LANGUAGE_DIRECTORY = RESOURCE_DIRECTORY + "languages/";

    // Protection from the unknown!
    public static final String FINGERPRINT = "@FINGERPRINT@";

    // Excellent reference to how many ticks make up a second.
    public static final int SECOND_IN_TICKS = 20;

    // Canadian ones too!
    public static Logger logger;

    @SidedProxy(clientSide = "madscience.client.ClientProxy", serverSide = "madscience.server.CommonProxy")
    public static CommonProxy proxy;

    @Instance(ID)
    public static MadScience instance;

    @Mod.Metadata(ID)
    public static ModMetadata metadata;
    
    public static MadGUI guiHandler = new MadGUI();

    private static Configuration config;

    /** @param event */
    @EventHandler
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void postInit(FMLPostInitializationEvent event)
    {
    	// Interface with NEI and attempt to call functions from it if it exists.
    	// Note: This method was given by Alex_hawks, buy him a beer if you see him!
        if (Loader.isModLoaded("NotEnoughItems"))
        {
            try
            {
                Class clazz = Class.forName("codechicken.nei.api.API");
                Method m = clazz.getMethod("hideItem", Integer.TYPE);

                m.invoke(null, MadFurnaces.CRYOTUBEGHOST.blockID);
            }
            catch (Throwable e)
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

    /** @param event */
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        // Register any custom sounds we want to play (Client only).
        proxy.registerSoundHandler();
    }

    @EventHandler
    public void invalidFingerprint(FMLFingerprintViolationEvent event)
    {

        // Report (log) to the user that the version of Mad Science
        // they are using has been changed/tampered with
        if (FINGERPRINT.equals("@FINGERPRINT@"))
        {
            LogWrapper.warning("The copy of Mad Science that you are running is a development version of the mod, and as such may be unstable and/or incomplete.");
        }
        else
        {
            LogWrapper.severe("The copy of Mad Science that you are running has been modified from the original, and unpredictable things may happen. Please consider re-downloading the original version of the mod.");
        }
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        // --------------
        // PRE-INT CONFIG
        // --------------

        // Logging.
        logger = event.getModLog();
        logger.setParent(FMLLog.getLogger());

        // Read our mod only config.
        config = new Configuration(event.getSuggestedConfigurationFile());
        MadConfig.load(config);

        // Setup Mod Metadata for players to see in mod list with other mods.
        metadata.modId = MadScience.ID;
        metadata.name = MadScience.NAME;
        metadata.description = "Adds machines, items and mobs to create your own laboratory! Remember kids, science has no limits.. no bounds..";
        metadata.url = "http://madsciencemod.com/";
        metadata.logoFile = "assets/madscience/logo.png";
        metadata.version = "@MAJOR@.@MINOR@";
        metadata.authorList = Arrays.asList(new String[]
        { "Maxwolf Goodliffe", "Fox Diller" });
        metadata.credits = "Thanks to Prowler for the awesome assets!";
        metadata.autogenerated = false;

        // Custom creative mode tab to organize our mod items and blocks.
        MadEntities.createCustomCreativeTab("tabMadScience", NAME);

        // ------
        // FLUIDS
        // ------

        // Creates both flowing and still variants of new fluid.
        // Note: Still's ID must be 1 above Flowing.
        MadFluids.createLiquidDNA(MadConfig.LIQUIDDNA, MadConfig.LIQUIDDNA_BUCKET);

        // Liquid Mutant DNA.
        MadFluids.createLiquidDNAMutant(MadConfig.LIQUIDDNA_MUTANT, MadConfig.LIQUIDDNA_MUTANT_BUCKET);
        
        // ------
        // BLOCKS
        // ------

        // Abomination Egg
        MadBlocks.createAbominationEgg(MadConfig.ABOMINATIONEGG);
        
        // -----
        // ITEMS
        // -----

        // Mainframe Components and Circuits
        MadComponents.createMainframeComponents(MadConfig.MAINFRAME_COMPONENTS);

        // Empty data reel that can be turned into memory or genome.
        MadEntities.createEmptyDataReel(MadConfig.DATAREEL_EMPTY);

        // -------
        // NEEDLES
        // -------

        MadNeedles.createEmptyNeedle(MadConfig.NEEDLE);
        MadNeedles.createDirtyNeedle(MadConfig.NEEDLE_DIRTY);
        MadNeedles.createCaveSpiderNeedle(MadConfig.NEEDLE_CAVESPIDER);
        MadNeedles.createChickenNeedle(MadConfig.NEEDLE_CHICKEN);
        MadNeedles.createCowNeedle(MadConfig.NEEDLE_COW);
        MadNeedles.createCreeperNeedle(MadConfig.NEEDLE_CREEPER);
        MadNeedles.createEndermanNeedle(MadConfig.NEEDLE_ENDERMAN);
        MadNeedles.createHorseNeedle(MadConfig.NEEDLE_HORSE);
        MadNeedles.createMushroomCowNeedle(MadConfig.NEEDLE_MUSHROOMCOW);
        MadNeedles.createOcelotNeedle(MadConfig.NEEDLE_OCELOT);
        MadNeedles.createPigNeedle(MadConfig.NEEDLE_PIG);
        MadNeedles.createSheepNeedle(MadConfig.NEEDLE_SHEEP);
        MadNeedles.createSpiderNeedle(MadConfig.NEEDLE_SPIDER);
        MadNeedles.createSquidNeedle(MadConfig.NEEDLE_SQUID);
        MadNeedles.createVillagerNeedle(MadConfig.NEEDLE_VILLAGER);
        MadNeedles.createWitchNeedle(MadConfig.NEEDLE_WITCH);
        MadNeedles.createWolfNeedle(MadConfig.NEEDLE_WOLF);
        MadNeedles.createZombieNeedle(MadConfig.NEEDLE_ZOMBIE);
        MadNeedles.createMutantNeedle(MadConfig.NEEDLE_MUTANT);
        MadNeedles.createBatNeedle(MadConfig.NEEDLE_BAT);

        // -----------
        // DNA SAMPLES
        // -----------

        MadDNA.createCaveSpiderDNA(MadConfig.DNA_CAVESPIDER);
        MadDNA.createChickenDNA(MadConfig.DNA_CHICKEN);
        MadDNA.createCowDNA(MadConfig.DNA_COW);
        MadDNA.createCreeperDNA(MadConfig.DNA_CREEPER);
        MadDNA.createEndermanDNA(MadConfig.DNA_ENDERMAN);
        MadDNA.createGhastDNA(MadConfig.DNA_GHAST);
        MadDNA.createHorseDNA(MadConfig.DNA_HORSE);
        MadDNA.createMushroomCowDNA(MadConfig.DNA_MUSHROOMCOW);
        MadDNA.createOcelotDNA(MadConfig.DNA_OCELOT);
        MadDNA.createPigDNA(MadConfig.DNA_PIG);
        MadDNA.createSheepDNA(MadConfig.DNA_SHEEP);
        MadDNA.createSkeletonDNA(MadConfig.DNA_SKELETON);
        MadDNA.createSpiderDNA(MadConfig.DNA_SPIDER);
        MadDNA.createSquidDNA(MadConfig.DNA_SQUID);
        MadDNA.createVillagerDNA(MadConfig.DNA_VILLAGER);
        MadDNA.createWitchDNA(MadConfig.DNA_WITCH);
        MadDNA.createWolfDNA(MadConfig.DNA_WOLF);
        MadDNA.createZombieDNA(MadConfig.DNA_ZOMBIE);
        MadDNA.createBatDNA(MadConfig.DNA_BAT);
        MadDNA.createSlimeDNA(MadConfig.DNA_SLIME);

        // -----------------
        // GENOME DATA REELS
        // -----------------

        MadGenomes.createCaveSpiderGenome(MadConfig.GENOME_CAVESPIDER);
        MadGenomes.createChickenGenome(MadConfig.GENOME_CHICKEN);
        MadGenomes.createCowGenome(MadConfig.GENOME_COW);
        MadGenomes.createCreeperGenome(MadConfig.GENOME_CREEPER);
        MadGenomes.createEndermanGenome(MadConfig.GENOME_ENDERMAN);
        MadGenomes.createGhastGenome(MadConfig.GENOME_GHAST);
        MadGenomes.createHorseGenome(MadConfig.GENOME_HORSE);
        MadGenomes.createMushroomCowGenome(MadConfig.GENOME_MUSHROOMCOW);
        MadGenomes.createOcelotGenome(MadConfig.GENOME_OCELOT);
        MadGenomes.createPigGenome(MadConfig.GENOME_PIG);
        MadGenomes.createSheepGenome(MadConfig.GENOME_SHEEP);
        MadGenomes.createSkeletonGenome(MadConfig.GENOME_SKELETON);
        MadGenomes.createSpiderGenome(MadConfig.GENOME_SPIDER);
        MadGenomes.createSquidGenome(MadConfig.GENOME_SQUID);
        MadGenomes.createVillagerGenome(MadConfig.GENOME_VILLAGER);
        MadGenomes.createWitchGenome(MadConfig.GENOME_WITCH);
        MadGenomes.createWolfGenome(MadConfig.GENOME_WOLF);
        MadGenomes.createZombieGenome(MadConfig.GENOME_ZOMBIE);
        MadGenomes.createBatGenome(MadConfig.GENOME_BAT);
        MadGenomes.createSlimeGenome(MadConfig.GENOME_SLIME);
        MadGenomes.createPigZombieGenome(MadConfig.GENOME_PIGZOMBIE);

        // -------------
        // TILE ENTITIES
        // -------------

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

        // --------------------
        // MONSTER PLACER ITEMS
        // --------------------

        MadEntities.createGeneticallyModifiedMonsterPlacer(MadConfig.GENETICALLYMODIFIED_MONSTERPLACER);
        MadEntities.createCombinedGenomeMonsterPlacer(MadConfig.COMBINEDGENOME_MONSTERPLACER);
        MadEntities.createCombinedMemoryMonsterPlacer(MadConfig.COMBINEDMEMORY_MONSTERPLACER);

        // -------------------------
        // GENETICALLY MODIFIED MOBS
        // -------------------------

        // Werewolf [Villager + Wolf]
        MadMobs.createGMOMob(MadConfig.GMO_WEREWOLF_METAID, WerewolfMobEntity.class, new NBTTagCompound(),
                MadMobs.GMO_WEREWOLF_INTERNALNAME, MadMobs.GENOME_WEREWOLF_INTERNALNAME, MadMobs.GMO_WEREWOLF_DISPLAYNAME,
                MadColors.villagerEgg(), MadColors.wolfEgg(), MadGenomes.GENOME_VILLAGER, MadGenomes.GENOME_WOLF, MadConfig.GMO_WEREWOLF_COOKTIME);

        // Disgusting Meatcube [Slime + Cow,Pig,Chicken]
        MadFurnaces.createMeatcubeTileEntity(MadConfig.MEATCUBE, MadConfig.GMO_MEATCUBE_METAID, MadColors.slimeEgg(), MadColors.pigEgg(), MadConfig.GMO_MEATCUBE_COOKTIME);
        
        // Creeper Cow [Creeper + Cow]
        MadMobs.createGMOMob(MadConfig.GMO_CREEPERCOW_METAID, CreeperCowMobEntity.class, new NBTTagCompound(),
                MadMobs.GMO_CREEPERCOW_INTERNALNAME, MadMobs.GENOME_CREEPERCOW_INTERNALNAME, MadMobs.GMO_CREEPERCOW_DISPLAYNAME,
                MadColors.creeperEgg(), MadColors.cowEgg(), MadGenomes.GENOME_CREEPER, MadGenomes.GENOME_COW, MadConfig.GMO_CREEPERCOW_COOKTIME);

        // --------------------------
        // Bart74(bart.74@hotmail.fr)
        // --------------------------

        // Wooly cow [Cow + Sheep]
        MadMobs.createGMOMob(MadConfig.GMO_WOOLYCOW_METAID, WoolyCowMobEntity.class, new NBTTagCompound(),
                MadMobs.GMO_WOOLYCOW_INTERNALNAME, MadMobs.GENOME_WOOLYCOW_INTERNALNAME, MadMobs.GMO_WOOLYCOW_DISPLAYNAME,
                MadColors.cowEgg(), MadColors.sheepEgg(), MadGenomes.GENOME_COW, MadGenomes.GENOME_SHEEP, MadConfig.GMO_WOOLYCOW_COOKTIME);

        // ----------------------------------------
        // Deuce_Loosely(captainlunautic@yahoo.com)
        // ----------------------------------------

        // Shoggoth [Slime + Squid]
        MadMobs.createGMOMob(MadConfig.GMO_SHOGGOTH_METAID, ShoggothMobEntity.class, new NBTTagCompound(),
                MadMobs.GMO_SHOGGOTH_INTERNALNAME, MadMobs.GENOME_SHOGGOTH_INTERNALNAME, MadMobs.GMO_SHOGGOTH_DISPLAYNAME,
                MadColors.slimeEgg(), MadColors.squidEgg(), MadGenomes.GENOME_SLIME, MadGenomes.GENOME_SQUID, MadConfig.GMO_SHOGGOTH_COOKTIME);

        // ------------------------------------
        // monodemono(coolplanet3000@gmail.com)
        // ------------------------------------

        // The Abomination [Enderman + Spider]
        MadMobs.createGMOMob(MadConfig.GMO_ABOMINATION_METAID, AbominationMobEntity.class, new NBTTagCompound(),
                MadMobs.GMO_ABOMINATION_INTERNALNAME, MadMobs.GENOME_ABOMINATION_INTERNALNAME, MadMobs.GMO_ABOMINATION_DISPLAYNAME,
                MadColors.endermanEgg(), MadColors.spiderEgg(), MadGenomes.GENOME_ENDERMAN, MadGenomes.GENOME_SPIDER, MadConfig.GMO_ABOMINATION_COOKTIME);
        
        // Add Forge hook for Abomination so we can know when it kills another mob so we can lay an egg.
        MinecraftForge.EVENT_BUS.register(new AbominationMobLivingHandler());

        // ---------------------------------
        // Pyrobrine(haskar.spore@gmail.com)
        // ---------------------------------

        // Wither Skeleton [Enderman + Skeleton]
        MadMobs.createVanillaGMOMob(MadConfig.GMO_WITHERSKELETON_METAID, MadTags.witherSkeleton(), 
                EntityList.getStringFromID(51), MadMobs.GENOME_WITHERSKELETON_INTERNALNAME, MadMobs.GMO_WITHERSKELETON_DISPLAYNAME,
                MadColors.endermanEgg(), MadColors.skeletonEgg(), MadGenomes.GENOME_ENDERMAN, MadGenomes.GENOME_SKELETON, MadConfig.GMO_WITHERSKELETON_COOKTIME);

        // Villager Zombie [Villager + Zombie]
        MadMobs.createVanillaGMOMob(MadConfig.GMO_VILLAGERZOMBIE_METAID, MadTags.villagerZombie(), 
                EntityList.getStringFromID(54), MadMobs.GENOME_VILLAGERZOMBIE_INTERNALNAME, MadMobs.GMO_VILLAGERZOMBIE_DISPLAYNAME,
                MadColors.villagerEgg(), MadColors.zombieEgg(), MadGenomes.GENOME_VILLAGER, MadGenomes.GENOME_ZOMBIE, MadConfig.GMO_VILLAGERZOMBIE_COOKTIME);

        // Skeleton Horse [Horse + Skeleton]
        MadMobs.createVanillaGMOMob(MadConfig.GMO_SKELETONHORSE_METAID, MadTags.horseType(4),
                EntityList.getStringFromID(100), MadMobs.GENOME_SKELETONHORSE_INTERNALNAME, MadMobs.GMO_SKELETONHORSE_DISPLAYNAME,
                MadColors.horseEgg(), MadColors.skeletonEgg(), MadGenomes.GENOME_HORSE, MadGenomes.GENOME_SKELETON, MadConfig.GMO_SKELETONHORSE_COOKTIME);

        // Zombie Horse [Zombie + Horse]
        MadMobs.createVanillaGMOMob(MadConfig.GMO_ZOMBIEHORSE_METAID, MadTags.horseType(3),
                EntityList.getStringFromID(100), MadMobs.GENOME_ZOMBIEHORSE_INTERNALNAME, MadMobs.GMO_ZOMBIEHORSE_DISPLAYNAME,
                MadColors.horseEgg(), MadColors.zombieEgg(), MadGenomes.GENOME_ZOMBIE, MadGenomes.GENOME_HORSE, MadConfig.GMO_ZOMBIEHORSE_COOKTIME);

        // ---------------------------------
        // TheTechnician(tallahlf@gmail.com)
        // ---------------------------------

        // Ender Squid [Enderman + Squid]
        MadMobs.createGMOMob(MadConfig.GMO_ENDERSQUID_METAID, EnderSquidMobEntity.class, new NBTTagCompound(),
                MadMobs.GMO_ENDERSQUID_INTERNALNAME, MadMobs.GENOME_ENDERSQUID_INTERNALNAME, MadMobs.GMO_ENDERSQUID_DISPLAYNAME,
                MadColors.endermanEgg(), MadColors.squidEgg(), MadGenomes.GENOME_ENDERMAN, MadGenomes.GENOME_SQUID, MadConfig.GMO_ENDERSQUID_COOKTIME);

        // ---------
        // DONE INIT
        // ---------
    }

    @EventHandler
    public void serverLoad(FMLServerStartingEvent event)
    {
        // Hook server starting up so we may add a command to it.
        // Note: Runs in single player also since minecraft is always running a
        // server.
        event.registerServerCommand(new ExampleServerCommand());
    }
}
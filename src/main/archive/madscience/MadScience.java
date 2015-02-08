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
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import madscience.tiles.BlockAbominationEgg;
import madscience.tiles.BlockEnderslime;
import madscience.tiles.BlockFluidDNA;
import madscience.gui.MadGUI;
import madscience.items.ItemCircuits;
import madscience.items.ItemComponents;
import madscience.items.dna.ItemDNASample;
import madscience.items.dna.ItemGenome;
import madscience.items.weapons.ItemWeaponPart;
import madscience.items.dna.ItemNeedle;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

@Mod(modid = MadScience.ID, name = MadScience.NAME, version = MadScience.VERSION_FULL, useMetadata = false, acceptedMinecraftVersions = "[1.7.10,)", dependencies = "after:VoltzEngine")
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
    @SidedProxy(clientSide = "madscience.ClientProxy", serverSide = "madscience.CommonProxy")
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

    public static Block blockDNA;
    public static Block blockMutantDNA;
    public static Block blockAbominationEgg;
    public static Block blockEnderslime;

    public static String liquidDNA_name = "maddna";
    public static final String liquidMutantDNA_name = "maddnamutant";

    public static Fluid liquidDNA;
    public static Fluid liquidMutantDNA;

    public MadScience()
    {
        super(ID);
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

        //  FLUIDS
        liquidDNA = new Fluid(liquidDNA_name).setDensity(3).setViscosity(4000).setLuminosity(5);
        if(!FluidRegistry.registerFluid(liquidDNA))
            liquidDNA = FluidRegistry.getFluid(liquidDNA_name);

        liquidMutantDNA = new Fluid(liquidMutantDNA_name).setDensity(3).setViscosity(4000).setLuminosity(5);
        if(!FluidRegistry.registerFluid(liquidMutantDNA))
            liquidMutantDNA = FluidRegistry.getFluid(liquidMutantDNA_name);

        // Blocks
        blockDNA = getManager().newBlock("maddna", new BlockFluidDNA(liquidDNA));
        blockMutantDNA = getManager().newBlock("madmutantdna", new BlockFluidDNA(liquidMutantDNA));
        blockAbominationEgg = getManager().newBlock(BlockAbominationEgg.class);
        blockEnderslime = getManager().newBlock(BlockEnderslime.class);

        // Items
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
}

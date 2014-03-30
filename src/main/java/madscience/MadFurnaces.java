package madscience;

import madscience.items.ItemBlockTooltip;
import madscience.items.MadGenomeInfo;
import madscience.tileentities.clayfurnace.ClayfurnaceBlock;
import madscience.tileentities.clayfurnace.ClayfurnaceEntity;
import madscience.tileentities.clayfurnace.ClayfurnaceRecipes;
import madscience.tileentities.cryofreezer.CryofreezerBlock;
import madscience.tileentities.cryofreezer.CryofreezerEntity;
import madscience.tileentities.cryotube.CryotubeBlock;
import madscience.tileentities.cryotube.CryotubeBlockGhost;
import madscience.tileentities.cryotube.CryotubeEntity;
import madscience.tileentities.dataduplicator.DataDuplicatorBlock;
import madscience.tileentities.dataduplicator.DataDuplicatorEntity;
import madscience.tileentities.dnaextractor.DNAExtractorBlock;
import madscience.tileentities.dnaextractor.DNAExtractorEntity;
import madscience.tileentities.incubator.IncubatorBlock;
import madscience.tileentities.incubator.IncubatorEntity;
import madscience.tileentities.incubator.IncubatorRecipes;
import madscience.tileentities.magloader.MagLoaderBlock;
import madscience.tileentities.magloader.MagLoaderBlockGhost;
import madscience.tileentities.magloader.MagLoaderEntity;
import madscience.tileentities.mainframe.MainframeBlock;
import madscience.tileentities.mainframe.MainframeEntity;
import madscience.tileentities.mainframe.MainframeRecipes;
import madscience.tileentities.meatcube.MeatcubeBlock;
import madscience.tileentities.meatcube.MeatcubeEntity;
import madscience.tileentities.sanitizer.SanitizerBlock;
import madscience.tileentities.sanitizer.SanitizerEntity;
import madscience.tileentities.sequencer.SequencerBlock;
import madscience.tileentities.sequencer.SequencerEntity;
import madscience.tileentities.soniclocator.SoniclocatorBlock;
import madscience.tileentities.soniclocator.SoniclocatorBlockGhost;
import madscience.tileentities.soniclocator.SoniclocatorEntity;
import madscience.tileentities.thermosonicbonder.ThermosonicBonderBlock;
import madscience.tileentities.thermosonicbonder.ThermosonicBonderEntity;
import madscience.tileentities.thermosonicbonder.ThermosonicBonderRecipes;
import madscience.tileentities.voxbox.VoxBoxBlock;
import madscience.tileentities.voxbox.VoxBoxEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class MadFurnaces
{
    // -------------
    // TILE ENTITIES
    // -------------

    // DNA Extractor.
    public static BlockContainer DNAEXTRACTOR_TILEENTITY;
    public static final String DNAEXTRACTOR_INTERNALNAME = "dnaExtractor";

    // Needle Sanitizer
    public static BlockContainer SANTITIZER_TILEENTITY;
    public static final String SANTITIZER_INTERNALNAME = "needleSanitizer";

    // Mainframe
    public static BlockContainer MAINFRAME_TILEENTITY;
    public static final String MAINFRAME_INTERNALNAME = "computerMainframe";

    // Genome Sequencer
    public static BlockContainer SEQUENCER_TILEENTITY;
    public static final String SEQUENCER_INTERNALNAME = "genomeSequencer";

    // Cryogenic Freezer
    public static BlockContainer CRYOFREEZER_TILEENTITY;
    public static final String CRYOFREEZER_INTERNALNAME = "cryoFreezer";

    // Genome Incubator
    public static BlockContainer INCUBATOR_TILEENTITY;
    public static final String INCUBATOR_INTERNALNAME = "genomeIncubator";

    // Cryogenic Tube
    public static BlockContainer CRYOTUBE_TILEENTITY;
    public static final String CRYOTUBE_INTERNALNAME = "cryoTube";

    // Cryogenic Tube 'Ghost Block'
    public static Block CRYOTUBEGHOST;
    public static final String CRYOTUBEGHOST_INTERNALNAME = "ghostCryoTube";

    // Thermosonic Bonder
    public static BlockContainer THERMOSONIC_TILEENTITY;
    public static final String THERMOSONIC_INTERNALNAME = "thermosonicBonder";

    // Data Reel Duplicator
    public static BlockContainer DATADUPLICATOR_TILEENTITY;
    public static final String DATADUPLICATOR_INTERNALNAME = "dataDuplicator";

    // Soniclocator Device
    public static BlockContainer SONICLOCATOR_TILEENTITY;
    public static final String SONICLOCATOR_INTERNALNAME = "soniclocator";

    // Soniclocator 'Ghost Block'
    public static Block SONICLOCATORGHOST;
    public static final String SONICLOCATORGHOST_INTERNALNAME = "ghostSoniclocator";

    // Meat Cube [Slime + Cow,Pig,Chicken]
    public static BlockContainer MEATCUBE_TILEENTITY;
    public static final String MEATCUBE_INTERNALNAME = "meatCube";
    
    // Clay Furnace
    public static BlockContainer CLAYFURNACE_TILEENTITY;
    public static final String CLAYFURNACE_INTERNALNAME = "clayFurnace";
    
    // VOX Box
    public static BlockContainer VOXBOX_TILEENTITY;
    public static final String VOXBOX_INTERNALNAME = "voxBox";
    
    // Magazine Loader
    public static BlockContainer MAGLOADER_TILEENTITY;
    public static final String MAGLOADER_INTERNALNAME = "magLoader";
    
    // Magazine Loader 'Ghost Block'
    public static Block MAGLOADERGHOST;
    public static final String MAGLOADERGHOST_INTERNALNAME = "ghostMagLoader";    
    
    // CnC Machine

    
    // -----------------------------
    // CUSTOM FURNANCES REGISTRY ADD
    // -----------------------------

    @EventHandler
    public static void createCryoFreezerTileEntity(int blockID)
    {
        // Cryogenic Freezer
        CRYOFREEZER_TILEENTITY = (BlockContainer) new CryofreezerBlock(blockID).setUnlocalizedName(CRYOFREEZER_INTERNALNAME);

        // Register the block with the world (so we can then tie it to a tile
        // entity).
        GameRegistry.registerBlock(CRYOFREEZER_TILEENTITY, ItemBlockTooltip.class, MadScience.ID + CRYOFREEZER_TILEENTITY.getUnlocalizedName().substring(5));

        // Register the tile-entity with the game world.
        GameRegistry.registerTileEntity(CryofreezerEntity.class, CRYOFREEZER_TILEENTITY.getUnlocalizedName());

        // Register block handler for custom GUI, this way right clicking will
        NetworkRegistry.instance().registerGuiHandler(MadScience.instance, MadScience.guiHandler);

        // Register our rendering handles on clients and ignore them on servers.
        MadScience.proxy.registerRenderingHandler(blockID);

        // Shaped Recipe
        GameRegistry.addRecipe(new ItemStack(CRYOFREEZER_TILEENTITY, 1), new Object[]
        { "131",
          "121",
          "141",

        '1', new ItemStack(MadComponents.COMPONENT_CASE),
        '2', new ItemStack(MadComponents.COMPONENT_COMPUTER),
        '3', new ItemStack(MadCircuits.CIRCUIT_COMPARATOR),
        '4', new ItemStack(MadComponents.COMPONENT_FAN), });
    }

    public static void createCryotubeGhostTileEntity(int blockID)
    {
        // Acts as a collision box for upper two blocks of cryotube.
        CRYOTUBEGHOST = new CryotubeBlockGhost(blockID).setUnlocalizedName(CRYOTUBEGHOST_INTERNALNAME);
        GameRegistry.registerBlock(CRYOTUBEGHOST, MadScience.ID + CRYOTUBEGHOST.getUnlocalizedName().substring(5));
    }

    public static void createCryotubeTileEntity(int blockID)
    {
        // Converts both a villagers brain activity and body heat into a renewable energy source.
        CRYOTUBE_TILEENTITY = (BlockContainer) new CryotubeBlock(blockID).setUnlocalizedName(CRYOTUBE_INTERNALNAME);
        GameRegistry.registerBlock(CRYOTUBE_TILEENTITY, ItemBlockTooltip.class, MadScience.ID + CRYOTUBE_TILEENTITY.getUnlocalizedName().substring(5));
        GameRegistry.registerTileEntity(CryotubeEntity.class, CRYOTUBE_TILEENTITY.getUnlocalizedName());

        // Register custom rendering for this tile entity.
        NetworkRegistry.instance().registerGuiHandler(MadScience.instance, MadScience.guiHandler);

        // Register our rendering handles on clients and ignore them on servers.
        MadScience.proxy.registerRenderingHandler(blockID);

        // Shaped Recipe
        GameRegistry.addRecipe(new ItemStack(CRYOTUBE_TILEENTITY, 1), new Object[]
        { "121",
          "131",
          "141",

        '1', Block.blockIron,
        '2', new ItemStack(MadCircuits.CIRCUIT_ENDEREYE),
        '3', new ItemStack(MadComponents.COMPONENT_COMPUTER),
        '4', new ItemStack(MadComponents.COMPONENT_POWERSUPPLY), });
    }

    public static void createDataReelDuplicatorTileEntity(int blockID)
    {
        // Copies data reels for memories and genomes alike.
        DATADUPLICATOR_TILEENTITY = (BlockContainer) new DataDuplicatorBlock(blockID).setUnlocalizedName(DATADUPLICATOR_INTERNALNAME);
        GameRegistry.registerBlock(DATADUPLICATOR_TILEENTITY, ItemBlockTooltip.class, MadScience.ID + DATADUPLICATOR_TILEENTITY.getUnlocalizedName().substring(5));
        GameRegistry.registerTileEntity(DataDuplicatorEntity.class, DATADUPLICATOR_TILEENTITY.getUnlocalizedName());

        // Register custom rendering for this tile entity.
        NetworkRegistry.instance().registerGuiHandler(MadScience.instance, MadScience.guiHandler);

        // Register our rendering handles on clients and ignore them on servers.
        MadScience.proxy.registerRenderingHandler(blockID);

        // Shaped Recipe for Data Reel Duplicator.
        GameRegistry.addRecipe(new ItemStack(MadFurnaces.DATADUPLICATOR_TILEENTITY, 1), new Object[]
        { "161",
          "232",
          "454",

        '1', new ItemStack(MadEntities.DATAREEL_EMPTY, 1),
        '2', new ItemStack(MadComponents.COMPONENT_CASE),
        '3', new ItemStack(Item.redstoneRepeater, 1),
        '4', new ItemStack(MadCircuits.CIRCUIT_SPIDEREYE),
        '5', new ItemStack(MadComponents.COMPONENT_POWERSUPPLY),
        '6', new ItemStack(MadComponents.COMPONENT_FAN) });
    }

    @EventHandler
    public static void createDNAExtractorTileEntity(int blockID)
    {
        // Populate our static instance.
        DNAEXTRACTOR_TILEENTITY = (BlockContainer) new DNAExtractorBlock(blockID).setUnlocalizedName(DNAEXTRACTOR_INTERNALNAME);

        // Register the tile-entity with the game world.
        GameRegistry.registerTileEntity(DNAExtractorEntity.class, DNAEXTRACTOR_TILEENTITY.getUnlocalizedName());

        // Register block handler for custom GUI.
        NetworkRegistry.instance().registerGuiHandler(MadScience.instance, MadScience.guiHandler);

        // Register the block with the world.
        GameRegistry.registerBlock(DNAEXTRACTOR_TILEENTITY, ItemBlockTooltip.class, MadScience.ID + DNAEXTRACTOR_TILEENTITY.getUnlocalizedName().substring(5));

        // Register our rendering handles on clients and ignore them on servers.
        MadScience.proxy.registerRenderingHandler(blockID);

        // Shaped Recipe
        GameRegistry.addRecipe(new ItemStack(DNAEXTRACTOR_TILEENTITY, 1), new Object[]
        { "414",
          "424",
          "434",

        '1', new ItemStack(MadCircuits.CIRCUIT_ENDEREYE),
        '2', new ItemStack(MadCircuits.CIRCUIT_SPIDEREYE),
        '3', new ItemStack(MadComponents.COMPONENT_COMPUTER),
        '4', new ItemStack(MadComponents.COMPONENT_CASE), });
    }

    @EventHandler
    public static void createGeneIncubatorTileEntity(int blockID)
    {
        // Genome Incubator
        INCUBATOR_TILEENTITY = (BlockContainer) new IncubatorBlock(blockID).setUnlocalizedName(INCUBATOR_INTERNALNAME);

        // Register the block with the world (so we can then tie it to a tile
        // entity).
        GameRegistry.registerBlock(INCUBATOR_TILEENTITY, ItemBlockTooltip.class, MadScience.ID + INCUBATOR_TILEENTITY.getUnlocalizedName().substring(5));

        // Register the tile-entity with the game world.
        GameRegistry.registerTileEntity(IncubatorEntity.class, INCUBATOR_TILEENTITY.getUnlocalizedName());

        // Register block handler for custom GUI, this way right clicking will
        NetworkRegistry.instance().registerGuiHandler(MadScience.instance, MadScience.guiHandler);

        // Register our rendering handles on clients and ignore them on servers.
        MadScience.proxy.registerRenderingHandler(blockID);

        // Shaped Recipe
        GameRegistry.addRecipe(new ItemStack(INCUBATOR_TILEENTITY, 1), new Object[]
        { "656",
          "142",
          "636",

        '1', new ItemStack(MadCircuits.CIRCUIT_GLOWSTONE),
        '2', new ItemStack(MadCircuits.CIRCUIT_COMPARATOR),
        '3', new ItemStack(MadComponents.COMPONENT_POWERSUPPLY), 
        '4', new ItemStack(MadComponents.COMPONENT_COMPUTER), 
        '5', new ItemStack(MadComponents.COMPONENT_FAN), 
        '6', new ItemStack(MadComponents.COMPONENT_CASE), });
    }

    @EventHandler
    public static void createGeneSequencerTileEntity(int blockID)
    {
        // Genetic Sequencer
        SEQUENCER_TILEENTITY = (BlockContainer) new SequencerBlock(blockID).setUnlocalizedName(SEQUENCER_INTERNALNAME);

        // Register the block with the world (so we can then tie it to a tile
        // entity).
        GameRegistry.registerBlock(SEQUENCER_TILEENTITY, ItemBlockTooltip.class, MadScience.ID + SEQUENCER_TILEENTITY.getUnlocalizedName().substring(5));

        // Register the tile-entity with the game world.
        GameRegistry.registerTileEntity(SequencerEntity.class, SEQUENCER_TILEENTITY.getUnlocalizedName());

        // Register block handler for custom GUI, this way right clicking will
        NetworkRegistry.instance().registerGuiHandler(MadScience.instance, MadScience.guiHandler);

        // Register our rendering handles on clients and ignore them on servers.
        MadScience.proxy.registerRenderingHandler(blockID);

        // Shaped Recipe
        GameRegistry.addRecipe(new ItemStack(SEQUENCER_TILEENTITY, 1), new Object[]
        { "172",
          "858",
          "364",

        '1', new ItemStack(MadCircuits.CIRCUIT_EMERALD),
        '2', new ItemStack(MadCircuits.CIRCUIT_COMPARATOR),
        '3', new ItemStack(MadCircuits.CIRCUIT_DIAMOND),
        '4', new ItemStack(MadCircuits.CIRCUIT_ENDEREYE),
        '5', new ItemStack(MadComponents.COMPONENT_COMPUTER), 
        '6', new ItemStack(MadComponents.COMPONENT_POWERSUPPLY), 
        '7', new ItemStack(MadComponents.COMPONENT_FAN), 
        '8', new ItemStack(MadComponents.COMPONENT_CASE), });
    }

    @EventHandler
    public static void createMainframeTileEntity(int blockID)
    {
        // Populate our static instance.
        MAINFRAME_TILEENTITY = (BlockContainer) new MainframeBlock(blockID).setUnlocalizedName(MAINFRAME_INTERNALNAME);

        // Register the block with the world (so we can then tie it to a tile
        // entity).
        GameRegistry.registerBlock(MAINFRAME_TILEENTITY, ItemBlockTooltip.class, MadScience.ID + MAINFRAME_TILEENTITY.getUnlocalizedName().substring(5));

        // Register the tile-entity with the game world.
        GameRegistry.registerTileEntity(MainframeEntity.class, MAINFRAME_TILEENTITY.getUnlocalizedName());

        // Register block handler for custom GUI, this way right clicking will
        NetworkRegistry.instance().registerGuiHandler(MadScience.instance, MadScience.guiHandler);

        // Register our rendering handles on clients and ignore them on servers.
        MadScience.proxy.registerRenderingHandler(blockID);

        // Shaped Recipe
        GameRegistry.addRecipe(new ItemStack(MAINFRAME_TILEENTITY, 1), new Object[]
        { "111",
          "121", 
          "111",

        '1', new ItemStack(MadComponents.COMPONENT_COMPUTER),
        '2', new ItemStack(MadComponents.COMPONENT_CASE), });
    }

    @EventHandler
    public static void createMeatcubeTileEntity(int blockID, int metaID, int primaryColor, int secondaryColor, int cookTime)
    {
        // Disgusting meat cube that spawns chicken, cow and pig meat when hit.
        MEATCUBE_TILEENTITY = (BlockContainer) new MeatcubeBlock(blockID).setUnlocalizedName(MEATCUBE_INTERNALNAME);
        GameRegistry.registerBlock(MEATCUBE_TILEENTITY, ItemBlockTooltip.class, MadScience.ID + MEATCUBE_TILEENTITY.getUnlocalizedName().substring(5));
        GameRegistry.registerTileEntity(MeatcubeEntity.class, MEATCUBE_TILEENTITY.getUnlocalizedName());

        // Register custom rendering for this tile entity.
        NetworkRegistry.instance().registerGuiHandler(MadScience.instance, MadScience.guiHandler);

        // Register our rendering handles on clients and ignore them on servers.
        MadScience.proxy.registerRenderingHandler(blockID);

        // Add mob to combined genome entity list so it can be created by other
        GenomeRegistry.registerGenome(new MadGenomeInfo((short) metaID, MEATCUBE_INTERNALNAME, primaryColor, secondaryColor));

        // Create meatcube with slime and pig, cow or chicken genomes!
        MainframeRecipes.addRecipe(new ItemStack(MadGenomes.GENOME_SLIME), new ItemStack(MadGenomes.GENOME_COW), new ItemStack(MadEntities.COMBINEDGENOME_MONSTERPLACER, 1, metaID), cookTime);
        MainframeRecipes.addRecipe(new ItemStack(MadGenomes.GENOME_SLIME), new ItemStack(MadGenomes.GENOME_PIG), new ItemStack(MadEntities.COMBINEDGENOME_MONSTERPLACER, 1, metaID), cookTime);
        MainframeRecipes.addRecipe(new ItemStack(MadGenomes.GENOME_SLIME), new ItemStack(MadGenomes.GENOME_CHICKEN), new ItemStack(MadEntities.COMBINEDGENOME_MONSTERPLACER, 1, metaID), cookTime);

        // Now we need to bake our meatcube in the oven until golden brown.
        IncubatorRecipes.addSmelting(MadEntities.COMBINEDGENOME_MONSTERPLACER.itemID, metaID, new ItemStack(MEATCUBE_TILEENTITY, 1));
    }

    @EventHandler
    public static void createSanitizerTileEntity(int blockID)
    {
        // Populate our static instance.
        SANTITIZER_TILEENTITY = (BlockContainer) new SanitizerBlock(blockID).setUnlocalizedName(SANTITIZER_INTERNALNAME);

        // Register the block with the world (so we can then tie it to a tile
        // entity).
        GameRegistry.registerBlock(SANTITIZER_TILEENTITY, ItemBlockTooltip.class, MadScience.ID + SANTITIZER_TILEENTITY.getUnlocalizedName().substring(5));

        // Register the tile-entity with the game world.
        GameRegistry.registerTileEntity(SanitizerEntity.class, SANTITIZER_TILEENTITY.getUnlocalizedName());

        // Register block handler for custom GUI, this way right clicking will
        NetworkRegistry.instance().registerGuiHandler(MadScience.instance, MadScience.guiHandler);

        // Register our rendering handles on clients and ignore them on servers.
        MadScience.proxy.registerRenderingHandler(blockID);

        // Shaped Recipe
        GameRegistry.addRecipe(new ItemStack(SANTITIZER_TILEENTITY, 1), new Object[]
        { "545", 
          "535", 
          "126",

        '1', new ItemStack(MadCircuits.CIRCUIT_GLOWSTONE),
        '2', new ItemStack(MadCircuits.CIRCUIT_REDSTONE),
        '3', new ItemStack(MadComponents.COMPONENT_POWERSUPPLY), 
        '4', new ItemStack(MadComponents.COMPONENT_FAN), 
        '5', new ItemStack(MadComponents.COMPONENT_CASE), 
        '6', new ItemStack(MadCircuits.CIRCUIT_ENDERPEARL), });
    }

    public static void createSoniclocatorGhostTileEntity(int blockID)
    {
        // Acts as a collision box for upper two blocks of Soniclocator device.
        SONICLOCATORGHOST = new SoniclocatorBlockGhost(blockID).setUnlocalizedName(SONICLOCATORGHOST_INTERNALNAME);
        GameRegistry.registerBlock(SONICLOCATORGHOST, MadScience.ID + SONICLOCATORGHOST.getUnlocalizedName().substring(5));
    }

    public static void createSoniclocatorTileEntity(int blockID)
    {
        // Transposes block types in exchange for others using sonic waves.
        SONICLOCATOR_TILEENTITY = (BlockContainer) new SoniclocatorBlock(blockID).setUnlocalizedName(SONICLOCATOR_INTERNALNAME);
        GameRegistry.registerBlock(SONICLOCATOR_TILEENTITY, ItemBlockTooltip.class, MadScience.ID + SONICLOCATOR_TILEENTITY.getUnlocalizedName().substring(5));
        GameRegistry.registerTileEntity(SoniclocatorEntity.class, SONICLOCATOR_TILEENTITY.getUnlocalizedName());

        // Register custom rendering for this tile entity.
        NetworkRegistry.instance().registerGuiHandler(MadScience.instance, MadScience.guiHandler);

        // Register our rendering handles on clients and ignore them on servers.
        MadScience.proxy.registerRenderingHandler(blockID);

        // Recipe for Soniclocator.
        GameRegistry.addRecipe(new ItemStack(SONICLOCATOR_TILEENTITY, 1), new Object[]
        { "111", 
          "323", 
          "545",

        '1', new ItemStack(MadComponents.COMPONENT_THUMPER),
        '2', new ItemStack(MadComponents.COMPONENT_SCREEN),
        '3', new ItemStack(MadComponents.COMPONENT_COMPUTER),
        '4', new ItemStack(MadComponents.COMPONENT_POWERSUPPLY),
        '5', new ItemStack(MadCircuits.CIRCUIT_ENDEREYE),
        });
    }

    public static void createThermosonicBonderTileEntity(int blockID)
    {
        // Creates silicon wafers, transistors, CPU's, and RAM chips.
        THERMOSONIC_TILEENTITY = (BlockContainer) new ThermosonicBonderBlock(blockID).setUnlocalizedName(THERMOSONIC_INTERNALNAME);
        GameRegistry.registerBlock(THERMOSONIC_TILEENTITY, ItemBlockTooltip.class, MadScience.ID + THERMOSONIC_TILEENTITY.getUnlocalizedName().substring(5));
        GameRegistry.registerTileEntity(ThermosonicBonderEntity.class, THERMOSONIC_TILEENTITY.getUnlocalizedName());

        // Register custom rendering for this tile entity.
        NetworkRegistry.instance().registerGuiHandler(MadScience.instance, MadScience.guiHandler);

        // Register our rendering handles on clients and ignore them on servers.
        MadScience.proxy.registerRenderingHandler(blockID);
        
        // Grab the final sacrifice block from our configuration file.
        ItemStack finalSacrifice = null;
        try
        {
            finalSacrifice = new ItemStack(MadConfig.THERMOSONICBONDER_FINALSACRIFICE, 1, 0);
        }
        catch (Exception err)
        {
            MadScience.logger.info("Attempted to load a final sacrifice ID for a block that does not exist, learn to config file better user!");
            MadScience.logger.info("Setting Thermosonic Bonder final sacrifice item back to a beacon just to spite you!");
            finalSacrifice = new ItemStack(Block.beacon);
        }

        // Shaped Recipe for Thermosonic Bonder Tile Entity
        GameRegistry.addRecipe(new ItemStack(MadFurnaces.THERMOSONIC_TILEENTITY, 1), new Object[]
        { "343", 
          "353", 
          "121",

        '1', Block.glowStone,
        '2', finalSacrifice,
        '3', Block.blockIron,
        '4', Block.blockRedstone,
        '5', Block.blockDiamond });

        // 1x Fused Quartz = 1x Silicon Wafer.
        ThermosonicBonderRecipes.addSmelting(MadComponents.COMPONENT_FUSEDQUARTZ.itemID,
                                             new ItemStack(MadComponents.COMPONENT_SILICONWAFER));

        // 1x Silicon Wafer = 16x Transistors.
        ThermosonicBonderRecipes.addSmelting(MadComponents.COMPONENT_SILICONWAFER.itemID,
                                             new ItemStack(MadComponents.COMPONENT_TRANSISTOR, 16));

        // 1x Redstone Circuit = 1x CPU.
        ThermosonicBonderRecipes.addSmelting(MadCircuits.CIRCUIT_REDSTONE.itemID,
                                             new ItemStack(MadComponents.COMPONENT_CPU));

        // 1x Glowstone Circuit 1x RAM.
        ThermosonicBonderRecipes.addSmelting(MadCircuits.CIRCUIT_GLOWSTONE.itemID,
                                             new ItemStack(MadComponents.COMPONENT_RAM));
    }

    public static void createClayFurnaceTileEntity(int blockID)
    {
        // A early-game block that can give huge return on investment for ores for clay and fire and time.
        CLAYFURNACE_TILEENTITY = (BlockContainer) new ClayfurnaceBlock(blockID).setUnlocalizedName(CLAYFURNACE_INTERNALNAME);
        GameRegistry.registerBlock(CLAYFURNACE_TILEENTITY, ItemBlockTooltip.class, MadScience.ID + CLAYFURNACE_TILEENTITY.getUnlocalizedName().substring(5));
        GameRegistry.registerTileEntity(ClayfurnaceEntity.class, CLAYFURNACE_TILEENTITY.getUnlocalizedName());

        // Register custom rendering for this tile entity.
        NetworkRegistry.instance().registerGuiHandler(MadScience.instance, MadScience.guiHandler);

        // Register our rendering handles on clients and ignore them on servers.
        MadScience.proxy.registerRenderingHandler(blockID);
        
        // Wrapping hardened clay blocks around a furnace will make a clay furnace.
        GameRegistry.addRecipe(new ItemStack(CLAYFURNACE_TILEENTITY, 1), new Object[]
        { "111", 
          "121", 
          "111",

        '1', new ItemStack(Block.hardenedClay),
        '2', new ItemStack(Block.furnaceIdle)
        });
        
        // Clay Furnace will only convert gold and iron ore into full blocks.
        ClayfurnaceRecipes.addSmelting(Block.oreIron.blockID, new ItemStack(Block.blockIron), 0.15F);
        ClayfurnaceRecipes.addSmelting(Block.oreGold.blockID, new ItemStack(Block.blockGold), 0.15F);
    }

    public static void createVOXBoxTileEntity(int blockID)
    {
        // Automatic Diagnostic and Announcement System
        // AKA: Black Mesa Announcement System
        VOXBOX_TILEENTITY = (BlockContainer) new VoxBoxBlock(blockID).setUnlocalizedName(VOXBOX_INTERNALNAME);
        GameRegistry.registerBlock(VOXBOX_TILEENTITY, ItemBlockTooltip.class, MadScience.ID + VOXBOX_TILEENTITY.getUnlocalizedName().substring(5));
        GameRegistry.registerTileEntity(VoxBoxEntity.class, VOXBOX_TILEENTITY.getUnlocalizedName());

        // Register custom rendering for this tile entity.
        NetworkRegistry.instance().registerGuiHandler(MadScience.instance, MadScience.guiHandler);

        // Register our rendering handles on clients and ignore them on servers.
        MadScience.proxy.registerRenderingHandler(blockID);
        
        // Recipe for VOX box contains a juke block.
        GameRegistry.addRecipe(new ItemStack(VOXBOX_TILEENTITY, 1), new Object[]
        { "121", 
          "465", 
          "131",

          '1', new ItemStack(MadComponents.COMPONENT_CASE, 1, 0),
          '2', new ItemStack(MadComponents.COMPONENT_COMPUTER, 1, 0),
          '3', new ItemStack(MadComponents.COMPONENT_POWERSUPPLY, 1, 0),
          '4', new ItemStack(MadCircuits.CIRCUIT_SPIDEREYE, 1, 0),
          '5', new ItemStack(MadCircuits.CIRCUIT_ENDEREYE, 1, 0),
          '6', new ItemStack(Block.jukebox, 1, 0),
        });
    }

    public static void createMagLoaderTileEntity(int blockID)
    {
        // Loads ammunition into pulse rifle magazine at in-human speeds.
        MAGLOADER_TILEENTITY = (BlockContainer) new MagLoaderBlock(blockID).setUnlocalizedName(MAGLOADER_INTERNALNAME);
        GameRegistry.registerBlock(MAGLOADER_TILEENTITY, ItemBlockTooltip.class, MadScience.ID + MAGLOADER_TILEENTITY.getUnlocalizedName().substring(5));
        GameRegistry.registerTileEntity(MagLoaderEntity.class, MAGLOADER_TILEENTITY.getUnlocalizedName());

        // Register custom rendering for this tile entity.
        NetworkRegistry.instance().registerGuiHandler(MadScience.instance, MadScience.guiHandler);

        // Register our rendering handles on clients and ignore them on servers.
        MadScience.proxy.registerRenderingHandler(blockID);
    }

    public static void createMagLoaderGhostTileEntity(int blockID)
    {
        // Acts as a collision box for upper blocks of Magazine Loader.
        MAGLOADERGHOST = new MagLoaderBlockGhost(blockID).setUnlocalizedName(MAGLOADERGHOST_INTERNALNAME);
        GameRegistry.registerBlock(MAGLOADERGHOST, MadScience.ID + MAGLOADERGHOST.getUnlocalizedName().substring(5));
    }
}

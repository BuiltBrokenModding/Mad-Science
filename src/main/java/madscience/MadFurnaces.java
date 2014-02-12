package madscience;

import madscience.items.MadGenomeInfo;
import madscience.metaitems.CombinedMemoryMonsterPlacer;
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
import madscience.tileentities.mainframe.MainframeBlock;
import madscience.tileentities.mainframe.MainframeEntity;
import madscience.tileentities.mainframe.MainframeRecipes;
import madscience.tileentities.meatcube.MeatcubeBlock;
import madscience.tileentities.meatcube.MeatcubeEntity;
import madscience.tileentities.sanitizer.SanitizerBlock;
import madscience.tileentities.sanitizer.SanitizerEntity;
import madscience.tileentities.sequencer.SequencerBlock;
import madscience.tileentities.sequencer.SequencerEntity;
import madscience.tileentities.thermosonicbonder.ThermosonicBonderBlock;
import madscience.tileentities.thermosonicbonder.ThermosonicBonderEntity;
import madscience.tileentities.thermosonicbonder.ThermosonicBonderRecipes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class MadFurnaces
{
    // -------------
    // TILE ENTITIES
    // -------------

    // DNA Extractor.
    public static BlockContainer DNAEXTRACTOR_TILEENTITY;
    public static final String DNAEXTRACTOR_DISPLAYNAME = "DNA Extractor";
    public static final String DNAEXTRACTOR_INTERNALNAME = "dnaExtractor";

    // Needle Sanitizer
    public static BlockContainer SANTITIZER_TILEENTITY;
    public static final String SANTITIZER_DISPLAYNAME = "Syringe Sanitizer";
    public static final String SANTITIZER_INTERNALNAME = "needleSanitizer";

    // Mainframe
    public static BlockContainer MAINFRAME_TILEENTITY;
    public static final String MAINFRAME_DISPLAYNAME = "Computer Mainframe";
    public static final String MAINFRAME_INTERNALNAME = "computerMainframe";

    // Genome Sequencer
    public static BlockContainer SEQUENCER_TILEENTITY;
    public static final String SEQUENCER_DISPLAYNAME = "Genome Sequencer";
    public static final String SEQUENCER_INTERNALNAME = "genomeSequencer";

    // Cryogenic Freezer
    public static BlockContainer CRYOFREEZER_TILEENTITY;
    public static final String CRYOFREEZER_DISPLAYNAME = "Cryogenic Freezer";
    public static final String CRYOFREEZER_INTERNALNAME = "cryoFreezer";

    // Genome Incubator
    public static BlockContainer INCUBATOR_TILEENTITY;
    public static final String INCUBATOR_DISPLAYNAME = "Genome Incubator";
    public static final String INCUBATOR_INTERNALNAME = "genomeIncubator";

    // Cryogenic Tube
    public static BlockContainer CRYOTUBE_TILEENTITY;
    public static final String CRYOTUBE_DISPLAYNAME = "Cryogenic Tube";
    public static final String CRYOTUBE_INTERNALNAME = "cryoTube";
    
    // Cryogenic Tube 'Ghost Block'
    public static Block CRYOTUBEGHOST;
    public static final String CRYOTUBEGHOST_INTERNALNAME = "ghostCryoTube";
    
    // Thermosonic Bonder
    public static BlockContainer THERMOSONIC_TILEENTITY;
    public static final String THERMOSONIC_DISPLAYNAME = "Thermosonic Bonder";
    public static final String THERMOSONIC_INTERNALNAME = "thermosonicBonder";
    
    // Data Reel Duplicator
    public static BlockContainer DATADUPLICATOR_TILEENTITY;
    public static final String DATADUPLICATOR_DISPLAYNAME = "Data Reel Duplicator";
    public static final String DATADUPLICATOR_INTERNALNAME = "dataDuplicator";
    
    // Meat Cube [Slime + Cow,Pig,Chicken]
    public static BlockContainer MEATCUBE_TILEENTITY;
    public static final String MEATCUBE_DISPLAYNAME = "Disgusting Meat Cube";
    public static final String MEATCUBE_INTERNALNAME = "meatCube";

    // -----------------------------
    // CUSTOM FURNANCES REGISTRY ADD
    // -----------------------------

    @EventHandler
    public static void createMeatcubeTileEntity(int blockID, int metaID, int primaryColor, int secondaryColor, int cookTime)
    {
        // Disgusting meat cube that spawns chicken, cow and pig meat when hit.
        MEATCUBE_TILEENTITY = (BlockContainer) new MeatcubeBlock(blockID).setUnlocalizedName(MEATCUBE_INTERNALNAME);
        GameRegistry.registerBlock(MEATCUBE_TILEENTITY, MadScience.ID + MEATCUBE_TILEENTITY.getUnlocalizedName().substring(5));
        GameRegistry.registerTileEntity(MeatcubeEntity.class, MEATCUBE_TILEENTITY.getUnlocalizedName());
        LanguageRegistry.addName(MEATCUBE_TILEENTITY, MEATCUBE_DISPLAYNAME);

        // Register custom rendering for this tile entity.
        NetworkRegistry.instance().registerGuiHandler(MadScience.instance, MadScience.guiHandler);

        // Register our rendering handles on clients and ignore them on servers.
        MadScience.proxy.registerRenderingHandler(blockID);

        // Add mob to combined genome entity list so it can be created by other
        GenomeRegistry.registerGenome(new MadGenomeInfo((short) metaID, MEATCUBE_INTERNALNAME, MEATCUBE_DISPLAYNAME, primaryColor, secondaryColor));
        LanguageRegistry.instance().addStringLocalization("entity." + MEATCUBE_INTERNALNAME + ".name", MEATCUBE_DISPLAYNAME);

        // Create meatcube with slime and pig, cow or chicken genomes!
        MainframeRecipes.addRecipe(new ItemStack(MadGenomes.GENOME_SLIME), new ItemStack(MadGenomes.GENOME_COW), new ItemStack(MadEntities.COMBINEDGENOME_MONSTERPLACER, 1, metaID), cookTime);
        MainframeRecipes.addRecipe(new ItemStack(MadGenomes.GENOME_SLIME), new ItemStack(MadGenomes.GENOME_PIG), new ItemStack(MadEntities.COMBINEDGENOME_MONSTERPLACER, 1, metaID), cookTime);
        MainframeRecipes.addRecipe(new ItemStack(MadGenomes.GENOME_SLIME), new ItemStack(MadGenomes.GENOME_CHICKEN), new ItemStack(MadEntities.COMBINEDGENOME_MONSTERPLACER, 1, metaID), cookTime);

        // Now we need to bake our meatcube in the oven until golden brown.
        IncubatorRecipes.addSmelting(MadEntities.COMBINEDGENOME_MONSTERPLACER.itemID, metaID, new ItemStack(MEATCUBE_TILEENTITY, 1));
    }
    
    @EventHandler
    public static void createCryoFreezerTileEntity(int blockID)
    {
        // Cryogenic Freezer
        CRYOFREEZER_TILEENTITY = (BlockContainer) new CryofreezerBlock(blockID).setUnlocalizedName(CRYOFREEZER_INTERNALNAME);

        // Register the block with the world (so we can then tie it to a tile
        // entity).
        GameRegistry.registerBlock(CRYOFREEZER_TILEENTITY, MadScience.ID + CRYOFREEZER_TILEENTITY.getUnlocalizedName().substring(5));

        // Register the tile-entity with the game world.
        GameRegistry.registerTileEntity(CryofreezerEntity.class, CRYOFREEZER_TILEENTITY.getUnlocalizedName());

        // Proper name for our tile entity for player to see.
        LanguageRegistry.addName(CRYOFREEZER_TILEENTITY, CRYOFREEZER_DISPLAYNAME);

        // Register block handler for custom GUI, this way right clicking will
        NetworkRegistry.instance().registerGuiHandler(MadScience.instance, MadScience.guiHandler);
        
        // Register our rendering handles on clients and ignore them on servers.
        MadScience.proxy.registerRenderingHandler(blockID);
        
        // Shaped Recipe
        GameRegistry.addRecipe(new ItemStack(CRYOFREEZER_TILEENTITY, 1), new Object[]
        { "131",
          "121",
          "141",
          
          '1', new ItemStack(MadComponents.MAINFRAME_COMPONENTS_METAITEM, 1, MadComponents.COMPONENT_CASE_METAID),
          '2', new ItemStack(MadComponents.MAINFRAME_COMPONENTS_METAITEM, 1, MadComponents.COMPONENT_COMPUTER_METAID),
          '3', new ItemStack(MadComponents.MAINFRAME_COMPONENTS_METAITEM, 1, MadComponents.CIRCUIT_COMPARATOR_METAID),
          '4', new ItemStack(MadComponents.MAINFRAME_COMPONENTS_METAITEM, 1, MadComponents.COMPONENT_FAN_METAID),
        });
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
        GameRegistry.registerBlock(DNAEXTRACTOR_TILEENTITY, MadScience.ID + DNAEXTRACTOR_TILEENTITY.getUnlocalizedName().substring(5));

        // Proper name for our tile entity for player to see.
        LanguageRegistry.addName(DNAEXTRACTOR_TILEENTITY, DNAEXTRACTOR_DISPLAYNAME);
        
        // Register our rendering handles on clients and ignore them on servers.
        MadScience.proxy.registerRenderingHandler(blockID);
        
        // Shaped Recipe
        GameRegistry.addRecipe(new ItemStack(DNAEXTRACTOR_TILEENTITY, 1), new Object[]
        { "414",
          "424",
          "434",
          
          '1', new ItemStack(MadComponents.MAINFRAME_COMPONENTS_METAITEM, 1, MadComponents.CIRCUIT_ENDEREYE_METAID),
          '2', new ItemStack(MadComponents.MAINFRAME_COMPONENTS_METAITEM, 1, MadComponents.CIRCUIT_SPIDEREYE_METAID),
          '3', new ItemStack(MadComponents.MAINFRAME_COMPONENTS_METAITEM, 1, MadComponents.COMPONENT_COMPUTER_METAID),
          '4', new ItemStack(MadComponents.MAINFRAME_COMPONENTS_METAITEM, 1, MadComponents.COMPONENT_CASE_METAID),
        });
    }

    @EventHandler
    public static void createGeneIncubatorTileEntity(int blockID)
    {
        // Genome Incubator
        INCUBATOR_TILEENTITY = (BlockContainer) new IncubatorBlock(blockID).setUnlocalizedName(INCUBATOR_INTERNALNAME);

        // Register the block with the world (so we can then tie it to a tile
        // entity).
        GameRegistry.registerBlock(INCUBATOR_TILEENTITY, MadScience.ID + INCUBATOR_TILEENTITY.getUnlocalizedName().substring(5));

        // Register the tile-entity with the game world.
        GameRegistry.registerTileEntity(IncubatorEntity.class, INCUBATOR_TILEENTITY.getUnlocalizedName());

        // Proper name for our tile entity for player to see.
        LanguageRegistry.addName(INCUBATOR_TILEENTITY, INCUBATOR_DISPLAYNAME);

        // Register block handler for custom GUI, this way right clicking will
        NetworkRegistry.instance().registerGuiHandler(MadScience.instance, MadScience.guiHandler);
        
        // Register our rendering handles on clients and ignore them on servers.
        MadScience.proxy.registerRenderingHandler(blockID);
        
        // Shaped Recipe
        GameRegistry.addRecipe(new ItemStack(INCUBATOR_TILEENTITY, 1), new Object[]
        { "656",
          "142",
          "636",
          
          '1', new ItemStack(MadComponents.MAINFRAME_COMPONENTS_METAITEM, 1, MadComponents.CIRCUIT_GLOWSTONE_METAID),
          '2', new ItemStack(MadComponents.MAINFRAME_COMPONENTS_METAITEM, 1, MadComponents.CIRCUIT_COMPARATOR_METAID),
          '3', new ItemStack(MadComponents.MAINFRAME_COMPONENTS_METAITEM, 1, MadComponents.COMPONENT_POWERSUPPLY_METAID),
          '4', new ItemStack(MadComponents.MAINFRAME_COMPONENTS_METAITEM, 1, MadComponents.COMPONENT_COMPUTER_METAID),
          '5', new ItemStack(MadComponents.MAINFRAME_COMPONENTS_METAITEM, 1, MadComponents.COMPONENT_FAN_METAID),
          '6', new ItemStack(MadComponents.MAINFRAME_COMPONENTS_METAITEM, 1, MadComponents.COMPONENT_CASE_METAID),
        });
    }

    @EventHandler
    public static void createGeneSequencerTileEntity(int blockID)
    {
        // Genetic Sequencer
        SEQUENCER_TILEENTITY = (BlockContainer) new SequencerBlock(blockID).setUnlocalizedName(SEQUENCER_INTERNALNAME);

        // Register the block with the world (so we can then tie it to a tile
        // entity).
        GameRegistry.registerBlock(SEQUENCER_TILEENTITY, MadScience.ID + SEQUENCER_TILEENTITY.getUnlocalizedName().substring(5));

        // Register the tile-entity with the game world.
        GameRegistry.registerTileEntity(SequencerEntity.class, SEQUENCER_TILEENTITY.getUnlocalizedName());

        // Proper name for our tile entity for player to see.
        LanguageRegistry.addName(SEQUENCER_TILEENTITY, SEQUENCER_DISPLAYNAME);

        // Register block handler for custom GUI, this way right clicking will
        NetworkRegistry.instance().registerGuiHandler(MadScience.instance, MadScience.guiHandler);
        
        // Register our rendering handles on clients and ignore them on servers.
        MadScience.proxy.registerRenderingHandler(blockID);
        
        // Shaped Recipe
        GameRegistry.addRecipe(new ItemStack(SEQUENCER_TILEENTITY, 1), new Object[]
        { "172",
          "858",
          "364",
          
          '1', new ItemStack(MadComponents.MAINFRAME_COMPONENTS_METAITEM, 1, MadComponents.CIRCUIT_EMERALD_METAID),
          '2', new ItemStack(MadComponents.MAINFRAME_COMPONENTS_METAITEM, 1, MadComponents.CIRCUIT_COMPARATOR_METAID),
          '3', new ItemStack(MadComponents.MAINFRAME_COMPONENTS_METAITEM, 1, MadComponents.CIRCUIT_DIAMOND_METAID),
          '4', new ItemStack(MadComponents.MAINFRAME_COMPONENTS_METAITEM, 1, MadComponents.CIRCUIT_ENDEREYE_METAID),
          '5', new ItemStack(MadComponents.MAINFRAME_COMPONENTS_METAITEM, 1, MadComponents.COMPONENT_COMPUTER_METAID),
          '6', new ItemStack(MadComponents.MAINFRAME_COMPONENTS_METAITEM, 1, MadComponents.COMPONENT_POWERSUPPLY_METAID),
          '7', new ItemStack(MadComponents.MAINFRAME_COMPONENTS_METAITEM, 1, MadComponents.COMPONENT_FAN_METAID),
          '8', new ItemStack(MadComponents.MAINFRAME_COMPONENTS_METAITEM, 1, MadComponents.COMPONENT_CASE_METAID),
        });
    }

    @EventHandler
    public static void createMainframeTileEntity(int blockID)
    {
        // Populate our static instance.
        MAINFRAME_TILEENTITY = (BlockContainer) new MainframeBlock(blockID).setUnlocalizedName(MAINFRAME_INTERNALNAME);

        // Register the block with the world (so we can then tie it to a tile
        // entity).
        GameRegistry.registerBlock(MAINFRAME_TILEENTITY, MadScience.ID + MAINFRAME_TILEENTITY.getUnlocalizedName().substring(5));

        // Register the tile-entity with the game world.
        GameRegistry.registerTileEntity(MainframeEntity.class, MAINFRAME_TILEENTITY.getUnlocalizedName());

        // Proper name for our tile entity for player to see.
        LanguageRegistry.addName(MAINFRAME_TILEENTITY, MAINFRAME_DISPLAYNAME);

        // Register block handler for custom GUI, this way right clicking will
        NetworkRegistry.instance().registerGuiHandler(MadScience.instance, MadScience.guiHandler);
        
        // Register our rendering handles on clients and ignore them on servers.
        MadScience.proxy.registerRenderingHandler(blockID);
        
        // Shaped Recipe
        GameRegistry.addRecipe(new ItemStack(MAINFRAME_TILEENTITY, 1), new Object[]
        { "111",
          "121",
          "111",
          
          '1', new ItemStack(MadComponents.MAINFRAME_COMPONENTS_METAITEM, 1, MadComponents.COMPONENT_COMPUTER_METAID),
          '2', new ItemStack(MadComponents.MAINFRAME_COMPONENTS_METAITEM, 1, MadComponents.COMPONENT_CASE_METAID),
        });
    }

    @EventHandler
    public static void createSanitizerTileEntity(int blockID)
    {
        // Populate our static instance.
        SANTITIZER_TILEENTITY = (BlockContainer) new SanitizerBlock(blockID).setUnlocalizedName(SANTITIZER_INTERNALNAME);

        // Register the block with the world (so we can then tie it to a tile
        // entity).
        GameRegistry.registerBlock(SANTITIZER_TILEENTITY, MadScience.ID + SANTITIZER_TILEENTITY.getUnlocalizedName().substring(5));

        // Register the tile-entity with the game world.
        GameRegistry.registerTileEntity(SanitizerEntity.class, SANTITIZER_TILEENTITY.getUnlocalizedName());

        // Proper name for our tile entity for player to see.
        LanguageRegistry.addName(SANTITIZER_TILEENTITY, SANTITIZER_DISPLAYNAME);

        // Register block handler for custom GUI, this way right clicking will
        NetworkRegistry.instance().registerGuiHandler(MadScience.instance, MadScience.guiHandler);
        
        // Register our rendering handles on clients and ignore them on servers.
        MadScience.proxy.registerRenderingHandler(blockID);
        
        // Shaped Recipe
        GameRegistry.addRecipe(new ItemStack(SANTITIZER_TILEENTITY, 1), new Object[]
        { "545",
          "535",
          "126",
          
          '1', new ItemStack(MadComponents.MAINFRAME_COMPONENTS_METAITEM, 1, MadComponents.CIRCUIT_GLOWSTONE_METAID),
          '2', new ItemStack(MadComponents.MAINFRAME_COMPONENTS_METAITEM, 1, MadComponents.CIRCUIT_REDSTONE_METAID),
          '3', new ItemStack(MadComponents.MAINFRAME_COMPONENTS_METAITEM, 1, MadComponents.COMPONENT_POWERSUPPLY_METAID),
          '4', new ItemStack(MadComponents.MAINFRAME_COMPONENTS_METAITEM, 1, MadComponents.COMPONENT_FAN_METAID),
          '5', new ItemStack(MadComponents.MAINFRAME_COMPONENTS_METAITEM, 1, MadComponents.COMPONENT_CASE_METAID),
          '6', new ItemStack(MadComponents.MAINFRAME_COMPONENTS_METAITEM, 1, MadComponents.CIRCUIT_ENDERPEARL_METAID),
        });
    }

    public static void createCryotubeTileEntity(int blockID)
    {
        // Converts both a villagers brain activity and body heat into a renewable energy source.
        CRYOTUBE_TILEENTITY = (BlockContainer) new CryotubeBlock(blockID).setUnlocalizedName(CRYOTUBE_INTERNALNAME);
        GameRegistry.registerBlock(CRYOTUBE_TILEENTITY, MadScience.ID + CRYOTUBE_TILEENTITY.getUnlocalizedName().substring(5));
        GameRegistry.registerTileEntity(CryotubeEntity.class, CRYOTUBE_TILEENTITY.getUnlocalizedName());
        LanguageRegistry.addName(CRYOTUBE_TILEENTITY, CRYOTUBE_DISPLAYNAME);

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
          '2', new ItemStack(MadComponents.MAINFRAME_COMPONENTS_METAITEM, 1, MadComponents.CIRCUIT_ENDEREYE_METAID),
          '3', new ItemStack(MadComponents.MAINFRAME_COMPONENTS_METAITEM, 1, MadComponents.COMPONENT_COMPUTER_METAID),
          '4', new ItemStack(MadComponents.MAINFRAME_COMPONENTS_METAITEM, 1, MadComponents.COMPONENT_POWERSUPPLY_METAID),
        });
    }
    
    public static void createCryotubeGhostTileEntity(int blockID)
    {
        // Acts as a collision box for upper two blocks of cryotube.
        CRYOTUBEGHOST = (Block) new CryotubeBlockGhost(blockID).setUnlocalizedName(CRYOTUBEGHOST_INTERNALNAME);
        GameRegistry.registerBlock(CRYOTUBEGHOST, MadScience.ID + CRYOTUBEGHOST.getUnlocalizedName().substring(5));
        LanguageRegistry.addName(CRYOTUBEGHOST, CRYOTUBE_DISPLAYNAME);
    }

    public static void createThermosonicBonderTileEntity(int blockID)
    {
        // Creates silicon wafers, transistors, CPU's, and RAM chips.
        THERMOSONIC_TILEENTITY = (BlockContainer) new ThermosonicBonderBlock(blockID).setUnlocalizedName(THERMOSONIC_INTERNALNAME);
        GameRegistry.registerBlock(THERMOSONIC_TILEENTITY, MadScience.ID + THERMOSONIC_TILEENTITY.getUnlocalizedName().substring(5));
        GameRegistry.registerTileEntity(ThermosonicBonderEntity.class, THERMOSONIC_TILEENTITY.getUnlocalizedName());
        LanguageRegistry.addName(THERMOSONIC_TILEENTITY, THERMOSONIC_DISPLAYNAME);

        // Register custom rendering for this tile entity.
        NetworkRegistry.instance().registerGuiHandler(MadScience.instance, MadScience.guiHandler);
        
        // Register our rendering handles on clients and ignore them on servers.
        MadScience.proxy.registerRenderingHandler(blockID);
        
        // Shaped Recipe for Thermosonic Bonder Tile Entity
        GameRegistry.addRecipe(new ItemStack(MadFurnaces.THERMOSONIC_TILEENTITY, 1), new Object[]
        { "343",
          "353",
          "121",
          
          '1', Block.glowStone,
          '2', Block.beacon,
          '3', Block.blockIron,
          '4', Block.blockRedstone,
          '5', Block.blockDiamond
        });
        
        // 1x Fused Quartz = 1x Silicon Wafer.
        ThermosonicBonderRecipes.addSmelting(MadComponents.MAINFRAME_COMPONENTS_METAITEM.itemID, MadComponents.COMPONENT_FUSEDQUARTZ_METAID, new ItemStack(MadComponents.MAINFRAME_COMPONENTS_METAITEM, 1, MadComponents.COMPONENT_SILICONWAFER_METAID));
        
        // 1x Silicon Wafer = 16x Transistors.
        ThermosonicBonderRecipes.addSmelting(MadComponents.MAINFRAME_COMPONENTS_METAITEM.itemID, MadComponents.COMPONENT_SILICONWAFER_METAID, new ItemStack(MadComponents.MAINFRAME_COMPONENTS_METAITEM, 16, MadComponents.COMPONENT_TRANSISTOR_METAID));
        
        // 1x Redstone Circuit = 1x CPU.
        ThermosonicBonderRecipes.addSmelting(MadComponents.MAINFRAME_COMPONENTS_METAITEM.itemID, MadComponents.CIRCUIT_REDSTONE_METAID, new ItemStack(MadComponents.MAINFRAME_COMPONENTS_METAITEM, 1, MadComponents.COMPONENT_CPU_METAID));
        
        // 1x Glowstone Circuit  1x RAM.
        ThermosonicBonderRecipes.addSmelting(MadComponents.MAINFRAME_COMPONENTS_METAITEM.itemID, MadComponents.CIRCUIT_GLOWSTONE_METAID, new ItemStack(MadComponents.MAINFRAME_COMPONENTS_METAITEM, 1, MadComponents.COMPONENT_RAM_METAID));
    }

    public static void createDataReelDuplicatorTileEntity(int blockID)
    {
        // Copies data reels for memories and genomes alike.
        DATADUPLICATOR_TILEENTITY = (BlockContainer) new DataDuplicatorBlock(blockID).setUnlocalizedName(DATADUPLICATOR_INTERNALNAME);
        GameRegistry.registerBlock(DATADUPLICATOR_TILEENTITY, MadScience.ID + DATADUPLICATOR_TILEENTITY.getUnlocalizedName().substring(5));
        GameRegistry.registerTileEntity(DataDuplicatorEntity.class, DATADUPLICATOR_TILEENTITY.getUnlocalizedName());
        LanguageRegistry.addName(DATADUPLICATOR_TILEENTITY, DATADUPLICATOR_DISPLAYNAME);

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
          '2', new ItemStack(MadComponents.MAINFRAME_COMPONENTS_METAITEM, 1, MadComponents.COMPONENT_CASE_METAID),
          '3', new ItemStack(Item.redstoneRepeater, 1),
          '4', new ItemStack(MadComponents.MAINFRAME_COMPONENTS_METAITEM, 1, MadComponents.CIRCUIT_SPIDEREYE_METAID),
          '5', new ItemStack(MadComponents.MAINFRAME_COMPONENTS_METAITEM, 1, MadComponents.COMPONENT_POWERSUPPLY_METAID),
          '6', new ItemStack(MadComponents.MAINFRAME_COMPONENTS_METAITEM, 1, MadComponents.COMPONENT_FAN_METAID)
        });
    }
}

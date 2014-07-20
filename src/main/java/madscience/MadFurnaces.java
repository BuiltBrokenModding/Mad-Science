package madscience;

import madscience.factory.mod.MadMod;
import madscience.items.ItemBlockTooltip;
import madscience.items.combinedgenomes.MadGenomeInfo;
import madscience.items.combinedgenomes.MadGenomeRegistry;
import madscience.tile.clayfurnace.ClayfurnaceBlock;
import madscience.tile.clayfurnace.ClayfurnaceEntity;
import madscience.tile.clayfurnace.ClayfurnaceRecipes;
import madscience.tile.cncmachine.CnCMachineBlock;
import madscience.tile.cncmachine.CnCMachineBlockGhost;
import madscience.tile.cncmachine.CnCMachineEntity;
import madscience.tile.cncmachine.CnCMachineRecipes;
import madscience.tile.cryotube.CryotubeBlock;
import madscience.tile.cryotube.CryotubeBlockGhost;
import madscience.tile.cryotube.CryotubeEntity;
import madscience.tile.incubator.IncubatorBlock;
import madscience.tile.incubator.IncubatorEntity;
import madscience.tile.incubator.IncubatorRecipes;
import madscience.tile.magloader.MagLoaderBlock;
import madscience.tile.magloader.MagLoaderBlockGhost;
import madscience.tile.magloader.MagLoaderEntity;
import madscience.tile.mainframe.MainframeBlock;
import madscience.tile.mainframe.MainframeEntity;
import madscience.tile.mainframe.MainframeRecipes;
import madscience.tile.meatcube.MeatcubeBlock;
import madscience.tile.meatcube.MeatcubeEntity;
import madscience.tile.sequencer.SequencerBlock;
import madscience.tile.sequencer.SequencerEntity;
import madscience.tile.soniclocator.SoniclocatorBlock;
import madscience.tile.soniclocator.SoniclocatorBlockGhost;
import madscience.tile.soniclocator.SoniclocatorEntity;
import madscience.tile.voxbox.VoxBoxBlock;
import madscience.tile.voxbox.VoxBoxEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.registry.GameRegistry;

public class MadFurnaces
{
    // -------------
    // TILE ENTITIES
    // -------------

    // Mainframe
    public static BlockContainer MAINFRAME_TILEENTITY;
    public static final String MAINFRAME_INTERNALNAME = "computerMainframe";

    // Genome Sequencer
    public static BlockContainer SEQUENCER_TILEENTITY;
    public static final String SEQUENCER_INTERNALNAME = "genomeSequencer";

    // Genome Incubator
    public static BlockContainer INCUBATOR_TILEENTITY;
    public static final String INCUBATOR_INTERNALNAME = "genomeIncubator";

    // Cryogenic Tube
    public static BlockContainer CRYOTUBE_TILEENTITY;
    public static final String CRYOTUBE_INTERNALNAME = "cryoTube";

    // Cryogenic Tube 'Ghost Block'
    public static Block CRYOTUBEGHOST;
    private static final String CRYOTUBEGHOST_INTERNALNAME = "ghostCryoTube";

    // Soniclocator Device
    public static BlockContainer SONICLOCATOR_TILEENTITY;
    public static final String SONICLOCATOR_INTERNALNAME = "soniclocator";

    // Soniclocator 'Ghost Block'
    public static Block SONICLOCATORGHOST;
    private static final String SONICLOCATORGHOST_INTERNALNAME = "ghostSoniclocator";

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
    private static final String MAGLOADERGHOST_INTERNALNAME = "ghostMagLoader";    
    
    // CnC Machine
    public static BlockContainer CNCMACHINE_TILEENTITY;
    public static final String CNCMACHINE_INTERNALNAME = "cncMachine";
    
    // CnC Machine 'Ghost Block'
    public static Block CNCMACHINEGHOST_TILEENTITY;
    private static final String CNCMACHINEGHOST_INTERNALNAME = "ghostCnCMachine";    
    
    // -----------------------------
    // CUSTOM FURNANCES REGISTRY ADD
    // -----------------------------

    static void createCryotubeGhostTileEntity(int blockID)
    {
        // Acts as a collision box for upper two blocks of cryotube.
        MadMod.LOGGER.info("-Cryogenic Tube Ghost Block");
        CRYOTUBEGHOST = new CryotubeBlockGhost(blockID).setUnlocalizedName(CRYOTUBEGHOST_INTERNALNAME);
        GameRegistry.registerBlock(CRYOTUBEGHOST, MadMod.ID + CRYOTUBEGHOST_INTERNALNAME);
    }

    static void createCryotubeTileEntity(int blockID)
    {
        // Converts both a villagers brain activity and body heat into a renewable energy source.
        MadMod.LOGGER.info("-Cryogenic Tube Tile Entity");
        CRYOTUBE_TILEENTITY = (BlockContainer) new CryotubeBlock(blockID).setUnlocalizedName(CRYOTUBE_INTERNALNAME);
        GameRegistry.registerBlock(CRYOTUBE_TILEENTITY, ItemBlockTooltip.class, MadMod.ID + CRYOTUBE_INTERNALNAME);
        GameRegistry.registerTileEntity(CryotubeEntity.class, CRYOTUBE_INTERNALNAME);

        // Register our rendering handles on clients and ignore them on servers.
        MadForgeMod.proxy.registerRenderingHandler(blockID);

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

    @EventHandler
    static void createGeneIncubatorTileEntity(int blockID)
    {
        // Genome Incubator
        MadMod.LOGGER.info("-Genome Incubator Tile Entity");
        INCUBATOR_TILEENTITY = (BlockContainer) new IncubatorBlock(blockID).setUnlocalizedName(INCUBATOR_INTERNALNAME);

        // Register the block with the world (so we can then tie it to a tile entity).
        GameRegistry.registerBlock(INCUBATOR_TILEENTITY, ItemBlockTooltip.class, MadMod.ID + INCUBATOR_INTERNALNAME);

        // Register the tile-entity with the game world.
        GameRegistry.registerTileEntity(IncubatorEntity.class, INCUBATOR_INTERNALNAME);

        // Register our rendering handles on clients and ignore them on servers.
        MadForgeMod.proxy.registerRenderingHandler(blockID);

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
    static void createGeneSequencerTileEntity(int blockID)
    {
        // Genetic Sequencer
        MadMod.LOGGER.info("-Genetic Sequencer Tile Entity");
        SEQUENCER_TILEENTITY = (BlockContainer) new SequencerBlock(blockID).setUnlocalizedName(SEQUENCER_INTERNALNAME);

        // Register the block with the world (so we can then tie it to a tile
        // entity).
        GameRegistry.registerBlock(SEQUENCER_TILEENTITY, ItemBlockTooltip.class, MadMod.ID + SEQUENCER_INTERNALNAME);

        // Register the tile-entity with the game world.
        GameRegistry.registerTileEntity(SequencerEntity.class, SEQUENCER_INTERNALNAME);

        // Register our rendering handles on clients and ignore them on servers.
        MadForgeMod.proxy.registerRenderingHandler(blockID);

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
    static void createMainframeTileEntity(int blockID)
    {
        // Populate our static instance.
        MadMod.LOGGER.info("-Computer Mainframe Tile Entity");
        MAINFRAME_TILEENTITY = (BlockContainer) new MainframeBlock(blockID).setUnlocalizedName(MAINFRAME_INTERNALNAME);

        // Register the block with the world (so we can then tie it to a tile
        // entity).
        GameRegistry.registerBlock(MAINFRAME_TILEENTITY, ItemBlockTooltip.class, MadMod.ID + MAINFRAME_INTERNALNAME);

        // Register the tile-entity with the game world.
        GameRegistry.registerTileEntity(MainframeEntity.class, MAINFRAME_INTERNALNAME);

        // Register our rendering handles on clients and ignore them on servers.
        MadForgeMod.proxy.registerRenderingHandler(blockID);

        // Shaped Recipe
        GameRegistry.addRecipe(new ItemStack(MAINFRAME_TILEENTITY, 1), new Object[]
        { "111",
          "121", 
          "111",

        '1', new ItemStack(MadComponents.COMPONENT_COMPUTER),
        '2', new ItemStack(MadComponents.COMPONENT_CASE), });
    }

    @EventHandler
    static void createMeatcubeTileEntity(int blockID, int metaID, int primaryColor, int secondaryColor, int cookTime)
    {
        // Disgusting meat cube that spawns chicken, cow and pig meat when hit.
        MadMod.LOGGER.info("-Disgusting Meat Cube Tile Entity");
        MEATCUBE_TILEENTITY = (BlockContainer) new MeatcubeBlock(blockID).setUnlocalizedName(MEATCUBE_INTERNALNAME);
        GameRegistry.registerBlock(MEATCUBE_TILEENTITY, ItemBlockTooltip.class, MadMod.ID + MEATCUBE_INTERNALNAME);
        GameRegistry.registerTileEntity(MeatcubeEntity.class, MEATCUBE_INTERNALNAME);

        // Register our rendering handles on clients and ignore them on servers.
        MadForgeMod.proxy.registerRenderingHandler(blockID);

        // Add mob to combined genome entity list so it can be created by other
        MadGenomeRegistry.registerGenome(new MadGenomeInfo((short) metaID, MEATCUBE_INTERNALNAME, primaryColor, secondaryColor));

        // Create meatcube with slime and pig, cow or chicken genomes!
        MainframeRecipes.addRecipe(new ItemStack(MadGenomes.GENOME_SLIME), new ItemStack(MadGenomes.GENOME_COW), new ItemStack(MadEntities.COMBINEDGENOME_MONSTERPLACER, 1, metaID), cookTime);
        MainframeRecipes.addRecipe(new ItemStack(MadGenomes.GENOME_SLIME), new ItemStack(MadGenomes.GENOME_PIG), new ItemStack(MadEntities.COMBINEDGENOME_MONSTERPLACER, 1, metaID), cookTime);
        MainframeRecipes.addRecipe(new ItemStack(MadGenomes.GENOME_SLIME), new ItemStack(MadGenomes.GENOME_CHICKEN), new ItemStack(MadEntities.COMBINEDGENOME_MONSTERPLACER, 1, metaID), cookTime);

        // Now we need to bake our meatcube in the oven until golden brown.
        IncubatorRecipes.addSmelting(MadEntities.COMBINEDGENOME_MONSTERPLACER.itemID, metaID, new ItemStack(MEATCUBE_TILEENTITY, 1));
    }

    static void createSoniclocatorGhostTileEntity(int blockID)
    {
        MadMod.LOGGER.info("-Soniclocator Ghost Block");
        
        // Acts as a collision box for upper two blocks of Soniclocator device.
        SONICLOCATORGHOST = new SoniclocatorBlockGhost(blockID).setUnlocalizedName(SONICLOCATORGHOST_INTERNALNAME);
        GameRegistry.registerBlock(SONICLOCATORGHOST, MadMod.ID + SONICLOCATORGHOST_INTERNALNAME);
    }

    static void createSoniclocatorTileEntity(int blockID)
    {
        MadMod.LOGGER.info("-Soniclocator Tile Entity");
        
        // Transposes block types in exchange for others using sonic waves.
        SONICLOCATOR_TILEENTITY = (BlockContainer) new SoniclocatorBlock(blockID).setUnlocalizedName(SONICLOCATOR_INTERNALNAME);
        GameRegistry.registerBlock(SONICLOCATOR_TILEENTITY, ItemBlockTooltip.class, MadMod.ID + SONICLOCATOR_INTERNALNAME);
        GameRegistry.registerTileEntity(SoniclocatorEntity.class, SONICLOCATOR_INTERNALNAME);

        // Register our rendering handles on clients and ignore them on servers.
        MadForgeMod.proxy.registerRenderingHandler(blockID);

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

    static void createClayFurnaceTileEntity(int blockID)
    {
        MadMod.LOGGER.info("-Clay Furnace Tile Entity");
        
        // A early-game block that can give huge return on investment for ores for clay and fire and time.
        CLAYFURNACE_TILEENTITY = (BlockContainer) new ClayfurnaceBlock(blockID).setUnlocalizedName(CLAYFURNACE_INTERNALNAME);
        GameRegistry.registerBlock(CLAYFURNACE_TILEENTITY, ItemBlockTooltip.class, MadMod.ID + CLAYFURNACE_INTERNALNAME);
        GameRegistry.registerTileEntity(ClayfurnaceEntity.class, CLAYFURNACE_INTERNALNAME);

        // Register our rendering handles on clients and ignore them on servers.
        MadForgeMod.proxy.registerRenderingHandler(blockID);
        
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

    static void createVOXBoxTileEntity(int blockID)
    {
        MadMod.LOGGER.info("-Announcement Box Tile Entity");
        
        // Automatic Diagnostic and Announcement System
        // AKA: Black Mesa Announcement System
        VOXBOX_TILEENTITY = (BlockContainer) new VoxBoxBlock(blockID).setUnlocalizedName(VOXBOX_INTERNALNAME);
        GameRegistry.registerBlock(VOXBOX_TILEENTITY, ItemBlockTooltip.class, MadMod.ID + VOXBOX_INTERNALNAME);
        GameRegistry.registerTileEntity(VoxBoxEntity.class, VOXBOX_INTERNALNAME);

        // Register our rendering handles on clients and ignore them on servers.
        MadForgeMod.proxy.registerRenderingHandler(blockID);
        
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

    static void createMagLoaderTileEntity(int blockID)
    {
        MadMod.LOGGER.info("-Magazine Loader Tile Entity");
        
        // Loads ammunition into pulse rifle magazine at in-human speeds.
        MAGLOADER_TILEENTITY = (BlockContainer) new MagLoaderBlock(blockID).setUnlocalizedName(MAGLOADER_INTERNALNAME);
        GameRegistry.registerBlock(MAGLOADER_TILEENTITY, ItemBlockTooltip.class, MadMod.ID + MAGLOADER_INTERNALNAME);
        GameRegistry.registerTileEntity(MagLoaderEntity.class, MAGLOADER_INTERNALNAME);

        // Register our rendering handles on clients and ignore them on servers.
        MadForgeMod.proxy.registerRenderingHandler(blockID);
        
        // Recipe for Magazine Loader.
        GameRegistry.addRecipe(new ItemStack(MAGLOADER_TILEENTITY, 1), new Object[]
        { " 1 ", 
          " 2 ", 
          "435",

          '1', new ItemStack(Block.hopperBlock, 1, 0),
          '2', new ItemStack(Block.pistonBase, 1, 0),
          '3', new ItemStack(Block.dispenser, 1, 0),
          '4', new ItemStack(MadComponents.COMPONENT_POWERSUPPLY, 1, 0),
          '5', new ItemStack(MadCircuits.CIRCUIT_COMPARATOR, 1, 0)
        });
    }

    static void createMagLoaderGhostTileEntity(int blockID)
    {
        MadMod.LOGGER.info("-Magazine Loader Ghost Block");
        
        // Acts as a collision box for upper blocks of Magazine Loader.
        MAGLOADERGHOST = new MagLoaderBlockGhost(blockID).setUnlocalizedName(MAGLOADERGHOST_INTERNALNAME);
        GameRegistry.registerBlock(MAGLOADERGHOST, MadMod.ID + MAGLOADERGHOST_INTERNALNAME);
    }

    static void createCnCMachineTileEntity(int blockID)
    {
        MadMod.LOGGER.info("-Cnc Machine Tile Entity");
        
        // Cuts out blocks of Iron into shapes for gun parts using binary codes in written books.
        CNCMACHINE_TILEENTITY = (BlockContainer) new CnCMachineBlock(blockID).setUnlocalizedName(CNCMACHINE_INTERNALNAME);
        GameRegistry.registerBlock(CNCMACHINE_TILEENTITY, ItemBlockTooltip.class, MadMod.ID + CNCMACHINE_INTERNALNAME);
        GameRegistry.registerTileEntity(CnCMachineEntity.class, CNCMACHINE_INTERNALNAME);

        // Register our rendering handles on clients and ignore them on servers.
        MadForgeMod.proxy.registerRenderingHandler(blockID);
        
        // Recipe for CnC machine.
        GameRegistry.addRecipe(new ItemStack(CNCMACHINE_TILEENTITY, 1), new Object[]
        { "456", 
          "212", 
          "232",

          '1', new ItemStack(Block.sand, 64, 0),
          '2', new ItemStack(Block.obsidian, 1, 0),
          '3', new ItemStack(MadComponents.COMPONENT_POWERSUPPLY, 1, 0),
          '4', new ItemStack(MadCircuits.CIRCUIT_EMERALD, 1, 0),
          '5', new ItemStack(Block.pistonBase, 1, 0),
          '6', new ItemStack(MadComponents.COMPONENT_CPU, 1, 0),
        });
        
        // Machine recipes for converting iron blocks into weapon parts from binary code in written book.
        CnCMachineRecipes.addSmeltingResult("m41a barrel", new ItemStack(MadComponents.COMPONENT_PULSERIFLEBARREL), 0.42F);
        CnCMachineRecipes.addSmeltingResult("m41a bolt", new ItemStack(MadComponents.COMPONENT_PULSERIFLEBOLT), 0.42F);
        CnCMachineRecipes.addSmeltingResult("m41a reciever", new ItemStack(MadComponents.COMPONENT_PULSERIFLERECIEVER), 0.42F);
        CnCMachineRecipes.addSmeltingResult("m41a trigger", new ItemStack(MadComponents.COMPONENT_PULSERIFLETRIGGER), 0.42F);
        CnCMachineRecipes.addSmeltingResult("m41a magazine", new ItemStack(MadWeapons.WEAPONITEM_MAGAZINEITEM, 16), 0.42F);
        CnCMachineRecipes.addSmeltingResult("m41a bullets", new ItemStack(MadComponents.COMPONENT_PULSERIFLEBULLETCASING, 64), 0.42F);
        CnCMachineRecipes.addSmeltingResult("m41a grenade", new ItemStack(MadComponents.COMPONENT_PULSERIFLEGRENADECASING, 32), 0.42F);
    }

    static void createCnCMachineGhostTileEntity(int blockID)
    {
        MadMod.LOGGER.info("-CnC Machine Ghost Block");
        
        // Acts as a collision box for upper blocks of CnC Machine.
        CNCMACHINEGHOST_TILEENTITY = (Block) new CnCMachineBlockGhost(blockID).setUnlocalizedName(CNCMACHINEGHOST_INTERNALNAME);
        GameRegistry.registerBlock(CNCMACHINEGHOST_TILEENTITY, MadMod.ID + CNCMACHINEGHOST_INTERNALNAME);
    }
}

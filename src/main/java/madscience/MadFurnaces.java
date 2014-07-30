package madscience;

import madscience.factory.mod.MadMod;
import madscience.items.ItemBlockTooltip;
import madscience.tile.SoniclocatorEntity;
import madscience.tile.cncmachine.CnCMachineBlock;
import madscience.tile.cncmachine.CnCMachineBlockGhost;
import madscience.tile.cncmachine.CnCMachineEntity;
import madscience.tile.cncmachine.CnCMachineRecipes;
import madscience.tile.magloader.MagLoaderBlock;
import madscience.tile.magloader.MagLoaderBlockGhost;
import madscience.tile.magloader.MagLoaderEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class MadFurnaces
{
    // -------------
    // TILE ENTITIES
    // -------------

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

    static void createMagLoaderTileEntity(int blockID)
    {
        MadMod.log().info("-Magazine Loader Tile Entity");
        
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
        MadMod.log().info("-Magazine Loader Ghost Block");
        
        // Acts as a collision box for upper blocks of Magazine Loader.
        MAGLOADERGHOST = new MagLoaderBlockGhost(blockID).setUnlocalizedName(MAGLOADERGHOST_INTERNALNAME);
        GameRegistry.registerBlock(MAGLOADERGHOST, MadMod.ID + MAGLOADERGHOST_INTERNALNAME);
    }

    static void createCnCMachineTileEntity(int blockID)
    {
        MadMod.log().info("-Cnc Machine Tile Entity");
        
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
        MadMod.log().info("-CnC Machine Ghost Block");
        
        // Acts as a collision box for upper blocks of CnC Machine.
        CNCMACHINEGHOST_TILEENTITY = new CnCMachineBlockGhost(blockID).setUnlocalizedName(CNCMACHINEGHOST_INTERNALNAME);
        GameRegistry.registerBlock(CNCMACHINEGHOST_TILEENTITY, MadMod.ID + CNCMACHINEGHOST_INTERNALNAME);
    }
}

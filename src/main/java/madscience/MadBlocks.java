package madscience;

import madscience.blocks.abominationegg.AbominationEggBlock;
import madscience.blocks.abominationegg.AbominationEggMobSpawnerTileEntity;
import madscience.blocks.enderslime.EnderslimeBlock;
import madscience.items.ItemBlockTooltip;
import net.minecraft.block.Block;
import cpw.mods.fml.common.registry.GameRegistry;

public class MadBlocks
{
    // Abomination Egg
    public static Block ABOMINATIONEGGBLOCK;
    public static final String ABOMINATIONEGG_INTERNALNAME = "abominationEgg";
    
    // Enderslime Block
    public static Block ENDERSLIMEBLOCK;
    public static final String ENDERSLIMEBLOCK_INTERNALNAME = "enderslimeBlock";

    public static void createAbominationEgg(int blockID)
    {
        MadScience.logger.info("-Abomination Egg");
        ABOMINATIONEGGBLOCK = new AbominationEggBlock(blockID).setUnlocalizedName(ABOMINATIONEGG_INTERNALNAME);

        // Register the block with the world.
        GameRegistry.registerBlock(ABOMINATIONEGGBLOCK, ItemBlockTooltip.class, MadScience.ID + ABOMINATIONEGGBLOCK.getUnlocalizedName().substring(5));
        GameRegistry.registerTileEntity(AbominationEggMobSpawnerTileEntity.class, ABOMINATIONEGGBLOCK.getUnlocalizedName());

        // Register our rendering handles on clients and ignore them on servers.
        MadScience.proxy.registerRenderingHandler(blockID);
    }

    public static void createEnderslimeBlock(int blockID)
    {
        MadScience.logger.info("-Enderslime Block");
        ENDERSLIMEBLOCK = new EnderslimeBlock(blockID).setUnlocalizedName(ENDERSLIMEBLOCK_INTERNALNAME);

        // Register the block with the world.
        GameRegistry.registerBlock(ENDERSLIMEBLOCK, ItemBlockTooltip.class, MadScience.ID + ENDERSLIMEBLOCK.getUnlocalizedName().substring(5));

        // Register our rendering handles on clients and ignore them on servers.
        MadScience.proxy.registerRenderingHandler(blockID);
    }
}

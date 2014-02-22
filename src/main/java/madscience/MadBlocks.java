package madscience;

import madscience.blocks.abominationegg.AbominationEggBlock;
import madscience.blocks.abominationegg.AbominationEggMobSpawnerTileEntity;
import net.minecraft.block.Block;
import cpw.mods.fml.common.registry.GameRegistry;

public class MadBlocks
{
    public static Block ABOMINATIONEGGBLOCK;
    public static final String ABOMINATIONEGG_INTERNALNAME = "abominationEgg";

    public static void createAbominationEgg(int blockID)
    {
        // Abomination Egg
        ABOMINATIONEGGBLOCK = new AbominationEggBlock(blockID).setUnlocalizedName(ABOMINATIONEGG_INTERNALNAME);

        // Register the block with the world.
        GameRegistry.registerBlock(ABOMINATIONEGGBLOCK, MadScience.ID + ABOMINATIONEGGBLOCK.getUnlocalizedName().substring(5));
        GameRegistry.registerTileEntity(AbominationEggMobSpawnerTileEntity.class, ABOMINATIONEGGBLOCK.getUnlocalizedName());

        // Register our rendering handles on clients and ignore them on servers.
        MadScience.proxy.registerRenderingHandler(blockID);
    }
}

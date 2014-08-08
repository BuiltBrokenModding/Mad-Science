package madscience;

import madscience.blocks.enderslime.EnderslimeBlock;
import madscience.factory.block.MadBlockTooltip;
import madscience.factory.mod.MadMod;
import net.minecraft.block.Block;
import cpw.mods.fml.common.registry.GameRegistry;

public class MadBlocks
{
    // Enderslime Block
    public static Block ENDERSLIMEBLOCK;
    public static final String ENDERSLIMEBLOCK_INTERNALNAME = "enderslimeBlock";

    static void createEnderslimeBlock(int blockID)
    {
        MadMod.log().info("-Enderslime Block");
        ENDERSLIMEBLOCK = new EnderslimeBlock(blockID).setUnlocalizedName(ENDERSLIMEBLOCK_INTERNALNAME);

        // Register the block with the world.
        GameRegistry.registerBlock(ENDERSLIMEBLOCK, MadBlockTooltip.class, MadMod.ID + ENDERSLIMEBLOCK.getUnlocalizedName().substring(5));

        // Register our rendering handles on clients and ignore them on servers.
        MadForgeMod.proxy.registerRenderingHandler(blockID);
    }
}

package madscience;

import madscience.blocks.enderslime.EnderslimeBlock;
import madscience.factory.mod.MadMod;
import net.minecraft.block.Block;

public class MadBlocks
{
    // Enderslime Block
    public static Block ENDERSLIMEBLOCK;
    public static final String ENDERSLIMEBLOCK_INTERNALNAME = "enderslimeBlock";

    static void createEnderslimeBlock(int blockID)
    {
        MadMod.log().info("-Enderslime Block");
        ENDERSLIMEBLOCK = new EnderslimeBlock(blockID).setUnlocalizedName(ENDERSLIMEBLOCK_INTERNALNAME);
    }
}

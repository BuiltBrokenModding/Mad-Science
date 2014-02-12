package madscience.fluids.dna;

import madscience.MadFluids;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.ForgeSubscribe;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class LiquidDNARender implements ISimpleBlockRenderingHandler
{
    @Override
    public int getRenderId()
    {
        // Returns the hooked render ID from mod initialization.
        return MadFluids.liquidDNARenderID;
    }

    @ForgeSubscribe
    public void postStitch(TextureStitchEvent.Post event)
    {
        MadFluids.LIQUIDDNA.setIcons(MadFluids.LIQUIDDNA_BLOCK.getBlockTextureFromSide(0), MadFluids.LIQUIDDNA_BLOCK.getBlockTextureFromSide(1));
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
    {
        // Nothing to see here.
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
    {
        // Use the rendering ID we hooked in client proxy to render our fluid the way we want.
        if (block.getRenderType() != MadFluids.liquidDNARenderID)
        {
            return true;
        }

        renderer.renderBlockFluids(block, x, y, z);
        return true;
    }

    @Override
    public boolean shouldRender3DInInventory()
    {
        // Do not render this block in full 3D, only as sprite.
        return false;
    }

}

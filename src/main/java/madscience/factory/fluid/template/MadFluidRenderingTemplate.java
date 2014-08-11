package madscience.factory.fluid.template;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.ForgeSubscribe;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MadFluidRenderingTemplate implements ISimpleBlockRenderingHandler
{
    private int currentRenderID = -1;
    
    public MadFluidRenderingTemplate()
    {
        super();
    }
    
    @Override
    public int getRenderId()
    {
        // Assign temporary render ID until we get this sorted out.
        if (currentRenderID == -1)
        {
            currentRenderID = RenderingRegistry.getNextAvailableRenderId();
        }
        
        // If render ID is set to anything other than default then return that.
        if (currentRenderID != -1)
        {
            return currentRenderID;
        }
        
        // Default response is to return negative one to stop rendering.
        return currentRenderID;
    }

    @ForgeSubscribe
    public void postStitch(TextureStitchEvent.Post event) // NO_UCD (unused code)
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
        if (block.getRenderType() != currentRenderID)
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

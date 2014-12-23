package madscience.fluid;


import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import madscience.factory.MadFluidFactory;
import madscience.product.MadFluidFactoryProduct;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.ForgeSubscribe;


@SideOnly(Side.CLIENT)
public class MadFluidRenderingTemplate implements ISimpleBlockRenderingHandler
{
    private int currentRenderID = - 1;

    private String registeredFluidName;
    private MadFluidFactoryProduct registeredFluid;

    public MadFluidRenderingTemplate(MadFluidFactoryProduct madFluidFactoryProduct)
    {
        super();

        this.registeredFluid = madFluidFactoryProduct;
        this.registeredFluidName = madFluidFactoryProduct.getFluidName();
    }

    public MadFluidFactoryProduct getRegisteredFluid()
    {
        if (this.registeredFluid == null)
        {
            MadFluidFactoryProduct reloadedFluid = MadFluidFactory.instance().getFluidInfo( this.registeredFluidName );
            this.registeredFluid = reloadedFluid;
            this.registeredFluidName = reloadedFluid.getFluidName();
        }

        return this.registeredFluid;
    }

    @Override
    public int getRenderId()
    {
        // Assign temporary render ID until we get this sorted out.
        if (currentRenderID == - 1)
        {
            currentRenderID = RenderingRegistry.getNextAvailableRenderId();
        }

        // If render ID is set to anything other than default then return that.
        if (currentRenderID != - 1)
        {
            return currentRenderID;
        }

        // Default response is to return negative one to stop rendering.
        return currentRenderID;
    }

    @ForgeSubscribe
    public void postStitch(TextureStitchEvent.Post event) // NO_UCD (unused code)
    {
        this.getRegisteredFluid().getFluid().setIcons( this.getRegisteredFluid().getFluidBlock().getBlockTextureFromSide( 0 ),
                                                       this.getRegisteredFluid().getFluidBlock().getBlockTextureFromSide( 1 ) );
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
    {
        // Nothing to see here.
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world,
                                    int x,
                                    int y,
                                    int z,
                                    Block block,
                                    int modelId,
                                    RenderBlocks renderer)
    {
        // Use the rendering ID we hooked in client proxy to render our fluid the way we want.
        if (block.getRenderType() != currentRenderID)
        {
            return true;
        }

        renderer.renderBlockFluids( block,
                                    x,
                                    y,
                                    z );
        return true;
    }

    @Override
    public boolean shouldRender3DInInventory()
    {
        // Do not render this block in full 3D, only as sprite.
        return false;
    }
}

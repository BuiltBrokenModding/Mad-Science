package madscience.fluid;


import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import madscience.product.FluidFactoryProduct;
import net.minecraft.block.Block;
import net.minecraft.util.Icon;
import net.minecraftforge.fluids.Fluid;


public class FluidTemplate extends Fluid
{
    private FluidFactoryProduct registeredFluid = null;

    private String registeredFluidName;

    public FluidTemplate(FluidFactoryProduct fluidFactoryProduct)
    {
        super( fluidFactoryProduct.getFluidName() );

        this.registeredFluid = fluidFactoryProduct;
        this.registeredFluidName = fluidFactoryProduct.getFluidName();

        this.setUnlocalizedName( fluidFactoryProduct.getFluidName() );

        // Give the classic minecraft fluid the same ID as our forge fluid registry class.
        this.setBlockID( fluidFactoryProduct.getFluidID() );

        // used by the block to work out how much it slows entities.
        // Example: 3
        this.setDensity( fluidFactoryProduct.getDensity() );

        // used by the block to work out how fast it flows
        // Example: 4000
        this.setViscosity( fluidFactoryProduct.getViscosity() );

        // Set how well light can pass through this block.
        // Example: 5
        this.setLuminosity( fluidFactoryProduct.getLuminosity() );
    }

    public FluidFactoryProduct getRegisteredFluid()
    {
        return this.registeredFluid;
    }

    @SideOnly(Side.CLIENT)
    public Icon getIcon(int side, int meta) // NO_UCD (unused code)
    {
        // Use the same default minecraft icon for water (for now).
        return Block.waterMoving.getIcon( side,
                                          meta );
    }

    /**
     * Returns the unlocalized name of this fluid.
     */
    @Override
    public String getUnlocalizedName()
    {
        return "tile." + this.unlocalizedName + ".name";
    }
}

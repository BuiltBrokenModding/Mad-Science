package madscience.factory.fluid;

import madscience.factory.product.MadFluidFactoryProduct;
import net.minecraft.block.Block;
import net.minecraft.util.Icon;
import net.minecraftforge.fluids.Fluid;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MadFluidTemplate extends Fluid
{
    private MadFluidFactoryProduct registeredFluid = null;
    
    private String registeredFluidName;
    
    public MadFluidTemplate(MadFluidFactoryProduct madFluidFactoryProduct)
    {
        super(madFluidFactoryProduct.getFluidName());
        
        this.registeredFluid = madFluidFactoryProduct;
        this.registeredFluidName = madFluidFactoryProduct.getFluidName();
        
        this.setUnlocalizedName(madFluidFactoryProduct.getFluidName());
        
        // Give the classic minecraft fluid the same ID as our forge fluid registry class.
        this.setBlockID(madFluidFactoryProduct.getFluidID());
        
        // used by the block to work out how much it slows entities.
        // Example: 3
        this.setDensity(madFluidFactoryProduct.getDensity());

        // used by the block to work out how fast it flows
        // Example: 4000
        this.setViscosity(madFluidFactoryProduct.getViscosity());

        // Set how well light can pass through this block.
        // Example: 5
        this.setLuminosity(madFluidFactoryProduct.getLuminosity());
    }
    
    public MadFluidFactoryProduct getRegisteredFluid()
    {
        return this.registeredFluid;
    }

    @SideOnly(Side.CLIENT)
    public Icon getIcon(int side, int meta) // NO_UCD (unused code)
    {
        // Use the same default minecraft icon for water (for now).
        return Block.waterMoving.getIcon(side, meta);
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

package madscience.fluid;


import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import madscience.ModMetadata;
import madscience.factory.FluidFactory;
import madscience.mod.ModLoader;
import madscience.product.FluidFactoryProduct;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;


public class FluidBlockTemplate extends BlockFluidClassic
{
    @SideOnly(Side.CLIENT)
    private Icon stillIcon;

    @SideOnly(Side.CLIENT)
    private Icon flowingIcon;

    private String registeredFluidName;
    private FluidFactoryProduct registeredFluid;

    public FluidBlockTemplate(FluidFactoryProduct fluidFactoryProduct)
    {
        super( fluidFactoryProduct.getFluidID(),
               fluidFactoryProduct.getFluid(),
               Material.water );

        this.registeredFluid = fluidFactoryProduct;
        this.registeredFluidName = fluidFactoryProduct.getFluidName();

        // Using same name that we registered our fluid with for the block.
        this.setUnlocalizedName( fluidFactoryProduct.getFluidName() );

        // Only add the fluid source block to creative tab if requested.
        if (fluidFactoryProduct.getShowFluidBlockInCreativeTab())
        {
            this.setCreativeTab( ModLoader.getCreativeTab() );
        }
    }

    public FluidFactoryProduct getRegisteredFluid()
    {
        if (this.registeredFluid == null)
        {
            FluidFactoryProduct reloadedFluid = FluidFactory.instance().getFluidInfo( this.registeredFluidName );
            this.registeredFluid = reloadedFluid;
            this.registeredFluidName = reloadedFluid.getFluidName();
        }

        return this.registeredFluid;
    }

    @Override
    public boolean canDisplace(IBlockAccess world, int x, int y, int z)
    {
        if (world.getBlockMaterial( x,
                                    y,
                                    z ).isLiquid())
        {
            return false;
        }

        return super.canDisplace( world,
                                  x,
                                  y,
                                  z );
    }

    @Override
    public boolean displaceIfPossible(World world, int x, int y, int z)
    {
        if (world.getBlockMaterial( x,
                                    y,
                                    z ).isLiquid())
        {
            return false;
        }

        return super.displaceIfPossible( world,
                                         x,
                                         y,
                                         z );
    }

    @Override
    public Icon getIcon(int side, int meta)
    {
        return (side == 0 || side == 1)
               ? stillIcon
               : flowingIcon;
    }

    @Override
    public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity entity)
    {
        // Hurts any mob player or not that touches fluid.
        // entity.attackEntityFrom(DamageSource.generic, 5);

        /* // Allows the potion affect to effect players if (entity instanceof EntityPlayer) { ((EntityPlayer) entity).addPotionEffect(new PotionEffect(Potion.heal.getId(), 2 * 20, 0)); } */
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister register)
    {
        stillIcon = register.registerIcon( ModMetadata.ID + ":" +
                                           this.getRegisteredFluid().getIconStillPath() );
        flowingIcon = register.registerIcon( ModMetadata.ID + ":" +
                                             this.getRegisteredFluid().getIconFlowingPath() );
    }
}

package madscience.factory.fluid.template;

import madscience.factory.fluid.prefab.MadFluidFactoryProduct;
import madscience.factory.mod.MadMod;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MadFluidBlockTemplate extends BlockFluidClassic
{
    @SideOnly(Side.CLIENT)
    private Icon stillIcon;
    
    @SideOnly(Side.CLIENT)
    private Icon flowingIcon;
    
    public MadFluidBlockTemplate(MadFluidFactoryProduct madFluidFactoryProduct)
    {
        super(madFluidFactoryProduct.getFluidID(), madFluidFactoryProduct.getFluid(), Material.water);
        
        // Using same name that we registered our fluid with for the block.
        this.setUnlocalizedName(madFluidFactoryProduct.getFluidName());

        // Add the block to the specific tab in creative mode.
        this.setCreativeTab(MadMod.getCreativeTab());
    }

    @Override
    public boolean canDisplace(IBlockAccess world, int x, int y, int z)
    {
        if (world.getBlockMaterial(x, y, z).isLiquid())
        {
            return false;
        }

        return super.canDisplace(world, x, y, z);
    }

    /*
     * @Override public int colorMultiplier(IBlockAccess iblockaccess, int i, int j, int k) { // Changes the color of the default minecraft water to be blood red. // Note: HEX color can be changed to any HEX based color code. return 0x8A0707; } */

    @Override
    public boolean displaceIfPossible(World world, int x, int y, int z)
    {
        if (world.getBlockMaterial(x, y, z).isLiquid())
        {
            return false;
        }

        return super.displaceIfPossible(world, x, y, z);
    }

    @Override
    public Icon getIcon(int side, int meta)
    {
        return (side == 0 || side == 1) ? stillIcon : flowingIcon;
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
        stillIcon = register.registerIcon(MadMod.ID + ":" + MadFluids.LIQUIDDNA_INTERNALNAME + "_still");
        flowingIcon = register.registerIcon(MadMod.ID + ":" + MadFluids.LIQUIDDNA_INTERNALNAME + "_flowing");
    }
}

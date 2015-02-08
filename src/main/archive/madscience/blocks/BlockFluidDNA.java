package madscience.tiles;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import madscience.MadFluids;
import madscience.MadScience;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class BlockFluidDNA extends BlockFluidClassic
{
    @SideOnly(Side.CLIENT)
    protected IIcon stillIcon;
    @SideOnly(Side.CLIENT)
    protected IIcon flowingIcon;

    public BlockFluidDNA(Fluid fluid)
    {
        super(fluid, Material.water);
    }

    @Override
    public boolean canDisplace(IBlockAccess world, int x, int y, int z)
    {
        Block block = world.getBlock(x, y, z);
        if (block != null && block.getMaterial() == Material.water)
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
        Block block = world.getBlock(x, y, z);
        if (block != null && block.getMaterial() == Material.water)
        {
            return false;
        }

        return super.displaceIfPossible(world, x, y, z);
    }

    @Override
    public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity entity)
    {
        if(getFluid() == MadScience.liquidMutantDNA)
        {
            entity.attackEntityFrom(DamageSource.generic, 1);
        }
    }


    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {
        if(getFluid() == MadScience.liquidDNA)
        {
            stillIcon = register.registerIcon(MadScience.ID + ":" + MadFluids.LIQUIDDNA_INTERNALNAME + "_still");
            flowingIcon = register.registerIcon(MadScience.ID + ":" + MadFluids.LIQUIDDNA_INTERNALNAME + "_flowing");
        }
        else
        {
            stillIcon = register.registerIcon(MadScience.ID + ":" + MadFluids.LIQUIDDNA_MUTANT_INTERNALNAME + "_still");
            flowingIcon = register.registerIcon(MadScience.ID + ":" + MadFluids.LIQUIDDNA_MUTANT_INTERNALNAME + "_flowing");
        }
    }
}

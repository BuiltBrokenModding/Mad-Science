package madscience.blocks;

import com.builtbroken.mc.core.registry.IRegistryInit;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import madscience.MadScience;
import madscience.mobs.abomination.EntityAbomination;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDragonEgg;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockAbominationEgg extends BlockDragonEgg implements ITileEntityProvider, IRegistryInit
{
    public BlockAbominationEgg()
    {
        this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 1.0F, 0.9375F);
        this.setHardness(5.0F);
        this.setResistance(1.0F);
    }

    @Override
    public void onRegistered()
    {
        GameRegistry.registerTileEntity(TileEntityAbominationEgg.class, "TileAbominationEgg");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        // Make sure you set this as your TileEntity class!
        return new TileEntityAbominationEgg();
    }

    /**
     * The type of render function that is called for this block
     */
    @Override
    public int getRenderType()
    {
        return 27;
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube? This determines whether or not to render the shared face of two adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    @Override
    public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
        par1World.scheduleBlockUpdate(par2, par3, par4, this, this.tickRate(par1World));
    }

    /**
     * Called when the block is clicked by a player. Args: x, y, z, entityPlayer
     */
    @Override
    public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer)
    {
        // We override this function so nothing funny happens like with dragon egg.
    }

    public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
        return true;
    }

    @Override
    public void onBlockDestroyedByPlayer(World par1World, int par2, int par3, int par4, int par5)
    {
        // Play nasty sound of egg exploding into meat chunks.
        par1World.playSoundEffect(par2 + 0.5D, par3 + 0.5D, par4 + 0.5D, EntityAbomination.ABOMINATION_EGGPOP, 1.0F, 1.0F);
        par1World.setBlock(par2, par3, par4, MadScience.blockMutantDNA);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase living, ItemStack stack)
    {
        super.onBlockPlacedBy(world, x, y, z, living, stack);
        int dir = MathHelper.floor_double((living.rotationYaw * 4F) / 360F + 0.5D) & 3;
        world.setBlockMetadataWithNotify(x, y, z, dir, 0);
        world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, EntityAbomination.ABOMINATION_EGG, 1.0F, 1.0F);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
    {
        world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
    }

    @Override
    public int quantityDropped(Random rand)
    {
        return 0;
    }

    // This is the icon to use for showing the block in your hand.
    @Override
    public void registerBlockIcons(IIconRegister icon)
    {
        this.blockIcon = icon.registerIcon(MadScience.ID + ":abominationEgg");
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        return true;
    }
}

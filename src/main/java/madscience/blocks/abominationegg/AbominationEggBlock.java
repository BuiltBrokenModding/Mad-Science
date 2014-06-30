package madscience.blocks.abominationegg;

import java.util.Random;

import madscience.MadBlocks;
import madscience.MadEntities;
import madscience.MadFluids;
import madscience.MadScience;
import madscience.mobs.abomination.AbominationSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDragonEgg;
import net.minecraft.block.BlockSand;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFallingSand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class AbominationEggBlock extends BlockDragonEgg implements ITileEntityProvider
{
    private AbominationEggMobSpawnerTileEntity lastPlacedTileEntity;

    public AbominationEggBlock(int par1)
    {
        super(par1);
        this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 1.0F, 0.9375F);
        this.setHardness(5.0F);
        this.setResistance(1.0F);
        this.setCreativeTab(MadEntities.tabMadScience);
    }

    @Override
    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
    {
        int l = par1World.getBlockId(par2, par3, par4);
        Block block = Block.blocksList[l];
        return block == null || block.isBlockReplaceable(par1World, par2, par3, par4);
    }

    @Override
    public TileEntity createNewTileEntity(World world)
    {
        // Make sure you set this as your TileEntity class!
        return new AbominationEggMobSpawnerTileEntity();
    }

    /** Drops the block items with a specified chance of dropping the specified items */
    @Override
    public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7)
    {
        super.dropBlockAsItemWithChance(par1World, par2, par3, par4, par5, par6, par7);
    }

    /** Checks if the dragon egg can fall down, and if so, makes it fall. */
    private void fallIfPossible(World par1World, int par2, int par3, int par4)
    {
        if (BlockSand.canFallBelow(par1World, par2, par3 - 1, par4) && par3 >= 0)
        {
            byte b0 = 32;

            if (!BlockSand.fallInstantly && par1World.checkChunksExist(par2 - b0, par3 - b0, par4 - b0, par2 + b0, par3 + b0, par4 + b0))
            {
                EntityFallingSand entityfallingsand = new EntityFallingSand(par1World, par2 + 0.5F, par3 + 0.5F, par4 + 0.5F, this.blockID);
                par1World.spawnEntityInWorld(entityfallingsand);
            }
            else
            {
                par1World.setBlockToAir(par2, par3, par4);

                while (BlockSand.canFallBelow(par1World, par2, par3 - 1, par4) && par3 > 0)
                {
                    --par3;
                }

                if (par3 > 0)
                {
                    par1World.setBlock(par2, par3, par4, this.blockID, 0, 2);
                }
            }
        }
    }

    @Override
    public int getExpDrop(World world, int data, int enchantmentLevel)
    {
        return 15 + world.rand.nextInt(15) + world.rand.nextInt(15);
    }

    @Override
    public int getFlammability(IBlockAccess world, int x, int y, int z, int metadata, ForgeDirection face)
    {
        return 250;
    }

    /** The type of render function that is called for this block */
    @Override
    public int getRenderType()
    {
        return 27;
    }

    @Override
    @SideOnly(Side.CLIENT)
    /**
     * only called by clickMiddleMouseButton , and passed to inventory.setCurrentItem (along with isCreative)
     */
    public int idPicked(World par1World, int par2, int par3, int par4)
    {
        return 0;
    }

    @Override
    public boolean isFlammable(IBlockAccess world, int x, int y, int z, int metadata, ForgeDirection face)
    {
        return getFlammability(world, x, y, z, metadata, face) > 0;
    }

    /** Is this block (a) opaque and (b) a full 1m cube? This determines whether or not to render the shared face of two adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block. */
    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    /** Called upon block activation (right click on the block.) */
    @Override
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        // Override main ender dragon egg event to prevent it from teleporting away.
        return true;
    }

    /** Called whenever the block is added into the world. Args: world, x, y, z */
    @Override
    public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
        par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, this.tickRate(par1World));
    }

    /** Called when the block is clicked by a player. Args: x, y, z, entityPlayer */
    @Override
    public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer)
    {
        // We override this function so nothing funny happens like with dragon egg.
    }

    @Override
    public void onBlockDestroyedByExplosion(World par1World, int par2, int par3, int par4, Explosion par5Explosion)
    {
        // Play nasty sound of the egg exploding into meat chunks.
        par1World.playSoundEffect(par2 + 0.5D, par3 + 0.5D, par4 + 0.5D, AbominationSounds.ABOMINATION_EGGPOP, 1.0F, 1.0F);
        par1World.setBlock(par2, par3, par4, MadFluids.LIQUIDDNA_MUTANT_BLOCK.blockID);
    }

    @Override
    public void onBlockDestroyedByPlayer(World par1World, int par2, int par3, int par4, int par5)
    {
        // Play nasty sound of egg exploding into meat chunks.
        par1World.playSoundEffect(par2 + 0.5D, par3 + 0.5D, par4 + 0.5D, AbominationSounds.ABOMINATION_EGGPOP, 1.0F, 1.0F);
        par1World.setBlock(par2, par3, par4, MadFluids.LIQUIDDNA_MUTANT_BLOCK.blockID);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase living, ItemStack stack)
    {
        super.onBlockPlacedBy(world, x, y, z, living, stack);
        lastPlacedTileEntity = (AbominationEggMobSpawnerTileEntity) world.getBlockTileEntity(x, y, z);
        int dir = MathHelper.floor_double((living.rotationYaw * 4F) / 360F + 0.5D) & 3;
        world.setBlockMetadataWithNotify(x, y, z, dir, 0);
        world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, AbominationSounds.ABOMINATION_EGG, 1.0F, 1.0F);
    }

    /** Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are their own) Args: x, y, z, neighbor blockID */
    @Override
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
        par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, this.tickRate(par1World));
    }

    /** Returns the quantity of items to drop on block destruction. */
    @Override
    public int quantityDropped(Random par1Random)
    {
        return 0;
    }

    // This is the icon to use for showing the block in your hand.
    @Override
    public void registerIcons(IconRegister icon)
    {
        this.blockIcon = icon.registerIcon(MadScience.ID + ":" + MadBlocks.ABOMINATIONEGG_INTERNALNAME);
    }

    /** If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc) */
    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    /**
     * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
     * coordinates.  Args: blockAccess, x, y, z, side
     */
    public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        return true;
    }

    /** How many world ticks before ticking */
    @Override
    public int tickRate(World par1World)
    {
        return 5;
    }

    /** Ticks the block if it's been scheduled */
    @Override
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        this.fallIfPossible(par1World, par2, par3, par4);
    }
}

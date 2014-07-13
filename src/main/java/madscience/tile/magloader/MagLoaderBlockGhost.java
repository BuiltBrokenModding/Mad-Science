package madscience.tile.magloader;

import java.util.Random;

import madscience.MadMachines;
import madscience.MadScience;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import universalelectricity.api.UniversalElectricity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MagLoaderBlockGhost extends Block
{
    public MagLoaderBlockGhost(int blockID)
    {
        // 'Ghost' block for Magazine Loader that serves as physics collision and proxy to GUI.
        super(blockID, UniversalElectricity.machine);

        // Determines how many hits it takes to break the block.
        this.setHardness(3.5F);

        // Determines how resistant this block is to explosions.
        this.setResistance(2000.0F);

        // Default 1x1x1 bounds that all point to the same type of block and open same GUI.
        this.setBlockBounds(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, int blockID, int metadata)
    {
        // Breaks the main cryotube block which will destroy all of us!
        switch (metadata)
        {
        case 1:
            world.setBlockToAir(x, y - 1, z);
            break;
        }

        super.breakBlock(world, x, y, z, blockID, metadata);
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata)
    {
        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int side, int meta)
    {
        return this.blockIcon;
    }

    @Override
    public int getRenderType()
    {
        return -1;
    }

    @Override
    public boolean hasTileEntity(int meta)
    {
        return false;
    }

    // Returns the ID of the items to drop on destruction.
    @Override
    public int idDropped(int par1, Random par2Random, int par3)
    {
        // Since we are a ghost we return our parent object.
        return MadMachines.MAGLOADER_TILEENTITY.blockID;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int idPicked(World par1World, int par2, int par3, int par4)
    {
        // Ghost objects return expected parent block.
        return MadMachines.MAGLOADER_TILEENTITY.blockID;
    }

    @Override
    public boolean isBlockSolid(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        // Ghost
        return false;
    }

    @Override
    public boolean isOpaqueCube()
    {
        // Ghost
        return false;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
    {
        // Called upon block activation (right click on the block.)
        if (world.isRemote)
        {
            return true;
        }
        else if (!player.isSneaking())
        {
            // Use our metadata to help us locate our parent tile-entity in the world.
            int meta = world.getBlockMetadata(x, y, z);
            MagLoaderEntity tileentityfurnace = null;

            // Hook the tile-entity now that we have an idea where it is.
            switch (meta)
            {
            case 1:
                tileentityfurnace = (MagLoaderEntity) world.getBlockTileEntity(x, y - 1, z);
                break;
            }

            // If our parent tile-entity has been found open the GUI based on offset.
            if (tileentityfurnace != null)
            {
                switch (meta)
                {
                case 1:
                    player.openGui(MadScience.instance, MadMachines.MAGLOADER_TILEENTITY.blockID, world, x, y - 1, z);
                    break;
                }
            }
            return true;
        }
        else
        {
            return false;
        }
    }

    // This is the icon to use for showing the block in your hand.
    @Override
    public void registerIcons(IconRegister icon)
    {
        // Since we are a ghost we use our parents icon information.
        this.blockIcon = icon.registerIcon(MadScience.ID + ":" + MadMachines.MAGLOADER_INTERNALNAME);
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        // Ghost
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        // Ghost
        return false;
    }
}

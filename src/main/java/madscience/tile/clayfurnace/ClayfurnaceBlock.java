package madscience.tile.clayfurnace;

import java.util.List;
import java.util.Random;

import madscience.MadEntities;
import madscience.MadForgeMod;
import madscience.MadFurnaces;
import madscience.factory.mod.MadMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ClayfurnaceBlock extends BlockContainer
{
    // Is the random generator used by furnace to drop the inventory contents in
    // random directions.
    private Random furnaceRand = new Random();

    // This flag is used to prevent the furnace inventory to be dropped upon
    // block removal, is used internally when the furnace block changes from
    // idle to active and vice-versa.
    private boolean keepFurnaceInventory;

    // Stores instance of our tile entity on the client.
    private ClayfurnaceEntity ENTITY;

    public ClayfurnaceBlock(int id)
    {
        // Solid like a rock baby.
        super(id, Material.clay);

        // Set what tab we show up in creative tab.
        this.setCreativeTab(MadEntities.tabMadScience);

        // Gives clayfurnace same resistance as iron block.
        this.setHardness(5.0F);
        this.setResistance(10.0F);

        // Define how big this item is we make it same size as a default block.
        this.setBlockBounds(0.0F, 0.0F, 0.00F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
    {
        super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
    }

    /** Called on server worlds only when the block has been replaced by a different block ID, or the same block with a different metadata value, but before the new metadata value is set. Args: World, x, y, z, old block ID, old metadata */
    @Override
    public void breakBlock(World world, int x, int y, int z, int blockID, int metaData)
    {
        if (!keepFurnaceInventory)
        {
            ClayfurnaceEntity tileentityfurnace = (ClayfurnaceEntity) world.getBlockTileEntity(x, y, z);

            if (tileentityfurnace != null)
            {
                for (int j1 = 0; j1 < tileentityfurnace.getSizeInputInventory(); ++j1)
                {
                    ItemStack itemstack = tileentityfurnace.getStackInSlot(j1);

                    if (itemstack != null)
                    {
                        float f = this.furnaceRand.nextFloat() * 0.8F + 0.1F;
                        float f1 = this.furnaceRand.nextFloat() * 0.8F + 0.1F;
                        float f2 = this.furnaceRand.nextFloat() * 0.8F + 0.1F;

                        while (itemstack.stackSize > 0)
                        {
                            int k1 = this.furnaceRand.nextInt(21) + 10;

                            if (k1 > itemstack.stackSize)
                            {
                                k1 = itemstack.stackSize;
                            }

                            itemstack.stackSize -= k1;
                            EntityItem entityitem = new EntityItem(world, x + f, y + f1, z + f2, new ItemStack(itemstack.itemID, k1, itemstack.getItemDamage()));

                            if (itemstack.hasTagCompound())
                            {
                                entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
                            }

                            float f3 = 0.05F;
                            entityitem.motionX = (float) this.furnaceRand.nextGaussian() * f3;
                            entityitem.motionY = (float) this.furnaceRand.nextGaussian() * f3 + 0.2F;
                            entityitem.motionZ = (float) this.furnaceRand.nextGaussian() * f3;

                            world.spawnEntityInWorld(entityitem);
                        }
                    }
                }

                world.func_96440_m(x, y, z, blockID);
            }
        }

        super.breakBlock(world, x, y, z, blockID, metaData);
    }

    @Override
    public boolean canBlockStay(World world, int x, int y, int z)
    {
        if (world.getBlockMaterial(x, y + 1, z).isSolid())
        {
            return false;
        }

        return true;
    }

    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z)
    {
        return !super.canPlaceBlockAt(world, x, y, z) ? false : this.canBlockStay(world, x, y, z);
    }

    @Override
    public TileEntity createNewTileEntity(World world)
    {
        // Make sure you set this as your TileEntity class!
        return new ClayfurnaceEntity();
    }

    @Override
    public int damageDropped(int metadata)
    {
        return metadata;
    }

    public TileEntity getBlockEntity()
    {
        // Returns the TileEntity used by this block.
        return new ClayfurnaceEntity();
    }

    /** If hasComparatorInputOverride returns true, the return value from this is used instead of the redstone signal strength when this block inputs to a comparator. */
    @Override
    public int getComparatorInputOverride(World par1World, int par2, int par3, int par4, int par5)
    {
        return Container.calcRedstoneFromInventory((IInventory) par1World.getBlockTileEntity(par2, par3, par4));
    }

    @Override
    public int getFlammability(IBlockAccess world, int x, int y, int z, int metadata, ForgeDirection face)
    {
        return 150;
    }

    @Override
    public int getRenderType()
    {
        return -1;
    }

    /** If this returns true, then comparators facing away from this block will use the value from getComparatorInputOverride instead of the actual redstone signal strength. */
    @Override
    public boolean hasComparatorInputOverride()
    {
        return true;
    }

    @Override
    public boolean hasTileEntity(int meta)
    {
        return true;
    }

    // Returns the ID of the items to drop on destruction.
    @Override
    public int idDropped(int par1, Random par2Random, int par3)
    {
        if (ENTITY != null && !ENTITY.hasStoppedSmoldering)
        {
            // Breaking clay furnace gives you just clay, good job user.
            return Block.hardenedClay.blockID;
        }
        else
        {
            return 0;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int idPicked(World par1World, int par2, int par3, int par4)
    {
        return MadFurnaces.CLAYFURNACE_TILEENTITY.blockID;
    }

    @Override
    public boolean isBlockNormalCube(World world, int x, int y, int z)
    {
        return false;
    }

    @Override
    public boolean isBlockSolidOnSide(World world, int x, int y, int z, ForgeDirection side)
    {
        return true;
    }

    @Override
    public boolean isFlammable(IBlockAccess world, int x, int y, int z, int metadata, ForgeDirection face)
    {
        return getFlammability(world, x, y, z, metadata, face) > 0;
    }

    // It's not an opaque cube, so you need this.
    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer player, int par6, float par7, float par8, float par9)
    {
        // Called upon block activation (right click on the block.)
        if (par1World.isRemote)
        {
            return true;
        }
        else if (!player.isSneaking())
        {
            // Open GUI on the client...
            ClayfurnaceEntity ENTITY = (ClayfurnaceEntity) par1World.getBlockTileEntity(par2, par3, par4);

            // Check if the player is using flint and steel on us.
            ItemStack compareFlintNSteel = new ItemStack(Item.flintAndSteel);
            ItemStack playerItem = player.getCurrentEquippedItem();
            if (ENTITY != null)
            {
                // Determine what we should do to the clay furnace depending on it's current state.
                if (!ENTITY.hasBeenLit)
                {
                    // IDLE OR WORKING
                    if (player != null && playerItem != null && playerItem.itemID == compareFlintNSteel.itemID)
                    {
                        par1World.playSoundEffect(par2 + 0.5D, par3 + 0.5D, par4 + 0.5D, "fire.ignite", 1.0F, 1.0F);
                        ENTITY.setLitStatus(true);
                        if (playerItem.getItemDamage() < playerItem.getMaxDamage())
                        {
                            player.getCurrentEquippedItem().attemptDamageItem(1, par1World.rand);
                        }
                        return true;
                    }
                    else
                    {
                        player.openGui(MadForgeMod.instance, this.blockID, par1World, par2, par3, par4);
                        return true;
                    }
                }
                else if (ENTITY.hasBeenLit && ENTITY.hasCompletedBurnCycle && !ENTITY.hasStoppedSmoldering && !ENTITY.hasCooledDown)
                {
                    // SMOLDERING FURNACE MODE
                    return false;
                }
                else if (ENTITY.hasBeenLit && ENTITY.hasCompletedBurnCycle && ENTITY.hasStoppedSmoldering && !ENTITY.hasCooledDown)
                {
                    // RED HOT BLOCK MODE
                    return false;
                }
                else
                {
                    return false;
                }
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z)
    {
        super.onBlockAdded(world, x, y, z);
        this.setDefaultDirection(world, x, y, z);
    }

    @Override
    public void onBlockClicked(World world, int X, int Y, int Z, EntityPlayer player)
    {
        // Called when the block is clicked by a player (left click).
        if (world.isRemote)
        {
            return;
        }
        else if (!player.isSneaking())
        {
            // Get our tile entity instance.
            ClayfurnaceEntity clayFurnace = (ClayfurnaceEntity) world.getBlockTileEntity(X, Y, Z);
            if (clayFurnace == null)
            {
                return;
            }

            if (clayFurnace.hasCooledDown)
            {
                // COOLED OFF MODE - WAITING FOR PLAYER TO HIT US
                if (player.canHarvestBlock(this))
                {
                    MadMod.LOGGER.info("Clay Furnace: Harvested player after having been cooled down!");
                    world.playSoundEffect(X + 0.5D, Y + 0.5D, Z + 0.5D, "random.anvil_land", 1.0F, 1.0F);

                    // Set ourselves to the end result we should be!
                    ItemStack finalForm = clayFurnace.createEndResult();
                    world.setBlock(X, Y, Z, finalForm.itemID);
                }
                return;
            }

            // Player broke the red hot block before it was completely cooled off.
            if (clayFurnace.hasStoppedSmoldering && !clayFurnace.hasCooledDown)
            {
                // RED HOT MODE
                world.playSoundEffect(X + 0.5D, Y + 0.5D, Z + 0.5D, "liquid.lavapop", 1.0F, 1.0F);
                world.playSoundEffect(X + 0.5D, Y + 0.5D, Z + 0.5D, "random.fizz", 1.0F, 1.0F);
                world.setBlock(X, Y, Z, Block.lavaStill.blockID);
                return;
            }

            if (clayFurnace.hasBeenLit && clayFurnace.hasCompletedBurnCycle && !clayFurnace.hasStoppedSmoldering && !clayFurnace.hasCooledDown)
            {
                // SMOLDERING FURNACE MODE
                if (player.canHarvestBlock(this))
                {
                    world.playSoundEffect(X + 0.5D, Y + 0.5D, Z + 0.5D, "dig.sand", 1.0F, 1.0F);
                    world.playSoundEffect(X + 0.5D, Y + 0.5D, Z + 0.5D, "random.fizz", 1.0F, 1.0F);
                    clayFurnace.TEXTURE = "models/" + MadFurnaces.CLAYFURNACE_INTERNALNAME + "/redhot0.png";
                    clayFurnace.hasStoppedSmoldering = true;
                    clayFurnace.animationCurrentFrame = 0;
                    return;
                }
            }
        }
        else
        {
            return;
        }
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase living, ItemStack stack)
    {
        super.onBlockPlacedBy(world, x, y, z, living, stack);
        ENTITY = (ClayfurnaceEntity) world.getBlockTileEntity(x, y, z);
        int dir = MathHelper.floor_double((living.rotationYaw * 4F) / 360F + 0.5D) & 3;
        world.setBlockMetadataWithNotify(x, y, z, dir, 0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.blockIcon = par1IconRegister.registerIcon(MadMod.ID + ":" + MadFurnaces.CLAYFURNACE_INTERNALNAME);
    }

    // It's not a normal block, so you need this too.
    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    // Set a blocks direction
    private void setDefaultDirection(World par1World, int par2, int par3, int par4)
    {
        if (!par1World.isRemote)
        {
            int l = par1World.getBlockId(par2, par3, par4 - 1);
            int i1 = par1World.getBlockId(par2, par3, par4 + 1);
            int j1 = par1World.getBlockId(par2 - 1, par3, par4);
            int k1 = par1World.getBlockId(par2 + 1, par3, par4);
            byte b0 = 3;

            if (Block.opaqueCubeLookup[l] && !Block.opaqueCubeLookup[i1])
            {
                b0 = 3;
            }

            if (Block.opaqueCubeLookup[i1] && !Block.opaqueCubeLookup[l])
            {
                b0 = 2;
            }

            if (Block.opaqueCubeLookup[j1] && !Block.opaqueCubeLookup[k1])
            {
                b0 = 5;
            }

            if (Block.opaqueCubeLookup[k1] && !Block.opaqueCubeLookup[j1])
            {
                b0 = 4;
            }

            par1World.setBlockMetadataWithNotify(par2, par3, par4, b0, 2);
        }
    }

    // This will tell minecraft not to render any side of our cube.
    @Override
    public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int l)
    {
        return false;
    }
}

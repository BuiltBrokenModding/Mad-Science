package madscience.tile.meatcube;

import java.util.List;
import java.util.Random;

import madscience.MadEntities;
import madscience.MadMachines;
import madscience.MadScience;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
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
import universalelectricity.api.UniversalElectricity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MeatcubeBlock extends BlockContainer
{
    // Is the random generator used by furnace to drop the inventory contents in
    // random directions.
    private Random furnaceRand = new Random();

    // This flag is used to prevent the furnace inventory to be dropped upon
    // block removal, is used internally when the furnace block changes from
    // idle to active and vice-versa.
    private boolean keepFurnaceInventory;

    // Stores instance of our tile entity on the client.
    private MeatcubeEntity ENTITY;

    public MeatcubeBlock(int id)
    {
        // Solid like a rock baby.
        super(id, UniversalElectricity.machine);

        // Set what tab we show up in creative tab.
        this.setCreativeTab(MadEntities.tabMadScience);

        // Gives meatcube same resistance as iron block.
        this.setHardness(5.0F);
        this.setResistance(10.0F);

        // Define how big this item is we make it same size as a default block.
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    /** Adds all intersecting collision boxes to a list. (Be sure to only add boxes to the list if they intersect the mask.) Parameters: World, X, Y, Z, mask, list, colliding entity */
    @Override
    public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
    {
        // this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
    }

    /** Called on server worlds only when the block has been replaced by a different block ID, or the same block with a different metadata value, but before the new metadata value is set. Args: World, x, y, z, old block ID, old metadata */
    @Override
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        if (!keepFurnaceInventory)
        {
            MeatcubeEntity tileentityfurnace = (MeatcubeEntity) par1World.getBlockTileEntity(par2, par3, par4);

            if (tileentityfurnace != null)
            {
                for (int j1 = 0; j1 < (tileentityfurnace.getSizeOutputInventory() + tileentityfurnace.getSizeInputInventory()); ++j1)
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
                            EntityItem entityitem = new EntityItem(par1World, par2 + f, par3 + f1, par4 + f2, new ItemStack(itemstack.itemID, k1, itemstack.getItemDamage()));

                            if (itemstack.hasTagCompound())
                            {
                                entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
                            }

                            float f3 = 0.05F;
                            entityitem.motionX = (float) this.furnaceRand.nextGaussian() * f3;
                            entityitem.motionY = (float) this.furnaceRand.nextGaussian() * f3 + 0.2F;
                            entityitem.motionZ = (float) this.furnaceRand.nextGaussian() * f3;

                            par1World.spawnEntityInWorld(entityitem);
                        }
                    }
                }

                par1World.func_96440_m(par2, par3, par4, par5);
            }
        }

        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }

    @Override
    public TileEntity createNewTileEntity(World world)
    {
        // Make sure you set this as your TileEntity class!
        return new MeatcubeEntity();
    }

    @Override
    public int damageDropped(int metadata)
    {
        return metadata;
    }

    public TileEntity getBlockEntity()
    {
        // Returns the TileEntity used by this block.
        return new MeatcubeEntity();
    }

    /** If hasComparatorInputOverride returns true, the return value from this is used instead of the redstone signal strength when this block inputs to a comparator. */
    @Override
    public int getComparatorInputOverride(World par1World, int par2, int par3, int par4, int par5)
    {
        return Container.calcRedstoneFromInventory((IInventory) par1World.getBlockTileEntity(par2, par3, par4));
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
        // Breaking or killing meatcube only gives you a bone.
        return Item.bone.itemID;
    }

    @Override
    @SideOnly(Side.CLIENT)
    /**
     * only called by clickMiddleMouseButton , and passed to inventory.setCurrentItem (along with isCreative)
     */
    public int idPicked(World par1World, int par2, int par3, int par4)
    {
        return MadMachines.MEATCUBE_TILEENTITY.blockID;
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
            MeatcubeEntity tileentityfurnace = (MeatcubeEntity) par1World.getBlockTileEntity(par2, par3, par4);

            if (tileentityfurnace != null)
            {
                player.openGui(MadScience.instance, this.blockID, par1World, par2, par3, par4);
            }
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
        super.onBlockAdded(par1World, par2, par3, par4);
        this.setDefaultDirection(par1World, par2, par3, par4);
    }

    @Override
    public void onBlockClicked(World world, int X, int Y, int Z, EntityPlayer player)
    {
        // Called when the block is clicked by a player.
        if (world.isRemote)
        {
            return;
        }
        else if (!player.isSneaking())
        {
            // Get our tile entity instance.
            MeatcubeEntity meatCube = (MeatcubeEntity) world.getBlockTileEntity(X, Y, Z);
            if (meatCube == null)
            {
                return;
            }

            // Remove some health from the block if we can.
            if (meatCube.currentMeatCubeDamageValue <= meatCube.currentMaximumMeatCubeDamage && meatCube.currentMeatCubeDamageValue > 0)
            {
                meatCube.currentMeatCubeDamageValue--;

                // Spawn raw chicken, steak, and pork when meatcube is hit.
                ItemStack itemstack = new ItemStack(Item.beefRaw, 1);
                float foodTypeRoll = this.furnaceRand.nextFloat();
                int foodAmountRoll = this.furnaceRand.nextInt(5);

                // Prevent zero food spawn.
                if (foodAmountRoll <= 0)
                {
                    foodAmountRoll = 1;
                }

                // Determine what kind of food will spawn from random roll.
                if (foodTypeRoll <= 0.2F)
                {
                    // Beef
                    itemstack = new ItemStack(Item.beefRaw, foodAmountRoll);
                }
                else if (foodTypeRoll <= 0.5F)
                {
                    // Chicken
                    itemstack = new ItemStack(Item.chickenRaw, foodAmountRoll);
                }
                else if (foodTypeRoll <= 0.8F)
                {
                    // Pork
                    itemstack = new ItemStack(Item.porkRaw, foodAmountRoll);
                }

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
                        EntityItem entityitem = new EntityItem(world, X + f, Y + f1, Z + f2, new ItemStack(itemstack.itemID, k1, itemstack.getItemDamage()));

                        if (itemstack.hasTagCompound())
                        {
                            entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
                        }

                        float f3 = 0.05F;
                        entityitem.motionX = (float) this.furnaceRand.nextGaussian() * f3;
                        entityitem.motionY = (float) this.furnaceRand.nextGaussian() * f3 + 0.2F;
                        entityitem.motionZ = (float) this.furnaceRand.nextGaussian() * f3;

                        // Spawn the randomly chosen food item in a randomly chosen direction.
                        world.spawnEntityInWorld(entityitem);

                        // Play a meat slapping sound effect from our list of them.
                        world.playSoundEffect(X + 0.5D, Y + 0.5D, Z + 0.5D, MeatcubeSounds.MEATCUBE_MEATSLAP, 1.0F, world.rand.nextFloat() * 0.1F + 0.9F);

                        // Play a cow mooaning sound, but not every single time because that is annoying.
                        if (world.rand.nextBoolean() && world.rand.nextInt(10) < 5)
                        {
                            world.playSoundEffect(X + 0.5D, Y + 0.5D, Z + 0.5D, MeatcubeSounds.MEATCUBE_MOOANING, 1.0F, world.rand.nextFloat() * 0.1F + 0.9F);
                        }
                    }

                    // Prevent counter from going below zero.
                    if (meatCube.currentMeatCubeDamageValue < 0)
                    {
                        meatCube.currentMeatCubeDamageValue = 0;
                    }
                }
            }
        }
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase living, ItemStack stack)
    {
        super.onBlockPlacedBy(world, x, y, z, living, stack);
        ENTITY = (MeatcubeEntity) world.getBlockTileEntity(x, y, z);
        int dir = MathHelper.floor_double((living.rotationYaw * 4F) / 360F + 0.5D) & 3;
        world.setBlockMetadataWithNotify(x, y, z, dir, 0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.blockIcon = par1IconRegister.registerIcon(MadScience.ID + ":" + MadMachines.MEATCUBE_INTERNALNAME);
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

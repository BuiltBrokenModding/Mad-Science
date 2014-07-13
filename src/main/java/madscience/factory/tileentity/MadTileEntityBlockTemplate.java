package madscience.factory.tileentity;

import java.util.List;
import java.util.Random;

import madscience.MadEntities;
import madscience.MadScience;
import madscience.factory.MadTileEntityFactoryProduct;
import madscience.factory.sounds.MadSoundTriggerEnum;
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
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import universalelectricity.api.UniversalElectricity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MadTileEntityBlockTemplate extends BlockContainer
{    
    private static MadTileEntityFactoryProduct registeredProduct;
    
    /** Determines if this machine should explode it's inventory contents into world when broken. */
    private boolean keepFurnaceInventory;

    public MadTileEntityBlockTemplate(MadTileEntityFactoryProduct registeredMachine)
    {
        // Create Minecraft/Forge to create a block with our specifications.
        super(registeredMachine.getBlockID(), UniversalElectricity.machine);
        
        // Hold onto information about this machine.
        this.registeredProduct = registeredMachine;
        
        // Set name that is used internally to reference machine.
        this.setUnlocalizedName(registeredMachine.getMachineName());
        
        // Set what tab we show up in creative tab.
        this.setCreativeTab(MadEntities.tabMadScience);

        // Determines how many hits it takes to break the block.
        this.setHardness(3.5F);

        // Determines how resistant this block is to explosions.
        this.setResistance(2000.0F);

        // Define how big this item is we make it same size as a default block.
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    public MadTileEntityBlockTemplate(int par1, Material par2Material) // NO_UCD (unused code)
    {
        super(par1, par2Material);
    }

    /** Adds all intersecting collision boxes to a list. (Be sure to only add boxes to the list if they intersect the mask.) Parameters: World, X, Y, Z, mask, list, colliding entity */
    @Override
    public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
    {
        super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
    }

    /** Called on server worlds only when the block has been replaced by a different block ID, or the same block with a different metadata value, but before the new metadata value is set. Args: World, x, y, z, old block ID, old metadata */
    @Override
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        // Grab our block from the world upon breaking it.
        TileEntity vanillaTileInstance = par1World.getBlockTileEntity(par2, par3, par4);
        MadTileEntityPrefab tileEntityInstance = null;
        
        // Attempt to cast this block as one of ours.
        if (vanillaTileInstance != null && vanillaTileInstance instanceof MadTileEntityPrefab)
        {
            tileEntityInstance = (MadTileEntityPrefab) vanillaTileInstance;
            
            // Check if we need to play a sound for being destroyed.
            if (this.registeredProduct != null)
            {
                this.registeredProduct.playTriggerSound(MadSoundTriggerEnum.DESTROYED, par2, par3, par4, par1World);
            }
        }
        
        // Make items stored inside the inventory of slots spawn everywhere if there is anything inside.
        if (!keepFurnaceInventory)
        {
            if (tileEntityInstance != null)
            {
                for (int slotNumber = 0; slotNumber < tileEntityInstance.getSizeInventory(); ++slotNumber)
                {
                    ItemStack itemstack = tileEntityInstance.getStackInSlot(slotNumber);
    
                    if (itemstack != null)
                    {
                        float f = par1World.rand.nextFloat() * 0.8F + 0.1F;
                        float f1 = par1World.rand.nextFloat() * 0.8F + 0.1F;
                        float f2 = par1World.rand.nextFloat() * 0.8F + 0.1F;
    
                        while (itemstack.stackSize > 0)
                        {
                            int k1 = par1World.rand.nextInt(21) + 10;
    
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
                            entityitem.motionX = (float) par1World.rand.nextGaussian() * f3;
                            entityitem.motionY = (float) par1World.rand.nextGaussian() * f3 + 0.2F;
                            entityitem.motionZ = (float) par1World.rand.nextGaussian() * f3;
    
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
        // Returns the TileEntity used by this block.
        return registeredProduct.getNewTileEntityLogicClassInstance();
    }
    
    public TileEntity getBlockEntity()
    {
        // Returns the TileEntity used by this block.
        return registeredProduct.getNewTileEntityLogicClassInstance();
    }

    @Override
    public int damageDropped(int par1)
    {
        // Makes blocks drop the correct metadata in the world.
        return par1;
    }

    /** If hasComparatorInputOverride returns true, the return value from this is used instead of the redstone signal strength when this block inputs to a comparator. */
    @Override
    public int getComparatorInputOverride(World par1World, int par2, int par3, int par4, int par5)
    {
        return Container.calcRedstoneFromInventory((IInventory) par1World.getBlockTileEntity(par2, par3, par4));
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

    @Override
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return blockID;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int idPicked(World par1World, int par2, int par3, int par4)
    {
        return blockID;
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
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        // Called upon block activation (right click on the block.)
        if (par1World.isRemote)
        {
            return true;
        }
        else if (!par5EntityPlayer.isSneaking())
        {
            // Open GUI on the client...
            TileEntity vanillaTileInstance = par1World.getBlockTileEntity(par2, par3, par4);
            MadTileEntityPrefab tileEntityInstance = null;
            
            // Attempt to cast this block as one of ours.
            if (vanillaTileInstance != null && vanillaTileInstance instanceof MadTileEntityPrefab)
            {
                tileEntityInstance = (MadTileEntityPrefab) vanillaTileInstance;
            }
    
            if (tileEntityInstance != null)
            {
                par5EntityPlayer.openGui(MadScience.instance, this.blockID, par1World, par2, par3, par4);
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
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase living, ItemStack stack)
    {
        super.onBlockPlacedBy(world, x, y, z, living, stack);
        
        TileEntity vanillaTileInstance = world.getBlockTileEntity(x, y, z);
        MadTileEntityPrefab tileEntityInstance = null;
        
        // Attempt to cast this block as one of ours.
        if (vanillaTileInstance != null && vanillaTileInstance instanceof MadTileEntityPrefab)
        {
            // Check if we need to play a sound for being destroyed.
            tileEntityInstance = (MadTileEntityPrefab) vanillaTileInstance;
            if (tileEntityInstance != null)
            {
                if (this.registeredProduct != null)
                {
                    this.registeredProduct.playTriggerSound(MadSoundTriggerEnum.CREATED, x, y, z, world);
                }
                
                int dir = MathHelper.floor_double((living.rotationYaw * 4F) / 360F + 0.5D) & 3;
                world.setBlockMetadataWithNotify(x, y, z, dir, 0);
            }
        }
    }

    @Override
    public void registerIcons(IconRegister icon)
    {
        this.blockIcon = icon.registerIcon(MadScience.ID + ":" + registeredProduct.getMachineName());
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

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

    @Override
    public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int l)
    {
        return false;
    }

}
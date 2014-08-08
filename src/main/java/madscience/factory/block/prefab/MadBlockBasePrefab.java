package madscience.factory.block.prefab;

import java.util.List;
import java.util.Random;

import madscience.factory.MadBlockFactory;
import madscience.factory.MadItemFactory;
import madscience.factory.block.MadBlockFactoryProduct;
import madscience.factory.mod.MadMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import universalelectricity.api.UniversalElectricity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

abstract class MadBlockBasePrefab extends Block
{    
    private MadBlockFactoryProduct registeredBlock;
    private String registeredItemName;

    public MadBlockBasePrefab(MadBlockFactoryProduct registeredBlock)
    {
        // Create Minecraft/Forge to create a block with our specifications.
        super(registeredBlock.getBlockID(), UniversalElectricity.machine);
        
        // Hold onto information about this machine.
        this.registeredBlock = registeredBlock;
        
        // Set name that is used internally to reference machine.
        this.setUnlocalizedName(registeredBlock.getBlockBaseName());
        
        // Set what tab we show up in creative tab.
        this.setCreativeTab(MadMod.getCreativeTab());

        // Determines how many hits it takes to break the block.
        this.setHardness(registeredBlock.getBlockHardness());

        // Determines how resistant this block is to explosions.
        this.setResistance(registeredBlock.getBlockExplosionResistance());
        
        // Determines how much light bleeds through this block.
        this.setLightOpacity(registeredBlock.getLightOpacity());
        
        // Determines how much light this block itself gives off around it.
        this.setLightValue(registeredBlock.getLightBrightness());
        
        // Mad Block Factory products never have tiles attached to them.
        this.hasTileEntity(0);
    }

    public MadBlockBasePrefab(int blockID, Material blockMaterial) // NO_UCD (unused code)
    {
        super(blockID, blockMaterial);
    }
    
    public MadBlockFactoryProduct getRegisteredBlock()
    {
        // Only query and recreate the registered item if we need it.
        if (this.registeredBlock == null)
        {
            MadBlockFactoryProduct reloadedItem = MadBlockFactory.instance().getBlockInfo(this.registeredItemName);
            this.registeredBlock = reloadedItem;
            this.registeredItemName = reloadedItem.getBlockBaseName();
        }
        
        return this.registeredBlock;
    }

    @Override
    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
    {
        int blockIDAtPosition = par1World.getBlockId(par2, par3, par4);
        Block block = Block.blocksList[blockIDAtPosition];
        return block == null || block.isBlockReplaceable(par1World, par2, par3, par4);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int side, int meta)
    {
        return this.blockIcon;
    }
    
    @Override
    public int getFlammability(IBlockAccess world, int x, int y, int z, int metadata, ForgeDirection face)
    {
        return this.getRegisteredBlock().getBlockFireResistance();
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
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
        par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, this.tickRate(par1World));
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase living, ItemStack stack)
    {
        super.onBlockPlacedBy(world, x, y, z, living, stack);
        int dir = MathHelper.floor_double((living.rotationYaw * 4F) / 360F + 0.5D) & 3;
        world.setBlockMetadataWithNotify(x, y, z, dir, 0);
    }

    @Override
    public void registerIcons(IconRegister icon)
    {
        this.blockIcon = icon.registerIcon(MadMod.ID + ":" + registeredBlock.getBlockBaseName());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        return true;
    }

    @Override
    public int damageDropped(int par1)
    {
        // TODO Auto-generated method stub
        return super.damageDropped(par1);
    }

    @Override
    public int getRenderBlockPass()
    {
        // TODO Auto-generated method stub
        return super.getRenderBlockPass();
    }

    @Override
    public int getRenderColor(int par1)
    {
        // TODO Auto-generated method stub
        return super.getRenderColor(par1);
    }

    @Override
    public int getRenderType()
    {
        // TODO Auto-generated method stub
        return super.getRenderType();
    }

    @Override
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        // TODO Auto-generated method stub
        super.getSubBlocks(par1, par2CreativeTabs, par3List);
    }

    @Override
    protected String getTextureName()
    {
        // TODO Auto-generated method stub
        return super.getTextureName();
    }

    @Override
    public boolean getTickRandomly()
    {
        // TODO Auto-generated method stub
        return super.getTickRandomly();
    }

    @Override
    public void harvestBlock(World par1World, EntityPlayer par2EntityPlayer, int par3, int par4, int par5, int par6)
    {
        // TODO Auto-generated method stub
        super.harvestBlock(par1World, par2EntityPlayer, par3, par4, par5, par6);
    }

    @Override
    protected void initializeBlock()
    {
        // TODO Auto-generated method stub
        super.initializeBlock();
    }

    @Override
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        // TODO Auto-generated method stub
        super.updateTick(par1World, par2, par3, par4, par5Random);
    }
}
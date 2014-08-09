package madscience.factory.block.prefab;

import java.util.List;
import java.util.Random;

import madscience.factory.MadBlockFactory;
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
    private String registeredBlockName;

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
            MadBlockFactoryProduct reloadedBlock = MadBlockFactory.instance().getBlockInfo(this.registeredBlockName);
            this.registeredBlock = reloadedBlock;
            this.registeredBlockName = reloadedBlock.getBlockBaseName();
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
    public boolean canRenderInPass(int pass)
    {
        return super.canRenderInPass(pass);
    }

    @Override
    protected boolean canSilkHarvest()
    {
        return super.canSilkHarvest();
    }

    @Override
    public int getBlockColor()
    {
        return super.getBlockColor();
    }

    @Override
    public int getRenderBlockPass()
    {
        return super.getRenderBlockPass();
    }

    @Override
    public int getRenderColor(int par1)
    {
        return super.getRenderColor(par1);
    }

    @Override
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        super.getSubBlocks(par1, par2CreativeTabs, par3List);
    }

    @Override
    public String getUnlocalizedName()
    {
        return super.getUnlocalizedName();
    }

    @Override
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        super.updateTick(par1World, par2, par3, par4, par5Random);
    }

    @Override
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        return super.onBlockActivated(par1World, par2, par3, par4, par5EntityPlayer, par6, par7, par8, par9);
    }

    @Override
    public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer)
    {
        super.onBlockClicked(par1World, par2, par3, par4, par5EntityPlayer);
    }
}
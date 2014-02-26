package madscience.items;

import java.util.List;

import madscience.MadEntities;
import madscience.MadScience;
import madscience.util.MadUtils;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemGenome extends Item
{
    /** Base color of the egg */
    public int primaryColor;

    /** Color of the egg spots */
    public int secondaryColor;

    @SideOnly(Side.CLIENT)
    private Icon genomeReelLayer1;

    @SideOnly(Side.CLIENT)
    private Icon genomeReelLayer2;

    public ItemGenome(int id, int primaryColor, int secondaryColor)
    {
        super(id);

        // Define color information about the egg in HEX color notation (thanks Notch).
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;

        // All of our needles inherit this class and all our needles go into
        // creative tab.
        this.setCreativeTab(MadEntities.tabMadScience);

        // No needles may be repaired.
        this.setNoRepair();

        // Needles have maximum health of ten.
        // Note: also determines how many DNA strands are required to cook this item.
        this.setMaxDamage(63);

        // Define that we can have normal stack of items.
        this.maxStackSize = 1;
    }
    
/*    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List info, boolean par4)
    {
        String tooltip = StatCollector.translateToLocal(getUnlocalizedName() + ".tooltip");

        if (tooltip != null && tooltip.length() > 0)
        {
            info.addAll(MadUtils.splitStringPerWord(tooltip, 5));
        }
    }*/

    @Override
    public boolean canHarvestBlock(Block par1Block)
    {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
    {
        // Prevents coloring of the third render pass (our base of the icon).
        if (par2 == 2)
        {
            return 16777215;
        }

        // Applies custom coloring to the mob eggs based on constructor colors passed along.
        return this != null ? (par2 == 0 ? this.primaryColor : this.secondaryColor) : 16777215;
    }

    public int getDamageVsEntity(Entity par1Entity)
    {
        return 2;
    }

    @Override
    public Icon getIcon(ItemStack stack, int pass)
    {
        if (pass == 0)
        {
            return this.genomeReelLayer1;
        }
        else if (pass == 1)
        {
            return this.genomeReelLayer2;
        }
        else if (pass == 2)
        {
            return this.itemIcon;
        }

        return this.itemIcon;
    }

    @Override
    public Icon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining)
    {
        // Fired by Minecraft/Forge so it can get the icons for different render passes.
        return getIcon(stack, renderPass);
    }

    @Override
    public int getItemEnchantability()
    {
        return 0;
    }

    @Override
    public int getRenderPasses(int metadata)
    {
        return 3;
    }

    @Override
    public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block)
    {
        return 0.0F;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister par1IconRegister)
    {
        super.registerIcons(par1IconRegister);

        this.itemIcon = par1IconRegister.registerIcon(MadScience.ID + ":genomeDataReel_overlay");
        this.genomeReelLayer1 = par1IconRegister.registerIcon(MadScience.ID + ":genomeDataReel1");
        this.genomeReelLayer2 = par1IconRegister.registerIcon(MadScience.ID + ":genomeDataReel2");

        this.iconString = itemIcon.getIconName();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses()
    {
        // Tells minecraft to make more than one pass in rendering this item so we can have multiple layers to it.
        return true;
    }
}

package madscience.items.dna;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import madscience.MadScience;
import madscience.items.ItemComponent;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

/**
 * Created by robert on 2/6/2015.
 */
public class ItemDNA extends Item
{
    @SideOnly(Side.CLIENT)
    protected IIcon layer1;

    @SideOnly(Side.CLIENT)
    protected IIcon layer2;

    public ItemDNA()
    {
        this.setHasSubtypes(true);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List items)
    {
        for (EnumDNA dna : EnumDNA.values())
        {
            items.add(new ItemStack(item, 1, dna.ordinal()));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int pass)
    {
        if ((pass == 0 || pass == 1) && getDNA(stack.getItemDamage()) != null)
        {
            EnumDNA dna = getDNA(stack.getItemDamage());
            if (pass == 0)
            {
                return dna.primary_color;
            }
            else if (pass == 1)
            {
                return dna.secondary_color;
            }
        }

        // Applies custom coloring to the mob eggs based on constructor colors passed along.
        return 16777215;
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass)
    {
        if (pass == 0)
        {
            return this.layer1;
        }
        else if (pass == 1)
        {
            return this.layer2;
        }

        return this.itemIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }

    @Override
    public int getRenderPasses(int meta)
    {
        return getDNA(meta) != null ? 3 : 1;
    }

    public EnumDNA getDNA(int meta)
    {
        if(meta >= 0 && meta <= EnumDNA.values().length)
            return EnumDNA.values()[meta];
        return null;
    }
}

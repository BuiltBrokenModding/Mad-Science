package madscience.items.dna;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import madscience.MadScience;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemGenome extends ItemDNA
{
    public ItemGenome()
    {
        this.maxStackSize = 1;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List items)
    {
        items.add(new ItemStack(item, 1, 0));
        for (EnumDNA dna : EnumDNA.values())
        {
            items.add(new ItemStack(item, 1, dna.ordinal() + 1));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        if (stack.getItemDamage() == 0)
        {
            return "item.dataReelEmpty";
        }
        else if (getDNA(stack.getItemDamage()) != null)
        {
            return "item." + getDNA(stack.getItemDamage()).genomeString();
        }
        return super.getUnlocalizedName();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon(MadScience.ID + ":genomeDataReel_overlay");
        this.layer1 = par1IconRegister.registerIcon(MadScience.ID + ":genomeDataReel1");
        this.layer2 = par1IconRegister.registerIcon(MadScience.ID + ":genomeDataReel2");
    }

    @Override
    public EnumDNA getDNA(int meta)
    {
        if (meta >= 1 && meta <= EnumDNA.values().length + 1)
            return EnumDNA.values()[meta - 1];
        return null;
    }
}

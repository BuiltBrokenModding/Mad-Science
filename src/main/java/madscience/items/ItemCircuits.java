package madscience.items;

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
 * Created by robert on 2/5/2015.
 */
public class ItemCircuits extends ItemComponent
{
    public ItemCircuits()
    {
        this.setHasSubtypes(true);
    }

    @Override @SideOnly(Side.CLIENT)
    public String getUnlocalizedName(ItemStack stack)
    {
        if(stack.getItemDamage() >= 0 && stack.getItemDamage() < EnumCircuits.values().length)
        {
            return "item." + EnumCircuits.values()[stack.getItemDamage()].INTERNAL_NAME;
        }
        return super.getUnlocalizedName();
    }

    @Override @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg)
    {
        for(EnumCircuits circuit : EnumCircuits.values())
        {
            circuit.icon = reg.registerIcon(MadScience.ID + ":" + circuit.INTERNAL_NAME);
        }
    }

    @Override @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int p_77617_1_)
    {
        return this.itemIcon;
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List items)
    {
        for(EnumCircuits circuit : EnumCircuits.values())
        {
            items.add(circuit.stack());
        }
    }

    /**
     * Defined list of sub items
     */
    public enum EnumCircuits
    {
        COMPARATOR("circuitComparator"),
        DIAMOND("circuitDiamond"),
        EMERALID("circuitEmerald"),
        ENDER_EYE("circuitEnderEye"),
        ENDER_PERAL("circuitEnderPearl"),
        GLOWSTONE("circuitGlowstone"),
        REDSTONE("circuitRedstone"),
        SPIDER_EYE("circuitSpiderEye");

        public final String INTERNAL_NAME;

        @SideOnly(Side.CLIENT)
        public IIcon icon;

        private EnumCircuits(String langName)
        {
            this.INTERNAL_NAME = langName;
        }

        public ItemStack stack()
        {
            return new ItemStack(MadScience.itemCircuits, 1, ordinal());
        }
    }

}

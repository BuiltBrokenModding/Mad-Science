package madscience.content.items.weapons;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import madscience.MadScience;
import madscience.content.items.ItemComponent;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

/**
 * Created by robert on 2/6/2015.
 */
public class ItemWeaponPart extends ItemComponent
{
    public ItemWeaponPart()
    {
        this.setHasSubtypes(true);
    }

    @Override @SideOnly(Side.CLIENT)
    public String getUnlocalizedName(ItemStack stack)
    {
        if(stack.getItemDamage() >= 0 && stack.getItemDamage() < EnumWeaponParts.values().length)
        {
            return "item." + EnumWeaponParts.values()[stack.getItemDamage()].INTERNAL_NAME;
        }
        return super.getUnlocalizedName();
    }

    @Override @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg)
    {
        for(EnumWeaponParts circuit : EnumWeaponParts.values())
        {
            circuit.icon = reg.registerIcon(MadScience.ID + ":" + circuit.INTERNAL_NAME);
        }
    }

    @Override @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int p_77617_1_)
    {
        return this.itemIcon;
    }

    @Override @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List items)
    {
        for(EnumWeaponParts circuit : EnumWeaponParts.values())
        {
            items.add(circuit.stack());
        }
    }

    public enum EnumWeaponParts
    {
        BARREL("componentPulseRifleBarrel"),
        BOLT("componentPulseRifleBolt"),
        BULLET_CASING("componentPulseRifleBulletCasing"),
        GRENADE_CASING("componentPulseRifleGrenadeCasing"),
        RECEIVER("componentPulseRifleReciever"),
        TRIGGER("componentPulseRifleTrigger");

        public final String INTERNAL_NAME;

        @SideOnly(Side.CLIENT)
        public IIcon icon;

        private EnumWeaponParts(String langName)
        {
            this.INTERNAL_NAME = langName;
        }

        public ItemStack stack()
        {
            return new ItemStack(MadScience.itemWeaponParts, 1, ordinal());
        }
    }
}

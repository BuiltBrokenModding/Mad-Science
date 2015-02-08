package madscience.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import madscience.MadScience;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

/**
 * Created by robert on 2/5/2015.
 */
public class ItemComponents extends ItemComponent
{
    public ItemComponents()
    {
        this.setHasSubtypes(true);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getUnlocalizedName(ItemStack stack)
    {
        if (stack.getItemDamage() >= 0 && stack.getItemDamage() < EnumComponents.values().length)
        {
            return "item." + EnumComponents.values()[stack.getItemDamage()].INTERNAL_NAME;
        }
        return super.getUnlocalizedName();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg)
    {
        for (EnumComponents circuit : EnumComponents.values())
        {
            circuit.icon = reg.registerIcon(MadScience.ID + ":" + circuit.INTERNAL_NAME);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta)
    {
        if (meta >= 0 && meta < EnumComponents.values().length)
        {
            return EnumComponents.values()[meta].icon;
        }
        return this.itemIcon;
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List items)
    {
        for (EnumComponents circuit : EnumComponents.values())
        {
            items.add(circuit.stack());
        }
    }

    public enum EnumComponents
    {
        CASE("componentCase"),
        COMPUTER("componentComputer"),
        CPU("componentCPU"),
        ENDERSLIME("componentEnderslime"),
        FAN("componentFan"),
        FUSE_QUARTZ("componentFusedQuartz"),
        MAGNETIC_TAPE("componentMagneticTape"),
        POWER_SUPPLY("componentPowerSupply"),
        RAM("componentRAM"),
        SCREEN("componentScreen"),
        SILICON_WAFER("componentSiliconWafer"),
        THUMPER("componentThumper"),
        TRANSISTOR("componentTransistor");

        public final String INTERNAL_NAME;

        @SideOnly(Side.CLIENT)
        public IIcon icon;

        private EnumComponents(String langName)
        {
            this.INTERNAL_NAME = langName;
        }

        public ItemStack stack()
        {
            return new ItemStack(MadScience.itemComponents, 1, ordinal());
        }
    }
}

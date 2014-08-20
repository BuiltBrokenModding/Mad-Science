package madscience.factory.creativetab;

import madscience.factory.mod.MadMod;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class MadCreativeTab extends CreativeTabs
{
    public MadCreativeTab(String label)
    {
        super(label);
    }

    @Override
    public ItemStack getIconItemStack()
    {
        return super.getIconItemStack();
    }

//    @Override
//    public Item getTabIconItem()
//    {
//        return super.getTabIconItem();
//    }
//
//    @Override
//    public int getTabIconItemIndex()
//    {
//        return super.getTabIconItemIndex();
//    }

    @Override
    public String getTabLabel()
    {
        // Using modID as creative tab label.
        return MadMod.ID;
    }
}

package madscience.factory.creativetab;

import madscience.factory.mod.MadMod;
import net.minecraft.creativetab.CreativeTabs;
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
        // Grab the icon for creative tab from main mod data.
        ItemStack possibleIcon = MadMod.getCreativeTabIcon();
        if (possibleIcon != null)
        {
            return possibleIcon;
        }
        
        // Default response is to return texture for stone.
        return super.getIconItemStack();
    }

    @Override
    public String getTabLabel()
    {
        // Using modID as creative tab label.
        return MadMod.ID;
    }
}

package madscience.mod;


import madscience.ModMetadata;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;


public class CreativeTab extends CreativeTabs
{
    public CreativeTab(String label)
    {
        super( label );
    }

    @Override
    public ItemStack getIconItemStack()
    {
        // Grab the icon for creative tab from main mod data.
        ItemStack possibleIcon = ModLoader.getCreativeTabIcon();
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
        return ModMetadata.ID;
    }
}

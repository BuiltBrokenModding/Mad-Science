package madscience.server;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockMadScience extends ItemBlock
{
    // For registering blocks on the server without fancy tooltips and descriptions.
    public ItemBlockMadScience(int id)
    {
        super(id);
    }

    @Override
    public String getItemDisplayName(ItemStack itemstack)
    {
        return getUnlocalizedName(itemstack);
    }

    @Override
    public int getMetadata(int i)
    {
        return i;
    }
}

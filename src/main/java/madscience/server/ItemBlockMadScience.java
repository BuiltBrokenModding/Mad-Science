package madscience.server;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockMadScience extends ItemBlock
{

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

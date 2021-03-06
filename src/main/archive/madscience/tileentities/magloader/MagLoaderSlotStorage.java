package madscience.tileentities.magloader;

import madscience.MadWeapons;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class MagLoaderSlotStorage extends Slot
{
    public MagLoaderSlotStorage(IInventory inv, int index, int x, int y)
    {
        super(inv, index, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        // Check if we are pulse rifle bullet itemstack or not.
        ItemStack compareMagazineItem = new ItemStack(MadWeapons.WEAPONITEM_BULLETITEM);
        if (stack != null && stack.isItemEqual(compareMagazineItem))
        {
            return true;
        }

        return false;
    }
}

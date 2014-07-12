package madscience.tile.magloader;

import madscience.MadWeapons;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

class MagLoaderSlotInput extends Slot
{
    MagLoaderSlotInput(IInventory inv, int index, int x, int y)
    {
        super(inv, index, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        // Input slot 1 - Pulse Rifle Magazine
        ItemStack compareMagazine = new ItemStack(MadWeapons.WEAPONITEM_MAGAZINEITEM);
        if (compareMagazine.isItemEqual(stack))
        {
            return true;
        }

        return false;
    }
}

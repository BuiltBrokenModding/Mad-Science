package madscience.tileentities.magloader;

import madscience.MadWeapons;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class MagLoaderSlotInput extends Slot
{
    public MagLoaderSlotInput(IInventory inv, int index, int x, int y)
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

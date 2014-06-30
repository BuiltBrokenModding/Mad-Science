package madscience.tileentities.sanitizer;

import madscience.MadNeedles;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

class SanitizerSlotInputDirtyNeedles extends Slot
{
    SanitizerSlotInputDirtyNeedles(IInventory inv, int index, int x, int y)
    {
        super(inv, index, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        // Input slot 2 - Dirty needles that need cleaning.
        ItemStack compareDirtyNeedle = new ItemStack(MadNeedles.NEEDLE_DIRTY);

        if (compareDirtyNeedle.isItemEqual(stack))
        {
            return true;
        }

        return false;
    }
}

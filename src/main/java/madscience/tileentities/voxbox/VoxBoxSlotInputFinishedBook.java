package madscience.tileentities.voxbox;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

class VoxBoxSlotInputFinishedBook extends Slot
{
    VoxBoxSlotInputFinishedBook(IInventory inv, int index, int x, int y)
    {
        super(inv, index, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        // Only written books may be inserted.
        ItemStack completedBookComparisonItem = new ItemStack(Item.writtenBook);
        if (completedBookComparisonItem.isItemEqual(stack))
        {
            return true;
        }

        return false;
    }
}

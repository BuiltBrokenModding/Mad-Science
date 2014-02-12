package madscience.tileentities.dataduplicator;

import madscience.MadEntities;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class DataDuplicatorSlotInputEmptyDataReel extends Slot
{
    public DataDuplicatorSlotInputEmptyDataReel(IInventory inv, int index, int x, int y)
    {
        super(inv, index, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        // Input slot - Empty data reel.
        ItemStack compareEmptyDataReel = new ItemStack(MadEntities.DATAREEL_EMPTY);

        if (compareEmptyDataReel.isItemEqual(stack))
        {
            return true;
        }

        return false;
    }
}

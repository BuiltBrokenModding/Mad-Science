package madscience.tileentities.incubator;

import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class IncubatorSlotInputEgg extends Slot
{
    public IncubatorSlotInputEgg(IInventory inv, int index, int x, int y)
    {
        super(inv, index, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        // Input slot 1 - Chicken egg.
        ItemStack compareEggs = new ItemStack(Item.egg);

        if (compareEggs.isItemEqual(stack))
        {
            return true;
        }

        return false;
    }
}

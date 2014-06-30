package madscience.tileentities.sanitizer;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

class SanitizerSlotInputWaterBucket extends Slot
{
    SanitizerSlotInputWaterBucket(IInventory inv, int index, int x, int y)
    {
        super(inv, index, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        // Input slot 1 - Filled water bucket.
        ItemStack compareWaterBucket = new ItemStack(Item.bucketWater);

        if (compareWaterBucket.isItemEqual(stack))
        {
            return true;
        }

        return false;
    }
}

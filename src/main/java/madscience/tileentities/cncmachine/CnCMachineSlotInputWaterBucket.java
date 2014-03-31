package madscience.tileentities.cncmachine;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CnCMachineSlotInputWaterBucket extends Slot
{
    public CnCMachineSlotInputWaterBucket(IInventory inv, int index, int x, int y)
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

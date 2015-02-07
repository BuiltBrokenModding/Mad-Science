package madscience.tileentities.dnaextractor;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class DNAExtractorSlotInputEmptyBucket extends Slot
{
    public DNAExtractorSlotInputEmptyBucket(IInventory inv, int index, int x, int y)
    {
        super(inv, index, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        // Input slot 2 - Empty water bucket.
        ItemStack compareWaterBucket = new ItemStack(Item.bucketEmpty);

        if (compareWaterBucket.isItemEqual(stack))
        {
            return true;
        }

        return false;
    }
}

package madscience.tileentities.meatcube;

import madscience.MadFluids;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

class MeatcubeSlotInputWaterBucket extends Slot
{
    MeatcubeSlotInputWaterBucket(IInventory inv, int index, int x, int y)
    {
        super(inv, index, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        // Input slot 1 - Filled water bucket.
        ItemStack compareLiquidDNAMutantBucket = new ItemStack(MadFluids.LIQUIDDNA_MUTANT_BUCKET_ITEM);

        if (compareLiquidDNAMutantBucket.isItemEqual(stack))
        {
            return true;
        }

        return false;
    }
}

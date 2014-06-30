package madscience.tileentities.soniclocator;

import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

class SoniclocatorSlotInputGravel extends Slot
{
    SoniclocatorSlotInputGravel(IInventory inv, int index, int x, int y)
    {
        super(inv, index, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        // Input slot 1 - Gravel block.
        ItemStack compareBlocks = new ItemStack(Block.gravel);

        if (compareBlocks.isItemEqual(stack))
        {
            return true;
        }

        return false;
    }
}

package madscience.tileentities.clayfurnace;

import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

class ClayfurnaceSlotInputCoal extends Slot
{
    ClayfurnaceSlotInputCoal(IInventory inv, int index, int x, int y)
    {
        super(inv, index, x, y);
    }

    @Override
    public int getSlotStackLimit()
    {
        // Clay furnace only needs 1 charcoal block to do work.
        return 1;
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        // Input slot 1 - Coal block as fuel source.
        ItemStack coalBlockCompare = new ItemStack(Block.coalBlock);

        if (coalBlockCompare.isItemEqual(stack))
        {
            return true;
        }

        return false;
    }
}

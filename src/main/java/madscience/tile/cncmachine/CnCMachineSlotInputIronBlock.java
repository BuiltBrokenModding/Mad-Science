package madscience.tile.cncmachine;

import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

class CnCMachineSlotInputIronBlock extends Slot
{
    CnCMachineSlotInputIronBlock(IInventory inv, int index, int x, int y)
    {
        super(inv, index, x, y);
    }

    @Override
    public int getSlotStackLimit()
    {
        return 1;
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        // Input slot 2 - Block of Iron that needs cutting.
        ItemStack compareIronBlock = new ItemStack(Block.blockIron);
        if (compareIronBlock.isItemEqual(stack))
        {
            return true;
        }

        return false;
    }
}

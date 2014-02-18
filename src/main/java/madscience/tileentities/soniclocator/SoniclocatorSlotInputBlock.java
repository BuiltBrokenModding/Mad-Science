package madscience.tileentities.soniclocator;

import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SoniclocatorSlotInputBlock extends Slot
{
    public SoniclocatorSlotInputBlock(IInventory inv, int index, int x, int y)
    {
        super(inv, index, x, y);
    }
    
    @Override
    public int getSlotStackLimit()
    {
        // We only need a sample of the block for targeting purposes.
        return 1;
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {        
        // Check if the target block is indeed a block.
        ItemStack compareChucnkItem = new ItemStack(Block.blocksList[stack.getItem().itemID]);
        if (stack != null && compareChucnkItem.isItemEqual(stack))
        {
            return true;
        }

        return false;
    }
}

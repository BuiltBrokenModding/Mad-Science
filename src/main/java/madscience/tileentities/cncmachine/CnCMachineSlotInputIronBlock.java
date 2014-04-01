package madscience.tileentities.cncmachine;

import madscience.MadNeedles;
import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class CnCMachineSlotInputIronBlock extends Slot
{
    public CnCMachineSlotInputIronBlock(IInventory inv, int index, int x, int y)
    {
        super(inv, index, x, y);
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

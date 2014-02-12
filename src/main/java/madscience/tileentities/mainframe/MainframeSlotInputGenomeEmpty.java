package madscience.tileentities.mainframe;

import madscience.MadEntities;
import madscience.MadGenomes;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class MainframeSlotInputGenomeEmpty extends Slot
{
    public MainframeSlotInputGenomeEmpty(IInventory inv, int index, int x, int y)
    {
        super(inv, index, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        // Input slot 1 - Empty genome data reel.
        ItemStack compareEmptyGenome = new ItemStack(MadEntities.DATAREEL_EMPTY);

        if (compareEmptyGenome.isItemEqual(stack))
        {
            return true;
        }

        return false;
    }
}

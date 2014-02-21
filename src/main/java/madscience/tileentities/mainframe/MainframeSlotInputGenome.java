package madscience.tileentities.mainframe;

import madscience.items.CombinedGenomeMonsterPlacer;
import madscience.items.ItemDataReelEmpty;
import madscience.items.ItemGenome;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class MainframeSlotInputGenome extends Slot
{
    public MainframeSlotInputGenome(IInventory inv, int index, int x, int y)
    {
        super(inv, index, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        // We do not allow empty genomes in this slot.
        if (stack != null && stack.getItem() instanceof ItemDataReelEmpty)
        {
            return false;
        }

        // Check if we are a genome data reel.
        if (stack != null && stack.getItem() instanceof ItemGenome)
        {
            return true;
        }

        // Check if we are a combined genome (monster from mainframe).
        if (stack != null && stack.getItem() instanceof CombinedGenomeMonsterPlacer)
        {
            return true;
        }

        return false;
    }
}

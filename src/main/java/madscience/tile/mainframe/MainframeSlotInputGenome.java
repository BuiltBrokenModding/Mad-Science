package madscience.tile.mainframe;

import madscience.items.combinedgenomes.CombinedGenomeMonsterPlacer;
import madscience.items.datareel.ItemDataReelEmpty;
import madscience.items.genomes.ItemGenomeBase;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

class MainframeSlotInputGenome extends Slot
{
    MainframeSlotInputGenome(IInventory inv, int index, int x, int y)
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
        if (stack != null && stack.getItem() instanceof ItemGenomeBase)
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

package madscience.tileentities.incubator;

import madscience.items.CombinedGenomeMonsterPlacer;
import madscience.items.dna.ItemGenome;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class IncubatorSlotInputGenome extends Slot
{
    public IncubatorSlotInputGenome(IInventory inv, int index, int x, int y)
    {
        super(inv, index, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
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

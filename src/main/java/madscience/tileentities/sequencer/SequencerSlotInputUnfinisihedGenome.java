package madscience.tileentities.sequencer;

import madscience.items.datareel.ItemDataReelEmpty;
import madscience.items.genomes.ItemGenomeBase;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

class SequencerSlotInputUnfinisihedGenome extends Slot
{
    SequencerSlotInputUnfinisihedGenome(IInventory inv, int index, int x, int y)
    {
        super(inv, index, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        // Empty genomes are allowed since they will be encoded in this device.
        if (stack != null && stack.getItem() instanceof ItemDataReelEmpty)
        {
            return true;
        }

        // Check if we are a genome data reel that is unfinished (AKA damaged).
        if (stack != null && stack.getItem() instanceof ItemGenomeBase && stack.isItemDamaged())
        {
            return true;
        }

        return false;
    }
}

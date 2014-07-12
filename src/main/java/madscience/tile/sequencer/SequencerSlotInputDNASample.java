package madscience.tile.sequencer;

import madscience.items.dna.ItemDecayDNABase;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

class SequencerSlotInputDNASample extends Slot
{
    SequencerSlotInputDNASample(IInventory inv, int index, int x, int y)
    {
        super(inv, index, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        // Check if we are a DNA sample.
        if (stack != null && stack.getItem() instanceof ItemDecayDNABase)
        {
            return true;
        }

        return false;
    }
}

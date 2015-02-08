package madscience.tileentities.sequencer;

import madscience.content.items.dna.ItemDNASample;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SequencerSlotInputDNASample extends Slot
{
    public SequencerSlotInputDNASample(IInventory inv, int index, int x, int y)
    {
        super(inv, index, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        // Check if we are a DNA sample.
        if (stack != null && stack.getItem() instanceof ItemDNASample)
        {
            return true;
        }

        return false;
    }
}

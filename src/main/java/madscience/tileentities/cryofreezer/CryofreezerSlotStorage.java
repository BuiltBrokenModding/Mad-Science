package madscience.tileentities.cryofreezer;

import madscience.items.ItemDecayDNA;
import madscience.items.ItemDecayNeedle;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

class CryofreezerSlotStorage extends Slot
{
    CryofreezerSlotStorage(IInventory inv, int index, int x, int y)
    {
        super(inv, index, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        // Check if we are a DNA sample.
        if (stack != null && stack.getItem() instanceof ItemDecayDNA)
        {
            return true;
        }

        // Check if we are a needle of DNA.
        if (stack != null && stack.getItem() instanceof ItemDecayNeedle)
        {
            return true;
        }

        return false;
    }
}

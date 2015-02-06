package madscience.tileentities.dnaextractor;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class DNAExtractorSlotInput extends Slot
{
    public DNAExtractorSlotInput(IInventory inv, int index, int x, int y)
    {
        super(inv, index, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        if (stack == null)
        {
            return false;
        }

        // Check if we are a mutant DNA needle.
        if (stack.getItem() instanceof NeedleMutant)
        {
            return true;
        }

        // Check if we are a DNA sample.
        ItemStack result = DNAExtractorRecipes.getSmeltingResult(stack);
        if (result != null)
        {
            return true;
        }

        return false;
    }
}

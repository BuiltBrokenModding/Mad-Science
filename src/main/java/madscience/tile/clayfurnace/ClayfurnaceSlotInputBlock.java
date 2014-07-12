package madscience.tile.clayfurnace;

import madscience.MadScience;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

class ClayfurnaceSlotInputBlock extends Slot
{
    ClayfurnaceSlotInputBlock(IInventory inv, int index, int x, int y)
    {
        super(inv, index, x, y);
    }

    @Override
    public int getSlotStackLimit()
    {
        // Clay furnace can only take one source block at a time.
        return 1;
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        try
        {
            // Ensure that we are going to be cooking a actual ore block.
            ItemStack itemsInputSlot2 = ClayfurnaceRecipes.getSmeltingResult(stack);
            if (itemsInputSlot2 != null)
            {
                return true;
            }
        }
        catch (Exception err)
        {
            MadScience.logger.info("CLAYFURNACE: Attempted to query Minecraft blocklist with value out of index.");
        }

        return false;
    }
}

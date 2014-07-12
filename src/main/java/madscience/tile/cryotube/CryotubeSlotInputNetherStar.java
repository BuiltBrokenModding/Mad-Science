package madscience.tile.cryotube;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

class CryotubeSlotInputNetherStar extends Slot
{
    CryotubeSlotInputNetherStar(IInventory inv, int index, int x, int y)
    {
        super(inv, index, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        // Checks for nether star.
        ItemStack compareNetherStar = new ItemStack(Item.netherStar);
        if (compareNetherStar.isItemEqual(stack))
        {
            return true;
        }

        return false;
    }
}

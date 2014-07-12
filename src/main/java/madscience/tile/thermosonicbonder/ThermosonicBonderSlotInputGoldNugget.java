package madscience.tile.thermosonicbonder;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

class ThermosonicBonderSlotInputGoldNugget extends Slot
{
    ThermosonicBonderSlotInputGoldNugget(IInventory inv, int index, int x, int y)
    {
        super(inv, index, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        // Input slot 1 - Gold nuggets.
        ItemStack compareNuggets = new ItemStack(Item.goldNugget);

        if (compareNuggets.isItemEqual(stack))
        {
            return true;
        }

        return false;
    }
}

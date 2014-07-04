package madscience.tileentities.thermosonicbonder;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

class ThermosonicBonderSlotInputComponent extends Slot
{
    ThermosonicBonderSlotInputComponent(IInventory inv, int index, int x, int y)
    {
        super(inv, index, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        ItemStack smeltingResult = ThermosonicBonderRecipes.getSmeltingResult(stack);
        if (smeltingResult != null)
        {
            return true;
        }

        return false;
    }
}

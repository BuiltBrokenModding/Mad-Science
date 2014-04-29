package madscience.tileentities.thermosonicbonder;

import madscience.MadCircuits;
import madscience.MadComponents;
import madscience.items.ItemComponent;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ThermosonicBonderSlotInputComponent extends Slot
{
    public ThermosonicBonderSlotInputComponent(IInventory inv, int index, int x, int y)
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

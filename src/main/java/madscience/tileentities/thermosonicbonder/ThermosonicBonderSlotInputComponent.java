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
        // Check if we are a fused-quartz.
        if (stack != null && stack.isItemEqual(new ItemStack(MadComponents.COMPONENT_FUSEDQUARTZ)))
        {
            return true;
        }

        // Check for silicon wafer.
        if (stack != null && stack.isItemEqual(new ItemStack(MadComponents.COMPONENT_SILICONWAFER)))
        {
            return true;
        }

        // Check for glowstone circuit board.
        if (stack != null && stack.isItemEqual(new ItemStack(MadCircuits.CIRCUIT_GLOWSTONE)))
        {
            return true;
        }

        // Check for redstone circuit board.
        if (stack != null && stack.isItemEqual(new ItemStack(MadCircuits.CIRCUIT_REDSTONE)))
        {
            return true;
        }

        return false;
    }
}

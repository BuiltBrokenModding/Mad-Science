package madscience.tileentities.thermosonicbonder;

import madscience.MadComponents;
import madscience.metaitems.MainframeComponents;
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
        // Check if we are a mainframe components in general before proceeding.
        if (stack != null && stack.getItem() instanceof MainframeComponents)
        {
            // Check if we are a fused-quartz.
            if (stack != null && stack.getItemDamage() == MadComponents.COMPONENT_FUSEDQUARTZ_METAID)
            {
                return true;
            }

            // Check for silicon wafer.
            if (stack != null && stack.getItemDamage() == MadComponents.COMPONENT_SILICONWAFER_METAID)
            {
                return true;
            }

            // Check for glowstone circuit board.
            if (stack != null && stack.getItemDamage() == MadComponents.CIRCUIT_GLOWSTONE_METAID)
            {
                return true;
            }

            // Check for redstone circuit board.
            if (stack != null && stack.getItemDamage() == MadComponents.CIRCUIT_REDSTONE_METAID)
            {
                return true;
            }
        }

        return false;
    }
}

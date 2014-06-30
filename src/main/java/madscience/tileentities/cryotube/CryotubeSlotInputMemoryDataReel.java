package madscience.tileentities.cryotube;

import madscience.items.ItemDataReelEmpty;
import madscience.metaitems.CombinedMemoryMonsterPlacer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

class CryotubeSlotInputMemoryDataReel extends Slot
{
    CryotubeSlotInputMemoryDataReel(IInventory inv, int index, int x, int y)
    {
        super(inv, index, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        // Empty genomes are allowed since they will be encoded in this device.
        if (stack != null && stack.getItem() instanceof ItemDataReelEmpty)
        {
            return true;
        }

        // Check if we are a memory data reel.
        if (stack != null && stack.getItem() instanceof CombinedMemoryMonsterPlacer)
        {
            return true;
        }

        return false;
    }
}

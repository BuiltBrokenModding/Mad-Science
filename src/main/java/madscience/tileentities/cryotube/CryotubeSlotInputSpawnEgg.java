package madscience.tileentities.cryotube;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CryotubeSlotInputSpawnEgg extends Slot
{
    public CryotubeSlotInputSpawnEgg(IInventory inv, int index, int x, int y)
    {
        super(inv, index, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        // Check for spawn egg of villager only.
        ItemStack compareVillagerSpawnEgg = new ItemStack(Item.monsterPlacer, 1, 120);

        if (compareVillagerSpawnEgg.isItemEqual(stack))
        {
            return true;
        }

        return false;
    }
}

package madscience.tileentities.soniclocator;

import java.util.ArrayList;
import madscience.MadScience;
import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

class SoniclocatorSlotInputBlock extends Slot
{
    SoniclocatorSlotInputBlock(IInventory inv, int index, int x, int y)
    {
        super(inv, index, x, y);
    }

    @Override
    public int getSlotStackLimit()
    {
        // We only need a sample of the block for targeting purposes.
        return 1;
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        try
        {
            // Check if the target block is indeed a block.
            ItemStack compareChucnkItem = new ItemStack(Block.blocksList[stack.getItem().itemID]);
            if (stack != null && compareChucnkItem.isItemEqual(stack))
            {
                return true;
            }
        }
        catch (Exception err)
        {
            MadScience.logger.info("SONICLOCATOR: Attempted to query Minecraft blocklist with value out of index.");
        }
        
        // Check if the target block is inside the OreDictionary if first query fails.
        int oreID = OreDictionary.getOreID(stack);
        ArrayList<ItemStack> oreDictOres = OreDictionary.getOres(oreID);
        for (ItemStack someItem : oreDictOres)
        {
            if (OreDictionary.itemMatches(someItem, stack, false)) return true;
        }

        return false;
    }
}

package madscience.tileentities.dataduplicator;

import madscience.MadEntities;
import madscience.content.items.CombinedGenomeMonsterPlacer;
import madscience.content.items.dna.ItemGenome;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class DataDuplicatorSlotInputFullDataReel extends Slot
{
    public DataDuplicatorSlotInputFullDataReel(IInventory inv, int index, int x, int y)
    {
        super(inv, index, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        // Disallow empty genomes.
        ItemStack compareEmptyDataReel = new ItemStack(MadEntities.DATAREEL_EMPTY);
        if (compareEmptyDataReel.isItemEqual(stack))
        {
            return false;
        }

        // Check if we are a genome data reel that is unfinished (AKA damaged).
        if (stack != null && stack.getItem() instanceof ItemGenome && stack.isItemDamaged())
        {
            return false;
        }

        // Completed genomes are allowed to be duplicated.
        if (stack != null && stack.getItem() instanceof ItemGenome && !stack.isItemDamaged())
        {
            return true;
        }

        // Completed mutant genomes are allowed to be duplicated.
        if (stack != null && stack.getItem() instanceof CombinedGenomeMonsterPlacer)
        {
            return true;
        }

        // Memory reels are allowed since only way to acquire the best is by chance.
        if (stack != null && stack.getItem() instanceof CombinedMemoryMonsterPlacer)
        {
            return true;
        }

        return false;
    }
}

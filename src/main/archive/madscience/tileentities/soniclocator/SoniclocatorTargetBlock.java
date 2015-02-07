package madscience.tileentities.soniclocator;

import net.minecraft.item.ItemStack;

public class SoniclocatorTargetBlock
{
    public ItemStack foundItem;
    public int targetX;
    public int targetY;
    public int targetZ;

    public SoniclocatorTargetBlock(int posX, int posY, int posZ, ItemStack chunkItem)
    {
        // Position of the block we want to replace.
        targetX = posX;
        targetY = posY;
        targetZ = posZ;

        // ItemStack we examined in the world.
        foundItem = chunkItem;
    }
}

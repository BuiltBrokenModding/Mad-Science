package madscience.tileentities.soniclocator;

import net.minecraft.item.ItemStack;

class SoniclocatorTargetBlock
{
    ItemStack foundItem;
    int targetX;
    int targetY;
    int targetZ;

    SoniclocatorTargetBlock(int posX, int posY, int posZ, ItemStack chunkItem)
    {
        // Position of the block we want to replace.
        targetX = posX;
        targetY = posY;
        targetZ = posZ;

        // ItemStack we examined in the world.
        foundItem = chunkItem;
    }
}

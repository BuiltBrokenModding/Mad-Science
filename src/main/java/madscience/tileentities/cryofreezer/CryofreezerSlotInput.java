package madscience.tileentities.cryofreezer;

import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

class CryofreezerSlotInput extends Slot
{
    CryofreezerSlotInput(IInventory inv, int index, int x, int y)
    {
        super(inv, index, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        // Input slot 1 - Iceblock or snowball.
        ItemStack compareSnowBalls = new ItemStack(Item.snowball);
        ItemStack compareIceblock = new ItemStack(Block.ice);
        ItemStack compareSnow = new ItemStack(Block.snow);
        ItemStack compareSnowBlock = new ItemStack(Block.blockSnow);

        if (compareSnowBalls.isItemEqual(stack) || compareIceblock.isItemEqual(stack) || compareSnow.isItemEqual(stack) || compareSnowBlock.isItemEqual(stack))
        {
            return true;
        }

        return false;
    }
}

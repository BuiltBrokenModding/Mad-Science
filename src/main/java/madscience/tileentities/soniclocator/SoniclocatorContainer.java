package madscience.tileentities.soniclocator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SoniclocatorContainer extends Container
{
    private SoniclocatorEntity soniclocatorTileEntity;

    public SoniclocatorContainer(InventoryPlayer par1InventoryPlayer, SoniclocatorEntity par2TileEntityFurnace)
    {
        // Hook the server world entity that is our block.
        this.soniclocatorTileEntity = par2TileEntityFurnace;

        // Input Slot 1 - Stack of gravel to replace our target blocks with.
        this.addSlotToContainer(new SoniclocatorSlotInputGravel(par2TileEntityFurnace, 0, 27, 35));

        // Input Slot 2 - Target block that we want the soniclocator to find.
        this.addSlotToContainer(new SoniclocatorSlotInputBlock(par2TileEntityFurnace, 1, 58, 35));

        // Output Slot 1 - Target blocks that the soniclocator has harvested.
        this.addSlotToContainer(new SoniclocatorSlotOutput(par1InventoryPlayer.player, par2TileEntityFurnace, 2, 127, 35));

        // Create slots for main player inventory area.
        int i;
        for (i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(par1InventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        // Create slots for player inventory hotbar area.
        for (i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(par1InventoryPlayer, i, 8 + i * 18, 142));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return this.soniclocatorTileEntity.isUseableByPlayer(par1EntityPlayer);
    }

    /** Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that. */
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int invSlot)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.inventorySlots.get(invSlot);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (invSlot == 2)
            {
                if (!this.mergeItemStack(itemstack1, 3, 38, true))
                {
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (invSlot != 1 && invSlot != 0)
            {
                if (invSlot >= 3 && invSlot < 30)
                {
                    if (!this.mergeItemStack(itemstack1, 30, 38, false))
                    {
                        return null;
                    }
                }
                else if (invSlot >= 30 && invSlot < 39 && !this.mergeItemStack(itemstack1, 3, 30, false))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 3, 38, false))
            {
                return null;
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack) null);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(player, itemstack1);
        }

        return itemstack;
    }
}

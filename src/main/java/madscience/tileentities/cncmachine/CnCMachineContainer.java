package madscience.tileentities.cncmachine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;

public class CnCMachineContainer extends Container
{
    // Tile entity from this world.
    private CnCMachineEntity ENTITY;

    public CnCMachineContainer(InventoryPlayer player, CnCMachineEntity tileEntity)
    {
        // Hook the server world entity that is our block.
        this.ENTITY = tileEntity; 

        // Input Slot 1 - Water bucket to cut the Iron block with.
        this.addSlotToContainer(new CnCMachineSlotInputWaterBucket(tileEntity, 0, 31, 34));

        // Input Slot 2 - Iron block that needs cutting into shape.
        this.addSlotToContainer(new CnCMachineSlotInputIronBlock(tileEntity, 1, 67, 44));
        
        // Input Slot 3 - Written book with binary code on what part to make.
        this.addSlotToContainer(new CnCMachineSlotInputFinishedBook(tileEntity, 2, 67, 17));

        // Output Slot 1 - Finished weapon part
        this.addSlotToContainer(new SlotFurnace(player.player, tileEntity, 3, 131, 44));

        // Output Slot 2 - Empty bucket.
        this.addSlotToContainer(new SlotFurnace(player.player, tileEntity, 4, 31, 9));

        // Create slots for main player inventory area.
        int i;
        for (i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(player, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        // Create slots for player inventory hotbar area.
        for (i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(player, i, 8 + i * 18, 142));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return this.ENTITY.isUseableByPlayer(par1EntityPlayer);
    }

    /** Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that. */
    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.inventorySlots.get(par2);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (par2 == 2)
            {
                if (!this.mergeItemStack(itemstack1, 3, 38, true))
                {
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (par2 != 1 && par2 != 0)
            {
                if (CnCMachineRecipes.getSmeltingResult(itemstack1) != null)
                {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false))
                    {
                        return null;
                    }
                }
                else if (par2 >= 3 && par2 < 30)
                {
                    if (!this.mergeItemStack(itemstack1, 30, 38, false))
                    {
                        return null;
                    }
                }
                else if (par2 >= 30 && par2 < 39 && !this.mergeItemStack(itemstack1, 3, 30, false))
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

            slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
        }

        return itemstack;
    }
}

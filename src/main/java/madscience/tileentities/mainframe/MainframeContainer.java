package madscience.tileentities.mainframe;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;

public class MainframeContainer extends Container
{
    private MainframeEntity mainframeTileEntity;

    public MainframeContainer(InventoryPlayer par1InventoryPlayer, MainframeEntity par2TileEntityFurnace)
    {
        // Hook the server world entity that is our block.
        this.mainframeTileEntity = par2TileEntityFurnace;

        // INPUT SLOT 1 - WATER BUCKET TO FILL INTERNAL TANK.
        this.addSlotToContainer(new MainframeSlotInputWaterBucket(par2TileEntityFurnace, 0, 27, 27));

        // INPUT SLOT 2 - GENOME INPUT 1
        this.addSlotToContainer(new MainframeSlotInputGenome(par2TileEntityFurnace, 1, 73, 17));

        // INPUT SLOT 3 - GENOME INPUT 2
        this.addSlotToContainer(new MainframeSlotInputGenome(par2TileEntityFurnace, 2, 96, 17));

        // INPUT SLOT 4 - EMPTY GENOME DATA REEL
        this.addSlotToContainer(new MainframeSlotInputGenomeEmpty(par2TileEntityFurnace, 3, 115, 55));

        // OUTPUT SLOT 1 - MERGED GENOME DATA REEL
        this.addSlotToContainer(new SlotFurnace(par1InventoryPlayer.player, par2TileEntityFurnace, 4, 148, 38));

        // OUTPUT SLOT 2 - EMPTY BUCKET USED TO FILL INTERNAL TANK.
        this.addSlotToContainer(new SlotFurnace(par1InventoryPlayer.player, par2TileEntityFurnace, 5, 27, 7));

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
        return this.mainframeTileEntity.isUseableByPlayer(par1EntityPlayer);
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
                if (par2 >= 3 && par2 < 30)
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

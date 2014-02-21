package madscience.tileentities.dnaextractor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class DNAExtractorContainer extends Container
{
    private DNAExtractorEntity dnaExtractorTileEntity;

    public DNAExtractorContainer(InventoryPlayer par1InventoryPlayer, DNAExtractorEntity par2TileEntityFurnace)
    {
        // Hook the server world entity.
        this.dnaExtractorTileEntity = par2TileEntityFurnace;

        // Input Slot 1 - Incoming filled needles of DNA from mobs.
        this.addSlotToContainer(new DNAExtractorSlotInput(par2TileEntityFurnace, 0, 9, 32));

        // Input Slot 2 - Empty bucket to get filled with mutant DNA.
        this.addSlotToContainer(new DNAExtractorSlotInputEmptyBucket(par2TileEntityFurnace, 1, 152, 61));

        // Output Slot 1 - DNA sample.
        this.addSlotToContainer(new DNAExtractorSlotOutput(par1InventoryPlayer.player, par2TileEntityFurnace, 2, 72, 32));

        // Output Slot 2 - Dirty needles.
        this.addSlotToContainer(new DNAExtractorSlotOutput(par1InventoryPlayer.player, par2TileEntityFurnace, 3, 105, 32));

        // Output Slot 3 - Filled mutant DNA bucket.
        this.addSlotToContainer(new DNAExtractorSlotOutput(par1InventoryPlayer.player, par2TileEntityFurnace, 4, 152, 36));

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
        return this.dnaExtractorTileEntity.isUseableByPlayer(par1EntityPlayer);
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
                if (DNAExtractorRecipes.getSmeltingResult(itemstack1) != null)
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

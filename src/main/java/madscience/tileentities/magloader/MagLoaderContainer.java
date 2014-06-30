package madscience.tileentities.magloader;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;

public class MagLoaderContainer extends Container
{
    private MagLoaderEntity ENTITY;

    public MagLoaderContainer(InventoryPlayer par1InventoryPlayer, MagLoaderEntity par2TileEntityFurnace)
    {
        // Hook the server world entity.
        this.ENTITY = par2TileEntityFurnace;

        // Input Slot 1 - Pulse Rifle Magazine that needs filling.
        this.addSlotToContainer(new MagLoaderSlotInput(par2TileEntityFurnace, 0, 89, 34));

        // Output Slot 1 - Filled pulse rifle magazine after cooking process.
        this.addSlotToContainer(new SlotFurnace(par1InventoryPlayer.player, par2TileEntityFurnace, 1, 143, 34));

        // Storage Area - Holds stacks of bullets which will be loaded into magazines.
        int rowCount = 3;
        int colCount = 4;
        final int SLOT_SIZE = 18;
        final int offsetX = 8;
        final int offsetY = 16;
        int slotNum = 2;
        int x = 0;
        int y = 0;

        for (int row = 0; row < rowCount; row++)
        {
            for (int col = 0; col < colCount; col++)
            {
                x = offsetX + col * SLOT_SIZE;
                y = offsetY + row * SLOT_SIZE;
                addSlotToContainer(new MagLoaderSlotStorage(par2TileEntityFurnace, slotNum, x, y));
                slotNum++;
            }
        }

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
        return this.ENTITY.isUseableByPlayer(par1EntityPlayer);
    }

    /** Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that. */
    @Override
    public ItemStack transferStackInSlot(EntityPlayer thePlayer, int slotNumber)
    {
        ItemStack stack = null;
        Slot slotObject = (Slot) inventorySlots.get(slotNumber);
        int machInvSize = ENTITY.getSizeInputInventory() + ENTITY.getSizeStorageInventory() + ENTITY.getSizeOutputInventory();

        if (slotObject != null && slotObject.getHasStack())
        {
            ItemStack stackInSlot = slotObject.getStack();
            stack = stackInSlot.copy();

            if (slotNumber < machInvSize)
            {
                if (!mergeItemStack(stackInSlot, machInvSize, inventorySlots.size(), true))
                {
                    return null;
                }
            }
            else if (!mergeItemStack(stackInSlot, 0, 9, false))
            {
                return null;
            }

            if (stackInSlot.stackSize == 0)
            {
                slotObject.putStack(null);
            }
            else
            {
                slotObject.onSlotChanged();
            }

            if (stackInSlot.stackSize == stack.stackSize)
            {
                return null;
            }

            slotObject.onPickupFromSlot(thePlayer, stackInSlot);
        }

        return stack;
    }
}

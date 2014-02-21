package madscience.tileentities.cryofreezer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class CryofreezerContainer extends Container
{
    private CryofreezerEntity cryoFreezerTileEntity;

    public CryofreezerContainer(InventoryPlayer par1InventoryPlayer, CryofreezerEntity par2TileEntityFurnace)
    {
        // Hook the server world entity.
        this.cryoFreezerTileEntity = par2TileEntityFurnace;

        // Our container object.
        this.cryoFreezerTileEntity.container = this;

        // Input Slot 1 - Iceblock or snowball input for freezer fuel.
        this.addSlotToContainer(new CryofreezerSlotInput(par2TileEntityFurnace, 0, 9, 35));

        // Storage Area - Heals needles and DNA samples by consuming ice or
        // snow.
        int rowCount = 3;
        int colCount = 7;
        final int SLOT_SIZE = 18;
        final int offsetX = 39;
        final int offsetY = 16;
        int slotNum = 1;
        int x = 0;
        int y = 0;

        for (int row = 0; row < rowCount; row++)
        {
            for (int col = 0; col < colCount; col++)
            {
                x = offsetX + col * SLOT_SIZE;
                y = offsetY + row * SLOT_SIZE;
                addSlotToContainer(new CryofreezerSlotStorage(par2TileEntityFurnace, slotNum, x, y));
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
        return this.cryoFreezerTileEntity.isUseableByPlayer(par1EntityPlayer);
    }

    /** Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that. */
    @Override
    public ItemStack transferStackInSlot(EntityPlayer thePlayer, int slotNumber)
    {
        ItemStack stack = null;
        Slot slotObject = (Slot) inventorySlots.get(slotNumber);
        int machInvSize = cryoFreezerTileEntity.getSizeInputInventory() + cryoFreezerTileEntity.getSizeOutputInventory();

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

package madscience.factory.tileentity;

import madscience.factory.MadTileEntityFactory;
import madscience.factory.MadTileEntityFactoryProduct;
import madscience.factory.slotcontainers.MadSlotContainerInterface;
import madscience.factory.slotcontainers.MadSlotContainerTypeEnum;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class MadContainerTemplate extends Container
{
    private MadTileEntity ENTITY;

    public MadContainerTemplate()
    {
        super();
    }

    public MadContainerTemplate(InventoryPlayer playerEntity, MadTileEntity tileEntity)
    {
        // Hook the server world entity.
        this.ENTITY = tileEntity;

        // Query machine registry for slot container information.
        MadTileEntityFactoryProduct MACHINE = MadTileEntityFactory.getMachineInfo(this.ENTITY.getMachineInternalName());

        // Grab our array of containers from the template object.
        MadSlotContainerInterface[] CONTAINERS = MACHINE.getContainerTemplate();

        // Loop through the containers and use the data inside them to prepare the server slot containers.
        for (int i = 0; i < CONTAINERS.length; i++)
        {
            MadSlotContainerInterface slotContainer = CONTAINERS[i];
            this.addSlotToContainer(new Slot(tileEntity, slotContainer.slot(), slotContainer.offsetX(), slotContainer.offsetY()));
        }

        // Create slots for main player inventory area.
        int i;
        for (i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(playerEntity, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        // Create slots for player inventory hotbar area.
        for (i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(playerEntity, i, 8 + i * 18, 142));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return this.ENTITY.isUseableByPlayer(par1EntityPlayer);
    }

    /** Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that. */
    @Override
    public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int slotNumber)
    {
        ItemStack copyOfSlotItem = null;
        Slot slotContainer = (Slot) this.inventorySlots.get(slotNumber);

        if (slotContainer != null && slotContainer.getHasStack())
        {
            ItemStack stackInSlot = slotContainer.getStack();
            copyOfSlotItem = stackInSlot.copy();
            int inventorySize = this.ENTITY.getSizeInventory();

            // Check if the slot number is within range of our actual inventory for the machine.
//            if (slotNumber <= inventorySize)
//            {
//                if (!this.mergeItemStack(stackInSlot, inventorySize, 38, true))
//                {
//                    return null;
//                }
//
//                slotContainer.onSlotChange(stackInSlot, copyOfSlotItem);
//            }
//            else if (!this.mergeItemStack(stackInSlot, inventorySize, 38, false))
//            {
//                return null;
//            }

//            if (stackInSlot.stackSize == 0)
//            {
//                slotContainer.putStack((ItemStack) null);
//            }
//            else
//            {
//                
//            }
//
//            if (stackInSlot.stackSize == copyOfSlotItem.stackSize)
//            {
//                return null;
//            }
//
//            slotContainer.onSlotChanged();
//            slotContainer.onPickupFromSlot(entityPlayer, stackInSlot);
        }

        return null;
    }

}
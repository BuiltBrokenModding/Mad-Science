package madscience.tileentities.dnaextractor;

import madscience.factory.interfaces.slotcontainers.MadSlotContainerInterface;
import madscience.factory.tileentity.MadTileEntityFactory;
import madscience.factory.tileentity.MadTileEntityTemplate;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;

public class DNAExtractorContainer extends Container
{
    private DNAExtractorEntity ENTITY;

    public DNAExtractorContainer(InventoryPlayer playerEntity, DNAExtractorEntity worldEntity)
    {
        // Hook the server world entity.
        this.ENTITY = worldEntity;
        
        // Query machine registry for slot container information.
        MadTileEntityTemplate MACHINE = MadTileEntityFactory.getMachineInfo(this.ENTITY.getInvName());
        
        // Grab our array of containers from the template object.
        MadSlotContainerInterface[] CONTAINERS = MACHINE.getSlotContainers();
        
        // Loop through the containers and use the data inside them to prepare the server slot containers.
        for(int i = 0; i < CONTAINERS.length; i++)
        {
            MadSlotContainerInterface slotContainer = CONTAINERS[i];
            this.addSlotToContainer(new Slot(worldEntity,
                    slotContainer.slot(),
                    slotContainer.offsetX(),
                    slotContainer.offsetY()));
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
        ItemStack itemstack = null;
        Slot slotContainer = (Slot) this.inventorySlots.get(slotNumber);

        if (slotContainer != null && slotContainer.getHasStack())
        {
            ItemStack itemstack1 = slotContainer.getStack();
            itemstack = itemstack1.copy();

            if (slotNumber == 2)
            {
                if (!this.mergeItemStack(itemstack1, 3, 38, true))
                {
                    return null;
                }

                slotContainer.onSlotChange(itemstack1, itemstack);
            }
            else if (slotNumber != 1 && slotNumber != 0)
            {
                if (DNAExtractorRecipes.getSmeltingResult(itemstack1) != null)
                {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false))
                    {
                        return null;
                    }
                }
                else if (slotNumber >= 3 && slotNumber < 30)
                {
                    if (!this.mergeItemStack(itemstack1, 30, 38, false))
                    {
                        return null;
                    }
                }
                else if (slotNumber >= 30 && slotNumber < 39 && !this.mergeItemStack(itemstack1, 3, 30, false))
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
                slotContainer.putStack((ItemStack) null);
            }
            else
            {
                slotContainer.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize)
            {
                return null;
            }

            slotContainer.onPickupFromSlot(entityPlayer, itemstack1);
        }

        return itemstack;
    }
}

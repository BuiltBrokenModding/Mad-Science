package madscience.factory.templates;

import madscience.factory.MadTileEntityFactory;
import madscience.factory.MadTileEntityFactoryProduct;
import madscience.factory.slotcontainers.MadSlotContainerInterface;
import madscience.tileentities.prefab.MadTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class MadContainerTemplate extends Container
{
    protected MadTileEntity ENTITY;

    public MadContainerTemplate()
    {
        super();
    }
    
    public MadContainerTemplate(InventoryPlayer playerEntity, MadTileEntity worldEntity)
    {
        // Hook the server world entity.
        this.ENTITY = worldEntity;
        
        // Query machine registry for slot container information.
        MadTileEntityFactoryProduct MACHINE = MadTileEntityFactory.getMachineInfo(this.ENTITY.getMachineInternalName());
        
        // Grab our array of containers from the template object.
        MadSlotContainerInterface[] CONTAINERS = MACHINE.getContainerTemplate();
        
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
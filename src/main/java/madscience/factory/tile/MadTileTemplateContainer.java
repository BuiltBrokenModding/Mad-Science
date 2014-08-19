package madscience.factory.tile;

import madscience.factory.MadTileEntityFactory;
import madscience.factory.container.MadSlotContainer;
import madscience.factory.product.MadTileEntityFactoryProduct;
import madscience.factory.slot.MadInputSlot;
import madscience.factory.slot.MadOutputSlot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class MadTileTemplateContainer extends Container
{
    private MadTileEntityPrefab ENTITY;
    
    private MadSlotContainer[] CONTAINERS;

    // For this particular case, there are only 2 slots in the custom inventory, plus a slot that is 'active'
    // but not able to interact with (like an active spell slot that doesn't really contain an item)
    private int ACTIVE_SLOT;

    // Note how these all back-reference themselves, so all you need to do is change the initial value and the
    // rest are automatically adjusted! Just swap ACTIVE_SLOT with your inventory index.
    private int INV_START;
    private int INV_END;
    private int HOTBAR_START;
    private int HOTBAR_END;

    public MadTileTemplateContainer()
    {
        super();
    }

    public MadTileTemplateContainer(InventoryPlayer entityPlayer, MadTileEntityPrefab tileEntity)
    {
        // Hook the server world entity.
        this.ENTITY = tileEntity;

        // Query machine registry for slot container information.
        MadTileEntityFactoryProduct MACHINE = MadTileEntityFactory.instance().getMachineInfo(this.ENTITY.getMachineInternalName());

        // Grab our array of containers from the template object.
        CONTAINERS = MACHINE.getContainerTemplate();

        // Loop through the containers and use the data inside them to prepare the server slot containers.
        for (int i = 0; i < CONTAINERS.length; i++)
        {
            MadSlotContainer slotContainer = CONTAINERS[i];
            
            // Depending on slot type we use different kinds of slot templates to help control what can be placed inside of them.
            if (slotContainer.getSlotType().name().toLowerCase().contains("input"))
            {
                // Use the custom slot template!
                this.addSlotToContainer(new MadInputSlot(tileEntity, slotContainer.slot(), slotContainer.offsetX(), slotContainer.offsetY(), slotContainer.getSlotType()));
            }
            else if (slotContainer.getSlotType().name().toLowerCase().contains("output"))
            {
                // Output slots cannot ever have items inserted into them and only taken out.
                this.addSlotToContainer(new MadOutputSlot(entityPlayer.player, tileEntity, slotContainer.slot(), slotContainer.offsetX(), slotContainer.offsetY(), slotContainer.getSlotType()));
            }
            else
            {
                // If we have no idea what this slot is just make it a normal one.
                this.addSlotToContainer(new Slot(tileEntity, slotContainer.slot(), slotContainer.offsetX(), slotContainer.offsetY()));
            }
        }

        // Create slots for main player inventory area.
        int i;
        for (i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(entityPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        // Create slots for player inventory hotbar area.
        for (i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(entityPlayer, i, 8 + i * 18, 142));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityPlayer)
    {
        return this.ENTITY.isUseableByPlayer(entityPlayer);
    }

    /** Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that. */
    @Override
    public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int slotNumber)
    {
        if (this.CONTAINERS == null)
        {
            return null;
        }
        
        ItemStack copyOfContainerItemStack = null;
        Slot containerSlot = (Slot) this.inventorySlots.get(slotNumber);
        
        // Ensures that shift-clicking will properly work. This method is from coolAlias.
        // http://www.minecraftforum.net/forums/mapping-and-modding/mapping-and-modding-tutorials/1571051-custom-container-how-to-properly-override-shift
        ACTIVE_SLOT = this.ENTITY.getInputSlotCount();
        INV_START = CONTAINERS.length;
        INV_END = INV_START + 26;
        HOTBAR_START = INV_END + 1;
        HOTBAR_END = HOTBAR_START + 8;
        
        if (containerSlot != null && containerSlot.getHasStack())
        {
            ItemStack itemBeingShiftClicked = containerSlot.getStack();
            copyOfContainerItemStack = itemBeingShiftClicked.copy();

            // Input ingredient or bust!
            if (slotNumber < INV_START + 1)
            {
                // try to place in player inventory / action bar
                if (!this.mergeItemStack(itemBeingShiftClicked, INV_START, HOTBAR_END + 1, true))
                {
                    return null;
                }

                containerSlot.onSlotChange(itemBeingShiftClicked, copyOfContainerItemStack);
            }
            // Item is in inventory / hotbar, try to place either in input slots.
            else
            {
                // Check if item is a input ingredient.
                if (this.ENTITY.isItemUsedInInputRecipes(itemBeingShiftClicked))
                {
                    for(int x = 0; x < ACTIVE_SLOT; x++) 
                    {
                        if (this.ENTITY.isItemValidForSlot(x, itemBeingShiftClicked))
                        {
                            if (!this.mergeItemStack(itemBeingShiftClicked, x, ACTIVE_SLOT, false))
                            {
                                return null;
                            }                
                        }
                    }
                }
                // Check if item in player's inventory, but not in action bar.
                else if (slotNumber >= INV_START && slotNumber < HOTBAR_START) 
                {
                    // place in action bar
                    if (!this.mergeItemStack(itemBeingShiftClicked, HOTBAR_START, HOTBAR_START + 1, false))
                    {
                        return null;
                    }
                }
                // Cannot place item in action bar - place in player inventory
                else if (slotNumber >= HOTBAR_START && slotNumber < HOTBAR_END + 1)
                {
                    if (!this.mergeItemStack(itemBeingShiftClicked, INV_START, INV_END + 1, false))
                    {
                        return null;
                    }
                }
            }

            if (itemBeingShiftClicked.stackSize == 0)
            {
                containerSlot.putStack((ItemStack) null);
            }
            else
            {
                containerSlot.onSlotChanged();
            }

            if (itemBeingShiftClicked.stackSize == copyOfContainerItemStack.stackSize)
            {
                return null;
            }

            containerSlot.onPickupFromSlot(entityPlayer, itemBeingShiftClicked);
        }

        return copyOfContainerItemStack;
    }
}
package madscience.factory.tileentity;

import madscience.factory.MadTileEntityFactory;
import madscience.factory.MadTileEntityFactoryProduct;
import madscience.factory.slotcontainers.MadSlotContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class MadContainerTemplate extends Container
{
    private MadTileEntityPrefab ENTITY;

    // For this particular case, there are only 2 slots in the custom inventory, plus a slot that is 'active'
    // but not able to interact with (like an active spell slot that doesn't really contain an item)
    private static int ACTIVE_SLOT = 0;

    // Note how these all back-reference themselves, so all you need to do is change the initial value and the
    // rest are automatically adjusted! Just swap ACTIVE_SLOT with your inventory index like the above tut
    private static final int ARMOR_START = ACTIVE_SLOT + 1, ARMOR_END = ARMOR_START + 3, INV_START = ARMOR_END + 1, INV_END = INV_START + 26, HOTBAR_START = INV_END + 1, HOTBAR_END = HOTBAR_START + 8;

    public MadContainerTemplate()
    {
        super();
    }

    public MadContainerTemplate(InventoryPlayer entityPlayer, MadTileEntityPrefab tileEntity)
    {
        // Hook the server world entity.
        this.ENTITY = tileEntity;

        // Query machine registry for slot container information.
        MadTileEntityFactoryProduct MACHINE = MadTileEntityFactory.getMachineInfo(this.ENTITY.getMachineInternalName());

        // Grab our array of containers from the template object.
        MadSlotContainer[] CONTAINERS = MACHINE.getContainerTemplate();

        // Ensures that shift-clicking will properly work. This method is from coolAlias.
        // http://www.minecraftforum.net/forums/mapping-and-modding/mapping-and-modding-tutorials/1571051-custom-container-how-to-properly-override-shift
        ACTIVE_SLOT = CONTAINERS.length;

        // Loop through the containers and use the data inside them to prepare the server slot containers.
        for (int i = 0; i < CONTAINERS.length; i++)
        {
            MadSlotContainer slotContainer = CONTAINERS[i];
            this.addSlotToContainer(new Slot(tileEntity, slotContainer.slot(), slotContainer.offsetX(), slotContainer.offsetY()));
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
        ItemStack copyOfContainerItemStack = null;
        Slot containerSlot = (Slot) this.inventorySlots.get(slotNumber);

        if (containerSlot != null && containerSlot.getHasStack())
        {
            ItemStack itemBeingShiftClicked = containerSlot.getStack();
            copyOfContainerItemStack = itemBeingShiftClicked.copy();

            // Either armor or input ingredient.
            if (slotNumber < ARMOR_END + 1 && slotNumber != ACTIVE_SLOT)
            {
                // try to place in player inventory / action bar
                if (!this.mergeItemStack(itemBeingShiftClicked, INV_START, HOTBAR_END + 1, true))
                {
                    return null;
                }

                containerSlot.onSlotChange(itemBeingShiftClicked, copyOfContainerItemStack);
            }
            // Item is in inventory / hotbar, try to place either in eye or armor slots.
            else
            {
                // Check if item is a input ingredient,
                if (this.ENTITY.isItemUsedInInputRecipes(itemBeingShiftClicked))
                {
                    if (!this.mergeItemStack(itemBeingShiftClicked, 0, ACTIVE_SLOT, false))
                    {
                        return null;
                    }
                }
                // Check if item is armor.
                else if (itemBeingShiftClicked.getItem() instanceof ItemArmor)
                {
                    int type = ((ItemArmor) itemBeingShiftClicked.getItem()).armorType;
                    if (!this.mergeItemStack(itemBeingShiftClicked, ARMOR_START + type, ARMOR_START + type + 1, false))
                    {
                        return null;
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
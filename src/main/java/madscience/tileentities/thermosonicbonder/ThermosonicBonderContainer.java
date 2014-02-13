package madscience.tileentities.thermosonicbonder;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ThermosonicBonderContainer extends Container
{
    // Tile entity that sits in the world.
    private ThermosonicBonderEntity thermosonicBonderTileEntity;

    public ThermosonicBonderContainer(InventoryPlayer par1InventoryPlayer, ThermosonicBonderEntity par2TileEntityFurnace)
    {
        // Hook the server world entity that is our block.
        this.thermosonicBonderTileEntity = par2TileEntityFurnace;

        // Input Slot 1 - Fresh egg to mutate with our genome.
        this.addSlotToContainer(new ThermosonicBonderSlotInputGoldNugget(par2TileEntityFurnace, 0, 37, 39));

        // Input Slot 2 - Completed genome data reel to encode onto egg.
        this.addSlotToContainer(new ThermosonicBonderSlotInputComponent(par2TileEntityFurnace, 1, 69, 39));

        // Output Slot 1 - Mob egg from genome encoding onto fresh egg.
        this.addSlotToContainer(new ThermosonicBonderSlotOutput(par1InventoryPlayer.player, par2TileEntityFurnace, 2, 140, 39));

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
        return this.thermosonicBonderTileEntity.isUseableByPlayer(par1EntityPlayer);
    }

    /** Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that. */
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int invSlot)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.inventorySlots.get(invSlot);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (invSlot == 2)
            {
                if (!this.mergeItemStack(itemstack1, 3, 38, true))
                {
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (invSlot != 1 && invSlot != 0)
            {
                if (ThermosonicBonderRecipes.getSmeltingResult(itemstack1) != null)
                {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false))
                    {
                        return null;
                    }
                }
                else if (invSlot >= 3 && invSlot < 30)
                {
                    if (!this.mergeItemStack(itemstack1, 30, 38, false))
                    {
                        return null;
                    }
                }
                else if (invSlot >= 30 && invSlot < 39 && !this.mergeItemStack(itemstack1, 3, 30, false))
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

            slot.onPickupFromSlot(player, itemstack1);
        }

        return itemstack;
    }
}
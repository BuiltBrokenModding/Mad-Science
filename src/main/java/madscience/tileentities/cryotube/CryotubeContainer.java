package madscience.tileentities.cryotube;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CryotubeContainer extends Container
{
    // Instance of our entity (logic) in the world.
    private CryotubeEntity cryoTubeTileEntity;

    public CryotubeContainer(InventoryPlayer par1InventoryPlayer, CryotubeEntity par2TileEntityFurnace)
    {
        // Hook the server world entity that is our block so we can get relevant
        // information from it.
        this.cryoTubeTileEntity = par2TileEntityFurnace;

        // Input Slot 1 - Spawn egg.
        this.addSlotToContainer(new CryotubeSlotInputSpawnEgg(par2TileEntityFurnace, 0, 11, 26));

        // Input Slot 2 - Empty data reel or existing subject memory data reel.
        this.addSlotToContainer(new CryotubeSlotInputMemoryDataReel(par2TileEntityFurnace, 1, 11, 47));

        // Input Slot 3 - Nether star to convert brainwaves into electricity.
        this.addSlotToContainer(new CryotubeSlotInputNetherStar(par2TileEntityFurnace, 2, 113, 52));

        // Output Slot 1 - Memory data reel of given subject.
        this.addSlotToContainer(new CryotubeSlotOutput(par1InventoryPlayer.player, par2TileEntityFurnace, 3, 144, 22));

        // Output Slot 2 - Rotten flesh from deceased subjects.
        this.addSlotToContainer(new CryotubeSlotOutput(par1InventoryPlayer.player, par2TileEntityFurnace, 4, 144, 56));

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
        return this.cryoTubeTileEntity.isUseableByPlayer(par1EntityPlayer);
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
                if (!this.mergeItemStack(itemstack1, 0, 1, false))
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

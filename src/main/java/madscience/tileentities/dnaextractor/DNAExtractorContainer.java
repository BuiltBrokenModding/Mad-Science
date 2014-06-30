package madscience.tileentities.dnaextractor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;

public class DNAExtractorContainer extends Container
{
    private DNAExtractorEntity ENTITY;

    public DNAExtractorContainer(InventoryPlayer par1InventoryPlayer, DNAExtractorEntity par2TileEntityFurnace)
    {
        // Hook the server world entity.
        this.ENTITY = par2TileEntityFurnace;

        // Input Slot 1 - Incoming filled needles of DNA from mobs.
        this.addSlotToContainer(new DNAExtractorSlotInput(par2TileEntityFurnace,
                DNAExtractorEnumContainer.InputGeneticMatrial.getSlotNumber(),
                DNAExtractorEnumContainer.InputGeneticMatrial.offsetX(),
                DNAExtractorEnumContainer.InputGeneticMatrial.offsetY()));

        // Input Slot 2 - Empty bucket to get filled with mutant DNA.
        this.addSlotToContainer(new DNAExtractorSlotInputEmptyBucket(par2TileEntityFurnace,
                DNAExtractorEnumContainer.InputEmptyBucket.getSlotNumber(),
                DNAExtractorEnumContainer.InputEmptyBucket.offsetX(),
                DNAExtractorEnumContainer.InputEmptyBucket.offsetY()));

        // Output Slot 1 - DNA sample.
        this.addSlotToContainer(new SlotFurnace(par1InventoryPlayer.player,
                par2TileEntityFurnace,
                DNAExtractorEnumContainer.OutputDNASample.getSlotNumber(),
                DNAExtractorEnumContainer.OutputDNASample.offsetX(),
                DNAExtractorEnumContainer.OutputDNASample.offsetY()));

        // Output Slot 2 - Dirty needles.
        this.addSlotToContainer(new SlotFurnace(par1InventoryPlayer.player,
                par2TileEntityFurnace,
                DNAExtractorEnumContainer.OutputDirtyNeedle.getSlotNumber(),
                DNAExtractorEnumContainer.OutputDirtyNeedle.offsetX(),
                DNAExtractorEnumContainer.OutputDirtyNeedle.offsetY()));

        // Output Slot 3 - Filled mutant DNA bucket.
        this.addSlotToContainer(new SlotFurnace(par1InventoryPlayer.player,
                par2TileEntityFurnace,
                DNAExtractorEnumContainer.OutputFilledMutantDNABucket.getSlotNumber(),
                DNAExtractorEnumContainer.OutputFilledMutantDNABucket.offsetX(),
                DNAExtractorEnumContainer.OutputFilledMutantDNABucket.offsetY()));

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

package madscience.items.weapons.pulseriflemagazine;

import madscience.MadWeapons;
import madscience.items.weapons.pulserifle.PulseRifleSounds;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.ICraftingHandler;

public class PulseRifleMagazineCraftingHandler implements ICraftingHandler
{
    @Override
    public void onCrafting(EntityPlayer player, ItemStack item, IInventory inv)
    {
        // If we are crafting a magazine with bullets then do not give the player a free magazine.
        if (item.itemID == MadWeapons.WEAPONITEM_MAGAZINEITEM.itemID)
        {
            // Play sound of shell being inserted into the magazine.
            player.worldObj.playSoundAtEntity(player, PulseRifleSounds.PULSERIFLE_MAGAZINERELOAD, 1.0F, 1.0F);
            return;
        }

        if (item.itemID == MadWeapons.WEAPONITEM_BULLETITEM.itemID)
        {
            int magazineSlot = 0;
            boolean shouldGiveEmptyMagazine = false;
            for (int currentSlot = 0; currentSlot < inv.getSizeInventory(); currentSlot++)
            {
                if (inv.getStackInSlot(currentSlot) != null)
                {
                    ItemStack currentSlotItemStack = inv.getStackInSlot(currentSlot);
                    if (currentSlotItemStack.getItem() != null && currentSlotItemStack.itemID == MadWeapons.WEAPONITEM_MAGAZINEITEM.itemID && currentSlotItemStack.isItemDamaged())
                    {
                        // Remember what slot was the magazine slot.
                        magazineSlot = currentSlot;
                        shouldGiveEmptyMagazine = true;
                        break;
                    }
                }
            }

            // Set the slot contents for modified magazine, we have to add 2 because Minecraft eats one by default for recipe.
            if (shouldGiveEmptyMagazine)
            {
                // Only give the empty magazine if we are supposed to.
                inv.setInventorySlotContents(magazineSlot, new ItemStack(MadWeapons.WEAPONITEM_MAGAZINEITEM, 2));
                player.worldObj.playSoundAtEntity(player, PulseRifleSounds.PULSERIFLE_MAGAZINEUNLOAD, 1.0F, 1.0F);
            }
            
            return;
        }
    }

    @Override
    public void onSmelting(EntityPlayer player, ItemStack item)
    {
        // Nothing to see here.
    }
}
package madscience.item.weapon.pulserifle;

import madscience.MadWeapons;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.ForgeSubscribe;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class PulseRifleItemRenderPlayer
{
    @ForgeSubscribe
    public void onRender(RenderPlayerEvent.Pre ev) // NO_UCD (unused code)
    {
        // Ensure the event is not null.
        if (ev == null)
        {
            return;
        }

        // Check the entity player.
        if (ev.entityPlayer == null)
        {
            return;
        }

        // Check currently being used item by the player.
        ItemStack playerItem = ev.entityPlayer.getCurrentEquippedItem();
        if (playerItem == null)
        {
            return;
        }

        // Check that currently used item is a M41A Pulse Rifle.
        if (!playerItem.isItemEqual(new ItemStack(MadWeapons.WEAPONITEM_PULSERIFLE)))
        {
            return;
        }

        // Check if the player is currently using the item based on stored NBT data.
        // Create NBT data if required.
        if (playerItem.stackTagCompound == null)
        {
            playerItem.stackTagCompound = new NBTTagCompound();
        }

        // Initialize variables we will use to communicate the status of the pulse rifle.
        int pulseRifleFireTime = 0;
        int previousFireTime = 0;
        int rightClickTime = 0;
        int primaryAmmoCount = 0;
        int secondaryAmmoCount = 0;
        boolean primaryFireModeEnabled = true;
        boolean isPrimaryEmpty = true;
        boolean isSecondaryEmpty = true;
        boolean isLeftPressed = false;
        boolean isRightPressed = false;

        // Grab NBT data from the item the player is holding.
        if (!playerItem.stackTagCompound.hasNoTags())
        {
            if (playerItem.stackTagCompound.hasKey("playerFireTime"))
            {
                pulseRifleFireTime = playerItem.stackTagCompound.getInteger("playerFireTime");
            }

            if (playerItem.stackTagCompound.hasKey("previousFireTime"))
            {
                previousFireTime = playerItem.stackTagCompound.getInteger("previousFireTime");
            }

            if (playerItem.stackTagCompound.hasKey("rightClickTime"))
            {
                rightClickTime = playerItem.stackTagCompound.getInteger("rightClickTime");
            }

            if (playerItem.stackTagCompound.hasKey("primaryAmmoCount"))
            {
                primaryAmmoCount = playerItem.stackTagCompound.getInteger("primaryAmmoCount");
            }

            if (playerItem.stackTagCompound.hasKey("secondaryAmmoCount"))
            {
                secondaryAmmoCount = playerItem.stackTagCompound.getInteger("secondaryAmmoCount");
            }

            if (playerItem.stackTagCompound.hasKey("primaryFireModeEnabled"))
            {
                primaryFireModeEnabled = playerItem.stackTagCompound.getBoolean("primaryFireModeEnabled");
            }

            if (playerItem.stackTagCompound.hasKey("isPrimaryEmpty"))
            {
                isPrimaryEmpty = playerItem.stackTagCompound.getBoolean("isPrimaryEmpty");
            }

            if (playerItem.stackTagCompound.hasKey("isSecondaryEmpty"))
            {
                isSecondaryEmpty = playerItem.stackTagCompound.getBoolean("isSecondaryEmpty");
            }

            if (playerItem.stackTagCompound.hasKey("isLeftPressed"))
            {
                isLeftPressed = playerItem.stackTagCompound.getBoolean("isLeftPressed");
            }

            if (playerItem.stackTagCompound.hasKey("isRightPressed"))
            {
                isRightPressed = playerItem.stackTagCompound.getBoolean("isRightPressed");
            }
        }

        // At this point we know player is holding pulse rifle and firing it.
        if (pulseRifleFireTime > 0 && isLeftPressed)
        {
            ev.entityPlayer.setItemInUse(playerItem, pulseRifleFireTime);
        }
    }
}

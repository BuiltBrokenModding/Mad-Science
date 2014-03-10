package madscience.items.weapons.pulserifle;

import madscience.MadWeapons;
import madscience.network.MadPackets;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;

public class WeaponItemPulseRiflePackets extends MadPackets
{
    private int playerButtonPressed;
    private int playerFireTime;
    private int primaryAmmoCount;
    private boolean primaryFireModeEnabled;
    private int secondaryAmmoCount;
    private boolean shouldUnloadWeapon;

    public WeaponItemPulseRiflePackets()
    {
        // Required for reflection.
    }

    public WeaponItemPulseRiflePackets(int fireTime, int buttonPressed, int primaryAmmo, int secondaryAmmo, boolean primaryFire, boolean shouldUnload)
    {
        playerFireTime = fireTime;
        playerButtonPressed = buttonPressed;
        primaryAmmoCount = primaryAmmo;
        secondaryAmmoCount = secondaryAmmo;
        primaryFireModeEnabled = primaryFire;
        shouldUnloadWeapon = shouldUnload;
    }

    @Override
    public void execute(EntityPlayer player, Side side) throws ProtocolException
    {
        // Packet received by client, executing payload.
        if (side.isServer())
        {
            // ------
            // SERVER
            // ------

            // Check if the player is holding an item at all.
            ItemStack playerHeldItem = player.getHeldItem();
            if (playerHeldItem == null)
            {
                return;
            }

            // Process information on the server for the client holding the pulse rifle.
            if (playerHeldItem.isItemEqual(new ItemStack(MadWeapons.WEAPONITEM_PULSERIFLE)))
            {
                // Tell the gun that we want it to fire a bullet using the information we gathered from the client.
                ((WeaponItemPulseRifle) playerHeldItem.getItem()).onRecievePacketFromClient(playerFireTime, playerButtonPressed, primaryAmmoCount, secondaryAmmoCount, primaryFireModeEnabled, shouldUnloadWeapon, player);
            }
            return;
        }

        if (side.isClient())
        {
            // ------
            // CLIENT
            // ------

            // Check if the player is holding an item at all.
            ItemStack playerHeldItem = player.getHeldItem();
            if (playerHeldItem == null)
            {
                return;
            }

            // Process information on the server for the client holding the pulse rifle.
            if (playerHeldItem.isItemEqual(new ItemStack(MadWeapons.WEAPONITEM_PULSERIFLE)))
            {
                // Tell the gun that we want it to fire a bullet using the information we gathered from the client.
                ((WeaponItemPulseRifle) playerHeldItem.getItem()).onRecievePacketFromServer(playerFireTime, playerButtonPressed, primaryAmmoCount, secondaryAmmoCount, primaryFireModeEnabled, shouldUnloadWeapon, player);
                return;
            }
        }
        else
        {
            throw new ProtocolException("Cannot not apply server packet to a client!");
        }
    }

    @Override
    public void read(ByteArrayDataInput in) throws ProtocolException
    {
        playerFireTime = in.readInt();
        playerButtonPressed = in.readInt();
        primaryAmmoCount = in.readInt();
        secondaryAmmoCount = in.readInt();
        primaryFireModeEnabled = in.readBoolean();
        shouldUnloadWeapon = in.readBoolean();
    }

    @Override
    public void write(ByteArrayDataOutput out)
    {
        out.writeInt(playerFireTime);
        out.writeInt(playerButtonPressed);
        out.writeInt(primaryAmmoCount);
        out.writeInt(secondaryAmmoCount);
        out.writeBoolean(primaryFireModeEnabled);
        out.writeBoolean(shouldUnloadWeapon);
    }
}
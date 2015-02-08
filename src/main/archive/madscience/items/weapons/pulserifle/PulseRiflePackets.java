package madscience.content.items.weapons.pulserifle;

import madscience.MadWeapons;
import madscience.network.MadPackets;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;

public class PulseRiflePackets extends MadPackets
{
    private boolean isPrimaryEmpty;
    private boolean isSecondaryEmpty;
    private boolean leftMouseDown;
    private int playerButtonPressed;
    private int playerFireTime;
    private int playerRightClickTime;
    private int previousFireTime;
    private int primaryAmmoCount;
    private boolean primaryFireModeEnabled;
    private boolean rightMouseDown;
    private int secondaryAmmoCount;
    private boolean shouldUnloadWeapon;
    private boolean magazineInserted;

    public PulseRiflePackets()
    {
        // Required for reflection.
    }

    public PulseRiflePackets(int fireTime, int lastFireTime, int rightClickTime, int buttonPressed, int primaryAmmo, int secondaryAmmo, boolean primaryFire, boolean primaryEmpty, boolean secondaryEmpty, boolean shouldUnload, boolean isLeftMouseDown,
            boolean isRightMouseDown, boolean magazineLoaded)
    {
        playerFireTime = fireTime;
        previousFireTime = lastFireTime;
        playerRightClickTime = rightClickTime;
        playerButtonPressed = buttonPressed;
        primaryAmmoCount = primaryAmmo;
        secondaryAmmoCount = secondaryAmmo;
        primaryFireModeEnabled = primaryFire;
        shouldUnloadWeapon = shouldUnload;
        isPrimaryEmpty = primaryEmpty;
        isSecondaryEmpty = secondaryEmpty;
        leftMouseDown = isLeftMouseDown;
        rightMouseDown = isRightMouseDown;
        magazineInserted = magazineLoaded;
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

            // Server side only please.
            if (player.worldObj.isRemote)
            {
                return;
            }

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
                ((PulseRifleItem) playerHeldItem.getItem()).onRecievePacketFromClient(playerFireTime, previousFireTime, playerRightClickTime, playerButtonPressed, primaryFireModeEnabled, shouldUnloadWeapon, isPrimaryEmpty, isSecondaryEmpty, leftMouseDown,
                        rightMouseDown, magazineInserted, player);
            }
            return;
        }
        else if (side.isClient())
        {
            // ------
            // CLIENT
            // ------

            // Server side only please.
            if (!player.worldObj.isRemote)
            {
                return;
            }

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
                // MadScience.logger.info("Primary Ammo: " + primaryAmmoCount);
                ((PulseRifleItem) playerHeldItem.getItem()).onRecievePacketFromServer(playerFireTime, previousFireTime, playerRightClickTime, playerButtonPressed, primaryAmmoCount, secondaryAmmoCount, primaryFireModeEnabled, shouldUnloadWeapon,
                        isPrimaryEmpty, isSecondaryEmpty, leftMouseDown, rightMouseDown, magazineInserted, player);
                return;
            }
        }
        else
        {
            throw new ProtocolException("Packet was sent with no side information, this is very bad! Possible hacking attempt with pulse rifle!");
        }
    }

    @Override
    public void read(ByteArrayDataInput in) throws ProtocolException
    {
        playerFireTime = in.readInt();
        previousFireTime = in.readInt();
        playerRightClickTime = in.readInt();
        playerButtonPressed = in.readInt();
        primaryAmmoCount = in.readInt();
        secondaryAmmoCount = in.readInt();
        primaryFireModeEnabled = in.readBoolean();
        shouldUnloadWeapon = in.readBoolean();
        isPrimaryEmpty = in.readBoolean();
        isSecondaryEmpty = in.readBoolean();
        leftMouseDown = in.readBoolean();
        rightMouseDown = in.readBoolean();
        magazineInserted = in.readBoolean();
    }

    @Override
    public void write(ByteArrayDataOutput out)
    {
        out.writeInt(playerFireTime);
        out.writeInt(previousFireTime);
        out.writeInt(playerRightClickTime);
        out.writeInt(playerButtonPressed);
        out.writeInt(primaryAmmoCount);
        out.writeInt(secondaryAmmoCount);
        out.writeBoolean(primaryFireModeEnabled);
        out.writeBoolean(shouldUnloadWeapon);
        out.writeBoolean(isPrimaryEmpty);
        out.writeBoolean(isSecondaryEmpty);
        out.writeBoolean(leftMouseDown);
        out.writeBoolean(rightMouseDown);
        out.writeBoolean(magazineInserted);
    }
}
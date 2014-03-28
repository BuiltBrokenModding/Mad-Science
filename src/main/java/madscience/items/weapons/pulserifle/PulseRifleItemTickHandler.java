package madscience.items.weapons.pulserifle;

import java.util.EnumSet;

import madscience.MadScience;
import madscience.MadWeapons;
import madscience.items.weapons.KeyBindingInterceptor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class PulseRifleItemTickHandler implements ITickHandler
{
    GameSettings gs = null;
    KeyBindingInterceptor intLeft = null;
    KeyBindingInterceptor intRight = null;

    public PulseRifleItemTickHandler()
    {
        // Get the current client game instance, but don't call this on the server.
        if (!MadScience.proxy.getClient().equals(null))
        {
            try
            {
                gs = Minecraft.getMinecraft().gameSettings;
            }
            catch (Exception err)
            {
                MadScience.logger.info("Skipping pulse rifle keybinding interceptor init on server!");
                return;
            }

            // Setup key intercepter so we can have full control over left and right click events.
            if (intLeft == null && gs != null)
            {
                intLeft = new KeyBindingInterceptor(gs.keyBindAttack);
                gs.keyBindAttack = intLeft;
                intLeft.setInterceptionActive(true);
            }

            if (intRight == null && gs != null)
            {
                intRight = new KeyBindingInterceptor(gs.keyBindUseItem);
                gs.keyBindUseItem = intRight;
                intRight.setInterceptionActive(true);
            }
        }
    }

    public void disableKeyIntercepter()
    {
        // Remove key intercepter so we can have relinquish full control over left and right click events.
        if (intLeft != null)
        {
            intLeft.setInterceptionActive(false);
            gs.keyBindAttack = intLeft.getOriginalKeyBinding();
            intLeft = null;
        }

        if (intRight != null)
        {
            intRight.setInterceptionActive(false);
            gs.keyBindUseItem = intRight.getOriginalKeyBinding();
            intRight = null;
        }
    }

    @Override
    public String getLabel()
    {
        // Name as known by Forge for debugging purposes.
        return MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + " TickHandler";
    }

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData)
    {
        // Nothing to see here.
    }

    @Override
    public EnumSet<TickType> ticks()
    {
        // Client side only operation for tick operation, custom packets sent to server.
        return EnumSet.of(TickType.RENDER, TickType.CLIENT);
    }

    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData)
    {
        // Check if there is a world.
        World world = MadScience.proxy.getClientWorld();
        if (world == null)
        {
            this.disableKeyIntercepter();
            return;
        }

        // Check if there is a player to control.
        EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
        if (player == null)
        {
            this.disableKeyIntercepter();
            return;
        }

        // Check if the player is holding an item at all.
        ItemStack playerHeldItem = player.getHeldItem();
        if (playerHeldItem == null)
        {
            this.disableKeyIntercepter();
            return;
        }

        // Check if game settings object is null. Should never be the case!
        if (gs == null)
        {
            this.disableKeyIntercepter();
            return;
        }

        // Holy shit, we're holding something, is it what we care about?
        if (playerHeldItem.isItemEqual(new ItemStack(MadWeapons.WEAPONITEM_PULSERIFLE)))
        {
            // Only intercept keys from client if they are holding the pulse rifle.
            boolean pleft = false;
            boolean pright = false;

            // Setup key intercepter so we can have full control over left and right click events.
            if (intLeft == null)
            {
                intLeft = new KeyBindingInterceptor(gs.keyBindAttack);
                gs.keyBindAttack = intLeft;
                intLeft.setInterceptionActive(true);
            }

            if (intRight == null)
            {
                intRight = new KeyBindingInterceptor(gs.keyBindUseItem);
                gs.keyBindUseItem = intRight;
                intRight.setInterceptionActive(true);
            }

            // Create NBT data if required.
            if (playerHeldItem.stackTagCompound == null)
            {
                playerHeldItem.stackTagCompound = new NBTTagCompound();
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
            boolean magazineInserted = false;

            // Grab NBT data from the item the player is holding.
            if (!playerHeldItem.stackTagCompound.hasNoTags())
            {
                if (playerHeldItem.stackTagCompound.hasKey("playerFireTime"))
                {
                    pulseRifleFireTime = playerHeldItem.stackTagCompound.getInteger("playerFireTime");
                }

                if (playerHeldItem.stackTagCompound.hasKey("previousFireTime"))
                {
                    previousFireTime = playerHeldItem.stackTagCompound.getInteger("previousFireTime");
                }

                if (playerHeldItem.stackTagCompound.hasKey("rightClickTime"))
                {
                    rightClickTime = playerHeldItem.stackTagCompound.getInteger("rightClickTime");
                }

                if (playerHeldItem.stackTagCompound.hasKey("primaryAmmoCount"))
                {
                    primaryAmmoCount = playerHeldItem.stackTagCompound.getInteger("primaryAmmoCount");
                }

                if (playerHeldItem.stackTagCompound.hasKey("secondaryAmmoCount"))
                {
                    secondaryAmmoCount = playerHeldItem.stackTagCompound.getInteger("secondaryAmmoCount");
                }

                if (playerHeldItem.stackTagCompound.hasKey("primaryFireModeEnabled"))
                {
                    primaryFireModeEnabled = playerHeldItem.stackTagCompound.getBoolean("primaryFireModeEnabled");
                }

                if (playerHeldItem.stackTagCompound.hasKey("isPrimaryEmpty"))
                {
                    isPrimaryEmpty = playerHeldItem.stackTagCompound.getBoolean("isPrimaryEmpty");
                }

                if (playerHeldItem.stackTagCompound.hasKey("isSecondaryEmpty"))
                {
                    isSecondaryEmpty = playerHeldItem.stackTagCompound.getBoolean("isSecondaryEmpty");
                }

                if (playerHeldItem.stackTagCompound.hasKey("isLeftPressed"))
                {
                    isLeftPressed = playerHeldItem.stackTagCompound.getBoolean("isLeftPressed");
                }

                if (playerHeldItem.stackTagCompound.hasKey("isRightPressed"))
                {
                    isRightPressed = playerHeldItem.stackTagCompound.getBoolean("isRightPressed");
                }
                
                if (playerHeldItem.stackTagCompound.hasKey("magazineInserted"))
                {
                    magazineInserted = playerHeldItem.stackTagCompound.getBoolean("magazineInserted");
                }
            }

            // Change the FOV of our screen when shooting the pulse rifle.
            if (type.equals(EnumSet.of(TickType.RENDER)))
            {
                // Only run the right-click functionality if internal NBT firetime is greater than zero.
                if (isLeftPressed && pulseRifleFireTime > 0 && primaryFireModeEnabled)
                {
                    MadScience.proxy.onBowUse(playerHeldItem, player, pulseRifleFireTime);
                    // player.setItemInUse(playerHeldItem, pulseRifleFireTime);
                    // playerHeldItem.useItemRightClick(world, player);
                }
                else
                {
                    MadScience.proxy.resetSavedFOV();
                }
            }

            // LEFT CLICK
            if (intLeft.isKeyDown())
            {
                isLeftPressed = true;

                if (!pleft)
                {
                    pleft = true;
                }

                if (pleft)
                {
                    intLeft.retrieveClick();
                }
            }
            else
            {
                isLeftPressed = false;
                pleft = false;

                // Sets the fire time back to zero, this is purely client-side operation.
                playerHeldItem.stackTagCompound.setInteger("playerFireTime", 0);
                playerHeldItem.stackTagCompound.setInteger("previousFireTime", 0);

                // Sets the is empty status to false so it can be triggered again.
                playerHeldItem.stackTagCompound.setBoolean("isPrimaryEmpty", false);
                playerHeldItem.stackTagCompound.setBoolean("isSecondaryEmpty", false);

                // Return the field of view to normal.
                MadScience.proxy.resetSavedFOV();
            }

            // RIGHT CLICK
            if (intRight.isKeyDown())
            {
                isRightPressed = true;

                if (!pright)
                {
                    pright = true;
                }

                if (pright)
                {
                    intRight.retrieveClick();
                }
            }
            else
            {
                isRightPressed = false;
                pright = false;
                playerHeldItem.stackTagCompound.setInteger("rightClickTime", 0);
            }

            // Save mouse NBT data that is used in packet system.
            playerHeldItem.stackTagCompound.setBoolean("isRightPressed", isRightPressed);
            playerHeldItem.stackTagCompound.setBoolean("isLeftPressed", isLeftPressed);

            // Always flatten right-click time when you let go of the key (for changing fire modes and reloading/unloading).
            if (intRight != null && !intRight.isKeyDown() && rightClickTime > 0)
            {
                rightClickTime = 0;
                playerHeldItem.stackTagCompound.setInteger("rightClickTime", rightClickTime);
                // MadScience.logger.info("Client: Reset Rightclick Time");
            }

            // Reset the primary empty prompts.
            if (pulseRifleFireTime <= 0 && primaryAmmoCount <= 0 && isPrimaryEmpty && intLeft != null && !intLeft.isKeyDown())
            {
                isPrimaryEmpty = false;
                playerHeldItem.stackTagCompound.setBoolean("isPrimaryEmpty", isPrimaryEmpty);
                // MadScience.logger.info("Client: Reset Primary Empty");
            }

            // Reset the secondary empty prompts.
            if (pulseRifleFireTime <= 0 && secondaryAmmoCount <= 0 && isSecondaryEmpty && intLeft != null && !intLeft.isKeyDown())
            {
                isSecondaryEmpty = false;
                playerHeldItem.stackTagCompound.setBoolean("isSecondaryEmpty", isSecondaryEmpty);
                // MadScience.logger.info("Client: Reset Secondary Empty");
            }

            // Flatten left-click time when not holding the button down and there is something to decrease.
            if (intLeft != null && !intLeft.isKeyDown() && pulseRifleFireTime > 0 && previousFireTime >= 0)
            {
                pulseRifleFireTime = 0;
                previousFireTime = 0;
                playerHeldItem.stackTagCompound.setInteger("playerFireTime", pulseRifleFireTime);
                playerHeldItem.stackTagCompound.setInteger("previousFireTime", previousFireTime);

                isPrimaryEmpty = false;
                playerHeldItem.stackTagCompound.setBoolean("isPrimaryEmpty", isPrimaryEmpty);

                isSecondaryEmpty = false;
                playerHeldItem.stackTagCompound.setBoolean("isSecondaryEmpty", isSecondaryEmpty);
                // MadScience.logger.info("Client: Reset Firetime");
            }

            // Flatten out the ammo if we are primary.
            if (intLeft != null && intLeft.isKeyDown() && pulseRifleFireTime > 0 && previousFireTime >= 0 && primaryAmmoCount <= 0 && primaryFireModeEnabled)
            {
                // MadScience.logger.info("PRIMARY CLEAR");
                isPrimaryEmpty = false;
                playerHeldItem.stackTagCompound.setBoolean("isPrimaryEmpty", isPrimaryEmpty);
                pulseRifleFireTime = 0;
                previousFireTime = 0;
                playerHeldItem.stackTagCompound.setInteger("playerFireTime", pulseRifleFireTime);
                playerHeldItem.stackTagCompound.setInteger("previousFireTime", previousFireTime);
            }

            // Flatten out the ammo if we are secondary.
            if (intLeft != null && intLeft.isKeyDown() && pulseRifleFireTime > 0 && previousFireTime >= 0 && secondaryAmmoCount <= 0 && !primaryFireModeEnabled)
            {
                // MadScience.logger.info("SECONDARY CLEAR");
                isSecondaryEmpty = false;
                playerHeldItem.stackTagCompound.setBoolean("isSecondaryEmpty", isSecondaryEmpty);
                pulseRifleFireTime = 0;
                previousFireTime = 0;
                playerHeldItem.stackTagCompound.setInteger("playerFireTime", pulseRifleFireTime);
                playerHeldItem.stackTagCompound.setInteger("previousFireTime", previousFireTime);
            }

            // Check if the weapon should still be firing at the end of this tick before next one.
            if (intLeft.isKeyDown() && pleft && intLeft.pressed)
            {
                // Send a left clicking packet.
                PacketDispatcher.sendPacketToServer(new PulseRiflePackets(pulseRifleFireTime, previousFireTime, rightClickTime, 0, primaryAmmoCount, secondaryAmmoCount, primaryFireModeEnabled, isPrimaryEmpty, isSecondaryEmpty, player.isSneaking(),
                        isLeftPressed, isRightPressed, magazineInserted).makePacket());

                intLeft.pressed = true;
                intLeft.pressTime++;

                if (pulseRifleFireTime > 0)
                {
                    intRight.pressed = true;
                    intRight.pressTime++;
                }
                return;
            }

            // Ensure that right-click will always fire one time by increasing it's firetime manually.
            if (intRight.isKeyDown() && pright && intRight.pressed && intRight.pressTime == 0)
            {
                // Send a right clicking packet.
                PacketDispatcher.sendPacketToServer(new PulseRiflePackets(pulseRifleFireTime, previousFireTime, rightClickTime, 2, primaryAmmoCount, secondaryAmmoCount, primaryFireModeEnabled, isPrimaryEmpty, isSecondaryEmpty, player.isSneaking(),
                        isLeftPressed, isRightPressed, magazineInserted).makePacket());

                intRight.pressed = true;
                intRight.pressTime++;
                return;
            }

            // If we reach the bottom of this statement then no right or left click is being held and everything should be zeroed out.
            if (!intRight.isKeyDown())
            {
                pulseRifleFireTime = 0;
                playerHeldItem.stackTagCompound.setInteger("playerFireTime", pulseRifleFireTime);

                previousFireTime = 0;
                playerHeldItem.stackTagCompound.setInteger("previousFireTime", previousFireTime);

                isLeftPressed = false;
                playerHeldItem.stackTagCompound.setBoolean("isLeftPressed", isLeftPressed);
            }

            if (!intLeft.isKeyDown())
            {
                rightClickTime = 0;
                isRightPressed = false;
                playerHeldItem.stackTagCompound.setInteger("rightClickTime", rightClickTime);
                playerHeldItem.stackTagCompound.setBoolean("isRightPressed", isRightPressed);
            }

            if (world.getWorldTime() % 10L == 0L)
            {
                PacketDispatcher.sendPacketToServer(new PulseRiflePackets(pulseRifleFireTime, previousFireTime, rightClickTime, 1, primaryAmmoCount, secondaryAmmoCount, primaryFireModeEnabled, isPrimaryEmpty, isSecondaryEmpty, player.isSneaking(),
                        isLeftPressed, isRightPressed, magazineInserted).makePacket());
            }
        }
        else
        {
            this.disableKeyIntercepter();
        }
    }

}

package madscience.items.weapons.pulserifle;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import madscience.MadEntities;
import madscience.MadScience;
import madscience.MadWeapons;
import madscience.items.weapons.pulseriflebullet.PulseRifleBulletEntity;
import madscience.items.weapons.pulseriflegrenade.PulseRifleGrenadeEntity;
import madscience.items.weapons.pulseriflemagazine.PulseRifleMagazineComparator;
import madscience.items.weapons.pulseriflemagazine.PulseRifleMagazineComparatorItem;
import madscience.util.MadUtils;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PulseRifleItem extends ItemBow
{
    private static final int BULLET_MAXIMUM = 99;
    private static final int GRENADE_MAXIMUM = 4;

    public PulseRifleItem(int itemID)
    {
        // Inform Minecraft that there is an amazing weapon.
        super(itemID);
        this.setUnlocalizedName(MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME);
        this.setCreativeTab(MadEntities.tabMadScience);

        // Cannot repair pulse rifle using Anvil.
        this.setNoRepair();

        // Maximum damage is set to zero.
        this.setMaxDamage(0);

        // We do not have any sub-types.
        this.setHasSubtypes(false);

        // Pulse Rifles don't stack.
        this.maxStackSize = 1;
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List info, boolean par4)
    {
        // Display localalized tooltip for the item from the language file that is currently active.
        String tooltip = StatCollector.translateToLocal("item." + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + ".tooltip");
        if (tooltip != null && tooltip.length() > 0)
        {
            info.addAll(MadUtils.splitStringPerWord(tooltip, 4));
        }
    }

    @Override
    public boolean canHarvestBlock(Block par1Block)
    {
        // You cannot harvest blocks with a weapon.
        return false;
    }

    @Override
    public boolean canItemEditBlocks()
    {
        // Prevents this item from being able to edit blocks in the world.
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
    {
        // Create NBT data if required.
        if (par1ItemStack.stackTagCompound == null)
        {
            par1ItemStack.stackTagCompound = new NBTTagCompound();
        }

        // Use this color retrieval function to get current render pass and write it to NBT data.
        par1ItemStack.stackTagCompound.setInteger("renderPass", par2);

        // Grab our current firing mode, we need it to determine colors.
        boolean primaryFireModeEnabled = true;
        if (par1ItemStack.stackTagCompound.hasKey("primaryFireModeEnabled"))
        {
            primaryFireModeEnabled = par1ItemStack.stackTagCompound.getBoolean("primaryFireModeEnabled");
        }

        // Depending on fire mode set the ammunition counter to red for bullets or blue for grenades.
        switch (par2)
        {
        case 0:
        {
            return 16777215;
        }
        case 1:
        case 2:
        {
            if (primaryFireModeEnabled)
            {
                return Color.RED.getRGB();
            }
            else
            {
                return Color.CYAN.getRGB();
            }
        }
        }

        // Always return the default color since we have no intention of changing it here.
        return 16777215;
    }

    public int getDamageVsEntity(Entity par1Entity)
    {
        // You cannot hurt other people with the weapon in melee
        return 0;
    }

    @Override
    public int getItemEnchantability()
    {
        // Weapons are not enchantable.
        return 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getItemIconForUseDuration(int par1)
    {
        //MadScience.logger.info("DURATION: " + par1);
        return MadWeapons.WEAPONITEM_PULSERIFLE.itemIcon;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        // Tells animation system to play animation of bow with hands stuck out infront of player.
        return EnumAction.bow;
    }

    @Override
    public int getRenderPasses(int metadata)
    {
        // Tell the ItemRenderer that we would like to be smacked 4 times.
        return 4;
    }

    @Override
    public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block)
    {
        // Trying to break a block with a weapon won't work.
        return 0.0F;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        // Displays the localized name for the item from the language file that is currently active.
        String name = ("item." + StatCollector.translateToLocal(MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME)).trim();
        return name;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isFull3D()
    {
        // Informs minecraft to render this item like a bow and not a regular item.
        return true;
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, int X, int Y, int Z, EntityPlayer player)
    {
        // True to prevent harvesting, false to continue as normal
        return true;
    }

    @Override
    public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        return par1ItemStack;
    }

    @Override
    public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack)
    {
        // Prevent the swinging animation that minecraft items do when left-click.
        return true;
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        // Prevent any further processing by normal Minecraft code.
        return true;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
    {
        // Processed before damage is done, further processing is canceled and the entity is not attacked.
        return true;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4)
    {
        // Prevent the underlying bow class from firing an arrow when we let go of the fire button.
    }

    public void onRecievePacketFromClient(int clientFireTime, int clientpreviousFireTime, int clientrightClickTime, int clientButtonPressed, boolean clientprimaryFireModeEnabled, boolean clientshouldUnloadWeapon, boolean clientisPrimaryEmpty,
            boolean clientisSecondaryEmpty, boolean leftPressed, boolean rightPressed, EntityPlayer player)
    {
        // Hook the item the player is currently holding in his hand (if there is any).
        ItemStack playerItem = player.getHeldItem();
        if (playerItem == null)
        {
            return;
        }

        // Only do this for our pulse rifle.
        if (!playerItem.isItemEqual(new ItemStack(MadWeapons.WEAPONITEM_PULSERIFLE)))
        {
            return;
        }

        // Create NBT data if required.
        if (playerItem.stackTagCompound == null)
        {
            playerItem.stackTagCompound = new NBTTagCompound();
        }

        // ------
        // SERVER
        // ------
        if (player.worldObj.isRemote)
        {
            return;
        }

        // Initialize variables we will use to communicate the status of the pulse rifle.
        int primaryAmmoCount = 0;
        int secondaryAmmoCount = 0;
        boolean primaryFireModeEnabled = clientprimaryFireModeEnabled;
        boolean isLeftPressed = leftPressed;
        boolean isRightPressed = rightPressed;

        // Special variable used only on the server to enable semi-automatic firing of grenades.
        boolean hasFiredGrenade = false;

        // Grab NBT data from the item the player is holding.
        if (!playerItem.stackTagCompound.hasNoTags())
        {
            // Semi-automatic grenade check
            if (playerItem.stackTagCompound.hasKey("hasFiredGrenade"))
            {
                hasFiredGrenade = playerItem.stackTagCompound.getBoolean("hasFiredGrenade");
            }

            // Ammo
            if (playerItem.stackTagCompound.hasKey("primaryAmmoCount"))
            {
                primaryAmmoCount = playerItem.stackTagCompound.getInteger("primaryAmmoCount");
            }

            if (playerItem.stackTagCompound.hasKey("secondaryAmmoCount"))
            {
                secondaryAmmoCount = playerItem.stackTagCompound.getInteger("secondaryAmmoCount");
            }

            // Firing mode
            if (playerItem.stackTagCompound.hasKey("primaryFireModeEnabled"))
            {
                primaryFireModeEnabled = playerItem.stackTagCompound.getBoolean("primaryFireModeEnabled");
            }
        }

        // Called when we receive a packet from a client that is telling us to fire the weapon!
        if (clientButtonPressed == 0 && leftPressed)
        {
            // ----------
            // LEFT CLICK
            // ----------
            
            // Decrease the amount of ammo depending on fire-mode.
            if (primaryFireModeEnabled)
            {
                // ------------
                // PRIMARY FIRE
                // ------------
                if (primaryAmmoCount > 0)
                {
                    // Play the sound of the pulse rifle are predictable rates.
                    boolean shouldFire = false;
                    if (player.worldObj.getWorldTime() % 12F == 0L && clientFireTime >= 1)
                    {
                        player.worldObj.playSoundAtEntity(player, PulseRifleSounds.PULSERIFLE_FIRE, 0.1F, 1.0F);
                        shouldFire = true;
                    }
                    else if (clientFireTime <= 0)
                    {
                        player.worldObj.playSoundAtEntity(player, PulseRifleSounds.PULSERIFLE_FIRE, 0.1F, 1.0F);
                        shouldFire = true;
                    }

                    // Increment the fire time on the weapon so we know how long it has been shooting.
                    clientpreviousFireTime = clientFireTime;
                    clientFireTime += 1;

                    // Prevents bullet-spam and the gun firing a bullet every single tick, a little slower...
                    if (shouldFire)
                    {
                        if (clientFireTime <= clientpreviousFireTime)
                        {
                            // MadScience.logger.info("SKIPPING BECAUSE FIRETIME WAS " + previousFireTime + " / " + pulseRifleFireTime);
                            // Nothing to see here...
                        }
                        else
                        {
                            // Remove a bullet.
                            primaryAmmoCount--;

                            // Actually spawn the bullet in the game world.
                            player.worldObj.spawnEntityInWorld(new PulseRifleBulletEntity(player.worldObj, player, 4.2F));
                        }
                    }
                }
                else
                {
                    if (!clientisPrimaryEmpty)
                    {
                        // Out of bullets.
                        player.worldObj.playSoundAtEntity(player, PulseRifleSounds.PULSERIFLE_EMPTY, 0.5F, 1.0F);

                        clientisPrimaryEmpty = true;
                        playerItem.stackTagCompound.setBoolean("isPrimaryEmpty", clientisPrimaryEmpty);

                        clientFireTime = 0;
                        clientpreviousFireTime = 0;
                        playerItem.stackTagCompound.setInteger("playerFireTime", clientFireTime);
                        playerItem.stackTagCompound.setInteger("previousFireTime", clientpreviousFireTime);
                    }
                }
            }
            else
            {
                // --------------
                // SECONDARY FIRE
                // --------------

                // Increment the fire time on the weapon so we know how long it has been shooting.
                if (secondaryAmmoCount > 0)
                {
                    clientpreviousFireTime = clientFireTime;
                    clientFireTime += 1;
                }

                // Check if we have been holding down left click long enough to fire a grenade.
                if (secondaryAmmoCount > 0 && clientFireTime == 1 && clientpreviousFireTime <= 0 && !hasFiredGrenade)
                {
                    // We have fired a grenade!
                    hasFiredGrenade = true;

                    // Remove a grenade.
                    secondaryAmmoCount--;

                    // Play the grenade launcher sound as a single burst.
                    player.worldObj.playSoundAtEntity(player, PulseRifleSounds.PULSERIFLE_FIREGRENADE, 1.0F, 1.0F);

                    // Spawn grenade in the game world.
                    player.worldObj.spawnEntityInWorld(new PulseRifleGrenadeEntity(player.worldObj, player, 1.0F));

                    // MadScience.logger.info("FIRING GRENADE");
                }
                else if (secondaryAmmoCount <= 0)
                {
                    // Out of grenades.
                    if (!clientisSecondaryEmpty)
                    {
                        // MadScience.logger.info("OUT OF GRENADES");
                        player.worldObj.playSoundAtEntity(player, PulseRifleSounds.PULSERIFLE_EMPTY, 0.5F, 1.0F);
                        clientisSecondaryEmpty = true;
                        playerItem.stackTagCompound.setBoolean("isSecondaryEmpty", clientisSecondaryEmpty);
                        clientFireTime = 0;
                        clientpreviousFireTime = 0;
                        playerItem.stackTagCompound.setInteger("playerFireTime", clientFireTime);
                        playerItem.stackTagCompound.setInteger("previousFireTime", clientpreviousFireTime);
                    }
                }
            }
        }
        else if (clientButtonPressed == 2 && clientFireTime <= 0 && isRightPressed)
        {
            // -----------
            // RIGHT CLICK
            // -----------
            
            if (clientrightClickTime == 1)
            {
                if (clientshouldUnloadWeapon)
                {
                    // Reload the weapon, or unload depending on status.
                    if (primaryAmmoCount > 0 && primaryFireModeEnabled)
                    {
                        primaryAmmoCount = unloadMagazine(player, primaryAmmoCount);
                    }
                    else if (primaryAmmoCount <= 0 && primaryFireModeEnabled)
                    {
                        primaryAmmoCount = reloadMagazine(player, playerItem, primaryAmmoCount);
                    }
                    else if (secondaryAmmoCount > 0 && !primaryFireModeEnabled)
                    {
                        secondaryAmmoCount = unloadGrenades(player, secondaryAmmoCount);
                    }
                    else if (secondaryAmmoCount <= 0 && !primaryFireModeEnabled)
                    {
                        secondaryAmmoCount = reloadGrenades(player, playerItem, secondaryAmmoCount);
                    }
                }
                else
                {
                    // Change firing mode from bullets and grenades.
                    primaryFireModeEnabled = !primaryFireModeEnabled;
                    // player.addChatMessage("Primary Fire Mode: " + String.valueOf(primaryFireModeEnabled).toUpperCase());
                    player.worldObj.playSoundAtEntity(player, PulseRifleSounds.PULSERIFLE_EMPTY, 1.0F, 0.42F);
                }
            }

            // Increase the right-click time to prevent it from slipping through multiple times.
            clientrightClickTime++;
        }
        else if (clientButtonPressed == 1)
        {
            // -------------------
            // INTERMEDIATE PACKET
            // NO LEFT - NO RIGHT
            // -------------------
            
            isLeftPressed = false;
            isRightPressed = false;
            clientFireTime = 0;
            clientpreviousFireTime = 0;
            clientrightClickTime = 0;
            //MadScience.logger.info("INTERMEDIATE PACKET");
        }

        // Save the data we just changed onto the item that will be synced with the client.
        playerItem.stackTagCompound.setInteger("playerFireTime", clientFireTime);
        playerItem.stackTagCompound.setInteger("previousFireTime", clientpreviousFireTime);
        playerItem.stackTagCompound.setInteger("rightClickTime", clientrightClickTime);
        playerItem.stackTagCompound.setInteger("primaryAmmoCount", primaryAmmoCount);
        playerItem.stackTagCompound.setInteger("secondaryAmmoCount", secondaryAmmoCount);
        playerItem.stackTagCompound.setBoolean("primaryFireModeEnabled", primaryFireModeEnabled);
        playerItem.stackTagCompound.setBoolean("isPrimaryEmpty", clientisPrimaryEmpty);
        playerItem.stackTagCompound.setBoolean("isSecondaryEmpty", clientisSecondaryEmpty);
        playerItem.stackTagCompound.setBoolean("isLeftPressed", isLeftPressed);
        playerItem.stackTagCompound.setBoolean("isRightPressed", isRightPressed);

        // Special server-only variables we save to item.
        playerItem.stackTagCompound.setBoolean("hasFiredGrenade", hasFiredGrenade);

        // Debug Information For Server
        // MadScience.logger.info("Server: Ammo Count: " + primaryAmmoCount);
        // MadScience.logger.info("Server: Left Click Time: " + clientFireTime + "/" + clientpreviousFireTime);
        // MadScience.logger.info("Server: Right Click Time: " + clientrightClickTime);

        // Send the same packet back to the client with updated information from the server about their status.
        PacketDispatcher.sendPacketToPlayer(new PulseRiflePackets(clientFireTime, clientpreviousFireTime, clientrightClickTime, clientButtonPressed, primaryAmmoCount, secondaryAmmoCount, primaryFireModeEnabled, clientisPrimaryEmpty,
                clientisSecondaryEmpty, player.isSneaking(), isLeftPressed, isRightPressed).makePacket(), (Player) player);
    }

    @SideOnly(Side.CLIENT)
    public void onRecievePacketFromServer(int playerFireTime, int previousFireTime, int rightClickTime, int playerButtonPressed, int primaryAmmoCount, int secondaryAmmoCount, boolean primaryFireModeEnabled, boolean shouldUnloadWeapon,
            boolean isPrimaryEmpty, boolean isSecondaryEmpty, boolean leftPressed, boolean rightPressed, EntityPlayer player)
    {
        // Got a packet from the server that is telling a client to play a client
        if (player == null)
        {
            return;
        }

        // Ensure that we are on a client side only world.
        if (!player.worldObj.isRemote)
        {
            return;
        }

        // Hook the item the player is currently holding in his hand (if there is any).
        ItemStack playerItem = player.getHeldItem();
        if (playerItem == null)
        {
            return;
        }

        // Only do this for our pulse rifle.
        if (!playerItem.isItemEqual(new ItemStack(MadWeapons.WEAPONITEM_PULSERIFLE)))
        {
            return;
        }

        // Create NBT data if required.
        if (playerItem.stackTagCompound == null)
        {
            playerItem.stackTagCompound = new NBTTagCompound();
        }

        // Save the data that we received from the server and is known as 'correct'.
        playerItem.stackTagCompound.setInteger("playerFireTime", playerFireTime);
        playerItem.stackTagCompound.setInteger("previousFireTime", previousFireTime);
        playerItem.stackTagCompound.setInteger("rightClickTime", rightClickTime);
        playerItem.stackTagCompound.setInteger("primaryAmmoCount", primaryAmmoCount);
        playerItem.stackTagCompound.setInteger("secondaryAmmoCount", secondaryAmmoCount);
        playerItem.stackTagCompound.setBoolean("primaryFireModeEnabled", primaryFireModeEnabled);
        playerItem.stackTagCompound.setBoolean("isPrimaryEmpty", isPrimaryEmpty);
        playerItem.stackTagCompound.setBoolean("isSecondaryEmpty", isSecondaryEmpty);
        playerItem.stackTagCompound.setBoolean("isLeftPressed", leftPressed);
        playerItem.stackTagCompound.setBoolean("isRightPressed", rightPressed);

        // MadScience.logger.info("Client - Left Click Time: " + playerFireTime + "/" + previousFireTime);
        // MadScience.logger.info("Client - Right Click Time: " + rightClickTime);
    }

    @Override
    public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5)
    {
        super.onUpdate(par1ItemStack, par2World, par3Entity, par4, par5);

        // Only do this for our pulse rifle.
        if (!par1ItemStack.isItemEqual(new ItemStack(MadWeapons.WEAPONITEM_PULSERIFLE)))
        {
            return;
        }

        // Create NBT data if required.
        if (par1ItemStack.stackTagCompound == null)
        {
            par1ItemStack.stackTagCompound = new NBTTagCompound();
        }

        // Initialize variables we will use to communicate the status of the pulse rifle.
        int pulseRifleFireTime = 0;
        int previousFireTime = 0;
        int primaryAmmoCount = 0;
        int secondaryAmmoCount = 0;
        boolean primaryFireModeEnabled = true;
        boolean isPrimaryEmpty = true;
        boolean isSecondaryEmpty = true;
        boolean isLeftPressed = false;
        boolean isRightPressed = false;

        // Special variable used only on the server to enable semi-automatic firing of grenades.
        boolean hasFiredGrenade = false;

        // Grab NBT data from the item the player is holding.
        if (!par1ItemStack.stackTagCompound.hasNoTags())
        {
            // Fire time.
            if (par1ItemStack.stackTagCompound.hasKey("playerFireTime"))
            {
                pulseRifleFireTime = par1ItemStack.stackTagCompound.getInteger("playerFireTime");
            }

            if (par1ItemStack.stackTagCompound.hasKey("previousFireTime"))
            {
                previousFireTime = par1ItemStack.stackTagCompound.getInteger("previousFireTime");
            }

            // Semi-automatic grenade check
            if (par1ItemStack.stackTagCompound.hasKey("hasFiredGrenade"))
            {
                hasFiredGrenade = par1ItemStack.stackTagCompound.getBoolean("hasFiredGrenade");
            }

            // Ammo
            if (par1ItemStack.stackTagCompound.hasKey("primaryAmmoCount"))
            {
                primaryAmmoCount = par1ItemStack.stackTagCompound.getInteger("primaryAmmoCount");
            }

            if (par1ItemStack.stackTagCompound.hasKey("secondaryAmmoCount"))
            {
                secondaryAmmoCount = par1ItemStack.stackTagCompound.getInteger("secondaryAmmoCount");
            }

            // Firing mode
            if (par1ItemStack.stackTagCompound.hasKey("primaryFireModeEnabled"))
            {
                primaryFireModeEnabled = par1ItemStack.stackTagCompound.getBoolean("primaryFireModeEnabled");
            }

            if (par1ItemStack.stackTagCompound.hasKey("isPrimaryEmpty"))
            {
                isPrimaryEmpty = par1ItemStack.stackTagCompound.getBoolean("isPrimaryEmpty");
            }

            if (par1ItemStack.stackTagCompound.hasKey("isSecondaryEmpty"))
            {
                isSecondaryEmpty = par1ItemStack.stackTagCompound.getBoolean("isSecondaryEmpty");
            }

            // Mouse buttons
            if (par1ItemStack.stackTagCompound.hasKey("isLeftPressed"))
            {
                isLeftPressed = par1ItemStack.stackTagCompound.getBoolean("isLeftPressed");
            }

            if (par1ItemStack.stackTagCompound.hasKey("isRightPressed"))
            {
                isRightPressed = par1ItemStack.stackTagCompound.getBoolean("isRightPressed");
            }
        }
        
        // Force the player to hold the weapons out infront of them like a bow.
        if (pulseRifleFireTime > 0 && isLeftPressed)
        {
            if (primaryFireModeEnabled)
            {                
                EntityPlayer thePlayer = (EntityPlayer) par3Entity;
                if (thePlayer == null)
                {
                    return;
                }
                
                ItemStack theWeapon = thePlayer.getHeldItem(); 
                if (theWeapon == null)
                {
                    return;
                }
                
                if (theWeapon.isItemEqual(par1ItemStack) && theWeapon.itemID == MadWeapons.WEAPONITEM_PULSERIFLE.itemID)
                {
                    thePlayer.setItemInUse(par1ItemStack, theWeapon.getMaxItemUseDuration());
                    //MadScience.logger.info("ITEM SET IN USE");
                    if (thePlayer.getItemInUse() != null)
                    {
                        thePlayer.getItemInUse().useItemRightClick(par2World, thePlayer);
                        //MadScience.logger.info("USING ITEM");
                    }
                }
            }
        }
        
        // ------
        // SERVER
        // ------
        if (par2World.isRemote)
        {
            return;
        }

        // Check if we need to disable the has fired grenade flag which is server-only.
        if (hasFiredGrenade && !isLeftPressed && !isRightPressed && !primaryFireModeEnabled && pulseRifleFireTime <= 0)
        {
            // MadScience.logger.info("Server: Reset Fired Grenade Status");
            // BUG: This will activate if a GUI or player inventory is accessed, no apparent workaround.
            hasFiredGrenade = false;
            par1ItemStack.stackTagCompound.setBoolean("hasFiredGrenade", false);
            par2World.playSoundAtEntity(par3Entity, PulseRifleSounds.PULSERIFLE_CHAMBERGRENADE, 1.0F, 1.0F);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        // Grabs the default icon for the gun which located in item folder.
        this.itemIcon = par1IconRegister.registerIcon(MadScience.ID + ":" + (this.getUnlocalizedName().substring(5)));
    }

    public int reloadGrenades(EntityPlayer player, ItemStack playerItem, int secondaryAmmoCount)
    {
        // ---------------
        // RELOAD GRENADES
        // ---------------

        boolean foundGrenades = false;
        for (int i = 0; i < player.inventory.getSizeInventory(); i++)
        {
            // Build up a list of all grenades the player has.
            ItemStack playerInventoryItem = player.inventory.getStackInSlot(i);
            if (playerInventoryItem != null)
            {
                // Check if the item is a grenade and that is is not damaged (since that is impossible for grenades).
                if (playerInventoryItem.isItemEqual(new ItemStack(MadWeapons.WEAPONITEM_GRENADEITEM)) && !playerInventoryItem.isItemDamaged())
                {
                    // If the stack is larger than one then loop through them and take our fill, or just add the one.
                    if (playerInventoryItem.stackSize >= PulseRifleItem.GRENADE_MAXIMUM)
                    {
                        for (int x = 0; x < playerInventoryItem.stackSize; x++)
                        {
                            // Ensure that we are below our maximum for grenades.
                            if (secondaryAmmoCount >= PulseRifleItem.GRENADE_MAXIMUM)
                            {
                                break;
                            }

                            // Consume items in the stack until we are full.
                            ItemStack gotGrenades = player.inventory.decrStackSize(i, 4);
                            if (gotGrenades != null)
                            {
                                secondaryAmmoCount += gotGrenades.stackSize;
                                foundGrenades = true;
                            }
                        }
                    }
                    else
                    {
                        // Ensure that we are below our maximum for grenades.
                        if (secondaryAmmoCount >= PulseRifleItem.GRENADE_MAXIMUM)
                        {
                            break;
                        }

                        // Adds a single grenade that was located in the players inventory and not stacked.
                        ItemStack gotGrenades = player.inventory.decrStackSize(i, playerInventoryItem.stackSize);
                        if (gotGrenades != null)
                        {
                            secondaryAmmoCount += gotGrenades.stackSize;
                            foundGrenades = true;
                        }
                    }
                }
            }
        }

        // Only play the sound and set the data field once if we found some grenades to load into the gun.
        if (foundGrenades)
        {
            player.worldObj.playSoundAtEntity(player, PulseRifleSounds.PULSERIFLE_RELOADGRENADE, 1.0F, 1.0F);
            playerItem.stackTagCompound.setBoolean("isSecondaryEmpty", false);
            playerItem.stackTagCompound.setBoolean("hasFiredGrenade", false);
        }
        return secondaryAmmoCount;
    }

    public int reloadMagazine(EntityPlayer player, ItemStack playerItem, int primaryAmmoCount)
    {
        // ---------------
        // RELOAD MAGAZINE
        // ---------------

        // Loop through the players inventory and look for highest magazine.
        if (player.inventory.hasItem(MadWeapons.WEAPONITEM_MAGAZINEITEM.itemID))
        {
            List<PulseRifleMagazineComparatorItem> list = new ArrayList<PulseRifleMagazineComparatorItem>();
            for (int i = 0; i < player.inventory.getSizeInventory(); i++)
            {
                // Build up a list of all magazines that have rounds.
                ItemStack playerInventoryItem = player.inventory.getStackInSlot(i);
                if (playerInventoryItem != null)
                {
                    // Check if the item is a magazine and has rounds in it (is damaged).
                    if (playerInventoryItem.getItem() == MadWeapons.WEAPONITEM_MAGAZINEITEM && playerInventoryItem.isItemDamaged())
                    {
                        list.add(new PulseRifleMagazineComparatorItem(i, playerInventoryItem.getItemDamage()));
                        // MadScience.logger.info("[" + i + "]" + playerInventoryItem.getDisplayName() + "@" + playerInventoryItem.getItemDamage());
                    }
                }
            }

            // Sort our list of magazines by bullet count.
            // Collections.sort(list, Collections.reverseOrder(new PulseRifleMagazineComparator()));
            Collections.sort(list, new PulseRifleMagazineComparator());

            // Now we can be assured that the highest magazine bullet count will be index zero in this list.
            if (list != null && list.size() >= 1)
            {
                PulseRifleMagazineComparatorItem bestMagazine = list.get(0);
                if (bestMagazine != null)
                {
                    // Remove the item from the players inventory we are loading it into the weapon now.
                    player.inventory.decrStackSize(bestMagazine.slotNumber, 1);

                    // Add the amount of bullets adjusted for weird MC item damage offset.
                    primaryAmmoCount += Math.abs(100 - bestMagazine.bulletCount);

                    // player.addChatMessage("Reloaded magazine with " + String.valueOf(primaryAmmoCount) + " round(s).");
                    player.worldObj.playSoundAtEntity(player, PulseRifleSounds.PULSERIFLE_RELOAD, 1.0F, 1.0F);
                    playerItem.stackTagCompound.setBoolean("isPrimaryEmpty", false);
                }
            }
        }
        return primaryAmmoCount;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses()
    {
        // Tells minecraft to make more than one pass in rendering this item.
        return true;
    }

    @Override
    public boolean shouldRotateAroundWhenRendering()
    {
        // Prevents us having to rotate the weapon 180 degrees in renderer.
        return true;
    }

    public int unloadGrenades(EntityPlayer player, int secondaryAmmoCount)
    {
        // ---------------
        // UNLOAD GRENADES
        // ---------------

        // Removes all the grenades from the rifle as an itemstack, if not then drop them on the ground.
        if (!player.inventory.addItemStackToInventory(new ItemStack(MadWeapons.WEAPONITEM_GRENADEITEM, secondaryAmmoCount, 0)))
        {
            player.dropPlayerItemWithRandomChoice(new ItemStack(MadWeapons.WEAPONITEM_GRENADEITEM, secondaryAmmoCount, 0), true);
        }

        // player.addChatMessage("Removed " + String.valueOf(secondaryAmmoCount) + " grenade(s) with 0 remaining.");
        player.worldObj.playSoundAtEntity(player, PulseRifleSounds.PULSERIFLE_UNLOAD, 1.0F, 1.0F);
        secondaryAmmoCount = 0;
        return secondaryAmmoCount;
    }

    public int unloadMagazine(EntityPlayer player, int primaryAmmoCount)
    {
        // ---------------
        // UNLOAD MAGAZINE
        // ---------------

        // Attempts to unload the current magazine from the weapon.
        primaryAmmoCount = Math.abs(100 - primaryAmmoCount);
        int ammodisplayAmmount = Math.abs(100 - primaryAmmoCount);
        if (!player.inventory.addItemStackToInventory(new ItemStack(MadWeapons.WEAPONITEM_MAGAZINEITEM, 1, primaryAmmoCount)))
        {
            player.dropPlayerItemWithRandomChoice(new ItemStack(MadWeapons.WEAPONITEM_MAGAZINEITEM, 1, primaryAmmoCount), true);
        }

        // player.addChatMessage("Unloaded magazine with " + String.valueOf(ammodisplayAmmount) + " round(s).");
        player.worldObj.playSoundAtEntity(player, PulseRifleSounds.PULSERIFLE_UNLOAD, 1.0F, 1.0F);
        primaryAmmoCount = 0;
        return primaryAmmoCount;
    }
}

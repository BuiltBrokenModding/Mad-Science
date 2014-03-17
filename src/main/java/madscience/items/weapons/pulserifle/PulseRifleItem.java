package madscience.items.weapons.pulserifle;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

import madscience.MadEntities;
import madscience.MadScience;
import madscience.MadSounds;
import madscience.MadWeapons;
import madscience.items.weapons.KeyBindingInterceptor;
import madscience.util.MadTechneModel;
import madscience.util.MadUtils;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.oredict.OreDictionary;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PulseRifleItem extends ItemBow implements ITickHandler, IItemRenderer
{
    private enum TransformationTypes
    {
        DROPPED, INVENTORY, NONE, THIRDPERSONEQUIPPED
    }

    private static final int BULLET_MAXIMUM = 99;
    private static final int GRENADE_MAXIMUM = 4;
    GameSettings gs = null;

    KeyBindingInterceptor intLeft = null;
    KeyBindingInterceptor intRight = null;

    private MadTechneModel MODEL_COUNTER_LEFT = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "_counter0.mad");
    private MadTechneModel MODEL_COUNTER_RIGHT = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "_counter1.mad");
    private MadTechneModel MODEL_RIFLE = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + ".mad");

    private MadTechneModel MODEL_FLASH0 = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "_flash0.mad");
    private MadTechneModel MODEL_FLASH1 = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "_flash1.mad");
    private MadTechneModel MODEL_FLASH2 = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "_flash2.mad");
    private MadTechneModel MODEL_FLASH3 = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "_flash3.mad");
    private MadTechneModel MODEL_FLASH4 = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "_flash4.mad");

    private ResourceLocation TEXTURE_COUNTER_EIGHT = new ResourceLocation(MadScience.ID, "models/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "_counter8.png");
    private ResourceLocation TEXTURE_COUNTER_FIVE = new ResourceLocation(MadScience.ID, "models/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "_counter5.png");
    private ResourceLocation TEXTURE_COUNTER_FOUR = new ResourceLocation(MadScience.ID, "models/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "_counter4.png");
    private ResourceLocation TEXTURE_COUNTER_NINE = new ResourceLocation(MadScience.ID, "models/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "_counter9.png");
    private ResourceLocation TEXTURE_COUNTER_ONE = new ResourceLocation(MadScience.ID, "models/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "_counter1.png");
    private ResourceLocation TEXTURE_COUNTER_SEVEN = new ResourceLocation(MadScience.ID, "models/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "_counter7.png");
    private ResourceLocation TEXTURE_COUNTER_SIX = new ResourceLocation(MadScience.ID, "models/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "_counter6.png");
    private ResourceLocation TEXTURE_COUNTER_THREE = new ResourceLocation(MadScience.ID, "models/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "_counter3.png");
    private ResourceLocation TEXTURE_COUNTER_TWO = new ResourceLocation(MadScience.ID, "models/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "_counter2.png");
    private ResourceLocation TEXTURE_COUNTER_ZERO = new ResourceLocation(MadScience.ID, "models/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "_counter0.png");

    private ResourceLocation TEXTURE_RIFLE = new ResourceLocation(MadScience.ID, "models/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + ".png");
    private ResourceLocation TEXTURE_FLASH12 = new ResourceLocation(MadScience.ID, "models/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "_flash12.png");
    private ResourceLocation TEXTURE_FLASH34 = new ResourceLocation(MadScience.ID, "models/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "_flash34.png");

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

        // Get the current client game instance.
        try
        {
            gs = Minecraft.getMinecraft().gameSettings;
        }
        catch (Exception err)
        {
            MadScience.logger.info("Error while attempting to grab instance of Minecraft client.");
            return;
        }

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

    private ResourceLocation convertIntegerToCounterTexture(Integer ammoCount)
    {
        switch (ammoCount)
        {
        case 0:
            return TEXTURE_COUNTER_ZERO;
        case 1:
            return TEXTURE_COUNTER_ONE;
        case 2:
            return TEXTURE_COUNTER_TWO;
        case 3:
            return TEXTURE_COUNTER_THREE;
        case 4:
            return TEXTURE_COUNTER_FOUR;
        case 5:
            return TEXTURE_COUNTER_FIVE;
        case 6:
            return TEXTURE_COUNTER_SIX;
        case 7:
            return TEXTURE_COUNTER_SEVEN;
        case 8:
            return TEXTURE_COUNTER_EIGHT;
        case 9:
            return TEXTURE_COUNTER_NINE;
        default:
            return TEXTURE_COUNTER_ZERO;
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

    @SideOnly(Side.CLIENT)
    public boolean doClick(int button)
    {
        // Grab instance of client world and player that is controlling it.
        World world = Minecraft.getMinecraft().theWorld;
        EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;

        if (world == null)
        {
            return false;
        }

        if (player == null)
        {
            return false;
        }

        // Hook the item the player is currently holding in his hand (if there is any).
        ItemStack playerItem = player.getHeldItem();
        if (playerItem == null)
        {
            return false;
        }

        // Only do this for our pulse rifle.
        if (!playerItem.isItemEqual(new ItemStack(MadWeapons.WEAPONITEM_PULSERIFLE)))
        {
            return false;
        }

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
        }

        // Send a packet to the server since we are overriding minecraft's system.
        PacketDispatcher.sendPacketToServer(new PulseRiflePackets(pulseRifleFireTime, previousFireTime, rightClickTime, button, primaryAmmoCount, secondaryAmmoCount, primaryFireModeEnabled, isPrimaryEmpty, isSecondaryEmpty, player.isSneaking())
                .makePacket());
        return true;
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
        // MadScience.logger.info("DURATION: " + par1);
        return MadWeapons.WEAPONITEM_PULSERIFLE.itemIcon;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        // Tells animation system to play animation of bow with hands stuck out infront of player.
        return EnumAction.bow;
    }

    @Override
    public String getLabel()
    {
        // Name as known by Forge for debugging purposes.
        return MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + " TickHandler";
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
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        switch (type)
        {
        case ENTITY:
        case EQUIPPED:
        case EQUIPPED_FIRST_PERSON:
        case INVENTORY:
            return true;
        default:
            return false;
        }
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
            boolean clientisSecondaryEmpty, EntityPlayer player)
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
        int pulseRifleFireTime = clientFireTime;
        int previousFireTime = clientpreviousFireTime;
        int rightClickTime = clientrightClickTime;
        int primaryAmmoCount = 0;
        int secondaryAmmoCount = 0;
        boolean primaryFireModeEnabled = clientprimaryFireModeEnabled;
        boolean isPrimaryEmpty = clientisPrimaryEmpty;
        boolean isSecondaryEmpty = clientisSecondaryEmpty;

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
        }

        // Called when we receive a packet from a client that is telling us to fire the weapon!
        if (clientButtonPressed == 0)
        {
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
                    if (player.worldObj.getWorldTime() % 12F == 0L && pulseRifleFireTime >= 1)
                    {
                        player.worldObj.playSoundAtEntity(player, MadSounds.PULSERIFLE_FIRE, 0.5F, 1.0F);
                        shouldFire = true;
                    }
                    else if (pulseRifleFireTime <= 0)
                    {
                        player.worldObj.playSoundAtEntity(player, MadSounds.PULSERIFLE_FIRE, 0.5F, 1.0F);
                        shouldFire = true;
                    }

                    // Increment the fire time on the weapon so we know how long it has been shooting.
                    previousFireTime = pulseRifleFireTime;
                    pulseRifleFireTime += 1;

                    // Prevents bullet-spam and the gun firing a bullet every single tick, a little slower...
                    if (shouldFire)
                    {
                        if (pulseRifleFireTime <= previousFireTime)
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
                    if (!isPrimaryEmpty)
                    {
                        // Out of bullets.
                        player.worldObj.playSoundAtEntity(player, MadSounds.PULSERIFLE_EMPTY, 0.5F, 1.0F);

                        isPrimaryEmpty = true;
                        playerItem.stackTagCompound.setBoolean("isPrimaryEmpty", isPrimaryEmpty);

                        pulseRifleFireTime = 0;
                        previousFireTime = 0;
                        playerItem.stackTagCompound.setInteger("playerFireTime", pulseRifleFireTime);
                        playerItem.stackTagCompound.setInteger("previousFireTime", previousFireTime);
                    }
                }
            }
            else
            {
                // Only allow grenades to fire during first tick.
                if (pulseRifleFireTime <= 0)
                {
                    // --------------
                    // SECONDARY FIRE
                    // --------------
                    if (secondaryAmmoCount > 0)
                    {
                        // Remove a grenade.
                        secondaryAmmoCount--;

                        // Increment the fire time on the weapon so we know how long it has been shooting.
                        previousFireTime = pulseRifleFireTime;
                        pulseRifleFireTime += 1;

                        // Play the grenade launcher sound as a single burst.
                        player.worldObj.playSoundAtEntity(player, MadSounds.PULSERIFLE_FIREGRENADE, 1.0F, 1.0F);

                        // Spawn grenade in the game world.
                        player.worldObj.spawnEntityInWorld(new PulseRifleGrenadeEntity(player.worldObj, player, 1.0F));
                    }
                    else
                    {
                        // Out of grenades.
                        if (!isSecondaryEmpty)
                        {
                            player.worldObj.playSoundAtEntity(player, MadSounds.PULSERIFLE_EMPTY, 0.5F, 1.0F);
                            isSecondaryEmpty = true;
                            playerItem.stackTagCompound.setBoolean("isSecondaryEmpty", isSecondaryEmpty);
                            pulseRifleFireTime = 0;
                            previousFireTime = 0;
                            playerItem.stackTagCompound.setInteger("playerFireTime", pulseRifleFireTime);
                            playerItem.stackTagCompound.setInteger("previousFireTime", previousFireTime);
                        }
                    }
                }
            }
        }
        else if (clientButtonPressed == 2 && pulseRifleFireTime <= 0)
        {
            if (rightClickTime == 1)
            {
                if (clientshouldUnloadWeapon)
                {
                    // Reload the weapon, or unload depending on status.
                    if (primaryAmmoCount > 0 && primaryFireModeEnabled)
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

                        //player.addChatMessage("Unloaded magazine with " + String.valueOf(ammodisplayAmmount) + " round(s).");
                        player.worldObj.playSoundAtEntity(player, MadSounds.PULSERIFLE_UNLOAD, 1.0F, 1.0F);
                        primaryAmmoCount = 0;
                    }
                    else if (primaryAmmoCount <= 0 && primaryFireModeEnabled)
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
                            //Collections.sort(list, Collections.reverseOrder(new PulseRifleMagazineComparator()));
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
                                    
                                    //player.addChatMessage("Reloaded magazine with " + String.valueOf(primaryAmmoCount) + " round(s).");
                                    player.worldObj.playSoundAtEntity(player, MadSounds.PULSERIFLE_RELOAD, 1.0F, 1.0F);
                                    playerItem.stackTagCompound.setBoolean("isPrimaryEmpty", false);
                                }
                            }
                        }
                    }
                    else if (secondaryAmmoCount > 0 && !primaryFireModeEnabled)
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
                        player.worldObj.playSoundAtEntity(player, MadSounds.PULSERIFLE_UNLOAD, 1.0F, 1.0F);
                        secondaryAmmoCount = 0;
                    }
                    else if (secondaryAmmoCount <= 0 && !primaryFireModeEnabled)
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
                                    if (playerInventoryItem.stackSize >= this.GRENADE_MAXIMUM)
                                    {
                                        for (int x = 0; x < playerInventoryItem.stackSize; x++)
                                        {
                                            // Ensure that we are below our maximum for grenades.
                                            if (secondaryAmmoCount >= this.GRENADE_MAXIMUM)
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
                                        if (secondaryAmmoCount >= this.GRENADE_MAXIMUM)
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
                            player.worldObj.playSoundAtEntity(player, MadSounds.PULSERIFLE_RELOADGRENADE, 1.0F, 1.0F);
                            playerItem.stackTagCompound.setBoolean("isSecondaryEmpty", false);
                        }
                    }
                }
                else
                {
                    // Change firing mode from bullets and grenades.
                    primaryFireModeEnabled = !primaryFireModeEnabled;
                    // player.addChatMessage("Primary Fire Mode: " + String.valueOf(primaryFireModeEnabled).toUpperCase());
                    player.worldObj.playSoundAtEntity(player, MadSounds.PULSERIFLE_EMPTY, 1.0F, 0.42F);
                }
            }

            // Increase the right-click time to prevent it from slipping through multiple times.
            rightClickTime++;
        }

        // Save the data we just changed onto the item that will be synced with the client.
        playerItem.stackTagCompound.setInteger("playerFireTime", pulseRifleFireTime);
        playerItem.stackTagCompound.setInteger("previousFireTime", previousFireTime);
        playerItem.stackTagCompound.setInteger("rightClickTime", rightClickTime);
        playerItem.stackTagCompound.setInteger("primaryAmmoCount", primaryAmmoCount);
        playerItem.stackTagCompound.setInteger("secondaryAmmoCount", secondaryAmmoCount);
        playerItem.stackTagCompound.setBoolean("primaryFireModeEnabled", primaryFireModeEnabled);
        playerItem.stackTagCompound.setBoolean("isPrimaryEmpty", isPrimaryEmpty);
        playerItem.stackTagCompound.setBoolean("isSecondaryEmpty", isSecondaryEmpty);

        // Debug Information
        // MadScience.logger.info("Ammo Count: " + primaryAmmoCount);
        // MadScience.logger.info("Left Click Time: " + pulseRifleFireTime + "/" + previousFireTime);
        // MadScience.logger.info("Right Click Time: " + rightClickTime);

        // Send the same packet back to the client with updated information from the server about their status.
        PacketDispatcher.sendPacketToPlayer(
                new PulseRiflePackets(pulseRifleFireTime, previousFireTime, rightClickTime, clientButtonPressed, primaryAmmoCount, secondaryAmmoCount, primaryFireModeEnabled, isPrimaryEmpty, isSecondaryEmpty, player.isSneaking()).makePacket(),
                (Player) player);
    }

    public void onRecievePacketFromServer(int playerFireTime, int previousFireTime, int rightClickTime, int playerButtonPressed, int primaryAmmoCount, int secondaryAmmoCount, boolean primaryFireModeEnabled, boolean shouldUnloadWeapon,
            boolean isPrimaryEmpty, boolean isSecondaryEmpty, EntityPlayer player)
    {
        // Got a packet from the server that is telling a client to play a client
        EntityClientPlayerMP playerMP = Minecraft.getMinecraft().thePlayer;
        if (playerMP == null)
        {
            return;
        }

        // Ensure that we are on a client side only world.
        if (!playerMP.worldObj.isRemote)
        {
            return;
        }

        // Hook the item the player is currently holding in his hand (if there is any).
        ItemStack playerItem = playerMP.getHeldItem();
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
    }

    @Override
    public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5)
    {
        super.onUpdate(par1ItemStack, par2World, par3Entity, par4, par5);

        // Hook the item the player is currently holding in his hand (if there is any).
        ItemStack playerItem = ((EntityPlayer) par3Entity).getHeldItem();
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

        // Initialize variables we will use to communicate the status of the pulse rifle.
        int pulseRifleFireTime = 0;
        int previousFireTime = 0;
        int rightClickTime = 0;
        int primaryAmmoCount = 0;
        int secondaryAmmoCount = 0;
        boolean primaryFireModeEnabled = true;
        boolean isPrimaryEmpty = true;
        boolean isSecondaryEmpty = true;

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
        }

        // Always flatten right-click time when you let go of the key (for changing fire modes and reloading/unloading).
        if (intRight != null && !intRight.isKeyDown() && rightClickTime > 0)
        {
            rightClickTime = 0;
            playerItem.stackTagCompound.setInteger("rightClickTime", rightClickTime);
        }

        // Reset the primary empty prompts.
        if (pulseRifleFireTime <= 0 && primaryAmmoCount <= 0 && isPrimaryEmpty && intLeft != null && !intLeft.isKeyDown())
        {
            isPrimaryEmpty = false;
            playerItem.stackTagCompound.setBoolean("isPrimaryEmpty", isPrimaryEmpty);
        }

        // Reset the secondary empty prompts.
        if (pulseRifleFireTime <= 0 && secondaryAmmoCount <= 0 && isSecondaryEmpty && intLeft != null && !intLeft.isKeyDown())
        {
            isSecondaryEmpty = false;
            playerItem.stackTagCompound.setBoolean("isSecondaryEmpty", isSecondaryEmpty);
        }

        // Flatten left-click time when not holding the button down and there is something to decrease.
        if (intLeft != null && !intLeft.isKeyDown() && pulseRifleFireTime > 0 && previousFireTime >= 0)
        {
            pulseRifleFireTime = 0;
            previousFireTime = 0;
            playerItem.stackTagCompound.setInteger("playerFireTime", pulseRifleFireTime);
            playerItem.stackTagCompound.setInteger("previousFireTime", previousFireTime);

            isPrimaryEmpty = false;
            playerItem.stackTagCompound.setBoolean("isPrimaryEmpty", isPrimaryEmpty);

            isSecondaryEmpty = false;
            playerItem.stackTagCompound.setBoolean("isSecondaryEmpty", isSecondaryEmpty);
        }

        // Flatten out the ammo if we are primary.
        if (intLeft != null && intLeft.isKeyDown() && pulseRifleFireTime > 0 && previousFireTime >= 0 && primaryAmmoCount <= 0 && primaryFireModeEnabled)
        {
            // MadScience.logger.info("PRIMARY CLEAR");
            isPrimaryEmpty = false;
            playerItem.stackTagCompound.setBoolean("isPrimaryEmpty", isPrimaryEmpty);
            pulseRifleFireTime = 0;
            previousFireTime = 0;
            playerItem.stackTagCompound.setInteger("playerFireTime", pulseRifleFireTime);
            playerItem.stackTagCompound.setInteger("previousFireTime", previousFireTime);
        }

        // Flatten out the ammo if we are secondary.
        if (intLeft != null && intLeft.isKeyDown() && pulseRifleFireTime > 0 && previousFireTime >= 0 && secondaryAmmoCount <= 0 && !primaryFireModeEnabled)
        {
            // MadScience.logger.info("SECONDARY CLEAR");
            isSecondaryEmpty = false;
            playerItem.stackTagCompound.setBoolean("isSecondaryEmpty", isSecondaryEmpty);
            pulseRifleFireTime = 0;
            previousFireTime = 0;
            playerItem.stackTagCompound.setInteger("playerFireTime", pulseRifleFireTime);
            playerItem.stackTagCompound.setInteger("previousFireTime", previousFireTime);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        // Grabs the default icon for the gun which located in item folder.
        this.itemIcon = par1IconRegister.registerIcon(MadScience.ID + ":" + (this.getUnlocalizedName().substring(5)));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {
        // Grab client side instance of the player.
        EntityClientPlayerMP playerMP = Minecraft.getMinecraft().thePlayer;
        if (playerMP == null)
        {
            return;
        }

        // Ensure that we are on a client side only world.
        if (!playerMP.worldObj.isRemote)
        {
            return;
        }

        // Initialize variables we will use to communicate the status of the pulse rifle.
        int renderPass = 0;
        int pulseRifleFireTime = 0;
        int primaryAmmoCount = 0;
        int secondaryAmmoCount = 0;
        boolean primaryFireModeEnabled = true;

        // Create NBT data if required.
        if (item.stackTagCompound == null)
        {
            item.stackTagCompound = new NBTTagCompound();
        }

        // Grab NBT data from the item the player is holding.
        if (!item.stackTagCompound.hasNoTags())
        {
            if (item.stackTagCompound.hasKey("renderPass"))
            {
                renderPass = item.stackTagCompound.getInteger("renderPass");
            }

            if (item.stackTagCompound.hasKey("playerFireTime"))
            {
                pulseRifleFireTime = item.stackTagCompound.getInteger("playerFireTime");
            }

            if (item.stackTagCompound.hasKey("primaryAmmoCount"))
            {
                primaryAmmoCount = item.stackTagCompound.getInteger("primaryAmmoCount");
            }

            if (item.stackTagCompound.hasKey("secondaryAmmoCount"))
            {
                secondaryAmmoCount = item.stackTagCompound.getInteger("secondaryAmmoCount");
            }

            if (item.stackTagCompound.hasKey("primaryFireModeEnabled"))
            {
                primaryFireModeEnabled = item.stackTagCompound.getBoolean("primaryFireModeEnabled");
            }
        }

        // Prepare OpenGL for a transformation.
        GL11.glPushMatrix();

        // Split the ammo count up based on firing mode and by digits.
        List<Integer> splitAmmoCount = null;
        if (primaryFireModeEnabled)
        {
            // Bullets.
            splitAmmoCount = MadUtils.splitIntegerPerDigit(primaryAmmoCount);
        }
        else
        {
            // Grenades.
            splitAmmoCount = MadUtils.splitIntegerPerDigit(secondaryAmmoCount);
        }

        // If the ammo count is still somehow null even after all this we will make it zero.
        if (splitAmmoCount == null)
        {
            splitAmmoCount = new ArrayList<Integer>();
            splitAmmoCount.add(0);
            splitAmmoCount.add(0);
        }

        // If the ammo count is zero then add two of them!
        if (splitAmmoCount.size() == 0)
        {
            splitAmmoCount.add(0);
            splitAmmoCount.add(0);
        }

        // Decide what muzzle flash we are going to use this tick.
        int muzzleFlashRandomizer = playerMP.worldObj.rand.nextInt(5);

        // Change numbers on pulse rifle to match current firing mode ammo count.
        switch (renderPass)
        {
        case 0:
        {
            // Base Weapon Texture.
            Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE_RIFLE);
        }
            break;
        case 1:
        {
            // Weapon Counter Digit 1.
            if (splitAmmoCount.size() == 1)
            {
                // If ammo count is a single digit then add a leading zero.
                Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE_COUNTER_ZERO);
            }
            else
            {
                // Otherwise, show the other digit.
                Minecraft.getMinecraft().renderEngine.bindTexture(this.convertIntegerToCounterTexture(splitAmmoCount.get(1)));
            }

        }
            break;
        case 2:
        {
            // Weapon Counter Digit 2.
            Minecraft.getMinecraft().renderEngine.bindTexture(this.convertIntegerToCounterTexture(splitAmmoCount.get(0)));
        }
            break;
        case 3:
        {
            // Muzzle flash texture.
            switch (muzzleFlashRandomizer)
            {
            case 0:
            {
                // First muzzle flash is default one which is apart of weapon texture map.
                Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE_RIFLE);
            }
                break;
            case 1:
            {
                // Muzzle flash one and two share the same texture map.
                Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE_FLASH12);
            }
                break;
            case 2:
            {
                Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE_FLASH12);
            }
                break;
            case 3:
            {
                // Muzzle flash three and four share the same texture map.
                Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE_FLASH34);
            }
                break;
            case 4:
            {
                Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE_FLASH34);
            }
                break;
            }
        }
            break;
        }

        // Adjust rendering space to match what caller expects.
        TransformationTypes transformationToBeUndone = TransformationTypes.NONE;
        switch (type)
        {
        case EQUIPPED:
        {
            float scale = 0.20F;
            GL11.glScalef(scale, scale, scale);
            GL11.glTranslatef(3.0F, -1.0F, 2.0F);
            GL11.glRotatef(-60.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(42.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(22.0F, 0.0F, 1.0F, 0.0F);
            GL11.glEnable(GL11.GL_CULL_FACE);
            transformationToBeUndone = TransformationTypes.THIRDPERSONEQUIPPED;
            break;
        }
        case EQUIPPED_FIRST_PERSON:
        {
            float scale = 0.15F;

            // Change position and rotation based on firing status.
            GL11.glScalef(scale, scale, scale);
            if (playerMP.isUsingItem() && playerMP.getItemInUse().itemID == MadWeapons.WEAPONITEM_PULSERIFLE.itemID)
            {
                GL11.glTranslatef(5.5F, 5.0F, 4.5F);
                GL11.glRotatef(155.0F, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(25.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(13.0F, 0.0F, 0.0F, 1.0F);
            }
            else
            {
                GL11.glTranslatef(8.5F, 8.0F, 5.5F);
                GL11.glRotatef(142.0F, 0.0F, 1.0F, 0.0F);
            }
            break;
        }
        case INVENTORY:
        {
            float scale = 0.15F;
            GL11.glScalef(scale, scale, scale);
            GL11.glTranslatef(1.5F, 1.0F, 0.0F);
            GL11.glRotatef(270.0F, 0.0F, 0.5F, 0.0F);
            GL11.glRotatef(45.0F, 0.5F, 0.0F, 0.0F);
            transformationToBeUndone = TransformationTypes.INVENTORY;
            break;
        }
        case ENTITY:
        {
            float scale = 0.15F;
            GL11.glScalef(scale, scale, scale);
            GL11.glTranslatef(0.0F, 1.0F, 0.0F);
            GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
            transformationToBeUndone = TransformationTypes.DROPPED;
            break;
        }
        default:
            break; // never here
        }

        switch (renderPass)
        {
        case 0:
        {
            // Move the bolt and show muzzle flash on the rifle when firing.
            if (pulseRifleFireTime > 1 && primaryFireModeEnabled)
            {
                // BULLETS
                if (playerMP.worldObj.getWorldTime() % 2F == 0L)
                {
                    this.showMovingParts(muzzleFlashRandomizer);
                }
                else
                {
                    this.hideMovingParts(muzzleFlashRandomizer);
                }
            }
            else if (pulseRifleFireTime > 0 && !primaryFireModeEnabled)
            {
                // GRENADES
                MODEL_RIFLE.parts.get("PumpFront").showModel = false;
                MODEL_RIFLE.parts.get("PumpBack").showModel = false;
                MODEL_RIFLE.parts.get("PumpFrontBack").showModel = true;
                MODEL_RIFLE.parts.get("PumpBackBack").showModel = true;
            }
            else
            {
                // Weapon in normal firing position.
                this.hideMovingParts(muzzleFlashRandomizer);
            }

            // Renders the base pulse rifle in the gameworld at the correct scale.
            MODEL_RIFLE.renderAll();
        }
            break;
        case 1:
        {
            // Weapon Counter Digit 1.
            MODEL_COUNTER_LEFT.renderAll();
        }
            break;
        case 2:
        {
            // Weapon Counter Digit 2.
            MODEL_COUNTER_RIGHT.renderAll();
        }
            break;
        case 3:
        {
            // Muzzle Flash
            switch (muzzleFlashRandomizer)
            {
            case 0:
            {
                MODEL_FLASH0.renderAll();
            }
                break;
            case 1:
            {
                MODEL_FLASH1.renderAll();
            }
                break;
            case 2:
            {
                MODEL_FLASH2.renderAll();
            }
                break;
            case 3:
            {
                MODEL_FLASH3.renderAll();
            }
                break;
            case 4:
            {
                MODEL_FLASH4.renderAll();
            }
                break;
            }
        }
            break;
        }

        // Pop OpenGL matrix with our latest transformation (bring on the final form jokes!).
        GL11.glPopMatrix();

        switch (transformationToBeUndone)
        {
        case NONE:
        {
            break;
        }
        case DROPPED:
        {
            GL11.glTranslatef(0.0F, -0.5F, 0.0F);
            float scale = 1.0F;
            GL11.glScalef(scale, scale, scale);
            break;
        }
        case INVENTORY:
        {
            GL11.glTranslatef(0.5F, 0.5F, 0.5F);
            break;
        }
        case THIRDPERSONEQUIPPED:
        {
            GL11.glDisable(GL11.GL_CULL_FACE);
        }
        default:
            break;
        }
    }

    public void showMovingParts(int randomizer)
    {
        // Show 'moving' parts of the rifle during firing.
        MODEL_RIFLE.parts.get("Bolt").showModel = false;
        MODEL_RIFLE.parts.get("BoltBack").showModel = true;

        // Show the muzzle flash.
        switch (randomizer)
        {
        case 0:
        {
            MODEL_FLASH0.parts.get("Flash0").showModel = true;
            MODEL_FLASH0.parts.get("Flash1").showModel = true;
            MODEL_FLASH0.parts.get("Flash2").showModel = true;
            MODEL_FLASH0.parts.get("Flash3").showModel = true;
            MODEL_FLASH0.parts.get("Flash4").showModel = true;
        }
            break;
        case 1:
        {
            MODEL_FLASH1.parts.get("Flash0").showModel = true;
            MODEL_FLASH1.parts.get("Flash1").showModel = true;
            MODEL_FLASH1.parts.get("Flash2").showModel = true;
        }
            break;
        case 2:
        {
            MODEL_FLASH2.parts.get("Flash0").showModel = true;
            MODEL_FLASH2.parts.get("Flash1").showModel = true;
            MODEL_FLASH2.parts.get("Flash2").showModel = true;
        }
            break;
        case 3:
        {
            MODEL_FLASH3.parts.get("Flash0").showModel = true;
            MODEL_FLASH3.parts.get("Flash1").showModel = true;
            MODEL_FLASH3.parts.get("Flash2").showModel = true;
        }
            break;
        case 4:
        {
            MODEL_FLASH4.parts.get("Flash0").showModel = true;
            MODEL_FLASH4.parts.get("Flash1").showModel = true;
            MODEL_FLASH4.parts.get("Flash2").showModel = true;
        }
            break;
        }
    }

    public void hideMovingParts(int randomizer)
    {
        // Hide 'moving' parts of the pulse rifle by default.
        MODEL_RIFLE.parts.get("Bolt").showModel = true;
        MODEL_RIFLE.parts.get("BoltBack").showModel = false;

        switch (randomizer)
        {
        case 0:
        {
            // Hide the muzzle flash.
            MODEL_FLASH0.parts.get("Flash0").showModel = false;
            MODEL_FLASH0.parts.get("Flash1").showModel = false;
            MODEL_FLASH0.parts.get("Flash2").showModel = false;
            MODEL_FLASH0.parts.get("Flash3").showModel = false;
            MODEL_FLASH0.parts.get("Flash4").showModel = false;
        }
            break;
        case 1:
        {
            MODEL_FLASH1.parts.get("Flash0").showModel = false;
            MODEL_FLASH1.parts.get("Flash1").showModel = false;
            MODEL_FLASH1.parts.get("Flash2").showModel = false;
        }
            break;
        case 2:
        {
            MODEL_FLASH2.parts.get("Flash0").showModel = false;
            MODEL_FLASH2.parts.get("Flash1").showModel = false;
            MODEL_FLASH2.parts.get("Flash2").showModel = false;
        }
            break;
        case 3:
        {
            MODEL_FLASH3.parts.get("Flash0").showModel = false;
            MODEL_FLASH3.parts.get("Flash1").showModel = false;
            MODEL_FLASH3.parts.get("Flash2").showModel = false;
        }
            break;
        case 4:
        {
            MODEL_FLASH4.parts.get("Flash0").showModel = false;
            MODEL_FLASH4.parts.get("Flash1").showModel = false;
            MODEL_FLASH4.parts.get("Flash2").showModel = false;
        }
            break;
        }

        // Hide the pump.
        MODEL_RIFLE.parts.get("PumpFrontBack").showModel = false;
        MODEL_RIFLE.parts.get("PumpBackBack").showModel = false;
        MODEL_RIFLE.parts.get("PumpFront").showModel = true;
        MODEL_RIFLE.parts.get("PumpBack").showModel = true;
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

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
    {
        switch (type)
        {
        case ENTITY:
        {
            return (helper == ItemRendererHelper.ENTITY_BOBBING || helper == ItemRendererHelper.ENTITY_ROTATION || helper == ItemRendererHelper.BLOCK_3D);
        }
        case EQUIPPED:
        {
            return (helper == ItemRendererHelper.BLOCK_3D || helper == ItemRendererHelper.EQUIPPED_BLOCK);
        }
        case EQUIPPED_FIRST_PERSON:
        {
            return (helper == ItemRendererHelper.EQUIPPED_BLOCK);
        }
        case INVENTORY:
        {
            return (helper == ItemRendererHelper.INVENTORY_BLOCK);
        }
        default:
        {
            return false;
        }
        }
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
        World world = Minecraft.getMinecraft().theWorld;
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

            int pulseRifleFireTime = 0;
            int previousFireTime = 0;
            int primaryAmmoCount = 0;
            int secondaryAmmoCount = 0;
            boolean primaryFireModeEnabled = true;
            boolean isPrimaryEmpty = true;

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
            }

            // Change the FOV of our screen when shooting the pulse rifle.
            if (type.equals(EnumSet.of(TickType.RENDER)))
            {
                // Only run the right-click functionality if internal NBT firetime is greater than zero.
                if (pulseRifleFireTime > 1 && previousFireTime > 0 && primaryFireModeEnabled && primaryAmmoCount > 0)
                {
                    MadScience.proxy.onBowUse(playerHeldItem, player, pulseRifleFireTime);
                    playerHeldItem.useItemRightClick(player.worldObj, player);
                    player.setItemInUse(playerHeldItem, pulseRifleFireTime);
                }
                else
                {
                    MadScience.proxy.resetSavedFOV();
                }
            }

            // LEFT CLICK
            if (intLeft.isKeyDown())
            {
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
                pright = false;
                playerHeldItem.stackTagCompound.setInteger("rightClickTime", 0);
            }

            // Check if the weapon should still be firing at the end of this tick before next one.
            if (intLeft.isKeyDown() && pleft && intLeft.pressed)
            {
                if (doClick(0))
                {
                    // MadScience.logger.info("LEFT");

                    intLeft.pressed = true;
                    intLeft.pressTime++;

                    if (pulseRifleFireTime > 0)
                    {
                        intRight.pressed = true;
                        intRight.pressTime++;
                    }
                    return;
                }
            }

            // Ensure that right-click will always fire one time by increasing it's firetime manually.
            if (intRight.isKeyDown() && pright && intRight.pressed && intRight.pressTime == 0)
            {
                if (doClick(2))
                {
                    // MadScience.logger.info("RIGHT");
                    intRight.pressed = true;
                    intRight.pressTime++;
                    return;
                }
            }
        }
        else
        {
            this.disableKeyIntercepter();
        }
    }
}

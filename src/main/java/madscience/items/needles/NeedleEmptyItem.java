package madscience.items.needles;

import java.util.List;

import madscience.MadNeedles;
import madscience.factory.mod.MadMod;
import madscience.util.MadUtils;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NeedleEmptyItem extends Item
{
    public NeedleEmptyItem(int itemID)
    {
        super(itemID);

        // Set proper tab for the base (empty) needle.
        this.setCreativeTab(MadMod.getCreativeTab());

        // Needles won't repair other needles.
        this.setNoRepair();

        // Maximum amount of damage this item can receive before breaking.
        this.setMaxDamage(10);
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List info, boolean par4)
    {
        String tooltip = StatCollector.translateToLocal(getUnlocalizedName() + ".tooltip");

        if (tooltip != null && tooltip.length() > 0)
        {
            info.addAll(MadUtils.splitStringPerWord(tooltip, 5));
        }
    }

    @Override
    public boolean canHarvestBlock(Block par1Block)
    {
        // You cannot harvest blocks with a needle.
        return false;
    }

    public int getDamageVsEntity(Entity par1Entity) // NO_UCD (unused code)
    {
        // Stabbing another entity other than yourself takes 1 heart.
        return 2;
    }

    @Override
    public int getItemEnchantability()
    {
        // This item is not enchantable.
        return 0;
    }

    @Override
    public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block)
    {
        // Trying to break a block with a needle won't work very well.
        return 0.0F;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack items, World world, EntityPlayer player)
    {
        // Note: Item usage methods are called both on client and server!

        // You cannot cheat and take from yourself in creative!
        if (player.capabilities.isCreativeMode)
        {
            return items;
        }

        // Check if we are allowed to right-click with needle right now.
        if (items.stackSize <= 0)
        {
            return items;
        }

        // Play sound of using the needle on yourself.
        player.playSound(NeedleSounds.NEEDLEITEM_STABPLAYER, 1.0F, 1.0F);

        // If needle is empty we will take blood from the player!
        if (player instanceof EntityPlayer && player.inventory.consumeInventoryItem(MadNeedles.NEEDLE_ITEM.itemID))
        {
            // If needle is empty we will take blood from the player!
            player.attackEntityFrom(DamageSource.generic, 2);
            player.addExhaustion(5.0F);

            // Return blood of a villager (human) to the player for stabbing
            player.inventory.addItemStackToInventory(new ItemStack(MadNeedles.NEEDLE_VILLAGER));

            // Remove an item from our stack!
            return items;
        }

        return items;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack items, EntityPlayer player, Entity entity)
    {
        // Called when the player Left Clicks (attacks) an entity.
        // Processed before damage is done, if return value is true further
        // processing is canceled and the entity is not attacked.

        // Note: Item usage methods are called both on client and server!

        // Check if player has room to perform this action.
        if (items.stackSize <= 0)
        {
            return false;
        }

        // Remove an item from our stack!
        if (!player.inventory.consumeInventoryItem(MadNeedles.NEEDLE_ITEM.itemID))
        {
            return false;
        }

        // Play a sound of you using the needle on a mob!
        entity.playSound(NeedleSounds.NEEDLEITEM_STABMOB, 1.0F, 1.0F);

        // -------
        // MUTANTS
        // -------

        // Pig Zombie
        if (entity instanceof EntityPigZombie)
        {
            if (!player.inventory.addItemStackToInventory(new ItemStack(MadNeedles.NEEDLE_MUTANT)))
            {
                player.dropPlayerItem(new ItemStack(MadNeedles.NEEDLE_MUTANT));
            }
            return false;
        }
        
        // Mooshroom
        if (entity instanceof EntityMooshroom)
        {
            if (!player.inventory.addItemStackToInventory(new ItemStack(MadNeedles.NEEDLE_MUSHROOMCOW)))
            {
                player.dropPlayerItem(new ItemStack(MadNeedles.NEEDLE_MUSHROOMCOW));
            }
            return false;
        }

        // ---------
        // BASE MOBS
        // ---------

        // Cave Spider
        if (entity instanceof EntityCaveSpider)
        {
            if (!player.inventory.addItemStackToInventory(new ItemStack(MadNeedles.NEEDLE_CAVESPIDER)))
            {
                player.dropPlayerItem(new ItemStack(MadNeedles.NEEDLE_CAVESPIDER));
            }
            return false;
        }

        // Chicken
        if (entity instanceof EntityChicken)
        {
            if (!player.inventory.addItemStackToInventory(new ItemStack(MadNeedles.NEEDLE_CHICKEN)))
            {
                player.dropPlayerItem(new ItemStack(MadNeedles.NEEDLE_CHICKEN));
            }
            return false;
        }

        // Cow
        if (entity instanceof EntityCow)
        {
            if (!player.inventory.addItemStackToInventory(new ItemStack(MadNeedles.NEEDLE_COW)))
            {
                player.dropPlayerItem(new ItemStack(MadNeedles.NEEDLE_COW));
            }
            return false;
        }

        // Creeper
        if (entity instanceof EntityCreeper)
        {
            if (!player.inventory.addItemStackToInventory(new ItemStack(MadNeedles.NEEDLE_CREEPER)))
            {
                player.dropPlayerItem(new ItemStack(MadNeedles.NEEDLE_CREEPER));
            }
            return false;
        }

        // Bat
        if (entity instanceof EntityBat)
        {
            if (!player.inventory.addItemStackToInventory(new ItemStack(MadNeedles.NEEDLE_BAT)))
            {
                player.dropPlayerItem(new ItemStack(MadNeedles.NEEDLE_BAT));
            }
            return false;
        }

        // Enderman
        if (entity instanceof EntityEnderman)
        {
            if (!player.inventory.addItemStackToInventory(new ItemStack(MadNeedles.NEEDLE_ENDERMAN)))
            {
                player.dropPlayerItem(new ItemStack(MadNeedles.NEEDLE_ENDERMAN));
            }
            return false;
        }

        // Horse
        if (entity instanceof EntityHorse)
        {
            if (!player.inventory.addItemStackToInventory(new ItemStack(MadNeedles.NEEDLE_HORSE)))
            {
                player.dropPlayerItem(new ItemStack(MadNeedles.NEEDLE_HORSE));
            }
            return false;
        }

        // Ocelot
        if (entity instanceof EntityOcelot)
        {
            if (!player.inventory.addItemStackToInventory(new ItemStack(MadNeedles.NEEDLE_OCELOT)))
            {
                player.dropPlayerItem(new ItemStack(MadNeedles.NEEDLE_OCELOT));
            }
            return false;
        }

        // Pig
        if (entity instanceof EntityPig)
        {
            if (!player.inventory.addItemStackToInventory(new ItemStack(MadNeedles.NEEDLE_PIG)))
            {
                player.dropPlayerItem(new ItemStack(MadNeedles.NEEDLE_PIG));
            }
            return false;
        }

        // Sheep
        if (entity instanceof EntitySheep)
        {
            if (!player.inventory.addItemStackToInventory(new ItemStack(MadNeedles.NEEDLE_SHEEP)))
            {
                player.dropPlayerItem(new ItemStack(MadNeedles.NEEDLE_SHEEP));
            }
            return false;
        }

        // Spider
        if (entity instanceof EntitySpider)
        {
            if (!player.inventory.addItemStackToInventory(new ItemStack(MadNeedles.NEEDLE_SPIDER)))
            {
                player.dropPlayerItem(new ItemStack(MadNeedles.NEEDLE_SPIDER));
            }
            return false;
        }

        // Squid
        if (entity instanceof EntitySquid)
        {
            if (!player.inventory.addItemStackToInventory(new ItemStack(MadNeedles.NEEDLE_SQUID)))
            {
                player.dropPlayerItem(new ItemStack(MadNeedles.NEEDLE_SQUID));
            }
            return false;
        }

        // Villager
        if (entity instanceof EntityVillager)
        {
            if (!player.inventory.addItemStackToInventory(new ItemStack(MadNeedles.NEEDLE_VILLAGER)))
            {
                player.dropPlayerItem(new ItemStack(MadNeedles.NEEDLE_VILLAGER));
            }
            return false;
        }

        // Witch
        if (entity instanceof EntityWitch)
        {
            if (!player.inventory.addItemStackToInventory(new ItemStack(MadNeedles.NEEDLE_WITCH)))
            {
                player.dropPlayerItem(new ItemStack(MadNeedles.NEEDLE_WITCH));
            }
            return false;
        }

        // Wolf
        if (entity instanceof EntityWolf)
        {
            if (!player.inventory.addItemStackToInventory(new ItemStack(MadNeedles.NEEDLE_WOLF)))
            {
                player.dropPlayerItem(new ItemStack(MadNeedles.NEEDLE_WOLF));
            }
            return false;
        }

        // Zombie
        if (entity instanceof EntityZombie)
        {
            if (!player.inventory.addItemStackToInventory(new ItemStack(MadNeedles.NEEDLE_ZOMBIE)))
            {
                player.dropPlayerItem(new ItemStack(MadNeedles.NEEDLE_ZOMBIE));
            }
            return false;
        }

        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon(MadMod.ID + ":" + (this.getUnlocalizedName().substring(5)));
    }
}

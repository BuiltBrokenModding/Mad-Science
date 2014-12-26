package madscience;

import madscience.factory.ItemFactory;
import madscience.item.ItemPrefab;
import madscience.product.ItemFactoryProduct;
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
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class Emptyneedle extends ItemPrefab
{
    public Emptyneedle(int itemID)
    {
        super(itemID);
    }

    public Emptyneedle(ItemFactoryProduct itemData)
    {
        super(itemData);
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

        // If needle is empty we will take blood from the player!
        if (player instanceof EntityPlayer && player.inventory.consumeInventoryItem(this.itemID))
        {
            // If needle is empty we will take blood from the player!
            player.attackEntityFrom(DamageSource.generic, 2);
            player.addExhaustion(5.0F);

            // Return blood of a villager (human) to the player for stabbing.
            ItemStack villagerNeedle = ItemFactory.instance().getItemStackByFullyQualifiedName("needle", "villager", 1);
            player.inventory.addItemStackToInventory(villagerNeedle.copy());

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
        if (!player.inventory.consumeInventoryItem(this.itemID))
        {
            return false;
        }

        // Pig Zombie
        if (entity instanceof EntityPigZombie)
        {
            ItemStack muntantNeedle = ItemFactory.instance().getItemStackByFullyQualifiedName("needle", "mutant", 1);
            if (!player.inventory.addItemStackToInventory(muntantNeedle))
            {
                player.dropPlayerItem(muntantNeedle);
            }
            return false;
        }
        
        // Mooshroom
        if (entity instanceof EntityMooshroom)
        {
            ItemStack mushroomCowNeedle = ItemFactory.instance().getItemStackByFullyQualifiedName("needle", "mushroomcow", 1);
            if (!player.inventory.addItemStackToInventory(mushroomCowNeedle))
            {
                player.dropPlayerItem(mushroomCowNeedle);
            }
            return false;
        }

        // Cave Spider
        if (entity instanceof EntityCaveSpider)
        {
            ItemStack caveSpiderNeedle = ItemFactory.instance().getItemStackByFullyQualifiedName("needle", "cavespider", 1);
            if (!player.inventory.addItemStackToInventory(caveSpiderNeedle))
            {
                player.dropPlayerItem(caveSpiderNeedle);
            }
            return false;
        }

        // Chicken
        if (entity instanceof EntityChicken)
        {
            ItemStack chickenNeedle = ItemFactory.instance().getItemStackByFullyQualifiedName("needle", "chicken", 1);
            if (!player.inventory.addItemStackToInventory(chickenNeedle))
            {
                player.dropPlayerItem(chickenNeedle);
            }
            return false;
        }

        // Cow
        if (entity instanceof EntityCow)
        {
            ItemStack cowNeedle = ItemFactory.instance().getItemStackByFullyQualifiedName("needle", "cow", 1);
            if (!player.inventory.addItemStackToInventory(cowNeedle))
            {
                player.dropPlayerItem(cowNeedle);
            }
            return false;
        }

        // Creeper
        if (entity instanceof EntityCreeper)
        {
            ItemStack creeperNeedle = ItemFactory.instance().getItemStackByFullyQualifiedName("needle", "creeper", 1);
            if (!player.inventory.addItemStackToInventory(creeperNeedle))
            {
                player.dropPlayerItem(creeperNeedle);
            }
            return false;
        }

        // Bat
        if (entity instanceof EntityBat)
        {
            ItemStack batNeedle = ItemFactory.instance().getItemStackByFullyQualifiedName("needle", "bat", 1);
            if (!player.inventory.addItemStackToInventory(batNeedle))
            {
                player.dropPlayerItem(batNeedle);
            }
            return false;
        }

        // Enderman
        if (entity instanceof EntityEnderman)
        {
            ItemStack endermanNeedle = ItemFactory.instance().getItemStackByFullyQualifiedName("needle", "enderman", 1);
            if (!player.inventory.addItemStackToInventory(endermanNeedle))
            {
                player.dropPlayerItem(endermanNeedle);
            }
            return false;
        }

        // Horse
        if (entity instanceof EntityHorse)
        {
            ItemStack horseNeedle = ItemFactory.instance().getItemStackByFullyQualifiedName("needle", "horse", 1);
            if (!player.inventory.addItemStackToInventory(horseNeedle))
            {
                player.dropPlayerItem(horseNeedle);
            }
            return false;
        }

        // Ocelot
        if (entity instanceof EntityOcelot)
        {
            ItemStack ocelotNeedle = ItemFactory.instance().getItemStackByFullyQualifiedName("needle", "ocelot", 1);
            if (!player.inventory.addItemStackToInventory(ocelotNeedle))
            {
                player.dropPlayerItem(ocelotNeedle);
            }
            return false;
        }

        // Pig
        if (entity instanceof EntityPig)
        {
            ItemStack pigNeedle = ItemFactory.instance().getItemStackByFullyQualifiedName("needle", "pig", 1);
            if (!player.inventory.addItemStackToInventory(pigNeedle))
            {
                player.dropPlayerItem(pigNeedle);
            }
            return false;
        }

        // Sheep
        if (entity instanceof EntitySheep)
        {
            ItemStack sheepNeedle = ItemFactory.instance().getItemStackByFullyQualifiedName("needle", "sheep", 1);
            if (!player.inventory.addItemStackToInventory(sheepNeedle))
            {
                player.dropPlayerItem(sheepNeedle);
            }
            return false;
        }

        // Spider
        if (entity instanceof EntitySpider)
        {
            ItemStack spiderNeedle = ItemFactory.instance().getItemStackByFullyQualifiedName("needle", "spider", 1);
            if (!player.inventory.addItemStackToInventory(spiderNeedle))
            {
                player.dropPlayerItem(spiderNeedle);
            }
            return false;
        }

        // Squid
        if (entity instanceof EntitySquid)
        {
            ItemStack squidNeedle = ItemFactory.instance().getItemStackByFullyQualifiedName("needle", "squid", 1);
            if (!player.inventory.addItemStackToInventory(squidNeedle))
            {
                player.dropPlayerItem(squidNeedle);
            }
            return false;
        }

        // Villager
        if (entity instanceof EntityVillager)
        {
            ItemStack villagerNeedle = ItemFactory.instance().getItemStackByFullyQualifiedName("needle", "villager", 1);
            if (!player.inventory.addItemStackToInventory(villagerNeedle))
            {
                player.dropPlayerItem(villagerNeedle);
            }
            return false;
        }

        // Witch
        if (entity instanceof EntityWitch)
        {
            ItemStack witchNeedle = ItemFactory.instance().getItemStackByFullyQualifiedName("needle", "witch", 1);
            if (!player.inventory.addItemStackToInventory(witchNeedle))
            {
                player.dropPlayerItem(witchNeedle);
            }
            return false;
        }

        // Wolf
        if (entity instanceof EntityWolf)
        {
            ItemStack wolfNeedle = ItemFactory.instance().getItemStackByFullyQualifiedName("needle", "wolf", 1);
            if (!player.inventory.addItemStackToInventory(wolfNeedle))
            {
                player.dropPlayerItem(wolfNeedle);
            }
            return false;
        }

        // Zombie
        if (entity instanceof EntityZombie)
        {
            ItemStack zombieNeedle = ItemFactory.instance().getItemStackByFullyQualifiedName("needle", "zombie", 1);
            if (!player.inventory.addItemStackToInventory(zombieNeedle))
            {
                player.dropPlayerItem(zombieNeedle);
            }
            return false;
        }

        return false;
    }
}

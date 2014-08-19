package madscience.item;

import madscience.factory.MadItemFactory;
import madscience.factory.item.MadItemPrefab;
import madscience.factory.product.MadItemFactoryProduct;
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

public class ItemNeedleEmptyLogic extends MadItemPrefab
{
    public ItemNeedleEmptyLogic(MadItemFactoryProduct itemData)
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
            ItemStack villagerNeedle = MadItemFactory.instance().getItemStackByFullyQualifiedName("needle", "villager", 1);
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
            ItemStack muntantNeedle = MadItemFactory.instance().getItemStackByFullyQualifiedName("needle", "mutant", 1);
            if (!player.inventory.addItemStackToInventory(muntantNeedle))
            {
                player.dropPlayerItem(muntantNeedle);
            }
            return false;
        }
        
        // Mooshroom
        if (entity instanceof EntityMooshroom)
        {
            ItemStack mushroomCowNeedle = MadItemFactory.instance().getItemStackByFullyQualifiedName("needle", "mushroomcow", 1);
            if (!player.inventory.addItemStackToInventory(mushroomCowNeedle))
            {
                player.dropPlayerItem(mushroomCowNeedle);
            }
            return false;
        }

        // Cave Spider
        if (entity instanceof EntityCaveSpider)
        {
            ItemStack caveSpiderNeedle = MadItemFactory.instance().getItemStackByFullyQualifiedName("needle", "cavespider", 1);
            if (!player.inventory.addItemStackToInventory(caveSpiderNeedle))
            {
                player.dropPlayerItem(caveSpiderNeedle);
            }
            return false;
        }

        // Chicken
        if (entity instanceof EntityChicken)
        {
            ItemStack chickenNeedle = MadItemFactory.instance().getItemStackByFullyQualifiedName("needle", "chicken", 1);
            if (!player.inventory.addItemStackToInventory(chickenNeedle))
            {
                player.dropPlayerItem(chickenNeedle);
            }
            return false;
        }

        // Cow
        if (entity instanceof EntityCow)
        {
            ItemStack cowNeedle = MadItemFactory.instance().getItemStackByFullyQualifiedName("needle", "cow", 1);
            if (!player.inventory.addItemStackToInventory(cowNeedle))
            {
                player.dropPlayerItem(cowNeedle);
            }
            return false;
        }

        // Creeper
        if (entity instanceof EntityCreeper)
        {
            ItemStack creeperNeedle = MadItemFactory.instance().getItemStackByFullyQualifiedName("needle", "creeper", 1);
            if (!player.inventory.addItemStackToInventory(creeperNeedle))
            {
                player.dropPlayerItem(creeperNeedle);
            }
            return false;
        }

        // Bat
        if (entity instanceof EntityBat)
        {
            ItemStack batNeedle = MadItemFactory.instance().getItemStackByFullyQualifiedName("needle", "bat", 1);
            if (!player.inventory.addItemStackToInventory(batNeedle))
            {
                player.dropPlayerItem(batNeedle);
            }
            return false;
        }

        // Enderman
        if (entity instanceof EntityEnderman)
        {
            ItemStack endermanNeedle = MadItemFactory.instance().getItemStackByFullyQualifiedName("needle", "enderman", 1);
            if (!player.inventory.addItemStackToInventory(endermanNeedle))
            {
                player.dropPlayerItem(endermanNeedle);
            }
            return false;
        }

        // Horse
        if (entity instanceof EntityHorse)
        {
            ItemStack horseNeedle = MadItemFactory.instance().getItemStackByFullyQualifiedName("needle", "horse", 1);
            if (!player.inventory.addItemStackToInventory(horseNeedle))
            {
                player.dropPlayerItem(horseNeedle);
            }
            return false;
        }

        // Ocelot
        if (entity instanceof EntityOcelot)
        {
            ItemStack ocelotNeedle = MadItemFactory.instance().getItemStackByFullyQualifiedName("needle", "ocelot", 1);
            if (!player.inventory.addItemStackToInventory(ocelotNeedle))
            {
                player.dropPlayerItem(ocelotNeedle);
            }
            return false;
        }

        // Pig
        if (entity instanceof EntityPig)
        {
            ItemStack pigNeedle = MadItemFactory.instance().getItemStackByFullyQualifiedName("needle", "pig", 1);
            if (!player.inventory.addItemStackToInventory(pigNeedle))
            {
                player.dropPlayerItem(pigNeedle);
            }
            return false;
        }

        // Sheep
        if (entity instanceof EntitySheep)
        {
            ItemStack sheepNeedle = MadItemFactory.instance().getItemStackByFullyQualifiedName("needle", "sheep", 1);
            if (!player.inventory.addItemStackToInventory(sheepNeedle))
            {
                player.dropPlayerItem(sheepNeedle);
            }
            return false;
        }

        // Spider
        if (entity instanceof EntitySpider)
        {
            ItemStack spiderNeedle = MadItemFactory.instance().getItemStackByFullyQualifiedName("needle", "spider", 1);
            if (!player.inventory.addItemStackToInventory(spiderNeedle))
            {
                player.dropPlayerItem(spiderNeedle);
            }
            return false;
        }

        // Squid
        if (entity instanceof EntitySquid)
        {
            ItemStack squidNeedle = MadItemFactory.instance().getItemStackByFullyQualifiedName("needle", "squid", 1);
            if (!player.inventory.addItemStackToInventory(squidNeedle))
            {
                player.dropPlayerItem(squidNeedle);
            }
            return false;
        }

        // Villager
        if (entity instanceof EntityVillager)
        {
            ItemStack villagerNeedle = MadItemFactory.instance().getItemStackByFullyQualifiedName("needle", "villager", 1);
            if (!player.inventory.addItemStackToInventory(villagerNeedle))
            {
                player.dropPlayerItem(villagerNeedle);
            }
            return false;
        }

        // Witch
        if (entity instanceof EntityWitch)
        {
            ItemStack witchNeedle = MadItemFactory.instance().getItemStackByFullyQualifiedName("needle", "witch", 1);
            if (!player.inventory.addItemStackToInventory(witchNeedle))
            {
                player.dropPlayerItem(witchNeedle);
            }
            return false;
        }

        // Wolf
        if (entity instanceof EntityWolf)
        {
            ItemStack wolfNeedle = MadItemFactory.instance().getItemStackByFullyQualifiedName("needle", "wolf", 1);
            if (!player.inventory.addItemStackToInventory(wolfNeedle))
            {
                player.dropPlayerItem(wolfNeedle);
            }
            return false;
        }

        // Zombie
        if (entity instanceof EntityZombie)
        {
            ItemStack zombieNeedle = MadItemFactory.instance().getItemStackByFullyQualifiedName("needle", "zombie", 1);
            if (!player.inventory.addItemStackToInventory(zombieNeedle))
            {
                player.dropPlayerItem(zombieNeedle);
            }
            return false;
        }

        return false;
    }
}

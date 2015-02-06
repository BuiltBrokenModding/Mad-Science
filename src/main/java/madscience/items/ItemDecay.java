package madscience.items;

import java.util.List;

import madscience.MadConfig;
import madscience.MadEntities;
import madscience.MadNeedles;
import madscience.MadScience;
import madscience.util.MadUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemDecay extends Item
{
    public ItemDecay()
    {

        // All of our needles inherit this class and all our needles go into
        // creative tab.
        this.setCreativeTab(MadEntities.tabMadScience);

        // No needles may be repaired.
        this.setNoRepair();

        // Needles have maximum health of ten.
        this.setMaxDamage(10);

        // Define that we can have normal stack of items.
        this.maxStackSize = 64;
    }
    
/*    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List info, boolean par4)
    {
        String tooltip = StatCollector.translateToLocal(getUnlocalizedName() + ".tooltip");

        if (tooltip != null && tooltip.length() > 0)
        {
            info.addAll(MadUtils.splitStringPerWord(tooltip, 5));
        }
    }*/

    public int getDamageVsEntity(Entity par1Entity)
    {
        // Stabbing another entity other than yourself takes 1 heart.
        return 2;
    }

    @Override
    public boolean onEntityItemUpdate(EntityItem entityItem)
    {
        // Check if the entityItem is not null.
        if (entityItem == null)
        {
            return false;
        }

        if (!MadConfig.DECAY_BLOODWORK)
        {
            return false;
        }

        // Only perform update on server.
        if (entityItem.worldObj.isRemote)
        {
            return false;
        }

        /* // Check if current biome is cold. try { BiomeGenBase biomegenbase = entityItem.worldObj.getWorldChunkManager().getBiomeGenAt(entityItem.serverPosX, entityItem.serverPosY); float currentTemp = biomegenbase.getFloatTemperature(); if (currentTemp
         * < 0.2 || biomegenbase.getEnableSnow()) { return false; } } catch (Exception err) { MadScience.logger.info(err.getMessage()); return false; } */

        // Ensure that our item is currently loaded.
        if (!entityItem.addedToChunk)
        {
            return false;
        }

        // Get the items that this entityItem represent.
        ItemStack stack = entityItem.getEntityItem();

        if (entityItem.worldObj.getWorldTime() % (MadConfig.DECAY_DELAY_IN_SECONDS * MadScience.SECOND_IN_TICKS) != 0L)
        {
            return false;
        }

        // Check if we need to damage ourselves.
        int dmg = stack.getItemDamage();
        if (dmg >= stack.getMaxDamage())
        {
            // Ensure we sit at the ceiling of damage to ensure items stack.
            // stack.setItemDamage(this.getMaxDamage());
            stack = new ItemStack(MadNeedles.NEEDLE_DIRTY);
            return false;
        }

        // Damage ourselves for not being in proper environment.
        if (stack != null && stack.getItem() == this)
        {
            stack.setItemDamage(dmg + 1);

            // Debugging message.
            //MadScience.logger.info("WORLD(" + this.getUnlocalizedName() + "): " + stack.getItemDamage());
            return false;
        }

        return false;
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean inHand)
    {
        if (stack == null)
        {
            return;
        }

        if (!MadConfig.DECAY_BLOODWORK)
        {
            return;
        }

        if (world == null)
        {
            return;
        }

        if (world.getWorldTime() % (MadConfig.DECAY_DELAY_IN_SECONDS * MadScience.SECOND_IN_TICKS) != 0L)
        {
            return;
        }

        // Only perform update on server.
        if (world.isRemote)
        {
            return;
        }

        /* // Check if current biome is cold. try { BiomeGenBase biomegenbase = world.getWorldChunkManager().getBiomeGenAt(entity.serverPosX, entity.serverPosY); float currentTemp = biomegenbase.getFloatTemperature(); if (currentTemp < 0.2 ||
         * biomegenbase.getEnableSnow()) { return; } } catch (Exception err) { MadScience.logger.info(err.getMessage()); return; } */

        // Check if we need to turn into a dirty needle.
        int dmg = stack.getItemDamage();
        if (dmg >= stack.getMaxDamage())
        {
            replaceItemStack(stack, entity);
            return;
        }

        // Damage ourselves for not being in proper environment.
        if (stack != null && stack.getItem() == this)
        {
            stack.setItemDamage(dmg + 1);

            // Debugging message.
            //MadScience.logger.info("PLAYER(" + this.getUnlocalizedName() + "): " + stack.getItemDamage());
        }
    }

    public void replaceItemStack(ItemStack stack, Entity entity)
    {
        //MadScience.logger.info(this.getUnlocalizedName() + " have expired!");
    }
}

package madscience.items.dna;

import madscience.MadConfig;
import madscience.MadScience;
import madscience.items.ItemComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

/** Prefab for any item that will decay
 * Created by robert on 2/6/2015.
 */
public abstract class ItemDNADecay extends ItemDNA
{
    public int maxLifeTime = 1000;

    @Override
    public boolean onEntityItemUpdate(EntityItem entityItem)
    {
        if (!MadConfig.DECAY_BLOODWORK && entityItem != null && !entityItem.worldObj.isRemote && canDecay(entityItem.getEntityItem()))
        {
            if (entityItem.worldObj.getWorldTime() % (MadConfig.DECAY_DELAY_IN_SECONDS * MadScience.SECOND_IN_TICKS) == 0L)
            {
                int ticksExist = getTicks(entityItem.getEntityItem());

                if (ticksExist + 1 > maxLifeTime)
                {
                    //set stack to dirty needles
                    entityItem.setEntityItemStack(getDecayedStack(entityItem.getEntityItem()));
                }
                else
                {
                    setTicks(entityItem.getEntityItem(), ticksExist + 1);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean inHand)
    {
        if (!MadConfig.DECAY_BLOODWORK && entity instanceof EntityPlayer && !world.isRemote && canDecay(stack))
        {
            if (world.getWorldTime() % (MadConfig.DECAY_DELAY_IN_SECONDS * MadScience.SECOND_IN_TICKS) == 0L)
            {
                int ticksExist = getTicks(stack);

                if (ticksExist + 1 > maxLifeTime)
                {
                    //set stack to dirty needles
                    ((EntityPlayer) entity).inventory.setInventorySlotContents(slot, getDecayedStack(stack));
                }
                else
                {
                    setTicks(stack, ticksExist + 1);
                }
            }
        }
    }

    /**
     * Gets the number of ticks on the item
     */
    public static int getTicks(ItemStack stack)
    {
        if (stack.getTagCompound() == null)
            stack.setTagCompound(new NBTTagCompound());

        return stack.getTagCompound().getInteger("ticksExist");
    }

    /**
     * Sets the number of ticks on the item
     */
    public static void setTicks(ItemStack stack, int ticks)
    {
        if (stack.getTagCompound() == null)
        stack.setTagCompound(new NBTTagCompound());

        stack.getTagCompound().setInteger("ticksExist", ticks);
    }

    /**
     * Can the stack decay, use this to prevent key items from decaying
     */
    public boolean canDecay(ItemStack stack)
    {
        return stack != null && getDNA(stack.getItemDamage()) != null;
    }

    /**
     * Called to get the item that will replace the current item if it decays fully
     */
    public abstract ItemStack getDecayedStack(ItemStack stack);
}

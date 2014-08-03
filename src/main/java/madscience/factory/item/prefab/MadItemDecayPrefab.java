package madscience.factory.item.prefab;

import madscience.MadNeedles;
import madscience.factory.item.MadItemFactoryProduct;
import madscience.util.MadUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

abstract class MadItemDecayPrefab extends MadItemBasePrefab
{
    public MadItemDecayPrefab(MadItemFactoryProduct itemData)
    {
        super(itemData);
    }

    @Override
    public boolean onEntityItemUpdate(EntityItem entityItem)
    {
        // Check if the entityItem is not null.
        if (entityItem == null)
        {
            return false;
        }

        // Only perform update on server.
        if (entityItem.worldObj.isRemote)
        {
            return false;
        }

        // Ensure that our item is currently loaded.
        if (!entityItem.addedToChunk)
        {
            return false;
        }

        // Get the items that this entityItem represent.
        ItemStack stack = entityItem.getEntityItem();

        // Decay DNA every 120 seconds while exposed.
        if (entityItem.worldObj.getWorldTime() % (120 * MadUtils.SECOND_IN_TICKS) != 0L)
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

        if (world == null)
        {
            return;
        }

        // Decay DNA every 120 seconds while exposed.
        if (world.getWorldTime() % (120 * MadUtils.SECOND_IN_TICKS) != 0L)
        {
            return;
        }

        // Only perform update on server.
        if (world.isRemote)
        {
            return;
        }

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

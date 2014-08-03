package madscience.factory.item.prefab;

import madscience.factory.item.MadItemFactoryProduct;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MadItemPrefab extends MadItemDecayPrefab
{
    public MadItemPrefab(MadItemFactoryProduct itemData)
    {
        super(itemData);
    }
    
    @Override
    public boolean onEntityItemUpdate(EntityItem entityItem)
    {
        return super.onEntityItemUpdate(entityItem);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean inHand)
    {
        super.onUpdate(stack, world, entity, par4, inHand);
    }

    @Override
    public void replaceItemStack(ItemStack stack, Entity entity)
    {
        super.replaceItemStack(stack, entity);
    }
}

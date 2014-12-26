package madscience.item;


import madscience.product.ItemFactoryProduct;
import madscience.sound.SoundTriggerEnum;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;


public class ItemPrefab extends ItemBasePrefab
{
    public ItemPrefab(int itemID)
    {
        super( itemID );
    }

    public ItemPrefab(ItemFactoryProduct itemData)
    {
        super( itemData );
    }

    @Override
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player)
    {
        // Right click trigger sound.
        SubItemsArchive subItem = this.getRegisteredItem().getSubItemByDamageValue( item.getItemDamage() );
        if (subItem != null)
        {
            subItem.playTriggerSound( SoundTriggerEnum.RIGHTCLICK,
                                      player );
        }

        return super.onItemRightClick( item,
                                       world,
                                       player );
    }

    @Override
    public boolean onLeftClickEntity(ItemStack item, EntityPlayer player, Entity entity)
    {
        SubItemsArchive subItem = this.getRegisteredItem().getSubItemByDamageValue( item.getItemDamage() );
        if (subItem != null)
        {
            subItem.playTriggerSound( SoundTriggerEnum.LEFTCLICK,
                                      player );
        }

        return super.onLeftClickEntity( item,
                                        player,
                                        entity );
    }

    @Override
    public void onUpdate(ItemStack item, World world, Entity entity, int renderPass, boolean hasSubtypes)
    {
        super.onUpdate( item,
                        world,
                        entity,
                        renderPass,
                        hasSubtypes );
    }
}

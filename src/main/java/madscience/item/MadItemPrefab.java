package madscience.item;


import madscience.product.MadItemFactoryProduct;
import madscience.sound.MadSoundTriggerEnum;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;


public class MadItemPrefab extends MadItemBasePrefab
{
    public MadItemPrefab(int itemID)
    {
        super( itemID );
    }

    public MadItemPrefab(MadItemFactoryProduct itemData)
    {
        super( itemData );
    }

    @Override
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player)
    {
        // Right click trigger sound.
        MadMetaItemData subItem = this.getRegisteredItem().getSubItemByDamageValue( item.getItemDamage() );
        if (subItem != null)
        {
            subItem.playTriggerSound( MadSoundTriggerEnum.RIGHTCLICK,
                                      player );
        }

        return super.onItemRightClick( item,
                                       world,
                                       player );
    }

    @Override
    public boolean onLeftClickEntity(ItemStack item, EntityPlayer player, Entity entity)
    {
        MadMetaItemData subItem = this.getRegisteredItem().getSubItemByDamageValue( item.getItemDamage() );
        if (subItem != null)
        {
            subItem.playTriggerSound( MadSoundTriggerEnum.LEFTCLICK,
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

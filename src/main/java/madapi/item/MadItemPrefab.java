package madapi.item;

import madapi.product.MadItemFactoryProduct;
import madapi.sound.MadSoundTriggerEnum;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MadItemPrefab extends MadItemBasePrefab
{
    public MadItemPrefab(int itemID)
    {
        super(itemID);
    }

    public MadItemPrefab(MadItemFactoryProduct itemData)
    {
        super(itemData);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player)
    {
        // Right click trigger sound.
        MadMetaItemData subItem = this.getRegisteredItem().getSubItemByDamageValue(item.getItemDamage());
        if (subItem != null)
        {
            subItem.playTriggerSound(MadSoundTriggerEnum.RIGHTCLICK, player);
        }
        
        return super.onItemRightClick(item, world, player);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack item, EntityPlayer player, Entity entity)
    {
        MadMetaItemData subItem = this.getRegisteredItem().getSubItemByDamageValue(item.getItemDamage());
        if (subItem != null)
        {
            subItem.playTriggerSound(MadSoundTriggerEnum.LEFTCLICK, player);
        }
        
        return super.onLeftClickEntity(item, player, entity);
    }

    @Override
    public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5)
    {
        super.onUpdate(par1ItemStack, par2World, par3Entity, par4, par5);
    }
}

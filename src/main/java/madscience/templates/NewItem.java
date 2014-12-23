package madscience.templates;


import madscience.item.MadItemPrefab;
import madscience.product.MadItemFactoryProduct;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;


public class NewItem extends MadItemPrefab
{

    public NewItem(int itemID)
    {
        super( itemID );
        // TODO Auto-generated constructor stub
    }

    public NewItem(MadItemFactoryProduct itemData)
    {
        super( itemData );
        // TODO Auto-generated constructor stub
    }

    @Override
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player)
    {
        // TODO Auto-generated method stub
        return super.onItemRightClick( item,
                                       world,
                                       player );
    }

    @Override
    public boolean onLeftClickEntity(ItemStack item, EntityPlayer player, Entity entity)
    {
        // TODO Auto-generated method stub
        return super.onLeftClickEntity( item,
                                        player,
                                        entity );
    }

    @Override
    public void onUpdate(ItemStack item, World world, Entity entity, int renderPass, boolean hasSubtypes)
    {
        // TODO Auto-generated method stub
        super.onUpdate( item,
                        world,
                        entity,
                        renderPass,
                        hasSubtypes );
    }

}

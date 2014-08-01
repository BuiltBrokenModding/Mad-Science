package madscience.items;

import madscience.factory.item.MadItemFactoryProduct;
import madscience.factory.item.prefab.MadItemDecayPrefab;
import madscience.factory.item.prefab.MadItemPrefab;
import madscience.factory.mod.MadMod;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemDNASampleLogic extends MadItemPrefab
{
    public ItemDNASampleLogic(MadItemFactoryProduct itemData)
    {
        super(itemData);
    }

    @Override
    public void replaceItemStack(ItemStack stack, Entity entity)
    {
        // Loop through the players inventory and convert any items into dirty
        // needles if they decay completely.
        for (int i = 0; i < 36; i++)
        {
            if (((EntityPlayer) entity).inventory.mainInventory[i] != null)
            {
                if (((EntityPlayer) entity).inventory.mainInventory[i] == stack)
                {
                    ((EntityPlayer) entity).inventory.mainInventory[i] = new ItemStack(Item.slimeBall, stack.stackSize);
                }
            }
        }
    }


}

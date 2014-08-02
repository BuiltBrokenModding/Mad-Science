package madscience.factory.item.prefab;

import java.util.List;

import madscience.factory.item.MadItemFactoryProduct;
import madscience.util.MadUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;

public class MadItemPrefab extends MadItemDecayPrefab
{
    public MadItemPrefab(MadItemFactoryProduct itemData)
    {
        super(itemData);
    }
    
    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List info, boolean par4)
    {
        // Only displays tooltip information when SHIFT key is pressed.
        String tooltip = StatCollector.translateToLocal(getUnlocalizedName() + ".tooltip");
        String defaultTooltip = StatCollector.translateToLocal("noshift.tooltip");
        boolean isShiftPressed = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);

        // Use LWJGL to detect what key is being pressed.
        if (tooltip != null && tooltip.length() > 0 && isShiftPressed)
        {
            info.addAll(MadUtils.splitStringPerWord(tooltip, 5));
        }
        else if (defaultTooltip != null && defaultTooltip.length() > 0 && !isShiftPressed)
        {
            info.addAll(MadUtils.splitStringPerWord(String.valueOf(defaultTooltip), 10));
        }
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

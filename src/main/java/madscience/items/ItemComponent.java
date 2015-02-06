package madscience.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import madscience.MadEntities;
import madscience.MadScience;
import madscience.util.MadUtils;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import org.lwjgl.input.Keyboard;

import java.util.List;

public class ItemComponent extends Item
{
    public ItemComponent()
    {
        // All of our components extend this class.
        this.setCreativeTab(MadEntities.tabMadScience);

        // No components may be repaired.
        this.setNoRepair();

        // Makes it so your item doesn't have the damage.
        setMaxDamage(0);

        // Define that we can have normal stack of items.
        this.maxStackSize = 64;
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

    public int getDamageVsEntity(Entity par1Entity)
    {
        // Stabbing another entity other than yourself takes 1 heart.
        return 2;
    }

    @Override
    public int getItemEnchantability()
    {
        // This item is not enchantable.
        return 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon(MadScience.ID + ":" + (this.getUnlocalizedName().substring(5)));
    }
}

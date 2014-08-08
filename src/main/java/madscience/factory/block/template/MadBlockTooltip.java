package madscience.factory.block.template;

import java.util.List;

import madscience.util.MadUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import org.lwjgl.input.Keyboard;

public class MadBlockTooltip extends ItemBlock
{
    public MadBlockTooltip(int id) // NO_UCD (unused code)
    {
        super(id);
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

}

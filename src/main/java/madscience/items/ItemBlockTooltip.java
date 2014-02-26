package madscience.items;

import java.util.List;

import madscience.util.MadUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class ItemBlockTooltip extends ItemBlock
{
        public ItemBlockTooltip(int id)
        {
                super(id);
        }

        @Override
        public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List info, boolean par4)
        {
                String tooltip = StatCollector.translateToLocal(getUnlocalizedName() + ".tooltip");

                if (tooltip != null && tooltip.length() > 0)
                {
                        info.addAll(MadUtils.splitStringPerWord(tooltip, 5));
                }
        }

}

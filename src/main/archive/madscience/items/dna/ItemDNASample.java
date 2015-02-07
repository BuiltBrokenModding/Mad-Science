package madscience.items.dna;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import madscience.MadScience;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ItemDNASample extends ItemDNADecay
{
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon(MadScience.ID + ":dnaSample_overlay");
        this.layer2 = par1IconRegister.registerIcon(MadScience.ID + ":dnaSample");
        this.layer1 = this.itemIcon;
    }

    @Override
    public ItemStack getDecayedStack(ItemStack stack)
    {
        return new ItemStack(Items.slime_ball, stack.stackSize);
    }
}

package madscience.items;

import madscience.MadNeedles;
import madscience.MadScience;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemDecayNeedle extends ItemDecay
{
    /** Base color of the egg */
    public int primaryColor;

    /** Color of the egg spots */
    public int secondaryColor;

    @SideOnly(Side.CLIENT)
    private Icon needleReelLayer1;

    @SideOnly(Side.CLIENT)
    private Icon needleReelLayer2;

    public ItemDecayNeedle(int id, int primaryColor, int secondaryColor)
    {
        // Used to determine that we should return a dirty needle.
        super(id);

        // Define color information about the egg in HEX color notation (thanks Notch).
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
    {
        // Prevents coloring of the third render pass (our base of the icon).
        if (par2 == 2)
        {
            return 16777215;
        }

        // Applies custom coloring to the mob eggs based on constructor colors passed along.
        return this != null ? (par2 == 0 ? this.primaryColor : this.secondaryColor) : 16777215;
    }

    @Override
    public Icon getIcon(ItemStack stack, int pass)
    {
        if (pass == 0)
        {
            return this.needleReelLayer1;
        }
        else if (pass == 1)
        {
            return this.needleReelLayer2;
        }
        else if (pass == 2)
        {
            return this.itemIcon;
        }

        return this.itemIcon;
    }

    @Override
    public Icon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining)
    {
        // Fired by Minecraft/Forge so it can get the icons for different render passes.
        return getIcon(stack, renderPass);
    }

    @Override
    public int getRenderPasses(int metadata)
    {
        return 3;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister par1IconRegister)
    {
        super.registerIcons(par1IconRegister);

        this.itemIcon = par1IconRegister.registerIcon(MadScience.ID + ":needleDNA_overlay");
        this.needleReelLayer1 = par1IconRegister.registerIcon(MadScience.ID + ":needleDNA1");
        this.needleReelLayer2 = par1IconRegister.registerIcon(MadScience.ID + ":needleDNA2");

        this.iconString = itemIcon.getIconName();
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
                    ((EntityPlayer) entity).inventory.mainInventory[i] = new ItemStack(MadNeedles.NEEDLE_DIRTY, stack.stackSize);
                }
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses()
    {
        // Tells minecraft to make more than one pass in rendering this item so we can have multiple layers to it.
        return true;
    }
}

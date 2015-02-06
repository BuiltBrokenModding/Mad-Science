package madscience.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import madscience.MadScience;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemDecayDNA extends ItemDecay
{
    /**
     * Base color of the egg
     */
    public int primaryColor;

    /**
     * Color of the egg spots
     */
    public int secondaryColor;

    @SideOnly(Side.CLIENT)
    private IIcon dnaSampleOverlay;

    public ItemDecayDNA(int id, int primaryColor, int secondaryColor)
    {
        // Used to ensure that DNA samples decay into bad blood samples.
        super(id);

        // Define color information about the egg in HEX color notation (thanks Notch).
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
    {
        // Applies custom coloring to the mob eggs based on constructor colors passed along.
        return this != null ? (par2 == 0 ? this.primaryColor : this.secondaryColor) : 16777215;
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass)
    {
        if (pass == 0)
        {
            return this.itemIcon;
        }
        else if (pass == 1)
        {
            return this.dnaSampleOverlay;
        }

        return this.itemIcon;
    }

    @Override
    public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining)
    {
        // Fired by Minecraft/Forge so it can get the icons for different render passes.
        return getIcon(stack, renderPass);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister par1IconRegister)
    {
        super.registerIcons(par1IconRegister);

        this.itemIcon = par1IconRegister.registerIcon(MadScience.ID + ":dnaSample_overlay");
        this.dnaSampleOverlay = par1IconRegister.registerIcon(MadScience.ID + ":dnaSample");

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
                    ((EntityPlayer) entity).inventory.mainInventory[i] = new ItemStack(Items.slime_ball, stack.stackSize);
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

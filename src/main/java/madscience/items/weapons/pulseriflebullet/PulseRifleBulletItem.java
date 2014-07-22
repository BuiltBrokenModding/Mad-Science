package madscience.items.weapons.pulseriflebullet;

import java.util.List;

import madscience.MadWeapons;
import madscience.factory.mod.MadMod;
import madscience.util.MadUtils;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PulseRifleBulletItem extends Item
{
    public PulseRifleBulletItem(int itemID)
    {
        super(itemID);
        this.setUnlocalizedName(MadWeapons.WEAPONITEM_PULSERIFLE_BULLET_INTERNALNAME);
        this.setCreativeTab(MadMod.getCreativeTab());

        // Bullets have no individual damage.
        this.setNoRepair();

        // Bullets cannot take damage, they deal it motha trucka'
        this.setMaxDamage(0);

        // Bullets can stack like normal items up to 64.
        this.setMaxStackSize(64);

        // Bullets have no subtypes, what is this ARMA?
        this.setHasSubtypes(false);
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List info, boolean par4)
    {
        // Display localalized tooltip for the item from the language file that is currently active.
        String tooltip = StatCollector.translateToLocal("item." + MadWeapons.WEAPONITEM_PULSERIFLE_BULLET_INTERNALNAME + ".tooltip");
        if (tooltip != null && tooltip.length() > 0)
        {
            info.addAll(MadUtils.splitStringPerWord(tooltip, 4));
        }
    }

    @Override
    public boolean canHarvestBlock(Block par1Block)
    {
        return false;
    }

    @Override
    public int getItemEnchantability()
    {
        // Weapons are not enchantable.
        return 0;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        // Displays the localized name for the item from the language file that is currently active.
        String name = ("item." + StatCollector.translateToLocal(MadWeapons.WEAPONITEM_PULSERIFLE_BULLET_INTERNALNAME)).trim();
        return name;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon(MadMod.ID + ":" + (this.getUnlocalizedName().substring(5)));
    }

    @Override
    public boolean shouldRotateAroundWhenRendering()
    {
        // Prevents us having to rotate the weapon 180 degrees in renderer.
        return true;
    }
}

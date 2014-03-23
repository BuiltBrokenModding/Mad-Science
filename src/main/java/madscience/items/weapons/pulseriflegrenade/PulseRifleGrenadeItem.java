package madscience.items.weapons.pulseriflegrenade;

import java.util.List;

import madscience.MadEntities;
import madscience.MadScience;
import madscience.MadWeapons;
import madscience.util.MadUtils;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PulseRifleGrenadeItem extends Item
{
    public PulseRifleGrenadeItem(int itemID)
    {
        super(itemID);
        this.setUnlocalizedName(MadWeapons.WEAPONITEM_PULSERIFLE_GRENADE_INTERNALNAME);
        this.setCreativeTab(MadEntities.tabMadScience);

        // Cannot repair grenades as they take no damage.
        this.setNoRepair();

        // Grenades take no damage.
        this.setMaxDamage(0);

        // Grenades will only stack one at a time due to the explosive effects.
        this.setMaxStackSize(16);

        // Grenades have no subtypes.
        this.setHasSubtypes(false);
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List info, boolean par4)
    {
        // Display localalized tooltip for the item from the language file that is currently active.
        String tooltip = StatCollector.translateToLocal("item." + MadWeapons.WEAPONITEM_PULSERIFLE_GRENADE_INTERNALNAME + ".tooltip");
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
        String name = ("item." + StatCollector.translateToLocal(MadWeapons.WEAPONITEM_PULSERIFLE_GRENADE_INTERNALNAME)).trim();
        return name;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon(MadScience.ID + ":" + (this.getUnlocalizedName().substring(5)));
    }

    @Override
    public boolean shouldRotateAroundWhenRendering()
    {
        // Prevents us having to rotate the weapon 180 degrees in renderer.
        return true;
    }
}

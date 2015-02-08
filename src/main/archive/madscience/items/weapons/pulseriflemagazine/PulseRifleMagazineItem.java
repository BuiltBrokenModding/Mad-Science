package madscience.content.items.weapons.pulseriflemagazine;

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

public class PulseRifleMagazineItem extends Item
{
    public PulseRifleMagazineItem(int itemID)
    {
        super(itemID);
        this.setUnlocalizedName(MadWeapons.WEAPONITEM_PULSERIFLE_MAGAZINE_INTERNALNAME);
        this.setCreativeTab(MadEntities.tabMadScience);

        // You cannot repair magazines since we use them to measure how many bullets are inside of them.
        this.setNoRepair();

        // Magazines can hold up to 99 bullets.
        this.setMaxDamage(99);

        // Magazines will stack up to 64 like normal minecraft items.
        this.setMaxStackSize(64);

        // We do not have any sub-types.
        this.setHasSubtypes(false);
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List info, boolean par4)
    {
        // Display localalized tooltip for the item from the language file that is currently active.
        if (par1ItemStack.getItemDamage() == 0)
        {
            // Magazine has no bullets inside of it.
            info.add("Empty Magazine");
        }
        else
        {
            // Magazine has bullets inside of it.
            String tooltip = StatCollector.translateToLocal("item." + MadWeapons.WEAPONITEM_PULSERIFLE_MAGAZINE_INTERNALNAME + ".tooltip");
            if (tooltip != null && tooltip.length() > 0)
            {
                // Replace the tooltip with information about current rounds.
                if (par1ItemStack.getItemDamage() == 99)
                {
                    tooltip = tooltip.replace("%0", String.valueOf(1));
                }
                else
                {
                    tooltip = tooltip.replace("%0", String.valueOf(Math.abs(100 - this.getDamage(par1ItemStack))));
                }

                tooltip = tooltip.replace("%1", String.valueOf(this.getMaxDamage()));
                info.addAll(MadUtils.splitStringPerWord(tooltip, 4));
            }
        }
    }

    @Override
    public boolean canHarvestBlock(Block par1Block)
    {
        return false;
    }

    @Override
    public boolean doesContainerItemLeaveCraftingGrid(ItemStack par1ItemStack)
    {
        // Keep the magazine on the crafting table.
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
        String name = ("item." + StatCollector.translateToLocal(MadWeapons.WEAPONITEM_PULSERIFLE_MAGAZINE_INTERNALNAME)).trim();
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

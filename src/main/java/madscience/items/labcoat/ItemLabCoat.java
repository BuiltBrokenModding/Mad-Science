package madscience.items.labcoat;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import madscience.MadEntities;
import madscience.MadScience;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public class ItemLabCoat extends ItemArmor
{
    public ItemLabCoat(int id, EnumArmorMaterial material, int index, int slot)
    {
        super(id, material, index, slot);
        this.setCreativeTab(MadEntities.tabMadScience);
        this.setMaxDamage(200000);
    }

    @Override
    public Item setUnlocalizedName(String par1Str)
    {
        super.setUnlocalizedName(par1Str);
        this.setTextureName(par1Str);
        return this;
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, int layer)
    {
        return MadScience.ID + ":" + MadScience.MODEL_DIRECTORY + "labCoat/labCoat.png";
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon(MadScience.ID + ":" + (this.getUnlocalizedName().substring(5)));
    }
}

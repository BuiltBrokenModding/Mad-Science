package madscience.items.dna;

import com.builtbroken.mc.core.registry.IRegistryInit;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import madscience.MadScience;
import madscience.util.MadUtils;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

public class ItemNeedle extends ItemDNADecay implements IRegistryInit
{
    public static final String NEEDLEITEM_STABPLAYER = MadScience.ID + ":needleEmpty.Stabself";
    public static final String NEEDLEITEM_STABMOB = MadScience.ID + ":needleEmpty.Stab";

    @Override
    public ItemStack onItemRightClick(ItemStack items, World world, EntityPlayer player)
    {
        if (items != null && items.stackSize > 0 && player != null)
        {
            // Play a sound of you using the needle on a mob!
            player.playSound(NEEDLEITEM_STABPLAYER, 1.0F, 1.0F);
            //If needle is an empty needle
            if (items.getItemDamage() == 0)
            {
                if (!player.capabilities.isCreativeMode)
                {
                    items.stackSize--;
                    player.attackEntityFrom(DamageSource.generic, 2);
                    player.addExhaustion(5.0F);
                }

                //Add DNA needle to inventory
                ItemStack stack = EnumDNA.getNeedleFor(player);
                if (!player.inventory.addItemStackToInventory(stack))
                {
                    player.entityDropItem(stack, 0);
                }

                // Remove an item from our stack!
                return items;
            }
        }

        return items;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack items, EntityPlayer player, Entity entity)
    {
        if (items != null && items.stackSize > 0 && entity != null)
        {
            // Play a sound of you using the needle on a mob!
            entity.playSound(NEEDLEITEM_STABMOB, 1.0F, 1.0F);
            //If needle is an empty needle
            if (items.getItemDamage() == 0)
            {
                //Get filled DNA needle
                ItemStack stack = EnumDNA.getNeedleFor(entity);
                if (stack != null)
                {
                    //Just to be nice to the player only consume needle for valid DNA
                    items.stackSize--;
                    if (!player.inventory.addItemStackToInventory(stack))
                    {
                        player.entityDropItem(stack, 0);
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        if (stack.getItemDamage() == 0)
        {
            return "item.needleEmpty";
        }
        else if (stack.getItemDamage() == 1)
        {
            return "item.needleDirty";
        }
        else if (getDNA(stack.getItemDamage()) != null)
        {
            return "item." + getDNA(stack.getItemDamage()).needleString();
        }
        return super.getUnlocalizedName();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List items)
    {
        items.add(new ItemStack(item, 1, 0));
        items.add(new ItemStack(item, 1, 1));
        for (EnumDNA dna : EnumDNA.values())
        {
            items.add(new ItemStack(item, 1, dna.ordinal() + 2));
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon(MadScience.ID + ":needleDNA_overlay");
        this.layer1 = par1IconRegister.registerIcon(MadScience.ID + ":needleDNA1");
        this.layer2 = par1IconRegister.registerIcon(MadScience.ID + ":needleDNA2");
    }

    @Override
    public EnumDNA getDNA(int meta)
    {
        if (meta >= 2 && meta <= EnumDNA.values().length + 2)
            return EnumDNA.values()[meta - 2];
        return null;
    }

    @Override
    public ItemStack getDecayedStack(ItemStack stack)
    {
        return new ItemStack(this, stack.stackSize, 1);
    }

    @Override
    public void onRegistered()
    {
        // Needle Sanitizer (cleans dirty needles).
        //TODO SanitizerRecipes.addSmelting(this, new ItemStack(this), 0.15F);

        // Shaped.
        GameRegistry.addRecipe(new ItemStack(this), new Object[]
                {
                        // Recipe.
                        "GIG", "G G", " S ",

                        // Parameters.
                        'G', Blocks.glass, 'I', Items.iron_ingot, 'S', Items.stick});
    }

    @Override @SideOnly(Side.CLIENT)
    public void onClientRegistered()
    {

    }
}

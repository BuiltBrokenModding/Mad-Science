package madscience.items.needles;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import madscience.MadConfig;
import madscience.MadNeedles;
import madscience.MadScience;
import madscience.items.ItemComponent;
import madscience.items.dna.EnumDNA;
import madscience.util.MadUtils;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

public class ItemNeedle extends ItemComponent
{
    @SideOnly(Side.CLIENT)
    private IIcon needleReelLayer1;

    @SideOnly(Side.CLIENT)
    private IIcon needleReelLayer2;

    public static int maxLifeTime = 1000;

    public ItemNeedle()
    {
        this.setHasSubtypes(true);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack items, World world, EntityPlayer player)
    {
        if (items != null && items.stackSize > 0 && player != null)
        {
            // Play a sound of you using the needle on a mob!
            player.playSound(NeedleSounds.NEEDLEITEM_STABMOB, 1.0F, 1.0F);
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
            entity.playSound(NeedleSounds.NEEDLEITEM_STABMOB, 1.0F, 1.0F);
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
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List info, boolean par4)
    {
        String tooltip = StatCollector.translateToLocal(getUnlocalizedName() + ".tooltip");

        if (tooltip != null && tooltip.length() > 0)
        {
            info.addAll(MadUtils.splitStringPerWord(tooltip, 5));
        }
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
        else if (stack.getItemDamage() >= 2 && stack.getItemDamage() < EnumDNA.values().length + 2)
        {
            return EnumDNA.values()[stack.getItemDamage() - 2].needleString();
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

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int pass)
    {
        if((pass == 0 || pass == 1) && stack.getItemDamage() >= 2 && stack.getItemDamage() < EnumDNA.values().length + 2)
        {
            EnumDNA dna = EnumDNA.values()[stack.getItemDamage() - 2];
            if(pass == 0)
            {
                return dna.primary_color;
            }
            else if(pass == 1)
            {
                return dna.secondary_color;
            }
        }

        // Applies custom coloring to the mob eggs based on constructor colors passed along.
        return 16777215;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister par1IconRegister)
    {
        super.registerIcons(par1IconRegister);

        this.itemIcon = par1IconRegister.registerIcon(MadScience.ID + ":needleDNA_overlay");
        this.needleReelLayer1 = par1IconRegister.registerIcon(MadScience.ID + ":needleDNA1");
        this.needleReelLayer2 = par1IconRegister.registerIcon(MadScience.ID + ":needleDNA2");
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass)
    {
        if (pass == 0)
        {
            return this.needleReelLayer1;
        }
        else if (pass == 1)
        {
            return this.needleReelLayer2;
        }

        return this.itemIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }

    @Override
    public int getRenderPasses(int metadata)
    {
        return metadata >= 2 && metadata < EnumDNA.values().length + 2 ? 3 : 1;
    }

    @Override
    public boolean onEntityItemUpdate(EntityItem entityItem)
    {
        if(!MadConfig.DECAY_BLOODWORK && entityItem != null && !entityItem.worldObj.isRemote && entityItem.getEntityItem() != null)
        {
            if (entityItem.worldObj.getWorldTime() % (MadConfig.DECAY_DELAY_IN_SECONDS * MadScience.SECOND_IN_TICKS) == 0L)
            {
                if(entityItem.getEntityItem().getTagCompound() == null)
                    entityItem.getEntityItem().setTagCompound(new NBTTagCompound());

                int ticksExist = entityItem.getEntityItem().getTagCompound().getInteger("ticksExist");

                if(ticksExist + 1 > maxLifeTime)
                {
                    //set stack to dirty needles
                    entityItem.setEntityItemStack(new ItemStack(this, entityItem.getEntityItem().stackSize, 1));
                }
                else
                {
                    //Increase life ticks
                    entityItem.getEntityItem().getTagCompound().setInteger("ticksExist", ticksExist + 1);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean inHand)
    {
        if(!MadConfig.DECAY_BLOODWORK && entity instanceof EntityPlayer && !world.isRemote && stack != null)
        {
            if (world.getWorldTime() % (MadConfig.DECAY_DELAY_IN_SECONDS * MadScience.SECOND_IN_TICKS) == 0L)
            {
                if(stack.getTagCompound() == null)
                    stack.setTagCompound(new NBTTagCompound());

                int ticksExist = stack.getTagCompound().getInteger("ticksExist");

                if(ticksExist + 1 > maxLifeTime)
                {
                    //set stack to dirty needles
                    ((EntityPlayer) entity).inventory.setInventorySlotContents(slot, new ItemStack(this, stack.stackSize, 1));
                }
                else
                {
                    //Increase life ticks
                    stack.getTagCompound().setInteger("ticksExist", ticksExist + 1);
                }
            }
        }
    }
}

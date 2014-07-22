package madscience.items.spawnegg;

import java.util.Collection;
import java.util.List;

import madscience.factory.mod.MadMod;
import madscience.util.MadColors;
import madscience.util.MadUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.Facing;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class GeneticallyModifiedMonsterPlacer extends Item
{

    private static void addNBTData(Entity entity, NBTTagCompound spawnData)
    {
        NBTTagCompound newTag = new NBTTagCompound();
        entity.writeToNBTOptional(newTag);

        for (NBTBase nbt : (Collection<NBTBase>) spawnData.getTags())
            newTag.setTag(nbt.getName(), nbt.copy());

        entity.readFromNBT(newTag);
    }
    
    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List info, boolean par4)
    {
        String firstCreature = MadColors.getNameFromColor(this.getColorFromItemStack(par1ItemStack, 0));
        String secondCreature = MadColors.getNameFromColor(this.getColorFromItemStack(par1ItemStack, 1));
        String tooltip = "[" + firstCreature + "] - [" + secondCreature + "]";

        if (tooltip != null && tooltip.length() > 0)
        {
            info.addAll(MadUtils.splitStringPerWord(tooltip, 5));
        }
    }

    private static Entity spawnCreature(World world, ItemStack stack, double x, double y, double z)
    {
        MadSpawnEggInfo info = MadGMORegistry.getEggInfo((short) stack.getItemDamage());

        if (info == null)
            return null;

        String mobID = info.mobID;
        NBTTagCompound spawnData = info.getSpawnData();

        if (stack.hasTagCompound())
        {
            NBTTagCompound compound = stack.getTagCompound();
            if (compound.hasKey("mobID"))
                mobID = compound.getString("mobID");
            if (compound.hasKey("spawnData"))
                spawnData = compound.getCompoundTag("spawnData");
        }

        Entity entity = null;

        entity = EntityList.createEntityByName(mobID, world);

        if (entity != null)
        {
            if (entity instanceof EntityLiving)
            {
                EntityLiving entityliving = (EntityLiving) entity;
                entity.setLocationAndAngles(x, y, z, MathHelper.wrapAngleTo180_float(world.rand.nextFloat() * 360.0F), 0.0F);
                entityliving.rotationYawHead = entityliving.rotationYaw;
                entityliving.renderYawOffset = entityliving.rotationYaw;
                entityliving.onSpawnWithEgg(null);
                if (!spawnData.hasNoTags())
                    addNBTData(entity, spawnData);
                world.spawnEntityInWorld(entity);
                entityliving.playLivingSound();
                spawnRiddenCreatures(entity, world, spawnData);
            }
        }

        return entity;
    }

    private static void spawnRiddenCreatures(Entity entity, World world, NBTTagCompound cur)
    {
        while (cur.hasKey("Riding"))
        {
            cur = cur.getCompoundTag("Riding");
            Entity newEntity = EntityList.createEntityByName(cur.getString("id"), world);
            if (newEntity != null)
            {
                addNBTData(newEntity, cur);
                newEntity.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
                world.spawnEntityInWorld(newEntity);
                entity.mountEntity(newEntity);
            }
            entity = newEntity;
        }
    }

    private Icon icon;

    public GeneticallyModifiedMonsterPlacer(int id)
    {
        super(id);
        setHasSubtypes(true);
        setCreativeTab(MadMod.getCreativeTab());
        this.setUnlocalizedName("gmoMonsterPlacer");

        // We may stack the same amount as normal spawn eggs.
        this.maxStackSize = 64;
    }

    @Override
    public int getColorFromItemStack(ItemStack stack, int par2)
    {
        MadSpawnEggInfo info = MadGMORegistry.getEggInfo((short) stack.getItemDamage());

        if (info == null)
            return 16777215;

        int color = (par2 == 0) ? info.getPrimaryColor() : info.getSecondaryColor();

        if (stack.hasTagCompound())
        {
            NBTTagCompound compound = stack.getTagCompound();
            if (par2 == 0 && compound.hasKey("primaryColor"))
                color = compound.getInteger("primaryColor");
            if (par2 != 0 && compound.hasKey("secondaryColor"))
                color = compound.getInteger("secondaryColor");
        }

        return color;
    }

    @Override
    public Icon getIconFromDamageForRenderPass(int par1, int par2)
    {
        return par2 > 0 ? icon : super.getIconFromDamageForRenderPass(par1, par2);
    }

    @Override
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List list)
    {
        for (MadSpawnEggInfo info : MadGMORegistry.getEggInfoList())
            list.add(new ItemStack(par1, 1, info.eggID));
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        String name = ("" + StatCollector.translateToLocal(getUnlocalizedName() + ".name")).trim();
        MadSpawnEggInfo info = MadGMORegistry.getEggInfo((short) stack.getItemDamage());

        if (info == null)
            return name;

        String mobID = info.mobID;

        if (stack.hasTagCompound())
        {
            NBTTagCompound compound = stack.getTagCompound();
            if (compound.hasKey("mobID"))
                mobID = compound.getString("mobID");
        }

        name = "entity." + mobID;

        return name;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        if (world.isRemote)
            return stack;

        MovingObjectPosition trace = getMovingObjectPositionFromPlayer(world, player, true);

        if (trace == null)
            return stack;

        if (trace.typeOfHit == EnumMovingObjectType.TILE)
        {
            int x = trace.blockX;
            int y = trace.blockY;
            int z = trace.blockZ;

            if (!world.canMineBlock(player, x, y, z) || !player.canPlayerEdit(x, y, z, trace.sideHit, stack))
                return stack;

            if (world.getBlockMaterial(x, y, z) == Material.water)
            {

                Entity entity = spawnCreature(world, stack, x, y, z);
                if (entity != null)
                {
                    if (entity instanceof EntityLiving && stack.hasDisplayName())
                        ((EntityLiving) entity).setCustomNameTag(stack.getDisplayName());
                    if (!player.capabilities.isCreativeMode)
                        --stack.stackSize;
                }
            }
        }

        return stack;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10)
    {
        if (world.isRemote)
            return true;

        int i1 = world.getBlockId(x, y, z);
        x += Facing.offsetsXForSide[par7];
        y += Facing.offsetsYForSide[par7];
        z += Facing.offsetsZForSide[par7];
        double d0 = 0.0D;

        if (par7 == 1 && Block.blocksList[i1] != null && Block.blocksList[i1].getRenderType() == 11)
            d0 = 0.5D;

        Entity entity = spawnCreature(world, stack, x + 0.5D, y + d0, z + 0.5D);

        if (entity != null)
        {
            if (entity instanceof EntityLiving && stack.hasDisplayName())
                ((EntityLiving) entity).setCustomNameTag(stack.getDisplayName());
            if (!player.capabilities.isCreativeMode)
                --stack.stackSize;
        }
        return true;

    }

    @Override
    public void registerIcons(IconRegister iconRegister)
    {
        itemIcon = iconRegister.registerIcon(MadMod.ID + ":gmoMonsterPlacer");
        icon = iconRegister.registerIcon(MadMod.ID + ":gmoMonsterPlacer_overlay");
    }

    @Override
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }
}
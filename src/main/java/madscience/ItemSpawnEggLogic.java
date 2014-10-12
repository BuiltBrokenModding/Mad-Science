package madscience;

import java.util.Collection;

import madapi.factory.MadItemFactory;
import madapi.item.MadItemPrefab;
import madapi.item.MadMetaItemData;
import madapi.product.MadItemFactoryProduct;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemSpawnEggLogic extends MadItemPrefab
{
    public ItemSpawnEggLogic(MadItemFactoryProduct itemData)
    {
        super(itemData);
    }
    
    private static void addNBTData(Entity entity, NBTTagCompound spawnData)
    {
        NBTTagCompound newTag = new NBTTagCompound();
        entity.writeToNBTOptional(newTag);

        for (NBTBase nbt : (Collection<NBTBase>) spawnData.getTags())
            newTag.setTag(nbt.getName(), nbt.copy());

        entity.readFromNBT(newTag);
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

    private static Entity spawnCreature(World world, ItemStack stack, double x, double y, double z)
    {
        // Query item product so we can get clean sub-item name.
        MadItemFactoryProduct baseSpawnEggItem = MadItemFactory.instance().getItemInfo(stack.getUnlocalizedName());
        if (baseSpawnEggItem == null)
        {
            return null;
        }
        
        MadMetaItemData spawnEggSubItem = baseSpawnEggItem.getSubItemByDamageValue(stack.getItemDamage());
        if (spawnEggSubItem == null)
        {
            return null;
        }
        
        // Grab the name of the mob we want to spawn from the sub-item name on the spawn-egg.
        String mobID = spawnEggSubItem.getItemName();
        
        // TODO: Query MadMobFactory for information about sub item mob data.
        NBTTagCompound spawnData = new NBTTagCompound();

        if (stack.hasTagCompound())
        {
            NBTTagCompound compound = stack.getTagCompound();
            if (compound.hasKey("mobID"))
            {
                mobID = compound.getString("mobID");
            }
            
            if (compound.hasKey("spawnData"))
            {
                spawnData = compound.getCompoundTag("spawnData");
            }
        }

        // Create the mob we want to spawn into the game world by name from sub-item on spawn egg.
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
                {
                    addNBTData(entity, spawnData);
                }
                
                world.spawnEntityInWorld(entity);
                entityliving.playLivingSound();
                spawnRiddenCreatures(entity, world, spawnData);
            }
        }

        return entity;
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
    
    private NBTTagCompound createItemTag(byte count, short damage, short id)
    {
        NBTTagCompound item = new NBTTagCompound();
        item.setByte("Count", count);
        item.setShort("Damage", damage);
        item.setShort("id", id);
        return item;
    }

    private NBTTagCompound getEntityTag(String entityID)
    {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("id", entityID);
        return tag;
    }

    public NBTTagCompound horseType(int type)
    {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("Type", type);
        return tag;
    }

    public NBTTagCompound villagerZombie()
    {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setByte("IsVillager", (byte) 1);
        return tag;
    }

    public NBTTagCompound witherSkeleton()
    {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setByte("SkeletonType", (byte) 1);
        NBTTagList list = new NBTTagList();
        NBTTagCompound swordItem = createItemTag((byte) 1, (short) 0, (short) 272);
        list.appendTag(swordItem);
        for (int i = 0; i < 4; ++i)
            list.appendTag(new NBTTagCompound());
        tag.setTag("Equipment", list);
        return tag;
    }
}
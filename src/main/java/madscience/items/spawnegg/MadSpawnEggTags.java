package madscience.items.spawnegg;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class MadSpawnEggTags
{
    private static NBTTagCompound createItemTag(byte count, short damage, short id)
    {
        NBTTagCompound item = new NBTTagCompound();
        item.setByte("Count", count);
        item.setShort("Damage", damage);
        item.setShort("id", id);
        return item;
    }

    private static NBTTagCompound getEntityTag(String entityID)
    {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("id", entityID);
        return tag;
    }

    public static NBTTagCompound horseType(int type)
    {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("Type", type);
        return tag;
    }

    public static NBTTagCompound villagerZombie()
    {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setByte("IsVillager", (byte) 1);
        return tag;
    }

    public static NBTTagCompound witherSkeleton()
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

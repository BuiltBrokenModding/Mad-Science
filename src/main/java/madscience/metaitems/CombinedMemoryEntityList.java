package madscience.metaitems;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityEggInfo;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLLog;

public class CombinedMemoryEntityList
{
    /** Maps entity names to their numeric identifiers */
    private static Map stringToIDMapping = new HashMap();

    /** This is a HashMap of the Creative Entity Eggs/Spawners. */
    public static HashMap entityEggs = new LinkedHashMap();

    /** adds a mapping between Entity classes and both a string representation and an ID */
    public static void addMapping(String par1Str, int par2)
    {
        stringToIDMapping.put(par1Str, Integer.valueOf(par2));
    }

    /** Adds a entity mapping with egg info. */
    public static void addMapping(String par1Str, int par2, int par3, int par4)
    {
        addMapping(par1Str, par2);
        entityEggs.put(Integer.valueOf(par2), new EntityEggInfo(par2, par3, par4));
    }
}

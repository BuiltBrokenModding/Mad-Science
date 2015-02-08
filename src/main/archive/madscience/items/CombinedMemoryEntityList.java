package madscience.content.items;

import net.minecraft.entity.EntityList;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

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
        entityEggs.put(Integer.valueOf(par2), new EntityList.EntityEggInfo(par2, par3, par4));
    }
}

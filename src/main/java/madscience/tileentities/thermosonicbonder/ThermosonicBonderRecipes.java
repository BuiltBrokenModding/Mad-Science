package madscience.tileentities.thermosonicbonder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemStack;

public class ThermosonicBonderRecipes
{
    /** The list of smelting results. */
    private static Map smeltingList = new HashMap();
    private static HashMap<List<Integer>, ItemStack> metaSmeltingList = new HashMap<List<Integer>, ItemStack>();

    /** A metadata sensitive version of adding a furnace recipe. */
    public static void addSmelting(int itemID, int metadata, ItemStack itemstack)
    {
        metaSmeltingList.put(Arrays.asList(itemID, metadata), itemstack);
    }

    /** Adds a smelting recipe. */
    public static void addSmelting(int par1, ItemStack itemStack)
    {
        ThermosonicBonderRecipes.smeltingList.put(Integer.valueOf(par1), itemStack);
    }

    /** Grabs the amount of base experience for this item to give when pulled from the furnace slot. */
    public static float getExperience(ItemStack item)
    {
        return 0.0F;
    }

    /** Used to get the resulting ItemStack form a source ItemStack
     * 
     * @param item The Source ItemStack
     * @return The result ItemStack */
    public static ItemStack getSmeltingResult(ItemStack item)
    {
        if (item == null)
        {
            return null;
        }
        ItemStack ret = metaSmeltingList.get(Arrays.asList(item.itemID, item.getItemDamage()));
        if (ret != null)
        {
            return ret;
        }
        return (ItemStack) smeltingList.get(Integer.valueOf(item.itemID));
    }

    public Map<List<Integer>, ItemStack> getMetaSmeltingList()
    {
        return metaSmeltingList;
    }

    public Map getSmeltingList()
    {
        return ThermosonicBonderRecipes.smeltingList;
    }
}

package madscience.tile.sanitizer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemStack;

public class SanitizerRecipes
{
    /** The list of smelting results. */
    private static Map smeltingList = new HashMap();
    private static Map experienceList = new HashMap();
    private static HashMap<List<Integer>, ItemStack> metaSmeltingList = new HashMap<List<Integer>, ItemStack>();

    private static HashMap<List<Integer>, Float> metaExperience = new HashMap<List<Integer>, Float>();

    /** Adds a smelting recipe. */
    public static void addSmelting(int par1, ItemStack par2ItemStack, float par3)
    {
        SanitizerRecipes.smeltingList.put(Integer.valueOf(par1), par2ItemStack);
        SanitizerRecipes.experienceList.put(Integer.valueOf(par2ItemStack.itemID), Float.valueOf(par3));
    }

    

    /** Used to get the resulting ItemStack form a source ItemStack
     * 
     * @param item The Source ItemStack
     * @return The result ItemStack */
    static ItemStack getSmeltingResult(ItemStack item)
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
        return SanitizerRecipes.smeltingList;
    }
}

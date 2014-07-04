package madscience.tileentities.dnaextractor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemStack;

public class DNAExtractorRecipes
{
    /** The list of smelting results. */
    private static Map smeltingList = new HashMap();
    private static Map experienceList = new HashMap();
    private static HashMap<List<Integer>, ItemStack> metaSmeltingList = new HashMap<List<Integer>, ItemStack>();
    private static HashMap<List<Integer>, Float> metaExperience = new HashMap<List<Integer>, Float>();

    /** A metadata sensitive version of adding a furnace recipe. */
    public static void addSmelting(int itemID, int metadata, ItemStack itemstack, float experience)
    {
        metaSmeltingList.put(Arrays.asList(itemID, metadata), itemstack);
        metaExperience.put(Arrays.asList(itemstack.itemID, itemstack.getItemDamage()), experience);
    }

    /** Adds a smelting recipe. */
    public static void addSmelting(int par1, ItemStack par2ItemStack, float par3)
    {
        DNAExtractorRecipes.smeltingList.put(Integer.valueOf(par1), par2ItemStack);
        DNAExtractorRecipes.experienceList.put(Integer.valueOf(par2ItemStack.itemID), Float.valueOf(par3));
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
        return DNAExtractorRecipes.smeltingList;
    }
}

package madscience.tileentities.cncmachine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemStack;

public class CnCMachineRecipes
{
    private static HashMap<List<String>, Float> metaExperience = new HashMap<List<String>, Float>();
    private static HashMap<List<String>, ItemStack> metaSmeltingList = new HashMap<List<String>, ItemStack>();

    private static Map weaponExperiencePerCode = new HashMap();
    private static Map weaponSchematicsPerCode = new HashMap();

    public static void addSmeltingResult(String binaryText, ItemStack smeltingResult, float experience)
    {
        CnCMachineRecipes.weaponSchematicsPerCode.put(String.valueOf(binaryText.toLowerCase().toString()), smeltingResult);
        CnCMachineRecipes.weaponExperiencePerCode.put(String.valueOf(binaryText.toLowerCase().toString()), Float.valueOf(experience));
    }

    public static float getExperience(String binaryText)
    {
        // Returns a certain amount of experience for smelting the item.
        if (binaryText == null)
        {
            return 0;
        }

        float ret = 0.0F;
        if (weaponExperiencePerCode.containsKey(String.valueOf(binaryText.toLowerCase().toString())))
        {
            ret = ((Float) weaponExperiencePerCode.get(String.valueOf(binaryText.toLowerCase().toString()))).floatValue();
        }
        return (ret < 0 ? 0 : ret);
    }

    public static ItemStack getSmeltingResult(String binaryText)
    {
        // Returns the smelting result from a given binary piece of text.
        if (binaryText == null)
        {
            return null;
        }
        return (ItemStack) weaponSchematicsPerCode.get(String.valueOf(binaryText.toLowerCase().toString()));
    }

    public Map<List<String>, ItemStack> getMetaSmeltingList()
    {
        // Returns the entire smelting list for iterator purposes.
        return metaSmeltingList;
    }

    public Map getSmeltingList()
    {
        // Returns the list of Items mapped to binary codes.
        return CnCMachineRecipes.weaponSchematicsPerCode;
    }
}

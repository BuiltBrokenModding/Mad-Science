package madscience.tileentities.cncmachine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import madscience.MadScience;
import madscience.util.MadUtils;
import net.minecraft.item.ItemStack;

public class CnCMachineRecipes
{
    private static HashMap<List<String>, Float> metaExperience = new HashMap<List<String>, Float>();
    private static HashMap<List<String>, ItemStack> metaSmeltingList = new HashMap<List<String>, ItemStack>();

    private static Map weaponExperiencePerCode = new HashMap();
    private static Map weaponSchematicsPerCode = new HashMap();

    public static void addSmeltingResult(String plainText, ItemStack smeltingResult, float experience)
    {
        // Force the inputed plain text to be all lower case with and trimmed.
        String trimmedInput = String.valueOf(plainText.toLowerCase().trim());

        // Convert the plain text into a literal binary representation of itself.
        String binaryASCII = MadUtils.AsciiToBinary(trimmedInput);

        // Debuggin'
        //MadScience.logger.info("ASCII:" + trimmedInput);
        //MadScience.logger.info("BINARY:" + binaryASCII);
        //MadScience.logger.info("DECODED:" + MadUtils.BinaryToAscii(binaryASCII));

        // Add the recipes that convert binary strings into item stacks.
        CnCMachineRecipes.weaponSchematicsPerCode.put(String.valueOf(binaryASCII), smeltingResult);
        CnCMachineRecipes.weaponExperiencePerCode.put(String.valueOf(binaryASCII), Float.valueOf(experience));
    }

    

    static ItemStack getSmeltingResult(String binaryText)
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

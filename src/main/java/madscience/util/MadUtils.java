package madscience.util;

import java.util.ArrayList;
import java.util.List;

import madscience.MadScience;
import madscience.world.MadExplosion;
import net.minecraft.entity.Entity;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class MadUtils
{
    public static List<String> splitStringPerWord(String string, int wordsPerLine)
    {
        String[] words = string.split(" ");
        List<String> lines = new ArrayList<String>();

        for (int lineCount = 0; lineCount < Math.ceil((float) words.length / (float) wordsPerLine); lineCount++)
        {
            String stringInLine = "";

            for (int i = lineCount * wordsPerLine; i < Math.min(wordsPerLine + lineCount * wordsPerLine, words.length); i++)
            {
                stringInLine += words[i] + " ";
            }

            lines.add(stringInLine.trim());
        }

        return lines;
    }

    public static List<Integer> splitIntegerPerDigit(int i)
    {
        // Splits an integer into corresponding array of numbers.
        // http://stackoverflow.com/questions/5196186/split-int-value-into-seperate-digits
        List<Integer> digits = new ArrayList<Integer>();
        while (i > 0)
        {
            digits.add(i % 10);
            i /= 10;
        }
        return digits;
    }
}

package madscience.util;

import java.util.ArrayList;
import java.util.List;

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
}

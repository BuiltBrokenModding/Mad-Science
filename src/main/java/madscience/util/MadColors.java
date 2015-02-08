package madscience.util;

import java.awt.*;

public class MadColors
{
    public static String getNameFromColor(int colorRGB)
    {
        // Accepts color elements that should convert back to mob names.
        if (colorRGB == batEgg())
        {
            return "Bat";
        }

        if (colorRGB == blazeEgg())
        {
            return "Blaze";
        }

        if (colorRGB == caveSpiderEgg())
        {
            return "Cave Spider";
        }

        if (colorRGB == chickenEgg())
        {
            return "Chicken";
        }

        if (colorRGB == cowEgg())
        {
            return "Cow";
        }

        if (colorRGB == creeperEgg())
        {
            return "Creeper";
        }

        if (colorRGB == endermanEgg())
        {
            return "Enderman";
        }

        if (colorRGB == ghastEgg())
        {
            return "Ghast";
        }

        if (colorRGB == horseEgg())
        {
            return "Horse";
        }

        if (colorRGB == magmaCubeEgg())
        {
            return "Magmacube";
        }

        if (colorRGB == mooshroomCowEgg())
        {
            return "Mooshroom Cow";
        }

        if (colorRGB == ocelotEgg())
        {
            return "Ocelot";
        }

        if (colorRGB == pigEgg())
        {
            return "Pig";
        }

        if (colorRGB == sheepEgg())
        {
            return "Sheep";
        }

        if (colorRGB == silverFishEgg())
        {
            return "Silver Fish";
        }

        if (colorRGB == skeletonEgg())
        {
            return "Skeleton";
        }

        if (colorRGB == slimeEgg())
        {
            return "Slime";
        }

        if (colorRGB == spiderEgg())
        {
            return "Spider";
        }

        if (colorRGB == squidEgg())
        {
            return "Squid";
        }

        if (colorRGB == villagerEgg())
        {
            return "Villager";
        }

        if (colorRGB == witchEgg())
        {
            return "Witch";
        }

        if (colorRGB == wolfEgg())
        {
            return "Wolf";
        }

        if (colorRGB == zombieEgg())
        {
            return "Zombie";
        }

        return null;
    }

    public static int batEgg()
    {
        return new Color(71, 58, 45).getRGB();
    }

    public static int blazeEgg()
    {
        return new Color(223, 161, 1).getRGB();
    }

    public static int caveSpiderEgg()
    {
        return new Color(11, 63, 74).getRGB();
    }

    public static int chickenEgg()
    {
        return new Color(153, 153, 153).getRGB();
    }

    public static int cowEgg()
    {
        return new Color(64, 51, 36).getRGB();
    }

    public static int creeperEgg()
    {
        return new Color(12, 151, 10).getRGB();
    }

    public static int endermanEgg()
    {
        return new Color(21, 21, 21).getRGB();
    }

    public static int ghastEgg()
    {
        return new Color(214, 214, 214).getRGB();
    }

    public static int horseEgg()
    {
        return new Color(174, 143, 113).getRGB();
    }

    public static int magmaCubeEgg()
    {
        return new Color(49, 0, 0).getRGB();
    }

    public static int mooshroomCowEgg()
    {
        return new Color(150, 14, 15).getRGB();
    }

    public static int ocelotEgg()
    {
        return new Color(224, 208, 117).getRGB();
    }

    public static int pigEgg()
    {
        return new Color(206, 142, 139).getRGB();
    }

    public static int sheepEgg()
    {
        return new Color(198, 198, 198).getRGB();
    }

    public static int silverFishEgg()
    {
        return new Color(100, 100, 100).getRGB();
    }

    public static int skeletonEgg()
    {
        return new Color(166, 166, 166).getRGB();
    }

    public static int slimeEgg()
    {
        return new Color(73, 145, 56).getRGB();
    }

    public static int spiderEgg()
    {
        return new Color(47, 41, 35).getRGB();
    }

    public static int squidEgg()
    {
        return new Color(31, 53, 70).getRGB();
    }

    public static int villagerEgg()
    {
        return new Color(78, 54, 46).getRGB();
    }

    public static int witchEgg()
    {
        return new Color(47, 0, 0).getRGB();
    }

    public static int wolfEgg()
    {
        return new Color(195, 191, 191).getRGB();
    }

    public static int zombieEgg()
    {
        return new Color(0, 159, 159).getRGB();
    }
}

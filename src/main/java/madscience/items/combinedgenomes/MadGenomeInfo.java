package madscience.items.combinedgenomes;

public class MadGenomeInfo
{
    final short genomeID;
    private final String mobID;
    private final int primaryColor;
    private final int secondaryColor;

    public MadGenomeInfo(short genomeID, String mobID, int primaryColor, int secondaryColor)
    {
        this.genomeID = genomeID;
        this.mobID = mobID;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
    }

    public int getPrimaryColor()
    {
        return primaryColor;
    }

    public int getSecondaryColor()
    {
        return secondaryColor;
    }

    public String getMobID()
    {
        return mobID;
    }
}
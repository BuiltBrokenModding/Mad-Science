package madscience.items;

public class MadGenomeInfo
{
    public final short genomeID;
    final String mobID;
    final int primaryColor;
    final int secondaryColor;

    public MadGenomeInfo(short genomeID, String mobID, int primaryColor, int secondaryColor)
    {
        this.genomeID = genomeID;
        this.mobID = mobID;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
    }
}
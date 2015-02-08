package madscience.content.items.dna;


public class MadGenomeInfo
{

    public final short genomeID;
    public final String mobID;
    public final int primaryColor;
    public final int secondaryColor;

    public MadGenomeInfo(short genomeID, String mobID, int primaryColor, int secondaryColor)
    {
        this.genomeID = genomeID;
        this.mobID = mobID;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
    }
}
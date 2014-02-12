package madscience.items;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.nbt.NBTTagCompound;

public class MadGenomeInfo
{

    public final short genomeID;
    public final String mobID;
    public final String displayName;
    public final int primaryColor;
    public final int secondaryColor;

    public MadGenomeInfo(short genomeID, String mobID, String displayName, int primaryColor, int secondaryColor)
    {
        this.genomeID = genomeID;
        this.mobID = mobID;
        this.displayName = displayName;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
    }

    public MadGenomeInfo(short eggID, String mobID, int primaryColor, int secondaryColor)
    {
        this(eggID, mobID, null, primaryColor, secondaryColor);
    }

}
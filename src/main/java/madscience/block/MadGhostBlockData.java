package madscience.block;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class MadGhostBlockData
{
    @Expose
    @SerializedName("GhostBlockX")
    private int ghostBlocksX;

    @Expose
    @SerializedName("GhostBlockY")
    private int ghostBlocksY;

    @Expose
    @SerializedName("GhostBlockZ")
    private int ghostBlocksZ;

    public MadGhostBlockData(int ghostX, int ghostY, int ghostZ)
    {
        super();

        this.ghostBlocksX = ghostX;
        this.ghostBlocksY = ghostY;
        this.ghostBlocksZ = ghostZ;
    }

    public int getGhostBlocksX()
    {
        return ghostBlocksX;
    }

    public int getGhostBlocksY()
    {
        return ghostBlocksY;
    }

    public int getGhostBlocksZ()
    {
        return ghostBlocksZ;
    }
}
package madscience.factory.block;

import com.google.gson.annotations.Expose;

public class MadGhostBlockData
{
    @Expose
    private int ghostBlocksX;
    
    @Expose
    private int ghostBlocksY;
    
    @Expose
    private int ghostBlocksZ;
    
    public MadGhostBlockData(
            int ghostX,
            int ghostY,
            int ghostZ)
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
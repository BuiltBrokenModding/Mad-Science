package madscience.tileentities.dnaextractor;

import net.minecraftforge.common.ForgeDirection;

enum DNAExtractorEnumGUI
{    
    MutantDNATank(131, 19, 176, 31, 16, 58),
    Power(10, 49, 176, 0, 14, 14),
    Work(32, 31, 176, 14, 31, 17),
    Help(9, 32, 166, 4, 6, 5);
    
    /* Screen coordinates for where data where render. */
    private final int screenX;
    
    /* Screen coordinates for where data where render. */
    private final int screenY;
    
    /* Filler coordinates where image data to render at screen coords can be found. */
    private final int fillerX;
    
    /* Filler coordinates where image data to render at screen coords can be found. */
    private final int fillerY;
    
    /* Total size of area that needs to be rendered. */
    private final int sizeX;
    
    /* Total size of area that needs to be rendered. */
    private final int sizeY;

    private DNAExtractorEnumGUI(int screenX, int screenY, int fillX, int fillY, int sizeX, int sizeY)
    {
        this.screenX = screenX;
        this.screenY = screenY;
        
        this.fillerX = fillX;
        this.fillerY = fillY;
        
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }
    
    public int screenY()
    {
        return this.screenY;
    }
    
    public int screenX()
    {
        return this.screenX;
    }
    
    public int fillerY()
    {
        return this.fillerY;
    }
    
    public int fillerX()
    {
        return this.fillerX;
    }
    
    public int sizeY()
    {
        return this.sizeY;
    }
    
    public int sizeX()
    {
        return this.sizeX;
    }
}

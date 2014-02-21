package madscience.tileentities.soniclocator;

public class SoniclocatorLocationItem
{
    // Defines where the object is in global coordinates.
    public int posX;
    public int posY;
    public int posZ;
    
    public SoniclocatorLocationItem(int x, int y, int z)
    {
        // Floors the numbers and stores them for later lookup.
        posX = x;
        posY = y;
        posZ = z;
    }
}

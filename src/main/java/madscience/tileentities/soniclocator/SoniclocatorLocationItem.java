package madscience.tileentities.soniclocator;

class SoniclocatorLocationItem
{
    // Defines where the object is in global coordinates.
    int posX;
    int posY;
    int posZ;
    
    SoniclocatorLocationItem(int x, int y, int z)
    {
        // Floors the numbers and stores them for later lookup.
        posX = x;
        posY = y;
        posZ = z;
    }
}

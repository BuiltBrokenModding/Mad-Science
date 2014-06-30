package madscience.tileentities.prefab;

import net.minecraftforge.common.ForgeDirection;

public interface MadContainerInterface
{        
    /* Returns the slot number for the given container based on it's order in the list. */
    public abstract int getSlotNumber();

    /* Returns the valid input direction for a given container slot. */
    public abstract ForgeDirection getInputDirection();

    /* Returns true if the given container slot allows items to be inserted into it. */
    public abstract boolean canInsert();

    /* Returns the valid extraction direction for a given container slot. */
    public abstract ForgeDirection getExtractDirection();

    /* Returns true if we are allowed to extract items from the given container slot. */
    public abstract boolean canExtract();
    
    /* Position on texture where slot can be located. */
    public abstract int offsetX();
    
    /* Position on texture where slot can be located. */
    public abstract int offsetY();
    
    /* Size of slot. */
    public abstract int sizeX();
    
    /* Size of slot. */
    public abstract int sizeY();
}
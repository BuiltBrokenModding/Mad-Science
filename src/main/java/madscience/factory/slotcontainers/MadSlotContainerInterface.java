package madscience.factory.slotcontainers;

import net.minecraftforge.common.ForgeDirection;

public interface MadSlotContainerInterface
{
    /* Returns true if we are allowed to extract items from the given container slot. */
    public abstract boolean canExtract();

    /* Returns true if the given container slot allows items to be inserted into it. */
    public abstract boolean canInsert();

    /* Returns the valid extraction direction for a given container slot. */
    public abstract ForgeDirection getExtractDirection();

    /* Returns the valid input direction for a given container slot. */
    public abstract ForgeDirection getInputDirection();

    /* Internal string used to search in localization files. */
    public abstract String getTooltip();

    /* Position on texture where slot can be located. */
    public abstract int offsetX();

    /* Position on texture where slot can be located. */
    public abstract int offsetY();

    /* Size of slot. */
    public abstract int sizeX();

    /* Size of slot. */
    public abstract int sizeY();

    /* Index that this particular container instance will reference. */
    public abstract int slot();
}
package madscience.factory.slotcontainers;

import net.minecraftforge.common.ForgeDirection;

import com.google.gson.annotations.Expose;

public final class MadSlotContainer implements IMadSlotContainer
{
    /* Determines what side this entry can have items extracted from it. */
    @Expose private final ForgeDirection extractSide;

    /* Determines what side this entry can accept items from. */
    @Expose private final ForgeDirection insertSide;

    /* Determines if entry can extract period. */
    @Expose private final boolean allowExtract;

    /* Determines if entry can have items inserted period. */
    @Expose private final boolean allowInput;

    /* Offset of container slot on GUI. */
    @Expose private final int offsetX;

    /* Offset of container slot on GUI. */
    @Expose private final int offsetY;

    /* Size of container slot on GUI. */
    @Expose private final int sizeX;

    /* Size of container slot on GUI. */
    @Expose private final int sizeY;
    
    /** Reference to type of slot this will be input, output, other, etc. */
    @Expose private final MadSlotContainerTypeEnum slotType;
    
    /** Reference to container number used by Minecraft/Forge and our own logic classes. */
    @Expose private final int slotNumber;

    public MadSlotContainer(
            int slotNumber,
            MadSlotContainerTypeEnum slotType,
            ForgeDirection extractSide,
            ForgeDirection insertSide,
            boolean canExtract,
            boolean canInput,
            int slotX,
            int slotY,
            int sizeX,
            int sizeY)
    {
        // Slot number.
        this.slotNumber = slotNumber;
        
        // Determines what type of slot this is: input, output, other, etc.
        this.slotType = slotType;
        
        // Determines which side of the device can input or output to automations.
        this.extractSide = extractSide;
        this.insertSide = insertSide;

        // Determines if this can slot can be extracted from.
        this.allowExtract = canExtract;

        // Determines if this slot can accept input.
        this.allowInput = canInput;

        // Offsets for determining where the container is positioned on GUI.
        this.offsetX = slotX;
        this.offsetY = slotY;

        // Determines to the total size of this container slot.
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    @Override
    public boolean canExtract()
    {
        return this.allowExtract;
    }

    @Override
    public boolean canInsert()
    {
        return this.allowInput;
    }

    @Override
    public ForgeDirection getExtractDirection()
    {
        if (this.extractSide != null)
        {
            return this.extractSide;
        }
        else
        {
            return ForgeDirection.UNKNOWN;
        }
    }

    @Override
    public ForgeDirection getInputDirection()
    {
        if (this.insertSide != null)
        {
            return this.insertSide;
        }
        else
        {
            return ForgeDirection.UNKNOWN;
        }
    }

    @Override
    public int offsetX()
    {
        return this.offsetX;
    }

    @Override
    public int offsetY()
    {
        return this.offsetY;
    }

    @Override
    public int sizeX()
    {
        return this.sizeX;
    }

    @Override
    public int sizeY()
    {
        return this.sizeY;
    }

    @Override
    public int slot()
    {
        return this.slotNumber;
    }

    @Override
    public MadSlotContainerTypeEnum getSlotType()
    {
        return slotType;
    }
}

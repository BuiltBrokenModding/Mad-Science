package madscience.tileentities.dnaextractor;

import madscience.factory.slotcontainers.MadSlotContainerInterface;
import madscience.factory.slotcontainers.MadSlotContainerTypeEnum;
import net.minecraftforge.common.ForgeDirection;

public enum DNAExtractorEnumContainers implements MadSlotContainerInterface
{
    InputGeneticMaterial(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, ForgeDirection.UNKNOWN, ForgeDirection.NORTH, false, true, 9, 32, 18, 18),
    InputEmptyBucket(MadSlotContainerTypeEnum.INPUT_EMPTYBUCKET, ForgeDirection.UNKNOWN, ForgeDirection.EAST, false, true, 152, 61, 18, 18),
    OutputDNASample(MadSlotContainerTypeEnum.OUTPUT_RESULT1, ForgeDirection.SOUTH, ForgeDirection.UNKNOWN, true, false, 72, 32, 18, 18),
    OutputDirtyNeedle(MadSlotContainerTypeEnum.OUTPUT_WASTE, ForgeDirection.UP, ForgeDirection.UNKNOWN, true, false, 105, 32, 18, 18),
    OutputFilledMutantDNABucket(MadSlotContainerTypeEnum.OUTPUT_FILLEDBUCKET, ForgeDirection.WEST, ForgeDirection.UNKNOWN, true, false, 152, 36, 18, 18);

    /* Determines what side this entry can have items extracted from it. */
    private final ForgeDirection extractSide;

    /* Determines what side this entry can accept items from. */
    private final ForgeDirection insertSide;

    /* Determines if entry can extract period. */
    private final boolean allowExtract;

    /* Determines if entry can have items inserted period. */
    private final boolean allowInput;

    /* Offset of container slot on GUI. */
    private final int offsetX;

    /* Offset of container slot on GUI. */
    private final int offsetY;

    /* Size of container slot on GUI. */
    private final int sizeX;

    /* Size of container slot on GUI. */
    private final int sizeY;
    
    /** Reference to type of slot this will be input, output, other, etc. */
    private final MadSlotContainerTypeEnum slotType;

    DNAExtractorEnumContainers(
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
        return this.ordinal();
    }

    @Override
    public MadSlotContainerTypeEnum getSlotType()
    {
        return slotType;
    }
}

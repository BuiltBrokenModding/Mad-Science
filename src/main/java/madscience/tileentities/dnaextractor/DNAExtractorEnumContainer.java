package madscience.tileentities.dnaextractor;

import madscience.tileentities.prefab.MadContainerInterface;
import net.minecraftforge.common.ForgeDirection;

enum DNAExtractorEnumContainer implements MadContainerInterface
{    
    InputGeneticMatrial(ForgeDirection.UNKNOWN, ForgeDirection.NORTH, false, true, 9, 32, 18, 18),
    InputEmptyBucket(ForgeDirection.UNKNOWN, ForgeDirection.EAST, false, true, 152, 61, 18, 18),
    OutputDNASample(ForgeDirection.SOUTH, ForgeDirection.UNKNOWN, true, false, 72, 32, 18, 18),
    OutputDirtyNeedle(ForgeDirection.UP, ForgeDirection.UNKNOWN, true, false, 105, 32, 18, 18),
    OutputFilledMutantDNABucket(ForgeDirection.WEST, ForgeDirection.UNKNOWN, true, false, 152, 36, 18, 18);

    private final ForgeDirection extractSide;
    private final ForgeDirection insertSide;
    private final boolean allowExtract;
    private final boolean allowInput;
    private final int offsetX;
    private final int offsetY;
    private final int sizeX;
    private final int sizeY;

    private DNAExtractorEnumContainer(
            ForgeDirection extractSide,
            ForgeDirection insertSide,
            boolean canExtract,
            boolean canInput,
            int slotX, int slotY,
            int sizeX, int sizeY)
    {        
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
    public int getSlotNumber()
    {
        return this.ordinal();
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
    public boolean canExtract()
    {
        return this.allowExtract;
    }


}

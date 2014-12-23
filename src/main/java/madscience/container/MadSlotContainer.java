package madscience.container;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import net.minecraftforge.common.ForgeDirection;


public final class MadSlotContainer
{
    /* Determines what side this entry can have items extracted from it. */
    @Expose
    @SerializedName("ExtractSide")
    private final ForgeDirection extractSide;

    /* Determines what side this entry can accept items from. */
    @Expose
    @SerializedName("InsertSide")
    private final ForgeDirection insertSide;

    /* Determines if entry can extract period. */
    @Expose
    @SerializedName("AllowExtract")
    private final boolean allowExtract;

    /* Determines if entry can have items inserted period. */
    @Expose
    @SerializedName("AllowInput")
    private final boolean allowInput;

    /* Offset of container slot on GUI. */
    @Expose
    @SerializedName("OffsetX")
    private final int offsetX;

    /* Offset of container slot on GUI. */
    @Expose
    @SerializedName("OffsetY")
    private final int offsetY;

    /* Size of container slot on GUI. */
    @Expose
    @SerializedName("SizeX")
    private final int sizeX;

    /* Size of container slot on GUI. */
    @Expose
    @SerializedName("SizeY")
    private final int sizeY;

    /**
     * Reference to type of slot this will be input, output, other, etc.
     */
    @Expose
    @SerializedName("SlotType")
    private final MadSlotContainerTypeEnum slotType;

    /**
     * Reference to container number used by Minecraft/Forge and our own logic classes.
     */
    @Expose
    @SerializedName("SlotNumber")
    private final int slotNumber;

    public MadSlotContainer( // NO_UCD (unused code)
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

    public boolean canExtract()
    {
        return this.allowExtract;
    }

    public boolean canInsert()
    {
        return this.allowInput;
    }

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

    public int offsetX()
    {
        return this.offsetX;
    }

    public int offsetY()
    {
        return this.offsetY;
    }

    public int sizeX()
    {
        return this.sizeX;
    }

    public int sizeY()
    {
        return this.sizeY;
    }

    public int slot()
    {
        return this.slotNumber;
    }

    public MadSlotContainerTypeEnum getSlotType()
    {
        return slotType;
    }
}

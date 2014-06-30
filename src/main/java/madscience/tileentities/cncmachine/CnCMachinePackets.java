package madscience.tileentities.cncmachine;

import madscience.network.MadPackets;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;

public class CnCMachinePackets extends MadPackets
{
    // Decoded book contents.
    private String decodedBookContents;

    // Tile entity in the world.
    private CnCMachineEntity ENTITY;

    // Determines if there is an iron block in our input slot.
    private boolean hasIronBlock;

    // Cook time.
    private int lastItemCookTimeMaximum;
    private int lastItemCookTimeValue;

    // Energy.
    private long lastItemStoredEnergy;
    private long lastItemStoredEnergyMaximum;

    // Water level.
    private int lastWaterLevel;
    private int lastWaterMaximum;

    // Texture that will displayed on the model.
    private String TEXTURE;

    // XYZ coordinates of tile entity source.
    private int tilePosX;
    private int tilePosY;
    private int tilePosZ;
    
    // Sound tracking variables that ensure clients will only hear a given sound once.
    private boolean clientSound_FinishedCrushing;
    private boolean clientSound_InsertIronBlock;
    private boolean clientSound_InvalidBook;
    private boolean clientSound_PowerOn;
    private boolean clientSound_PressStop;

    public CnCMachinePackets()
    {
        // Required for reflection.
    }

    CnCMachinePackets(int posX, int posY, int posZ,
            int cookTime, int cookTimeMax,
            long energyStored, long energyMax,
            int waterLevel, int waterLevelMaximum,
            String bookContents,
            String currentTexture,
            boolean ironBlockInputted,
            boolean soundFinishCrushing,
            boolean soundInsertIronBlock,
            boolean soundInvalidBook,
            boolean soundPowerOn,
            boolean soundPressStop)
    {
        // World position information.
        tilePosX = posX;
        tilePosY = posY;
        tilePosZ = posZ;

        // Cook time.
        lastItemCookTimeValue = cookTime;
        lastItemCookTimeMaximum = cookTimeMax;

        // Energy.
        lastItemStoredEnergy = energyStored;
        lastItemStoredEnergyMaximum = energyMax;

        // Water level.
        lastWaterLevel = waterLevel;
        lastWaterMaximum = waterLevelMaximum;

        // Last known texture.
        decodedBookContents = bookContents;

        // Texture.
        TEXTURE = currentTexture;

        // Has the player inserted an iron block.
        hasIronBlock = ironBlockInputted;
        
        // Sound tracking.
        clientSound_FinishedCrushing = soundFinishCrushing;
        clientSound_InsertIronBlock = soundInsertIronBlock;
        clientSound_InvalidBook = soundInvalidBook;
        clientSound_PowerOn = soundPowerOn;
        clientSound_PressStop = soundPressStop;
    }

    @Override
    public void execute(EntityPlayer player, Side side) throws ProtocolException
    {
        // ------
        // CLIENT
        // ------

        // Packet received by client, executing payload.
        if (side.isClient())
        {
            ENTITY = (CnCMachineEntity) player.worldObj.getBlockTileEntity(tilePosX, tilePosY, tilePosZ);
            if (ENTITY == null)
                return;

            // Cook time.
            this.ENTITY.currentItemCookingValue = lastItemCookTimeValue;
            this.ENTITY.currentItemCookingMaximum = lastItemCookTimeMaximum;

            // Energy.
            this.ENTITY.setEnergy(ForgeDirection.UNKNOWN, lastItemStoredEnergy);
            this.ENTITY.setEnergyCapacity(lastItemStoredEnergyMaximum);

            // Water level.
            this.ENTITY.WATER_TANK.setFluid(new FluidStack(FluidRegistry.WATER, lastWaterLevel));
            this.ENTITY.WATER_TANK.setCapacity(lastWaterMaximum);

            // Book contents for client GUI's.
            this.ENTITY.BOOK_DECODED = decodedBookContents;

            // Texture.
            this.ENTITY.TEXTURE = TEXTURE;

            // Iron block inputed flag.
            this.ENTITY.hasIronBlock = hasIronBlock;
            
            // Sound tracking.
            this.ENTITY.clientSound_FinishedCrushing = clientSound_FinishedCrushing;
            this.ENTITY.clientSound_InsertIronBlock = clientSound_InsertIronBlock;
            this.ENTITY.clientSound_InvalidBook = clientSound_InvalidBook;
            this.ENTITY.clientSound_PowerOn = clientSound_PowerOn;
            this.ENTITY.clientSound_PressStop = clientSound_PressStop;
        }
        else
        {
            throw new ProtocolException("Cannot send this packet to the server!");
        }
    }

    @Override
    public void read(ByteArrayDataInput in) throws ProtocolException
    {
        // ------
        // CLIENT
        // ------

        // World coordinate information.
        this.tilePosX = in.readInt();
        this.tilePosY = in.readInt();
        this.tilePosZ = in.readInt();

        // Cook time.
        lastItemCookTimeValue = in.readInt();
        lastItemCookTimeMaximum = in.readInt();

        // Energy.
        lastItemStoredEnergy = in.readLong();
        lastItemStoredEnergyMaximum = in.readLong();

        // Water level.
        lastWaterLevel = in.readInt();
        lastWaterMaximum = in.readInt();

        // Book contents.
        decodedBookContents = in.readUTF();

        // Texture.
        TEXTURE = in.readUTF();

        // Iron block inputed flag.
        hasIronBlock = in.readBoolean();
        
        // Sound tracking.
        clientSound_FinishedCrushing = in.readBoolean();
        clientSound_InsertIronBlock = in.readBoolean();
        clientSound_InvalidBook = in.readBoolean();
        clientSound_PowerOn = in.readBoolean();
        clientSound_PressStop = in.readBoolean();
    }

    @Override
    public void write(ByteArrayDataOutput out)
    {
        // ------
        // SERVER
        // ------

        // World coordinate information.
        out.writeInt(tilePosX);
        out.writeInt(tilePosY);
        out.writeInt(tilePosZ);

        // Cook time.
        out.writeInt(lastItemCookTimeValue);
        out.writeInt(lastItemCookTimeMaximum);

        // Energy.
        out.writeLong(lastItemStoredEnergy);
        out.writeLong(lastItemStoredEnergyMaximum);

        // Water level.
        out.writeInt(lastWaterLevel);
        out.writeInt(lastWaterMaximum);

        // Book contents.
        out.writeUTF(decodedBookContents);

        // Texture.
        out.writeUTF(TEXTURE);

        // Iron block inputed flag.
        out.writeBoolean(hasIronBlock);
        
        // Sound tracking.
        out.writeBoolean(clientSound_FinishedCrushing);
        out.writeBoolean(clientSound_InsertIronBlock);
        out.writeBoolean(clientSound_InvalidBook);
        out.writeBoolean(clientSound_PowerOn);
        out.writeBoolean(clientSound_PressStop);
    }
}
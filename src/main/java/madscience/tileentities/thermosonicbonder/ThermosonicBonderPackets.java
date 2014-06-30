package madscience.tileentities.thermosonicbonder;

import madscience.network.MadPackets;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.ForgeDirection;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;

public class ThermosonicBonderPackets extends MadPackets
{
    // XYZ coordinates of tile entity source.
    private int tilePosX;
    private int tilePosY;
    private int tilePosZ;

    // Tile entity this packet is intended for.
    private ThermosonicBonderEntity thermosonicBonderTileEntity;

    // Cook time.
    private int lastItemCookTimeValue;
    private int lastItemCookTimeMaximum;

    // Energy.
    private long lastItemStoredEnergy;
    private long lastItemStoredEnergyMaximum;

    // Heat.
    private int lastHeatValue;
    private int lastHeatMaximum;

    // Texture.
    private String lastTexture;

    public ThermosonicBonderPackets()
    {
        // Required for reflection.
    }

    ThermosonicBonderPackets(int posX, int posY, int posZ, int cookTime, int cookTimeMax, long energyStored, long energyMax, int heatValue, int heatMax, String tileTexture)
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

        // Heat.
        lastHeatValue = heatValue;
        lastHeatMaximum = heatMax;

        // Texture.
        lastTexture = tileTexture;
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
            thermosonicBonderTileEntity = (ThermosonicBonderEntity) player.worldObj.getBlockTileEntity(tilePosX, tilePosY, tilePosZ);
            if (thermosonicBonderTileEntity == null)
                return;

            // Cook time.
            this.thermosonicBonderTileEntity.currentItemCookingValue = lastItemCookTimeValue;
            this.thermosonicBonderTileEntity.currentItemCookingMaximum = lastItemCookTimeMaximum;

            // Energy.
            this.thermosonicBonderTileEntity.setEnergy(ForgeDirection.UNKNOWN, lastItemStoredEnergy);
            this.thermosonicBonderTileEntity.setEnergyCapacity(lastItemStoredEnergyMaximum);

            // Heat.
            this.thermosonicBonderTileEntity.currentHeatValue = lastHeatValue;
            this.thermosonicBonderTileEntity.currentHeatMaximum = lastHeatMaximum;

            // Texture.
            this.thermosonicBonderTileEntity.TEXTURE = lastTexture;
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
        this.lastItemCookTimeValue = in.readInt();
        this.lastItemCookTimeMaximum = in.readInt();

        // Energy.
        this.lastItemStoredEnergy = in.readLong();
        this.lastItemStoredEnergyMaximum = in.readLong();

        // Heat.
        this.lastHeatValue = in.readInt();
        this.lastHeatMaximum = in.readInt();

        // Texture.
        this.lastTexture = in.readUTF();
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

        // Heat.
        out.writeInt(lastHeatValue);
        out.writeInt(lastHeatMaximum);

        // Texture.
        out.writeUTF(lastTexture);
    }
}
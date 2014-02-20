package madscience.tileentities.soniclocator;

import madscience.network.MadPackets;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.ForgeDirection;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;

public class SoniclocatorPackets extends MadPackets
{
    // Stores last known amount of heat this block was known to have.
    private int lastHeatValue;
    private int lastHeatValueMaximum;
    private long lastItemEnergyMaximum;

    // Energy
    private long lastItemStoredEnergy;

    // Stores last known number of targets and total thumps performed.
    private long lastKnownNumberOfTargets;
    private long lastKnownNumberOfTotalThumps;

    // Texture to display.
    private String lastTexture;
    // Tile entity from the world.
    private SoniclocatorEntity soniclocatorTileEntity;

    // XYZ coordinates of tile entity source.
    private int tilePosX;
    private int tilePosY;

    private int tilePosZ;

    public SoniclocatorPackets()
    {
        // Required for reflection.
    }

    public SoniclocatorPackets(int posX, int posY, int posZ, long energyStored, long energyMax, int heatValue, int heatMax, long totalTargets, long totalThumps, String tileTexture)
    {
        // World position information.
        tilePosX = posX;
        tilePosY = posY;
        tilePosZ = posZ;

        // Energy
        lastItemStoredEnergy = energyStored;
        lastItemEnergyMaximum = energyMax;

        // Stores last known amount of heat this block was known to have.
        lastHeatValue = heatValue;
        lastHeatValueMaximum = heatMax;

        // Stores last known amount of targets and thumps this machine has performed.
        lastKnownNumberOfTargets = totalTargets;
        lastKnownNumberOfTotalThumps = totalThumps;

        // Texture to display.
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
            soniclocatorTileEntity = (SoniclocatorEntity) player.worldObj.getBlockTileEntity(tilePosX, tilePosY, tilePosZ);
            if (soniclocatorTileEntity == null)
                return;

            // Energy.
            this.soniclocatorTileEntity.setEnergy(ForgeDirection.UNKNOWN, lastItemStoredEnergy);
            this.soniclocatorTileEntity.setEnergyCapacity(lastItemEnergyMaximum);

            // Heat level.
            this.soniclocatorTileEntity.currentHeatValue = lastHeatValue;
            this.soniclocatorTileEntity.currentHeatMaximum = lastHeatValueMaximum;

            // Machine Statistics.
            this.soniclocatorTileEntity.lastKnownNumberOfTargets = this.lastKnownNumberOfTargets;
            this.soniclocatorTileEntity.lastKnownNumberOfTotalThumps = this.lastKnownNumberOfTotalThumps;

            // Current texture.
            this.soniclocatorTileEntity.soniclocatorTexture = lastTexture;
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

        // Energy
        lastItemStoredEnergy = in.readLong();
        lastItemEnergyMaximum = in.readLong();

        // Stores last known amount of heat this block was known to have.
        lastHeatValue = in.readInt();
        lastHeatValueMaximum = in.readInt();

        // Stores last known number of targets and number of thumps.
        lastKnownNumberOfTargets = in.readLong();
        lastKnownNumberOfTotalThumps = in.readLong();

        // Texture to display.
        lastTexture = in.readUTF();
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

        // Energy
        out.writeLong(lastItemStoredEnergy);
        out.writeLong(lastItemEnergyMaximum);

        // Stores last known amount of heat this block was known to have.
        out.writeInt(lastHeatValue);
        out.writeInt(lastHeatValueMaximum);

        // Stores last known number of targets and number of thumps.
        out.writeLong(lastKnownNumberOfTargets);
        out.writeLong(lastKnownNumberOfTotalThumps);

        // Texture to display.
        out.writeUTF(lastTexture);
    }
}
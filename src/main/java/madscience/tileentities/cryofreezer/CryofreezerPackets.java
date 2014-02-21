package madscience.tileentities.cryofreezer;

import madscience.network.MadPackets;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.ForgeDirection;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;

public class CryofreezerPackets extends MadPackets
{
    // XYZ coordinates of tile entity source.
    private int tilePosX;
    private int tilePosY;
    private int tilePosZ;

    // Tile entity reference upon execute.
    private CryofreezerEntity cryoFreezerTileEntity;

    // Last known current cook time.
    private int lastItemCookTimeValue;

    // Last known maximum cook time.
    private int lastItemCookTimeMaximum;

    // Last known amount of energy stored.
    private long lastItemStoredEnergy;

    // Last known maximum amount of energy.
    private long lastItemStoredMaxEnergy;

    // Last known texture displayed on the tile entity.
    private String cryofreezerTexture;

    public CryofreezerPackets()
    {
        // Required for reflection.
    }

    public CryofreezerPackets(int posX, int posY, int posZ, int cookTime, int cookTimeMax, long energyStored, long energyMax, String tileTexture)
    {
        // World position information.
        tilePosX = posX;
        tilePosY = posY;
        tilePosZ = posZ;

        // Tile entity specific.
        lastItemCookTimeValue = cookTime;
        lastItemCookTimeMaximum = cookTimeMax;
        lastItemStoredMaxEnergy = energyMax;
        lastItemStoredEnergy = energyStored;
        cryofreezerTexture = tileTexture;
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
            cryoFreezerTileEntity = (CryofreezerEntity) player.worldObj.getBlockTileEntity(tilePosX, tilePosY, tilePosZ);
            if (cryoFreezerTileEntity == null)
                return;

            this.cryoFreezerTileEntity.currentItemCookingValue = lastItemCookTimeValue;
            this.cryoFreezerTileEntity.currentItemCookingMaximum = lastItemCookTimeMaximum;
            this.cryoFreezerTileEntity.setEnergyCapacity(lastItemStoredMaxEnergy);
            this.cryoFreezerTileEntity.setEnergy(ForgeDirection.UNKNOWN, lastItemStoredEnergy);
            this.cryoFreezerTileEntity.cryofreezerTexture = cryofreezerTexture;
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

        // Packet being read by client, verifying integrity.
        this.lastItemCookTimeValue = in.readInt();
        this.lastItemCookTimeMaximum = in.readInt();
        this.lastItemStoredMaxEnergy = in.readLong();
        this.lastItemStoredEnergy = in.readLong();
        this.cryofreezerTexture = in.readUTF();
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

        // Packet being written by server.
        out.writeInt(lastItemCookTimeValue);
        out.writeInt(lastItemCookTimeMaximum);
        out.writeLong(lastItemStoredMaxEnergy);
        out.writeLong(lastItemStoredEnergy);
        out.writeUTF(cryofreezerTexture);
    }
}
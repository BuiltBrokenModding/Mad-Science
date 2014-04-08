package madscience.tileentities.dataduplicator;

import madscience.network.MadPackets;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.ForgeDirection;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;

public class DataDuplicatorPackets extends MadPackets
{
    // XYZ coordinates of tile entity source.
    private int tilePosX;
    private int tilePosY;
    private int tilePosZ;

    // Tile entity that we will reference from the world.
    private DataDuplicatorEntity dataDuplicatorTileEntity;

    // Cook time.
    private int lastCookTime;
    private int lastCookTimeMaximum;

    // Energy.
    private long lastEnergy;
    private long lastEnergyMaximum;

    // Texture.
    private String lastTexture;

    public DataDuplicatorPackets()
    {
        // Required for reflection.
    }

    public DataDuplicatorPackets(int posX, int posY, int posZ, int cookTime, int cookTimeMax, long energyStored, long energyMax, String tileTexture)
    {
        // World position information.
        tilePosX = posX;
        tilePosY = posY;
        tilePosZ = posZ;

        // Cook time.
        lastCookTime = cookTime;
        lastCookTimeMaximum = cookTimeMax;

        // Energy.
        lastEnergy = energyStored;
        lastEnergyMaximum = energyMax;

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
            dataDuplicatorTileEntity = (DataDuplicatorEntity) player.worldObj.getBlockTileEntity(tilePosX, tilePosY, tilePosZ);
            if (dataDuplicatorTileEntity == null)
                return;

            // Cook time.
            this.dataDuplicatorTileEntity.currentItemCookingValue = lastCookTime;
            this.dataDuplicatorTileEntity.currentItemCookingMaximum = lastCookTimeMaximum;

            // Energy.
            this.dataDuplicatorTileEntity.setEnergy(ForgeDirection.UNKNOWN, lastEnergy);
            this.dataDuplicatorTileEntity.setEnergyCapacity(lastEnergyMaximum);

            // Texture.
            this.dataDuplicatorTileEntity.TEXTURE = lastTexture;
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
        lastCookTime = in.readInt();
        lastCookTimeMaximum = in.readInt();

        // Energy.
        lastEnergy = in.readLong();
        lastEnergyMaximum = in.readLong();

        // Texture.
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

        // Cook time.
        out.writeInt(lastCookTime);
        out.writeInt(lastCookTimeMaximum);

        // Energy.
        out.writeLong(lastEnergy);
        out.writeLong(lastEnergyMaximum);

        // Texture.
        out.writeUTF(lastTexture);
    }
}
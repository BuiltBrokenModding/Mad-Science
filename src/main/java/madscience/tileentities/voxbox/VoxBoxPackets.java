package madscience.tileentities.voxbox;

import madscience.network.MadPackets;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.ForgeDirection;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;

public class VoxBoxPackets extends MadPackets
{
    // Tile entity that we will reference from the world.
    private VoxBoxEntity entity;
    // Energy.
    private long lastEnergy;
    private long lastEnergyMaximum;

    // Texture.
    private String lastTexture;

    // XYZ coordinates of tile entity source.
    private int tilePosX;
    private int tilePosY;

    private int tilePosZ;

    public VoxBoxPackets()
    {
        // Required for reflection.
    }

    public VoxBoxPackets(int posX, int posY, int posZ, long energyStored, long energyMax, String tileTexture)
    {
        // World position information.
        tilePosX = posX;
        tilePosY = posY;
        tilePosZ = posZ;

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
            entity = (VoxBoxEntity) player.worldObj.getBlockTileEntity(tilePosX, tilePosY, tilePosZ);
            if (entity == null)
                return;

            // Energy.
            this.entity.setEnergy(ForgeDirection.UNKNOWN, lastEnergy);
            this.entity.setEnergyCapacity(lastEnergyMaximum);

            // Texture.
            this.entity.TEXTURE = lastTexture;
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

        // Energy.
        out.writeLong(lastEnergy);
        out.writeLong(lastEnergyMaximum);

        // Texture.
        out.writeUTF(lastTexture);
    }
}
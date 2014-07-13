package madscience.factory.tileentity;

import madscience.network.MadPackets;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;

public class MadTileEntityPacketTemplate extends MadPackets
{
    // XYZ coordinates of tile entity source.
    private int tilePosX;
    private int tilePosY;
    private int tilePosZ;

    // Tile entity from the world.
    private MadTileEntityPrefab ENTITY;

    // Stores intermediate amount of time item has cooked out of total.
    private int lastCookTimeValue;
    private int lastCookTimeMaximum;

    // Stores last known amount of energy this block was known to have.
    private long lastEnergy;
    private long lastEnergyMaximum;

    // Amount of stored mutant DNA.
    private int lastFluidLevel;
    private int lastFluidMaximum;

    // Last displayed texture.
    private String lastTexture;

    public MadTileEntityPacketTemplate()
    {
        // Required for reflection.
    }

    public MadTileEntityPacketTemplate(int posX, int posY, int posZ, int cookTime, int cookTimeMax, long energyStored, long energyMax, int mutantDNALevel, int mutantDNAMaximum, String tileTexture) // NO_UCD (use default)
    {
        // World position information.
        tilePosX = posX;
        tilePosY = posY;
        tilePosZ = posZ;

        // Stores intermediate amount of time item has cooked out of total.
        lastCookTimeValue = cookTime;
        lastCookTimeMaximum = cookTimeMax;

        // Stores last known amount of energy this block was known to have.
        lastEnergy = energyStored;
        lastEnergyMaximum = energyMax;

        // Amount of stored mutant DNA.
        lastFluidLevel = mutantDNALevel;
        lastFluidMaximum = mutantDNAMaximum;

        // Last displayed texture.
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
            // Grab our wanted entity from the game world.
            TileEntity possibleTileEntity = player.worldObj.getBlockTileEntity(tilePosX, tilePosY, tilePosZ);
            if (possibleTileEntity instanceof MadTileEntityPrefab)
            {
                ENTITY = (MadTileEntityPrefab) possibleTileEntity;
            }

            // Null check.
            if (ENTITY == null)
                return;

            // Cook time.
            this.ENTITY.setProgressValue(lastCookTimeValue);
            this.ENTITY.setProgressMaximum(lastCookTimeMaximum);

            // Energy.
            this.ENTITY.setEnergy(ForgeDirection.UNKNOWN, lastEnergy);
            this.ENTITY.setEnergyCapacity(lastEnergyMaximum);

            // Fluid amount.
            this.ENTITY.setFluidAmount(lastFluidLevel);
            this.ENTITY.setFluidCapacity(lastFluidMaximum);

            // Tile entity texture.
            this.ENTITY.setEntityTexture(lastTexture);
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

        // Stores intermediate amount of time item has cooked out of total.
        this.lastCookTimeValue = in.readInt();
        this.lastCookTimeMaximum = in.readInt();

        // Stores last known amount of energy this block was known to have.
        this.lastEnergy = in.readLong();
        this.lastEnergyMaximum = in.readLong();

        // Amount of stored mutant DNA.
        this.lastFluidLevel = in.readInt();
        this.lastFluidMaximum = in.readInt();

        // Last displayed texture.
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

        // Stores intermediate amount of time item has cooked out of total.
        out.writeInt(lastCookTimeValue);
        out.writeInt(lastCookTimeMaximum);

        // Stores last known amount of energy this block was known to have.
        out.writeLong(lastEnergy);
        out.writeLong(lastEnergyMaximum);

        // Amount of stored mutant DNA.
        out.writeInt(lastFluidLevel);
        out.writeInt(lastFluidMaximum);

        // Last displayed texture.
        out.writeUTF(lastTexture);
    }
}
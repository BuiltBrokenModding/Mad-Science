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
    private MadTileEntity ENTITY;

    // Stores intermediate amount of time item has cooked out of total.
    private int lastItemCookTimeValue;
    private int lastItemCookTimeMaximum;

    // Stores last known amount of energy this block was known to have.
    private long lastItemStoredEnergy;
    private long lastItemStoredEnergyMaximum;

    // Amount of stored mutant DNA.
    private int lastLiquidDNAMutantLevel;
    private int lastLiquidDNAMutantMaximum;

    // Last displayed texture.
    private String lastTexture;

    public MadTileEntityPacketTemplate()
    {
        // Required for reflection.
    }

    public MadTileEntityPacketTemplate(int posX, int posY, int posZ, int cookTime, int cookTimeMax, long energyStored, long energyMax, int mutantDNALevel, int mutantDNAMaximum, String tileTexture)
    {
        // World position information.
        tilePosX = posX;
        tilePosY = posY;
        tilePosZ = posZ;

        // Stores intermediate amount of time item has cooked out of total.
        lastItemCookTimeValue = cookTime;
        lastItemCookTimeMaximum = cookTimeMax;

        // Stores last known amount of energy this block was known to have.
        lastItemStoredEnergy = energyStored;
        lastItemStoredEnergyMaximum = energyMax;

        // Amount of stored mutant DNA.
        lastLiquidDNAMutantLevel = mutantDNALevel;
        lastLiquidDNAMutantMaximum = mutantDNAMaximum;

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
            if (possibleTileEntity instanceof MadTileEntity)
            {
                ENTITY = (MadTileEntity) possibleTileEntity;
            }

            // Null check.
            if (ENTITY == null)
                return;

            // Cook time.
            this.ENTITY.setProgressValue(lastItemCookTimeValue);
            this.ENTITY.setProgressMaximum(lastItemCookTimeMaximum);

            // Energy.
            this.ENTITY.setEnergy(ForgeDirection.UNKNOWN, lastItemStoredEnergy);
            this.ENTITY.setEnergyCapacity(lastItemStoredEnergyMaximum);

            // Fluid amount.
            this.ENTITY.setFluidAmount(lastLiquidDNAMutantLevel);
            this.ENTITY.setFluidCapacity(lastLiquidDNAMutantMaximum);

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
        this.lastItemCookTimeValue = in.readInt();
        this.lastItemCookTimeMaximum = in.readInt();

        // Stores last known amount of energy this block was known to have.
        this.lastItemStoredEnergy = in.readLong();
        this.lastItemStoredEnergyMaximum = in.readLong();

        // Amount of stored mutant DNA.
        this.lastLiquidDNAMutantLevel = in.readInt();
        this.lastLiquidDNAMutantMaximum = in.readInt();

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
        out.writeInt(lastItemCookTimeValue);
        out.writeInt(lastItemCookTimeMaximum);

        // Stores last known amount of energy this block was known to have.
        out.writeLong(lastItemStoredEnergy);
        out.writeLong(lastItemStoredEnergyMaximum);

        // Amount of stored mutant DNA.
        out.writeInt(lastLiquidDNAMutantLevel);
        out.writeInt(lastLiquidDNAMutantMaximum);

        // Last displayed texture.
        out.writeUTF(lastTexture);
    }
}
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
    // XYZ coordinates of tile entity source.
    private int tilePosX;
    private int tilePosY;
    private int tilePosZ;

    // Tile entity in the world.
    private CnCMachineEntity ENTITY;

    // Cook time.
    private int lastItemCookTimeValue;
    private int lastItemCookTimeMaximum;

    // Energy.
    private long lastItemStoredEnergy;
    private long lastItemStoredEnergyMaximum;

    // Water level.
    private int lastWaterLevel;
    private int lastWaterMaximum;

    // Last known texture.
    private String lastTexture;

    public CnCMachinePackets()
    {
        // Required for reflection.
    }

    public CnCMachinePackets(int posX, int posY, int posZ, int cookTime, int cookTimeMax, long energyStored, long energyMax, int waterLevel, int waterLevelMaximum, String tileTexture)
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

            // Last known texture.
            this.ENTITY.TEXTURE = lastTexture;
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

        // Last known texture.
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
        out.writeInt(lastItemCookTimeValue);
        out.writeInt(lastItemCookTimeMaximum);

        // Energy.
        out.writeLong(lastItemStoredEnergy);
        out.writeLong(lastItemStoredEnergyMaximum);

        // Water level.
        out.writeInt(lastWaterLevel);
        out.writeInt(lastWaterMaximum);

        // Last known texture.
        out.writeUTF(lastTexture);
    }
}
package madscience.tileentities.mainframe;

import madscience.network.MadPackets;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;

public class MainframePackets extends MadPackets
{
    // XYZ coordinates of tile entity source.
    private int tilePosX;
    private int tilePosY;
    private int tilePosZ;

    // Tile entity in the world.
    private MainframeEntity mainframeTileEntity;

    // Cook time.
    private int lastItemCookTimeValue;
    private int lastItemCookTimeMaximum;

    // Energy.
    private long lastItemStoredEnergy;
    private long lastItemStoredEnergyMaximum;

    // Water level.
    private int lastWaterLevel;
    private int lastWaterLevelMaximum;

    // Heat level.
    private int lastHeatValue;
    private int lastHeatMaximum;

    // Last known texture for mainframe.
    private String lastTexture;

    public MainframePackets()
    {
        // Required for reflection.
    }

    MainframePackets(int posX, int posY, int posZ, int cookTime, int cookTimeMax, long energyStored, long energyMax, int waterLevel, int waterLevelMax, int heatLevel, int heatMax, String tileTexture)
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
        lastWaterLevelMaximum = waterLevelMax;

        // Heat level.
        lastHeatValue = heatLevel;
        lastHeatMaximum = heatMax;

        // Last known texture for mainframe.
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
            mainframeTileEntity = (MainframeEntity) player.worldObj.getBlockTileEntity(tilePosX, tilePosY, tilePosZ);
            if (mainframeTileEntity == null)
                return;

            // Cook time.
            this.mainframeTileEntity.currentItemCookingValue = lastItemCookTimeValue;
            this.mainframeTileEntity.currentItemCookingMaximum = lastItemCookTimeMaximum;

            // Energy.
            this.mainframeTileEntity.setEnergy(ForgeDirection.UNKNOWN, lastItemStoredEnergy);
            this.mainframeTileEntity.setEnergyCapacity(lastItemStoredEnergyMaximum);

            // Water level.
            this.mainframeTileEntity.internalWaterTank.setFluid(new FluidStack(FluidRegistry.WATER, lastWaterLevel));
            this.mainframeTileEntity.internalWaterTank.setCapacity(lastWaterLevelMaximum);

            // Heat level.
            this.mainframeTileEntity.currentHeatValue = lastHeatValue;
            this.mainframeTileEntity.currentHeatMaximum = lastHeatMaximum;

            // Last known texture for mainframe.
            this.mainframeTileEntity.mainframeTexturePath = lastTexture;
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

        // Water level.
        this.lastWaterLevel = in.readInt();
        this.lastWaterLevelMaximum = in.readInt();

        // Heat level.
        this.lastHeatValue = in.readInt();
        this.lastHeatMaximum = in.readInt();

        // Last known texture for mainframe.
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

        // Water level.
        out.writeInt(lastWaterLevel);
        out.writeInt(lastWaterLevelMaximum);

        // Heat level.
        out.writeInt(lastHeatValue);
        out.writeInt(lastHeatMaximum);

        // Last known texture for mainframe.
        out.writeUTF(lastTexture);
    }
}
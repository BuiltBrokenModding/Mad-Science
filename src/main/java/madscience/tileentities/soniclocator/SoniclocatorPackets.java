package madscience.tileentities.soniclocator;

import madscience.network.MadPackets;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.ForgeDirection;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;

public class SoniclocatorPackets extends MadPackets
{
    // XYZ coordinates of tile entity source.
    private int tilePosX;
    private int tilePosY;
    private int tilePosZ;
   
    // Tile entity from the world.
    private SoniclocatorEntity soniclocatorTileEntity;

    // Energy
    private long lastItemStoredEnergy;
    private long lastItemEnergyMaximum;

    // Stores last known amount of heat this block was known to have.
    private int lastHeatValue;
    private int lastHeatValueMaximum;
    
    // Texture to display.
    private String lastTexture;

    public SoniclocatorPackets(int posX, int posY, int posZ,
            long energyStored, long energyMax,
            int heatValue, int heatMax,
            String tileTexture)
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
        
        // Texture to display.
        lastTexture = tileTexture;
    }
    
    public SoniclocatorPackets()
    {
        // Required for reflection.
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
        
        // Texture to display.
        out.writeUTF(lastTexture);
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
        lastItemEnergyMaximum  = in.readLong();

        // Stores last known amount of heat this block was known to have.
        lastHeatValue = in.readInt();
        lastHeatValueMaximum = in.readInt();
        
        // Texture to display.
        lastTexture = in.readUTF();
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
            if (soniclocatorTileEntity == null) return;

            // Energy.
            this.soniclocatorTileEntity.setEnergy(ForgeDirection.UNKNOWN, lastItemStoredEnergy);
            this.soniclocatorTileEntity.setEnergyCapacity(lastItemEnergyMaximum);

            // Heat level.
            this.soniclocatorTileEntity.currentHeatValue = lastHeatValue;
            this.soniclocatorTileEntity.currentHeatMaximum = lastHeatValueMaximum;
            
            // Current texture.
            this.soniclocatorTileEntity.soniclocatorTexture = lastTexture;
        }
        else
        {
            throw new ProtocolException("Cannot send this packet to the server!");
        }
    }
}
package madscience.tileentities.sequencer;

import madscience.MadScience;
import madscience.network.MadPackets;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.ForgeDirection;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;

public class SequencerPackets extends MadPackets
{
    // XYZ coordinates of tile entity source.
    private int tilePosX;
    private int tilePosY;
    private int tilePosZ;
   
    // Tile entity this packet is for.
    private SequencerEntity sequencerTileEntity;

    // Cook time.
    private int lastItemCookTimeValue;
    private int lastItemCookTimeMaximum;

    // Energy.
    private long lastItemStoredEnergy;
    private long lastItemStoredEnergyMaximum;
    
    // Texture we should be displaying.
    private String lastTexture;

    public SequencerPackets(int posX, int posY, int posZ,
            int cookTime, int cookTimeMax,
            long energyStored, long energyMax,
            String tileTexture)
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
        
        // Texture we should be displaying.
        lastTexture = tileTexture;
    }
    
    public SequencerPackets()
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
        
        // Cook time.
        out.writeInt(lastItemCookTimeValue);
        out.writeInt(lastItemCookTimeMaximum);

        // Energy.
        out.writeLong(lastItemStoredEnergy);
        out.writeLong(lastItemStoredEnergyMaximum);
        
        // Texture we should be displaying.
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
        
        // Cook time.
        this.lastItemCookTimeValue = in.readInt();
        this.lastItemCookTimeMaximum = in.readInt();

        // Energy.
        this.lastItemStoredEnergy = in.readLong();
        this.lastItemStoredEnergyMaximum = in.readLong();
        
        // Texture we should be displaying.
        this.lastTexture = in.readUTF();
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
            sequencerTileEntity = (SequencerEntity) player.worldObj.getBlockTileEntity(tilePosX, tilePosY, tilePosZ);
            if (sequencerTileEntity == null) return;
            
            // Cook time.
            this.sequencerTileEntity.currentItemCookingValue = lastItemCookTimeValue;
            this.sequencerTileEntity.currentItemCookingMaximum = lastItemCookTimeMaximum;

            // Energy.
            this.sequencerTileEntity.setEnergy(ForgeDirection.UNKNOWN, lastItemStoredEnergy);
            this.sequencerTileEntity.setEnergyCapacity(lastItemStoredEnergyMaximum);
            
            // Texture we should be displaying.
            this.sequencerTileEntity.sequencerTexture = lastTexture;
        }
        else
        {
            throw new ProtocolException("Cannot send this packet to the server!");
        }
    }
}
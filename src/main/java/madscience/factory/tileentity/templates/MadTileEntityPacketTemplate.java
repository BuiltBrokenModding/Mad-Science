package madscience.factory.tileentity.templates;

import madscience.factory.tileentity.prefab.MadTileEntityPrefab;
import madscience.network.MadPackets;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;

public class MadTileEntityPacketTemplate extends MadPackets
{
    /** Registered machine name from factory. */
    private String machineName;
    
    /** X coordinate of tile entity. */
    private int tilePosX;
    
    /** Y coordinate of tile entity. */
    private int tilePosY;
    
    /** Z coordinate of tile entity. */
    private int tilePosZ;

    /** Tile entity from the world. */
    private MadTileEntityPrefab madTileEntity;

    /** Progress value */
    private int lastCookTimeValue;
    
    /** Progress maximum */
    private int lastCookTimeMaximum;

    /** Energy value. */
    private long lastEnergy;
    
    /** Energy maximum. */
    private long lastEnergyMaximum;

    /** Fluid amount. */
    private int lastFluidLevel;
    
    /** Fluid capacity. */
    private int lastFluidMaximum;
    
    /** Heat current value */
    private int lastHeatValue;
    
    /** Heat trigger, when isOverheating() will return true. */
    private int lastHeatTriggerValue;
    
    /** Heat maximum amount. */
    private int lastHeatMaximum;
    
    /** Damage value. */
    private int lastDamageValue;
    
    /** Damage maximum amount. */
    private int lastDamageMaximum;

    /** Last displayed texture resource path (without modid). */
    private String lastTexture;
    
    public MadTileEntityPacketTemplate()
    {
        // Required for reflection.
    }

    public MadTileEntityPacketTemplate(MadTileEntityPrefab madMachine) // NO_UCD (use default)
    {
        // Machine name.
        this.machineName = madMachine.getMachineInternalName();
        
        // World position information.
        this.tilePosX = madMachine.xCoord;
        this.tilePosY = madMachine.yCoord;
        this.tilePosZ = madMachine.zCoord;

        // Stores intermediate amount of time item has cooked out of total.
        this.lastCookTimeValue = madMachine.getProgressValue();
        this.lastCookTimeMaximum = madMachine.getProgressMaximum();

        // Stores last known amount of energy this block was known to have.
        this.lastEnergy = madMachine.getEnergyValue();
        this.lastEnergyMaximum = madMachine.getEnergyCapacity();

        // Amount of stored fluid.
        this.lastFluidLevel = madMachine.getFluidAmount();
        this.lastFluidMaximum = madMachine.getFluidCapacity();
        
        // Heat.
        this.lastHeatValue = madMachine.getHeatLevelValue();
        this.lastHeatTriggerValue = madMachine.getHeatLevelTriggerValue();
        this.lastHeatMaximum = madMachine.getHeatLevelMaximum();
        
        // Damage.
        this.lastDamageValue = madMachine.getDamageValue();
        this.lastDamageMaximum = madMachine.getDamageMaximum();
        
        // Last displayed texture.
        this.lastTexture = madMachine.getEntityTexture();
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
                madTileEntity = (MadTileEntityPrefab) possibleTileEntity;
            }

            // Null check.
            if (madTileEntity == null)
            {
                return;
            }
            
            // Name check.
            if (!this.madTileEntity.getMachineInternalName().equals(this.machineName))
            {
                return;
            }   

            // Cook time.
            this.madTileEntity.setProgressValue(lastCookTimeValue);
            this.madTileEntity.setProgressMaximum(lastCookTimeMaximum);

            // Energy.
            this.madTileEntity.setEnergy(ForgeDirection.UNKNOWN, lastEnergy);
            this.madTileEntity.setEnergyCapacity(lastEnergyMaximum);
            
            // Fluid amount.
            this.madTileEntity.setFluidAmount(lastFluidLevel);
            this.madTileEntity.setFluidCapacity(lastFluidMaximum);
            
            // Heat.
            this.madTileEntity.setHeatLevelValue(lastHeatValue);
            this.madTileEntity.setHeatLevelTriggerValue(lastHeatTriggerValue);
            this.madTileEntity.setHeatLevelMaximum(lastHeatMaximum);

            // Damage value.
            this.madTileEntity.setDamageValue(lastDamageValue);
            this.madTileEntity.setDamageMaximum(lastDamageMaximum);
            
            // Tile entity texture.
            this.madTileEntity.setTextureRenderedOnModel(lastTexture);
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

        // Machine name.
        this.machineName = in.readUTF();
        
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
        
        // Amount of stored fluid.
        this.lastFluidLevel = in.readInt();
        this.lastFluidMaximum = in.readInt();
        
        // Heat.
        this.lastHeatValue = in.readInt();
        this.lastHeatTriggerValue = in.readInt();
        this.lastHeatMaximum = in.readInt();
        
        // Damage.
        this.lastDamageValue = in.readInt();
        this.lastDamageMaximum = in.readInt();
        
        // Last displayed texture.
        this.lastTexture = in.readUTF();
    }

    @Override
    public void write(ByteArrayDataOutput out)
    {
        // ------
        // SERVER
        // ------

        // Machine name.
        out.writeUTF(this.machineName);
        
        // World coordinate information.
        out.writeInt(this.tilePosX);
        out.writeInt(this.tilePosY);
        out.writeInt(this.tilePosZ);

        // Stores intermediate amount of time item has cooked out of total.
        out.writeInt(this.lastCookTimeValue);
        out.writeInt(this.lastCookTimeMaximum);

        // Stores last known amount of energy this block was known to have.
        out.writeLong(this.lastEnergy);
        out.writeLong(this.lastEnergyMaximum);
        
        // Amount of stored fluid.
        out.writeInt(this.lastFluidLevel);
        out.writeInt(this.lastFluidMaximum);
        
        // Heat.
        out.writeInt(this.lastHeatValue);
        out.writeInt(this.lastHeatTriggerValue);
        out.writeInt(this.lastHeatMaximum);
        
        // Damage.
        out.writeInt(this.lastDamageValue);
        out.writeInt(this.lastDamageMaximum);
        
        // Last displayed texture.
        out.writeUTF(this.lastTexture);
    }
}
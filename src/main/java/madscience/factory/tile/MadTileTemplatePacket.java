package madscience.factory.tile;

import java.util.ArrayList;
import java.util.List;

import madscience.factory.model.MadModelData;
import madscience.factory.model.MadModelPosition;
import madscience.factory.model.MadModelScale;
import madscience.factory.rendering.MadModelWorldRender;
import madscience.network.MadPackets;
import madscience.util.MadUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;

public class MadTileTemplatePacket extends MadPackets
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
    
    /** Single string that represents model visibility data. */
    private String entityModelData;

    /** Last displayed texture resource path (without modid). */
    private String lastTexture;
    
    /** Delimited list of position and scale information for world renderer. */
    private String worldRenderInformation;
    
    public MadTileTemplatePacket()
    {
        // Required for reflection.
    }

    public MadTileTemplatePacket(MadTileEntityPrefab madMachine) // NO_UCD (use default)
    {
        // ------
        // SERVER
        // ------
        
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
        
        // Model information.
        List<String> packetModelData = new ArrayList<String>();
        for (MadModelData modelData : madMachine.getEntityModelData())
        {
            StringBuilder singleCompressesdModelPiece = new StringBuilder();
            singleCompressesdModelPiece.append(modelData.getModelPieceName());
            singleCompressesdModelPiece.append("|");
            singleCompressesdModelPiece.append(String.valueOf(MadUtils.convertBooleanToByte(modelData.isModelVisible())));
            packetModelData.add(singleCompressesdModelPiece.toString());
        }
        
        StringBuilder compressedPacketModelData = new StringBuilder();
        for (String singleCompressesModelPiece : packetModelData)
        {
            compressedPacketModelData.append(singleCompressesModelPiece);
            compressedPacketModelData.append(":");
        }
        this.entityModelData = MadUtils.removeLastChar(compressedPacketModelData.toString());
        
        // World render information.
        MadModelWorldRender worldRenderInfo = madMachine.getEntityWorldRenderInformation();
        StringBuilder worldRenderInfoCompressed = new StringBuilder();
        worldRenderInfoCompressed.append(worldRenderInfo.getModelWorldPosition().getModelTranslateX());         // POSITION
        worldRenderInfoCompressed.append(":");
        worldRenderInfoCompressed.append(worldRenderInfo.getModelWorldPosition().getModelTranslateY());
        worldRenderInfoCompressed.append(":");
        worldRenderInfoCompressed.append(worldRenderInfo.getModelWorldPosition().getModelTranslateZ());
        worldRenderInfoCompressed.append("|");
        worldRenderInfoCompressed.append(worldRenderInfo.getModelWorldScale().getModelScaleX());                // SCALE
        worldRenderInfoCompressed.append(":");
        worldRenderInfoCompressed.append(worldRenderInfo.getModelWorldScale().getModelScaleY());
        worldRenderInfoCompressed.append(":");
        worldRenderInfoCompressed.append(worldRenderInfo.getModelWorldScale().getModelScaleZ());
        this.worldRenderInformation = worldRenderInfoCompressed.toString();
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
            
            // Model information.
            // Note: MODELNAME:BYTEBOOL
            String[] splitServerModelCompressesdData = this.entityModelData.split("\\:");
            for (String splitServerModel : splitServerModelCompressesdData)
            {
                String[] packetModelComponents = splitServerModel.split("\\|");
                if (packetModelComponents[0] == null || packetModelComponents[1] == null)
                {
                    throw new IllegalArgumentException("[" + this.machineName + "]Invalid model packet information, cannot update model instance!");
                }
                
                String modelName = packetModelComponents[0];
                byte modelVisible = Byte.parseByte(packetModelComponents[1]);
                
                this.madTileEntity.setModelWorldRenderVisibilityByName(modelName, MadUtils.convertByteToBoolean(modelVisible));
            }
            
            // World Render Information.
            // NOTE: POSITION:SCALE (XYZ|XYZ).
            String[] splitServerWorldRenderInformation = this.worldRenderInformation.split("\\|");
            if (splitServerWorldRenderInformation[0] == null || splitServerWorldRenderInformation[1] == null)
            {
                throw new IllegalArgumentException("[" + this.machineName + "]Invalid world render information packet, cannot update world render information!");
            }
            
            // Get position and scale information from received packet information.
            String[] splitWorldRenderPosition = splitServerWorldRenderInformation[0].split("\\:");
            String[] splitWorldRenderScale = splitServerWorldRenderInformation[1].split("\\:");
            
            // Check if the position data is valid.
            if (splitWorldRenderPosition[0] == null || splitWorldRenderPosition[1] == null || splitWorldRenderPosition[2] == null)
            {
                throw new IllegalArgumentException("[" + this.machineName + "]Invalid world render position data, cannot update world render information!");
            }
            
            // Check if the scale data is valid.
            if (splitWorldRenderScale[0] == null || splitWorldRenderScale[1] == null || splitWorldRenderScale[2] == null)
            {
                throw new IllegalArgumentException("[" + this.machineName + "]Invalid world render scale data, cannot update world render information!");
            }
            
            // Convert position information into floating points
            float[] floatRenderPosition = new float[3];
            floatRenderPosition[0] = Float.parseFloat(splitWorldRenderPosition[0]);
            floatRenderPosition[1] = Float.parseFloat(splitWorldRenderPosition[1]);
            floatRenderPosition[2] = Float.parseFloat(splitWorldRenderPosition[2]);
            
            // Convert scale information into floating points.
            float[] floatRenderScale = new float[3];
            floatRenderScale[0] = Float.parseFloat(splitWorldRenderScale[0]);
            floatRenderScale[1] = Float.parseFloat(splitWorldRenderScale[1]);
            floatRenderScale[2] = Float.parseFloat(splitWorldRenderScale[2]);
            
            // Create position object from received data.
            MadModelPosition receivedPosition = new MadModelPosition(
                    floatRenderPosition[0],
                    floatRenderPosition[1],
                    floatRenderPosition[2]);
            
            // Create scale object from received data.
            MadModelScale receivedScale = new MadModelScale(
                    floatRenderScale[0],
                    floatRenderScale[1],
                    floatRenderScale[2]);
            
            if (receivedPosition == null || receivedScale == null)
            {
                throw new IllegalArgumentException("[" + this.machineName + "]Invalid world render data, cannot create world render objects from received packet data!");
            }
            
            // Apply the data to the client.
            this.madTileEntity.setWorldRenderInformation(receivedPosition, receivedScale);
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
        
        // Model information.
        this.entityModelData = in.readUTF();
        
        // World render information.
        this.worldRenderInformation = in.readUTF();
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
        
        // Model information.
        out.writeUTF(this.entityModelData);
        
        // World render information.
        out.writeUTF(this.worldRenderInformation);
    }
}
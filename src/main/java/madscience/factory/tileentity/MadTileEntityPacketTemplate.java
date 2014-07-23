package madscience.factory.tileentity;

import madscience.factory.MadTileEntityFactory;
import madscience.factory.MadTileEntityFactoryProduct;
import madscience.factory.model.MadModelFile;
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
    /** Name of this registered machine for reference by receiving client. */
    private String machineName;
    
    /** XYZ coordinates of tile entity source. */
    private int tilePosX;
    private int tilePosY;
    private int tilePosZ;

    /** Tile entity from the world. */
    private MadTileEntityPrefab ENTITY;

    /** Stores intermediate amount of time item has cooked out of total. */
    private int lastCookTimeValue;
    private int lastCookTimeMaximum;

    /** Stores last known amount of energy this block was known to have. */
    private long lastEnergy;
    private long lastEnergyMaximum;

    /** Amount of stored fluid. */
    private int lastFluidLevel;
    private int lastFluidMaximum;
    
    /** Heat. */
    private int lastHeatValue;
    private int lastHeatTriggerValue;
    private int lastHeatMaximum;
    
    /** Damage. */
    private int lastDamageValue;
    private int lastDamageMaximum;
    
    /** Model visibility for world object. */
    MadModelFile[] lastWorldModelList;
    
    /** Model visibility for item. */
    MadModelFile[] lastItemModelList;

    /** Last displayed texture. */
    private String lastTexture;
    
    public MadTileEntityPacketTemplate()
    {
        // Required for reflection.
    }

    public MadTileEntityPacketTemplate(
            String machineName,
            int posX,
            int posY,
            int posZ,
            int cookTime,
            int cookTimeMax,
            long energyStored,
            long energyMax,
            int fluidValue,
            int fluidMaximum,
            int heatValue,
            int heatTriggerValue,
            int heatMaximum,
            int damageValue,
            int damageMaximum,
            MadModelFile[] modelListWorld,
            MadModelFile[] modelListItem,
            String tileTexture) // NO_UCD (use default)
    {
        // Machine name.
        this.machineName = machineName;
        
        // World position information.
        this.tilePosX = posX;
        this.tilePosY = posY;
        this.tilePosZ = posZ;

        // Stores intermediate amount of time item has cooked out of total.
        this.lastCookTimeValue = cookTime;
        this.lastCookTimeMaximum = cookTimeMax;

        // Stores last known amount of energy this block was known to have.
        this.lastEnergy = energyStored;
        this.lastEnergyMaximum = energyMax;

        // Amount of stored fluid.
        this.lastFluidLevel = fluidValue;
        this.lastFluidMaximum = fluidMaximum;
        
        // Heat.
        this.lastHeatValue = heatValue;
        this.lastHeatTriggerValue = heatTriggerValue;
        this.lastHeatMaximum = heatMaximum;
        
        // Damage.
        this.lastDamageValue = damageValue;
        this.lastDamageMaximum = damageMaximum;
        
        // Model visibility arrays.
        this.lastWorldModelList = modelListWorld;
        this.lastItemModelList = modelListItem;
        
        // Last displayed texture.
        this.lastTexture = tileTexture;
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
            {
                return;
            }
            
            // Name check.
            if (!this.ENTITY.getMachineInternalName().equals(this.machineName))
            {
                return;
            }   

            // Cook time.
            this.ENTITY.setProgressValue(lastCookTimeValue);
            this.ENTITY.setProgressMaximum(lastCookTimeMaximum);

            // Energy.
            this.ENTITY.setEnergy(ForgeDirection.UNKNOWN, lastEnergy);
            this.ENTITY.setEnergyCapacity(lastEnergyMaximum);
            
            // Fluid amount.
            this.ENTITY.setFluidAmount(lastFluidLevel);
            this.ENTITY.setFluidCapacity(lastFluidMaximum);
            
            // Heat.
            this.ENTITY.setHeatLevelValue(lastHeatValue);
            this.ENTITY.setHeatLevelTriggerValue(lastHeatTriggerValue);
            this.ENTITY.setHeatLevelMaximum(lastHeatMaximum);

            // Damage value.
            this.ENTITY.setDamageValue(lastDamageValue);
            this.ENTITY.setDamageMaximum(lastDamageMaximum);
            
            // Model visibility for world/item models.
            this.ENTITY.setModelsForClientWorldRender(lastWorldModelList);
            this.ENTITY.setModelsForClientItemRender(lastItemModelList);

            // Tile entity texture.
            this.ENTITY.setTextureRenderedOnModel(lastTexture);
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
        
        // Using machine name we got grab instance of registered machine from client factory.
        // Note: We are only using the master list as checksum to length of model list for each machine.
        MadTileEntityFactoryProduct registeredMachine = MadTileEntityFactory.getMachineInfo(this.machineName);
        if (registeredMachine != null)
        {
            // Load reference of world and item models from JSON loader.
            lastWorldModelList = registeredMachine.getMasterModelsForWorldRender();
            lastItemModelList = registeredMachine.getMasterModelsforItemRender();
        }
        
        // Model visibility for world models.
        for(int i = 0; i < lastWorldModelList.length; i++)
        {
            lastWorldModelList[i].setModelVisible(in.readBoolean());
        }
        
        // Model visibility for item models.
        for(int i = 0; i < lastItemModelList.length; i++)
        {
            lastItemModelList[i].setModelVisible(in.readBoolean());
        }

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
        
        // Model visibility for world models.
        for (MadModelFile modelStatus : this.lastWorldModelList)
        {
            out.writeBoolean(modelStatus.isModelVisible());
        }
        
        // Model visibility for item models.
        for (MadModelFile modelStatus : this.lastItemModelList)
        {
            out.writeBoolean(modelStatus.isModelVisible());
        }

        // Last displayed texture.
        out.writeUTF(this.lastTexture);
    }
}
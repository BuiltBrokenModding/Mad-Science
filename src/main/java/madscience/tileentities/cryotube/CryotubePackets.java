package madscience.tileentities.cryotube;

import madscience.network.MadPackets;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.ForgeDirection;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;

public class CryotubePackets extends MadPackets
{
    // Tile entity.
    private CryotubeEntity ENTITY;

    // Energy.
    private long lastEnergy;
    private long lastEnergyMax;

    // Hatch time.
    private int lastHatchTime;

    private int lastHatchTimeMax;
    // Health.
    private int lastSubjectHealth;

    private int lastSubjectHealthMax;
    // Neural activity.
    private int lastSubjectNeuralActivity;

    private int lastSubjectNeuralActivityMax;
    // Current texture path.
    private String lastTexture;

    // XYZ coordinates of tile entity source.
    private int tilePosX;
    private int tilePosY;

    private int tilePosZ;

    public CryotubePackets()
    {
        // Required for reflection.
    }

    CryotubePackets(int posX, int posY, int posZ, int hatchTime, int hatchTimeMax, long energyStored, long energyMax, int subjectHealth, int subjectMaxHealth, int neuralActivity, int neuralActivityMax, String tileTexture)
    {
        // World position information.
        tilePosX = posX;
        tilePosY = posY;
        tilePosZ = posZ;

        // Hatch time.
        lastHatchTime = hatchTime;
        lastHatchTimeMax = hatchTimeMax;

        // Energy.
        lastEnergy = energyStored;
        lastEnergyMax = energyMax;

        // Subject health.
        lastSubjectHealth = subjectHealth;
        lastSubjectHealthMax = subjectMaxHealth;

        // Subject neural activity.
        lastSubjectNeuralActivity = neuralActivity;
        lastSubjectNeuralActivityMax = neuralActivityMax;

        // Texture to use on ourselves.
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
            // Get tile entity from world.
            ENTITY = (CryotubeEntity) player.worldObj.getBlockTileEntity(tilePosX, tilePosY, tilePosZ);
            if (ENTITY == null)
                return;

            // Hatch time.
            this.ENTITY.hatchTimeCurrentValue = lastHatchTime;
            this.ENTITY.hatchTimeMaximum = lastHatchTimeMax;

            // Energy.
            this.ENTITY.setEnergy(ForgeDirection.UNKNOWN, lastEnergy);
            this.ENTITY.setEnergyCapacity(lastEnergyMax);

            // Subject Health.
            this.ENTITY.subjectCurrentHealth = lastSubjectHealth;
            this.ENTITY.subjectMaximumHealth = lastSubjectHealthMax;

            // Subject Neural Activity.
            this.ENTITY.neuralActivityValue = lastSubjectNeuralActivity;
            this.ENTITY.neuralActivityMaximum = lastSubjectNeuralActivityMax;

            // Tile entity texture.
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

        // Hatch time.
        this.lastHatchTime = in.readInt();
        this.lastHatchTimeMax = in.readInt();

        // Energy.
        this.lastEnergy = in.readLong();
        this.lastEnergyMax = in.readLong();

        // Health.
        this.lastSubjectHealth = in.readInt();
        this.lastSubjectHealthMax = in.readInt();

        // Neural Activity.
        this.lastSubjectNeuralActivity = in.readInt();
        this.lastSubjectNeuralActivityMax = in.readInt();

        // Tile entity texture.
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

        // Hatch time.
        out.writeInt(lastHatchTime);
        out.writeInt(lastHatchTimeMax);

        // Energy.
        out.writeLong(lastEnergy);
        out.writeLong(lastEnergyMax);

        // Subject Health.
        out.writeInt(lastSubjectHealth);
        out.writeInt(lastSubjectHealthMax);

        // Subject Neural Activity
        out.writeInt(lastSubjectNeuralActivity);
        out.writeInt(lastSubjectNeuralActivityMax);

        // Tile entity texture.
        out.writeUTF(lastTexture);
    }
}
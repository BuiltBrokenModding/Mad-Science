package madscience.tile.magloader;

import madscience.network.MadPackets;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.ForgeDirection;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;

public class MagLoaderPackets extends MadPackets
{
    // Does the loader have bullets loaded into it.
    private int bulletCount;

    // Tile entity reference upon execute.
    private MagLoaderEntity ENTITY;

    // Determines if the sound of a magazine being inserted into the machine has been played.
    private boolean hasPlayedMagazineInsertSound;

    // Last known maximum cook time.
    private int lastItemCookTimeMaximum;

    // Last known current cook time.
    private int lastItemCookTimeValue;

    // Last known amount of energy stored.
    private long lastItemStoredEnergy;

    // Last known maximum amount of energy.
    private long lastItemStoredMaxEnergy;

    // Does the loader have magazines inserted into it.
    private int magazineCount;
    
    // Does the loader have anything in output slot (this also counts as magazine count).
    private int outputMagazineCount;

    // XYZ coordinates of tile entity source.
    private int tilePosX;
    private int tilePosY;
    private int tilePosZ;

    public MagLoaderPackets()
    {
        // Required for reflection.
    }

    MagLoaderPackets(int posX, int posY, int posZ, int cookTime, int cookTimeMax, long energyStored, long energyMax, int storageItemsCount, int inputItemsCount, boolean playedMagInsertSound, int outputtedMagazines)
    {
        // World position information.
        tilePosX = posX;
        tilePosY = posY;
        tilePosZ = posZ;

        // Tile entity specific.
        lastItemCookTimeValue = cookTime;
        lastItemCookTimeMaximum = cookTimeMax;
        lastItemStoredMaxEnergy = energyMax;
        lastItemStoredEnergy = energyStored;

        // Magazine loader specific.
        bulletCount = storageItemsCount;
        magazineCount = inputItemsCount;
        hasPlayedMagazineInsertSound = playedMagInsertSound;
        outputMagazineCount = outputtedMagazines;
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
            ENTITY = (MagLoaderEntity) player.worldObj.getBlockTileEntity(tilePosX, tilePosY, tilePosZ);
            if (ENTITY == null)
                return;

            this.ENTITY.currentItemCookingValue = lastItemCookTimeValue;
            this.ENTITY.currentItemCookingMaximum = lastItemCookTimeMaximum;
            this.ENTITY.setEnergyCapacity(lastItemStoredMaxEnergy);
            this.ENTITY.setEnergy(ForgeDirection.UNKNOWN, lastItemStoredEnergy);
            this.ENTITY.clientBulletCount = bulletCount;
            this.ENTITY.clientMagazineCount = magazineCount;
            this.ENTITY.hasPlayedMagazineInsertSound = hasPlayedMagazineInsertSound;
            this.ENTITY.clientOutputMagazineCount = outputMagazineCount;
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

        // Packet being read by client, verifying integrity.
        this.lastItemCookTimeValue = in.readInt();
        this.lastItemCookTimeMaximum = in.readInt();
        this.lastItemStoredMaxEnergy = in.readLong();
        this.lastItemStoredEnergy = in.readLong();

        // Magazine loader packet for client.
        this.bulletCount = in.readInt();
        this.magazineCount = in.readInt();
        this.hasPlayedMagazineInsertSound = in.readBoolean();
        this.outputMagazineCount = in.readInt();
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

        // Packet being written by server.
        out.writeInt(lastItemCookTimeValue);
        out.writeInt(lastItemCookTimeMaximum);
        out.writeLong(lastItemStoredMaxEnergy);
        out.writeLong(lastItemStoredEnergy);

        // Magazine loader server information.
        out.writeInt(bulletCount);
        out.writeInt(magazineCount);
        out.writeBoolean(hasPlayedMagazineInsertSound);
        out.writeInt(outputMagazineCount);
    }
}
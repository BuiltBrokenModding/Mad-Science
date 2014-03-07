package madscience.tileentities.clayfurnace;

import madscience.network.MadPackets;
import net.minecraft.entity.player.EntityPlayer;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;

public class ClayfurnacePackets extends MadPackets
{
    // XYZ coordinates of tile entity source.
    private int tilePosX;
    private int tilePosY;
    private int tilePosZ;

    // Tile entity that we want to hook in the world.
    private ClayfurnaceEntity clayfurnaceTileEntity;

    // Cook time.
    private int lastItemCookTimeValue;
    private int lastItemCookTimeMaximum;

    // Determines if this block has been lit on fire by the player with flint and steel.
    public boolean hasBeenLit = false;

    // Determines if this block has been hit by the player after a burn cycle completed.
    public boolean hasStoppedSmoldering = false;

    // Determines if clay furnace has been burning long enough to consider it a full cycle.
    public boolean hasCompletedBurnCycle = false;

    // Determines if the block has
    public boolean hasCooledDown = false;

    // Last texture to be displayed.
    private String lastTexture;

    public ClayfurnacePackets()
    {
        // Required for reflection.
    }

    public ClayfurnacePackets(int posX, int posY, int posZ, int cookTime, int cookTimeMax, boolean beenLit, boolean stoppedSmoldering, boolean completedBurn, boolean cooledDown, String tileTexture)
    {
        // World position information.
        tilePosX = posX;
        tilePosY = posY;
        tilePosZ = posZ;

        // Cook time.
        lastItemCookTimeValue = cookTime;
        lastItemCookTimeMaximum = cookTimeMax;

        // Last texture to be displayed.
        lastTexture = tileTexture;

        // Information about our current state.
        hasBeenLit = beenLit;
        hasStoppedSmoldering = stoppedSmoldering;
        hasCompletedBurnCycle = completedBurn;
        hasCooledDown = cooledDown;
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
            clayfurnaceTileEntity = (ClayfurnaceEntity) player.worldObj.getBlockTileEntity(tilePosX, tilePosY, tilePosZ);
            if (clayfurnaceTileEntity == null)
                return;

            // Cook time.
            this.clayfurnaceTileEntity.currentItemCookingValue = lastItemCookTimeValue;
            this.clayfurnaceTileEntity.currentItemCookingMaximum = lastItemCookTimeMaximum;

            // Last texture to be displayed.
            this.clayfurnaceTileEntity.TEXTURE = lastTexture;

            // Information about our current state.
            this.clayfurnaceTileEntity.hasBeenLit = hasBeenLit;
            this.clayfurnaceTileEntity.hasStoppedSmoldering = hasStoppedSmoldering;
            this.clayfurnaceTileEntity.hasCompletedBurnCycle = hasCompletedBurnCycle;
            this.clayfurnaceTileEntity.hasCooledDown = hasCooledDown;
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

        // Last texture to be displayed.
        this.lastTexture = in.readUTF();

        // Information about our current state.
        this.hasBeenLit = in.readBoolean();
        this.hasStoppedSmoldering = in.readBoolean();
        this.hasCompletedBurnCycle = in.readBoolean();
        this.hasCooledDown = in.readBoolean();
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

        // Last texture to be displayed.
        out.writeUTF(lastTexture);

        // Information about our current state.
        out.writeBoolean(hasBeenLit);
        out.writeBoolean(hasStoppedSmoldering);
        out.writeBoolean(hasCompletedBurnCycle);
        out.writeBoolean(hasCooledDown);
    }
}
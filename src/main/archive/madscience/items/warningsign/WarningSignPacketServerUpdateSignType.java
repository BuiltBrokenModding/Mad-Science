package madscience.content.items.warningsign;

import madscience.network.MadPackets;
import net.minecraft.entity.player.EntityPlayer;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;

public class WarningSignPacketServerUpdateSignType extends MadPackets
{
    // Entity in the world.
    private int entityID;
    private WarningSignEntity ENTITY;

    // Current sign to display.
    private int signType;

    // Direction we are hanging.
    // private int hangingDir;

    public WarningSignPacketServerUpdateSignType()
    {
        // Required for reflection.
    }

    public WarningSignPacketServerUpdateSignType(int entityID, int imgType /* , int direction */)
    {
        // Entity ID for the world.
        this.entityID = entityID;

        // Image type information.
        this.signType = imgType;

        // Direction
        // this.hangingDir = direction;
    }

    @Override
    public void execute(EntityPlayer player, Side side) throws ProtocolException
    {
        // Packet received by client, executing payload.
        if (side.isClient())
        {
            ENTITY = (WarningSignEntity) player.worldObj.getEntityByID(this.entityID);
            if (ENTITY == null)
                return;

            // Image type information.
            this.ENTITY.clientCurrentSignType = WarningSignEnum.values()[signType];
            //MadScience.logger.info("[Client][WarningSignEntity]Recieved update packet for Warning Sign ID " + this.entityID + " to become sign type " + this.ENTITY.clientCurrentSignType.title);

            // Direction information.
            // TODO: Find out if this can be removed completely.
            // this.ENTITY.setDirection(hangingDir);
        }
        else
        {
            throw new ProtocolException("Cannot send this packet to the server!");
        }
    }

    @Override
    public void read(ByteArrayDataInput in) throws ProtocolException
    {
        // Entity ID for the world.
        this.entityID = in.readInt();

        // Image type information.
        this.signType = in.readInt();

        // Direction.
        // this.hangingDir = in.readInt();
    }

    @Override
    public void write(ByteArrayDataOutput out)
    {
        // Entity ID for the world.
        out.writeInt(this.entityID);

        // Image type information.
        out.writeInt(this.signType);

        // Direction.
        // out.writeInt(this.hangingDir);
    }
}
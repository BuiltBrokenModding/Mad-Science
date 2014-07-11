package madscience.items.warningsign;

import madscience.network.MadPackets;
import net.minecraft.entity.player.EntityPlayer;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;

public class WarningSignPacketServerRequestReplySignType extends MadPackets
{
    // Entity in the world.
    private int entityID;
    private WarningSignEntity ENTITY;

    // Current sign to display.
    private int signType;

    public WarningSignPacketServerRequestReplySignType()
    {
        // Required for reflection.
    }

    WarningSignPacketServerRequestReplySignType(int entityID, int imgType)
    {
        // Entity ID for the world.
        this.entityID = entityID;

        // Image type information.
        this.signType = imgType;
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

            // Debugging based on what will happen!
            if (this.ENTITY.clientCurrentSignType != null)
            {
                // Mark the sign as having correctly talked with the server.
                this.ENTITY.clientHasRecievedServerReply = true;

                // Mark the sign as no longer needed to talk to the server.
                this.ENTITY.clientShouldRequestSignType = false;

                // If client already matched response from server then we don't need to update anything.
                if (this.ENTITY.clientCurrentSignType.ordinal() == signType)
                {
                    //MadScience.logger.info("[Client][WarningSignPacketServerLoginReply]Recieved update packet for Warning Sign ID " + this.entityID + " but don't have to update because sign types matched.");
                }
                else
                {
                    // Changes the client image to match what the server told us to.
                    this.ENTITY.clientCurrentSignType = WarningSignEnum.values()[signType];
                    //MadScience.logger.info("[Client][WarningSignPacketServerLoginReply]Recieved update packet for Warning Sign ID " + this.entityID + " to become sign type " + this.ENTITY.clientCurrentSignType.title);
                }
            }
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
    }

    @Override
    public void write(ByteArrayDataOutput out)
    {
        // Entity ID for the world.
        out.writeInt(this.entityID);

        // Image type information.
        out.writeInt(this.signType);
    }
}
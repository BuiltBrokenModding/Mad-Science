package madscience.items.warningsign;

import madscience.MadScience;
import madscience.network.MadPackets;
import net.minecraft.entity.player.EntityPlayer;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;

public class WarningSignPacketClientRequestSignType extends MadPackets
{
    // Entity in the world.
    private int entityID;
    private WarningSignEntity ENTITY;

    // Current sign to display.
    private int signType;

    public WarningSignPacketClientRequestSignType()
    {
        // Required for reflection.
    }

    public WarningSignPacketClientRequestSignType(int entityID, int imgType)
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
        if (side.isServer())
        {
            // Grab the entity from the server side game world.
            ENTITY = (WarningSignEntity) player.worldObj.getEntityByID(this.entityID);
            if (ENTITY == null)
                return;

            // Determines if server sign type matches what client sent us.
            if (this.ENTITY.serverCurrentSignType != null)
            {
                // Send packet back to that specific player with information on what this sign should be.
                PacketDispatcher.sendPacketToPlayer(new WarningSignPacketServerRequestReplySignType(this.ENTITY.entityId, this.ENTITY.serverCurrentSignType.ordinal()).makePacket(), (Player) player);

                // Debugging!
//                if (this.ENTITY.serverCurrentSignType.ordinal() != this.signType)
//                {
//                    MadScience.logger.info("[Server][WarningSignPacket]Recieved client (" + player.username + ") request packet against sign " + this.entityID + " with type " + WarningSignEnum.values()[this.signType].title + " and replied "
//                            + this.ENTITY.serverCurrentSignType.title);
//                }
//                else
//                {
//                    MadScience.logger.info("[Server][WarningSignPacket]Recieved client (" + player.username + ") request packet for sign " + this.entityID + " but they already matched on server! Replied with confirmation packet sign type "
//                            + this.ENTITY.serverCurrentSignType.title + ".");
//                }
            }
        }
        else
        {
            throw new ProtocolException("Cannot send this packet to the clients!");
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
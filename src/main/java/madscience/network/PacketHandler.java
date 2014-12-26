package madscience.network;


import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import madscience.mod.ModLoader;
import madscience.network.PacketList.ProtocolException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;


public class PacketHandler implements IPacketHandler
{
    @Override
    public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player)
    {
        try
        {
            EntityPlayer entityPlayer = (EntityPlayer) player;
            ByteArrayDataInput in = ByteStreams.newDataInput( packet.data );
            int packetId =
                    in.readUnsignedByte(); // Assuming your packetId is between 0 (inclusive) and 256 (exclusive). If you need more you need to change this
            PacketList packetList = PacketList.constructPacket( packetId );
            packetList.read( in );
            packetList.execute( entityPlayer,
                                entityPlayer.worldObj.isRemote
                                ? Side.CLIENT
                                : Side.SERVER );
        }
        catch (ProtocolException e)
        {
            if (player instanceof EntityPlayerMP)
            {
                ((EntityPlayerMP) player).playerNetServerHandler.kickPlayerFromServer( "Protocol Exception!" );
                ModLoader.log().warning( "Player " +
                                         ((EntityPlayer) player).username +
                                         " caused a Protocol Exception!" );
            }
        }
        catch (ReflectiveOperationException e)
        {
            throw new RuntimeException( "Unexpected Reflection exception during Packet construction!",
                                        e );
        }
    }
}
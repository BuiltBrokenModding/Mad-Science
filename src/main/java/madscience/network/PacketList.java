package madscience.network;


import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import madscience.ModMetadata;
import madscience.tile.TileTemplatePacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;


public abstract class PacketList
{
    private static final BiMap<Integer, Class<? extends PacketList>> idMap;

    static
    {
        ImmutableBiMap.Builder<Integer, Class<? extends PacketList>> builder = ImmutableBiMap.builder();

        // Mad Tile Entity Packet
        builder.put( Integer.valueOf( 1 ),
                     TileTemplatePacket.class );

        // Mad Particle Packet (Can be any particle!)
        builder.put( Integer.valueOf( 2 ),
                     ParticlePacket.class );

        idMap = builder.build();
    }

    static PacketList constructPacket(int packetId) throws ProtocolException, ReflectiveOperationException
    {
        Class<? extends PacketList> clazz = idMap.get( Integer.valueOf( packetId ) );
        if (clazz == null)
        {
            throw new ProtocolException( "Unknown Packet Id!" );
        }
        else
        {
            return clazz.newInstance();
        }
    }

    public abstract void execute(EntityPlayer player, Side side) throws ProtocolException;

    public final int getPacketId()
    {
        if (idMap.inverse().containsKey( getClass() ))
        {
            return idMap.inverse().get( getClass() ).intValue();
        }
        else
        {
            throw new RuntimeException( "Packet " + getClass().getSimpleName() +
                                        " is missing a mapping!" );
        }
    }

    public final Packet makePacket()
    {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeByte( getPacketId() );
        write( out );
        return PacketDispatcher.getPacket( ModMetadata.CHANNEL_NAME,
                                           out.toByteArray() );
    }

    public abstract void read(ByteArrayDataInput in) throws ProtocolException;

    public abstract void write(ByteArrayDataOutput out);

    public static class ProtocolException extends Exception
    {
        public ProtocolException()
        {
        }

        public ProtocolException(String message)
        {
            super( message );
        }
    }
}
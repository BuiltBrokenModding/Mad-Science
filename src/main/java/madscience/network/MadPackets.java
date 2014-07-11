package madscience.network;

import madscience.MadScience;
import madscience.factory.tileentity.MadTileEntityPacketTemplate;
import madscience.items.warningsign.WarningSignPacketClientRequestSignType;
import madscience.items.warningsign.WarningSignPacketServerRequestReplySignType;
import madscience.items.warningsign.WarningSignPacketServerUpdateSignType;
import madscience.items.weapons.pulserifle.PulseRiflePackets;
import madscience.tileentities.clayfurnace.ClayfurnacePackets;
import madscience.tileentities.cncmachine.CnCMachinePackets;
import madscience.tileentities.cryofreezer.CryofreezerPackets;
import madscience.tileentities.cryotube.CryotubePackets;
import madscience.tileentities.dataduplicator.DataDuplicatorPackets;
import madscience.tileentities.incubator.IncubatorPackets;
import madscience.tileentities.magloader.MagLoaderPackets;
import madscience.tileentities.mainframe.MainframePackets;
import madscience.tileentities.meatcube.MeatcubePackets;
import madscience.tileentities.sanitizer.SanitizerPackets;
import madscience.tileentities.sequencer.SequencerPackets;
import madscience.tileentities.soniclocator.SoniclocatorPackets;
import madscience.tileentities.thermosonicbonder.ThermosonicBonderPackets;
import madscience.tileentities.voxbox.VoxBoxPackets;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;

public abstract class MadPackets
{
    public static class ProtocolException extends Exception
    {
        public ProtocolException()
        {
        }

        public ProtocolException(String message)
        {
            super(message);
        }
    }

    private static final BiMap<Integer, Class<? extends MadPackets>> idMap;

    static
    {
        ImmutableBiMap.Builder<Integer, Class<? extends MadPackets>> builder = ImmutableBiMap.builder();

        // Cryogenic Freezer
        builder.put(Integer.valueOf(0), CryofreezerPackets.class);

        // Cryogenic Tube
        builder.put(Integer.valueOf(1), CryotubePackets.class);

        // Data Duplicator
        builder.put(Integer.valueOf(2), DataDuplicatorPackets.class);

        // Mad Tile Entity Packet
        builder.put(Integer.valueOf(3), MadTileEntityPacketTemplate.class);

        // Genome Incubator
        builder.put(Integer.valueOf(4), IncubatorPackets.class);

        // Computer Mainframe
        builder.put(Integer.valueOf(5), MainframePackets.class);

        // Disgusting Meat Cube
        builder.put(Integer.valueOf(6), MeatcubePackets.class);

        // Syringe Sanitizer
        builder.put(Integer.valueOf(7), SanitizerPackets.class);

        // Genetic Sequencer
        builder.put(Integer.valueOf(8), SequencerPackets.class);

        // Thermosonic Bonder
        builder.put(Integer.valueOf(9), ThermosonicBonderPackets.class);

        // Soniclocator Device
        builder.put(Integer.valueOf(10), SoniclocatorPackets.class);
        
        // Mad Particle Packet (Can be any particle!)
        builder.put(Integer.valueOf(11), MadParticlePacket.class);
        
        // Clay Furnace
        builder.put(Integer.valueOf(12), ClayfurnacePackets.class);
        
        // Pulse Rifle
        builder.put(Integer.valueOf(13), PulseRiflePackets.class);
        
        // VOX Box
        builder.put(Integer.valueOf(14), VoxBoxPackets.class);
        
        // Magazine Loader
        builder.put(Integer.valueOf(15), MagLoaderPackets.class);
        
        // CnC Machine
        builder.put(Integer.valueOf(16), CnCMachinePackets.class);
        
        // Warning Sign Server (Sent from server to all clients to change Sign Type)
        builder.put(Integer.valueOf(17), WarningSignPacketServerUpdateSignType.class);
        
        // Warning Sign Server (Sent from server to specific client)
        builder.put(Integer.valueOf(18), WarningSignPacketServerRequestReplySignType.class);
        
        // Warning Sign Client (Sent by clients to server to ask for painting)
        builder.put(Integer.valueOf(19), WarningSignPacketClientRequestSignType.class);

        idMap = builder.build();
    }

    static MadPackets constructPacket(int packetId) throws ProtocolException, ReflectiveOperationException
    {
        Class<? extends MadPackets> clazz = idMap.get(Integer.valueOf(packetId));
        if (clazz == null)
        {
            throw new ProtocolException("Unknown Packet Id!");
        }
        else
        {
            return clazz.newInstance();
        }
    }

    public abstract void execute(EntityPlayer player, Side side) throws ProtocolException;

    public final int getPacketId()
    {
        if (idMap.inverse().containsKey(getClass()))
        {
            return idMap.inverse().get(getClass()).intValue();
        }
        else
        {
            throw new RuntimeException("Packet " + getClass().getSimpleName() + " is missing a mapping!");
        }
    }

    public final Packet makePacket()
    {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeByte(getPacketId());
        write(out);
        return PacketDispatcher.getPacket(MadScience.CHANNEL_NAME, out.toByteArray());
    }

    public abstract void read(ByteArrayDataInput in) throws ProtocolException;

    public abstract void write(ByteArrayDataOutput out);
}
package madscience.network;

import madscience.MadScience;
import madscience.tileentities.cryofreezer.CryofreezerPackets;
import madscience.tileentities.cryotube.CryotubePackets;
import madscience.tileentities.dataduplicator.DataDuplicatorPackets;
import madscience.tileentities.dnaextractor.DNAExtractorPackets;
import madscience.tileentities.incubator.IncubatorPackets;
import madscience.tileentities.mainframe.MainframePackets;
import madscience.tileentities.meatcube.MeatcubePackets;
import madscience.tileentities.sanitizer.SanitizerPackets;
import madscience.tileentities.sequencer.SequencerPackets;
import madscience.tileentities.soniclocator.SoniclocatorPackets;
import madscience.tileentities.thermosonicbonder.ThermosonicBonderPackets;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraftforge.common.ForgeDirection;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;

public abstract class MadPackets
{
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
        
        // DNA Extractor
        builder.put(Integer.valueOf(3), DNAExtractorPackets.class);
        
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
        
        idMap = builder.build();
    }

    public static MadPackets constructPacket(int packetId) throws ProtocolException, ReflectiveOperationException
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

    public static class ProtocolException extends Exception
    {

        public ProtocolException()
        {
        }

        public ProtocolException(String message, Throwable cause)
        {
            super(message, cause);
        }

        public ProtocolException(String message)
        {
            super(message);
        }

        public ProtocolException(Throwable cause)
        {
            super(cause);
        }
    }

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

    public abstract void write(ByteArrayDataOutput out);

    public abstract void read(ByteArrayDataInput in) throws ProtocolException;

    public abstract void execute(EntityPlayer player, Side side) throws ProtocolException;
}
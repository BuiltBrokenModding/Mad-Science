package madscience.network;

import madscience.MadForgeMod;
import net.minecraft.entity.player.EntityPlayer;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;

public class MadParticlePacket extends MadPackets
{
    // Stores the name of the particle to send.
    private String particleName;
    
    // XYZ coordinates of where we want the particles to spawn.
    private double particlePosX;
    private double particlePosY;
    private double particlePosZ;
    
    // XYZ for determining particle velocity.
    private double velX;
    private double velY;
    private double velZ;

    public MadParticlePacket()
    {
        // Required for reflection.
    }

    public MadParticlePacket(String whatParticle, double posX, double posY, double posZ, double velocityX, double velocityY, double velocityZ)
    {
        particleName = whatParticle;
        
        particlePosX = posX;
        particlePosY = posY;
        particlePosZ = posZ;
        
        velX = velocityX;
        velY = velocityY;
        velZ = velocityZ;
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
            // Spawn the particle name for the given amount of time at specified coordinates and velocity vectors.
            for (int var3 = 0; var3 < 7; ++var3)
            {
                double var4 = player.worldObj.rand.nextGaussian() * 0.02D;
                double var6 = player.worldObj.rand.nextGaussian() * 0.02D;
                double var8 = player.worldObj.rand.nextGaussian() * 0.02D;
                
                // Use the Forge network proxy to spawn the particle properly on the client only.
                MadForgeMod.proxy.spawnParticle(particleName, particlePosX, particlePosY, particlePosZ, velX, velY, velZ);
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
        // ------
        // CLIENT
        // ------

        particleName = in.readUTF();
        
        particlePosX = in.readDouble();
        particlePosY = in.readDouble();
        particlePosZ = in.readDouble();
        
        velX = in.readDouble();
        velY = in.readDouble();
        velZ = in.readDouble();
    }

    @Override
    public void write(ByteArrayDataOutput out)
    {
        // ------
        // SERVER
        // ------

        out.writeUTF(particleName);
        
        out.writeDouble(particlePosX);
        out.writeDouble(particlePosY);
        out.writeDouble(particlePosZ);
        
        out.writeDouble(velX);
        out.writeDouble(velY);
        out.writeDouble(velZ);
    }
}
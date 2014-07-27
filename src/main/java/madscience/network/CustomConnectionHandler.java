package madscience.network;

import madscience.factory.mod.MadMod;
import net.minecraft.client.Minecraft;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.server.MinecraftServer;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IConnectionHandler;
import cpw.mods.fml.common.network.Player;

public class CustomConnectionHandler implements IConnectionHandler
{
    private boolean hasSeenMessage = false;
    private long jenkinsLastBuild = 0;

    public CustomConnectionHandler(long newBuildNumber)
    {
        // Carries along the build number retrieved from the Jenkins build server.
        jenkinsLastBuild = newBuildNumber;
    }

    @Override
    public void playerLoggedIn(Player player, NetHandler netHandler, INetworkManager manager)
    {
    }

    @Override
    public String connectionReceived(NetLoginHandler netHandler, INetworkManager manager)
    {
        return null;
    }

    @Override
    public void connectionOpened(NetHandler netClientHandler, String server, int port, INetworkManager manager)
    {
    }

    @Override
    public void connectionOpened(NetHandler netClientHandler, MinecraftServer server, INetworkManager manager)
    {
    }

    @SuppressWarnings("unused")
    @Override
    public void clientLoggedIn(NetHandler clientHandler, INetworkManager manager, Packet1Login login)
    {
        if (hasSeenMessage == false)
        {
            if (FMLCommonHandler.instance().getSide().isClient())
            {
                Minecraft mc = FMLClientHandler.instance().getClient();
                int maxTries = 30;
                while (mc.thePlayer == null && maxTries > 0)
                {
                    maxTries--;
                }

                if (mc.thePlayer != null && MadMod.VBUILD != "@BUILD@")
                {
                    // Ensure both numbers are proper for comparing.
                    long runningBuild = new Long(MadMod.VBUILD);

                    // Tell the user how many builds behind they are.
                    if (jenkinsLastBuild > runningBuild)
                    {
                        long buildDiff = jenkinsLastBuild - runningBuild;
                        
                        if (buildDiff > 1)
                        {
                            mc.thePlayer.addChatMessage(MadMod.NAME + ": You're " + String.valueOf(buildDiff) + " versions behind. Visit madsciencemod.com for updates.");
                        }
                        else
                        {
                            mc.thePlayer.addChatMessage(MadMod.NAME + ": You're " + String.valueOf(buildDiff) + " version behind. Visit madsciencemod.com for updates.");
                        }
                    }
                }
            }

            hasSeenMessage = true;
        }
    }

    @Override
    public void connectionClosed(INetworkManager manager)
    {
    }

}
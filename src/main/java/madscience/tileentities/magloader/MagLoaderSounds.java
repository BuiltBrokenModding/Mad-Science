package madscience.tileentities.magloader;

import madscience.MadFurnaces;
import madscience.MadScience;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MagLoaderSounds
{
    // Magazine Loader
    public static final String MAGLOADER_IDLE = MadScience.ID + ":" + MadFurnaces.MAGLOADER_INTERNALNAME + ".Idle";

    @SideOnly(Side.CLIENT)
    public static void init(SoundLoadEvent event)
    {
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MAGLOADER_INTERNALNAME + "/Idle.ogg");
    }
}

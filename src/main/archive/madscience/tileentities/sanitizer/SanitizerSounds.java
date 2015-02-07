package madscience.tileentities.sanitizer;

import madscience.MadFurnaces;
import madscience.MadScience;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SanitizerSounds
{
    // Needle Sanitizer
    public static final String SANTITIZER_IDLE = MadScience.ID + ":" + MadFurnaces.SANTITIZER_INTERNALNAME + ".Idle";
    
    @SideOnly(Side.CLIENT)
    public static void init(SoundLoadEvent event)
    {
        // Cleaning needle sound.
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.SANTITIZER_INTERNALNAME + "/Idle.ogg");
    }
}

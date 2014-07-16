package madscience.tile.sanitizer;

import madscience.MadFurnaces;
import madscience.factory.mod.MadMod;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SanitizerSounds
{
    // Needle Sanitizer
    static final String SANTITIZER_IDLE = MadMod.ID + ":" + MadFurnaces.SANTITIZER_INTERNALNAME + ".Idle";
    
    @SideOnly(Side.CLIENT)
    public static void init(SoundLoadEvent event)
    {
        // Cleaning needle sound.
        event.manager.addSound(MadMod.ID + ":" + MadFurnaces.SANTITIZER_INTERNALNAME + "/Idle.ogg");
    }
}

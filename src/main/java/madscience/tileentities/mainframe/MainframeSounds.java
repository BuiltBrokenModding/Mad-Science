package madscience.tileentities.mainframe;

import net.minecraftforge.client.event.sound.SoundLoadEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import madscience.MadFurnaces;
import madscience.MadScience;

public class MainframeSounds
{
    
    static final String MAINFRAME_FINISH = MadScience.ID + ":" + MadFurnaces.MAINFRAME_INTERNALNAME + ".Finish";
    static final String MAINFRAME_IDLE = MadScience.ID + ":" + MadFurnaces.MAINFRAME_INTERNALNAME + ".Idle";
    static final String MAINFRAME_OVERHEAT = MadScience.ID + ":" + MadFurnaces.MAINFRAME_INTERNALNAME + ".Overheat";
    static final String MAINFRAME_START = MadScience.ID + ":" + MadFurnaces.MAINFRAME_INTERNALNAME + ".Start";
    static final String MAINFRAME_WORK = MadScience.ID + ":" + MadFurnaces.MAINFRAME_INTERNALNAME + ".Work";
    
    @SideOnly(Side.CLIENT)
    public static void init(SoundLoadEvent event)
    {
        // Finish processing.
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MAINFRAME_INTERNALNAME + "/Finish.ogg");

        // Idle running sound.
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MAINFRAME_INTERNALNAME + "/Idle.ogg");

        // Overheating noise.
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MAINFRAME_INTERNALNAME + "/Overheat.ogg");

        // Startup noise.
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MAINFRAME_INTERNALNAME + "/Start.ogg");

        // Work noises.
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MAINFRAME_INTERNALNAME + "/Work1.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MAINFRAME_INTERNALNAME + "/Work2.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MAINFRAME_INTERNALNAME + "/Work3.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MAINFRAME_INTERNALNAME + "/Work4.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MAINFRAME_INTERNALNAME + "/Work5.ogg");
    }
}

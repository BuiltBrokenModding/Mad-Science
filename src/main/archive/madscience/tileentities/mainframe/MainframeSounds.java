package madscience.tileentities.mainframe;

import net.minecraftforge.client.event.sound.SoundLoadEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import madscience.MadFurnaces;
import madscience.MadScience;

public class MainframeSounds
{
    // Computer Mainframe
    public static final String MAINFRAME_BREAK = MadScience.ID + ":" + MadFurnaces.MAINFRAME_INTERNALNAME + ".Break";
    public static final String MAINFRAME_FINISH = MadScience.ID + ":" + MadFurnaces.MAINFRAME_INTERNALNAME + ".Finish";
    public static final String MAINFRAME_IDLE = MadScience.ID + ":" + MadFurnaces.MAINFRAME_INTERNALNAME + ".Idle";
    public static final String MAINFRAME_OFF = MadScience.ID + ":" + MadFurnaces.MAINFRAME_INTERNALNAME + ".Off";
    public static final String MAINFRAME_ON = MadScience.ID + ":" + MadFurnaces.MAINFRAME_INTERNALNAME + ".On";
    public static final String MAINFRAME_OVERHEAT = MadScience.ID + ":" + MadFurnaces.MAINFRAME_INTERNALNAME + ".Overheat";
    public static final String MAINFRAME_START = MadScience.ID + ":" + MadFurnaces.MAINFRAME_INTERNALNAME + ".Start";
    public static final String MAINFRAME_WORK = MadScience.ID + ":" + MadFurnaces.MAINFRAME_INTERNALNAME + ".Work";
    
    @SideOnly(Side.CLIENT)
    public static void init(SoundLoadEvent event)
    {
        // Break from overheating.
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MAINFRAME_INTERNALNAME + "/Break.ogg");

        // Finish processing.
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MAINFRAME_INTERNALNAME + "/Finish.ogg");

        // Idle running sound.
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MAINFRAME_INTERNALNAME + "/Idle.ogg");

        // Turning off noise.
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MAINFRAME_INTERNALNAME + "/Off.ogg");

        // Turning on noise.
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MAINFRAME_INTERNALNAME + "/On.ogg");

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

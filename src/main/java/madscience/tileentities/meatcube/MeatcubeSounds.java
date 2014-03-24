package madscience.tileentities.meatcube;

import madscience.MadFurnaces;
import madscience.MadScience;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MeatcubeSounds
{
    // Meatcube
    public static final String MEATCUBE_REGROW = MadScience.ID + ":" + MadFurnaces.MEATCUBE_INTERNALNAME + ".Belly";
    public static final String MEATCUBE_HEARTBEAT = MadScience.ID + ":" + MadFurnaces.MEATCUBE_INTERNALNAME + ".Heartbeat";
    public static final String MEATCUBE_IDLE = MadScience.ID + ":" + MadFurnaces.MEATCUBE_INTERNALNAME + ".Idle";
    public static final String MEATCUBE_MEATSLAP = MadScience.ID + ":" + MadFurnaces.MEATCUBE_INTERNALNAME + ".Meatslap";
    public static final String MEATCUBE_MOOANING = MadScience.ID + ":" + MadFurnaces.MEATCUBE_INTERNALNAME + ".Moo";
    
    @SideOnly(Side.CLIENT)
    public static void init(SoundLoadEvent event)
    {
        // Regrowing noises.
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MEATCUBE_INTERNALNAME + "/Belly1.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MEATCUBE_INTERNALNAME + "/Belly2.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MEATCUBE_INTERNALNAME + "/Belly3.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MEATCUBE_INTERNALNAME + "/Belly4.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MEATCUBE_INTERNALNAME + "/Belly5.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MEATCUBE_INTERNALNAME + "/Belly6.ogg");

        // Heartbeat noises.
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MEATCUBE_INTERNALNAME + "/Heartbeat1.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MEATCUBE_INTERNALNAME + "/Heartbeat2.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MEATCUBE_INTERNALNAME + "/Heartbeat3.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MEATCUBE_INTERNALNAME + "/Heartbeat4.ogg");

        // Idle noises.
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MEATCUBE_INTERNALNAME + "/Idle1.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MEATCUBE_INTERNALNAME + "/Idle2.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MEATCUBE_INTERNALNAME + "/Idle3.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MEATCUBE_INTERNALNAME + "/Idle4.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MEATCUBE_INTERNALNAME + "/Idle5.ogg");

        // Meat slapping noises.
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MEATCUBE_INTERNALNAME + "/Meatslap1.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MEATCUBE_INTERNALNAME + "/Meatslap2.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MEATCUBE_INTERNALNAME + "/Meatslap3.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MEATCUBE_INTERNALNAME + "/Meatslap4.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MEATCUBE_INTERNALNAME + "/Meatslap5.ogg");

        // Mooing noises.
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MEATCUBE_INTERNALNAME + "/Moo1.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MEATCUBE_INTERNALNAME + "/Moo2.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MEATCUBE_INTERNALNAME + "/Moo3.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MEATCUBE_INTERNALNAME + "/Moo4.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MEATCUBE_INTERNALNAME + "/Moo5.ogg");
    }
}

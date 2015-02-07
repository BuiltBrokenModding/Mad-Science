package madscience.tileentities.thermosonicbonder;

import madscience.MadFurnaces;
import madscience.MadScience;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ThermosonicBonderSounds
{
    // Thermosonic Bonder
    public static final String THERMOSONICBONDER_IDLE = MadScience.ID + ":" + MadFurnaces.THERMOSONIC_INTERNALNAME + ".Idle";
    public static final String THERMOSONICBONDER_LASERSTART = MadScience.ID + ":" + MadFurnaces.THERMOSONIC_INTERNALNAME + ".LaserStart";
    public static final String THERMOSONICBONDER_LASERSTOP = MadScience.ID + ":" + MadFurnaces.THERMOSONIC_INTERNALNAME + ".LaserStop";
    public static final String THERMOSONICBONDER_LASERWORK = MadScience.ID + ":" + MadFurnaces.THERMOSONIC_INTERNALNAME + ".LaserWorking";
    public static final String THERMOSONICBONDER_STAMP = MadScience.ID + ":" + MadFurnaces.THERMOSONIC_INTERNALNAME + ".Stamp";
    
    @SideOnly(Side.CLIENT)
    public static void init(SoundLoadEvent event)
    {
        // Idle
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.THERMOSONIC_INTERNALNAME + "/Idle.ogg");

        // Laser Start
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.THERMOSONIC_INTERNALNAME + "/LaserStart.ogg");

        // Laser Stop
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.THERMOSONIC_INTERNALNAME + "/LaserStop.ogg");

        // Laser Working
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.THERMOSONIC_INTERNALNAME + "/LaserWorking.ogg");

        // Stamp (output slot being filled)
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.THERMOSONIC_INTERNALNAME + "/Stamp1.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.THERMOSONIC_INTERNALNAME + "/Stamp2.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.THERMOSONIC_INTERNALNAME + "/Stamp3.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.THERMOSONIC_INTERNALNAME + "/Stamp4.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.THERMOSONIC_INTERNALNAME + "/Stamp5.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.THERMOSONIC_INTERNALNAME + "/Stamp6.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.THERMOSONIC_INTERNALNAME + "/Stamp7.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.THERMOSONIC_INTERNALNAME + "/Stamp8.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.THERMOSONIC_INTERNALNAME + "/Stamp9.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.THERMOSONIC_INTERNALNAME + "/Stamp10.ogg");
    }
}

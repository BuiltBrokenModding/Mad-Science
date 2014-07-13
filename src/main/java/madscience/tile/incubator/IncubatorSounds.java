package madscience.tile.incubator;

import madscience.MadMachines;
import madscience.MadScience;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class IncubatorSounds
{
    // Genome Incubator
    static final String INCUBATOR_FINISH = MadScience.ID + ":" + MadMachines.INCUBATOR_INTERNALNAME + ".Finish";
    static final String INCUBATOR_START = MadScience.ID + ":" + MadMachines.INCUBATOR_INTERNALNAME + ".Start";
    static final String INCUBATOR_WORK = MadScience.ID + ":" + MadMachines.INCUBATOR_INTERNALNAME + ".Work";
    
    @SideOnly(Side.CLIENT)
    public static void init(SoundLoadEvent event)
    {
        // Finish creating mob egg.
        event.manager.addSound(MadScience.ID + ":" + MadMachines.INCUBATOR_INTERNALNAME + "/Finish.ogg");

        // Start creating mob egg.
        event.manager.addSound(MadScience.ID + ":" + MadMachines.INCUBATOR_INTERNALNAME + "/Start.ogg");

        // Working noises.
        event.manager.addSound(MadScience.ID + ":" + MadMachines.INCUBATOR_INTERNALNAME + "/Work1.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadMachines.INCUBATOR_INTERNALNAME + "/Work2.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadMachines.INCUBATOR_INTERNALNAME + "/Work3.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadMachines.INCUBATOR_INTERNALNAME + "/Work4.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadMachines.INCUBATOR_INTERNALNAME + "/Work5.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadMachines.INCUBATOR_INTERNALNAME + "/Work6.ogg");
    }
}

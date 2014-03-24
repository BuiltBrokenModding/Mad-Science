package madscience.tileentities.incubator;

import madscience.MadFurnaces;
import madscience.MadScience;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class IncubatorSounds
{
    // Genome Incubator
    public static final String INCUBATOR_FINISH = MadScience.ID + ":" + MadFurnaces.INCUBATOR_INTERNALNAME + ".Finish";
    public static final String INCUBATOR_START = MadScience.ID + ":" + MadFurnaces.INCUBATOR_INTERNALNAME + ".Start";
    public static final String INCUBATOR_WORK = MadScience.ID + ":" + MadFurnaces.INCUBATOR_INTERNALNAME + ".Work";
    
    @SideOnly(Side.CLIENT)
    public static void init(SoundLoadEvent event)
    {
        // Finish creating mob egg.
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.INCUBATOR_INTERNALNAME + "/Finish.ogg");

        // Start creating mob egg.
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.INCUBATOR_INTERNALNAME + "/Start.ogg");

        // Working noises.
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.INCUBATOR_INTERNALNAME + "/Work1.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.INCUBATOR_INTERNALNAME + "/Work2.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.INCUBATOR_INTERNALNAME + "/Work3.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.INCUBATOR_INTERNALNAME + "/Work4.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.INCUBATOR_INTERNALNAME + "/Work5.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.INCUBATOR_INTERNALNAME + "/Work6.ogg");
    }
}

package madscience.tileentities.dataduplicator;

import madscience.MadFurnaces;
import madscience.MadScience;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class DataDuplicatorSounds
{
    // Data Reel Duplicator
    static final String DATADUPLICATOR_WORK = MadScience.ID + ":" + MadFurnaces.DATADUPLICATOR_INTERNALNAME + ".Work";
    static final String DATADUPLICATOR_IDLE = MadScience.ID + ":" + MadFurnaces.DATADUPLICATOR_INTERNALNAME + ".Idle";
    static final String DATADUPLICATOR_FINISH = MadScience.ID + ":" + MadFurnaces.DATADUPLICATOR_INTERNALNAME + ".Finish";
    static final String DATADUPLICATOR_START = MadScience.ID + ":" + MadFurnaces.DATADUPLICATOR_INTERNALNAME + ".Start";
    
    @SideOnly(Side.CLIENT)
    public static void init(SoundLoadEvent event)
    {
        // Idle
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.DATADUPLICATOR_INTERNALNAME + "/Idle.ogg");

        // Work
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.DATADUPLICATOR_INTERNALNAME + "/Work.ogg");

        // Start
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.DATADUPLICATOR_INTERNALNAME + "/Start.ogg");

        // Finish
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.DATADUPLICATOR_INTERNALNAME + "/Finish.ogg");
    }
}

package madscience.tile.dataduplicator;

import madscience.MadFurnaces;
import madscience.factory.mod.MadMod;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class DataDuplicatorSounds
{
    // Data Reel Duplicator
    static final String DATADUPLICATOR_WORK = MadMod.ID + ":" + MadFurnaces.DATADUPLICATOR_INTERNALNAME + ".Work";
    static final String DATADUPLICATOR_IDLE = MadMod.ID + ":" + MadFurnaces.DATADUPLICATOR_INTERNALNAME + ".Idle";
    static final String DATADUPLICATOR_FINISH = MadMod.ID + ":" + MadFurnaces.DATADUPLICATOR_INTERNALNAME + ".Finish";
    static final String DATADUPLICATOR_START = MadMod.ID + ":" + MadFurnaces.DATADUPLICATOR_INTERNALNAME + ".Start";
    
    @SideOnly(Side.CLIENT)
    public static void init(SoundLoadEvent event)
    {
        // Idle
        event.manager.addSound(MadMod.ID + ":" + MadFurnaces.DATADUPLICATOR_INTERNALNAME + "/Idle.ogg");

        // Work
        event.manager.addSound(MadMod.ID + ":" + MadFurnaces.DATADUPLICATOR_INTERNALNAME + "/Work.ogg");

        // Start
        event.manager.addSound(MadMod.ID + ":" + MadFurnaces.DATADUPLICATOR_INTERNALNAME + "/Start.ogg");

        // Finish
        event.manager.addSound(MadMod.ID + ":" + MadFurnaces.DATADUPLICATOR_INTERNALNAME + "/Finish.ogg");
    }
}

package madscience.tile.sequencer;

import madscience.MadFurnaces;
import madscience.factory.mod.MadMod;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SequencerSounds
{
    // Genome Sequencer
    static final String SEQUENCER_FINISH = MadMod.ID + ":" + MadFurnaces.SEQUENCER_INTERNALNAME + ".Finish";
    static final String SEQUENCER_START = MadMod.ID + ":" + MadFurnaces.SEQUENCER_INTERNALNAME + ".Start";
    static final String SEQUENCER_WORK = MadMod.ID + ":" + MadFurnaces.SEQUENCER_INTERNALNAME + ".Work";
    
    @SideOnly(Side.CLIENT)
    public static void init(SoundLoadEvent event)
    {
        // Finish sequencing genome.
        event.manager.addSound(MadMod.ID + ":" + MadFurnaces.SEQUENCER_INTERNALNAME + "/Finish.ogg");

        // Start sequencing genome.
        event.manager.addSound(MadMod.ID + ":" + MadFurnaces.SEQUENCER_INTERNALNAME + "/Start.ogg");

        // Working on genome.
        event.manager.addSound(MadMod.ID + ":" + MadFurnaces.SEQUENCER_INTERNALNAME + "/Work1.ogg");
        event.manager.addSound(MadMod.ID + ":" + MadFurnaces.SEQUENCER_INTERNALNAME + "/Work2.ogg");
        event.manager.addSound(MadMod.ID + ":" + MadFurnaces.SEQUENCER_INTERNALNAME + "/Work3.ogg");
        event.manager.addSound(MadMod.ID + ":" + MadFurnaces.SEQUENCER_INTERNALNAME + "/Work4.ogg");
        event.manager.addSound(MadMod.ID + ":" + MadFurnaces.SEQUENCER_INTERNALNAME + "/Work5.ogg");
        event.manager.addSound(MadMod.ID + ":" + MadFurnaces.SEQUENCER_INTERNALNAME + "/Work6.ogg");
        event.manager.addSound(MadMod.ID + ":" + MadFurnaces.SEQUENCER_INTERNALNAME + "/Work7.ogg");
        event.manager.addSound(MadMod.ID + ":" + MadFurnaces.SEQUENCER_INTERNALNAME + "/Work8.ogg");
    }
}

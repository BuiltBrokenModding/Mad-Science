package madscience.tile.cncmachine;

import madscience.MadMachines;
import madscience.MadScience;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CnCMachineSounds
{
    static final String CNCMACHINE_FINISHCRUSHING = MadScience.ID + ":" + MadMachines.CNCMACHINE_INTERNALNAME + ".FinishCrushing";
    static final String CNCMACHINE_FINISHED = MadScience.ID + ":" + MadMachines.CNCMACHINE_INTERNALNAME + ".Finished";
    static final String CNCMACHINE_INSERTIRONBLOCK = MadScience.ID + ":" + MadMachines.CNCMACHINE_INTERNALNAME + ".InsertIronBlock";
    static final String CNCMACHINE_INVALIDBOOK = MadScience.ID + ":" + MadMachines.CNCMACHINE_INTERNALNAME + ".InvalidBook";
    static final String CNCMACHINE_POWERON = MadScience.ID + ":" + MadMachines.CNCMACHINE_INTERNALNAME + ".PowerOn";
    static final String CNCMACHINE_PRESS = MadScience.ID + ":" + MadMachines.CNCMACHINE_INTERNALNAME + ".Press";
    static final String CNCMACHINE_PRESSINGWORK = MadScience.ID + ":" + MadMachines.CNCMACHINE_INTERNALNAME + ".PressingWork";
    static final String CNCMACHINE_PRESSSTOP = MadScience.ID + ":" + MadMachines.CNCMACHINE_INTERNALNAME + ".PressStop";
    static final String CNCMACHINE_WATERFLOW = MadScience.ID + ":" + MadMachines.CNCMACHINE_INTERNALNAME + ".WaterFlow";
    static final String CNCMACHINE_WATERWORK = MadScience.ID + ":" + MadMachines.CNCMACHINE_INTERNALNAME + ".WaterWork";

    @SideOnly(Side.CLIENT)
    public static void init(SoundLoadEvent event)
    {
        // Finish crushing iron block sound.
        event.manager.addSound(MadScience.ID + ":" + MadMachines.CNCMACHINE_INTERNALNAME + "/FinishCrushing.ogg");
        
        // Machine has finished cutting the weapon component from the iron block/
        event.manager.addSound(MadScience.ID + ":" + MadMachines.CNCMACHINE_INTERNALNAME + "/Finished.ogg");
        
        // Played when a block of iron has been inserted into the machine.
        event.manager.addSound(MadScience.ID + ":" + MadMachines.CNCMACHINE_INTERNALNAME + "/InsertIronBlock.ogg");
        
        // Played when the machines is activated with an invalid book inside of it.
        event.manager.addSound(MadScience.ID + ":" + MadMachines.CNCMACHINE_INTERNALNAME + "/InvalidBook.ogg");
        
        // Played when redstone signal is applied to the machine when it has power stored.
        event.manager.addSound(MadScience.ID + ":" + MadMachines.CNCMACHINE_INTERNALNAME + "/PowerOn.ogg");
        
        // Sound of iron blocks being crushed into a smaller shape before cutting.
        event.manager.addSound(MadScience.ID + ":" + MadMachines.CNCMACHINE_INTERNALNAME + "/Press1.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadMachines.CNCMACHINE_INTERNALNAME + "/Press2.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadMachines.CNCMACHINE_INTERNALNAME + "/Press3.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadMachines.CNCMACHINE_INTERNALNAME + "/Press4.ogg");
        
        // Background noise played while the iron block is being crushed into a smaller form.
        event.manager.addSound(MadScience.ID + ":" + MadMachines.CNCMACHINE_INTERNALNAME + "/PressingWork.ogg");
        
        // Played when the crushing of iron block has stopped and press starts to go back up.
        event.manager.addSound(MadScience.ID + ":" + MadMachines.CNCMACHINE_INTERNALNAME + "/PressStop.ogg");
        
        // Played while the water jets are operational and cutting on the iron block.
        event.manager.addSound(MadScience.ID + ":" + MadMachines.CNCMACHINE_INTERNALNAME + "/WaterFlow.ogg");
        
        // Background noise played while water is being sprayed onto the block and it is being cut.
        event.manager.addSound(MadScience.ID + ":" + MadMachines.CNCMACHINE_INTERNALNAME + "/WaterWork.ogg");
    }
}

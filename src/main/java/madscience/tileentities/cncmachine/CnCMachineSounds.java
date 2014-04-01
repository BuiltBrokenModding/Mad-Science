package madscience.tileentities.cncmachine;

import madscience.MadFurnaces;
import madscience.MadScience;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CnCMachineSounds
{
    // Needle CnCMachine
    //public static final String CNCMACHINE_IDLE = MadScience.ID + ":" + MadFurnaces.CNCMACHINE_INTERNALNAME + ".Idle";
    
    @SideOnly(Side.CLIENT)
    public static void init(SoundLoadEvent event)
    {
        // Cleaning needle sound.
        //event.manager.addSound(MadScience.ID + ":" + MadFurnaces.CNCMACHINE_INTERNALNAME + "/Idle.ogg");
    }
}

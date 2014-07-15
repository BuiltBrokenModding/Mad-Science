package madscience.tile.cryotube;

import madscience.MadFurnaces;
import madscience.MadScience;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CryotubeSounds
{
    // Cryogenic Tube
    static final String CRYOTUBE_STILLBIRTH = MadScience.ID + ":" + MadFurnaces.CRYOTUBE_INTERNALNAME + ".Stillbirth";
    static final String CRYOTUBE_WORK = MadScience.ID + ":" + MadFurnaces.CRYOTUBE_INTERNALNAME + ".Work";
    static final String CRYOTUBE_HATCH = MadScience.ID + ":" + MadFurnaces.CRYOTUBE_INTERNALNAME + ".Hatch";
    static final String CRYOTUBE_HATCHING = MadScience.ID + ":" + MadFurnaces.CRYOTUBE_INTERNALNAME + ".Hatching";
    static final String CRYOTUBE_CRACKEGG = MadScience.ID + ":" + MadFurnaces.CRYOTUBE_INTERNALNAME + ".CrackEgg";
    static final String CRYOTUBE_IDLE = MadScience.ID + ":" + MadFurnaces.CRYOTUBE_INTERNALNAME + ".Idle";
    static final String CRYOTUBE_OFF = MadScience.ID + ":" + MadFurnaces.CRYOTUBE_INTERNALNAME + ".Off";
    
    @SideOnly(Side.CLIENT)
    public static void init(SoundLoadEvent event)
    {
        // Stillbirth
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.CRYOTUBE_INTERNALNAME + "/Stillbirth.ogg");

        // Work
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.CRYOTUBE_INTERNALNAME + "/Work.ogg");

        // Cracking Egg
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.CRYOTUBE_INTERNALNAME + "/CrackEgg.ogg");

        // Hatching Loop
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.CRYOTUBE_INTERNALNAME + "/Hatching.ogg");

        // Idle working sound when cannot smelt.
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.CRYOTUBE_INTERNALNAME + "/Idle.ogg");

        // Sound of cryotube being turned off.
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.CRYOTUBE_INTERNALNAME + "/Off.ogg");

        // Hatching Random Noises
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.CRYOTUBE_INTERNALNAME + "/Hatch1.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.CRYOTUBE_INTERNALNAME + "/Hatch2.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.CRYOTUBE_INTERNALNAME + "/Hatch3.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.CRYOTUBE_INTERNALNAME + "/Hatch4.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.CRYOTUBE_INTERNALNAME + "/Hatch5.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.CRYOTUBE_INTERNALNAME + "/Hatch6.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.CRYOTUBE_INTERNALNAME + "/Hatch7.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.CRYOTUBE_INTERNALNAME + "/Hatch8.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.CRYOTUBE_INTERNALNAME + "/Hatch9.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.CRYOTUBE_INTERNALNAME + "/Hatch10.ogg");
    }
}

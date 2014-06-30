package madscience.mobs.abomination;

import madscience.MadMobs;
import madscience.MadScience;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class AbominationSounds
{
    
    static final String ABOMINATION_HISS = MadScience.ID + ":" + MadMobs.GMO_ABOMINATION_INTERNALNAME + ".hiss";
    static final String ABOMINATION_GROWL = MadScience.ID + ":" + MadMobs.GMO_ABOMINATION_INTERNALNAME + ".growl";
    static final String ABOMINATION_DEATH = MadScience.ID + ":" + MadMobs.GMO_ABOMINATION_INTERNALNAME + ".death";
    static final String ABOMINATION_STEP = MadScience.ID + ":" + MadMobs.GMO_ABOMINATION_INTERNALNAME + ".step";
    static final String ABOMINATION_PAIN = MadScience.ID + ":" + MadMobs.GMO_ABOMINATION_INTERNALNAME + ".pain";
    public static final String ABOMINATION_EGG = MadScience.ID + ":" + MadMobs.GMO_ABOMINATION_INTERNALNAME + ".egg";
    public static final String ABOMINATION_HATCH = MadScience.ID + ":" + MadMobs.GMO_ABOMINATION_INTERNALNAME + ".egghatch";
    public static final String ABOMINATION_EGGPOP = MadScience.ID + ":" + MadMobs.GMO_ABOMINATION_INTERNALNAME + ".eggpop";
    
    @SideOnly(Side.CLIENT)
    public static void init(SoundLoadEvent event)
    {
        // monodemono(coolplanet3000@gmail.com)
        // The Abomination [Enderman + Spider]
        //event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_ABOMINATION_INTERNALNAME + "/attack.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_ABOMINATION_INTERNALNAME + "/death.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_ABOMINATION_INTERNALNAME + "/egg.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_ABOMINATION_INTERNALNAME + "/egghatch.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_ABOMINATION_INTERNALNAME + "/growl1.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_ABOMINATION_INTERNALNAME + "/growl2.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_ABOMINATION_INTERNALNAME + "/growl3.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_ABOMINATION_INTERNALNAME + "/hissl.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_ABOMINATION_INTERNALNAME + "/hiss2.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_ABOMINATION_INTERNALNAME + "/hiss3.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_ABOMINATION_INTERNALNAME + "/hiss4.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_ABOMINATION_INTERNALNAME + "/pain1.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_ABOMINATION_INTERNALNAME + "/pain2.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_ABOMINATION_INTERNALNAME + "/pain3.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_ABOMINATION_INTERNALNAME + "/step1.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_ABOMINATION_INTERNALNAME + "/step2.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_ABOMINATION_INTERNALNAME + "/step3.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_ABOMINATION_INTERNALNAME + "/step4.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_ABOMINATION_INTERNALNAME + "/eggpop.ogg");
    }
}

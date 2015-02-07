package madscience.mobs.woolycow;

import madscience.MadMobs;
import madscience.MadScience;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class WoolyCowSounds
{
    // Bart74(bart.74@hotmail.fr)
    // Wooly cow [Cow + Sheep]
    public static final String WOOLYCOW_HURT = MadScience.ID + ":" + MadMobs.GMO_WOOLYCOW_INTERNALNAME + ".hurt";
    public static final String WOOLYCOW_SAY = MadScience.ID + ":" + MadMobs.GMO_WOOLYCOW_INTERNALNAME + ".say";
    public static final String WOOLYCOW_STEP = MadScience.ID + ":" + MadMobs.GMO_WOOLYCOW_INTERNALNAME + ".step";
    
    @SideOnly(Side.CLIENT)
    public static void init(SoundLoadEvent event)
    {
        // Bart74(bart.74@hotmail.fr)
        // Wooly cow [Cow + Sheep]
        event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_CREEPERCOW_INTERNALNAME + "/hurt1.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_CREEPERCOW_INTERNALNAME + "/hurt2.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_CREEPERCOW_INTERNALNAME + "/hurt3.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_CREEPERCOW_INTERNALNAME + "/say1.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_CREEPERCOW_INTERNALNAME + "/say2.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_CREEPERCOW_INTERNALNAME + "/say3.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_CREEPERCOW_INTERNALNAME + "/say4.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_CREEPERCOW_INTERNALNAME + "/step1.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_CREEPERCOW_INTERNALNAME + "/step2.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_CREEPERCOW_INTERNALNAME + "/step3.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_CREEPERCOW_INTERNALNAME + "/step4.ogg");
    }
}

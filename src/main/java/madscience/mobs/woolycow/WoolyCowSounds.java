package madscience.mobs.woolycow;

import madscience.MadMobs;
import madscience.factory.mod.MadMod;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class WoolyCowSounds
{
    // Bart74(bart.74@hotmail.fr)
    // Wooly cow [Cow + Sheep]
    static final String WOOLYCOW_HURT = MadMod.ID + ":" + MadMobs.GMO_WOOLYCOW_INTERNALNAME + ".hurt";
    static final String WOOLYCOW_SAY = MadMod.ID + ":" + MadMobs.GMO_WOOLYCOW_INTERNALNAME + ".say";
    static final String WOOLYCOW_STEP = MadMod.ID + ":" + MadMobs.GMO_WOOLYCOW_INTERNALNAME + ".step";
    
    @SideOnly(Side.CLIENT)
    public static void init(SoundLoadEvent event)
    {
        // Bart74(bart.74@hotmail.fr)
        // Wooly cow [Cow + Sheep]
        event.manager.addSound(MadMod.ID + ":" + MadMobs.GMO_CREEPERCOW_INTERNALNAME + "/hurt1.ogg");
        event.manager.addSound(MadMod.ID + ":" + MadMobs.GMO_CREEPERCOW_INTERNALNAME + "/hurt2.ogg");
        event.manager.addSound(MadMod.ID + ":" + MadMobs.GMO_CREEPERCOW_INTERNALNAME + "/hurt3.ogg");
        event.manager.addSound(MadMod.ID + ":" + MadMobs.GMO_CREEPERCOW_INTERNALNAME + "/say1.ogg");
        event.manager.addSound(MadMod.ID + ":" + MadMobs.GMO_CREEPERCOW_INTERNALNAME + "/say2.ogg");
        event.manager.addSound(MadMod.ID + ":" + MadMobs.GMO_CREEPERCOW_INTERNALNAME + "/say3.ogg");
        event.manager.addSound(MadMod.ID + ":" + MadMobs.GMO_CREEPERCOW_INTERNALNAME + "/say4.ogg");
        event.manager.addSound(MadMod.ID + ":" + MadMobs.GMO_CREEPERCOW_INTERNALNAME + "/step1.ogg");
        event.manager.addSound(MadMod.ID + ":" + MadMobs.GMO_CREEPERCOW_INTERNALNAME + "/step2.ogg");
        event.manager.addSound(MadMod.ID + ":" + MadMobs.GMO_CREEPERCOW_INTERNALNAME + "/step3.ogg");
        event.manager.addSound(MadMod.ID + ":" + MadMobs.GMO_CREEPERCOW_INTERNALNAME + "/step4.ogg");
    }
}

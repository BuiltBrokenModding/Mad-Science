package madscience.mobs.werewolf;

import madscience.MadMobs;
import madscience.MadScience;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class WerewolfMobSounds
{
    // Werewolf [Villager + Wolf]
    static final String WEREWOLF_ATTACK = MadScience.ID + ":" + MadMobs.GMO_WEREWOLF_INTERNALNAME + ".Attack";
    static final String WEREWOLF_IDLE = MadScience.ID + ":" + MadMobs.GMO_WEREWOLF_INTERNALNAME + ".Death";
    static final String WEREWOLF_DEATH = MadScience.ID + ":" + MadMobs.GMO_WEREWOLF_INTERNALNAME + ".Snarl";
    
    @SideOnly(Side.CLIENT)
    public static void init(SoundLoadEvent event)
    {
        // Werewolf
        event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_WEREWOLF_INTERNALNAME + "/Attack1.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_WEREWOLF_INTERNALNAME + "/Attack2.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_WEREWOLF_INTERNALNAME + "/Death.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_WEREWOLF_INTERNALNAME + "/Snarl1.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_WEREWOLF_INTERNALNAME + "/Snarl2.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_WEREWOLF_INTERNALNAME + "/Snarl3.ogg");
    }
}

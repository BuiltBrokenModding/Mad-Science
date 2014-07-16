package madscience.mobs.creepercow;

import madscience.MadMobs;
import madscience.factory.mod.MadMod;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CreeperCowSounds
{
    // Creeper Cow [Creeper + Cow]
    static final String CREEPERCOW_ATTACK = MadMod.ID + ":" + MadMobs.GMO_CREEPERCOW_INTERNALNAME + ".Attack";
    
    @SideOnly(Side.CLIENT)
    public static void init(SoundLoadEvent event)
    {
        // Creeper Cow [Creeper + Cow]
        event.manager.addSound(MadMod.ID + ":" + MadMobs.GMO_ENDERSQUID_INTERNALNAME + "/Attack.ogg");
    }
}

package madscience.tile.cryofreezer;

import madscience.MadFurnaces;
import madscience.factory.mod.MadMod;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CryofreezerSounds
{
    // Cryogenic Freezer
    static final String CRYOFREEZER_IDLE = MadMod.ID + ":" + MadFurnaces.CRYOFREEZER_INTERNALNAME + ".Idle";
    
    @SideOnly(Side.CLIENT)
    public static void init(SoundLoadEvent event)
    {
        event.manager.addSound(MadMod.ID + ":" + MadFurnaces.CRYOFREEZER_INTERNALNAME + "/Idle.ogg");
    }
}

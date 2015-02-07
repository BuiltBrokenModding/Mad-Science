package madscience.tileentities.cryofreezer;

import madscience.MadFurnaces;
import madscience.MadScience;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CryofreezerSounds
{
    // Cryogenic Freezer
    public static final String CRYOFREEZER_IDLE = MadScience.ID + ":" + MadFurnaces.CRYOFREEZER_INTERNALNAME + ".Idle";
    
    @SideOnly(Side.CLIENT)
    public static void init(SoundLoadEvent event)
    {
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.CRYOFREEZER_INTERNALNAME + "/Idle.ogg");
    }
}

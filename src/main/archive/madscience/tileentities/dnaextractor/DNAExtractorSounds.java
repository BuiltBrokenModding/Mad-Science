package madscience.tileentities.dnaextractor;

import madscience.MadFurnaces;
import madscience.MadScience;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class DNAExtractorSounds
{
    // DNA Extractor
    public static final String DNAEXTRACTOR_FINISH = MadScience.ID + ":" + MadFurnaces.DNAEXTRACTOR_INTERNALNAME + ".Finish";
    public static final String DNAEXTRACTOR_IDLE = MadScience.ID + ":" + MadFurnaces.DNAEXTRACTOR_INTERNALNAME + ".Idle";
    
    @SideOnly(Side.CLIENT)
    public static void init(SoundLoadEvent event)
    {
        // Finish extracting needle.
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.DNAEXTRACTOR_INTERNALNAME + "/Finish.ogg");

        // Idle sound while extracting DNA.
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.DNAEXTRACTOR_INTERNALNAME + "/Idle.ogg");
    }
}

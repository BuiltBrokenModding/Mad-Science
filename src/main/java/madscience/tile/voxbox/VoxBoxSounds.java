package madscience.tile.voxbox;

import madscience.MadFurnaces;
import madscience.MadScience;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class VoxBoxSounds
{
    @SideOnly(Side.CLIENT)
    public static void init(SoundLoadEvent event)
    {
        // Add all the sounds to our game client that we have registered in the VOX sound registry.
        for (VoxBoxSoundItem voxSound : VoxBoxSoundRegistry.voxWords)
        {
            // MadScience.logger.info(s.internalName);
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.VOXBOX_INTERNALNAME + "/" + voxSound.fileName);
        }
    }
}

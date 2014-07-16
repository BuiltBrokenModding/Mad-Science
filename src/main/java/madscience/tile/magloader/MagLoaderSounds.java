package madscience.tile.magloader;

import madscience.MadFurnaces;
import madscience.factory.mod.MadMod;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MagLoaderSounds
{
    // Played when a magazine is inserted into the input slot for loading.
    static final String MAGLOADER_INSERTMAGAZINE = MadMod.ID + ":" + MadFurnaces.MAGLOADER_INTERNALNAME + ".InsertMagazine";

    // Bullets being loading into magazine sounds to play during machine progress.
    static final String MAGLOADER_LOADING = MadMod.ID + ":" + MadFurnaces.MAGLOADER_INTERNALNAME + ".Loading";

    // Played when the machine starts cooking the bullets into a loaded magazine.
    static final String MAGLOADER_PUSHSTART = MadMod.ID + ":" + MadFurnaces.MAGLOADER_INTERNALNAME + ".PushStart";

    // Played when the machine finishes loading a magazine.
    static final String MAGLOADER_PUSHSTOP = MadMod.ID + ":" + MadFurnaces.MAGLOADER_INTERNALNAME + ".PushStop";

    @SideOnly(Side.CLIENT)
    public static void init(SoundLoadEvent event)
    {
        event.manager.addSound(MadMod.ID + ":" + MadFurnaces.MAGLOADER_INTERNALNAME + "/InsertMagazine.ogg");
        event.manager.addSound(MadMod.ID + ":" + MadFurnaces.MAGLOADER_INTERNALNAME + "/PushStart.ogg");
        event.manager.addSound(MadMod.ID + ":" + MadFurnaces.MAGLOADER_INTERNALNAME + "/PushStep.ogg");
        event.manager.addSound(MadMod.ID + ":" + MadFurnaces.MAGLOADER_INTERNALNAME + "/PushStop.ogg");
        event.manager.addSound(MadMod.ID + ":" + MadFurnaces.MAGLOADER_INTERNALNAME + "/Loading1.ogg");
        event.manager.addSound(MadMod.ID + ":" + MadFurnaces.MAGLOADER_INTERNALNAME + "/Loading2.ogg");
        event.manager.addSound(MadMod.ID + ":" + MadFurnaces.MAGLOADER_INTERNALNAME + "/Loading3.ogg");
        event.manager.addSound(MadMod.ID + ":" + MadFurnaces.MAGLOADER_INTERNALNAME + "/Loading4.ogg");
        event.manager.addSound(MadMod.ID + ":" + MadFurnaces.MAGLOADER_INTERNALNAME + "/Loading5.ogg");
        event.manager.addSound(MadMod.ID + ":" + MadFurnaces.MAGLOADER_INTERNALNAME + "/Loading6.ogg");
    }
}

package madscience.items.needles;

import madscience.MadNeedles;
import madscience.MadScience;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NeedleSounds
{
    // Needles
    static final String NEEDLEITEM_STABPLAYER = MadScience.ID + ":" + MadNeedles.NEEDLE_ITEM_INTERNALNAME + ".Stabself";
    static final String NEEDLEITEM_STABMOB = MadScience.ID + ":" + MadNeedles.NEEDLE_ITEM_INTERNALNAME + ".Stab";
    
    @SideOnly(Side.CLIENT)
    public static void init(SoundLoadEvent event)
    {
        // Needles (of all types!)
        event.manager.addSound(MadScience.ID + ":" + MadNeedles.NEEDLE_ITEM_INTERNALNAME + "/Stab.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadNeedles.NEEDLE_ITEM_INTERNALNAME + "/Stabself.ogg");
    }
}

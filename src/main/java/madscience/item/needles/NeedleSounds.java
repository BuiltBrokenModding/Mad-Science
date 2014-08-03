package madscience.item.needles;

import madscience.MadNeedles;
import madscience.factory.mod.MadMod;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NeedleSounds
{
    // Needles
    static final String NEEDLEITEM_STABPLAYER = MadMod.ID + ":" + MadNeedles.NEEDLE_ITEM_INTERNALNAME + ".Stabself";
    static final String NEEDLEITEM_STABMOB = MadMod.ID + ":" + MadNeedles.NEEDLE_ITEM_INTERNALNAME + ".Stab";
    
    @SideOnly(Side.CLIENT)
    public static void init(SoundLoadEvent event)
    {
        // Needles (of all types!)
        event.manager.addSound(MadMod.ID + ":" + MadNeedles.NEEDLE_ITEM_INTERNALNAME + "/Stab.ogg");
        event.manager.addSound(MadMod.ID + ":" + MadNeedles.NEEDLE_ITEM_INTERNALNAME + "/Stabself.ogg");
    }
}

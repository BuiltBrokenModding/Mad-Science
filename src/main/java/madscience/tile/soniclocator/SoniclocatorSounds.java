package madscience.tile.soniclocator;

import madscience.MadFurnaces;
import madscience.factory.mod.MadMod;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SoniclocatorSounds
{
    // Soniclocator Device
    static final String SONICLOCATOR_PLACE = MadMod.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + ".Place";
    static final String SONICLOCATOR_IDLE = MadMod.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + ".Idle";
    static final String SONICLOCATOR_IDLECHARGED = MadMod.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + ".IdleCharged";
    static final String SONICLOCATOR_FINISH = MadMod.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + ".Finish";
    static final String SONICLOCATOR_EMPTY = MadMod.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + ".Empty";
    static final String SONICLOCATOR_THUMP = MadMod.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + ".Thump";
    static final String SONICLOCATOR_THUMPCHARGE = MadMod.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + ".ThumpCharge";
    static final String SONICLOCATOR_THUMPSTART = MadMod.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + ".ThumpStart";
    static final String SONICLOCATOR_COOLDOWNBEEP = MadMod.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + ".CooldownBeep";
    static final String SONICLOCATOR_COOLDOWN = MadMod.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + ".Cooldown";
    static final String SONICLOCATOR_EXPLODE = MadMod.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + ".Explode";
    
    
    @SideOnly(Side.CLIENT)
    public static void init(SoundLoadEvent event)
    {
        // Idle
        event.manager.addSound(MadMod.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + "/Idle.ogg");

        // Idle Charged
        event.manager.addSound(MadMod.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + "/IdleCharged.ogg");

        // Place
        event.manager.addSound(MadMod.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + "/Place.ogg");

        // Empty
        event.manager.addSound(MadMod.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + "/Empty.ogg");

        // Finish
        event.manager.addSound(MadMod.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + "/Finish.ogg");

        // Thump
        event.manager.addSound(MadMod.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + "/Thump1.ogg");
        event.manager.addSound(MadMod.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + "/Thump2.ogg");
        event.manager.addSound(MadMod.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + "/Thump3.ogg");
        event.manager.addSound(MadMod.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + "/Thump4.ogg");

        // Thump Charge
        event.manager.addSound(MadMod.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + "/ThumpCharge.ogg");

        // Thump Start
        event.manager.addSound(MadMod.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + "/ThumpStart.ogg");
        
        // Cooldown Beep
        event.manager.addSound(MadMod.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + "/CooldownBeep.ogg");
        
        // Cooldown
        event.manager.addSound(MadMod.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + "/Cooldown.ogg");
        
        // Explode
        event.manager.addSound(MadMod.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + "/Explode.ogg");
        
        // Overload
        //event.manager.addSound(MadMod.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + "/Overload.ogg");
    }
}

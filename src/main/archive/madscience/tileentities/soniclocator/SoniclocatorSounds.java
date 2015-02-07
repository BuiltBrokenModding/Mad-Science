package madscience.tileentities.soniclocator;

import madscience.MadFurnaces;
import madscience.MadScience;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SoniclocatorSounds
{
    // Soniclocator Device
    public static final String SONICLOCATOR_PLACE = MadScience.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + ".Place";
    public static final String SONICLOCATOR_IDLE = MadScience.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + ".Idle";
    public static final String SONICLOCATOR_IDLECHARGED = MadScience.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + ".IdleCharged";
    public static final String SONICLOCATOR_FINISH = MadScience.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + ".Finish";
    public static final String SONICLOCATOR_EMPTY = MadScience.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + ".Empty";
    public static final String SONICLOCATOR_THUMP = MadScience.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + ".Thump";
    public static final String SONICLOCATOR_THUMPCHARGE = MadScience.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + ".ThumpCharge";
    public static final String SONICLOCATOR_THUMPSTART = MadScience.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + ".ThumpStart";
    public static final String SONICLOCATOR_COOLDOWNBEEP = MadScience.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + ".CooldownBeep";
    public static final String SONICLOCATOR_COOLDOWN = MadScience.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + ".Cooldown";
    public static final String SONICLOCATOR_EXPLODE = MadScience.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + ".Explode";
    public static final String SONICLOCATOR_OVERLOAD = MadScience.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + ".Overload";
    
    @SideOnly(Side.CLIENT)
    public static void init(SoundLoadEvent event)
    {
        // Idle
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + "/Idle.ogg");

        // Idle Charged
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + "/IdleCharged.ogg");

        // Place
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + "/Place.ogg");

        // Empty
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + "/Empty.ogg");

        // Finish
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + "/Finish.ogg");

        // Thump
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + "/Thump1.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + "/Thump2.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + "/Thump3.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + "/Thump4.ogg");

        // Thump Charge
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + "/ThumpCharge.ogg");

        // Thump Start
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + "/ThumpStart.ogg");
        
        // Cooldown Beep
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + "/CooldownBeep.ogg");
        
        // Cooldown
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + "/Cooldown.ogg");
        
        // Explode
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + "/Explode.ogg");
        
        // Overload
        event.manager.addSound(MadScience.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + "/Overload.ogg");
    }
}

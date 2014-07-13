package madscience.tile.soniclocator;

import madscience.MadMachines;
import madscience.MadScience;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SoniclocatorSounds
{
    // Soniclocator Device
    static final String SONICLOCATOR_PLACE = MadScience.ID + ":" + MadMachines.SONICLOCATOR_INTERNALNAME + ".Place";
    static final String SONICLOCATOR_IDLE = MadScience.ID + ":" + MadMachines.SONICLOCATOR_INTERNALNAME + ".Idle";
    static final String SONICLOCATOR_IDLECHARGED = MadScience.ID + ":" + MadMachines.SONICLOCATOR_INTERNALNAME + ".IdleCharged";
    static final String SONICLOCATOR_FINISH = MadScience.ID + ":" + MadMachines.SONICLOCATOR_INTERNALNAME + ".Finish";
    static final String SONICLOCATOR_EMPTY = MadScience.ID + ":" + MadMachines.SONICLOCATOR_INTERNALNAME + ".Empty";
    static final String SONICLOCATOR_THUMP = MadScience.ID + ":" + MadMachines.SONICLOCATOR_INTERNALNAME + ".Thump";
    static final String SONICLOCATOR_THUMPCHARGE = MadScience.ID + ":" + MadMachines.SONICLOCATOR_INTERNALNAME + ".ThumpCharge";
    static final String SONICLOCATOR_THUMPSTART = MadScience.ID + ":" + MadMachines.SONICLOCATOR_INTERNALNAME + ".ThumpStart";
    static final String SONICLOCATOR_COOLDOWNBEEP = MadScience.ID + ":" + MadMachines.SONICLOCATOR_INTERNALNAME + ".CooldownBeep";
    static final String SONICLOCATOR_COOLDOWN = MadScience.ID + ":" + MadMachines.SONICLOCATOR_INTERNALNAME + ".Cooldown";
    static final String SONICLOCATOR_EXPLODE = MadScience.ID + ":" + MadMachines.SONICLOCATOR_INTERNALNAME + ".Explode";
    
    
    @SideOnly(Side.CLIENT)
    public static void init(SoundLoadEvent event)
    {
        // Idle
        event.manager.addSound(MadScience.ID + ":" + MadMachines.SONICLOCATOR_INTERNALNAME + "/Idle.ogg");

        // Idle Charged
        event.manager.addSound(MadScience.ID + ":" + MadMachines.SONICLOCATOR_INTERNALNAME + "/IdleCharged.ogg");

        // Place
        event.manager.addSound(MadScience.ID + ":" + MadMachines.SONICLOCATOR_INTERNALNAME + "/Place.ogg");

        // Empty
        event.manager.addSound(MadScience.ID + ":" + MadMachines.SONICLOCATOR_INTERNALNAME + "/Empty.ogg");

        // Finish
        event.manager.addSound(MadScience.ID + ":" + MadMachines.SONICLOCATOR_INTERNALNAME + "/Finish.ogg");

        // Thump
        event.manager.addSound(MadScience.ID + ":" + MadMachines.SONICLOCATOR_INTERNALNAME + "/Thump1.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadMachines.SONICLOCATOR_INTERNALNAME + "/Thump2.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadMachines.SONICLOCATOR_INTERNALNAME + "/Thump3.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadMachines.SONICLOCATOR_INTERNALNAME + "/Thump4.ogg");

        // Thump Charge
        event.manager.addSound(MadScience.ID + ":" + MadMachines.SONICLOCATOR_INTERNALNAME + "/ThumpCharge.ogg");

        // Thump Start
        event.manager.addSound(MadScience.ID + ":" + MadMachines.SONICLOCATOR_INTERNALNAME + "/ThumpStart.ogg");
        
        // Cooldown Beep
        event.manager.addSound(MadScience.ID + ":" + MadMachines.SONICLOCATOR_INTERNALNAME + "/CooldownBeep.ogg");
        
        // Cooldown
        event.manager.addSound(MadScience.ID + ":" + MadMachines.SONICLOCATOR_INTERNALNAME + "/Cooldown.ogg");
        
        // Explode
        event.manager.addSound(MadScience.ID + ":" + MadMachines.SONICLOCATOR_INTERNALNAME + "/Explode.ogg");
        
        // Overload
        //event.manager.addSound(MadScience.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + "/Overload.ogg");
    }
}

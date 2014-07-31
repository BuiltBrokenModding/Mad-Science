package madscience;

import java.util.Iterator;

import madscience.factory.MadTileEntityFactory;
import madscience.factory.tileentity.MadTileEntityFactoryProduct;
import madscience.items.needles.NeedleSounds;
import madscience.items.weapons.pulserifle.PulseRifleSounds;
import madscience.mobs.abomination.AbominationSounds;
import madscience.mobs.creepercow.CreeperCowSounds;
import madscience.mobs.werewolf.WerewolfMobSounds;
import madscience.mobs.woolycow.WoolyCowSounds;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MadSounds
{
    @SideOnly(Side.CLIENT)
    @ForgeSubscribe
    public void onSoundLoad(SoundLoadEvent event) // NO_UCD (unused code)
    {
        // Grab an iterable array of all registered machines.
        Iterable<MadTileEntityFactoryProduct> registeredMachines = MadTileEntityFactory.instance().getMachineInfoList();
        for (Iterator iterator = registeredMachines.iterator(); iterator.hasNext();)
        {
            MadTileEntityFactoryProduct registeredMachine = (MadTileEntityFactoryProduct) iterator.next();
            if (registeredMachine != null)
            {
                // Grab processed list of filenames of sounds that need to be registered with Forge/MC.
                String[] unregisteredSounds = registeredMachine.loadSoundArchive();
                if (unregisteredSounds == null)
                {
                    continue;
                }
                
                // Loop through the filenames and register them.
                for (String unregisteredSound: unregisteredSounds)
                {
                    event.manager.addSound(unregisteredSound);
                    //MadScience.logger.info("[" + registeredMachine.getMachineName() + "]Registering Sound:" + unregisteredSound);
                }
            }
        }

        // ----
        // MOBS
        // ----

        // Werewolf
        WerewolfMobSounds.init(event);

        // Creeper Cow
        CreeperCowSounds.init(event);

        // Wooly Cow
        WoolyCowSounds.init(event);

        // Abomination
        AbominationSounds.init(event);

        // Needles
        NeedleSounds.init(event);

        // -------------
        // TILE ENTITIES
        // -------------

        // M41A Pulse Rifle
        PulseRifleSounds.init(event);
    }
}

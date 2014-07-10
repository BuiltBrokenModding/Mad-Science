package madscience;

import java.util.Iterator;
import java.util.logging.Level;

import madscience.factory.MadTileEntityFactory;
import madscience.factory.MadTileEntityFactoryProduct;
import madscience.factory.tileentity.MadTileEntityRendererTemplate;
import madscience.items.needles.NeedleSounds;
import madscience.items.weapons.pulserifle.PulseRifleSounds;
import madscience.mobs.abomination.AbominationSounds;
import madscience.mobs.creepercow.CreeperCowSounds;
import madscience.mobs.werewolf.WerewolfMobSounds;
import madscience.mobs.woolycow.WoolyCowSounds;
import madscience.tileentities.cncmachine.CnCMachineSounds;
import madscience.tileentities.cryofreezer.CryofreezerSounds;
import madscience.tileentities.cryotube.CryotubeSounds;
import madscience.tileentities.dataduplicator.DataDuplicatorSounds;
import madscience.tileentities.incubator.IncubatorSounds;
import madscience.tileentities.magloader.MagLoaderSounds;
import madscience.tileentities.mainframe.MainframeSounds;
import madscience.tileentities.meatcube.MeatcubeSounds;
import madscience.tileentities.sanitizer.SanitizerSounds;
import madscience.tileentities.sequencer.SequencerSounds;
import madscience.tileentities.soniclocator.SoniclocatorSounds;
import madscience.tileentities.thermosonicbonder.ThermosonicBonderSounds;
import madscience.tileentities.voxbox.VoxBoxSounds;
import net.minecraft.launchwrapper.LogWrapper;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MadSounds
{
    @SideOnly(Side.CLIENT)
    @ForgeSubscribe
    public void onSoundLoad(SoundLoadEvent event) // NO_UCD (unused code)
    {
        // Grab an iterable array of all registered machines.
        Iterable<MadTileEntityFactoryProduct> registeredMachines = MadTileEntityFactory.getMachineInfoList();
        for (Iterator iterator = registeredMachines.iterator(); iterator.hasNext();)
        {
            MadTileEntityFactoryProduct registeredMachine = (MadTileEntityFactoryProduct) iterator.next();
            if (registeredMachine != null)
            {
                // Grab processed list of filenames of sounds that need to be registered with Forge/MC.
                String[] unregisteredSounds = registeredMachine.getSoundArchiveFilenameArray();
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

        // Disgusting Meat Cube
        MeatcubeSounds.init(event);

        // -------------
        // TILE ENTITIES
        // -------------

        // Computer Mainframe
        MainframeSounds.init(event);

        // Cryogenic Freezer
        CryofreezerSounds.init(event);

        // Genome Incubator
        IncubatorSounds.init(event);

        // Genetic Sequencer
        SequencerSounds.init(event);

        // Needle Sanitizer
        SanitizerSounds.init(event);

        // Cryogenic Tube
        CryotubeSounds.init(event);

        // Data Duplicator
        DataDuplicatorSounds.init(event);

        // Thermosonic Bonder
        ThermosonicBonderSounds.init(event);

        // Soniclocator Device
        SoniclocatorSounds.init(event);

        // M41A Pulse Rifle
        PulseRifleSounds.init(event);

        // Announcement System
        VoxBoxSounds.init(event);

        // Magazine Loader
        MagLoaderSounds.init(event);

        // CnC Machine
        CnCMachineSounds.init(event);
    }
}

package madscience;

import java.util.logging.Level;

import madscience.items.needles.NeedleSounds;
import madscience.items.weapons.pulserifle.PulseRifleSounds;
import madscience.mobs.abomination.AbominationSounds;
import madscience.mobs.creepercow.CreeperCowSounds;
import madscience.mobs.werewolf.WerewolfMobSounds;
import madscience.mobs.woolycow.WoolyCowSounds;
import madscience.tileentities.cryofreezer.CryofreezerSounds;
import madscience.tileentities.cryotube.CryotubeSounds;
import madscience.tileentities.dataduplicator.DataDuplicatorSounds;
import madscience.tileentities.dnaextractor.DNAExtractorSounds;
import madscience.tileentities.incubator.IncubatorSounds;
import madscience.tileentities.mainframe.MainframeSounds;
import madscience.tileentities.meatcube.MeatcubeSounds;
import madscience.tileentities.sanitizer.SanitizerSounds;
import madscience.tileentities.sequencer.SequencerSounds;
import madscience.tileentities.soniclocator.SoniclocatorSounds;
import madscience.tileentities.thermosonicbonder.ThermosonicBonderSounds;
import madscience.tileentities.voxbox.VoxBoxSounds;
import net.minecraft.launchwrapper.LogWrapper;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MadSounds
{
    @SideOnly(Side.CLIENT)
    @ForgeSubscribe
    public void onSoundLoad(SoundLoadEvent event)
    {
        try
        {
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
            
            // Computer Mainframe
            MainframeSounds.init(event);
            
            // Cryogenic Freezer
            CryofreezerSounds.init(event);
            
            // DNA Extractor
            DNAExtractorSounds.init(event);
            
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
            
        }
        catch (Exception err)
        {
            // Print generic message to the log.
            LogWrapper.log(Level.SEVERE, "Failed to register one or more sounds: " + err.getMessage());
        }
    }
}

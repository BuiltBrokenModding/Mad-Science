package madscience;

import java.util.logging.Level;

import net.minecraft.launchwrapper.LogWrapper;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MadSounds
{
    // ----
    // MOBS
    // ----

    // Werewolf [Villager + Wolf]
    public static final String WEREWOLF_ATTACK = MadScience.ID + ":" + MadMobs.GMO_WEREWOLF_INTERNALNAME + ".Attack";
    public static final String WEREWOLF_IDLE = MadScience.ID + ":" + MadMobs.GMO_WEREWOLF_INTERNALNAME + ".Death";
    public static final String WEREWOLF_DEATH = MadScience.ID + ":" + MadMobs.GMO_WEREWOLF_INTERNALNAME + ".Snarl";
    
    // Creeper Cow [Creeper + Cow]
    public static final String CREEPERCOW_ATTACK = MadScience.ID + ":" + MadMobs.GMO_CREEPERCOW_INTERNALNAME + ".Attack";
    public static final String CREEPERCOW_DEATH = MadScience.ID + ":" + MadMobs.GMO_CREEPERCOW_INTERNALNAME + ".Death";
    public static final String CREEPERCOW_IDLE = MadScience.ID + ":" + MadMobs.GMO_CREEPERCOW_INTERNALNAME + ".Idle";    
    
    // Bart74(bart.74@hotmail.fr)
    // Wooly cow [Cow + Sheep]
    public static final String WOOLYCOW_HURT = MadScience.ID + ":" + MadMobs.GMO_WOOLYCOW_INTERNALNAME + ".hurt";
    public static final String WOOLYCOW_SAY = MadScience.ID + ":" + MadMobs.GMO_WOOLYCOW_INTERNALNAME + ".say";
    public static final String WOOLYCOW_STEP = MadScience.ID + ":" + MadMobs.GMO_WOOLYCOW_INTERNALNAME + ".step";
    
    // monodemono(coolplanet3000@gmail.com)
    // The Abomination [Enderman + Spider]
    public static final String ABOMINATION_ATTACK = MadScience.ID + ":" + MadMobs.GMO_ABOMINATION_INTERNALNAME + ".attack";
    public static final String ABOMINATION_HISS = MadScience.ID + ":" + MadMobs.GMO_ABOMINATION_INTERNALNAME + ".hiss";
    public static final String ABOMINATION_GROWL = MadScience.ID + ":" + MadMobs.GMO_ABOMINATION_INTERNALNAME + ".growl";
    public static final String ABOMINATION_DEATH = MadScience.ID + ":" + MadMobs.GMO_ABOMINATION_INTERNALNAME + ".death";
    public static final String ABOMINATION_STEP = MadScience.ID + ":" + MadMobs.GMO_ABOMINATION_INTERNALNAME + ".step";
    public static final String ABOMINATION_PAIN = MadScience.ID + ":" + MadMobs.GMO_ABOMINATION_INTERNALNAME + ".pain";
    public static final String ABOMINATION_EGG = MadScience.ID + ":" + MadMobs.GMO_ABOMINATION_INTERNALNAME + ".egg";
    public static final String ABOMINATION_HATCH = MadScience.ID + ":" + MadMobs.GMO_ABOMINATION_INTERNALNAME + ".egghatch";
    public static final String ABOMINATION_EGGPOP = MadScience.ID + ":" + MadMobs.GMO_ABOMINATION_INTERNALNAME + ".eggpop";
    
    // Pyrobrine(haskar.spore@gmail.com)
    // Ender Squid [Enderman + Squid]
    public static final String ENDERSQUID_ATTACK = MadScience.ID + ":" + MadMobs.GMO_ENDERSQUID_INTERNALNAME + ".Attack";
    public static final String ENDERSQUID_IDLE = MadScience.ID + ":" + MadMobs.GMO_ENDERSQUID_INTERNALNAME + ".Death";
    public static final String ENDERSQUID_DEATH = MadScience.ID + ":" + MadMobs.GMO_ENDERSQUID_INTERNALNAME + ".Idle";

    // -----
    // ITEMS
    // -----

    // Needles
    public static final String NEEDLEITEM_STABPLAYER = MadScience.ID + ":" + MadNeedles.NEEDLE_ITEM_INTERNALNAME + ".Stabself";
    public static final String NEEDLEITEM_STABMOB = MadScience.ID + ":" + MadNeedles.NEEDLE_ITEM_INTERNALNAME + ".Stab";

    // -----------
    // TILE ENTITY
    // -----------

    // Meatcube
    public static final String MEATCUBE_REGROW = MadScience.ID + ":" + MadFurnaces.MEATCUBE_INTERNALNAME + ".Belly";
    public static final String MEATCUBE_HEARTBEAT = MadScience.ID + ":" + MadFurnaces.MEATCUBE_INTERNALNAME + ".Heartbeat";
    public static final String MEATCUBE_IDLE = MadScience.ID + ":" + MadFurnaces.MEATCUBE_INTERNALNAME + ".Idle";
    public static final String MEATCUBE_MEATSLAP = MadScience.ID + ":" + MadFurnaces.MEATCUBE_INTERNALNAME + ".Meatslap";
    public static final String MEATCUBE_MOOANING = MadScience.ID + ":" + MadFurnaces.MEATCUBE_INTERNALNAME + ".Moo";

    // Computer Mainframe
    public static final String MAINFRAME_BREAK = MadScience.ID + ":" + MadFurnaces.MAINFRAME_INTERNALNAME + ".Break";
    public static final String MAINFRAME_FINISH = MadScience.ID + ":" + MadFurnaces.MAINFRAME_INTERNALNAME + ".Finish";
    public static final String MAINFRAME_IDLE = MadScience.ID + ":" + MadFurnaces.MAINFRAME_INTERNALNAME + ".Idle";
    public static final String MAINFRAME_OFF = MadScience.ID + ":" + MadFurnaces.MAINFRAME_INTERNALNAME + ".Off";
    public static final String MAINFRAME_ON = MadScience.ID + ":" + MadFurnaces.MAINFRAME_INTERNALNAME + ".On";
    public static final String MAINFRAME_OVERHEAT = MadScience.ID + ":" + MadFurnaces.MAINFRAME_INTERNALNAME + ".Overheat";
    public static final String MAINFRAME_START = MadScience.ID + ":" + MadFurnaces.MAINFRAME_INTERNALNAME + ".Start";
    public static final String MAINFRAME_WORK = MadScience.ID + ":" + MadFurnaces.MAINFRAME_INTERNALNAME + ".Work";

    // Cryogenic Freezer
    public static final String CRYOFREEZER_IDLE = MadScience.ID + ":" + MadFurnaces.CRYOFREEZER_INTERNALNAME + ".Idle";

    // DNA Extractor
    public static final String DNAEXTRACTOR_FINISH = MadScience.ID + ":" + MadFurnaces.DNAEXTRACTOR_INTERNALNAME + ".Finish";
    public static final String DNAEXTRACTOR_IDLE = MadScience.ID + ":" + MadFurnaces.DNAEXTRACTOR_INTERNALNAME + ".Idle";

    // Genome Incubator
    public static final String INCUBATOR_FINISH = MadScience.ID + ":" + MadFurnaces.INCUBATOR_INTERNALNAME + ".Finish";
    public static final String INCUBATOR_START = MadScience.ID + ":" + MadFurnaces.INCUBATOR_INTERNALNAME + ".Start";
    public static final String INCUBATOR_WORK = MadScience.ID + ":" + MadFurnaces.INCUBATOR_INTERNALNAME + ".Work";

    // Genome Sequencer
    public static final String SEQUENCER_FINISH = MadScience.ID + ":" + MadFurnaces.SEQUENCER_INTERNALNAME + ".Finish";
    public static final String SEQUENCER_START = MadScience.ID + ":" + MadFurnaces.SEQUENCER_INTERNALNAME + ".Start";
    public static final String SEQUENCER_WORK = MadScience.ID + ":" + MadFurnaces.SEQUENCER_INTERNALNAME + ".Work";

    // Needle Sanitizer
    public static final String SANTITIZER_IDLE = MadScience.ID + ":" + MadFurnaces.SANTITIZER_INTERNALNAME + ".Idle";
    
    // Cryogenic Tube
    public static final String CRYOTUBE_STILLBIRTH = MadScience.ID + ":" + MadFurnaces.CRYOTUBE_INTERNALNAME + ".Stillbirth";
    public static final String CRYOTUBE_WORK = MadScience.ID + ":" + MadFurnaces.CRYOTUBE_INTERNALNAME + ".Work";
    public static final String CRYOTUBE_HATCH = MadScience.ID + ":" + MadFurnaces.CRYOTUBE_INTERNALNAME + ".Hatch";    
    public static final String CRYOTUBE_HATCHING = MadScience.ID + ":" + MadFurnaces.CRYOTUBE_INTERNALNAME + ".Hatching";
    public static final String CRYOTUBE_CRACKEGG = MadScience.ID + ":" + MadFurnaces.CRYOTUBE_INTERNALNAME + ".CrackEgg";
    public static final String CRYOTUBE_IDLE = MadScience.ID + ":" + MadFurnaces.CRYOTUBE_INTERNALNAME + ".Idle";
    public static final String CRYOTUBE_OFF = MadScience.ID + ":" + MadFurnaces.CRYOTUBE_INTERNALNAME + ".Off";
    
    // Thermosonic Bonder
    public static final String THERMOSONICBONDER_IDLE = MadScience.ID + ":" + MadFurnaces.THERMOSONIC_INTERNALNAME + ".Idle";
    public static final String THERMOSONICBONDER_LASERSTART = MadScience.ID + ":" + MadFurnaces.THERMOSONIC_INTERNALNAME + ".LaserStart";
    public static final String THERMOSONICBONDER_LASERSTOP = MadScience.ID + ":" + MadFurnaces.THERMOSONIC_INTERNALNAME + ".LaserStop";
    public static final String THERMOSONICBONDER_LASERWORK = MadScience.ID + ":" + MadFurnaces.THERMOSONIC_INTERNALNAME + ".LaserWorking";
    public static final String THERMOSONICBONDER_STAMP = MadScience.ID + ":" + MadFurnaces.THERMOSONIC_INTERNALNAME + ".Stamp";
    
    // Data Reel Duplicator
    public static final String DATADUPLICATOR_WORK = MadScience.ID + ":" + MadFurnaces.DATADUPLICATOR_INTERNALNAME + ".Work";
    public static final String DATADUPLICATOR_IDLE = MadScience.ID + ":" + MadFurnaces.DATADUPLICATOR_INTERNALNAME + ".Idle";
    public static final String DATADUPLICATOR_FINISH = MadScience.ID + ":" + MadFurnaces.DATADUPLICATOR_INTERNALNAME + ".Finish";
    public static final String DATADUPLICATOR_START = MadScience.ID + ":" + MadFurnaces.DATADUPLICATOR_INTERNALNAME + ".Start";
    
    // Soniclocator Device
    public static final String SONICLOCATOR_WORK = MadScience.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + ".Work";
    public static final String SONICLOCATOR_PLACE = MadScience.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + ".Place";
    public static final String SONICLOCATOR_IDLE = MadScience.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + ".Idle";
    public static final String SONICLOCATOR_FINISH = MadScience.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + ".Finish";
    public static final String SONICLOCATOR_EMPTY = MadScience.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + ".Empty";

    @SideOnly(Side.CLIENT)
    @ForgeSubscribe
    public void onSoundLoad(SoundLoadEvent event)
    {
        try
        {
            // ----------
            // MOB SOUNDS
            // ----------

            // Werewolf
            event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_WEREWOLF_INTERNALNAME + "/Attack1.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_WEREWOLF_INTERNALNAME + "/Attack2.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_WEREWOLF_INTERNALNAME + "/Death.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_WEREWOLF_INTERNALNAME + "/Snarl1.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_WEREWOLF_INTERNALNAME + "/Snarl2.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_WEREWOLF_INTERNALNAME + "/Snarl3.ogg");
            
            // Creeper Cow [Creeper + Cow]
            event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_ENDERSQUID_INTERNALNAME + "/Attack.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_ENDERSQUID_INTERNALNAME + "/Death.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_ENDERSQUID_INTERNALNAME + "/Idle.ogg");
            
            // Bart74(bart.74@hotmail.fr)
            // Wooly cow [Cow + Sheep]
            event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_CREEPERCOW_INTERNALNAME + "/hurt1.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_CREEPERCOW_INTERNALNAME + "/hurt2.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_CREEPERCOW_INTERNALNAME + "/hurt3.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_CREEPERCOW_INTERNALNAME + "/say1.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_CREEPERCOW_INTERNALNAME + "/say2.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_CREEPERCOW_INTERNALNAME + "/say3.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_CREEPERCOW_INTERNALNAME + "/say4.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_CREEPERCOW_INTERNALNAME + "/step1.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_CREEPERCOW_INTERNALNAME + "/step2.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_CREEPERCOW_INTERNALNAME + "/step3.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_CREEPERCOW_INTERNALNAME + "/step4.ogg");
            
            // monodemono(coolplanet3000@gmail.com)
            // The Abomination [Enderman + Spider]
            event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_ABOMINATION_INTERNALNAME + "/attack.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_ABOMINATION_INTERNALNAME + "/death.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_ABOMINATION_INTERNALNAME + "/egg.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_ABOMINATION_INTERNALNAME + "/egghatch.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_ABOMINATION_INTERNALNAME + "/growl1.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_ABOMINATION_INTERNALNAME + "/growl2.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_ABOMINATION_INTERNALNAME + "/growl3.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_ABOMINATION_INTERNALNAME + "/hissl.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_ABOMINATION_INTERNALNAME + "/hiss2.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_ABOMINATION_INTERNALNAME + "/hiss3.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_ABOMINATION_INTERNALNAME + "/hiss4.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_ABOMINATION_INTERNALNAME + "/pain1.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_ABOMINATION_INTERNALNAME + "/pain2.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_ABOMINATION_INTERNALNAME + "/pain3.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_ABOMINATION_INTERNALNAME + "/step1.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_ABOMINATION_INTERNALNAME + "/step2.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_ABOMINATION_INTERNALNAME + "/step3.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_ABOMINATION_INTERNALNAME + "/step4.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_ABOMINATION_INTERNALNAME + "/eggpop.ogg");
            
            // Pyrobrine(haskar.spore@gmail.com)
            // Ender Squid [Enderman + Squid]
            event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_ENDERSQUID_INTERNALNAME + "/Attack.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_ENDERSQUID_INTERNALNAME + "/Death.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadMobs.GMO_ENDERSQUID_INTERNALNAME + "/Idle.ogg");

            // -----------
            // ITEM SOUNDS
            // -----------

            // Needles (of all types!)
            event.manager.addSound(MadScience.ID + ":" + MadNeedles.NEEDLE_ITEM_INTERNALNAME + "/Stab.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadNeedles.NEEDLE_ITEM_INTERNALNAME + "/Stabself.ogg");

            // ---------------
            // MEATCUBE SOUNDS
            // ---------------

            // Regrowing noises.
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MEATCUBE_INTERNALNAME + "/Belly1.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MEATCUBE_INTERNALNAME + "/Belly2.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MEATCUBE_INTERNALNAME + "/Belly3.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MEATCUBE_INTERNALNAME + "/Belly4.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MEATCUBE_INTERNALNAME + "/Belly5.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MEATCUBE_INTERNALNAME + "/Belly6.ogg");

            // Heartbeat noises.
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MEATCUBE_INTERNALNAME + "/Heartbeat1.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MEATCUBE_INTERNALNAME + "/Heartbeat2.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MEATCUBE_INTERNALNAME + "/Heartbeat3.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MEATCUBE_INTERNALNAME + "/Heartbeat4.ogg");

            // Idle noises.
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MEATCUBE_INTERNALNAME + "/Idle1.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MEATCUBE_INTERNALNAME + "/Idle2.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MEATCUBE_INTERNALNAME + "/Idle3.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MEATCUBE_INTERNALNAME + "/Idle4.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MEATCUBE_INTERNALNAME + "/Idle5.ogg");

            // Meat slapping noises.
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MEATCUBE_INTERNALNAME + "/Meatslap1.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MEATCUBE_INTERNALNAME + "/Meatslap2.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MEATCUBE_INTERNALNAME + "/Meatslap3.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MEATCUBE_INTERNALNAME + "/Meatslap4.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MEATCUBE_INTERNALNAME + "/Meatslap5.ogg");

            // Mooing noises.
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MEATCUBE_INTERNALNAME + "/Moo1.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MEATCUBE_INTERNALNAME + "/Moo2.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MEATCUBE_INTERNALNAME + "/Moo3.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MEATCUBE_INTERNALNAME + "/Moo4.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MEATCUBE_INTERNALNAME + "/Moo5.ogg");

            // -------------------------
            // COMPUTER MAINFRAME SOUNDS
            // -------------------------

            // Break from overheating.
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MAINFRAME_INTERNALNAME + "/Break.ogg");

            // Finish processing.
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MAINFRAME_INTERNALNAME + "/Finish.ogg");

            // Idle running sound.
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MAINFRAME_INTERNALNAME + "/Idle.ogg");

            // Turning off noise.
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MAINFRAME_INTERNALNAME + "/Off.ogg");

            // Turning on noise.
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MAINFRAME_INTERNALNAME + "/On.ogg");

            // Overheating noise.
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MAINFRAME_INTERNALNAME + "/Overheat.ogg");

            // Startup noise.
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MAINFRAME_INTERNALNAME + "/Start.ogg");

            // Work noises.
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MAINFRAME_INTERNALNAME + "/Work1.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MAINFRAME_INTERNALNAME + "/Work2.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MAINFRAME_INTERNALNAME + "/Work3.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MAINFRAME_INTERNALNAME + "/Work4.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.MAINFRAME_INTERNALNAME + "/Work5.ogg");

            // ------------
            // CRYO FREEZER
            // ------------

            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.CRYOFREEZER_INTERNALNAME + "/Idle.ogg");

            // -------------
            // DNA EXTRACTOR
            // -------------

            // Finish extracting needle.
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.DNAEXTRACTOR_INTERNALNAME + "/Finish.ogg");

            // Idle sound while extracting DNA.
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.DNAEXTRACTOR_INTERNALNAME + "/Idle.ogg");

            // ----------------
            // GENOME INCUBATOR
            // ----------------

            // Finish creating mob egg.
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.INCUBATOR_INTERNALNAME + "/Finish.ogg");

            // Start creating mob egg.
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.INCUBATOR_INTERNALNAME + "/Start.ogg");

            // Working noises.
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.INCUBATOR_INTERNALNAME + "/Work1.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.INCUBATOR_INTERNALNAME + "/Work2.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.INCUBATOR_INTERNALNAME + "/Work3.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.INCUBATOR_INTERNALNAME + "/Work4.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.INCUBATOR_INTERNALNAME + "/Work5.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.INCUBATOR_INTERNALNAME + "/Work6.ogg");

            // ----------------
            // GENOME SEQUENCER
            // ----------------

            // Finish sequencing genome.
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.SEQUENCER_INTERNALNAME + "/Finish.ogg");

            // Start sequencing genome.
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.SEQUENCER_INTERNALNAME + "/Start.ogg");

            // Working on genome.
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.SEQUENCER_INTERNALNAME + "/Work1.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.SEQUENCER_INTERNALNAME + "/Work2.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.SEQUENCER_INTERNALNAME + "/Work3.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.SEQUENCER_INTERNALNAME + "/Work4.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.SEQUENCER_INTERNALNAME + "/Work5.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.SEQUENCER_INTERNALNAME + "/Work6.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.SEQUENCER_INTERNALNAME + "/Work7.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.SEQUENCER_INTERNALNAME + "/Work8.ogg");

            // ----------------
            // NEEDLE SANITIZER
            // ----------------

            // Cleaning needle sound.
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.SANTITIZER_INTERNALNAME + "/Idle.ogg");
            
            // --------------
            // CRYOGENIC TUBE
            // --------------
            
            // Stillbirth
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.CRYOTUBE_INTERNALNAME + "/Stillbirth.ogg");
            
            // Work
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.CRYOTUBE_INTERNALNAME + "/Work.ogg");
            
            // Cracking Egg
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.CRYOTUBE_INTERNALNAME + "/CrackEgg.ogg");
            
            // Hatching Loop
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.CRYOTUBE_INTERNALNAME + "/Hatching.ogg");
            
            // Idle working sound when cannot smelt.
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.CRYOTUBE_INTERNALNAME + "/Idle.ogg");
            
            // Sound of cryotube being turned off.
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.CRYOTUBE_INTERNALNAME + "/Off.ogg");
            
            // Hatching Random Noises
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.CRYOTUBE_INTERNALNAME + "/Hatch1.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.CRYOTUBE_INTERNALNAME + "/Hatch2.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.CRYOTUBE_INTERNALNAME + "/Hatch3.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.CRYOTUBE_INTERNALNAME + "/Hatch4.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.CRYOTUBE_INTERNALNAME + "/Hatch5.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.CRYOTUBE_INTERNALNAME + "/Hatch6.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.CRYOTUBE_INTERNALNAME + "/Hatch7.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.CRYOTUBE_INTERNALNAME + "/Hatch8.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.CRYOTUBE_INTERNALNAME + "/Hatch9.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.CRYOTUBE_INTERNALNAME + "/Hatch10.ogg");
            
            // ------------------
            // THERMOSONIC BONDER
            // ------------------
            
            // Idle
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.THERMOSONIC_INTERNALNAME + "/Idle.ogg");
            
            // Laser Start
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.THERMOSONIC_INTERNALNAME + "/LaserStart.ogg");
            
            // Laser Stop
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.THERMOSONIC_INTERNALNAME + "/LaserStop.ogg");
            
            // Laser Working
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.THERMOSONIC_INTERNALNAME + "/LaserWorking.ogg");
            
            // Stamp (output slot being filled)
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.THERMOSONIC_INTERNALNAME + "/Stamp1.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.THERMOSONIC_INTERNALNAME + "/Stamp2.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.THERMOSONIC_INTERNALNAME + "/Stamp3.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.THERMOSONIC_INTERNALNAME + "/Stamp4.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.THERMOSONIC_INTERNALNAME + "/Stamp5.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.THERMOSONIC_INTERNALNAME + "/Stamp6.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.THERMOSONIC_INTERNALNAME + "/Stamp7.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.THERMOSONIC_INTERNALNAME + "/Stamp8.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.THERMOSONIC_INTERNALNAME + "/Stamp9.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.THERMOSONIC_INTERNALNAME + "/Stamp10.ogg");
            
            // --------------------
            // DATA REEL DUPLICATOR
            // --------------------
            
            // Idle
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.DATADUPLICATOR_INTERNALNAME + "/Idle.ogg");
            
            // Work
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.DATADUPLICATOR_INTERNALNAME + "/Work.ogg");
            
            // Start
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.DATADUPLICATOR_INTERNALNAME + "/Start.ogg");
            
            // Finish
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.DATADUPLICATOR_INTERNALNAME + "/Finish.ogg");
            
            // -------------------
            // SONICLOCATOR DEVICE
            // -------------------
            
            // Idle
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + "/Idle.ogg");
            
            // Work
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + "/Work1.ogg");
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + "/Work2.ogg");
            
            // Place
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + "/Place.ogg");
            
            // Empty
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + "/Empty.ogg");
            
            // Finish
            event.manager.addSound(MadScience.ID + ":" + MadFurnaces.SONICLOCATOR_INTERNALNAME + "/Finish.ogg");

        }
        catch (Exception err)
        {
            // Print generic message to the log.
            LogWrapper.log(Level.SEVERE, "Failed to register one or more sounds: " + err.getMessage());
        }
    }
}

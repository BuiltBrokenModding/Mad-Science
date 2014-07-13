package madscience.factory.sounds;

import com.google.gson.annotations.Expose;


public final class MadSound
{
    @Expose private MadSoundFile sound = null;
    
    public MadSound( // NO_UCD (unused code)
            String fileName,
            int length,
            int randomVariance,
            MadSoundTriggerEnum trigger,
            MadSoundPlaybackTypeEnum playbackMode)
    {
        this.sound = new MadSoundFile(fileName, length, randomVariance, trigger, playbackMode);
    }

    public String getSoundNameWithoutExtension()
    {
        return this.sound.getSoundNameWithoutExtension();
    }

    public String getSoundNameWithExtension()
    {
        return this.sound.getSoundNameWithExtension();
    }

    public MadSoundTriggerEnum getSoundTrigger()
    {
        return this.sound.getSoundTrigger();
    }

    public int getSoundLengthInSeconds()
    {
        return this.sound.getSoundLengthInSeconds();
    }

    public MadSoundPlaybackTypeEnum getSoundPlaybackMode()
    {
        return this.sound.getSoundPlaybackMode();
    }

    public int getSoundRandomVariance()
    {
        return this.sound.getSoundRandomVariance();
    }

    public String getSoundExtension()
    {
        return this.sound.getSoundExtension();
    }
}
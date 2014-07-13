package madscience.factory.sounds;

import org.apache.commons.io.FilenameUtils;

import com.google.gson.annotations.Expose;

class MadSoundFile
{
    @Expose private String soundNameWithoutExtension = null;
    @Expose private String soundNameWithExtension = null;
    @Expose private String soundExtension = null;
    @Expose private MadSoundTriggerEnum soundTrigger = null;
    @Expose private int soundLengthInSeconds = 0;
    @Expose private MadSoundPlaybackTypeEnum soundPlaybackMode = null;
    @Expose private int randomVariance = 1;
    
    MadSoundFile(
            String fileName,
            int length,
            int randomVariance,
            MadSoundTriggerEnum trigger,
            MadSoundPlaybackTypeEnum playbackMode)
    {
        super();
        
        this.soundNameWithExtension = fileName;
        this.soundNameWithoutExtension = FilenameUtils.removeExtension(fileName);
        this.soundExtension = FilenameUtils.getExtension(fileName);
        this.soundLengthInSeconds = length;
        this.soundPlaybackMode = playbackMode;
        this.soundTrigger = trigger;
        this.randomVariance = randomVariance;
    }

    public String getSoundNameWithoutExtension()
    {
        return soundNameWithoutExtension;
    }

    public String getSoundNameWithExtension()
    {
        return soundNameWithExtension;
    }

    public MadSoundTriggerEnum getSoundTrigger()
    {
        return soundTrigger;
    }

    public int getSoundLengthInSeconds()
    {
        return soundLengthInSeconds;
    }

    public MadSoundPlaybackTypeEnum getSoundPlaybackMode()
    {
        return soundPlaybackMode;
    }

    public int getSoundRandomVariance()
    {
        return randomVariance;
    }

    public String getSoundExtension()
    {
        return soundExtension;
    }
}

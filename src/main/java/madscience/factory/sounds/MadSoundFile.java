package madscience.factory.sounds;

import org.apache.commons.io.FilenameUtils;

import com.google.gson.annotations.Expose;

public class MadSoundFile implements IMadSound
{
    @Expose private String soundNameWithoutExtension = null;
    @Expose private String soundNameWithExtension = null;
    @Expose private String soundExtension = null;
    @Expose private MadSoundTriggerEnum soundTrigger = null;
    @Expose private int soundLengthInSeconds = 0;
    @Expose private MadSoundPlaybackTypeEnum soundPlaybackMode = null;
    @Expose private int randomVariance = 1;
    
    public MadSoundFile(
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

    @Override
    public String getSoundNameWithoutExtension()
    {
        return soundNameWithoutExtension;
    }

    @Override
    public String getSoundNameWithExtension()
    {
        return soundNameWithExtension;
    }

    @Override
    public MadSoundTriggerEnum getSoundTrigger()
    {
        return soundTrigger;
    }

    @Override
    public int getSoundLengthInSeconds()
    {
        return soundLengthInSeconds;
    }

    @Override
    public MadSoundPlaybackTypeEnum getSoundPlaybackMode()
    {
        return soundPlaybackMode;
    }

    @Override
    public int getSoundRandomVariance()
    {
        return randomVariance;
    }

    @Override
    public String getSoundExtension()
    {
        return soundExtension;
    }
}

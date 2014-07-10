package madscience.factory.sounds;

import org.apache.commons.io.FilenameUtils;

public class MadSound implements MadSoundInterface
{
    private String soundNameWithoutExtension = null;
    private String soundNameWithExtension = null;
    private String soundExtension = null;
    private MadSoundTriggerEnum soundTrigger = null;
    private int soundLengthInSeconds = 0;
    private MadSoundPlaybackTypeEnum soundPlaybackMode = null;
    private int randomVariance = 1;
    
    public MadSound(
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

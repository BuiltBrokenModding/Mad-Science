package madscience.factory.sounds;


public final class MadSound implements IMadSound
{
    private MadSoundFile sound = null;
    
    public MadSound(
            String fileName,
            int length,
            int randomVariance,
            MadSoundTriggerEnum trigger,
            MadSoundPlaybackTypeEnum playbackMode)
    {
        this.sound = new MadSoundFile(fileName, length, randomVariance, trigger, playbackMode);
    }

    @Override
    public String getSoundNameWithoutExtension()
    {
        return this.sound.getSoundNameWithoutExtension();
    }

    @Override
    public String getSoundNameWithExtension()
    {
        return this.sound.getSoundNameWithExtension();
    }

    @Override
    public MadSoundTriggerEnum getSoundTrigger()
    {
        return this.sound.getSoundTrigger();
    }

    @Override
    public int getSoundLengthInSeconds()
    {
        return this.sound.getSoundLengthInSeconds();
    }

    @Override
    public MadSoundPlaybackTypeEnum getSoundPlaybackMode()
    {
        return this.sound.getSoundPlaybackMode();
    }

    @Override
    public int getSoundRandomVariance()
    {
        return this.sound.getSoundRandomVariance();
    }

    @Override
    public String getSoundExtension()
    {
        return this.sound.getSoundExtension();
    }
}
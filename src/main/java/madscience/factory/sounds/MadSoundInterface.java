package madscience.factory.sounds;

public interface MadSoundInterface
{
    public abstract String getSoundNameWithoutExtension();

    public abstract String getSoundNameWithExtension();

    public abstract MadSoundTriggerEnum getSoundTrigger();

    public abstract int getSoundLengthInSeconds();

    public abstract MadSoundPlaybackTypeEnum getSoundPlaybackMode();
    
    public abstract int getSoundRandomVariance();
    
    public abstract String getSoundExtension();
}
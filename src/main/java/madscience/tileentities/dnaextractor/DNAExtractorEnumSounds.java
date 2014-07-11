package madscience.tileentities.dnaextractor;

import madscience.factory.sounds.MadSound;
import madscience.factory.sounds.MadSoundInterface;
import madscience.factory.sounds.MadSoundPlaybackTypeEnum;
import madscience.factory.sounds.MadSoundTriggerEnum;

public enum DNAExtractorEnumSounds implements MadSoundInterface
{
    Finish("Finish.ogg", 2, 1, MadSoundTriggerEnum.WORKEND, MadSoundPlaybackTypeEnum.PLAY),
    Idle("Idle.ogg", 2, 1, MadSoundTriggerEnum.IDLEON, MadSoundPlaybackTypeEnum.PLAY);
    
    private MadSound sound = null;
    
    DNAExtractorEnumSounds(
            String fileName,
            int length,
            int randomVariance,
            MadSoundTriggerEnum trigger,
            MadSoundPlaybackTypeEnum playbackMode)
    {
        this.sound = new MadSound(fileName, length, randomVariance, trigger, playbackMode);
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
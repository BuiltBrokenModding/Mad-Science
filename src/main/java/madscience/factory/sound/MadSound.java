package madscience.factory.sound;

import com.google.gson.annotations.Expose;


public final class MadSound
{
    @Expose private MadSoundFile sound = null;
    
    private boolean hasLoaded = false;
    
    private String resourcePath;
    
    public MadSound( // NO_UCD (unused code)
            String fileName,
            float length,
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

    public float getSoundLengthInSeconds()
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

    public boolean isLoaded()
    {
        return hasLoaded;
    }

    public void setLoaded()
    {
        this.hasLoaded = true;
    }

    public String getResourcePath()
    {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath)
    {
        this.resourcePath = resourcePath;
    }
}
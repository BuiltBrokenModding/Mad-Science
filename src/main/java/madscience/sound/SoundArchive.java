package madscience.sound;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public final class SoundArchive
{
    @Expose
    @SerializedName("Sound")
    private Sound sound = null;

    private boolean hasLoaded = false;

    private String resourcePath;

    public SoundArchive( // NO_UCD (unused code)
                         String fileName,
                         float length,
                         int randomVariance,
                         SoundTriggerEnum trigger,
                         SoundPlaybackTypeEnum playbackMode)
    {
        this.sound = new Sound( fileName,
                                length,
                                randomVariance,
                                trigger,
                                playbackMode );
    }

    public String getSoundNameWithoutExtension()
    {
        return this.sound.getSoundNameWithoutExtension();
    }

    public String getSoundNameWithExtension()
    {
        return this.sound.getSoundNameWithExtension();
    }

    public SoundTriggerEnum getSoundTrigger()
    {
        return this.sound.getSoundTrigger();
    }

    public float getSoundLengthInSeconds()
    {
        return this.sound.getSoundLengthInSeconds();
    }

    public SoundPlaybackTypeEnum getSoundPlaybackMode()
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
package madapi.sound;

import org.apache.commons.io.FilenameUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class MadSoundFile
{
    @Expose 
    @SerializedName("SoundNameWithExtension")
    private String soundNameWithExtension = null;
    
    @Expose 
    @SerializedName("SoundTrigger")
    private MadSoundTriggerEnum soundTrigger = null;
    
    @Expose 
    @SerializedName("SoundLengthInSeconds")
    private float soundLengthInSeconds = 0;
    
    @Expose 
    @SerializedName("SoundPlaybackMode")
    private MadSoundPlaybackTypeEnum soundPlaybackMode = null;
    
    @Expose
    @SerializedName("RandomVariance")
    private int randomVariance = 1;
    
    @Expose(serialize = false, deserialize = true)
    @SerializedName("SoundExtension")
    private String soundExtension = null;
    
    @Expose(serialize = false, deserialize = true)
    @SerializedName("SoundNameWithoutExtension")
    private String soundNameWithoutExtension = null;
    
    MadSoundFile(
            String fileName,
            float length,
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

    public float getSoundLengthInSeconds()
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

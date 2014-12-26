package madscience.sound;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.io.FilenameUtils;


public class Sound
{
    @Expose
    @SerializedName("SoundNameWithExtension")
    private String soundNameWithExtension = null;

    @Expose
    @SerializedName("SoundTrigger")
    private SoundTriggerEnum soundTrigger = null;

    @Expose
    @SerializedName("SoundLengthInSeconds")
    private float soundLengthInSeconds = 0;

    @Expose
    @SerializedName("SoundPlaybackMode")
    private SoundPlaybackTypeEnum soundPlaybackMode = null;

    @Expose
    @SerializedName("RandomVariance")
    private int randomVariance = 1;
    
/*    @Expose(serialize = false, deserialize = true)
    @SerializedName("SoundExtension")
    private String soundExtension = null;
    
    @Expose(serialize = false, deserialize = true)
    @SerializedName("SoundNameWithoutExtension")
    private String soundNameWithoutExtension = null;*/

    Sound(String fileName,
          float length,
          int randomVariance,
          SoundTriggerEnum trigger,
          SoundPlaybackTypeEnum playbackMode)
    {
        super();

        this.soundNameWithExtension = fileName;
        //        this.soundNameWithoutExtension = FilenameUtils.removeExtension(fileName);
        //        this.soundExtension = FilenameUtils.getExtension(fileName);
        this.soundLengthInSeconds = length;
        this.soundPlaybackMode = playbackMode;
        this.soundTrigger = trigger;
        this.randomVariance = randomVariance;
    }

    public String getSoundNameWithoutExtension()
    {
        return FilenameUtils.removeExtension( soundNameWithExtension );
    }

    public String getSoundNameWithExtension()
    {
        return soundNameWithExtension;
    }

    public SoundTriggerEnum getSoundTrigger()
    {
        return soundTrigger;
    }

    public float getSoundLengthInSeconds()
    {
        return soundLengthInSeconds;
    }

    public SoundPlaybackTypeEnum getSoundPlaybackMode()
    {
        return soundPlaybackMode;
    }

    public int getSoundRandomVariance()
    {
        return randomVariance;
    }

    public String getSoundExtension()
    {
        return FilenameUtils.getExtension( soundNameWithExtension );
    }
}

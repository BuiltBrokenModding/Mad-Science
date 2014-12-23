package madscience.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.io.FilenameUtils;


public class MadModelFile
{
    @Expose
    @SerializedName("ModelPath")
    private String modelPath;

    @Expose
    @SerializedName("ModelVisible")
    private boolean modelVisible;

    private String modelNameWithoutExtension;

    @SuppressWarnings("ucd")
    public MadModelFile(String modelPath, boolean visibleByDefault)
    {
        super();

        this.modelPath = modelPath;
        this.modelNameWithoutExtension = FilenameUtils.getBaseName( modelPath );
        this.modelVisible = visibleByDefault;
    }

    /**
     * Path to model in assets folder.
     */
    public String getModelPath()
    {
        return modelPath;
    }

    /**
     * Default visibility of the model.
     */
    public boolean isModelVisible()
    {
        return modelVisible;
    }

    /**
     * Name of model without extension.
     */
    public String getModelName()
    {
        // Cache that result!
        if (modelNameWithoutExtension == null)
        {
            this.modelNameWithoutExtension = FilenameUtils.getBaseName( modelPath );
        }

        return modelNameWithoutExtension;
    }

    public void setVisibility(boolean visible)
    {
        this.modelVisible = visible;
    }
}

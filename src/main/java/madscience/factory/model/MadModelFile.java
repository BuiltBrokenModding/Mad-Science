package madscience.factory.model;

import org.apache.commons.io.FilenameUtils;

import com.google.gson.annotations.Expose;

public class MadModelFile
{
    @Expose
    private String modelPath;
    
    @Expose
    private boolean modelVisible;
    
    private String modelNameWithoutExtension;

    public MadModelFile(String modelPath, boolean visibleByDefault)
    {
        super();

        this.modelPath = modelPath;
        this.modelNameWithoutExtension = FilenameUtils.getBaseName(modelPath);
        this.modelVisible = visibleByDefault;
    }

    /** Path to model in assets folder. */
    public String getModelPath()
    {
        return modelPath;
    }

    /** Default visibility of the model. */
    public boolean isModelVisible()
    {
        return modelVisible;
    }

    /** Name of model without extension. */
    public String getModelName()
    {
        // Cache that result!
        if (modelNameWithoutExtension == null)
        {
            this.modelNameWithoutExtension = FilenameUtils.getBaseName(modelPath);
        }
        
        return modelNameWithoutExtension;
    }

    public void setVisibility(boolean visible)
    {
        this.modelVisible = visible;
    }
}

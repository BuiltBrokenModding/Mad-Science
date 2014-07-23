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

    public String getModelPath()
    {
        return modelPath;
    }

    public boolean isModelVisible()
    {
        return modelVisible;
    }

    public void setModelVisible(boolean modelVisible)
    {
        this.modelVisible = modelVisible;
    }

    public String getModelName()
    {
        // Cache that result!
        if (modelNameWithoutExtension == null)
        {
            this.modelNameWithoutExtension = FilenameUtils.getBaseName(modelPath);
        }
        
        return modelNameWithoutExtension;
    }
}

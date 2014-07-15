package madscience.factory.model;

import com.google.gson.annotations.Expose;

public class MadModelFile
{
    @Expose
    private String modelPath;

    public MadModelFile(String modelPath)
    {
        super();

        this.modelPath = modelPath;
    }

    public String getModelPath()
    {
        return modelPath;
    }
}

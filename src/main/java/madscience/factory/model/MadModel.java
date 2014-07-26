package madscience.factory.model;

import com.google.gson.annotations.Expose;

public class MadModel
{
    @Expose
    private MadModelFile[] machineModels;

    @Expose
    private String texturePath;

    public MadModel(MadModelFile[] machineModels, String machineTexture)
    {
        super();

        this.machineModels = machineModels;
        this.texturePath = machineTexture;
    }

    public MadModelData[] getMachineModelsDataClone()
    {
        MadModelData[] modelData = new MadModelData[this.machineModels.length];
        int x = 0;
        for (MadModelFile modelReference : this.machineModels)
        {
            modelData[x] = new MadModelData(modelReference.isModelVisible(), modelReference.getModelName());
            x++;
        }
        
        return modelData;
    }

    public String getMachineTexture()
    {
        return texturePath;
    }

    public MadModelFile[] getMachineModelsFilesClone()
    {
        MadModelFile[] modelData = new MadModelFile[this.machineModels.length];
        int x = 0;
        for (MadModelFile modelReference : this.machineModels)
        {
            modelData[x] = new MadModelFile(modelReference.getModelPath(), modelReference.isModelVisible());
            x++;
        }
        
        return modelData;
    }

    public int getModelPartCount()
    {
        return machineModels.length;
    }
}

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

    public MadModelFile[] getMachineModels()
    {
        return machineModels;
    }

    public String getMachineTexture()
    {
        return texturePath;
    }
}

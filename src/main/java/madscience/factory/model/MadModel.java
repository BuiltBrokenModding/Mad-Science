package madscience.factory.model;

import madscience.factory.rendering.MadModelItemRender;
import madscience.factory.rendering.MadModelWorldRender;

import com.google.gson.annotations.Expose;

public class MadModel
{
    @Expose
    private MadModelFile[] machineModels;

    @Expose
    private String texturePath;

    @Expose
    private MadModelItemRender itemRenderInfo;
    
    @Expose
    private MadModelWorldRender worldRenderInfo;
    
    public MadModel(
            MadModelFile[] machineModels,
            String machineTexture,
            MadModelItemRender itemRenderInfo,
            MadModelWorldRender worldRenderInfo)
    {
        super();

        // Model definition by file.
        this.machineModels = machineModels;
        
        // Path to default texture to use on entity and item if no logic class to specify.
        this.texturePath = machineTexture;
        
        if (itemRenderInfo == null)
        {
            // Default item rendering.
            itemRenderInfo = defaultItemRenderInfo();
        }
        
        if (worldRenderInfo == null)
        {
            // Default world rendering.
            worldRenderInfo = defaultWorldRenderInfo();
        }
        
        // Apply item render information.
        this.itemRenderInfo = itemRenderInfo;
        
        // Apply world render information.
        this.worldRenderInfo = worldRenderInfo;
    }

    public static MadModelWorldRender defaultWorldRenderInfo()
    {
        // Use default world rendering info if null.
        MadModelWorldRender tmpWorldRenderInfo = new MadModelWorldRender(
                new MadModelPosition(0.5F, 0.5F, 0.5F),
                new MadModelScale(1.0F, 1.0F, 1.0F));
        
        return tmpWorldRenderInfo;
    }

    public static MadModelItemRender defaultItemRenderInfo()
    {
        // Use default item rendering info if null.
        MadModelItemRender tmpItemInfo = new MadModelItemRender(
                true,
                true,
                true,
                true,
                new MadModelScale(1.4F, 1.4F, 1.4F),                // EQUIPPED
                new MadModelPosition(0.1F, 0.3F, 0.3F),
                new MadModelRotation(90.0F, 0.0F, 1.0F, 0.0F),
                new MadModelScale(1.0F, 1.0F, 1.0F),                // FIRST_PERSON
                new MadModelPosition(0.2F, 0.9F, 0.5F),
                new MadModelRotation(90.0F, 0.0F, 1.0F, 0.0F),
                new MadModelScale(1.0F, 1.0F, 1.0F),                // INVENTORY
                new MadModelPosition(0.5F, 0.42F, 0.5F),
                new MadModelRotation(270.0F, 0.0F, 0.5F, 0.0F),
                new MadModelScale(1.0F, 1.0F, 1.0F),                // ENTITY
                new MadModelPosition(1.0F, 1.0F, 1.0F),
                new MadModelRotation(180.0F, 0.0F, 1.0F, 0.0F));
        
        return tmpItemInfo;
    }

    public MadModelData[] getMachineModelsDataClone()
    {
        MadModelData[] modelData = new MadModelData[this.machineModels.length];
        int x = 0;
        for (MadModelFile modelReference : this.machineModels)
        {
            modelData[x] = new MadModelData(
                    modelReference.isModelVisible(),
                    modelReference.getModelName());
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

    public MadModelItemRender getItemRenderInfo()
    {
        return itemRenderInfo;
    }

    public MadModelWorldRender getWorldRenderInfo()
    {
        return worldRenderInfo;
    }

    public void setItemRenderInfo(MadModelItemRender itemRenderInfo)
    {
        this.itemRenderInfo = itemRenderInfo;
    }

    public void setWorldRenderInfo(MadModelWorldRender worldRenderInfo)
    {
        this.worldRenderInfo = worldRenderInfo;
    }
}

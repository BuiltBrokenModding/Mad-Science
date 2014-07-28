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
                new MadModelPosition(0.0F, 0.0F, 0.0F),
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

    public MadModelItemRender getItemRenderInfoClone()
    {
        MadModelItemRender renderItem = new MadModelItemRender(
                itemRenderInfo.isRenderItemEntity3D(),
                itemRenderInfo.isRenderItemEquipped3D(),
                itemRenderInfo.isRenderItemFirstPerson3D(),
                itemRenderInfo.isRenderItemInventory3D(),
                new MadModelScale(
                        itemRenderInfo.getModelItemEquippedScale().getModelScaleX(),            // EQUIPPED
                        itemRenderInfo.getModelItemEquippedScale().getModelScaleY(),
                        itemRenderInfo.getModelItemEquippedScale().getModelScaleZ()),
                new MadModelPosition(
                        itemRenderInfo.getModelItemEquippedPosition().getModelTranslateX(),
                        itemRenderInfo.getModelItemEquippedPosition().getModelTranslateY(),
                        itemRenderInfo.getModelItemEquippedPosition().getModelTranslateZ()),
                new MadModelRotation(
                        itemRenderInfo.getModelItemEquippedRotation().getModelRotationAngle(),
                        itemRenderInfo.getModelItemEquippedRotation().getModelRotationX(),
                        itemRenderInfo.getModelItemEquippedRotation().getModelRotationY(),
                        itemRenderInfo.getModelItemEquippedRotation().getModelRotationZ()),
                new MadModelScale(
                        itemRenderInfo.getModelItemFirstPersonScale().getModelScaleX(),         // FIRST_PERSON
                        itemRenderInfo.getModelItemFirstPersonScale().getModelScaleY(),
                        itemRenderInfo.getModelItemFirstPersonScale().getModelScaleZ()),
                new MadModelPosition(
                        itemRenderInfo.getModelItemFirstPersonPosition().getModelTranslateX(),
                        itemRenderInfo.getModelItemFirstPersonPosition().getModelTranslateY(),
                        itemRenderInfo.getModelItemFirstPersonPosition().getModelTranslateZ()),
                new MadModelRotation(
                        itemRenderInfo.getModelItemFirstPersonRotation().getModelRotationAngle(),
                        itemRenderInfo.getModelItemFirstPersonRotation().getModelRotationX(),
                        itemRenderInfo.getModelItemFirstPersonRotation().getModelRotationY(),
                        itemRenderInfo.getModelItemFirstPersonRotation().getModelRotationZ()),
                new MadModelScale(
                        itemRenderInfo.getModelItemInventoryScale().getModelScaleX(),           // INVENTORY
                        itemRenderInfo.getModelItemInventoryScale().getModelScaleY(),
                        itemRenderInfo.getModelItemInventoryScale().getModelScaleZ()),
                new MadModelPosition(
                        itemRenderInfo.getModelItemInventoryPosition().getModelTranslateX(),
                        itemRenderInfo.getModelItemInventoryPosition().getModelTranslateY(),
                        itemRenderInfo.getModelItemInventoryPosition().getModelTranslateZ()),
                new MadModelRotation(
                        itemRenderInfo.getModelItemInventoryRotation().getModelRotationAngle(),
                        itemRenderInfo.getModelItemInventoryRotation().getModelRotationX(),
                        itemRenderInfo.getModelItemInventoryRotation().getModelRotationY(),
                        itemRenderInfo.getModelItemInventoryRotation().getModelRotationZ()),
                new MadModelScale(
                        itemRenderInfo.getModelItemEntityScale().getModelScaleX(),              // ENTITY
                        itemRenderInfo.getModelItemEntityScale().getModelScaleY(),
                        itemRenderInfo.getModelItemEntityScale().getModelScaleZ()),
                new MadModelPosition(
                        itemRenderInfo.getModelItemEntityPosition().getModelTranslateX(),
                        itemRenderInfo.getModelItemEntityPosition().getModelTranslateY(),
                        itemRenderInfo.getModelItemEntityPosition().getModelTranslateZ()),
                new MadModelRotation(
                        itemRenderInfo.getModelItemEntityRotation().getModelRotationAngle(),
                        itemRenderInfo.getModelItemEntityRotation().getModelRotationX(),
                        itemRenderInfo.getModelItemEntityRotation().getModelRotationY(),
                        itemRenderInfo.getModelItemEntityRotation().getModelRotationZ()));
        
        return renderItem;
    }

    public MadModelWorldRender getWorldRenderInfoClone()
    {
        MadModelWorldRender renderWorld = new MadModelWorldRender(
                new MadModelPosition(
                        worldRenderInfo.getModelWorldPosition().getModelTranslateX(),
                        worldRenderInfo.getModelWorldPosition().getModelTranslateY(),
                        worldRenderInfo.getModelWorldPosition().getModelTranslateZ()),
                new MadModelScale(
                        worldRenderInfo.getModelWorldScale().getModelScaleX(),
                        worldRenderInfo.getModelWorldScale().getModelScaleY(),
                        worldRenderInfo.getModelWorldScale().getModelScaleZ()));
        
        return renderWorld;
    }

    public void setItemRenderInfoDefaults()
    {
        this.itemRenderInfo = MadModel.defaultItemRenderInfo();
    }

    public void setWorldRenderInfoDefaults()
    {
        this.worldRenderInfo = MadModel.defaultWorldRenderInfo();
    }
}

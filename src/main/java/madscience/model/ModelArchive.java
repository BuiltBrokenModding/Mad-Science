package madscience.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import madscience.rendering.ItemRenderInfo;
import madscience.rendering.WorldRenderInfo;


public class ModelArchive
{
    @Expose
    @SerializedName("MachineModels")
    private MachineModel[] machineModels;

    @Expose
    @SerializedName("TexturePath")
    private String texturePath;

    @Expose
    @SerializedName("ItemRenderInfo")
    private ItemRenderInfo itemRenderInfo;

    @Expose
    @SerializedName("WorldRenderInfo")
    private WorldRenderInfo worldRenderInfo;

    @SuppressWarnings("ucd")
    public ModelArchive(MachineModel[] machineModels,
                        String machineTexture,
                        ItemRenderInfo itemRenderInfo,
                        WorldRenderInfo worldRenderInfo)
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

    public static WorldRenderInfo defaultWorldRenderInfo()
    {
        // Use default world rendering info if null.
        WorldRenderInfo tmpWorldRenderInfo = new WorldRenderInfo( new ModelPosition( 0.5F,
                                                                                     0.5F,
                                                                                     0.5F ),
                                                                  new ModelScale( 1.0F,
                                                                                  1.0F,
                                                                                  1.0F ) );

        return tmpWorldRenderInfo;
    }

    @SuppressWarnings("ucd")
    public static ItemRenderInfo defaultItemRenderInfo()
    {
        // Use default item rendering info if null.
        ItemRenderInfo tmpItemInfo = new ItemRenderInfo( true,
                                                         true,
                                                         true,
                                                         true,
                                                         new ModelScale( 1.4F,
                                                                         1.4F,
                                                                         1.4F ),
                                                         // EQUIPPED
                                                         new ModelPosition( 0.1F,
                                                                            0.3F,
                                                                            0.3F ),
                                                         new ModelRotation( 90.0F,
                                                                            0.0F,
                                                                            1.0F,
                                                                            0.0F ),
                                                         new ModelScale( 1.0F,
                                                                         1.0F,
                                                                         1.0F ),
                                                         // FIRST_PERSON
                                                         new ModelPosition( 0.2F,
                                                                            0.9F,
                                                                            0.5F ),
                                                         new ModelRotation( 90.0F,
                                                                            0.0F,
                                                                            1.0F,
                                                                            0.0F ),
                                                         new ModelScale( 1.0F,
                                                                         1.0F,
                                                                         1.0F ),
                                                         // INVENTORY
                                                         new ModelPosition( 0.5F,
                                                                            0.42F,
                                                                            0.5F ),
                                                         new ModelRotation( 270.0F,
                                                                            0.0F,
                                                                            0.5F,
                                                                            0.0F ),
                                                         new ModelScale( 1.0F,
                                                                         1.0F,
                                                                         1.0F ),
                                                         // ENTITY
                                                         new ModelPosition( 0.0F,
                                                                            0.0F,
                                                                            0.0F ),
                                                         new ModelRotation( 180.0F,
                                                                            0.0F,
                                                                            1.0F,
                                                                            0.0F ) );

        return tmpItemInfo;
    }

    public ModelData[] getMachineModelsDataClone()
    {
        ModelData[] modelData = new ModelData[this.machineModels.length];
        int x = 0;
        for (MachineModel modelReference : this.machineModels)
        {
            modelData[x] = new ModelData( modelReference.isModelVisible(),
                                          modelReference.getModelName() );
            x++;
        }

        return modelData;
    }

    public String getMachineTexture()
    {
        return texturePath;
    }

    public MachineModel[] getMachineModelsFilesClone()
    {
        MachineModel[] modelData = new MachineModel[this.machineModels.length];
        int x = 0;
        for (MachineModel modelReference : this.machineModels)
        {
            modelData[x] = new MachineModel( modelReference.getModelPath(),
                                             modelReference.isModelVisible() );
            x++;
        }

        return modelData;
    }

    public int getModelPartCount()
    {
        if (machineModels == null)
        {
            return 0;
        }

        return machineModels.length;
    }

    public ItemRenderInfo getItemRenderInfoClone()
    {
        ItemRenderInfo renderItem = new ItemRenderInfo( itemRenderInfo.isRenderItemEntity3D(),
                                                        itemRenderInfo.isRenderItemEquipped3D(),
                                                        itemRenderInfo.isRenderItemFirstPerson3D(),
                                                        itemRenderInfo.isRenderItemInventory3D(),
                                                        new ModelScale( itemRenderInfo.getModelItemEquippedScale().getModelScaleX(),
                                                                        // EQUIPPED
                                                                        itemRenderInfo.getModelItemEquippedScale().getModelScaleY(),
                                                                        itemRenderInfo.getModelItemEquippedScale().getModelScaleZ() ),
                                                        new ModelPosition( itemRenderInfo.getModelItemEquippedPosition().getModelTranslateX(),
                                                                           itemRenderInfo.getModelItemEquippedPosition().getModelTranslateY(),
                                                                           itemRenderInfo.getModelItemEquippedPosition().getModelTranslateZ() ),
                                                        new ModelRotation( itemRenderInfo.getModelItemEquippedRotation().getModelRotationAngle(),
                                                                           itemRenderInfo.getModelItemEquippedRotation().getModelRotationX(),
                                                                           itemRenderInfo.getModelItemEquippedRotation().getModelRotationY(),
                                                                           itemRenderInfo.getModelItemEquippedRotation().getModelRotationZ() ),
                                                        new ModelScale( itemRenderInfo.getModelItemFirstPersonScale().getModelScaleX(),
                                                                        // FIRST_PERSON
                                                                        itemRenderInfo.getModelItemFirstPersonScale().getModelScaleY(),
                                                                        itemRenderInfo.getModelItemFirstPersonScale().getModelScaleZ() ),
                                                        new ModelPosition( itemRenderInfo.getModelItemFirstPersonPosition().getModelTranslateX(),
                                                                           itemRenderInfo.getModelItemFirstPersonPosition().getModelTranslateY(),
                                                                           itemRenderInfo.getModelItemFirstPersonPosition().getModelTranslateZ() ),
                                                        new ModelRotation( itemRenderInfo.getModelItemFirstPersonRotation().getModelRotationAngle(),
                                                                           itemRenderInfo.getModelItemFirstPersonRotation().getModelRotationX(),
                                                                           itemRenderInfo.getModelItemFirstPersonRotation().getModelRotationY(),
                                                                           itemRenderInfo.getModelItemFirstPersonRotation().getModelRotationZ() ),
                                                        new ModelScale( itemRenderInfo.getModelItemInventoryScale().getModelScaleX(),
                                                                        // INVENTORY
                                                                        itemRenderInfo.getModelItemInventoryScale().getModelScaleY(),
                                                                        itemRenderInfo.getModelItemInventoryScale().getModelScaleZ() ),
                                                        new ModelPosition( itemRenderInfo.getModelItemInventoryPosition().getModelTranslateX(),
                                                                           itemRenderInfo.getModelItemInventoryPosition().getModelTranslateY(),
                                                                           itemRenderInfo.getModelItemInventoryPosition().getModelTranslateZ() ),
                                                        new ModelRotation( itemRenderInfo.getModelItemInventoryRotation().getModelRotationAngle(),
                                                                           itemRenderInfo.getModelItemInventoryRotation().getModelRotationX(),
                                                                           itemRenderInfo.getModelItemInventoryRotation().getModelRotationY(),
                                                                           itemRenderInfo.getModelItemInventoryRotation().getModelRotationZ() ),
                                                        new ModelScale( itemRenderInfo.getModelItemEntityScale().getModelScaleX(),
                                                                        // ENTITY
                                                                        itemRenderInfo.getModelItemEntityScale().getModelScaleY(),
                                                                        itemRenderInfo.getModelItemEntityScale().getModelScaleZ() ),
                                                        new ModelPosition( itemRenderInfo.getModelItemEntityPosition().getModelTranslateX(),
                                                                           itemRenderInfo.getModelItemEntityPosition().getModelTranslateY(),
                                                                           itemRenderInfo.getModelItemEntityPosition().getModelTranslateZ() ),
                                                        new ModelRotation( itemRenderInfo.getModelItemEntityRotation().getModelRotationAngle(),
                                                                           itemRenderInfo.getModelItemEntityRotation().getModelRotationX(),
                                                                           itemRenderInfo.getModelItemEntityRotation().getModelRotationY(),
                                                                           itemRenderInfo.getModelItemEntityRotation().getModelRotationZ() ) );

        return renderItem;
    }

    public WorldRenderInfo getWorldRenderInfoClone()
    {
        WorldRenderInfo renderWorld =
                new WorldRenderInfo( new ModelPosition( worldRenderInfo.getModelWorldPosition().getModelTranslateX(),
                                                        worldRenderInfo.getModelWorldPosition().getModelTranslateY(),
                                                        worldRenderInfo.getModelWorldPosition().getModelTranslateZ() ),
                                     new ModelScale( worldRenderInfo.getModelWorldScale().getModelScaleX(),
                                                     worldRenderInfo.getModelWorldScale().getModelScaleY(),
                                                     worldRenderInfo.getModelWorldScale().getModelScaleZ() ) );

        return renderWorld;
    }

    public void setItemRenderInfoDefaults()
    {
        this.itemRenderInfo = ModelArchive.defaultItemRenderInfo();
    }

    public void setWorldRenderInfoDefaults()
    {
        this.worldRenderInfo = ModelArchive.defaultWorldRenderInfo();
    }
}

package madscience.rendering;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import madscience.model.ModelPosition;
import madscience.model.ModelRotation;
import madscience.model.ModelScale;


public class ItemRenderInfo
{
    @Expose
    @SerializedName("RenderItemEntity3D")
    private boolean renderItemEntity3D;

    @Expose
    @SerializedName("RenderItemEquipped3D")
    private boolean renderItemEquipped3D;

    @Expose
    @SerializedName("RenderItemFirstPerson3D")
    private boolean renderItemFirstPerson3D;

    @Expose
    @SerializedName("RenderItemInventory3D")
    private boolean renderItemInventory3D;

    @Expose
    @SerializedName("ModelItemEquippedScale")
    private ModelScale modelItemEquippedScale;

    @Expose
    @SerializedName("ModelItemEquippedPosition")
    private ModelPosition modelItemEquippedPosition;

    @Expose
    @SerializedName("ModelItemEquippedRotation")
    private ModelRotation modelItemEquippedRotation;

    @Expose
    @SerializedName("ModelItemFirstPersonScale")
    private ModelScale modelItemFirstPersonScale;

    @Expose
    @SerializedName("ModelItemFirstPersonPosition")
    private ModelPosition modelItemFirstPersonPosition;

    @Expose
    @SerializedName("ModelItemFirstPersonRotation")
    private ModelRotation modelItemFirstPersonRotation;

    @Expose
    @SerializedName("ModelItemInventoryScale")
    private ModelScale modelItemInventoryScale;

    @Expose
    @SerializedName("ModelItemInventoryPosition")
    private ModelPosition modelItemInventoryPosition;

    @Expose
    @SerializedName("ModelItemInventoryRotation")
    private ModelRotation modelItemInventoryRotation;

    @Expose
    @SerializedName("ModelItemEntityScale")
    private ModelScale modelItemEntityScale;

    @Expose
    @SerializedName("ModelItemEntityPosition")
    private ModelPosition modelItemEntityPosition;

    @Expose
    @SerializedName("ModelItemEntityRotation")
    private ModelRotation modelItemEntityRotation;

    public ItemRenderInfo(boolean shouldRenderItemEntity3D,
                          boolean shouldRenderItemEquipped3D,
                          boolean shouldRenderItemFirstPerson3D,
                          boolean shouldRenderItemInventory3D,
                          ModelScale modelItemEquippedScale,
                          // Equipped.
                          ModelPosition modelItemEquippedPosition,
                          ModelRotation modelItemEquippedRotation,
                          ModelScale modelItemFirstPersonScale,
                          // First Person.
                          ModelPosition modelItemFirstPersonPosition,
                          ModelRotation modelItemFirstPersonRotation,
                          ModelScale modelItemInventoryScale,
                          // Inventory.
                          ModelPosition modelItemInventoryPosition,
                          ModelRotation modelItemInventoryRotation,
                          ModelScale modelItemEntityScale,
                          // Entity.
                          ModelPosition modelItemEntityPosition,
                          ModelRotation modelItemEntityRotation)
    {
        super();

        this.renderItemEntity3D = shouldRenderItemEntity3D;
        this.renderItemEquipped3D = shouldRenderItemEquipped3D;
        this.renderItemFirstPerson3D = shouldRenderItemFirstPerson3D;
        this.renderItemInventory3D = shouldRenderItemInventory3D;

        this.modelItemEquippedScale = modelItemEquippedScale;
        this.modelItemEquippedPosition = modelItemEquippedPosition;
        this.modelItemEquippedRotation = modelItemEquippedRotation;

        this.modelItemFirstPersonScale = modelItemFirstPersonScale;
        this.modelItemFirstPersonPosition = modelItemFirstPersonPosition;
        this.modelItemFirstPersonRotation = modelItemFirstPersonRotation;

        this.modelItemInventoryScale = modelItemInventoryScale;
        this.modelItemInventoryPosition = modelItemInventoryPosition;
        this.modelItemInventoryRotation = modelItemInventoryRotation;

        this.modelItemEntityScale = modelItemEntityScale;
        this.modelItemEntityPosition = modelItemEntityPosition;
        this.modelItemEntityRotation = modelItemEntityRotation;
    }

    public ModelScale getModelItemEquippedScale()
    {
        return modelItemEquippedScale;
    }

    public ModelPosition getModelItemEquippedPosition()
    {
        return modelItemEquippedPosition;
    }

    public ModelRotation getModelItemEquippedRotation()
    {
        return modelItemEquippedRotation;
    }

    public ModelScale getModelItemFirstPersonScale()
    {
        return modelItemFirstPersonScale;
    }

    public ModelPosition getModelItemFirstPersonPosition()
    {
        return modelItemFirstPersonPosition;
    }

    public ModelRotation getModelItemFirstPersonRotation()
    {
        return modelItemFirstPersonRotation;
    }

    public ModelScale getModelItemInventoryScale()
    {
        return modelItemInventoryScale;
    }

    public ModelRotation getModelItemInventoryRotation()
    {
        return modelItemInventoryRotation;
    }

    public ModelScale getModelItemEntityScale()
    {
        return modelItemEntityScale;
    }

    public ModelRotation getModelItemEntityRotation()
    {
        return modelItemEntityRotation;
    }

    public boolean isRenderItemEntity3D()
    {
        return renderItemEntity3D;
    }

    public boolean isRenderItemEquipped3D()
    {
        return renderItemEquipped3D;
    }

    public boolean isRenderItemFirstPerson3D()
    {
        return renderItemFirstPerson3D;
    }

    public boolean isRenderItemInventory3D()
    {
        return renderItemInventory3D;
    }

    public ModelPosition getModelItemInventoryPosition()
    {
        return modelItemInventoryPosition;
    }

    public ModelPosition getModelItemEntityPosition()
    {
        return modelItemEntityPosition;
    }
}

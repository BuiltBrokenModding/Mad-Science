package madscience.factory.rendering;

import madscience.factory.model.MadModelPosition;
import madscience.factory.model.MadModelRotation;
import madscience.factory.model.MadModelScale;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MadModelItemRender
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
    private MadModelScale modelItemEquippedScale;
    
    @Expose
    @SerializedName("ModelItemEquippedPosition")
    private MadModelPosition modelItemEquippedPosition;
    
    @Expose
    @SerializedName("ModelItemEquippedRotation")
    private MadModelRotation modelItemEquippedRotation;
    
    @Expose
    @SerializedName("ModelItemFirstPersonScale")
    private MadModelScale modelItemFirstPersonScale;
    
    @Expose
    @SerializedName("ModelItemFirstPersonPosition")
    private MadModelPosition modelItemFirstPersonPosition;
    
    @Expose
    @SerializedName("ModelItemFirstPersonRotation")
    private MadModelRotation modelItemFirstPersonRotation; 
    
    @Expose
    @SerializedName("ModelItemInventoryScale")
    private MadModelScale modelItemInventoryScale;
    
    @Expose
    @SerializedName("ModelItemInventoryPosition")
    private MadModelPosition modelItemInventoryPosition;
    
    @Expose
    @SerializedName("ModelItemInventoryRotation")
    private MadModelRotation modelItemInventoryRotation;
    
    @Expose
    @SerializedName("ModelItemEntityScale")
    private MadModelScale modelItemEntityScale;
    
    @Expose
    @SerializedName("ModelItemEntityPosition")
    private MadModelPosition modelItemEntityPosition;
    
    @Expose
    @SerializedName("ModelItemEntityRotation")
    private MadModelRotation modelItemEntityRotation;
    
    public MadModelItemRender(
            boolean shouldRenderItemEntity3D,
            boolean shouldRenderItemEquipped3D,
            boolean shouldRenderItemFirstPerson3D,
            boolean shouldRenderItemInventory3D,
            MadModelScale modelItemEquippedScale,               // Equipped.
            MadModelPosition modelItemEquippedPosition,
            MadModelRotation modelItemEquippedRotation,
            MadModelScale modelItemFirstPersonScale,            // First Person.
            MadModelPosition modelItemFirstPersonPosition,
            MadModelRotation modelItemFirstPersonRotation,
            MadModelScale modelItemInventoryScale,              // Inventory.
            MadModelPosition modelItemInventoryPosition,
            MadModelRotation modelItemInventoryRotation,
            MadModelScale modelItemEntityScale,                 // Entity.
            MadModelPosition modelItemEntityPosition,
            MadModelRotation modelItemEntityRotation)
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

    public MadModelScale getModelItemEquippedScale()
    {
        return modelItemEquippedScale;
    }

    public MadModelPosition getModelItemEquippedPosition()
    {
        return modelItemEquippedPosition;
    }

    public MadModelRotation getModelItemEquippedRotation()
    {
        return modelItemEquippedRotation;
    }

    public MadModelScale getModelItemFirstPersonScale()
    {
        return modelItemFirstPersonScale;
    }

    public MadModelPosition getModelItemFirstPersonPosition()
    {
        return modelItemFirstPersonPosition;
    }

    public MadModelRotation getModelItemFirstPersonRotation()
    {
        return modelItemFirstPersonRotation;
    }

    public MadModelScale getModelItemInventoryScale()
    {
        return modelItemInventoryScale;
    }

    public MadModelRotation getModelItemInventoryRotation()
    {
        return modelItemInventoryRotation;
    }

    public MadModelScale getModelItemEntityScale()
    {
        return modelItemEntityScale;
    }

    public MadModelRotation getModelItemEntityRotation()
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

    public MadModelPosition getModelItemInventoryPosition()
    {
        return modelItemInventoryPosition;
    }

    public MadModelPosition getModelItemEntityPosition()
    {
        return modelItemEntityPosition;
    }
}

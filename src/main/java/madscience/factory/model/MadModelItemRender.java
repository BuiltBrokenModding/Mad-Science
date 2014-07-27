package madscience.factory.model;

import com.google.gson.annotations.Expose;

public class MadModelItemRender
{
    @Expose
    private boolean renderItemEntity3D;
    
    @Expose
    private boolean renderItemEquipped3D;
    
    @Expose
    private boolean renderItemFirstPerson3D;
    
    @Expose
    private boolean renderItemInventory3D;
    
    @Expose
    private MadModelScale modelItemEquippedScale;
    
    @Expose
    private MadModelPosition modelItemEquippedPosition;
    
    @Expose
    private MadModelRotation modelItemEquippedRotation;
    
    @Expose
    private MadModelScale modelItemFirstPersonScale;
    
    @Expose
    private MadModelPosition modelItemFirstPersonPosition;
    
    @Expose
    private MadModelRotation modelItemFirstPersonRotation; 
    
    @Expose
    private MadModelScale modelItemInventoryScale;
    
    @Expose
    private MadModelRotation modelItemInventoryRotation;
    
    @Expose
    private MadModelScale modelItemEntityScale;
    
    @Expose
    private MadModelRotation modelItemEntityRotation;
    
    public MadModelItemRender(
            boolean shouldRenderItemEntity3D,
            boolean shouldRenderItemEquipped3D,
            boolean shouldRenderItemFirstPerson3D,
            boolean shouldRenderItemInventory3D,
            MadModelScale modelItemEquippedScale,
            MadModelPosition modelItemEquippedPosition,
            MadModelRotation modelItemEquippedRotation,
            MadModelScale modelItemFirstPersonScale,
            MadModelPosition modelItemFirstPersonPosition,
            MadModelRotation modelItemFirstPersonRotation,
            MadModelScale modelItemInventoryScale,
            MadModelRotation modelItemInventoryRotation,
            MadModelScale modelItemEntityScale,
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
        this.modelItemInventoryRotation = modelItemInventoryRotation;
        
        this.modelItemEntityScale = modelItemEntityScale;
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
}

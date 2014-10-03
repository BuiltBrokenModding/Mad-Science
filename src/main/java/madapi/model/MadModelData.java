package madapi.model;

import net.minecraft.nbt.NBTTagCompound;

public class MadModelData
{
    private boolean isModelVisible;
    
    private String modelPieceName;

    private MadModelData() { }
    
    public MadModelData(
            boolean isModelVisible,
            String modelPieceName)
    {
        this.isModelVisible = isModelVisible;
        this.modelPieceName = modelPieceName;
    }
    
    public boolean isModelVisible()
    {
        return isModelVisible;
    }

    public void setModelVisible(boolean isModelVisible)
    {
        this.isModelVisible = isModelVisible;
    }

    public String getModelPieceName()
    {
        return modelPieceName;
    }

    public void setModelPieceName(String modelPieceName)
    {
        this.modelPieceName = modelPieceName;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound modelTag)
    {
        modelTag.setString("ModelName", this.modelPieceName);
        modelTag.setBoolean("ModelVisible", this.isModelVisible);
        
        return modelTag;
    }
    
    public static MadModelData loadModelDataFromNBT(NBTTagCompound modelPart)
    {
        MadModelData itemstack = new MadModelData();
        itemstack.readFromNBT(modelPart);
        return itemstack != null ? itemstack : null; 
    }

    private void readFromNBT(NBTTagCompound modelPart)
    {
        this.modelPieceName = modelPart.getString("ModelName");
        this.isModelVisible = modelPart.getBoolean("ModelVisible");
    }
}

package madscience.model;


import net.minecraft.nbt.NBTTagCompound;


public class ModelData
{
    private boolean isModelVisible;

    private String modelPieceName;

    private ModelData()
    {
    }

    public ModelData(boolean isModelVisible, String modelPieceName)
    {
        this.isModelVisible = isModelVisible;
        this.modelPieceName = modelPieceName;
    }

    public static ModelData loadModelDataFromNBT(NBTTagCompound modelPart)
    {
        ModelData itemstack = new ModelData();
        itemstack.readFromNBT( modelPart );
        return itemstack != null
               ? itemstack
               : null;
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
        modelTag.setString( "ModelName",
                            this.modelPieceName );
        modelTag.setBoolean( "ModelVisible",
                             this.isModelVisible );

        return modelTag;
    }

    private void readFromNBT(NBTTagCompound modelPart)
    {
        this.modelPieceName = modelPart.getString( "ModelName" );
        this.isModelVisible = modelPart.getBoolean( "ModelVisible" );
    }
}

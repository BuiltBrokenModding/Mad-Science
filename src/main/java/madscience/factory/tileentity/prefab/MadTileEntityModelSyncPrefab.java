package madscience.factory.tileentity.prefab;

import madscience.factory.model.MadModel;
import madscience.factory.model.MadModelData;
import madscience.factory.tileentity.MadTileEntityFactoryProduct;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class MadTileEntityModelSyncPrefab extends MadTileEntityDamagePrefab
{
    /** Path to current texture that should be displayed on our model. */
    private String entityTexture;
    
    /** Snapshot of current model configuration for this entity. */
    private MadModelData[] entityModelReference;
    
    public MadTileEntityModelSyncPrefab() 
    {
        super();
    }

    public MadTileEntityModelSyncPrefab(MadTileEntityFactoryProduct registeredMachine)
    {
        super(registeredMachine);
        
        // Default model and texture information.
        MadModel renderingInformation = registeredMachine.getModelArchive();
        if (renderingInformation != null)
        {
            // Load default texture.
            if (this.entityTexture == null)
            {
                this.entityTexture = renderingInformation.getMachineTexture();
            }
            
            // Load the default model configuration.
            if (this.entityModelReference == null)
            {
                this.entityModelReference = renderingInformation.getMachineModelsDataClone();
            }
        }
    }
    
    public void hideAllModelPieces()
    {
        // Hide all the model pieces that makeup this machine.
        for (MadModelData modelReference : this.getEntityModelData())
        {
            this.setModelWorldRenderVisibilityByName(modelReference.getModelPieceName(), false);
        }
    }
    
    public void setModelWorldRenderVisibilityByName(String modelName, boolean visible)
    {
        int x = 0;
        for (MadModelData modelData : this.entityModelReference)
        {
            if (modelData.getModelPieceName().equals(modelName))
            {
                if (modelData.isModelVisible() != visible)
                {
                    this.entityModelReference[x] = new MadModelData(visible, modelName);
                    break;
                }
                break;
            }
            x++;
        }
    }
    
    /** Sets the texture resource path to whatever is specified. */
    public void setTextureRenderedOnModel(String entityTexture)
    {
        this.entityTexture = entityTexture;
    }
    
    public String getEntityTexture()
    {
        return entityTexture;
    }
    
    @Override
    public void updateEntity()
    {
        super.updateEntity();
    }

    @Override
    public void initiate()
    {
        super.initiate();
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        
        // Model instance reload.
        NBTTagList modelTagList = nbt.getTagList("ModelDataList");
        this.entityModelReference = new MadModelData[modelTagList.tagCount()];
        
        for (int i = 0; i < modelTagList.tagCount(); ++i)
        {
            NBTTagCompound modelPart = (NBTTagCompound) modelTagList.tagAt(i);
            byte partNumber = modelPart.getByte("ModelData");
            
            if (partNumber >= 0 && partNumber < this.entityModelReference.length)
            {
                this.setModelDataPartContents(partNumber, MadModelData.loadModelDataFromNBT(modelPart));
            }
        }
        
        // Path to current texture what should be loaded onto the model.
        this.entityTexture = nbt.getString("TexturePath");
    }
    
    private void setModelDataPartContents(int partNumber, MadModelData modelData)
    {
        this.entityModelReference[partNumber] = modelData;
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        
        // Model instance saving.
        NBTTagList modelTagList = new NBTTagList();
        for (int i = 0; i < this.entityModelReference.length; ++i)
        {
            NBTTagCompound modelTag = new NBTTagCompound();
            modelTag.setByte("ModelData", (byte) i);
            this.entityModelReference[i].writeToNBT(modelTag);
            modelTagList.appendTag(modelTag);
        }
        
        nbt.setTag("ModelDataList", modelTagList);
        
        // Path to current texture that should be loaded onto the model.
        nbt.setString("TexturePath", this.entityTexture);
    }

    public MadModelData[] getEntityModelData()
    {
        return entityModelReference;
    }
}

package madscience.factory.tileentity.prefab;

import java.util.LinkedHashMap;
import java.util.Map;

import madscience.factory.model.MadModel;
import madscience.factory.model.MadModelFile;
import madscience.factory.tileentity.MadTileEntityFactoryProduct;
import net.minecraft.nbt.NBTTagCompound;

public class MadTileEntityModelSyncPrefab extends MadTileEntityDamagePrefab
{
    /** Path to current texture that should be displayed on our model. */
    private String entityTexture;
    
    /** Contains model property classes associated with a given registered machine.
     *  Purpose of this list is to act as primary reference on server for default model configurations. */
    private Map<String, MadModelFile> modelRenderingInfo;
    
    public MadTileEntityModelSyncPrefab()
    {
        super();
    }

    public MadTileEntityModelSyncPrefab(MadTileEntityFactoryProduct registeredMachine)
    {
        super(registeredMachine);
        
        // Default model and texture information.
        this.modelRenderingInfo = new LinkedHashMap<String, MadModelFile>();
        MadModel renderingInformation = registeredMachine.getModelArchive();
        if (renderingInformation != null)
        {
            // Load default model rendering information into a server instance so we can manipulate it.
            for (MadModelFile defaultModelConfig : renderingInformation.getMachineModels())
            {
                this.modelRenderingInfo.put(defaultModelConfig.getModelName(), defaultModelConfig);
            }
            
            // Load default texture.
            this.entityTexture = renderingInformation.getMachineTexture();
        }
    }
    
    public void setModelWorldRenderVisibilityByName(String modelName, boolean shouldRender)
    {
        if (modelRenderingInfo.containsKey(modelName))
        {
            MadModelFile referenceModel = modelRenderingInfo.get(modelName);
            referenceModel.setVisibility(shouldRender);
            MadModelFile updatedEntry = modelRenderingInfo.put(modelName, referenceModel);
            if (!referenceModel.equals(updatedEntry))
            {
                throw new IllegalArgumentException("[" + this.getMachineInternalName() + "]Cannot modify visibility of '" + modelName + "' because it does not exist in reference list!");
            }
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
        
        // TODO: Reload model specific information from NBT data for complex machine state saving.
        
        
        // Path to current texture what should be loaded onto the model.
        this.entityTexture = nbt.getString("TexturePath");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        
        // TODO: Model instance information that is specific to this machine and this machine only in the game world.
        
        
        // Path to current texture that should be loaded onto the model.
        nbt.setString("TexturePath", this.entityTexture);
    }

    public Map<String, MadModelFile> getModelRenderingInfo()
    {
        return modelRenderingInfo;
    }
}

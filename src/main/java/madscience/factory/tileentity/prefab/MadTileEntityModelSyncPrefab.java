package madscience.factory.tileentity.prefab;

import madscience.factory.model.MadModel;
import madscience.factory.tileentity.MadTileEntityFactoryProduct;
import net.minecraft.nbt.NBTTagCompound;

public class MadTileEntityModelSyncPrefab extends MadTileEntityDamagePrefab
{
    /** Path to current texture that should be displayed on our model. */
    private String entityTexture;
    
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
            this.entityTexture = renderingInformation.getMachineTexture();
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
}

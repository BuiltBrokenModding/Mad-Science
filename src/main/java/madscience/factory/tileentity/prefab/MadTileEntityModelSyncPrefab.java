package madscience.factory.tileentity.prefab;

import java.util.Hashtable;

import madscience.factory.MadTileEntityFactory;
import madscience.factory.MadTileEntityFactoryProduct;
import madscience.factory.mod.MadMod;
import madscience.factory.model.MadModelFile;
import net.minecraft.nbt.NBTTagCompound;

public class MadTileEntityModelSyncPrefab extends MadTileEntityDamagePrefab
{
    /** Hashtable which links client models names to objects, this is instanced per machine to allow each machine models
     *  to be independent of their parent/master listing. */
    private Hashtable<String, MadModelFile> clientModelWorldReference = null;
    
    /** Hashtable linking client model names to the master object listings, this is instanced per machine to allow each
     *  machine model to be independent of their parent/master listing. */
    private Hashtable<String, MadModelFile> clientModelItemReference = null;
    
    /** Path to current texture that should be displayed on our model. */
    private String entityTexture;
    
    public MadTileEntityModelSyncPrefab()
    {
        super();
    }

    public MadTileEntityModelSyncPrefab(MadTileEntityFactoryProduct registeredMachine)
    {
        super(registeredMachine);
        
        // Machine when being spawned in world for the first time by the player get information about rendering passed to them.
        this.loadModelReferencesFromMaster(registeredMachine);
        
        // Default texture that is to be used on the machine if none specified, also used by icons before entity is rendered in world.
        // TODO: Make this load from JSON loader so idle.png does not have to be the default texture.
        this.entityTexture = "models/" + registeredMachine.getMachineName() + "/idle.png";
    }

    /** Using a registered machine reference we can fill out the model instance information for world and item rendering.
     *  IMPORTANT: This data is purely a reference and meant to fill out the arrays with defaults. Instance information about
     *  each individual model status per machine is loaded from NBT. */
    private void loadModelReferencesFromMaster(MadTileEntityFactoryProduct registeredMachine)
    {
        // Create the hashtables that will hold our model reference from factory product.
        clientModelWorldReference = new Hashtable<String, MadModelFile>();
        clientModelItemReference = new Hashtable<String, MadModelFile>();
        
        // Populate the client model arrays from master references in factory product data.
        Hashtable<String,MadModelFile> masterWorldModelReference = registeredMachine.getMasterWorldModelHashtable();
        Hashtable<String,MadModelFile> masterItemModelReference = registeredMachine.getMasterItemModelHashtable();
        
        if (masterWorldModelReference != null)
        {
            clientModelWorldReference.putAll(masterWorldModelReference);
        }
        
        if (masterItemModelReference != null)
        {
            clientModelItemReference.putAll(masterItemModelReference);
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
    
    /** Called by updateEntity() on server only to update world model rendering information.
     *  Determines how item is rendered in the world on clients.  */
    public void updateWorldModel()
    {
        
    }
    
    /** Called by updateEntity() on server only to update model rendering information.
     *  Determines how item is rendered on a client. */
    public void updateItemModel()
    {
        
    }
    
    public MadModelFile[] getClientModelsforItemRender()
    {
        return clientModelItemReference.values().toArray(new MadModelFile[]{});
    }

    public MadModelFile[] getClientModelsForWorldRender()
    {
        return clientModelWorldReference.values().toArray(new MadModelFile[]{});
    }
    
    /** Loop through received model status from server and apply changes to client instance
     *  of model rendering for items that show up in hotbar and when item is not being displayed
     *  in the game world. */
    public void setModelsForClientItemRender(MadModelFile[] recievedItemModelList)
    {
        if (recievedItemModelList == null)
        {
            return;
        }
        
        // Apply information from packet to client model reference.
        for (MadModelFile recievedItemModel : recievedItemModelList)
        {
            // Skip if there is no model data to actually set!
            if (recievedItemModel == null)
            {
                continue;
            }
            
            // Ensure that the client model reference contains the key we want to modify.
            if (this.clientModelItemReference.containsKey(recievedItemModel.getModelName()))
            {
                MadModelFile clientItemModel = this.clientModelItemReference.get(recievedItemModel.getModelName());
                
                // Model visibility.
                clientItemModel.setModelVisible(recievedItemModel.isModelVisible());
            }
        }
    }
    
    /** Loop through received model status from server and apply changes to client instance of reference objects.
     *  This particular instance is for world based models which are used to render the machine in the game world. */
    public void setModelsForClientWorldRender(MadModelFile[] recievedWorldModelList)
    {
        // Abort if input parameter is empty, nothing to set!
        if (recievedWorldModelList == null)
        {
            return;
        }
        
        // Apply information from packet to client model reference.
        for (MadModelFile recievedWorldModel : recievedWorldModelList)
        {
            // Skip if the model data is null, this would be because there is no tile entity yet.
            if (recievedWorldModel == null)
            {
                continue;
            }
            
            // Ensure that the client model reference contains the key we want to modify.
            if (this.clientModelWorldReference.containsKey(recievedWorldModel.getModelName()))
            {
                MadModelFile clientWorldModel = this.clientModelWorldReference.get(recievedWorldModel.getModelName());
                //this.clientModelWorldReference.remove(recievedWorldModel.getModelName());
                
                // Model visibility.
                clientWorldModel.setModelVisible(recievedWorldModel.isModelVisible());
                
                // Update the hashmap entry to be sent over the wire.
                //this.clientModelWorldReference.put(recievedWorldModel.getModelName(), clientWorldModel);
            }
        }
    }
    
    /** Sets server item information for model visibility by using the name of the model piece.
     *  This is sent to clients during packet update allowing instanced model manipulation. */
    public void setModelItemRenderVisibilityByName(String modelName, boolean isVisible)
    {
        if (this.clientModelItemReference.containsKey(modelName))
        {
            MadModelFile clientItemModel = this.clientModelItemReference.get(modelName);
            clientItemModel.setModelVisible(isVisible);
            
            // Inserting a value into hashtable updated existing values and returns them.
            MadModelFile updatedItemModelValue = this.clientModelItemReference.put(modelName, clientItemModel);
            
            // Check that the values match.
            if (!updatedItemModelValue.equals(clientItemModel))
            {
                MadMod.log().info("[" + this.getMachineInternalName() + "]Unable to update item model '" + modelName + "', however it exists in the reference model list.");
            }
        }
        else
        {
            MadMod.log().info("[" + this.getMachineInternalName() + "]Unable to update item model '" + modelName + "' because it doesn't exist.");
        }
    }
    
    /** Sets server world information for model visibility by using the name of the model piece.
     *  This is sent to clients during packet update allowing instanced model manipulation. */
    public void setModelWorldRenderVisibilityByName(String modelName, boolean isVisible)
    {
        if (this.clientModelWorldReference.containsKey(modelName))
        {
            MadModelFile clientWorldModel = this.clientModelWorldReference.get(modelName);
            clientWorldModel.setModelVisible(isVisible);
            
            // Inserting a value into hashtable updated existing values and returns them.
            MadModelFile updatedWorldModelValue = this.clientModelWorldReference.put(modelName, clientWorldModel);
            
            // Check that the values match.
            if (!updatedWorldModelValue.equals(clientWorldModel))
            {
                MadMod.log().info("[" + this.getMachineInternalName() + "]Unable to update world model '" + modelName + "', however it exists in the reference model list.");
            }
        }
        else
        {
            MadMod.log().info("[" + this.getMachineInternalName() + "]Unable to update world model '" + modelName + "' because it doesn't exist.");
        }
    }
    
    @Override
    public void updateEntity()
    {
        super.updateEntity();
        
        if (!this.worldObj.isRemote)
        {
            // Update world model information now that we have manipulated it.
            this.updateWorldModel();
            
            // Update the item model information if required.
            this.updateItemModel();
        }
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
        
        // Loading from disk and not spawned we need to get info from factory product.
        MadTileEntityFactoryProduct potentialMachine = this.getRegisteredMachine();
        if (potentialMachine != null)
        {
            this.loadModelReferencesFromMaster(potentialMachine);
        }
        
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
}

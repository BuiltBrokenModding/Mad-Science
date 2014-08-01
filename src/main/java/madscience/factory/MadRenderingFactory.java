package madscience.factory;

import java.util.LinkedHashMap;
import java.util.Map;

import madscience.factory.mod.MadMod;
import madscience.factory.model.MadTileModel;
import madscience.factory.model.MadModelData;
import madscience.factory.model.MadModelPosition;
import madscience.factory.model.MadModelScale;
import madscience.factory.rendering.MadRenderingFactoryProduct;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MadRenderingFactory
{
    /** Allow only one instance to be created. */ 
    private static MadRenderingFactory instance;
    
    /** Contains model property classes associated with a given registered machine.
     *  Purpose of this list is to act as primary reference on client for default model configurations. */
    private static final Map<String, MadTileModel> masterModelInformationReference = new LinkedHashMap<String, MadTileModel>();
    
    /** Primary rendering registry which will keep track of every item and entity that exists in the game world. */
    private static final Map<String, MadRenderingFactoryProduct> worldInstanceRenderingReference = new LinkedHashMap<String, MadRenderingFactoryProduct>();
    
    private MadRenderingFactory()
    {
        super();
    }
    
    public static synchronized MadRenderingFactory instance()
    {
        if (instance == null)
        {
            instance = new MadRenderingFactory();
        }
        
        return instance;
    }
    
    /** Associates given product name with a collection of 'default' rendering information. */
    public void registerModelsToProduct(String productName, MadTileModel renderInformation)
    {
        // Check for input parameters being null.
        if (productName == null || renderInformation == null)
        {
            throw new IllegalArgumentException("Cannot register machine with null parameters!");
        }
        
        // Ensure the product name is not empty.
        if (productName.isEmpty())
        {
            throw new IllegalArgumentException("Cannot register empty product name!");
        }
        
        // Ensure that we have not already associated default models for this product.
        if (!masterModelInformationReference.containsKey(productName))
        {
            // Add the product and associated models from tile entity factory product.
            MadRenderingFactory.masterModelInformationReference.put(productName, renderInformation);
            MadMod.log().info("[" + productName + "]Loading master reference for machine with " + renderInformation.getModelPartCount() + " model parts.");
        }
        else
        {
            throw new IllegalArgumentException("Cannot register '" + productName + "' because it already exists in rendering factory!");
        }
    }
    
    /** Updates or creates a new rendering instance for a given model.
     *  The other parameters help ensure uniqueness amongst the mapping. */
    public void updateModelInstance(
            String productName,
            boolean isItem,
            MadModelData[] modelInformation,
            MadModelPosition modelPosition,
            MadModelScale modelScale,
            String... renderKeyData)
    {
        // Check all input data, this is very important for integrity sake.
        boolean badInputData = false;
        
        // Check model information.
        if (productName == null ||
            modelInformation == null ||
            modelPosition == null ||
            modelScale == null)
        {
            badInputData = true;
        }
        
        // Check machine name.
        if (productName.isEmpty())
        {
            badInputData = true;
        }
        
        // Throw exception if anything bad happens.
        if (badInputData)
        {
            throw new IllegalArgumentException("Invalid model information, cannot update model instance!");
        }
        
        // Create a unique key which we use to create or find existing model rendering instance.
        String renderKey = this.createRenderKey(productName, isItem, renderKeyData);
        
        // If there was no listing to update then we will create a new one!
        if (!MadRenderingFactory.worldInstanceRenderingReference.containsKey(renderKey))
        {
            this.createNewRenderingInstance(productName, isItem, renderKey);
        }
        
        // Check to see if the particular rendering instance exists.
        if (MadRenderingFactory.worldInstanceRenderingReference.containsKey(renderKey))
        {
            // Grab the existing instance.
            MadRenderingFactoryProduct factoryRenderProduct = MadRenderingFactory.worldInstanceRenderingReference.get(renderKey);
            
            // Update the model information by using model names and transmitted status information.
            boolean modelsUpdated = false;
            int totalModelsUpdated = 0;
            for (MadModelData modelPiece : modelInformation)
            {               
                if (factoryRenderProduct.setRenderVisibilityByName(modelPiece.getModelPieceName(), modelPiece.isModelVisible()))
                {
                    // Model was updated!
                    modelsUpdated = true;
                    totalModelsUpdated++;
                }
            }
            
            // Update the world rendering information if required.
            factoryRenderProduct.setWorldRenderInformation(modelPosition, modelScale);
            
            // Update the rendering instance listing.
            MadRenderingFactoryProduct updatedListing = MadRenderingFactory.worldInstanceRenderingReference.put(renderKey, factoryRenderProduct);
            
            // Inform console if updated model visibility.
            if (modelsUpdated)
            {
                MadMod.log().info("[" + productName + "]Updating instance number " + updatedListing.getRenderingID() + " with key " + renderKey + " total of " + totalModelsUpdated + " updates.");
            }
            
            // Check that inserted data matches updated one.
            if (!factoryRenderProduct.equals(updatedListing))
            {
                throw new IllegalArgumentException("Cannot update rendering instance for '" + productName + "'. Something went wrong with updating rendering instance!");
            }
        }
    }

    private void createNewRenderingInstance(String productName, boolean isItem, String renderKey)
    {
        // Create a new rendering instance for this query.
        if (masterModelInformationReference.containsKey(productName))
        {
            // Grab the master reference to this machines models that have already been loaded into memory.
            MadTileModel masterMadModelReference = MadRenderingFactory.masterModelInformationReference.get(productName);
            
            String renderType;
            if (isItem)
            {
                renderType = "item";
            }
            else
            {
                renderType = "tile entity";
            }
            
            // Create a new rendering instance for this device.
            MadMod.log().info("[" + productName + "]Creating new " + renderType + " render instance with key: " + renderKey);
            MadRenderingFactory.worldInstanceRenderingReference.put(renderKey, new MadRenderingFactoryProduct(masterMadModelReference));
        }
        else
        {
            throw new IllegalArgumentException("Cannot create rendering instance for '" + productName + "'. The master reference required for cloning does not exist!");
        }
    }

    /** Creates a unique string based key which is used by the linked list to keep track of which instance belongs to what block in the game world and or item rendering references for inventory items. */
    private String createRenderKey(String productName, boolean isItem, String... renderKeyData)
    {
        // Build the key which is used to make each thing unique.
        StringBuffer renderKey = new StringBuffer();
        if (!isItem)
        {
            // World instance uses coordinates plus name to make instance unique.
            renderKey.append("tile:");
            renderKey.append(productName);
        }
        else
        {
            // Item instance should have damage value
            renderKey.append("item:");
            renderKey.append(productName);
        }
        
        // Add all the parts of the render key data into a single string.
        for (int i = 0; i < renderKeyData.length; i++) 
        {
            renderKey.append(":");
            renderKey.append(renderKeyData[i]);
        }
        
        // Output the final render key as it will be used in linked list mapping.
        return renderKey.toString();
    }

    /** Grabs current model instance information or creates new model instance from reference. */
    public MadRenderingFactoryProduct getModelInstance(String productName, boolean isItem, String... renderKeyData)
    {
        // Create the key which lets us query the instancing reference.
        String renderKey = this.createRenderKey(productName, isItem, renderKeyData);
        
        // Check to see if the particular rendering instance exists.
        if (MadRenderingFactory.worldInstanceRenderingReference.containsKey(renderKey))
        {
            return MadRenderingFactory.worldInstanceRenderingReference.get(renderKey);
        }
        else
        {
            this.createNewRenderingInstance(productName, isItem, renderKey);
            return MadRenderingFactory.worldInstanceRenderingReference.get(renderKey);
        }
    }
}

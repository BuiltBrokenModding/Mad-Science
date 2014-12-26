package madscience.factory;


import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import madscience.mod.ModLoader;
import madscience.model.ModelArchive;
import madscience.model.ModelData;
import madscience.model.ModelPosition;
import madscience.model.ModelScale;
import madscience.product.RenderingFactoryProduct;

import java.util.LinkedHashMap;
import java.util.Map;


@SideOnly(Side.CLIENT)
public class RenderingFactory
{
    /**
     * Contains model property classes associated with a given registered machine.
     * Purpose of this list is to act as primary reference on client for default model configurations.
     */
    private static final Map<String, ModelArchive> masterModelInformationReference =
            new LinkedHashMap<String, ModelArchive>();
    /**
     * Primary rendering registry which will keep track of every item and entity that exists in the game world.
     */
    private static final Map<String, RenderingFactoryProduct> worldInstanceRenderingReference =
            new LinkedHashMap<String, RenderingFactoryProduct>();
    /**
     * Allow only one instance to be created.
     */
    private static RenderingFactory instance;

    private RenderingFactory()
    {
        super();
    }

    public static synchronized RenderingFactory instance()
    {
        if (instance == null)
        {
            instance = new RenderingFactory();
        }

        return instance;
    }

    /**
     * Associates given product name with a collection of 'default' rendering information.
     */
    public void registerModelsToProduct(String productName, ModelArchive renderInformation)
    {
        // Check for input parameters being null.
        if (productName == null || renderInformation == null)
        {
            throw new IllegalArgumentException( "Cannot register machine with null parameters!" );
        }

        // Ensure the product name is not empty.
        if (productName.isEmpty())
        {
            throw new IllegalArgumentException( "Cannot register empty product name!" );
        }

        // Ensure that we have not already associated default models for this product.
        if (! masterModelInformationReference.containsKey( productName ))
        {
            // Add the product and associated models from tile entity factory product.
            RenderingFactory.masterModelInformationReference.put( productName,
                                                                  renderInformation );
            ModLoader.log().info( "[" + productName +
                                  "]Loading master reference for product with " +
                                  renderInformation.getModelPartCount() +
                                  " model parts." );
        }
        else
        {
            throw new IllegalArgumentException( "Cannot register '" +
                                                productName +
                                                "' because it already exists in rendering factory!" );
        }
    }

    /**
     * Updates or creates a new rendering instance for a given model.
     * The other parameters help ensure uniqueness amongst the mapping.
     */
    public void updateModelInstance(String productName,
                                    boolean isItem,
                                    ModelData[] modelInformation,
                                    ModelPosition modelPosition,
                                    ModelScale modelScale,
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
            throw new IllegalArgumentException( "Invalid model information, cannot update model instance!" );
        }

        // Create a unique key which we use to create or find existing model rendering instance.
        String renderKey = this.createRenderKey( productName,
                                                 isItem,
                                                 renderKeyData );

        // If there was no listing to update then we will create a new one!
        if (! RenderingFactory.worldInstanceRenderingReference.containsKey( renderKey ))
        {
            this.createNewRenderingInstance( productName,
                                             isItem,
                                             renderKey );
        }

        // Check to see if the particular rendering instance exists.
        if (RenderingFactory.worldInstanceRenderingReference.containsKey( renderKey ))
        {
            // Grab the existing instance.
            RenderingFactoryProduct factoryRenderProduct =
                    RenderingFactory.worldInstanceRenderingReference.get( renderKey );

            // Update the model information by using model names and transmitted status information.
            boolean modelsUpdated = false;
            int totalModelsUpdated = 0;
            for (ModelData modelPiece : modelInformation)
            {
                if (factoryRenderProduct.setRenderVisibilityByName( modelPiece.getModelPieceName(),
                                                                    modelPiece.isModelVisible() ))
                {
                    // Model was updated!
                    modelsUpdated = true;
                    totalModelsUpdated++;
                }
            }

            // Update the world rendering information if required.
            factoryRenderProduct.setWorldRenderInformation( modelPosition,
                                                            modelScale );

            // Update the rendering instance listing.
            RenderingFactoryProduct updatedListing = RenderingFactory.worldInstanceRenderingReference.put( renderKey,
                                                                                                           factoryRenderProduct );

            // Inform console if updated model visibility.
            if (modelsUpdated)
            {
                ModLoader.log().info( "[" + productName +
                                      "]Updating instance number " +
                                      updatedListing.getRenderingID() +
                                      " with key " + renderKey +
                                      " total of " + totalModelsUpdated +
                                      " updates." );
            }

            // Check that inserted data matches updated one.
            if (! factoryRenderProduct.equals( updatedListing ))
            {
                throw new IllegalArgumentException( "Cannot update rendering instance for '" +
                                                    productName +
                                                    "'. Something went wrong with updating rendering instance!" );
            }
        }
    }

    private void createNewRenderingInstance(String productName, boolean isItem, String renderKey)
    {
        // Create a new rendering instance for this query.
        if (masterModelInformationReference.containsKey( productName ))
        {
            // Grab the master reference to this machines models that have already been loaded into memory.
            ModelArchive masterModelArchiveReference =
                    RenderingFactory.masterModelInformationReference.get( productName );

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
            ModLoader.log().info( "[" + productName + "]Creating new " +
                                  renderType +
                                  " render instance with key: " +
                                  renderKey );
            RenderingFactory.worldInstanceRenderingReference.put( renderKey,
                                                                  new RenderingFactoryProduct( masterModelArchiveReference ) );
        }
        else
        {
            throw new IllegalArgumentException( "Cannot create rendering instance for '" +
                                                productName +
                                                "'. The master reference required for cloning does not exist!" );
        }
    }

    /**
     * Creates a unique string based key which is used by the linked list to keep track of which instance belongs to what block in the game world and or item rendering references for inventory items.
     */
    private String createRenderKey(String productName, boolean isItem, String... renderKeyData)
    {
        // Build the key which is used to make each thing unique.
        StringBuffer renderKey = new StringBuffer();
        if (! isItem)
        {
            // World instance uses coordinates plus name to make instance unique.
            renderKey.append( "tile:" );
            renderKey.append( productName );
        }
        else
        {
            // Item instance should have damage value
            renderKey.append( "item:" );
            renderKey.append( productName );
        }

        // Add all the parts of the render key data into a single string.
        for (int i = 0; i < renderKeyData.length; i++)
        {
            renderKey.append( ":" );
            renderKey.append( renderKeyData[i] );
        }

        // Output the final render key as it will be used in linked list mapping.
        return renderKey.toString();
    }

    /**
     * Grabs current model instance information or creates new model instance from reference.
     */
    public RenderingFactoryProduct getModelInstance(String productName, boolean isItem, String... renderKeyData)
    {
        // Create the key which lets us query the instancing reference.
        String renderKey = this.createRenderKey( productName,
                                                 isItem,
                                                 renderKeyData );

        // Check to see if the particular rendering instance exists.
        if (RenderingFactory.worldInstanceRenderingReference.containsKey( renderKey ))
        {
            return RenderingFactory.worldInstanceRenderingReference.get( renderKey );
        }
        else
        {
            this.createNewRenderingInstance( productName,
                                             isItem,
                                             renderKey );
            return RenderingFactory.worldInstanceRenderingReference.get( renderKey );
        }
    }
}

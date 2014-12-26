package madscience.product;


import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import madscience.ModMetadata;
import madscience.model.*;
import madscience.rendering.ItemRenderInfo;
import madscience.rendering.WorldRenderInfo;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;

import java.util.LinkedHashMap;
import java.util.Map;


@SideOnly(Side.CLIENT)
public class RenderingFactoryProduct
{
    /**
     * Unique ID for our model to render in the world.
     */
    private int renderingID = - 1;

    /**
     * Default texture that is rendered on the model if no other is specified.
     */
    private ResourceLocation textureResource = null;

    /**
     * Primary reference to all loaded models.
     */
    private Map<String, TechneModel> modelRenderingReference = null;

    /**
     * Contains item rendering information such as should render icons in 3D, scaling and positioning for inventory, FPS, Entity render passes.
     */
    private ItemRenderInfo modelItemRenderInformation = null;

    /**
     * Contains world rendering information such as position and scale which determine how large the model appears in the game world.
     */
    private WorldRenderInfo modelWorldRenderInformation = null;

    public RenderingFactoryProduct(ModelArchive renderInformation)
    {
        super();

        // Master model list we will manipulate with packets from server.
        this.modelRenderingReference = new LinkedHashMap<String, TechneModel>();

        // Unique rendering ID for each instance of a renderer product.
        this.renderingID = RenderingRegistry.getNextAvailableRenderId();

        // Load default texture from this machine as a resource location for renderer to bind to when referenced.
        this.textureResource = new ResourceLocation( ModMetadata.ID,
                                                     renderInformation.getMachineTexture() );

        // Load clone of item render information.
        this.modelItemRenderInformation = renderInformation.getItemRenderInfoClone();

        // Load clone of world render information.
        this.modelWorldRenderInformation = renderInformation.getWorldRenderInfoClone();

        // Load clone of models and textures associated with this product.
        for (MachineModel productModel : renderInformation.getMachineModelsFilesClone())
        {
            this.modelRenderingReference.put( productModel.getModelName(),
                                              (TechneModel) AdvancedModelLoader.loadModel( productModel.getModelPath() ) );
        }
    }

    /**
     * Tells the TechneModel rendering system to render all the parts that makeup a given model.
     */
    public void renderMadModelParts()
    {
        // Loop through our keys from reference models (which are updated by packet system).
        for (TechneModel modelPart : this.modelRenderingReference.values())
        {
            if (modelPart != null)
            {
                if (modelPart.isVisible())
                {
                    modelPart.renderAll();
                }
            }
        }
    }

    public int getRenderingID()
    {
        return renderingID;
    }

    public ResourceLocation getTextureResource()
    {
        return textureResource;
    }

    /**
     * Updates rendering product with proper visibility status per instance. Returns false if no change was needed, true if change was made.
     */
    public boolean setRenderVisibilityByName(String modelName, boolean visible)
    {
        // Attempt to locate the piece based on it's name.
        if (modelRenderingReference.containsKey( modelName ))
        {
            // Grab the model piece from reference.
            TechneModel queriedModel = modelRenderingReference.get( modelName );

            if (queriedModel.isVisible() != visible)
            {
                queriedModel.setVisible( visible );

                // Update the reference.
                TechneModel replacedModel = modelRenderingReference.put( modelName,
                                                                         queriedModel );

                // Check that list was updated to match what was inputted.
                if (! queriedModel.equals( replacedModel ))
                {
                    throw new IllegalArgumentException( "Could not update model piece '" +
                                                        modelName +
                                                        "' visibility. Something is wrong with rendering reference mapping!" );
                }

                return true;
            }
        }

        return false;
    }

    public Map<String, TechneModel> getModelRenderingReference()
    {
        return modelRenderingReference;
    }

    public ItemRenderInfo getModelItemRenderInformation()
    {
        return modelItemRenderInformation;
    }

    public WorldRenderInfo getModelWorldRenderInformation()
    {
        return modelWorldRenderInformation;
    }

    public void setWorldRenderInformation(ModelPosition newWorldPosition, ModelScale newWorldScale)
    {
        this.modelWorldRenderInformation = new WorldRenderInfo( newWorldPosition,
                                                                newWorldScale );
    }
}

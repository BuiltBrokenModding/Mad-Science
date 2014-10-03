package madscience.factory.product;

import java.util.LinkedHashMap;
import java.util.Map;

import madscience.MadModMetadata;
import madscience.factory.mod.MadModLoader;
import madscience.factory.model.MadModel;
import madscience.factory.model.MadModelFile;
import madscience.factory.model.MadModelPosition;
import madscience.factory.model.MadModelScale;
import madscience.factory.model.MadTechneModel;
import madscience.factory.rendering.MadModelItemRender;
import madscience.factory.rendering.MadModelWorldRender;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MadRenderingFactoryProduct
{
    /** Unique ID for our model to render in the world. */
    private int renderingID = -1;
    
    /** Default texture that is rendered on the model if no other is specified. */
    private ResourceLocation textureResource = null;
    
    /** Primary reference to all loaded models. */
    private Map<String, MadTechneModel> modelRenderingReference = null;
    
    /** Contains item rendering information such as should render icons in 3D, scaling and positioning for inventory, FPS, Entity render passes. */
    private MadModelItemRender modelItemRenderInformation = null;
    
    /** Contains world rendering information such as position and scale which determine how large the model appears in the game world. */
    private MadModelWorldRender modelWorldRenderInformation = null;
    
    public MadRenderingFactoryProduct(MadModel renderInformation)
    {
        super();
        
        // Master model list we will manipulate with packets from server.
        this.modelRenderingReference = new LinkedHashMap<String, MadTechneModel>();
        
        // Unique rendering ID for each instance of a renderer product.
        this.renderingID = RenderingRegistry.getNextAvailableRenderId();
        
        // Load default texture from this machine as a resource location for renderer to bind to when referenced.
        this.textureResource = new ResourceLocation(MadModMetadata.ID, renderInformation.getMachineTexture());
        
        // Load clone of item render information.
        this.modelItemRenderInformation = renderInformation.getItemRenderInfoClone();
        
        // Load clone of world render information.
        this.modelWorldRenderInformation = renderInformation.getWorldRenderInfoClone();
        
        // Load clone of models and textures associated with this product.
        for (MadModelFile productModel : renderInformation.getMachineModelsFilesClone())
        {
            this.modelRenderingReference.put(productModel.getModelName(), (MadTechneModel) AdvancedModelLoader.loadModel(productModel.getModelPath()));            
        }
    }
    
    /** Tells the MadTechneModel rendering system to render all the parts that makeup a given model. */
    public void renderMadModelParts()
    {
        // Loop through our keys from reference models (which are updated by packet system).
        for (MadTechneModel modelPart : this.modelRenderingReference.values())
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
    
    /** Updates rendering product with proper visibility status per instance. Returns false if no change was needed, true if change was made. */
    public boolean setRenderVisibilityByName(String modelName, boolean visible)
    {
        // Attempt to locate the piece based on it's name.
        if (modelRenderingReference.containsKey(modelName))
        {
            // Grab the model piece from reference.
            MadTechneModel queriedModel = modelRenderingReference.get(modelName);
            
            if (queriedModel.isVisible() != visible)
            {
                queriedModel.setVisible(visible);
                
                // Update the reference.
                MadTechneModel replacedModel = modelRenderingReference.put(modelName, queriedModel);
                
                // Check that list was updated to match what was inputed.
                if (!queriedModel.equals(replacedModel))
                {
                    throw new IllegalArgumentException("Could not update model piece '" + modelName + "' visibility. Something is wrong with rendering reference mapping!");
                }
                
                return true;
            }
        }
        
        return false;
    }

    public Map<String, MadTechneModel> getModelRenderingReference()
    {
        return modelRenderingReference;
    }

    public MadModelItemRender getModelItemRenderInformation()
    {
        return modelItemRenderInformation;
    }

    public MadModelWorldRender getModelWorldRenderInformation()
    {
        return modelWorldRenderInformation;
    }

    public void setWorldRenderInformation(MadModelPosition newWorldPosition, MadModelScale newWorldScale)
    {
        this.modelWorldRenderInformation = new MadModelWorldRender(newWorldPosition, newWorldScale);
    }
}

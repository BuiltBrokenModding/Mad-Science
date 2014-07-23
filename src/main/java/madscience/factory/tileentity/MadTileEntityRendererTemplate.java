package madscience.factory.tileentity;

import java.util.Collection;
import java.util.Hashtable;

import madscience.factory.MadTileEntityFactory;
import madscience.factory.MadTileEntityFactoryProduct;
import madscience.factory.mod.MadMod;
import madscience.factory.model.MadModel;
import madscience.factory.model.MadModelFile;
import madscience.factory.model.MadTechneModel;
import madscience.factory.tileentity.prefab.MadTileEntityPrefab;
import madscience.util.MadUtils;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MadTileEntityRendererTemplate extends TileEntitySpecialRenderer implements ISimpleBlockRenderingHandler, IItemRenderer
{
    /** Reference to different rendering types such as FPS, TPS, etc. */
    private enum TransformationTypes
    {
        NONE, DROPPED, INVENTORY, THIRDPERSONEQUIPPED
    }

    /** Unique ID for our model to render in the world. */
    private int rendereingID = RenderingRegistry.getNextAvailableRenderId();

    /** Default texture that is rendered on the model if no other is specified. */
    private ResourceLocation techneModelTexture = null;
    
    /** Reference to models and texture that should be applied to them. */
    private MadModel renderingInfo;
    
    /** Reference to registered machine from factory. */
    private MadTileEntityFactoryProduct registeredMachine = null;
    
    /** Hashtable which links client only models to their model reference classes, allowing them to be manipulated by server. */
    private Hashtable<String, MadTechneModel> clientTechneModels = null;
    
    private Hashtable<String, MadModelFile> masterReferenceModels = null;
    
    /** Called on startup of game when renderer is associated with event system with Minecraft/Forge.
     *  This file  */
    public MadTileEntityRendererTemplate(MadTileEntityFactoryProduct registeredProduct)
    {
        super();
        
        // Grab a list of all the models and associated textures for this machine.
        MadModel modelArchive = registeredProduct.getModelArchive();
        if (modelArchive != null)
        {
            // There can only be one texture binded, and many models associated.
            MadModelFile[] modelFiles = modelArchive.getMachineModels();
            
            // Load the default texture for this machine model.
            techneModelTexture = new ResourceLocation(MadMod.ID, modelArchive.getMachineTexture());
            
            if (modelFiles != null)
            {
                // Load all of the associated models for this tile entity.
                this.loadModelsAssociatedWithMachine(modelFiles);
            }
        }
    }

    /** Consumes an array of model files which need to be loaded on client side model loader.
     *  This method should only be called from the client and not the server since no rendering
     *  capabilities exist on the server. */
    private void loadModelsAssociatedWithMachine(MadModelFile[] modelFiles)
    {
        // Abort if we already have models loaded!
        if (masterReferenceModels != null && clientTechneModels != null)
        {
            return;
        }
        
        // Create the hashtables for this machines models.
        clientTechneModels = new Hashtable<String, MadTechneModel>();
        masterReferenceModels = new Hashtable<String, MadModelFile>();
        
        // Populate the newly created array with our data.
        for(MadModelFile model : modelFiles)
        {
            if (model != null)
            {
                // Debuggin'
                MadMod.log().info("Loading model: " + model.getModelPath());
                
                // Load the base model for this machine.
                MadTechneModel tmpModelActual = (MadTechneModel) AdvancedModelLoader.loadModel(model.getModelPath());
                String tmpModelName = model.getModelName();
                
                if (tmpModelActual != null && tmpModelName != null)
                {
                    clientTechneModels.put(tmpModelName, tmpModelActual);
                    
                    // Link the name of the model to the entire reference object.
                    masterReferenceModels.put(tmpModelName, model);
                }
            }
        }
    }

    public MadTileEntityRendererTemplate()
    {
        super();
    }

    @Override
    public int getRenderId()
    {
        // Returns our unique rendering ID for this specific tile entity.
        return rendereingID;
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        switch (type)
        {
        case ENTITY:
        case EQUIPPED:
        case EQUIPPED_FIRST_PERSON:
        case INVENTORY:
            return true;
        default:
            return false;
        }
    }

    private void renderMadModelAt(MadTileEntityPrefab tileEntity, double x, double y, double z, float scale)
    {
        // Grab the individual tile entity in the world.
        MadTileEntityPrefab madTileEntity = null;
        if (tileEntity instanceof MadTileEntityPrefab)
        {
            madTileEntity = (MadTileEntityPrefab) tileEntity;
        }
        
        // Check for null on returned object, casting should not fail though!
        if (madTileEntity == null)
        {
            return;
        }

        // Changes the objects rotation to match whatever the player was facing.
        int rotation = 180;
        switch (madTileEntity.getBlockMetadata() % 4)
        {
        case 0:
            rotation = 0;
            break;
        case 3:
            rotation = 90;
            break;
        case 2:
            rotation = 180;
            break;
        case 1:
            rotation = 270;
            break;
        }

        // Begin OpenGL render push.
        GL11.glPushMatrix();

        // Left and right positives center the object and the middle one raises it up to connect with bottom of connecting block.
        GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);

        // Using this and the above select the tile entity will always face the player.
        switch (rotation)
        {
        case 0:
            GL11.glRotatef(-rotation, 0.0F, 1.0F, 0.0F);
            break;
        case 90:
            GL11.glRotatef(rotation, 0.0F, 1.0F, 0.0F);
            break;
        case 180:
            GL11.glRotatef(-rotation, 0.0F, 1.0F, 0.0F);
            break;
        case 270:
            GL11.glRotatef(rotation, 0.0F, 1.0F, 0.0F);
            break;
        }

        // Grab texture and model information from entity.
        if (madTileEntity.getEntityTexture() != null && !madTileEntity.getEntityTexture().isEmpty())
        {
            // Apply our custom texture from asset directory.
            bindTexture(new ResourceLocation(MadMod.ID, madTileEntity.getEntityTexture()));
        }
        else
        {
            // Default texture if custom animation is not there.
            bindTexture(this.techneModelTexture);
        }

        GL11.glPushMatrix();
        
        // Render model from entity information
        this.renderMadModelParts(madTileEntity.getClientModelsForWorldRender());
        
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }

    /** Tells the MadTechneModel rendering system to render all the parts that makeup a given model. */
    private void renderMadModelParts(MadModelFile[] madModelFiles)
    {
        // Loop through our keys from reference models (which are updated by packet system).
        for (MadModelFile modelPart : madModelFiles)
        {
            if (modelPart != null)
            {
                // Reference the key in the set to model hashtable.
                MadTechneModel loadedModel = clientTechneModels.get(modelPart.getModelName());
                if (loadedModel != null && modelPart != null)
                {
                    // Determine if this part of the model is visible or not.
                    if (modelPart.isModelVisible())
                    {
                        loadedModel.renderAll();
                    }
                }
            }
        }
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
    {
        return;
    }

    @Override
    /** Renders a image or techne model based on master reference list from factory product data. */
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {
        GL11.glPushMatrix();
        
        // Populate our factory object based on object name that is attempting to be rendered.
        String getMachineName = MadUtils.cleanTag(item.getUnlocalizedName());
        registeredMachine = MadTileEntityFactory.getMachineInfo(getMachineName);
        
        // If we find a machine that matches the one wanting to be rendered we will grab model information.
        if (registeredMachine != null)
        {
            renderingInfo = registeredMachine.getModelArchive();
            if (renderingInfo != null)
            {
                // There can only be one texture binded, and many models associated.
                MadModelFile[] modelFiles = renderingInfo.getMachineModels();

                // Create texture location based on the model info stored in the registered machine.
                if (this.techneModelTexture == null)
                {
                    this.techneModelTexture = new ResourceLocation(MadMod.ID, renderingInfo.getMachineTexture());
                }
                
                if (modelFiles != null)
                {
                    // Create the hashtables for this machines models.
                    this.loadModelsAssociatedWithMachine(modelFiles);
                }
            }
        }
        
        // Use default texture provided in factory product.
        Minecraft.getMinecraft().renderEngine.bindTexture(this.techneModelTexture);
        //bindTexture(this.techneModelTexture);

        // adjust rendering space to match what caller expects
        TransformationTypes transformationToBeUndone = TransformationTypes.NONE;
        switch (type)
        {
        case EQUIPPED:
        {
            float scale = 1.4F;
            GL11.glScalef(scale, scale, scale);
            GL11.glTranslatef(0.1F, 0.3F, 0.3F);
            // GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
            GL11.glEnable(GL11.GL_CULL_FACE);
            transformationToBeUndone = TransformationTypes.THIRDPERSONEQUIPPED;
            break;
        }
        case EQUIPPED_FIRST_PERSON:
        {
            float scale = 1.0F;
            GL11.glScalef(scale, scale, scale);
            GL11.glTranslatef(0.2F, 0.9F, 0.5F);
            // GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
            break;
        }
        case INVENTORY:
        {
            float scale = 1.0F;
            GL11.glScalef(scale, scale, scale);
            // GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(270.0F, 0.0F, 0.5F, 0.0F);
            transformationToBeUndone = TransformationTypes.INVENTORY;
            break;
        }
        case ENTITY:
        {
            float scale = 1.0F;
            GL11.glScalef(scale, scale, scale);
            // GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
            transformationToBeUndone = TransformationTypes.DROPPED;
            break;
        }
        default:
            break; // never here
        }

        // Renders the model from master reference stored in factory product data.
        this.renderMadModelParts(masterReferenceModels.values().toArray(new MadModelFile[]{}));
        GL11.glPopMatrix();

        switch (transformationToBeUndone)
        {
        case NONE:
        {
            break;
        }
        case DROPPED:
        {
            GL11.glTranslatef(0.0F, -0.5F, 0.0F);
            float scale = 1.0F;
            GL11.glScalef(scale, scale, scale);
            break;
        }
        case INVENTORY:
        {
            GL11.glTranslatef(0.5F, 0.5F, 0.5F);
            break;
        }
        case THIRDPERSONEQUIPPED:
        {
            GL11.glDisable(GL11.GL_CULL_FACE);
        }
        default:
            break;
        }
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float scale)
    {
        // Check if the tile entity wanting to be rendered is one of ours.
        if (tileEntity instanceof MadTileEntityPrefab)
        {
            this.renderMadModelAt((MadTileEntityPrefab) tileEntity, x, y, z, scale);
        }
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
    {
        // Overridden by our tile entity.
        return false;
    }

    @Override
    public boolean shouldRender3DInInventory()
    {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
    {
        switch (type)
        {
        case ENTITY:
        {
            return (helper == ItemRendererHelper.ENTITY_BOBBING || helper == ItemRendererHelper.ENTITY_ROTATION || helper == ItemRendererHelper.BLOCK_3D);
        }
        case EQUIPPED:
        {
            return (helper == ItemRendererHelper.BLOCK_3D || helper == ItemRendererHelper.EQUIPPED_BLOCK);
        }
        case EQUIPPED_FIRST_PERSON:
        {
            return (helper == ItemRendererHelper.EQUIPPED_BLOCK);
        }
        case INVENTORY:
        {
            return (helper == ItemRendererHelper.INVENTORY_BLOCK);
        }
        default:
        {
            return false;
        }
        }
    }
}

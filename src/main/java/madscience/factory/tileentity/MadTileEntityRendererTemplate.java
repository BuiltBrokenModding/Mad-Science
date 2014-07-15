package madscience.factory.tileentity;

import madscience.MadScience;
import madscience.factory.MadTileEntityFactoryProduct;
import madscience.factory.model.MadModel;
import madscience.factory.model.MadModelFile;
import madscience.factory.model.MadTechneModel;
import madscience.factory.tileentity.prefab.MadTileEntityPrefab;
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
    private enum TransformationTypes
    {
        NONE, DROPPED, INVENTORY, THIRDPERSONEQUIPPED
    }

    // Unique ID for our model to render in the world.
    private int rendereingID = RenderingRegistry.getNextAvailableRenderId();

    /** Binary model file created with Techne */
    private static MadTechneModel[] techneModels = null;

    /** Default texture that is rendered on the model if no other is specified. */
    private static ResourceLocation techneModelTexture = null;
    
    public MadTileEntityRendererTemplate(MadTileEntityFactoryProduct registeredProduct)
    {
        super();
        
        // Grab a list of all the models and associated textures for this machine.
        MadModel modelArchive = registeredProduct.getModelArchive();
        
        // Since there can only be one texture binded, and many models we follow this model.
        MadModelFile[] modeFiles = modelArchive.getMachineModels();
        
        // Set the length of model and resource arrays to model archive length.
        techneModels = new MadTechneModel[modeFiles.length];
        
        // Load the default texture for this machine model.
        techneModelTexture = new ResourceLocation(MadScience.ID, modelArchive.getMachineTexture());
        
        // Populate the newly created array with our data.
        int i = 0;
        for(MadModelFile model : modeFiles)
        {
            // Load the base model for this machine.
            techneModels[i] = (MadTechneModel) AdvancedModelLoader.loadModel(model.getModelPath());
            i++;
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

    private void renderAModelAt(MadTileEntityPrefab tileEntity, double x, double y, double z, float f)
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
        if (madTileEntity != null)
        {
            // Apply our custom texture from asset directory.
            if (madTileEntity.getEntityTexture() != null && !madTileEntity.getEntityTexture().isEmpty())
            {
                bindTexture(new ResourceLocation(MadScience.ID, madTileEntity.getEntityTexture()));
            }
            else
            {
                // Default texture if custom animation is not there.
                bindTexture(this.techneModelTexture);
            }
        }

        GL11.glPushMatrix();
        renderTechneModels();
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }

    /** Tells the MadTechneModel rendering system to render all the parts that makeup it's given model. */
    private void renderTechneModels()
    {
        for (MadTechneModel model : techneModels)
        {
            model.renderAll();
        }
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
    {
        return;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {
        GL11.glPushMatrix();
        
        // Use default texture provided in factory product.
        Minecraft.getMinecraft().renderEngine.bindTexture(this.techneModelTexture);

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

        // Renders the model in the gameworld at the correct scale.
        renderTechneModels();
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
            this.renderAModelAt((MadTileEntityPrefab) tileEntity, x, y, z, scale);
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

package madscience.factory.tileentity.templates;

import madscience.factory.MadRenderingFactory;
import madscience.factory.mod.MadMod;
import madscience.factory.rendering.MadRenderTransformationTypes;
import madscience.factory.rendering.MadRenderingFactoryProduct;
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

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MadTileEntityRendererTemplate extends TileEntitySpecialRenderer implements ISimpleBlockRenderingHandler, IItemRenderer
{
    private MadRenderingFactoryProduct currentRenderProduct = null;
    private int currentRenderID = -1;

    /** Called on startup of game when renderer is associated with event system with Minecraft/Forge. */
    public MadTileEntityRendererTemplate()
    {
        super();
    }
    
    @Override
    public int getRenderId()
    {
        // Returns our unique rendering ID for this specific tile entity.
        if (currentRenderProduct == null)
        {
            // Assign temporary render ID until we get this sorted out.
            if (currentRenderID == -1)
            {
                currentRenderID = RenderingRegistry.getNextAvailableRenderId();
            }
        }
        
        // If render ID is set to anything other than default then return that.
        if (currentRenderID != -1)
        {
            return currentRenderID;
        }
        
        // Default response is to return negative one to stop rendering.
        return currentRenderID;
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
        
        // Check if model instance needs to updated before render.
        MadRenderingFactory.instance().updateModelInstance(
                madTileEntity.getMachineInternalName(),
                false,
                madTileEntity.getEntityModelData(),
                String.valueOf(madTileEntity.xCoord),
                String.valueOf(madTileEntity.yCoord),
                String.valueOf(madTileEntity.zCoord));
        
        // Grab model instance from rendering factory.
        this.currentRenderProduct = MadRenderingFactory.instance().getModelInstance(
                MadUtils.cleanTag(madTileEntity.getMachineInternalName()),
                false,
                String.valueOf(madTileEntity.xCoord),
                String.valueOf(madTileEntity.yCoord),
                String.valueOf(madTileEntity.zCoord));
        
        if (currentRenderProduct == null)
        {
            return;
        }
        
        // Set rendering ID to whatever instance demands.
        this.currentRenderID = currentRenderProduct.getRenderingID();

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
        if (madTileEntity.getEntityTexture() != null)
        {
            bindTexture(new ResourceLocation(MadMod.ID, madTileEntity.getEntityTexture()));
        }
        else
        {
            // If entity is not providing a texture resource then use the default!
            bindTexture(this.currentRenderProduct.getTextureResource());
        }

        GL11.glPushMatrix();

        // Render model instance.
        this.currentRenderProduct.renderMadModelParts();
        
        GL11.glPopMatrix();
        GL11.glPopMatrix();
        
        //Cleanup.
        this.currentRenderProduct = null;
        this.currentRenderID = -1;
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
        
        // TODO: Update item render instance as requested by server.
        
        // Grab rendering instance for item from rendering factory.
        this.currentRenderProduct = MadRenderingFactory.instance().getModelInstance(
                MadUtils.cleanTag(item.getUnlocalizedName()),
                true,
                String.valueOf(item.getItemDamage()),
                String.valueOf(item.getMaxDamage()));
        
        if (currentRenderProduct == null)
        {
            return;
        }
        
        // Set rendering ID to whatever instance demands.
        this.currentRenderID = currentRenderProduct.getRenderingID();
        
        // Use default texture provided in factory product.
        Minecraft.getMinecraft().renderEngine.bindTexture(this.currentRenderProduct.getTextureResource());

        // adjust rendering space to match what caller expects
        MadRenderTransformationTypes transformationToBeUndone = MadRenderTransformationTypes.NONE;
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
            transformationToBeUndone = MadRenderTransformationTypes.THIRDPERSONEQUIPPED;
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
            transformationToBeUndone = MadRenderTransformationTypes.INVENTORY;
            break;
        }
        case ENTITY:
        {
            float scale = 1.0F;
            GL11.glScalef(scale, scale, scale);
            // GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
            transformationToBeUndone = MadRenderTransformationTypes.DROPPED;
            break;
        }
        default:
            break; // never here
        }

        // Renders the model from master reference stored in factory product data.
        this.currentRenderProduct.renderMadModelParts();
        
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
        
        //Cleanup.
        this.currentRenderProduct = null;
        this.currentRenderID = -1;
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

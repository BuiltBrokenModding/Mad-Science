package madscience.tileentities.magloader;

import madscience.MadFurnaces;
import madscience.MadScience;
import madscience.util.MadTechneModel;
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
public class MagLoaderRender extends TileEntitySpecialRenderer implements ISimpleBlockRenderingHandler, IItemRenderer
{
    private enum TransformationTypes
    {
        DROPPED, INVENTORY, NONE, THIRDPERSONEQUIPPED
    }

    // Tile entity that keep track of data and interacts with the user.
    private MagLoaderEntity ENTITY;
    
    // Base magazine loader model with no moving pieces.
    private MadTechneModel MAGLOADER_BASE = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadFurnaces.MAGLOADER_INTERNALNAME + "/" + MadFurnaces.MAGLOADER_INTERNALNAME + ".mad");

    // Internal bullet models that look like they are funneled down into magazines.
    private MadTechneModel MAGLOADER_BULLET0 = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadFurnaces.MAGLOADER_INTERNALNAME + "/" + MadFurnaces.MAGLOADER_INTERNALNAME + "_Bullet0.mad");
    private MadTechneModel MAGLOADER_BULLET1 = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadFurnaces.MAGLOADER_INTERNALNAME + "/" + MadFurnaces.MAGLOADER_INTERNALNAME + "_Bullet1.mad");
    private MadTechneModel MAGLOADER_BULLET2 = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadFurnaces.MAGLOADER_INTERNALNAME + "/" + MadFurnaces.MAGLOADER_INTERNALNAME + "_Bullet2.mad");
    private MadTechneModel MAGLOADER_BULLET3 = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadFurnaces.MAGLOADER_INTERNALNAME + "/" + MadFurnaces.MAGLOADER_INTERNALNAME + "_Bullet3.mad");
    private MadTechneModel MAGLOADER_BULLET4 = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadFurnaces.MAGLOADER_INTERNALNAME + "/" + MadFurnaces.MAGLOADER_INTERNALNAME + "_Bullet4.mad");

    // Magazine to be displayed.
    private MadTechneModel MAGLOADER_MAGAZINE = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadFurnaces.MAGLOADER_INTERNALNAME + "/" + MadFurnaces.MAGLOADER_INTERNALNAME + "_Magazine.mad");
    private MadTechneModel MAGLOADER_MAGAZINEBASE = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadFurnaces.MAGLOADER_INTERNALNAME + "/" + MadFurnaces.MAGLOADER_INTERNALNAME + "_MagazineBase.mad");

    // Pushing mechanism that makes it look like bullets are being pushed down into magazine.
    private MadTechneModel MAGLOADER_PUSH0 = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadFurnaces.MAGLOADER_INTERNALNAME + "/" + MadFurnaces.MAGLOADER_INTERNALNAME + "_push0.mad");
    private MadTechneModel MAGLOADER_PUSH1 = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadFurnaces.MAGLOADER_INTERNALNAME + "/" + MadFurnaces.MAGLOADER_INTERNALNAME + "_push1.mad");
    private MadTechneModel MAGLOADER_PUSH2 = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadFurnaces.MAGLOADER_INTERNALNAME + "/" + MadFurnaces.MAGLOADER_INTERNALNAME + "_push2.mad");
    private MadTechneModel MAGLOADER_PUSH3 = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadFurnaces.MAGLOADER_INTERNALNAME + "/" + MadFurnaces.MAGLOADER_INTERNALNAME + "_push3.mad");
    private MadTechneModel MAGLOADER_PUSH4 = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadFurnaces.MAGLOADER_INTERNALNAME + "/" + MadFurnaces.MAGLOADER_INTERNALNAME + "_push4.mad");
    private MadTechneModel MAGLOADER_PUSH5 = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadFurnaces.MAGLOADER_INTERNALNAME + "/" + MadFurnaces.MAGLOADER_INTERNALNAME + "_push5.mad");
    
    // Next available render ID for model instancing.
    public int RENDERID = RenderingRegistry.getNextAvailableRenderId();

    // Texture that is used when bullets have been loaded into the machine.
    private ResourceLocation TEXTURE_HASBULLETS = new ResourceLocation(MadScience.ID, "models/" + MadFurnaces.MAGLOADER_INTERNALNAME + "/full.png");

    // Empty texture that is used when no bullets are inside of the machine.
    private ResourceLocation TEXTURE_NOBULLETS = new ResourceLocation(MadScience.ID, "models/" + MadFurnaces.MAGLOADER_INTERNALNAME + "/empty.png");

    @Override
    public int getRenderId()
    {
        return RENDERID;
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        switch (type)
        {
        case ENTITY:
        case EQUIPPED:
        case EQUIPPED_FIRST_PERSON:
            return true;
        case INVENTORY:
        default:
            return false;
        }
    }

    public void renderAModelAt(MagLoaderEntity tileEntity, double x, double y, double z, float f)
    {
        // Grab the individual tile entity in the world.
        ENTITY = tileEntity;
        if (ENTITY == null)
        {
            return;
        }

        // Changes the objects rotation to match whatever the player was facing.
        int rotation = 180;
        switch (ENTITY.getBlockMetadata() % 4)
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

        // Left and right positives center the object and the middle one raises
        // it up to connect with bottom of connecting block.
        GL11.glTranslatef((float) x + 0.5F, (float) y + 0.25F, (float) z + 0.5F);

        // Scale the magazine loader down to fit within normal size limitations.
        GL11.glScalef(0.5F, 0.5F, 0.5F);

        // Using this and the above select the tile entity will always face.
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

        // Check if there are any bullets loaded into the magazine loader.
        if (ENTITY != null && ENTITY.clientBulletCount > 0)
        {
            // MadScience.logger.info("Bullet Count: " + ENTITY.clientBulletCount);
            bindTexture(TEXTURE_HASBULLETS);
        }
        else
        {
            // Texture that is used when no bullets are loaded into the machine (default).
            bindTexture(TEXTURE_NOBULLETS);
        }

        GL11.glPushMatrix();

        // Base magazine loader object with no moving parts on it.
        MAGLOADER_BASE.renderAll();

        // Only run the next bits if we have an entity that we can read state information from.
        if (ENTITY != null)
        {
            // Get the cooking time scaled to 100%.
            int cookingTimeScaled = ENTITY.getItemCookTimeScaled(100);

            if (cookingTimeScaled <= 25 && cookingTimeScaled > 0)
            {
                // push0 and bullet0-4 visible = 25%
                MAGLOADER_PUSH0.renderAll();
                MAGLOADER_BULLET0.renderAll();
                MAGLOADER_BULLET1.renderAll();
                MAGLOADER_BULLET2.renderAll();
                MAGLOADER_BULLET3.renderAll();
                MAGLOADER_BULLET4.renderAll();
            }
            else if (cookingTimeScaled <= 50 && cookingTimeScaled > 25)
            {
                // push1 and bullet1-4 visible = 50%
                MAGLOADER_PUSH1.renderAll();
                MAGLOADER_BULLET1.renderAll();
                MAGLOADER_BULLET2.renderAll();
                MAGLOADER_BULLET3.renderAll();
                MAGLOADER_BULLET4.renderAll();
            }
            else if (cookingTimeScaled <= 75 && cookingTimeScaled > 50)
            {
                // push2 and bullet2-4 visible = 75%
                MAGLOADER_PUSH2.renderAll();
                MAGLOADER_BULLET2.renderAll();
                MAGLOADER_BULLET3.renderAll();
                MAGLOADER_BULLET4.renderAll();
            }
            else if (cookingTimeScaled <= 85 && cookingTimeScaled > 75)
            {
                // push3 and bullet3-4 visible = 85%
                MAGLOADER_PUSH3.renderAll();
                MAGLOADER_BULLET3.renderAll();
                MAGLOADER_BULLET4.renderAll();
            }
            else if (cookingTimeScaled <= 95 && cookingTimeScaled > 85)
            {
                // push4 and bullet4-4 visible = 90%
                MAGLOADER_PUSH4.renderAll();
                MAGLOADER_BULLET4.renderAll();
            }
            else if (cookingTimeScaled >= 100 && cookingTimeScaled > 95)
            {
                // push5 visible = Done!
                MAGLOADER_PUSH5.renderAll();
            }
            else
            {
                if (cookingTimeScaled > 0)
                {
                    // Last but not least if we have no idea just render the full push-rod.
                    MAGLOADER_PUSH5.renderAll();
                }
            }

            // Visible when an empty magazine(s) is in the machine.
            //MadScience.logger.info("Magazine Loader Output Magazine Count: " + String.valueOf(ENTITY.clientOutputMagazineCount));
            if (ENTITY.clientMagazineCount > 0 || ENTITY.clientOutputMagazineCount > 0)
            {
                // Magazine that can be inserted into the machine for loading.
                MAGLOADER_MAGAZINE.renderAll();
                MAGLOADER_MAGAZINEBASE.renderAll();
            }
        }

        GL11.glPopMatrix();
        GL11.glPopMatrix();
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

        // Use the same texture we do on the block normally (no bullets).
        Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE_NOBULLETS);

        // adjust rendering space to match what caller expects
        TransformationTypes transformationToBeUndone = TransformationTypes.NONE;
        switch (type)
        {
        case EQUIPPED:
        {
            float scale = 1.0F;
            GL11.glScalef(scale, scale, scale);
            GL11.glTranslatef(0.1F, 0.3F, 0.3F);
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
            GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
            break;
        }
        case INVENTORY:
        {
            float scale = 1.0F;
            GL11.glScalef(scale, scale, scale);
            GL11.glRotatef(270.0F, 0.0F, 0.5F, 0.0F);
            transformationToBeUndone = TransformationTypes.INVENTORY;
            break;
        }
        case ENTITY:
        {
            float scale = 1.0F;
            GL11.glScalef(scale, scale, scale);
            GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
            transformationToBeUndone = TransformationTypes.DROPPED;
            break;
        }
        default:
            break;
        }

        // Renders the magazine loader with base piece and nothing else.
        MAGLOADER_BASE.renderAll();
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
        this.renderAModelAt((MagLoaderEntity) tileEntity, x, y, z, scale);
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

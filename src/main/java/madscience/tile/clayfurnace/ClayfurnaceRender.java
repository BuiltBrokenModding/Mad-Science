package madscience.tile.clayfurnace;

import madscience.MadFurnaces;
import madscience.factory.mod.MadMod;
import madscience.factory.model.MadTechneModel;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
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
public class ClayfurnaceRender extends TileEntitySpecialRenderer implements ISimpleBlockRenderingHandler, IItemRenderer
{
    private enum TransformationTypes
    {
        NONE, DROPPED, INVENTORY, THIRDPERSONEQUIPPED
    }

    private int modelRenderID = RenderingRegistry.getNextAvailableRenderId();
    private MadTechneModel MODEL = (MadTechneModel) AdvancedModelLoader.loadModel(MadMod.MODEL_PATH + MadFurnaces.CLAYFURNACE_INTERNALNAME + "/" + MadFurnaces.CLAYFURNACE_INTERNALNAME + ".mad");
    private ClayfurnaceEntity ENTITY;
    private ResourceLocation TEXTURE = new ResourceLocation(MadMod.ID, "models/" + MadFurnaces.CLAYFURNACE_INTERNALNAME + "/idle.png");

    @Override
    public int getRenderId()
    {
        // Returns our unique rendering ID for this specific tile entity.
        return modelRenderID;
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

    private void renderAModelAt(ClayfurnaceEntity tileEntity, double x, double y, double z, float f)
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

        // If we are a clay furnace we have to shrink our scale, otherwise render normal size.
        if (!ENTITY.hasStoppedSmoldering)
        {
            GL11.glTranslatef((float) x + 0.5F, (float) y + 0.34F, (float) z + 0.5F);
            GL11.glScalef(0.6F, 0.68F, 0.6F);
        }
        else
        {
            GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
            GL11.glScalef(1.0F, 1.0F, 1.0F);
        }

        // Using this and the above select the tile entity will always face the
        // player.
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

        // Apply our custom texture from asset directory.
        if (ENTITY != null && ENTITY.TEXTURE != null && !ENTITY.TEXTURE.isEmpty())
        {
            bindTexture(new ResourceLocation(MadMod.ID, ENTITY.TEXTURE));
        }
        else
        {
            bindTexture(TEXTURE);
        }

        GL11.glPushMatrix();
        MODEL.renderAll();
        GL11.glPopMatrix();

        // Determine if we are a clay furnace still or red hot or post cooldown mode.
        if (!ENTITY.hasStoppedSmoldering)
        {
            // IDLE OR WORKING
            MODEL.parts.get("MoltenBlockShell").showModel = false;
            MODEL.parts.get("MoltenBlock").showModel = false;
            MODEL.parts.get("Base1").showModel = true;
            MODEL.parts.get("Base2").showModel = true;
            MODEL.parts.get("Base3").showModel = true;
            MODEL.parts.get("Base4").showModel = true;
            MODEL.parts.get("Top1").showModel = true;
            MODEL.parts.get("Top2").showModel = true;
            MODEL.parts.get("Top3").showModel = true;
            MODEL.parts.get("Top4").showModel = true;
            MODEL.parts.get("Post1").showModel = true;
            MODEL.parts.get("Post2").showModel = true;
            MODEL.parts.get("Post3").showModel = true;
            MODEL.parts.get("Post4").showModel = true;
        }
        else if (ENTITY.hasStoppedSmoldering && !ENTITY.hasCooledDown)
        {
            // RED HOT BLOCK
            MODEL.parts.get("MoltenBlockShell").showModel = false;
            MODEL.parts.get("MoltenBlock").showModel = true;
            MODEL.parts.get("Base1").showModel = false;
            MODEL.parts.get("Base2").showModel = false;
            MODEL.parts.get("Base3").showModel = false;
            MODEL.parts.get("Base4").showModel = false;
            MODEL.parts.get("Top1").showModel = false;
            MODEL.parts.get("Top2").showModel = false;
            MODEL.parts.get("Top3").showModel = false;
            MODEL.parts.get("Top4").showModel = false;
            MODEL.parts.get("Post1").showModel = false;
            MODEL.parts.get("Post2").showModel = false;
            MODEL.parts.get("Post3").showModel = false;
            MODEL.parts.get("Post4").showModel = false;
        }
        else if (ENTITY.hasStoppedSmoldering && ENTITY.hasCooledDown)
        {
            // COOLDOWN MODE AFTER RED HOT STATUS ENDS
            MODEL.parts.get("MoltenBlockShell").showModel = true;
            MODEL.parts.get("MoltenBlock").showModel = false;
            MODEL.parts.get("Base1").showModel = false;
            MODEL.parts.get("Base2").showModel = false;
            MODEL.parts.get("Base3").showModel = false;
            MODEL.parts.get("Base4").showModel = false;
            MODEL.parts.get("Top1").showModel = false;
            MODEL.parts.get("Top2").showModel = false;
            MODEL.parts.get("Top3").showModel = false;
            MODEL.parts.get("Top4").showModel = false;
            MODEL.parts.get("Post1").showModel = false;
            MODEL.parts.get("Post2").showModel = false;
            MODEL.parts.get("Post3").showModel = false;
            MODEL.parts.get("Post4").showModel = false;
        }

        this.MODEL.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

        MODEL.renderAll();
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

        // Use the same texture we do on the block normally.
        Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE);

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
            float scale = 0.65F;
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
        MODEL.renderAll();
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
        this.renderAModelAt((ClayfurnaceEntity) tileEntity, x, y, z, scale);
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

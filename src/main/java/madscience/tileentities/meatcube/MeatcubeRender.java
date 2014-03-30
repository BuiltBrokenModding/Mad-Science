package madscience.tileentities.meatcube;

import madscience.MadFurnaces;
import madscience.MadScience;
import madscience.util.MadTechneModel;
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
public class MeatcubeRender extends TileEntitySpecialRenderer implements ISimpleBlockRenderingHandler, IItemRenderer
{
    private enum TransformationTypes
    {
        NONE, DROPPED, INVENTORY, THIRDPERSONEQUIPPED
    }

    // Unique ID for our model to render in the world.
    public int RENDERID = RenderingRegistry.getNextAvailableRenderId();
    
    // Difference pieces of our model that all together makeup a complete model.
    private MadTechneModel MEATCUBE_BASE = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadFurnaces.MEATCUBE_INTERNALNAME + "/" + MadFurnaces.MEATCUBE_INTERNALNAME + ".mad");
    private MadTechneModel MEATCUBE_PIECE1 = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadFurnaces.MEATCUBE_INTERNALNAME + "/" + MadFurnaces.MEATCUBE_INTERNALNAME + "1.mad");
    private MadTechneModel MEATCUBE_PIECE2 = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadFurnaces.MEATCUBE_INTERNALNAME + "/" + MadFurnaces.MEATCUBE_INTERNALNAME + "2.mad");
    private MadTechneModel MEATCUBE_PIECE3 = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadFurnaces.MEATCUBE_INTERNALNAME + "/" + MadFurnaces.MEATCUBE_INTERNALNAME + "3.mad");
    private MadTechneModel MEATCUBE_PIECE4 = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadFurnaces.MEATCUBE_INTERNALNAME + "/" + MadFurnaces.MEATCUBE_INTERNALNAME + "4.mad");
    private MadTechneModel MEATCUBE_PIECE5 = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadFurnaces.MEATCUBE_INTERNALNAME + "/" + MadFurnaces.MEATCUBE_INTERNALNAME + "5.mad");
    private MadTechneModel MEATCUBE_PIECE6 = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadFurnaces.MEATCUBE_INTERNALNAME + "/" + MadFurnaces.MEATCUBE_INTERNALNAME + "6.mad");
    private MadTechneModel MEATCUBE_PIECE7 = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadFurnaces.MEATCUBE_INTERNALNAME + "/" + MadFurnaces.MEATCUBE_INTERNALNAME + "7.mad");
    private MadTechneModel MEATCUBE_PIECE8 = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadFurnaces.MEATCUBE_INTERNALNAME + "/" + MadFurnaces.MEATCUBE_INTERNALNAME + "8.mad");
    private MadTechneModel MEATCUBE_PIECE9 = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadFurnaces.MEATCUBE_INTERNALNAME + "/" + MadFurnaces.MEATCUBE_INTERNALNAME + "9.mad");
    private MadTechneModel MEATCUBE_PIECE10 = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadFurnaces.MEATCUBE_INTERNALNAME + "/" + MadFurnaces.MEATCUBE_INTERNALNAME + "10.mad");
    private MadTechneModel MEATCUBE_PIECE11 = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadFurnaces.MEATCUBE_INTERNALNAME + "/" + MadFurnaces.MEATCUBE_INTERNALNAME + "11.mad");
    private MadTechneModel MEATCUBE_PIECE12 = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadFurnaces.MEATCUBE_INTERNALNAME + "/" + MadFurnaces.MEATCUBE_INTERNALNAME + "12.mad");
    private MadTechneModel MEATCUBE_PIECE13 = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadFurnaces.MEATCUBE_INTERNALNAME + "/" + MadFurnaces.MEATCUBE_INTERNALNAME + "13.mad");
    private MadTechneModel MEATCUBE_PIECE14 = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadFurnaces.MEATCUBE_INTERNALNAME + "/" + MadFurnaces.MEATCUBE_INTERNALNAME + "14.mad");
    
    // Tile Entity in the world.
    private MeatcubeEntity ENTITY;
    
    // Default texture that we should show.
    private ResourceLocation TEXTURE = new ResourceLocation(MadScience.ID, "models/" + MadFurnaces.MEATCUBE_INTERNALNAME + "/meatcube_0.png");

    @Override
    public int getRenderId()
    {
        // Returns our unique rendering ID for this specific tile entity.
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
        case INVENTORY:
            return true;
        default:
            return false;
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
            //GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
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
            //GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
            break;
        }
        case INVENTORY:
        {
            float scale = 1.0F;
            GL11.glScalef(scale, scale, scale);
            //GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(270.0F, 0.0F, 0.5F, 0.0F);
            transformationToBeUndone = TransformationTypes.INVENTORY;
            break;
        }
        case ENTITY:
        {
            float scale = 1.0F;
            GL11.glScalef(scale, scale, scale);
            //GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
            transformationToBeUndone = TransformationTypes.DROPPED;
            break;
        }
        default:
            break; // never here
        }

        showAllMeatCubePieces();
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
    
    public void renderAModelAt(MeatcubeEntity tileEntity, double x, double y, double z, float f)
    {
        // Grab the individual tile entity in the world.
        ENTITY = (MeatcubeEntity) tileEntity;
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
        GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);

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
        if (ENTITY != null && ENTITY.meatcubeTexturePath != null && !ENTITY.meatcubeTexturePath.isEmpty())
        {
            bindTexture(new ResourceLocation(MadScience.ID, ENTITY.meatcubeTexturePath));
        }
        else
        {
            bindTexture(TEXTURE);
        }

        GL11.glPushMatrix();
        
        // Base meatcube with piece zero.
        MEATCUBE_BASE.renderAll();
        
        // Display different chunks of the model based on internal health value.
        if (ENTITY.currentMeatCubeDamageValue >= 1)
        {
            MEATCUBE_PIECE1.renderAll();
            //MEATCUBE_PIECE1.parts.get("Meat1").showModel = true;
        }

        if (ENTITY.currentMeatCubeDamageValue >= 2)
        {
            MEATCUBE_PIECE2.renderAll();
            //MEATCUBE_PIECE2.parts.get("Meat2").showModel = true;
        }

        if (ENTITY.currentMeatCubeDamageValue >= 3)
        {
            MEATCUBE_PIECE3.renderAll();
            //MEATCUBE_PIECE3.parts.get("Meat3").showModel = true;
        }

        if (ENTITY.currentMeatCubeDamageValue >= 4)
        {
            MEATCUBE_PIECE4.renderAll();
            //MEATCUBE_PIECE4.parts.get("Meat4").showModel = true;
        }

        if (ENTITY.currentMeatCubeDamageValue >= 5)
        {
            MEATCUBE_PIECE5.renderAll();
            //MEATCUBE_PIECE5.parts.get("Meat5").showModel = true;
        }

        if (ENTITY.currentMeatCubeDamageValue >= 6)
        {
            MEATCUBE_PIECE6.renderAll();
            //MEATCUBE_PIECE6.parts.get("Meat6").showModel = true;
        }

        if (ENTITY.currentMeatCubeDamageValue >= 7)
        {
            MEATCUBE_PIECE7.renderAll();
            //MEATCUBE_PIECE7.parts.get("Meat7").showModel = true;
        }

        if (ENTITY.currentMeatCubeDamageValue >= 8)
        {
            MEATCUBE_PIECE8.renderAll();
            //MEATCUBE_PIECE8.parts.get("Meat8").showModel = true;
        }

        if (ENTITY.currentMeatCubeDamageValue >= 9)
        {
            MEATCUBE_PIECE9.renderAll();
            //MEATCUBE_PIECE9.parts.get("Meat9").showModel = true;
        }

        if (ENTITY.currentMeatCubeDamageValue >= 10)
        {
            MEATCUBE_PIECE10.renderAll();
            //MEATCUBE_PIECE10.parts.get("Meat10").showModel = true;
        }

        if (ENTITY.currentMeatCubeDamageValue >= 11)
        {
            MEATCUBE_PIECE11.renderAll();
            //MEATCUBE_PIECE11.parts.get("Meat11").showModel = true;
        }

        if (ENTITY.currentMeatCubeDamageValue >= 12)
        {
            MEATCUBE_PIECE12.renderAll();
            //MEATCUBE_PIECE12.parts.get("Meat12").showModel = true;
        }

        if (ENTITY.currentMeatCubeDamageValue >= 13)
        {
            MEATCUBE_PIECE13.renderAll();
            //MEATCUBE_PIECE13.parts.get("Meat13").showModel = true;
        }

        if (ENTITY.currentMeatCubeDamageValue >= 14)
        {
            MEATCUBE_PIECE14.renderAll();
            //MEATCUBE_PIECE14.parts.get("Meat14").showModel = true;
        }
        
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }

    public void showAllMeatCubePieces()
    {
        // Base meatcube with piece zero.
        MEATCUBE_BASE.renderAll();
        
        // Meat gibs.
        MEATCUBE_PIECE1.renderAll();
        MEATCUBE_PIECE2.renderAll();
        MEATCUBE_PIECE3.renderAll();
        MEATCUBE_PIECE4.renderAll();
        MEATCUBE_PIECE5.renderAll();
        MEATCUBE_PIECE6.renderAll();
        MEATCUBE_PIECE7.renderAll();
        MEATCUBE_PIECE8.renderAll();
        MEATCUBE_PIECE9.renderAll();
        MEATCUBE_PIECE10.renderAll();
        MEATCUBE_PIECE11.renderAll();
        MEATCUBE_PIECE12.renderAll();
        MEATCUBE_PIECE13.renderAll();
        MEATCUBE_PIECE14.renderAll();
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float scale)
    {
        this.renderAModelAt((MeatcubeEntity) tileEntity, x, y, z, scale);
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

package madscience.tileentities.cncmachine;

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
public class CnCMachineRender extends TileEntitySpecialRenderer implements ISimpleBlockRenderingHandler, IItemRenderer
{
    private enum TransformationTypes
    {
        DROPPED, INVENTORY, NONE, THIRDPERSONEQUIPPED
    }

    private int animFrame;

    private CnCMachineEntity ENTITY;
    
    // Base model files, always visible.
    private MadTechneModel MODEL_BASE = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadFurnaces.CNCMACHINE_INTERNALNAME + "/" + MadFurnaces.CNCMACHINE_INTERNALNAME + "_Base.mad");
    private MadTechneModel MODEL_SCREEN = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadFurnaces.CNCMACHINE_INTERNALNAME + "/" + MadFurnaces.CNCMACHINE_INTERNALNAME + "_Monitor.mad");

    // Compressed Block 0 - 3.
    private MadTechneModel MODEL_COMPESSEDBLOCK0 = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadFurnaces.CNCMACHINE_INTERNALNAME + "/" + MadFurnaces.CNCMACHINE_INTERNALNAME + "_CompressedBlock0.mad");
    private MadTechneModel MODEL_COMPESSEDBLOCK1 = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadFurnaces.CNCMACHINE_INTERNALNAME + "/" + MadFurnaces.CNCMACHINE_INTERNALNAME + "_CompressedBlock1.mad");
    private MadTechneModel MODEL_COMPESSEDBLOCK2 = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadFurnaces.CNCMACHINE_INTERNALNAME + "/" + MadFurnaces.CNCMACHINE_INTERNALNAME + "_CompressedBlock2.mad");
    private MadTechneModel MODEL_COMPESSEDBLOCK3 = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadFurnaces.CNCMACHINE_INTERNALNAME + "/" + MadFurnaces.CNCMACHINE_INTERNALNAME + "_CompressedBlock3.mad");

    // Cutblock 0 - 8.
    private MadTechneModel MODEL_CUTBLOCK0 = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadFurnaces.CNCMACHINE_INTERNALNAME + "/" + MadFurnaces.CNCMACHINE_INTERNALNAME + "_Cutblock0.mad");
    private MadTechneModel MODEL_CUTBLOCK1 = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadFurnaces.CNCMACHINE_INTERNALNAME + "/" + MadFurnaces.CNCMACHINE_INTERNALNAME + "_Cutblock1.mad");
    private MadTechneModel MODEL_CUTBLOCK2 = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadFurnaces.CNCMACHINE_INTERNALNAME + "/" + MadFurnaces.CNCMACHINE_INTERNALNAME + "_Cutblock2.mad");
    private MadTechneModel MODEL_CUTBLOCK3 = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadFurnaces.CNCMACHINE_INTERNALNAME + "/" + MadFurnaces.CNCMACHINE_INTERNALNAME + "_Cutblock3.mad");
    private MadTechneModel MODEL_CUTBLOCK4 = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadFurnaces.CNCMACHINE_INTERNALNAME + "/" + MadFurnaces.CNCMACHINE_INTERNALNAME + "_Cutblock4.mad");
    private MadTechneModel MODEL_CUTBLOCK5 = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadFurnaces.CNCMACHINE_INTERNALNAME + "/" + MadFurnaces.CNCMACHINE_INTERNALNAME + "_Cutblock5.mad");
    private MadTechneModel MODEL_CUTBLOCK6 = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadFurnaces.CNCMACHINE_INTERNALNAME + "/" + MadFurnaces.CNCMACHINE_INTERNALNAME + "_Cutblock6.mad");
    private MadTechneModel MODEL_CUTBLOCK7 = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadFurnaces.CNCMACHINE_INTERNALNAME + "/" + MadFurnaces.CNCMACHINE_INTERNALNAME + "_Cutblock7.mad");
    private MadTechneModel MODEL_CUTBLOCK8 = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadFurnaces.CNCMACHINE_INTERNALNAME + "/" + MadFurnaces.CNCMACHINE_INTERNALNAME + "_Cutblock8.mad");

    // Press 0 - 3.
    private MadTechneModel MODEL_PRESS0 = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadFurnaces.CNCMACHINE_INTERNALNAME + "/" + MadFurnaces.CNCMACHINE_INTERNALNAME + "_Press0.mad");
    private MadTechneModel MODEL_PRESS1 = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadFurnaces.CNCMACHINE_INTERNALNAME + "/" + MadFurnaces.CNCMACHINE_INTERNALNAME + "_Press1.mad");
    private MadTechneModel MODEL_PRESS2 = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadFurnaces.CNCMACHINE_INTERNALNAME + "/" + MadFurnaces.CNCMACHINE_INTERNALNAME + "_Press2.mad");
    private MadTechneModel MODEL_PRESS3 = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadFurnaces.CNCMACHINE_INTERNALNAME + "/" + MadFurnaces.CNCMACHINE_INTERNALNAME + "_Press3.mad");
    
    // Water 0 - 8.
    private MadTechneModel MODEL_WATER0 = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadFurnaces.CNCMACHINE_INTERNALNAME + "/" + MadFurnaces.CNCMACHINE_INTERNALNAME + "_Water0.mad");
    private MadTechneModel MODEL_WATER1 = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadFurnaces.CNCMACHINE_INTERNALNAME + "/" + MadFurnaces.CNCMACHINE_INTERNALNAME + "_Water1.mad");
    private MadTechneModel MODEL_WATER2 = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadFurnaces.CNCMACHINE_INTERNALNAME + "/" + MadFurnaces.CNCMACHINE_INTERNALNAME + "_Water2.mad");
    private MadTechneModel MODEL_WATER3 = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadFurnaces.CNCMACHINE_INTERNALNAME + "/" + MadFurnaces.CNCMACHINE_INTERNALNAME + "_Water3.mad");
    private MadTechneModel MODEL_WATER4 = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadFurnaces.CNCMACHINE_INTERNALNAME + "/" + MadFurnaces.CNCMACHINE_INTERNALNAME + "_Water4.mad");
    private MadTechneModel MODEL_WATER5 = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadFurnaces.CNCMACHINE_INTERNALNAME + "/" + MadFurnaces.CNCMACHINE_INTERNALNAME + "_Water5.mad");
    private MadTechneModel MODEL_WATER6 = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadFurnaces.CNCMACHINE_INTERNALNAME + "/" + MadFurnaces.CNCMACHINE_INTERNALNAME + "_Water6.mad");
    private MadTechneModel MODEL_WATER7 = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadFurnaces.CNCMACHINE_INTERNALNAME + "/" + MadFurnaces.CNCMACHINE_INTERNALNAME + "_Water7.mad");
    private MadTechneModel MODEL_WATER8 = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadFurnaces.CNCMACHINE_INTERNALNAME + "/" + MadFurnaces.CNCMACHINE_INTERNALNAME + "_Water8.mad");

    // Default render ID for base machine.
    public int RENDERID = RenderingRegistry.getNextAvailableRenderId();

    // Default texture used for icon rendering and base machine.
    private ResourceLocation TEXTURE_OFF = new ResourceLocation(MadScience.ID, "models/" + MadFurnaces.CNCMACHINE_INTERNALNAME + "/off.png");

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

    public void renderAllCutBlocks()
    {
        MODEL_CUTBLOCK0.renderAll();
        MODEL_CUTBLOCK1.renderAll();
        MODEL_CUTBLOCK2.renderAll();
        MODEL_CUTBLOCK3.renderAll();
        MODEL_CUTBLOCK4.renderAll();
        MODEL_CUTBLOCK5.renderAll();
        MODEL_CUTBLOCK6.renderAll();
        MODEL_CUTBLOCK7.renderAll();
        MODEL_CUTBLOCK8.renderAll();
    }

    public void renderAModelAt(CnCMachineEntity tileEntity, double x, double y, double z, float f)
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
        GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);

        // Forces tile entity to always face player.
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

        if (ENTITY != null && ENTITY.TEXTURE != null && !ENTITY.TEXTURE.isEmpty())
        {
            bindTexture(new ResourceLocation(MadScience.ID, ENTITY.TEXTURE));
        }
        else
        {
            // Default texture of we have no other reference to make.
            bindTexture(TEXTURE_OFF);
        }

        GL11.glPushMatrix();

        // DEFAULT STATE
        this.renderBaseMachine();

        // Show different stages of the machines progress.
        if (ENTITY != null)
        {
            // Get the amount of cooking time scaled by the number of steps we want to complete.
            int cookTime = ENTITY.getItemCookTimeScaled(17);
            // MadScience.logger.info("CnC Machine Cooking: " + String.valueOf(cookTime));

            if (cookTime < 1)
            {
                // Press0 - visible
                MODEL_PRESS0.renderAll();
            }

            // LOADING IRON BLOCK
            if (ENTITY.currentItemCookingValue < 1 && ENTITY.hasIronBlock)
            {
                MODEL_COMPESSEDBLOCK0.renderAll();
            }

            // Only show different parts of CnC Machine when it is powered, active and cooking.
            if (ENTITY.canSmelt() && ENTITY.isPowered() && ENTITY.isRedstonePowered())
            {
                // COMPRESSING IRON BLOCK
                // STEP 0
                if (ENTITY.currentItemCookingValue > 0 && cookTime <= 0)
                {
                    MODEL_PRESS0.renderAll();
                    MODEL_COMPESSEDBLOCK0.renderAll();
                }

                // STEP 1
                if (cookTime == 1)
                {
                    MODEL_PRESS1.renderAll();
                    MODEL_COMPESSEDBLOCK1.renderAll();
                }

                // STEP 2
                if (cookTime == 2)
                {
                    MODEL_PRESS2.renderAll();
                    MODEL_COMPESSEDBLOCK2.renderAll();
                }

                // STEP 3
                if (cookTime == 3)
                {
                    MODEL_PRESS3.renderAll();
                    MODEL_COMPESSEDBLOCK3.renderAll();
                }

                // STEP 4
                if (cookTime == 4)
                {
                    MODEL_PRESS3.renderAll();
                    this.renderAllCutBlocks();
                }

                // STEP 5
                if (cookTime == 5)
                {
                    MODEL_PRESS2.renderAll();
                    this.renderAllCutBlocks();
                }

                // STEP 6
                if (cookTime == 6)
                {
                    MODEL_PRESS1.renderAll();
                    this.renderAllCutBlocks();
                }

                // CUTTING STATE
                // STEP 0
                if (cookTime == 7)
                {
                    // NOTE: WATER TURNS ON AT THIS STAGE
                    MODEL_PRESS0.renderAll();
                    this.renderAllCutBlocks();
                }

                // STEP 1
                if (cookTime == 8)
                {
                    this.renderAllCutBlocks();
                    
                    MODEL_PRESS0.renderAll();
                    MODEL_WATER0.renderAll();
                }

                // STEP 2
                if (cookTime == 9)
                {
                    MODEL_CUTBLOCK1.renderAll();
                    MODEL_CUTBLOCK2.renderAll();
                    
                    MODEL_CUTBLOCK3.renderAll();
                    MODEL_CUTBLOCK4.renderAll();
                    MODEL_CUTBLOCK5.renderAll();
                    
                    MODEL_CUTBLOCK6.renderAll();
                    MODEL_CUTBLOCK7.renderAll();
                    MODEL_CUTBLOCK8.renderAll();
                    MODEL_PRESS0.renderAll();
                    MODEL_WATER1.renderAll();
                }

                // STEP 3
                if (cookTime == 10)
                {
                    MODEL_CUTBLOCK2.renderAll();
                    
                    MODEL_CUTBLOCK3.renderAll();
                    MODEL_CUTBLOCK4.renderAll();
                    MODEL_CUTBLOCK5.renderAll();
                    
                    MODEL_CUTBLOCK6.renderAll();
                    MODEL_CUTBLOCK7.renderAll();
                    MODEL_CUTBLOCK8.renderAll();
                    
                    MODEL_PRESS0.renderAll();
                    MODEL_WATER2.renderAll();
                }

                // STEP 4
                if (cookTime == 11)
                {
                    MODEL_CUTBLOCK3.renderAll();
                    MODEL_CUTBLOCK4.renderAll();
                    MODEL_CUTBLOCK5.renderAll();
                    
                    MODEL_CUTBLOCK6.renderAll();
                    MODEL_CUTBLOCK7.renderAll();
                    MODEL_CUTBLOCK8.renderAll();
                    
                    MODEL_PRESS0.renderAll();
                    MODEL_WATER5.renderAll();
                }

                // STEP 5
                if (cookTime == 12)
                {
                    MODEL_CUTBLOCK4.renderAll();
                    MODEL_CUTBLOCK5.renderAll();
                    
                    MODEL_CUTBLOCK6.renderAll();
                    MODEL_CUTBLOCK7.renderAll();
                    MODEL_CUTBLOCK8.renderAll();
                    
                    MODEL_PRESS0.renderAll();
                    MODEL_WATER4.renderAll();
                }

                // STEP 6
                if (cookTime == 13)
                {
                    MODEL_CUTBLOCK5.renderAll();
                    
                    MODEL_CUTBLOCK6.renderAll();
                    MODEL_CUTBLOCK7.renderAll();
                    MODEL_CUTBLOCK8.renderAll();
                    
                    MODEL_PRESS0.renderAll();
                    MODEL_WATER3.renderAll();
                }

                // STEP 7
                if (cookTime == 14)
                {
                    MODEL_CUTBLOCK6.renderAll();
                    MODEL_CUTBLOCK7.renderAll();
                    MODEL_CUTBLOCK8.renderAll();
                    
                    MODEL_PRESS0.renderAll();
                    MODEL_WATER6.renderAll();
                }

                // STEP 8
                if (cookTime == 15)
                {
                    MODEL_CUTBLOCK7.renderAll();
                    MODEL_CUTBLOCK8.renderAll();
                    
                    MODEL_PRESS0.renderAll();
                    MODEL_WATER7.renderAll();
                }

                // STEP 9
                if (cookTime == 16)
                {
                    MODEL_CUTBLOCK8.renderAll();
                    
                    MODEL_PRESS0.renderAll();
                    MODEL_WATER8.renderAll();
                }

                // STEP 10 - DONE!
                if (cookTime >= 16)
                {
                    MODEL_PRESS0.renderAll();
                }
            }
        }

        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }

    public void renderBaseMachine()
    {
        // Always visible base pieces of CnC Machine.
        MODEL_BASE.renderAll();
        MODEL_SCREEN.renderAll();
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
        Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE_OFF);

        // Adjust rendering space to match what caller expects
        TransformationTypes transformationToBeUndone = TransformationTypes.NONE;
        switch (type)
        {
        case EQUIPPED:
        {
            float scale = 1.4F;
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
            break; // never here
        }

        // Default state for machine while in players hands and as item.
        this.renderBaseMachine();

        // Press0 - visible
        MODEL_PRESS0.renderAll();

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
        this.renderAModelAt((CnCMachineEntity) tileEntity, x, y, z, scale);
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

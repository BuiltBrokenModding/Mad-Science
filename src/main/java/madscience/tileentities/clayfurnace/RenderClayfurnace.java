package madscience.tileentities.clayfurnace;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import madscience.MadFurnaces;
import madscience.MadScience;
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
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderClayfurnace extends TileEntitySpecialRenderer implements ISimpleBlockRenderingHandler, IItemRenderer
{
    private enum TransformationTypes
    {
        NONE, DROPPED, INVENTORY, THIRDPERSONEQUIPPED
    }

    public int modelRenderID = RenderingRegistry.getNextAvailableRenderId();
    private IModelCustom MODEL = AdvancedModelLoader.loadModel(new ResourceLocation(MadScience.MODEL_PATH + MadFurnaces.CLAYFURNACE_INTERNALNAME + "/" + MadFurnaces.CLAYFURNACE_INTERNALNAME + ".mad"));

    private ResourceLocation TEXTURE_IDLE = new ResourceLocation(MadScience.ID, "models/" + MadFurnaces.CLAYFURNACE_INTERNALNAME + "/idle.png");
    private ResourceLocation TEXTURE_SHELL = new ResourceLocation(MadScience.ID, "models/" + MadFurnaces.CLAYFURNACE_INTERNALNAME + "/shell.png");
    private ResourceLocation TEXTURE_DONE = new ResourceLocation(MadScience.ID, "models/" + MadFurnaces.CLAYFURNACE_INTERNALNAME + "/done.png");

    private ResourceLocation[] TEXTURE_WORK = new ResourceLocation[]
            {
                    new ResourceLocation(MadScience.ID, "models/" + MadFurnaces.CLAYFURNACE_INTERNALNAME + "/work0.png"),
                    new ResourceLocation(MadScience.ID, "models/" + MadFurnaces.CLAYFURNACE_INTERNALNAME + "/work1.png"),
                    new ResourceLocation(MadScience.ID, "models/" + MadFurnaces.CLAYFURNACE_INTERNALNAME + "/work2.png"),
                    new ResourceLocation(MadScience.ID, "models/" + MadFurnaces.CLAYFURNACE_INTERNALNAME + "/work3.png"),
                    new ResourceLocation(MadScience.ID, "models/" + MadFurnaces.CLAYFURNACE_INTERNALNAME + "/work4.png")
            };

    private ResourceLocation[] TEXTURE_REDHOT = new ResourceLocation[]
            {
                    new ResourceLocation(MadScience.ID, "models/" + MadFurnaces.CLAYFURNACE_INTERNALNAME + "/redhot0.png"),
                    new ResourceLocation(MadScience.ID, "models/" + MadFurnaces.CLAYFURNACE_INTERNALNAME + "/redhot1.png"),
                    new ResourceLocation(MadScience.ID, "models/" + MadFurnaces.CLAYFURNACE_INTERNALNAME + "/redhot2.png"),
                    new ResourceLocation(MadScience.ID, "models/" + MadFurnaces.CLAYFURNACE_INTERNALNAME + "/redhot3.png"),
                    new ResourceLocation(MadScience.ID, "models/" + MadFurnaces.CLAYFURNACE_INTERNALNAME + "/redhot4.png")
            };

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

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float scale)
    {
        TileClayFurnace tile = (TileClayFurnace) tileEntity;
        // Changes the objects rotation to match whatever the player was facing.
        int rotation = 180;
        switch (tile.getBlockMetadata() % 4)
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
        if (tile.state != TileClayFurnace.BurnState.SMOLDERING)
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


        bindTexture(getTextureBasedOnState(tile, tile.state));

        if (tile.state == TileClayFurnace.BurnState.COOLING)
        {
            MODEL.renderOnly("MoltenBlock");
        }
        else if (tile.state == TileClayFurnace.BurnState.DONE)
        {
            MODEL.renderOnly("MoltenBlockShell");
        }
        else
        {
            MODEL.renderAllExcept("MoltenBlockShell", "MoltenBlock");
        }

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
        Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE_IDLE);

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
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
    {
        // Overridden by our tile entity.
        return false;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId)
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

    private ResourceLocation getTextureBasedOnState(TileClayFurnace tile, TileClayFurnace.BurnState state)
    {
        // Active state has many textures based on item cook progress.
        if (state == TileClayFurnace.BurnState.DONE)
        {
            // COOLED DOWN (WAITING FOR PLAYER TO HIT US)
            return TEXTURE_DONE;
        }
        else if (state == TileClayFurnace.BurnState.SMOLDERING)
        {
            // SMOLDERING FURNACE MODE
            return TEXTURE_SHELL;
        }
        else if (state == TileClayFurnace.BurnState.COOLING || state == TileClayFurnace.BurnState.COOKING)
        {
            // COOL DOWN (RED HOT MODE)
            if (tile.tickRate() % (MadScience.SECOND_IN_TICKS * 5) == 0L)
            {
                ResourceLocation l;
                if (state == TileClayFurnace.BurnState.COOLING)
                {
                    l = TEXTURE_REDHOT[tile.animationFrame];
                }
                else
                {
                    l = TEXTURE_WORK[tile.animationFrame];
                }
                // Update animation frame.
                ++tile.animationFrame;
                if (tile.animationFrame >= 5)
                {
                    tile.animationFrame = 0;
                }
                return l;
            }
        }

        return TEXTURE_IDLE;
    }
}

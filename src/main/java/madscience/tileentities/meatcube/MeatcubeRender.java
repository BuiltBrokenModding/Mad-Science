package madscience.tileentities.meatcube;

import madscience.MadFurnaces;
import madscience.MadScience;
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
    public int modelRenderID = RenderingRegistry.getNextAvailableRenderId();

    // The model of your block
    private MeatcubeModel model;

    // Tile Entity that our block inits.
    private MeatcubeEntity lastPlacedTileEntity;

    private ResourceLocation meatcubeTextureDefault = new ResourceLocation(MadScience.ID, "models/" + MadFurnaces.MEATCUBE_INTERNALNAME + "/meatcube_0.png");

    public MeatcubeRender()
    {
        // Creates a new model but we will tie it to our unique rendering ID.
        this.model = new MeatcubeModel();
    }

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
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
    {
        return;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {
        GL11.glPushMatrix();

        // Use the same texture we do on the block normally.
        Minecraft.getMinecraft().renderEngine.bindTexture(meatcubeTextureDefault);

        // adjust rendering space to match what caller expects
        TransformationTypes transformationToBeUndone = TransformationTypes.NONE;
        switch (type)
        {
        case EQUIPPED:
        {
            float scale = 1.4F;
            GL11.glScalef(scale, scale, scale);
            GL11.glTranslatef(0.1F, 0.3F, 0.3F);
            GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
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
            GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
            break;
        }
        case INVENTORY:
        {
            float scale = 1.0F;
            GL11.glScalef(scale, scale, scale);
            GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(270.0F, 0.0F, 1.0F, 0.0F);
            transformationToBeUndone = TransformationTypes.INVENTORY;
            break;
        }
        case ENTITY:
        {
            float scale = 1.0F;
            GL11.glScalef(scale, scale, scale);
            GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
            transformationToBeUndone = TransformationTypes.DROPPED;
            break;
        }
        default:
            break; // never here
        }

        // Renders the model in the gameworld at the correct scale.
        GL11.glTranslatef(0.0F, -1.0F, 0.0F);
        this.model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
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
        // Grab the individual tile entity in the world.
        lastPlacedTileEntity = (MeatcubeEntity) tileEntity;
        if (lastPlacedTileEntity == null)
        {
            return;
        }

        // Changes the objects rotation to match whatever the player was facing.
        int rotation = 180;
        switch (lastPlacedTileEntity.getBlockMetadata() % 4)
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
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);

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
        if (lastPlacedTileEntity != null && lastPlacedTileEntity.meatcubeTexturePath != null && !lastPlacedTileEntity.meatcubeTexturePath.isEmpty())
        {
            bindTexture(new ResourceLocation(MadScience.ID, lastPlacedTileEntity.meatcubeTexturePath));
        }
        else
        {
            bindTexture(meatcubeTextureDefault);
        }

        // Flips the model around so it is not upside-down.
        GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(180, 0.0F, 1.0F, 0.0F);

        // All parts of the meat are hidden by default.
        model.Meat0.showModel = false;
        model.Meat1.showModel = false;
        model.Meat2.showModel = false;
        model.Meat3.showModel = false;
        model.Meat4.showModel = false;
        model.Meat5.showModel = false;
        model.Meat6.showModel = false;
        model.Meat7.showModel = false;
        model.Meat8.showModel = false;
        model.Meat9.showModel = false;
        model.Meat10.showModel = false;
        model.Meat11.showModel = false;
        model.Meat12.showModel = false;
        model.Meat13.showModel = false;
        model.Meat14.showModel = false;

        // Display different chunks of the model based on internal health value.
        if (lastPlacedTileEntity.currentMeatCubeDamageValue >= 0)
        {
            model.Meat0.showModel = true;
        }

        if (lastPlacedTileEntity.currentMeatCubeDamageValue >= 1)
        {
            model.Meat1.showModel = true;
        }

        if (lastPlacedTileEntity.currentMeatCubeDamageValue >= 2)
        {
            model.Meat2.showModel = true;
        }

        if (lastPlacedTileEntity.currentMeatCubeDamageValue >= 3)
        {
            model.Meat3.showModel = true;
        }

        if (lastPlacedTileEntity.currentMeatCubeDamageValue >= 4)
        {
            model.Meat4.showModel = true;
        }

        if (lastPlacedTileEntity.currentMeatCubeDamageValue >= 5)
        {
            model.Meat5.showModel = true;
        }

        if (lastPlacedTileEntity.currentMeatCubeDamageValue >= 6)
        {
            model.Meat6.showModel = true;
        }

        if (lastPlacedTileEntity.currentMeatCubeDamageValue >= 7)
        {
            model.Meat7.showModel = true;
        }

        if (lastPlacedTileEntity.currentMeatCubeDamageValue >= 8)
        {
            model.Meat8.showModel = true;
        }

        if (lastPlacedTileEntity.currentMeatCubeDamageValue >= 9)
        {
            model.Meat9.showModel = true;
        }

        if (lastPlacedTileEntity.currentMeatCubeDamageValue >= 10)
        {
            model.Meat10.showModel = true;
        }

        if (lastPlacedTileEntity.currentMeatCubeDamageValue >= 11)
        {
            model.Meat11.showModel = true;
        }

        if (lastPlacedTileEntity.currentMeatCubeDamageValue >= 12)
        {
            model.Meat12.showModel = true;
        }

        if (lastPlacedTileEntity.currentMeatCubeDamageValue >= 13)
        {
            model.Meat13.showModel = true;
        }

        if (lastPlacedTileEntity.currentMeatCubeDamageValue >= 14)
        {
            model.Meat14.showModel = true;
        }

        this.model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

        // Finish pushing data to OpenGL.
        GL11.glPopMatrix();
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

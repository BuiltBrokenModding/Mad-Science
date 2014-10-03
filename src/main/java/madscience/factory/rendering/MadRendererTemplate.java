package madscience.factory.rendering;

import madscience.MadModLoader;
import madscience.MadModMetadata;
import madscience.factory.MadRenderingFactory;
import madscience.factory.MadTileEntityFactory;
import madscience.factory.model.MadModel;
import madscience.factory.model.MadModelPosition;
import madscience.factory.model.MadModelScale;
import madscience.factory.product.MadRenderingFactoryProduct;
import madscience.factory.product.MadTileEntityFactoryProduct;
import madscience.factory.tile.MadTileEntityPrefab;
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
public class MadRendererTemplate extends TileEntitySpecialRenderer implements ISimpleBlockRenderingHandler, IItemRenderer
{
    private MadRenderingFactoryProduct currentRenderProduct = null;
    private int currentRenderID = -1;

    /** Called on startup of game when renderer is associated with event system with Minecraft/Forge. */
    public MadRendererTemplate()
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
        // Grab rendering instance for item from rendering factory.
        this.currentRenderProduct = MadRenderingFactory.instance().getModelInstance(
                MadUtils.cleanTag(item.getUnlocalizedName()),
                true,
                String.valueOf(item.getItemDamage()),
                String.valueOf(item.getMaxDamage()));
        
        if (currentRenderProduct == null)
        {
            // Default response is to render no item types if we have no rendering data.
            return false;
        }
        
        // Determine how we should render this tile entity as an item block. Returning false uses 2D icon instead of model.
        boolean shouldRender = false;
        switch (type)
        {
            case ENTITY:
            {
                shouldRender = currentRenderProduct.getModelItemRenderInformation().isRenderItemEntity3D();
            }
            break;
            case EQUIPPED:
            {
                shouldRender = currentRenderProduct.getModelItemRenderInformation().isRenderItemEquipped3D();
            }
            break;
            case EQUIPPED_FIRST_PERSON:
            {
                shouldRender = currentRenderProduct.getModelItemRenderInformation().isRenderItemFirstPerson3D();
            }
            break;
            case INVENTORY:
            {
                shouldRender = currentRenderProduct.getModelItemRenderInformation().isRenderItemInventory3D();
            }
            break;
            default:
            {
                shouldRender = false;
            }
            break;
        }
        
        // Cleanup.
        this.currentRenderProduct = null;
        this.currentRenderID = -1;
        
        // Return based on information from render product.
        return shouldRender;
    }

    private void renderMadModelAt(MadTileEntityPrefab tileEntity, double x, double y, double z, float scale)
    {
        // Grab the individual tile entity in the world.
        MadTileEntityPrefab madTileEntity = null;
        if (tileEntity instanceof MadTileEntityPrefab)
        {
            madTileEntity = tileEntity;
        }
        
        // Check for null on returned object, casting should not fail though!
        if (madTileEntity == null)
        {
            return;
        }
        
        // Grab world rendering information.
        MadModelWorldRender worldRenderInfo = madTileEntity.getEntityWorldRenderInformation();
        if (worldRenderInfo == null)
        {
            return;
        }
        
        // Check if model instance needs to updated before render.
        MadRenderingFactory.instance().updateModelInstance(
                madTileEntity.getMachineInternalName(),
                false,
                madTileEntity.getEntityModelData(),
                worldRenderInfo.getModelWorldPosition(),
                worldRenderInfo.getModelWorldScale(),
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
        GL11.glTranslatef(
                (float) x + currentRenderProduct.getModelWorldRenderInformation().getModelWorldPosition().getModelTranslateX(),
                (float) y + currentRenderProduct.getModelWorldRenderInformation().getModelWorldPosition().getModelTranslateY(),
                (float) z + currentRenderProduct.getModelWorldRenderInformation().getModelWorldPosition().getModelTranslateZ());
        
        // Scale the model as according to world rendering information.
        GL11.glScalef(
                currentRenderProduct.getModelWorldRenderInformation().getModelWorldScale().getModelScaleX(),
                currentRenderProduct.getModelWorldRenderInformation().getModelWorldScale().getModelScaleY(),
                currentRenderProduct.getModelWorldRenderInformation().getModelWorldScale().getModelScaleZ());

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
            bindTexture(new ResourceLocation(MadModMetadata.ID, madTileEntity.getEntityTexture()));
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
        
        // Get registered machine for this item.
        String cleanedName = MadUtils.cleanTag(item.getUnlocalizedName());
        MadTileEntityFactoryProduct registeredMachine = MadTileEntityFactory.instance().getMachineInfo(cleanedName);
        if (registeredMachine == null)
        {
            return;
        }
        
        // Grab loaded item rendering information from the factory.
        MadModel machineModels = registeredMachine.getModelArchive();
        if (machineModels == null)
        {
            return;
        }
        
        // Grab world rendering information from the model archive.
        MadModelItemRender itemRenderInfo = machineModels.getItemRenderInfoClone();
        if (itemRenderInfo == null)
        {
            return;
        }
        
        // Depending on what type of rendering we are doing populate needed position and scale information.
        MadModelPosition itemRenderPosition = null;
        MadModelScale itemRenderScale = null;
        
        switch (type)
        {
        case ENTITY:
            itemRenderPosition = itemRenderInfo.getModelItemEntityPosition();
            itemRenderScale = itemRenderInfo.getModelItemEntityScale();
            break;
        case EQUIPPED:
            itemRenderPosition = itemRenderInfo.getModelItemEquippedPosition();
            itemRenderScale = itemRenderInfo.getModelItemEquippedScale();
            break;
        case EQUIPPED_FIRST_PERSON:
            itemRenderPosition = itemRenderInfo.getModelItemFirstPersonPosition();
            itemRenderScale = itemRenderInfo.getModelItemFirstPersonScale();
            break;
        case INVENTORY:
            itemRenderPosition = itemRenderInfo.getModelItemInventoryPosition();
            itemRenderScale = itemRenderInfo.getModelItemInventoryScale();
            break;
        default:
            break;
        }
        
        // We cannot proceed if the position and scaling information is null.
        if (itemRenderPosition == null || itemRenderScale == null)
        {
            return;
        }
        
        // Update item render instance as requested by server.
        MadRenderingFactory.instance().updateModelInstance(
                registeredMachine.getMachineName(),
                true,
                machineModels.getMachineModelsDataClone(),
                itemRenderPosition,
                itemRenderScale,
                String.valueOf(item.getItemDamage()),
                String.valueOf(item.getMaxDamage()));
        
        // Grab rendering instance for item from rendering factory.
        this.currentRenderProduct = MadRenderingFactory.instance().getModelInstance(
                registeredMachine.getMachineName(),
                true,
                String.valueOf(item.getItemDamage()),
                String.valueOf(item.getMaxDamage()));
        
        // Cannot proceed without a final rendering product to read data from about our render state.
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
            // Scale
            GL11.glScalef(
                    currentRenderProduct.getModelItemRenderInformation().getModelItemEquippedScale().getModelScaleX(),
                    currentRenderProduct.getModelItemRenderInformation().getModelItemEquippedScale().getModelScaleY(),
                    currentRenderProduct.getModelItemRenderInformation().getModelItemEquippedScale().getModelScaleZ());
            
            // Position
            GL11.glTranslatef(
                    currentRenderProduct.getModelItemRenderInformation().getModelItemEquippedPosition().getModelTranslateX(),
                    currentRenderProduct.getModelItemRenderInformation().getModelItemEquippedPosition().getModelTranslateY(),
                    currentRenderProduct.getModelItemRenderInformation().getModelItemEquippedPosition().getModelTranslateZ());
            
            // Rotation
            GL11.glRotatef(
                    currentRenderProduct.getModelItemRenderInformation().getModelItemEquippedRotation().getModelRotationAngle(),
                    currentRenderProduct.getModelItemRenderInformation().getModelItemEquippedRotation().getModelRotationX(),
                    currentRenderProduct.getModelItemRenderInformation().getModelItemEquippedRotation().getModelRotationY(),
                    currentRenderProduct.getModelItemRenderInformation().getModelItemEquippedRotation().getModelRotationZ());
            
            GL11.glEnable(GL11.GL_CULL_FACE);
            transformationToBeUndone = MadRenderTransformationTypes.THIRDPERSONEQUIPPED;
            break;
        }
        case EQUIPPED_FIRST_PERSON:
        {
            // Scale
            GL11.glScalef(
                    currentRenderProduct.getModelItemRenderInformation().getModelItemFirstPersonScale().getModelScaleX(),
                    currentRenderProduct.getModelItemRenderInformation().getModelItemFirstPersonScale().getModelScaleY(),
                    currentRenderProduct.getModelItemRenderInformation().getModelItemFirstPersonScale().getModelScaleZ());
            
            // Position
            GL11.glTranslatef(
                    currentRenderProduct.getModelItemRenderInformation().getModelItemFirstPersonPosition().getModelTranslateX(),
                    currentRenderProduct.getModelItemRenderInformation().getModelItemFirstPersonPosition().getModelTranslateY(),
                    currentRenderProduct.getModelItemRenderInformation().getModelItemFirstPersonPosition().getModelTranslateZ());
            
            // Rotation
            GL11.glRotatef(
                    currentRenderProduct.getModelItemRenderInformation().getModelItemFirstPersonRotation().getModelRotationAngle(),
                    currentRenderProduct.getModelItemRenderInformation().getModelItemFirstPersonRotation().getModelRotationX(),
                    currentRenderProduct.getModelItemRenderInformation().getModelItemFirstPersonRotation().getModelRotationY(),
                    currentRenderProduct.getModelItemRenderInformation().getModelItemFirstPersonRotation().getModelRotationZ());
            
            break;
        }
        case INVENTORY:
        {
            // Scale
            GL11.glScalef(
                    currentRenderProduct.getModelItemRenderInformation().getModelItemInventoryScale().getModelScaleX(),
                    currentRenderProduct.getModelItemRenderInformation().getModelItemInventoryScale().getModelScaleY(),
                    currentRenderProduct.getModelItemRenderInformation().getModelItemInventoryScale().getModelScaleZ());
            
            // Position
            GL11.glTranslatef(
                    currentRenderProduct.getModelItemRenderInformation().getModelItemInventoryPosition().getModelTranslateX(),
                    currentRenderProduct.getModelItemRenderInformation().getModelItemInventoryPosition().getModelTranslateY(),
                    currentRenderProduct.getModelItemRenderInformation().getModelItemInventoryPosition().getModelTranslateZ());
            
            // Rotation
            GL11.glRotatef(
                    currentRenderProduct.getModelItemRenderInformation().getModelItemInventoryRotation().getModelRotationAngle(),
                    currentRenderProduct.getModelItemRenderInformation().getModelItemInventoryRotation().getModelRotationX(),
                    currentRenderProduct.getModelItemRenderInformation().getModelItemInventoryRotation().getModelRotationY(),
                    currentRenderProduct.getModelItemRenderInformation().getModelItemInventoryRotation().getModelRotationZ());
            
            
            transformationToBeUndone = MadRenderTransformationTypes.INVENTORY;
            break;
        }
        case ENTITY:
        {
            // Scale
            GL11.glScalef(
                    currentRenderProduct.getModelItemRenderInformation().getModelItemEntityScale().getModelScaleX(),
                    currentRenderProduct.getModelItemRenderInformation().getModelItemEntityScale().getModelScaleY(),
                    currentRenderProduct.getModelItemRenderInformation().getModelItemEntityScale().getModelScaleZ());
            
            // Position
            GL11.glTranslatef(
                    currentRenderProduct.getModelItemRenderInformation().getModelItemEntityPosition().getModelTranslateX(),
                    currentRenderProduct.getModelItemRenderInformation().getModelItemEntityPosition().getModelTranslateY(),
                    currentRenderProduct.getModelItemRenderInformation().getModelItemEntityPosition().getModelTranslateZ());
            
            // Rotation
            GL11.glRotatef(
                    currentRenderProduct.getModelItemRenderInformation().getModelItemEntityRotation().getModelRotationAngle(),
                    currentRenderProduct.getModelItemRenderInformation().getModelItemEntityRotation().getModelRotationX(),
                    currentRenderProduct.getModelItemRenderInformation().getModelItemEntityRotation().getModelRotationY(),
                    currentRenderProduct.getModelItemRenderInformation().getModelItemEntityRotation().getModelRotationZ());
            
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

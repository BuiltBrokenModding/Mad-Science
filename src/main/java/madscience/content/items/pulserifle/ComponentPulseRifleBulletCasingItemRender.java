package madscience.content.items.pulserifle;

import madscience.MadScience;
import madscience.content.items.weapons.ItemWeaponPart;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

public class ComponentPulseRifleBulletCasingItemRender implements IItemRenderer
{
    private enum TransformationTypes
    {
        DROPPED, INVENTORY, NONE, THIRDPERSONEQUIPPED
    }

    private IModelCustom MODEL = AdvancedModelLoader.loadModel(new ResourceLocation(MadScience.MODEL_PATH + "weaponComponents/" + ItemWeaponPart.EnumWeaponParts.BULLET_CASING.INTERNAL_NAME + ".mad"));
    private ResourceLocation TEXTURE = new ResourceLocation(MadScience.ID, "models/weaponComponents/" + ItemWeaponPart.EnumWeaponParts.BULLET_CASING.INTERNAL_NAME + ".png");

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
                float scale = 1.0F;
                GL11.glScalef(scale, scale, scale);
                GL11.glTranslatef(5.0F, 1.0F, -2.0F);
                GL11.glTranslatef(-3.0F, 0.0F, 3.0F);
                GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
                GL11.glEnable(GL11.GL_CULL_FACE);
                transformationToBeUndone = TransformationTypes.THIRDPERSONEQUIPPED;
                break;
            }
            case EQUIPPED_FIRST_PERSON:
            {
                float scale = 1.0F;
                GL11.glScalef(scale, scale, scale);
                GL11.glTranslatef(-1.0F, 0.7F, -0.5F);
                GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(270.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
                break;
            }
            case INVENTORY:
            {
                float scale = 1.8F;
                GL11.glScalef(scale, scale, scale);
                GL11.glTranslatef(-0.65F, -1.5F, 0.0F);
                GL11.glRotatef(-180.0F, 0.0F, 1.0F, 0.0F);
                transformationToBeUndone = TransformationTypes.INVENTORY;
                break;
            }
            case ENTITY:
            {
                float scale = 0.25F;
                GL11.glScalef(scale, scale, scale);
                GL11.glTranslatef(0.0F, -0.5F, 0.0F);
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

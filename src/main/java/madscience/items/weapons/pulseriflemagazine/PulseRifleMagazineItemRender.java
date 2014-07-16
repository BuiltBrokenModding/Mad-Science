package madscience.items.weapons.pulseriflemagazine;

import madscience.MadWeapons;
import madscience.factory.mod.MadMod;
import madscience.factory.model.MadTechneModel;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;

import org.lwjgl.opengl.GL11;

public class PulseRifleMagazineItemRender implements IItemRenderer
{
    private enum TransformationTypes
    {
        DROPPED, INVENTORY, NONE, THIRDPERSONEQUIPPED
    }

    private MadTechneModel MODEL = (MadTechneModel) AdvancedModelLoader.loadModel(MadMod.MODEL_PATH + MadWeapons.WEAPONITEM_PULSERIFLE_MAGAZINE_INTERNALNAME + "/" + MadWeapons.WEAPONITEM_PULSERIFLE_MAGAZINE_INTERNALNAME + ".mad");
    private ResourceLocation TEXTURE = new ResourceLocation(MadMod.ID, "models/" + MadWeapons.WEAPONITEM_PULSERIFLE_MAGAZINE_INTERNALNAME + "/" + MadWeapons.WEAPONITEM_PULSERIFLE_MAGAZINE_INTERNALNAME + ".png");

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
            float scale = 0.20F;
            GL11.glScalef(scale, scale, scale);
            GL11.glTranslatef(3.0F, -1.0F, 2.0F);
            GL11.glRotatef(-60.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(42.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(22.0F, 0.0F, 1.0F, 0.0F);
            GL11.glEnable(GL11.GL_CULL_FACE);
            transformationToBeUndone = TransformationTypes.THIRDPERSONEQUIPPED;
            break;
        }
        case EQUIPPED_FIRST_PERSON:
        {
            float scale = 0.15F;
            GL11.glScalef(scale, scale, scale);
            GL11.glTranslatef(8.66F, 9.0F, 3.0F);
            GL11.glRotatef(142.0F, 0.0F, 1.0F, 0.0F);
            break;
        }
        case INVENTORY:
        {
            float scale = 0.48F;
            GL11.glScalef(scale, scale, scale);
            GL11.glTranslatef(2.0F, -0.5F, 0.0F);
            GL11.glRotatef(270.0F, 0.0F, 0.5F, 0.0F);
            GL11.glRotatef(-45.0F, 0.5F, 0.0F, 0.0F);
            transformationToBeUndone = TransformationTypes.INVENTORY;
            break;
        }
        case ENTITY:
        {
            float scale = 0.15F;
            GL11.glScalef(scale, scale, scale);
            GL11.glTranslatef(0.0F, 1.0F, 0.0F);
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

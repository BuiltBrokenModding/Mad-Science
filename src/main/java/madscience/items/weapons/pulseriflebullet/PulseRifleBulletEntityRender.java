package madscience.items.weapons.pulseriflebullet;

import madscience.MadWeapons;
import madscience.factory.mod.MadMod;
import madscience.factory.model.MadTechneModel;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class PulseRifleBulletEntityRender extends RenderArrow
{
    private MadTechneModel MODEL = (MadTechneModel) AdvancedModelLoader.loadModel(MadMod.MODEL_PATH + MadWeapons.WEAPONITEM_PULSERIFLE_BULLET_INTERNALNAME + "/" + MadWeapons.WEAPONITEM_PULSERIFLE_BULLET_INTERNALNAME + ".mad");
    private ResourceLocation TEXTURE = new ResourceLocation(MadMod.ID, "models/" + MadWeapons.WEAPONITEM_PULSERIFLE_BULLET_INTERNALNAME + "/" + MadWeapons.WEAPONITEM_PULSERIFLE_BULLET_INTERNALNAME + ".png");

    @Override
    public void doRender(Entity entity, double d0, double d1, double d2, float f, float f1)
    {
        this.bindTexture(TEXTURE);
        GL11.glPushMatrix();
        GL11.glTranslatef((float) d0, (float) d1, (float) d2);
        GL11.glRotatef(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * f1 - 90.0F, 0.0F, 1.0F, 0.0F);
        // GL11.glRotatef(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * f1, 0.0F, 0.0F, 1.0F);
        byte b0 = 0;
        float f2 = 0.0F;
        float f3 = 0.5F;
        float f4 = (0 + b0 * 10) / 32.0F;
        float f5 = (5 + b0 * 10) / 32.0F;
        float f6 = 0.0F;
        float f7 = 0.15625F;
        float f8 = (5 + b0 * 10) / 32.0F;
        float f9 = (10 + b0 * 10) / 32.0F;
        float f10 = 0.05625F;
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        // float f11 = (float) entity.arrowShake - f1;

        if (f1 > 0.0F)
        {
            float f12 = -MathHelper.sin(f1 * 3.0F) * f1;
            GL11.glRotatef(f12, 0.0F, 0.0F, 1.0F);
        }

        GL11.glRotatef(45.0F, 1.0F, 0.0F, 0.0F);
        GL11.glScalef(f10, f10, f10);
        GL11.glTranslatef(-4.0F, 0.0F, 0.0F);
        GL11.glNormal3f(f10, 0.0F, 0.0F);

        MODEL.renderAll();

        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return TEXTURE;
    }
}

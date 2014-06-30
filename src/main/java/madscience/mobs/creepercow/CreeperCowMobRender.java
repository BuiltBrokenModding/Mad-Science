package madscience.mobs.creepercow;

import madscience.MadMobs;
import madscience.MadScience;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CreeperCowMobRender extends RenderLiving
{
    // Refers to location in asset folder with other textures and sounds.
    private static final ResourceLocation mobTexture = new ResourceLocation(MadScience.ID, "models/" + MadMobs.GMO_CREEPERCOW_INTERNALNAME + "/" + MadMobs.GMO_CREEPERCOW_INTERNALNAME + ".png");
    private static final ResourceLocation mobTextureArmor = new ResourceLocation(MadScience.ID, "models/" + MadMobs.GMO_CREEPERCOW_INTERNALNAME + "/" + MadMobs.GMO_CREEPERCOW_INTERNALNAME + "_armor.png");

    /** The creeper model. */
    private ModelBase creeperModel = new CreeperCowMobModel(2.0F);

    public CreeperCowMobRender(CreeperCowMobModel par1ModelBase, float par2)
    {
        // Note: Render types for simple things can be EntityLiving or extend
        // existing mobs.
        super(par1ModelBase, par2);
    }

    @Override
    public void doRender(Entity entity, double d, double d1, double d2, float f, float f1)
    {
        doRenderLiving((CreeperCowMobEntity) entity, d, d1, d2, f, f1);
    }

    @Override
    public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2, float f, float f1)
    {
        super.doRenderLiving(entityliving, d, d1, d2, f, f1);
    }

    private int func_77061_b(CreeperCowMobEntity par1EntityCreeper, int par2, float par3)
    {
        return -1;
    }

    /** Returns an ARGB int color back. Args: entityLiving, lightBrightness, partialTickTime */
    @Override
    protected int getColorMultiplier(EntityLivingBase par1EntityLivingBase, float par2, float par3)
    {
        return this.updateCreeperColorMultiplier((CreeperCowMobEntity) par1EntityLivingBase, par2, par3);
    }

    private ResourceLocation getCreeperTextures(CreeperCowMobEntity par1EntityCreeper)
    {
        return mobTexture;
    }

    /** Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture. */
    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return this.getCreeperTextures((CreeperCowMobEntity) par1Entity);
    }

    @Override
    protected int inheritRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3)
    {
        return this.func_77061_b((CreeperCowMobEntity) par1EntityLivingBase, par2, par3);
    }

    /** Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args: entityLiving, partialTickTime */
    @Override
    protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
    {
        this.updateCreeperScale((CreeperCowMobEntity) par1EntityLivingBase, par2);
    }

    /** A method used to render a creeper's powered form as a pass model. */
    private int renderCreeperPassModel(CreeperCowMobEntity par1EntityCreeper, int par2, float par3)
    {
        if (par1EntityCreeper.getPowered())
        {
            if (par1EntityCreeper.isInvisible())
            {
                GL11.glDepthMask(false);
            }
            else
            {
                GL11.glDepthMask(true);
            }

            if (par2 == 1)
            {
                float f1 = par1EntityCreeper.ticksExisted + par3;
                this.bindTexture(mobTextureArmor);
                GL11.glMatrixMode(GL11.GL_TEXTURE);
                GL11.glLoadIdentity();
                float f2 = f1 * 0.01F;
                float f3 = f1 * 0.01F;
                GL11.glTranslatef(f2, f3, 0.0F);
                this.setRenderPassModel(this.creeperModel);
                GL11.glMatrixMode(GL11.GL_MODELVIEW);
                GL11.glEnable(GL11.GL_BLEND);
                float f4 = 0.5F;
                GL11.glColor4f(f4, f4, f4, 1.0F);
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
                return 1;
            }

            if (par2 == 2)
            {
                GL11.glMatrixMode(GL11.GL_TEXTURE);
                GL11.glLoadIdentity();
                GL11.glMatrixMode(GL11.GL_MODELVIEW);
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glDisable(GL11.GL_BLEND);
            }
        }

        return -1;
    }

    /** Queries whether should render the specified pass or not. */
    @Override
    protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3)
    {
        return this.renderCreeperPassModel((CreeperCowMobEntity) par1EntityLivingBase, par2, par3);
    }

    /** Updates color multiplier based on creeper state called by getColorMultiplier */
    private int updateCreeperColorMultiplier(CreeperCowMobEntity par1EntityCreeper, float par2, float par3)
    {
        float f2 = par1EntityCreeper.getCreeperFlashIntensity(par3);

        if ((int) (f2 * 10.0F) % 2 == 0)
        {
            return 0;
        }
        else
        {
            int i = (int) (f2 * 0.2F * 255.0F);

            if (i < 0)
            {
                i = 0;
            }

            if (i > 255)
            {
                i = 255;
            }

            short short1 = 255;
            short short2 = 255;
            short short3 = 255;
            return i << 24 | short1 << 16 | short2 << 8 | short3;
        }
    }

    /** Updates creeper scale in prerender callback */
    private void updateCreeperScale(CreeperCowMobEntity par1EntityCreeper, float par2)
    {
        float f1 = par1EntityCreeper.getCreeperFlashIntensity(par2);
        float f2 = 1.0F + MathHelper.sin(f1 * 100.0F) * f1 * 0.01F;

        if (f1 < 0.0F)
        {
            f1 = 0.0F;
        }

        if (f1 > 1.0F)
        {
            f1 = 1.0F;
        }

        f1 *= f1;
        f1 *= f1;
        float f3 = (1.0F + f1 * 0.4F) * f2;
        float f4 = (1.0F + f1 * 0.1F) / f2;
        GL11.glScalef(f3, f4, f3);
    }
}
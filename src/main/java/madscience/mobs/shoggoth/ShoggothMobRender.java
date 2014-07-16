package madscience.mobs.shoggoth;

import madscience.MadMobs;
import madscience.factory.mod.MadMod;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ShoggothMobRender extends RenderLiving
{
    // Refers to location in asset folder with other textures and sounds.
    private static final ResourceLocation mobTexture = new ResourceLocation(MadMod.ID, "models/" + MadMobs.GMO_SHOGGOTH_INTERNALNAME + "/" + MadMobs.GMO_SHOGGOTH_INTERNALNAME + ".png");
    private ModelBase scaleAmount;

    public ShoggothMobRender(ModelBase par1ModelBase, ModelBase par2ModelBase, float par3)
    {
        super(par1ModelBase, par3);
        this.scaleAmount = par2ModelBase;
    }

    /** Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture. */
    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return this.getSlimeTextures((ShoggothMobEntity) par1Entity);
    }

    private ResourceLocation getSlimeTextures(ShoggothMobEntity par1EntitySlime)
    {
        return mobTexture;
    }

    /** Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args: entityLiving, partialTickTime */
    @Override
    protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
    {
        this.scaleSlime((ShoggothMobEntity) par1EntityLivingBase, par2);
    }

    /** sets the scale for the slime based on getSlimeSize in EntitySlime */
    private void scaleSlime(ShoggothMobEntity par1EntitySlime, float par2)
    {
        float f1 = par1EntitySlime.getSlimeSize();
        float f2 = (par1EntitySlime.prevSquishFactor + (par1EntitySlime.squishFactor - par1EntitySlime.prevSquishFactor) * par2) / (f1 * 0.5F + 1.0F);
        float f3 = 1.0F / (f2 + 1.0F);
        GL11.glScalef(f3 * f1, 1.0F / f3 * f1, f3 * f1);
    }

    /** Queries whether should render the specified pass or not. */
    @Override
    protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3)
    {
        return this.shouldSlimeRenderPass((ShoggothMobEntity) par1EntityLivingBase, par2, par3);
    }

    /** Determines whether Slime Render should pass or not. */
    private int shouldSlimeRenderPass(ShoggothMobEntity par1EntitySlime, int par2, float par3)
    {
        if (par1EntitySlime.isInvisible())
        {
            return 0;
        }
        else if (par2 == 0)
        {
            this.setRenderPassModel(this.scaleAmount);
            GL11.glEnable(GL11.GL_NORMALIZE);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            return 1;
        }
        else
        {
            if (par2 == 1)
            {
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            }

            return -1;
        }
    }
}
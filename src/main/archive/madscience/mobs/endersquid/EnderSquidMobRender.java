package madscience.mobs.endersquid;

import java.util.Random;

import madscience.MadMobs;
import madscience.MadScience;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EnderSquidMobRender extends RenderLiving
{
    private static final ResourceLocation endermanEyesTexture = new ResourceLocation(MadScience.ID, "models/" + MadMobs.GMO_ENDERSQUID_INTERNALNAME + "/" + MadMobs.GMO_ENDERSQUID_INTERNALNAME + "_eyes.png");
    private static final ResourceLocation endermanTextures = new ResourceLocation(MadScience.ID, "models/" + MadMobs.GMO_ENDERSQUID_INTERNALNAME + "/" + MadMobs.GMO_ENDERSQUID_INTERNALNAME + ".png");

    /** The model of the enderman */
    private EnderSquidMobModel endermanModel;
    private Random rnd = new Random();

    public EnderSquidMobRender(EnderSquidMobModel enderSquidMobModel, float f)
    {
        super(enderSquidMobModel, f);
        this.endermanModel = (EnderSquidMobModel) super.mainModel;
        this.setRenderPassModel(this.endermanModel);
    }

    /** Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic (Render<T extends
     * Entity) and this method has signature public void doRender(T entity, double d, double d1, double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that. */
    @Override
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
        this.renderEnderman((EnderSquidMobEntity) par1Entity, par2, par4, par6, par8, par9);
    }

    @Override
    public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
    {
        this.renderEnderman((EnderSquidMobEntity) par1EntityLiving, par2, par4, par6, par8, par9);
    }

    protected ResourceLocation getEndermanTextures(EnderSquidMobEntity par1EntityEnderman)
    {
        return endermanTextures;
    }

    /** Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture. */
    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return this.getEndermanTextures((EnderSquidMobEntity) par1Entity);
    }

    /** Render the block an enderman is carrying */
    protected void renderCarrying(EnderSquidMobEntity par1EntityEnderman, float par2)
    {
        super.renderEquippedItems(par1EntityEnderman, par2);

        if (par1EntityEnderman.getCarried() > 0)
        {
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glPushMatrix();
            float f1 = 0.5F;
            GL11.glTranslatef(0.0F, 0.6875F, -0.75F);
            f1 *= 1.0F;
            GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            GL11.glScalef(-f1, -f1, f1);
            int i = par1EntityEnderman.getBrightnessForRender(par2);
            int j = i % 65536;
            int k = i / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0F, k / 1.0F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.bindTexture(TextureMap.locationBlocksTexture);
            this.renderBlocks.renderBlockAsItem(Block.blocksList[par1EntityEnderman.getCarried()], par1EntityEnderman.getCarryingData(), 1.0F);
            GL11.glPopMatrix();
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        }
    }

    /** Renders the enderman */
    public void renderEnderman(EnderSquidMobEntity par1EntityEnderman, double par2, double par4, double par6, float par8, float par9)
    {
        this.endermanModel.isCarrying = par1EntityEnderman.getCarried() > 0;
        this.endermanModel.isAttacking = par1EntityEnderman.isScreaming();

        if (par1EntityEnderman.isScreaming())
        {
            double d3 = 0.02D;
            par2 += this.rnd.nextGaussian() * d3;
            par6 += this.rnd.nextGaussian() * d3;
        }

        super.doRenderLiving(par1EntityEnderman, par2, par4, par6, par8, par9);
    }

    @Override
    protected void renderEquippedItems(EntityLivingBase par1EntityLivingBase, float par2)
    {
        this.renderCarrying((EnderSquidMobEntity) par1EntityLivingBase, par2);
    }

    /** Render the endermans eyes */
    protected int renderEyes(EnderSquidMobEntity par1EntityEnderman, int par2, float par3)
    {
        if (par2 != 0)
        {
            return -1;
        }
        else
        {
            this.bindTexture(endermanEyesTexture);
            float f1 = 1.0F;
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
            GL11.glDisable(GL11.GL_LIGHTING);

            if (par1EntityEnderman.isInvisible())
            {
                GL11.glDepthMask(false);
            }
            else
            {
                GL11.glDepthMask(true);
            }

            char c0 = 61680;
            int j = c0 % 65536;
            int k = c0 / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0F, k / 1.0F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, f1);
            return 1;
        }
    }

    public void renderPlayer(EntityLivingBase par1EntityLivingBase, double par2, double par4, double par6, float par8, float par9)
    {
        this.renderEnderman((EnderSquidMobEntity) par1EntityLivingBase, par2, par4, par6, par8, par9);
    }

    /** Queries whether should render the specified pass or not. */
    @Override
    protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3)
    {
        return this.renderEyes((EnderSquidMobEntity) par1EntityLivingBase, par2, par3);
    }
}

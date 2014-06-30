package madscience.mobs.woolycow;

import madscience.MadMobs;
import madscience.MadScience;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class WoolyCowMobRender extends RenderLiving
{
    private static final ResourceLocation shearedSheepTextures = new ResourceLocation(MadScience.ID, "models/" + MadMobs.GMO_WOOLYCOW_INTERNALNAME + "/" + MadMobs.GMO_WOOLYCOW_INTERNALNAME + ".png");
    // Location for different stages of being sheered.
    private static final ResourceLocation sheepTextures = new ResourceLocation(MadScience.ID, "models/" + MadMobs.GMO_WOOLYCOW_INTERNALNAME + "/" + MadMobs.GMO_WOOLYCOW_INTERNALNAME + "_fur.png");

    public WoolyCowMobRender(ModelBase par1ModelBase, ModelBase par2ModelBase, float par3)
    {
        // Note: Render types for simple things can be EntityLiving or extend
        // existing mobs.
        super(par1ModelBase, par3);
        this.setRenderPassModel(par2ModelBase);
    }

    @Override
    public void doRender(Entity entity, double d, double d1, double d2, float f, float f1)
    {
        doRenderLiving((WoolyCowMobEntity) entity, d, d1, d2, f, f1);
    }

    @Override
    public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2, float f, float f1)
    {
        super.doRenderLiving(entityliving, d, d1, d2, f, f1);
    }

    /** Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture. */
    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return this.setSheeredTexture((WoolyCowMobEntity) par1Entity);
    }

    private ResourceLocation setSheeredTexture(WoolyCowMobEntity par1EntitySheep)
    {
        return shearedSheepTextures;
    }

    private int setWoolColorAndRender(WoolyCowMobEntity par1EntitySheep, int par2, float par3)
    {
        if (par2 == 0 && !par1EntitySheep.getSheared())
        {
            this.bindTexture(sheepTextures);
            float f1 = 1.0F;
            int j = par1EntitySheep.getFleeceColor();
            GL11.glColor3f(f1 * WoolyCowMobEntity.fleeceColorTable[j][0], f1 * WoolyCowMobEntity.fleeceColorTable[j][1], f1 * WoolyCowMobEntity.fleeceColorTable[j][2]);
            return 1;
        }
        else
        {
            return -1;
        }
    }

    /** Queries whether should render the specified pass or not. */
    @Override
    protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3)
    {
        return this.setWoolColorAndRender((WoolyCowMobEntity) par1EntityLivingBase, par2, par3);
    }
}
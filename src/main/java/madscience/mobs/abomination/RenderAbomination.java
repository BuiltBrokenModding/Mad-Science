package madscience.mobs.abomination;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import madscience.MadScience;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderAbomination extends RenderLiving
{
    // Refers to location in asset folder with other textures and sounds.
    private static final ResourceLocation mobTexture = new ResourceLocation(MadScience.ID, "models/gmoAbomination/gmoAbomination.png");

    public RenderAbomination(ModelAbomination par1ModelBase, float par2)
    {
        // Note: Render types for simple things can be EntityLiving or extend
        // existing mobs.
        super(par1ModelBase, par2);
        this.setRenderPassModel(new ModelAbomination());
    }

    @Override
    protected float getDeathMaxRotation(EntityLivingBase par1EntityLivingBase)
    {
        return this.setSpiderDeathMaxRotation((EntityAbomination) par1EntityLivingBase);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return this.getSpiderTextures((EntityAbomination) par1Entity);
    }

    protected ResourceLocation getSpiderTextures(EntityAbomination par1EntitySpider)
    {
        return mobTexture;
    }

    protected float setSpiderDeathMaxRotation(EntityAbomination par1EntitySpider)
    {
        return 180.0F;
    }
}
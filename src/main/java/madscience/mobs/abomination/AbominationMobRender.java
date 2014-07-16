package madscience.mobs.abomination;

import madscience.MadMobs;
import madscience.factory.mod.MadMod;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class AbominationMobRender extends RenderLiving
{
    // Refers to location in asset folder with other textures and sounds.
    private static final ResourceLocation mobTexture = new ResourceLocation(MadMod.ID, "models/" + MadMobs.GMO_ABOMINATION_INTERNALNAME + "/" + MadMobs.GMO_ABOMINATION_INTERNALNAME + ".png");

    public AbominationMobRender(AbominationMobModel par1ModelBase, float par2)
    {
        // Note: Render types for simple things can be EntityLiving or extend
        // existing mobs.
        super(par1ModelBase, par2);
        this.setRenderPassModel(new AbominationMobModel());
    }

    @Override
    protected float getDeathMaxRotation(EntityLivingBase par1EntityLivingBase)
    {
        return this.setSpiderDeathMaxRotation((AbominationMobEntity) par1EntityLivingBase);
    }

    /** Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture. */
    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return this.getSpiderTextures((AbominationMobEntity) par1Entity);
    }

    private ResourceLocation getSpiderTextures(AbominationMobEntity par1EntitySpider)
    {
        return mobTexture;
    }

    private float setSpiderDeathMaxRotation(AbominationMobEntity par1EntitySpider)
    {
        return 180.0F;
    }
}
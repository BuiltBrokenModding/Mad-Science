package madscience.mobs.werewolf;

import madscience.MadMobs;
import madscience.factory.mod.MadMod;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class WerewolfMobRender extends RenderLiving
{
    // Refers to location in asset folder with other textures and sounds.
    private static final ResourceLocation mobTexture = new ResourceLocation(MadMod.ID, "models/" + MadMobs.GMO_WEREWOLF_INTERNALNAME + "/" + MadMobs.GMO_WEREWOLF_INTERNALNAME + ".png");

    public WerewolfMobRender(WerewolfMobModel par1ModelBase, float par2)
    {
        // Note: Render types for simple things can be EntityLiving or extend
        // existing mobs.
        super(par1ModelBase, par2);
    }

    @Override
    public void doRender(Entity entity, double d, double d1, double d2, float f, float f1)
    {
        doRenderLiving((WerewolfMobEntity) entity, d, d1, d2, f, f1);
    }

    @Override
    public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2, float f, float f1)
    {
        super.doRenderLiving(entityliving, d, d1, d2, f, f1);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return mobTexture;
    }
}
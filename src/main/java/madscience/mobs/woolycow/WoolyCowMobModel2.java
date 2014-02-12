package madscience.mobs.woolycow;

import java.util.Random;

import net.minecraft.client.model.ModelQuadruped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class WoolyCowMobModel2 extends ModelQuadruped
{
    private float headRotationAngleX;

    public WoolyCowMobModel2()
    {
        super(12, 0.0F);

        textureWidth = 64;
        textureHeight = 32;

        this.head = new ModelRenderer(this, 0, 0);
        this.head.addBox(-4.0F, -4.0F, -6.0F, 8, 8, 6, 0.1F);
        this.head.setRotationPoint(0.0F, 4.0F, -8.0F);
        this.head.setTextureOffset(22, 0).addBox(-5.0F, -5.0F, -4.0F, 1, 3, 1, 0.0F);
        this.head.setTextureOffset(22, 0).addBox(4.0F, -5.0F, -4.0F, 1, 3, 1, 0.0F);

        this.body = new ModelRenderer(this, 18, 4);
        this.body.addBox(-6.0F, -10.0F, -7.0F, 12, 18, 10, 1.75F);
        this.body.setRotationPoint(0.0F, 5.0F, 2.0F);
        this.body.setTextureOffset(52, 0).addBox(-2.0F, 2.0F, -8.0F, 4, 6, 1);

        --this.leg1.rotationPointX;
        ++this.leg2.rotationPointX;
        this.leg1.rotationPointZ += 0.0F;
        this.leg2.rotationPointZ += 0.0F;
        --this.leg3.rotationPointX;
        ++this.leg4.rotationPointX;
        --this.leg3.rotationPointZ;
        --this.leg4.rotationPointZ;
        this.field_78151_h += 2.0F;
    }

    @Override
    public ModelRenderer getRandomModelBox(Random par1Random)
    {
        return (ModelRenderer) this.boxList.get(par1Random.nextInt(this.boxList.size()));
    }

    /** Used for easily adding entity-dependent animations. The second and third float params here are the same second and third as in the setRotationAngles method. */
    @Override
    public void setLivingAnimations(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4)
    {
        super.setLivingAnimations(par1EntityLivingBase, par2, par3, par4);
        this.head.rotationPointY = 6.0F + ((WoolyCowMobEntity) par1EntityLivingBase).getHeadRotationY(par4) * 9.0F;
        this.headRotationAngleX = ((WoolyCowMobEntity) par1EntityLivingBase).getHeadRotationX(par4);
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
    {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.head.rotateAngleX = this.headRotationAngleX;
    }
}

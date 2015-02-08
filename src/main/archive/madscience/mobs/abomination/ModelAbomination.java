package madscience.content.abomination;

import java.util.Random;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.MathHelper;

public class ModelAbomination extends ModelBase
{
    ModelRenderer Body;
    // fields
    ModelRenderer Head;
    ModelRenderer Leg1;
    ModelRenderer Leg2;
    ModelRenderer Leg3;
    ModelRenderer Leg4;
    ModelRenderer Leg5;
    ModelRenderer Leg6;
    ModelRenderer Leg7;
    ModelRenderer Leg8;
    ModelRenderer RearEnd;

    public ModelAbomination()
    {
        float f = 0.0F;
        byte b0 = 15;

        textureWidth = 64;
        textureHeight = 32;

        Head = new ModelRenderer(this, 32, 17);
        Head.addBox(-2F, -3F, -4F, 4, 3, 12);
        Head.setRotationPoint(0F, 19F, -3F);
        Head.setTextureSize(64, 32);
        Head.mirror = true;
        setRotation(Head, 0.4886922F, 0F, 0F);
        Body = new ModelRenderer(this, 0, 7);
        Body.addBox(-3.466667F, -3F, -3F, 7, 3, 6);
        Body.setRotationPoint(0F, 21F, 0F);
        Body.setTextureSize(64, 32);
        Body.mirror = true;
        setRotation(Body, 0F, 0F, 0F);
        RearEnd = new ModelRenderer(this, 0, 17);
        RearEnd.addBox(-1.5F, -2F, -7F, 3, 2, 13);
        RearEnd.setRotationPoint(0F, 21F, 9F);
        RearEnd.setTextureSize(64, 32);
        RearEnd.mirror = true;
        setRotation(RearEnd, -0.0872665F, 0F, 0F);
        Leg8 = new ModelRenderer(this, 34, 14);
        Leg8.addBox(-1F, -1F, -1F, 11, 1, 1);
        Leg8.setRotationPoint(4F, 20F, -1F);
        Leg8.setTextureSize(64, 32);
        Leg8.mirror = true;
        setRotation(Leg8, 0F, 0.5759587F, 0.1919862F);
        Leg6 = new ModelRenderer(this, 34, 10);
        Leg6.addBox(-1F, -1F, -1F, 9, 1, 1);
        Leg6.setRotationPoint(4F, 20F, 0F);
        Leg6.setTextureSize(64, 32);
        Leg6.mirror = true;
        setRotation(Leg6, 0F, 0.2792527F, 0.1919862F);
        Leg4 = new ModelRenderer(this, 34, 6);
        Leg4.addBox(-1F, -1F, -1F, 4, 1, 1);
        Leg4.setRotationPoint(2F, 18F, 4F);
        Leg4.setTextureSize(64, 32);
        Leg4.mirror = true;
        setRotation(Leg4, 0F, -0.7330383F, -0.7330383F);
        Leg2 = new ModelRenderer(this, 34, 2);
        Leg2.addBox(-1F, -1F, -1F, 6, 1, 1);
        Leg2.setRotationPoint(2F, 18F, 2F);
        Leg2.setTextureSize(64, 32);
        Leg2.mirror = true;
        setRotation(Leg2, 0F, -0.7330383F, -0.7330383F);
        Leg7 = new ModelRenderer(this, 34, 12);
        Leg7.addBox(-10F, -1F, -1F, 11, 1, 1);
        Leg7.setRotationPoint(-4F, 20F, -1F);
        Leg7.setTextureSize(64, 32);
        Leg7.mirror = true;
        setRotation(Leg7, 0F, -0.5759587F, -0.1919862F);
        Leg5 = new ModelRenderer(this, 34, 8);
        Leg5.addBox(-8F, -1F, -1F, 9, 1, 1);
        Leg5.setRotationPoint(-4F, 20F, 0F);
        Leg5.setTextureSize(64, 32);
        Leg5.mirror = true;
        setRotation(Leg5, 0F, -0.2792527F, -0.1919862F);
        Leg3 = new ModelRenderer(this, 34, 4);
        Leg3.addBox(-3F, -1F, -1F, 4, 1, 1);
        Leg3.setRotationPoint(-2F, 18F, 4F);
        Leg3.setTextureSize(64, 32);
        Leg3.mirror = true;
        setRotation(Leg3, 0F, 0.7330383F, 0.7330383F);
        Leg1 = new ModelRenderer(this, 34, 0);
        Leg1.addBox(-5F, -1F, -1F, 6, 1, 1);
        Leg1.setRotationPoint(-2F, 18F, 2F);
        Leg1.setTextureSize(64, 32);
        Leg1.mirror = true;
        setRotation(Leg1, 0F, 0.7330383F, 0.7330383F);
    }

    @Override
    public ModelRenderer getRandomModelBox(Random par1Random)
    {
        return (ModelRenderer) this.boxList.get(par1Random.nextInt(this.boxList.size()));
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        Head.render(f5);
        Body.render(f5);
        RearEnd.render(f5);
        Leg8.render(f5);
        Leg6.render(f5);
        Leg4.render(f5);
        Leg2.render(f5);
        Leg7.render(f5);
        Leg5.render(f5);
        Leg3.render(f5);
        Leg1.render(f5);
    }

    /** Used for easily adding entity-dependent animations. The second and third float params here are the same second and third as in the setRotationAngles method. */
    public void setLivingAnimations(EntityLiving par1EntityLiving, float par2, float par3, float par4)
    {
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
    {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.Head.rotateAngleY = f3 / (180F / (float) Math.PI);
        this.Head.rotateAngleX = f4 / (180F / (float) Math.PI);
        float f6 = ((float) Math.PI / 4F);
        this.Leg1.rotateAngleZ = -f6;
        this.Leg2.rotateAngleZ = f6;
        this.Leg3.rotateAngleZ = -f6 * 0.74F;
        this.Leg4.rotateAngleZ = f6 * 0.74F;
        this.Leg5.rotateAngleZ = -f6 * 0.74F;
        this.Leg6.rotateAngleZ = f6 * 0.74F;
        this.Leg7.rotateAngleZ = -f6;
        this.Leg8.rotateAngleZ = f6;
        float f7 = -0.0F;
        float f8 = 0.3926991F;
        this.Leg1.rotateAngleY = f8 * 2.0F + f7;
        this.Leg2.rotateAngleY = -f8 * 2.0F - f7;
        this.Leg3.rotateAngleY = f8 * 1.0F + f7;
        this.Leg4.rotateAngleY = -f8 * 1.0F - f7;
        this.Leg5.rotateAngleY = -f8 * 1.0F + f7;
        this.Leg6.rotateAngleY = f8 * 1.0F - f7;
        this.Leg7.rotateAngleY = -f8 * 2.0F + f7;
        this.Leg8.rotateAngleY = f8 * 2.0F - f7;
        float f9 = -(MathHelper.cos(f * 0.6662F * 2.0F + 0.0F) * 0.4F) * f1;
        float f10 = -(MathHelper.cos(f * 0.6662F * 2.0F + (float) Math.PI) * 0.4F) * f1;
        float f11 = -(MathHelper.cos(f * 0.6662F * 2.0F + ((float) Math.PI / 2F)) * 0.4F) * f1;
        float f12 = -(MathHelper.cos(f * 0.6662F * 2.0F + ((float) Math.PI * 3F / 2F)) * 0.4F) * f1;
        float f13 = Math.abs(MathHelper.sin(f * 0.6662F + 0.0F) * 0.4F) * f1;
        float f14 = Math.abs(MathHelper.sin(f * 0.6662F + (float) Math.PI) * 0.4F) * f1;
        float f15 = Math.abs(MathHelper.sin(f * 0.6662F + ((float) Math.PI / 2F)) * 0.4F) * f1;
        float f16 = Math.abs(MathHelper.sin(f * 0.6662F + ((float) Math.PI * 3F / 2F)) * 0.4F) * f1;
        this.Leg1.rotateAngleY += f9;
        this.Leg2.rotateAngleY += -f9;
        this.Leg3.rotateAngleY += f10;
        this.Leg4.rotateAngleY += -f10;
        this.Leg5.rotateAngleY += f11;
        this.Leg6.rotateAngleY += -f11;
        this.Leg7.rotateAngleY += f12;
        this.Leg8.rotateAngleY += -f12;
        this.Leg1.rotateAngleZ += f13;
        this.Leg2.rotateAngleZ += -f13;
        this.Leg3.rotateAngleZ += f14;
        this.Leg4.rotateAngleZ += -f14;
        this.Leg5.rotateAngleZ += f15;
        this.Leg6.rotateAngleZ += -f15;
        this.Leg7.rotateAngleZ += f16;
        this.Leg8.rotateAngleZ += -f16;
    }
}

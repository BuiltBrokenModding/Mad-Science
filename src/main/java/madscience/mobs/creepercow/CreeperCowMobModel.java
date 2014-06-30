package madscience.mobs.creepercow;

import java.util.Random;

import net.minecraft.client.model.ModelQuadruped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.MathHelper;

public class CreeperCowMobModel extends ModelQuadruped
{
    private ModelRenderer field_78133_b;
    // fields
    private ModelRenderer horn1;
    private ModelRenderer horn2;

    private ModelRenderer udders;

    public CreeperCowMobModel()
    {
        this(0.0F);
    }

    CreeperCowMobModel(float par1)
    {
        super(12, 0.0F);

        byte b0 = 4;
        textureWidth = 64;
        textureHeight = 32;

        horn1 = new ModelRenderer(this, 53, 8);
        horn1.addBox(-4F, -6F, -4F, 1, 4, 1);
        horn1.setRotationPoint(0F, 3F, -7F);
        horn1.setTextureSize(64, 32);
        horn1.mirror = true;
        setRotation(horn1, 0F, 0F, 0F);
        horn2 = new ModelRenderer(this, 53, 8);
        horn2.addBox(3F, -6F, -4F, 1, 4, 1);
        horn2.setRotationPoint(0F, 3F, -7F);
        horn2.setTextureSize(64, 32);
        horn2.mirror = true;
        setRotation(horn2, 0F, 0F, 0F);
        udders = new ModelRenderer(this, 52, 0);
        udders.addBox(-2F, -3F, 0F, 4, 6, 2);
        udders.setRotationPoint(0F, 14F, 6F);
        udders.setTextureSize(64, 32);
        udders.mirror = true;
        setRotation(udders, 1.570796F, 0F, 0F);

        this.head = new ModelRenderer(this, 0, 0);
        this.head.addBox(-4.0F, -4.0F, -6.0F, 8, 8, 6, 0.0F);
        this.head.setRotationPoint(0.0F, 4.0F, -8.0F);
        this.head.setTextureOffset(22, 0).addBox(-5.0F, -5.0F, -4.0F, 1, 3, 1, 0.0F);
        this.head.setTextureOffset(22, 0).addBox(4.0F, -5.0F, -4.0F, 1, 3, 1, 0.0F);

        this.field_78133_b = new ModelRenderer(this, 32, 0);
        this.field_78133_b.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, par1 + 0.5F);
        this.field_78133_b.setRotationPoint(0.0F, b0, 0.0F);

        this.body = new ModelRenderer(this, 18, 4);
        this.body.addBox(-6.0F, -10.0F, -7.0F, 12, 18, 10, 0.0F);
        this.body.setRotationPoint(0.0F, 5.0F, 2.0F);
        this.body.setTextureOffset(52, 0).addBox(-2.0F, 2.0F, -8.0F, 4, 6, 1);

        /* this.leg1 = new ModelRenderer(this, 0, 16); this.leg1.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, par1); this.leg1.setRotationPoint(-2.0F, (float)(12 + b0), 4.0F); this.leg2 = new ModelRenderer(this, 0, 16); this.leg2.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4,
         * par1); this.leg2.setRotationPoint(2.0F, (float)(12 + b0), 4.0F); this.leg3 = new ModelRenderer(this, 0, 16); this.leg3.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, par1); this.leg3.setRotationPoint(-2.0F, (float)(12 + b0), -4.0F); this.leg4 = new
         * ModelRenderer(this, 0, 16); this.leg4.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, par1); this.leg4.setRotationPoint(2.0F, (float)(12 + b0), -4.0F); */

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

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        horn1.render(f5);
        horn2.render(f5);
        udders.render(f5);
        this.head.render(f5);
        this.body.render(f5);
        this.leg1.render(f5);
        this.leg2.render(f5);
        this.leg3.render(f5);
        this.leg4.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    @Override
    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
    {
        super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
        this.head.rotateAngleY = par4 / (180F / (float) Math.PI);
        this.head.rotateAngleX = par5 / (180F / (float) Math.PI);
        this.leg1.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 1.4F * par2;
        this.leg2.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float) Math.PI) * 1.4F * par2;
        this.leg3.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float) Math.PI) * 1.4F * par2;
        this.leg4.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 1.4F * par2;
    }
}

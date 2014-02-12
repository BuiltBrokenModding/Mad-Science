package madscience.tileentities.meatcube;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class MeatcubeModel extends ModelBase
{
    // fields
    ModelRenderer Bone;
    ModelRenderer Meat0;
    ModelRenderer Meat1;
    ModelRenderer Meat2;
    ModelRenderer Meat3;
    ModelRenderer Meat4;
    ModelRenderer Meat5;
    ModelRenderer Meat6;
    ModelRenderer Meat7;
    ModelRenderer Meat8;
    ModelRenderer Meat9;
    ModelRenderer Meat10;
    ModelRenderer Meat11;
    ModelRenderer Meat12;
    ModelRenderer Meat13;
    ModelRenderer Meat14;

    public MeatcubeModel()
    {
        textureWidth = 128;
        textureHeight = 32;

        Bone = new ModelRenderer(this, 64, 12);
        Bone.addBox(0F, 0F, 0F, 4, 16, 4);
        Bone.setRotationPoint(-2F, 8F, -2F);
        Bone.setTextureSize(128, 32);
        Bone.mirror = true;
        setRotation(Bone, 0F, 0F, 0F);
        Meat0 = new ModelRenderer(this, 0, 15);
        Meat0.addBox(0F, 0F, 0F, 16, 1, 16);
        Meat0.setRotationPoint(-8F, 23F, -8F);
        Meat0.setTextureSize(128, 32);
        Meat0.mirror = true;
        setRotation(Meat0, 0F, 0F, 0F);
        Meat1 = new ModelRenderer(this, 0, 14);
        Meat1.addBox(-8F, -16F, -8F, 16, 1, 16);
        Meat1.setRotationPoint(0F, 38F, 0F);
        Meat1.setTextureSize(128, 32);
        Meat1.mirror = true;
        setRotation(Meat1, 0F, 0F, 0F);
        Meat2 = new ModelRenderer(this, 0, 13);
        Meat2.addBox(-8F, -16F, -8F, 16, 1, 16);
        Meat2.setRotationPoint(0F, 37F, 0F);
        Meat2.setTextureSize(128, 32);
        Meat2.mirror = true;
        setRotation(Meat2, 0F, 0F, 0F);
        Meat3 = new ModelRenderer(this, 0, 12);
        Meat3.addBox(-8F, -16F, -8F, 16, 1, 16);
        Meat3.setRotationPoint(0F, 36F, 0F);
        Meat3.setTextureSize(128, 32);
        Meat3.mirror = true;
        setRotation(Meat3, 0F, 0F, 0F);
        Meat4 = new ModelRenderer(this, 0, 11);
        Meat4.addBox(-8F, -16F, -8F, 16, 1, 16);
        Meat4.setRotationPoint(0F, 35F, 0F);
        Meat4.setTextureSize(128, 32);
        Meat4.mirror = true;
        setRotation(Meat4, 0F, 0F, 0F);
        Meat5 = new ModelRenderer(this, 0, 10);
        Meat5.addBox(-8F, -16F, -8F, 16, 1, 16);
        Meat5.setRotationPoint(0F, 34F, 0F);
        Meat5.setTextureSize(128, 32);
        Meat5.mirror = true;
        setRotation(Meat5, 0F, 0F, 0F);
        Meat6 = new ModelRenderer(this, 0, 9);
        Meat6.addBox(-8F, -16F, -8F, 16, 1, 16);
        Meat6.setRotationPoint(0F, 33F, 0F);
        Meat6.setTextureSize(128, 32);
        Meat6.mirror = true;
        setRotation(Meat6, 0F, 0F, 0F);
        Meat7 = new ModelRenderer(this, 0, 8);
        Meat7.addBox(-8F, -16F, -8F, 16, 1, 16);
        Meat7.setRotationPoint(0F, 32F, 0F);
        Meat7.setTextureSize(128, 32);
        Meat7.mirror = true;
        setRotation(Meat7, 0F, 0F, 0F);
        Meat8 = new ModelRenderer(this, 0, 7);
        Meat8.addBox(-8F, -16F, -8F, 16, 1, 16);
        Meat8.setRotationPoint(0F, 31F, 0F);
        Meat8.setTextureSize(128, 32);
        Meat8.mirror = true;
        setRotation(Meat8, 0F, 0F, 0F);
        Meat9 = new ModelRenderer(this, 0, 6);
        Meat9.addBox(-8F, -16F, -8F, 16, 1, 16);
        Meat9.setRotationPoint(0F, 30F, 0F);
        Meat9.setTextureSize(128, 32);
        Meat9.mirror = true;
        setRotation(Meat9, 0F, 0F, 0F);
        Meat10 = new ModelRenderer(this, 0, 5);
        Meat10.addBox(-8F, -16F, -8F, 16, 1, 16);
        Meat10.setRotationPoint(0F, 29F, 0F);
        Meat10.setTextureSize(128, 32);
        Meat10.mirror = true;
        setRotation(Meat10, 0F, 0F, 0F);
        Meat11 = new ModelRenderer(this, 0, 4);
        Meat11.addBox(-8F, -16F, -8F, 16, 1, 16);
        Meat11.setRotationPoint(0F, 28F, 0F);
        Meat11.setTextureSize(128, 32);
        Meat11.mirror = true;
        setRotation(Meat11, 0F, 0F, 0F);
        Meat12 = new ModelRenderer(this, 0, 3);
        Meat12.addBox(-8F, -16F, -8F, 16, 1, 16);
        Meat12.setRotationPoint(0F, 27F, 0F);
        Meat12.setTextureSize(128, 32);
        Meat12.mirror = true;
        setRotation(Meat12, 0F, 0F, 0F);
        Meat13 = new ModelRenderer(this, 0, 3);
        Meat13.addBox(-8F, -16F, -8F, 16, 1, 16);
        Meat13.setRotationPoint(0F, 26F, 0F);
        Meat13.setTextureSize(128, 32);
        Meat13.mirror = true;
        setRotation(Meat13, 0F, 0F, 0F);
        Meat14 = new ModelRenderer(this, 0, 2);
        Meat14.addBox(-8F, -16F, -8F, 16, 1, 16);
        Meat14.setRotationPoint(0F, 25F, 0F);
        Meat14.setTextureSize(128, 32);
        Meat14.mirror = true;
        setRotation(Meat14, 0F, 0F, 0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        Bone.render(f5);
        Meat0.render(f5);
        Meat1.render(f5);
        Meat2.render(f5);
        Meat3.render(f5);
        Meat4.render(f5);
        Meat5.render(f5);
        Meat6.render(f5);
        Meat7.render(f5);
        Meat8.render(f5);
        Meat9.render(f5);
        Meat10.render(f5);
        Meat11.render(f5);
        Meat12.render(f5);
        Meat13.render(f5);
        Meat14.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    // Note: Model exporter messes up this line make sure to add entity to end
    // of both.
    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
    {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }
}

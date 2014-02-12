package madscience.tileentities.incubator;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class IncubatorModel extends ModelBase
{
    // fields
    ModelRenderer Foot4;
    ModelRenderer Foot2;
    ModelRenderer Foot3;
    ModelRenderer Top;
    ModelRenderer Foot1;
    ModelRenderer LeftRight;
    ModelRenderer Bottom;
    ModelRenderer FrontRight;
    ModelRenderer Left;
    ModelRenderer Egg;
    ModelRenderer Glass;

    public IncubatorModel()
    {
        textureWidth = 64;
        textureHeight = 32;

        Foot4 = new ModelRenderer(this, 12, 22);
        Foot4.addBox(0F, 0F, 0F, 1, 1, 1);
        Foot4.setRotationPoint(6F, 23F, 6F);
        Foot4.setTextureSize(64, 32);
        Foot4.mirror = true;
        setRotation(Foot4, 0F, 0F, 0F);
        Foot2 = new ModelRenderer(this, 12, 22);
        Foot2.addBox(0F, 0F, 0F, 1, 1, 1);
        Foot2.setRotationPoint(-7F, 23F, -7F);
        Foot2.setTextureSize(64, 32);
        Foot2.mirror = true;
        setRotation(Foot2, 0F, 0F, 0F);
        Foot3 = new ModelRenderer(this, 12, 22);
        Foot3.addBox(0F, 0F, 0F, 1, 1, 1);
        Foot3.setRotationPoint(-7F, 23F, 6F);
        Foot3.setTextureSize(64, 32);
        Foot3.mirror = true;
        setRotation(Foot3, 0F, 0F, 0F);
        Top = new ModelRenderer(this, 0, 0);
        Top.addBox(0F, 0F, 0F, 16, 4, 16);
        Top.setRotationPoint(-8F, 9F, -8F);
        Top.setTextureSize(64, 32);
        Top.mirror = true;
        setRotation(Top, 0F, 0F, 0F);
        Foot1 = new ModelRenderer(this, 12, 22);
        Foot1.addBox(0F, 0F, 0F, 1, 1, 1);
        Foot1.setRotationPoint(6F, 23F, -7F);
        Foot1.setTextureSize(64, 32);
        Foot1.mirror = true;
        setRotation(Foot1, 0F, 0F, 0F);
        LeftRight = new ModelRenderer(this, 0, 20);
        LeftRight.addBox(0F, 0F, 0F, 3, 9, 3);
        LeftRight.setRotationPoint(-8F, 13F, -8F);
        LeftRight.setTextureSize(64, 32);
        LeftRight.mirror = true;
        setRotation(LeftRight, 0F, 0F, 0F);
        Bottom = new ModelRenderer(this, 0, 0);
        Bottom.addBox(0F, 0F, 0F, 16, 1, 16);
        Bottom.setRotationPoint(-8F, 22F, -8F);
        Bottom.setTextureSize(64, 32);
        Bottom.mirror = true;
        setRotation(Bottom, 0F, 0F, 0F);
        FrontRight = new ModelRenderer(this, 0, 20);
        FrontRight.addBox(0F, 0F, 0F, 3, 9, 3);
        FrontRight.setRotationPoint(5F, 13F, -8F);
        FrontRight.setTextureSize(64, 32);
        FrontRight.mirror = true;
        setRotation(FrontRight, 0F, 0F, 0F);
        Left = new ModelRenderer(this, 3, 0);
        Left.addBox(0F, 0F, 0F, 16, 9, 13);
        Left.setRotationPoint(-8F, 13F, -5F);
        Left.setTextureSize(64, 32);
        Left.mirror = true;
        setRotation(Left, 0F, 0F, 0F);
        Egg = new ModelRenderer(this, 12, 24);
        Egg.addBox(0F, 0F, 0F, 8, 7, 1);
        Egg.setRotationPoint(-4F, 14F, -6F);
        Egg.setTextureSize(64, 32);
        Egg.mirror = true;
        setRotation(Egg, 0F, 0F, 0F);
        Glass = new ModelRenderer(this, 42, 22);
        Glass.addBox(0F, 0F, 0F, 10, 9, 1);
        Glass.setRotationPoint(-5F, 13F, -8F);
        Glass.setTextureSize(64, 32);
        Glass.mirror = true;
        setRotation(Glass, 0F, 0F, 0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        Foot4.render(f5);
        Foot2.render(f5);
        Foot3.render(f5);
        Top.render(f5);
        Foot1.render(f5);
        LeftRight.render(f5);
        Bottom.render(f5);
        FrontRight.render(f5);
        Left.render(f5);
        Egg.render(f5);
        Glass.render(f5);
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

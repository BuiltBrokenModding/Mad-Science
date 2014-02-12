package madscience.tileentities.dnaextractor;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class DNAExtractorModel extends ModelBase
{
    // fields
    ModelRenderer Foot4;
    ModelRenderer Foot2;
    ModelRenderer Foot3;
    ModelRenderer Top;
    ModelRenderer Foot1;
    ModelRenderer Middle;
    ModelRenderer Base;

    public DNAExtractorModel()
    {
        textureWidth = 64;
        textureHeight = 32;

        Foot4 = new ModelRenderer(this, 0, 30);
        Foot4.addBox(0F, 0F, 0F, 1, 1, 1);
        Foot4.setRotationPoint(6F, 23F, 6F);
        Foot4.setTextureSize(64, 32);
        Foot4.mirror = true;
        setRotation(Foot4, 0F, 0F, 0F);
        Foot2 = new ModelRenderer(this, 0, 30);
        Foot2.addBox(0F, 0F, 0F, 1, 1, 1);
        Foot2.setRotationPoint(-7F, 23F, -7F);
        Foot2.setTextureSize(64, 32);
        Foot2.mirror = true;
        setRotation(Foot2, 0F, 0F, 0F);
        Foot3 = new ModelRenderer(this, 0, 30);
        Foot3.addBox(0F, 0F, 0F, 1, 1, 1);
        Foot3.setRotationPoint(-7F, 23F, 6F);
        Foot3.setTextureSize(64, 32);
        Foot3.mirror = true;
        setRotation(Foot3, 0F, 0F, 0F);
        Top = new ModelRenderer(this, 0, 3);
        Top.addBox(0F, 0F, 0F, 16, 9, 16);
        Top.setRotationPoint(-8F, 9F, -8F);
        Top.setTextureSize(64, 32);
        Top.mirror = true;
        setRotation(Top, 0F, 0F, 0F);
        Foot1 = new ModelRenderer(this, 0, 30);
        Foot1.addBox(0F, 0F, 0F, 1, 1, 1);
        Foot1.setRotationPoint(6F, 23F, -7F);
        Foot1.setTextureSize(64, 32);
        Foot1.mirror = true;
        setRotation(Foot1, 0F, 0F, 0F);
        Middle = new ModelRenderer(this, 0, 0);
        Middle.addBox(0F, 0F, 0F, 16, 4, 15);
        Middle.setRotationPoint(-8F, 18F, -7F);
        Middle.setTextureSize(64, 32);
        Middle.mirror = true;
        setRotation(Middle, 0F, 0F, 0F);
        Base = new ModelRenderer(this, 0, 13);
        Base.addBox(0F, 0F, 0F, 16, 1, 16);
        Base.setRotationPoint(-8F, 22F, -8F);
        Base.setTextureSize(64, 32);
        Base.mirror = true;
        setRotation(Base, 0F, 0F, 0F);
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
        Middle.render(f5);
        Base.render(f5);
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

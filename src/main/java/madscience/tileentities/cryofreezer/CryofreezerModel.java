package madscience.tileentities.cryofreezer;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class CryofreezerModel extends ModelBase
{
    // fields
    ModelRenderer Base;
    ModelRenderer Leg1;
    ModelRenderer Leg2;
    ModelRenderer Leg3;
    ModelRenderer Leg4;

    public CryofreezerModel()
    {
        textureWidth = 64;
        textureHeight = 32;

        Base = new ModelRenderer(this, 0, 0);
        Base.addBox(0F, 0F, 0F, 16, 15, 16);
        Base.setRotationPoint(-8F, 8F, -8F);
        Base.setTextureSize(64, 32);
        Base.mirror = true;
        setRotation(Base, 0F, 0F, 0F);
        Leg1 = new ModelRenderer(this, 0, 30);
        Leg1.addBox(0F, 0F, 0F, 1, 1, 1);
        Leg1.setRotationPoint(-8F, 23F, -8F);
        Leg1.setTextureSize(64, 32);
        Leg1.mirror = true;
        setRotation(Leg1, 0F, 0F, 0F);
        Leg2 = new ModelRenderer(this, 0, 30);
        Leg2.addBox(0F, 0F, 0F, 1, 1, 1);
        Leg2.setRotationPoint(7F, 23F, 7F);
        Leg2.setTextureSize(64, 32);
        Leg2.mirror = true;
        setRotation(Leg2, 0F, 0F, 0F);
        Leg3 = new ModelRenderer(this, 0, 30);
        Leg3.addBox(0F, 0F, 0F, 1, 1, 1);
        Leg3.setRotationPoint(7F, 23F, -8F);
        Leg3.setTextureSize(64, 32);
        Leg3.mirror = true;
        setRotation(Leg3, 0F, 0F, 0F);
        Leg4 = new ModelRenderer(this, 0, 30);
        Leg4.addBox(0F, 0F, 0F, 1, 1, 1);
        Leg4.setRotationPoint(-8F, 23F, 7F);
        Leg4.setTextureSize(64, 32);
        Leg4.mirror = true;
        setRotation(Leg4, 0F, 0F, 0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        Base.render(f5);
        Leg1.render(f5);
        Leg2.render(f5);
        Leg3.render(f5);
        Leg4.render(f5);
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

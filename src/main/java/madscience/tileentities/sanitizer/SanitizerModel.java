package madscience.tileentities.sanitizer;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class SanitizerModel extends ModelBase
{
    // fields
    ModelRenderer Bottom;
    ModelRenderer Top;
    ModelRenderer Middle;

    public SanitizerModel()
    {
        textureWidth = 64;
        textureHeight = 32;

        Bottom = new ModelRenderer(this, 0, 0);
        Bottom.addBox(0F, 0F, 0F, 16, 1, 16);
        Bottom.setRotationPoint(-8F, 23F, -8F);
        Bottom.setTextureSize(64, 32);
        Bottom.mirror = true;
        setRotation(Bottom, 0F, 0F, 0F);
        Top = new ModelRenderer(this, 0, 0);
        Top.addBox(0F, 0F, 0F, 16, 12, 16);
        Top.setRotationPoint(-8F, 8F, -8F);
        Top.setTextureSize(64, 32);
        Top.mirror = true;
        setRotation(Top, 0F, 0F, 0F);
        Middle = new ModelRenderer(this, 11, 23);
        Middle.addBox(0F, 0F, 0F, 16, 3, 5);
        Middle.setRotationPoint(-8F, 20F, 3F);
        Middle.setTextureSize(64, 32);
        Middle.mirror = true;
        setRotation(Middle, 0F, 0F, 0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        Bottom.render(f5);
        Top.render(f5);
        Middle.render(f5);
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

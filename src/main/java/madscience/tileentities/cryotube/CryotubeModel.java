package madscience.tileentities.cryotube;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class CryotubeModel extends ModelBase
{
    // fields
    ModelRenderer Top;
    ModelRenderer Base;
    ModelRenderer Tube1;
    ModelRenderer Tube2;
    ModelRenderer Tube3;
    ModelRenderer Tube4;

    public CryotubeModel()
    {
        textureWidth = 128;
        textureHeight = 64;

        Top = new ModelRenderer(this, 0, 0);
        Top.addBox(0F, 0F, -16F, 16, 9, 16);
        Top.setRotationPoint(-8F, -24F, 8F);
        Top.setTextureSize(128, 64);
        Top.mirror = true;
        setRotation(Top, 0F, 0F, 0F);
        Base = new ModelRenderer(this, 64, 42);
        Base.addBox(0F, -2F, 0F, 16, 2, 16);
        Base.setRotationPoint(-8F, 24F, -8F);
        Base.setTextureSize(128, 64);
        Base.mirror = true;
        setRotation(Base, 0F, 0F, 0F);
        Tube1 = new ModelRenderer(this, 0, 15);
        Tube1.addBox(0F, -38F, 0F, 14, 38, 11);
        Tube1.setRotationPoint(-7F, 22F, -4F);
        Tube1.setTextureSize(128, 64);
        Tube1.mirror = true;
        setRotation(Tube1, 0F, 0F, 0F);
        Tube2 = new ModelRenderer(this, 74, -1);
        Tube2.addBox(0F, 0F, 0F, 4, 38, 4);
        Tube2.setRotationPoint(-7F, -16F, -4F);
        Tube2.setTextureSize(128, 64);
        Tube2.mirror = true;
        setRotation(Tube2, 0F, 0.8011061F, 0F);
        Tube3 = new ModelRenderer(this, 90, -1);
        Tube3.addBox(0F, 0F, 0F, 4, 38, 4);
        Tube3.setRotationPoint(1.3F, -16F, -3.95F);
        Tube3.setTextureSize(128, 64);
        Tube3.mirror = true;
        setRotation(Tube3, 0F, 0.8011061F, 0F);
        Tube4 = new ModelRenderer(this, 106, 0);
        Tube4.addBox(0F, 0F, 0F, 8, 38, 3);
        Tube4.setRotationPoint(-4F, -16F, -7F);
        Tube4.setTextureSize(128, 64);
        Tube4.mirror = true;
        setRotation(Tube4, 0F, 0F, 0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        Top.render(f5);
        Base.render(f5);
        Tube1.render(f5);
        Tube2.render(f5);
        Tube3.render(f5);
        Tube4.render(f5);
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

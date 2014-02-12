package madscience.tileentities.sequencer;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class SequencerModel extends ModelBase
{
    // fields
    ModelRenderer Lower;
    ModelRenderer Base;
    ModelRenderer Foot1;
    ModelRenderer Foot2;
    ModelRenderer Foot3;
    ModelRenderer Foot4;
    ModelRenderer Top;
    ModelRenderer DNA1;
    ModelRenderer DNA2;
    ModelRenderer DNA3;
    ModelRenderer DNA4;
    ModelRenderer DNA5;
    ModelRenderer DNA6;
    ModelRenderer DNA7;
    ModelRenderer DNA8;
    ModelRenderer DNA9;
    ModelRenderer DNA10;

    public SequencerModel()
    {
        textureWidth = 64;
        textureHeight = 32;

        Lower = new ModelRenderer(this, 0, 10);
        Lower.addBox(0F, 0F, 0F, 16, 4, 15);
        Lower.setRotationPoint(-8F, 18F, -7F);
        Lower.setTextureSize(64, 32);
        Lower.mirror = true;
        setRotation(Lower, 0F, 0F, 0F);
        Base = new ModelRenderer(this, 0, 13);
        Base.addBox(0F, 0F, 0F, 16, 1, 16);
        Base.setRotationPoint(-8F, 22F, -8F);
        Base.setTextureSize(64, 32);
        Base.mirror = true;
        setRotation(Base, 0F, 0F, 0F);
        Foot1 = new ModelRenderer(this, 0, 30);
        Foot1.addBox(0F, 0F, 0F, 1, 1, 1);
        Foot1.setRotationPoint(6F, 23F, -7F);
        Foot1.setTextureSize(64, 32);
        Foot1.mirror = true;
        setRotation(Foot1, 0F, 0F, 0F);
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
        Foot4 = new ModelRenderer(this, 0, 30);
        Foot4.addBox(0F, 0F, 0F, 1, 1, 1);
        Foot4.setRotationPoint(6F, 23F, 6F);
        Foot4.setTextureSize(64, 32);
        Foot4.mirror = true;
        setRotation(Foot4, 0F, 0F, 0F);
        Top = new ModelRenderer(this, 0, 4);
        Top.addBox(0F, 0F, 0F, 16, 8, 9);
        Top.setRotationPoint(-8F, 10F, -1F);
        Top.setTextureSize(64, 32);
        Top.mirror = true;
        setRotation(Top, 0F, 0F, 0F);
        DNA1 = new ModelRenderer(this, 60, 0);
        DNA1.addBox(0F, 0F, 0F, 1, 2, 1);
        DNA1.setRotationPoint(-7F, 16F, -6F);
        DNA1.setTextureSize(64, 32);
        DNA1.mirror = true;
        setRotation(DNA1, 0F, 0F, 0F);
        DNA2 = new ModelRenderer(this, 60, 0);
        DNA2.addBox(0F, 0F, 0F, 1, 2, 1);
        DNA2.setRotationPoint(-5F, 16F, -6F);
        DNA2.setTextureSize(64, 32);
        DNA2.mirror = true;
        setRotation(DNA2, 0F, 0F, 0F);
        DNA3 = new ModelRenderer(this, 60, 0);
        DNA3.addBox(0F, 0F, 0F, 1, 2, 1);
        DNA3.setRotationPoint(-3F, 16F, -6F);
        DNA3.setTextureSize(64, 32);
        DNA3.mirror = true;
        setRotation(DNA3, 0F, 0F, 0F);
        DNA4 = new ModelRenderer(this, 60, 0);
        DNA4.addBox(0F, 0F, 0F, 1, 2, 1);
        DNA4.setRotationPoint(-1F, 16F, -6F);
        DNA4.setTextureSize(64, 32);
        DNA4.mirror = true;
        setRotation(DNA4, 0F, 0F, 0F);
        DNA5 = new ModelRenderer(this, 60, 0);
        DNA5.addBox(0F, 0F, 0F, 1, 2, 1);
        DNA5.setRotationPoint(1F, 16F, -6F);
        DNA5.setTextureSize(64, 32);
        DNA5.mirror = true;
        setRotation(DNA5, 0F, 0F, 0F);
        DNA6 = new ModelRenderer(this, 60, 0);
        DNA6.addBox(0F, 0F, 0F, 1, 2, 1);
        DNA6.setRotationPoint(-7F, 16F, -4F);
        DNA6.setTextureSize(64, 32);
        DNA6.mirror = true;
        setRotation(DNA6, 0F, 0F, 0F);
        DNA7 = new ModelRenderer(this, 60, 0);
        DNA7.addBox(0F, 0F, 0F, 1, 2, 1);
        DNA7.setRotationPoint(-5F, 16F, -4F);
        DNA7.setTextureSize(64, 32);
        DNA7.mirror = true;
        setRotation(DNA7, 0F, 0F, 0F);
        DNA8 = new ModelRenderer(this, 60, 0);
        DNA8.addBox(0F, 0F, 0F, 1, 2, 1);
        DNA8.setRotationPoint(-3F, 16F, -4F);
        DNA8.setTextureSize(64, 32);
        DNA8.mirror = true;
        setRotation(DNA8, 0F, 0F, 0F);
        DNA9 = new ModelRenderer(this, 60, 0);
        DNA9.addBox(0F, 0F, 0F, 1, 2, 1);
        DNA9.setRotationPoint(-1F, 16F, -4F);
        DNA9.setTextureSize(64, 32);
        DNA9.mirror = true;
        setRotation(DNA9, 0F, 0F, 0F);
        DNA10 = new ModelRenderer(this, 60, 0);
        DNA10.addBox(0F, 0F, 0F, 1, 2, 1);
        DNA10.setRotationPoint(1F, 16F, -4F);
        DNA10.setTextureSize(64, 32);
        DNA10.mirror = true;
        setRotation(DNA10, 0F, 0F, 0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        Lower.render(f5);
        Base.render(f5);
        Foot1.render(f5);
        Foot2.render(f5);
        Foot3.render(f5);
        Foot4.render(f5);
        Top.render(f5);
        DNA1.render(f5);
        DNA2.render(f5);
        DNA3.render(f5);
        DNA4.render(f5);
        DNA5.render(f5);
        DNA6.render(f5);
        DNA7.render(f5);
        DNA8.render(f5);
        DNA9.render(f5);
        DNA10.render(f5);
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

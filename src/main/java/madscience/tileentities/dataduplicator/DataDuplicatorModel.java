package madscience.tileentities.dataduplicator;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class DataDuplicatorModel extends ModelBase
{
    ModelRenderer leg1;
    ModelRenderer leg2;
    ModelRenderer leg3;
    ModelRenderer leg4;
    ModelRenderer base;
    ModelRenderer top;
    ModelRenderer middle;
    ModelRenderer leftdisc;
    ModelRenderer rightdisc;
    ModelRenderer reader;
    ModelRenderer leftpin;
    ModelRenderer rightpin;
    ModelRenderer bottomtape;
    ModelRenderer righttape;
    ModelRenderer lefttape;
    ModelRenderer rightdiscpin;
    ModelRenderer leftdiscpin;

    public DataDuplicatorModel()
    {
        textureWidth = 64;
        textureHeight = 32;

        leg1 = new ModelRenderer(this, 0, 0);
        leg1.addBox(0F, 0F, 0F, 1, 1, 1);
        leg1.setRotationPoint(-8F, 23F, -8F);
        leg1.setTextureSize(64, 32);
        leg1.mirror = true;
        setRotation(leg1, 0F, 0F, 0F);
        leg2 = new ModelRenderer(this, 0, 0);
        leg2.addBox(0F, 0F, 0F, 1, 1, 1);
        leg2.setRotationPoint(-8F, 23F, 7F);
        leg2.setTextureSize(64, 32);
        leg2.mirror = true;
        setRotation(leg2, 0F, 0F, 0F);
        leg3 = new ModelRenderer(this, 0, 0);
        leg3.addBox(0F, 0F, 0F, 1, 1, 1);
        leg3.setRotationPoint(7F, 23F, 7F);
        leg3.setTextureSize(64, 32);
        leg3.mirror = true;
        setRotation(leg3, 0F, 0F, 0F);
        leg4 = new ModelRenderer(this, 0, 0);
        leg4.addBox(0F, 0F, 0F, 1, 1, 1);
        leg4.setRotationPoint(7F, 23F, -8F);
        leg4.setTextureSize(64, 32);
        leg4.mirror = true;
        setRotation(leg4, 0F, 0F, 0F);
        base = new ModelRenderer(this, 0, 3);
        base.addBox(0F, 0F, 0F, 16, 2, 16);
        base.setRotationPoint(-8F, 21F, -8F);
        base.setTextureSize(64, 32);
        base.mirror = true;
        setRotation(base, 0F, 0F, 0F);
        top = new ModelRenderer(this, 0, 3);
        top.addBox(0F, 0F, 0F, 16, 2, 16);
        top.setRotationPoint(-8F, 8F, -8F);
        top.setTextureSize(64, 32);
        top.mirror = true;
        setRotation(top, 0F, 0F, 0F);
        middle = new ModelRenderer(this, 2, 7);
        middle.addBox(0F, 0F, 0F, 16, 11, 14);
        middle.setRotationPoint(-8F, 10F, -6F);
        middle.setTextureSize(64, 32);
        middle.mirror = true;
        setRotation(middle, 0F, 0F, 0F);
        leftdisc = new ModelRenderer(this, 50, 7);
        leftdisc.addBox(0F, 0F, 0F, 6, 6, 1);
        leftdisc.setRotationPoint(-7F, 11F, -7F);
        leftdisc.setTextureSize(64, 32);
        leftdisc.mirror = true;
        setRotation(leftdisc, 0F, 0F, 0F);
        rightdisc = new ModelRenderer(this, 50, 0);
        rightdisc.addBox(0F, 0F, 0F, 6, 6, 1);
        rightdisc.setRotationPoint(1F, 11F, -7F);
        rightdisc.setTextureSize(64, 32);
        rightdisc.mirror = true;
        setRotation(rightdisc, 0F, 0F, 0F);
        reader = new ModelRenderer(this, 4, 0);
        reader.addBox(0F, 0F, 0F, 4, 1, 1);
        reader.setRotationPoint(-2F, 19F, -7F);
        reader.setTextureSize(64, 32);
        reader.mirror = true;
        setRotation(reader, 0F, 0F, 0F);
        leftpin = new ModelRenderer(this, 44, 0);
        leftpin.addBox(0F, 0F, 0F, 1, 1, 2);
        leftpin.setRotationPoint(-7F, 19F, -8F);
        leftpin.setTextureSize(64, 32);
        leftpin.mirror = true;
        setRotation(leftpin, 0F, 0F, 0F);
        rightpin = new ModelRenderer(this, 38, 0);
        rightpin.addBox(0F, 0F, 0F, 1, 1, 2);
        rightpin.setRotationPoint(6F, 19F, -8F);
        rightpin.setTextureSize(64, 32);
        rightpin.mirror = true;
        setRotation(rightpin, 0F, 0F, 0F);
        bottomtape = new ModelRenderer(this, 0, 2);
        bottomtape.addBox(0F, 0F, 0F, 14, 0, 1);
        bottomtape.setRotationPoint(-7F, 20F, -7F);
        bottomtape.setTextureSize(64, 32);
        bottomtape.mirror = true;
        setRotation(bottomtape, 0F, 0F, 0F);
        righttape = new ModelRenderer(this, 0, 25);
        righttape.addBox(0F, 0F, 0F, 0, 3, 1);
        righttape.setRotationPoint(7F, 17F, -7F);
        righttape.setTextureSize(64, 32);
        righttape.mirror = true;
        setRotation(righttape, 0F, 0F, 0F);
        lefttape = new ModelRenderer(this, 0, 21);
        lefttape.addBox(0F, 0F, 0F, 0, 3, 1);
        lefttape.setRotationPoint(-7F, 17F, -7F);
        lefttape.setTextureSize(64, 32);
        lefttape.mirror = true;
        setRotation(lefttape, 0F, 0F, 0F);
        rightdiscpin = new ModelRenderer(this, 34, 0);
        rightdiscpin.addBox(-0.5F, -0.5F, 0.5333334F, 1, 1, 1);
        rightdiscpin.setRotationPoint(4F, 14F, -8F);
        rightdiscpin.setTextureSize(64, 32);
        rightdiscpin.mirror = true;
        setRotation(rightdiscpin, 0F, 0F, 0F);
        leftdiscpin = new ModelRenderer(this, 30, 0);
        leftdiscpin.addBox(-0.5F, -0.5F, 0.5F, 1, 1, 1);
        leftdiscpin.setRotationPoint(-4F, 14F, -8F);
        leftdiscpin.setTextureSize(64, 32);
        leftdiscpin.mirror = true;
        setRotation(leftdiscpin, 0F, 0F, 0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        leg1.render(f5);
        leg2.render(f5);
        leg3.render(f5);
        leg4.render(f5);
        base.render(f5);
        top.render(f5);
        middle.render(f5);
        leftdisc.render(f5);
        rightdisc.render(f5);
        reader.render(f5);
        leftpin.render(f5);
        rightpin.render(f5);
        bottomtape.render(f5);
        righttape.render(f5);
        lefttape.render(f5);
        rightdiscpin.render(f5);
        leftdiscpin.render(f5);
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

package madscience.tileentities.thermosonicbonder;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ThermosonicBonderModel extends ModelBase
{
    // fields
    ModelRenderer peg1;
    ModelRenderer peg2;
    ModelRenderer plug;
    ModelRenderer Base;
    ModelRenderer viewer;
    ModelRenderer aimpoint;
    ModelRenderer laser;
    ModelRenderer Arm2;
    ModelRenderer Arm1;

    public ThermosonicBonderModel()
    {
        textureWidth = 64;
        textureHeight = 32;

        peg1 = new ModelRenderer(this, 0, 22);
        peg1.addBox(0F, 0F, 0F, 2, 8, 2);
        peg1.setRotationPoint(-8F, 16F, -8F);
        peg1.setTextureSize(64, 32);
        peg1.mirror = true;
        setRotation(peg1, 0F, 0F, 0F);
        peg2 = new ModelRenderer(this, 0, 22);
        peg2.addBox(0F, 0F, 0F, 2, 8, 2);
        peg2.setRotationPoint(6F, 16F, -8F);
        peg2.setTextureSize(64, 32);
        peg2.mirror = true;
        setRotation(peg2, 0F, 0F, 0F);
        plug = new ModelRenderer(this, 42, 0);
        plug.addBox(0F, 0F, 0F, 6, 5, 5);
        plug.setRotationPoint(-3F, 14F, 3F);
        plug.setTextureSize(64, 32);
        plug.mirror = true;
        setRotation(plug, 0F, 0F, 0F);
        Base = new ModelRenderer(this, 8, 11);
        Base.addBox(0F, 0F, 0F, 14, 7, 14);
        Base.setRotationPoint(-7F, 17F, -7F);
        Base.setTextureSize(64, 32);
        Base.mirror = true;
        setRotation(Base, 0F, 0F, 0F);
        viewer = new ModelRenderer(this, 0, 0);
        viewer.addBox(-1F, 0F, -1F, 8, 1, 7);
        viewer.setRotationPoint(-3F, 13F, -6F);
        viewer.setTextureSize(64, 32);
        viewer.mirror = true;
        setRotation(viewer, 0.7853982F, 0F, 0F);
        aimpoint = new ModelRenderer(this, 30, 0);
        aimpoint.addBox(-1F, 0F, 0F, 2, 3, 2);
        aimpoint.setRotationPoint(0F, 17F, 0F);
        aimpoint.setTextureSize(64, 32);
        aimpoint.mirror = true;
        setRotation(aimpoint, 0.7853982F, 0F, 0F);
        laser = new ModelRenderer(this, 38, 0);
        laser.addBox(-0.5F, 0F, -0.5F, 1, 6, 1);
        laser.setRotationPoint(0F, 12F, -3.5F);
        laser.setTextureSize(64, 32);
        laser.mirror = true;
        setRotation(laser, 0.7853982F, 0F, 0F);
        Arm2 = new ModelRenderer(this, 0, 10);
        Arm2.addBox(0F, 0F, 0F, 1, 9, 3);
        Arm2.setRotationPoint(2F, 13F, -4.5F);
        Arm2.setTextureSize(64, 32);
        Arm2.mirror = true;
        setRotation(Arm2, 0.7853982F, 0F, 0F);
        Arm1 = new ModelRenderer(this, 0, 10);
        Arm1.addBox(0F, 0F, 0F, 1, 9, 3);
        Arm1.setRotationPoint(-3F, 13F, -4.5F);
        Arm1.setTextureSize(64, 32);
        Arm1.mirror = true;
        setRotation(Arm1, 0.7853982F, 0F, 0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        peg1.render(f5);
        peg2.render(f5);
        plug.render(f5);
        Base.render(f5);
        viewer.render(f5);
        aimpoint.render(f5);
        laser.render(f5);
        Arm2.render(f5);
        Arm1.render(f5);
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

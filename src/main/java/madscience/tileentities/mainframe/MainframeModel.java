package madscience.tileentities.mainframe;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class MainframeModel extends ModelBase
{
    // fields
    ModelRenderer Bottom;
    ModelRenderer Shell;
    ModelRenderer Top;
    ModelRenderer Glass;

    public MainframeModel()
    {
        textureWidth = 64;
        textureHeight = 32;

        Bottom = new ModelRenderer(this, 8, 17);
        Bottom.addBox(0F, 0F, 0F, 14, 1, 14);
        Bottom.setRotationPoint(-7F, 23F, -7F);
        Bottom.setTextureSize(64, 32);
        Bottom.mirror = true;
        setRotation(Bottom, 0F, 0F, 0F);
        Shell = new ModelRenderer(this, 0, 0);
        Shell.addBox(0F, 0F, 0F, 16, 15, 16);
        Shell.setRotationPoint(-8F, 8F, -8F);
        Shell.setTextureSize(64, 32);
        Shell.mirror = true;
        setRotation(Shell, 0F, 0F, 0F);
        Top = new ModelRenderer(this, 8, 17);
        Top.addBox(0F, 0F, 0F, 14, 1, 14);
        Top.setRotationPoint(-7F, 8F, 7F);
        Top.setTextureSize(64, 32);
        Top.mirror = true;
        setRotation(Top, 3.162537F, 0F, 0F);
        Glass = new ModelRenderer(this, 48, 0);
        Glass.addBox(0F, 0F, 0F, 14, 13, 1);
        Glass.setRotationPoint(-7F, 9F, -8.1F);
        Glass.setTextureSize(64, 32);
        Glass.mirror = true;
        setRotation(Glass, 0F, 0F, 0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        Bottom.render(f5);
        Shell.render(f5);
        Top.render(f5);
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

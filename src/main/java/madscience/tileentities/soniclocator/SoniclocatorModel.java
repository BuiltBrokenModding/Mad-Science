package madscience.tileentities.soniclocator;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class SoniclocatorModel extends ModelBase
{
    //fields
    ModelRenderer ShieldRight;
    ModelRenderer ShieldLeft;
    ModelRenderer ShieldFront;
    ModelRenderer ShieldMiddle;
    ModelRenderer WarningTrimLeft;
    ModelRenderer WarningTrimRight;
    ModelRenderer WarningTrimBack;
    ModelRenderer Thumper1;
    ModelRenderer Thumper2;
    ModelRenderer Thumper3;
    ModelRenderer BaseMiddle;
    ModelRenderer BaseTrimRightBack;
    ModelRenderer BaseTrimLeftBack;
    ModelRenderer TrimRight;
    ModelRenderer TrimLeft;
    ModelRenderer TrimBack;
    ModelRenderer TrimFront;
    ModelRenderer BraceRight;
    ModelRenderer BraceLeft;
    ModelRenderer BraceRightBack;
    ModelRenderer BraceLeftBack;
    ModelRenderer PanelFront;
    ModelRenderer Monitor;
    ModelRenderer BaseBottom1;
    ModelRenderer BaseBottom2;
    ModelRenderer BaseBottom3;
    ModelRenderer BaseBottom4;

    public SoniclocatorModel()
    {
        textureWidth = 128;
        textureHeight = 128;
        
          ShieldRight = new ModelRenderer(this, 1, 1);
          ShieldRight.addBox(0F, 0F, 0F, 1, 21, 11);
          ShieldRight.setRotationPoint(5F, -23F, -8F);
          ShieldRight.setTextureSize(128, 128);
          ShieldRight.mirror = true;
          setRotation(ShieldRight, 0.1479915F, 0F, 0F);
          ShieldLeft = new ModelRenderer(this, 26, 1);
          ShieldLeft.addBox(0F, 0F, 0F, 1, 21, 11);
          ShieldLeft.setRotationPoint(-6F, -23F, -8F);
          ShieldLeft.setTextureSize(128, 128);
          ShieldLeft.mirror = true;
          setRotation(ShieldLeft, 0.1479915F, 0F, 0F);
          ShieldFront = new ModelRenderer(this, 51, 1);
          ShieldFront.addBox(0F, 0F, 0F, 10, 20, 1);
          ShieldFront.setRotationPoint(-5F, -23F, -8F);
          ShieldFront.setTextureSize(128, 128);
          ShieldFront.mirror = true;
          setRotation(ShieldFront, 0.1479865F, 0F, 0F);
          ShieldMiddle = new ModelRenderer(this, 1, 34);
          ShieldMiddle.addBox(0F, 0F, 0F, 14, 3, 13);
          ShieldMiddle.setRotationPoint(-7F, -4F, -6F);
          ShieldMiddle.setTextureSize(128, 128);
          ShieldMiddle.mirror = true;
          setRotation(ShieldMiddle, 0F, 0F, 0F);
          WarningTrimLeft = new ModelRenderer(this, 82, 75);
          WarningTrimLeft.addBox(0F, 0F, 0F, 2, 2, 12);
          WarningTrimLeft.setRotationPoint(-8F, 16F, -5.533333F);
          WarningTrimLeft.setTextureSize(128, 128);
          WarningTrimLeft.mirror = true;
          setRotation(WarningTrimLeft, 0F, 0F, 0.7853982F);
          WarningTrimRight = new ModelRenderer(this, 82, 90);
          WarningTrimRight.addBox(0F, 0F, 0F, 2, 2, 12);
          WarningTrimRight.setRotationPoint(8F, 16F, -5.5F);
          WarningTrimRight.setTextureSize(128, 128);
          WarningTrimRight.mirror = true;
          setRotation(WarningTrimRight, 0F, 0F, 0.7853982F);
          WarningTrimBack = new ModelRenderer(this, 86, 35);
          WarningTrimBack.addBox(0F, 0F, 0F, 14, 2, 2);
          WarningTrimBack.setRotationPoint(-7F, 17.5F, 6.5F);
          WarningTrimBack.setTextureSize(128, 128);
          WarningTrimBack.mirror = true;
          setRotation(WarningTrimBack, 0.7853982F, 0F, 0F);
          Thumper1 = new ModelRenderer(this, 1, 75);
          Thumper1.addBox(0F, 0F, 0F, 3, 42, 10);
          Thumper1.setRotationPoint(-4.5F, -26F, -4F);
          Thumper1.setTextureSize(128, 128);
          Thumper1.mirror = true;
          setRotation(Thumper1, 0F, 0F, 0F);
          Thumper2 = new ModelRenderer(this, 28, 75);
          Thumper2.addBox(0F, 0F, 0F, 3, 42, 10);
          Thumper2.setRotationPoint(-1.5F, -26F, -4F);
          Thumper2.setTextureSize(128, 128);
          Thumper2.mirror = true;
          setRotation(Thumper2, 0F, 0F, 0F);
          Thumper3 = new ModelRenderer(this, 55, 75);
          Thumper3.addBox(0F, 0F, 0F, 3, 42, 10);
          Thumper3.setRotationPoint(1.5F, -26F, -4F);
          Thumper3.setTextureSize(128, 128);
          Thumper3.mirror = true;
          setRotation(Thumper3, 0F, 0F, 0F);
          BaseMiddle = new ModelRenderer(this, 1, 59);
          BaseMiddle.addBox(0F, 0F, 0F, 14, 2, 13);
          BaseMiddle.setRotationPoint(-7F, 16F, -6F);
          BaseMiddle.setTextureSize(128, 128);
          BaseMiddle.mirror = true;
          setRotation(BaseMiddle, 0F, 0F, 0F);
          BaseTrimRightBack = new ModelRenderer(this, 66, 23);
          BaseTrimRightBack.addBox(0F, 0F, 0F, 5, 5, 2);
          BaseTrimRightBack.setRotationPoint(5F, 21F, -0.5F);
          BaseTrimRightBack.setTextureSize(128, 128);
          BaseTrimRightBack.mirror = true;
          setRotation(BaseTrimRightBack, 0F, 0F, 0.6632251F);
          BaseTrimLeftBack = new ModelRenderer(this, 71, 32);
          BaseTrimLeftBack.addBox(0F, 0F, 0F, 5, 5, 2);
          BaseTrimLeftBack.setRotationPoint(-5F, 21F, -0.5F);
          BaseTrimLeftBack.setTextureSize(128, 128);
          BaseTrimLeftBack.mirror = true;
          setRotation(BaseTrimLeftBack, 0F, 0F, 0.9075712F);
          TrimRight = new ModelRenderer(this, 56, 59);
          TrimRight.addBox(0F, 0F, 0F, 1, 2, 13);
          TrimRight.setRotationPoint(7F, 15F, -6F);
          TrimRight.setTextureSize(128, 128);
          TrimRight.mirror = true;
          setRotation(TrimRight, 0F, 0F, 0F);
          TrimLeft = new ModelRenderer(this, 85, 59);
          TrimLeft.addBox(0F, 0F, 0F, 1, 2, 13);
          TrimLeft.setRotationPoint(-8F, 15F, -6F);
          TrimLeft.setTextureSize(128, 128);
          TrimLeft.mirror = true;
          setRotation(TrimLeft, 0F, 0F, 0F);
          TrimBack = new ModelRenderer(this, 1, 55);
          TrimBack.addBox(0F, 0F, 0F, 16, 2, 1);
          TrimBack.setRotationPoint(-8F, 15F, 7F);
          TrimBack.setTextureSize(128, 128);
          TrimBack.mirror = true;
          setRotation(TrimBack, 0F, 0F, 0F);
          TrimFront = new ModelRenderer(this, 1, 51);
          TrimFront.addBox(0F, 0F, 0F, 16, 2, 1);
          TrimFront.setRotationPoint(-8F, 15F, -7F);
          TrimFront.setTextureSize(128, 128);
          TrimFront.mirror = true;
          setRotation(TrimFront, 0F, 0F, 0F);
          BraceRight = new ModelRenderer(this, 74, 1);
          BraceRight.addBox(0F, 0F, 0F, 2, 17, 2);
          BraceRight.setRotationPoint(5F, -1F, -6F);
          BraceRight.setTextureSize(128, 128);
          BraceRight.mirror = true;
          setRotation(BraceRight, 0F, 0F, 0F);
          BraceLeft = new ModelRenderer(this, 83, 1);
          BraceLeft.addBox(0F, 0F, 0F, 2, 17, 2);
          BraceLeft.setRotationPoint(-7F, -1F, -6F);
          BraceLeft.setTextureSize(128, 128);
          BraceLeft.mirror = true;
          setRotation(BraceLeft, 0F, 0F, 0F);
          BraceRightBack = new ModelRenderer(this, 92, 1);
          BraceRightBack.addBox(0F, 0F, 0F, 1, 22, 1);
          BraceRightBack.setRotationPoint(5.5F, -2F, -6F);
          BraceRightBack.setTextureSize(128, 128);
          BraceRightBack.mirror = true;
          setRotation(BraceRightBack, 0.6426736F, 0F, 0F);
          BraceLeftBack = new ModelRenderer(this, 97, 1);
          BraceLeftBack.addBox(0F, 0F, 0F, 1, 22, 1);
          BraceLeftBack.setRotationPoint(-6.5F, -2F, -6F);
          BraceLeftBack.setTextureSize(128, 128);
          BraceLeftBack.mirror = true;
          setRotation(BraceLeftBack, 0.6426736F, 0F, 0F);
          PanelFront = new ModelRenderer(this, 82, 105);
          PanelFront.addBox(0F, 0F, 0F, 13, 7, 1);
          PanelFront.setRotationPoint(-6.5F, 9F, -6F);
          PanelFront.setTextureSize(128, 128);
          PanelFront.mirror = true;
          setRotation(PanelFront, -0.1396263F, 0F, 0F);
          Monitor = new ModelRenderer(this, 82, 115);
          Monitor.addBox(0F, 0F, 0F, 15, 10, 2);
          Monitor.setRotationPoint(-7.5F, -1F, -8F);
          Monitor.setTextureSize(128, 128);
          Monitor.mirror = true;
          setRotation(Monitor, 0F, 0F, 0F);
          BaseBottom1 = new ModelRenderer(this, 56, 41);
          BaseBottom1.addBox(-2F, 0F, -5F, 4, 7, 10);
          BaseBottom1.setRotationPoint(0F, 17F, 0.5F);
          BaseBottom1.setTextureSize(128, 128);
          BaseBottom1.mirror = true;
          setRotation(BaseBottom1, 0F, 0F, 0F);
          BaseBottom2 = new ModelRenderer(this, 56, 41);
          BaseBottom2.addBox(-2F, 0F, -5F, 4, 7, 10);
          BaseBottom2.setRotationPoint(0F, 17F, 0.5F);
          BaseBottom2.setTextureSize(128, 128);
          BaseBottom2.mirror = true;
          setRotation(BaseBottom2, 0F, 0.7853982F, 0F);
          BaseBottom3 = new ModelRenderer(this, 56, 41);
          BaseBottom3.addBox(-2F, 0F, -5F, 4, 7, 10);
          BaseBottom3.setRotationPoint(0F, 17F, 0.5F);
          BaseBottom3.setTextureSize(128, 128);
          BaseBottom3.mirror = true;
          setRotation(BaseBottom3, 0F, 1.570796F, 0F);
          BaseBottom4 = new ModelRenderer(this, 56, 41);
          BaseBottom4.addBox(-2F, 0F, -5F, 4, 7, 10);
          BaseBottom4.setRotationPoint(0F, 17F, 0.5F);
          BaseBottom4.setTextureSize(128, 128);
          BaseBottom4.mirror = true;
          setRotation(BaseBottom4, 0F, -0.7853982F, 0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        ShieldRight.render(f5);
        ShieldLeft.render(f5);
        ShieldFront.render(f5);
        ShieldMiddle.render(f5);
        WarningTrimLeft.render(f5);
        WarningTrimRight.render(f5);
        WarningTrimBack.render(f5);
        Thumper1.render(f5);
        Thumper2.render(f5);
        Thumper3.render(f5);
        BaseMiddle.render(f5);
        BaseTrimRightBack.render(f5);
        BaseTrimLeftBack.render(f5);
        TrimRight.render(f5);
        TrimLeft.render(f5);
        TrimBack.render(f5);
        TrimFront.render(f5);
        BraceRight.render(f5);
        BraceLeft.render(f5);
        BraceRightBack.render(f5);
        BraceLeftBack.render(f5);
        PanelFront.render(f5);
        Monitor.render(f5);
        BaseBottom1.render(f5);
        BaseBottom2.render(f5);
        BaseBottom3.render(f5);
        BaseBottom4.render(f5);
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

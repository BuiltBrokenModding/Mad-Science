package madscience.mobs.werewolf;

import java.util.Random;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.MathHelper;

public class WerewolfMobModel extends ModelBase
{
    // fields
    private ModelRenderer tail;
    private ModelRenderer nose;
    private ModelRenderer wolfhead;
    private ModelRenderer ear2;
    private ModelRenderer ear1;
    private ModelRenderer body;
    private ModelRenderer rightarm;
    private ModelRenderer leftarm;
    private ModelRenderer rightleg;
    private ModelRenderer leftleg;

    public WerewolfMobModel()
    {
        textureWidth = 64;
        textureHeight = 32;

        tail = new ModelRenderer(this, 0, 0);
        tail.addBox(0F, 0F, 0F, 2, 2, 7);
        tail.setRotationPoint(-1F, 8F, 2F);
        tail.setTextureSize(64, 32);
        tail.mirror = true;
        setRotation(tail, -0.6320364F, 0.0371786F, 0F);
        nose = new ModelRenderer(this, 48, 0);
        nose.addBox(-2F, 0F, -5F, 3, 3, 5);
        nose.setRotationPoint(0.5F, -3.7F, 0F);
        nose.setTextureSize(64, 32);
        nose.mirror = true;
        setRotation(nose, 0F, 0F, 0F);
        wolfhead = new ModelRenderer(this, 22, 0);
        wolfhead.addBox(-3F, -3F, -2F, 8, 8, 5);
        wolfhead.setRotationPoint(-1F, -4.7F, 0F);
        wolfhead.setTextureSize(64, 32);
        wolfhead.mirror = true;
        setRotation(wolfhead, 0F, 0F, 0F);
        ear2 = new ModelRenderer(this, 56, 8);
        ear2.addBox(1F, -5F, 0F, 2, 3, 2);
        ear2.setRotationPoint(0.9F, -4.7F, 0F);
        ear2.setTextureSize(64, 32);
        ear2.mirror = true;
        setRotation(ear2, 0F, 0F, 0F);
        ear1 = new ModelRenderer(this, 56, 8);
        ear1.addBox(-3F, -5F, 0F, 2, 3, 2);
        ear1.setRotationPoint(-0.9F, -4.7F, 0F);
        ear1.setTextureSize(64, 32);
        ear1.mirror = true;
        setRotation(ear1, 0F, 0F, 0F);
        body = new ModelRenderer(this, 24, 16);
        body.addBox(-4F, 0F, -2F, 8, 12, 4);
        body.setRotationPoint(0F, 0F, 0F);
        body.setTextureSize(64, 32);
        body.mirror = true;
        setRotation(body, 0F, 0F, 0F);
        rightarm = new ModelRenderer(this, 48, 16);
        rightarm.addBox(-3F, -2F, -2F, 4, 12, 4);
        rightarm.setRotationPoint(-5F, 2F, 0F);
        rightarm.setTextureSize(64, 32);
        rightarm.mirror = true;
        setRotation(rightarm, 0F, 0F, 0F);
        leftarm = new ModelRenderer(this, 48, 16);
        leftarm.addBox(-1F, -2F, -2F, 4, 12, 4);
        leftarm.setRotationPoint(5F, 2F, 0F);
        leftarm.setTextureSize(64, 32);
        leftarm.mirror = true;
        setRotation(leftarm, 0F, 0F, 0F);
        rightleg = new ModelRenderer(this, 0, 16);
        rightleg.addBox(-2F, 0F, -2F, 4, 12, 4);
        rightleg.setRotationPoint(-2F, 12F, 0F);
        rightleg.setTextureSize(64, 32);
        rightleg.mirror = true;
        setRotation(rightleg, 0F, 0F, 0F);
        leftleg = new ModelRenderer(this, 0, 16);
        leftleg.addBox(-2F, 0F, -2F, 4, 12, 4);
        leftleg.setRotationPoint(2F, 12F, 0F);
        leftleg.setTextureSize(64, 32);
        leftleg.mirror = true;
        setRotation(leftleg, 0F, 0F, 0F);
    }

    @Override
    public ModelRenderer getRandomModelBox(Random par1Random)
    {
        return (ModelRenderer) this.boxList.get(par1Random.nextInt(this.boxList.size()));
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        tail.render(f5);
        nose.render(f5);
        wolfhead.render(f5);
        ear2.render(f5);
        ear1.render(f5);
        body.render(f5);
        rightarm.render(f5);
        leftarm.render(f5);
        rightleg.render(f5);
        leftleg.render(f5);
    }

    

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
    {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);

        // Head
        this.wolfhead.rotateAngleX = f4 / (180F / (float) Math.PI);
        this.wolfhead.rotateAngleY = f3 / (180F / (float) Math.PI);

        // Muzzle
        this.nose.rotateAngleX = f4 / (180F / (float) Math.PI);
        this.nose.rotateAngleY = f3 / (180F / (float) Math.PI);

        // Ears
        this.ear1.rotateAngleX = f4 / (180F / (float) Math.PI);
        this.ear1.rotateAngleY = f3 / (180F / (float) Math.PI);
        this.ear2.rotateAngleX = f4 / (180F / (float) Math.PI);
        this.ear2.rotateAngleY = f3 / (180F / (float) Math.PI);

        // Arms
        this.leftarm.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
        this.rightarm.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * 1.4F * f1;

        // Legs
        this.leftleg.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
        this.rightleg.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * 1.4F * f1;
    }
}

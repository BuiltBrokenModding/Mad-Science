package madscience;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntitySplashFX;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy
{
    public float fovModifierHand = 0F;

    @Override
    public int getArmorIndex(String armor)
    {
        return RenderingRegistry.addNewArmourRendererPrefix(armor);
    }

    /* INSTANCES */
    @Override
    public Object getClient()
    {
        return FMLClientHandler.instance().getClient();
    }

    @Override
    public World getClientWorld()
    {
        return FMLClientHandler.instance().getClient().theWorld;
    }

    @Override
    public void spawnParticle(String fxName, double posX, double posY, double posZ, double velX, double velY, double velZ)
    {
        World clientWorld = MadScience.proxy.getClientWorld();
        if (clientWorld == null)
        {
            MadScience.logger.info("Mad Particle: Could not spawn particle because client world was null!");
            return;
        }

        if (fxName.equals("splash"))
        {
            EntityFX someParticle = new EntitySplashFX(clientWorld, posX, posY, posZ, velX, velY, velZ);
            Minecraft.getMinecraft().effectRenderer.addEffect(someParticle);
        }
        else
        {
            // Normal minecraft particle system ignores velocity completely.
            clientWorld.spawnParticle(fxName,
                    posX,
                    posY,
                    posZ
                    , 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public void resetSavedFOV()
    {
        this.fovModifierHand = 0F;
    }

    @Override
    public void onBowUse(ItemStack stack, EntityPlayer player, int pulseRifleFireTime)
    {
        float f = 1.0F;

        if (player.capabilities.isFlying)
        {
            f *= 1.1F;
        }

        float speedOnGround = 0.1F;
        //int i = player.getItemInUseDuration();
        int i = pulseRifleFireTime;
        float f1 = (float) i / 420.0F;

        if (f1 > 1.0F)
        {
            f1 = 1.0F;
        }
        else
        {
            f1 *= f1;
        }

        f *= 1.0F - f1 * 0.25F;

        fovModifierHand = fovModifierHand > 0.001F ? fovModifierHand : (Float) ObfuscationReflectionHelper.getPrivateValue(EntityRenderer.class, Minecraft.getMinecraft().entityRenderer, "fovModifierHand", "field_78507_R");
        fovModifierHand += (f - fovModifierHand) * 0.5F;

        if (fovModifierHand > 1.5F)
        {
            fovModifierHand = 1.5F;
        }

        if (fovModifierHand < 0.1F)
        {
            fovModifierHand = 0.1F;
        }

        ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, Minecraft.getMinecraft().entityRenderer, fovModifierHand, "fovModifierHand", "field_78507_R");
    }
}

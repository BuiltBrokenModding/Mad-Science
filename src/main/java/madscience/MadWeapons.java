package madscience;

import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import madscience.items.weapons.pulserifle.WeaponItemPulseRifle;
import madscience.items.weapons.pulserifle.WeaponItemPulseRifleBullet;

public class MadWeapons
{
    // Pulse Rifle
    public static WeaponItemPulseRifle WEAPONITEM_PULSERIFLE;
    public static final String WEAPONITEM_PULSERIFLE_INTERNALNAME = "pulseRifle";
    
    // Pulse Rifle Bullet
    public static final String WEAPONITEM_PULSERIFLE_BULLET_INTERNALNAME = "pulseRifleBullet";
    
    public static void createPulseRifle(int itemID)
    {
        // Totally not a rip off from that movie with Sigourney Weaver.
        WEAPONITEM_PULSERIFLE = (WeaponItemPulseRifle) new WeaponItemPulseRifle(itemID).setUnlocalizedName(WEAPONITEM_PULSERIFLE_INTERNALNAME);
        GameRegistry.registerItem(WEAPONITEM_PULSERIFLE, WEAPONITEM_PULSERIFLE_INTERNALNAME);
        MadScience.proxy.registerRenderingHandler(itemID);
    }

    public static void createPulseRifleBullet(int itemID)
    {
        // Bullet for the gun that is rendered in the world.
        EntityRegistry.registerModEntity(WeaponItemPulseRifleBullet.class, WEAPONITEM_PULSERIFLE_BULLET_INTERNALNAME, itemID, MadScience.instance, 120, 3, true);
        MadScience.proxy.registerRenderingHandler(itemID);
    }
}

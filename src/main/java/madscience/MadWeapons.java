package madscience;

import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import madscience.items.weapons.pulserifle.PulseRifleBulletItem;
import madscience.items.weapons.pulserifle.PulseRifleGrenadeEntity;
import madscience.items.weapons.pulserifle.PulseRifleGrenadeItem;
import madscience.items.weapons.pulserifle.PulseRifleItem;
import madscience.items.weapons.pulserifle.PulseRifleBulletEntity;
import madscience.items.weapons.pulserifle.PulseRifleMagazineCraftingHandler;
import madscience.items.weapons.pulserifle.PulseRifleMagazineItem;

public class MadWeapons
{
    // Pulse Rifle
    public static PulseRifleItem WEAPONITEM_PULSERIFLE;
    public static final String WEAPONITEM_PULSERIFLE_INTERNALNAME = "pulseRifle";
    
    // Pulse Rifle Bullet
    public static PulseRifleBulletItem WEAPONITEM_BULLETITEM;
    public static final String WEAPONITEM_PULSERIFLE_BULLET_INTERNALNAME = "pulseRifleBullet";
    
    // Pulse Rifle Grenade
    public static PulseRifleGrenadeItem WEAPONITEM_GRENADEITEM;
    public static final String WEAPONITEM_PULSERIFLE_GRENADE_INTERNALNAME = "pulseRifleGrenade";
    
    // Pulse Rifle Magazine
    public static PulseRifleMagazineItem WEAPONITEM_MAGAZINEITEM;
    public static final String WEAPONITEM_PULSERIFLE_MAGAZINE_INTERNALNAME = "pulseRifleMagazine";
    
    public static void createPulseRifle(int itemID)
    {
        // Totally not a rip off from that movie with Sigourney Weaver.
        WEAPONITEM_PULSERIFLE = (PulseRifleItem) new PulseRifleItem(itemID).setUnlocalizedName(WEAPONITEM_PULSERIFLE_INTERNALNAME);
        GameRegistry.registerItem(WEAPONITEM_PULSERIFLE, WEAPONITEM_PULSERIFLE_INTERNALNAME);
        MadScience.proxy.registerRenderingHandler(itemID);
    }

    public static void createPulseRifleBullet(int itemID)
    {
        // Bullet for the gun that is rendered in the world.
        WEAPONITEM_BULLETITEM = (PulseRifleBulletItem) new PulseRifleBulletItem(itemID).setUnlocalizedName(WEAPONITEM_PULSERIFLE_BULLET_INTERNALNAME);
        EntityRegistry.registerModEntity(PulseRifleBulletEntity.class, WEAPONITEM_PULSERIFLE_BULLET_INTERNALNAME, itemID, MadScience.instance, 120, 3, true);
        GameRegistry.registerItem(WEAPONITEM_BULLETITEM, WEAPONITEM_PULSERIFLE_BULLET_INTERNALNAME);
        MadScience.proxy.registerRenderingHandler(itemID);
    }

    public static void createPulseRifleGrenade(int itemID)
    {
        // Grenade for the gun that is rendered in the world.
        WEAPONITEM_GRENADEITEM = (PulseRifleGrenadeItem) new PulseRifleGrenadeItem(itemID).setUnlocalizedName(WEAPONITEM_PULSERIFLE_GRENADE_INTERNALNAME);
        EntityRegistry.registerModEntity(PulseRifleGrenadeEntity.class, WEAPONITEM_PULSERIFLE_GRENADE_INTERNALNAME, itemID, MadScience.instance, 120, 3, true);
        GameRegistry.registerItem(WEAPONITEM_GRENADEITEM, WEAPONITEM_PULSERIFLE_GRENADE_INTERNALNAME);
        MadScience.proxy.registerRenderingHandler(itemID);
    }

    public static void createPulseRifleMagazine(int itemID)
    {
        // Magazine that is crafted with bullets to fill them and loaded into rifle.
        WEAPONITEM_MAGAZINEITEM = (PulseRifleMagazineItem) new PulseRifleMagazineItem(itemID).setUnlocalizedName(WEAPONITEM_PULSERIFLE_MAGAZINE_INTERNALNAME);
        GameRegistry.registerItem(WEAPONITEM_MAGAZINEITEM, WEAPONITEM_PULSERIFLE_MAGAZINE_INTERNALNAME);
        GameRegistry.registerCraftingHandler(new PulseRifleMagazineCraftingHandler());
        MadScience.proxy.registerRenderingHandler(itemID);        
    }
}

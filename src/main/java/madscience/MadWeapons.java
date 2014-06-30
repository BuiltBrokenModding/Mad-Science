package madscience;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import madscience.items.weapons.pulserifle.PulseRifleItem;
import madscience.items.weapons.pulseriflebullet.PulseRifleBulletEntity;
import madscience.items.weapons.pulseriflebullet.PulseRifleBulletItem;
import madscience.items.weapons.pulseriflegrenade.PulseRifleGrenadeEntity;
import madscience.items.weapons.pulseriflegrenade.PulseRifleGrenadeItem;
import madscience.items.weapons.pulseriflemagazine.PulseRifleMagazineCraftingHandler;
import madscience.items.weapons.pulseriflemagazine.PulseRifleMagazineItem;

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
    
    static void createPulseRifle(int itemID)
    {
        // Totally not a rip off from that movie with Sigourney Weaver.
        MadScience.logger.info("-M41A Pulse Rifle");
        WEAPONITEM_PULSERIFLE = (PulseRifleItem) new PulseRifleItem(itemID).setUnlocalizedName(WEAPONITEM_PULSERIFLE_INTERNALNAME);
        GameRegistry.registerItem(WEAPONITEM_PULSERIFLE, WEAPONITEM_PULSERIFLE_INTERNALNAME);
        MadScience.proxy.registerRenderingHandler(itemID);
        
        // Pulse Rifle recipe.
        GameRegistry.addRecipe(new ItemStack(WEAPONITEM_PULSERIFLE, 1), new Object[]
        { "123", 
          "654", 
          "789",

          '1', new ItemStack(MadComponents.COMPONENT_PULSERIFLEBARREL, 1, 0),
          '2', new ItemStack(MadComponents.COMPONENT_PULSERIFLEBOLT, 1, 0),
          '3', new ItemStack(MadComponents.COMPONENT_PULSERIFLERECIEVER, 1, 0),
          '4', new ItemStack(MadComponents.COMPONENT_PULSERIFLETRIGGER, 1, 0),
          '5', new ItemStack(MadComponents.COMPONENT_SCREEN, 1, 0),
          '6', new ItemStack(MadComponents.COMPONENT_POWERSUPPLY, 1, 0),
          '7', new ItemStack(MadComponents.COMPONENT_CPU, 1, 0),
          '8', new ItemStack(MadCircuits.CIRCUIT_DIAMOND, 1, 0),
          '9', new ItemStack(Item.dyePowder, 1, 2) // Cactus Green Dye
        });
    }

    static void createPulseRifleBullet(int itemID)
    {
        // Bullet for the gun that is rendered in the world.
        MadScience.logger.info("-Pulse Rifle Bullet");
        WEAPONITEM_BULLETITEM = (PulseRifleBulletItem) new PulseRifleBulletItem(itemID).setUnlocalizedName(WEAPONITEM_PULSERIFLE_BULLET_INTERNALNAME);
        EntityRegistry.registerModEntity(PulseRifleBulletEntity.class, WEAPONITEM_PULSERIFLE_BULLET_INTERNALNAME, itemID, MadScience.instance, 120, 3, true);
        GameRegistry.registerItem(WEAPONITEM_BULLETITEM, WEAPONITEM_PULSERIFLE_BULLET_INTERNALNAME);
        MadScience.proxy.registerRenderingHandler(itemID);
    }

    static void createPulseRifleGrenade(int itemID)
    {
        // Grenade for the gun that is rendered in the world.
        MadScience.logger.info("-Pulse Rifle Grenade");
        WEAPONITEM_GRENADEITEM = (PulseRifleGrenadeItem) new PulseRifleGrenadeItem(itemID).setUnlocalizedName(WEAPONITEM_PULSERIFLE_GRENADE_INTERNALNAME);
        EntityRegistry.registerModEntity(PulseRifleGrenadeEntity.class, WEAPONITEM_PULSERIFLE_GRENADE_INTERNALNAME, itemID, MadScience.instance, 120, 3, true);
        GameRegistry.registerItem(WEAPONITEM_GRENADEITEM, WEAPONITEM_PULSERIFLE_GRENADE_INTERNALNAME);
        MadScience.proxy.registerRenderingHandler(itemID);        
    }

    static void createPulseRifleMagazine(int itemID)
    {
        // Magazine that is crafted with bullets to fill them and loaded into rifle.
        MadScience.logger.info("-Pulse Rifle Magazine");
        WEAPONITEM_MAGAZINEITEM = (PulseRifleMagazineItem) new PulseRifleMagazineItem(itemID).setUnlocalizedName(WEAPONITEM_PULSERIFLE_MAGAZINE_INTERNALNAME);
        GameRegistry.registerItem(WEAPONITEM_MAGAZINEITEM, WEAPONITEM_PULSERIFLE_MAGAZINE_INTERNALNAME);
        GameRegistry.registerCraftingHandler(new PulseRifleMagazineCraftingHandler());
        MadScience.proxy.registerRenderingHandler(itemID);
    }
}

package madscience.content.items.weapons.pulserifle;

import madscience.MadScience;
import madscience.MadWeapons;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PulseRifleSounds
{
    // Pulse Rifle
    public static final String PULSERIFLE_FIRE = MadScience.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + ".Fire";
    public static final String PULSERIFLE_EMPTY = MadScience.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + ".Empty";
    public static final String PULSERIFLE_FIREGRENADE = MadScience.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + ".FireGrenade";
    public static final String PULSERIFLE_GRENADEEXPLODE = MadScience.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + ".GrenadeExplode";
    public static final String PULSERIFLE_RELOAD = MadScience.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + ".Reload";
    public static final String PULSERIFLE_UNLOAD = MadScience.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + ".Unload";
    public static final String PULSERIFLE_RELOADGRENADE = MadScience.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + ".ReloadGrenade";
    public static final String PULSERIFLE_CHAMBERGRENADE = MadScience.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + ".ChamberGrenade";
    public static final String PULSERIFLE_MAGAZINERELOAD = MadScience.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + ".MagazineReload";
    public static final String PULSERIFLE_MAGAZINEUNLOAD = MadScience.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + ".MagazineUnload";
    public static final String PULSERIFLE_RICOCHET = MadScience.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + ".Ricochet";

    @SideOnly(Side.CLIENT)
    public static void init(SoundLoadEvent event)
    {
        // Empty
        event.manager.addSound(MadScience.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/Empty.ogg");

        // Fire
        event.manager.addSound(MadScience.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/Fire0.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/Fire1.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/Fire2.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/Fire3.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/Fire4.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/Fire5.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/Fire6.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/Fire7.ogg");

        // Fire Grenade
        event.manager.addSound(MadScience.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/FireGrenade.ogg");

        // Grenade Explode
        event.manager.addSound(MadScience.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/GrenadeExplode.ogg");

        // Reload
        event.manager.addSound(MadScience.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/Reload.ogg");

        // Reload Grenade
        event.manager.addSound(MadScience.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/ReloadGrenade.ogg");

        // Chamber Grenade (racking sound).
        event.manager.addSound(MadScience.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/ChamberGrenade.ogg");

        // Unload
        event.manager.addSound(MadScience.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/Unload.ogg");

        // Magazine Load Bullet
        event.manager.addSound(MadScience.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/MagazineReload0.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/MagazineReload1.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/MagazineReload2.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/MagazineReload3.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/MagazineReload4.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/MagazineReload5.ogg");

        // Magazine Unload Bullets
        event.manager.addSound(MadScience.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/MagazineUnload.ogg");

        // Bullet Ricochet
        event.manager.addSound(MadScience.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/Ricochet0.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/Ricochet1.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/Ricochet2.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/Ricochet3.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/Ricochet4.ogg");
        event.manager.addSound(MadScience.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/Ricochet5.ogg");
    }
}

package madscience.item.weapon.pulserifle;

import madscience.MadWeapons;
import madscience.factory.mod.MadMod;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PulseRifleSounds
{
    // Pulse Rifle
    static final String PULSERIFLE_FIRE = MadMod.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + ".Fire";
    static final String PULSERIFLE_EMPTY = MadMod.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + ".Empty";
    static final String PULSERIFLE_FIREGRENADE = MadMod.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + ".FireGrenade";
    public static final String PULSERIFLE_GRENADEEXPLODE = MadMod.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + ".GrenadeExplode";
    static final String PULSERIFLE_RELOAD = MadMod.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + ".Reload";
    static final String PULSERIFLE_UNLOAD = MadMod.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + ".Unload";
    static final String PULSERIFLE_RELOADGRENADE = MadMod.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + ".ReloadGrenade";
    static final String PULSERIFLE_CHAMBERGRENADE = MadMod.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + ".ChamberGrenade";
    public static final String PULSERIFLE_MAGAZINERELOAD = MadMod.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + ".MagazineReload";
    public static final String PULSERIFLE_MAGAZINEUNLOAD = MadMod.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + ".MagazineUnload";
    public static final String PULSERIFLE_RICOCHET = MadMod.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + ".Ricochet";

    @SideOnly(Side.CLIENT)
    public static void init(SoundLoadEvent event)
    {
        // Empty
        event.manager.addSound(MadMod.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/Empty.ogg");

        // Fire
        event.manager.addSound(MadMod.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/Fire0.ogg");
        event.manager.addSound(MadMod.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/Fire1.ogg");
        event.manager.addSound(MadMod.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/Fire2.ogg");
        event.manager.addSound(MadMod.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/Fire3.ogg");
        event.manager.addSound(MadMod.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/Fire4.ogg");
        event.manager.addSound(MadMod.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/Fire5.ogg");
        event.manager.addSound(MadMod.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/Fire6.ogg");
        event.manager.addSound(MadMod.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/Fire7.ogg");

        // Fire Grenade
        event.manager.addSound(MadMod.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/FireGrenade.ogg");

        // Grenade Explode
        event.manager.addSound(MadMod.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/GrenadeExplode.ogg");

        // Reload
        event.manager.addSound(MadMod.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/Reload.ogg");

        // Reload Grenade
        event.manager.addSound(MadMod.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/ReloadGrenade.ogg");

        // Chamber Grenade (racking sound).
        event.manager.addSound(MadMod.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/ChamberGrenade.ogg");

        // Unload
        event.manager.addSound(MadMod.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/Unload.ogg");

        // Magazine Load Bullet
        event.manager.addSound(MadMod.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/MagazineReload0.ogg");
        event.manager.addSound(MadMod.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/MagazineReload1.ogg");
        event.manager.addSound(MadMod.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/MagazineReload2.ogg");
        event.manager.addSound(MadMod.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/MagazineReload3.ogg");
        event.manager.addSound(MadMod.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/MagazineReload4.ogg");
        event.manager.addSound(MadMod.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/MagazineReload5.ogg");

        // Magazine Unload Bullets
        event.manager.addSound(MadMod.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/MagazineUnload.ogg");

        // Bullet Ricochet
        event.manager.addSound(MadMod.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/Ricochet0.ogg");
        event.manager.addSound(MadMod.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/Ricochet1.ogg");
        event.manager.addSound(MadMod.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/Ricochet2.ogg");
        event.manager.addSound(MadMod.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/Ricochet3.ogg");
        event.manager.addSound(MadMod.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/Ricochet4.ogg");
        event.manager.addSound(MadMod.ID + ":" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/Ricochet5.ogg");
    }
}

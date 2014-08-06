package madscience;

import madscience.factory.mod.MadMod;
import madscience.item.components.ComponentCPU;
import madscience.item.components.ComponentCase;
import madscience.item.components.ComponentComputer;
import madscience.item.components.ComponentEnderslime;
import madscience.item.components.ComponentFan;
import madscience.item.components.ComponentFusedQuartz;
import madscience.item.components.ComponentMagneticTape;
import madscience.item.components.ComponentPowerSupply;
import madscience.item.components.ComponentRAM;
import madscience.item.components.ComponentScreen;
import madscience.item.components.ComponentSiliconWafer;
import madscience.item.components.ComponentThumper;
import madscience.item.components.ComponentTransistor;
import madscience.item.components.pulserifle.ComponentPulseRifleBarrel;
import madscience.item.components.pulserifle.ComponentPulseRifleBolt;
import madscience.item.components.pulserifle.ComponentPulseRifleBulletCasing;
import madscience.item.components.pulserifle.ComponentPulseRifleGrenadeCasing;
import madscience.item.components.pulserifle.ComponentPulseRifleReceiver;
import madscience.item.components.pulserifle.ComponentPulseRifleTrigger;
import cpw.mods.fml.common.registry.GameRegistry;

public class MadComponents
{
    // Screen
    public static ComponentScreen COMPONENT_SCREEN;
    private static final String COMPONENT_SCREEN_INTERNALNAME = "componentScreen";

    // Transistor
    public static ComponentTransistor COMPONENT_TRANSISTOR;
    private static final String COMPONENT_TRANSISTOR_INTERNALNAME = "componentTransistor";

    // Magnetic Tape
    public static ComponentMagneticTape COMPONENT_MAGNETICTAPE;
    private static final String COMPONENT_MAGNETICTAPE_INTERNALNAME = "componentMagneticTape";
    
    // Thumper
    public static ComponentThumper COMPONENT_THUMPER;
    private static final String COMPONENT_THUMPER_INTERNALNAME = "componentThumper";
    
    // Ender Slime
    public static ComponentEnderslime COMPONENT_ENDERSLIME;
    private static final String COMPONENT_ENDERSLIME_INTERNALNAME = "componentEnderslime";
    
    // M41A Barrel
    public static ComponentPulseRifleBarrel COMPONENT_PULSERIFLEBARREL;
    public static final String COMPONENT_PULSERIFLEBARREL_INTERNALNAME = "componentPulseRifleBarrel";
    
    // M41A Bolt
    public static ComponentPulseRifleBolt COMPONENT_PULSERIFLEBOLT;
    public static final String COMPONENT_PULSERIFLEBOLT_INTERNALNAME = "componentPulseRifleBolt";
    
    // M41A Receiver
    public static ComponentPulseRifleReceiver COMPONENT_PULSERIFLERECIEVER;
    public static final String COMPONENT_PULSERIFLERECIEVER_INTERNALNAME = "componentPulseRifleReciever";
    
    // M41A Trigger
    public static ComponentPulseRifleTrigger COMPONENT_PULSERIFLETRIGGER;
    public static final String COMPONENT_PULSERIFLETRIGGER_INTERNALNAME = "componentPulseRifleTrigger";
    
    // M41A Bullet Casing
    public static ComponentPulseRifleBulletCasing COMPONENT_PULSERIFLEBULLETCASING;
    public static final String COMPONENT_PULSERIFLEBULLETCASING_INTERNALNAME = "componentPulseRifleBulletCasing";
    
    // M41A Grenade Casing
    public static ComponentPulseRifleGrenadeCasing COMPONENT_PULSERIFLEGRENADECASING;
    public static final String COMPONENT_PULSERIFLEGRENADECASING_INTERNALNAME = "componentPulseRifleGrenadeCasing";
}

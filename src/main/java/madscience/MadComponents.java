package madscience;

import madscience.factory.mod.MadMod;
import madscience.items.components.ComponentCPU;
import madscience.items.components.ComponentCase;
import madscience.items.components.ComponentComputer;
import madscience.items.components.ComponentEnderslime;
import madscience.items.components.ComponentFan;
import madscience.items.components.ComponentFusedQuartz;
import madscience.items.components.ComponentMagneticTape;
import madscience.items.components.ComponentPowerSupply;
import madscience.items.components.ComponentRAM;
import madscience.items.components.ComponentScreen;
import madscience.items.components.ComponentSiliconWafer;
import madscience.items.components.ComponentThumper;
import madscience.items.components.ComponentTransistor;
import madscience.items.components.pulserifle.ComponentPulseRifleBarrel;
import madscience.items.components.pulserifle.ComponentPulseRifleBolt;
import madscience.items.components.pulserifle.ComponentPulseRifleBulletCasing;
import madscience.items.components.pulserifle.ComponentPulseRifleGrenadeCasing;
import madscience.items.components.pulserifle.ComponentPulseRifleReceiver;
import madscience.items.components.pulserifle.ComponentPulseRifleTrigger;
import cpw.mods.fml.common.registry.GameRegistry;

public class MadComponents
{
    // Case
    public static ComponentCase COMPONENT_CASE;
    private static final String COMPONENT_CASE_INTERNALNAME = "componentCase";

    // CPU
    public static ComponentCPU COMPONENT_CPU;
    private static final String COMPONENT_CPU_INTERNALNAME = "componentCPU";

    // Fan
    public static ComponentFan COMPONENT_FAN;
    private static final String COMPONENT_FAN_INTERNALNAME = "componentFan";

    // Power Supply
    public static ComponentPowerSupply COMPONENT_POWERSUPPLY;
    private static final String COMPONENT_POWERSUPPLY_INTERNALNAME = "componentPowerSupply";

    // RAM
    public static ComponentRAM COMPONENT_RAM;
    private static final String COMPONENT_RAM_INTERNALNAME = "componentRAM";

    // Silicon Wafer
    public static ComponentSiliconWafer COMPONENT_SILICONWAFER;
    private static final String COMPONENT_SILICONWAFER_INTERNALNAME = "componentSiliconWafer";

    // Computer
    public static ComponentComputer COMPONENT_COMPUTER;
    private static final String COMPONENT_COMPUTER_INTERNALNAME = "componentComputer";

    // Screen
    public static ComponentScreen COMPONENT_SCREEN;
    private static final String COMPONENT_SCREEN_INTERNALNAME = "componentScreen";

    // Transistor
    public static ComponentTransistor COMPONENT_TRANSISTOR;
    private static final String COMPONENT_TRANSISTOR_INTERNALNAME = "componentTransistor";

    // Fused Quartz
    public static ComponentFusedQuartz COMPONENT_FUSEDQUARTZ;
    private static final String COMPONENT_FUSEDQUARTZ_INTERNALNAME = "componentFusedQuartz";

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
    
    static void createComponentCaseItem(int itemID)
    {
        MadMod.LOGGER.info("-Component Case");
        COMPONENT_CASE = (ComponentCase) new ComponentCase(itemID).setUnlocalizedName(COMPONENT_CASE_INTERNALNAME);
        GameRegistry.registerItem(COMPONENT_CASE, COMPONENT_CASE_INTERNALNAME);
    }

    static void createComponentComputerItem(int itemID)
    {
        MadMod.LOGGER.info("-Component Computer");
        COMPONENT_COMPUTER = (ComponentComputer) new ComponentComputer(itemID).setUnlocalizedName(COMPONENT_COMPUTER_INTERNALNAME);
        GameRegistry.registerItem(COMPONENT_COMPUTER, COMPONENT_COMPUTER_INTERNALNAME);
    }

    static void createComponentCPUItem(int itemID)
    {
        MadMod.LOGGER.info("-Component CPU");
        COMPONENT_CPU = (ComponentCPU) new ComponentCPU(itemID).setUnlocalizedName(COMPONENT_CPU_INTERNALNAME);
        GameRegistry.registerItem(COMPONENT_CPU, COMPONENT_CPU_INTERNALNAME);
    }

    static void createComponentFanItem(int itemID)
    {
        MadMod.LOGGER.info("-Component Fan");
        COMPONENT_FAN = (ComponentFan) new ComponentFan(itemID).setUnlocalizedName(COMPONENT_FAN_INTERNALNAME);
        GameRegistry.registerItem(COMPONENT_FAN, COMPONENT_FAN_INTERNALNAME);
    }

    static void createComponentFusedQuartzItem(int itemID)
    {
        MadMod.LOGGER.info("-Component Fused Quartz");
        COMPONENT_FUSEDQUARTZ = (ComponentFusedQuartz) new ComponentFusedQuartz(itemID).setUnlocalizedName(COMPONENT_FUSEDQUARTZ_INTERNALNAME);
        GameRegistry.registerItem(COMPONENT_FUSEDQUARTZ, COMPONENT_FUSEDQUARTZ_INTERNALNAME);
    }

    static void createComponentMagneticTapeItem(int itemID)
    {
        MadMod.LOGGER.info("-Component Magnetic Tape");
        COMPONENT_MAGNETICTAPE = (ComponentMagneticTape) new ComponentMagneticTape(itemID).setUnlocalizedName(COMPONENT_MAGNETICTAPE_INTERNALNAME);
        GameRegistry.registerItem(COMPONENT_MAGNETICTAPE, COMPONENT_MAGNETICTAPE_INTERNALNAME);
    }

    static void createComponentPowerSupplyItem(int itemID)
    {
        MadMod.LOGGER.info("-Component Power Supply");
        COMPONENT_POWERSUPPLY = (ComponentPowerSupply) new ComponentPowerSupply(itemID).setUnlocalizedName(COMPONENT_POWERSUPPLY_INTERNALNAME);
        GameRegistry.registerItem(COMPONENT_POWERSUPPLY, COMPONENT_POWERSUPPLY_INTERNALNAME);
    }

    static void createComponentRAMItem(int itemID)
    {
        MadMod.LOGGER.info("-Component RAM");
        COMPONENT_RAM = (ComponentRAM) new ComponentRAM(itemID).setUnlocalizedName(COMPONENT_RAM_INTERNALNAME);
        GameRegistry.registerItem(COMPONENT_RAM, COMPONENT_RAM_INTERNALNAME);
    }

    static void createComponentScreenItem(int itemID)
    {
        MadMod.LOGGER.info("-Component Screen");
        COMPONENT_SCREEN = (ComponentScreen) new ComponentScreen(itemID).setUnlocalizedName(COMPONENT_SCREEN_INTERNALNAME);
        GameRegistry.registerItem(COMPONENT_SCREEN, COMPONENT_SCREEN_INTERNALNAME);
    }

    static void createComponentSiliconWaferItem(int itemID)
    {
        MadMod.LOGGER.info("-Component Silicon Wafer");
        COMPONENT_SILICONWAFER = (ComponentSiliconWafer) new ComponentSiliconWafer(itemID).setUnlocalizedName(COMPONENT_SILICONWAFER_INTERNALNAME);
        GameRegistry.registerItem(COMPONENT_SILICONWAFER, COMPONENT_SILICONWAFER_INTERNALNAME);
    }

    static void createComponentTransistorItem(int itemID)
    {
        MadMod.LOGGER.info("-Component Transistor");
        COMPONENT_TRANSISTOR = (ComponentTransistor) new ComponentTransistor(itemID).setUnlocalizedName(COMPONENT_TRANSISTOR_INTERNALNAME);
        GameRegistry.registerItem(COMPONENT_TRANSISTOR, COMPONENT_TRANSISTOR_INTERNALNAME);
    }

    static void createComponentThumperItem(int itemID)
    {
        MadMod.LOGGER.info("-Component Thumper");
        COMPONENT_THUMPER = (ComponentThumper) new ComponentThumper(itemID).setUnlocalizedName(COMPONENT_THUMPER_INTERNALNAME);
        GameRegistry.registerItem(COMPONENT_THUMPER, COMPONENT_THUMPER_INTERNALNAME);
    }

    static void createComponentEnderslimeItem(int itemID)
    {
        MadMod.LOGGER.info("-Component Enderslime");
        COMPONENT_ENDERSLIME = (ComponentEnderslime) new ComponentEnderslime(itemID).setUnlocalizedName(COMPONENT_ENDERSLIME_INTERNALNAME);
        GameRegistry.registerItem(COMPONENT_ENDERSLIME, COMPONENT_ENDERSLIME_INTERNALNAME);      
    }
    
    static void createComponentPulseRifleBarrelItem(int itemID)
    {
        // M41A Barrel
        MadMod.LOGGER.info("-Component Pulse Rifle Barrel");
        COMPONENT_PULSERIFLEBARREL = (ComponentPulseRifleBarrel) new ComponentPulseRifleBarrel(itemID).setUnlocalizedName(COMPONENT_PULSERIFLEBARREL_INTERNALNAME);
        GameRegistry.registerItem(COMPONENT_PULSERIFLEBARREL, COMPONENT_PULSERIFLEBARREL_INTERNALNAME);
        MadForgeMod.proxy.registerRenderingHandler(itemID);
    }

    static void createComponentPulseRifleBoltItem(int itemID)
    {
        // M41A Bolt
        MadMod.LOGGER.info("-Component Pulse Rifle Bolt");
        COMPONENT_PULSERIFLEBOLT = (ComponentPulseRifleBolt) new ComponentPulseRifleBolt(itemID).setUnlocalizedName(COMPONENT_PULSERIFLEBOLT_INTERNALNAME);
        GameRegistry.registerItem(COMPONENT_PULSERIFLEBOLT, COMPONENT_PULSERIFLEBOLT_INTERNALNAME);
        MadForgeMod.proxy.registerRenderingHandler(itemID);
    }

    static void createComponentPulseRifleReceiverItem(int itemID)
    {
        // M41A Receiver
        MadMod.LOGGER.info("-Component Pulse Rifle Receiver");
        COMPONENT_PULSERIFLERECIEVER = (ComponentPulseRifleReceiver) new ComponentPulseRifleReceiver(itemID).setUnlocalizedName(COMPONENT_PULSERIFLERECIEVER_INTERNALNAME);
        GameRegistry.registerItem(COMPONENT_PULSERIFLERECIEVER, COMPONENT_PULSERIFLERECIEVER_INTERNALNAME);
        MadForgeMod.proxy.registerRenderingHandler(itemID);
    }

    static void createComponentPulseRifleTriggerItem(int itemID)
    {
        // M41A Trigger
        MadMod.LOGGER.info("-Component Pulse Rifle Trigger");
        COMPONENT_PULSERIFLETRIGGER = (ComponentPulseRifleTrigger) new ComponentPulseRifleTrigger(itemID).setUnlocalizedName(COMPONENT_PULSERIFLETRIGGER_INTERNALNAME);
        GameRegistry.registerItem(COMPONENT_PULSERIFLETRIGGER, COMPONENT_PULSERIFLETRIGGER_INTERNALNAME);
        MadForgeMod.proxy.registerRenderingHandler(itemID);
    }

    static void createComponentPulseRifleBulletCasingItem(int itemID)
    {
        // M41A Bullet Casing
        MadMod.LOGGER.info("-Component Pulse Rifle Bullet Casing");
        COMPONENT_PULSERIFLEBULLETCASING = (ComponentPulseRifleBulletCasing) new ComponentPulseRifleBulletCasing(itemID).setUnlocalizedName(COMPONENT_PULSERIFLEBULLETCASING_INTERNALNAME);
        GameRegistry.registerItem(COMPONENT_PULSERIFLEBULLETCASING, COMPONENT_PULSERIFLEBULLETCASING_INTERNALNAME);
        MadForgeMod.proxy.registerRenderingHandler(itemID);
    }

    static void createComponentPulseRifleGrenadeCasingItem(int itemID)
    {
        // M41A Grenade Casing
        MadMod.LOGGER.info("-Component Pulse Rifle Grenade Casing");
        COMPONENT_PULSERIFLEGRENADECASING = (ComponentPulseRifleGrenadeCasing) new ComponentPulseRifleGrenadeCasing(itemID).setUnlocalizedName(COMPONENT_PULSERIFLEGRENADECASING_INTERNALNAME);
        GameRegistry.registerItem(COMPONENT_PULSERIFLEGRENADECASING, COMPONENT_PULSERIFLEGRENADECASING_INTERNALNAME);
        MadForgeMod.proxy.registerRenderingHandler(itemID);
    }
}

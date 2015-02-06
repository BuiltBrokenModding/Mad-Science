package madscience;

import madscience.items.components.pulserifle.ComponentPulseRifleBarrel;
import madscience.items.components.pulserifle.ComponentPulseRifleBolt;
import madscience.items.components.pulserifle.ComponentPulseRifleBulletCasing;
import madscience.items.components.pulserifle.ComponentPulseRifleGrenadeCasing;
import madscience.items.components.pulserifle.ComponentPulseRifleReceiver;
import madscience.items.components.pulserifle.ComponentPulseRifleTrigger;
import cpw.mods.fml.common.registry.GameRegistry;

public class MadComponents
{
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
    
    public static void createComponentCaseItem(int itemID)
    {
        MadScience.logger.info("-Component Case");
        COMPONENT_CASE = (ComponentCase) new ComponentCase(itemID).setUnlocalizedName(COMPONENT_CASE_INTERNALNAME);
        GameRegistry.registerItem(COMPONENT_CASE, COMPONENT_CASE_INTERNALNAME);
    }

    public static void createComponentComputerItem(int itemID)
    {
        MadScience.logger.info("-Component Computer");
        COMPONENT_COMPUTER = (ComponentComputer) new ComponentComputer(itemID).setUnlocalizedName(COMPONENT_COMPUTER_INTERNALNAME);
        GameRegistry.registerItem(COMPONENT_COMPUTER, COMPONENT_COMPUTER_INTERNALNAME);
    }

    public static void createComponentCPUItem(int itemID)
    {
        MadScience.logger.info("-Component CPU");
        COMPONENT_CPU = (ComponentCPU) new ComponentCPU(itemID).setUnlocalizedName(COMPONENT_CPU_INTERNALNAME);
        GameRegistry.registerItem(COMPONENT_CPU, COMPONENT_CPU_INTERNALNAME);
    }

    public static void createComponentFanItem(int itemID)
    {
        MadScience.logger.info("-Component Fan");
        COMPONENT_FAN = (ComponentFan) new ComponentFan(itemID).setUnlocalizedName(COMPONENT_FAN_INTERNALNAME);
        GameRegistry.registerItem(COMPONENT_FAN, COMPONENT_FAN_INTERNALNAME);
    }

    public static void createComponentFusedQuartzItem(int itemID)
    {
        MadScience.logger.info("-Component Fused Quartz");
        COMPONENT_FUSEDQUARTZ = (ComponentFusedQuartz) new ComponentFusedQuartz(itemID).setUnlocalizedName(COMPONENT_FUSEDQUARTZ_INTERNALNAME);
        GameRegistry.registerItem(COMPONENT_FUSEDQUARTZ, COMPONENT_FUSEDQUARTZ_INTERNALNAME);
    }

    public static void createComponentMagneticTapeItem(int itemID)
    {
        MadScience.logger.info("-Component Magnetic Tape");
        COMPONENT_MAGNETICTAPE = (ComponentMagneticTape) new ComponentMagneticTape(itemID).setUnlocalizedName(COMPONENT_MAGNETICTAPE_INTERNALNAME);
        GameRegistry.registerItem(COMPONENT_MAGNETICTAPE, COMPONENT_MAGNETICTAPE_INTERNALNAME);
    }

    public static void createComponentPowerSupplyItem(int itemID)
    {
        MadScience.logger.info("-Component Power Supply");
        COMPONENT_POWERSUPPLY = (ComponentPowerSupply) new ComponentPowerSupply(itemID).setUnlocalizedName(COMPONENT_POWERSUPPLY_INTERNALNAME);
        GameRegistry.registerItem(COMPONENT_POWERSUPPLY, COMPONENT_POWERSUPPLY_INTERNALNAME);
    }

    public static void createComponentRAMItem(int itemID)
    {
        MadScience.logger.info("-Component RAM");
        COMPONENT_RAM = (ComponentRAM) new ComponentRAM(itemID).setUnlocalizedName(COMPONENT_RAM_INTERNALNAME);
        GameRegistry.registerItem(COMPONENT_RAM, COMPONENT_RAM_INTERNALNAME);
    }

    public static void createComponentScreenItem(int itemID)
    {
        MadScience.logger.info("-Component Screen");
        COMPONENT_SCREEN = (ComponentScreen) new ComponentScreen(itemID).setUnlocalizedName(COMPONENT_SCREEN_INTERNALNAME);
        GameRegistry.registerItem(COMPONENT_SCREEN, COMPONENT_SCREEN_INTERNALNAME);
    }

    public static void createComponentSiliconWaferItem(int itemID)
    {
        MadScience.logger.info("-Component Silicon Wafer");
        COMPONENT_SILICONWAFER = (ComponentSiliconWafer) new ComponentSiliconWafer(itemID).setUnlocalizedName(COMPONENT_SILICONWAFER_INTERNALNAME);
        GameRegistry.registerItem(COMPONENT_SILICONWAFER, COMPONENT_SILICONWAFER_INTERNALNAME);
    }

    public static void createComponentTransistorItem(int itemID)
    {
        MadScience.logger.info("-Component Transistor");
        COMPONENT_TRANSISTOR = (ComponentTransistor) new ComponentTransistor(itemID).setUnlocalizedName(COMPONENT_TRANSISTOR_INTERNALNAME);
        GameRegistry.registerItem(COMPONENT_TRANSISTOR, COMPONENT_TRANSISTOR_INTERNALNAME);
    }

    public static void createComponentThumperItem(int itemID)
    {
        MadScience.logger.info("-Component Thumper");
        COMPONENT_THUMPER = (ComponentThumper) new ComponentThumper(itemID).setUnlocalizedName(COMPONENT_THUMPER_INTERNALNAME);
        GameRegistry.registerItem(COMPONENT_THUMPER, COMPONENT_THUMPER_INTERNALNAME);
    }

    public static void createComponentEnderslimeItem(int itemID)
    {
        MadScience.logger.info("-Component Enderslime");
        COMPONENT_ENDERSLIME = (ComponentEnderslime) new ComponentEnderslime(itemID).setUnlocalizedName(COMPONENT_ENDERSLIME_INTERNALNAME);
        GameRegistry.registerItem(COMPONENT_ENDERSLIME, COMPONENT_ENDERSLIME_INTERNALNAME);      
    }
    
    public static void createComponentPulseRifleBarrelItem(int itemID)
    {
        // M41A Barrel
        MadScience.logger.info("-Component Pulse Rifle Barrel");
        COMPONENT_PULSERIFLEBARREL = (ComponentPulseRifleBarrel) new ComponentPulseRifleBarrel(itemID).setUnlocalizedName(COMPONENT_PULSERIFLEBARREL_INTERNALNAME);
        GameRegistry.registerItem(COMPONENT_PULSERIFLEBARREL, COMPONENT_PULSERIFLEBARREL_INTERNALNAME);
        MadScience.proxy.registerRenderingHandler(itemID);
    }

    public static void createComponentPulseRifleBoltItem(int itemID)
    {
        // M41A Bolt
        MadScience.logger.info("-Component Pulse Rifle Bolt");
        COMPONENT_PULSERIFLEBOLT = (ComponentPulseRifleBolt) new ComponentPulseRifleBolt(itemID).setUnlocalizedName(COMPONENT_PULSERIFLEBOLT_INTERNALNAME);
        GameRegistry.registerItem(COMPONENT_PULSERIFLEBOLT, COMPONENT_PULSERIFLEBOLT_INTERNALNAME);
        MadScience.proxy.registerRenderingHandler(itemID);
    }

    public static void createComponentPulseRifleReceiverItem(int itemID)
    {
        // M41A Receiver
        MadScience.logger.info("-Component Pulse Rifle Receiver");
        COMPONENT_PULSERIFLERECIEVER = (ComponentPulseRifleReceiver) new ComponentPulseRifleReceiver(itemID).setUnlocalizedName(COMPONENT_PULSERIFLERECIEVER_INTERNALNAME);
        GameRegistry.registerItem(COMPONENT_PULSERIFLERECIEVER, COMPONENT_PULSERIFLERECIEVER_INTERNALNAME);
        MadScience.proxy.registerRenderingHandler(itemID);
    }

    public static void createComponentPulseRifleTriggerItem(int itemID)
    {
        // M41A Trigger
        MadScience.logger.info("-Component Pulse Rifle Trigger");
        COMPONENT_PULSERIFLETRIGGER = (ComponentPulseRifleTrigger) new ComponentPulseRifleTrigger(itemID).setUnlocalizedName(COMPONENT_PULSERIFLETRIGGER_INTERNALNAME);
        GameRegistry.registerItem(COMPONENT_PULSERIFLETRIGGER, COMPONENT_PULSERIFLETRIGGER_INTERNALNAME);
        MadScience.proxy.registerRenderingHandler(itemID);
    }

    public static void createComponentPulseRifleBulletCasingItem(int itemID)
    {
        // M41A Bullet Casing
        MadScience.logger.info("-Component Pulse Rifle Bullet Casing");
        COMPONENT_PULSERIFLEBULLETCASING = (ComponentPulseRifleBulletCasing) new ComponentPulseRifleBulletCasing(itemID).setUnlocalizedName(COMPONENT_PULSERIFLEBULLETCASING_INTERNALNAME);
        GameRegistry.registerItem(COMPONENT_PULSERIFLEBULLETCASING, COMPONENT_PULSERIFLEBULLETCASING_INTERNALNAME);
        MadScience.proxy.registerRenderingHandler(itemID);
    }

    public static void createComponentPulseRifleGrenadeCasingItem(int itemID)
    {
        // M41A Grenade Casing
        MadScience.logger.info("-Component Pulse Rifle Grenade Casing");
        COMPONENT_PULSERIFLEGRENADECASING = (ComponentPulseRifleGrenadeCasing) new ComponentPulseRifleGrenadeCasing(itemID).setUnlocalizedName(COMPONENT_PULSERIFLEGRENADECASING_INTERNALNAME);
        GameRegistry.registerItem(COMPONENT_PULSERIFLEGRENADECASING, COMPONENT_PULSERIFLEGRENADECASING_INTERNALNAME);
        MadScience.proxy.registerRenderingHandler(itemID);
    }
}

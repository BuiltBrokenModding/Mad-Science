package madscience;

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
import madscience.items.components.pulserifle.ComponentPulseRifleReciever;
import madscience.items.components.pulserifle.ComponentPulseRifleTrigger;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import cpw.mods.fml.common.registry.GameRegistry;

public class MadComponents
{
    // Case
    public static ComponentCase COMPONENT_CASE;
    public static final String COMPONENT_CASE_INTERNALNAME = "componentCase";

    // CPU
    public static ComponentCPU COMPONENT_CPU;
    public static final String COMPONENT_CPU_INTERNALNAME = "componentCPU";

    // Fan
    public static ComponentFan COMPONENT_FAN;
    public static final String COMPONENT_FAN_INTERNALNAME = "componentFan";

    // Power Supply
    public static ComponentPowerSupply COMPONENT_POWERSUPPLY;
    public static final String COMPONENT_POWERSUPPLY_INTERNALNAME = "componentPowerSupply";

    // RAM
    public static ComponentRAM COMPONENT_RAM;
    public static final String COMPONENT_RAM_INTERNALNAME = "componentRAM";

    // Silicon Wafer
    public static ComponentSiliconWafer COMPONENT_SILICONWAFER;
    public static final String COMPONENT_SILICONWAFER_INTERNALNAME = "componentSiliconWafer";

    // Computer
    public static ComponentComputer COMPONENT_COMPUTER;
    public static final String COMPONENT_COMPUTER_INTERNALNAME = "componentComputer";

    // Screen
    public static ComponentScreen COMPONENT_SCREEN;
    public static final String COMPONENT_SCREEN_INTERNALNAME = "componentScreen";

    // Transistor
    public static ComponentTransistor COMPONENT_TRANSISTOR;
    public static final String COMPONENT_TRANSISTOR_INTERNALNAME = "componentTransistor";

    // Fused Quartz
    public static ComponentFusedQuartz COMPONENT_FUSEDQUARTZ;
    public static final String COMPONENT_FUSEDQUARTZ_INTERNALNAME = "componentFusedQuartz";

    // Magnetic Tape
    public static ComponentMagneticTape COMPONENT_MAGNETICTAPE;
    public static final String COMPONENT_MAGNETICTAPE_INTERNALNAME = "componentMagneticTape";
    
    // Thumper
    public static ComponentThumper COMPONENT_THUMPER;
    public static final String COMPONENT_THUMPER_INTERNALNAME = "componentThumper";
    
    // Ender Slime
    public static ComponentEnderslime COMPONENT_ENDERSLIME;
    public static final String COMPONENT_ENDERSLIME_INTERNALNAME = "componentEnderslime";
    
    // M41A Barrel
    public static ComponentPulseRifleBarrel COMPONENT_PULSERIFLEBARREL;
    public static final String COMPONENT_PULSERIFLEBARREL_INTERNALNAME = "componentPulseRifleBarrel";
    
    // M41A Bolt
    public static ComponentPulseRifleBolt COMPONENT_PULSERIFLEBOLT;
    public static final String COMPONENT_PULSERIFLEBOLT_INTERNALNAME = "componentPulseRifleBolt";
    
    // M41A Receiver
    public static ComponentPulseRifleReciever COMPONENT_PULSERIFLERECIEVER;
    public static final String COMPONENT_PULSERIFLERECIEVER_INTERNALNAME = "componentPulseRifleReciever";
    
    // M41A Trigger
    public static ComponentPulseRifleTrigger COMPONENT_PULSERIFLETRIGGER;
    public static final String COMPONENT_PULSERIFLETRIGGER_INTERNALNAME = "componentPulseRifleTrigger";
    
    public static void createComponentCaseItem(int itemID)
    {
        COMPONENT_CASE = (ComponentCase) new ComponentCase(itemID).setUnlocalizedName(COMPONENT_CASE_INTERNALNAME);
        GameRegistry.registerItem(COMPONENT_CASE, COMPONENT_CASE_INTERNALNAME);
    }

    public static void createComponentComputerItem(int itemID)
    {
        COMPONENT_COMPUTER = (ComponentComputer) new ComponentComputer(itemID).setUnlocalizedName(COMPONENT_COMPUTER_INTERNALNAME);
        GameRegistry.registerItem(COMPONENT_COMPUTER, COMPONENT_COMPUTER_INTERNALNAME);
    }

    public static void createComponentCPUItem(int itemID)
    {
        COMPONENT_CPU = (ComponentCPU) new ComponentCPU(itemID).setUnlocalizedName(COMPONENT_CPU_INTERNALNAME);
        GameRegistry.registerItem(COMPONENT_CPU, COMPONENT_CPU_INTERNALNAME);
    }

    public static void createComponentFanItem(int itemID)
    {
        COMPONENT_FAN = (ComponentFan) new ComponentFan(itemID).setUnlocalizedName(COMPONENT_FAN_INTERNALNAME);
        GameRegistry.registerItem(COMPONENT_FAN, COMPONENT_FAN_INTERNALNAME);
    }

    public static void createComponentFusedQuartzItem(int itemID)
    {
        COMPONENT_FUSEDQUARTZ = (ComponentFusedQuartz) new ComponentFusedQuartz(itemID).setUnlocalizedName(COMPONENT_FUSEDQUARTZ_INTERNALNAME);
        GameRegistry.registerItem(COMPONENT_FUSEDQUARTZ, COMPONENT_FUSEDQUARTZ_INTERNALNAME);
    }

    public static void createComponentMagneticTapeItem(int itemID)
    {
        COMPONENT_MAGNETICTAPE = (ComponentMagneticTape) new ComponentMagneticTape(itemID).setUnlocalizedName(COMPONENT_MAGNETICTAPE_INTERNALNAME);
        GameRegistry.registerItem(COMPONENT_MAGNETICTAPE, COMPONENT_MAGNETICTAPE_INTERNALNAME);
    }

    public static void createComponentPowerSupplyItem(int itemID)
    {
        COMPONENT_POWERSUPPLY = (ComponentPowerSupply) new ComponentPowerSupply(itemID).setUnlocalizedName(COMPONENT_POWERSUPPLY_INTERNALNAME);
        GameRegistry.registerItem(COMPONENT_POWERSUPPLY, COMPONENT_POWERSUPPLY_INTERNALNAME);
    }

    public static void createComponentRAMItem(int itemID)
    {
        COMPONENT_RAM = (ComponentRAM) new ComponentRAM(itemID).setUnlocalizedName(COMPONENT_RAM_INTERNALNAME);
        GameRegistry.registerItem(COMPONENT_RAM, COMPONENT_RAM_INTERNALNAME);
    }

    public static void createComponentScreenItem(int itemID)
    {
        COMPONENT_SCREEN = (ComponentScreen) new ComponentScreen(itemID).setUnlocalizedName(COMPONENT_SCREEN_INTERNALNAME);
        GameRegistry.registerItem(COMPONENT_SCREEN, COMPONENT_SCREEN_INTERNALNAME);
    }

    public static void createComponentSiliconWaferItem(int itemID)
    {
        COMPONENT_SILICONWAFER = (ComponentSiliconWafer) new ComponentSiliconWafer(itemID).setUnlocalizedName(COMPONENT_SILICONWAFER_INTERNALNAME);
        GameRegistry.registerItem(COMPONENT_SILICONWAFER, COMPONENT_SILICONWAFER_INTERNALNAME);
    }

    public static void createComponentTransistorItem(int itemID)
    {
        COMPONENT_TRANSISTOR = (ComponentTransistor) new ComponentTransistor(itemID).setUnlocalizedName(COMPONENT_TRANSISTOR_INTERNALNAME);
        GameRegistry.registerItem(COMPONENT_TRANSISTOR, COMPONENT_TRANSISTOR_INTERNALNAME);
    }

    public static void createComponentThumperItem(int itemID)
    {
        COMPONENT_THUMPER = (ComponentThumper) new ComponentThumper(itemID).setUnlocalizedName(COMPONENT_THUMPER_INTERNALNAME);
        GameRegistry.registerItem(COMPONENT_THUMPER, COMPONENT_THUMPER_INTERNALNAME);
    }

    public static void createComponentEnderslimeItem(int itemID)
    {
        COMPONENT_ENDERSLIME = (ComponentEnderslime) new ComponentEnderslime(itemID).setUnlocalizedName(COMPONENT_ENDERSLIME_INTERNALNAME);
        GameRegistry.registerItem(COMPONENT_ENDERSLIME, COMPONENT_ENDERSLIME_INTERNALNAME);      
        MadScience.proxy.registerRenderingHandler(itemID);
    }
    
    public static void createComponentPulseRifleBarrelItem(int itemID)
    {
        // M41A Barrel
        COMPONENT_PULSERIFLEBARREL = (ComponentPulseRifleBarrel) new ComponentPulseRifleBarrel(itemID).setUnlocalizedName(COMPONENT_PULSERIFLEBARREL_INTERNALNAME);
        GameRegistry.registerItem(COMPONENT_PULSERIFLEBARREL, COMPONENT_PULSERIFLEBARREL_INTERNALNAME);
    }

    public static void createComponentPulseRifleBoltItem(int itemID)
    {
        // M41A  Bolt
        COMPONENT_PULSERIFLEBOLT = (ComponentPulseRifleBolt) new ComponentPulseRifleBolt(itemID).setUnlocalizedName(COMPONENT_PULSERIFLEBOLT_INTERNALNAME);
        GameRegistry.registerItem(COMPONENT_PULSERIFLEBOLT, COMPONENT_PULSERIFLEBOLT_INTERNALNAME);
    }

    public static void createComponentPulseRifleReceiverItem(int itemID)
    {
        // M41A Receiver
        COMPONENT_PULSERIFLERECIEVER = (ComponentPulseRifleReciever) new ComponentPulseRifleReciever(itemID).setUnlocalizedName(COMPONENT_PULSERIFLERECIEVER_INTERNALNAME);
        GameRegistry.registerItem(COMPONENT_PULSERIFLERECIEVER, COMPONENT_PULSERIFLERECIEVER_INTERNALNAME);
    }

    public static void createComponentPulseRifleTriggerItem(int itemID)
    {
        // M41A Trigger
        COMPONENT_PULSERIFLETRIGGER = (ComponentPulseRifleTrigger) new ComponentPulseRifleTrigger(itemID).setUnlocalizedName(COMPONENT_PULSERIFLETRIGGER_INTERNALNAME);
        GameRegistry.registerItem(COMPONENT_PULSERIFLETRIGGER, COMPONENT_PULSERIFLETRIGGER_INTERNALNAME);
    }
}

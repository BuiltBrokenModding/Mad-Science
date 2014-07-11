package madscience;

import madscience.items.circuits.CircuitComparator;
import madscience.items.circuits.CircuitDiamond;
import madscience.items.circuits.CircuitEmerald;
import madscience.items.circuits.CircuitEnderEye;
import madscience.items.circuits.CircuitEnderPearl;
import madscience.items.circuits.CircuitGlowstone;
import madscience.items.circuits.CircuitRedstone;
import madscience.items.circuits.CircuitSpiderEye;
import cpw.mods.fml.common.registry.GameRegistry;

class MadCircuits
{
    // Circuit Comparator
    static CircuitComparator CIRCUIT_COMPARATOR;
    private static final String CIRCUIT_COMPARATOR_INTERNALNAME = "circuitComparator";

    // Circuit Diamond
    static CircuitDiamond CIRCUIT_DIAMOND;
    private static final String CIRCUIT_DIAMOND_INTERNALNAME = "circuitDiamond";

    // Circuit Emerald
    static CircuitEmerald CIRCUIT_EMERALD;
    private static final String CIRCUIT_EMERALD_INTERNALNAME = "circuitEmerald";

    // Circuit Ender Eye
    static CircuitEnderEye CIRCUIT_ENDEREYE;
    private static final String CIRCUIT_ENDEREYE_INTERNALNAME = "circuitEnderEye";

    // Circuit Ender Pearl
    static CircuitEnderPearl CIRCUIT_ENDERPEARL;
    private static final String CIRCUIT_ENDERPEARL_INTERNALNAME = "circuitEnderPearl";

    // Circuit Glowstone
    static CircuitGlowstone CIRCUIT_GLOWSTONE;
    private static final String CIRCUIT_GLOWSTONE_INTERNALNAME = "circuitGlowstone";

    // Circuit Redstone
    static CircuitRedstone CIRCUIT_REDSTONE;
    private static final String CIRCUIT_REDSTONE_INTERNALNAME = "circuitRedstone";

    // Circuit Spider Eye
    static CircuitSpiderEye CIRCUIT_SPIDEREYE;
    private static final String CIRCUIT_SPIDEREYE_INTERNALNAME = "circuitSpiderEye";
    
    
    static void createCircuitComparatorItem(int itemID)
    {
        MadScience.logger.info("-Circuit Comparator");
        CIRCUIT_COMPARATOR = (CircuitComparator) new CircuitComparator(itemID).setUnlocalizedName(CIRCUIT_COMPARATOR_INTERNALNAME);
        GameRegistry.registerItem(CIRCUIT_COMPARATOR, CIRCUIT_COMPARATOR_INTERNALNAME);
    }

    static void createCircuitDiamondItem(int itemID)
    {
        MadScience.logger.info("-Circuit Diamond");
        CIRCUIT_DIAMOND = (CircuitDiamond) new CircuitDiamond(itemID).setUnlocalizedName(CIRCUIT_DIAMOND_INTERNALNAME);
        GameRegistry.registerItem(CIRCUIT_DIAMOND, CIRCUIT_DIAMOND_INTERNALNAME);
    }

    static void createCircuitEmeraldItem(int itemID)
    {
        MadScience.logger.info("-Circuit Emerald");
        CIRCUIT_EMERALD = (CircuitEmerald) new CircuitEmerald(itemID).setUnlocalizedName(CIRCUIT_EMERALD_INTERNALNAME);
        GameRegistry.registerItem(CIRCUIT_EMERALD, CIRCUIT_EMERALD_INTERNALNAME);
    }

    static void createCircuitEnderEyeItem(int itemID)
    {
        MadScience.logger.info("-Circuit Ender Eye");
        CIRCUIT_ENDEREYE = (CircuitEnderEye) new CircuitEnderEye(itemID).setUnlocalizedName(CIRCUIT_ENDEREYE_INTERNALNAME);
        GameRegistry.registerItem(CIRCUIT_ENDEREYE, CIRCUIT_ENDEREYE_INTERNALNAME);
    }

    static void createCircuitEnderPerlItem(int itemID)
    {
        MadScience.logger.info("-Circuit Ender Perl");
        CIRCUIT_ENDERPEARL = (CircuitEnderPearl) new CircuitEnderPearl(itemID).setUnlocalizedName(CIRCUIT_ENDERPEARL_INTERNALNAME);
        GameRegistry.registerItem(CIRCUIT_ENDERPEARL, CIRCUIT_ENDERPEARL_INTERNALNAME);
    }


    static void createCircuitGlowstoneItem(int itemID)
    {
        MadScience.logger.info("-Circuit Glowstone");
        CIRCUIT_GLOWSTONE = (CircuitGlowstone) new CircuitGlowstone(itemID).setUnlocalizedName(CIRCUIT_GLOWSTONE_INTERNALNAME);
        GameRegistry.registerItem(CIRCUIT_GLOWSTONE, CIRCUIT_GLOWSTONE_INTERNALNAME);
    }


    static void createCircuitRedstoneItem(int itemID)
    {
        MadScience.logger.info("-Circuit Redstone");
        CIRCUIT_REDSTONE = (CircuitRedstone) new CircuitRedstone(itemID).setUnlocalizedName(CIRCUIT_REDSTONE_INTERNALNAME);
        GameRegistry.registerItem(CIRCUIT_REDSTONE, CIRCUIT_REDSTONE_INTERNALNAME);
    }

    static void createCircuitSpiderEyeItem(int itemID)
    {
        MadScience.logger.info("-Circuit Spider Eye");
        CIRCUIT_SPIDEREYE = (CircuitSpiderEye) new CircuitSpiderEye(itemID).setUnlocalizedName(CIRCUIT_SPIDEREYE_INTERNALNAME);
        GameRegistry.registerItem(CIRCUIT_SPIDEREYE, CIRCUIT_SPIDEREYE_INTERNALNAME);
    }
}

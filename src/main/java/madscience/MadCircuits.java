package madscience;

import madscience.factory.mod.MadMod;
import madscience.item.circuits.CircuitComparator;
import madscience.item.circuits.CircuitDiamond;
import madscience.item.circuits.CircuitEmerald;
import madscience.item.circuits.CircuitEnderEye;
import madscience.item.circuits.CircuitEnderPearl;
import madscience.item.circuits.CircuitGlowstone;
import madscience.item.circuits.CircuitRedstone;
import madscience.item.circuits.CircuitSpiderEye;
import cpw.mods.fml.common.registry.GameRegistry;

public class MadCircuits
{
    // Circuit Comparator
    public static CircuitComparator CIRCUIT_COMPARATOR;
    private static final String CIRCUIT_COMPARATOR_INTERNALNAME = "circuitComparator";

    // Circuit Diamond
    public static CircuitDiamond CIRCUIT_DIAMOND;
    private static final String CIRCUIT_DIAMOND_INTERNALNAME = "circuitDiamond";

    // Circuit Emerald
    public static CircuitEmerald CIRCUIT_EMERALD;
    private static final String CIRCUIT_EMERALD_INTERNALNAME = "circuitEmerald";

    // Circuit Ender Eye
    public static CircuitEnderEye CIRCUIT_ENDEREYE;
    private static final String CIRCUIT_ENDEREYE_INTERNALNAME = "circuitEnderEye";

    // Circuit Ender Pearl
    public static CircuitEnderPearl CIRCUIT_ENDERPEARL;
    private static final String CIRCUIT_ENDERPEARL_INTERNALNAME = "circuitEnderPearl";

    // Circuit Glowstone
    public static CircuitGlowstone CIRCUIT_GLOWSTONE;
    private static final String CIRCUIT_GLOWSTONE_INTERNALNAME = "circuitGlowstone";

    // Circuit Redstone
    public static CircuitRedstone CIRCUIT_REDSTONE;
    private static final String CIRCUIT_REDSTONE_INTERNALNAME = "circuitRedstone";

    // Circuit Spider Eye
    public static CircuitSpiderEye CIRCUIT_SPIDEREYE;
    private static final String CIRCUIT_SPIDEREYE_INTERNALNAME = "circuitSpiderEye";
    
    
    static void createCircuitComparatorItem(int itemID)
    {
        MadMod.log().info("-Circuit Comparator");
        CIRCUIT_COMPARATOR = (CircuitComparator) new CircuitComparator(itemID).setUnlocalizedName(CIRCUIT_COMPARATOR_INTERNALNAME);
        GameRegistry.registerItem(CIRCUIT_COMPARATOR, CIRCUIT_COMPARATOR_INTERNALNAME);
    }

    static void createCircuitDiamondItem(int itemID)
    {
        MadMod.log().info("-Circuit Diamond");
        CIRCUIT_DIAMOND = (CircuitDiamond) new CircuitDiamond(itemID).setUnlocalizedName(CIRCUIT_DIAMOND_INTERNALNAME);
        GameRegistry.registerItem(CIRCUIT_DIAMOND, CIRCUIT_DIAMOND_INTERNALNAME);
    }

    static void createCircuitEmeraldItem(int itemID)
    {
        MadMod.log().info("-Circuit Emerald");
        CIRCUIT_EMERALD = (CircuitEmerald) new CircuitEmerald(itemID).setUnlocalizedName(CIRCUIT_EMERALD_INTERNALNAME);
        GameRegistry.registerItem(CIRCUIT_EMERALD, CIRCUIT_EMERALD_INTERNALNAME);
    }

    static void createCircuitEnderEyeItem(int itemID)
    {
        MadMod.log().info("-Circuit Ender Eye");
        CIRCUIT_ENDEREYE = (CircuitEnderEye) new CircuitEnderEye(itemID).setUnlocalizedName(CIRCUIT_ENDEREYE_INTERNALNAME);
        GameRegistry.registerItem(CIRCUIT_ENDEREYE, CIRCUIT_ENDEREYE_INTERNALNAME);
    }

    static void createCircuitEnderPerlItem(int itemID)
    {
        MadMod.log().info("-Circuit Ender Perl");
        CIRCUIT_ENDERPEARL = (CircuitEnderPearl) new CircuitEnderPearl(itemID).setUnlocalizedName(CIRCUIT_ENDERPEARL_INTERNALNAME);
        GameRegistry.registerItem(CIRCUIT_ENDERPEARL, CIRCUIT_ENDERPEARL_INTERNALNAME);
    }


    static void createCircuitGlowstoneItem(int itemID)
    {
        MadMod.log().info("-Circuit Glowstone");
        CIRCUIT_GLOWSTONE = (CircuitGlowstone) new CircuitGlowstone(itemID).setUnlocalizedName(CIRCUIT_GLOWSTONE_INTERNALNAME);
        GameRegistry.registerItem(CIRCUIT_GLOWSTONE, CIRCUIT_GLOWSTONE_INTERNALNAME);
    }


    static void createCircuitRedstoneItem(int itemID)
    {
        MadMod.log().info("-Circuit Redstone");
        CIRCUIT_REDSTONE = (CircuitRedstone) new CircuitRedstone(itemID).setUnlocalizedName(CIRCUIT_REDSTONE_INTERNALNAME);
        GameRegistry.registerItem(CIRCUIT_REDSTONE, CIRCUIT_REDSTONE_INTERNALNAME);
    }

    static void createCircuitSpiderEyeItem(int itemID)
    {
        MadMod.log().info("-Circuit Spider Eye");
        CIRCUIT_SPIDEREYE = (CircuitSpiderEye) new CircuitSpiderEye(itemID).setUnlocalizedName(CIRCUIT_SPIDEREYE_INTERNALNAME);
        GameRegistry.registerItem(CIRCUIT_SPIDEREYE, CIRCUIT_SPIDEREYE_INTERNALNAME);
    }
}

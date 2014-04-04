package madscience;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;
import madscience.items.circuits.CircuitComparator;
import madscience.items.circuits.CircuitDiamond;
import madscience.items.circuits.CircuitEmerald;
import madscience.items.circuits.CircuitEnderEye;
import madscience.items.circuits.CircuitEnderPearl;
import madscience.items.circuits.CircuitGlowstone;
import madscience.items.circuits.CircuitRedstone;
import madscience.items.circuits.CircuitSpiderEye;

public class MadCircuits
{
    // Circuit Comparator
    public static CircuitComparator CIRCUIT_COMPARATOR;
    public static final String CIRCUIT_COMPARATOR_INTERNALNAME = "circuitComparator";

    // Circuit Diamond
    public static CircuitDiamond CIRCUIT_DIAMOND;
    public static final String CIRCUIT_DIAMOND_INTERNALNAME = "circuitDiamond";

    // Circuit Emerald
    public static CircuitEmerald CIRCUIT_EMERALD;
    public static final String CIRCUIT_EMERALD_INTERNALNAME = "circuitEmerald";

    // Circuit Ender Eye
    public static CircuitEnderEye CIRCUIT_ENDEREYE;
    public static final String CIRCUIT_ENDEREYE_INTERNALNAME = "circuitEnderEye";

    // Circuit Ender Pearl
    public static CircuitEnderPearl CIRCUIT_ENDERPEARL;
    public static final String CIRCUIT_ENDERPEARL_INTERNALNAME = "circuitEnderPearl";

    // Circuit Glowstone
    public static CircuitGlowstone CIRCUIT_GLOWSTONE;
    public static final String CIRCUIT_GLOWSTONE_INTERNALNAME = "circuitGlowstone";

    // Circuit Redstone
    public static CircuitRedstone CIRCUIT_REDSTONE;
    public static final String CIRCUIT_REDSTONE_INTERNALNAME = "circuitRedstone";

    // Circuit Spider Eye
    public static CircuitSpiderEye CIRCUIT_SPIDEREYE;
    public static final String CIRCUIT_SPIDEREYE_INTERNALNAME = "circuitSpiderEye";
    
    
    public static void createCircuitComparatorItem(int itemID)
    {
        MadScience.logger.info("-Circuit Comparator");
        CIRCUIT_COMPARATOR = (CircuitComparator) new CircuitComparator(itemID).setUnlocalizedName(CIRCUIT_COMPARATOR_INTERNALNAME);
        GameRegistry.registerItem(CIRCUIT_COMPARATOR, CIRCUIT_COMPARATOR_INTERNALNAME);
    }

    public static void createCircuitDiamondItem(int itemID)
    {
        MadScience.logger.info("-Circuit Diamond");
        CIRCUIT_DIAMOND = (CircuitDiamond) new CircuitDiamond(itemID).setUnlocalizedName(CIRCUIT_DIAMOND_INTERNALNAME);
        GameRegistry.registerItem(CIRCUIT_DIAMOND, CIRCUIT_DIAMOND_INTERNALNAME);
    }

    public static void createCircuitEmeraldItem(int itemID)
    {
        MadScience.logger.info("-Circuit Emerald");
        CIRCUIT_EMERALD = (CircuitEmerald) new CircuitEmerald(itemID).setUnlocalizedName(CIRCUIT_EMERALD_INTERNALNAME);
        GameRegistry.registerItem(CIRCUIT_EMERALD, CIRCUIT_EMERALD_INTERNALNAME);
    }

    public static void createCircuitEnderEyeItem(int itemID)
    {
        MadScience.logger.info("-Circuit Ender Eye");
        CIRCUIT_ENDEREYE = (CircuitEnderEye) new CircuitEnderEye(itemID).setUnlocalizedName(CIRCUIT_ENDEREYE_INTERNALNAME);
        GameRegistry.registerItem(CIRCUIT_ENDEREYE, CIRCUIT_ENDEREYE_INTERNALNAME);
    }

    public static void createCircuitEnderPerlItem(int itemID)
    {
        MadScience.logger.info("-Circuit Ender Perl");
        CIRCUIT_ENDERPEARL = (CircuitEnderPearl) new CircuitEnderPearl(itemID).setUnlocalizedName(CIRCUIT_ENDERPEARL_INTERNALNAME);
        GameRegistry.registerItem(CIRCUIT_ENDERPEARL, CIRCUIT_ENDERPEARL_INTERNALNAME);
    }


    public static void createCircuitGlowstoneItem(int itemID)
    {
        MadScience.logger.info("-Circuit Glowstone");
        CIRCUIT_GLOWSTONE = (CircuitGlowstone) new CircuitGlowstone(itemID).setUnlocalizedName(CIRCUIT_GLOWSTONE_INTERNALNAME);
        GameRegistry.registerItem(CIRCUIT_GLOWSTONE, CIRCUIT_GLOWSTONE_INTERNALNAME);
    }


    public static void createCircuitRedstoneItem(int itemID)
    {
        MadScience.logger.info("-Circuit Redstone");
        CIRCUIT_REDSTONE = (CircuitRedstone) new CircuitRedstone(itemID).setUnlocalizedName(CIRCUIT_REDSTONE_INTERNALNAME);
        GameRegistry.registerItem(CIRCUIT_REDSTONE, CIRCUIT_REDSTONE_INTERNALNAME);
    }

    public static void createCircuitSpiderEyeItem(int itemID)
    {
        MadScience.logger.info("-Circuit Spider Eye");
        CIRCUIT_SPIDEREYE = (CircuitSpiderEye) new CircuitSpiderEye(itemID).setUnlocalizedName(CIRCUIT_SPIDEREYE_INTERNALNAME);
        GameRegistry.registerItem(CIRCUIT_SPIDEREYE, CIRCUIT_SPIDEREYE_INTERNALNAME);
    }
}

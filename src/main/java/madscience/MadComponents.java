package madscience;

import java.awt.Panel;

import madscience.metaitems.MainframeComponents;
import madscience.metaitems.MainframeComponentsMetadata;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.registry.GameRegistry;

public class MadComponents
{
    // --------------
    // METADATA ITEMS
    // --------------

    // Metadata and component item.
    public static MainframeComponents MAINFRAME_COMPONENTS_METAITEM;
    public static String MAINFRAME_COMPONENTS_INTERNALNAME = "madComponents";

    // Case
    public static int COMPONENT_CASE_METAID;
    public static final String COMPONENT_CASE_INTERNALNAME = "componentCase";

    // CPU
    public static int COMPONENT_CPU_METAID;
    public static final String COMPONENT_CPU_INTERNALNAME = "componentCPU";

    // Fan
    public static int COMPONENT_FAN_METAID;
    public static final String COMPONENT_FAN_INTERNALNAME = "componentFan";

    // Power Supply
    public static int COMPONENT_POWERSUPPLY_METAID;
    public static final String COMPONENT_POWERSUPPLY_INTERNALNAME = "componentPowerSupply";

    // RAM
    public static int COMPONENT_RAM_METAID;
    public static final String COMPONENT_RAM_INTERNALNAME = "componentRAM";

    // Silicon Wafer
    public static int COMPONENT_SILICONWAFER_METAID;
    public static final String COMPONENT_SILICONWAFER_INTERNALNAME = "componentSiliconWafer";

    // Computer
    public static int COMPONENT_COMPUTER_METAID;
    public static final String COMPONENT_COMPUTER_INTERNALNAME = "componentComputer";

    // Screen
    public static int COMPONENT_SCREEN_METAID;
    public static final String COMPONENT_SCREEN_INTERNALNAME = "componentScreen";

    // Transistor
    public static int COMPONENT_TRANSISTOR_METAID;
    public static final String COMPONENT_TRANSISTOR_INTERNALNAME = "componentTransistor";

    // Fused Quartz
    public static int COMPONENT_FUSEDQUARTZ_METAID;
    public static final String COMPONENT_FUSEDQUARTZ_INTERNALNAME = "componentFusedQuartz";
    
    // Magnetic Tape
    public static int COMPONENT_MAGNETICTAPE_METAID;
    public static final String COMPONENT_MAGNETICTAPE_INTERNALNAME = "componentMagneticTape";

    // Circuit Comparator
    public static int CIRCUIT_COMPARATOR_METAID;
    public static final String CIRCUIT_COMPARATOR_INTERNALNAME = "circuitComparator";

    // Circuit Diamond
    public static int CIRCUIT_DIAMOND_METAID;
    public static final String CIRCUIT_DIAMOND_INTERNALNAME = "circuitDiamond";

    // Circuit Emerald
    public static int CIRCUIT_EMERALD_METAID;
    public static final String CIRCUIT_EMERALD_INTERNALNAME = "circuitEmerald";

    // Circuit Ender Eye
    public static int CIRCUIT_ENDEREYE_METAID;
    public static final String CIRCUIT_ENDEREYE_INTERNALNAME = "circuitEnderEye";

    // Circuit Ender Pearl
    public static int CIRCUIT_ENDERPEARL_METAID;
    public static final String CIRCUIT_ENDERPEARL_INTERNALNAME = "circuitEnderPearl";

    // Circuit Glowstone
    public static int CIRCUIT_GLOWSTONE_METAID;
    public static final String CIRCUIT_GLOWSTONE_INTERNALNAME = "circuitGlowstone";

    // Circuit Redstone
    public static int CIRCUIT_REDSTONE_METAID;
    public static final String CIRCUIT_REDSTONE_INTERNALNAME = "circuitRedstone";

    // Circuit Spider Eye
    public static int CIRCUIT_SPIDEREYE_METAID;
    public static final String CIRCUIT_SPIDEREYE_INTERNALNAME = "circuitSpiderEye";

    // --------------
    // METADATA ITEMS
    // --------------

    // Mainframe Components
    public static void createMainframeComponents(int itemID)
    {
        // Create instance of this metadata item which contains many items.
        MAINFRAME_COMPONENTS_METAITEM = new MainframeComponents(itemID);
        GameRegistry.registerItem(MAINFRAME_COMPONENTS_METAITEM, "Component");
        //LanguageRegistry.addName(MAINFRAME_COMPONENTS_METAITEM, MAINFRAME_COMPONENTS_INTERNALNAME);

        // Add the names of our meta items to the game registry in the proper
        // order.
        for (int i = 0; i < MainframeComponentsMetadata.getInternalNameFromDamage.length; i++)
        {
            ItemStack tempItem = new ItemStack(MAINFRAME_COMPONENTS_METAITEM, 1, i);
            //LanguageRegistry.addName(tempItem.copy(), MainframeComponentsMetadata.getDisplayNameFromDamage[i]);

            // Case
            if (tempItem.getUnlocalizedName() == COMPONENT_CASE_INTERNALNAME)
            {
                COMPONENT_CASE_METAID = tempItem.getItemDamage();
                continue;
            }

            // CPU
            if (tempItem.getUnlocalizedName() == COMPONENT_CPU_INTERNALNAME)
            {
                COMPONENT_CPU_METAID = tempItem.getItemDamage();
                continue;
            }

            // Fan
            if (tempItem.getUnlocalizedName() == COMPONENT_FAN_INTERNALNAME)
            {
                COMPONENT_FAN_METAID = tempItem.getItemDamage();
                continue;
            }

            // Power Supply
            if (tempItem.getUnlocalizedName() == COMPONENT_POWERSUPPLY_INTERNALNAME)
            {
                COMPONENT_POWERSUPPLY_METAID = tempItem.getItemDamage();
                continue;
            }

            // RAM
            if (tempItem.getUnlocalizedName() == COMPONENT_RAM_INTERNALNAME)
            {
                COMPONENT_RAM_METAID = tempItem.getItemDamage();
                continue;
            }

            // Silicon Wafer
            if (tempItem.getUnlocalizedName() == COMPONENT_SILICONWAFER_INTERNALNAME)
            {
                COMPONENT_SILICONWAFER_METAID = tempItem.getItemDamage();
                continue;
            }

            // Computer
            if (tempItem.getUnlocalizedName() == COMPONENT_COMPUTER_INTERNALNAME)
            {
                COMPONENT_COMPUTER_METAID = tempItem.getItemDamage();
                continue;
            }

            // Screen
            if (tempItem.getUnlocalizedName() == COMPONENT_SCREEN_INTERNALNAME)
            {
                COMPONENT_SCREEN_METAID = tempItem.getItemDamage();
                continue;
            }

            // Transistor
            if (tempItem.getUnlocalizedName() == COMPONENT_TRANSISTOR_INTERNALNAME)
            {
                COMPONENT_TRANSISTOR_METAID = tempItem.getItemDamage();
                continue;
            }

            // Fused Quartz
            if (tempItem.getUnlocalizedName() == COMPONENT_FUSEDQUARTZ_INTERNALNAME)
            {
                COMPONENT_FUSEDQUARTZ_METAID = tempItem.getItemDamage();
                continue;
            }

            // Circuit Comparator
            if (tempItem.getUnlocalizedName() == CIRCUIT_COMPARATOR_INTERNALNAME)
            {
                CIRCUIT_COMPARATOR_METAID = tempItem.getItemDamage();
                continue;
            }

            // Circuit Diamond
            if (tempItem.getUnlocalizedName() == CIRCUIT_DIAMOND_INTERNALNAME)
            {
                CIRCUIT_DIAMOND_METAID = tempItem.getItemDamage();
                continue;
            }

            // Circuit Emerald
            if (tempItem.getUnlocalizedName() == CIRCUIT_EMERALD_INTERNALNAME)
            {
                CIRCUIT_EMERALD_METAID = tempItem.getItemDamage();
                continue;
            }

            // Circuit Ender Eye
            if (tempItem.getUnlocalizedName() == CIRCUIT_ENDEREYE_INTERNALNAME)
            {
                CIRCUIT_ENDEREYE_METAID = tempItem.getItemDamage();
                continue;
            }

            // Circuit Ender Pearl
            if (tempItem.getUnlocalizedName() == CIRCUIT_ENDERPEARL_INTERNALNAME)
            {
                CIRCUIT_ENDERPEARL_METAID = tempItem.getItemDamage();
                continue;
            }

            // Circuit Glowstone
            if (tempItem.getUnlocalizedName() == CIRCUIT_GLOWSTONE_INTERNALNAME)
            {
                CIRCUIT_GLOWSTONE_METAID = tempItem.getItemDamage();
                continue;
            }

            // Circuit Redstone
            if (tempItem.getUnlocalizedName() == CIRCUIT_REDSTONE_INTERNALNAME)
            {
                CIRCUIT_REDSTONE_METAID = tempItem.getItemDamage();
                continue;
            }

            // Circuit Spider Eye
            if (tempItem.getUnlocalizedName() == CIRCUIT_SPIDEREYE_INTERNALNAME)
            {
                CIRCUIT_SPIDEREYE_METAID = tempItem.getItemDamage();
                continue;
            }
            
            // Magnetic Tape
            if (tempItem.getUnlocalizedName() == COMPONENT_MAGNETICTAPE_INTERNALNAME)
            {
                COMPONENT_MAGNETICTAPE_METAID = tempItem.getItemDamage();
                continue;
            }
        }

        // ---------------------------
        // TRANSISTOR CREATION PROCESS
        // ---------------------------

        // Bake 1 Quartz = 1 Fused Quartz.
        FurnaceRecipes.smelting().addSmelting(Item.netherQuartz.itemID, new ItemStack(MAINFRAME_COMPONENTS_METAITEM, 1, COMPONENT_FUSEDQUARTZ_METAID), 0.1F);

        // Bake 1 Quartz Block = 4 Fused Quartz.
        FurnaceRecipes.smelting().addSmelting(Block.blockNetherQuartz.blockID, new ItemStack(MAINFRAME_COMPONENTS_METAITEM, 4, COMPONENT_FUSEDQUARTZ_METAID), 0.1F);

        // Craft 1 Fire Charge + 1 Sand = 8 Fused Quartz.
        GameRegistry.addShapelessRecipe(new ItemStack(MAINFRAME_COMPONENTS_METAITEM, 8, COMPONENT_FUSEDQUARTZ_METAID), new Object[]
        { new ItemStack(Item.fireballCharge), new ItemStack(Block.sand) });

        // ---------------------
        // CIRCUIT BOARD RECIPES
        // ---------------------

        // Circuit Comparator
        GameRegistry.addRecipe(new ItemStack(MAINFRAME_COMPONENTS_METAITEM, 1, CIRCUIT_COMPARATOR_METAID), new Object[]
        { "TTT",
          "TCT",
          "TTT",
          
          'T', new ItemStack(MAINFRAME_COMPONENTS_METAITEM, 1, COMPONENT_TRANSISTOR_METAID),
          'C', Item.comparator,
        });
        
        // Circuit Diamond
        GameRegistry.addRecipe(new ItemStack(MAINFRAME_COMPONENTS_METAITEM, 1, CIRCUIT_DIAMOND_METAID), new Object[]
        { "TTT",
          "TDT",
          "TTT",
          
          'T', new ItemStack(MAINFRAME_COMPONENTS_METAITEM, 1, COMPONENT_TRANSISTOR_METAID),
          'D', Item.diamond,
        });
        
        // Circuit Emerald
        GameRegistry.addRecipe(new ItemStack(MAINFRAME_COMPONENTS_METAITEM, 1, CIRCUIT_EMERALD_METAID), new Object[]
        { "TTT",
          "TET",
          "TTT",
          
          'T', new ItemStack(MAINFRAME_COMPONENTS_METAITEM, 1, COMPONENT_TRANSISTOR_METAID),
          'E', Item.emerald,
        });
        
        // Circuit Ender Eye
        GameRegistry.addRecipe(new ItemStack(MAINFRAME_COMPONENTS_METAITEM, 1, CIRCUIT_ENDEREYE_METAID), new Object[]
        { "TTT",
          "TET",
          "TTT",
          
          'T', new ItemStack(MAINFRAME_COMPONENTS_METAITEM, 1, COMPONENT_TRANSISTOR_METAID),
          'E', Item.eyeOfEnder,
        });
        
        // Circuit Ender Pearl
        GameRegistry.addRecipe(new ItemStack(MAINFRAME_COMPONENTS_METAITEM, 1, CIRCUIT_ENDERPEARL_METAID), new Object[]
        { "TTT",
          "TPT",
          "TTT",
          
          'T', new ItemStack(MAINFRAME_COMPONENTS_METAITEM, 1, COMPONENT_TRANSISTOR_METAID),
          'P', Item.enderPearl,
        });
        
        // Circuit Glowstone
        GameRegistry.addRecipe(new ItemStack(MAINFRAME_COMPONENTS_METAITEM, 1, CIRCUIT_GLOWSTONE_METAID), new Object[]
        { "TTT",
          "TGT",
          "TTT",
          
          'T', new ItemStack(MAINFRAME_COMPONENTS_METAITEM, 1, COMPONENT_TRANSISTOR_METAID),
          'G', Item.glowstone,
        });
        
        // Circuit Redstone
        GameRegistry.addRecipe(new ItemStack(MAINFRAME_COMPONENTS_METAITEM, 1, CIRCUIT_REDSTONE_METAID), new Object[]
        { "TTT",
          "TRT",
          "TTT",
          
          'T', new ItemStack(MAINFRAME_COMPONENTS_METAITEM, 1, COMPONENT_TRANSISTOR_METAID),
          'R', Item.redstone,
        });
        
        // Circuit Spider Eye
        GameRegistry.addRecipe(new ItemStack(MAINFRAME_COMPONENTS_METAITEM, 1, CIRCUIT_SPIDEREYE_METAID), new Object[]
        { "TTT",
          "TST",
          "TTT",
          
          'T', new ItemStack(MAINFRAME_COMPONENTS_METAITEM, 1, COMPONENT_TRANSISTOR_METAID),
          'S', Item.spiderEye,
        });
        
        // -----------------
        // COMPONENT RECIPES
        // -----------------
        
        // Computer
        GameRegistry.addRecipe(new ItemStack(MAINFRAME_COMPONENTS_METAITEM, 1, COMPONENT_COMPUTER_METAID), new Object[]
        { "ECE",
          "FBD",
          "EAE",
          
          'A', new ItemStack(MAINFRAME_COMPONENTS_METAITEM, 1, COMPONENT_SCREEN_METAID),
          'B', new ItemStack(MAINFRAME_COMPONENTS_METAITEM, 1, COMPONENT_CPU_METAID),
          'C', new ItemStack(MAINFRAME_COMPONENTS_METAITEM, 1, COMPONENT_FAN_METAID),
          'D', new ItemStack(MAINFRAME_COMPONENTS_METAITEM, 1, COMPONENT_POWERSUPPLY_METAID),
          'E', new ItemStack(MAINFRAME_COMPONENTS_METAITEM, 1, COMPONENT_CASE_METAID),
          'F', new ItemStack(MAINFRAME_COMPONENTS_METAITEM, 1, COMPONENT_RAM_METAID),
        });
        
        // Case
        GameRegistry.addRecipe(new ItemStack(MAINFRAME_COMPONENTS_METAITEM, 1, COMPONENT_CASE_METAID), new Object[]
        { "121",
          "2 2",
          "121",
          
          '1', Item.ingotIron,
          '2', Item.stick
        });
        
        // Screen
        GameRegistry.addRecipe(new ItemStack(MAINFRAME_COMPONENTS_METAITEM, 1, COMPONENT_SCREEN_METAID), new Object[]
        { "444",
          "333",
          "212",
          
          '1', new ItemStack(MAINFRAME_COMPONENTS_METAITEM, 1, CIRCUIT_DIAMOND_METAID),
          '2', new ItemStack(MAINFRAME_COMPONENTS_METAITEM, 1, CIRCUIT_REDSTONE_METAID),
          '3', new ItemStack(MAINFRAME_COMPONENTS_METAITEM, 1, COMPONENT_TRANSISTOR_METAID),
          '4', Block.glass,
        });
        
        // Power Supply
        GameRegistry.addRecipe(new ItemStack(MAINFRAME_COMPONENTS_METAITEM, 1, COMPONENT_POWERSUPPLY_METAID), new Object[]
        { "141",
          "323",
          "141",
          
          '1', Item.ingotIron,
          '2', new ItemStack(MAINFRAME_COMPONENTS_METAITEM, 1, CIRCUIT_REDSTONE_METAID),
          '3', new ItemStack(MAINFRAME_COMPONENTS_METAITEM, 1, COMPONENT_TRANSISTOR_METAID),
          '4', Block.blockRedstone,
        });
        
        // Fan
        GameRegistry.addRecipe(new ItemStack(MAINFRAME_COMPONENTS_METAITEM, 1, COMPONENT_FAN_METAID), new Object[]
        { "121",
          "222",
          "121",
          
          '1', new ItemStack(MAINFRAME_COMPONENTS_METAITEM, 1, COMPONENT_CASE_METAID),
          '2', Item.ingotIron,
        });
        
        // Magnetic Tape
        GameRegistry.addRecipe(new ItemStack(MAINFRAME_COMPONENTS_METAITEM, 1, COMPONENT_MAGNETICTAPE_METAID), new Object[]
        { "111",
          "222",
          
          '1', Item.redstone,
          '2', Item.slimeBall,
        });
    }
}

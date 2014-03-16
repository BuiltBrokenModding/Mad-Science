package madscience;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.registry.GameRegistry;

public class MadRecipes
{
    public static void createWeaponRecipes()
    {
        // Creates all the needed recipes to craft and interact with weapons.
        
        // Add 99 types of recipes for magazines that always return proper amount of bullets.
        for (int i = 1; i <= 99; i++)
        {
            GameRegistry.addShapelessRecipe(new ItemStack(MadWeapons.WEAPONITEM_BULLETITEM, i), new Object[]
                    { 
                        new ItemStack(MadWeapons.WEAPONITEM_MAGAZINEITEM, 1, i),
                    });
        }
        
        // Add 99 types of recipes for magazines that always return properly filled magazine.
        for (int i = 0; i <= 99; i++)
        {
            GameRegistry.addShapelessRecipe(new ItemStack(MadWeapons.WEAPONITEM_MAGAZINEITEM, 1, i + 1), new Object[]
                    { 
                        new ItemStack(MadWeapons.WEAPONITEM_MAGAZINEITEM, 1, i),
                        new ItemStack(MadWeapons.WEAPONITEM_BULLETITEM, 1),
                    });
        }        
    }
    
    public static void createCircuitRecipes()
    {
        // Create circuits which are used in the creation of other machines in the mod.
        
        // Circuit Comparator
        GameRegistry.addRecipe(new ItemStack(MadCircuits.CIRCUIT_COMPARATOR), new Object[]
        { "TTT", 
          "TCT", 
          "TTT",

        'T', new ItemStack(MadComponents.COMPONENT_TRANSISTOR),
        'C', Item.comparator, });
        
        // Circuit Diamond
        GameRegistry.addRecipe(new ItemStack(MadCircuits.CIRCUIT_DIAMOND), new Object[]
        { "TTT",
          "TDT",
          "TTT",

        'T', new ItemStack(MadComponents.COMPONENT_TRANSISTOR),
        'D', Item.diamond, });
        
        // Circuit Emerald
        GameRegistry.addRecipe(new ItemStack(MadCircuits.CIRCUIT_EMERALD), new Object[]
        { "TTT",
          "TET", 
          "TTT",

        'T', new ItemStack(MadComponents.COMPONENT_TRANSISTOR),
        'E', Item.emerald, });
        
        // Circuit Ender Eye
        GameRegistry.addRecipe(new ItemStack(MadCircuits.CIRCUIT_ENDEREYE), new Object[]
        { "TTT",
          "TET",
          "TTT",

        'T', new ItemStack(MadComponents.COMPONENT_TRANSISTOR),
        'E', Item.eyeOfEnder, });
        
        // Circuit Ender Pearl
        GameRegistry.addRecipe(new ItemStack(MadCircuits.CIRCUIT_ENDERPEARL), new Object[]
        { "TTT", 
          "TPT", 
          "TTT",

        'T', new ItemStack(MadComponents.COMPONENT_TRANSISTOR),
        'P', Item.enderPearl, });
        
        // Circuit Glowstone
        GameRegistry.addRecipe(new ItemStack(MadCircuits.CIRCUIT_GLOWSTONE), new Object[]
        { "TTT",
          "TGT",
          "TTT",

        'T', new ItemStack(MadComponents.COMPONENT_TRANSISTOR),
        'G', Item.glowstone, });
        
        // Circuit Redstone
        GameRegistry.addRecipe(new ItemStack(MadCircuits.CIRCUIT_REDSTONE), new Object[]
        { "TTT", 
          "TRT", 
          "TTT",

        'T', new ItemStack(MadComponents.COMPONENT_TRANSISTOR),
        'R', Item.redstone, });
        
        // Circuit Spider Eye
        GameRegistry.addRecipe(new ItemStack(MadCircuits.CIRCUIT_SPIDEREYE), new Object[]
        { "TTT",
          "TST", 
          "TTT",

        'T', new ItemStack(MadComponents.COMPONENT_TRANSISTOR),
        'S', Item.spiderEye, });
    }

    public static void createComponentsRecipes()
    {
        // Create components that are needed to craft everything else in the mod.
        
        // Case
        GameRegistry.addRecipe(new ItemStack(MadComponents.COMPONENT_CASE), new Object[]
        { "121",
          "2 2",
          "121",

        '1', Item.ingotIron,
        '2', Item.stick });
        
        // Computer
        GameRegistry.addRecipe(new ItemStack(MadComponents.COMPONENT_COMPUTER), new Object[]
        { "ECE", 
          "FBD",
          "EAE",

        'A', new ItemStack(MadComponents.COMPONENT_SCREEN),
        'B', new ItemStack(MadComponents.COMPONENT_CPU), 
        'C', new ItemStack(MadComponents.COMPONENT_FAN),
        'D', new ItemStack(MadComponents.COMPONENT_POWERSUPPLY),
        'E', new ItemStack(MadComponents.COMPONENT_CASE),
        'F', new ItemStack(MadComponents.COMPONENT_RAM), });
        
        // Fan
        GameRegistry.addRecipe(new ItemStack(MadComponents.COMPONENT_FAN), new Object[]
        { "121",
          "222",
          "121",

        '1', new ItemStack(MadComponents.COMPONENT_CASE),
        '2', Item.ingotIron, });
        
        
        // Bake 1 Quartz = 1 Fused Quartz.
        FurnaceRecipes.smelting().addSmelting(Item.netherQuartz.itemID, new ItemStack(MadComponents.COMPONENT_FUSEDQUARTZ), 0.1F);

        // Bake 1 Quartz Block = 4 Fused Quartz.
        FurnaceRecipes.smelting().addSmelting(Block.blockNetherQuartz.blockID, new ItemStack(MadComponents.COMPONENT_FUSEDQUARTZ, 4), 0.1F);

        // Craft 1 Fire Charge + 1 Sand = 8 Fused Quartz.
        GameRegistry.addShapelessRecipe(new ItemStack(MadComponents.COMPONENT_FUSEDQUARTZ, 8), new Object[]
        { new ItemStack(Item.fireballCharge), new ItemStack(Block.sand) });
        
        // Magnetic Tape
        GameRegistry.addRecipe(new ItemStack(MadComponents.COMPONENT_MAGNETICTAPE), new Object[]
        { "111",
          "222",

        '1', Item.redstone,
        '2', Item.slimeBall, });
        
        // Power Supply
        GameRegistry.addRecipe(new ItemStack(MadComponents.COMPONENT_POWERSUPPLY), new Object[]
        { "141",
          "323",
          "141",

        '1', Item.ingotIron,
        '2', new ItemStack(MadCircuits.CIRCUIT_REDSTONE),
        '3', new ItemStack(MadComponents.COMPONENT_TRANSISTOR),
        '4', Block.blockRedstone, });
        
        // Screen
        GameRegistry.addRecipe(new ItemStack(MadComponents.COMPONENT_SCREEN), new Object[]
        { "444",
          "333",
          "212",

        '1', new ItemStack(MadCircuits.CIRCUIT_DIAMOND),
        '2', new ItemStack(MadCircuits.CIRCUIT_REDSTONE),
        '3', new ItemStack(MadComponents.COMPONENT_TRANSISTOR),
        '4', Block.glass, });
        
        // Thumper
        GameRegistry.addRecipe(new ItemStack(MadComponents.COMPONENT_THUMPER), new Object[]
        { "535",
          "212",
          "222",

        '1', new ItemStack(MadComponents.COMPONENT_POWERSUPPLY),
        '2', MadBlocks.ENDERSLIMEBLOCK,
        '3', new ItemStack(Block.blockRedstone),
        '5', Block.pistonBase,});
        
        // Enderslime Block
        GameRegistry.addRecipe(new ItemStack(MadBlocks.ENDERSLIMEBLOCK), new Object[]
        { "111",
          "111",
          "111",

        '1', new ItemStack(MadComponents.COMPONENT_ENDERSLIME),});
    }
}

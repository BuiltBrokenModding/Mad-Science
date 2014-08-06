package madscience;

import madscience.factory.mod.MadMod;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

class MadRecipes
{    
    static void createOtherRecipes()
    {
        // Creates recipes that fit in no other category, such as one to aid in creation of wither skeletons in early-game.

        // Filled mutant DNA syringe ontop of skeleton skull surrounded by soul sand will make a wither skeleton egg.
        MadMod.log().info("-Wither skeleton early-game spawn egg recipe");
        GameRegistry.addRecipe(new ItemStack(MadEntities.GENETICALLYMODIFIED_MONSTERPLACER, 1, MadConfig.GMO_WITHERSKELETON_METAID), new Object[]
        { "212",
          "232",
          "242",

        '1', new ItemStack(MadNeedles.NEEDLE_MUTANT, 1, OreDictionary.WILDCARD_VALUE),
        '2', Block.slowSand,
        '3', new ItemStack(Item.skull, 1, 0),
        '4', new ItemStack(Item.egg, 1, 0)});
        
        // Laboratory Coat Body.
        MadMod.log().info("-Laboratory Coat Body recipe");
        GameRegistry.addRecipe(new ShapedOreRecipe(MadEntities.LABCOAT_BODY, new Object[]
        { "W W",
          "WSW",
          "WSW", 
          
          // White Wool (Coat)
          'W', new ItemStack(Block.cloth, 1, 0),
          
          // Light Blue Wool (Shirt)
          'S', new ItemStack(Block.cloth, 1, 3),
        }));
        
        // Laboratory Coat Leggings.
        MadMod.log().info("-Laboratory Coat Leggings recipe");
        GameRegistry.addRecipe(new ShapedOreRecipe(MadEntities.LABCOAT_LEGGINGS, new Object[]
        { "PBP",
          "P P",
          "P P", 
          
          // Brown Wool (Pants)
          'P', new ItemStack(Block.cloth, 1, 12),
          
          // Black Wool (Beltbuckle)
          'B', new ItemStack(Block.cloth, 1, 15),
          }));
        
        // Safety Goggles.
        MadMod.log().info("-Safety Goggles recipe");
        GameRegistry.addRecipe(new ShapedOreRecipe(MadEntities.LABCOAT_GOGGLES, new Object[]
        { "SSS",
          "S S",
          "SGS", 
          
          'S', new ItemStack(Item.silk, 1, 0),
          'G', new ItemStack(Block.glass, 1, 0)
          }));
        
        // Warning Sign
        MadMod.log().info("-Warning Sign recipe");
        GameRegistry.addRecipe(new ShapedOreRecipe(MadEntities.WARNING_SIGN, new Object[]
        { "WWW",
          "IPI",
          "WWW", 
          
          // Yellow Wool
          'W', new ItemStack(Block.cloth, 1, 4),
          'I', new ItemStack(Item.ingotIron, 1, 0),
          'P', new ItemStack(Item.painting, 1, 0)
          }));
    }
    
    static void createWeaponRecipes()
    {
        // Creates all the needed recipes to craft and interact with weapons.
        
        // Add 99 types of recipes for magazines that always return proper amount of bullets.
        MadMod.log().info("-99 magazine unloading recipes");
        for (int i = 1; i <= 99; i++)
        {
            // A magazine with 3 bullets actually has damage value of 96 and still returns 3 bullets.
            //MadScience.logger.info("1 magazine with " + String.valueOf(Math.abs(100 - i)) + " damage will create " + String.valueOf(Math.abs(i)) + " bullets.");
            GameRegistry.addShapelessRecipe(new ItemStack(MadWeapons.WEAPONITEM_BULLETITEM, Math.abs(i)), new Object[]
                    { 
                        new ItemStack(MadWeapons.WEAPONITEM_MAGAZINEITEM, 1, Math.abs(100 - i)),
                    });
        }
        
        // Adding an empty (no damage) magazine and a bullet will create a magazine with 99 damage.
        MadMod.log().info("-Index 0 magazine (first bullet) recipe");
        GameRegistry.addShapelessRecipe(new ItemStack(MadWeapons.WEAPONITEM_MAGAZINEITEM, 1, 99), new Object[]
                { 
                    new ItemStack(MadWeapons.WEAPONITEM_MAGAZINEITEM, 1, 0),
                    new ItemStack(MadWeapons.WEAPONITEM_BULLETITEM, 1),
                });
        
        // Add 99 types of recipes for magazines that always return properly filled magazine.
        MadMod.log().info("-99 bullet loading recipes");
        for (int i = 1; i <= 98; i++)
        {   
            // A magazine and 1 bullet would create a magazine with damage of 99.
            //MadScience.logger.info("1 bullet and a magazine with " + String.valueOf(Math.abs(100 - i)) + " damage will create a magazine with " + String.valueOf(Math.abs(99 - i)) + " damage.");
            GameRegistry.addShapelessRecipe(new ItemStack(MadWeapons.WEAPONITEM_MAGAZINEITEM, 1, Math.abs(99 - i)), new Object[]
                    { 
                        new ItemStack(MadWeapons.WEAPONITEM_MAGAZINEITEM, 1, Math.abs(100 - i)),
                        new ItemStack(MadWeapons.WEAPONITEM_BULLETITEM, 1),
                    });
        }
        
        // M41A Pulse Rifle Grenades
        MadMod.log().info("-Pulse Rifle Grenades recipe");
        GameRegistry.addRecipe(new ItemStack(MadWeapons.WEAPONITEM_GRENADEITEM, 16), new Object[]
        { "424",
          "424",
          "313",

        '1', new ItemStack(MadComponents.COMPONENT_PULSERIFLEGRENADECASING, 16),
        '2', Block.tnt,
        '3', Item.gunpowder,
        '4', Item.ingotIron,});
        
        // M41A Pulse Rifle Bullets
        MadMod.log().info("-Pulse Rifle Bullets recipe");
        GameRegistry.addRecipe(new ItemStack(MadWeapons.WEAPONITEM_BULLETITEM, 64), new Object[]
        { "454",
          "424",
          "313",

          '1', new ItemStack(MadComponents.COMPONENT_PULSERIFLEBULLETCASING, 64),
          '2', Block.tnt,
          '3', Item.gunpowder,
          '4', Block.hardenedClay,
          '5', Block.obsidian,});
    }
    
    static void createCircuitRecipes()
    {
        // Create circuits which are used in the creation of other machines in the mod.
        
        // Circuit Comparator
        MadMod.log().info("-Comparator Circuit recipe");
        GameRegistry.addRecipe(new ItemStack(MadCircuits.CIRCUIT_COMPARATOR), new Object[]
        { "TTT", 
          "TCT", 
          "TTT",

        'T', new ItemStack(MadComponents.COMPONENT_TRANSISTOR),
        'C', Item.comparator, });
        
        // Circuit Diamond
        MadMod.log().info("-Diamond Circuit recipe");
        GameRegistry.addRecipe(new ItemStack(MadCircuits.CIRCUIT_DIAMOND), new Object[]
        { "TTT",
          "TDT",
          "TTT",

        'T', new ItemStack(MadComponents.COMPONENT_TRANSISTOR),
        'D', Item.diamond, });
        
        // Circuit Emerald
        MadMod.log().info("-Emerald Circuit recipe");
        GameRegistry.addRecipe(new ItemStack(MadCircuits.CIRCUIT_EMERALD), new Object[]
        { "TTT",
          "TET", 
          "TTT",

        'T', new ItemStack(MadComponents.COMPONENT_TRANSISTOR),
        'E', Item.emerald, });
        
        // Circuit Ender Eye
        MadMod.log().info("-Ender Eye Circuit recipe");
        GameRegistry.addRecipe(new ItemStack(MadCircuits.CIRCUIT_ENDEREYE), new Object[]
        { "TTT",
          "TET",
          "TTT",

        'T', new ItemStack(MadComponents.COMPONENT_TRANSISTOR),
        'E', Item.eyeOfEnder, });
        
        // Circuit Ender Pearl
        MadMod.log().info("-Ender Pearl Circuit recipe");
        GameRegistry.addRecipe(new ItemStack(MadCircuits.CIRCUIT_ENDERPEARL), new Object[]
        { "TTT", 
          "TPT", 
          "TTT",

        'T', new ItemStack(MadComponents.COMPONENT_TRANSISTOR),
        'P', Item.enderPearl, });
        
        // Circuit Glowstone
        MadMod.log().info("-Glowstone Circuit recipe");
        GameRegistry.addRecipe(new ItemStack(MadCircuits.CIRCUIT_GLOWSTONE), new Object[]
        { "TTT",
          "TGT",
          "TTT",

        'T', new ItemStack(MadComponents.COMPONENT_TRANSISTOR),
        'G', Item.glowstone, });
        
        // Circuit Redstone
        MadMod.log().info("-Redstone Circuit recipe");
        GameRegistry.addRecipe(new ItemStack(MadCircuits.CIRCUIT_REDSTONE), new Object[]
        { "TTT", 
          "TRT", 
          "TTT",

        'T', new ItemStack(MadComponents.COMPONENT_TRANSISTOR),
        'R', Item.redstone, });
        
        // Circuit Spider Eye
        MadMod.log().info("-Spider Eye Circuit recipe");
        GameRegistry.addRecipe(new ItemStack(MadCircuits.CIRCUIT_SPIDEREYE), new Object[]
        { "TTT",
          "TST", 
          "TTT",

        'T', new ItemStack(MadComponents.COMPONENT_TRANSISTOR),
        'S', Item.spiderEye, });
    }

    static void createComponentsRecipes()
    {        
        // Magnetic Tape
        MadMod.log().info("-Magnetic Tape Component recipe");
        GameRegistry.addRecipe(new ItemStack(MadComponents.COMPONENT_MAGNETICTAPE), new Object[]
        { "111",
          "222",

        '1', Item.redstone,
        '2', Item.slimeBall, });
        
        // Screen
        MadMod.log().info("-Screen Component recipe");
        GameRegistry.addRecipe(new ItemStack(MadComponents.COMPONENT_SCREEN), new Object[]
        { "444",
          "333",
          "212",

        '1', new ItemStack(MadCircuits.CIRCUIT_DIAMOND),
        '2', new ItemStack(MadCircuits.CIRCUIT_REDSTONE),
        '3', new ItemStack(MadComponents.COMPONENT_TRANSISTOR),
        '4', Block.glass, });
        
        // Thumper
        MadMod.log().info("-Thumper Component recipe");
        GameRegistry.addRecipe(new ItemStack(MadComponents.COMPONENT_THUMPER), new Object[]
        { "535",
          "212",
          "222",

        '1', new ItemStack(MadComponents.COMPONENT_POWERSUPPLY),
        '2', MadBlocks.ENDERSLIMEBLOCK,
        '3', new ItemStack(Block.blockRedstone),
        '5', Block.pistonBase,});
        
        // Enderslime Block
        MadMod.log().info("-Enderslime Component recipe");
        GameRegistry.addRecipe(new ItemStack(MadBlocks.ENDERSLIMEBLOCK), new Object[]
        { "111",
          "111",
          "111",

        '1', new ItemStack(MadComponents.COMPONENT_ENDERSLIME),});
    }
}

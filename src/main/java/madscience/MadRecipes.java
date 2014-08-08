package madscience;

import madscience.factory.mod.MadMod;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

class MadRecipes
{    
    static void createComponentsRecipes()
    {                        
        // Enderslime Block
        MadMod.log().info("-Enderslime Component recipe");
        GameRegistry.addRecipe(new ItemStack(MadBlocks.ENDERSLIMEBLOCK), new Object[]
        { "111",
          "111",
          "111",

        '1', new ItemStack(MadComponents.COMPONENT_ENDERSLIME),});
    }
}

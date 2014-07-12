/** Copyright (c) SpaceToad, 2011 http://www.mod-buildcraft.com
 * 
 * BuildCraft is distributed under the terms of the Minecraft Mod Public License 1.0, or MMPL. Please check the contents of the license located in http://www.mod-buildcraft.com/MMPL-1.0.txt */
package madscience.tile.mainframe;

import java.util.SortedSet;
import java.util.TreeSet;

import net.minecraft.item.ItemStack;

import com.google.common.base.Objects;

public final class MainframeRecipes
{
    static final class GenomeRecipe implements Comparable<GenomeRecipe>
    {

        private final ItemStack ingredient1;
        private final ItemStack ingredient2;
        final ItemStack result;
        final int delay;

        private GenomeRecipe(ItemStack ingredient1, ItemStack ingredient2, ItemStack result, int delay)
        {
            if (ingredient1 == null)
                throw new IllegalArgumentException("First Ingredient cannot be null!");
            this.ingredient1 = ingredient1;
            this.ingredient2 = ingredient2;
            this.result = result;
            this.delay = delay;
        }

        // Compares to only the types of source materials.
        // We consider non-null < null in order that one-ingredient recipe is
        // checked after
        // the failure of matching two-ingredient recipes which include that
        // liquid.
        @Override
        public int compareTo(GenomeRecipe other)
        {
            if (other == null)
                return -1;
            else if (ingredient1.getItem() != other.ingredient1.getItem())
                return ingredient1.getItem().getUnlocalizedName().compareTo(other.ingredient1.getItem().getUnlocalizedName());
            else if (ingredient1.stackSize != other.ingredient1.stackSize)
                return other.ingredient1.stackSize - ingredient1.stackSize;
            else if (ingredient2 == null)
                return other.ingredient2 == null ? 0 : 1;
            else if (other.ingredient2 == null)
                return -1;
            else if (ingredient2.getItem() != other.ingredient2.getItem())
                return ingredient2.getItem().getUnlocalizedName().compareTo(other.ingredient2.getItem().getUnlocalizedName());
            else if (ingredient2.stackSize != other.ingredient2.stackSize)
                return other.ingredient2.stackSize - ingredient2.stackSize;

            return 0;
        }

        // equals() should be consistent with compareTo().
        @Override
        public boolean equals(Object obj)
        {
            return obj instanceof GenomeRecipe && Objects.equal(ingredient1, ((GenomeRecipe) obj).ingredient1) && Objects.equal(ingredient2, ((GenomeRecipe) obj).ingredient2);
        }

        // hashCode() should be overridden because equals() was overridden.
        @Override
        public int hashCode()
        {
            return Objects.hashCode(ingredient1, ingredient2);
        }
    }

    private static SortedSet<GenomeRecipe> recipes = new TreeSet<GenomeRecipe>();

    public static void addRecipe(ItemStack ingredient1, ItemStack ingredient2, ItemStack result, int delay)
    {
        GenomeRecipe recipe = new GenomeRecipe(ingredient1, ingredient2, result, delay);
        recipes.add(recipe);
    }

    static GenomeRecipe findGenomeRecipe(ItemStack item1, ItemStack item2)
    {
        // Loop through all the recipes that we have for the mainframe computer.
        for (GenomeRecipe recipe : recipes)
        {
            // No inputs, return.
            if (item1 == null && item2 == null)
            {
                continue;
            }

            // Attempt to match-up items inside mainframe with known recipes.
            if (recipe.ingredient1.isItemEqual(item1) && recipe.ingredient2.isItemEqual(item2) || recipe.ingredient1.isItemEqual(item2) && recipe.ingredient2.isItemEqual(item1))
            {
                return recipe;
            }
        }

        return null;
    }

    
}

package madscience.factory.recipes;

import madscience.factory.slotcontainers.MadSlotContainerTypeEnum;
import net.minecraft.item.ItemStack;

public interface IMadRecipeComponent
{

    public abstract MadSlotContainerTypeEnum getSlotType();

    public abstract String getInternalName();
    
    public abstract int getAmount();
    
    public abstract String getNameWithModID();
    
    public abstract int getMetaDamage();
    
    public abstract boolean isLoaded();

    public abstract ItemStack getItemStack();
    
    public abstract void loadRecipe(ItemStack associatedItemStack);

    public abstract String getModID();

    public abstract String getExpectedItemName();

}
package madscience.factory.slot;

import madscience.factory.MadTileEntityFactory;
import madscience.factory.container.MadSlotContainer;
import madscience.factory.container.MadSlotContainerTypeEnum;
import madscience.factory.product.MadTileEntityFactoryProduct;
import madscience.factory.recipe.MadRecipe;
import madscience.factory.recipe.MadRecipeComponent;
import madscience.factory.tile.MadTileEntityPrefab;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;

public class MadInputSlot extends Slot
{
    private String machineName = null;
    private MadTileEntityFactoryProduct registeredMachine = null;

    public MadInputSlot(
            MadTileEntityPrefab tileEntity,
            int index,
            int x,
            int y,
            MadSlotContainerTypeEnum slotType)
    {
        super(tileEntity, index, x, y);
        this.machineName = tileEntity.getMachineInternalName();
    }
    
    @Override
    public int getSlotStackLimit()
    {
        // TODO: Make this change to size based on recipe table.
        return 64;
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        // Grab the recipe archive object array.
        MadTileEntityFactoryProduct possibleMachine = MadTileEntityFactory.instance().getMachineInfo(machineName);

        // Prevent multiple queries.
        if (registeredMachine == null)
        {
            this.registeredMachine = possibleMachine;
        }

        if (this.registeredMachine != null)
        {
            // Check if this slot is for buckets (filled or unfilled).
            MadSlotContainer[] containerSlots = this.registeredMachine.getContainerTemplate();
            for (MadSlotContainer machineSlot : containerSlots)
            {
                if (machineSlot.getSlotType().name().toLowerCase().contains("bucket") &&
                    stack.getItem() instanceof ItemBucket)
                {
                    return true;
                }
            }
            
            // Loop through all of the machines internal recipes and look for matches to item for any input slot.
            MadRecipe[] recipeArchiveArray = this.registeredMachine.getRecipeArchive();
            for (MadRecipe machineRecipe : recipeArchiveArray)
            {
                MadRecipeComponent[] inputIngredients = machineRecipe.getInputIngredientsArray();
                for (MadRecipeComponent recipeResult : inputIngredients)
                {
                    if (!recipeResult.isLoaded())
                    {
                        continue;
                    }

                    // Determine if this recipe result matches anything in ingredient list.
                    if (recipeResult.getAssociatedItemStack().getItem().equals(stack.getItem()))
                    {
                        return true;
                    }
                }
            }
        }

        // Ensure that we return false in the event something goes wrong before this.
        return false;
    }
}

package madscience.slot;


import madscience.container.ContainerTemplate;
import madscience.container.SlotContainerTypeEnum;
import madscience.factory.TileEntityFactory;
import madscience.product.TileEntityFactoryProduct;
import madscience.recipe.RecipeArchive;
import madscience.recipe.RecipeComponent;
import madscience.tile.TileEntityPrefab;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;


public class InputSlot extends Slot
{
    private String machineName = null;
    private TileEntityFactoryProduct registeredMachine = null;

    public InputSlot(TileEntityPrefab tileEntity, int index, int x, int y, SlotContainerTypeEnum slotType)
    {
        super( tileEntity,
               index,
               x,
               y );
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
        TileEntityFactoryProduct possibleMachine = TileEntityFactory.instance().getMachineInfo( machineName );

        // Prevent multiple queries.
        if (registeredMachine == null)
        {
            this.registeredMachine = possibleMachine;
        }

        if (this.registeredMachine != null)
        {
            // Check if this slot is for buckets (filled or unfilled).
            ContainerTemplate[] containerSlots = this.registeredMachine.getContainerTemplate();
            for (ContainerTemplate machineSlot : containerSlots)
            {
                if (machineSlot.getSlotType().name().toLowerCase().contains( "bucket" ) &&
                    stack.getItem() instanceof ItemBucket)
                {
                    return true;
                }
            }

            // Loop through all of the machines internal recipes and look for matches to item for any input slot.
            RecipeArchive[] recipeArchiveArray = this.registeredMachine.getRecipeArchive();
            for (RecipeArchive machineRecipe : recipeArchiveArray)
            {
                RecipeComponent[] inputIngredients = machineRecipe.getInputIngredientsArray();
                for (RecipeComponent recipeResult : inputIngredients)
                {
                    if (! recipeResult.isLoaded())
                    {
                        continue;
                    }

                    // Determine if this recipe result matches anything in ingredient list.
                    if (recipeResult.getAssociatedItemStack().getItem().equals( stack.getItem() ))
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
